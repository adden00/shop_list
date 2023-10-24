package com.adden00.shopping_list.features.cards.presentation

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.adden00.shopping_list.R
import com.adden00.shopping_list.app.di.ui.DaggerCardsComponent
import com.adden00.shopping_list.app.getAppComponent
import com.adden00.shopping_list.core.ViewModelFactory
import com.adden00.shopping_list.core.utills.OnCardClickListener
import com.adden00.shopping_list.databinding.DialogAddNewCardBinding
import com.adden00.shopping_list.databinding.DialogQrBinding
import com.adden00.shopping_list.databinding.FragmentCardsBinding
import com.adden00.shopping_list.features.cards.presentation.models.CardModelPres
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsEffect
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsEvent
import com.adden00.shopping_list.features.cards.presentation.mvi.CardsState
import com.adden00.shopping_list.features.cards.presentation.utills.addEmptyCard
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CardsFragment : Fragment() {
    private var _binding: FragmentCardsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CardsViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        CardsAdapter(object : OnCardClickListener {
            override fun onClick(item: CardModelPres) {
                if (item.cardQr.isNotEmpty()) {
                    openQr(item.cardQr, BarcodeFormat.QR_CODE)
                } else if (item.cardBarcode.isNotEmpty()) {
                    openQr(item.cardBarcode, BarcodeFormat.CODABAR)
                } else {
                    openQr(item.cardCode, BarcodeFormat.CODABAR)
                }
            }

            override fun onLongClick(item: CardModelPres) = Unit
            override fun onAddCard() {
                showCreateCardDialog()
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerCardsComponent.factory().create(requireContext().getAppComponent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUi()
        subscribeOnState()
    }

    private fun subscribeOnState() {
        viewModel.cardsState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.cardsEffect
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun render(state: CardsState) {
        binding.pbarCardsLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.tvEmptyList.visibility =
            if (state.cardsList.isEmpty() && !state.isLoading) View.VISIBLE else View.GONE
        binding.swipeRefresh.isRefreshing = false
        val addedList = state.cardsList.addEmptyCard()
        Log.d("CardsFragment", addedList.toString())
        adapter.submitList(state.cardsList.addEmptyCard())
    }

    private fun handleEffect(effect: CardsEffect) {
        when (effect) {
            is CardsEffect.InternetError -> Unit
            is CardsEffect.LogOut -> Unit
            is CardsEffect.ShowMessage -> Unit
            is CardsEffect.Waiting -> Unit
        }
    }

    private fun setUi() {
        binding.rcCards.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcCards.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.newEvent(CardsEvent.GetCards)
        }
        binding.fabNewCard.setOnClickListener {
        }
    }

    private fun showCreateCardDialog() {
        val dialogBinding =
            DialogAddNewCardBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()

        with(dialogBinding) {
            btnCreate.setOnClickListener {
                if (!edName.text.isNullOrEmpty() && !edNumber.text.isNullOrEmpty()) {
                    viewModel.newEvent(
                        CardsEvent.AddCard(
                            dialogBinding.edName.text?.toString() ?: "",
                            dialogBinding.edNumber.text?.toString() ?: "",
                            "",
                            "",
                            "D7FFE6"
                        )
                    )
                    dialog.dismiss()
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.fill_all_fields),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }

    private fun openQr(code: String, format: BarcodeFormat) {
        val dialogBinding =
            DialogQrBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()
        dialogBinding.imQr.setImageBitmap(getQrCodeBitmap(code, format))
        dialogBinding.btClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }

    private fun getQrCodeBitmap(content: String, format: BarcodeFormat): Bitmap {
        val size = 512 //pixels
//        val qrCodeContent = "WIFI:S:$ssid;T:WPA;P:$password;;"
//        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = MultiFormatWriter().encode(content, format, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}

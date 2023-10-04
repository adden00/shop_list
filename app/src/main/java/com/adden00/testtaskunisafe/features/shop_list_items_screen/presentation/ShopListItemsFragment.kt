package com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adden00.testtaskunisafe.R
import com.adden00.testtaskunisafe.app.di.ui.DaggerShopListItemsComponent
import com.adden00.testtaskunisafe.app.getAppComponent
import com.adden00.testtaskunisafe.core.Constants
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.core.customGetParcelable
import com.adden00.testtaskunisafe.core.utills.OnClickListener
import com.adden00.testtaskunisafe.databinding.DialogConfirmBinding
import com.adden00.testtaskunisafe.databinding.DialogInputBinding
import com.adden00.testtaskunisafe.databinding.FragmentShopListItemsBinding
import com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.models.ShopListItemModel
import com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.mvi.ShopListItemsEffect
import com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.mvi.ShopListItemsEvent
import com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation.mvi.ShopListItemsState
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.models.ShopListModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ShopListItemsFragment : Fragment() {


    private var _binding: FragmentShopListItemsBinding? = null
    private val binding get() = _binding!!

    private val currentShopList: ShopListModel by lazy {
        arguments?.customGetParcelable<ShopListModel>(Constants.NAVIGATION_ITEM_KEY) as ShopListModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ShopListItemsViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        ShopListItemsAdapter(object : OnClickListener<ShopListItemModel> {
            override fun onClick(item: ShopListItemModel) = Unit
            override fun onLongClick(item: ShopListItemModel) {
                removeListDialog(item)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerShopListItemsComponent.factory().create(requireContext().getAppComponent())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopListItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUi()
        subscribeOnState()
        viewModel.newEvent(ShopListItemsEvent.LoadAllItems(currentShopList.id))
    }

    private fun subscribeOnState() {
        viewModel.shopListItemsState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.shopListItemsEffect
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun render(state: ShopListItemsState) {
        binding.tvEmptyList.visibility = if(state.list.isEmpty()) View.VISIBLE else View.GONE
        binding.swipeRefresh.isRefreshing = false
        binding.pbarLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.pbarIsUpdating.visibility = if (state.isUpdating) View.VISIBLE else View.GONE
        adapter.submitList(state.list)
    }

    private fun handleEffect(effect: ShopListItemsEffect) {
        when (effect) {
            is ShopListItemsEffect.ShowInternetError -> {
                showSnackBar(getString(R.string.internet_error))
            }

            is ShopListItemsEffect.Waiting -> Unit
        }
    }

    private fun setUi() {
        binding.toolbar.title = currentShopList.name
        binding.rcShopListItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rcShopListItems.adapter = adapter
        binding.fabNewItem.setOnClickListener {
            showCreateItemDialog()
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.newEvent(ShopListItemsEvent.LoadAllItems(currentShopList.id))
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("SetTextI18n")
    private fun removeListDialog(item: ShopListItemModel) {
        val dialogBinding = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog
            .Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialogBinding.btnYes.setOnClickListener {
            viewModel.newEvent(ShopListItemsEvent.RemoveItem(currentShopList.id, item.id))
            dialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.tvConfirmMessage.text = getString(R.string.do_you_want_to_remove) + item.name + "?"
        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)

        dialog.show()
    }

    private fun showCreateItemDialog() {
        val dialogBinding =
            DialogInputBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()

        dialogBinding.edInput.hint = getString(R.string.name)
        dialogBinding.tvTitle.text = getString(R.string.add_new_item)
        dialogBinding.btnConfirm.text = getString(R.string.add)
        
        dialogBinding.btnConfirm.setOnClickListener {
            viewModel.newEvent(
                ShopListItemsEvent.AddNewItem(
                    currentShopList.id,
                    dialogBinding.edInput.text.toString()
                )
            )
            dialog.dismiss()
        }

        dialogBinding.edInput.addTextChangedListener {
            dialogBinding.btnConfirm.isEnabled = !it.isNullOrEmpty()
        }

        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }
}
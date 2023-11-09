package ru.usafe.shopping.features.shop_lists.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.usafe.shopping.R
import ru.usafe.shopping.app.getAppComponent
import ru.usafe.shopping.core.Constants
import ru.usafe.shopping.core.ViewModelFactory
import ru.usafe.shopping.core.utills.OnClickListener
import ru.usafe.shopping.databinding.DialogConfirmBinding
import ru.usafe.shopping.databinding.DialogInputBinding
import ru.usafe.shopping.databinding.FragmentShopListsBinding
import ru.usafe.shopping.features.shop_lists.presentation.models.ShopListModel
import ru.usafe.shopping.features.shop_lists.presentation.mvi.ShopListEffect
import ru.usafe.shopping.features.shop_lists.presentation.mvi.ShopListEvent
import ru.usafe.shopping.features.shop_lists.presentation.mvi.ShopListState
import javax.inject.Inject

class ShopListsFragment : Fragment() {

    private var _binding: FragmentShopListsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ShopListsViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        ShopListsAdapter(object : OnClickListener<ShopListModel> {
            override fun onClick(item: ShopListModel) {
                val args = bundleOf(Constants.NAVIGATION_ITEM_KEY to item)
                findNavController().navigate(
                    R.id.action_shopListFragment_to_shopListItemsFragment,
                    args
                )
            }
            override fun onLongClick(item: ShopListModel) {
                removeListDialog(item)
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ru.usafe.shopping.app.di.ui.DaggerShopListsComponent.factory()
            .create(requireContext().getAppComponent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopListsBinding.inflate(inflater, container, false)
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

    private fun setUi() {
        binding.rcShopLists.layoutManager = LinearLayoutManager(requireContext())
        binding.rcShopLists.adapter = adapter
        binding.fabNewList.setOnClickListener {
            showCreateListDialog()
        }
        binding.btnOpenCards.setOnClickListener {
            navigateToCards()
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.newEvent(ShopListEvent.GetAllShopLists)
        }
        binding.btnOpenCards.visibility = if (Constants.CARDS_TOGGLE) View.VISIBLE else View.GONE
        binding.tvAccountId.setOnClickListener {
            viewModel.newEvent(ShopListEvent.CopyShopListId { content ->
                val clipboard =
                    ContextCompat.getSystemService(
                        requireContext(),
                        ClipboardManager::class.java
                    )
                val clip = ClipData.newPlainText("Message", content)
                clipboard?.setPrimaryClip(clip)
            })
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logOut -> {
                    viewModel.newEvent(ShopListEvent.LogOut)
                }
            }
            true
        }
    }


    private fun subscribeOnState() {
        viewModel.shopListState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.shopListEffect
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun render(state: ShopListState) {
        binding.tvEmptyList.visibility =
            if (state.list.isEmpty() && !state.isLoading) View.VISIBLE else View.GONE
        binding.pbarListLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.pbarIsUpdating.visibility = if (state.isUpdating) View.VISIBLE else View.GONE
        binding.swipeRefresh.isRefreshing = false
        adapter.submitList(state.list)
        binding.tvAccountId.text = state.id ?: ""
    }

    private fun handleEffect(effect: ShopListEffect) {
        when (effect) {
            is ShopListEffect.InternetError -> {
                showSnackBar(getString(R.string.internet_error))
            }

            is ShopListEffect.LogOut -> {
                logOut()
            }

            is ShopListEffect.ShowMessage -> {
                showSnackBar(effect.message)
            }
            is ShopListEffect.Waiting -> Unit
        }
    }

    @SuppressLint("SetTextI18n")
    private fun removeListDialog(item: ShopListModel) {
        val dialogBinding = DialogConfirmBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog
            .Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        with(dialogBinding) {
            btnYes.setOnClickListener {
                viewModel.newEvent(ShopListEvent.RemoveShopList(item.id))
                dialog.dismiss()
            }
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
            tvConfirmMessage.text = getString(R.string.do_you_want_to_remove) + item.name + "?"
        }

        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }


    private fun showCreateListDialog() {
        val dialogBinding =
            DialogInputBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()

        with(dialogBinding) {
            edInput.hint = getString(R.string.name)
            tvTitle.text = getString(R.string.create_new_shopping_list)
            btnConfirm.text = getString(R.string.create)

            btnConfirm.setOnClickListener {
                viewModel.newEvent(ShopListEvent.CreateShopList(edInput.text.toString()))
                dialog.dismiss()
            }

            edInput.addTextChangedListener {
                btnConfirm.isEnabled = !it.isNullOrEmpty()
            }
        }

        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun navigateToCards() {
        findNavController().navigate(R.id.action_shopListFragment_to_cardsFragment)
    }

    private fun logOut() {
        findNavController().popBackStack()
        findNavController().navigate(R.id.main_nav)
    }

}
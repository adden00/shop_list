package com.adden00.testtaskunisafe.features.shop_lists_screen.presentation

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
import com.adden00.testtaskunisafe.R
import com.adden00.testtaskunisafe.app.di.ui.DaggerShopListsComponent
import com.adden00.testtaskunisafe.app.getAppComponent
import com.adden00.testtaskunisafe.core.Constants
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.core.utills.OnClickListener
import com.adden00.testtaskunisafe.databinding.DialogConfirmBinding
import com.adden00.testtaskunisafe.databinding.DialogInputBinding
import com.adden00.testtaskunisafe.databinding.FragmentShopListsBinding
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.models.ShopListModel
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi.ShopListEffect
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi.ShopListEvent
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.mvi.ShopListState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        DaggerShopListsComponent.factory().create(requireContext().getAppComponent()).inject(this)
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
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.newEvent(ShopListEvent.GetAllShopLists)
        }
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
                R.id.copyToken -> {

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
        binding.tvEmptyList.visibility = if(state.list.isEmpty()) View.VISIBLE else View.GONE
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
                findNavController().popBackStack()
                findNavController().navigate(R.id.main_nav)
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
}
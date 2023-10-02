package com.adden00.testtaskunisafe.features.shop_list_screen.presentation

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.databinding.DialogNewShopListBinding
import com.adden00.testtaskunisafe.databinding.FragmentShopListBinding
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi.ShopListEffect
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi.ShopListEvent
import com.adden00.testtaskunisafe.features.shop_list_screen.presentation.mvi.ShopListState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ShopListFragment : Fragment() {

    private var _binding: FragmentShopListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ShopListViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        ShopListAdapter { item ->

            //TODO кликнули по листу
        }
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
        _binding = FragmentShopListBinding.inflate(inflater, container, false)
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
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logOut -> {
                    viewModel.newEvent(ShopListEvent.LogOut)
                }
                R.id.copyToken -> {
                    viewModel.newEvent(ShopListEvent.CopyShopListId {content ->
                        val clipboard =
                            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
                        val clip = ClipData.newPlainText("Message", content)
                        clipboard?.setPrimaryClip(clip)
                    })
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
        binding.pbarListLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.swipeRefresh.isRefreshing = false
        adapter.submitList(state.list)
    }

    private fun handleEffect(effect: ShopListEffect) {
        when (effect) {
            is ShopListEffect.InternetError -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.internet_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            is ShopListEffect.LogOut -> {
                findNavController().navigate(R.id.action_shopListFragment_to_startFragment)
            }

            is ShopListEffect.ShowMessage -> {
                Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT).show()

            }

            is ShopListEffect.Waiting -> Unit
        }
    }

    private fun showCreateListDialog() {
        val createListBinding =
            DialogNewShopListBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(createListBinding.root)
        val dialog = builder.create()

        createListBinding.btnCreate.setOnClickListener {
            viewModel.newEvent(ShopListEvent.CreateShopList(createListBinding.edName.text.toString()))
            dialog.dismiss()
        }

        createListBinding.edName.addTextChangedListener {
            createListBinding.btnCreate.isEnabled = !it.isNullOrEmpty()
        }

        dialog.window?.decorView?.setBackgroundResource(R.drawable.dialog_bg)
        dialog.show()
    }

}
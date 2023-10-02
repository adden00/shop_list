package com.adden00.testtaskunisafe.features.shop_list_items_screen.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adden00.testtaskunisafe.app.di.ui.DaggerShopListItemsComponent
import com.adden00.testtaskunisafe.app.getAppComponent
import com.adden00.testtaskunisafe.core.Constants
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.core.customGetParcelable
import com.adden00.testtaskunisafe.databinding.FragmentShopListItemsBinding
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.ShopListsViewModel
import com.adden00.testtaskunisafe.features.shop_lists_screen.presentation.models.ShopListModel
import javax.inject.Inject

class ShopListItemsFragment: Fragment() {


    private var _binding: FragmentShopListItemsBinding? = null
    private val binding get() = _binding!!

    private val currentShopList: ShopListModel by lazy {
        arguments?.customGetParcelable<ShopListModel>(Constants.NAVIGATION_ITEM_KEY) as ShopListModel
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ShopListsViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        ShopListItemsAdapter { item, isChecked ->

            //TODO кликнули по галочке
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerShopListItemsComponent.factory().create(requireContext().getAppComponent()).inject(this)
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
    }

    private fun subscribeOnState() {

    }

    private fun setUi() {
        binding.toolbar.title = currentShopList.name
    }
}
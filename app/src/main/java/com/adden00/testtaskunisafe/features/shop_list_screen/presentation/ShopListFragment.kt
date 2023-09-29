package com.adden00.testtaskunisafe.features.shop_list_screen.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adden00.testtaskunisafe.app.di.ui.DaggerShopListsComponent
import com.adden00.testtaskunisafe.app.getAppComponent
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.databinding.FragmentShopListBinding
import javax.inject.Inject

class ShopListFragment: Fragment() {

    private var _binding: FragmentShopListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ShopListViewModel by viewModels { viewModelFactory }
    private val adapter by lazy {
        ShopListAdapter {
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

    }
}
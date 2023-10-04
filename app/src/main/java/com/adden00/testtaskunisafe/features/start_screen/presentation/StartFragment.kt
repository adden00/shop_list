package com.adden00.testtaskunisafe.features.start_screen.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adden00.testtaskunisafe.R
import com.adden00.testtaskunisafe.app.di.ui.DaggerStartComponent
import com.adden00.testtaskunisafe.app.getAppComponent
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.databinding.FragmentStartBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class StartFragment: Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StartViewModel by viewModels { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerStartComponent.factory().create(requireContext().getAppComponent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
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
        binding.btnTryAgain.setOnClickListener {
            viewModel.tryNavigate()
        }
    }

    private fun subscribeOnState() {
        viewModel.startScreenEffects
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(state: StartScreenState) {
        binding.blockInternetError.visibility = if (state.internetIsConnected) View.GONE else View.VISIBLE
        if (state.needToAuth) {
            findNavController().navigate(R.id.action_startFragment_to_authFragment)
        }
        if (state.tokenReceived) {
            findNavController().navigate(R.id.action_startFragment_to_shopListFragment)
        }
    }
}
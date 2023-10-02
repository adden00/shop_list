package com.adden00.testtaskunisafe.features.start_screen.presentation

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
import androidx.navigation.fragment.findNavController
import com.adden00.testtaskunisafe.R
import com.adden00.testtaskunisafe.app.di.ui.DaggerAuthComponent
import com.adden00.testtaskunisafe.app.getAppComponent
import com.adden00.testtaskunisafe.core.ViewModelFactory
import com.adden00.testtaskunisafe.databinding.DialogLoginBinding
import com.adden00.testtaskunisafe.databinding.FragmentStartBinding
import com.adden00.testtaskunisafe.features.start_screen.presentation.mvi.AuthScreenEffect
import com.adden00.testtaskunisafe.features.start_screen.presentation.mvi.AuthScreenState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: StartViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAuthComponent.factory().create(requireContext().getAppComponent()).inject(this)
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

    private fun subscribeOnState() {
        viewModel.authScreenState
            .flowWithLifecycle(lifecycle)
            .onEach(::render)
            .launchIn(lifecycleScope)

        viewModel.authEffect
            .flowWithLifecycle(lifecycle)
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun render(state: AuthScreenState) {
        binding.pbar.visibility = if(state.isLoading) View.VISIBLE else View.GONE

    }


        private fun handleEffect(effect: AuthScreenEffect) {
            when(effect) {
                AuthScreenEffect.Waiting -> Unit
                AuthScreenEffect.InternetError -> showSnackBar(getString(R.string.internet_error))
                AuthScreenEffect.NavigateToShopLists -> navigateToShopLists()
                AuthScreenEffect.WrongTokenError -> showSnackBar(getString(R.string.wring_shopping_list_id))
            }
        }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToShopLists() {
        findNavController().navigate(R.id.action_startFragment_to_shopListFragment)
    }

    private fun setUi() {
        binding.bntImport.setOnClickListener{
            openLoginDialog()
        }
        binding.btnCreateNew.setOnClickListener {
            viewModel.createNewAccount()
        }
    }

    private fun openLoginDialog() {
        val dialogBinding = DialogLoginBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()

        dialogBinding.edId.addTextChangedListener {
            dialogBinding.btnLogin.isEnabled = !it.isNullOrEmpty()
        }
        dialogBinding.btnLogin.setOnClickListener {
            viewModel.logInAccount(dialogBinding.edId.text.toString())
            dialog.dismiss()
        }
        dialog.window?.decorView?.setBackgroundResource(R.drawable.dialog_bg)
        dialog.show()
    }

}
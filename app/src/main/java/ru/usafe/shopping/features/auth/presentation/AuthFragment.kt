package ru.usafe.shopping.features.auth.presentation

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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.usafe.shopping.R
import ru.usafe.shopping.app.getAppComponent
import ru.usafe.shopping.core.ViewModelFactory
import ru.usafe.shopping.databinding.DialogInputBinding
import ru.usafe.shopping.databinding.DialogRegisterBinding
import ru.usafe.shopping.databinding.FragmentAuthBinding
import ru.usafe.shopping.features.auth.presentation.mvi.AuthScreenEffect
import ru.usafe.shopping.features.auth.presentation.mvi.AuthScreenState
import java.util.regex.Pattern
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AuthViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ru.usafe.shopping.app.di.ui.DaggerAuthComponent.factory()
            .create(requireContext().getAppComponent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
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
        binding.pbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    private fun handleEffect(effect: AuthScreenEffect) {
        when (effect) {
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
        findNavController().navigate(R.id.action_authFragment_to_shopListFragment)
    }

    private fun setUi() {
        binding.bntImport.setOnClickListener {
            openLoginDialog()
        }
        binding.btnCreateNew.setOnClickListener {
            openRegisterDialog()
        }
    }

    private fun openLoginDialog() {
        val dialogBinding = DialogInputBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()

        with(dialogBinding) {
            edInput.hint = getString(R.string.id)
            tvTitle.text = getString(R.string.import_account_by_id)
            btnConfirm.text = getString(R.string.log_in)

            edInput.addTextChangedListener {
                btnConfirm.isEnabled = !it.isNullOrEmpty()
            }
            btnConfirm.setOnClickListener {
                viewModel.logInAccount(edInput.text.toString())
                dialog.dismiss()
            }
        }
        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }

    private fun openRegisterDialog() {
        val dialogBinding = DialogRegisterBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog =
            AlertDialog.Builder(requireContext()).apply { setView(dialogBinding.root) }.create()

        with(dialogBinding) {
            edName.addTextChangedListener {
                btnCreate.isEnabled = !it.isNullOrEmpty()
            }
//            edPhone.setOnFocusChangeListener { _, isFocised ->
//                if (isFocised && edPhone.text.isNullOrEmpty())
//                    edPhone.setText("+7")
//            }

            btnCreate.setOnClickListener {
                if (
//                    edPhone.text?.length == Constants.PHONE_LENGTH &&
                    checkEmail(edMail.text.toString())) {
                    viewModel.registerNewAccount(
                        edName.text.toString(),
                        edMail.text.toString(),
//                        edPhone.text.toString()
                    )
                    dialog.dismiss()
                } else
                    Snackbar.make(
                        dialogBinding.root,
                        getString(R.string.write_correct_email), Snackbar.LENGTH_SHORT
                    )
                        .show()
//                else if (edPhone.text?.length != Constants.PHONE_LENGTH)
//                    Snackbar.make(dialogBinding.root, "Write correct phone", Snackbar.LENGTH_SHORT)
//                        .show()
            }
        }
        dialog.window?.decorView?.setBackgroundResource(R.drawable.bg_dialog)
        dialog.show()
    }


    private fun checkEmail(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
        )
        return pattern.matcher(email).matches()
    }
}
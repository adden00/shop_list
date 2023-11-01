package ru.usafe.shopping.features.cards.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
import ru.usafe.shopping.R
import ru.usafe.shopping.app.getAppComponent
import ru.usafe.shopping.core.ViewModelFactory
import ru.usafe.shopping.features.cards.presentation.mvi.CardsEvent
import javax.inject.Inject

class QrScannerFragment : Fragment(), ResultHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: CardsViewModel by activityViewModels { viewModelFactory }


    private var _mScannerView: ZXingScannerView? = null
    private val mScannerView: ZXingScannerView get() = _mScannerView!!

    val cardName: String by lazy {
        arguments?.getString(CARD_NAME_KEY) ?: getString(R.string.unknown)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ru.usafe.shopping.app.di.ui.DaggerQrScannerComponent.factory()
            .create(requireContext().getAppComponent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Here, thisActivity is the current activity
        _mScannerView =
            ZXingScannerView(requireActivity())   // Programmatically initialize the scanner view
        Log.d("scanner", "qr scanner view created")
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                100
            );
        }

        return mScannerView
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this as ResultHandler) // Register ourselves as a handler for scan results.
        mScannerView.startCamera()
        Log.d("scanner", "camera started")

    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(qr: Result?) {
        qr?.let {

            when (it.barcodeFormat) {

                BarcodeFormat.CODE_128 -> {
                    viewModel.newEvent(
                        CardsEvent.AddCard(
                            cardName,
                            "",
                            "",
                            it.text,
                            "#9F9F9F",
                        )
                    )
                }

                else -> {
                    viewModel.newEvent(
                        CardsEvent.AddCard(
                            cardName,
                            "",
                            it.text,
                            "",
                            "#9F9F9F",
                        )
                    )

                    Log.d("scanner", "wrong type: ${it.barcodeFormat}")
                }
            }
        }

        findNavController().navigateUp()
    }

    companion object {
        const val CARD_NAME_KEY = "card_name"
    }
}
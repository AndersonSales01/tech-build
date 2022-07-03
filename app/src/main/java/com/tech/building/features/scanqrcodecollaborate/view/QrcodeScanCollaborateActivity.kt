package com.tech.building.features.scanqrcodecollaborate.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.features.scanqrcodecollaborate.viewmodel.QrcodeScanCollaborateUiAction
import com.tech.building.features.scanqrcodecollaborate.viewmodel.QrcodeScanCollaborateViewModel
import com.tech.building.features.utils.provider.CameraUtil
import kotlinx.android.synthetic.main.activity_qrcode_scan_collaborate.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

private const val CAMERA_PERMISSION_REQUEST_CODE = 1
private const val TAG = "QrcodeScan"
private const val RESULT_CODE_COLLABORATOR_SCANNED = 2001
private const val COLLABORATOR_SCANNED_KEY = "collaboratorScanned"

class QrcodeScanCollaborateActivity : AppCompatActivity(R.layout.activity_qrcode_scan_collaborate) {

    private val viewModel: QrcodeScanCollaborateViewModel by viewModel()
    private var isLocked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionObserver()
        setStateObserver()
        viewModel.checkPermission(this)
    }

    private fun setActionObserver() {
        viewModel.actionLiveData.observe(this) { action ->
            when (action) {
                is QrcodeScanCollaborateUiAction.StartCamera -> bindCameraUseCases()
                is QrcodeScanCollaborateUiAction.RequestPermission -> requestPermission()
                is QrcodeScanCollaborateUiAction.ScanSuccess -> collaboratorScannedResult(action.collaborator)
            }
        }
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(this) { state ->
            when {
                state.hasError -> {
                    alertDialogError()
                }
            }
        }
    }

    private fun requestPermission() {
        // opening up dialog to ask for camera permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.startCamera()
        } else {
            Toast.makeText(
                this,
                "Camera permission required",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun bindCameraUseCases() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({

            val scanner = CameraUtil.newInstanceBarcodeScanner()

            // setting up the analysis use case
            val analysisUseCase = ImageAnalysis.Builder()
                .build()

            // define the actual functionality of our analysis use case
            analysisUseCase.setAnalyzer(
                Executors.newSingleThreadExecutor()
            ) { imageProxy ->
                processBarcodeScanner(scanner, imageProxy)
            }

            CameraUtil.bindCameraUseCases(
                cameraView = cameraView,
                analysisUseCase = analysisUseCase,
                lifecycleOwner = this,
                context = this
            )
        }, ContextCompat.getMainExecutor(this))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processBarcodeScanner(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {

        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)
                    barcode?.rawValue?.let { value ->
                        if (!isLocked) {
                            isLocked = true
                            viewModel.qrCodeScannerValue(value)
                        }

                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, it.message.orEmpty())
                }.addOnCompleteListener {
                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }

    private fun alertDialogError() {
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setMessage(resources.getString(R.string.qrcode_screen_scan_error_message))
            .setNegativeButton("não") { dialog, which ->
                onBackPressed()
            }
            .setPositiveButton("Sim") { dialog, which ->
                isLocked = false
            }
            .show()
    }

    private fun collaboratorScannedResult(
        collaboratorModel: CollaboratorModel
    ) {
        setResult(RESULT_CODE_COLLABORATOR_SCANNED, Intent().apply {
            putExtra(COLLABORATOR_SCANNED_KEY, collaboratorModel)
        })
        finish()
    }
}
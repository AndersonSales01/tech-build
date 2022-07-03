package com.tech.building.features.utils.provider

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning

private const val TAG = "QrcodeScan"

object CameraUtil {
    fun newInstanceBarcodeScanner(): BarcodeScanner {
        val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
        ).build()
        return BarcodeScanning.getClient(options)
    }

    private fun preview(
        cameraView: PreviewView
    ) = Preview.Builder()
        .build()
        .also {
            it.setSurfaceProvider(cameraView.surfaceProvider)
        }

    fun bindCameraUseCases(
        cameraView: PreviewView,
        analysisUseCase: ImageAnalysis,
        lifecycleOwner: LifecycleOwner,
        context: Context,
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // setting up the preview use case
            val previewUseCase = preview(cameraView)

            // configure to use the back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    previewUseCase,
                    analysisUseCase
                )
            } catch (illegalStateException: IllegalStateException) {
                // If the use case has already been bound to another lifecycle or method is not called on main thread.
                Log.e(TAG, illegalStateException.message.orEmpty())
            } catch (illegalArgumentException: IllegalArgumentException) {
                // If the provided camera selector is unable to resolve a camera to be used for the given use cases.
                Log.e(TAG, illegalArgumentException.message.orEmpty())
            }
        }, ContextCompat.getMainExecutor(context))
    }
}
package com.example.bankapp.ui.theme.components

import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bankapp.R
import com.example.bankapp.ui.theme.register.RegisterViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.concurrent.Executor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraItem(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavHostController
) {
    val permissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val context = LocalContext.current
    val cameraController = remember { LifecycleCameraController(context) }
    val lifeCycle = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        permissionState.launchPermissionRequest()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val executor = ContextCompat.getMainExecutor(context)
                takePicture(
                    cameraController = cameraController,
                    executor = executor,
                    viewModel = viewModel,
                    navController = navController
                )
            }) {
                Text(text = stringResource(id = R.string.camera))
            }
        }) {
        if (permissionState.status.isGranted) {
            CameraComposable(
                modifier = Modifier.padding(it),
                lifeCycle = lifeCycle,
                cameraController = cameraController
            )
        }
    }
}

private fun takePicture(
    cameraController: LifecycleCameraController,
    executor: Executor,
    viewModel: RegisterViewModel,
    navController: NavHostController
) {
    val file = File.createTempFile("pictureID", ".jpg")
    val outPutDirectory = ImageCapture.OutputFileOptions.Builder(file).build()
    cameraController.takePicture(
        outPutDirectory,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                viewModel.uploadPictureId(outputFileResults.savedUri?.path.toString())
                viewModel.isTakePhoto.value = true
                navController.navigateUp()
            }

            override fun onError(exception: ImageCaptureException) {
                println("Error save picture")
            }
        })
}

@Composable
fun CameraComposable(
    cameraController: LifecycleCameraController,
    lifeCycle: LifecycleOwner,
    modifier: Modifier
) {

    cameraController.bindToLifecycle(lifeCycle)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            previewView.controller = cameraController

            previewView
        })
}
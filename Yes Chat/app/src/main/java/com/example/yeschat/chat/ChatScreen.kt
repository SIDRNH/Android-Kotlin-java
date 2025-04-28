package com.example.yeschat.chat

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatScreen(navController: NavController, channelId: String, state: ChatScreenState, onEvent: (ChatScreenEvent) -> Unit) {

    fun createImageUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date());
        val storageDir = navController.context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES);
        val photoFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir[0]);
        return FileProvider.getUriForFile(
            navController.context,
            "${navController.context.packageName}.provider",
            photoFile
        )
    }
    val cameraImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSuccess ->
            if (isSuccess) {
//                val uri = cameraImageUri.value;
//                cameraImageUri.value?.let {
//                    //Send Image to Server
//                }
            }
        }
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            if (isGranted) {
                cameraImageLauncher.launch(createImageUri());
            }
        }
    )

    LaunchedEffect(state.requestCameraPermission) {
        if (state.requestCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    LaunchedEffect(state.launchCamera) {
        if (state.launchCamera) {
            val uri = createImageUri();
            cameraImageLauncher.launch(input = uri);
            onEvent(ChatScreenEvent.ImageCaptured(uri = uri));
        }
    }
    LaunchedEffect(true) {
        onEvent(ChatScreenEvent.ListenMessages(chanelId = channelId));
    }
    Scaffold(
        containerColor = Color.Black
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ChatMessage(channelId = channelId, state = state, onEvent = onEvent);
        }
    }
    if (state.isDialogOpen) {
        ContentSelectionDialog(
            onCameraSelected = {
        Log.d("Boolean", "${navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED}")
                if (navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    onEvent(ChatScreenEvent.CameraPermission(true))
                }else {
                    onEvent(ChatScreenEvent.CameraPermission(false))
                }
            },
            onGallerySelected = {}
        );
    }
}
package com.mnowo.photofilter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log.d
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.photofilter.feature_edit.EditEvent
import com.mnowo.photofilter.feature_edit.EditViewModel

@Composable
fun EditScreen(viewModel: EditViewModel = hiltViewModel()) {


    var imageUrl by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri
    }

    imageUrl?.let {
        val source = ImageDecoder.createSource(context.contentResolver, it)
        viewModel.setPictureBitmap(
            ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.RGBA_F16, true)
        )
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(text = "Browse picture from gallery")
            }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            viewModel.pictureBitmap.value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .requiredWidth(300.dp)
                        .requiredHeight(400.dp)
                )
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        viewModel.onEvent(EditEvent.RedFilter(it))
                    }) {
                        Text(text = "Red filter")
                    }
                    Button(onClick = { }) {
                        Text(text = "Green filter")
                    }

                    Button(onClick = { }) {
                        Text(text = "Blue filter")
                    }
                }
            }
        }
    }
}

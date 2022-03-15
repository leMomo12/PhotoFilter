package com.mnowo.photofilter.feature_edit

import android.graphics.Bitmap
import android.util.Log.d
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.blue
import androidx.core.graphics.get
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(

) : ViewModel() {

    private val _pictureBitmap = mutableStateOf<Bitmap?>(null)
    val pictureBitmap: State<Bitmap?> = _pictureBitmap

    fun setPictureBitmap(bitmap: Bitmap) {
        _pictureBitmap.value = bitmap
    }

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.RedFilter -> {
                viewModelScope.launch {
                    pictureBitmap.value?.let {
                        val r = it.get(200, 200).red
                        val g = it.get(200, 200).green
                        val b = it.get(200, 200).blue
                        d("Bitmap", "x=200, y=200: r: $r , g: $g , b: $b ")

                        val result = editBitmapRed(it)
                        _pictureBitmap.value = result
                    }
                }
            }
            is EditEvent.GreenFilter -> {

            }
            is EditEvent.BlueFilter -> {

            }
        }
    }

    private suspend fun editBitmapRed(bitmap: Bitmap): Bitmap {
        _pictureBitmap.value = null
        val width = bitmap.width
        val height = bitmap.height

        val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGBA_F16)

        val job = viewModelScope.launch(Dispatchers.IO) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val r = bitmap.getPixel(x, y).red
                    val g = bitmap.getPixel(x, y).green
                    val b = bitmap.getPixel(x, y).blue

                    d("Bitmap", "New r: $r , g: $g, b: $b")

                    val grey = (r + g + b) / 3

                    newBitmap.setPixel(x, y, grey)
                    newBitmap.getPixel(x,y).red
                    d("NewBitmap", "New ")
                }
            }
            d("Bitmap", "Finished")
        }
        job.join()
        val r = newBitmap.get(200, 200).red
        val g = newBitmap.get(200, 200).green
        val b = newBitmap.get(200, 200).blue
        d("Bitmap", "x=200, y=200: r: $r , g: $g , b: $b ")
        return newBitmap
    }
}
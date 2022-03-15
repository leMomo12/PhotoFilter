package com.mnowo.photofilter.feature_edit

import android.graphics.Bitmap

sealed class EditEvent {
    data class RedFilter(var bitmap: Bitmap) : EditEvent()
    data class GreenFilter(var bitmap: Bitmap) : EditEvent()
    data class BlueFilter(var bitmap: Bitmap) : EditEvent()
}

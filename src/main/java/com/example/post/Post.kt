package com.example.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val text: String,
    val imageUrl: String,
    var isLiked: Boolean = false
) : Parcelable
package com.example.phototracker.data

import android.app.Application

class PhotoApplication: Application() {
    val database: PhotoRoomDatabase by lazy { PhotoRoomDatabase.getDatabase(this) }
}
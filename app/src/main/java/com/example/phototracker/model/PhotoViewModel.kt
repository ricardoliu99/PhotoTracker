package com.example.phototracker.model

import androidx.lifecycle.*
import com.example.phototracker.data.Photo
import com.example.phototracker.data.PhotoDao
import kotlinx.coroutines.launch
import java.util.*

class PhotoViewModel(private val photoDao: PhotoDao) : ViewModel() {
    val allPhotos: LiveData<List<Photo>> = photoDao.getAll().asLiveData()
    var uriToAdd: String? = null
    var photoTime: String? = null

    fun addNewPhoto(
        title: String,
        description: String,
        lat: Double,
        lon: Double,
        locName: String
    ) {
        val newPhoto = Photo(
            title = title,
            date = photoTime,
            latitude = lat,
            longitude = lon,
            locationName = locName,
            description = description,
            uri = uriToAdd,
        )
        viewModelScope.launch {
            photoDao.insert(newPhoto)
        }
    }

    fun isInputBlank(title: String, description: String): Boolean {
        return title.isBlank() || description.isBlank()
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch { photoDao.delete(photo) }
    }

    fun setImageDefault() {
        uriToAdd = null
    }

    fun isUriToAddNull(): Boolean {
        return uriToAdd == null
    }

    fun retrievePhoto(id: Int): LiveData<Photo> {
        return photoDao.getPhotoById(id).asLiveData()
    }

    fun updatePhoto(photo: Photo, title: String, description: String) {
        val newPhoto = photo.copy(title=title, description=description)
        viewModelScope.launch {
            photoDao.update(newPhoto)
        }
    }

}

class PhotoViewModelFactory(private val photoDao: PhotoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoViewModel(photoDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}
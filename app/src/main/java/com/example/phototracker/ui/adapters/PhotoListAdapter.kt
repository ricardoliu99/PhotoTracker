package com.example.phototracker.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.phototracker.R
import com.example.phototracker.data.Photo
import com.example.phototracker.databinding.ListPhotoBinding


class PhotoListAdapter(
    private val deletePhoto: (Photo) -> Unit,
    private val moveToLocation: (Photo) -> Unit,
    private val updatePhoto: (Photo) -> Unit,
    private val showFullImage: (Photo) -> Unit
) :
    ListAdapter<Photo, PhotoListAdapter.PhotoViewHolder>(DiffCallBack) {

    class PhotoViewHolder(private var binding: ListPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            photo: Photo, context: Context,
            deletePhoto: (Photo) -> Unit,
            moveToLocation: (Photo) -> Unit,
            updatePhoto: (Photo) -> Unit
        ) {
            binding.photo = photo
            binding.cardTitle.text = photo.title
            binding.cardSecondaryText.text =
                context.getString(
                    R.string.secondary_text,
                    photo.locationName,
                    photo.date
                )
            binding.cardNotes.text = photo.description
            binding.cardViewInMap.setOnClickListener {
                moveToLocation(photo)
            }
            binding.cardEdit.setOnClickListener {
                updatePhoto(photo)
            }
            binding.cardDelete.setOnClickListener {
                deletePhoto(photo)
            }
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ListPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnLongClickListener {
            showFullImage(current)
            true
        }
        holder.bind(current, holder.itemView.context, deletePhoto, moveToLocation, updatePhoto)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.description == newItem.description
            }

        }
    }
}
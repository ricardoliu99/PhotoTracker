package com.example.phototracker.ui

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.phototracker.data.Photo
import com.example.phototracker.data.PhotoApplication
import com.example.phototracker.databinding.FragmentPhotoListBinding
import com.example.phototracker.model.PhotoViewModel
import com.example.phototracker.model.PhotoViewModelFactory
import com.example.phototracker.ui.adapters.PhotoListAdapter

class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentResolver: ContentResolver
    private val viewModel: PhotoViewModel by activityViewModels {
        PhotoViewModelFactory(
            (activity?.application as PhotoApplication).database.photoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentResolver = requireActivity().contentResolver
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PhotoListAdapter(
            { deletePhoto(it) },
            { moveToLocation(it) },
            { updatePhoto(it) },
            { showFullImage(it) }
        )
        binding.recyclerView.adapter = adapter
        viewModel.allPhotos.observe(this.viewLifecycleOwner) {
            it.let {
                adapter.submitList(it)
            }
        }

    }

    private fun showFullImage(photo: Photo) {
        photo.uri?.let {
            val action =
                PhotoListFragmentDirections.actionPhotoListFragmentToFullImageFragment(
                    photo.uri
                )
            findNavController().navigate(action)
        }
    }

    private fun updatePhoto(photo: Photo) {
        val action =
            PhotoListFragmentDirections.actionPhotoListFragmentToEditPhotoFragment(
                photo.id
            )
        findNavController().navigate(action)
    }

    private fun deletePhoto(photo: Photo) {
        try {
            contentResolver.delete(
                Uri.parse(photo.uri),
                null,
                null
            )
        } catch (e: Exception) {
            // case when user deletes photo outside app
        }
        viewModel.deletePhoto(photo)
    }

    private fun moveToLocation(photo: Photo) {
        val action =
            PhotoListFragmentDirections.actionPhotoListFragmentToMapFragment(
                photo.latitude.toFloat(), photo.longitude.toFloat(), photo.locationName
            )
        findNavController().navigate(action)
    }

}
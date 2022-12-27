package com.example.phototracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.phototracker.data.Photo
import com.example.phototracker.data.PhotoApplication
import com.example.phototracker.databinding.FragmentEditPhotoBinding
import com.example.phototracker.model.PhotoViewModel
import com.example.phototracker.model.PhotoViewModelFactory

class EditPhotoFragment : DialogFragment() {
    private var _binding: FragmentEditPhotoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoViewModel by activityViewModels {
        PhotoViewModelFactory(
            (activity?.application as PhotoApplication).database.photoDao()
        )
    }
    private val navArgs: EditPhotoFragmentArgs by navArgs()
    private lateinit var currentPhoto: Photo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.retrievePhoto(navArgs.id)
            .observe(this.viewLifecycleOwner) { photo ->
                currentPhoto = photo
                bind(currentPhoto)
            }
        binding.cancelButton.setOnClickListener {
            val action =
                EditPhotoFragmentDirections.actionEditPhotoFragmentToPhotoListFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(photo: Photo) {
        binding.apply {
            provideTitle.setText(photo.title, TextView.BufferType.SPANNABLE)
            provideDescription.setText(photo.description, TextView.BufferType.SPANNABLE)
            binding.saveButton.setOnClickListener {
                updatePhoto(photo)
                val action =
                    EditPhotoFragmentDirections.actionEditPhotoFragmentToPhotoListFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun updatePhoto(photo: Photo) {
        viewModel.updatePhoto(
            photo,
            binding.provideTitle.text.toString(),
            binding.provideDescription.text.toString()
        )
    }
}
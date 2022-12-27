package com.example.phototracker.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.phototracker.R
import com.example.phototracker.data.PhotoApplication
import com.example.phototracker.databinding.FragmentAddPhotoBinding
import com.example.phototracker.model.PhotoViewModel
import com.example.phototracker.model.PhotoViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AddPhotoFragment : Fragment() {
    private var _binding: FragmentAddPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private var currentLocationName: String? = null

    private val viewModel: PhotoViewModel by activityViewModels {
        PhotoViewModelFactory(
            (activity?.application as PhotoApplication).database.photoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        setLatLon()
    }

    override fun onResume() {
        super.onResume()
        setLatLon()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageToAdd()
        binding.provideImageButton.setOnClickListener {
            val action =
                AddPhotoFragmentDirections.actionAddPhotoFragmentToCameraFragment()
            findNavController().navigate(action)
        }

        binding.saveButton.setOnClickListener {
            addNewPhoto()
        }
    }

    private fun setImageToAdd() {
        if (viewModel.isUriToAddNull()) {
            val res = R.drawable.ic_baseline_image_24
            val uri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + resources.getResourcePackageName(res) +
                        "/" + resources.getResourceTypeName(res) +
                        "/" + resources.getResourceEntryName(res)
            )
            binding.currentImgUri = uri.toString()
        } else {
            binding.currentImgUri = viewModel.uriToAdd.toString()
        }
    }

    // Permission already checked in MainActivity
    @SuppressLint("MissingPermission")
    private fun setLatLon() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                this@AddPhotoFragment.currentLocation = location
            }
    }


    private fun setLocationName() {
        val geocoder = Geocoder(requireContext())
        try {
            currentLocationName = geocoder.getFromLocation(
                currentLocation!!.latitude,
                currentLocation!!.longitude,
                1
            )[0].locality
        } catch (e: Exception) {
            showWarning("Search for location name failed.")
        }
    }

    private fun isInputBlank(): Boolean {
        return viewModel.isInputBlank(
            binding.provideTitle.text.toString(),
            binding.provideDescription.text.toString()
        )
    }

    private fun showWarning(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun addNewPhoto() {
        setLatLon()
        if (currentLocation == null) {
            showWarning("Please enable location settings. " +
                    "If recently enabled, wait a moment before saving.")
        } else if (viewModel.isUriToAddNull()) {
            showWarning("Please take a photo.")
        } else if (isInputBlank()) {
            showWarning("Please provide title and description.")
        } else {
            setLocationName()
            viewModel.addNewPhoto(
                binding.provideTitle.text.toString(),
                binding.provideDescription.text.toString(),
                currentLocation!!.latitude,
                currentLocation!!.longitude,
                currentLocationName!!
            )
            viewModel.setImageDefault()
            val action =
                AddPhotoFragmentDirections.actionAddPhotoFragmentToPhotoListFragment()
            findNavController().navigate(action)
        }

    }
}
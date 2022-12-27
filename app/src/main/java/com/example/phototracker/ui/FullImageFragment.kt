package com.example.phototracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.phototracker.databinding.FragmentFullImageBinding

class FullImageFragment: Fragment() {
    private var _binding: FragmentFullImageBinding? = null
    private val binding get() = _binding!!
    private val navArgs: FullImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageToShow = navArgs.uri
    }
}
package com.baharudin.imovie.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.baharudin.imovie.R
import com.baharudin.imovie.databinding.FragmentSeeAllBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllFragment : Fragment(R.layout.fragment_see_all) {

    private var _binding : FragmentSeeAllBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSeeAllBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}
package com.example.dictionaryapp.precentation.screen.menu

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreeenManuBinding
import com.example.dictionaryapp.precentation.screen.seacher.SearchScreen

class MenuScreen : Fragment(R.layout.screeen_manu) {
    private val binding by viewBinding(ScreeenManuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.engUzbButton.setOnClickListener {
//            parentFragmentManager.beginTransaction().replace(R.id.container, SearchScreen().apply {
//                arguments = bundleOf(Pair("English", true))
//            }).addToBackStack("menu").commit()
            findNavController().navigate(MenuScreenDirections.actionMenuScreenToSearchScreen(true))
        }

        binding.uzbEngButton.setOnClickListener {
//            parentFragmentManager.beginTransaction().replace(R.id.container, SearchScreen().apply {
//                arguments = bundleOf(Pair("English", false))
//            }).addToBackStack("menu2").commit()
            findNavController().navigate(MenuScreenDirections.actionMenuScreenToSearchScreen(false))

        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                    .setMessage("Do you want to exit the Dictionary App ?")
                    .setPositiveButton("Yes") { _, _ ->
                        requireActivity().finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
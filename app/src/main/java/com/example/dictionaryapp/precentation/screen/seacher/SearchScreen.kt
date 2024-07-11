package com.example.dictionaryapp.precentation.screen.seacher

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.SystemClock.*
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.databinding.ScreenSeacherBinding
import com.example.dictionaryapp.precentation.adapter.DictionaryAdapter
import com.example.dictionaryapp.precentation.dialog.BottomSheetDialog
import com.example.dictionaryapp.precentation.myLog
import java.util.Locale

@Suppress("DEPRECATION")
class SearchScreen : Fragment(R.layout.screen_seacher), SearchContract.View {
    private val binding by viewBinding(ScreenSeacherBinding::bind)
    private val presenter by lazy { SearchPresenter(this) }
    private lateinit var adapter: DictionaryAdapter
    private var currentQuery: String? = null
    private var bool: Boolean = true
    private val navArgs :SearchScreenArgs by navArgs()
    private var mTTS: TextToSpeech? = null
    private val REQ_CODE_SPEECH_INPUT = 100
    private var presentTime: Log? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bool = navArgs.bool
//        englishUzbExchange()
        clickExchange()
        engUzb()
        loadAdapter()
        presenter.loadDictionary()
        binding.microphone.setOnClickListener {
            promptSpeechInput()
        }

        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        if (mTTS == null) {
            mTTS = TextToSpeech(requireContext()) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = mTTS?.setLanguage(Locale.ENGLISH)
                    if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        Log.e("TTS", "Language not supported")
                    } else {
                    }
                } else {
                    Log.e("TTS", "Initialization failed")
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText
                if (currentQuery == null) presenter.loadDictionary()
                else if (bool) presenter.loadByDictionaryEng(query = currentQuery!!)
                else presenter.loadByDictionary(query = currentQuery!!)
                return true
            }
        })
        val closeButton = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView

        closeButton.setOnClickListener {
            binding.searchView.setQuery(null, false)
            binding.searchView.clearFocus()
        }
        openLikeScreen()
        popBackStack()
    }

    override fun onResume() {
        loadAdapter()
        super.onResume()
    }
    private fun popBackStack(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun loadAdapter() {
        adapter = DictionaryAdapter()
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())

        adapter.onClickLike {
            presenter.replaceDictionary(
                DictionaryData(
                    it.id,
                    it.english,
                    it.type,
                    it.transcript,
                    it.uzbek,
                    it.countable,
                    if (it.is_favourite == 1) 0 else 1
                )
            )
//            adapter.notifyDataSetChanged()

            if (currentQuery != null) {
                if (bool) {
                    presenter.loadByDictionaryEng(currentQuery!!)
                } else {
                    presenter.loadByDictionary(currentQuery!!)
                }
            } else {
                presenter.loadDictionary()
            }


        }
        adapter.onClickSound {
            speak(it)
        }

        adapter.onClickItem {
            presenter.getByDictionary(it)
        }
    }

    override fun showDictionary(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor, query = currentQuery)
            binding.empty.isVisible = (cursor.count == 0)
            adapter.setBool(bool)
//            adapter.notifyDataSetChanged()
        }
    }


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun showBottomSheetDialog(data: DictionaryData) {
        "Show bottom sheet".myLog()
        val dialog = BottomSheetDialog(data)
        dialog.apply {
            arguments = bundleOf(Pair("bool", bool))
        }
        dialog.show(
            requireActivity().supportFragmentManager, dialog.tag
        )
        dialog.onClickSound {
            speak(it)
        }
    }


    private fun speak(text: String) {
        mTTS?.setSpeechRate(0.45f)
        mTTS?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }


//    private fun englishUzbExchange() {
//        bool = requireArguments().getBoolean("English", false)
//    }

    private fun clickExchange() {
        binding.screenExchange.setOnClickListener {
            bool = !bool
            engUzb()
            presenter.loadDictionary()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun engUzb() {
        if (bool) binding.engUzbText.text = "English-Uzbek Dictionary"
        else binding.engUzbText.text = "Uzbek-English Dictionary"
    }

    private fun openLikeScreen() {
        binding.like.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.container, LikeScreen())
//                .addToBackStack("search")
//                .commit()

            findNavController().navigate(SearchScreenDirections.actionSearchScreenToLikeScreen())
        }
    }

    fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        if (!bool) intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-UZ")
        else intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_string))

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireActivity(), "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                currentQuery = message?.get(0)

                // Set the recognized speech text to the SearchView
                binding.searchView.setQuery(currentQuery, true)

                // Perform search or any other action as needed
                if (bool) {
                    presenter.loadByDictionaryEng(currentQuery!!)
                } else {
                    presenter.loadByDictionary(currentQuery!!)
                }
            }
        }
    }

}

private fun String.myLog() {
    Log.d("TTT", this)
}

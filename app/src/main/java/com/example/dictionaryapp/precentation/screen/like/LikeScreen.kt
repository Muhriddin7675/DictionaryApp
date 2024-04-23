package com.example.dictionaryapp.precentation.screen.like

import android.database.Cursor
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.databinding.ScreeenLikeBinding
import com.example.dictionaryapp.precentation.adapter.LikeAdapter
import com.example.dictionaryapp.precentation.dialog.BottomSheetDialog
import com.example.dictionaryapp.precentation.screen.info.InfoScreen
import java.util.Locale

class LikeScreen : Fragment(R.layout.screeen_like), LikeContract.View {
    private val binding by viewBinding(ScreeenLikeBinding::bind)
    private val presenter by lazy { LikePresenter(this) }
    private val adapter by lazy { LikeAdapter() }
    private var mTTS: TextToSpeech? = null
    var backInfo: (() -> Unit?)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadAdapter()
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
        presenter.loadLikeList()
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
            backInfo?.invoke()
        }
    }

    override fun showLikeList(ls: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(ls)
            binding.empty.isVisible = (ls.count == 0)
        }
    }

    private fun loadAdapter() {
        binding.rvLike.adapter = adapter
        binding.rvLike.layoutManager = LinearLayoutManager(requireContext())

        adapter.onClickLike {
            presenter.replaceLike(it)
        }
        adapter.onClickSound {
            speak(it)
        }
        adapter.onClickItem {
            val dialog = BottomSheetDialog(it)
            dialog.apply {
                arguments = bundleOf(Pair("bool", true))
            }
            dialog.show(
                requireActivity().supportFragmentManager, dialog.tag
            )
            dialog.onClickSound {
                speak(it)
            }
        }
    }

    private fun speak(text: String) {
        mTTS?.setSpeechRate(0.45f)
        mTTS?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

}
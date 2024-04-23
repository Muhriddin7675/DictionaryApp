package com.example.dictionaryapp.precentation.screen.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.databinding.ScreenInfoBinding
import java.util.Locale

class InfoScreen : Fragment(R.layout.screen_info), InfoContract.View {

    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val presenter by lazy { InfoPresenter(this) }
    private var id: Long = -1
    private lateinit var dicData: DictionaryData
    private var bool: Boolean = false
    private var tts: TextToSpeech? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        tts = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
                 tts?.setLanguage(Locale.ENGLISH)
        })
        id = requireArguments().getLong("ID", -1)

        val k = requireArguments().getLong("idLike", -1)
        if (k > 0) {
            id = k
        }

        presenter.getByDictionary(id)
        bool = requireArguments().getBoolean("bool", true)

        binding.like.setOnClickListener {
            presenter.replaceLike(
                DictionaryData(
                    dicData.id,
                    dicData.english,
                    dicData.type,
                    dicData.transcript,
                    dicData.uzbek,
                    dicData.countable,
                    if (dicData.is_favourite == 1) 0
                    else 1
                )
            )
        }
        binding.soundInfo.setOnClickListener {
            speckOut(dicData.english)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun showDictionary(data: DictionaryData) {
        this.dicData = data
        view?.post {
            if (bool!!) {
                binding.textEng.text = dicData.english
                binding.like.setImageResource(
                    if (dicData.is_favourite == 1) {
                        R.drawable.ic_like_on
                    } else R.drawable.ic_like_off
                )
                binding.textUzb.text = dicData.uzbek
                binding.textCountable.text = dicData.countable
                binding.textTranscript.isVisible = true
                binding.textTranscript.text = dicData.transcript
                binding.type.text = dicData.type
            } else {
                binding.soundInfo.isVisible = false
                binding.textEng.text = dicData.uzbek
                binding.textTranscript.text = dicData.english
                binding.type.text = dicData.type
            }
        }
    }
    fun speckOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,null)
    }
}
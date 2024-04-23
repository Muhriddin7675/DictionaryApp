package com.example.dictionaryapp.precentation.dialog

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.databinding.ScreenInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class BottomSheetDialog(data: DictionaryData) : BottomSheetDialogFragment() {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private var bool: Boolean? = null
    private val myData = data
    private var soundClick: ((String) -> Unit)? = null
//    private var likeClick: ((DictionaryData) -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bool = requireArguments().getBoolean("Bool", true)

        view.post {
            if (bool!!) {
                binding.textEng.text = myData.english
                binding.like.setImageResource(
                    if (myData.is_favourite == 1) {
                        R.drawable.ic_like_on
                    } else R.drawable.ic_like_off
                )
                binding.textUzb.text = myData.uzbek
                binding.textCountable.text = myData.countable
                binding.textTranscript.isVisible = true
                binding.textTranscript.text = myData.transcript
                binding.type.text = myData.type
            } else {
                binding.soundInfo.isVisible = false
                binding.textEng.text = myData.uzbek
                binding.textTranscript.text = myData.english
                binding.type.text = myData.type
            }
        }
        binding.soundInfo.setOnClickListener {
          soundClick?.invoke(myData.english)
        }
//        binding.like.setOnClickListener {
//            likeClick?.invoke(
//                    DictionaryData(
//                        myData.id,
//                        myData.english,
//                        myData.type,
//                        myData.transcript,
//                        myData.uzbek,
//                        myData.countable,
//                        if (myData.is_favourite == 1) 0
//                        else 1
//                    )
//                )
//        }
    }

    fun onClickSound(st: (String) -> Unit) {
        soundClick = st
    }

//    fun onClickLike(dt:(DictionaryData)->Unit){
//        likeClick = dt
//    }

}
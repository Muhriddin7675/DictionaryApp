package com.example.dictionaryapp.precentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.databinding.ItemSearchBinding
import com.example.dictionaryapp.domain.AppRepositoryImp.Companion.init
import java.util.Locale

class LikeAdapter : RecyclerView.Adapter<LikeAdapter.LikeViewHolder>() {
    private var cursor: Cursor? = null
    private var clickLike: ((DictionaryData) -> Unit)? = null
    private lateinit var tts: TextToSpeech
    private var itemClick: ((DictionaryData) -> Unit)? = null
    private lateinit var soundClick:((String)->Unit)


    inner class LikeViewHolder(private val binding: ItemSearchBinding) : ViewHolder(binding.root) {
        private val context: Context = binding.root.context

        private fun speakOut(text: String) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: DictionaryData) {
            binding.textLanguage1.text = item.english
            binding.textLanguage2.text = item.uzbek

            if (item.is_favourite == 1) binding.itemLike.setImageResource(R.drawable.ic_like_on)
            else binding.itemLike.setImageResource(R.drawable.ic_like_off)

            binding.itemLike.setOnClickListener {

                clickLike?.invoke(
                    DictionaryData(
                        item.id,
                        item.english,
                        item.type,
                        item.transcript,
                        item.uzbek,
                        item.countable,
                        0
                    )
                )
                notifyDataSetChanged()


            }

            binding.itemSound.setOnClickListener {
                soundClick.invoke(item.english)
            }
            binding.root.setOnClickListener {
                itemClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder =
        LikeViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = cursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        cursor?.let {
            it.moveToPosition(position)
            val dictionaryData = DictionaryData(
                id = it.getLong(it.getColumnIndex("id")),
                english = it.getString(it.getColumnIndex("english")),
                type = it.getString(it.getColumnIndex("type")),
                transcript = it.getString(it.getColumnIndex("transcript")),
                uzbek = it.getString(it.getColumnIndex("uzbek")),
                countable = it.getString(it.getColumnIndex("countable")),
                is_favourite = it.getInt(it.getColumnIndex("is_favourite")),
            )
            holder.bind(dictionaryData)
        }

    }

    fun onClickLike(like: (DictionaryData) -> Unit) {
        clickLike = like
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor) {
        this.cursor?.close()
        this.cursor = cursor
        notifyDataSetChanged()
    }
    fun onClickItem(item : ((DictionaryData)->Unit)){
        itemClick =  item
    }
    fun onClickSound(tx :(String)->Unit){
        soundClick = tx
    }
}
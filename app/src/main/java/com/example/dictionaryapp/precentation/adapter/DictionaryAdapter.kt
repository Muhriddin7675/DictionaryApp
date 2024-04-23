package com.example.dictionaryapp.precentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.databinding.ItemSearchBinding
import com.example.dictionaryapp.precentation.createSpannable
import com.example.dictionaryapp.precentation.myLog
import java.util.Locale


class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.SearchViewHolder>() {

    private var cursor: Cursor? = null
    private var query: String? = null
    private var bool = true
    private var time = System.currentTimeMillis()
    private var onClickLike: ((DictionaryData) -> Unit)? = null
    private var setOnClickItem: ((Long) -> Unit)? = null
    private lateinit var soundClick: ((String) -> Unit)


    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context: Context = binding.root.context
        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: DictionaryData) {
            if (bool) {
                if (query == null) binding.textLanguage1.text = item.english
                else binding.textLanguage1.text = item.english.createSpannable(query!!)
                binding.textLanguage2.text = item.uzbek
                binding.itemSound.isVisible = true
            } else {
                if (query == null) binding.textLanguage1.text = item.uzbek
                else binding.textLanguage1.text = item.uzbek.createSpannable(query!!)
                binding.textLanguage2.text = item.english
                binding.itemSound.isVisible = false
            }


            binding.itemLike.setOnClickListener {
                if (System.currentTimeMillis() - time > 800) {
                    onClickLike?.invoke(item)
                    notifyDataSetChanged()
                }
                time = System.currentTimeMillis()
            }

            if (item.is_favourite == 1) binding.itemLike.setImageResource(R.drawable.ic_like_on)
            else binding.itemLike.setImageResource(R.drawable.ic_like_off)

            binding.itemSound.setOnClickListener {
                soundClick.invoke(item.english)
            }
            binding.root.setOnClickListener {
                setOnClickItem?.invoke(item.id)
                "click item adapter".myLog()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = cursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: DictionaryAdapter.SearchViewHolder, position: Int) {
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

    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

    fun setBool(boolean: Boolean) {
        bool = boolean
    }

    fun onClickLike(like: (DictionaryData) -> Unit) {
        onClickLike = like
    }

    fun onClickItem(item: (Long) -> Unit) {
        setOnClickItem = item
    }

    fun onClickSound(tx: (String) -> Unit) {
        soundClick = tx
    }
}




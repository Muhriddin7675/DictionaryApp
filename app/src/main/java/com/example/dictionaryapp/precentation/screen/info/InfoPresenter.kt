package com.example.dictionaryapp.precentation.screen.info

import android.annotation.SuppressLint
import android.util.Log
import com.example.dictionaryapp.data.model.DictionaryData
import java.util.concurrent.Executors

class InfoPresenter(private val view:InfoContract.View):InfoContract.Presenter {
    private val model:InfoContract.Model = InfoModel()
    private var executors = Executors.newSingleThreadExecutor()

    override fun replaceLike(data: DictionaryData) {
        executors.execute{
            model.replaceLike(data)
            getByDictionary(data.id)
        }
    }

    @SuppressLint("Range")
    override fun getByDictionary(id: Long) {
        executors.execute{
            val cursor =  model.getByDictionary(id)
            cursor.let {
                it.moveToPosition(0)
                val dictionaryData = DictionaryData(
                    id = it.getLong(it.getColumnIndex("id")),
                    english = it.getString(it.getColumnIndex("english")),
                    type = it.getString(it.getColumnIndex("type")),
                    transcript = it.getString(it.getColumnIndex("transcript")),
                    uzbek = it.getString(it.getColumnIndex("uzbek")),
                    countable = it.getString(it.getColumnIndex("countable")),
                    is_favourite = it.getInt(it.getColumnIndex("is_favourite")),
                )
                view.showDictionary(dictionaryData)
            }

        }
    }
}
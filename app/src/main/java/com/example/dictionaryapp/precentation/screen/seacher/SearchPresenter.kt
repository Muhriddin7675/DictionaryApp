package com.example.dictionaryapp.precentation.screen.seacher

import android.annotation.SuppressLint
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.precentation.myLog
import java.util.concurrent.Executors

class SearchPresenter(private val view: SearchContract.View) : SearchContract.Presenter {

    private val model: SearchContract.Model = SearchModel()
    private var executors = Executors.newSingleThreadExecutor()

    override fun loadDictionary() {
        executors.execute {
            val cursor = model.getAllDictionary()
            view.showDictionary(cursor)
        }
    }

    override fun loadByDictionary(query: String) {
        executors.execute {
            val cursor = model.getByDictionary(query)
            view.showDictionary(cursor)
        }
    }

    override fun loadByDictionaryEng(query: String) {
        executors.execute {
            "loadByDictionaryEng() ".myLog()
            val cursor = model.getByDictionaryEng(query)
            view.showDictionary(cursor)
        }
    }

    override fun replaceDictionary(dictionaryData: DictionaryData) {
        executors.execute {
            model.replaceDictionary(dictionaryData)
        }
    }

    @SuppressLint("Range")
    override fun getByDictionary(id: Long) {
    "getByDictionary fun ".myLog()
        executors.execute {
            val cursor = model.getByDictionary(id)
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

                view.showBottomSheetDialog(dictionaryData)
            }

        }

    }


}
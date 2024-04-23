package com.example.dictionaryapp.precentation.screen.seacher

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData

interface SearchContract {
    interface Model {
        fun getAllDictionary(): Cursor
        fun getByDictionary(query: String): Cursor
        fun replaceDictionary(data: DictionaryData)
        fun getByDictionary(id:Long):Cursor
        fun getByDictionaryEng(query: String): Cursor
    }

    interface View {
        fun showDictionary(cursor: Cursor)
        fun showBottomSheetDialog(data: DictionaryData)
    }

    interface Presenter {
        fun loadDictionary()
        fun loadByDictionary(query: String)
        fun loadByDictionaryEng(query: String)
        fun replaceDictionary(dictionaryData: DictionaryData)
        fun getByDictionary(id:Long)
    }
}
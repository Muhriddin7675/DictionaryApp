package com.example.dictionaryapp.precentation.screen.info

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData

interface InfoContract {
    interface Model{
        fun replaceLike(data: DictionaryData)
        fun getByDictionary(id:Long): Cursor

    }
    interface View{
        fun showDictionary(data: DictionaryData)
    }
    interface Presenter{
        fun replaceLike(data: DictionaryData)
        fun getByDictionary(id:Long)


    }
}
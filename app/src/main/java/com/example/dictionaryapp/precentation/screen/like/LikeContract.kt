package com.example.dictionaryapp.precentation.screen.like

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData

interface LikeContract {

    interface Model{
        fun getLikeList():Cursor
        fun replaceLike(data: DictionaryData)
    }
    interface View{
        fun showLikeList(ls:Cursor)

    }
    interface Presenter{
        fun loadLikeList()
        fun replaceLike(data: DictionaryData)
    }
}
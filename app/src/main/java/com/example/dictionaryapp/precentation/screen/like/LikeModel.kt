package com.example.dictionaryapp.precentation.screen.like

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.domain.AppRepository
import com.example.dictionaryapp.domain.AppRepositoryImp

class LikeModel:LikeContract.Model {

    private val data:AppRepository = AppRepositoryImp.getInstance()
    override fun getLikeList():Cursor = data.getLikeList()
    override fun replaceLike(data: DictionaryData) = this.data.replaceDictionary(data)
}
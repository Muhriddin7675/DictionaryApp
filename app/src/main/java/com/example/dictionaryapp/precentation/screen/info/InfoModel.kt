package com.example.dictionaryapp.precentation.screen.info

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.domain.AppRepository
import com.example.dictionaryapp.domain.AppRepositoryImp

class InfoModel:InfoContract.Model {
    private val db:AppRepository = AppRepositoryImp.getInstance()

    override fun replaceLike(data: DictionaryData) = db.replaceDictionary(data)
    override fun getByDictionary(id: Long): Cursor = db.getByDictionary(id)
}
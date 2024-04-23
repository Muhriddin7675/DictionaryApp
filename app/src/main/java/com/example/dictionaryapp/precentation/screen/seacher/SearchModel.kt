package com.example.dictionaryapp.precentation.screen.seacher

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.domain.AppRepository
import com.example.dictionaryapp.domain.AppRepositoryImp

class SearchModel: SearchContract.Model {
    private val data : AppRepository = AppRepositoryImp.getInstance()

    override fun getAllDictionary(): Cursor = data.getAllDictionary()
    override fun getByDictionary(query: String): Cursor = data.getByDictionary(query)
    override fun replaceDictionary(data: DictionaryData) = this.data.replaceDictionary(data)
    override fun getByDictionaryEng(query: String): Cursor = data.getByDictionaryEng(query)
    override fun getByDictionary(id: Long): Cursor = data.getByDictionary(id)

}
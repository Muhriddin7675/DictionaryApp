package com.example.dictionaryapp.domain

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData
import com.example.dictionaryapp.data.sourse.DictionaryDatabase

class AppRepositoryImp : AppRepository {

    companion object {
        private lateinit var instance: AppRepository

        fun init() {
            if (!(::instance.isInitialized)) instance = AppRepositoryImp()
        }

        fun getInstance(): AppRepository = instance

    }

    private val dictionaryDao = DictionaryDatabase.getInstance().getDictionaryDao()

    override fun getAllDictionary(): Cursor = dictionaryDao.getAllDictionary()
    override fun getByDictionary(attach: String): Cursor = dictionaryDao.getByDictionary(attach)
    override fun getByDictionary(id: Long): Cursor = dictionaryDao.getByDictionary(id)
    override fun replaceDictionary(data: DictionaryData) = dictionaryDao.replaceDictionary(data)
    override fun getLikeList(): Cursor = dictionaryDao.getLikeList()
    override fun getEnglish(uzbek: String): Cursor = dictionaryDao.getEnglish(uzbek)
    override fun getUzbek(english: String): Cursor = dictionaryDao.getUzbek(english)
    override fun getByDictionaryEng(query: String): Cursor =  dictionaryDao.getByDictionaryEng(query)
}
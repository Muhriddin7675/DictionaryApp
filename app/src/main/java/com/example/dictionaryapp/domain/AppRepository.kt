package com.example.dictionaryapp.domain

import android.database.Cursor
import com.example.dictionaryapp.data.model.DictionaryData

interface AppRepository {
    fun getAllDictionary(): Cursor
    fun getByDictionary(attach:String): Cursor
    fun replaceDictionary(data: DictionaryData)
    fun getLikeList():Cursor
    fun getByDictionary(id:Long):Cursor
    fun getEnglish(uzbek:String):Cursor
    fun getUzbek(english:String):Cursor
    fun getByDictionaryEng(query:String):Cursor
}
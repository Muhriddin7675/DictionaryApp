package com.example.dictionaryapp.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.data.model.DictionaryData

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary")
    fun getAllDictionary(): Cursor

    @Query("SELECT * FROM dictionary WHERE dictionary.uzbek LIKE :query ||'%'")
    fun getByDictionary(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE dictionary.english LIKE :query ||'%'")
    fun getByDictionaryEng(query: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceDictionary(data: DictionaryData)

    @Query("SELECT * FROM dictionary WHERE is_favourite == 1")
    fun getLikeList(): Cursor

    @Query("SELECT * FROM dictionary WHERE id =:id LIMIT 1")
    fun getByDictionary(id: Long): Cursor

    @Query("SELECT * FROM dictionary WHERE english =:english LIMIT 1")
    fun getUzbek(english: String): Cursor

    @Query("SELECT * FROM dictionary WHERE english =:uzbek LIMIT 1")
    fun getEnglish(uzbek: String): Cursor


}
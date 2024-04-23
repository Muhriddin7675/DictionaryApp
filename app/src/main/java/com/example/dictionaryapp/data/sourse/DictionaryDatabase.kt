package com.example.dictionaryapp.data.sourse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapp.data.dao.DictionaryDao
import com.example.dictionaryapp.data.model.DictionaryData

@Database(entities = [DictionaryData::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun getDictionaryDao(): DictionaryDao

    companion object {
        private lateinit var instance: DictionaryDatabase

        fun init(context: Context) {
            if (!(Companion::instance.isInitialized)) instance =
                Room.databaseBuilder(context, DictionaryDatabase::class.java, "MyDictionary.db")
                    .createFromAsset("dictionary.db")
                    .build()
        }
        fun getInstance(): DictionaryDatabase = instance
    }
}
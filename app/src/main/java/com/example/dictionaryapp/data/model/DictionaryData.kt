package com.example.dictionaryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity("dictionary")
class DictionaryData(
    @PrimaryKey(autoGenerate = true) var id :Long,
    var english:String,
    var type:String,
    var transcript:String,
    var uzbek:String,
    var countable:String,
    var is_favourite:Int,
    )
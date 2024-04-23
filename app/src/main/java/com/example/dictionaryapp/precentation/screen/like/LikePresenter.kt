package com.example.dictionaryapp.precentation.screen.like

import com.example.dictionaryapp.data.model.DictionaryData
import java.util.concurrent.Executors

class LikePresenter(private val view: LikeContract.View) : LikeContract.Presenter {
    private val model: LikeContract.Model = LikeModel()
    private var executors = Executors.newSingleThreadExecutor()
    override fun loadLikeList() {
        executors.execute {
            val cursor = model.getLikeList()
            view.showLikeList(cursor)
        }
    }

    override fun replaceLike(data: DictionaryData) {
       executors.execute{
              model.replaceLike(data)
              loadLikeList()
       }
    }
}
package com.example.desafiofirebase.gameList.view

import android.util.Log
import com.example.desafiofirebase.gameEdit.GameEditFragment
import com.example.desafiofirebase.gameList.model.GameModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class GamesRepository (private val _database: DatabaseReference) {

    fun getGames(callback: (games: MutableList<GameModel>) -> Unit) {
        val games = _database.orderByKey()
        val singleListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mutableList = mutableListOf<GameModel>()

                for (postSnapshot in snapshot.children) {
                    mutableList.add(postSnapshot.getValue<GameModel>()!!)
                }
                callback.invoke(mutableList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(GameEditFragment.toString(), "ERROR" + error.message)
                callback.invoke(mutableListOf())
            }

        }

        games.addListenerForSingleValueEvent(singleListener)
    }

    fun saveGame(game: GameModel) {
        val newGameRef = _database.push()
        newGameRef.setValue(game)
    }
}
package com.example.desafiofirebase.gameList.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.gameList.model.GameModel
import com.squareup.picasso.Picasso

class GameListViewHolder (_view: View): RecyclerView.ViewHolder(_view) {
    private val title = _view.findViewById<TextView>(R.id.textViewGameName)
    private val gameYear = _view.findViewById<TextView>(R.id.textViewGameDate)
    private val image = _view.findViewById<ImageView>(R.id.imageViewGame)

    fun bind(gameModel: GameModel) {
        gameYear.text = gameModel.createdAt.toString()
        title.text = gameModel.name
       Picasso.get().load(gameModel.imgUrl).into(image)
    }
}


package com.example.desafiofirebase.gameList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.gameList.model.GameModel
import com.example.desafiofirebase.R
import com.example.desafiofirebase.gameList.viewholder.GameListViewHolder

class GameListAdapter(private val _dataset: List<GameModel>, private val listener: (GameModel) ->Unit) :
    RecyclerView.Adapter<GameListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_game_item_list, parent, false)
        return GameListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val item = _dataset[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = _dataset.size

}
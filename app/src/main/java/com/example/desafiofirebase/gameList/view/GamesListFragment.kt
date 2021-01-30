package com.example.desafiofirebase.gameList.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofirebase.R
import com.example.desafiofirebase.gameList.adapter.GameListAdapter
import com.example.desafiofirebase.gameList.model.GameModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GamesListFragment : Fragment(){
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _database: DatabaseReference
    private lateinit var _viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        val navController = findNavController()
        _auth = FirebaseAuth.getInstance()
        val user = _auth.currentUser ?: return
        _database = FirebaseDatabase.getInstance().getReference().child("games").child("users").child(user.uid).child("games")

        _viewModel = ViewModelProvider(
            this,
            GameViewModel.GameViewModelFactory(GamesRepository(_database))
        ).get(GameViewModel::class.java)

        _viewModel.games.observe(viewLifecycleOwner, Observer {
            createList(it, navController)
        })

        _viewModel.getGames()

        onAddGame(navController)
    }

    private fun createList(games: List<GameModel>, navController: NavController) {
        val viewManager = GridLayoutManager(_view.context, 2)
        val recyclerView = _view.findViewById<RecyclerView>(R.id.recyclerViewGames)
        val viewAdapter = GameListAdapter(games) {
            val bundle = bundleOf(
                IMG_URL to it.imgUrl,
                NAME to it.name,
                DESCRIPTION to it.description,
                CREATED_AT to it.createdAt,
                ID to it.id,
            )
            navController.navigate(R.id.action_gamesListFragment_to_gameDetailsFragment, bundle)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


    private fun onAddGame(navController: NavController) {
        _view.findViewById<FloatingActionButton>(R.id.buttonGameRegister).setOnClickListener {
            navController.navigate(R.id.action_gamesListFragment_to_gameEditFragment)
        }
    }

    companion object {
        const val IMG_URL = "IMG_URL"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
        const val CREATED_AT = "CREATED_AT"
        const val ID = "ID"
    }
}
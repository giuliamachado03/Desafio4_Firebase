package com.example.desafiofirebase.gameDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.desafiofirebase.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GameDetailsFragment : Fragment() {

    private lateinit var _view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        _view.findViewById<FloatingActionButton>(R.id.buttonGameEdit).setOnClickListener {
            _view.findNavController().navigate(R.id.action_gameDetailsFragment_to_gameEditFragment)
        }
    }

    companion object {

                }
            }

package com.example.desafiofirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class LoginFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        _view.findViewById<Button>(R.id.buttonCreateAccountLogin).setOnClickListener {
            _view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        _view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            val emailContainer = _view.findViewById<EditText>(R.id.editTextLoginEmail)
            val passwordContainer = _view.findViewById<EditText>(R.id.editTextLoginPassword)

            verificaEFazLogin(emailContainer, passwordContainer)
        }
    }


    private fun verificaEFazLogin(
        emailContainer: EditText?,
        passwordContainer: EditText?
    ) {

        val emailText = emailContainer!!.text.toString()
        val passText = passwordContainer!!.text.toString()

        if (!emailText.isBlank()) {
            if (!passText.isBlank()) {
                realizarLogin(emailText, passText)
            } else {
                passwordContainer.error = "O campo de senha está vazio"
            }
        } else {
            emailContainer.error = "O campo de email está vazio"
        }
    }

    private fun realizarLogin(emailText: String, passText: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(emailText, passText)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _view.findNavController().navigate(R.id.action_loginFragment_to_gamesListFragment)

                } else {
                    Toast.makeText(_view.context, "Credenciais incorretas", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


package com.example.desafiofirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class LoginFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth

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

        _view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            val email = _view.findViewById<EditText>(R.id.editTextLoginEmail)
            val password = _view.findViewById<EditText>(R.id.editTextLoginPassword)

            realizarLogin(email, password)
        }
    }

        private fun realizarLogin(

            email: EditText,
            password: EditText
        ) {

            val emailText:String = email.text.toString()
            val passText = password.text.toString()

                if (!emailText.isBlank()) {
                    if (!passText.isBlank()) {
                        realizarLogin(email, password)
                    } else {
                        password.error = "O campo senha não pode estar vazio"
                    }
                } else {
                    email.error = "O campo email não pode estar vazio"
                }
            }


         fun fazerLogin(email: String, password: String, name : String) {

            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build()

                        user!!.updateProfile(profileUpdates)
                    }else{
                        Toast.makeText(_view.context, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

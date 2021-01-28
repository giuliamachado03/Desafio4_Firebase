package com.example.desafiofirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class RegisterFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        _view.findViewById<MaterialButton>(R.id.buttonCreateAccount).setOnClickListener {
            val name =   _view.findViewById<EditText>(R.id.editTextRegisterName)
            val email =   _view.findViewById<EditText>(R.id.editTextRegisterEmail)
            val password =   _view.findViewById<EditText>(R.id.editTextRegisterPassword)
            val repeatPassword =   _view.findViewById<EditText>(R.id.editTextRegisterRepeatPassword)

            verificaECriarUsuario(name, email, password, repeatPassword)

        }
    }

    private fun verificaECriarUsuario(
        name: EditText,
        email: EditText,
        password: EditText,
        repeatPassword: EditText
    ) {

        val nameText = name.text.toString()
        val emailText: String = email.text.toString()
        val passText = password.text.toString()
        val repeatText = repeatPassword.text.toString()

        if (!nameText.isBlank()) {
            if (!emailText.isBlank()) {
                if (!passText.isBlank() && passText.length >= 6) {
                    if (repeatText == passText) {
                        criarUsuario(nameText, emailText, passText)
                    } else {
                        repeatPassword.error = "Senhas diferentes "
                    }
                } else {
                    password.error = "A senha não pode ter menos que 6 caracteres"
                }
            } else {
                email.error = "O campo email não pode estar vazio"
            }
        } else {
            name.error = "O campo nome não pode estar vazio"
        }
    }

    private fun criarUsuario(name: String, email: String, pass: String) {
        val mAuth = FirebaseAuth.getInstance()

        mAuth.createUserWithEmailAndPassword(email, pass)
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
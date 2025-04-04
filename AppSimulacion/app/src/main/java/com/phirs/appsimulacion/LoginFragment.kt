package com.phirs.appsimulacion

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {

    private lateinit var etUser: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Inicializar vistas
        etUser = view.findViewById(R.id.et_user)
        etPass = view.findViewById(R.id.et_pass)
        btnLogin = view.findViewById(R.id.btn_login)

        // Configurar el botón de login
        btnLogin.setOnClickListener {
            val username = etUser.text.toString()
            val password = etPass.text.toString()

            // Validar campos vacíos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener credenciales guardadas
            val savedUsername = sharedPreferences.getString("username", "")
            val savedPassword = sharedPreferences.getString("password", "")

            // Validar credenciales
            if (username == savedUsername && password == savedPassword) {
                // Credenciales correctas, ir a MainActivity
                val intent = Intent(requireActivity(), Menu::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
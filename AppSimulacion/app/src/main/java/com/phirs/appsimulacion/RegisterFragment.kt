package com.phirs.appsimulacion

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterFragment : Fragment() {

    private lateinit var etUserRegister: EditText
    private lateinit var etPassRegister: EditText
    private lateinit var btnRegister: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Inicializar vistas
        etUserRegister = view.findViewById(R.id.et_userRegister)
        etPassRegister = view.findViewById(R.id.et_passRegister)
        btnRegister = view.findViewById(R.id.btn_register)

        // Configurar el botón de registro
        btnRegister.setOnClickListener {
            val username = etUserRegister.text.toString()
            val password = etPassRegister.text.toString()

            // Validar campos vacíos
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar credenciales
            with(sharedPreferences.edit()) {
                putString("username", username)
                putString("password", password)
                apply()
            }

            Toast.makeText(requireContext(), "Registro exitoso!", Toast.LENGTH_SHORT).show()

            // Limpiar campos después del registro
            etUserRegister.text.clear()
            etPassRegister.text.clear()
        }

        return view
    }
}
package com.phirs.appsimulacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class Login : AppCompatActivity() {

    lateinit var navegation: BottomNavigationView

    /* Funcion para cargar el loadFragment */
    private fun loadFragment(fragment: androidx.fragment.app.Fragment){
        supportFragmentManager.commit {
            replace(R.id.frameContainerLogin, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
    /* Fragments para el Login y el Register */
    private val mOnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.user_login -> {
                loadFragment(LoginFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.register_login -> {
                supportFragmentManager.commit {
                    loadFragment(RegisterFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
        }
        false
    }
    /* Cuerpo de la activity */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        navegation = findViewById(R.id.navMenuLogin)
        navegation.setOnNavigationItemSelectedListener(mOnNavMenu)

        /* Color original para los iconos de los botones del fragment */
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navMenuLogin)
        bottomNavigationView.itemIconTintList = null

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /* Auto inicio del primer fragment  */
        supportFragmentManager.commit {
            replace<LoginFragment>(R.id.frameContainerLogin)
            setReorderingAllowed(true)
            addToBackStack("replacement")
        }
    }
    /* Elimina la pila de fragments al cerrar el fragment */
    override fun onBackPressed() {
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE) // cierra la pila de fragmentos
        super.onBackPressed()
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // Cierra la actividad actual
    }
}
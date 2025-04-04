package com.phirs.appsimulacion

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu : AppCompatActivity() {

    lateinit var navigation: BottomNavigationView

    /* Funcion para cargar el loadFragment */
    private fun loadFragment(fragment: androidx.fragment.app.Fragment){
        supportFragmentManager.commit {
            replace(R.id.frameContainerMenu, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    /* Botones de los fragments del menu */
    private val mOnNavMenu = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemVenta -> {
                loadFragment(VentaMenuFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemCompra -> {
                supportFragmentManager.commit {
                    loadFragment(CompraMenuFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
        }
        false
    }

    /* cuerpo de la actividad */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        navigation = findViewById(R.id.navMenuMenu)
        navigation.setOnNavigationItemSelectedListener(mOnNavMenu)

        /* color original para los botones de los fragments */
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navMenuMenu)
        bottomNavigationView.itemIconTintList = null

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /* Auto inicia el primer fragment */
        supportFragmentManager.commit {
            replace<CompraMenuFragment>(R.id.frameContainerMenu)
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
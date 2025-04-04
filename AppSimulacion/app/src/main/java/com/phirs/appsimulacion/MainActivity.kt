package com.phirs.appsimulacion

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import com.google.android.material.animation.AnimationUtils
import android.view.animation.AnimationUtils
import android.os.Handler
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        /* Animacion para el logo de la aplicacion */
        val animation1 = AnimationUtils.loadAnimation(this, R.anim.logo)
        val logo = findViewById<ImageView>(R.id.logo)
        logo.startAnimation(animation1)

        /* Tiempo de espera para cambiar a la siguiente activity de forma automatica */
        Handler().postDelayed({
            val intent = Intent(
                this@MainActivity,
                Login::class.java
            )
            startActivity(intent)
            finish()
        }, 3000)

    }
}
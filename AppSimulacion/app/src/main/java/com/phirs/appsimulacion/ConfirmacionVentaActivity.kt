package com.phirs.appsimulacion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.phirs.appsimulacion.databinding.ActivityConfirmacionVentaBinding

class ConfirmacionVentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmacionVentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmacionVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir datos del Fragment anterior
        val marca = intent.getStringExtra("MARCA") ?: "No especificada"
        val modelo = intent.getStringExtra("MODELO") ?: "No especificado"
        val anio = intent.getStringExtra("ANIO") ?: "No especificado"
        val cotizacion = intent.getDoubleExtra("COTIZACION", 0.0)
        val estado = intent.getStringExtra("ESTADO") ?: "No especificado"
        val accesorios = intent.getStringExtra("ACCESORIOS") ?: "Ninguno"

        // Mostrar resumen
        binding.tvResumen.text = "Cotización: $${"%.2f".format(cotizacion)}"
        binding.tvDetalles.text = """
            📱 Marca: $marca
            🔍 Modelo: $modelo
            📅 Año: $anio
            ⚙️ Estado: ${estado.replaceFirstChar { it.uppercase() }}
            🎁 Accesorios: $accesorios
        """.trimIndent()

        // Botón de confirmación
        binding.btnConfirmarVenta.setOnClickListener {
            if (validarCampos()) {
                val nombre = binding.etNombre.text.toString()
                val telefono = binding.etTelefono.text.toString()
                val metodoEntrega = if (binding.rgMetodoEntrega.checkedRadioButtonId == R.id.rbRecogerTienda) "Recoger en tienda" else "Envío a domicilio"

                // Simular envío de datos a un servidor (aquí iría tu lógica real)
                enviarDatosVenta(
                    nombre,
                    telefono,
                    marca,
                    modelo,
                    cotizacion,
                    metodoEntrega
                )

                // Mostrar confirmación
                Toast.makeText(
                    this,
                    "✅ Venta confirmada. ¡Gracias por confiar en nosotros!",
                    Toast.LENGTH_LONG
                ).show()

                finish() // Cierra la Activity
            }
        }
    }

    private fun validarCampos(): Boolean {
        if (binding.etNombre.text.isNullOrEmpty()) {
            binding.etNombre.error = "Ingresa tu nombre"
            return false
        }
        if (binding.etTelefono.text.isNullOrEmpty()) {
            binding.etTelefono.error = "Ingresa tu teléfono"
            return false
        }
        if (binding.rgMetodoEntrega.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Selecciona un método de entrega", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun enviarDatosVenta(
        nombre: String,
        telefono: String,
        marca: String,
        modelo: String,
        precio: Double,
        metodoEntrega: String
    ) {
        // Aquí iría la lógica para enviar los datos a tu backend
        // Ejemplo con Firebase, Retrofit, o Room:
        // databaseReference.child("ventas").push().setValue(...)
    }
}
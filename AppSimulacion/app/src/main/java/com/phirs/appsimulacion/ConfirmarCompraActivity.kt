package com.phirs.appsimulacion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.phirs.appsimulacion.databinding.ActivityConfirmarCompraBinding

class ConfirmarCompraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmarCompraBinding
    private lateinit var adapter: ProductosCompraAdapter
    private val productos = mutableListOf<Celular>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmarCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir productos del carrito
        productos.addAll(intent.getSerializableExtra("CARRITO") as List<Celular>)
        val total = intent.getDoubleExtra("TOTAL", 0.0)

        // Configurar RecyclerView
        adapter = ProductosCompraAdapter(productos, false) // false para modo confirmación (sin botón)
        binding.rvProductosCompra.apply {
            layoutManager = LinearLayoutManager(this@ConfirmarCompraActivity)
            adapter = this@ConfirmarCompraActivity.adapter
            setHasFixedSize(true)
        }

        // Mostrar total
        binding.tvTotalCompra.text = "Total: $${"%.2f".format(total)}"

        // Configurar botón de confirmación
        binding.btnConfirmarCompra.setOnClickListener {
            if (validarCampos()) {
                confirmarCompra()
            }
        }
    }

    private fun validarCampos(): Boolean {
        if (binding.etNombreCliente.text.isNullOrEmpty()) {
            binding.etNombreCliente.error = "Ingresa tu nombre"
            return false
        }
        if (binding.etTelefonoCliente.text.isNullOrEmpty()) {
            binding.etTelefonoCliente.error = "Ingresa tu teléfono"
            return false
        }
        if (binding.rgMetodoPago.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Selecciona un método de pago", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun confirmarCompra() {
        val metodoPago = if (binding.rgMetodoPago.checkedRadioButtonId == R.id.rbTarjeta) {
            "Tarjeta"
        } else {
            "Efectivo"
        }

        val nombreCliente = binding.etNombreCliente.text.toString()
        val total = productos.sumOf { it.precio }

        Toast.makeText(
            this,
            "✅ Compra confirmada!\nGracias $nombreCliente\nTotal: $${"%.2f".format(total)}",
            Toast.LENGTH_LONG
        ).show()

        finish()
    }
}
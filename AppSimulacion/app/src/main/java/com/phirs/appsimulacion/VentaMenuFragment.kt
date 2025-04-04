package com.phirs.appsimulacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phirs.appsimulacion.databinding.FragmentVentaMenuBinding
import com.google.android.material.snackbar.Snackbar

class VentaMenuFragment : Fragment() {
    private var _binding: FragmentVentaMenuBinding? = null
    private val binding get() = _binding!!
    private var cotizacionActual: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentaMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCotizar.setOnClickListener {
            if (validarCampos()) {
                cotizarDispositivo()
            }
        }

        binding.btnConfirmar.setOnClickListener {
            confirmarVenta()
        }
    }

    private fun validarCampos(): Boolean {
        if (binding.etMarca.text.isNullOrEmpty()) {
            mostrarSnackbar("Ingresa la marca del dispositivo")
            return false
        }

        if (binding.etModelo.text.isNullOrEmpty()) {
            mostrarSnackbar("Ingresa el modelo del dispositivo")
            return false
        }

        if (binding.etAnio.text.isNullOrEmpty()) {
            mostrarSnackbar("Ingresa el año de fabricación")
            return false
        }

        if (binding.rgEstado.checkedRadioButtonId == -1) {
            mostrarSnackbar("Selecciona el estado del dispositivo")
            return false
        }

        return true
    }

    private fun cotizarDispositivo() {
        // Obtener datos del formulario
        val marca = binding.etMarca.text.toString()
        val modelo = binding.etModelo.text.toString()
        val anio = binding.etAnio.text.toString().toInt()
        val estado = when (binding.rgEstado.checkedRadioButtonId) {
            R.id.rbBueno -> "bueno"
            R.id.rbRegular -> "regular"
            else -> "malo"
        }
        val incluyeCargador = binding.cbCargador.isChecked
        val incluyeAudifonos = binding.cbAudifonos.isChecked
        val incluyeCaja = binding.cbCaja.isChecked

        // Simular cálculo de cotización
        cotizacionActual = calcularCotizacion(
            marca,
            modelo,
            anio,
            estado,
            incluyeCargador,
            incluyeAudifonos,
            incluyeCaja
        )

        // Mostrar resultado
        binding.tvCotizacion.text = "Cotización: $${"%.2f".format(cotizacionActual)}"
        binding.tvCotizacion.visibility = View.VISIBLE
        binding.btnConfirmar.visibility = View.VISIBLE

        mostrarSnackbar("Cotización generada con éxito")
    }

    private fun calcularCotizacion(
        marca: String,
        modelo: String,
        anio: Int,
        estado: String,
        cargador: Boolean,
        audifonos: Boolean,
        caja: Boolean
    ): Double {
        // Base según marca y modelo
        var base = when {
            marca.equals("Samsung", ignoreCase = true) -> {
                when {
                    modelo.contains("S23") -> 600.0
                    modelo.contains("S22") -> 500.0
                    modelo.contains("S21") -> 400.0
                    modelo.contains("Note 20") -> 350.0
                    modelo.contains("Z Flip") -> 550.0
                    modelo.contains("A") && modelo.contains("5") -> 200.0
                    else -> 150.0
                }
            }
            marca.equals("Apple", ignoreCase = true) -> {
                when {
                    modelo.contains("iPhone 15") -> 800.0
                    modelo.contains("iPhone 14") -> 700.0
                    modelo.contains("iPhone 13") -> 600.0
                    else -> 300.0
                }
            }
            else -> 100.0 // Otras marcas
        }

        // Ajuste por año
        val anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val antiguedad = anioActual - anio
        base *= (1 - (antiguedad * 0.10)).coerceAtLeast(0.3)

        // Ajuste por estado
        base *= when (estado) {
            "bueno" -> 0.9
            "regular" -> 0.7
            else -> 0.5
        }

        // Bonificación por accesorios
        if (cargador) base += 20.0
        if (audifonos) base += 30.0
        if (caja) base += 15.0

        return base.coerceAtLeast(50.0)
    }

    private fun confirmarVenta() {
        val intent = Intent(requireContext(), ConfirmacionVentaActivity::class.java).apply {
            putExtra("MARCA", binding.etMarca.text.toString())
            putExtra("MODELO", binding.etModelo.text.toString())
            putExtra("ANIO", binding.etAnio.text.toString())
            putExtra("COTIZACION", cotizacionActual)
            putExtra("ESTADO", when (binding.rgEstado.checkedRadioButtonId) {
                R.id.rbBueno -> "bueno"
                R.id.rbRegular -> "regular"
                else -> "malo"
            })
            putExtra("ACCESORIOS", buildString {
                if (binding.cbCargador.isChecked) append("Cargador, ")
                if (binding.cbAudifonos.isChecked) append("Audífonos, ")
                if (binding.cbCaja.isChecked) append("Caja")
                if (endsWith(", ")) delete(length - 2, length)
            }.ifEmpty { "Ninguno" })
        }
        startActivity(intent)
        resetearFormulario()
    }

    private fun resetearFormulario() {
        binding.etMarca.text?.clear()
        binding.etModelo.text?.clear()
        binding.etAnio.text?.clear()
        binding.rgEstado.clearCheck()
        binding.cbCargador.isChecked = false
        binding.cbAudifonos.isChecked = false
        binding.cbCaja.isChecked = false
        binding.tvCotizacion.visibility = View.GONE
        binding.btnConfirmar.visibility = View.GONE
    }

    private fun mostrarSnackbar(mensaje: String) {
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
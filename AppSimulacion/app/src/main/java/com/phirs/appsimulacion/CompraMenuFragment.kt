package com.phirs.appsimulacion

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.phirs.appsimulacion.databinding.FragmentCompraMenuBinding
import java.io.Serializable

class CompraMenuFragment : Fragment() {
    private var _binding: FragmentCompraMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var celularAdapter: SimulacionAdapter
    private val carrito = mutableListOf<Celular>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompraMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecyclerView()
        configurarBusquedaAutomatica()
        actualizarTotal()
    }

    private fun configurarRecyclerView() {
        celularAdapter = SimulacionAdapter(obtenerCelularesDisponibles()) { celular ->
            agregarAlCarrito(celular)
        }

        binding.rvItemsSimulacion.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = celularAdapter
            setHasFixedSize(true)
        }
    }

    private fun configurarBusquedaAutomatica() {
        // Configura el listener para el botón de simulación
        binding.btnSimular.setOnClickListener {
            if (carrito.isNotEmpty()) {
                finalizarCompra()
            } else {
                mostrarMensaje("🛒 Tu carrito está vacío")
            }
        }

        // Configura la búsqueda automática
        binding.etBuscarItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filtrarCelulares(s.toString())
            }
        })
    }

    private fun agregarAlCarrito(celular: Celular) {
        carrito.add(celular)
        actualizarTotal()
        mostrarMensaje("📦 ${celular.modelo} agregado al carrito")
    }

    private fun actualizarTotal() {
        val total = carrito.sumOf { it.precio }
        binding.tvTotal.text = "Total simulación: $${"%.2f".format(total)}"
        binding.btnSimular.isEnabled = carrito.isNotEmpty()
    }

    private fun finalizarCompra() {
        val intent = Intent(requireContext(), ConfirmarCompraActivity::class.java).apply {
            putExtra("CARRITO", ArrayList(carrito))
            putExtra("TOTAL", carrito.sumOf { it.precio })
        }
        startActivity(intent)
    }

    private fun filtrarCelulares(consulta: String) {
        val resultados = if (consulta.isBlank()) {
            obtenerCelularesDisponibles()
        } else {
            obtenerCelularesDisponibles().filter {
                it.modelo.contains(consulta, ignoreCase = true) ||
                        it.estado.toString().contains(consulta, ignoreCase = true)
            }
        }
        celularAdapter.actualizarDatos(resultados)
    }

    private fun mostrarMensaje(texto: String) {
        android.widget.Toast.makeText(requireContext(), texto, android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun obtenerCelularesDisponibles(): List<Celular> {
        return listOf(
            Celular(
                "S23U_256",
                "Samsung Galaxy S23 Ultra",
                EstadoCelular.REACONDICIONADO,
                "256GB", "12GB", "Snapdragon 8 Gen 2",
                "Incluye caja original y S-Pen", 899.99
            ),
            Celular(
                "S21FE_128",
                "Samsung Galaxy S21 FE",
                EstadoCelular.REPARADO,
                "128GB", "6GB", "Exynos 2100",
                "Pantalla nueva, batería al 95%", 449.99
            ),
            Celular(
                "N20U_256",
                "Samsung Galaxy Note 20 Ultra",
                EstadoCelular.USADO,
                "256GB", "8GB", "Snapdragon 865+",
                "Algunos rasguños, funciona perfecto", 499.99
            ),
            Celular(
                "ZFLIP4_128",
                "Samsung Galaxy Z Flip 4",
                EstadoCelular.REACONDICIONADO,
                "128GB", "8GB", "Snapdragon 8+ Gen 1",
                "Como nuevo, garantía 6 meses", 699.99
            ),
            Celular(
                "S22_256",
                "Samsung Galaxy S22+",
                EstadoCelular.REPARADO,
                "256GB", "8GB", "Exynos 2200",
                "Batería reemplazada", 599.99
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
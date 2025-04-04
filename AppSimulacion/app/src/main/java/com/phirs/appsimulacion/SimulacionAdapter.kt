package com.phirs.appsimulacion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phirs.appsimulacion.databinding.ItemCelularBinding
import java.io.Serializable

class SimulacionAdapter(
    // Lista de celulares a mostrar
    private var celulares: List<Celular>,
    // Función que se ejecuta al hacer clic en "Comprar"
    private val onItemClick: (Celular) -> Unit
) : RecyclerView.Adapter<SimulacionAdapter.CelularViewHolder>() {

    // ViewHolder que contiene las vistas de cada celular
    inner class CelularViewHolder(private val binding: ItemCelularBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Vincula los datos del celular a las vistas
        fun bind(celular: Celular) {
            with(binding) {
                // Modelo del celular (ej: Samsung Galaxy S23)
                tvModelo.text = celular.modelo

                // Estado (Reacondicionado/Reparado/Usado)
                tvEstado.text = when(celular.estado) {
                    EstadoCelular.REACONDICIONADO -> "✅ Reacondicionado (Garantía 6 meses)"
                    EstadoCelular.REPARADO -> "🔧 Reparado (Garantía 3 meses)"
                    EstadoCelular.USADO -> "📱 Usado (Buen estado)"
                }

                // Especificaciones técnicas
                tvEspecificaciones.text = "💾 ${celular.almacenamiento} | 🧠 ${celular.ram} | ⚙️ ${celular.procesador}"

                // Condición adicional
                tvCondicion.text = celular.condicion

                // Precio formateado
                tvPrecio.text = "💰 $${"%.2f".format(celular.precio)}"

                // Configura el botón de compra
                btnAccion.setOnClickListener {
                    onItemClick(celular)
                }
            }
        }
    }

    // Crea nuevas vistas (invocado por el layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelularViewHolder {
        // Infla el layout del item usando ViewBinding
        val binding = ItemCelularBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CelularViewHolder(binding)
    }

    // Reemplaza el contenido de una vista (invocado por el layout manager)
    override fun onBindViewHolder(holder: CelularViewHolder, position: Int) {
        holder.bind(celulares[position])
    }

    // Retorna el tamaño de tu lista
    override fun getItemCount() = celulares.size

    // Actualiza la lista de celulares
    fun actualizarDatos(nuevosCelulares: List<Celular>) {
        celulares = nuevosCelulares
        notifyDataSetChanged()
    }
}

// Enumeración para los estados posibles de un celular
enum class EstadoCelular {
    REACONDICIONADO, REPARADO, USADO
}

// Clase de datos que representa un celular Samsung
data class Celular(
    val id: String,              // Identificador único (ej: "S23U_256")
    val modelo: String,          // Modelo completo (ej: "Samsung Galaxy S23 Ultra")
    val estado: EstadoCelular,   // Estado del dispositivo
    val almacenamiento: String,  // Capacidad (ej: "256GB")
    val ram: String,             // Memoria RAM (ej: "12GB")
    val procesador: String,      // Procesador (ej: "Snapdragon 8 Gen 2")
    val condicion: String,       // Condición adicional (ej: "Incluye accesorios originales")
    val precio: Double           // Precio (ej: 899.99)
): Serializable  // Solo añade esto
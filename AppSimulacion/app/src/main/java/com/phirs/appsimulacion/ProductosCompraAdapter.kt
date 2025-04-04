package com.phirs.appsimulacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductosCompraAdapter(
    private val productos: List<Celular>,
    private val mostrarBoton: Boolean
) : RecyclerView.Adapter<ProductosCompraAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvModelo: TextView = view.findViewById(R.id.tvModelo)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
        val tvEspecificaciones: TextView = view.findViewById(R.id.tvEspecificaciones)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val btnAccion: View = view.findViewById(R.id.btnAccion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_celular, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.tvModelo.text = producto.modelo
        holder.tvEstado.text = producto.estado.toString()
        holder.tvEspecificaciones.text = "${producto.almacenamiento} • ${producto.ram}"
        holder.tvPrecio.text = "$${"%.2f".format(producto.precio)}"
        holder.btnAccion.visibility = if(mostrarBoton) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = productos.size
}
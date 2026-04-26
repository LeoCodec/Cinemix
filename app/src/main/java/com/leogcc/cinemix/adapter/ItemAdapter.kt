package com.leogcc.cinemix.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leogcc.cinemix.data.model.Item
import com.leogcc.cinemix.databinding.ItemCardBinding

class ItemAdapter(
    private val onClick: (Item) -> Unit,
    private val onDoubleClick: (Item) -> Unit
) : ListAdapter<Item, ItemAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val b: ItemCardBinding) : RecyclerView.ViewHolder(b.root) {
        private var lastClickTime = 0L

        fun bind(item: Item) {
            b.tvTitulo.text = item.titulo
            b.tvMeta.text = item.anio.toString() + " - " + item.genero
            b.tvTipo.text = when (item.tipo) {
                "pelicula" -> "Pelicula"
                "serie" -> "Serie"
                else -> "Libro"
            }
            b.tvRating.text = item.calificacion.toString()
            b.tvVeces.text = if (item.vecesVisto > 0) "Visto " + item.vecesVisto + " veces" else ""
            if (item.portadaUrl.isNotEmpty()) {
                Glide.with(b.root).load(item.portadaUrl).into(b.imgPortada)
            }
            b.root.setOnClickListener {
                val now = SystemClock.elapsedRealtime()
                if (now - lastClickTime < 600) {
                    onDoubleClick(item)
                    lastClickTime = 0L
                } else {
                    lastClickTime = now
                    b.root.postDelayed({
                        if (SystemClock.elapsedRealtime() - lastClickTime >= 600) {
                            onClick(item)
                        }
                    }, 600)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(a: Item, b: Item) = a.id == b.id
        override fun areContentsTheSame(a: Item, b: Item) = a == b
    }
}
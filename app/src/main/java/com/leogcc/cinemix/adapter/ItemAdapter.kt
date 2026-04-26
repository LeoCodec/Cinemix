package com.leogcc.cinemix.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leogcc.cinemix.data.model.Item
import com.leogcc.cinemix.databinding.ItemCardBinding
class ItemAdapter(private val onClick: (Item) -> Unit) :
    ListAdapter<Item, ItemAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(private val b: ItemCardBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Item) {
            b.tvTitulo.text = item.titulo
            b.tvMeta.text   = "{item.anio} · {item.genero}"
            b.tvTipo.text   = when (item.tipo) {
                "pelicula" -> "Pelicula"
                "serie"    -> "Serie"
                else       -> "Libro"
            }
            b.tvRating.text = "{"%.1f".format(item.calificacion)}"
            if (item.portadaUrl.isNotEmpty()) {
                Glide.with(b.root).load(item.portadaUrl).into(b.imgPortada)
            }
            b.root.setOnClickListener { onClick(item) }
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

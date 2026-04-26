package com.leogcc.cinemix.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.leogcc.cinemix.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = arguments?.getString("itemId") ?: return
        viewModel.cargar(itemId)

        viewModel.item.observe(viewLifecycleOwner) { item ->
            binding.tvTitulo.text   = item.titulo
            binding.tvMeta.text     = item.anio.toString() + " - " + item.genero
            binding.tvDirector.text = if (item.tipo == "libro") "Autor: " + item.autor else "Director: " + item.director
            binding.tvSinopsis.text = item.sinopsis
            binding.ratingBar.rating = item.calificacion
            binding.tvVeces.text    = "Visto " + item.vecesVisto + " veces"
            binding.btnFavorito.isChecked = item.esFavorito
            if (item.portadaUrl.isNotEmpty()) {
                Glide.with(this).load(item.portadaUrl).into(binding.imgPortada)
            }
        }

        binding.btnFavorito.setOnCheckedChangeListener { _, checked ->
            viewModel.toggleFavorito(checked)
        }

        binding.btnEliminar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Eliminar")
                .setMessage("Seguro que quieres eliminar esto?")
                .setPositiveButton("Si") { _, _ -> viewModel.eliminar() }
                .setNegativeButton("No", null)
                .show()
        }

        binding.btnEditar.setOnClickListener {
            Toast.makeText(requireContext(), "Editar proximamente", Toast.LENGTH_SHORT).show()
        }

        viewModel.eliminado.observe(viewLifecycleOwner) { ok ->
            if (ok) {
                Toast.makeText(requireContext(), "Eliminado!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
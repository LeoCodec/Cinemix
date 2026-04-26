package com.leogcc.cinemix.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leogcc.cinemix.databinding.FragmentEstadisticasBinding

class EstadisticasFragment : Fragment() {

    private var _binding: FragmentEstadisticasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEstadisticasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarEstadisticas()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        viewModel.estadisticas.observe(viewLifecycleOwner) { stats ->
            binding.tvPeliculas.text = "Peliculas: "
            binding.tvSeries.text    = "Series: "
            binding.tvLibros.text    = "Libros: "
            val total = (stats["pelicula"] ?: 0) + (stats["serie"] ?: 0) + (stats["libro"] ?: 0)
            binding.tvTotal.text = "Total: "
        }

        viewModel.cargarEstadisticas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
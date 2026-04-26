package com.leogcc.cinemix.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leogcc.cinemix.adapter.ItemAdapter
import com.leogcc.cinemix.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemAdapter(
            onClick = { item ->
                val action = HomeFragmentDirections.actionHomeToDetail(item.id)
                findNavController().navigate(action)
            },
            onDoubleClick = { item ->
                viewModel.incrementarVisto(item)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
        }

        viewModel.mensaje.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        viewModel.cargar(null)

        binding.btnTodos.setOnClickListener { viewModel.cargar(null) }
        binding.btnPeliculas.setOnClickListener { viewModel.cargar("pelicula") }
        binding.btnSeries.setOnClickListener { viewModel.cargar("serie") }
        binding.btnLibros.setOnClickListener { viewModel.cargar("libro") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
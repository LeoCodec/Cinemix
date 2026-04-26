package com.leogcc.cinemix.ui.add

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.leogcc.cinemix.data.model.Item
import com.leogcc.cinemix.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddViewModel by viewModels()
    private var portadaUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            portadaUri = it
            binding.imgPortada.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPortada.setOnClickListener { pickImage.launch("image/*") }

        binding.btnGuardar.setOnClickListener {
            val titulo = binding.etTitulo.text.toString().trim()
            if (titulo.isEmpty()) {
                binding.etTitulo.error = "El título es obligatorio"
                return@setOnClickListener
            }

            val tipo = when (binding.toggleTipo.checkedButtonId) {
                binding.btnPelicula.id -> "pelicula"
                binding.btnSerie.id    -> "serie"
                else                   -> "libro"
            }

            val item = Item(
                tipo         = tipo,
                titulo       = titulo,
                anio         = binding.etAnio.text.toString().toIntOrNull() ?: 0,
                genero       = binding.etGenero.text.toString().trim(),
                director     = binding.etDirector.text.toString().trim(),
                autor        = binding.etAutor.text.toString().trim(),
                sinopsis     = binding.etSinopsis.text.toString().trim(),
                calificacion = binding.ratingBar.rating,
                esFavorito   = binding.switchFavorito.isChecked,
                vecesVisto   = binding.etVeces.text.toString().toIntOrNull() ?: 0
            )

            viewModel.guardar(item, portadaUri)
        }

        viewModel.guardado.observe(viewLifecycleOwner) { ok ->
            if (ok) {
                Toast.makeText(requireContext(), "Guardado ✓", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

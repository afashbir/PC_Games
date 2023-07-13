package com.ewide.test.andri.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewide.test.andri.databinding.FragmentFavouritesBinding
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.ui.search.GameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(), GameAdapter.Listener {

    private var _binding: FragmentFavouritesBinding? = null

    private val viewModel: FavouritesViewModel by viewModels()

    private val favouritesAdapter: GameAdapter by lazy { GameAdapter(this) }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favourites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouritesAdapter
        }

        viewModel.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                FavouritesModelUiState.Loading -> showLoadingState()
                FavouritesModelUiState.EmptyResult -> showEmptyState()
                is FavouritesModelUiState.Success -> showFavourites(state.data)
            }
        }

        viewModel.load()
    }

    override fun onClick(item: Game) {
        viewModel.unFavorite(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showLoadingState() {
        binding.run {
            emptyMessage.isVisible = false
            favourites.isVisible = false
            loadingView.isVisible = true
        }
    }

    private fun showEmptyState() {
        binding.run {
            loadingView.isVisible = false
            favourites.isVisible = false
            emptyMessage.isVisible = true
        }
    }

    private fun showFavourites(data: List<Game>) {
        binding.run {
            loadingView.isVisible = false
            emptyMessage.isVisible = false
            favourites.isVisible = true
        }

        favouritesAdapter.setData(data)
    }
}
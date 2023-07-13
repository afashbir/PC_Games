package com.ewide.test.andri.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewide.test.andri.databinding.FragmentSearchBinding
import com.ewide.test.andri.domain.model.Game
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchBoxView.Listener {

    private var _binding: FragmentSearchBinding? = null

    private val searchViewModel: SearchViewModel by viewModels()

    private val resultAdapter: GameAdapter by lazy { GameAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultAdapter
        }

        searchViewModel.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                SearchModelUiState.Loading -> showLoadingState()
                SearchModelUiState.EmptyResult -> showEmptyState()
                is SearchModelUiState.Error -> showErrorState(state.throwable.message)
                is SearchModelUiState.Success -> showSearchResult(state.data)
            }
        }

        binding.searchBox.onSearch(this)
    }

    override fun onSearch(keyword: String) {
        searchViewModel.searchGame(keyword)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showSearchResult(data: List<Game>) {
        binding.run {
            startSearching.isVisible = false
            loadingView.isVisible = false
            errorResult.isVisible = false
            emptyResult.isVisible = false
            searchResult.isVisible = true
        }

        resultAdapter.setData(data)
    }

    private fun showErrorState(message: String?) {
        binding.run {
            startSearching.isVisible = false
            loadingView.isVisible = false
            searchResult.isVisible = false
            emptyResult.isVisible = false
            errorResult.isVisible = true
        }

        if (!message.isNullOrEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoadingState() {
        binding.run {
            startSearching.isVisible = false
            searchResult.isVisible = false
            errorResult.isVisible = false
            emptyResult.isVisible = false
            loadingView.isVisible = true
        }
    }

    private fun showEmptyState() {
        binding.run {
            startSearching.isVisible = false
            searchResult.isVisible = false
            errorResult.isVisible = false
            loadingView.isVisible = false
            emptyResult.isVisible = true
        }
    }
}
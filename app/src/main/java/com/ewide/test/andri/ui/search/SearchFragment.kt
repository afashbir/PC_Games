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
import coil.load
import com.ewide.test.andri.R
import com.ewide.test.andri.databinding.FragmentSearchBinding
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.domain.model.SortPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchBoxView.Listener, GameAdapter.Listener {

    private var _binding: FragmentSearchBinding? = null

    private val viewModel: SearchViewModel by viewModels()

    private val resultAdapter: GameAdapter by lazy { GameAdapter(this) }

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

        viewModel.uiState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                SearchModelUiState.Loading -> showLoadingState()
                SearchModelUiState.EmptyResult -> showEmptyState()
                is SearchModelUiState.Error -> showErrorState(state.throwable.message)
                is SearchModelUiState.Success -> showSearchResult(state.data, state.sortPref)
            }
        }

        binding.searchBox.onSearch(this)

        binding.sortButton.setOnClickListener { viewModel.sortResult() }
    }

    override fun onSearch(keyword: String) {
        viewModel.searchGame(keyword)
    }

    override fun onClick(item: Game) {
        viewModel.updateLovedStatus(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showSearchResult(data: List<Game>, sortPref: SortPref) {
        binding.run {
            startSearching.isVisible = false
            loadingView.isVisible = false
            errorResult.isVisible = false
            emptyResult.isVisible = false
            searchResult.isVisible = true

            sortButton.isVisible = true
            val drawableRes = when (sortPref) {
                SortPref.ASC ->  R.drawable.ic_desc
                SortPref.DESC -> R.drawable.ic_asc
            }

            sortButton.load(drawableRes)
        }

        resultAdapter.setData(data)
    }

    private fun showErrorState(message: String?) {
        binding.run {
            startSearching.isVisible = false
            loadingView.isVisible = false
            sortButton.isVisible = false
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
            sortButton.isVisible = false
            searchResult.isVisible = false
            errorResult.isVisible = false
            emptyResult.isVisible = false
            loadingView.isVisible = true
        }
    }

    private fun showEmptyState() {
        binding.run {
            startSearching.isVisible = false
            sortButton.isVisible = false
            searchResult.isVisible = false
            errorResult.isVisible = false
            loadingView.isVisible = false
            emptyResult.isVisible = true
        }
    }
}
package com.ewide.test.andri.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewide.test.andri.databinding.ItemGameBinding
import com.ewide.test.andri.domain.model.Game

class GameAdapter : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var games: List<Game> = listOf()

    inner class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game) {
            with(item) {
                binding.id.text = id
                binding.title.text = title
                binding.thumbnailView.load(thumbnailUrl.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount() = games.count()

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
    }

    fun setData(data: List<Game>) {
        games = data
        notifyItemRangeChanged(0, data.count())
    }
}
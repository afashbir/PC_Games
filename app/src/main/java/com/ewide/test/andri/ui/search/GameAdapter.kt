package com.ewide.test.andri.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewide.test.andri.R
import com.ewide.test.andri.databinding.ItemGameBinding
import com.ewide.test.andri.domain.model.Game

class GameAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var games: MutableList<Game> = mutableListOf()

    inner class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game, position: Int) {
            with(item) {
                binding.id.text = id
                binding.title.text = title
                binding.thumbnailView.load(thumbnailUrl.toString())

                displayLovedState(isLoved)
            }

            binding.loved.setOnClickListener {
                displayLovedState(!item.isLoved)
                games[position] = item.copy(isLoved = !item.isLoved)

                listener.onClick(games[position])
            }
        }

        private fun displayLovedState(isLoved: Boolean) {
            val lovedRes = if (isLoved) {
                R.drawable.ic_filled_favorite
            } else {
                R.drawable.ic_favorite
            }

            binding.loved.load(lovedRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount() = games.count()

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position], position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Game>) {
        games = data.toMutableList()
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(item: Game)
    }
}
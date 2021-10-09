package com.example.youtubeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class vidAdap(
    private val videos: Array<Array<String>>,
    private val plr: YouTubePlayer
) : RecyclerView.Adapter<vidAdap.VideoViewHolder>() {
    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.btnvid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_row,
            parent,
            false
        )
        return VideoViewHolder(itemView)
    }

    override fun onBindViewHolder(hold: VideoViewHolder, position: Int) {
        val currentTit = videos[position][0]
        val currentLi = videos[position][1]
        hold.button.text = currentTit
        hold.button.setOnClickListener {
            plr.loadVideo(currentLi, 0f)

        }
    }

    override fun getItemCount() = videos.size


}
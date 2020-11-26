package com.zing.zalo.hsapp.presentation.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zing.zalo.domain.entity.MediaSong
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.ContentMediaRowBinding
import com.zing.zalo.hsapp.framework.alarm.MediaFactory
import com.zing.zalo.hsapp.presentation.MyApplication

/**
 * RecycleView Adapter for list Media
 */
class MediaAdapter : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    var listMedia = listOf<MediaSong>()

    var selPos: Int = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ContentMediaRowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMedia[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listMedia.size
    }

    fun releaseMedia() {
        mediaPlayer?.run {
            stop()
            release()
        }
        mediaPlayer = null
    }

    inner class ViewHolder(val binding: ContentMediaRowBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.radioButton.setOnClickListener {
                if (adapterPosition != selPos) {
                    val oldSelPos = selPos
                    selPos = adapterPosition
                    notifyItemChanged(oldSelPos)
                    notifyItemChanged(selPos)

                    // stop old music
                    releaseMedia()

                    // play new music
                    mediaPlayer = MediaFactory.createOneTimeRingTone(MyApplication.getInstance(), selPos)
                    mediaPlayer?.start()
                }
            }
        }

        fun bind(item: MediaSong) {
            binding.radioButton.text = item.name

            if (adapterPosition == selPos) {
                binding.radioButton.isChecked = true
                binding.radioButton.setTextColor(MyApplication.getInstance().getColor(R.color.colorAccent))
            }
            else {
                binding.radioButton.isChecked = false
                binding.radioButton.setTextColor(MyApplication.getInstance().getColor(R.color.defaultTextView))
            }
        }
    }
}
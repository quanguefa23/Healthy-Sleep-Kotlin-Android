package com.zing.zalo.hsapp.framework.alarm

import android.content.Context
import android.media.MediaPlayer
import com.zing.zalo.domain.entity.MediaSong
import com.zing.zalo.hsapp.R

object MediaFactory {

    val listMedia = listOf(
            MediaSong(0, "Among Us Ringtone", R.raw.among_us_ringtone),
            MediaSong(1, "Baby Shark Trap", R.raw.baby_shark_trap),
            MediaSong(2, "Christmas Trap", R.raw.christmas_trap),
            MediaSong(3, "Jingle Bell Rock", R.raw.jingle_bell_rock),
            MediaSong(4, "Old Telephone", R.raw.old_telephone),
            MediaSong(5, "Old Town Road", R.raw.old_town_road),
            MediaSong(6, "Savage Love", R.raw.savage_love),
            MediaSong(7, "Suga Boom Boom", R.raw.suga_boom_boom)
        )

    private fun getMediaResourceById(id: Int) = listMedia[id].resId

    fun getMediaNameById(id: Int) = listMedia[id].name

    fun createLoopingRingTone(context: Context, id: Int): MediaPlayer =
        MediaPlayer.create(context, getMediaResourceById(id)).apply { isLooping = true }

    fun createOneTimeRingTone(context: Context, id: Int): MediaPlayer =
        MediaPlayer.create(context, getMediaResourceById(id)).apply { isLooping = false }
}
package com.zing.zalo.hsapp.framework.alarm

import android.content.Context
import android.media.MediaPlayer
import com.zing.zalo.hsapp.R

object MediaFactory {

    fun createLoopingRingTone(context: Context): MediaPlayer =
        MediaPlayer.create(context, R.raw.savage_love).apply { isLooping = true }
}
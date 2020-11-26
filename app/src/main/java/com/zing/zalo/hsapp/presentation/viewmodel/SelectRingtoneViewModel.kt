package com.zing.zalo.hsapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.zing.zalo.data.repository.Repository
import com.zing.zalo.domain.entity.MediaSong
import com.zing.zalo.hsapp.framework.alarm.MediaFactory

class SelectRingtoneViewModel @ViewModelInject constructor(val repository: Repository): ViewModel() {

    fun getListMedia(): List<MediaSong> = MediaFactory.listMedia

    fun saveMediaOption(opt: Int) {
        repository.saveMediaOption(opt)
    }

    fun getMediaOption() = repository.getMediaOption()
}
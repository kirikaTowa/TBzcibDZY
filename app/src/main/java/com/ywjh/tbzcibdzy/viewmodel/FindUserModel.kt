package com.ywjh.tbzcibdzy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ywjh.tbzcibdzy.roombasic.User

class FindUserModel:ViewModel() {

        var findUser: MutableLiveData<User>? = null
                get() {
                        if (field == null) {
                                field = MutableLiveData()
                        }
                        return field
                }

}
package com.ywjh.tbzcibdzy.utils

import android.content.Context
import android.content.SharedPreferences
import com.ywjh.tbzcibdzy.R


class MyData(private val context: Context) {

    val memoryname by lazy { context.resources.getString(R.string.Login_state) }
    val itemmemory by lazy{ context.getSharedPreferences(memoryname,Context.MODE_PRIVATE)}

    val deafultbool by lazy { context.resources.getBoolean(R.bool.default_bool) }
    var defaultboolvalue=false



    fun saveSlash( slash: Boolean){
        Thread {
            itemmemory.edit().putBoolean("slashs",slash).apply()
        }.start()
    }
    fun loadSlash():Boolean{

            defaultboolvalue =itemmemory.getBoolean("slashs",deafultbool)
        return defaultboolvalue
    }
}


package com.ywjh.tbzcibdzy.roombasic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity//Room表示创建实体类
class User(account: Long, psd: String) {

    @PrimaryKey
    var account: Long = account

    @ColumnInfo(name = "password")
    var psd: String = psd

}
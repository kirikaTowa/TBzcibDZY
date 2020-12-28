package com.ywjh.tbzcibdzy.roombasic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Goods(
    goodscode: Int, goodsname: String, goodssort: String, goodsprice: Float,
    goodspricecount: Float, goodssum: Int, goodsstate: Boolean, goodsimageuri: String?
)  {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @ColumnInfo(name = "goods_code")
    var goodscode: Int = goodscode
    @ColumnInfo(name = "goods_name")
    var goodsname: String = goodsname
    @ColumnInfo(name = "goods_sort")
    var goodssort: String = goodssort
    @ColumnInfo(name = "goods_price")
    var goodsprice: Float = goodsprice
    @ColumnInfo(name = "goods_count")
    var goodspricecount: Float = goodspricecount//折扣 0.23
    @ColumnInfo(name = "goods_sum")
    var goodssum: Int = goodssum
    //控制上架下架
    @ColumnInfo(name = "goods_state")
    var goodsstate: Boolean = goodsstate
    @ColumnInfo(name = "goods_imageuri")
    var goodsimageuri:String?=goodsimageuri
}

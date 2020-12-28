package com.ywjh.tbzcibdzy.roombasic

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoodsDao {

    @Insert
    fun insertGoods(vararg goods: Goods?)

    @Update//返回修改的记录条数
    fun updateGoods(vararg  goods: Goods?) //通过id进行替换内容

    @Delete
    fun deleteGoods(vararg  goods: Goods?)

    @Query("DELETE FROM Goods")//删除所有内容
    fun deleteAllGoods();

   @Query("SELECT * FROM Goods ")//降序查询
    fun getAllGoods():List<Goods>
/*
    @Query("SELECT * FROM Goods WHERE account In(:account)")//查询账号是否存在
    fun getUser(account:Long): LiveData<User>

    @Query("SELECT * FROM Goods ")
    fun getAllWordsLive(): LiveData<List<User>>*/
}

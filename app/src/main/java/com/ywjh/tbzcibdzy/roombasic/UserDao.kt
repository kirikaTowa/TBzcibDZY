package com.ywjh.tbzcibdzy.roombasic

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao //Database access object
interface UserDao {

    @Insert
    fun insertUsers(vararg users: User?)

    @Update//返回修改的记录条数
    fun updateUsers(vararg users: User?) //通过id进行替换内容

    @Delete
    fun deleteUsers(vararg users: User?)

    @Query("DELETE FROM User")//删除所有内容
    fun deleteAllUsers();

    @Query("SELECT * FROM User ")//降序查询
    fun getAllUsers():List<User>

    @Query("SELECT * FROM User WHERE account In(:account)")//查询账号是否存在
    fun getUser(account:Long):User

    @Query("SELECT * FROM User ")
    fun getAllWordsLive():LiveData<List<User>>
}
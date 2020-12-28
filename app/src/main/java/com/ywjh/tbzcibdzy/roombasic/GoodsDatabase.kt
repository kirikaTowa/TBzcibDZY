package com.ywjh.tbzcibdzy.roombasic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Goods::class],version = 1,exportSchema = false)
 abstract class GoodsDatabase: RoomDatabase() {
    //若有多个entities则返回多个Dao

    companion object {
        var INSTANCE: GoodsDatabase? = null

        @Synchronized//如果有多个客户端  且同时instance时保证不会有碰撞 只有一个instance生成
        open fun getDatabase(context: Context): GoodsDatabase? {//静态类的静态方法写open
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, GoodsDatabase::class.java, "goods_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

    abstract fun getGoodsDao(): GoodsDao
}



package com.ywjh.tbzcibdzy.roombasic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class],version = 2,exportSchema = false)
 abstract class UserDatabase: RoomDatabase() {
    //若有多个entities则返回多个Dao

    companion object {
        var INSTANCE: UserDatabase? = null

        @Synchronized//如果有多个客户端  且同时instance时保证不会有碰撞 只有一个instance生成
        open fun getDatabase(context: Context): UserDatabase? {//静态类的静态方法写open
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    //.addMigrations(MIGRATION_6_7)
                    .build()
            }
            return INSTANCE
        }
    }

    abstract fun getUserDao(): UserDao
}



package com.ywjh.tbzcibdzy.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.roombasic.GoodsDao
import com.ywjh.tbzcibdzy.roombasic.GoodsDatabase
import com.ywjh.tbzcibdzy.roombasic.UserDao
import com.ywjh.tbzcibdzy.roombasic.UserDatabase

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

//所有Activity基类
abstract class BaseActivity: AppCompatActivity(), AnkoLogger {
    private var userdatabase: UserDatabase?=null
    lateinit var userdao: UserDao



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutid())//基类抽取界面组
        initListener()//子类中并非一定要实现，创建一个function即可
        initDate()//数据支持
    }
    //    初始化数据,非私有加入open关键字才能复写
    open protected fun initDate() {
        userdatabase=UserDatabase.getDatabase(applicationContext)!!
        userdao=userdatabase?.getUserDao()!!



    }

    //    adapter listener
    open protected fun initListener(){}

    //创建抽象类以供获取Layout，abstract不用加
    abstract fun getLayoutid():Int

    //在基类线中弹toast解决线的问题   1？
    protected fun myToast(msg:String){
        runOnUiThread{toast(msg)}
    }

    //开启activity并且finish当前界面,添加内联支持inline 与reified
    //进入看发现限制是Activity或Activity子类  reified用于找到传入泛型具体的类 下面内部就可以获取Tclass名（所以直接传泛型无法获取名称）
   inline  fun <reified T:BaseActivity>startActivityAndFinish(){
        startActivity<T>()
        finish()
    }
}
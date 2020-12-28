package com.ywjh.tbzcibdzy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseActivity
import com.ywjh.tbzcibdzy.roombasic.User
import com.ywjh.tbzcibdzy.utils.MyData
import com.ywjh.tbzcibdzy.viewmodel.FindUserModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_regist.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity() {


    val mydata by lazy {  MyData(applicationContext)  }
    lateinit var  userviewModel:FindUserModel
    var loginFlag:Boolean=false
    var loginState:Boolean=false

    override fun getLayoutid(): Int {
        return R.layout.activity_login
    }

    override fun initDate() {
        super.initDate()

        if (mydata?.loadSlash())
        {
            startActivityAndFinish<MainActivity>()
        }


        userviewModel = ViewModelProvider(this).get(FindUserModel::class.java)
    }

    override fun initListener() {
        super.initListener()
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            loginState=isChecked
        }
    }

    fun LoginUser(v:View) {
        loginFlag=true
        var textcount = Account_text.text.toString()
        var textpws = Password_text.text.toString()


        if (textcount.isEmpty()) {
            myToast("请输入用户名")
        }
        if (textpws.isEmpty()) {
            myToast("请输入密码")
        }

        if (!textcount.isEmpty() && !textpws.isEmpty()) {
            userviewModel.findUser = userdao.getUser(textcount.toLong())



            userviewModel.findUser?.let {
                userviewModel.findUser!!.observe(this, Observer {
                    if (loginFlag==true){
                        if ((it!=null)){
                            if (  it.psd.equals(textpws)) {
                                myToast("登录成功")
                                mydata.saveSlash( loginState)
                                startActivityAndFinish<MainActivity>()
                            } else {
                                myToast("账号或密码错误")
                            }
                        }else{
                            myToast("用户不存在")
                        }
                    }

                })
            }

        }
    }


    fun IntoRegist(v:View){
        startActivity<RegistActivity>()
    }

}
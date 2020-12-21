package com.ywjh.tbzcibdzy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseActivity
import com.ywjh.tbzcibdzy.roombasic.User
import com.ywjh.tbzcibdzy.viewmodel.FindUserModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_regist.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity() {
    lateinit var  userviewModel:FindUserModel
    override fun getLayoutid(): Int {
        return R.layout.activity_login
    }

    override fun initDate() {
        super.initDate()
        userviewModel = ViewModelProvider(this).get(FindUserModel::class.java)
    }


    fun LoginUser(v:View) {
        var textcount = Account_text.text.toString()
        var textpws = Password_text.text.toString()


        if (textcount.isEmpty()) {
            myToast("请输入用户名")
        }
        if (textpws.isEmpty()) {
            myToast("请输入密码")
        }

        if (!textcount.isEmpty() && !textpws.isEmpty()) {
            userviewModel.findUser?.value = userdao.getUser(textcount.toLong())
            println("11111"+userviewModel.findUser?.value )

            if (  userviewModel.findUser?.value != null) {
                if (  userviewModel.findUser?.value!!.psd.equals(textpws)) {
                    myToast("登录成功")
                    startActivityAndFinish<MainActivity>()
                } else {
                    myToast("账号或密码错误")
                }
            } else {
                myToast("用户不存在")
            }
        }
    }



    fun IntoRegist(v:View){
        startActivity<RegistActivity>()
    }

}
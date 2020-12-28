package com.ywjh.tbzcibdzy.ui.activity


import android.os.AsyncTask
import android.os.Binder
import android.os.Handler
import android.view.View
import androidx.lifecycle.*
import com.ywjh.tbzcibdzy.base.BaseActivity
import com.ywjh.tbzcibdzy.roombasic.User
import com.ywjh.tbzcibdzy.roombasic.UserDao
import com.ywjh.tbzcibdzy.viewmodel.FindUserModel
import kotlinx.android.synthetic.main.activity_regist.*
import com.ywjh.tbzcibdzy.R


class RegistActivity : BaseActivity() {

    lateinit var  userviewModel:FindUserModel
    var registFlag:Boolean=false

    override fun getLayoutid(): Int {
        return R.layout.activity_regist
    }

    override fun initDate() {
        super.initDate()
        userviewModel = ViewModelProvider(this).get(FindUserModel::class.java)
    }

    fun RegistUser(v: View) {
        //会自动绑定get方法 插入成功后会自动get 设置个变量
        registFlag=true
        var textcount = Input_account.text.toString()
        var textpws = Input_password.text.toString()
        var textpwsre = Input_passwordreput.text.toString()

        if (textcount.isEmpty())
        { myToast("请输入用户名") }
        if (textpws.isEmpty())
        { myToast("请输入密码") }
        if (textpwsre.isEmpty())
        { myToast("请确认密码") }
        if (!textpwsre.equals(textpws))
        { myToast("两次密码输入不一致") }


        if (!textcount.isEmpty() && !textpws.isEmpty() && !textpwsre.isEmpty() && textpwsre.equals(textpws)) {
            userviewModel.findUser = userdao.getUser(textcount.toLong())


            userviewModel.findUser?.let {
                userviewModel.findUser!!.observe(this, Observer {
                    if (registFlag==true){
                        if ((it!=null)){
                            Input_account.text.clear()
                            myToast("账户已存在")
                        }else{
                            registFlag=false
                            var user = User(textcount.toLong(), textpws)
                            InsertAsyncTask(userdao).execute(user)
                            //增设查询该条记录
                            myToast("插入成功")
                            Handler().postDelayed(Runnable { startActivityAndFinish<LoginActivity>() }, 1000)
                        }
                    }

                })
            }


        }

    }

    internal class InsertAsyncTask(private val userdao: UserDao) : AsyncTask<User?, Unit, Unit>() {
        //在后台工作
        override fun doInBackground(vararg params: User?) {
            publishProgress()//该方法用于一段时间报告工作进度
            userdao.insertUsers(*params)
        }
    }
}



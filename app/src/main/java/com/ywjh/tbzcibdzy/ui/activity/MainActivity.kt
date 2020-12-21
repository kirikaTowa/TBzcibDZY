package com.ywjh.tbzcibdzy.ui.activity


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseActivity
import com.ywjh.tbzcibdzy.utils.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class  MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomBar.setOnTabSelectListener{
            // it代表参数id
            val transaction = supportFragmentManager.beginTransaction()
            //将当前container替换为第二个参数由FragmentUtil获取
            transaction.replace(R.id.container, FragmentUtil.fragmentUtil.getFragment(it),it.toString())//it后需要一个tag
            transaction.commit()
        }



    }

    override fun getLayoutid(): Int {
        return R.layout.activity_main
    }



    override fun onDestroy() {
        super.onDestroy()

    }


}
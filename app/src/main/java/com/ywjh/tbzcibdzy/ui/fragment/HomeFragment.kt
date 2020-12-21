package com.ywjh.stusqlmanager.ui.fragment

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.ywjh.tbzcibdzy.base.BaseFragment
import kotlinx.android.synthetic.main.activity_regist.*
import com.ywjh.tbzcibdzy.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.runOnUiThread

import java.io.IOException
import java.util.ArrayList

class HomeFragment: BaseFragment() {

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_home, null)
    }



}
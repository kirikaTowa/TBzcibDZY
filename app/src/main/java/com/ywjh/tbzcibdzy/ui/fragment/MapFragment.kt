package com.ywjh.stusqlmanager.ui.fragment

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.ArrayList

class MapFragment: BaseFragment() {
    lateinit var activity: Application
    var positionText: TextView? = null
    var mLocationClient: LocationClient? = null
    private var isFirstLocate = true
    lateinit var baiduMap: BaiduMap

    override fun initView(): View? {
        activity= getActivity()?.getApplication()!!
        SDKInitializer.initialize( activity)
        return View.inflate(context, R.layout.fragment_map, null)
    }

    override fun initListener() {

    }

    override fun initData() {
        positionText =textView8



        mLocationClient = LocationClient( activity)
        //注册定位监听器接口
        mLocationClient!!.registerLocationListener(MyLocationListener())

        baiduMap = bmapView.getMap()
        // 建立权限数组，动态申请
        baiduMap.isMyLocationEnabled=true

        val permissionList: MutableList<String> = ArrayList()
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!permissionList.isEmpty()) {
            val permissions = permissionList.toTypedArray()
            getActivity()?.let { ActivityCompat.requestPermissions(it, permissions, 1) }
        } else {
            requestLocation()
        }

    }

    private fun requestLocation() {
        initLocation();
        mLocationClient!!.start()//有权限则进行定位
    }

    private fun initLocation() {
        val option = LocationClientOption()
        //option.setLocationNotify(false)
        option.locationMode=LocationClientOption.LocationMode.Hight_Accuracy

        option.setScanSpan(10000)
        option.setIsNeedAddress(true)
        mLocationClient!!.locOption = option
    }


    //处理回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.size > 0) {
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {

                        myToast("必须同意所有权限才能使用本程序")

                    }
                }
                requestLocation()
            } else {
                myToast("发生未知错误")


            }
            else -> {
            }
        }
    }
    inner class MyLocationListener : BDLocationListener {
        override fun onReceiveLocation(p0: BDLocation?) {


            getActivity()?.runOnUiThread(Runnable {

                val currentPosition = StringBuilder()
                if (p0 != null) {
                    currentPosition.append("国家：").append(p0.getCountry()).append("\n")
                    currentPosition.append("省：").append(p0.getProvince()).append("\n")
                    currentPosition.append("市：").append(p0.getCity()).append("\n")
                    currentPosition.append("区：").append(p0.getDistrict()).append("\n")
                    currentPosition.append("街道：").append(p0.getStreet()).append("\n")
                    currentPosition.append("定位方式： ")
                    if (p0.getLocType() === BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS")
                    } else if (p0.getLocType() === BDLocation.TypeNetWorkLocation
                    ) {
                        currentPosition.append("网络")
                    }
                }
                positionText?.setText(currentPosition)
            })


            if (p0 != null) {
                if (p0.getLocType() == BDLocation.TypeGpsLocation
                    || p0.getLocType() == BDLocation.TypeNetWorkLocation
                ) {
                    navigateTo(p0)
                }
            }
        }




        fun onConnectHotSpotMessage(s: String?, i: Int) {}
    }
    private fun navigateTo(location: BDLocation) {
        if (isFirstLocate) {
            myToast("nav to ${location.addrStr}")

            val ll = LatLng(location.latitude, location.longitude)//赋值经纬度
            var update = MapStatusUpdateFactory.newLatLng(ll)//获取该经纬度的MapStatusUpdate对象
            baiduMap!!.animateMapStatus(update)//赋值移动
            update = MapStatusUpdateFactory.zoomTo(16f)//控制缩放级别
            baiduMap!!.animateMapStatus(update)//移动
            isFirstLocate = false//防止多次移动
        }
        val locationBuilder = MyLocationData.Builder()
        locationBuilder.latitude(location.latitude)
        locationBuilder.longitude(location.longitude)
        val locationData = locationBuilder.build()
        baiduMap!!.setMyLocationData(locationData)
    }
    override fun onResume() {
        super.onResume()
       bmapView.onResume()
    }

    override fun onPause() {
        super.onPause()
       bmapView.onPause()
    }




    override fun onDestroy() {
        super.onDestroy()
        bmapView?.let {
            it.onDestroy()
        }
        isFirstLocate = true
        mLocationClient!!.stop()
        baiduMap.isMyLocationEnabled=false
    }
}
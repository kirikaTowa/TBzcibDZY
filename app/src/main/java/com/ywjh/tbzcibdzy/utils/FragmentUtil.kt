package com.ywjh.tbzcibdzy.utils

import com.ywjh.tbzcibdzy.R
import com.ywjh.stusqlmanager.ui.fragment.ShopFragment
import com.ywjh.stusqlmanager.ui.fragment.HomeFragment
import com.ywjh.stusqlmanager.ui.fragment.MapFragment
import com.ywjh.stusqlmanager.ui.fragment.MyFragment

import com.ywjh.tbzcibdzy.base.BaseFragment


//管理Fragment的Util类
//单例
class FragmentUtil private constructor(){//私有化构造方法
    val homeFragment by lazy { HomeFragment()  }
    val mapFragment by lazy { MapFragment()  }
    val shopFragment by lazy {ShopFragment()  }
    val myFragment by lazy { MyFragment()  }

    companion object{//伴生对象
        val fragmentUtil by lazy { FragmentUtil() }
    }
//通过封装类选择进入那个 Fragment
    fun getFragment(tabId:Int): BaseFragment {//Base返回值默认不支持为空

        when(tabId){
            R.id.tab_home -> return  homeFragment
            R.id.tab_map -> return  mapFragment
            R.id.tab_shop-> return  shopFragment
            R.id.tab_my -> return  myFragment
        }

        //都没有就加null 非空判断 BaseFragment不支持空 所以加个？ 或者默认给homeFragment
        return  homeFragment
    }
}
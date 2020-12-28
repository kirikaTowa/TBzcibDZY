package com.ywjh.stusqlmanager.ui.fragment

import android.view.View
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseFragment
import com.ywjh.tbzcibdzy.ui.activity.LoginActivity
import com.ywjh.tbzcibdzy.ui.activity.MainActivity
import com.ywjh.tbzcibdzy.utils.MyData
import kotlinx.android.synthetic.main.fragment_myself.*
import org.jetbrains.anko.startActivity

class MyFragment: BaseFragment() {
    val mydata by lazy { getActivity()?.applicationContext?.let { MyData(it) } }
    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_myself, null)
    }

    override fun initListener() {
        Button_exit.setOnClickListener {
            mydata?.saveSlash(false)
            activity?.startActivity<LoginActivity>()
        }

    }

    override fun initData() {
        super.initData()

    }
}
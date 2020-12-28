package com.ywjh.stusqlmanager.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ywjh.tbzcibdzy.base.BaseFragment
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.adapter.GoodsAdapter
import com.ywjh.tbzcibdzy.roombasic.Goods
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment() {

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_home, null)
    }

    override fun initData() {
        super.initData()

            var list:List<Goods> =goodsdao.getAllGoods()



        val goodsAdapter = GoodsAdapter()
        recycleView.apply{
            adapter=goodsAdapter
            layoutManager= GridLayoutManager(requireContext(),2)//两列

        }
        goodsAdapter.submitList(list)
    }

}
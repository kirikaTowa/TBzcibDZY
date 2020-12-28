package com.ywjh.tbzcibdzy.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.ywjh.tbzcibdzy.roombasic.Goods
import com.ywjh.tbzcibdzy.R
import kotlinx.android.synthetic.main.goods_cell.view.*

class GoodsAdapter: ListAdapter<Goods,GoodsAdapter.MyViewHolder>(DIFFCALLBACK)  {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    object DIFFCALLBACK : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem === newItem //判断是否为同一个对象 三等于号
        }

        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.id == newItem.id//判断内容是否相同

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //创建holder
        val holder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.goods_cell, parent, false))

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photoItem=getItem(position)
        //使用with语句
        with(holder.itemView) {
            shimmerLayoutCell.apply {
                setShimmerColor(0x55FFFFFF)
                setShimmerAngle(0)//闪动角度
                startShimmerAnimation()
            }

            textViewUser.text=photoItem.goodsname
            textViewLikes.text="￥${photoItem.goodsprice}"
            textViewFavorites.text="打折:${photoItem.goodspricecount}"
            textViewFavorites2.text="存货:${photoItem.goodssum}"
            textViewFavorites4.text="种别码:${photoItem.goodscode}"
            textViewFavorites5.text="id:${photoItem.id}"


        }

        Glide.with(holder.itemView)
            .load(getItem(position).goodsimageuri)//photo对象
            .placeholder(R.drawable.photo_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { holder.itemView.shimmerLayoutCell?.stopShimmerAnimation() }//判断空， 图片未加载完全就切走 但listenrer还在监听
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false//都return false 不然不显图
                }//监听加载完成后停止shimmer
            }
            )
            .into(holder.itemView.imageView)//加载上去
    }


}
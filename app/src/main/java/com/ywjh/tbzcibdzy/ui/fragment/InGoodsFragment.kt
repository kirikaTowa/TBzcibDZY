package com.ywjh.stusqlmanager.ui.fragment

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseFragment
import com.ywjh.tbzcibdzy.roombasic.Goods
import kotlinx.android.synthetic.main.fragment_ingoods.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class InGoodsFragment: BaseFragment() {

    var goodsCode: Int?=null
    var goodsname: String?=null
    var goodssort: String?=null
    var goodsprice: Float?=null
    var goodspricecount: Float?=null
    var goodssum: Int?=null
    var goodsstate: Boolean=true
    var goodsimageuristring: String?=null
    var goodsimageuri: Uri?=null

    companion object {
        val TAKE_PHOTO = 1
        val CHOOSE_PHOTO = 2

    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_ingoods, null)
    }

    override fun initListener() {
        Switch_state.setOnCheckedChangeListener { buttonView, isChecked ->
            goodsstate=isChecked
        }
        Button_inphoto.setOnClickListener {
            if (getActivity()?.applicationContext?.let { it1 -> ContextCompat.checkSelfPermission(it1, Manifest.permission.WRITE_EXTERNAL_STORAGE) } !== PackageManager.PERMISSION_GRANTED) {
                getActivity()?.let { it1 -> ActivityCompat.requestPermissions(it1, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1) }
            } else {
                openAlbum()
            }
        }
        Button_incamera.setOnClickListener {

            val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss") // HH:mm:ss
            val date = Date(System.currentTimeMillis())
            var changeCode=simpleDateFormat.format(date)
            changeCode=changeCode+".jpg"
            println("1111111111111${changeCode}")

            // 1. 创建File对象，用于存储拍照后的图片
            val outputImage = File(getActivity()?.externalCacheDir, changeCode)
            println("11111111111"+outputImage)
            try {
                if (outputImage.exists()) {
                    outputImage.delete()
                }
                outputImage.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (Build.VERSION.SDK_INT < 24) {
                goodsimageuri = Uri.fromFile(outputImage)
            } else {
                goodsimageuri = getActivity()?.applicationContext?.let { it1 ->
                    FileProvider.getUriForFile(
                        it1,
                        "com.ywjh.cameraalbumtest.fileprovider",
                        outputImage
                    )
                }
            }
            // 启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, goodsimageuri)

            startActivityForResult(intent, TAKE_PHOTO)

        }



        Button_submitgoods.setOnClickListener {
            if (In_code.text!=null &&  In_goodsname.text!=null && In_goodssort.text!=null && In_goodsprice!=null
                    && In_goodscount.text!=null && In_goodssum.text!=null && goodsimageuristring!=null){

                goodsCode=In_code.text.toString().toInt()
                goodsname=In_goodsname.text.toString()
                goodssort=In_goodssort.text.toString()
                goodsprice=In_goodsprice.text.toString().toFloat()
                goodspricecount=In_goodscount.text.toString().toFloat()
                goodssum=In_goodssum?.text.toString().toInt()
/*                println("1111"+In_code.text!=null)
                println("1112"+ In_goodsname.text!=null)
                println("1113"+In_goodssort.text!=null)
                println("1114"+In_goodsprice==null)
                println("1115"+In_goodscount.text.toString().toFloat())
                println("1116"+In_goodssum.text)
                println("1117"+goodsimageuristring)*/
                var goods: Goods = Goods(goodsCode!!,goodsname!!,goodssort!!,goodsprice!!, goodspricecount!!, goodssum!!, goodsstate,goodsimageuristring)
                goodsdao.insertGoods( goods)
                myToast("上架成功")
            }else{


                myToast("请补全信息及上传图片")
            }


        }


    }

    override fun initData() {
        super.initData()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                try {
                    goodsimageuristring=goodsimageuri.toString()

                    // 将拍摄的照片显示出来
                    val bitmap = BitmapFactory.decodeStream(goodsimageuristring?.let {
                        getActivity()?.contentResolver?.openInputStream(
                                it.toUri())
                    })

                    imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            CHOOSE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                // 判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    // 4.4及以上系统使用这个方法处理图片
                    if (data != null) {
                        handleImageOnKitKat(data)
                    }
                } else {
                    // 4.4以下系统使用这个方法处理图片
                    if (data != null) {
                        handleImageBeforeKitKat(data)
                    }
                }
            }

        }
    }


    //4.4后 判断封装情况
    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        Log.d("TAG", "handleImageOnKitKat: uri is $uri")
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":".toRegex()).toTypedArray()[1] // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.path
        }
        displayImage(imagePath) // 根据图片路径显示图片
    }

    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
        val imagePath = getImagePath(uri, null)
        displayImage(imagePath)
    }

    fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO) // 打开相册
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum()
            } else {
                myToast("You denied the permission")

            }

        }
    }
    private fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        // 通过Uri和selection来获取真实的图片路径
        val cursor = getActivity()?.contentResolver?.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }

        goodsimageuristring=path
        return path

    }

    private fun displayImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            imageView.setImageBitmap(bitmap)
        } else {
            myToast("failed to get image")

        }
    }


}
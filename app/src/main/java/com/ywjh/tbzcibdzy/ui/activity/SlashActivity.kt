package com.ywjh.tbzcibdzy.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.ywjh.tbzcibdzy.R
import com.ywjh.tbzcibdzy.base.BaseActivity
import kotlinx.android.synthetic.main.activity_slash.*

class SlashActivity : BaseActivity(), ViewPropertyAnimatorListener {


    override fun getLayoutid(): Int {
        return R.layout.activity_slash
    }

    override fun initDate() {
        ViewCompat.animate(Image_kabo).scaleX(1f).scaleY(1f).setListener(this).setDuration(2000)
    }

    override fun onAnimationEnd(view: View?) {
        startActivityAndFinish<LoginActivity>()
    }

    override fun onAnimationCancel(view: View?) {

    }

    override fun onAnimationStart(view: View?) {

    }
}
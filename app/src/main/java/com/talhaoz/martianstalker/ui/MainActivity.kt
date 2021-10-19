package com.talhaoz.martianstalker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.content.ContextCompat
import com.talhaoz.martianstalker.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar init
        toolBar.setTitle("Martian Stalker")
        toolBar.overflowIcon = ContextCompat.getDrawable(applicationContext,R.drawable.ic_filter_icon)
        setSupportActionBar(toolBar)

        // tab layout init
        val fragmentAdapter =
            PagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
        // to prevent refetch data everytime tab changed
        viewPager.offscreenPageLimit = 100;


    }

    // filter menu init
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.filter_menu,menu)
        return true
    }



}
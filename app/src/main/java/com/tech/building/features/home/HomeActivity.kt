package com.tech.building.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.features.home.cardcarousel.view.CardCarouselFragment

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private val fragmentManager = supportFragmentManager
    private val fragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupCardCarousel()
    }

    private fun setupCardCarousel() {
        val fragment = CardCarouselFragment.newInstance()
        fragmentTransaction.add(R.id.carouselContainer, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}
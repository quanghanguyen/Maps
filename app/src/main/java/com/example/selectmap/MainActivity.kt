package com.example.selectmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.selectmap.databinding.ActivityMainBinding
import com.example.selectmap.map.MapsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openMap()
    }

    private fun openMap() {
        binding.openMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}
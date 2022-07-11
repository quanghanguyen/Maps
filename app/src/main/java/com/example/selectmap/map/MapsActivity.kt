package com.example.selectmap.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.selectmap.R
import com.example.selectmap.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

internal class MapsActivity :
    AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val hue = LatLng(16.461109, 107.570183)
        val khoa_hoc = LatLng(16.458719908325826, 107.59226512659689)
        val monaco = LatLng(16.48194127564437, 107.60030369996487)

        map.addMarker(
            (MarkerOptions()
                .position(khoa_hoc)
                .title("Sân Khoa Học Huế")
                .snippet("77 Nguyễn Huệ, Phú Nhuận, Thành phố Huế")
                    )
        )

        map.addMarker(
            MarkerOptions()
                .position(monaco)
                .title("Sân Monaco Huế")
                .snippet("FJJ2+M47, Vỹ Dạ, Thành phố Huế")
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(hue, 15f))

        enableMyLocation()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnMarkerClickListener(this)

    }


    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )

        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            return
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        displayCustomWindow(marker)
        return true
    }


    private fun displayCustomWindow(marker: Marker) {
        val transition = Slide(Gravity.BOTTOM)
        transition.addTarget(binding.customWindow)
        TransitionManager.beginDelayedTransition(binding.root, transition)

        binding.customWindow.visibility = View.VISIBLE
        binding.locationName.text = marker.title
        binding.locationAddress.text = marker.snippet
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }
}


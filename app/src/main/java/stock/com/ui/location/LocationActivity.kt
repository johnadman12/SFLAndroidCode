package stock.com.ui.location

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import stock.com.AppBase.BaseActivity
import stock.com.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.LatLng
import android.R.id
import com.google.android.gms.maps.SupportMapFragment



class LocationActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val latitude = 26.857858
        val longitude = 75.824929
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync { googleMap ->
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            val latLng = LatLng(latitude, longitude)
            val marker = googleMap.addMarker(MarkerOptions().position(latLng))
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17.0f)
            googleMap.animateCamera(cameraUpdate)
        }
    }


}

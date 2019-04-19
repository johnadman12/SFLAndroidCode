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
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_location.view.*


class LocationActivity : FragmentActivity() ,OnMapReadyCallback{
    override fun onMapReady(p0: GoogleMap?) {

    }

    private var mMap: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        /*val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment;
          mapFragment.getMapAsync(this);*/




    }


}

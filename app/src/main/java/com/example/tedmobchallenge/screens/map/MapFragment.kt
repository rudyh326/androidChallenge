package com.example.tedmobchallenge.screens.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tedmobchallenge.R
import com.example.tedmobchallenge.databinding.FragmentMapBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView

class MapFragment : Fragment()  {
    private lateinit var googleMap: GoogleMap
    private var huaweiMap: HuaweiMap? = null
    private lateinit var gmsMapView: SupportMapFragment
    private var hmsMapView: MapView? = null

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMapBinding.inflate(inflater)

        if (isGmsAvailable()) {
            binding.mapView.visibility = View.GONE
            gmsMapView = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            gmsMapView.getMapAsync {
                googleMap = it
            }
        } else {
            binding.mapFragment.visibility = View.GONE
            hmsMapView = binding.mapView
            var mapViewBundle: Bundle? = null
            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
            }
            hmsMapView?.apply {
                onCreate(mapViewBundle)
                getMapAsync {
                    huaweiMap = it
                }
            }
        }

        return binding.root
    }

    private fun isGmsAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(requireContext())
        return resultCode == ConnectionResult.SUCCESS
    }

    override fun onStart() {
        super.onStart()
        hmsMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        hmsMapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        hmsMapView?.onDestroy()
    }

    override fun onPause() {
        hmsMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        hmsMapView?.onResume()
    }

}
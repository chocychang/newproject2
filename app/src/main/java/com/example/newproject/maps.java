package com.example.newproject;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class maps extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private static final LatLng ntu = new LatLng(25.021947, 121.535308);
    private static final LatLng supervit = new LatLng(25.025974, 121.544861);
    private static final LatLng ooh = new LatLng(25.023850, 121.543061);
    private static final LatLng salt = new LatLng(25.023674, 121.543236);
    private static final LatLng sood = new LatLng(25.019817, 121.531715);
    private static final LatLng one = new LatLng(25.019265, 121.533544);
    private static final LatLng low = new LatLng(25.014772, 121.533712);

    private Marker msupervit,mooh,msalt,msood,mone,mlow;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        // Add some markers to the map, and add a data object to each marker.
        mMap.addMarker(new MarkerOptions().position(ntu).title("台大體育館"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ntu,13));
        msupervit = mMap.addMarker(new MarkerOptions().position(supervit).title("SuperVit 營養師的光合廚房").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mooh =mMap.addMarker(new MarkerOptions().position(ooh).title("Ooh Cha Cha 自然食科技大樓 & Hooch").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        msalt=mMap.addMarker(new MarkerOptions().position(salt).title("全國食養健康素食自助餐").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        msood=mMap.addMarker(new MarkerOptions().position(sood).title("素德蔬食").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mone=mMap.addMarker(new MarkerOptions().position(one).title("光一肆號").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mlow=mMap.addMarker(new MarkerOptions().position(low).title("Miss energy低GI廚房公館店").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        msupervit.setTag(0);
        mooh.setTag(0);
        msalt.setTag(0);
        msood.setTag(0);
        mone.setTag(1);
        mlow.setTag(0);
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        // Check if a click count was set, then display the click count.
        if (clickCount==0) {
            Toast.makeText(this, marker.getTitle() + " has no activity held ", Toast.LENGTH_SHORT).show();
        }
        else if(clickCount==1){
            int position=0;
            Intent intent=new Intent();
            intent.setClass(maps.this,join.class);
            Bundle bundle=new Bundle();
            bundle.putInt("key",position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}

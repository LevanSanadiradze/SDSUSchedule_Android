package com.systemcorp.sdsu.schedule.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.systemcorp.sdsu.schedule.R;

/**
 * Created by giorgi on 1/17/18.
 */

public class ContactsFragment extends Fragment implements View.OnClickListener {

    private MapView mapView;
    private ConstraintLayout mobile;
    private ConstraintLayout office;
    private ConstraintLayout cell;
    private ConstraintLayout direction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mobile = (ConstraintLayout) view.findViewById(R.id.mobile);
        office = (ConstraintLayout) view.findViewById(R.id.office);
        cell = (ConstraintLayout) view.findViewById(R.id.cell);
        direction = (ConstraintLayout) view.findViewById(R.id.direction);

        mobile.setOnClickListener(this);
        office.setOnClickListener(this);
        cell.setOnClickListener(this);
        direction.setOnClickListener(this);

        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                MapsInitializer.initialize(getActivity());

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.704794, 44.790047)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.704794, 44.790047), 17));
            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mobile:
                Intent mobileIntent = new Intent(Intent.ACTION_DIAL);
                mobileIntent.setData(Uri.parse("tel:" + "+995 32 2 311 611"));
                startActivity(mobileIntent);
                break;
            case R.id.office:
                Intent officeIntent = new Intent(Intent.ACTION_SENDTO);
                officeIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                officeIntent.putExtra(Intent.EXTRA_EMAIL, "georgiainfo@mail.sdsu.edu");
                startActivity(officeIntent);
                break;
            case R.id.cell:
                Intent cellIntent = new Intent(Intent.ACTION_DIAL);
                cellIntent.setData(Uri.parse("tel:" + "+995 593 498 512"));
                startActivity(cellIntent);
                break;
            case R.id.direction:
                Intent directionIntent = new Intent(Intent.ACTION_VIEW);
                directionIntent.setData(Uri.parse("geo:" + "41.704794," + "44.790047" + "?q=41.704794,44.790047"));//todo add correct latlng
                startActivity(directionIntent);
                break;
        }
    }
}

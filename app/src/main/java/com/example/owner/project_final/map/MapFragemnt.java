package com.example.owner.project_final.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.owner.project_final.L;
import com.example.owner.project_final.R;
import com.example.owner.project_final.firebase.PublicVariable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Administrator on 2018-05-11.
 */

public class MapFragemnt extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private ViewGroup mMapView;
    private ViewGroup mMarkerView;
    private boolean mCurrentlyTouching;
    private Bundle mCurLocationExtras;
    private Location mCurLocation;
    private TextView mStoreNameTextView;
    private TextView mAdressTextView;
    private ScrollView mScrollView;

    public static MapFragemnt getInstance() {
        MapFragemnt fragment = new MapFragemnt();
        return fragment;
    }

    public void setScrollView(ScrollView scrollView) {
        L.e("setScrollView : " + scrollView);
        mScrollView = scrollView;
    }

    public void onMapLoad() {
        if (mGoogleMap != null) onMapReady(mGoogleMap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        L.w("::::::::::::::::: onMapReady : " + googleMap);
        if (googleMap == null) return;
        setupMap(googleMap);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurLocationExtras = getArguments();
        setMarkerView();
    }

    public void setMarkerView() {
        mMarkerView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.map_marker, null);
//        mStoreNameTextView = (TextView) mMarkerView.findViewById(R.id.tv_address);
//        mAdressTextView = (TextView) mMarkerView.findViewById(R.id.tv_address_sub);

    }

    public void onMapUpdate() {
        L.e(">>>>>mGoogleMap = " + mGoogleMap);
        if (mGoogleMap != null) {
            onMapReady(mGoogleMap);
        }
    }


    private void setupMap(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            L.i("permission input");
            mGoogleMap.setMyLocationEnabled(true);
            if (mCurLocationExtras != null) {
                Double lat = mCurLocationExtras.getDouble(PublicVariable.LATITUDE);
                Double lng = mCurLocationExtras.getDouble(PublicVariable.LONGITUDE);


                LatLng pos = new LatLng(lat, lng);
                mGoogleMap.clear();
                MarkerOptions options = new MarkerOptions();
                options.position(pos);
//                options.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), mMarkerView)));
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker pinnedMarker = mGoogleMap.addMarker(options);

                CameraPosition position = new CameraPosition.Builder().target(pos)
                        .zoom(15f)
                        .bearing(0)
                        .build();

                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), Math.max(30, 1), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        L.e(":::::::::::::::Camera Move Finish");
                    }

                    @Override
                    public void onCancel() {
                    }
                });

                dropPinEffect(pinnedMarker);

            } else {
               if(getLocation() == null) return;
                LatLng pos = new LatLng(getLocation().getLatitude(), getLocation().getLongitude());
                MarkerOptions options = new MarkerOptions();
                options.position(pos);
                options.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), mMarkerView)));
                mGoogleMap.addMarker(options);

                CameraPosition position = new CameraPosition.Builder().target(new LatLng(getLocation().getLatitude(), getLocation().getLongitude()))
                        .zoom(16f)
                        .bearing(0)
                        .build();

                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), Math.max(30, 1), null);
            }
        } else {
            L.e("error");
        }
    }

    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return lastKnownLocationGPS;
                } else {
                    Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    return loc;
                }
            } else {
                return null;
            }
        }
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(":::::::::::mapfragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (ViewGroup) view.findViewById(R.id.container);

        TouchableWrapper frameLayout = new TouchableWrapper(getActivity());
        frameLayout.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), android.R.color.transparent));

        frameLayout.addView(mMapView);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mSupportMapFragment = SupportMapFragment.newInstance();
        mSupportMapFragment.getMapAsync(this);
        fragmentTransaction.add(R.id.map, mSupportMapFragment);
        fragmentTransaction.commit();

        return frameLayout;
    }

    @Override
    public void onMapLoaded() {
        L.e("::onMapLoaded");
    }


    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 3f * t);

                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();

                }
            }
        });
    }


    public class TouchableWrapper extends FrameLayout {

        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mScrollView != null) mScrollView.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    if (mScrollView != null) mScrollView.requestDisallowInterceptTouchEvent(true);
                    break;
            }
            return super.dispatchTouchEvent(event);
        }
    }

}

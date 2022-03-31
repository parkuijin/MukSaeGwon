package com.cookandriod.muksaegwon;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment {

    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mContext = getActivity();

        MapView mapView = new MapView(mContext);

        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);
        return v;
    }
}
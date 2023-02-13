package com.trackerimmo.geocoder;

import java.util.List;

public interface Search {

	interface SearchCallback {

        void onSuccess(Location location);

        void onFailure(Throwable e);

    }

    List<Location> getLocations(String q, SearchCallback callback);
}

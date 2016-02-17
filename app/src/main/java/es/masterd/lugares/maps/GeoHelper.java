package es.masterd.lugares.maps;

import com.google.android.maps.GeoPoint;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class GeoHelper {

    public static List<GeoPoint> findMarker(GeoPoint topleft, GeoPoint bottomright, int zoomlevel) {
        List<GeoPoint> marker = new LinkedList<GeoPoint>();
       if (zoomlevel == 0 || zoomlevel == 1)
            marker.addAll(Arrays.asList(geopoint));
        else {
            if (topleft.getLongitudeE6() > bottomright.getLongitudeE6()) {
                for (int i = 0; i < geopoint.length; i++) {
                    GeoPoint p = geopoint[i];
                    if ((p.getLongitudeE6() > topleft.getLongitudeE6() || p.getLongitudeE6() < bottomright.getLongitudeE6())
                            && p.getLatitudeE6() < topleft.getLatitudeE6() && p.getLatitudeE6() > bottomright.getLatitudeE6()) {
                        marker.add(p);
                    }
                }
            } else {
                for (int i = 0; i < geopoint.length; i++) {
                    GeoPoint p = geopoint[i];
                    if (p.getLongitudeE6() > topleft.getLongitudeE6() && p.getLatitudeE6() < topleft.getLatitudeE6()
                            && p.getLongitudeE6() < bottomright.getLongitudeE6() && p.getLatitudeE6() > bottomright.getLatitudeE6()) {
                        marker.add(p);
                    }
                }
            }
        }
        return marker;
    }

    public static GeoPoint[] geopoint = {
            new GeoPoint(40420088, -3688810),
            new GeoPoint(48858807, 2294254),
            new GeoPoint(41890358, 12492732),
            new GeoPoint(51500814, -0124646)
            

    };

}

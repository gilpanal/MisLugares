package es.masterd.lugares.maps;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import de.android1.overlaymanager.ManagedOverlay;
import de.android1.overlaymanager.ManagedOverlayGestureDetector;
import de.android1.overlaymanager.ManagedOverlayItem;
import de.android1.overlaymanager.OverlayManager;
import de.android1.overlaymanager.ZoomEvent;
import es.masterd.lugares.EditarLugarActivity;
import es.masterd.lugares.R;

public class MapaLugaresActivity extends MapActivity {

	List<Overlay> mapOverlays;
	MiItemizedOverlay itemizedoverlay;
	MapView mapView; // Objeto mapview
	MapController mapController;
	GeoPoint p;
	OverlayManager overlayManager;// Necesaria la libreria OverlaManager



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);



		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();

		overlayManager = new OverlayManager(getApplication(), mapView);


		//Marcamos los puntos en el mapa

		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		itemizedoverlay = new MiItemizedOverlay(this, drawable);
		itemizedoverlay.addLocalizacion(40.420088, -3.688810,"Madrid");
		itemizedoverlay.addLocalizacion(48.858807, 2.294254,"Paris");
		itemizedoverlay.addLocalizacion(41.89041, 12.492431,"Roma");
		itemizedoverlay.addLocalizacion(51.500814, -0.124646,"Londres");
		mapOverlays.clear();
		mapOverlays.add(itemizedoverlay);

	}

	@Override
	public void onWindowFocusChanged(boolean b) {

		createOverlayWithListener();

	}

	public void createOverlayWithListener() {

		final ManagedOverlay managedOverlay = overlayManager.createOverlay(	"listenerOverlay", getResources().getDrawable(R.drawable.marker));
		/*for (int i = 0; i<GeoHelper.geopoint.length ; i++) {
			managedOverlay.createItem(GeoHelper.geopoint[i], "Item"+i);
		}*/
		managedOverlay.setOnOverlayGestureListener(new ManagedOverlayGestureDetector.OnOverlayGestureListener() {

					public boolean onZoom(ZoomEvent zoom, ManagedOverlay overlay) {
						return false;
					}

					// Al hacer doble click añade un marcador y permite Editar
					// un lugar
					public boolean onDoubleTap(MotionEvent e, ManagedOverlay overlay, GeoPoint point, ManagedOverlayItem item) {

						Drawable defaultmarker = getResources().getDrawable(R.drawable.marker);

						ManagedOverlay managedOverlay = overlayManager.createOverlay(defaultmarker);

						managedOverlay.createItem(point);

						// Registra el ManagedOverlayer en el MapView
						overlayManager.populate();

						Toast.makeText(getApplicationContext(),"¡Has creado un marcador!", Toast.LENGTH_LONG).show();

						Intent mapalugares = new Intent(MapaLugaresActivity.this, EditarLugarActivity.class);
						startActivity(mapalugares);

						return true;
					}

					public void onLongPress(MotionEvent arg0,
							ManagedOverlay arg1) {
						// TODO Auto-generated method stub

					}

					public void onLongPressFinished(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub

					}

					public boolean onScrolled(MotionEvent arg0,
							MotionEvent arg1, float arg2, float arg3,
							ManagedOverlay arg4) {
						// TODO Auto-generated method stub
						return false;
					}

					public boolean onSingleTap(MotionEvent arg0,
							ManagedOverlay arg1, GeoPoint arg2,
							ManagedOverlayItem arg3) {
						// TODO Auto-generated method stub
						return false;
					}

				});
		overlayManager.populate();
	}


    public class MiItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	    private Context context;
	    public MiItemizedOverlay(Context context,Drawable defaultMarker) {
	    	super(boundCenterBottom(defaultMarker));
	    	this.context = context;
	    }

	    public void addLocalizacion(double lat,double lon, String etiqueta) {
		    int lt = (int) (lat * 1E6);
		    int ln = (int) (lon * 1E6);
		    GeoPoint punto = new GeoPoint(lt, ln);
		    OverlayItem item = new OverlayItem(punto, etiqueta, null);
		    mOverlays.add(item);
		    populate();


	    }

	    public void clear() {
		    mOverlays.clear();
		    populate();
	    }


	    @Override
	    protected OverlayItem createItem(int i) {

	    	return mOverlays.get(i);
	    }

	    @Override
	    public int size() {

	    	return mOverlays.size();
	    }

	    @Override
	    protected boolean onTap(int index) {
		    String etiqueta = mOverlays.get(index).getTitle();
		    Toast.makeText(context, etiqueta, Toast.LENGTH_LONG).show();



		    return false;
	    }

	 /*// Cuando tocamos una vez la pantalla muestra las coordenadas
		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) {

			// ---al levantar el usuario el dedo---
			if (event.getAction() == 1) {
				GeoPoint p = mapView.getProjection().fromPixels(
						(int) event.getX(), (int) event.getY());
				Toast.makeText(
						getBaseContext(),
						p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6()
								/ 1E6, Toast.LENGTH_SHORT).show();
				 
			}
			return false;
		}*/
    }

	@Override
	protected boolean isRouteDisplayed() {


		return false;
	}
}
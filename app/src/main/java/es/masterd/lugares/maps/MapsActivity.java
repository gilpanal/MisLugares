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


public class MapsActivity extends MapActivity {
	
	private MapView mapa;
	private MapController mapController;
	private List<Overlay> mapOverlays;
	private MiItemizedOverlay itemizedoverlay;
	private OverlayManager overlayManager;
	
	
	// Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        setContentView(R.layout.mapview);
        
        // Configure the map
        mapa = (MapView) findViewById(R.id.mapview);
        mapa.displayZoomControls(true);
        mapa.setBuiltInZoomControls(true);
        mapController = mapa.getController();
        mapController.setZoom(2); // Zoom x2
        mapa.setSatellite(true); // Activamos la vista satelite

        overlayManager = new OverlayManager(getApplication(), mapa);
        
        
    }
   
    @Override
	public void onWindowFocusChanged(boolean b) {

		createOverlayWithListener();

	}

	public void createOverlayWithListener() {

		final ManagedOverlay managedOverlay = overlayManager.createOverlay("listenerOverlay", getResources().getDrawable(R.drawable.marker));
		
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

						Toast.makeText(getApplicationContext(), "¡Has creado un marcador!", Toast.LENGTH_LONG).show();

						Intent mapalugares = new Intent(MapsActivity.this, EditarLugarActivity.class);
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
        public boolean onTouchEvent(MotionEvent event, MapView mapa)
        {   
            //---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapa.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                addLocalizacion(p.getLatitudeE6()/1E6,p.getLongitudeE6()/1E6,"");
              
            }                            
            return false;
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
	    
	
	    
	  
    }
    
    
	@Override
	protected boolean isRouteDisplayed() {
		
		
		
		
		//Marcamos unos puntos en el mapa
		
		mapOverlays = mapa.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		itemizedoverlay = new MiItemizedOverlay(this, drawable);
		itemizedoverlay.addLocalizacion(40.420088, -3.688810,"Madrid");
		itemizedoverlay.addLocalizacion(48.858807, 2.294254,"Paris");
		itemizedoverlay.addLocalizacion(41.89041, 12.492431,"Roma");
		itemizedoverlay.addLocalizacion(51.500814, -0.124646,"Londres");
		mapOverlays.clear();
		mapOverlays.add(itemizedoverlay);
		
		return false;
	}
	
}
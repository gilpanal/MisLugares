package es.masterd.lugares;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import es.masterd.lugares.maps.MapaLugaresActivity;

public class PrincipalActivity extends Activity {
	
	private static final int DIALOG_ABOUT = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Al presionar el boton Lista despliega en una ventan los lugares almacenados
		 Button botonLista = (Button) findViewById(R.id.botonlista);
	        botonLista.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent listalugares = new Intent(PrincipalActivity.this, ListaLugaresActivity.class);
					startActivity(listalugares);


				}
			});
		
        
        //Al presionar el boton Mapa despliega un mapa interactivo
        Button botonMapa = (Button) findViewById(R.id.botonmapa);
        botonMapa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent mapalugares = new Intent(PrincipalActivity.this, MapaLugaresActivity.class);
				startActivity(mapalugares);
				
				
			}
		});

		//Al presionar el boton Mapa despliega un mapa interactivo
		Button botonNewMapa = (Button) findViewById(R.id.botonNewMapa);
		botonNewMapa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent newmapalugares = new Intent(PrincipalActivity.this, MapaLugaresActivity.class);
				startActivity(newmapalugares);


			}
		});

	}
	
	/**
	 * Cargamos el menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menupricipal, menu);
		return true;
	}

	/**
	 * Respuesta a los eventos de menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menuAcercaDe:
			showDialog(DIALOG_ABOUT);
			return true;
		case R.id.menuQuit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Controla los dialogos emergentes
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ABOUT:
			AlertDialog dialogAbout;
			final AlertDialog.Builder builder;

			LayoutInflater li = LayoutInflater.from(this);
			View view = li.inflate(R.layout.acercade, null);

			builder = new AlertDialog.Builder(this).setIcon(R.drawable.todo).setTitle(getString(R.string.app_name)).setPositiveButton("Ok", null).setView(view);

			dialogAbout = builder.create();

			return dialogAbout;
		default:
			return null;
		}
	}
}
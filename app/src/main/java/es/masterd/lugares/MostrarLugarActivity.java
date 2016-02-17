package es.masterd.lugares;

import es.masterd.lugares.db.LugaresDBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MostrarLugarActivity extends Activity {
	
	private TextView mTitleText;//Nombre del lugar
	private TextView mBodyText;//Descrpcion
	private TextView mLatitudText;
	private TextView mLongitudText;//Comentario de la foto
	private TextView mImageText;

	private Long mRowId;
	private LugaresDBAdapter mDbHelper;
	
	


	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new LugaresDBAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.mostrarlugar);
		
		mTitleText = (TextView) findViewById(R.id.show_edit_summary);
		mBodyText = (TextView) findViewById(R.id.show_edit_description);
		mLatitudText = (TextView) findViewById(R.id.show_edit_latitud);
		mLongitudText = (TextView) findViewById(R.id.show_edit_longitud);		
		mImageText = (TextView) findViewById(R.id.show_edit_image);
		
		
		Button confirmButton = (Button) findViewById(R.id.show_edit_button);
		
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle
				.getSerializable(LugaresDBAdapter.KEY_ROWID);
		if (extras != null) {
			mRowId = extras.getLong(LugaresDBAdapter.KEY_ROWID);
		}
		
		
		Cursor todo = mDbHelper.fetchTodo(mRowId);
		String image = todo.getString(todo
				.getColumnIndexOrThrow(LugaresDBAdapter.KEY_IMAGE)) ;
		
		//Colocando Imagen en el ImageView 
		ImageView paper = (ImageView)findViewById(R.id.foto); 
		Resources res = getResources(); 
		int id = getResources().getIdentifier(image, "drawable", getPackageName()); 
		Drawable drawable = res.getDrawable(id); 
		paper.setImageDrawable(drawable);
		
		
		//Si se pulsa el boton pasamos al menu Editar
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent editarLugar = new Intent(MostrarLugarActivity.this, EditarLugarActivity.class);
				editarLugar.putExtra(LugaresDBAdapter.KEY_ROWID, mRowId);//Pasamos la posicion el la lista
				startActivity(editarLugar);
				setResult(RESULT_OK);
				finish();
			}

		});
		
	}
	
	//Si se pulsa la imagen pasa a un menu para seleccionar otra
	public void onClickSearch (View image){
		
		
		Intent editarImagen = new Intent(MostrarLugarActivity.this, SeleccionarImagen.class);
		startActivity(editarImagen);
    	
	}
	
	//Rellena los campos para que se muestren
	private void populateFields() {
		if (mRowId != null) {
			Cursor todo = mDbHelper.fetchTodo(mRowId);
			startManagingCursor(todo);
			
			mTitleText.setText(todo.getString(todo
					.getColumnIndexOrThrow(LugaresDBAdapter.KEY_SUMMARY)));
			mBodyText.setText(todo.getString(todo
					.getColumnIndexOrThrow(LugaresDBAdapter.KEY_DESCRIPTION)));
			mLatitudText.setText(todo.getString(todo
					.getColumnIndexOrThrow(LugaresDBAdapter.KEY_LATITUD)));
			mLongitudText.setText(todo.getString(todo
					.getColumnIndexOrThrow(LugaresDBAdapter.KEY_LONGITUD)));
			mImageText.setText(todo.getString(todo
					.getColumnIndexOrThrow(LugaresDBAdapter.KEY_IMAGE)));
			
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(LugaresDBAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}
	
	//Guarda el estado del registro
	private void saveState() {
		
		String summary = mTitleText.getText().toString();
		String description = mBodyText.getText().toString();
		String latitud = mLatitudText.getText().toString();
		String longitud = mLongitudText.getText().toString();
		String image = mImageText.getText().toString();
		

		if (mRowId == null) {
			long id = mDbHelper.createTodo(summary, description, latitud, longitud, image);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateTodo(mRowId, summary, description, latitud, longitud, image);
		}
	}
}
package es.masterd.lugares;


import es.masterd.lugares.db.LugaresDBAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;




public class ListaLugaresActivity extends ListActivity {
	private LugaresDBAdapter dbHelper;
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;//2
	private Cursor cursor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_list);
		this.getListView().setDividerHeight(2);//2
		dbHelper = new LugaresDBAdapter(this);
		dbHelper.open();
		fillData();
		registerForContextMenu(getListView());
	}

	// Crea el menu que permite añadir un lugar desde la lista
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, menu);
		return true;
	}

	// Reaccion al selecionar un item
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.insert:
			createTodo();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.insert:
			createTodo();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			dbHelper.deleteTodo(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void createTodo() {
		Intent i = new Intent(this, EditarLugarActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	// Al pulsar en un lugar abre la actividad MostrarLugarActivity
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, MostrarLugarActivity.class);
		i.putExtra(LugaresDBAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();

	}
	
	//Muestra el nombre del lugar en la lista
	private void fillData() {
		cursor = dbHelper.fetchAllTodos();
		startManagingCursor(cursor);
		/*
		// Mapeamos las querys SQL a los campos de las vistas
		String[] camposDb = new String[] { Lugares.LUGAR, Lugares.DESCRIPCION,};
		int[] camposView = new int[] {android.R.id.text1, android.R.id.text2};

		
		 * Paso 3: creamos el Adapter
		 

		// Con los objetos anteriores creamos el adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2, cursor,
				camposDb, camposView);

		
		
		 * Paso 5: Finalmente asociamos el adapter con esta activity
		 
		setListAdapter(adapter);
*/
		
		
		
		String[] from = new String[] { LugaresDBAdapter.KEY_SUMMARY, LugaresDBAdapter.KEY_DESCRIPTION };
		int[] to = new int[] {android.R.id.text1, android.R.id.text2  };

		
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2, cursor, from, to);
		setListAdapter(notes);
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);//Eliminar lugar
	}
}

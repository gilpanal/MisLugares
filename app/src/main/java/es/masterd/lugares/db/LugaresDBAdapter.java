package es.masterd.lugares.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;




public class LugaresDBAdapter {

	// Campos de la Base de Datos
	public static final String KEY_ROWID = "_id";
	
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_LATITUD = "latitud";
	public static final String KEY_LONGITUD = "longitud";
	public static final String KEY_IMAGE = "image";

	private static final String DATABASE_TABLE = "todo";
	
	private Context context;
	private SQLiteDatabase database;
	private LugaresDBHelper dbHelper;

	public LugaresDBAdapter(Context context) {
		this.context = context;
	}

	public LugaresDBAdapter open() throws SQLException {
		dbHelper = new LugaresDBHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Crea un nuevo lugar si se ha creado correctamente : Devuelve la ID
	 * si no devuelve -1 para indicar error.
	 */
	public long createTodo(String summary, String description, String latitud, String longitud, String image) {
		ContentValues initialValues = createContentValues(summary,
				description, latitud, longitud, image);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Actualiza el lugar
	 */
	public boolean updateTodo(long rowId, String summary,
			String description, String latitud, String longitud, String image) {
		ContentValues updateValues = createContentValues(summary,
				description, latitud, longitud, image);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * Elimina el lugar
	 */
	public boolean deleteTodo(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Devuelve un Cursor del lugar sobre la lista en la base de datos
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllTodos() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_SUMMARY, KEY_DESCRIPTION,KEY_LATITUD, KEY_LONGITUD, KEY_IMAGE }, null, null, null,
				null, null);
	}

	/**
	 * Devuelve un Cursor posicionado en el lugar definido
	 */
	public Cursor fetchTodo(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_SUMMARY, KEY_DESCRIPTION, KEY_LATITUD, KEY_LONGITUD, KEY_IMAGE },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String summary,
			String description, String latitud, String longitud, String image) {
		ContentValues values = new ContentValues();
		
		values.put(KEY_SUMMARY, summary);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_LATITUD, latitud);
		values.put(KEY_LONGITUD, longitud);
		values.put(KEY_IMAGE, image);
		
		return values;
	}
}

package es.masterd.lugares.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LugaresDBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "sitiosemblemiticos";

	private static final int DATABASE_VERSION = 1;

	// Declaracion de la base de datos SQL
	private static final String DATABASE_CREATE = "create table todo (_id integer primary key autoincrement, "
			+ "summary text not null, description text not null, latitud float,longitud float, image text not null);";

	public LugaresDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creacion de la base de datos
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL("INSERT INTO todo(summary,description,latitud,longitud,image) VALUES ('Puerta de Alcalá','España',40.420088,-3.688810,'madrid')");
		database.execSQL("INSERT INTO todo(summary,description,latitud,longitud,image) VALUES ('Torre Eiffel','Francia',48.858807,2.294254,'paris')");
		database.execSQL("INSERT INTO todo(summary,description,latitud,longitud,image) VALUES ('Coliseo','Italia',41.89041,12.492431,'roma')");
		database.execSQL("INSERT INTO todo(summary,description,latitud,longitud,image) VALUES ('Big Ben','Inglaterra',51.500814,-0.124646,'londres')");
	}

	// Actualizacion de la base de datos. Crea nueva version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(LugaresDBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(database);
	}
}
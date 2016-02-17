package es.masterd.lugares;

import es.masterd.lugares.db.LugaresDBAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class EditarLugarActivity extends Activity {
	private EditText mTitleText;//Nombre del lugar
	private EditText mBodyText;//Descrpcion
	private TextView mLatitudText;
	private TextView mLongitudText;
	private EditText mImageText;

	private Long mRowId;
	private LugaresDBAdapter mDbHelper;
	

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new LugaresDBAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.todo_edit);
		mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
		mBodyText = (EditText) findViewById(R.id.todo_edit_description);
		mLatitudText = (TextView) findViewById(R.id.todo_edit_latitud);
		mLongitudText = (TextView) findViewById(R.id.todo_edit_longitud);
		mImageText = (EditText) findViewById(R.id.todo_edit_image);

		Button confirmButton = (Button) findViewById(R.id.todo_edit_button);
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle
				.getSerializable(LugaresDBAdapter.KEY_ROWID);
		if (extras != null) {
			mRowId = extras.getLong(LugaresDBAdapter.KEY_ROWID);
		}
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});
	}

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

	private void saveState() {
		
		String summary = mTitleText.getText().toString();
		String description = mBodyText.getText().toString();
		String latitud = mLatitudText.getText().toString();
		String longitud = mLongitudText.getText().toString();
		String image = mImageText.getText().toString();
		

		if (mRowId == null) {
			long id = mDbHelper.createTodo(summary, description, latitud, longitud, "earth");
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateTodo(mRowId, summary, description, latitud, longitud, image);
		}
	}
}
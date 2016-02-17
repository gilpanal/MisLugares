package es.masterd.lugares;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;



public class SeleccionarImagen extends Activity {

	private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private String filemanagerstring;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
        
        finish();
        
    }

  
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                //OI FILE Manager
                filemanagerstring = selectedImageUri.getPath();

                //MEDIA GALLERY
                selectedImagePath = getPath(selectedImageUri);

                //DEBUG PURPOSE 
                if(selectedImagePath!=null)
                    System.out.println(selectedImagePath);
                else System.out.println("Imagen nula");
                if(filemanagerstring!=null)
                    System.out.println(filemanagerstring);
                else System.out.println("Cadena nula");

                //STRING
                if(selectedImagePath!=null)
                    System.out.println("Imagen Ok!");
                else
                    System.out.println("Cadena Ok!");
            }
        }
    }
    public String getPath(Uri uri) {
        String selectedImagePath;
        //1:MEDIA GALLERY --- query de MediaStore.Images.Media.DATA
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            selectedImagePath = cursor.getString(column_index);
        }else{
            selectedImagePath = null;
        }

        if(selectedImagePath == null){
            //2:OI FILE Manager --- llama al metodo: uri.getPath()
            selectedImagePath = uri.getPath();
        }
        return selectedImagePath;
    }
}
  










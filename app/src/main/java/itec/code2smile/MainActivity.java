package itec.code2smile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends Activity {

    private final static String kAName = "Album";
    private final static int ACTIVITY_RESULT = 2;
    private Uri imageUri;
    private Integer albumIndex;
    private String TAG = "MainActivity";
    private ArrayList<Album> albumList;
    private Set<String> albumNames;
    private CustomAdapter cstAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        albumList = new ArrayList<Album>();
        albumNames = new HashSet<String>();
        albumIndex = Singleton.mySettings.getAlbumIndex();
        Log.d("Album index",albumIndex.toString());
        final ListView listView = (ListView)findViewById(R.id.list_view);

        Button take_btn = (Button)findViewById(R.id.take_picture);
        take_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent("android.media.action.IMAGE_CAPTURE");

                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state))
                {
                    Log.d("State",state);
                    Log.d("File created:",Singleton.m_szWorkDir);
                    File photoFile = new File(Singleton.m_szPictDir,"photo.jpg");
                    mIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
                    imageUri = Uri.fromFile(photoFile);
                }

                startActivityForResult(mIntent,ACTIVITY_RESULT);
            }
        });

        Button gallery = (Button) findViewById(R.id.open_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(getBaseContext(),GalleryActivity.class);
                startActivity(galleryIntent);

            }
        });

        Button create_album = (Button)findViewById(R.id.create_album);
        create_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"create_album");
                albumIndex = Singleton.mySettings.getAlbumIndex();

                Log.d(TAG,"albumIndex:"+albumIndex.toString());

                String albumName = kAName + albumIndex.toString();
                Album newAlbum = new Album(albumIndex,albumName,null);
                albumList.add(newAlbum);
                Singleton.mySettings.incrementAlbumIndex(albumIndex+1);
                albumIndex = Singleton.mySettings.getAlbumIndex();
                Log.d(TAG,"newAlbumIndex:"+albumIndex.toString());

                for(Album i :albumList){
                    albumNames.add(i.getName());
                }
                Singleton.mySettings.writeAlbumNames(albumNames);
                Log.d(TAG,Singleton.mySettings.getAlbumNames().toString());
                //listView.addView();
            }
        });

        Button delete_album = (Button)findViewById(R.id.delete_album);
        delete_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"delete_album");
            }
        });

        ArrayList<String> justToTest = new ArrayList<String>();
        String alb = new String("dsada");
        justToTest.add(alb);
        justToTest.add(alb);
        justToTest.add(alb);

        //populate the listview

        for(int i=0;i<10;i++)
        {
            Album my_Album;
            int itemid = 100 + i;
            my_Album = new Album(itemid,"Values",justToTest);
            albumList.add(my_Album);
        }

        cstAdapter = new CustomAdapter(getBaseContext(), albumList);

        listView.setAdapter(cstAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACTIVITY_RESULT)
            if(resultCode==RESULT_OK)
            {
                Uri selectedImageUri = imageUri;

                try {

                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap myBitmap = BitmapFactory.decodeStream(inputStream, null, options);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.d("NullPointerException",""+e.getMessage());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d("FileNotFoundException",""+e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Exception",""+e.getMessage());
                }
            }
    }

}

package itec.code2smile;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private final static int ACTIVITY_RESULT = 2;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button take_btn = (Button)findViewById(R.id.take_picture);

        take_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent("android.media.action.IMAGE_CAPTURE");

                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state))
                {
                    Log.d("State",state);
                    File photoFile = new File(Environment.getExternalStorageDirectory(),"photo.jpg");
                    mIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
                    imageUri = Uri.fromFile(photoFile);
                }

                startActivityForResult(mIntent,ACTIVITY_RESULT);
            }
        });
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

                    ImageView img = (ImageView)findViewById(R.id.image_view);

                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap myBitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    img.setImageBitmap(myBitmap);

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

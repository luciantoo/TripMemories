package itec.code2smile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by Sined on 4/12/2014.
 */
public class GalleryActivity extends Activity {

    private final GalleryActivity GA = this;

    private final int PICKER = 1;

    private final String TAG = "Gallery activity";

    private final static String INTENT_EXTRA_TAG = "albumName";

    private static int count=0;

    private String albumName;

    private File photoFile = new File("photo.jpg");

    private LinkedList<String> pictureNames;

    private int currentPic = 0;

    private ProgressDialog progressDialog;

    private Gallery picGallery;

    private ImageView picView;

    private PicAdapter imgAdapt;

    private Button take_button;

    public class PicAdapter extends BaseAdapter {

        int defaultItemBackground;

        private Context galleryContext;

        private LinkedList<Bitmap> imageBitmaps;

        Bitmap placeholder;

        public PicAdapter(Context c) {

            galleryContext = c;

            imageBitmaps = new LinkedList<Bitmap>();

            File dir = new File(Singleton.m_szPictDir);
            File[] dirListing = dir.listFiles();
            if (dirListing != null) {

                count=0;

                for (File pict : dirListing){
                    if(!albumName.equals("gallery")){
                        String tag = pict.getName().toString();
                        if(tag.length()==19){
                            tag = tag.substring(0,7);
                        }else{
                            tag = tag.substring(0,6);
                        }

                        Log.d("Iteration","count "+count);
                        Log.d("SUBSTRING",""+tag);



                        Log.d(TAG,tag+"_"+albumName);
                        if (albumName.equals(tag)) {
                            Log.d(TAG, pict.getName().toString());
                            pictureNames.add(pict.getName().toString());

                            File pFile = new File(Singleton.m_szPictDir, pict.getName().toString());
                            Uri uri = Uri.fromFile(pFile);
                            String imgPath = uri.getPath();
                            if (uri != null) {
                                int targetWidth = 600;
                                int targetHeight = 400;

                                BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                                bmpOptions.inJustDecodeBounds = true;
                                BitmapFactory.decodeFile(imgPath, bmpOptions);

                                int currHeight = bmpOptions.outHeight;
                                int currWidth = bmpOptions.outWidth;

                                int sampleSize = 1;

                                if (currHeight > targetHeight || currWidth > targetWidth) {
                                    //use either width or height
                                    if (currWidth > currHeight)
                                        sampleSize = Math.round((float) currHeight / (float) targetHeight);
                                    else
                                        sampleSize = Math.round((float) currWidth / (float) targetWidth);
                                }

                                bmpOptions.inSampleSize = sampleSize;
                                bmpOptions.inJustDecodeBounds = false;
                                placeholder = BitmapFactory.decodeFile(imgPath, bmpOptions);
                                imageBitmaps.add(placeholder);
                                count++;
                            }
                        }

                    }
                    else{
                        Log.d(TAG, pict.getName().toString());
                        pictureNames.add(pict.getName().toString());
                        File pFile = new File(Singleton.m_szPictDir, pict.getName().toString());
                        Uri uri = Uri.fromFile(pFile);
                        String imgPath = uri.getPath();
                        if (uri != null) {
                            int targetWidth = 600;
                            int targetHeight = 400;

                            BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                            bmpOptions.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(imgPath, bmpOptions);

                            int currHeight = bmpOptions.outHeight;
                            int currWidth = bmpOptions.outWidth;

                            int sampleSize = 1;

                            if (currHeight > targetHeight || currWidth > targetWidth) {
                                //use either width or height
                                if (currWidth > currHeight)
                                    sampleSize = Math.round((float) currHeight / (float) targetHeight);
                                else
                                    sampleSize = Math.round((float) currWidth / (float) targetWidth);
                            }

                            bmpOptions.inSampleSize = sampleSize;
                            bmpOptions.inJustDecodeBounds = false;
                            placeholder = BitmapFactory.decodeFile(imgPath, bmpOptions);
                            imageBitmaps.add(placeholder);
                            count++;
                        }

                    }
                }
            }


            TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);

            defaultItemBackground = styleAttrs.getResourceId(
                    R.styleable.PicGallery_android_galleryItemBackground, 0);

            styleAttrs.recycle();
        }

        @Override
        public int getCount() {
            return imageBitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(galleryContext);
            imageView.setImageBitmap(imageBitmaps.get(position));
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundResource(defaultItemBackground);
            return imageView;
        }

        public void addPic(Bitmap newPic) {
            imageBitmaps.add(newPic);
        }

        public Bitmap getPic(int posn) {
            return imageBitmaps.get(posn);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        pictureNames = new LinkedList<String>();

        Intent receivedIntent = getIntent();
        albumName = receivedIntent.getStringExtra(INTENT_EXTRA_TAG);

        picView = (ImageView) findViewById(R.id.picture);

        picGallery = (Gallery) findViewById(R.id.gallery);

        imgAdapt = new PicAdapter(this);

        picGallery.setAdapter(imgAdapt);

        picGallery.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                File photo = new File(Singleton.m_szPictDir,pictureNames.get(position));

                String fileName = pictureNames.get(position);
                char a_char = fileName.charAt(13);

                Log.d("a_char",""+a_char);
                Log.d("int_a_char",""+(int)a_char);

                int val = (int)a_char - 48 + 1;
                String newFileName = fileName.substring(0,12)+val+".jpg";

                File newFile = new File(Singleton.m_szPictDir,newFileName);

                photo.renameTo(newFile);

                Toast.makeText(getApplicationContext(),
                        "Selected image has been transfered!", Toast.LENGTH_LONG)
                        .show();

                imgAdapt.notifyDataSetChanged();

                return true;
            }
        });

        picGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                picView.setImageBitmap(imgAdapt.getPic(position));
                imgAdapt.notifyDataSetChanged();
                picView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                Integer poz = new Integer(position);
                Log.d(TAG, "Tapped @ position " + poz.toString());

            }
        });

        take_button = (Button)findViewById(R.id.take_pic);
        take_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent("android.media.action.IMAGE_CAPTURE");

                count++;

                String state = Environment.getExternalStorageState();
                Log.d("State", state);
                Log.d("File created:", Singleton.m_szWorkDir);
                photoFile = new File(Singleton.m_szPictDir, albumName + count + albumName + ".jpg");

                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

                startActivityForResult(mIntent, PICKER);
            }
        });

    }

     public void PhotoUpdater() {

            File dir = new File(Singleton.m_szPictDir);
            File[] dirListing = dir.listFiles();
            if (dirListing != null) {
                for (File pict : dirListing){
                    if(albumName!="gallery"){
                        String tag = pict.getName().toString();

                        char a_char = tag.charAt(5);

                        if( ((int)a_char - 48) > 9){
                            tag = tag.substring(0,7);
                        }
                        else{
                            tag = tag.substring(0,6);
                        }



                        Log.d(TAG,tag+"_"+albumName);
                        if (albumName.equals(tag)) {
                            Log.d(TAG, pict.getName().toString());
                            pictureNames.add(pict.getName().toString());
                            GA.putPictures(PICKER, RESULT_OK, Uri.fromFile(
                                new File(Singleton.m_szPictDir, pict.getName().toString())));
                        }
                    }
                    else{
                        Log.d(TAG, pict.getName().toString());
                        pictureNames.add(pict.getName().toString());
                        GA.putPictures(PICKER, RESULT_OK, Uri.fromFile(
                                new File(Singleton.m_szPictDir, pict.getName().toString())));
                        imgAdapt.notifyDataSetChanged();
                    }
                }
            }
//            progressDialog.dismiss();


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == PICKER) {
                Uri pickedUri = Uri.fromFile(photoFile);
                Bitmap pic = null;
                String imgPath = "";


                String[] medData = {MediaStore.Images.Media.DATA};

                Cursor picCursor = managedQuery(pickedUri, medData, null, null, null);
                if (picCursor != null) {
                    int index = picCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    picCursor.moveToFirst();
                    imgPath = picCursor.getString(index);
                } else
                    imgPath = pickedUri.getPath();

                if (pickedUri != null) {
                    int targetWidth = 600;
                    int targetHeight = 400;

                    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                    bmpOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imgPath, bmpOptions);

                    int currHeight = bmpOptions.outHeight;
                    int currWidth = bmpOptions.outWidth;

                    int sampleSize = 1;

                    if (currHeight > targetHeight || currWidth > targetWidth) {
                        //use either width or height
                        if (currWidth > currHeight)
                            sampleSize = Math.round((float) currHeight / (float) targetHeight);
                        else
                            sampleSize = Math.round((float) currWidth / (float) targetWidth);
                    }

                    bmpOptions.inSampleSize = sampleSize;
                    bmpOptions.inJustDecodeBounds = false;
                    pic = BitmapFactory.decodeFile(imgPath, bmpOptions);

                    imgAdapt.addPic(pic);

                    picGallery.setAdapter(imgAdapt);

                    picView.setImageBitmap(pic);

                    picView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    protected void putPictures(int requestCode, int resultCode, Uri uri) {

        if (resultCode == RESULT_OK) {
            if (requestCode == PICKER) {
                    Uri pickedUri = uri;

                    Bitmap pic = null;
                    String imgPath = "";


                    String[] medData = {MediaStore.Images.Media.DATA};

                    Cursor picCursor = managedQuery(pickedUri, medData, null, null, null);
                    if (picCursor != null) {
                        int index = picCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        picCursor.moveToFirst();
                        imgPath = picCursor.getString(index);
                    } else
                        imgPath = pickedUri.getPath();

                    if (pickedUri != null) {
                        int targetWidth = 600;
                        int targetHeight = 400;

                        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                        bmpOptions.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(imgPath, bmpOptions);

                        int currHeight = bmpOptions.outHeight;
                        int currWidth = bmpOptions.outWidth;

                        int sampleSize = 1;

                        if (currHeight > targetHeight || currWidth > targetWidth) {
                            //use either width or height
                            if (currWidth > currHeight)
                                sampleSize = Math.round((float) currHeight / (float) targetHeight);
                            else
                                sampleSize = Math.round((float) currWidth / (float) targetWidth);
                        }

                        bmpOptions.inSampleSize = sampleSize;
                        bmpOptions.inJustDecodeBounds = false;
                        pic = BitmapFactory.decodeFile(imgPath, bmpOptions);

                        imgAdapt.addPic(pic);

                        picGallery.setAdapter(imgAdapt);

                        picView.setImageBitmap(pic);

                        picView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    }

            }
        }

    }
}



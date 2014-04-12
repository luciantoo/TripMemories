package itec.code2smile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Sined on 4/12/2014.
 */
public class GalleryActivity extends Activity{

    private final int PICKER = 1;

    private File photoFile = new File(Singleton.m_szPictDir,"photo.jpg");

    private int currentPic = 0;

    private Gallery picGallery;

    private ImageView picView;

    private PicAdapter imgAdapt;

    public class PicAdapter extends BaseAdapter {

        int defaultItemBackground;

        private Context galleryContext;

        private Bitmap[] imageBitmaps;

        Bitmap placeholder;

        public PicAdapter(Context c) {

            galleryContext = c;

            imageBitmaps  = new Bitmap[10];

            placeholder = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

            for(int i=0; i<imageBitmaps.length; i++)
                imageBitmaps[i]=placeholder;

            TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);

            defaultItemBackground = styleAttrs.getResourceId(
                    R.styleable.PicGallery_android_galleryItemBackground, 0);

            styleAttrs.recycle();
        }

        @Override
        public int getCount() {
            return imageBitmaps.length;
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
            imageView.setImageBitmap(imageBitmaps[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundResource(defaultItemBackground);
            return imageView;
        }

        public void addPic(Bitmap newPic)
        {
            imageBitmaps[currentPic] = newPic;
        }

        public Bitmap getPic(int posn)
        {
            return imageBitmaps[posn];
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        picView= (ImageView) findViewById(R.id.picture);

        picGallery = (Gallery) findViewById(R.id.gallery);

        imgAdapt = new PicAdapter(this);

        picGallery.setAdapter(imgAdapt);
        picGallery.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                currentPic = position;

//                Intent pickIntent = new Intent();
//                pickIntent.setType("image/*");
//                pickIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICKER);


                Intent mIntent = new Intent("android.media.action.IMAGE_CAPTURE");

                String state = Environment.getExternalStorageState();
                    Log.d("State", state);
                    Log.d("File created:",Singleton.m_szWorkDir);
                    photoFile = new File(Singleton.m_szPictDir,"photo.jpg");
                    mIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
//                    Uri imageUri = Uri.fromFile(photoFile);


                startActivityForResult(mIntent,PICKER);
                return true;
            }
        });

        picGallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                picView.setImageBitmap(imgAdapt.getPic(position));

            }
        });

    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode == RESULT_OK) {
                if (requestCode == PICKER) {
                    Uri pickedUri = Uri.fromFile(photoFile);
                    Bitmap pic = null;
                    String imgPath = "";


                    String[] medData = { MediaStore.Images.Media.DATA };

                    Cursor picCursor = managedQuery(pickedUri, medData, null, null, null);
                    if(picCursor!=null)
                    {
                        int index = picCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        picCursor.moveToFirst();
                        imgPath = picCursor.getString(index);
                    }
                    else
                        imgPath = pickedUri.getPath();

                    if(pickedUri!=null) {
                        int targetWidth = 600;
                        int targetHeight = 400;

                        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                        bmpOptions.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(imgPath, bmpOptions);

                        int currHeight = bmpOptions.outHeight;
                        int currWidth = bmpOptions.outWidth;

                        int sampleSize = 1;

                        if (currHeight>targetHeight || currWidth>targetWidth)
                        {
                            //use either width or height
                            if (currWidth>currHeight)
                                sampleSize = Math.round((float)currHeight/(float)targetHeight);
                            else
                                sampleSize = Math.round((float)currWidth/(float)targetWidth);
                        }

                        bmpOptions.inSampleSize = sampleSize;
                        bmpOptions.inJustDecodeBounds = false;
                        pic = BitmapFactory.decodeFile(imgPath, bmpOptions);

                        PicAdapter imgAdapt = new PicAdapter(this);

                        imgAdapt.addPic(pic);

                        picGallery.setAdapter(imgAdapt);

                        picView.setImageBitmap(pic);

                        picView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                }
            }

            super.onActivityResult(requestCode, resultCode, data);
        }
}



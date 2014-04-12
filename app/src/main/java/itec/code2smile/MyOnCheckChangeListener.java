package itec.code2smile;

import android.view.View;

/**
 * Created by Sined on 4/12/2014.
 */
public interface MyOnCheckChangeListener {
    void onItemClickListener(View view, Album item, int position);
    void onPictureClickListener(View view,Album album,int position);
}

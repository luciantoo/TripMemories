package itec.code2smile;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Sined on 4/12/2014.
 */
public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Album> albumList;
    private MyOnCheckChangeListener listener = null;

    public CustomAdapter(Context context, List<Album> albumList ) {
        this.context = context;
        this.albumList = albumList;
    }

    public void setListener(MyOnCheckChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Album myAlbum = albumList.get(position);
        final int Itemposition = position;
        View view = new CustomView(this.context, myAlbum);

        ImageView imageView = (ImageView) view.findViewById(CustomView.getPictureId());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                    listener.onPictureClickListener(v,myAlbum,Itemposition);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(listener != null)
                    listener.onItemClickListener(v, myAlbum, Itemposition);
            }
        });

        return view;
    }
}

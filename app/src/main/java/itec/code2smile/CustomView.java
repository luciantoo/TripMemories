package itec.code2smile;

import android.content.Context;
import android.view.Gravity;
import android.widget.*;

/**
 * Created by Sined on 4/12/2014.
 */
public class CustomView extends RelativeLayout{

    private int itemH = 100;
    private Album album;
    private static int ivLogoID=100;
    private Singleton mInst= Singleton.getInstance();
    private short id;

    public CustomView(Context context, Album album){
        super(context);

        setPadding(0, 6, 0, 6);
        //setBackground(getResources().getDrawable(R.drawable.album));

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemH,itemH);
        params.setMargins(20, 0, 20, 0);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ImageView ivLogo = new ImageView(context);
        ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.test));
        addView(ivLogo, params);

        //Relative Layout for Texts
        RelativeLayout.LayoutParams paramsTxt = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.WRAP_CONTENT);
        paramsTxt.addRule(RelativeLayout.RIGHT_OF, ivLogoID);

        LinearLayout m_ll = new LinearLayout(context);
        m_ll.setOrientation(LinearLayout.VERTICAL);
        m_ll.setGravity(Gravity.BOTTOM);
        //m_ll.setBackground(getResources().getDrawable(R.drawable.abc_list_pressed_holo_light));

        TextView m_TextName = new TextView(context);
        m_TextName.setTextSize(16);
        m_TextName.setText(album.getName());
        m_TextName.setGravity(Gravity.CENTER);
        m_ll.addView(m_TextName);

        addView(m_ll,paramsTxt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(LayoutParams.MATCH_PARENT, itemH);
    }

}

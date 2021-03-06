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

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemH,itemH);
        params.setMargins(20, 0, 20, 0);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ImageView ivLogo = new ImageView(context);
        ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.delete1));
        addView(ivLogo, params);
        ivLogo.setId(ivLogoID);

        //Relative Layout for Text
        RelativeLayout.LayoutParams paramsTxt = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.WRAP_CONTENT);
        paramsTxt.addRule(RelativeLayout.RIGHT_OF, ivLogoID);

        LinearLayout m_ll = new LinearLayout(context);
        m_ll.setMinimumWidth(LayoutParams.MATCH_PARENT);
        m_ll.setMinimumHeight(itemH);
        m_ll.setOrientation(LinearLayout.VERTICAL);
        m_ll.setGravity(Gravity.CENTER);

        TextView m_TextName = new TextView(context);
        m_TextName.setTextSize(16);
        m_TextName.setText(album.getName());
        m_TextName.setGravity(Gravity.CENTER);
        m_ll.addView(m_TextName);

        addView(m_ll,paramsTxt);
    }

    public static int getPictureId(){
        return ivLogoID;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(LayoutParams.MATCH_PARENT, itemH);
    }

}

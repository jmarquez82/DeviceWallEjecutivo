package cl.rinno.newdevicewall.gridlibrery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


public class TestGridItemView extends View implements IGridItemView {

    private GridItem mGridItem;
    private Context mContext;
    private TextView txDemo;


    public TestGridItemView(Context context) {
        super(context);
        this.mContext = context;
    }

    public TestGridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestGridItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public GridItem getGridItem() {
        return mGridItem;
    }

    @Override
    public void setGridItem(GridItem gridItem) {
        mGridItem = gridItem;
    }
}

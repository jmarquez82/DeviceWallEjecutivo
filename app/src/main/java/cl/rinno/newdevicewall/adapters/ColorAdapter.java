package cl.rinno.newdevicewall.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cl.rinno.newdevicewall.R;


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>
{

    private ArrayList<String> Colores = new ArrayList<>();
    Context context;

    public ColorAdapter(ArrayList<String> listaColores, Context context)
    {
        this.Colores =  listaColores;
        this.context = context;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, final int position)
    {
        Drawable background = (holder.detailsColor.getBackground());
        GradientDrawable shapeDrawable = (GradientDrawable) background;
        shapeDrawable.setColor(Color.parseColor(Colores.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return Colores.size();
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder
    {

        ImageView detailsColor;

        ColorViewHolder(View itemView)
        {
            super(itemView);

            detailsColor = (ImageView) itemView.findViewById(R.id.detailsColor);
        }
    }
}

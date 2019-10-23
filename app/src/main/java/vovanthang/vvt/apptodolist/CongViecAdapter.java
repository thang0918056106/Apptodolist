package vovanthang.vvt.apptodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;

    public CongViecAdapter(MainActivity context, int layout, ArrayList<CongViec> congViecs) {
        this.context = context;
        this.layout = layout;
        this.congViecs = congViecs;
    }

    ArrayList<CongViec> congViecs;
    @Override
    public int getCount() {
        return congViecs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
            TextView txtTen;
            ImageView imgDetele , imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layout,null);

            holder.txtTen = (TextView) view.findViewById(R.id.txtTenCongViec);
            holder.imgDetele = (ImageView) view.findViewById(R.id.imgDelete);
            holder.imgEdit  = (ImageView) view.findViewById(R.id.imgEdit);

            view.setTag(holder);

        }

        else {
            holder = (ViewHolder) view.getTag();
        }

        final CongViec congViec = congViecs.get(i);

        holder.txtTen.setText(congViec.getTencongviec());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DiaLogEdit(congViec.getTencongviec() , congViec.getIdCV());
            }
        });

        holder.imgDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.AlertDialogDelete(congViec.getTencongviec(),congViec.getIdCV());
            }
        });
        return view;
    }


}

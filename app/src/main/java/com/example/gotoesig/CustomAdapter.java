package com.example.gotoesig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

class CustomAdapter extends BaseAdapter {

    private Context context;


    public CustomAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return MainActivity.modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);


            holder.dateDep = (TextView) convertView.findViewById(R.id.dateDep);
            holder.heureDep = (TextView) convertView.findViewById(R.id.heureDep);
            holder.lieuDep = (TextView) convertView.findViewById(R.id.lieuDep);
            holder.btn_plus = (Button) convertView.findViewById(R.id.plus);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.dateDep.setText(MainActivity.modelArrayList.get(position).getJour().toString());
        holder.lieuDep.setText(String.valueOf(MainActivity.modelArrayList.get(position).getLieuDepart()));
        holder.heureDep.setText(String.valueOf(MainActivity.modelArrayList.get(position).getHoraire()));


        holder.btn_plus.setTag(R.integer.btn_plus_view, convertView);
        holder.btn_plus.setTag(R.integer.btn_plus_pos, position);
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.btn_plus.getTag(R.integer.btn_plus_view);
                Integer pos = (Integer) holder.btn_plus.getTag(R.integer.btn_plus_pos);

                //MainActivity.changeFrag(4);

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected Button btn_plus;
        private TextView dateDep, lieuDep, heureDep;

    }

}
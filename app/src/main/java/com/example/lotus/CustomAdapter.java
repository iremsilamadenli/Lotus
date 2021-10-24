package com.example.lotus;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {


    List<String> itemlist;


    public CustomAdapter(List<String> itemlist){
        this.itemlist = itemlist;
    }


    @NonNull
    @Override
    public CustomAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i ) {


        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.siralar_single_item,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i  ) {

        holder.siralar.setText(itemlist.get(i));
        //Time Part
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        holder.saat.setText(mdformat.format(calendar.getTime()));

    }

    @Override
    public int getItemCount() {
        return itemlist.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView siralar;
        TextView saat;



        public Holder(View view) {
            super(view);
            siralar= view.findViewById(R.id.siralar);
            saat= view.findViewById(R.id.saat);


        }
    }
}

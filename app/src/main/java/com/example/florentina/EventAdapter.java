package com.example.florentina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    Context context;

    ArrayList<Event> list;

    public EventAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new EventHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventHolder holder, int position) {

        Event  event =  list.get(position);
        holder.name.setText(event.getName());
        holder.desc.setText(event.getDesc());
        holder.expdate.setText(event.getExpdate());
        holder.price.setText(String.valueOf(event.getPrice()));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder{

        TextView name, desc, price, expdate;

        public EventHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.wc_header);
            desc = itemView.findViewById(R.id.wc_description);
            price = itemView.findViewById(R.id.wc_price);
            expdate = itemView.findViewById(R.id.wc_date);
        }
    }
}

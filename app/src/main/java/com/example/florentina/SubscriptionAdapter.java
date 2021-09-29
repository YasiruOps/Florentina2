package com.example.florentina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubsViewHolder> {

    Context context;
    ArrayList<Subscription> list;

    public SubscriptionAdapter(Context context, ArrayList<Subscription> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.subscription_item, parent, false);
        return new SubsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.SubsViewHolder holder, int position) {
        Subscription subscription = list.get(position);
        holder.varsubname.setText(subscription.getName());
        holder.varsubdescription.setText(subscription.getDesription());
        holder.varsubprice.setText(String.valueOf(subscription.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SubsViewHolder extends RecyclerView.ViewHolder{

        TextView varsubname, varsubdescription, varsubprice;
        Button btnedit, btndelete;
        ImageView img;

        public SubsViewHolder(@NonNull View itemView) {
            super(itemView);

            varsubname = itemView.findViewById(R.id.subname);
            varsubdescription = itemView.findViewById(R.id.subdescription);
            varsubprice =  itemView.findViewById(R.id.subprice);

        }
    }

}

package com.example.florentina;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Event_UserAdapter extends RecyclerView.Adapter<Event_UserAdapter.EventHolder> {

    Context context;

    ArrayList<Event> list;

    public Event_UserAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_user_item, parent, false);
        return new EventHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Event_UserAdapter.EventHolder holder, int position) {

        Event  event =  list.get(position);

        Glide.with(context)
                .load(event.getImgurl())
                .into(holder.img);

        //View CRUD
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
        ImageView img;
        Button editbtn, deletebtn;

        public EventHolder(@NonNull View itemView) {
            super(itemView);

            editbtn = itemView.findViewById(R.id.eventpopupbtn);
            deletebtn = itemView.findViewById(R.id.eventdeletebtn);

            img = itemView.findViewById(R.id.img3);

            name = itemView.findViewById(R.id.wc_header);
            desc = itemView.findViewById(R.id.wc_description);
            price = itemView.findViewById(R.id.wc_price);
            expdate = itemView.findViewById(R.id.wc_date);
        }
    }
}

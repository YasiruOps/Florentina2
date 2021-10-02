package com.example.florentina;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        //Glide.with(context)
               // .load(event.get())
               // .into(holder.img);

        //View CRUD
        holder.name.setText(event.getName());
        holder.desc.setText(event.getDesc());
        holder.expdate.setText(event.getExpdate());
        holder.price.setText(String.valueOf(event.getPrice()));

        //UPDATE CRUD
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.event_popup))
                        .setExpanded(true, 2000)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name, desc, price, expdate, url;

                name = view.findViewById(R.id.eventName);
                desc = view.findViewById(R.id.eventDesc);
                price = view.findViewById(R.id.eventPrice);
                expdate = view.findViewById(R.id.expDate);
                url = view.findViewById(R.id.eventURL);

                Button btnUpdate = view.findViewById(R.id.editevent_submitbtn);

                name.setText(event.getName());
                desc.setText(event.getDesc());
                price.setText(Float.toString(event.getPrice()));
                expdate.setText(event.getExpdate());
                url.setText(event.getImgurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("desc", desc.getText().toString());
                        map.put("expdate", expdate.getText().toString());
                        map.put("imgurl", url.getText().toString());
                        map.put("price", Float.parseFloat(price.getText().toString()));

                        FirebaseDatabase.getInstance().getReference()
                                .child("Events2")
                                .child(event.getEventId())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Data updated sucessfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Error while updating", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
            }
        });

    //DELETE CRUD
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data cannot be Undone.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Events2").child(event.getEventId()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder{

        TextView name, desc, price, expdate;
        CircleImageView img;
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

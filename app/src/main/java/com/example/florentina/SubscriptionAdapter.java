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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubsViewHolder> {



    Context context;
    ArrayList<Subscription> list;

    private Float disconted;

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


        //disconted=(subscription.getPrice()- subscription.getPrice()*20/100);

        Glide.with(context)
                .load(subscription.getImageURL())
                .into(holder.img);

        holder.varsubname.setText(subscription.getName());
        holder.varsubdescription.setText(subscription.getDesription());
        holder.varsubprice.setText(String.valueOf(subscription.getPrice()));

        //update popup call
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_subspopup))
                        .setExpanded(true, 1650)
                        .create();


                //retreive to show already existing data
                View view = dialogPlus.getHolderView();
                EditText name, desc, price ,url;
                Button updatebtn;

                updatebtn = view.findViewById(R.id.subbtnUpdate);

                name = view.findViewById(R.id.txtName);
                desc = view.findViewById(R.id.txtDescription);
                price = view.findViewById(R.id.txtPrice);
                url = view.findViewById(R.id.txtURL);

                name.setText(subscription.getName());
                desc.setText(subscription.getDesription());
                price.setText(Float.toString(subscription.getPrice()));
                url.setText(subscription.getImageURL());

                dialogPlus.show();

                //UPDATE crud
                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();

                        map.put("desription",desc.getText().toString());
                        map.put("name", name.getText().toString());
                        map.put("price", Float.parseFloat(price.getText().toString()));
                        map.put("imageURL", url.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Subscription2")
                                .child(subscription.getSubid())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Update Sucessfull", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Error while updating data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });


            }
        });
        //DELETE CRUD

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Subscription2")
                                .child(subscription.getSubid()).removeValue();
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

    public static class SubsViewHolder extends RecyclerView.ViewHolder{

        TextView varsubname, varsubdescription, varsubprice, discount;
        Button btnedit, btndelete;
        ImageView img;



        public SubsViewHolder(@NonNull View itemView) {
            super(itemView);

          //  discount = itemView.findViewById(R.id.discountedprice);

            img = itemView.findViewById(R.id.img1);

            btnedit = itemView.findViewById(R.id.editsubs);
            btndelete = itemView.findViewById(R.id.deletesubs);

            varsubname = itemView.findViewById(R.id.subname);
            varsubdescription = itemView.findViewById(R.id.subdescription);
            varsubprice =  itemView.findViewById(R.id.subprice);

        }
    }

}

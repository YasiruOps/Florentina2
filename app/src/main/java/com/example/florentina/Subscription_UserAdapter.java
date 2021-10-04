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

public class Subscription_UserAdapter extends RecyclerView.Adapter<Subscription_UserAdapter.SubsViewHolder> {



    Context context;
    ArrayList<Subscription> list;

    public Subscription_UserAdapter(Context context, ArrayList<Subscription> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public SubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.subscription_user_item, parent, false);
        return new SubsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Subscription_UserAdapter.SubsViewHolder holder, int position) {
        Subscription subscription = list.get(position);

        //CALCULATION
        Float discounted;
        discounted = (subscription.getPrice()*(100-20)/100);

        holder.discount.setText(String.valueOf(discounted));
            ////////////////////////////////////////
        Glide.with(context)
                .load(subscription.getImageURL())
                .into(holder.img);

        holder.varsubname.setText(subscription.getName());
        holder.varsubdescription.setText(subscription.getDesription());
        holder.varsubprice.setText(String.valueOf(subscription.getPrice()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SubsViewHolder extends RecyclerView.ViewHolder{

        TextView varsubname, varsubdescription, varsubprice, discount;
        ImageView img;



        public SubsViewHolder(@NonNull View itemView) {
            super(itemView);

           discount = itemView.findViewById(R.id.subprice);


            img = itemView.findViewById(R.id.img1);

            varsubname = itemView.findViewById(R.id.subname);
            varsubdescription = itemView.findViewById(R.id.subdescription);
            varsubprice =  itemView.findViewById(R.id.subprice);

        }
    }

}

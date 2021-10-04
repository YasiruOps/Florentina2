package com.example.florentina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class productUser extends AppCompatActivity {

    private View decorView;
    RecyclerView recyclerView;
    ArrayList<Product> list;
    ProductUserAdapter productUserAdapter;
    DatabaseReference database;
    Button varbtn;

//    private Integer counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_user);

        decorView = getWindow().getDecorView();
        recyclerView = findViewById(R.id.user_rv_product);
        database = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ImageView plus, minus;
//        TextView total, pcount;
//
//        plus = findViewById(R.id.plus_sign);
//        minus = findViewById(R.id.minus_sign);
//
//        total = findViewById(R.id.totalproducts);
//        pcount = findViewById(R.id.product_count);
//
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                counter++;
//            }
//        });
//
//        minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                counter--;
//            }
//        });
//
//        pcount.setText(Integer.toString(counter));






        list =new ArrayList<>();
        productUserAdapter = new ProductUserAdapter(this,list);
        recyclerView.setAdapter(productUserAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    //sets key to be used by update
                    product.setProductId(dataSnapshot.getKey());
                    list.add(product);
                }
                productUserAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });

        ///NAVIGATION BAR
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent1 = new Intent(productUser.this, HomePage.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_shop:
                        Intent intent2 = new Intent(productUser.this, productUser.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_event:
                        Intent intent3 = new Intent(productUser.this, Event_UserMain.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_random:
                        Intent intent4 = new Intent(productUser.this, Subscription_UserMain.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_userprofile:
                        Intent intent5 = new Intent(productUser.this, ProfilePage.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }

        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        decorView.setSystemUiVisibility(hideSystemBars());

    }



    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    };
}
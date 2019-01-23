/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.liorkaramany.swaiterbeta;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class OrdersList extends Activity {
    public static final int NUMBER_OF_ORDERS = 8;
    ListView[] o;
    List<Dish>[] lists;

    DatabaseReference ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        o = new ListView[NUMBER_OF_ORDERS];

        o[0] = (ListView) findViewById(R.id.o1);
        o[1] = (ListView) findViewById(R.id.o2);
        o[2] = (ListView) findViewById(R.id.o3);
        o[3] = (ListView) findViewById(R.id.o4);
        o[4] = (ListView) findViewById(R.id.o5);
        o[5] = (ListView) findViewById(R.id.o6);
        o[6] = (ListView) findViewById(R.id.o7);
        o[7] = (ListView) findViewById(R.id.o8);


        lists = new ArrayList[NUMBER_OF_ORDERS];

        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            lists[i] = new ArrayList<>();
        }

        ref = FirebaseDatabase.getInstance().getReference("orders");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Clear all the lists.
                for (int i = 0; i < NUMBER_OF_ORDERS; i++)
                    lists[i].clear();
                //Get each list into its dedicated list.
                int i = 0;
                for(DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    if (i < NUMBER_OF_ORDERS) {
                        GenericTypeIndicator<ArrayList<Dish>> t = new GenericTypeIndicator<ArrayList<Dish>>() {
                        };

                        if (orderSnapshot.getValue(t) == null) {
                            lists[i] = new ArrayList<>();
                            //lists[i].add(new Dish("1", "1", 1, "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi0hfiBxITgAhVKy6QKHdpkCsoQjRx6BAgBEAU&url=https%3A%2F%2Fwww.target.com%2Fp%2Fcoca-cola-20-fl-oz-bottle%2F-%2FA-12953529&psig=AOvVaw3YHZXEigojTTJ6Unza-eTw&ust=1548354466217800"));
                        }
                        else
                            lists[i] = orderSnapshot.getValue(t);

                        DishList adapter = new DishList(OrdersList.this, lists[i]);
                        o[i].setAdapter(adapter);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

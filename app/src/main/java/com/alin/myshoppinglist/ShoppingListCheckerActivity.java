package com.alin.myshoppinglist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alin.myshoppinglist.adapters.ShoppingListItemAdapter;
import com.alin.myshoppinglist.adapters.ShoppingListItemCheckerAdapter;
import com.alin.myshoppinglist.models.ShoppingList;
import com.alin.myshoppinglist.models.ShoppingListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingListCheckerActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbReference = database.getReference();
    List<ShoppingListItem> items = new ArrayList<>();

    private void getShoppingList(final String listName) {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> data = (Map<String, Object>)dataSnapshot.getValue();
                if (data == null) {
                    return;
                }
                for (Map.Entry<String, Object> e : data.entrySet()) {
                    if (e.getKey().equals(listName)) {
                        items = new ArrayList<>();
                        Log.d("database", e.getKey() + " " + e.getValue());
                        List<Object> dbItems = (List<Object>) e.getValue();
                        for (int i = 0; i < dbItems.size(); i++) {
                            items.add(new ShoppingListItem((Map<String, Object>) dbItems.get(i)));
                        }
                        ShoppingListItemCheckerAdapter adapter = (ShoppingListItemCheckerAdapter) ((ListView) findViewById(R.id.shopping_list_checker)).getAdapter();
                        adapter.clear();
                        adapter.addAll(items);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ValueEventListener", databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_checker);

        //Log.d("stringExtra", getIntent().getStringExtra("listName"));
        String listName = getIntent().getStringExtra("listName");
        getShoppingList(listName);

        ListView shoppingItems = findViewById(R.id.shopping_list_checker);
        ShoppingListItemCheckerAdapter adapter = new ShoppingListItemCheckerAdapter(this, R.layout.shopping_list_item, listName, items);
        shoppingItems.setAdapter(adapter);

        TextView listNameTextView = findViewById(R.id.list_name_checker);
        listNameTextView.setText(listName);

        FloatingActionButton fab = findViewById(R.id.fab_back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}

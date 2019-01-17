package com.alin.myshoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alin.myshoppinglist.models.ShoppingList;
import com.alin.myshoppinglist.models.ShoppingListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbReference = database.getReference();
    List<ShoppingList> sls = new ArrayList<>();
    ArrayAdapter arrAdapter;

    private void getShoppingLists() {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> data = (Map<String, Object>)dataSnapshot.getValue();
                if (data == null) {
                    return;
                }
                sls.clear();
                for (Map.Entry<String, Object> e : data.entrySet()) {
                    Log.d("database", e.getKey() + " " + e.getValue());
                    String shoppingListName = e.getKey();
                    List<ShoppingListItem> items = new ArrayList<>();
                    List<Object> dbItems = (List<Object>)e.getValue();
                    for (int i = 0; i < dbItems.size(); i++) {
                        items.add(new ShoppingListItem((Map<String, Object>)dbItems.get(i)));
                    }
                    sls.add(new ShoppingList(shoppingListName, items));
                }
                arrAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ValueEventListener", databaseError.getMessage());
            }
        });
    }

    public void setupListView() {
        ListView shoppingLists = findViewById(R.id.shopping_lists);

        getShoppingLists();
        arrAdapter = new ArrayAdapter<>(this, R.layout.shopping_lists, R.id.shopping_list, sls);
        shoppingLists.setAdapter(arrAdapter);

        shoppingLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent myIntent = new Intent(viewClicked.getContext(), ShoppingListCheckerActivity.class);
                myIntent.putExtra("listName", sls.get(position).name);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupListView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewShoppingListActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.alin.myshoppinglist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.alin.myshoppinglist.adapters.ShoppingListItemAdapter;
import com.alin.myshoppinglist.models.ShoppingList;
import com.alin.myshoppinglist.models.ShoppingListItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewShoppingListActivity extends AppCompatActivity {
    ShoppingList sl = new ShoppingList();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbReference = database.getReference();
    ShoppingListItemAdapter sliAdapter;

    private void setupFABs() {
        FloatingActionButton fab = findViewById(R.id.fab_back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab_new_item);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl.items.add(new ShoppingListItem());
                sliAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shopping_list);

        setupFABs();

        sl.addItem(new ShoppingListItem("mere"));
        sl.addItem(new ShoppingListItem("pere"));
        sl.addItem(new ShoppingListItem("cozonaci"));

        ListView shoppingItems = findViewById(R.id.shopping_items);
        sliAdapter = new ShoppingListItemAdapter(this, R.layout.shopping_list_item, sl.items);
        shoppingItems.setAdapter(sliAdapter);

        EditText listName = findViewById(R.id.list_name);
        listName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {
                sl.name = s.toString();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            if (sl.name.length() == 0) {
                return false;
            }
            dbReference.child(sl.name).setValue(sl.items);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

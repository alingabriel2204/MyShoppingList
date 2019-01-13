package com.alin.myshoppinglist.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.alin.myshoppinglist.R;
import com.alin.myshoppinglist.models.ShoppingListItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShoppingListItemCheckerAdapter extends ArrayAdapter<ShoppingListItem> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbReference = database.getReference();
    private int resourceLayout;
    private Context context;
    private String listName;
    private List<ShoppingListItem> items;

    public ShoppingListItemCheckerAdapter(Context context, int resource, String listName, List<ShoppingListItem> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
        this.items = items;
        this.listName = listName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shopping_list_item_checker, null);
        }

        //Handle TextView and display string from your list
        TextView item = view.findViewById(R.id.item_checker);
        item.setText(items.get(position).productName);

        Switch sw = view.findViewById(R.id.check_switch);
        sw.setChecked(items.get(position).checked);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                items.get(position).checked = isChecked;
                Log.d("itemsLength", items.toString());
                dbReference.child(listName).setValue(items);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

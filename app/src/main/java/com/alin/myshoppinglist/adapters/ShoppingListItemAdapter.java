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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alin.myshoppinglist.R;
import com.alin.myshoppinglist.models.ShoppingListItem;

import java.util.List;

public class ShoppingListItemAdapter extends ArrayAdapter<ShoppingListItem> {

    private int resourceLayout;
    private Context context;
    private boolean readOnly;
    private List<ShoppingListItem> items;

    public ShoppingListItemAdapter(Context context, int resource, List<ShoppingListItem> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.context = context;
        this.items = items;
        this.readOnly = true;
    }

    private void readOnlyChanged(View view) {
        TextView itemRead = view.findViewById(R.id.item_read_only);
        EditText item = view.findViewById(R.id.item);
        if (readOnly) {
            itemRead.setVisibility(View.VISIBLE);
            item.setVisibility(View.INVISIBLE);
        } else {
            itemRead.setVisibility(View.INVISIBLE);
            item.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shopping_list_item, null);
        }

        final TextView itemRead = view.findViewById(R.id.item_read_only);
        itemRead.setText(items.get(position).productName);

        final EditText item = view.findViewById(R.id.item);
        item.setText(items.get(position).productName);
        itemRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readOnly = !readOnly;
                //readOnlyChanged(view, position);
                if (readOnly) {
                    itemRead.setVisibility(View.VISIBLE);
                    item.setVisibility(View.INVISIBLE);
                } else {
                    itemRead.setVisibility(View.INVISIBLE);
                    item.setVisibility(View.VISIBLE);
                }
            }
        });

        item.addTextChangedListener(new TextWatcher() {
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
                Log.d("afterTextChanged", s.toString());
                items.get(position).productName = s.toString();
            }
        });

        EditText qty = view.findViewById(R.id.qty);
        qty.setText(Long.toString(items.get(position).qty));

        //Handle button and add onClickListener
        ImageButton deleteBtn = view.findViewById(R.id.delete_item);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Log.d("onClickDeleteItem", "delete button clicked");
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

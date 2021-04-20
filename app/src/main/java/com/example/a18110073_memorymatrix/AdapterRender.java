package com.example.a18110073_memorymatrix;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterRender extends BaseAdapter {
    Context context;
    List<Boolean> results;

    public AdapterRender(Context context, List<Boolean> results) {
        this.context = context;
        this.results = results;
        //this.shows.addAll(Collections.nCopies(36, false));
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView renderText = new TextView(context);
        renderText.setText("");
        renderText.setTextColor(Color.WHITE);

        renderText.setBackgroundColor(results.get(position) ? 0xFF61D7A8 : 0xFF93c4f6);

        renderText.setGravity(Gravity.CENTER);
        renderText.setHeight(150);
        renderText.setWidth(200);

        return renderText;
    }
}

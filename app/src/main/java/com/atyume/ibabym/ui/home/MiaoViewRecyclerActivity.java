package com.atyume.ibabym.ui.home;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atyume.ibabym.R;
import com.atyume.ibabym.adapter.HeaderItemTestAdapter;
import com.atyume.ibabym.utils.DismissDecoration;
import com.atyume.ibabym.utils.ItemTouchHelperCallback;

public class MiaoViewRecyclerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaoview);

        // setup the view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.miao_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // add some adapter with header and items
        HeaderItemTestAdapter adapter = new HeaderItemTestAdapter();
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new DismissDecoration(Color.rgb(10, 210, 25),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_archive_24dp),
                getResources().getDisplayMetrics().density));
    }
}

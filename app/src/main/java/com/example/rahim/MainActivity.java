package com.example.rahim;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SearchableSpinner searchableSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchableSpinner = findViewById(R.id.spinner);


       // ডাটা লোড করা
        List<String> items = new ArrayList<>();
        items.add("Option 1");
        items.add("Option 2");
        items.add("Option 3");
        items.add("Option 4");
        items.add("Option 5");
        items.add("Option 6");
        items.add("Option 7");
        items.add("Option 8");
        items.add("Option 9");
        items.add("Option 10");
        items.add("Option 11");
        items.add("Option 12");

        // স্পিনারে ডাটা সেট করা
        searchableSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items));

    }
}
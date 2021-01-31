package com.ipa.www.insuracepolicyapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class ProvidersHome extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_home);

        setupUIViews();
        initializeToolbar();
        setupListView();

    }

    private void setupUIViews(){
        toolbar = findViewById(R.id.tbmain);
        listView = findViewById(R.id.lvmain);

    }

    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InsuranceApp");
    }

    private void setupListView(){
        String[] title = getResources().getStringArray(R.array.Main);
        String[] description = getResources().getStringArray(R.array.Description);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, title, description);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              switch (i) {
                  case 0: {
                      Intent intent = new Intent(ProvidersHome.this, PolicyDataSave.class);
                      startActivity(intent);
                      break;
                  }
                  case 1: {
                      Intent intent = new Intent(ProvidersHome.this, PolicyDataSave.class);
                      startActivity(intent);
                      break;
                  }
                  case 2: {
                      Intent intent = new Intent(ProvidersHome.this, PolicyDataSave.class);
                      startActivity(intent);
                      break;
                  }
                  case 3: {
                      Intent intent = new Intent(ProvidersHome.this, PolicyDataSave.class);
                      startActivity(intent);
                      break;
                  }
                  case 4: {
                      Intent intent = new Intent(ProvidersHome.this, PolicyDataSave.class);
                      startActivity(intent);
                      break;
                  }

              }
          }
        });
    }

    public class SimpleAdapter extends BaseAdapter{
        //Simple adapter-layout given to the listview; corresponds to providers_home_single_item

        private Context ncontext;
        private LayoutInflater layoutInflater;
        private TextView title, description;
        private ImageView image;
        private String[] titleArray, descriptionArray;

        public SimpleAdapter(Context context, String title[], String description[]){
            ncontext = context;
            titleArray = title;
            descriptionArray = description;
            layoutInflater = LayoutInflater.from(context);
            //allows one to lure different layouts into a particular view
        }

        @Override
        public int getCount() {
            return titleArray.length;
        }

        @Override
        public Object getItem(int i) { //position
            return titleArray[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //view hands over control from providers home(the main activity) to providers_home_single_item
            //accessing providers_home_single_item from providers home(the main activity)
            if (view == null){
                view = layoutInflater.inflate(R.layout.providers_home_single_item, null);
                //view is null, takes providers_home_single_item and inflates it into view(which is available): the list view
            }

            //view has providers_home_single_item activity; title and description can be referenced in providers home activity
            title = view.findViewById(R.id.tvmain);
            description = view.findViewById(R.id.tvdesmain);
            image = view.findViewById(R.id.ivmain);

            title.setText(titleArray[i]);
            description.setText(descriptionArray[i]);
            //i gives the particular position of this item

            //setting up image drivers;
            if (titleArray[i].equalsIgnoreCase("Property")){
                image.setImageResource(R.drawable.property);
            }else if (titleArray[i].equalsIgnoreCase("Vehicle")){
                image.setImageResource(R.drawable.car);
            }else if (titleArray[i].equalsIgnoreCase("Life")){
                image.setImageResource(R.drawable.life);
            }else {
                image.setImageResource(R.drawable.health2);
            }

            return view;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }

    private void logout(){
        startActivity(new Intent(ProvidersHome.this, LogIn.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprovider, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Logoutmenu: {
                logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

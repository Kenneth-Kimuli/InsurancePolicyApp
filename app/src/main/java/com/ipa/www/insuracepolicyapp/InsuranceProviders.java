package com.ipa.www.insuracepolicyapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ipa.www.insuracepolicyapp.Utils.LetterImageView;

public class InsuranceProviders extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_providers);
        setUpUiViews();
        initializeToolbar();
        setUpListView();

    }

    private void setUpUiViews(){
        toolbar = findViewById(R.id.tbInsuranceProviders);
        listView = findViewById(R.id.lvInsuranceProviders);
    }

    private void initializeToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Insurance Providers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpListView(){
        final String[] InsuranceProviders = getResources().getStringArray(R.array.InsuranceProviders);

        InsuranceProvidersAdapter adapter = new InsuranceProvidersAdapter(this, R.layout.activity_insurance_providers_single_item, InsuranceProviders);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: {
                        startActivity(new Intent(InsuranceProviders.this, PolicyDataRetrivalIPPS1Property.class));
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(InsuranceProviders.this, PolicyDataRetrivalIPPS2Property.class));
                        break;
                    }
                    case 2: break;
                    case 3: break;
                    case 4: break;
                    case 5: break;
                    default: break;
                }
            }
        });

    }

    public class InsuranceProvidersAdapter extends ArrayAdapter{
        //defining the contents of the array

        private int resource;
        //layout needed to be put into the listview
        private LayoutInflater layoutInflater;
        public String[] InsuranceProviders = new String[]{};

        //assigning public variables with local variables
        public InsuranceProvidersAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.InsuranceProviders = objects;
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;

            //convertView object of class View
            //first launch convertView is empty and viewHolder is created
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(resource, null);
                viewHolder.ivLogo = convertView.findViewById(R.id.ivletter);
                viewHolder.tvInsurer = convertView.findViewById(R.id.tvInsuranceProvider);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.ivLogo.setOval(true);
            viewHolder.ivLogo.setLetter(InsuranceProviders[position].charAt(0));
            viewHolder.tvInsurer.setText(InsuranceProviders[position]);

            return convertView;
        }

        //ViewHolder; box containing the elements in the cardview
        class ViewHolder{
            private LetterImageView ivLogo;
            private TextView tvInsurer;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

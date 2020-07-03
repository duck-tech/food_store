package com.example.foodstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.Tab.CategoryAdapter;
import com.example.foodstore.Tab.DataCategory;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private CategoryAdapter categoryAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String urlGetCate = "http://192.168.56.1/foodstore/getCategory.php";
    private ArrayList<DataCategory> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        getSupportActionBar().hide();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        list = new ArrayList<DataCategory>();

        getData(urlGetCate);
    }

    private void getData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET , url , null ,
            new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0 ; i < response.length() ; i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            list.add(new DataCategory(jsonObject.getInt("IdCate"),jsonObject.getString("NameCate")));;
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    categoryAdapter();
                }

            },
            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext() , error.toString() , Toast.LENGTH_LONG).show();
                }

            }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void categoryAdapter() {
        categoryAdapter = new CategoryAdapter(getSupportFragmentManager() , this , list);
        viewPager.setAdapter(categoryAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}

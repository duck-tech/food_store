package com.example.foodstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.AboutUs.AboutAcitivity;
import com.example.foodstore.CartShopping.CartShoppingActivity;
import com.example.foodstore.CartShopping.DataCartShopping;
import com.example.foodstore.ListOrder.ListOrderActivity;
import com.example.foodstore.Login.LoginActivity;
import com.example.foodstore.Tab.CategoryAdapter;
import com.example.foodstore.Tab.DataCategory;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<DataCartShopping> giohang;
    private CategoryAdapter categoryAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String urlGetCate = "http://192.168.56.1/foodstore/getCategory.php";
    private ArrayList<DataCategory> list;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private void initView() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        list = new ArrayList<DataCategory>();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
//        tool bar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.open , R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (giohang != null) {}
        else {
            giohang = new ArrayList<>();
        }

        getData(urlGetCate);
    }

//    lấy dữ liệu category
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
//  hiển thị list thức ăn khi chuyển tab
    private void categoryAdapter() {
        categoryAdapter = new CategoryAdapter(getSupportFragmentManager() , this , list);
        viewPager.setAdapter(categoryAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//       chọn item trong navigation
        switch (menuItem.getItemId()) {
            case R.id.list_order:
                startActivity(new Intent(MainActivity.this , ListOrderActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(MainActivity.this , AboutAcitivity.class));
                break;
            case R.id.exit:
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

//    hàm onclick của floatingActionButton
    public void intentItem(View view) {
        Intent intent = new Intent(MainActivity.this , CartShoppingActivity.class);
        startActivity(intent);
    }
}

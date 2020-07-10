package com.example.foodstore.ListOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.R;
import com.example.foodstore.Tab.DataCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOrderActivity extends AppCompatActivity {

    private RecyclerView rcvListOrder;
    private ArrayList<DataListOrder> list;
    private String url = "http://192.168.56.1/foodstore/getTable.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        list = new ArrayList<>();
        getList(url);
    }

    private void getList(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET , url , null ,
            new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                try {
                    for (int i = 0 ; i < response.length() ; i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        list.add(new DataListOrder(
                                jsonObject.getString("nameTable"),
                                jsonObject.getString("date"),
                                jsonObject.getInt("total"),
                                jsonObject.getInt("status")
                        ));;
                    }
                    initView();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void initView() {
        rcvListOrder = findViewById(R.id.rcv_list_order);
        rcvListOrder.setAdapter(new ListOrderAdapter(list , getApplicationContext()));
        rcvListOrder.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListOrder.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvListOrder.getContext() , DividerItemDecoration.VERTICAL);
        rcvListOrder.addItemDecoration(dividerItemDecoration);
        rcvListOrder.setItemAnimator(new DefaultItemAnimator());
    }

}

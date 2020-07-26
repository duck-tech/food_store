package com.example.foodstore.ListOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class Dialog extends AppCompatActivity {

    private Intent intent;
    private TextView tvname , tvstatus , tvdate , tvtotal;
    private ArrayList<DataListDialog> listDialogs;
    private RecyclerView recyclerView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
        getItem();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rcv_dialog_item);
        listDialogs = new ArrayList<>();
        btn = findViewById(R.id.btn_payment);
        intent = getIntent();
        tvname = findViewById(R.id.tv_dialog_name_table);
        tvdate = findViewById(R.id.tv_dialog_date);
        tvstatus = findViewById(R.id.tv_dialog_status);
        tvtotal = findViewById(R.id.tv_dialog_total);

        tvname.setText(intent.getStringExtra("name"));
        tvdate.setText(intent.getStringExtra("date"));
        tvtotal.setText(intent.getExtras().getInt("total")+" VND");
        if (intent.getExtras().getInt("status") == 1) {
            tvstatus.setText("Đã thanh toán");
            tvstatus.setTextColor(Color.GREEN);
            btn.setVisibility(View.INVISIBLE);
        }else {
            tvstatus.setText("Chưa thanh toán");
            tvstatus.setTextColor(Color.RED);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPayment();
            }
        });
    }

    private void getItem() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET , "http://192.168.56.1/foodstore/getOrderItem.php?id="+intent.getExtras().getInt("id") , null ,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0 ; i < response.length() ; i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            listDialogs.add(new DataListDialog(
                                jsonObject.getString("img"),
                                jsonObject.getString("name"),
                                jsonObject.getInt("price"),
                                jsonObject.getInt("amount")
                            ));
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    initRecyclerView();
                }
            },
            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext() , error.toString() , LENGTH_LONG).show();
                }

            }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(new DialogAdapter(this , listDialogs));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void clickPayment() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.56.1/foodstore/updatePayment.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("1")){
                        startActivity(new Intent(Dialog.this , ListOrderActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext() , "Lỗi!" , Toast.LENGTH_LONG).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext() , error.toString() , LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("id" , String.valueOf(intent.getExtras().getInt("id")));
                params.put("status" , String.valueOf(1));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}

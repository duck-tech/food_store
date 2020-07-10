package com.example.foodstore.CartShopping;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.MainActivity;
import com.example.foodstore.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartShoppingActivity extends AppCompatActivity {

    private Button btn_order;
    private EditText edtNameTable;
    public static TextView tvTotal;// dùng để truy cập đến ItemFoodAdapter khi thay đổi số lượng
    public static int idTable;
    private RecyclerView rcvItem;
    private Spinner spinner;
    private String urlTable = "http://192.168.56.1/foodstore/insertTable.php";
    private String urlItemFood = "http://192.168.56.1/foodstore/chitietdonhang.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_shopping);

        initView();
        EventTotal();
    }

    private void initView() {
        btn_order = findViewById(R.id.btn_order);
        edtNameTable = findViewById(R.id.edt_name_table);
        tvTotal = findViewById(R.id.tvtotal);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.status_array , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        rcvItem = findViewById(R.id.rcv_cart_shopping);
        rcvItem.setAdapter(new ItemFoodAdapter(getApplicationContext()));
        rcvItem.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvItem.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvItem.getContext() , DividerItemDecoration.VERTICAL);
        rcvItem.addItemDecoration(dividerItemDecoration);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!edtNameTable.getText().toString().isEmpty() && MainActivity.giohang != null) {
                createTable(urlTable);
            }else {
                Toast.makeText(getApplicationContext() , "Không thể đặt." ,Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    public static void EventTotal() {
        Locale localeVN = new Locale("vi", "VN");
        final NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        if (MainActivity.giohang == null) {
            tvTotal.setText(currencyVN.format(0));
        }else {
            int total = 0;
            for (int i = 0 ; i < MainActivity.giohang.size() ; i++) {
                total += MainActivity.giohang.get(i).getPrice() * MainActivity.giohang.get(i).getSoluong();
            }
            tvTotal.setText(currencyVN.format(total));
        }
    }

    private void createTable(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    if (!response.trim().equals("fail")) {
                        insertFoodItem(urlItemFood , response);
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getBaseContext() , error.toString() , Toast.LENGTH_LONG).show();
                }
            }
        ){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                params.put("date", dtf.format(now));
                params.put("name_table", edtNameTable.getText().toString());
                String status = spinner.getSelectedItem().toString().trim().equals("Đã thanh toán") ? "1" : "0";
                params.put("status", status);
                int total = 0;
                for (int i = 0 ; i < MainActivity.giohang.size() ; i++) {
                    total += MainActivity.giohang.get(i).getPrice() * MainActivity.giohang.get(i).getSoluong();
                }
                params.put("total", String.valueOf(total));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void insertFoodItem(String url, final String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")) {
                            MainActivity.giohang.clear();
                            Toast.makeText(getApplicationContext() , "Bạn đã đặt thành công!" , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CartShoppingActivity.this , MainActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext() , error.toString() , Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0 ; i < MainActivity.giohang.size() ; i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("orderId" , id);
                        jsonObject.put("foodId" , MainActivity.giohang.get(i).getIdFood());
                        jsonObject.put("amount" , MainActivity.giohang.get(i).getSoluong());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                HashMap<String , String> hashMap = new HashMap<>();
                hashMap.put("json" , jsonArray.toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
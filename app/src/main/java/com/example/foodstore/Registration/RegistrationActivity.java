package com.example.foodstore.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.Login.LoginActivity;
import com.example.foodstore.MainActivity;
import com.example.foodstore.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextUsername;
    private EditText editTextPass;
    private Button buttonRegistration;
    private String urlInsert = "http://192.168.56.1/foodstore/insertUser.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();
    }

    private void initView() {
        editTextName = findViewById(R.id.output_name);
        editTextPhone = findViewById(R.id.output_phone);
        editTextUsername = findViewById(R.id.output_username);
        editTextPass = findViewById(R.id.output_password);
        buttonRegistration = findViewById(R.id.btn_registration);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty() || editTextPhone.getText().toString().isEmpty()
                || editTextUsername.getText().toString().isEmpty() ) {
                    Toast.makeText(RegistrationActivity.this , "Nhập đầy đủ thông tin" , Toast.LENGTH_LONG).show();
                }
                else {
                    insertUser(urlInsert);
                }
            }
        });
    }

    private void insertUser(String urlInsert) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("success")) {
                        Toast.makeText(RegistrationActivity.this, "Tạo thành công" , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this , LoginActivity.class));
                    }else {
                        Toast.makeText(RegistrationActivity.this, "Lỗi tạo tài khoản" , Toast.LENGTH_LONG).show();
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("name_user", editTextName.getText().toString().trim());
                params.put("phone", editTextPhone.getText().toString().trim());
                params.put("username", editTextUsername.getText().toString().trim());
                params.put("password", editTextPass.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}

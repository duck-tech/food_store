package com.example.foodstore.Tab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodstore.Food.DataFood;
import com.example.foodstore.Food.FoodAdapter;
import com.example.foodstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    private View mView;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private ArrayList<DataFood> allFoods;
    private int idCate;
    private Context mContext;
    private String url = "http://192.168.56.1/foodstore/getFood.php";

    public CategoryFragment() {}

    public CategoryFragment(int idCate , Context context) {
        this.idCate = idCate;
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_category , container , false );

        allFoods = new ArrayList<DataFood>();

        getFood(url);

        return mView;
    }

    private void initView() {
        recyclerView = mView.findViewById(R.id.recyclerview);
        foodAdapter = new FoodAdapter(allFoods);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void getFood(String url) {
        RequestQueue requestQueuef = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequestf = new JsonArrayRequest(Request.Method.GET , url , null ,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                try {
                    for (int i = 0 ; i < response.length() ; i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (jsonObject.getInt("idCate") == idCate) {
                            allFoods.add(new DataFood(
                                jsonObject.getInt("id"),
                                jsonObject.getString("nameFood"),
                                jsonObject.getString("imageFood"),
                                jsonObject.getInt("price"),
                                jsonObject.getInt("idCate")
                            ));
                        }
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
                    Toast.makeText(mContext , error.toString() , Toast.LENGTH_LONG).show();
                }
            }
        );
        requestQueuef.add(jsonArrayRequestf);
    }
}

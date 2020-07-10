package com.example.foodstore.Food;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodstore.CartShopping.DataCartShopping;
import com.example.foodstore.MainActivity;
import com.example.foodstore.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private ArrayList<DataFood> dataFoods;
    private Context mContext;

    public FoodAdapter(ArrayList<DataFood> dataFoods, Context context) {
        this.dataFoods = dataFoods;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_food , parent , false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        chuyển kiểu int thành kiểu chuổi tiền tệ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(dataFoods.get(position).getPrice());

        final byte[] decodedString = Base64.decode(dataFoods.get(position).getImageFood(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.ivFood.setImageBitmap(imgBitMap);
        holder.tvFood.setText(dataFoods.get(position).getNameFood());
        holder.tvPrice.setText(price);
        holder.btnDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.giohang.size() > 0) {
                    boolean check = false;
                    for (int i = 0 ; i < MainActivity.giohang.size() ; i++) {
                        if (MainActivity.giohang.get(i).getIdFood() == dataFoods.get(position).getIdFood()) {
                            MainActivity.giohang.get(i).setSoluong(MainActivity.giohang.get(i).getSoluong() + 1);
                            check = true;
                        }
                    }
                    if (check == false) {
                        MainActivity.giohang.add(new DataCartShopping(
                                dataFoods.get(position).getIdFood(),
                                dataFoods.get(position).getImageFood(),
                                dataFoods.get(position).getNameFood(),
                                dataFoods.get(position).getPrice(),
                                1
                        ));
                    }
                }else {
                    MainActivity.giohang.add(new DataCartShopping(
                            dataFoods.get(position).getIdFood(),
                            dataFoods.get(position).getImageFood(),
                            dataFoods.get(position).getNameFood(),
                            dataFoods.get(position).getPrice(),
                            1
                    ));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
       return dataFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFood;
        TextView tvFood;
        TextView tvPrice;
        Button btnDatMon;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.image_food);
            tvFood = itemView.findViewById(R.id.name_food);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnDatMon = itemView.findViewById(R.id.btn_add);

        }
    }
}

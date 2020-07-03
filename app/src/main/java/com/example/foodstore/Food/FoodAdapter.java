package com.example.foodstore.Food;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.MainActivity;
import com.example.foodstore.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private ArrayList<DataFood> dataFoods;

    public FoodAdapter(ArrayList<DataFood> dataFoods) {
        this.dataFoods = dataFoods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_food , parent , false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//        chuyển kiểu int thành kiểu chuổi tiền tệ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(dataFoods.get(position).getPrice());

        final byte[] decodedString = Base64.decode(dataFoods.get(position).getImageFood(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.ivFood.setImageBitmap(imgBitMap);
        holder.tvFood.setText(dataFoods.get(position).getNameFood());
        holder.tvPrice.setText(price);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt((String) holder.tvNumber.getText());
                index++;
                holder.tvNumber.setText(String.valueOf(index));
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt((String) holder.tvNumber.getText());
                if (index > 0) index--;
                holder.tvNumber.setText(String.valueOf(index));
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
        Button btnAdd;
        Button btnMinus;
        TextView tvNumber;
        TextView tvPrice;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ivFood = (ImageView) itemView.findViewById(R.id.image_food);
            tvFood = (TextView) itemView.findViewById(R.id.name_food);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            btnAdd = (Button) itemView.findViewById(R.id.btn_add);
            btnMinus = (Button) itemView.findViewById(R.id.btn_minus);

        }
    }
}

package com.example.foodstore.CartShopping;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.MainActivity;
import com.example.foodstore.R;

import java.text.NumberFormat;
import java.util.Locale;

public class ItemFoodAdapter extends RecyclerView.Adapter<ItemFoodAdapter.ViewHolder> {

    Context mContext;

    public ItemFoodAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_cart_shopping , parent , false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final byte[] decodedString = Base64.decode(MainActivity.giohang.get(position).getImageFood(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Locale localeVN = new Locale("vi", "VN");
        final NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);


        holder.imgFoodItem.setImageBitmap(imgBitMap);
        holder.tvNameFoodItem.setText(MainActivity.giohang.get(position).getNameFood());
        holder.tvSoLuong.setText(String.valueOf(MainActivity.giohang.get(position).getSoluong()));
        int price = MainActivity.giohang.get(position).getPrice() * MainActivity.giohang.get(position).getSoluong();
        String stringPrice = currencyVN.format(price);
        holder.tvPrice.setText(stringPrice);
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(holder.tvSoLuong.getText().toString());
                if (index > 0 ) {
                    index--;
                    String setPrice = currencyVN.format(MainActivity.giohang.get(position).getPrice() * index);
                    holder.tvPrice.setText(setPrice);
                    MainActivity.giohang.get(position).setSoluong(index);
                    if (index == 0) MainActivity.giohang.remove(position);
                }
                holder.tvSoLuong.setText(String.valueOf(index));
                CartShoppingActivity.EventTotal();
            }
        });
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(holder.tvSoLuong.getText().toString());
                index++;
                String setPrice = currencyVN.format(MainActivity.giohang.get(position).getPrice() * index);
                holder.tvPrice.setText(setPrice);
                MainActivity.giohang.get(position).setSoluong(index);
                holder.tvSoLuong.setText(String.valueOf(index));
                CartShoppingActivity.EventTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.giohang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFoodItem;
        TextView tvNameFoodItem;
        TextView tvSoLuong;
        TextView tvPrice;
        Button btnTang;
        Button btnGiam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoodItem = itemView.findViewById(R.id.image_food_item);
            tvNameFoodItem = itemView.findViewById(R.id.tv_name_food_item);
            tvSoLuong = itemView.findViewById(R.id.tv_soluong);
            tvPrice = itemView.findViewById(R.id.tv_price_food_item);
            btnGiam = itemView.findViewById(R.id.btn_giamsl);
            btnTang = itemView.findViewById(R.id.btn_tangsl);
        }
    }
}

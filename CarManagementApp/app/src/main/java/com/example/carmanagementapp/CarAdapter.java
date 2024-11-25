package com.example.carmanagementapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.tvCarName.setText(car.getName());
        holder.tvManufacturer.setText(car.getManufacturer());
        holder.tvPrice.setText(CurrencyFormatter.formatCurrency(car.getPrice())); // Định dạng giá tiền

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CarDetailActivity.class);
            intent.putExtra("carId", car.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditCarActivity.class);
            intent.putExtra("carId", car.getId());
            ((MainActivity) v.getContext()).startActivityForResult(intent, MainActivity.REQUEST_CODE_EDIT_CAR);
        });

        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            apiService.deleteCar(car.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        carList.remove(currentPosition);
                        notifyItemRemoved(currentPosition);
                        Toast.makeText(v.getContext(), "Car deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Failed to delete car", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(v.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }



    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView tvCarName, tvManufacturer, tvPrice;
        ImageView btnEdit, btnDelete; // Thay nút bằng icon

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvManufacturer = itemView.findViewById(R.id.tvManufacturer);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit); // Icon sửa
            btnDelete = itemView.findViewById(R.id.btnDelete); // Icon xóa
        }
    }

}

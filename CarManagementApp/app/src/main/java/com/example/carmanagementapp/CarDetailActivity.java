package com.example.carmanagementapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetailActivity extends AppCompatActivity {
    private TextView tvName, tvManufacturer, tvYear, tvPrice, tvDescription;
    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        tvName = findViewById(R.id.tvName);
        tvManufacturer = findViewById(R.id.tvManufacturer);
        tvYear = findViewById(R.id.tvYear);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);

        carId = getIntent().getStringExtra("carId");
        if (carId != null) {
            fetchCarDetails(carId);
        } else {
            Toast.makeText(this, "No car ID provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCarDetails(String carId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCarById(carId).enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Car car = response.body();
                    tvName.setText(car.getName());
                    tvManufacturer.setText(car.getManufacturer());
                    tvYear.setText(String.valueOf(car.getYear()));
                    tvPrice.setText(CurrencyFormatter.formatCurrency(car.getPrice())); // Định dạng giá tiền
                    tvDescription.setText(car.getDescription());
                } else {
                    Toast.makeText(CarDetailActivity.this, "Failed to fetch car details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CarDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

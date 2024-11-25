package com.example.carmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCarActivity extends AppCompatActivity {
    private EditText etName, etManufacturer, etYear, etPrice, etDescription;
    private Button btnSave;
    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Ánh xạ các thành phần giao diện
        etName = findViewById(R.id.etName);
        etManufacturer = findViewById(R.id.etManufacturer);
        etYear = findViewById(R.id.etYear);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        // Nhận ID của xe từ Intent
        carId = getIntent().getStringExtra("carId");
        loadCarDetails(carId);

        // Xử lý lưu sau khi chỉnh sửa
        btnSave.setOnClickListener(v -> updateCar());
    }

    private void loadCarDetails(String carId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCarById(carId).enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Car car = response.body();
                    etName.setText(car.getName());
                    etManufacturer.setText(car.getManufacturer());
                    etYear.setText(String.valueOf(car.getYear()));
                    etPrice.setText(String.valueOf(car.getPrice())); // Hiển thị giá không định dạng
                    etDescription.setText(car.getDescription());
                } else {
                    Toast.makeText(EditCarActivity.this, "Failed to fetch car details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditCarActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateCar() {
        String name = etName.getText().toString().trim();
        String manufacturer = etManufacturer.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(manufacturer) || TextUtils.isEmpty(yearStr)
                || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearStr);
        double price = Double.parseDouble(priceStr);

        Car updatedCar = new Car(name, manufacturer, year, price, description);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.updateCar(carId, updatedCar).enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedCar", response.body()); // Trả xe đã sửa về MainActivity
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(EditCarActivity.this, "Failed to update car", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditCarActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

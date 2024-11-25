package com.example.carmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList = new ArrayList<>();
    private FloatingActionButton fabAddCar;

    private static final int REQUEST_CODE_ADD_CAR = 100;
    public static final int REQUEST_CODE_EDIT_CAR = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ RecyclerView và FAB
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAddCar = findViewById(R.id.fabAddCar);
        fabAddCar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_CAR); // Mở AddCarActivity
        });

        // Thiết lập adapter và tải danh sách xe
        carAdapter = new CarAdapter(carList);
        recyclerView.setAdapter(carAdapter);

        fetchCars(); // Gọi API để lấy danh sách xe
    }

    private void fetchCars() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getCars("").enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    carList.clear();
                    carList.addAll(response.body());
                    carAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch cars", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_CAR && resultCode == RESULT_OK && data != null) {
            Car newCar = (Car) data.getSerializableExtra("newCar");
            if (newCar != null) {
                carList.add(0, newCar); // Thêm xe mới vào đầu danh sách
                carAdapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0); // Cuộn lên đầu danh sách
            }
        } else if (requestCode == REQUEST_CODE_EDIT_CAR && resultCode == RESULT_OK && data != null) {
            Car updatedCar = (Car) data.getSerializableExtra("updatedCar");
            if (updatedCar != null) {
                for (int i = 0; i < carList.size(); i++) {
                    if (carList.get(i).getId().equals(updatedCar.getId())) {
                        carList.set(i, updatedCar); // Cập nhật xe trong danh sách
                        carAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }




}

package com.example.carmanagementapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("cars")
    Call<List<Car>> getCars(@Query("search") String search); // Lấy danh sách xe (tìm kiếm)

    @GET("cars/{id}")
    Call<Car> getCarById(@Path("id") String id); // Lấy chi tiết xe

    @POST("cars")
    Call<Car> createCar(@Body Car car); // API tạo xe mới

    @PUT("cars/{id}")
    Call<Car> updateCar(@Path("id") String id, @Body Car car); // API cập nhật thông tin xe

    @DELETE("cars/{id}")
    Call<Void> deleteCar(@Path("id") String id);

}


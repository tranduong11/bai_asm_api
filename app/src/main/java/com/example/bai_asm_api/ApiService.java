package com.example.bai_asm_api;


import com.example.bai_asm_api.Car;
import com.example.bai_asm_api.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/register")
    Call<User> register(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);

    @GET("/")
    Call<List<Car>> getCars();

    @POST("/add_xe")
    Call<List<Car>> addCar(@Body Car car);

    @DELETE("/xoa/{id}")
    Call<List<Car>> deleteCar(@Path("id") String id);
}


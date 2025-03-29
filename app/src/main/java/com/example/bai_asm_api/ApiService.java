package com.example.bai_asm_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
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

    @PUT("/cap_nhat/{id}")
    Call<Car> updateCar(@Path("id") String id, @Body Car car);

    @DELETE("/xoa/{id}")
    Call<List<Car>> deleteCar(@Path("id") String id);
}


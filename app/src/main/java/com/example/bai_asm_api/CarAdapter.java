package com.example.bai_asm_api;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai_asm_api.Car;
import com.example.bai_asm_api.ApiService;
import com.example.bai_asm_api.RetrofitClient;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.tvCarName.setText(car.getTen());

        holder.btnDeleteCar.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getApiService();
            apiService.deleteCar(car.get_id()).enqueue(new Callback<List<Car>>() {
                @Override
                public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                    carList.remove(position);
                    notifyItemRemoved(position);
                }

                @Override
                public void onFailure(Call<List<Car>> call, Throwable t) {}
            });
        });

        holder.btnEditCar.setOnClickListener(v -> {
            car.setTen(car.getTen() + " - Bản Mới");
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView tvCarName;
        Button btnEditCar, btnDeleteCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarName = itemView.findViewById(R.id.tvCarName);
            btnEditCar = itemView.findViewById(R.id.btnEditCar);
            btnDeleteCar = itemView.findViewById(R.id.btnDeleteCar);
        }
    }
}


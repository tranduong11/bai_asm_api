package com.example.bai_asm_api;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;
    private ActivityResultLauncher<Intent> activityResultLauncher; // 🟢 Dùng để mở AddEditCarActivity và nhận kết quả

    public CarAdapter(List<Car> carList, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.carList = carList;
        this.activityResultLauncher = activityResultLauncher;
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
        holder.tvCarBrand.setText("Hãng: " + car.getHang());
        holder.tvCarPrice.setText("Giá: " + car.getGia() + " VNĐ");

        // 🟢 Xử lý khi nhấn vào nút Sửa
        holder.btnEditCar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddEditCarActivity.class);
            intent.putExtra("car", car); // Truyền dữ liệu xe sang màn hình sửa
            activityResultLauncher.launch(intent); // 🟢 Mở activity với kết quả trả về
        });

        // 🟢 Xử lý khi nhấn vào nút Xóa
        holder.btnDeleteCar.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getApiService();
            apiService.deleteCar(car.get_id()).enqueue(new Callback<List<Car>>() {
                @Override
                public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                    carList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(v.getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<Car>> call, Throwable t) {
                    Toast.makeText(v.getContext(), "Lỗi xóa xe!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void updateCarList(List<Car> newCarList) {
        carList.clear();
        carList.addAll(newCarList);
        notifyDataSetChanged();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView tvCarName, tvCarBrand, tvCarPrice;
        Button btnEditCar, btnDeleteCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvCarBrand = itemView.findViewById(R.id.tvCarBrand);
            tvCarPrice = itemView.findViewById(R.id.tvCarPrice);
            btnEditCar = itemView.findViewById(R.id.btnEditCar);
            btnDeleteCar = itemView.findViewById(R.id.btnDeleteCar);
        }
    }
}

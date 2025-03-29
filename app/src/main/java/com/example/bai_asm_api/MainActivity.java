package com.example.bai_asm_api;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CarAdapter adapter;
    Button btnAddCar;

    // 🟢 ActivityResultLauncher để nhận kết quả sau khi thêm/sửa xe
    private final ActivityResultLauncher<Intent> addEditCarLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadCarList(); // Load lại danh sách khi có thay đổi
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddCar = findViewById(R.id.btnAddCar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddCar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditCarActivity.class);
            addEditCarLauncher.launch(intent); // 🟢 Dùng ActivityResultLauncher thay vì startActivityForResult()
        });

        loadCarList();
    }

    private void loadCarList() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (adapter == null) {
                        adapter = new CarAdapter(response.body(), addEditCarLauncher); // 🟢 Truyền ActivityResultLauncher vào Adapter
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.updateCarList(response.body()); // Cập nhật danh sách
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Không có dữ liệu xe!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối đến server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

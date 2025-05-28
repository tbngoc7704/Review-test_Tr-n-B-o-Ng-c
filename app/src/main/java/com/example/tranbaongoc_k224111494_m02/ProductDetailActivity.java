package com.example.tranbaongoc_k224111494_m02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tranbaongoc_k224111494_m02.models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    EditText edt_product_id;
    EditText edt_product_code;
    EditText edt_product_name;
    EditText edt_product_price;
    Button btnNew;
    Button btnSave;
    Button btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail); // giữ giao diện XML như CustomerDetail

        edt_product_id = findViewById(R.id.edt_product_id);
        edt_product_code = findViewById(R.id.edt_product_code);
        edt_product_name = findViewById(R.id.edt_product_name);
        edt_product_price = findViewById(R.id.edt_product_price);

        btnNew = findViewById(R.id.btnNew);
        btnSave = findViewById(R.id.btnSave);
        btnRemove = findViewById(R.id.btnRemove);

        Intent intent = getIntent();
        Product p = (Product) intent.getSerializableExtra("SELECTED_PRODUCT");
        if (p != null) {
            // Hiển thị chi tiết sản phẩm khi mở từ danh sách
            edt_product_id.setText(String.valueOf(p.getId()));
            edt_product_code.setText(p.getProductCode());
            edt_product_name.setText(p.getProductName());
            edt_product_price.setText(String.valueOf(p.getUnitPrice()));
        }

        btnSave.setOnClickListener(v -> {
            String idStr = edt_product_id.getText().toString().trim();
            String code = edt_product_code.getText().toString().trim();
            String name = edt_product_name.getText().toString().trim();
            String priceStr = edt_product_price.getText().toString().trim();

            // Kiểm tra các trường không được để trống
            if (idStr.isEmpty() || code.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tất cả các trường!", Toast.LENGTH_SHORT).show();
                return;
            }

            int id;
            double price;

            // Kiểm tra định dạng số
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID phải là số nguyên hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá phải là số hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo sản phẩm mới
            Product newProduct = new Product(id, code, name, price, "");

            // Trả về kết quả cho MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_PRODUCT", newProduct);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        btnNew.setOnClickListener(v -> {
            // Xóa trắng form để nhập mới
            edt_product_id.setText("");
            edt_product_code.setText("");
            edt_product_name.setText("");
            edt_product_price.setText("");
        });

        btnRemove.setOnClickListener(v -> {
            // Có thể làm chức năng xóa nếu muốn
            // Nếu không, bạn có thể ẩn nút này khi thêm mới
        });
    }
}

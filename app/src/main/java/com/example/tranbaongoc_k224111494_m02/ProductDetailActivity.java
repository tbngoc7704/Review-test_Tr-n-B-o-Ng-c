package com.example.tranbaongoc_k224111494_m02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tranbaongoc_k224111494_m02.models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    EditText edt_product_id;
    EditText edt_product_code;
    EditText edt_product_name;
    EditText edt_product_price;
    EditText edt_product_imagelink;
    Button btnNew;
    Button btnSave;
    Button btnRemove;

    int type = 0; // 0: thêm mới, 1: cập nhật

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        addViews();
        addEvents();
    }

    private void addViews() {
        edt_product_id = findViewById(R.id.edt_product_id);
        edt_product_code = findViewById(R.id.edt_product_code);
        edt_product_name = findViewById(R.id.edt_product_name);
        edt_product_price = findViewById(R.id.edt_product_price);

        // Tìm EditText cho ImageURL - có thể null nếu không có trong layout


        btnNew = findViewById(R.id.btnNew);
        btnSave = findViewById(R.id.btnSave);
        btnRemove = findViewById(R.id.btnRemove);

        display_product_details();
    }

    private void addEvents() {
        btnNew.setOnClickListener(v -> do_new());
        btnSave.setOnClickListener(v -> do_save());
        btnRemove.setOnClickListener(v -> do_remove());
    }

    private void display_product_details() {
        Intent intent = getIntent();
        type = intent.getIntExtra("TYPE", 1);
        Product product = (Product) intent.getSerializableExtra("SELECTED_PRODUCT");

        if (product == null) {
            // Mode thêm mới - không hiển thị ID vì tự động generate
            edt_product_id.setText("");
            edt_product_id.setEnabled(false); // Disable ID field vì auto generate
            edt_product_code.setText("");
            edt_product_name.setText("");
            edt_product_price.setText("");

            // ImageURL không bắt buộc - có thể để trống
            if (edt_product_imagelink != null) {
                edt_product_imagelink.setText("");
            }
            return;
        }

        // Mode cập nhật - hiển thị thông tin sản phẩm
        edt_product_id.setText(String.valueOf(product.getId()));
        edt_product_id.setEnabled(false); // ID không được chỉnh sửa
        edt_product_code.setText(product.getProductCode());
        edt_product_name.setText(product.getProductName());
        edt_product_price.setText(String.valueOf(product.getUnitPrice()));

        // ImageURL không bắt buộc - hiển thị nếu có
        if (edt_product_imagelink != null) {
            String imageLink = product.getImageLink();
            edt_product_imagelink.setText(imageLink != null ? imageLink : "");
        }
    }

    private void do_new() {
        // Clear form để nhập mới
        edt_product_id.setText("");
        edt_product_code.setText("");
        edt_product_name.setText("");
        edt_product_price.setText("");

        // ImageURL không bắt buộc - có thể để trống
        if (edt_product_imagelink != null) {
            edt_product_imagelink.setText("");
        }
        type = 0; // Chuyển sang mode thêm mới
    }

    private void do_save() {
        // Validate input - chỉ validate các field bắt buộc
        String code = edt_product_code.getText().toString().trim();
        String name = edt_product_name.getText().toString().trim();
        String priceStr = edt_product_price.getText().toString().trim();

        if (code.isEmpty()) {
            edt_product_code.setError("Vui lòng nhập mã sản phẩm");
            edt_product_code.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            edt_product_name.setError("Vui lòng nhập tên sản phẩm");
            edt_product_name.requestFocus();
            return;
        }

        if (priceStr.isEmpty()) {
            edt_product_price.setError("Vui lòng nhập giá sản phẩm");
            edt_product_price.requestFocus();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                edt_product_price.setError("Giá sản phẩm phải lớn hơn hoặc bằng 0");
                edt_product_price.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            edt_product_price.setError("Giá sản phẩm không hợp lệ");
            edt_product_price.requestFocus();
            return;
        }

        Product product = new Product();

        // Chỉ set ID nếu đang ở mode update
        if (type == 1) {
            String idStr = edt_product_id.getText().toString().trim();
            if (!idStr.isEmpty()) {
                try {
                    product.setId(Integer.parseInt(idStr));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        // Với mode thêm mới (type = 0), ID sẽ được database tự động generate

        product.setProductCode(code);
        product.setProductName(name);
        product.setUnitPrice(price);

        // ImageURL không bắt buộc - có thể null hoặc empty
        String imageLink = "";
        if (edt_product_imagelink != null) {
            String inputImageLink = edt_product_imagelink.getText().toString().trim();
            imageLink = inputImageLink.isEmpty() ? "" : inputImageLink;
        }
        product.setImageLink(imageLink);

        // Trả kết quả về MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_PRODUCT", product);
        resultIntent.putExtra("TYPE", type); // 0 = new, 1 = update
        setResult(1000, resultIntent);
        finish();
    }

    private void do_remove() {
        String idStr = edt_product_id.getText().toString().trim();
        if (idStr.isEmpty() || type == 0) {
            Toast.makeText(this, "Không có sản phẩm để xóa", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int productId = Integer.parseInt(idStr);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("PRODUCT_ID_REMOVE", productId);
            setResult(9000, resultIntent);
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
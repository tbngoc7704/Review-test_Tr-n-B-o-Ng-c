package com.example.tranbaongoc_k224111494_m02;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tranbaongoc_k224111494_m02.adapters.ProductAdapter;
import com.example.tranbaongoc_k224111494_m02.models.ListProduct;
import com.example.tranbaongoc_k224111494_m02.models.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvProducts;
    ProductAdapter adapter;
    ArrayList<Product> products;
    private static final int REQUEST_CODE_ADD_PRODUCT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProducts = findViewById(R.id.lvProducts);

        ListProduct listProduct = new ListProduct();
        listProduct.generate_sample_dataset();
        products = listProduct.getProducts();
        adapter = new ProductAdapter(this, R.layout.item_product, products);
        lvProducts.setAdapter(adapter);
        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = products.get(position);
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("SELECTED_PRODUCT", selectedProduct);
            startActivity(intent);
        });
    }
    // Gắn menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    // Xử lý khi người dùng chọn item trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_new_product) {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT); // để nhận lại kết quả
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "Thông tin sinh viên", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode == RESULT_OK) {
            Product p = (Product) data.getSerializableExtra("NEW_PRODUCT");
            if (p != null) {
                products.add(p);  // bạn đang dùng products, không phải lp nhé
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

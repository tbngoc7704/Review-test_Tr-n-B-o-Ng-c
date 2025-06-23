package com.example.tranbaongoc_k224111494_m02;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tranbaongoc_k224111494_m02.adapters.ProductAdapter;
import com.example.tranbaongoc_k224111494_m02.models.Product;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvProducts;
    ProductAdapter adapter;
    ArrayList<Product> products;

    // SQLite Database constants
    String DATABASE_NAME = "SalesDatabase.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    // Request codes
    final int ID_CREATE_NEW_PRODUCT = 1;
    final int ID_UPDATE_PRODUCT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDatabase();
        addViews();
        addEvents();
    }

    private void openDatabase() {
        String path = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        File dbFile = new File(path);
        if (!dbFile.getParentFile().exists()) {
            dbFile.getParentFile().mkdirs();
        }
        database = SQLiteDatabase.openOrCreateDatabase(path, null);

        // Tạo bảng Products nếu chưa tồn tại
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Products (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProductCode TEXT, " +
                "ProductName TEXT, " +
                "UnitPrice REAL, " +
                "ImageLink TEXT)";
        database.execSQL(createTableQuery);
    }

    private void addViews() {
        lvProducts = findViewById(R.id.lvProducts);

        // Load products from database
        loadProductsFromDatabase();

        adapter = new ProductAdapter(this, R.layout.item_product, products);
        lvProducts.setAdapter(adapter);
    }

    private void loadProductsFromDatabase() {
        products = new ArrayList<>();
        String query = "SELECT * FROM Products ORDER BY Id DESC";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow("Id")));
                product.setProductCode(cursor.getString(cursor.getColumnIndexOrThrow("ProductCode")));
                product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow("ProductName")));
                product.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("UnitPrice")));

                String imageLink = cursor.getString(cursor.getColumnIndexOrThrow("ImageLink"));
                product.setImageLink(imageLink != null ? imageLink : "");

                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void addEvents() {
        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = products.get(position);
            openProductDetailActivity(selectedProduct);
        });
    }

    private void openProductDetailActivity(Product product) {
        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
        intent.putExtra("SELECTED_PRODUCT", product);
        intent.putExtra("TYPE", 1); // 1 = cập nhật
        startActivityForResult(intent, ID_UPDATE_PRODUCT);
    }

    private void openNewProductActivity() {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("TYPE", 0); // 0 = thêm mới
        startActivityForResult(intent, ID_CREATE_NEW_PRODUCT);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_new_product) {
            openNewProductActivity();
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "Thông tin sinh viên", Toast.LENGTH_SHORT).show();
            openAboutActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == ID_CREATE_NEW_PRODUCT || requestCode == ID_UPDATE_PRODUCT)
                && resultCode == 1000 && data != null) {
            Product product = (Product) data.getSerializableExtra("NEW_PRODUCT");
            int type = data.getIntExtra("TYPE", 1); // 0 = new, 1 = update
            if (type == 0) {
                process_save_product(product);
            } else {
                process_save_update_product(product);
            }
        } else if ((requestCode == ID_CREATE_NEW_PRODUCT || requestCode == ID_UPDATE_PRODUCT)
                && resultCode == 9000 && data != null) {
            int productId = data.getIntExtra("PRODUCT_ID_REMOVE", -1);
            if (productId != -1) {
                process_remove_product(productId);
            }
        }
    }

    private void process_save_product(Product product) {
        ContentValues values = new ContentValues();
        // Không cần thêm ID vì database tự động generate
        values.put("ProductCode", product.getProductCode());
        values.put("ProductName", product.getProductName());
        values.put("UnitPrice", product.getUnitPrice());

        // Xử lý ImageURL có thể null hoặc empty - không bắt buộc nhập
        String imageLink = product.getImageLink();
        if (imageLink == null || imageLink.trim().isEmpty()) {
            imageLink = ""; // Set empty string nếu null hoặc rỗng
        }
        values.put("ImageLink", imageLink);

        long id = database.insert("Products", null, values);
        if (id > 0) {
            refreshProductList();
            Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi thêm sản phẩm!", Toast.LENGTH_SHORT).show();
        }
    }

    private void process_save_update_product(Product product) {
        ContentValues values = new ContentValues();
        values.put("ProductCode", product.getProductCode());
        values.put("ProductName", product.getProductName());
        values.put("UnitPrice", product.getUnitPrice());

        // Xử lý ImageURL có thể null hoặc empty - không bắt buộc nhập
        String imageLink = product.getImageLink();
        if (imageLink == null || imageLink.trim().isEmpty()) {
            imageLink = ""; // Set empty string nếu null hoặc rỗng
        }
        values.put("ImageLink", imageLink);

        int rowsAffected = database.update("Products", values, "Id=?",
                new String[]{String.valueOf(product.getId())});
        if (rowsAffected > 0) {
            refreshProductList();
            Toast.makeText(this, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật sản phẩm!", Toast.LENGTH_SHORT).show();
        }
    }

    private void process_remove_product(int productId) {
        int rowsAffected = database.delete("Products", "Id=?",
                new String[]{String.valueOf(productId)});
        if (rowsAffected > 0) {
            refreshProductList();
            Toast.makeText(this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi xóa sản phẩm!", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshProductList() {
        loadProductsFromDatabase();
        adapter.setProducts(products);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
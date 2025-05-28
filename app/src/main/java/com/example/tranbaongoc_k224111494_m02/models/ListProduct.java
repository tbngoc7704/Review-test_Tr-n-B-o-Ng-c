package com.example.tranbaongoc_k224111494_m02.models;

import java.util.ArrayList;

public class ListProduct {
    private ArrayList<Product> products;

    public ListProduct() {
        products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void generate_sample_dataset() {
        products = new ArrayList<>();

        Product p1 = new Product(1, "P001", "Shampoo", 50.0, "https://salt.tikicdn.com/cache/750x750/ts/product/53/3c/a5/8507f04633ad1396bef309464e647229.jpg.webp");
        Product p2 = new Product(2, "P002", "Conditioner", 40.0, "https://salt.tikicdn.com/cache/750x750/ts/product/ac/c6/cb/37303319856d25885bb1a819edf8044a.jpg.webp");
        Product p3 = new Product(3, "P003", "Toothpaste", 25.0, "https://salt.tikicdn.com/cache/750x750/ts/product/e7/dd/9b/6936cd2190f819d73e2df85004256e54.jpeg.webp");
        Product p4 = new Product(4, "P004", "Toothbrush", 15.0, "https://salt.tikicdn.com/cache/750x750/ts/product/16/d7/d4/f748aefd7e0177f0b02179f15c3507ad.jpeg.webp");
        Product p5 = new Product(5, "P005", "Handwash", 20.0, "https://salt.tikicdn.com/cache/750x750/ts/product/8a/14/6c/8d704d37455ce7d9dc33ac78c4b5cfb7.png.webp");
        Product p6 = new Product(6, "P006", "Body Lotion", 60.0, "https://salt.tikicdn.com/cache/750x750/ts/product/b4/80/b0/0f5c14765c125d623b847c1795ac4d18.jpg.webp");
        Product p7 = new Product(7, "P007", "Face Wash", 35.0, "https://salt.tikicdn.com/cache/750x750/ts/product/40/24/19/c3acaae8985dde7536c7dd3fa33517fe.jpg.webp");
        Product p8 = new Product(8, "P008", "Face Mask", 45.0, "https://salt.tikicdn.com/cache/750x750/ts/product/2b/93/ee/b7e26fdcc2474d0b1abc263504188241.jpg.webp");
        Product p9 = new Product(9, "P009", "Lipstick", 90.0, "https://salt.tikicdn.com/cache/750x750/ts/product/de/d5/0c/9089fbeb2471cc556ae3c63d3ace91a1.jpg.webp");
        Product p10 = new Product(10, "P010", "Eyeliner", 70.0, "https://salt.tikicdn.com/cache/750x750/ts/product/7b/5f/43/cbae5df3db6ced4041112090a8420419.jpg.webp");
        Product p11 = new Product(11, "P011", "Moisturizer", 85.0, "https://salt.tikicdn.com/cache/750x750/ts/product/38/1c/5c/bd215f98235eb29293c5c2e2447792ac.jpg.webp");
        Product p12 = new Product(12, "P012", "Perfume", 150.0, "https://salt.tikicdn.com/cache/750x750/ts/product/e5/23/f7/2eded9d7c15638122f5871b37c903a8d.jpg.webp");
        Product p13 = new Product(13, "P013", "Sunscreen", 55.0, "https://salt.tikicdn.com/cache/750x750/ts/product/4e/2e/f9/796ad112a55f903e16406b7ea180b85a.png.webp");
        Product p14 = new Product(14, "P014", "Shower Gel", 30.0, "https://salt.tikicdn.com/cache/750x750/ts/product/2a/8e/e4/aff60b915040876deeac814d622105dd.jpg.webp");
        Product p15 = new Product(15, "P015", "Hair Oil", 65.0, "https://salt.tikicdn.com/cache/750x750/ts/product/64/ef/49/3127bbaaeda37642b97e1474f2d774da.png.webp");
        Product p16 = new Product(16, "P016", "Hand Cream", 25.0, "https://salt.tikicdn.com/cache/750x750/ts/product/5c/42/c5/28aa1f2aac5fdbb9a942000d17ac8241.jpg.webp");
        Product p17 = new Product(17, "P017", "Nail Polish", 20.0, "https://salt.tikicdn.com/cache/750x750/ts/product/53/e4/84/baf68d1838b27e34e5299dc1c43129af.jpg.webp");
        Product p18 = new Product(18, "P018", "Hair Spray", 40.0, "https://salt.tikicdn.com/cache/750x750/ts/product/d7/c6/6d/81e49ebad51169a22c311e7062533a47.png.webp");
        Product p19 = new Product(19, "P019", "Hair Brush", 35.0, "https://salt.tikicdn.com/cache/750x750/ts/product/87/28/a1/bf6117a95b905ff1553108ed1c07512b.jpg.webp");
        Product p20 = new Product(20, "P020", "Body Scrub", 50.0, "https://salt.tikicdn.com/cache/750x750/ts/product/95/88/3e/6c88d981e9869d4db2b7813ea8c70907.png.webp");

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
        products.add(p7);
        products.add(p8);
        products.add(p9);
        products.add(p10);
        products.add(p11);
        products.add(p12);
        products.add(p13);
        products.add(p14);
        products.add(p15);
        products.add(p16);
        products.add(p17);
        products.add(p18);
        products.add(p19);
        products.add(p20);

    }}

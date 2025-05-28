package com.example.tranbaongoc_k224111494_m02.models;

import java.util.ArrayList;

public class ListAccount {
    private ArrayList<Account> accounts;

    public ListAccount() {
        accounts = new ArrayList<>();
    }

    // Thêm 1 account
    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    // Getter
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // Setter
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    // Tạo dữ liệu mẫu
    public void generate_sample_dataset() {
        addAccount(new Account(1, "Tran Van A", "123"));
        addAccount(new Account(2, "Tran Van B", "1234"));
        addAccount(new Account(3, "Nguyen Van C", "123"));
        addAccount(new Account(4, "Vu Thi Thu", "1234"));
        addAccount(new Account(5, "Le Anh D", "123"));
        addAccount(new Account(6, "Tran Thi Cuc", "123"));
        addAccount(new Account(7, "Le Anh Vien", "12345"));
        addAccount(new Account(8, "Thi Trang", "1234"));
        addAccount(new Account(9, "Thu Trang", "123"));
        addAccount(new Account(10, "Anh Thu", "123"));
    }

    // Kiểm tra tồn tại
    public boolean isExisting(Account acc) {
        for (Account a : accounts) {
            if (a.getId() == acc.getId() ||
                    a.getUsername().equalsIgnoreCase(acc.getUsername())) {
                return true;
            }
        }
        return false;
    }
}

package com.minthanhtike.bookshop.room.tobag;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bag_table")
public class BagBooks {
    @PrimaryKey(autoGenerate = true)
    int tbId;
    String booksName;
    String authors;
    String price;
    String booksId;
    String category;
    String amount;

    public int getTbId() {
        return tbId;
    }

    public void setTbId(int tbId) {
        this.tbId = tbId;
    }

    public String getBooksName() {
        return booksName;
    }

    public void setBooksName(String booksName) {
        this.booksName = booksName;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBooksId() {
        return booksId;
    }

    public void setBooksId(String booksId) {
        this.booksId = booksId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BagBooks() {
    }

    public BagBooks(String booksName, String authors, String price, String booksId, String category, String amount) {
        this.booksName = booksName;
        this.authors = authors;
        this.price = price;
        this.booksId = booksId;
        this.category = category;
        this.amount = amount;
    }

    public BagBooks(int tbId, String booksName, String authors, String price, String booksId, String category, String amount) {
        this.tbId = tbId;
        this.booksName = booksName;
        this.authors = authors;
        this.price = price;
        this.booksId = booksId;
        this.category = category;
        this.amount = amount;
    }
}

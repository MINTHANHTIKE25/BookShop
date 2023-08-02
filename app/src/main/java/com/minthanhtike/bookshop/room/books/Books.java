package com.minthanhtike.bookshop.room.books;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books_table")
public class Books {
    @PrimaryKey(autoGenerate = true)
    int tbId;
    String booksName;
    String authors;
    String price;
    String booksId;
    String category;

    public Books(String booksName, String authors, String price, String booksId, String category) {
        this.booksName = booksName;
        this.authors = authors;
        this.price = price;
        this.booksId = booksId;
        this.category = category;
    }

    public Books(int tbId, String booksName, String authors, String price, String booksId, String category) {
        this.tbId = tbId;
        this.booksName = booksName;
        this.authors = authors;
        this.price = price;
        this.booksId = booksId;
        this.category = category;
    }

    public Books() {
    }

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
}

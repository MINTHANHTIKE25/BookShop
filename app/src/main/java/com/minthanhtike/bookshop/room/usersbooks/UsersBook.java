package com.minthanhtike.bookshop.room.usersbooks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users_book_tb")
public class UsersBook {
    @PrimaryKey (autoGenerate = true)
    int tbId;
    String booksName;
    String authors;
    String booksId;
    String category;

    public UsersBook(String booksName, String authors, String booksId, String category) {
        this.booksName = booksName;
        this.authors = authors;
        this.booksId = booksId;
        this.category = category;
    }

    public UsersBook(int tbId, String booksName, String authors, String booksId, String category) {
        this.tbId = tbId;
        this.booksName = booksName;
        this.authors = authors;
        this.booksId = booksId;
        this.category = category;
    }

    public UsersBook() {
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

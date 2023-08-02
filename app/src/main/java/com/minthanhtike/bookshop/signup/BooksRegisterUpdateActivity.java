package com.minthanhtike.bookshop.signup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.minthanhtike.bookshop.BookStoreActivity;
import com.minthanhtike.bookshop.R;
import com.minthanhtike.bookshop.room.books.Books;
import com.minthanhtike.bookshop.room.books.BooksViewModel;

public class BooksRegisterUpdateActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    EditText booksNameEdt, booksIdEdt, priceEdt, authorEdt;
    RadioGroup radioGroup;
    String categoryBooks;
    Button confirmBtn;
    BooksViewModel booksViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_register_update);
        initView();
        initEvent();
    }

    public void initView() {
        booksNameEdt = findViewById(R.id.books_name_edt);
        booksIdEdt = findViewById(R.id.books_id_edt);
        priceEdt = findViewById(R.id.books_price_edt);
        authorEdt = findViewById(R.id.books_author_edt);
        radioGroup = findViewById(R.id.books_rdo_gp);
        confirmBtn = findViewById(R.id.confirm_register_books);
        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
    }

    public void initEvent() {
        radioGroup.setOnCheckedChangeListener(this);
        confirmBtn.setOnClickListener(v -> {
            String booksName = booksNameEdt.getText().toString();
            String booksAuthor = authorEdt.getText().toString();
            String booksPrice = priceEdt.getText().toString();
            String booksId = booksIdEdt.getText().toString();

            if (booksName.isEmpty()) {
                Toast.makeText(this, "Book name is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (booksAuthor.isEmpty()) {
                Toast.makeText(this, "Author is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (booksPrice.isEmpty()) {
                Toast.makeText(this, "Book price is empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (booksId.isEmpty()) {
                Toast.makeText(this, "Books Id is empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (radioGroup.getCheckedRadioButtonId()==-1){
                Toast.makeText(this, "You didn't select the category", Toast.LENGTH_SHORT).show();
                return;
            }
            if (booksId.length() >= 6 & booksId.length() <= 8) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setMessage("Are u sure")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                booksViewModel.insert(new Books(booksName, booksAuthor, booksPrice, booksId, categoryBooks));
                                Toast.makeText(BooksRegisterUpdateActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BooksRegisterUpdateActivity.this, BookStoreActivity.class));
                                BooksRegisterUpdateActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                Toast.makeText(this, "Your Books Id must have 6 to 8 characters!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.novels_rdo:
                categoryBooks = getResources().getString(R.string.novels);
                break;
            case R.id.poems_rdo:
                categoryBooks = getResources().getString(R.string.poems);
                break;
            case R.id.science_rdo:
                categoryBooks = getResources().getString(R.string.science);
                break;
            case R.id.motivation_rdo:
                categoryBooks = getResources().getString(R.string.motivation);
                break;
            case R.id.politics_rdo:
                categoryBooks = getResources().getString(R.string.politics);
                break;
        }

    }
}
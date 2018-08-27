package com.example.choi.test;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class Display  extends AppCompatActivity{


    private DBHelper db;
    EditText name;
    EditText year;
    EditText rating;

    int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        name = (EditText)findViewById(R.id.edit_name);
        year = (EditText)findViewById(R.id.edit_year);
        rating = (EditText)findViewById(R.id.edit_rating);

        db = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int Value = extras.getInt("id");

            if(Value > 0){
                Cursor rs = db.getData(Value);
                id = Value;

                rs.moveToFirst();

                String n = rs.getString(rs.getColumnIndex(DBHelper.LIST_COLUMN_NAME));
                String y = rs.getString(rs.getColumnIndex(DBHelper.LIST_COLUMN_YEAR));
                String r = rs.getString(rs.getColumnIndex(DBHelper.LIST_COLUMN_RATING));

                if(!rs.isClosed()){
                    rs.close();
                }

                Button button = (Button)findViewById(R.id.button_save);
                button.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) n);
                year.setText((CharSequence) y);
                rating.setText((CharSequence) r);

            }

        }

    }

    public void insert(View view){

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            int Value = extras.getInt("id");

            if(Value > 0) {
                if(db.updateList(Value, name.getText().toString(), year.getText().toString(), rating.getText().toString())){
                    Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(getApplicationContext(), "수정실패", Toast.LENGTH_SHORT);
                }
            }

            else {
                if (db.insertList(name.getText().toString(), year.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "추가되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void delete(View view){

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            int Value = extras.getInt("id");

            if(Value > 0){

                db.deletList(Value);
                Toast.makeText(getApplicationContext(), "삭제되었습니다.",Toast.LENGTH_SHORT).show();
                finish();

            }

            else
                Toast.makeText(getApplicationContext(), "삭제되지 않았습니다.", Toast.LENGTH_SHORT).show();

        }

    }

    public void update(View view){

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            int Value = extras.getInt("id");

            if(Value > 0) {

                if(db.updateList(Value, name.getText().toString(), year.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정 되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "수정되지 않앗습니다.", Toast.LENGTH_SHORT).show();


            }

        }

    }
}

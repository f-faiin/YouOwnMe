package com.jnu.youownme;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private String personName,Number,Reason,Date;
    private EditText editTextName,editTextNumber,editTextReason,editTextDate;
    public static final int RESULT_OK_IN = 101;
    public static final int RESULT_OK_OUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextName= AddActivity.this.findViewById(R.id.edit_text_name);
        if(null!=personName)
            editTextName.setText(personName);
        editTextNumber= AddActivity.this.findViewById(R.id.edit_text_number);
        if(null!=Number)
            editTextNumber.setText(Number);
        editTextReason= AddActivity.this.findViewById(R.id.edit_text_reason);
        if(null!=Reason)
            editTextReason.setText(Reason);
        editTextDate= AddActivity.this.findViewById(R.id.edit_text_date);
        if(null!=Date)
            editTextDate.setText(Date);

        Button buttonOut = (Button)findViewById(R.id.out_button);
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_out = new Intent();
                intent_out.putExtra("person_name",editTextName.getText().toString());
                intent_out.putExtra("out_number",editTextNumber.getText().toString());
                intent_out.putExtra("out_reason",editTextReason.getText().toString());
                intent_out.putExtra("date",editTextDate.getText().toString());
                setResult(RESULT_OK_OUT,intent_out);
                finish();
            }
        });

        Button buttonIn = (Button)findViewById(R.id.in_button);
        buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_in = new Intent();
                intent_in.putExtra("person_name",editTextName.getText().toString());
                intent_in.putExtra("in_number",editTextNumber.getText().toString());
                intent_in.putExtra("in_reason",editTextReason.getText().toString());
                intent_in.putExtra("date",editTextDate.getText().toString());
                setResult(RESULT_OK_IN,intent_in);
                finish();
            }
        });
    }
}


package com.example.StayAlive;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: shelbi
 * Date: 2/11/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class OperationActivity extends Activity implements View.OnClickListener {
    Spinner spinnerOperation;
    EditText etSumma;
    Button btnSubmit,btnCancel;
    String[] operations={"Вклад","Отложить на запас","Снять"};
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation);

        etSumma=(EditText)findViewById(R.id.etSumma);
        btnSubmit=(Button)findViewById(R.id.btnSubmit) ;
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperation = (Spinner) findViewById(R.id.spinnerOperations);
        spinnerOperation.setAdapter(adapter);
        // заголовок
        spinnerOperation.setPrompt("Operations");
        // выделяем элемент
        spinnerOperation.setSelection(0);
        // устанавливаем обработчик нажатия
        spinnerOperation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //  Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSubmit:


                Intent intent = new Intent();
                int summa=Integer.parseInt(etSumma.getText().toString());
                String operation=spinnerOperation.getSelectedItem().toString() ;
                intent.putExtra("summa",summa);
                intent.putExtra("operation",operation);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            }
        }
 }


package com.example.StayAlive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends Activity implements View.OnClickListener
{
    /** Called when the activity is first created. */
    String[] persons={"Сергей","Арсен","Катя","Женя"};
    final int MENU_ALPHA_ID = 1;
    final int MENU_REMAINDER_ID = 2;
    final int MENU_BALANCE_ID=3;
    TextView tvResult,tvRemainder;
    Button btnAdd;
    Calendar calendar;
    Calendar calendar2;
    PersonalBankLogic bankLogic;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        tvResult=(TextView)findViewById(R.id.tvResult);
        tvRemainder=(TextView)findViewById(R.id.tvRemainder);
        registerForContextMenu(tvResult);

        bankLogic=new PersonalBankLogic(3000);
        int countOfDays=bankLogic.calculateCountOfDaysToLive(true,true,true,true,500);
        tvResult.setText(String.valueOf(countOfDays)+ " дней");

        // ???????
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spnPerson);
        spinner.setAdapter(adapter);
        // ?????????
        spinner.setPrompt("Persons");
        // ???????? ???????
        spinner.setSelection(0);
        // ????????????? ?????????? ???????
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // ?????????? ??????? ???????? ????????
               // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tvResult:
                // добавляем пункты
                menu.add(0, MENU_ALPHA_ID, 0, "Refresh");
                menu.add(0,MENU_BALANCE_ID,0,"Display Balance");
                menu.add(0, MENU_REMAINDER_ID, 0, "Check Remainder");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation  animation= AnimationUtils.loadAnimation(this, R.anim.myalpha);
        switch (item.getItemId()){
            case MENU_ALPHA_ID:

                int countOfDays=bankLogic.calculateCountOfDaysToLive(true,true,true,true,500);
                displayDays(countOfDays);
                break;

            case MENU_REMAINDER_ID:
                int remainder= bankLogic.getRemainder();
                String messageAboutRemainder="Ваш остаток составляет "+ String.valueOf(remainder)+" руб.";
                /*
                tvRemainder.setVisibility(View.VISIBLE);
                tvRemainder.setText(messageAboutRemainder);
                tvRemainder.startAnimation(animation);
                tvRemainder.setVisibility(View.INVISIBLE);   */
                Toast.makeText(this,messageAboutRemainder,Toast.LENGTH_SHORT).show();
                break;
            case MENU_BALANCE_ID:
                int balance=bankLogic.getCurrentBalance();
                String messageAboutBalance="Ваш текщий баланс составляет "+String.valueOf(balance) +"руб.";
                Toast.makeText(this,messageAboutBalance,Toast.LENGTH_SHORT).show();
                break;

        }



        return  super.onContextItemSelected(item);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();

        // Выведем в TextView информацию о нажатом пункте меню

        sb.append("\r\n itemId: "  + String.valueOf(item.getItemId()));

        sb.append("\r\n title: "   + item.getTitle());
        Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
    // обновление меню
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
        //  menu.setGroupVisible("@+id/group1",isFirstGroup);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  (R.id.btnAdd):

                Intent intent = new Intent(this, OperationActivity.class);
                startActivityForResult(intent, 1);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        int summa = data.getIntExtra("summa",-1);
        String operation=data.getStringExtra("operation");
        Toast.makeText(this,"Операция '"+ operation+ "' на сумму "+String.valueOf(summa)+" проведена успешно.",Toast.LENGTH_SHORT).show();

        bankLogic.AddOperation(summa,operation);
        int countOfDays=bankLogic.calculateCountOfDaysToLive(true,true,true,true,500);
        displayDays(countOfDays);


    }
    private void displayDays(int countOfDays)
    {
        Animation  animation= AnimationUtils.loadAnimation(this, R.anim.myalpha);
        tvResult.setText(String.valueOf(countOfDays)+ " days");
        tvResult.startAnimation(animation);
    }
}          /*Calendar calendar = GregorianCalendar.getInstance();
calendar.set(Calendar.MONTH, 10);
calendar.set(Calendar.DAY_OF_MONTH, 15);
calendar.set(Calendar.YEAR, 2005);
formatter = new SimpleDateFormat("MMM dd, yyyy");
System.out.println("Before: " + formatter.format(calendar.getTime()));

calendar.add(Calendar.DAY_OF_MONTH, 20);
System.out.println("After: " + formatter.format(calendar.getTime()));*/

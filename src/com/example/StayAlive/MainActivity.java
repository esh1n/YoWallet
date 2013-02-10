package com.example.StayAlive;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends Activity implements View.OnClickListener
{
    /** Called when the activity is first created. */
    final int MENU_ALPHA_ID = 1;
    final int MENU_REMAINDER_ID = 2;
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
        Date aDate = new Date(System.currentTimeMillis());

        calendar = GregorianCalendar.getInstance();
        calendar.setTime(aDate);
        calendar2 = GregorianCalendar.getInstance();
        calendar2.setTime(new Date(System.currentTimeMillis()));
        bankLogic=new PersonalBankLogic(3000);
        int countOfDays=bankLogic.calculateCountOfDaysToLive(true,true,true,true,500);
        tvResult.setText(String.valueOf(countOfDays)+ " days");

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
                tvResult.setText(String.valueOf(countOfDays)+ " days");
                tvResult.startAnimation(animation);
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

      private int cool(int i)
      {
          int i1=3,i2=4;

          return i-=(i1+i2);
      }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  (R.id.btnAdd):


                calendar2.add(Calendar.DAY_OF_MONTH, 1);
                calendar.setTime(new Date(System.currentTimeMillis()));
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                String dayOfWeek= calendar2.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,java.util.Locale.ENGLISH);

                Toast.makeText(this,dayOfWeek+String.valueOf(cool(7)),Toast.LENGTH_SHORT).show();

                Toast.makeText(this,formatter.format(calendar2.getTime()),Toast.LENGTH_SHORT).show();
                Toast.makeText(this,formatter.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
               // long DaysLeft= getDays(calendar.getTime(),calendar2.getTime());

              //  Toast.makeText(this,"Days left are "+String.valueOf(DaysLeft) ,Toast.LENGTH_SHORT).show();
                break;

        }
    }
}          /*Calendar calendar = GregorianCalendar.getInstance();
calendar.set(Calendar.MONTH, 10);
calendar.set(Calendar.DAY_OF_MONTH, 15);
calendar.set(Calendar.YEAR, 2005);
formatter = new SimpleDateFormat("MMM dd, yyyy");
System.out.println("Before: " + formatter.format(calendar.getTime()));

calendar.add(Calendar.DAY_OF_MONTH, 20);
System.out.println("After: " + formatter.format(calendar.getTime()));*/

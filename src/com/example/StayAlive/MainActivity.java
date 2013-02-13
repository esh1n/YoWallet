package com.example.StayAlive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class MainActivity extends Activity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * Called when the activity is first created.
     */
    public static final String APP_PREFERENCES = "mysettings"; // это будет именем файла настроек
    SharedPreferences mSettings;
    String[] persons = {"Сергей", "Арсен", "Катя", "Женя"};
    final int REQUEST_CODE_OPERATION = 1;
    final int REQUEST_CODE_SETTINGS = 2;
    final int MENU_ALPHA_ID = 1;
    final int MENU_REMAINDER_ID = 2;
    final int MENU_BALANCE_ID = 3;
    TextView tvResult, tvRemainder;
    Button btnAdd;
    PersonalBankLogic bankLogic;
    SharedPreferences settingsScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvRemainder = (TextView) findViewById(R.id.tvRemainder);
        registerForContextMenu(tvResult);

        bankLogic = new PersonalBankLogic(3000);
        int countOfDays = bankLogic.calculateCountOfDaysToLive(true, true, false, false, 500);
        tvResult.setText(String.valueOf(countOfDays) + " дней");

        // ???????
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spnPerson);
        spinner.setAdapter(adapter);

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


        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        settingsScreen = PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onStart() {

        // Toast.makeText(this,"work",Toast.LENGTH_SHORT).show();
        super.onStart();
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
                menu.add(0, MENU_BALANCE_ID, 0, "Display Balance");
                menu.add(0, MENU_REMAINDER_ID, 0, "Check Remainder");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        switch (item.getItemId()) {
            case MENU_ALPHA_ID:

                int countOfDays = bankLogic.calculateCountOfDaysToLive(true, true, false, false, 500);
                displayDays(countOfDays);
                break;

            case MENU_REMAINDER_ID:
                int remainder = bankLogic.getRemainder();
                String messageAboutRemainder = "Ваш остаток составляет " + String.valueOf(remainder) + " руб.";
                /*
                tvRemainder.setVisibility(View.VISIBLE);
                tvRemainder.setText(messageAboutRemainder);
                tvRemainder.startAnimation(animation);
                tvRemainder.setVisibility(View.INVISIBLE);   */
                Toast.makeText(this, messageAboutRemainder, Toast.LENGTH_SHORT).show();
                break;
            case MENU_BALANCE_ID:
                int balance = bankLogic.getCurrentBalance();
                String messageAboutBalance = "Ваш текщий баланс составляет " + String.valueOf(balance) + "руб.";
                Toast.makeText(this, messageAboutBalance, Toast.LENGTH_SHORT).show();
                break;

        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Настройки")) {
           /* StringBuilder sb = new StringBuilder();
            sb.append("\r\n itemId: "  + String.valueOf(item.getItemId()));
            sb.append("\r\n title: "   + item.getTitle());
            Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT).show();*/
            Intent settingsIntent = new Intent(this, PrefActivity.class);
            startActivityForResult(settingsIntent, REQUEST_CODE_SETTINGS);

        }
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
        switch (view.getId()) {
            case (R.id.btnAdd):

                Intent intent = new Intent(this, OperationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_OPERATION);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);

        switch (requestCode) {
            case REQUEST_CODE_SETTINGS:
                updateUserSettings();
                break;
            case REQUEST_CODE_OPERATION:
                if (data == null) {
                    return;
                }
                int summa = data.getIntExtra("summa", -1);
                String operation = data.getStringExtra("operation");
                Toast.makeText(this, "Операция '" + operation + "' на сумму " + String.valueOf(summa) + " проведена успешно.", Toast.LENGTH_SHORT).show();
                bankLogic.AddOperation(summa, operation);
                int countOfDays = bankLogic.calculateCountOfDaysToLive(true, true, false, false, 500);
                displayDays(countOfDays);
                break;


        }
        // если вернулось не ОК
      /*  } else {
            if(resultCode== REQUEST_CODE_SETTINGS)  {
                showUserSettings();
            }

            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();  */
    }


    private void displayDays(int countOfDays) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        tvResult.setText(String.valueOf(countOfDays) + " days");
        tvResult.startAnimation(animation);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    private void updateUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        Boolean isPizzaAfterTennis = sharedPrefs.getBoolean("pizza", false);
        Boolean isNeedInternet = sharedPrefs.getBoolean("inet", false);
        Boolean isNeedSalve = sharedPrefs.getBoolean("salve", false);
        String additionalExpenses = sharedPrefs.getString("addExpense", "120");
        int expense = Integer.parseInt(additionalExpenses);
        String wayToHome = sharedPrefs.getString("way", "не выбрано");
        boolean isWayByTrain = wayToHome.equals("На электричке");
        int countOfDays = bankLogic.calculateCountOfDaysToLive(isPizzaAfterTennis, isWayByTrain, isNeedInternet, isNeedSalve, expense);
        displayDays(countOfDays);
       /* String messageVerify=wayToHome+
                " Pizza ->"+String.valueOf(isPizzaAfterTennis)+
                " Spend -> "+String.valueOf(expense)+
                " isNeedSalve "+String.valueOf(isNeedSalve)+
                "isByTrain->"+String.valueOf(isWayByTrain)+
                "isNeedInternet->"+String.valueOf(isNeedInternet);

        Toast.makeText(this,messageVerify,Toast.LENGTH_LONG).show();   */


    }
}          /*Calendar calendar = GregorianCalendar.getInstance();
calendar.set(Calendar.MONTH, 10);
calendar.set(Calendar.DAY_OF_MONTH, 15);
calendar.set(Calendar.YEAR, 2005);
formatter = new SimpleDateFormat("MMM dd, yyyy");
System.out.println("Before: " + formatter.format(calendar.getTime()));

calendar.add(Calendar.DAY_OF_MONTH, 20);
System.out.println("After: " + formatter.format(calendar.getTime()));*/

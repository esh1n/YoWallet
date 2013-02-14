package com.example.StayAlive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class MainActivity extends Activity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int MENU_HELP_ID = 4;
    private static final int DANGER_COUNT_DAYS_TO_LIVE = 3;
    String[] persons = {"Сергей", "Арсен", "Катя", "Женя"};
    int btn;
    final int REQUEST_CODE_OPERATION = 1;
    final int REQUEST_CODE_SETTINGS = 2;
    final int MENU_BALANCE_NEW_ID = 1;
    final int MENU_REMAINDER_ID = 2;
    final int MENU_BALANCE_VIEW_ID = 3;
    final int DIALOG = 1;
    TextView tvResult, tvRemainder,tvBalance;
    Button btnAdd;
    PersonalBankLogic bankLogic;
    SharedPreferences settingsScreen;
    LinearLayout view;
    Dialog dialog;
    AlertDialog.Builder alertDialogBuilder;
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
        int countOfDays = bankLogic.calculateCountOfDaysToLive();
        tvResult.setText(String.valueOf(countOfDays) + " дней");

        // ???????
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spnPerson);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Persons");
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
        settingsScreen = PreferenceManager.getDefaultSharedPreferences(this);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                int newBalance=Integer.parseInt(userInput.getText().toString());
                                bankLogic.setCurrentBalance(newBalance);
                                int countOfDays = bankLogic.calculateCountOfDaysToLive();
                                displayDays(countOfDays);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        return alertDialogBuilder.create();
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);

    }


    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onStart() {

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
                menu.add(0, MENU_BALANCE_NEW_ID, 0, "Обновить баланс");
                menu.add(0, MENU_BALANCE_VIEW_ID, 0, "Показать баланс");
                menu.add(0, MENU_REMAINDER_ID, 0, "Показать остаток");
                int countOfDays=bankLogic.calculateCountOfDaysToLive();
                if (countOfDays<=DANGER_COUNT_DAYS_TO_LIVE)
                menu.add(0, MENU_HELP_ID, 0, "Что делать,если нечего есть?");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        switch (item.getItemId()) {
            case MENU_BALANCE_NEW_ID:
               showDialog(DIALOG);

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
            case MENU_BALANCE_VIEW_ID:
                int balance = bankLogic.getCurrentBalance();
                String messageAboutBalance = "Ваш текщий баланс составляет " + String.valueOf(balance) + "руб.";
                Toast.makeText(this, messageAboutBalance, Toast.LENGTH_SHORT).show();
                break;
            case MENU_HELP_ID:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;

        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Настройки")) {
            Intent settingsIntent = new Intent(this, PrefActivity.class);
            startActivityForResult(settingsIntent, REQUEST_CODE_SETTINGS);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
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
                int countOfDays = bankLogic.calculateCountOfDaysToLive();
                displayDays(countOfDays);
                break;


        }
    }


    private void displayDays(int countOfDays) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        int color=Color.WHITE;
        if (countOfDays<=DANGER_COUNT_DAYS_TO_LIVE)color=Color.RED;
        tvResult.setText(String.valueOf(countOfDays) + " days");
        tvResult.setTextColor(color);
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
        bankLogic.updateSettings(isPizzaAfterTennis, isWayByTrain, isNeedInternet, isNeedSalve, expense);
        int countOfDays = bankLogic.calculateCountOfDaysToLive();
        displayDays(countOfDays);
    }
}

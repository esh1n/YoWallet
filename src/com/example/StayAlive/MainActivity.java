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

public class MainActivity extends Activity implements View.OnClickListener
{
    /** Called when the activity is first created. */
    final int MENU_ALPHA_ID = 1;
    TextView tvResult;
    Button btnAdd;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        tvResult=(TextView)findViewById(R.id.tvResult);
        registerForContextMenu(tvResult);
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

                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation animation =null;
        switch (item.getItemId()){
            case MENU_ALPHA_ID:
                animation= AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;

        }
        tvResult.setText("8 days");
        tvResult.startAnimation(animation);
        return  super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  (R.id.btnAdd):
                Toast.makeText(this,"Worked!",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

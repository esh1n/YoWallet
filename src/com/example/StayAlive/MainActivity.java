package com.example.StayAlive;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    final int MENU_ALPHA_ID = 1;
    TextView tvResult;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvResult=(TextView)findViewById(R.id.tvResult);
        registerForContextMenu(tvResult);
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
}

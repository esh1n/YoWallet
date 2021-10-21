package com.example.StayAlive;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: SunShelbi
 * Date: 15.02.13
 * Time: 2:47
 * To change this template use File | Settings | File Templates.
 */
public class HelpActivity extends Activity implements View.OnClickListener {
    Button btnCall,btnWeb,btnMap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        btnCall=(Button)findViewById(R.id.btnCall);
        btnMap=(Button)findViewById(R.id.btnMap);
        btnWeb=(Button)findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnCall.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:89513037945"));
                startActivity(intent);
                break;
            case R.id.btnWeb:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sbrf.ru/voronezh/ru/about/branch/list_branch/"));
                startActivity(intent);
                break;
            case R.id.btnMap:
                intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:51.40,39.12"));
                startActivity(intent);
                break;
        }
    }
}

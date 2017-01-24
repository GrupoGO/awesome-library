package es.grupogo.awesomelibraryexample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.grupogo.awesomelibrary.AwesomeFunction;
import es.grupogo.awesomelibrary.BadgeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AwesomeFunction
        int dp = 20;
        int pixels = AwesomeFunction.dpToPx(this, dp);

        //Badgeview
        BadgeView badgeView = (BadgeView) findViewById(R.id.badgeview);
        badgeView.setBadgeText("Text");
        badgeView.setTextColor(Color.WHITE);
        badgeView.setBadgeTextSize(18);
        badgeView.setBadgeColor(Color.BLUE);

    }

}

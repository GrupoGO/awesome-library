package es.grupogo.awesomelibraryexample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.grupogo.awesomelibrary.AwesomeFunction;
import es.grupogo.awesomelibrary.BadgeView;
import es.grupogo.awesomelibrary.StickyHeader.StickyHeaderRecyclerView;

public class MainActivity extends AppCompatActivity {

    List<String> stringList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AwesomeFunction
        int dp = 20;
        int pixels = AwesomeFunction.dpToPx(this, dp);

        //Badgeview
       /** BadgeView badgeView = (BadgeView) findViewById(R.id.badgeview);
        badgeView.setBadgeText("Text");
        badgeView.setTextColor(Color.WHITE);
        badgeView.setBadgeTextSize(18);
        badgeView.setBadgeColor(Color.BLUE);**/

        stringList = Arrays.asList(this.getResources().getStringArray(R.array.countries));

        StickyHeaderRecyclerView stickyRecycler = (StickyHeaderRecyclerView) findViewById(R.id.sticky_header);
        MyAdapter adapter = new MyAdapter(stringList);
        stickyRecycler.setLayoutManager(new LinearLayoutManager(this));
        stickyRecycler.setAdapter(adapter);
        View header = findViewById(R.id.header);
        stickyRecycler.setHeader(header);

    }

}

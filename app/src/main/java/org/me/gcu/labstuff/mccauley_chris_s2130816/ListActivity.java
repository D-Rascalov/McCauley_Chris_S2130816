package org.me.gcu.labstuff.mccauley_chris_s2130816;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewTitle, textViewDescription, textViewStartDate, textViewEndDate, textLatitude, textViewLongitude, textViewPubDate;
    private Button markOnMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewDescription = (TextView)findViewById(R.id.textViewDescription);
        textViewStartDate = (TextView)findViewById(R.id.textViewStartDate);
        textViewEndDate = (TextView)findViewById(R.id.textViewEndDate);
        textLatitude = (TextView)findViewById(R.id.textLatitude);
        textViewLongitude = (TextView)findViewById(R.id.textViewLongitude);
        textViewPubDate = (TextView)findViewById(R.id.textViewPubDate);

        markOnMapButton = (Button)findViewById(R.id.markMapButton);
        markOnMapButton.setOnClickListener(this);


        Intent intent = this.getIntent();

        if (intent != null)
        {
            String item = intent.getStringExtra("item");
            System.out.println(item);
            //String subStringEndDate = description.substring(description.indexOf("End Date:") + 10, description.indexOf("<", description.indexOf("<") + 1));
            String itemNumber = item.substring(item.indexOf("Item Number") +13, item.indexOf("Title") -3);
            String title = item.substring(item.indexOf("Title") +7, item.indexOf("Description") -3);
            String description = item.substring(item.indexOf("Description") +13, item.indexOf("Latitude") -3);
            String latitude = item.substring(item.indexOf("Latitude") +10, item.indexOf("Longitude") -3);
            String longitude = item.substring(item.indexOf("Longitude") +11, item.indexOf("Start Date") -3);
            String startDate = item.substring(item.indexOf("Start Date") +12, item.indexOf("End Date") -3);
            String endDate = item.substring(item.indexOf("End Date") +10, item.indexOf("Published Date") -3);
            String pubDate = item.substring(item.indexOf("Published Date") +16, item.indexOf("GMT") -1);
            System.out.println(itemNumber);
            System.out.println(title);
            System.out.println(description);
            System.out.println(latitude);
            System.out.println(longitude);
            System.out.println(startDate);
            System.out.println(endDate);
            System.out.println(pubDate);

            textViewTitle.setText(title);
            textViewDescription.setText(description);
            textLatitude.setText(latitude);
            textViewLongitude.setText(longitude);
            textViewStartDate.setText(startDate);
            textViewEndDate.setText(endDate);
            textViewPubDate.setText(pubDate);

        }

    }

    @Override
    public void onClick(View view) {

        if (view == markOnMapButton) {


        }

    }
}
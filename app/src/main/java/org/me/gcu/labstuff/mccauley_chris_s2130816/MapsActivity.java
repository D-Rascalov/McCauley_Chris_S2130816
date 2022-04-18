package org.me.gcu.labstuff.mccauley_chris_s2130816;

//Chris McCauley
//S2130816
//Mobile Platform Development

import static android.graphics.Color.rgb;

import androidx.annotation.ColorInt;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


import org.me.gcu.labstuff.mccauley_chris_s2130816.databinding.ActivityMapsBinding;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    //Declare UI Components
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ViewFlipper avw;
    private Button s1Button, s2Button, s2RoadWorksButton, s2DateSpecificRoadWorksButton;
    private Button s2RoadSpecificRoadWorksButton, roadSearchButton, s2JourneyPlannerButton;
    private Button datePickerButton, journeyPlannerButton, roadPlannerMarkOnMapButton;
    private Button s3BackButton, s4BackButton, s5BackButton, s6BackButton, s7BackButton;
    private RadioGroup mapTypeGroup;
    private RadioButton normalViewButton, terrainViewButton, hybridViewButton, satelliteViewButton;
    private CheckBox panZoom;
    private ListView listView, dateListView, roadworkRoadList, journeyListView;
    private DatePicker datePicker, datePickerJourneyPlanner;
    private EditText roadSearchEditText;
    private TextView textViewTitle, textViewDescription, textViewStartDate, textViewEndDate, textLatitude, textViewLongitude, textViewPubDate, label;
    private ProgressBar determinateBar;

    private String result = "";
    private String url1="";

    //LinkedList to hold road work objects, which contain the required roadwork information
    private LinkedList<RoadWorksItem> roadWorkList = new LinkedList<>();

    // Traffic Scotland Planned Roadworks XML link
    private String
            urlSource="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.e("MyTag","in onCreate");

        //Initialise map controls
        mapTypeGroup = (RadioGroup)findViewById(R.id.mapTypeGroup);
        normalViewButton = (RadioButton)findViewById(R.id.normalViewRadio);
        terrainViewButton = (RadioButton)findViewById(R.id.terrainViewRadio);
        hybridViewButton = (RadioButton)findViewById(R.id.hybridViewRadio);
        satelliteViewButton = (RadioButton)findViewById(R.id.satelliteViewRadio);
        panZoom = (CheckBox)findViewById(R.id.panZoom);

        Log.e(getPackageName(), "just before avw");
        avw = (ViewFlipper) findViewById(R.id.vwFlip);
        if (avw == null)
        {
            Toast.makeText(getApplicationContext(), "Null ViewSwicther", Toast.LENGTH_LONG);
            Log.e(getPackageName(), "null pointer");
        }

        //Initialise UI components

        //Buttons
        s1Button = (Button) findViewById(R.id.screen1Button);
        s2Button = (Button) findViewById(R.id.screen2Button);
        s2RoadWorksButton = (Button) findViewById(R.id.screen2RoadWorksButton);
        s2DateSpecificRoadWorksButton = (Button) findViewById(R.id.screen2DateSpecificRoadWorksButton);
        s2RoadSpecificRoadWorksButton = (Button) findViewById(R.id.screen2RoadSpecificRoadWorksButton);
        roadSearchButton = (Button) findViewById(R.id.roadSearchButton);
        s2JourneyPlannerButton = (Button) findViewById(R.id.screen2JourneyPlannerButton);
        datePickerButton = (Button) findViewById(R.id.datePickerButton);
        journeyPlannerButton = (Button) findViewById(R.id.buttonJourneyPlanner);
        roadPlannerMarkOnMapButton = (Button)findViewById(R.id.markMapButton);
        s3BackButton = (Button) findViewById(R.id.screen3BackButton);
        s4BackButton = (Button) findViewById(R.id.screen4BackButton);
        s5BackButton = (Button) findViewById(R.id.screen5BackButton);
        s6BackButton = (Button) findViewById(R.id.screen6BackButton);
        s7BackButton = (Button) findViewById(R.id.screen7BackButton);

        //Date Pickers
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePickerJourneyPlanner = (DatePicker) findViewById(R.id.datePickerJourneyPlanner);

        //Text Views
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewDescription = (TextView)findViewById(R.id.textViewDescription);
        textViewStartDate = (TextView)findViewById(R.id.textViewStartDate);
        textViewEndDate = (TextView)findViewById(R.id.textViewEndDate);
        textLatitude = (TextView)findViewById(R.id.textLatitude);
        textViewLongitude = (TextView)findViewById(R.id.textViewLongitude);
        textViewPubDate = (TextView)findViewById(R.id.textViewPubDate);
        //label = (TextView)findViewById(R.id.label);

        //Edit Text
        roadSearchEditText = (EditText) findViewById(R.id.roadSearchEditText);

        //Progress Bar
        determinateBar = (ProgressBar) findViewById(R.id.determinateBar);

        //On click listeners
        roadPlannerMarkOnMapButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);
        s1Button.setOnClickListener(this);
        s1Button.setEnabled(false);
        s2Button.setOnClickListener(this);
        s2RoadWorksButton.setOnClickListener(this);
        s2DateSpecificRoadWorksButton.setOnClickListener(this);
        s2RoadSpecificRoadWorksButton.setOnClickListener(this);
        roadSearchButton.setOnClickListener(this);
        s2JourneyPlannerButton.setOnClickListener(this);
        journeyPlannerButton.setOnClickListener(this);
        normalViewButton.setOnClickListener(this);
        terrainViewButton.setOnClickListener(this);
        hybridViewButton.setOnClickListener(this);
        satelliteViewButton.setOnClickListener(this);
        normalViewButton.toggle();
        panZoom.setOnClickListener(this);
        s3BackButton.setOnClickListener(this);
        s4BackButton.setOnClickListener(this);
        s5BackButton.setOnClickListener(this);
        s6BackButton.setOnClickListener(this);
        s7BackButton.setOnClickListener(this);

        //List Views

        //Declare listview that will hold the roadwork information
        //Set on item click listener, when an item is selected go to new activity with core roadwork details
        listView = (ListView) findViewById(R.id.roadwork_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                RoadWorksListItem value = (RoadWorksListItem)adapterView.getItemAtPosition(position);
                RoadWorksItem item = roadWorkList.get(value.getItemNumber());

                textViewTitle.setText(item.getTitle());
                textViewDescription.setText(item.getDescription());
                textViewStartDate.setText(item.getStartDate());
                textViewEndDate.setText(item.getEndDate());
                textLatitude.setText(item.getLatitude());
                textViewLongitude.setText(item.getLongitude());
                textViewPubDate.setText(item.getPubDate());

                avw.showNext();
                avw.showNext();
                avw.showNext();
                avw.showNext();

            }
        });

        //Set on item click listener, when an item is selected go to new activity with core roadwork details
        dateListView = (ListView)findViewById(R.id.roadwork_date_list);
        dateListView.setClickable(true);
        dateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                RoadWorksListItem value = (RoadWorksListItem)adapterView.getItemAtPosition(position);
                RoadWorksItem item = roadWorkList.get(value.getItemNumber());

                textViewTitle.setText(item.getTitle());
                textViewDescription.setText(item.getDescription());
                textViewStartDate.setText(item.getStartDate());
                textViewEndDate.setText(item.getEndDate());
                textLatitude.setText(item.getLatitude());
                textViewLongitude.setText(item.getLongitude());
                textViewPubDate.setText(item.getPubDate());

                avw.showNext();
                avw.showNext();
                avw.showNext();
            }
        });

        //Set on item click listener, when an item is selected go to new activity with core roadwork details
        roadworkRoadList = (ListView) findViewById(R.id.roadwork_road_list);
        roadworkRoadList.setClickable(true);
        roadworkRoadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                RoadWorksListItem value = (RoadWorksListItem)adapterView.getItemAtPosition(position);
                RoadWorksItem item = roadWorkList.get(value.getItemNumber());

                textViewTitle.setText(item.getTitle());
                textViewDescription.setText(item.getDescription());
                textViewStartDate.setText(item.getStartDate());
                textViewEndDate.setText(item.getEndDate());
                textLatitude.setText(item.getLatitude());
                textViewLongitude.setText(item.getLongitude());
                textViewPubDate.setText(item.getPubDate());

                avw.showNext();
                avw.showNext();
            }
        });

        //Set on item click listener, when an item is selected go to new activity with core roadwork details
        journeyListView = (ListView) findViewById(R.id.roadwork_journey_list);
        journeyListView.setClickable(true);
        journeyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                RoadWorksListItem value = (RoadWorksListItem)adapterView.getItemAtPosition(position);
                RoadWorksItem item = roadWorkList.get(value.getItemNumber());

                textViewTitle.setText(item.getTitle());
                textViewDescription.setText(item.getDescription());
                textViewStartDate.setText(item.getStartDate());
                textViewEndDate.setText(item.getEndDate());
                textLatitude.setText(item.getLatitude());
                textViewLongitude.setText(item.getLongitude());
                textViewPubDate.setText(item.getPubDate());

                avw.showNext();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startProgress();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Glasgow and move the camera
        LatLng glasgow = new LatLng(55.8597, 4.2550);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(glasgow));

        //Increment the progress bar by 25%
        determinateBar.incrementProgressBy(25);
    }

    public void onClick(View arg0)
    {

        Log.e("MyTag","in onClick");
        Log.e("MyTag","after startProgress");

        //Button clicks for map controls, road search, date search, journey planner, return and mark on map
        if (arg0 == s1Button)
        {
            avw.showNext();
        }
        else
        if (arg0 == s2Button)
        {
            avw.showPrevious();
        }
        else
        if (arg0 == s3BackButton)
        {
            avw.showPrevious();
        }
        else
        if (arg0 == s4BackButton)
        {
            avw.showPrevious();
            avw.showPrevious();
        }
        else
        if (arg0 == s5BackButton)
        {
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
        }
        else
        if (arg0 == s6BackButton)
        {
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
        }
        else
        if (arg0 == s7BackButton)
        {
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
        }
        else
        if (arg0 == s2RoadWorksButton)
        {
            avw.showNext();

            //Create list of all roadworks and display in list view
            createRoadworkList(roadWorkList);
        }
        else
        if (arg0 == s2DateSpecificRoadWorksButton)
        {
            avw.showNext();
            avw.showNext();
        }
        else
        if (arg0 == datePickerButton)
        {
            //Create list of all roadworks on a specific date and display on list view
            createDateRoadworkList(roadWorkList);
        }
        else
        if (arg0 == s2RoadSpecificRoadWorksButton)
        {
            avw.showNext();
            avw.showNext();
            avw.showNext();
        }
        else
        if (arg0 == roadSearchButton)
        {
            //Create list of all roadworks on a specific road and display in list view
            createRoadRoadworkList(roadWorkList);

        }
        else
        if (arg0 == roadPlannerMarkOnMapButton)
        {

            //Mark the selected roadwork on the map adn return to map

            //Get latitude and longitude from text views
            double latitude = Double.parseDouble(textLatitude.getText().toString());
            double longitude = Double.parseDouble(textViewLongitude.getText().toString());

            // Add a marker in at the correct location
            LatLng newMarker = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(newMarker).title(textViewDescription.getText().toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newMarker));

            //Clear previous data
            textViewTitle.setText("");
            textViewDescription.setText("");
            textViewStartDate.setText("");
            textViewEndDate.setText("");
            textLatitude.setText("");
            textViewLongitude.setText("");
            textViewPubDate.setText("");

            //Return to map screen
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();
            avw.showPrevious();

        }
        else
        if (arg0 == s2JourneyPlannerButton)
        {
            avw.showNext();
            avw.showNext();
            avw.showNext();
            avw.showNext();

        }
        else
        if (arg0 == journeyPlannerButton)
        {
            //Create list of all roadworks on a specific date and display in list view
            createJourneyRoadworkList(roadWorkList);
        }
        else
        if (arg0 == normalViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else
        if (arg0 == terrainViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        else
        if (arg0 == hybridViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else
        if (arg0 == satelliteViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        if (panZoom.isChecked())
        {
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
        else
        {
            mMap.getUiSettings().setZoomControlsEnabled(false);
        }

    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    }

    // Need separate thread to access the internet resource over network.
    private class Task implements Runnable
    {
        //Get xml data from traffic scotland and store in result string
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            Log.e("MyTag","in run");
            determinateBar.incrementProgressBy(25);
            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new
                        InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");

                //Now read the data. Make sure that there are no specific
                // headers in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);
                }
                in.close();
                determinateBar.incrementProgressBy(25);
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            // Now that you have the xml data you can parse it
            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !
            MapsActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    parseData(result);
                }
            });
        }
    }

    //Parse the roadworks string into RoadWorksItem objects
    private void parseData(String dataToParse)
    {

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();

            //Loop until end of document
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        //Create new RoadWorksItem object to hold parsed data
                        RoadWorksItem item = new RoadWorksItem();
                        item.setItemNumber(roadWorkList.size()+1);

                        //Get title
                        eventType = xpp.next();
                        eventType = xpp.next();
                        String title = xpp.nextText();
                        item.setTitle(title);

                        //Get description
                        eventType = xpp.next();
                        eventType = xpp.next();
                        String description = xpp.nextText();

                        //Get start date
                        String subStringStartDate = description.substring(12, description.indexOf("<"));
                        item.setStartDate(subStringStartDate);

                        //Get end date
                        String subStringEndDate = description.substring(description.indexOf("End Date:") + 10, description.indexOf("<", description.indexOf("<") + 1));
                        item.setEndDate(subStringEndDate);
                        String subStringDescription = description.substring(description.indexOf(">", description.indexOf(">") + 1) + 1);
                        item.setDescription(subStringDescription);

                        //Get latitude and longitude
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        String location = xpp.nextText();
                        String subStringLatitude = location.substring(0, location.indexOf("-") -1);
                        item.setLatitude(subStringLatitude);
                        String subStringLongitude = location.substring(location.indexOf("-"));
                        item.setLongitude(subStringLongitude);

                        //Get published date
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        eventType = xpp.next();
                        String pubDate = xpp.nextText();
                        item.setPubDate(pubDate);

                        //Add RoadWorksItem to roadworks linked list
                        roadWorkList.add(item);
                    }
                }

                // Get the next event
                eventType = xpp.next();

            } // End of while

            System.out.println(roadWorkList.toString());
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");
        determinateBar.incrementProgressBy(25);
        determinateBar.setVisibility(determinateBar.GONE);
        s1Button.setEnabled(true);
        //s1Button.setBackgroundTint(rgb(128, 203, 196));

    } // End of parseData

    //Extract core roadworks details from RoadWorksItem object and add the RoadWorksListItem object,
    //then add the list item to a new array list so they can be displayed in the list view
    private void createRoadworkList(LinkedList<RoadWorksItem> roadworkList)
    {

        ArrayList<RoadWorksListItem> roadWorksArray = new ArrayList<>();

        for (int i = 0; i < roadworkList.size(); i++) {

            RoadWorksListItem rwlistItem = new RoadWorksListItem();
            rwlistItem.setItemNumber(roadworkList.get(i).getItemNumber());
            rwlistItem.setTitle(roadworkList.get(i).getTitle());
            rwlistItem.setDescription(roadworkList.get(i).getDescription());
            rwlistItem.setStartDate(roadworkList.get(i).getStartDate());
            rwlistItem.setEndDate(roadworkList.get(i).getEndDate());

            roadWorksArray.add(rwlistItem);

        }


        ArrayAdapter adapter = new ArrayAdapter<RoadWorksListItem>(this,
                R.layout.activity_listview, roadWorksArray);

        ListView listView = (ListView) findViewById(R.id.roadwork_list);
        listView.setAdapter(adapter);
    }

    //Extract core roadworks details from RoadWorksItem object and add the RoadWorksListItem object,
    //then add the list item to a new array list so they can be displayed in the list view
    private void createDateRoadworkList(LinkedList<RoadWorksItem> roadworkList)
    {

        //Get the date selected by the user
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();

        Date selectedDateParsed = new Date();
        Date startDateParsed = new Date();
        Date endDateParsed = new Date();

        String selectedDateParsedString = "";
        String startDateParsedString = "";
        String endDateParsedString = "";

        //Format date entered by user
        String selectedDate = String.valueOf(day) + "-0" + String.valueOf(month) + "-" + String.valueOf(year);
        SimpleDateFormat formatter = new SimpleDateFormat ("dd-MM-yyyy");
        try {
            selectedDateParsed = formatter.parse(selectedDate);
            //System.out.println(selectedDateParsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Array list of RoadworksListItem objects to hold any roadworks that match the date entered by the user
        ArrayList<RoadWorksListItem> roadWorksArray = new ArrayList<>();

        for (int i = 0; i < roadworkList.size(); i++) {

            String startDate = roadworkList.get(i).getStartDate().substring(0, roadworkList.get(i).getStartDate().indexOf(":")-5);
            SimpleDateFormat startDateformat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            try {
                startDateParsed = startDateformat.parse(startDate);
                startDateformat.applyPattern("dd-MM-yyyy");
                startDateParsedString = startDateformat.format(startDateParsed);
                //System.out.println(startDateParsedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat endDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String endDate = roadworkList.get(i).getEndDate().substring(0, roadworkList.get(i).getEndDate().indexOf(":")-5);
            try {
                endDateParsed = endDateFormat.parse(endDate);
                endDateFormat.applyPattern("dd-MM-yyyy");
                endDateParsedString = endDateFormat.format(endDateParsed);
                //System.out.println(endDateParsedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //label = (TextView)findViewById(R.id.label);
            //long diff = endDateParsed.getTime() - startDateParsed.getTime();
            //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            //int red = Color.RED;
            //int yellow = Color.YELLOW;
            //int green = Color.GREEN;
            //if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <= 4) {
            //    label.setTextColor(green);
            //}
            //else if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 4 && TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 7) {
            //    label.setTextColor(yellow);
            //}
            //else {
            //    label.setTextColor(red);
            //}

            selectedDateParsedString = startDateformat.format(selectedDateParsed);
            //System.out.println(selectedDateParsedString);
            if(startDateParsedString.equalsIgnoreCase(selectedDateParsedString) || endDateParsedString.equalsIgnoreCase(selectedDateParsedString)) {

                RoadWorksListItem rwlistItem = new RoadWorksListItem();
                rwlistItem.setItemNumber(roadworkList.get(i).getItemNumber());
                rwlistItem.setTitle(roadworkList.get(i).getTitle());
                rwlistItem.setDescription(roadworkList.get(i).getDescription());
                rwlistItem.setStartDate(roadworkList.get(i).getStartDate());
                rwlistItem.setEndDate(roadworkList.get(i).getEndDate());

                roadWorksArray.add(rwlistItem);
            }
        }


        ArrayAdapter adapter = new ArrayAdapter<RoadWorksListItem>(this,
                R.layout.activity_listview, roadWorksArray);

        ListView listView = (ListView) findViewById(R.id.roadwork_date_list);
        listView.setAdapter(adapter);

    }

    //Extract core roadworks details from RoadWorksItem object and add the RoadWorksListItem object,
    //then add the list item to a new array list so they can be displayed in the list view
    private void createRoadRoadworkList(LinkedList<RoadWorksItem> roadworkList)
    {

        String roadName = roadSearchEditText.getText().toString().toUpperCase(Locale.ROOT);

        ArrayList<RoadWorksListItem> roadWorksArray = new ArrayList<>();

        for (int i = 0; i < roadworkList.size(); i++) {

            if(roadworkList.get(i).getTitle().equalsIgnoreCase(roadName)) {

                RoadWorksListItem rwlistItem = new RoadWorksListItem();
                rwlistItem.setItemNumber(roadworkList.get(i).getItemNumber());
                rwlistItem.setTitle(roadworkList.get(i).getTitle());
                rwlistItem.setDescription(roadworkList.get(i).getDescription());
                rwlistItem.setStartDate(roadworkList.get(i).getStartDate());
                rwlistItem.setEndDate(roadworkList.get(i).getEndDate());

                roadWorksArray.add(rwlistItem);
            }
        }


        ArrayAdapter adapter = new ArrayAdapter<RoadWorksListItem>(this,
                R.layout.activity_listview, roadWorksArray);

        ListView listView = (ListView) findViewById(R.id.roadwork_road_list);
        listView.setAdapter(adapter);
    }

    //Extract core roadworks details from RoadWorksItem object and add the RoadWorksListItem object,
    //then add the list item to a new array list so they can be displayed in the list view
    private void createJourneyRoadworkList(LinkedList<RoadWorksItem> roadworkList)
    {

        //Get the date selected by the user
        int day = datePickerJourneyPlanner.getDayOfMonth();
        int month = datePickerJourneyPlanner.getMonth()+1;
        int year = datePickerJourneyPlanner.getYear();

        Date selectedDateParsed = new Date();
        Date startDateParsed = new Date();
        Date endDateParsed = new Date();

        String selectedDateParsedString = "";
        String startDateParsedString = "";
        String endDateParsedString = "";

        //Format date entered by user
        String selectedDate = String.valueOf(day) + "-0" + String.valueOf(month) + "-" + String.valueOf(year);
        SimpleDateFormat formatter = new SimpleDateFormat ("dd-MM-yyyy");
        try {
            selectedDateParsed = formatter.parse(selectedDate);
            //System.out.println(selectedDateParsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Array list of RoadworksListItem objects to hold any roadworks that match the date entered by the user
        ArrayList<RoadWorksListItem> roadWorksArray = new ArrayList<>();

        for (int i = 0; i < roadworkList.size(); i++) {

            String startDate = roadworkList.get(i).getStartDate().substring(0, roadworkList.get(i).getStartDate().indexOf(":")-5);
            SimpleDateFormat startDateformat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            try {
                startDateParsed = startDateformat.parse(startDate);
                startDateformat.applyPattern("dd-MM-yyyy");
                startDateParsedString = startDateformat.format(startDateParsed);
                //System.out.println(startDateParsedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat endDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String endDate = roadworkList.get(i).getEndDate().substring(0, roadworkList.get(i).getEndDate().indexOf(":")-5);
            try {
                endDateParsed = endDateFormat.parse(endDate);
                endDateFormat.applyPattern("dd-MM-yyyy");
                endDateParsedString = endDateFormat.format(endDateParsed);
                //System.out.println(endDateParsedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            selectedDateParsedString = startDateformat.format(selectedDateParsed);
            //System.out.println(selectedDateParsedString);
            if(startDateParsedString.equalsIgnoreCase(selectedDateParsedString) || endDateParsedString.equalsIgnoreCase(selectedDateParsedString)) {

                RoadWorksListItem rwlistItem = new RoadWorksListItem();
                rwlistItem.setItemNumber(roadworkList.get(i).getItemNumber());
                rwlistItem.setTitle(roadworkList.get(i).getTitle());
                rwlistItem.setDescription(roadworkList.get(i).getDescription());
                rwlistItem.setStartDate(roadworkList.get(i).getStartDate());
                rwlistItem.setEndDate(roadworkList.get(i).getEndDate());

                roadWorksArray.add(rwlistItem);
            }
        }


        ArrayAdapter adapter = new ArrayAdapter<RoadWorksListItem>(this,
                R.layout.activity_listview, roadWorksArray);

        ListView listView = (ListView) findViewById(R.id.roadwork_journey_list);
        listView.setAdapter(adapter);

    }


}

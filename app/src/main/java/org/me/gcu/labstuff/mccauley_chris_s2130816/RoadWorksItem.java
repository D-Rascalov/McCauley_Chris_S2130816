package org.me.gcu.labstuff.mccauley_chris_s2130816;

//Chris McCauley
//S2130816
//Mobile Platform Development

public class RoadWorksItem {

    //attributes
    private int itemNumber;
    private String title;
    private String description;
    private String latitude;
    private String longitude;
    private String startDate;
    private String endDate;
    private String pubDate;

//*********constructor**********

    public RoadWorksItem(){
        //default constructor
    }

    public RoadWorksItem (int itemNumberIn, String titleIn, String descriptionIn, String latitudeIn, String longitudeIn, String startDateIn, String endDateIn, String pubDateIn)
    {
        this.itemNumber=itemNumberIn;
        this.title=titleIn;
        this.description=descriptionIn;
        this.latitude=latitudeIn;
        this.longitude=longitudeIn;
        this.startDate=startDateIn;
        this.endDate=endDateIn;
        this.pubDate=pubDateIn;
    }

//************setters*************

    public void setItemNumber(int itemNumberIn){

        this.itemNumber=itemNumberIn;
    }
    public void setTitle(String titleIn){

        this.title=titleIn;
    }
    public void setDescription(String descriptionIn){

        this.description=descriptionIn;
    }
    public void setLatitude(String latitudeIn){

        this.latitude=latitudeIn;
    }
    public void setLongitude(String longitudeIn){

        this.longitude=longitudeIn;
    }
    public void setStartDate(String startDateIn){

        this.startDate=startDateIn;
    }
    public void setEndDate(String endDateIn){

        this.endDate=endDateIn;
    }
    public void setPubDate(String pubDateIn){

        this.pubDate=pubDateIn;
    }

//***********getters*****************

    public int getItemNumber(){

        return this.itemNumber;
    }
    public String getTitle(){

        return this.title;
    }
    public String getDescription(){

        return this.description;
    }
    public String getLatitude(){

        return this.latitude;
    }
    public String getLongitude(){

        return this.longitude;
    }
    public String getStartDate(){

        return this.startDate;
    }
    public String getEndDate(){

        return this.endDate;
    }
    public String getPubDate(){

        return this.pubDate;
    }


    @Override
    public String toString(){

        StringBuilder itemString= new StringBuilder();

        itemString.append("Item Number: ");
        itemString.append(this.itemNumber).append(" , ");
        itemString.append("Title: ");
        itemString.append(this.title ).append(" , ");
        itemString.append("Description: ");
        itemString.append(this.description ).append(" , ");
        itemString.append("Latitude: ");
        itemString.append( this.latitude ).append(" , ");
        itemString.append("Longitude: ");
        itemString.append( this.longitude ).append(" , ");
        itemString.append("Start Date: ");
        itemString.append( this.startDate ).append(" , ");
        itemString.append("End Date: ");
        itemString.append( this.endDate ).append(" , ");
        itemString.append("Published Date: ");
        itemString.append( this.pubDate );

        return itemString.toString();
    }
}

package org.me.gcu.labstuff.mccauley_chris_s2130816;

//Chris McCauley
//S2130816
//Mobile Platform Development

public class RoadWorksListItem {

    //attributes
    private int itemNumber;
    private String title;
    private String description;
    private String startDate;
    private String endDate;

//*********constructor**********

    public RoadWorksListItem(){
        //default constructor
    }

    public RoadWorksListItem (int itemNumberIn, String titleIn, String descriptionIn, String startDateIn, String endDateIn)
    {
        this.itemNumber=itemNumberIn;
        this.title=titleIn;
        this.description=descriptionIn;
        this.startDate=startDateIn;
        this.endDate=endDateIn;
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
    public void setStartDate(String startDateIn){

        this.startDate=startDateIn;
    }
    public void setEndDate(String endDateIn){

        this.endDate=endDateIn;
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
    public String getStartDate(){

        return this.startDate;
    }
    public String getEndDate(){

        return this.endDate;
    }


    @Override
    public String toString(){

        StringBuilder itemString= new StringBuilder();

        itemString.append(this.itemNumber).append(" , ");
        itemString.append(this.title ).append(" , ");
        itemString.append(this.description ).append(" , ");
        itemString.append( this.startDate ).append(" , ");
        itemString.append( this.endDate );

        return itemString.toString();
    }

}

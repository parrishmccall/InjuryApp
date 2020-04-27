package com.example.injury3;

public class Cases {

    private String accident_type;
    private String date;
    private String description;

    public Cases(){}

    public Cases(String accident_type, String date, String description){
        this.accident_type = accident_type;
        this.date = date;
        this.description = description;
    }

    private void setAccident_type(String accident_type){
        this.accident_type = accident_type;
    }

    public String getAccident_type(){
        return accident_type;
    }

    private void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    private void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }


}

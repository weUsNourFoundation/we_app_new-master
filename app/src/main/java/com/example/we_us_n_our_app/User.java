package com.example.we_us_n_our_app;

public class User {
    private String Name,JoiningDate,Email,Location;
    public User()
    {

    }
    public User(String name, String joiningdate,String email, String location){
        this.Name=name;
        this.JoiningDate= joiningdate;
        this.Email= email;
        this.Location= location;
    }
    public String getName() {
        return Name;
    }
    public String getJoiningDate(){return JoiningDate;}
    public String getEmail(){return Email;}
    public String getLocation(){return Location;}
}

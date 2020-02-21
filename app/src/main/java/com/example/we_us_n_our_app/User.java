package com.example.we_us_n_our_app;

public class User {
    private String Name,JoiningDate,Email;
    public User()
    {

    }
    public User(String name, String joiningdate,String email){
        this.Name=name;
        this.JoiningDate=joiningdate;
        this.Email=email;
    }
    public String getName() {
        return Name;
    }
    public String getJoiningDate(){return JoiningDate;}
    public String getEmail(){return Email;}
}

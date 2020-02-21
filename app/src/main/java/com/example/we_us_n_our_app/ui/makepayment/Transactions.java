package com.example.we_us_n_our_app.ui.makepayment;

public class Transactions {
    private String amount,date,uid;
    public Transactions()
    {

    }
    public Transactions(String amount, String date,String uid){
        this.amount=amount;
        this.date=date;
        this.uid=uid;
    }
    public String getAmount() {
        return amount;
    }
    public String getDate(){return date;}
    public String getUid(){return uid;}
}

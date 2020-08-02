package com.example.covidcure;

public class ExampleItem {

    String mimgurl;
    String mtitle;
    String mdescription;
    String mdate;

    //////////////////
    String murl;
    ///////////////


    public ExampleItem(String mimgurl, String mtitle, String mdescription, String mdate, String murl) {
        this.mimgurl = mimgurl;
        this.mtitle = mtitle;
        this.mdescription = mdescription;
        this.mdate = mdate;

        ///////////
        this.murl = murl;
        /////////////
    }

    public String getMimgurl() {
        return mimgurl;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getMdescription() {
        return mdescription;
    }

    public String getMdate() {
        return mdate;
    }

    ///////////////
    public String getMurl() {return murl;}
    //////////////

}

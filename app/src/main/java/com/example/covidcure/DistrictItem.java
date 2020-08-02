package com.example.covidcure;

public class DistrictItem
{
    String dist_name, dist_confirm, dist_active, dist_death, dist_recovered;

    public DistrictItem(String dist_name, String dist_confirm, String dist_active, String dist_death, String dist_recovered) {
        this.dist_name = dist_name;
        this.dist_confirm = dist_confirm;
        this.dist_active = dist_active;
        this.dist_death = dist_death;
        this.dist_recovered = dist_recovered;
    }

    public String getDist_name() {
        return dist_name;
    }

    public String getDist_confirm() {
        return dist_confirm;
    }

    public String getDist_active() {
        return dist_active;
    }

    public String getDist_death() {
        return dist_death;
    }

    public String getDist_recovered() {
        return dist_recovered;
    }
}

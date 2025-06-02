package com.example.exercice.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class DashboardBean {

    public int getActiveUsers() {
        // Example: Replace this with database count
        return 10;
    }

    public int getInactiveUsers() {
        return 10;
    }


}

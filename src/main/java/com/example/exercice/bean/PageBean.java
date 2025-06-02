package com.example.exercice.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PageBean implements Serializable {
    private String currentPage = "/view/dashboard.xhtml"; // Default page

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
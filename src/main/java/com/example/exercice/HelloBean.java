package com.example;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * A simple Jakarta EE 10 CDI bean.
 */
@Named("helloBean")  // This makes it accessible in JSF via #{helloBean}
@RequestScoped       // A new instance per HTTP request
public class HelloBean {

    public String getMessage() {
        return "Hello World from HelloBean!";
    }
}

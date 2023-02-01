package com.cooksys.springassessmentsocialmedia.assessment1team2.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}

package org.pahappa.systems.registrationapp.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Dependant extends User {
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Dependant() {}

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum Gender {
        MALE, FEMALE
    }
}


package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.exception.InvalidDateFormatException;
import org.pahappa.systems.registrationapp.exception.InvalidNameException;
import org.pahappa.systems.registrationapp.exception.UsernameAlreadyExistsException;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@ManagedBean
@SessionScoped
public class UpdateDependant implements Serializable {
    private String username;
    private String firstname;
    private String lastname;
    private String dateOfBirth;
    private String gender;
    private Dependant dependant;
    private Long id;
    User user;
    UserService userService = new UserService();

    DependantService dependantService=new DependantService();

    @PostConstruct
    public void init() {
        dependant = new Dependant();
        user = new User();

//        // Retrieve the user identifier from the cookie
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
//        Cookie cookie = (Cookie) cookies.get("selectedUser");
//
//        if (cookie != null) {
//            String username = cookie.getValue();
//            user = userService.getUserOfUsername(username);
//
//        }
    }

    public UpdateDependant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String updateADependant() {
        try {

            System.out.println("update dependant called"); // Debug statement
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse(dateOfBirth);

//            Dependant dependant = new Dependant();
            dependant.setId(id);
            dependant.setUsername(username);
            dependant.setFirstname(firstname);
            dependant.setLastname(lastname);
            dependant.setDateOfBirth(dob);
            dependant.setGender(gender);
            dependant.setUser(user); // Set the user retrieved from the cookie

            // Perform validations
            dependantService.validateUsername(dependant.getUsername());
            dependantService.validateName(dependant.getFirstname(), "First name");
            dependantService.validateName(dependant.getLastname(), "Last name");
            dependantService.validateDateOfBirth(dependant.getDateOfBirth());

            System.out.println("\n\nDependant: "+ dependant);

            dependantService.updateDependant(dependant);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant Updated successfully"));

//            System.out.println("Dependant added successfully: " + user.getUsername()); // Debug statement

            return "/pages/getUser";
        } catch (InvalidNameException | InvalidDateFormatException | UsernameAlreadyExistsException | ParseException e) {
            System.out.println("Exception: " + e.getMessage()); // Debug statement
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return "pages/getUser";
        }
    }

    public String selectDependant(Long dependant_id) {
        System.out.println("Commenced SLUSR");
        this.dependant = dependantService.getDependantById(dependant_id);
        this.id = dependant.getId();
        this.firstname=dependant.getFirstname();
        this.lastname=dependant.getLastname();
        this.gender=dependant.getGender();
        this.dateOfBirth=dependant.getDateOfBirth().toString();
        this.username=dependant.getUsername();
        this.user = dependant.getUser();

        System.out.println("Ended SLUSR");
        return "/pages/updateDependant";
    }

}

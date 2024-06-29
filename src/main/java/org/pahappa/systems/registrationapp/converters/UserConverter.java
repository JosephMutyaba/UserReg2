package org.pahappa.systems.registrationapp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;
import javax.inject.Inject;

@FacesConverter(forClass = User.class)
public class UserConverter implements Converter {

    @Inject
    private UserService userService; // Inject your UserService to retrieve user by username

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return userService.getUserOfUsername(value); // Implement findByUsername in your UserService
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof User) {
            return ((User) object).getUsername(); // Assumes username is unique
        } else {
            throw new IllegalArgumentException("Object is not of type User");
        }
    }
}

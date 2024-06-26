package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class DeleteUserView implements Serializable {
    private User user;
    UserService userService=new UserService();

    @PostConstruct
    public void init() {
        user = new User();
    }

    public DeleteUserView() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String deleteUser() {
        boolean userDeleted = userService.deleteUserOfUsername(user.getUsername());
        if (userDeleted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User deleted successfully"));
//            users=userService.displayAllUsers();

            return "/pages/displayUsers"; // Navigate to the index page
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User not found"));
            return null;
        }
    }

    public String selectUser(User selectedUser) {
        this.user = selectedUser;
//        this.username = selectedUser.getUsername();
        return "getUser?faces-redirect=true"; // Navigate to getUser.xhtml
    }

    public void selectUserNewUI(User selectedUser) {
        this.user = selectedUser;
//        this.username = selectedUser.getUsername();
//        return "getUser?faces-redirect=true"; // Navigate to getUser.xhtml
    }



}

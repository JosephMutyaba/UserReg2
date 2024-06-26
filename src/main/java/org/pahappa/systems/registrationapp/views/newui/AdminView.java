package org.pahappa.systems.registrationapp.views.newui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class AdminView {

    public String goToDashboard() {
        return "/pages/adminDashboard.xhtml?faces-redirect=true";
    }

    public String goToUsers() {
        return "users.xhtml?faces-redirect=true";
    }

    public String goToDependants() {
        return "dependants.xhtml?faces-redirect=true";
    }

    public String goToSettings() {
        return "settings.xhtml?faces-redirect=true";
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

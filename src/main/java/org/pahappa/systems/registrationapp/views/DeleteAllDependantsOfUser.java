package org.pahappa.systems.registrationapp.views;

import org.pahappa.systems.registrationapp.services.DependantService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class DeleteAllDependantsOfUser implements Serializable {
    private Long user_id;

    DependantService dependantService=new DependantService();

    public DeleteAllDependantsOfUser() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void deleteAllDependants() {
        System.out.println("deleteAllDependants entered");
        dependantService.deleteDependantsByUserId(user_id);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dependant deleted successfully"));
        System.out.println("deleteAllDependants exited");
    }

    public void selectUserId(Long user_id) {
        this.user_id = user_id;
    }
}

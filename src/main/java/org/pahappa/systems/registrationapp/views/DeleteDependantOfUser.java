package org.pahappa.systems.registrationapp.views;


import org.pahappa.systems.registrationapp.services.DependantService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class DeleteDependantOfUser implements Serializable {
    private Long dependant_id;

    public DeleteDependantOfUser() {
    }

    public Long getDependant_id() {
        return dependant_id;
    }

    public void setDependant_id(Long dependant_id) {
        this.dependant_id = dependant_id;
    }

    private final DependantService dependantService=new DependantService();

    public void deleteDependant() {
        dependantService.deleteDependantById(dependant_id);
    }

    public void deleteDependantAdmin() {
        dependantService.deleteDependantById(dependant_id);
//        return "/pages/dependants.xhtml?faces-redirect=true";
    }

    public void selectedId(Long dependant_id){
        this.dependant_id=dependant_id;
    }

    public void selectedIdAdmin(Long dependant_id){
        this.dependant_id=dependant_id;
//        return "/pages/deleteDependant.xhtml?faces-redirect=true";
    }

}

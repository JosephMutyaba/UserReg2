package org.pahappa.systems.registrationapp.views.newui;

import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.services.DependantService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class GetAllDependants implements Serializable {
    private List<Dependant> dependants;
    DependantService dependantService=new DependantService();

    public List<Dependant> getDependants() {
        return dependantService.getAllDependants();
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }

    @PostConstruct
    public void init() {
        dependants = new ArrayList<>();
        this.dependants= dependantService.getAllDependants();
    }
}

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
    private Long selectedUserId; // Add getter and setter
    private String selectedGender; // Add getter and setter

    private List<Dependant> dependants;

    DependantService dependantService=new DependantService();

    @PostConstruct
    public void init() {
        dependants = new ArrayList<>();
        this.dependants= dependantService.getAllDependants();
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public String getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(String selectedGender) {
        this.selectedGender = selectedGender;
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }


    public void filterDependants() {
        if ((selectedUserId == null || selectedUserId == 0) && (selectedGender == null || selectedGender.isEmpty())) {
            dependants = dependantService.getAllDependants();
        } else if (selectedUserId != null && selectedUserId != 0 && (selectedGender == null || selectedGender.isEmpty())) {
            dependants = dependantService.findDependantsByUser(selectedUserId);
        } else if ((selectedUserId == null || selectedUserId == 0) && selectedGender != null && !selectedGender.isEmpty()) {
            dependants = dependantService.findDependantsByGender(selectedGender);
        } else if (selectedUserId != null && selectedUserId != 0 && selectedGender != null && !selectedGender.isEmpty()) {
            dependants = dependantService.findDependantsByUserAndGender(selectedUserId, selectedGender);
        }
    }

    public List<Dependant> getDependants() {
        dependants = dependantService.getAllDependants();
        filterDependants();
        return dependants;
    }





}

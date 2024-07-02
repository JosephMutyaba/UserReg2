package org.pahappa.systems.registrationapp.views.newui;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.pahappa.systems.registrationapp.exception.DatabaseException;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.UserService;
import org.primefaces.model.chart.PieChartModel;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class ChartView {
    private List<User> users;
    private final UserService userService = new UserService();
    private int maleUserCount;
    private int femaleUserCount;
    private String maleFemaleRatio;


    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        users=new ArrayList<>();
        users=userService.displayAllUsers();
        computeStatistics();
        createPieModel();
    }


    public void setMaleUserCount(int maleUserCount) {
        this.maleUserCount = maleUserCount;
    }

    public void setFemaleUserCount(int femaleUserCount) {
        this.femaleUserCount = femaleUserCount;
    }

    public String getMaleFemaleRatio() {
        computeStatistics();
        return maleFemaleRatio;
    }

    public void setMaleFemaleRatio(String maleFemaleRatio) {
        this.maleFemaleRatio = maleFemaleRatio;
    }

    public List<User> getUsers() {
        users=userService.displayAllUsers();
        computeStatistics();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private int getMaleUserCount() {
        users=userService.displayAllUsers();
        computeStatistics();
        return maleUserCount;
    }

    private int getFemaleUserCount() {
        users=userService.displayAllUsers();
        computeStatistics();
        return femaleUserCount;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    private void createPieModel() {
        pieModel = new PieChartModel();

        // Fetch the counts of male and female users
        int maleCount = getMaleUserCount();
        int femaleCount = getFemaleUserCount();

        pieModel.set("Male", maleCount);
        pieModel.set("Female", femaleCount);

        pieModel.setTitle("Gender Statistics");
        pieModel.setLegendPosition("w");
        pieModel.setShowDataLabels(true);
    }


    private void computeStatistics() {
        users = userService.displayAllUsers();

        long maleCount = users.stream()
                .filter(user -> user.getGender().equalsIgnoreCase("Male"))
                .count();

        long femaleCount = users.stream()
                .filter(user -> user.getGender().equalsIgnoreCase("Female"))
                .count();

        this.maleUserCount = (int) maleCount;
        this.femaleUserCount = (int) femaleCount;

        if (femaleCount > 0) {
            this.maleFemaleRatio = String.format("%.1f:1", (double) maleCount / femaleCount);
        } else {
            this.maleFemaleRatio = "N/A";
        }
    }
}












//package org.pahappa.systems.registrationapp.views.newui;
//
//import javax.annotation.PostConstruct;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
//import org.pahappa.systems.registrationapp.exception.DatabaseException;
//import org.pahappa.systems.registrationapp.models.User;
//import org.pahappa.systems.registrationapp.services.UserService;
//import org.primefaces.model.chart.PieChartModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@ManagedBean
//@RequestScoped
//public class ChartView {
//    private List<User> users;
//    private final UserService userService = new UserService();
//    private int maleUserCount;
//    private int femaleUserCount;
//    private String maleFemaleRatio;
//    private PieChartModel pieModel;
//
//    @PostConstruct
//    public void init() {
//        users = new ArrayList<>();
//        try {
//            users = userService.displayAllUsers();
//            computeStatistics();
//            createPieModel();
//        } catch (DatabaseException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
//        }
//    }
//
//    public PieChartModel getPieModel() {
//        return pieModel;
//    }
//
//    private void computeStatistics() {
//        long maleCount = users.stream().filter(user -> "Male".equalsIgnoreCase(user.getGender())).count();
//        long femaleCount = users.stream().filter(user -> "Female".equalsIgnoreCase(user.getGender())).count();
//        this.maleUserCount = (int) maleCount;
//        this.femaleUserCount = (int) femaleCount;
//        this.maleFemaleRatio = (femaleCount > 0) ? String.format("%.1f:1", (double) maleCount / femaleCount) : "N/A";
//    }
//
//    private void createPieModel() {
//        pieModel = new PieChartModel();
//        computeStatistics();
//        pieModel.set("Male", maleUserCount);
//        pieModel.set("Female", femaleUserCount);
//        pieModel.setTitle("Gender Statistics");
//        pieModel.setLegendPosition("w");
//        pieModel.setShowDataLabels(true);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package org.pahappa.systems.registrationapp.views.newui;
////
////import javax.annotation.PostConstruct;
////import javax.faces.application.FacesMessage;
////import javax.faces.bean.ManagedBean;
////import javax.faces.bean.RequestScoped;
////import javax.faces.context.FacesContext;
////
////import org.pahappa.systems.registrationapp.exception.DatabaseException;
////import org.pahappa.systems.registrationapp.models.User;
////import org.pahappa.systems.registrationapp.services.UserService;
////import org.primefaces.model.chart.PieChartModel;
////
////import java.util.ArrayList;
////import java.util.List;
////
////@ManagedBean
////@RequestScoped
////public class ChartView {
////    private List<User> users;
////    private final UserService userService = new UserService();
////    private int maleUserCount;
////    private int femaleUserCount;
////    private String maleFemaleRatio;
////
////
////    private PieChartModel pieModel;
////
////    @PostConstruct
////    public void init() {
////        users=new ArrayList<>();
////        try {
////            users=userService.displayAllUsers();
////        } catch (DatabaseException e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
////
////        }
////        computeStatistics();
////        createPieModel();
////    }
////
////
////    public void setMaleUserCount(int maleUserCount) {
////        this.maleUserCount = maleUserCount;
////    }
////
////    public void setFemaleUserCount(int femaleUserCount) {
////        this.femaleUserCount = femaleUserCount;
////    }
////
////    public String getMaleFemaleRatio() {
////        computeStatistics();
////        return maleFemaleRatio;
////    }
////
////    public void setMaleFemaleRatio(String maleFemaleRatio) {
////        this.maleFemaleRatio = maleFemaleRatio;
////    }
////
////    public List<User> getUsers() {
////        try {
////            users=userService.displayAllUsers();
////        } catch (DatabaseException e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
////
////        }
////        computeStatistics();
////        return users;
////    }
////
////    public void setUsers(List<User> users) {
////        this.users = users;
////    }
////
////    private int getMaleUserCount() {
////        try {
////            users=userService.displayAllUsers();
////        } catch (DatabaseException e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
////
////        }
////        computeStatistics();
////        return maleUserCount;
////    }
////
////    private int getFemaleUserCount() {
////        try {
////            users=userService.displayAllUsers();
////        } catch (DatabaseException e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
////
////        }
////        computeStatistics();
////        return femaleUserCount;
////    }
////
////    public PieChartModel getPieModel() {
////        return pieModel;
////    }
////
////    private void createPieModel() {
////        pieModel = new PieChartModel();
////
////        // Fetch the counts of male and female users
////        int maleCount = getMaleUserCount();
////        int femaleCount = getFemaleUserCount();
////
////        pieModel.set("Male", maleCount);
////        pieModel.set("Female", femaleCount);
////
////        pieModel.setTitle("Gender Statistics");
////        pieModel.setLegendPosition("w");
////        pieModel.setShowDataLabels(true);
////    }
////
////
////    private void computeStatistics() {
////        try {
////            users = userService.displayAllUsers();
////        } catch (DatabaseException e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
////
////        }
////
////        long maleCount = users.stream()
////                .filter(user -> user.getGender().equalsIgnoreCase("Male"))
////                .count();
////
////        long femaleCount = users.stream()
////                .filter(user -> user.getGender().equalsIgnoreCase("Female"))
////                .count();
////
////        this.maleUserCount = (int) maleCount;
////        this.femaleUserCount = (int) femaleCount;
////
////        if (femaleCount > 0) {
////            this.maleFemaleRatio = String.format("%.1f:1", (double) maleCount / femaleCount);
////        } else {
////            this.maleFemaleRatio = "N/A";
////        }
////    }
////}

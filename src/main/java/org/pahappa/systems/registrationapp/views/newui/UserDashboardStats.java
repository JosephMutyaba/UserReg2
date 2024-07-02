package org.pahappa.systems.registrationapp.views.newui;

import org.pahappa.systems.registrationapp.exception.DatabaseException;
import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;
import org.pahappa.systems.registrationapp.views.LoginBean;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
public class UserDashboardStats implements Serializable {
    private String username;
    private User user;
    private final UserService userService = new UserService();
    private List<Dependant> dependants;
    private final DependantService dependantService = new DependantService();
    private int maleUserCount;
    private int femaleUserCount;
    private String maleFemaleRatio;

    // Pie chart model
    private PieChartModel pieModel;

    // Bar chart model
    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        user = new User();
        dependants = new ArrayList<>();
        // Load data and create charts after initialization
        loadUserData();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Dependant> getDependants() {
        return dependantService.getAllDependantsByUserId(user.getId());
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }

    public int getMaleUserCount() {

        computeStatistics();
        return maleUserCount;
    }

    public void setMaleUserCount(int maleUserCount) {
        this.maleUserCount = maleUserCount;
    }

    public int getFemaleUserCount() {
        computeStatistics();
        return femaleUserCount;
    }

    public void setFemaleUserCount(int femaleUserCount) {
        this.femaleUserCount = femaleUserCount;
    }

    public String getMaleFemaleRatio() {
        return maleFemaleRatio;
    }

    public void setMaleFemaleRatio(String maleFemaleRatio) {
        this.maleFemaleRatio = maleFemaleRatio;
    }

    public PieChartModel getPieModel() {
        dependants = dependantService.getAllDependantsByUserId(user.getId());
        computeStatistics();
        return pieModel;
    }

    public BarChartModel getBarModel() {
        dependants = dependantService.getAllDependantsByUserId(user.getId());
        computeStatistics();
        return barModel;
    }

    public void loadUserData() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String usernameParam = params.get("user");
        if (usernameParam != null) {
            User foundUser = userService.getUserOfUsername(usernameParam);
            if (foundUser != null) {
                this.user = foundUser;
                this.username = foundUser.getUsername();
                this.dependants = dependantService.getAllDependantsByUserId(user.getId());
                computeStatistics();
                createPieModel();
                createBarModel();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found", "User not found"));
            }
        }

    }



    private void computeStatistics() {
        dependants = dependantService.getAllDependantsByUserId(user.getId());

        long maleCount = dependants.stream()
                .filter(dependant -> dependant.getGender().equalsIgnoreCase("Male"))
                .count();

        long femaleCount = dependants.stream()
                .filter(dependant -> dependant.getGender().equalsIgnoreCase("Female"))
                .count();

        this.maleUserCount = (int) maleCount;
        this.femaleUserCount = (int) femaleCount;

//        if (femaleCount > 0) {
//            this.maleFemaleRatio = String.format("%.1f:1", (double) maleCount / femaleCount);
//        } else {
//            this.maleFemaleRatio = "N/A";
//        }
        this.maleFemaleRatio=calculateRatio(maleUserCount, femaleUserCount);
    }

    private void createPieModel() {
        pieModel = new PieChartModel();

        dependants=dependantService.getAllDependantsByUserId(user.getId());
        computeStatistics();

        // Fetch the counts of male and female users
        int maleCount = getMaleUserCount();
        int femaleCount = getFemaleUserCount();

        System.out.println("Creating Pie Model: Male Count = " + maleCount + ", Female Count = " + femaleCount);

        pieModel.set("Male", maleCount);
        pieModel.set("Female", femaleCount);

        pieModel.setTitle("Gender Statistics");
        pieModel.setLegendPosition("w");
        pieModel.setShowDataLabels(true);
//        pieModel.get("animatePie");
    }

    private void createBarModel() {
        barModel = new BarChartModel();

        ChartSeries dependantsSeries = new ChartSeries();
        dependantsSeries.setLabel("Dependants");

        // Get the current date and determine the start of the week
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);

        dependants=dependantService.getAllDependantsByUserId(user.getId());
        computeStatistics();

        // Populate the series with data for each day of the current week
        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            String dayLabel = day.getDayOfWeek().toString();

            int count = getDependantsRegisteredOn(toDate(day));
            System.out.println("Creating Bar Model: " + dayLabel + " Count = " + count);
            dependantsSeries.set(dayLabel, count);
        }

        barModel.addSeries(dependantsSeries);

        barModel.setTitle("Weekly Activity");
        barModel.setLegendPosition("ne");
    }

    private int getDependantsRegisteredOn(Date date) {
        List<Dependant> dependantsOnDay = null;
        dependantsOnDay = dependantService.getDependantsRegisteredOnADayAndOfUserId(date, user.getId());
        return dependantsOnDay.size();
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }



    ///////////////////////   RATIOS    ///////////////////////
    public String calculateRatio(int males, int females) {
        // Handle case where females count is zero
        if (females == 0) {
            if (males == 0) {
                return "0:0"; // Both males and females are zero
            } else {
                return "Infinity"; // Males are non-zero, females are zero
            }
        }

        // Calculate the ratio of males to females
        int gcd = gcd(males, females);
        int simplifiedMales = males / gcd;
        int simplifiedFemales = females / gcd;

        return simplifiedMales + ":" + simplifiedFemales;
    }

    // Method to find greatest common divisor (gcd) using Euclid's algorithm
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

}

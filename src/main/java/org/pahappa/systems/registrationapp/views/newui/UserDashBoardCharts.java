package org.pahappa.systems.registrationapp.views.newui;

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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class UserDashBoardCharts implements Serializable {
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
        dependants = retrieveLoggedInUserFromLoginBean();
        computeStatistics();
        createPieModel();
        createBarModel();
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
        return dependants;
    }

    public void setDependants(List<Dependant> dependants) {
        this.dependants = dependants;
    }

    public int getMaleUserCount() {
        return maleUserCount;
    }

    public void setMaleUserCount(int maleUserCount) {
        this.maleUserCount = maleUserCount;
    }

    public int getFemaleUserCount() {
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
        return pieModel;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private List<Dependant> retrieveLoggedInUserFromLoginBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean loginBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginBean}", LoginBean.class);
        user = loginBean.getLoggedInUser();
        return dependantService.getAllDependantsByUserId(user.getId());
    }

    private void computeStatistics() {
        long maleCount = dependants.stream()
                .filter(dependant -> dependant.getGender().equalsIgnoreCase("Male"))
                .count();

        long femaleCount = dependants.stream()
                .filter(dependant -> dependant.getGender().equalsIgnoreCase("Female"))
                .count();

        this.maleUserCount = (int) maleCount;
        this.femaleUserCount = (int) femaleCount;

        if (femaleCount > 0) {
            this.maleFemaleRatio = String.format("%.1f:1", (double) maleCount / femaleCount);
        } else {
            this.maleFemaleRatio = "N/A";
        }
    }

    private void createPieModel() {
        pieModel = new PieChartModel();

        pieModel.set("Male", maleUserCount);
        pieModel.set("Female", femaleUserCount);

        pieModel.setTitle("Gender Statistics");
        pieModel.setLegendPosition("w");
        pieModel.setShowDataLabels(true);
    }

    private void createBarModel() {
        barModel = new BarChartModel();

        ChartSeries dependantsSeries = new ChartSeries();
        dependantsSeries.setLabel("Dependants");

        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);

        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            String dayLabel = day.getDayOfWeek().toString();
            int count = getDependantsRegisteredOn(toDate(day));
            dependantsSeries.set(dayLabel, count);
        }

        barModel.addSeries(dependantsSeries);
        barModel.setTitle("Weekly Activity");
        barModel.setLegendPosition("ne");
    }

    private int getDependantsRegisteredOn(Date date) {
        List<Dependant> dependantsOnDay = dependantService.getDependantsRegisteredOnADayAndOfUserId(date, user.getId());
        return dependantsOnDay.size();
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

package org.pahappa.systems.registrationapp.views.newui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.registrationapp.models.Dependant;
import org.pahappa.systems.registrationapp.models.User;
import org.pahappa.systems.registrationapp.services.DependantService;
import org.pahappa.systems.registrationapp.services.UserService;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class WeeklyActivityView {

    private BarChartModel barModel;
    private final UserService userService=new UserService();
    private List<User> users;
    private final DependantService dependantService=new DependantService();
    private List<Dependant> dependants;

    @PostConstruct
    public void init() {
        users=new ArrayList<>();
        dependants=new ArrayList<>();

        createBarModel();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private void createBarModel() {
        barModel = new BarChartModel();

        ChartSeries users = new ChartSeries();
        users.setLabel("Users");

        ChartSeries dependants = new ChartSeries();
        dependants.setLabel("Dependants");

        // Get the current date and determine the start of the week
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);

        // Populate the series with data for each day of the current week
        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            String dayLabel = day.getDayOfWeek().toString();

            users.set(dayLabel, getUsersRegisteredOn(toDate(day)));
            dependants.set(dayLabel, getDependantsRegisteredOn(toDate(day)));
        }

        barModel.addSeries(users);
        barModel.addSeries(dependants);

        barModel.setTitle("Weekly Activity");
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
    }

    private int getUsersRegisteredOn(Date date) {
        users = userService.getUsersRegisteredOnADay(date);


        return users.size(); // Dummy data
    }

    private int getDependantsRegisteredOn(Date date) {

        dependants = dependantService.getUsersRegisteredOnADay(date);

        return dependants.size(); // Dummy data
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

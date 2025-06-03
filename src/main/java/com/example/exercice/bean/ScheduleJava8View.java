package com.example.exercice.bean;

import com.example.exercice.entity.CheckInOut;
import com.example.exercice.entity.User;
import com.example.exercice.service.CheckInOutService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Named("scheduleJava8View")
@ViewScoped
public class ScheduleJava8View implements Serializable {

    @Inject
    private CheckInOutService checkInOutService;

    private ScheduleModel eventModel;
    private ScheduleEvent<?> event;

    private User selectedUser;
    private String serverTimeZone = ZoneId.systemDefault().toString();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        event = new DefaultScheduleEvent<>();
    }

    public String loadUserScheduleAndNavigate(User user) {
        System.out.println("User ID: " + user.getId());
        System.out.println("User Name: " + user.getName());

        this.selectedUser = user;
        eventModel = new DefaultScheduleModel();
        event = new DefaultScheduleEvent<>();

        List<CheckInOut> checkInOutList = checkInOutService.findByUser(user);

        if (checkInOutList == null || checkInOutList.isEmpty()) {
            System.out.println("No check-in/out records found for user ID: " + user.getId());
        } else {
            System.out.println("Found " + checkInOutList.size() + " records for user ID: " + user.getId());
        }

        for (CheckInOut record : checkInOutList) {
            System.out.println("Adding event for CheckIn = " + record.getCheckIn() + ", CheckOut = " + record.getCheckOut());
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .title("Check-In/Out")
                    .startDate(record.getCheckIn())
                    .endDate(record.getCheckOut())
                    .description("Check-In: " + record.getCheckIn() + " | Check-Out: " + record.getCheckOut())
                    .build();
            eventModel.addEvent(event);
        }

        // Add static event 1 June 2025
        LocalDateTime staticStart = LocalDateTime.of(2025, 6, 1, 10, 0);
        LocalDateTime staticEnd = LocalDateTime.of(2025, 6, 1, 11, 0);
        DefaultScheduleEvent<?> staticEvent = DefaultScheduleEvent.builder()
                .title("Static Event 1 June 2025")
                .startDate(staticStart)
                .endDate(staticEnd)
                .description("Static Event from 10 AM to 11 AM")
                .build();
        eventModel.addEvent(staticEvent);

        System.out.println("Event Model has " + eventModel.getEvents().size() + " events.");

        return "/view/user-calendar.xhtml?faces-redirect=true";
    }

    public void addEvent() {
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent<>();
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        this.event = selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .title("New Event")
                .build();
    }

    // Getters and Setters
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }
}

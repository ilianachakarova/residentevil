package softuni.residentevil.domain.models.binding;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.residentevil.domain.entities.Creator;
import softuni.residentevil.domain.entities.Magnitude;
import softuni.residentevil.domain.entities.Mutation;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class VirusAddBindingModel {
    private String id;
    private String name;
    private String description;
    private String sideEffects;
    private Creator creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private LocalDate releasedOn;
    private String[] capitalsIds;

    public VirusAddBindingModel() {
    }

    @NotNull
    @Size(min = 3, max = 10, message = "Invalid name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Invalid description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Invalid side effects")
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @NotNull
    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @NotNull(message = "Invalid rate")
    @Min(0)
    @Max(100)
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @NotNull(message = "Invalid hours")
    @Min(0)
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }
    @NotNull
    public String[] getCapitalsIds() {
        return capitalsIds;
    }

    public void setCapitalsIds(String[] capitalsIds) {
        this.capitalsIds = capitalsIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

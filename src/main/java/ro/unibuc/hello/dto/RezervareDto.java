package ro.unibuc.hello.dto;

import java.time.LocalDate;

public class RezervareDto {

    private String id;
    private ApartamentEntity apartament;
    private UserEntity user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public RezervareDto() {}

    public RezervareDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ApartamentEntity getApartament() {
        return apartament;
    }

    public void setApartament(ApartamentEntity apartament) {
        this.apartament = apartament;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

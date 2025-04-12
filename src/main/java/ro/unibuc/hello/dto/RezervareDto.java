package ro.unibuc.hello.dto;

import java.time.LocalDate;

public class RezervareDto {

    private String id;
    private String idApartament;
    private String idUser;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public RezervareDto() {}

    public RezervareDto(String id) {
        this.id = id;
    }

    public RezervareDto(String id, String idApartament, String idUser, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.idApartament = idApartament;
        this.idUser = idUser;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdApartament() {
        return idApartament;
    }

    public void setIdApartament(String idApartament) {
        this.idApartament = idApartament;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

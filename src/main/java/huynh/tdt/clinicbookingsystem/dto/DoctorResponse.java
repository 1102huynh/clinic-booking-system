package huynh.tdt.clinicbookingsystem.dto;

public class DoctorResponse {
    private Long id;
    private String fullName;
    private String specialization;
    private Integer experienceYears;
    private Boolean available;

    public DoctorResponse() {
    }

    public DoctorResponse(Long id, String fullName, String specialization, Integer experienceYears, Boolean available) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}


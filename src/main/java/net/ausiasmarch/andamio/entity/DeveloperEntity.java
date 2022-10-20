package net.ausiasmarch.andamio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "developer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class DeveloperEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String last_name;
    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
    private final List<IssueEntity> issues;

    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
    private final List<ResolutionEntity> resolutions;

    @OneToMany(mappedBy = "developer", fetch = FetchType.LAZY)
    private final List<HelpEntity> helps;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usertype")
    private UsertypeEntity usertype;

    public DeveloperEntity() {

        this.issues = new ArrayList<>();
        this.resolutions = new ArrayList<>();
        this.helps = new ArrayList<>();
    }

    public DeveloperEntity(Long id) {

        this.issues = new ArrayList<>();
        this.resolutions = new ArrayList<>();
        this.helps = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIssues() {
        return issues.size();
    }

    public int getResolutions() {
        return resolutions.size();
    }

    public int getHelps() {
        return helps.size();
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public UsertypeEntity getUsertype() {
        return usertype;
    }

    public void setUsertype(UsertypeEntity usertype) {
        this.usertype = usertype;
    }

//    @PreRemove
//    public void nullify() {
//        this.projects.forEach(c -> c.setDeveloperEntity(null));
//        this.issues.forEach(c -> c.setDeveloperEntity(null));
//        this.resolutions.forEach(c -> c.setDeveloperEntity(null));
//        this.helps.forEach(c -> c.setDeveloperEntity(null));
//    }
}

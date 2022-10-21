package net.ausiasmarch.andamio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "team")
@JsonIgnoreProperties({"hibernateLazyInitialize", "handler"})
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_developer")
    private DeveloperEntity developer;

    public TeamEntity() {
        this.projects = new ArrayList<>();
        this.developers = new ArrayList<>();
    }

    //@OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private final List<ProjectEntity> projects;

    //@OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private final List<DeveloperEntity> developers;

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

    public int getProject() {
        return projects.size();
    }

    public int getDevelopers() {
        return developers.size();
    }

    public Long getDeveloper() {
        return developer.getId();
    }

    public void setDeveloper(DeveloperEntity developer) {
        this.developer = developer;
    }

}

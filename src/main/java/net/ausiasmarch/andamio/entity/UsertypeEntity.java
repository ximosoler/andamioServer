package net.ausiasmarch.andamio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "usertype")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsertypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "usertype", fetch = FetchType.LAZY)
    private final List<DeveloperEntity> developers;

    public UsertypeEntity() {
        this.developers = new ArrayList<>();
    }

    public UsertypeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
        this.developers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDevelopers() {
        return developers.size();
    }

    @PreRemove
    public void nullify() {
        this.developers.forEach(c -> c.setUsertype(null));
    }
}

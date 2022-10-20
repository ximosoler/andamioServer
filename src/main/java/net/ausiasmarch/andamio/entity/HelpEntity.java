package net.ausiasmarch.andamio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "help")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class HelpEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double percentage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_resolution")
    private ResolutionEntity resolution;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_developer")
    private DeveloperEntity developer;

    public HelpEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public ResolutionEntity getResolution() {
        return resolution;
    }

    public void setResolution(ResolutionEntity resolution) {
        this.resolution = resolution;
    }

    public DeveloperEntity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperEntity developer) {
        this.developer = developer;
    }

}

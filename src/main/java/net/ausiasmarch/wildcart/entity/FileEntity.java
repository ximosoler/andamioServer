package net.ausiasmarch.wildcart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Blob;

@Entity
@Table(name = "file")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileEntity {

    @Schema(example = "2")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(example = "paraguas 3422S")
    @Column(name = "name")
    private String name;
    @Schema(example = "jpg")
    @Column(name = "type")
    private String type;

    @Column(name = "file")
    private Blob file;

    public FileEntity() {
    }

    public FileEntity(String name, String type) {
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob fileBlob) {
        this.file = fileBlob;
    }

}

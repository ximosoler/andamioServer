package net.ausiasmarch.wildcart.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tipoproducto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class tipoproductoEntity implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    
    public Long getId(){
    return id;
    }
    
    public void setId(Long id){
    this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

package registro.model.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
@NamedQueries({ @NamedQuery(name = "Persona.findAll", query = "select o from Persona o") })
@SequenceGenerator(name = "Persona_Gen", sequenceName = "SEQ_PERSONA", allocationSize = 1)
public class Persona implements Serializable {

    public enum TipoIdentificacion{
           CEDULA("Cedula"), 
           TARJETA_IDENTIDAD("Tarjeta de identidad"),
           CEDULA_EXTRANJERA("Cedula de extranjeria");
           
           private String label;

           TipoIdentificacion(String label) {
               this.label = label;
           }

           public String getLabel() {
               return label;
           }
       }
    
    public enum Semestre{
           PRIMERO,SEGUNDO,TERCERO,CUARTO,QUINTO,SEXTO,SEPTIMO,OCTAVO,NOVENO;
       }
    
    
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Persona_Gen")
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String nombrecompleto;
    
    @Column(nullable = false, length = 20)
    private String numeroidentificacion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Persona.Semestre semestre;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Persona.TipoIdentificacion tipoidentificacion;

    @OneToMany(mappedBy = "persona", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
       private List<Seguimiento> seguimientoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getNumeroidentificacion() {
        return numeroidentificacion;
    }

    public void setNumeroidentificacion(String numeroidentificacion) {
        this.numeroidentificacion = numeroidentificacion;
    }

    public void setSemestre(Persona.Semestre semestre) {
        this.semestre = semestre;
    }

    public Persona.Semestre getSemestre() {
        return semestre;
    }

    public void setTipoidentificacion(Persona.TipoIdentificacion tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public Persona.TipoIdentificacion getTipoidentificacion() {
        return tipoidentificacion;
    }

    public List<Seguimiento> getSeguimientoList() {
        return seguimientoList;
    }

    public void setSeguimientoList(List<Seguimiento> seguimientoList) {
        this.seguimientoList = seguimientoList;
    }

    public Seguimiento addSeguimiento(Seguimiento seguimiento) {
        getSeguimientoList().add(seguimiento);
        seguimiento.setPersona(this);
        return seguimiento;
    }

    public Seguimiento removeSeguimiento(Seguimiento seguimiento) {
        getSeguimientoList().remove(seguimiento);
        seguimiento.setPersona(null);
        return seguimiento;
    }
}

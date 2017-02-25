package registro.model.entity;

import java.io.Serializable;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

@Entity
@NamedQueries({ @NamedQuery(name = "Seguimiento.findAll", query = "select o from Seguimiento o") })
@SequenceGenerator(name = "Seguimiento_Gen", sequenceName = "SEQ_SEGUIMIENTO", allocationSize = 1)
public class Seguimiento implements Serializable {
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date fechahora;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seguimiento_Gen")
    @Column(nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA")
    private Persona persona;

    public Seguimiento() {
    }


    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}

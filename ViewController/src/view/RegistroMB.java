package view;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import registro.model.entity.Persona;
import registro.model.entity.Seguimiento;
import registro.model.services.RegistroSessionEJBBean;


@ManagedBean
@SessionScoped
public class RegistroMB {

    @EJB
    private RegistroSessionEJBBean registroSessionEJBBean;

    private List<Persona.TipoIdentificacion> tipoIdentificacionList;
    private List<Persona.Semestre> semestreList;
    private List<Persona> personalist;
    private List<Seguimiento> seguimientoList;
    private Persona persona;
    private Seguimiento seguimiento;
    private boolean renderedAddSeguimiento;
    private boolean renderedShwPersona;
    private Date paramFechaHora;
    private String paramNumeroIdentificacion;


    public String goBackIndex() {
        this.preparateGoAddPersona();
        return "./index";
    }

    private void preparateGoAddPersona() {
        this.semestreList =
            new ArrayList<>(Arrays.asList(Persona.Semestre.PRIMERO, Persona.Semestre.SEGUNDO, Persona.Semestre.TERCERO,
                                          Persona.Semestre.CUARTO, Persona.Semestre.QUINTO, Persona.Semestre.SEXTO,
                                          Persona.Semestre.SEPTIMO, Persona.Semestre.OCTAVO, Persona.Semestre.NOVENO));

        this.tipoIdentificacionList =
            new ArrayList<>(Arrays.asList(Persona.TipoIdentificacion.CEDULA,
                                          Persona.TipoIdentificacion.CEDULA_EXTRANJERA,
                                          Persona.TipoIdentificacion.TARJETA_IDENTIDAD));
        this.persona = new Persona();
    }

    public String goAddPersonaAction() {
        this.preparateGoAddPersona();
        return "registro/personaAdd";
    }

    public void addPersonaAction() {
        try {
            this.registroSessionEJBBean.persistPersona(this.persona);
        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        this.persona = null;
        this.goBackIndex();
    }

    public String goAddSeguimientoAction() {
        this.renderedAddSeguimiento = false;
        return "registro/registroAdd";
    }


    public void findPersonaAction() {
        try {

            this.persona = this.registroSessionEJBBean.getPersonaByCriteria(this.paramNumeroIdentificacion);
            this.seguimientoList = this.registroSessionEJBBean.getSeguimientoByPersona(this.persona);
        } catch (Exception e) {
            this.seguimientoList = null;
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } finally {
            this.renderedAddSeguimiento = true;
        }
    }

    public void addSeguimientoAction() {
        this.seguimiento = new Seguimiento();
        this.seguimiento.setPersona(this.persona);
        this.registroSessionEJBBean.persistSeguimiento(this.seguimiento);
        this.findPersonaAction();
    }


    public void findPersonassAction() {
        try {
            if (this.paramNumeroIdentificacion.equals("")) {
                this.paramNumeroIdentificacion = null;
            }
            this.personalist =
                this.registroSessionEJBBean.getPersonassByCriteria(this.paramNumeroIdentificacion, this.paramFechaHora);
            this.renderedShwPersona = true;

        } catch (Exception e) {
            FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public void removeAction() {
        this.registroSessionEJBBean.removePersona(this.persona);
        this.findPersonassAction();
        this.renderedShwPersona = false;
    }

    public String goPersonassAction() {
        this.renderedShwPersona = false;
        return "registro/personass";
    }

    public String goPersonaAction() {

        return "registro/persona";
    }

    public String goBackAction() {
this.persona=null;
this.renderedAddSeguimiento=false;
        return "/index";
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public List<Persona.TipoIdentificacion> getTipoIdentificacionList() {
        return tipoIdentificacionList;
    }

    public List<Persona.Semestre> getSemestreList() {
        return semestreList;
    }

    public void setSeguimiento(Seguimiento seguimiento) {
        this.seguimiento = seguimiento;
    }

    public Seguimiento getSeguimiento() {
        return seguimiento;
    }

    public void setParamNumeroIdentificacion(String paramNumeroIdentificacion) {
        this.paramNumeroIdentificacion = paramNumeroIdentificacion;
    }

    public String getParamNumeroIdentificacion() {
        return paramNumeroIdentificacion;
    }

    public boolean isRenderedAddSeguimiento() {
        return renderedAddSeguimiento;
    }

    public void setParamFechaHora(Date paramFechaHora) {
        this.paramFechaHora = paramFechaHora;
    }

    public Date getParamFechaHora() {
        return paramFechaHora;
    }

    public List<Persona> getPersonalist() {
        return personalist;
    }

    public List<Seguimiento> getSeguimientoList() {
        return seguimientoList;
    }

    public boolean isRenderedShwPersona() {
        return renderedShwPersona;
    }
}

package registro.model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


import javax.ejb.SessionContext;
import javax.ejb.Stateless;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

import registro.model.entity.Persona;
import registro.model.entity.Seguimiento;

@Stateless(name = "RegistroSessionEJB", mappedName = "registro-Model-RegistroSessionEJB")
public class RegistroSessionEJBBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;

    public RegistroSessionEJBBean() {
    }

    private void validatePersobaByIdentificacion(String identificacion) throws Exception {
        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Persona o ");
        ejbql.append(" where o.numeroidentificacion=:identificacion");


        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("identificacion",identificacion);


        List<Persona> personaList = query.getResultList();
        
        if(!personaList.isEmpty()){
            throw new Exception("La persona ya se encuentra registrada");
        }
    }

    public void persistPersona(Persona persona) throws Exception {
        this.validatePersobaByIdentificacion(persona.getNumeroidentificacion());
        this.em.persist(persona);
    }

    public void persistSeguimiento(Seguimiento seguimiento) {
        seguimiento.setFechahora(new Date());
        this.em.persist(seguimiento);
    }

    public void removeSeguimiento(Seguimiento seguimiento) {
        this.em.remove(seguimiento);
    }

    private void removeSeguimientoForPersona(Persona persona) {

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Seguimiento o ");
        ejbql.append(" where o.persona.id=:idPersona");


        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("idPersona", persona.getId());


        List<Seguimiento> seguimientoList = query.getResultList();

        seguimientoList.forEach(p -> this.removeSeguimiento(p));
    }

    public void removePersona(Persona persona) {

        this.removeSeguimientoForPersona(persona);
       persona= this.em.merge(persona);
        this.em.remove(persona);
    }

    public Persona mergePersona(Persona persona) {
        return persona = this.em.merge(persona);
    }

    public Persona getPersonaByCriteria(String numeroIdentificacion) throws Exception {
        Persona persona = null;

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Persona o ");
        ejbql.append(" where o.numeroidentificacion=:numeroIdentificacion");


        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("numeroIdentificacion", numeroIdentificacion.trim());


        List<Persona> personaList = query.getResultList();
        if (personaList.isEmpty()) {
            throw new Exception("No se encontro la persona con el numero de documento");
        }

        persona = personaList.get(0);


        return persona;
    }

    public List<Persona> getPersonassByCriteria(String numeroIdentificacion, Date fecha) throws Exception {

        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select distinct o.persona from Seguimiento o ");

          if (numeroIdentificacion != null) {
            ejbql.append(" where o.persona.numeroidentificacion=:numeroIdentificacion");
        }

        Query query = this.em.createQuery(ejbql.toString());

         if (numeroIdentificacion != null) {
            query.setParameter("numeroIdentificacion", numeroIdentificacion.trim());
        }

        List<Persona> personaList = query.getResultList();
        if (personaList.isEmpty()) {
            throw new Exception("No se encontro personas");
        }


        return personaList;
    }

    public List<Seguimiento> getSeguimientoByPersona(Persona persona) throws Exception {


        StringBuilder ejbql = new StringBuilder();

        ejbql.append("select o from Seguimiento o ");
        ejbql.append(" where o.persona.id=:idPersona");


        Query query = this.em.createQuery(ejbql.toString());

        query.setParameter("idPersona", persona.getId());


        List<Seguimiento> seguimientoList = query.getResultList();
        if (seguimientoList.isEmpty()) {
            throw new Exception("la persona aun no tiene registro de seguimientos");
        }


        return seguimientoList;
    }

    public static Date clearTime(Date fecha) {

        Calendar calHora = Calendar.getInstance();
        calHora.setTime(fecha);
        calHora.set(Calendar.HOUR_OF_DAY, 00);
        calHora.set(Calendar.MINUTE, 00);
        calHora.set(Calendar.SECOND, 00);
        calHora.set(Calendar.MILLISECOND, 00);

        return calHora.getTime();
    }

    public static Date setMidNight(Date fecha) {

        Calendar calHora = Calendar.getInstance();
        calHora.setTime(fecha);
        calHora.set(Calendar.HOUR_OF_DAY, 23);
        calHora.set(Calendar.MINUTE, 59);
        calHora.set(Calendar.SECOND, 00);
        calHora.set(Calendar.MILLISECOND, 00);


        return calHora.getTime();
    }

}

package pt.ipleiria.estg.dei.ei.dae.monitor.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.entities.TipoSensor;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class TipoSensorBean {
    @PersistenceContext
    private EntityManager em;

    public boolean exists(String nome) {
        Long count = em.createQuery(
                        "SELECT COUNT(t) FROM TipoSensor t WHERE t.nome = :nome", Long.class)
                .setParameter("nome", nome)
                .getSingleResult();
        return count > 0;
    }

    public void create(String nome) throws MyEntityExistsException {
        if (exists(nome)) {
            throw new MyEntityExistsException("Tipo de sensor '"+nome+"' já existe");
        }
        try {
            TipoSensor tipoSensor = new TipoSensor(nome);
            em.persist(tipoSensor);
            em.flush();
        } catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public TipoSensor find(Long id) throws MyEntityNotFoundException {
        TipoSensor tipoSensor = em.find(TipoSensor.class, id);
        if (tipoSensor == null) {
            throw new MyEntityNotFoundException("Sensor com id "+id+" não existe");
        }
        return tipoSensor;
    }

    public TipoSensor findByName(String nome) throws MyEntityNotFoundException {
        TipoSensor tipoSensor;
        try {
            tipoSensor = em.createQuery(
                            "SELECT t FROM TipoSensor t WHERE LOWER(t.nome) = :nome", TipoSensor.class)
                    .setParameter("nome", nome.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e){
            throw new MyEntityNotFoundException("Sensor '"+ nome + "' não existe");
        }
        return tipoSensor;
    }
}

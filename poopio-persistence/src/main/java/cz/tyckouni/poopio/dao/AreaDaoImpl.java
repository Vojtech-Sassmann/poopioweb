package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.enums.AreaType;

import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 * @author Jan Gol <gol.honza@gmail.com>
 */
@Named
public class AreaDaoImpl implements AreaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Area area) {
        if (area == null) {
            throw new IllegalArgumentException("Area cannot be null!");
        }
        entityManager.persist(area);
        entityManager.flush();
    }

    @Override
    public void delete(Area area) {
        if (area == null) {
            throw new IllegalArgumentException("Area cannot be null!");
        }
        entityManager.remove(area);
        entityManager.flush();
    }

    @Override
    public Area findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null!");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        return entityManager.find(Area.class, id);
    }

    @Override
    public Area findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        try {
            return entityManager.createQuery("SELECT a FROM Area a WHERE name = :name", Area.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Area> getAllForType(AreaType areaType) {
        if (areaType == null) {
            throw new IllegalArgumentException("areaType cannot be null.");
        }
        return entityManager.createQuery("SELECT a FROM Area a WHERE type = :areaType", Area.class)
                .setParameter("areaType", areaType)
                .getResultList();
    }

    @Override
    public List<Area> getAll() {
        return entityManager.createQuery("SELECT a FROM Area a", Area.class)
                .getResultList();
    }

}

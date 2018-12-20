package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of WeaponDao interface
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Named
public class WeaponDaoImpl implements WeaponDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Weapon weapon) {
        if (weapon == null){
            throw new IllegalArgumentException("Argument weapon cannot be null!");
        }
        em.persist(weapon);
        em.flush();
    }

    @Override
    public void delete(Weapon weapon) {
        if (weapon == null){
            throw new IllegalArgumentException("Argument weapon cannot be null!");
        }
        em.remove(weapon);
        em.flush();
    }
    @Override
    public Weapon findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Argument id cannot be null!");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Argument id cannot be less than 0!");
        }
        return em.find(Weapon.class, id);
    }

    @Override
    public Weapon findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Argument name cannot be null!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Argument name cannot be empty!");
        }
        try {
            return em.createQuery("select w from Weapon w where w.name like :name", Weapon.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Weapon> getAll() {
        return em.createQuery("select w from Weapon w", Weapon.class)
                .getResultList();
    }

    @Override
    public List<Weapon> getAllForType(WeaponType type) {
        if (type == null){
            throw new IllegalArgumentException("Argument weaponType cannot be null!");
        }
        return em.createQuery("select w from Weapon w where w.type = :weaponType", Weapon.class)
                .setParameter("weaponType", type)
                .getResultList();
    }
}

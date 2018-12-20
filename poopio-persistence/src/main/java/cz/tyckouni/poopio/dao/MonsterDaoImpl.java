package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.MonsterAgility;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@Named
public class MonsterDaoImpl implements MonsterDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Monster monster) {
		if (monster == null) {
			throw new IllegalArgumentException("Argument monster can not be null.");
		}
		em.persist(monster);
		em.flush();
	}

	@Override
	public void delete(Monster monster) {
		if (monster == null) {
			throw new IllegalArgumentException("Argument monster can not be null.");
		}

		for (Weapon weapon : monster.getAppropriateWeapons()) {
			weapon.removeAppropriateMonster(monster);
		}
		for (Area area : monster.getAreas()) {
			area.removeMonster(monster);
		}

		em.remove(monster);
		em.flush();
	}

	@Override
	public Monster findById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument id can not be null.");
		}
		if (id < 0) {
			throw new IllegalArgumentException("Argument id can not be less than 0.");
		}
		return em.find(Monster.class, id);
	}

	@Override
	public Monster findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Argument name can not be null.");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Argument name can not be empty.");
		}
		try {
			return em.createQuery("select m from Monster m where m.name like :name", Monster.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Monster> getAllForAgility(MonsterAgility monsterAgility) {
		if (monsterAgility == null) {
			throw new IllegalArgumentException("Argument monsterAgility can not be null.");
		}
		return em.createQuery("select m from Monster m where m.agility = :monsterAgility", Monster.class)
				.setParameter("monsterAgility", monsterAgility)
				.getResultList();
	}

	@Override
	public List<Monster> getAll() {
		return em.createQuery("select m from Monster m", Monster.class)
				.getResultList();
	}
}

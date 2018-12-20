package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.entity.User;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Implementation of UserDao interface
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@Named
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(User user) {
		if(user == null){
			throw new IllegalArgumentException("Parameter User cannot be null!");
		}
		em.persist(user);
		em.flush();
	}

	@Override
	public void delete(User user) {
		if(user == null){
			throw new IllegalArgumentException("Parameter User cannot be null!");
		}
		em.remove(user);
		em.flush();
	}

	@Override
	public User findById(Long id) {
		if(id == null){
			throw new IllegalArgumentException("Parameter id cannot be null!");
		}
		if(id < 0){
			throw new IllegalArgumentException("Parameter id cannot be less than 0!");
		}
		return em.find(User.class, id);
	}

	@Override
	public User findByEmail(String email) {
		if(email == null){
			throw new IllegalArgumentException("Parameter email cannot be null!");
		}
		if(email.isEmpty()){
			throw new IllegalArgumentException("Parameter email cannot be empty!");
		}
		try {
			return em
					.createQuery("select u from User u where email = :email",
							User.class).setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException nrf) {
			return null;
		}
	}

	@Override
	public List<User> getAll() {
		return em.createQuery("SELECT u FROM User u",
				User.class).getResultList();
	}
}

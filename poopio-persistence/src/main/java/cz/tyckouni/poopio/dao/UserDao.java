package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.entity.User;

import java.util.List;

/**
 * Interface of UserDao
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
public interface UserDao {

	/**
	 * Creates user in database
	 * @param user which will be created
	 *
	 * @throws IllegalArgumentException when given parameter is null
	 */
	void create(User user);

	/**
	 * Deletes user from database
	 * @param user which will be deleted
	 *
	 * @throws IllegalArgumentException when given parameter is null
	 */
	void delete(User user);

	/**
	 * Finds user in database by his id and returns him as object User
	 * @param id by which will be user find
	 * @return User or null if there is not user with that id
	 *
	 * @throws IllegalArgumentException when given parameter is null or less than 0
	 */
	User findById(Long id);

	/**
	 * Finds user in database by his email and returns him as object User
	 * @param email by which will be user find
	 * @return User or null if there is not user whit that email
	 *
	 * @throws IllegalArgumentException when given parameter is null or empty
	 */
	User findByEmail(String email);

	/**
	 * Gets all users stored in database
	 * @return List of all Users in database or empty List if there are no users
	 */
	List<User> getAll();
}

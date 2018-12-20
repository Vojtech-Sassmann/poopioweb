package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.entity.User;
import cz.tyckouni.poopio.entity.User;

import java.util.List;

/**
 * Service interface for managing User entities.
 *
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
public interface UserService {
	/**
	 * Find user with the given id in database
	 *
	 * @param userId by which will be user find
	 * @return User object with that id
	 */
	User findUserById(Long userId);

	/**
	 * Find user with the given email in database
	 *
	 * @param email by which will be user find
	 * @return User object with that email
	 */
	User findUserByEmail(String email);

	/**
	 * Register the given user with the given unencrypted password.
	 *
	 * @param user which will be registered
	 * @param unencryptedPassword by which will be user authenticated
	 */
	void registerUser(User user, String unencryptedPassword);

	/**
	 * Get all registered users
	 *
	 * @return List of User objects
	 */
	List<User> getAllUsers();

	/**
	 * Try to authenticate a user.
	 *
	 * @param user which will be authenticated
	 * @param password
	 * @return true only if the hashed password matches the records, false otherwise
	 */
	boolean authenticate(User user, String password);

	/**
	 * Check if the given user is admin.
	 *
	 * @param user which will be checked
	 * @return true if user is Admin, false otherwise
	 */
	boolean isAdmin(User user);

	/**
	 * Set user role to admin.
	 *
	 * @param user which will be set to Admin
	 */
	void setAdmin(User user);

	/**
	 * Set user role to regular user.
	 *
	 * @param user which will be set to regular user
	 */
	void removeAdmin(User user);

	/**
	 * delete user from database by his id.
	 *
	 * @param user which will be deleted
	 */
	void deleteUser(User user);
}

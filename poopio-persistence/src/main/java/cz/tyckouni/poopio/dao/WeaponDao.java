package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;

import java.util.List;

/**
 * Interface of WeaponDao
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public interface WeaponDao {

	/**
	 * Saves the given weapon entity to the DB
	 * @param weapon weapon which will be saved
	 *
	 * @throws IllegalArgumentException when given parameter is null
	 */
	void create(Weapon weapon);

	/**
	 * Removes the given weapon entity from the DB
	 * @param weapon weapon which will be removed
	 *
	 * @throws IllegalArgumentException when given parameter is null
	 */
	void delete(Weapon weapon);

	/**
	 * Finds weapon entity by id
	 * @param id id by which will be weapon find
	 * @return weapon or null if there is not weapon with that id
	 *
	 * @throws IllegalArgumentException when given parameter is null or is negative
	 */
	Weapon findById(Long id);

	/**
	 * Finds weapon entity by name
	 * @param name name by which will be weapon find
	 * @return weapon or null if there is not weapon with that name
	 *
	 * @throws IllegalArgumentException when given parameter is null or empty
	 */
	Weapon findByName(String name);

	/**
	 * Gets all weapon entities in DB
	 * @return List of weapons
	 *
	 */
	List<Weapon> getAll();

	/**
	 * Gets all weapon entities with the given type
	 * @param type type by which be return list oe weapons
	 * @return List of weapons with the given type
	 *
	 * @throws IllegalArgumentException when given parameter is null, or if the parameter is other than enum permit
	 */
	List<Weapon> getAllForType(WeaponType type);

}
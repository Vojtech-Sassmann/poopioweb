package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;

import java.util.List;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public interface MonsterDao {
	/**
	 * Saves the given {@link Monster} entity to the DB.
	 *
	 * @param monster {@link Monster} entity that will be stored in DB.
	 * @throws IllegalArgumentException when the given entity is null.
	 */
	void create(Monster monster);

	/**
	 * Removes the given {@link Monster} entity from the DB.
	 *
	 * @param monster {@link Monster} entity that will be removed from DB.
	 * @throws IllegalArgumentException when the given entity is null.
	 */
	void delete(Monster monster);

	/**
	 * Finds {@link Monster} entity with the given ID.
	 *
	 * @param id Id of the searched {@link Monster} entity.
	 * @throws IllegalArgumentException when the given id is null or less then 0.
	 * @return {@link Monster} with the given id or null if it was not found.
	 */
	Monster findById(Long id);

	/**
	 * Finds {@link Monster} entity with the given name.
	 *
	 * @param name Name of the searched {@link Monster} entity.
	 * @throws IllegalArgumentException when the given name is null or empty.
	 * @return {@link Monster} with the given name or null if it was not found.
	 */
	Monster findByName(String name);

	/**
	 * Searches for the {@link Monster} entities with the given {@link MonsterAgility}
	 * and returns them in a {@link List}
	 *
	 * @param monsterAgility Type of the searched monsters.
	 * @throws IllegalArgumentException when the given {@link MonsterAgility} is null.
	 * @return {@link List} of monsters with the given {@link MonsterAgility}.
	 */
	List<Monster> getAllForAgility(MonsterAgility monsterAgility);

	/**
	 * Finds all {@link Monster} entities in DB and returns them in a {@link List}.
	 *
	 * @return {@link List} of all {@link Monster} entities in DB.
	 */
	List<Monster> getAll();
}

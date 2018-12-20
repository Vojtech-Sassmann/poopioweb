package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;

import java.util.List;

/**
 * Service interface for managing {@link Monster} entities.
 *
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public interface MonsterService {

	/**
	 * Finds all Monsters.
	 *
	 * @return List of all Monsters.
	 */
	List<Monster> getAllMonsters();

	/**
	 * Creates Monster entity.
	 *
	 * @param monster Monster entity to be created.
	 */
	void createMonster(Monster monster);

	/**
	 * Deletes the given Monster entity.
	 *
	 * @param monster Monster entity to be deleted.
	 */
	void deleteMonster(Monster monster);

	/**
	 * Finds the most widespread monsters. That means, it return a List
	 * of Monsters who are located in most Areas.
	 *
	 * @return List of most widespread Monsters.
	 */
	List<Monster> getTheMostWidespreadMonsters();

	/**
	 * Finds Monster entity by given Id.
	 *
	 * @param id Id to be used for search.
	 * @return Monster with given Id.
	 */
	Monster findById(Long id);

	/**
	 * Finds Monster entity by given Name.
	 *
	 * @param name Name to be used for search.
	 * @return Monster with given Name.
	 */
	Monster findByName(String name);

	/**
	 * Returns a List of Monsters with given MonsterAgility.
	 *
	 * @param agility To be used.
	 * @return List of Monsters with given agility.
	 */
	List<Monster> getAllForAgility(MonsterAgility agility);
}

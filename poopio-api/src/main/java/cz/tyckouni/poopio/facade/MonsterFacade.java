package cz.tyckouni.poopio.facade;

import cz.tyckouni.poopio.dto.MonsterChangeAgilityDTO;
import cz.tyckouni.poopio.dto.MonsterCreateDTO;
import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.MonsterUpdateDTO;
import cz.tyckouni.poopio.enums.MonsterAgility;

import java.util.List;

/**
 * Facade interface for {@link MonsterDTO} entities.
 *
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
public interface MonsterFacade {

	/**
	 * Finds all Monsters.
	 *
	 * @return List of all Monsters.
	 */
	List<MonsterDTO> getAllMonsters();

	/**
	 * Creates Monster entity.
	 *
	 * @param monster Monster entity to be created.
	 */
	Long createMonster(MonsterCreateDTO monster);

	/**
	 * Deletes the given Monster entity.
	 *
	 * @param monsterId Monster entity to be deleted.
	 */
	void deleteMonster(Long monsterId);

	/**
	 * Changes the agility of Monster
	 *
	 * @param change change that will be performed.
	 */
	void changeMonsterAgility(MonsterChangeAgilityDTO change);

	/**
	 * Finds monster with the same Id and changes its values
	 * by the given monster
	 */
	MonsterDTO updateMonster(MonsterUpdateDTO update);

	/**
	 * Returns a List of Monsters with given MonsterAgility.
	 *
	 * @param agility To be used.
	 * @return List of Monsters with given agility.
	 */
	List<MonsterDTO> getAllForAgility(MonsterAgility agility);

	/**
	 * Finds Monster entity by given Id.
	 *
	 * @param id Id to be used for search.
	 * @return Monster with given Id.
	 */
	MonsterDTO findById(Long id);

	/**
	 * Finds Monster entity by given Name.
	 *
	 * @param name Name to be used for search.
	 * @return Monster with given Name.
	 */
	MonsterDTO findByName(String name);

	/**
	 * Finds the most widespread monsters. That means, it return a List
	 * of Monsters who are located in most Areas.
	 *
	 * @return List of most widespread Monsters.
	 */
	List<MonsterDTO> getTheMostWidespreadMonsters();
}

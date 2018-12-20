package cz.tyckouni.poopio.facade;

import cz.tyckouni.poopio.dto.WeaponCreateDTO;
import cz.tyckouni.poopio.dto.WeaponDTO;
import cz.tyckouni.poopio.dto.WeaponUpdateDTO;
import cz.tyckouni.poopio.enums.WeaponType;

import java.util.List;

/**
 * Facade interface for WeaponDTO entities.
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public interface WeaponFacade {

    /**
     * Create weapon entity
     *
     * @param weapon Weapon entity to be create
     * @return id
     */
    Long createWeapon(WeaponCreateDTO weapon);

    /**
     * Delete weapon entity
     *
     * @param weaponId Id for weapon entity to be create
     */
    void deleteWeapon(Long weaponId);

    /**
     * Finds weapon with the same id and update their attributes
     * @param weaponUpdateDTO WeaponUpdateDTO with new attributes
     * @return WeaponDTO
     */
    WeaponDTO updateWeapon(WeaponUpdateDTO weaponUpdateDTO);

    /**
     * Finds weapon by Id.
     *
     * @param id id by which will be weapon find
     * @return weaponDTO or null if there is not weapon with that id
     */
    WeaponDTO findById(Long id);

    /**
     * Finds weapon by name
     *
     * @param name name by which will be weapon find
     * @return weapon or null if there is not weapon with that name
     */
    WeaponDTO findByName(String name);

    /**
     * Gets all weapons
     *
     * @return List of weapons
     */
    List<WeaponDTO> getAll();

    /**
     * Gets all weapons with the given type
     *
     * @param type type by which be return list oe weapons
     * @return List of weapons with the given type
     */
    List<WeaponDTO> getAllForType(WeaponType type);

    /**
     * Add appropriate monster entity to weapon
     *
     * @param weaponId Id of weapon
     * @param monsterId Id of monster
     */
    void addAppropriateMonster(Long weaponId, Long monsterId);

    /**
     * Remove appropriate monster entity for weapon
     *
     * @param weaponId Id of weapon
     * @param monsterId Id of monster
     */
    void removeAppropriateMonster(Long weaponId, Long monsterId);

    /**
     * Gets the most effective weapon
     *
     * @return List of the most effective weapons (weapons with the most monsters) or empty list
     */
    List<WeaponDTO> getTheMostEffectiveWeapon();

}

package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;

import java.util.List;

/**
 * Service interface for managing Weapon enities.
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public interface WeaponService {

    /**
     * Create weapon entity
     *
     * @param weapon Weapon entity to be create
     * @return id
     */
    void createWeapon(Weapon weapon);

    /**
     * Delete weapon entity
     *
     * @param weapon Weapon entity to be delete
     */
    void deleteWeapon(Weapon weapon);

    /**
     * Finds weapon by Id.
     *
     * @param id id by which will be weapon find
     * @return weapon or null if there is not weapon with that id
     */
    Weapon findById(Long id);

    /**
     * Finds weapon by name
     *
     * @param name name by which will be weapon find
     * @return weapon or null if there is not weapon with that name
     */
    Weapon findByName(String name);

    /**
     * Gets all weapons
     *
     * @return List of weapons
     */
    List<Weapon> getAllWeapons();

    /**
     * Gets all weapons with the given type
     *
     * @param type type by which be return list oe weapons
     * @return List of weapons with the given type
     */
    List<Weapon> getAllForType(WeaponType type);

    /**
     * Add appropriate monster entity to weapon
     *
     * @param weapon Weapon
     * @param monster Monster
     */
    void addAppropriateMonster(Weapon weapon, Monster monster);

    /**
     * Remove appropriate monster entity for weapon
     *
     * @param weapon Weapon
     * @param monster Monster
     */
    void removeAppropriateMonster(Weapon weapon, Monster monster);

    /**
     * Gets the most effective weapon
     *
     * @return List of the most effective weapons (weapons with the most monsters) or empty list
     */
    List<Weapon> getTheMostEffectiveWeapon();
}

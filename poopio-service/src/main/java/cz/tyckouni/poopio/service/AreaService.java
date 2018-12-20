package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;

import java.util.List;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public interface AreaService {

    /**
     * Finds all Areas.
     *
     * @return List of all Areas.
     */
    List<Area> getAllAreas();

    /**
     * Creates Area entity.
     *
     * @param area Area entity to be created.
     */
    void createArea(Area area);

    /**
     * Deletes the given Area entity.
     *
     * @param area Area entity to be deleted.
     */
    void deleteArea(Area area);

    /**
     * Finds the most dangerous Areas. That means, it return a List of Areas
     * which has highest number of monsters.
     *
     * @return List of most dangerous Areas.
     */
    List<Area> getTheMostDangerousAreas();

    /**
     * Finds Area entity by given Id.
     *
     * @param id Id to be used for search.
     * @return Area with given Id.
     */
    Area findById(Long id);

    /**
     * Finds Area entity by given Name.
     *
     * @param name Name to be used for search.
     * @return Area with given Name.
     */
    Area findByName(String name);

    /**
     * Returns a List of Areas with given AreaType.
     *
     * @param type To be used.
     * @return List of Areas with given type.
     */
    List<Area> getAllForType(AreaType type);

    /**
     * Adds Monster to Area
     *
     * @param area to be used.
     * @param monster To be added.
     */
    void addMonsterToArea(Area area, Monster monster);

    /**
     * Remove Monster From Area
     *
     * @param area to be used.
     * @param monster To be removed.
     */
    void removeMonsterFromArea(Area area, Monster monster);

}

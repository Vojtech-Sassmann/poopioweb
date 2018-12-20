package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.enums.AreaType;

import java.util.List;

/**
 * @author Jan Gol <gol.honza@gmail.com>
 */

public interface AreaDao {

    /**
     * Creates area in db.
     *
     * @param area that will be created.
     * @throws IllegalArgumentException when area is null.
     */
    void create(Area area);

    /**
     * Deletes area from db.
     *
     * @param area that will be deleted.
     * @throws IllegalArgumentException when area is null.
     */
    void delete(Area area);

    /**
     * Finds area with given Id and returns corresponding object Area.
     *
     * @param id Id of Area
     * @return Area
     * @throws IllegalArgumentException when id is null or negative.
     */
    Area findById(Long id);

    /**
     * Finds area with given Name and returns corresponding object Area.
     *
     * @param name Name of Area
     * @return Area
     * @throws IllegalArgumentException when name is null or empty.
     */
    Area findByName(String name);

    /**
     * Returns List of areas with given type.
     *
     * @param areaType AreaType
     * @return List of Areas
     * @throws IllegalArgumentException when areaType is null.
     */
    List<Area> getAllForType(AreaType areaType);

    /**
     * Returns List of ALL areas.
     *
     * @return List of Areas.
     */
    List<Area> getAll();
}

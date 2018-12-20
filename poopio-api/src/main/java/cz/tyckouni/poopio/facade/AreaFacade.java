package cz.tyckouni.poopio.facade;

import cz.tyckouni.poopio.dto.AreaCreateDTO;
import cz.tyckouni.poopio.dto.AreaDTO;
import cz.tyckouni.poopio.dto.AreaUpdateDTO;
import cz.tyckouni.poopio.enums.AreaType;
import java.util.List;

/**
 * Facade interface for AreaDTO entities
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public interface AreaFacade {

    /**
     * Finds all Areas.
     *
     * @return List of all Areas.
     */
    List<AreaDTO> getAllAreas();

    /**
     * Creates Area entity.
     *
     * @param area Area entity to be created.
     */
    Long createArea(AreaCreateDTO area);

    /**
     * Deletes the given Area entity.
     *
     * @param areaId Area entity to be deleted.
     */
    void deleteArea(Long areaId);

    /**
     * Finds area with the same Id and changes its values by the given area
     *
     * @param update To be used.
     * @return Updated AreaDTO
     */

    AreaDTO updateArea(AreaUpdateDTO update);

    /**
     * Returns a List of Areas with given AreaType.
     *
     * @param type To be used.
     * @return List of Areas with given type.
     */
    List<AreaDTO> getAllForType(AreaType type);

    /**
     * Finds Area entity by given Id.
     *
     * @param id Id to be used for search.
     * @return Area with given Id.
     */
    AreaDTO findById(Long id);

    /**
     * Finds Area entity by given Name.
     *
     * @param name Name to be used for search.
     * @return Area with given Name.
     */
    AreaDTO findByName(String name);

    /**
     * Finds the most dangerous Areas. That means, it return a List of Areas
     * which has highest number of monsters.
     *
     * @return List of most dangerous Areas.
     */
    List<AreaDTO> getTheMostDangerousAreas();

    /**
     * Adds Monster to Area
     *
     * @param areaId to be used.
     * @param monsterId To be added.
     */
    void addMonsterToArea(Long areaId, Long monsterId);

    /**
     * Remove Monster From Area
     *
     * @param areaId to be used.
     * @param monsterId To be removed.
     */
    void removeMonsterFromArea(Long areaId, Long monsterId);
}

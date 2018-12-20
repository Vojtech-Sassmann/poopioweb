package cz.tyckouni.poopio.rest.controllers;

import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.AreaCreateDTO;
import cz.tyckouni.poopio.dto.AreaDTO;
import cz.tyckouni.poopio.dto.AreaUpdateDTO;
import cz.tyckouni.poopio.enums.AreaType;

import cz.tyckouni.poopio.enums.UserRole;
import cz.tyckouni.poopio.facade.AreaFacade;
import cz.tyckouni.poopio.facade.MonsterFacade;
import cz.tyckouni.poopio.rest.ApiUris;
import cz.tyckouni.poopio.rest.exceptions.InvalidParameterException;
import cz.tyckouni.poopio.rest.exceptions.PrivilegeException;
import cz.tyckouni.poopio.rest.exceptions.ResourceAlreadyExistingException;
import cz.tyckouni.poopio.rest.exceptions.ResourceNotFoundException;

import cz.tyckouni.poopio.rest.security.RoleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */

@RestController
@RequestMapping(ApiUris.ROOT_URI_AREAS)
public class AreasController {

    private final static Logger log = LoggerFactory.getLogger(AreasController.class);

    private final AreaFacade areaFacade;
    private final MonsterFacade monsterFacade;
    private final RoleResolver roleResolver;

    @Inject
    public AreasController(AreaFacade areaFacade, MonsterFacade monsterFacade, RoleResolver roleResolver) {
        this.areaFacade = areaFacade;
        this.monsterFacade = monsterFacade;
        this.roleResolver = roleResolver;
    }

    /**
     * Get list of Areas curl -i -X GET http://localhost:8080/pa165/rest/auth/auth/areas
     *
     * @return List<AreaDTO>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AreaDTO> getAllAreas() {
        log.debug("rest getAllAreas()");

        return areaFacade.getAllAreas();
    }

    /**
     * Create a new Area by POST method
     * curl -X POST -i -H "Content-Type:
     * application/json" --data '{"name":"New Area","type":"MOUNTAINS"}'
     * http://localhost:8080/pa165/rest/auth/areas/create
     *
     * @param area AreaCreateDTO with required fields for creation
     * @return the created area AreaDTO
     * @throws ResourceAlreadyExistingException when area with given name
     * already exists
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO createArea(@RequestBody AreaCreateDTO area, HttpServletRequest request) {

        log.debug("rest createArea({})", area);

        if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
            throw new PrivilegeException("Not permitted.");
        }

        AreaDTO areaWithSameName = areaFacade.findByName(area.getName());
        if (areaWithSameName != null) {
            throw new ResourceAlreadyExistingException("Area with given name is already existing.");
        } else {
            Long id = areaFacade.createArea(area);
            return areaFacade.findById(id);
        }
    }

    /**
     * Delete one area by id
     * curl -i -X DELETE
     * http://localhost:8080/pa165/rest/auth/areas/1
     *
     * @param id identifier of area
     * @throws ResourceNotFoundException when area with given ID wasn't found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteArea(@PathVariable("id") long id, HttpServletRequest request) {

        log.debug("rest deleteArea({})", id);

        if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
            throw new PrivilegeException("Not permitted.");
        }

        try {
            areaFacade.deleteArea(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Area not found.");
        }
    }

    /**
     * Finds area with the same id and updates its attributes. Not provided
     * values are not changed. Id is required.
     *
     * curl -i -X PUT -H "Content-Type: application/json" --data '{"type":
     * "MOUNTAINS", "name": "Mountains full of Kyle Moms"}'
     * http://localhost:8080/pa165/rest/auth/areas/1
     *
     * @param areaUpdate required fields as specified in AreaUpdateDTO except id
     * @return the updated AreaDTO
     * @throws InvalidParameterException when the given parameters are invalid
     * @throws ResourceNotFoundException when area with given id is not found
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO updateArea(@RequestBody AreaUpdateDTO areaUpdate, HttpServletRequest request) {

        log.debug("rest updateArea({})", areaUpdate);

        if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
            throw new PrivilegeException("Not permitted.");
        }

        if (areaUpdate.getId() == null) {
            throw new InvalidParameterException("Value 'id' is required.");
        }

        AreaDTO updatedArea = areaFacade.updateArea(areaUpdate);

        if (updatedArea == null) {
            throw new ResourceNotFoundException("Area not found.");
        }

        return updatedArea;
    }

    /**
     * Gets all areas with given type by GET method
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/auth/areas/filter/type/MOUNTAINS
     *
     * @param type type that will be used for search
     * @return List of AreaDTO with given type
     */
    @RequestMapping(value = "/filter/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AreaDTO> getAllForType(@PathVariable("type") AreaType type) {

        log.debug("rest getAllForType({})", type);

        if (type == null) {
            throw new InvalidParameterException("Invalid parameters given.");
        }

        return areaFacade.getAllForType(type);
    }

    /**
     * Get Area by identifier id
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/auth/areas/1
     *
     * @param id identifier of area
     * @return AreaDTO
     * @throws ResourceNotFoundException when area is not found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO findById(@PathVariable("id") long id) {

        log.debug("rest findById({})", id);

        AreaDTO area = areaFacade.findById(id);

        if (area == null) {
            throw new ResourceNotFoundException("Area not found.");
        }

        return area;
    }

    /**
     * Get Area by name id
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/auth/areas/filter/name/district
     *
     * @param name name of area
     * @return AreaDTO
     * @throws ResourceNotFoundException when area is not found
     */
    @RequestMapping(value = "/filter/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AreaDTO findByName(@PathVariable("name") String name) {

        log.debug("rest findByName({})", name);

        AreaDTO area = areaFacade.findByName(name);

        if (area == null) {
            throw new ResourceNotFoundException("Area not found.");
        }

        return area;
    }

    /**
     * Get most dangerous areas
     * curl -i -X GET
     * http://localhost:8080/pa165/rest/auth/areas/filter/mostDangerous
     *
     * @return List of AreaDTO which are most dangerous
     */
    @RequestMapping(value = "/filter/mostDangerous", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AreaDTO> getTheMostDangerousAreas() {

        log.debug("rest getTheMostDangerousAreas()");

        return areaFacade.getTheMostDangerousAreas();
    }

    /**
     * Add MonsterDTO to area.
     *
     * curl -i -X POST -H "Content-Type: application/json"
     * http://localhost:8080/pa165/rest/auth/areas/1/addMonsterToArea?id=2
     *
     * @param areaId identifier of the area to be updated
     * @param id identifier of the area to be added
     * @throws ResourceNotFoundException when given id does not match any
     * MonsterDTO object
     */
    @RequestMapping(value = "{areaId}/addMonsterToArea", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

    public final void addMonsterToArea(@PathVariable("areaId") long areaId, @Param("id") long id) {

        log.debug("Rest addMonsterToArea with id ({}) to area with id({})", id, areaId);

        AreaDTO areaDTO = areaFacade.findById(areaId);

        if (areaDTO == null) {
            throw new ResourceNotFoundException("Area with given id does not exist.");
        }

        MonsterDTO monsterDTO = monsterFacade.findById(id);

        if (monsterDTO == null) {
            throw new ResourceNotFoundException("Monster with this id does not exist.");
        }

        areaFacade.addMonsterToArea(areaId, monsterDTO.getId());

    }

    /**
     * Remove MonsterDTO from area.
     *
     * curl -i -X POST -H "Content-Type: application/json"
     * http://localhost:8080/pa165/rest/auth/areas/1/removeMonsterFromArea?id=1
     *
     * @param areaId identified of the area to be updated
     * @param id identifier of the monster to be removed
     * @throws InvalidParameterException when the given parameters are invalid
     */
    @RequestMapping(value = "/{areaId}/removeMonsterFromArea", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

    public final void removeMonsterFromArea(@PathVariable("areaId") long areaId, @Param("id") long id) {

        log.debug("Rest removeMonsterFromArea with id ({}) for area with id({})", id, areaId);

        try {
            areaFacade.removeMonsterFromArea(areaId, id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to remove monster from area.");
        }
    }
}

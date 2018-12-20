package cz.tyckouni.poopio.service.facade;

import cz.tyckouni.poopio.dto.AreaCreateDTO;
import cz.tyckouni.poopio.dto.AreaDTO;
import cz.tyckouni.poopio.dto.AreaUpdateDTO;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.facade.AreaFacade;
import cz.tyckouni.poopio.service.BeanMappingService;
import cz.tyckouni.poopio.service.AreaService;
import cz.tyckouni.poopio.service.MonsterService;
import java.util.List;
import javax.inject.Inject;

import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.facade.AreaFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
@Service
@Transactional
public class AreaFacadeImpl implements AreaFacade {

    private final AreaService areaService;

    private final MonsterService monsterService;

    private final BeanMappingService beanMappingService;

    @Inject
    public AreaFacadeImpl(AreaService areaService, MonsterService monsterService, BeanMappingService beanMappingService) {
        this.areaService = areaService;
        this.monsterService = monsterService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long createArea(AreaCreateDTO area) {
        Area mappedArea = beanMappingService.mapTo(area, Area.class);
        areaService.createArea(mappedArea);
        return mappedArea.getId();
    }

    @Override
    public void deleteArea(Long id) {
        areaService.deleteArea(areaService.findById(id));
    }

    @Override
    public AreaDTO updateArea(AreaUpdateDTO update) {
        Area area = areaService.findById(update.getId());

        if (area == null) {
            return null;
        }

        area.setName(update.getName());
        area.setType(update.getType());

        return beanMappingService.mapTo(area, AreaDTO.class);
    }

    @Override

    public List<AreaDTO> getTheMostDangerousAreas() {
        return beanMappingService.mapTo(areaService.getTheMostDangerousAreas(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAllAreas() {
        return beanMappingService.mapTo(areaService.getAllAreas(), AreaDTO.class);
    }

    @Override
    public List<AreaDTO> getAllForType(AreaType type) {
        return beanMappingService.mapTo(areaService.getAllForType(type), AreaDTO.class);
    }

    @Override
    public AreaDTO findById(Long id) {
        return beanMappingService.mapTo(areaService.findById(id), AreaDTO.class);
    }

    @Override
    public AreaDTO findByName(String name) {
        return beanMappingService.mapTo(areaService.findByName(name), AreaDTO.class);
    }

    @Override
    public void addMonsterToArea(Long areaId, Long monsterId) {
        areaService.addMonsterToArea(areaService.findById(areaId),
                monsterService.findById(monsterId));
    }

    @Override
    public void removeMonsterFromArea(Long areaId, Long monsterId) {
        areaService.removeMonsterFromArea(areaService.findById(areaId),
                monsterService.findById(monsterId));
    }
}

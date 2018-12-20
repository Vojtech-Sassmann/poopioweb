package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.AreaDao;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.dao.AreaDao;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
@Service
public class AreaServiceImpl implements AreaService {

    private final AreaDao areaDao;

    @Inject
    public AreaServiceImpl(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Override
    public List<Area> getAllAreas() {
        return areaDao.getAll();
    }

    @Override
    public void createArea(Area area) {
        areaDao.create(area);
    }

    @Override
    public List<Area> getTheMostDangerousAreas() {
        List<Area> allAreas = areaDao.getAll();
        int maxNumOfMonsters = 0;
        for (Area area : allAreas) {
            if (area.getMonsters().size() > maxNumOfMonsters) {
                maxNumOfMonsters = area.getMonsters().size();
            }
        }
        int finalMaxNumOfMonsters = maxNumOfMonsters;
        return allAreas.stream()
                .filter(area -> area.getMonsters().size() == finalMaxNumOfMonsters)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArea(Area area) {
        areaDao.delete(area);
    }

    @Override
    public Area findById(Long id) {
        return areaDao.findById(id);
    }

    @Override
    public Area findByName(String name) {
        return areaDao.findByName(name);
    }

    @Override
    public List<Area> getAllForType(AreaType type) {
        return areaDao.getAllForType(type);
    }

    @Override
    public void addMonsterToArea(Area area, Monster monster) {
        if (area.getMonsters().contains(monster)) {
            throw new IllegalArgumentException(
                    "Monster is already in this area. monster: "
                    + monster.getId() + ", area: "
                    + area.getId());
        }
        area.addMonster(monster);
    }

    @Override
    public void removeMonsterFromArea(Area area, Monster monster) {
        if (!area.getMonsters().contains(monster)) {
            throw new IllegalArgumentException(
                    "Monster is not in this area. monster: "
                    + monster.getId() + ", area: "
                    + area.getId());
        }
        area.removeMonster(monster);
    }
}

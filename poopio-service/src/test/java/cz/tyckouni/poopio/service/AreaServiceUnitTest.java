package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.AreaDao;
import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.dao.AreaDao;
import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public class AreaServiceUnitTest {

    private AreaDao areaDao = mock(AreaDao.class);
    private MonsterDao monsterDao = mock(MonsterDao.class);

    @InjectMocks
    private AreaService areaService;

    private Area district;
    private Area mountains;
    private Area district2;

    private Monster m1;
    private Monster m2;
    private Monster m3;

    @BeforeMethod
    public void createEntities() {
        areaService = new AreaServiceImpl(areaDao);

        m1 = new Monster("Handless Butcher");
        m2 = new Monster("Kyles Mom");
        m3 = new Monster("Something scary");

        district = new Area("District");
        mountains = new Area("Mountains");
        district2 = new Area("Second District");

        district.setType(AreaType.DISTRICT);
        mountains.setType(AreaType.MOUNTAINS);
        district2.setType(AreaType.DISTRICT);

        district.setId(1L);
        mountains.setId(2L);
        district2.setId(3L);

        areaService.addMonsterToArea(district, m1);
        areaService.addMonsterToArea(district, m2);

        areaService.addMonsterToArea(mountains, m1);
        areaService.addMonsterToArea(mountains, m2);

        areaService.addMonsterToArea(district2, m2);
    }

    @Test
    public void testGetTheMostDangerousAreas() {
        when(areaDao.getAll()).thenReturn(Arrays.asList(district, mountains, district2));

        List<Area> mostDangerousAreas = areaService.getTheMostDangerousAreas();
        assertThat(mostDangerousAreas).containsOnly(district, mountains);
    }

    @Test
    public void testGetAllAreas() {
        when(areaDao.getAll()).thenReturn(Arrays.asList(district, mountains, district2));

        List<Area> allAreas = areaService.getAllAreas();
        assertThat(allAreas).containsOnly(district, mountains, district2);
    }

    @Test
    public void testDeleteArea() {
        when(areaDao.findById(1L)).thenReturn(district);

        areaService.deleteArea(district);

        verify(areaDao, times(1)).delete(district);
    }

    @Test
    public void testCreateArea() {
        areaService.createArea(district);

        verify(areaDao, times(1)).create(district);
    }

    @Test
    public void testFindById() {
        when(areaDao.findById(1L)).thenReturn(district);

        Area foundArea = areaService.findById(1L);

        assertThat(foundArea).isEqualToComparingFieldByField(district);
    }

    @Test
    public void testFindByName() {
        when(areaDao.findByName("District")).thenReturn(district);

        Area foundArea = areaService.findByName("District");

        assertThat(foundArea).isEqualToComparingFieldByField(district);
    }

    @Test
    public void testGetAllForType() {
        when(areaDao.getAllForType(AreaType.DISTRICT))
                .thenReturn(Collections.unmodifiableList(Arrays.asList(district, district2)));

        List<Area> foundAreas = areaService.getAllForType(AreaType.DISTRICT);

        assertThat(foundAreas).containsOnly(district, district2);
    }

    @Test
    public void testAddMonsterToArea() {
        when(areaDao.findById(district2.getId())).thenReturn(district2);
        when(monsterDao.findById(m3.getId())).thenReturn(m3);

        areaService.addMonsterToArea(district2, m3);

        assertThat(district2.getMonsters()).containsOnly(m2, m3);

    }

    @Test
    public void testRemoveMonsterFromArea() {
        when(areaDao.findById(district.getId())).thenReturn(district);
        when(monsterDao.findById(m2.getId())).thenReturn(m2);

        areaService.removeMonsterFromArea(district, m2);

        assertThat(district.getMonsters()).containsOnly(m1);

    }
}

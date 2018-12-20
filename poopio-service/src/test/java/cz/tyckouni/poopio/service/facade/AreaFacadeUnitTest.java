package cz.tyckouni.poopio.service.facade;

import cz.tyckouni.poopio.dto.AreaCreateDTO;
import cz.tyckouni.poopio.dto.AreaDTO;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.facade.AreaFacade;
import cz.tyckouni.poopio.service.BeanMappingService;
import cz.tyckouni.poopio.service.AreaService;
import cz.tyckouni.poopio.service.MonsterService;
import cz.tyckouni.poopio.service.config.ServiceConfiguration;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AreaFacadeUnitTest extends AbstractTestNGSpringContextTests {

    private AreaService areaService = mock(AreaService.class);
    private MonsterService monsterService = mock(MonsterService.class);

    @Inject
    private BeanMappingService beanMappingService;

    @InjectMocks
    private AreaFacade areaFacade;

    private Area district;
    private Area mountains;
    private Area district2;

    private Monster m1;
    private Monster m2;

    private AreaDTO districtDTO;
    private AreaDTO district2DTO;
    private AreaDTO mountainsDTO;

    private AreaCreateDTO districtCreateDTO;

    @BeforeMethod
    public void setFacade() {
        areaFacade = new AreaFacadeImpl(areaService, monsterService, beanMappingService);
    }

    @BeforeMethod
    public void createEntities() {

        m1 = new Monster("Handless Butcher");
        m2 = new Monster("Kyles Mom");

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

        districtDTO = new AreaDTO();
        mountainsDTO = new AreaDTO();
        district2DTO = new AreaDTO();

        districtDTO.setType(AreaType.DISTRICT);
        mountainsDTO.setType(AreaType.MOUNTAINS);
        district2DTO.setType(AreaType.DISTRICT);

        districtDTO.setId(1L);
        mountainsDTO.setId(2L);
        district2DTO.setId(3L);

        districtDTO.setName(district.getName());
        district2DTO.setName(district2.getName());
        mountainsDTO.setName(mountains.getName());

        districtCreateDTO = new AreaCreateDTO();
        districtCreateDTO.setName(district.getName());
        districtCreateDTO.setType(district.getType());
    }

    @Test
    public void testCreateArea() throws Exception {
        areaFacade.createArea(districtCreateDTO);

        verify(areaService, times(1)).createArea(district);
    }

    @Test
    public void testDeleteArea() throws Exception {
        when(areaService.findById(any(Long.class))).thenReturn(district2);

        areaFacade.deleteArea(district2DTO.getId());

        verify(areaService, times(1)).deleteArea(district2);
    }

    @Test
    public void testGetAllAreas() throws Exception {

        when(areaService.getAllAreas()).thenReturn(Arrays.asList(district, district2, mountains));

        List<AreaDTO> allAreas = areaFacade.getAllAreas();
        assertThat(allAreas).containsOnly(districtDTO, district2DTO, mountainsDTO);
    }

    @Test
    public void testGetAllForType() throws Exception {

        when(areaService.getAllForType(AreaType.DISTRICT)).thenReturn(Arrays.asList(district, district2));

        List<AreaDTO> allForType = areaFacade.getAllForType(AreaType.DISTRICT);
        assertThat(allForType).containsOnly(districtDTO, district2DTO);
    }

    @Test
    public void testFindById() throws Exception {
        when(areaService.findById(any(Long.class))).thenReturn(district);

        AreaDTO foundArea = areaFacade.findById(1L);

        assertThat(foundArea).isEqualToComparingFieldByField(districtDTO);
    }

    @Test
    public void testFindByName() throws Exception {
        when(areaService.findByName("District")).thenReturn(district);

        AreaDTO foundArea = areaFacade.findByName("District");

        assertThat(foundArea).isEqualToComparingFieldByField(districtDTO);
    }

    @Test
    public void testAddMonsterToArea() {
        Long id = 1L;

        when(areaService.findById(id)).thenReturn(district2);
        when(monsterService.findById(id)).thenReturn(m1);

        areaFacade.addMonsterToArea(id, id);

        verify(areaService, times(1)).addMonsterToArea(district2, m1);
    }

    @Test
    public void testRemoveMonsterFromArea() {
        Long id = 1L;

        when(areaService.findById(id)).thenReturn(district);
        when(monsterService.findById(id)).thenReturn(m1);

        areaFacade.removeMonsterFromArea(id, id);

        verify(areaService, times(1)).removeMonsterFromArea(district, m1);
    }
}

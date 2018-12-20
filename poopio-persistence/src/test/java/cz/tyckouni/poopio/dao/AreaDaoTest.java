package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.PersistenceApplicationContext;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.AreaType;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for AreaDao
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AreaDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private AreaDao areaDao;

    @Inject
    private MonsterDao monsterDao;

    @PersistenceContext
    private EntityManager em;


    private Area CreateTestArea(){
        Area area = new Area();
        area.setName("testArea");
        area.setType(AreaType.MOUNTAINS);
        return area;
    }

    private Area CreateTestArea1(){
        Area area = new Area();
        area.setName("area1");
        area.setType(AreaType.DISTRICT);
        return area;
    }

    //Tests for method: Create
    @Test
    public void testCreateAreaWithNull(){
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.create(null));
    }

    @Test
    public void testCreateArea(){
        Area area = CreateTestArea();
        areaDao.create(area);

        Area foundArea = areaDao.findById(area.getId());
        assertThat(foundArea).isEqualToComparingFieldByField(area);
    }

    //Tests for method: Delete
    @Test
    public void testDeleteAreaWithNull(){
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.delete(null));
    }

    @Test
    public void testDeleteArea(){
        Area area = CreateTestArea();
        areaDao.create(area);
        areaDao.delete(area);

        Area foundArea = areaDao.findById(area.getId());
        assertThat(foundArea).isEqualTo(null);
    }

    //Tests for method: FindById
    @Test
    public void testFindByIdWithNull() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.findById(null));
    }

    @Test
    public void testFindById(){
        Area area = CreateTestArea();
        areaDao.create(area);

        Area foundArea = areaDao.findById(area.getId());
        assertThat(foundArea).isEqualToComparingFieldByField(area);
    }

    @Test
    public void testFindByIdWithBadId(){
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.findById(-1L));
    }

    @Test
    public void testFindByIdNothingFound(){
        Area area = CreateTestArea();
        areaDao.create(area);

        Area foundArea = areaDao.findById(10L);
        assertThat(foundArea).isEqualTo(null);
    }

    //Tests for method: FindByName
    @Test
    public void testFindByNameWithNull() {
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.findByName(null));
    }

    @Test
    public void testFindByNameWithEmpty(){
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.findByName(""));
    }

    @Test
    public void testFindByName(){
        Area area = CreateTestArea();
        areaDao.create(area);

        Area foundArea = areaDao.findByName(area.getName());
        assertThat(foundArea).isEqualToComparingFieldByField(area);
    }

    @Test
    public void testFindByNameNothingFound(){
        Area foundArea = areaDao.findByName("test");
        assertThat(foundArea).isEqualTo(null);
    }

    //Tests for method: GetAll
    @Test
    public void testGetAllForEmptyDB(){
        List<Area> listOfAreas = areaDao.getAll();
        assertThat(listOfAreas).isEmpty();
    }

    @Test
    public void testGetAll(){
        Area area = CreateTestArea();
        Area area1 = CreateTestArea1();
        areaDao.create(area);
        areaDao.create(area1);

        List<Area> listOfAreas = areaDao.getAll();
        assertThat(listOfAreas).containsExactly(area,area1);
    }

    //Tests for method: GetAllForType
    @Test
    public void testGetAllForTypeWithNull(){
        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> areaDao.getAllForType(null));
    }

    @Test
    public void testGetAllForTypeForEmptyDB(){
        List<Area> listOfAreas = areaDao.getAllForType(AreaType.DESERT);
        assertThat(listOfAreas).isEmpty();
    }

    @Test
    public void testGetAllForTypeWithoutTypeOther(){
        Area area = CreateTestArea();
        Area area1 = CreateTestArea1();
        areaDao.create(area);
        areaDao.create(area1);

        List<Area> listOfAreas = areaDao.getAllForType(AreaType.OTHER);
        assertThat(listOfAreas).isEmpty();
    }

    @Test
    public void testGetAllForType(){
        Area area = CreateTestArea();
        Area area1 = CreateTestArea1();
        areaDao.create(area);
        areaDao.create(area1);

        List<Area> listOfAreas = areaDao.getAllForType(area.getType());
        assertThat(listOfAreas).containsExactly(area);
    }

    @Test
    public void testAddRelationWithMonster(){
        Area area = CreateTestArea();
        Monster monster = new Monster();

        monster.setName("Monster");
        area.addMonster(monster);

        monsterDao.create(monster);
        areaDao.create(area);

        Set<Area> areasForMonsters = monster.getAreas();
        assertThat(areasForMonsters).contains(area);
    }

    @Test
    public void testRemoveRelationWithMonster(){
        Area area = CreateTestArea();
        Monster monster = new Monster();

        monster.setName("Monster");
        area.addMonster(monster);

        monsterDao.create(monster);
        areaDao.create(area);

        area.removeMonster(monster);

        Set<Area> areasForMonsters = monster.getAreas();
        assertThat(areasForMonsters).doesNotContain(area);
    }

}

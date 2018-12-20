package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.entity.Area;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
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
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class MonsterServiceUnitTest {

	private MonsterDao monsterDao = mock(MonsterDao.class);

	@InjectMocks
	private MonsterService monsterService;

	private Monster zombie;
	private Monster headlessChicken;
	private Monster spider;

	@BeforeMethod
	public void createEntities() {
		monsterService = new MonsterServiceImpl(monsterDao);

		Area a1 = new Area("Desert");
		Area a2 = new Area("Forest");

		zombie = new Monster("Zombie");
		headlessChicken = new Monster("Headless Chicken");
		spider = new Monster("Spider");

		zombie.setAgility(MonsterAgility.FAST);
		headlessChicken.setAgility(MonsterAgility.FAST);
		spider.setAgility(MonsterAgility.SLOW);

		zombie.setId(1L);
		headlessChicken.setId(2L);
		spider.setId(3L);

		zombie.addArea(a1);
		zombie.addArea(a2);

		spider.addArea(a1);
		spider.addArea(a2);

		headlessChicken.addArea(a1);
	}

	@Test
	public void testGetTheMostWidespreadMonsters() {
		when(monsterDao.getAll()).thenReturn(Arrays.asList(zombie, headlessChicken, spider));

		List<Monster> mostWidespreadMonsters = monsterService.getTheMostWidespreadMonsters();
		assertThat(mostWidespreadMonsters).containsOnly(zombie, spider);
	}

	@Test
	public void testGetAllMonsters() {
		when(monsterDao.getAll()).thenReturn(Arrays.asList(zombie, headlessChicken, spider));

		List<Monster> allMonsters = monsterService.getAllMonsters();
		assertThat(allMonsters).containsOnly(zombie, spider, headlessChicken);
	}

	@Test
	public void testDeleteMonster() {
		when(monsterDao.findById(1L)).thenReturn(zombie);

		monsterService.deleteMonster(zombie);

		verify(monsterDao, times(1)).delete(zombie);
	}

	@Test
	public void testCreateMonster() {
		monsterService.createMonster(zombie);

		verify(monsterDao, times(1)).create(zombie);
	}

	@Test
	public void testFindById() {
		when(monsterDao.findById(1L)).thenReturn(zombie);

		Monster foundMonster = monsterService.findById(1L);

		assertThat(foundMonster).isEqualToComparingFieldByField(zombie);
	}

	@Test
	public void testFindByName() {
		when(monsterDao.findByName("Zombie")).thenReturn(zombie);

		Monster foundMonster = monsterService.findByName("Zombie");

		assertThat(foundMonster).isEqualToComparingFieldByField(zombie);
	}

	@Test
	public void testGetAllForAgility() {
		when(monsterDao.getAllForAgility(MonsterAgility.FAST))
				.thenReturn(Collections.unmodifiableList(Arrays.asList(zombie, headlessChicken)));

		List<Monster> foundMonsters = monsterService.getAllForAgility(MonsterAgility.FAST);

		assertThat(foundMonsters).containsOnly(zombie, headlessChicken);
	}
}

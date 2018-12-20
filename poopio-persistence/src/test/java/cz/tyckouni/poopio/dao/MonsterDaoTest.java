package cz.tyckouni.poopio.dao;


import cz.tyckouni.poopio.PersistenceApplicationContext;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.enums.MonsterAgility;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MonsterDaoTest extends AbstractTestNGSpringContextTests {

	@Inject
	private MonsterDao monsterDao;

	@PersistenceContext
	private EntityManager em;

	private Monster monster1;
	private Monster monster2;

	@Test (expectedExceptions=DataAccessException.class)
	public void createMonsterWithNullArgumentTest() {
		monsterDao.create(null);
	}

	@Test (expectedExceptions=DataAccessException.class)
	public void deleteMonsterWithNullArgumentTest() {
		monsterDao.delete(null);
	}

	@Test (expectedExceptions=DataAccessException.class)
	public void findByIdWithNullArgumentTest() {
		monsterDao.findById(null);
	}

	@Test (expectedExceptions=DataAccessException.class)
	public void findByIdWithNegativeIdTest() {
		monsterDao.findById(-1L);
	}

	@Test (expectedExceptions=DataAccessException.class)
	public void findByNameWithNullArgumentTest() {
		monsterDao.findByName(null);
	}

	@Test (expectedExceptions=DataAccessException.class)
	public void findByNameWithEmptyNameTest() {
		monsterDao.findByName("");
	}

	@Test (expectedExceptions=DataAccessException.class)
	public void getAllForAgilityWithNullArgumentTest() {

		monsterDao.getAllForAgility(null);
	}

	@Test
	public void createMonsterTest() {
		Monster monster = new Monster("Radioactive Sea Lion");
		monsterDao.create(monster);

		assertEquals(monsterDao.findByName(monster.getName()), monster);
	}

	@Test
	public void deleteMonsterTest() {
		setUpMonsters();
		monsterDao.delete(monster1);

		assertTrue(!monsterDao.getAll().contains(monster1));
	}

	@Test
	public void getAllTest() {
		setUpMonsters();
		assertTrue(monsterDao.getAll().size() == 2);
	}

	@Test
	public void getAllEmptyResultTest() {
		assertTrue(monsterDao.getAll().size() == 0);
	}

	@Test
	public void findByIdTest() {
		setUpMonsters();
		assertEquals(monsterDao.findById(monster1.getId()), monster1);
	}

	@Test
	public void findByIdNonExistingIdTest() {
		setUpMonsters();
		assertEquals(monsterDao.findById(100L), null);
	}

	@Test
	public void findByNameTest() {
		setUpMonsters();
		assertEquals(monsterDao.findByName(monster1.getName()), monster1);
	}

	@Test
	public void findByNameNonExistingMonsterTest() {
		setUpMonsters();
		assertEquals(monsterDao.findByName("Radioactive Sea Lion"), null);
	}

	@Test
	public void getAllForAgilityTest() {
		setUpMonsters();
		List<Monster> monsters = monsterDao.getAllForAgility(MonsterAgility.FAST);

		assertTrue(monsters.size() == 1 && monsters.contains(monster1));
	}

	@Test
	public void getAllForAgilityEmptyResultTest() {
		setUpMonsters();
		List<Monster> monsters = monsterDao.getAllForAgility(MonsterAgility.SLOW);

		assertTrue(monsters.size() == 0);
	}

	private void setUpMonsters(){
		monster1 = new Monster("Black Squirrel");
		monster2 = new Monster("Dark Sloth");
		monsterDao.create(monster1);
		monsterDao.create(monster2);
		monster1.setAgility(MonsterAgility.FAST);
	}
}

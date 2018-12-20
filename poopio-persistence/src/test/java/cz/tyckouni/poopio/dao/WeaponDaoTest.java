package cz.tyckouni.poopio.dao;

import cz.tyckouni.poopio.PersistenceApplicationContext;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WeaponDaoTest extends AbstractTestNGSpringContextTests {

	@Inject
	private WeaponDao weaponDao;

	@Inject
	private MonsterDao monsterDao;

	@Test
	public void testCreateWithNull() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.create(null));
	}


	@Test
	public void testCreate() {
		Weapon pistol = createPistol();
		Weapon foundWeapon = weaponDao.findById(pistol.getId());
		assertThat(foundWeapon).isEqualToComparingFieldByField(pistol);
	}

	@Test
	public void testRemoveWithNull() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.delete(null));
	}

	@Test
	public void testDelete() {
		Weapon rifle = createRifle();

		weaponDao.delete(rifle);

		Weapon foundWeapon = weaponDao.findById(rifle.getId());
		assertThat(foundWeapon).isEqualTo(null);
	}

	@Test
	public void testFindByIdWithNull() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.findById(null));
	}

	@Test
	public void testFindByIdNegativeId() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.findById(-1L));
	}

	@Test
	public void testFindById() {
		Weapon rifle = createRifle();

		Weapon foundWeapon = weaponDao.findById(rifle.getId());

		assertThat(foundWeapon).isEqualToComparingFieldByField(rifle);
	}

	@Test
	public void testFindByIdNothingFound() {
		Weapon foundWeapon = weaponDao.findById(1L);
		assertThat(foundWeapon).isEqualTo(null);
	}

	@Test
	public void testFindByNameWithNull() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.findByName(null));
	}

	@Test
	public void testFindByNameWithEmptyValue() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.findByName(""));
	}

	@Test
	public void testFindByName() {
		Weapon rifle = createRifle();

		Weapon foundWeapon = weaponDao.findByName(rifle.getName());

		assertThat(foundWeapon).isEqualToComparingFieldByField(rifle);
	}

	@Test
	public void testFindByNameNothingFound() {
		Weapon foundWeapon = weaponDao.findByName("Rifle");
		assertThat(foundWeapon).isEqualTo(null);
	}

	@Test
	public void testGetAllForTypeWithNull() {
		assertThatExceptionOfType(DataAccessException.class)
				.isThrownBy(() -> weaponDao.getAllForType(null));
	}

	@Test
	public void testGetAllForType() {
		Weapon rifle = createRifle();

		createPistol();

		List<Weapon> foundWeapons = weaponDao.getAllForType(WeaponType.RIFLE);

		assertThat(foundWeapons).containsExactly(rifle);
	}

	@Test
	public void testGetAllForTypeNothingFound() {
		createPistol();

		List<Weapon> foundWeapons = weaponDao.getAllForType(WeaponType.RIFLE);

		assertThat(foundWeapons).isEmpty();
	}

	@Test
	public void testGetAll() {
		Weapon rifle = createRifle();
		Weapon pistol = createPistol();

		List<Weapon> foundWeapons = weaponDao.getAll();

		assertThat(foundWeapons).containsExactly(rifle, pistol);
	}

	@Test
	public void testGetAllNothingFound() {
		List<Weapon> foundWeapons = weaponDao.getAll();
		assertThat(foundWeapons).isEmpty();
	}

	@Test
	public void testManyToManyRelationWithMonsterAdd() {
		Weapon rifle = createRifle();
		Monster zombie = new Monster("Zombie");

		rifle.addAppropriateMonster(zombie);

		monsterDao.create(zombie);

		Set<Weapon> weaponsForZombie = zombie.getAppropriateWeapons();

		assertThat(weaponsForZombie).contains(rifle);
	}

	@Test
	public void testManyToManyRelationWithMonsterRemove() {
		Weapon rifle = createRifle();
		Monster zombie = new Monster("Zombie");

		rifle.addAppropriateMonster(zombie);

		monsterDao.create(zombie);

		rifle.removeAppropriateMonster(zombie);

		Set<Weapon> weaponsForZombie = zombie.getAppropriateWeapons();

		assertThat(weaponsForZombie).doesNotContain(rifle);
	}

	private Weapon createRifle() {
		Weapon rifle = new Weapon("Rifle");
		rifle.setType(WeaponType.RIFLE);
		rifle.setMagazineCapacity(40);
		rifle.setRange(250);
		weaponDao.create(rifle);
		return rifle;
	}

	private Weapon createPistol() {
		Weapon pistol = new Weapon("Pistol");
		pistol.setType(WeaponType.PISTOL);
		pistol.setMagazineCapacity(10);
		pistol.setRange(50);
		weaponDao.create(pistol);
		return pistol;
	}
}

package cz.tyckouni.poopio.service;

import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.dao.WeaponDao;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.dao.MonsterDao;
import cz.tyckouni.poopio.dao.WeaponDao;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.WeaponType;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeaponServiceUnitTest {

    private WeaponDao weaponDao = mock(WeaponDao.class);

    private MonsterDao monsterDao = mock(MonsterDao.class);

    @InjectMocks
    private WeaponService weaponService;

    private Weapon pistol;
    private Weapon rifle;
    private Weapon shotgun;

    private Monster spider;
    private Monster zombie;

    @BeforeMethod
    public void setService() {
        weaponService = new WeaponServiceImpl(weaponDao, monsterDao);
    }

    @BeforeMethod
    public void createEntities(){
        spider = new Monster("Spider");
        zombie = new Monster("Zombie");

        pistol = new Weapon("Pistol");
        pistol.setId(1L);
        pistol.setRange(100);
        pistol.setMagazineCapacity(10);
        pistol.setType(WeaponType.PISTOL);

        rifle = new Weapon("Rifle");
        rifle.setId(2L);
        rifle.setRange(150);
        rifle.setMagazineCapacity(25);
        rifle.setType(WeaponType.RIFLE);

        shotgun = new Weapon("Shotgun");
        shotgun.setId(3L);
        shotgun.setRange(80);
        shotgun.setMagazineCapacity(2);
        shotgun.setType(WeaponType.SHOTGUN);

        pistol.addAppropriateMonster(spider);
        rifle.addAppropriateMonster(spider);
        rifle.addAppropriateMonster(zombie);
        shotgun.addAppropriateMonster(zombie);
    }

    @Test
    public void testCreateWeapon(){
        weaponService.createWeapon(pistol);

        verify(weaponDao, times(1)).create(pistol);
    }

    @Test
    public void testDeleteWeapon(){
        when(weaponDao.findById(1L)).thenReturn(pistol);

        weaponService.deleteWeapon(pistol);

        verify(weaponDao, times(1)).delete(pistol);
    }

    @Test
    public void testFindWeaponById(){
        when(weaponDao.findById(1L)).thenReturn(pistol);

        Weapon weapon = weaponService.findById(1L);

        assertThat(weapon).isEqualToComparingFieldByField(pistol);
    }

    @Test
    public void testFindWeaponByName(){
        when(weaponDao.findByName("Pistol")).thenReturn(pistol);

        Weapon weapon = weaponService.findByName("Pistol");

        assertThat(weapon).isEqualToComparingFieldByField(pistol);
    }

    @Test
    public void testGetAllWeapons(){
        when(weaponDao.getAll()).thenReturn(Arrays.asList(pistol, rifle, shotgun));

        List<Weapon> weapons = weaponService.getAllWeapons();

        assertThat(weapons).containsOnly(pistol, rifle, shotgun);
    }

    @Test
    public void testGetAllWeaponsByType(){
        when(weaponDao.getAllForType(WeaponType.SHOTGUN)).thenReturn(Arrays.asList(shotgun));

        List<Weapon> weapons = weaponService.getAllForType(WeaponType.SHOTGUN);

        assertThat(weapons).containsOnly(shotgun);
    }

    @Test
    public void testAddAppropriateMonster(){
        weaponService.addAppropriateMonster(pistol, zombie);

        assertThat(pistol.getAppropriateMonsters()).containsOnly(spider, zombie);

    }

    @Test
    public void testRemoveAppropriateMonster(){
        assertThat(pistol.getAppropriateMonsters()).containsOnly(spider);

        weaponService.removeAppropriateMonster(pistol, spider);

        assertThat(pistol.getAppropriateMonsters()).doesNotContain(spider);
    }

    @Test
    public void testGetTheMostEffectiveWeapon(){
        when(weaponDao.getAll()).thenReturn(Arrays.asList(pistol, rifle, shotgun));

        List<Weapon> mostEffectiveWeapons = weaponService.getTheMostEffectiveWeapon();

        assertThat(mostEffectiveWeapons).containsOnly(rifle);
    }
}

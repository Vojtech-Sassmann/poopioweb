package cz.tyckouni.poopio.service.facade;

import cz.tyckouni.poopio.dto.MonsterDTO;
import cz.tyckouni.poopio.dto.WeaponCreateDTO;
import cz.tyckouni.poopio.dto.WeaponDTO;
import cz.tyckouni.poopio.dto.WeaponUpdateDTO;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.facade.WeaponFacade;
import cz.tyckouni.poopio.service.BeanMappingService;
import cz.tyckouni.poopio.service.MonsterService;
import cz.tyckouni.poopio.service.WeaponService;
import cz.tyckouni.poopio.entity.Monster;
import cz.tyckouni.poopio.entity.Weapon;
import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.enums.WeaponType;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Implementation of Facade Unit tests
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class WeaponFacadeUnitTest{

    private WeaponService weaponService = mock(WeaponService.class);

    private MonsterService monsterService = mock(MonsterService.class);

    private BeanMappingService beanMappingService = mock(BeanMappingService.class);

    @InjectMocks
    private WeaponFacade weaponFacade;

    private Weapon pistol;
    private Weapon shotgun;
    private Weapon rifle;

    private WeaponDTO pistolDTO;
    private WeaponDTO shotgunDTO;
    private WeaponDTO rifleDTO;

    private WeaponCreateDTO pistolCreateDTO;

    private Monster zombie;

    private MonsterDTO zombieDTO;

    @BeforeMethod
    public void setFacade(){
        weaponFacade = new WeaponFacadeImpl(weaponService, monsterService, beanMappingService);
    }

    @BeforeMethod
    public void createEntities(){
        pistol = new Weapon("Pistol");
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

        pistolDTO = new WeaponDTO();
        pistolDTO.setName("Pistol");
        pistolDTO.setId(1L);
        pistolDTO.setRange(100);
        pistolDTO.setMagazineCapacity(10);
        pistolDTO.setType(WeaponType.PISTOL);

        rifleDTO = new WeaponDTO();
        rifleDTO.setName("Rifle");
        rifleDTO.setId(2L);
        rifleDTO.setRange(150);
        rifleDTO.setMagazineCapacity(25);
        rifleDTO.setType(WeaponType.RIFLE);

        shotgunDTO = new WeaponDTO();
        shotgunDTO.setName("Shotgun");
        shotgunDTO.setId(3L);
        shotgunDTO.setRange(80);
        shotgunDTO.setMagazineCapacity(2);
        shotgunDTO.setType(WeaponType.SHOTGUN);

        pistolCreateDTO = new WeaponCreateDTO();
        pistolCreateDTO.setName("Pistol");
        pistolCreateDTO.setRange(100);
        pistolCreateDTO.setMagazineCapacity(10);
        pistolCreateDTO.setType(WeaponType.PISTOL);

        zombie = new Monster();
        zombie.setId(1L);
        zombie.setName("Zombie");
        zombie.setHeight(54.0);
        zombie.setWeight(105.0);
        zombie.setAgility(MonsterAgility.FAST);

        zombieDTO = new MonsterDTO();
        zombieDTO.setId(1L);
        zombieDTO.setName(zombie.getName());
        zombieDTO.setHeight(zombie.getHeight());
        zombieDTO.setWeight(zombie.getWeight());
        zombieDTO.setAgility(zombie.getAgility());

        rifleDTO.addAppropriateMonster(zombieDTO);

    }

    @AfterMethod
    private void resetMock(){
        reset(weaponService);
        reset(monsterService);
        reset(beanMappingService);
    }

    @Test
    public void testCreateWeapon(){
        when(beanMappingService.mapTo(pistolCreateDTO, Weapon.class)).thenReturn(pistol);

        doAnswer(invocation -> {
            pistol.setId(1L);
            return null;
        }).when(weaponService).createWeapon(pistol);

        Long id = weaponFacade.createWeapon(pistolCreateDTO);

        verify(weaponService, times(1)).createWeapon(pistol);

        assertThat(id).isEqualTo(1L);
    }

    @Test
    public void testDeleteWeapon(){
        Long id = 1L;

        when(weaponService.findById(id)).thenReturn(pistol);

        weaponFacade.deleteWeapon(id);

        verify(weaponService, times(1)).deleteWeapon(pistol);
    }

    @Test
    public void testUpdateName(){
        when(weaponService.findById(2L)).thenReturn(rifle);

        WeaponUpdateDTO weaponUpdateDTO = new WeaponUpdateDTO();
        weaponUpdateDTO.setId(2L);
        weaponUpdateDTO.setName("New name");
        weaponUpdateDTO.setType(rifle.getType());
        weaponUpdateDTO.setRange(rifle.getRange());
        weaponUpdateDTO.setMagazineCapacity(rifle.getMagazineCapacity());
        weaponFacade.updateWeapon(weaponUpdateDTO);

        assertThat(rifle.getName()).isEqualTo("New name");
        Assertions.assertThat(rifle.getType()).isEqualTo(rifle.getType());
        assertThat(rifle.getMagazineCapacity()).isEqualTo(rifle.getMagazineCapacity());
        assertThat(rifle.getRange()).isEqualTo(rifle.getRange());
    }

    @Test
    public void testUpdateRange() {
        when(weaponService.findById(2L)).thenReturn(rifle);

        WeaponUpdateDTO weaponUpdateDTO = new WeaponUpdateDTO();
        weaponUpdateDTO.setId(2L);
        weaponUpdateDTO.setName(rifle.getName());
        weaponUpdateDTO.setType(rifle.getType());
        weaponUpdateDTO.setRange(1000);
        weaponUpdateDTO.setMagazineCapacity(rifle.getMagazineCapacity());
        weaponFacade.updateWeapon(weaponUpdateDTO);

        assertThat(rifle.getName()).isEqualTo(rifle.getName());
        Assertions.assertThat(rifle.getType()).isEqualTo(rifle.getType());
        assertThat(rifle.getMagazineCapacity()).isEqualTo(rifle.getMagazineCapacity());
        assertThat(rifle.getRange()).isEqualTo(1000);
    }

    @Test
    public void testUpdateWeaponType(){
        when(weaponService.findById(2L)).thenReturn(rifle);

        WeaponUpdateDTO weaponUpdateDTO = new WeaponUpdateDTO();
        weaponUpdateDTO.setId(2L);
        weaponUpdateDTO.setName(rifle.getName());
        weaponUpdateDTO.setType(WeaponType.OTHER);
        weaponUpdateDTO.setRange(rifle.getRange());
        weaponUpdateDTO.setMagazineCapacity(rifle.getMagazineCapacity());
        weaponFacade.updateWeapon(weaponUpdateDTO);

        assertThat(rifle.getName()).isEqualTo(rifle.getName());
        Assertions.assertThat(rifle.getType()).isEqualTo(WeaponType.OTHER);
        assertThat(rifle.getMagazineCapacity()).isEqualTo(rifle.getMagazineCapacity());
        assertThat(rifle.getRange()).isEqualTo(rifle.getRange());

    }

    @Test
    public void testUpdateMagazineCapacity(){
        when(weaponService.findById(2L)).thenReturn(rifle);

        WeaponUpdateDTO weaponUpdateDTO = new WeaponUpdateDTO();
        weaponUpdateDTO.setId(2L);
        weaponUpdateDTO.setName(rifle.getName());
        weaponUpdateDTO.setType(rifle.getType());
        weaponUpdateDTO.setRange(rifle.getRange());
        weaponUpdateDTO.setMagazineCapacity(33);
        weaponFacade.updateWeapon(weaponUpdateDTO);

        assertThat(rifle.getName()).isEqualTo(rifle.getName());
        Assertions.assertThat(rifle.getType()).isEqualTo(rifle.getType());
        assertThat(rifle.getMagazineCapacity()).isEqualTo(33);
        assertThat(rifle.getRange()).isEqualTo(rifle.getRange());
    }

    @Test
    public void testFindWeaponById(){
        Long id = 1L;

        when(weaponService.findById(id)).thenReturn(pistol);
        when(beanMappingService.mapTo(pistol,WeaponDTO.class)).thenReturn(pistolDTO);

        WeaponDTO weaponDTO = weaponFacade.findById(id);

        assertThat(weaponDTO).isEqualToComparingFieldByField(pistolDTO);
    }

    @Test
    public void testFindWeaponByName(){
        String name = "Pistol";

        when(weaponService.findByName(name)).thenReturn(pistol);
        when(beanMappingService.mapTo(pistol,WeaponDTO.class)).thenReturn(pistolDTO);

        WeaponDTO weaponDTO = weaponFacade.findByName(name);

        assertThat(weaponDTO).isEqualToComparingFieldByField(pistolDTO);
    }

    @Test
    public void testGetAllWeapons(){
        when(weaponService.getAllWeapons()).thenReturn(Arrays.asList(pistol,rifle,shotgun));
        when(beanMappingService.mapTo(any(), eq(WeaponDTO.class))).thenReturn(Arrays.asList(pistolDTO, rifleDTO, shotgunDTO));

        assertThat(weaponFacade.getAll()).containsOnly(pistolDTO, rifleDTO, shotgunDTO);
    }

    @Test
    public void testGetAllWeaponsForType(){
        when(weaponService.getAllForType(WeaponType.RIFLE)).thenReturn(Arrays.asList(rifle));
        when(beanMappingService.mapTo(any(), eq(WeaponDTO.class))).thenReturn(Arrays.asList(rifleDTO));

        assertThat(weaponFacade.getAllForType(WeaponType.RIFLE)).containsOnly(rifleDTO);
    }

    @Test
    public void testAddAppropriateMonster(){
        Long id = 1L;

        when(weaponService.findById(id)).thenReturn(pistol);
        when(monsterService.findById(id)).thenReturn(zombie);

        weaponFacade.addAppropriateMonster(id, id);

        verify(weaponService, times(1)).addAppropriateMonster(pistol, zombie);
    }

    @Test
    public void testRemoveAppropriateMonster(){
        Long weaponId = 2L;
        Long monsterId = 1L;

        when(weaponService.findById(weaponId)).thenReturn(rifle);
        when(monsterService.findById(monsterId)).thenReturn(zombie);

        weaponFacade.removeAppropriateMonster(weaponId, monsterId);

        verify(weaponService, times(1)).removeAppropriateMonster(rifle, zombie);
    }

    @Test
    public void testGetTheMostEffectiveWeapon(){
        when(weaponService.getTheMostEffectiveWeapon()).thenReturn(Arrays.asList(rifle));
        when(beanMappingService.mapTo(any(), eq(WeaponDTO.class))).thenReturn(Arrays.asList(rifleDTO));

        assertThat(weaponFacade.getTheMostEffectiveWeapon()).containsOnly(rifleDTO);
    }
}

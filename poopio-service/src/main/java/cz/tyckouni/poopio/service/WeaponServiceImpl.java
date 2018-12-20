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
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of WeaponService interface
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Service
public class WeaponServiceImpl implements WeaponService{

    private final WeaponDao weaponDao;

    private final MonsterDao monsterDao;

    @Inject
    public WeaponServiceImpl(WeaponDao weaponDao, MonsterDao monsterDao){
        this.weaponDao = weaponDao;
        this.monsterDao = monsterDao;
    }

    @Override
    public void createWeapon(Weapon weapon) {
        weaponDao.create(weapon);
    }

    @Override
    public void deleteWeapon(Weapon weapon) {
        weaponDao.delete(weapon);
    }

    @Override
    public Weapon findById(Long id) {
        return weaponDao.findById(id);
    }

    @Override
    public Weapon findByName(String name) {
        return weaponDao.findByName(name);
    }

    @Override
    public List<Weapon> getAllWeapons() {
        return weaponDao.getAll();
    }

    @Override
    public List<Weapon> getAllForType(WeaponType type) {
        return weaponDao.getAllForType(type);
    }

    @Override
    public void addAppropriateMonster(Weapon weapon, Monster monster) {
        if (weapon.getAppropriateMonsters().contains(monster)){
            throw new IllegalArgumentException("Monster is already assign as appropriate monster for weapon!");
        }
        weapon.addAppropriateMonster(monster);
    }

    @Override
    public void removeAppropriateMonster(Weapon weapon, Monster monster) {
        if (!weapon.getAppropriateMonsters().contains(monster)){
            throw new IllegalArgumentException("Monster is not assign as appropriate monster for weapon!");
        }
        weapon.removeAppropriateMonster(monster);
    }

    @Override
    public List<Weapon> getTheMostEffectiveWeapon() {
        List<Weapon> weapons =weaponDao.getAll();
        int maxMonsters = 0;
        for (Weapon weapon: weapons) {
            if (weapon.getAppropriateMonsters().size() > maxMonsters){
                maxMonsters = weapon.getAppropriateMonsters().size();
            }
        }
        int finalMaxMonsters = maxMonsters;
        return weapons.stream()
                .filter(weapon -> weapon.getAppropriateMonsters().size() == finalMaxMonsters)
                .collect(Collectors.toList());
    }
}

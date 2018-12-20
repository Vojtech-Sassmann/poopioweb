package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.enums.WeaponType;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO class for Weapon entity
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class WeaponDTO {

    private Long id;
    private String name;
    private WeaponType type;
    private Set<MonsterDTO> appropriateMonsters = new HashSet<>();
    private Integer range;
    private Integer magazineCapacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public Set<MonsterDTO> getAppropriateMonsters() {
        return appropriateMonsters;
    }

    public void setAppropriateMonsters(Set<MonsterDTO> appropriateMonsters) {
        this.appropriateMonsters = appropriateMonsters;
    }

    public void addAppropriateMonster(MonsterDTO monsterDTO){
        appropriateMonsters.add(monsterDTO);
    }

    public void removeAppropriateMonster(MonsterDTO monsterDTO){
        appropriateMonsters.remove(monsterDTO);
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer getMagazineCapacity() {
        return magazineCapacity;
    }

    public void setMagazineCapacity(Integer magazineCapacity) {
        this.magazineCapacity = magazineCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof WeaponDTO)) return false;

        WeaponDTO weaponDTO = (WeaponDTO) o;

        if (id != null ? !id.equals(weaponDTO.id) : weaponDTO.id != null) return false;
        if (name != null ? !name.equals(weaponDTO.name) : weaponDTO.name != null) return false;
        if (type != weaponDTO.type) return false;
        if (appropriateMonsters != null ? !appropriateMonsters.equals(weaponDTO.appropriateMonsters) : weaponDTO.appropriateMonsters != null)
            return false;
        if (range != null ? !range.equals(weaponDTO.range) : weaponDTO.range != null) return false;
        return magazineCapacity != null ? magazineCapacity.equals(weaponDTO.magazineCapacity) : weaponDTO.magazineCapacity == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (appropriateMonsters != null ? appropriateMonsters.hashCode() : 0);
        result = 31 * result + (range != null ? range.hashCode() : 0);
        result = 31 * result + (magazineCapacity != null ? magazineCapacity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeaponDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", appropriateMonsters=" + appropriateMonsters +
                ", range=" + range +
                ", magazineCapacity=" + magazineCapacity +
                '}';
    }
}

package cz.tyckouni.poopio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cz.tyckouni.poopio.enums.WeaponType;

/**
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Entity
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated
    private WeaponType type;

    @ManyToMany()
    private Set<Monster> appropriateMonsters = new HashSet<>();

    private Integer range;

    private Integer magazineCapacity;

    public Weapon(String name) {
        checkName(name);

        this.name = name;
    }

    public Weapon() {
    }

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
        checkName(name);

        this.name = name;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public Set<Monster> getAppropriateMonsters() {
        return Collections.unmodifiableSet(appropriateMonsters);
    }

    public void addAppropriateMonster(Monster monster) {
        this.appropriateMonsters.add(monster);
        monster.addAppropriateWeapon(this);
    }

    public void removeAppropriateMonster(Monster monster) {
        this.appropriateMonsters.remove(monster);
        monster.removeAppropriateWeapon(this);
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
        if (o == null || !(o instanceof Weapon)) return false;

        Weapon weapon = (Weapon) o;

        if (!getName().equals(weapon.getName())) return false;
        if (getType() != null ? !getType().equals(weapon.getType()) : weapon.getType() != null) return false;
        if (getRange() != null ? !getRange().equals(weapon.getRange()) : weapon.getRange() != null) return false;
        return getMagazineCapacity() != null ? getMagazineCapacity().equals(weapon.getMagazineCapacity()) : weapon.getMagazineCapacity() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getType() != null ? getType().hashCode(): 0);
        result = 31 * result + (getRange() != null ? getRange().hashCode() : 0);
        result = 31 * result + (getMagazineCapacity() != null ? getMagazineCapacity().hashCode() : 0);
        return result;
    }

    private void checkName(String value) {
		if (value == null) {
            throw new IllegalArgumentException("Name can not be null.");
		}
		if (value.isEmpty()) {
			throw new IllegalArgumentException("Name can not be empty.");
		}
	}
}

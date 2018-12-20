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

import cz.tyckouni.poopio.enums.MonsterAgility;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@Entity
public class Monster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@Column(nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "monsters")
	private Set<Area> areas = new HashSet<>();

	@ManyToMany(mappedBy = "appropriateMonsters")
	private Set<Weapon> appropriateWeapons = new HashSet<>();

	private Double weight;

	private Double height;

	@Enumerated
	private MonsterAgility agility;

	public Monster(String name) {
		checkName(name);

		this.name = name;
	}

	public Monster() {
	}

	public Set<Weapon> getAppropriateWeapons() {
		return Collections.unmodifiableSet(appropriateWeapons);
	}

	public void addAppropriateWeapon(Weapon weapon) {
		this.appropriateWeapons.add(weapon);
	}

	public void removeAppropriateWeapon(Weapon weapon) {
		this.appropriateWeapons.remove(weapon);
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

	public Set<Area> getAreas() {
		return Collections.unmodifiableSet(areas);
	}

	public void addArea(Area area) {
		this.areas.add(area);
	}

	public void removeArea(Area area) {
		this.areas.remove(area);
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public MonsterAgility getAgility() {
		return agility;
	}

	public void setAgility(MonsterAgility agility) {
		this.agility = agility;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof Monster)) return false;

		Monster monster = (Monster) o;

		if (!getName().equals(monster.getName())) return false;
		if (getWeight() != null ? !getWeight().equals(monster.getWeight()) : monster.getWeight() != null) return false;
		return getHeight() != null ? getHeight().equals(monster.getHeight()) : monster.getHeight() == null;
	}

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + (getWeight() != null ? getWeight().hashCode() : 0);
		result = 31 * result + (getHeight() != null ? getHeight().hashCode() : 0);
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

	@Override
	public String toString() {
		return "Monster{" +
				"id=" + id +
				", name='" + name + '\'' +
				", areas=" + areas +
				", appropriateWeapons=" + appropriateWeapons +
				", weight=" + weight +
				", height=" + height +
				", agility=" + agility +
				'}';
	}
}

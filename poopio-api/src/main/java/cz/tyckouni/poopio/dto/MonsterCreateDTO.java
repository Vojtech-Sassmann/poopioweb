package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.enums.MonsterAgility;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class MonsterCreateDTO {
	private String name;
	private Double height;
	private Double weight;
	private MonsterAgility agility;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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
		if (o == null || getClass() != o.getClass()) return false;

		MonsterCreateDTO that = (MonsterCreateDTO) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (height != null ? !height.equals(that.height) : that.height != null) return false;
		if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
		return agility == that.agility;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (height != null ? height.hashCode() : 0);
		result = 31 * result + (weight != null ? weight.hashCode() : 0);
		result = 31 * result + (agility != null ? agility.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "MonsterCreateDTO{" +
				"name='" + name + '\'' +
				", height=" + height +
				", weight=" + weight +
				", agility=" + agility +
				'}';
	}
}

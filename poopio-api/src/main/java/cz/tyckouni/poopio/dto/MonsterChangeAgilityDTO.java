package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.MonsterAgility;
import cz.tyckouni.poopio.enums.MonsterAgility;

import javax.validation.constraints.NotNull;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class MonsterChangeAgilityDTO {

	private Long monsterId;

	@NotNull
	private MonsterAgility agility;

	public  MonsterChangeAgilityDTO() {}

	public MonsterChangeAgilityDTO(Long monsterId, MonsterAgility agility) {
		this.monsterId = monsterId;
		this.agility = agility;
	}

	public Long getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(Long monsterId) {
		this.monsterId = monsterId;
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

		MonsterChangeAgilityDTO that = (MonsterChangeAgilityDTO) o;

		if (monsterId != null ? !monsterId.equals(that.monsterId) : that.monsterId != null) return false;
		return agility == that.agility;
	}

	@Override
	public int hashCode() {
		int result = monsterId != null ? monsterId.hashCode() : 0;
		result = 31 * result + (agility != null ? agility.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "MonsterChangeAgilityDTO{" +
				"monsterId=" + monsterId +
				", agility=" + agility +
				'}';
	}
}

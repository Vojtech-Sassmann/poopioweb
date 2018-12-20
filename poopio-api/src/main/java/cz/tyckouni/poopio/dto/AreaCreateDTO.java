package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.enums.AreaType;

import java.util.HashSet;
import java.util.Set;

/**
 * AreaCreateDTO class
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public class AreaCreateDTO {

    private String name;
    private AreaType type;
    private Set<Long> monsterIds = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AreaType getType() {
        return type;
    }

    public void setType(AreaType type) {
        this.type = type;
    }

    public Set<Long> getMonsterIds() {
        return monsterIds;
    }

    public void addMonster(Long monsterId) {
        monsterIds.add(monsterId);
    }

    public void removeMonster(long monsterId) {
        monsterIds.remove(monsterId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof AreaDTO)) {
            return false;
        }

        AreaDTO areaDTO = (AreaDTO) o;

        if (name != null ? !name.equals(areaDTO.getName()) : areaDTO.getName() != null) {
            return false;
        }
        if (type != areaDTO.getType()) {
            return false;
        }
        return monsterIds != null ? !monsterIds.equals(areaDTO.getMonsters()) : areaDTO.getMonsters() != null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 83 * result + (type != null ? type.hashCode() : 0);
        result = 83 * result + (monsterIds != null ? monsterIds.hashCode() : 0);
        return result;
    }
}

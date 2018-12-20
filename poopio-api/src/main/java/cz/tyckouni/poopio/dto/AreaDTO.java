package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.enums.AreaType;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO class for Area entity
 *
 * @author Jan Gol <gol.honza@gmail.com>
 */
public class AreaDTO {

    private Long id;
    private String name;
    private AreaType type;
    private Set<MonsterDTO> monsters = new HashSet<>();

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

    public AreaType getType() {
        return type;
    }

    public void setType(AreaType type) {
        this.type = type;
    }

    public Set<MonsterDTO> getMonsters() {
        return monsters;
    }
    
    public void setMonsters(Set<MonsterDTO> monsters){
        this.monsters = monsters;
    }

    public void addMonster(MonsterDTO monsterDTO) {
        monsters.add(monsterDTO);
    }

    public void removeMonster(MonsterDTO monsterDTO) {
        monsters.remove(monsterDTO);
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

        if (id != null ? !id.equals(areaDTO.id) : areaDTO.id != null) {
            return false;
        }
        if (name != null ? !name.equals(areaDTO.name) : areaDTO.name != null) {
            return false;
        }
        if (type != areaDTO.type) {
            return false;
        }
        return !(monsters != null ? !monsters.equals(areaDTO.monsters) : areaDTO.monsters != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 83 * result + (name != null ? name.hashCode() : 0);
        result = 83 * result + (type != null ? type.hashCode() : 0);
        result = 83 * result + (monsters != null ? monsters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AreaDTO{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", type=" + type
                + ", monsters=" + monsters
                + '}';
    }
}

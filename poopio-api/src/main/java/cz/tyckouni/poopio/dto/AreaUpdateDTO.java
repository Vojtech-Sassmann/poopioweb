package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.AreaType;
import cz.tyckouni.poopio.enums.AreaType;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Jan GOl <gol.honza@gmail.com>
 */
public class AreaUpdateDTO {

    @NotNull

    private Long id;
    private String name;
    private AreaType type;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AreaUpdateDTO that = (AreaUpdateDTO) o;

        if (name != null ? !name.equals(that.getName()) : that.getName() != null) {
            return false;
        }

        
        return type != null ? type.equals(that.getType()) : that.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 83 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AreaDTO{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", type=" + type
                + '}';
    }
}

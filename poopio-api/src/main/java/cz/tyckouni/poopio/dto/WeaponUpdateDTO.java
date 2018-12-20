package cz.tyckouni.poopio.dto;

import cz.tyckouni.poopio.enums.WeaponType;
import cz.tyckouni.poopio.enums.WeaponType;

import javax.validation.constraints.NotNull;

/**
 * WeaponUpdateDTO class
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class WeaponUpdateDTO {

    @NotNull
    private Long id;

    private String name;
    private WeaponType type;
    private Integer range;
    private Integer magazineCapacity;

    public WeaponUpdateDTO() {
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
        this.name = name;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
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
        if (o == null || getClass() != o.getClass()) return false;

        WeaponUpdateDTO that = (WeaponUpdateDTO) o;

        if (!id.equals(that.id)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != that.type) return false;
        if (range != null ? !range.equals(that.range) : that.range != null) return false;
        return magazineCapacity != null ? magazineCapacity.equals(that.magazineCapacity) : that.magazineCapacity == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (range != null ? range.hashCode() : 0);
        result = 31 * result + (magazineCapacity != null ? magazineCapacity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeaponUpdateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", range=" + range +
                ", magazineCapacity=" + magazineCapacity +
                '}';
    }
}

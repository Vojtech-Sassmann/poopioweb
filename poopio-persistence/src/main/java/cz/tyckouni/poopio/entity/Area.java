package cz.tyckouni.poopio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import cz.tyckouni.poopio.enums.AreaType;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Jan Gol <gol.honza@gmail.com>
 */
@Entity

public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated
    private AreaType type;

    @ManyToMany()
    private Set<Monster> monsters = new HashSet<>();

    public Area() {
    }

    public Area(String name) {
        checkName(name);
        this.name = name;
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

    public AreaType getType() {
        return type;
    }

    public void setType(AreaType type) {
        this.type = type;
    }

    public Set<Monster> getMonsters() {
        return Collections.unmodifiableSet(monsters);
    }

    public void addMonster(Monster monster) {
        this.monsters.add(monster);
        monster.addArea(this);
    }

    public void removeMonster(Monster monster) {
        this.monsters.remove(monster);
        monster.removeArea(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.getName());
        hash = 83 * hash + Objects.hashCode(this.getType());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Area)) {
            return false;
        }
        final Area other = (Area) obj;
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (this.getType() != other.getType()) {
            return false;
        }
        return true;
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

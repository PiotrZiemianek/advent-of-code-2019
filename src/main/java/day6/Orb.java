package day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Orb {
    private String name;
    private String iOrbitingAround;
    private List<String> orbitAroundMe = new ArrayList<>();

    public Orb(String name, String itOrbitingAround) {
        this.name = name;
        this.iOrbitingAround = itOrbitingAround;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orb orb = (Orb) o;
        return Objects.equals(name, orb.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void addToOrbitAroundMe(String toAdd) {
        orbitAroundMe.add(toAdd);
    }

    public void setIOrbitingAround(String iOrbitingAround) {
        this.iOrbitingAround = iOrbitingAround;
    }

    public String getName() {
        return name;
    }

    public List<String> getOrbitAroundMe() {
        return orbitAroundMe;
    }

    public String getIOrbitingAround() {
        return iOrbitingAround;
    }

}

package day6;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.*;

public class Day6 {
    private static Path path = Path.of("src/main/resources/day6input.txt");
    private static List<String> input = DataReaderFromFileService.read(path).orElse(new ArrayList<>());

    public static void main(String[] args) {
        Map<String, Orb> orbs = new HashMap<>();
        int totalNumberOfOrbits = 0;
        fillOrbsMap(orbs);

        for (Orb orb : orbs.values()) {
            String iOrbitingAround = orb.getIOrbitingAround();
            while (iOrbitingAround != null) {
                totalNumberOfOrbits += 1;

                orb = orbs.get(iOrbitingAround);
                iOrbitingAround = orb.getIOrbitingAround();
            }
        }

        System.out.println(totalNumberOfOrbits);

        List<String> pathOfYOU = getPathOfOrb(orbs, orbs.get("YOU"));
        List<String> pathOfSAN = getPathOfOrb(orbs, orbs.get("SAN"));
        String commonElement = null;
        int youNumberOfOrbitsToCommonElement = 0;
        int sanNumberOfOrbitsToCommonElement = 0;
        for (String YOU : pathOfYOU) {
            if (pathOfSAN.contains(YOU)) {
                commonElement = YOU;
                break;
            } else {
                youNumberOfOrbitsToCommonElement += 1;
            }
        }
        for (String SAN : pathOfSAN) {
            if (SAN.equals(commonElement)) {
                break;
            } else {
                sanNumberOfOrbitsToCommonElement += 1;
            }
        }
        int manyOrbitalTransfers = sanNumberOfOrbitsToCommonElement + youNumberOfOrbitsToCommonElement;
        System.out.println(manyOrbitalTransfers);
    }

    private static List<String> getPathOfOrb(Map<String, Orb> orbs, Orb orb) {
        List<String> path = new ArrayList<>();
        String iOrbitingAround = orb.getIOrbitingAround();
        while (iOrbitingAround != null) {
            path.add(iOrbitingAround);

            orb = orbs.get(iOrbitingAround);
            iOrbitingAround = orb.getIOrbitingAround();
        }
        return path;
    }

    private static void fillOrbsMap(Map<String, Orb> orbs) {
        input.forEach(s -> {
            String[] split = s.split("\\)");
            String orbitCenterName = split[0];
            String orbitingOrbName = split[1];
            Orb orbitCenter;
            Orb orbitingOrb;
            if (orbs.containsKey(orbitCenterName)) {
                orbitCenter = orbs.get(orbitCenterName);
                orbitCenter.addToOrbitAroundMe(orbitingOrbName);
            } else {
                orbitCenter = new Orb(orbitCenterName, null);
                orbs.put(orbitCenterName, orbitCenter);
            }

            if (orbs.containsKey(orbitingOrbName)) {
                orbitingOrb = orbs.get(orbitingOrbName);
                orbitingOrb.setIOrbitingAround(orbitCenterName);
            } else {
                orbitingOrb = new Orb(orbitingOrbName, orbitCenterName);
                orbs.put(orbitingOrbName, orbitingOrb);
            }
        });
    }
}

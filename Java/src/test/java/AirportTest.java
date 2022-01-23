import planes.ExperimentalPlane;
import types.ClassificationLevel;
import types.ExperimentalType;
import types.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.Arrays;
import java.util.List;

public class AirportTest {
    private static final List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalType.VTOL, ClassificationLevel.TOP_SECRET)
    );
    private static final Airport airport = new Airport(planes);
    private static final PassengerPlane expectedPlaneWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    @Test
    public void testGetTransportMilitaryPlanes() {
        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlanes();
        boolean hasOnlyTransportMilitaryPlanes = hasPlanesWithOnlyOneMilitaryType(transportMilitaryPlanes, MilitaryType.TRANSPORT);
        Assert.assertTrue(hasOnlyTransportMilitaryPlanes);
    }

    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        PassengerPlane actualPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        Assert.assertEquals(actualPlaneWithMaxPassengersCapacity, expectedPlaneWithMaxPassengerCapacity);
    }

    @Test
    public void testSortByMaxLoadCapacity() {
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.sortByMaxLoadCapacity().getPlanes();
        boolean hasCorrectSortingByMaxLoadCapacity = hasCorrectSortingByMaxLoadCapacity(planesSortedByMaxLoadCapacity);
        Assert.assertTrue(hasCorrectSortingByMaxLoadCapacity);
    }

    @Test
    public void testGetBomberMilitaryPlanes() {
        List<MilitaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();
        boolean hasOnlyBomberMilitaryPlanes = hasPlanesWithOnlyOneMilitaryType(bomberMilitaryPlanes, MilitaryType.BOMBER);
        Assert.assertTrue(hasOnlyBomberMilitaryPlanes);
    }

    @Test
    public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified(){
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasOnlyPlanesWithClassificationLevelHigherThanUnclassified = doesntHaveUnclassifiedPlanes(experimentalPlanes);
        Assert.assertTrue(hasOnlyPlanesWithClassificationLevelHigherThanUnclassified);
    }

    private boolean hasPlanesWithOnlyOneMilitaryType(List<MilitaryPlane> militaryPlanes, MilitaryType militaryType) {
        for (MilitaryPlane plane : militaryPlanes) {
            if (plane.getType() != militaryType) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCorrectSortingByMaxLoadCapacity(List<? extends Plane> planesSortedByMaxLoadCapacity) {
        for (int i = 0; i < planesSortedByMaxLoadCapacity.size() - 1; i++) {
            int currentPlaneMaxLoadCapacity = planesSortedByMaxLoadCapacity.get(i).getMaxLoadCapacity();
            int nextPlaneMaxLoadCapacity = planesSortedByMaxLoadCapacity.get(i + 1).getMaxLoadCapacity();
            if (currentPlaneMaxLoadCapacity > nextPlaneMaxLoadCapacity) {
                return false;
            }
        }
        return true;
    }

    private boolean doesntHaveUnclassifiedPlanes(List <ExperimentalPlane> experimentalPlanes) {
        for (ExperimentalPlane experimentalPlane : experimentalPlanes) {
            if (experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED) {
                return false;
            }
        }
        return true;
    }
}

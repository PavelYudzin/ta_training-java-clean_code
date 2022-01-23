import planes.ExperimentalPlane;
import types.MilitaryType;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Airport {
    private final List<? extends Plane> planes;

    public Airport(List<? extends Plane> planes) {
        this.planes = planes;
    }

    public List<? extends Plane> getPlanes() {
        return planes;
    }

    public List<PassengerPlane> getPassengerPlanes() {
        List<PassengerPlane> passengerPlanes;
        passengerPlanes = planes.stream()
                .filter((p) -> p instanceof PassengerPlane)
                .map(p -> (PassengerPlane) p)
                .collect(Collectors.toList());
        return passengerPlanes;
    }

    public List<MilitaryPlane> getMilitaryPlanes() {
        List<MilitaryPlane> militaryPlanes;
        militaryPlanes = planes.stream()
                .filter(p -> p instanceof MilitaryPlane)
                .map(p -> (MilitaryPlane) p)
                .collect(Collectors.toList());
        return militaryPlanes;
    }

    public List<ExperimentalPlane> getExperimentalPlanes() {
        List<ExperimentalPlane> experimentalPlanes;
        experimentalPlanes = planes.stream()
                .filter(p -> p instanceof ExperimentalPlane)
                .map(p -> (ExperimentalPlane) p)
                .collect(Collectors.toList());
        return experimentalPlanes;
    }

    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() {
        List<PassengerPlane> passengerPlanes = getPassengerPlanes();
        PassengerPlane planeWithMaxCapacity = passengerPlanes.get(0);
        for (PassengerPlane plane : passengerPlanes) {
            if (plane.getPassengersCapacity() > planeWithMaxCapacity.getPassengersCapacity()) {
                planeWithMaxCapacity = plane;
            }
        }
        return planeWithMaxCapacity;
    }

    public List<MilitaryPlane> getTransportMilitaryPlanes() {
        return getMilitaryPlanesByType(MilitaryType.TRANSPORT);
    }

    public List<MilitaryPlane> getBomberMilitaryPlanes() {
        return getMilitaryPlanesByType(MilitaryType.BOMBER);
    }

    public Airport sortByMaxDistance() {
        planes.sort(Comparator.comparingInt(Plane::getMaxFlightDistance));
        return this;
    }

    public Airport sortByMaxSpeed() {
        planes.sort(Comparator.comparingInt(Plane::getMaxSpeed));
        return this;
    }

    public Airport sortByMaxLoadCapacity() {
        planes.sort(Comparator.comparingInt(Plane::getMaxLoadCapacity));
        return this;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "Planes=" + planes.toString() +
                '}';
    }

    private List<MilitaryPlane> getMilitaryPlanesByType(MilitaryType militaryType) {
        List<MilitaryPlane> militaryPlanes = getMilitaryPlanes();
        List<MilitaryPlane> militaryPlanesByType;
        militaryPlanesByType = militaryPlanes.stream()
                .filter(p -> p.getType() == militaryType)
                .collect(Collectors.toList());
        return militaryPlanesByType;
    }
}

package sensor.util;

/**
 * A utility class to map volt values to distances. In our case we assume 1 V ~
 * 0.5 m.
 * 
 *@author Martin Schneider
 */
public class VoltDistanceMapper {
    /**
     * @param volt
     * @return distance in meter
     */
    public double mapVoltToDistance(double volt) {
        return volt / 2;
    }
}

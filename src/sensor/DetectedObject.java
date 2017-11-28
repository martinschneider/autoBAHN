package sensor;

/**
 * Class for detected objects.
 * 
 * @author Martin Schneider
 */

public class DetectedObject
{
    private double distanceToSensor;

    public DetectedObject(double distanceToSensor)
    {
        this.distanceToSensor = distanceToSensor;
    }

    /**
     * sets the distance to the sensor
     * 
     * @param distanceToSensor
     *            distance
     */
    public void setDistanceToSensor(double distanceToSensor)
    {
        this.distanceToSensor = distanceToSensor;
    }

    /**
     * @return distance of object to the sensor
     */
    public double getDistanceToSensor()
    {
        return distanceToSensor;
    }
}
package sensor;

import java.awt.Point;
import java.util.Vector;

import sensor.util.BufferArray;

/**
 * Implements an ultrasonic sensor using a data buffer. This sensor can handle
 * only a single object and saves distance information about it in a buffer. The
 * distance is calculated as the mean of all values in the buffer.
 * 
 * @author Martin Schneider
 */
public class UltraSonicSensorWithBuffer extends UltraSonicSensor
{
    private BufferArray data = new BufferArray(1);

    public UltraSonicSensorWithBuffer(Point position, double angle,
            double direction, int range)
    {
        super(position, angle, direction, range);
    }

    public UltraSonicSensorWithBuffer(int x, int y, double angle,
            double direction, int range)
    {
        super(x, y, angle, direction, range);
    }

    public void setBufferSize(int bufferSize)
    {
        data = new BufferArray(bufferSize);
    }

    /*
     * (non-Javadoc)
     * @see sensor.Sensor#addObject(sensor.DetectedObject)
     */
    public void addObject(DetectedObject object)
    {
        // the ultrasonic-sensor only detects one object at a time, so we remove
        // all objects from the list (without notifying the observers) before
        // adding the new one
        data.add(object);
        setChanged();
        if (getClosestObject() * 100 < minDistance)
            state = state.objectInDangerZone();
        else
            state = state.noObjectInDangerZone();
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * @see sensor.Sensor#removeObjects()
     */
    public void removeObjects()
    {
        data.clear();
        setChanged();
        notifyObservers(new Double(getDistance()));
    }

    /*
     * (non-Javadoc)
     * @see sensor.Sensor#addObject(int)
     */
    public void addObject(double distance)
    {
        addObject(new DetectedObject(distance));
    }

    public Vector<DetectedObject> getObjects()
    {
        Vector<DetectedObject> v = new Vector<DetectedObject>();
        v.add(new DetectedObject(getDistance()));
        return v;
    }

    @Override
    public double getClosestObject()
    {
        return getDistance();
    }

    public double getDistance()
    {
        double result = 0;
        for (int i = 0; i < data.length; i++)
        {
            if (!(data.get(i) == null))
            {
                result += ((DetectedObject) data.get(i)).getDistanceToSensor();
            }
        }
        if (!(data.length == 0))
        {
            result /= data.length;
        }
        return result;
    }

}

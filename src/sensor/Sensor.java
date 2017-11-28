package sensor;

import java.awt.Point;
import java.util.Observable;
import java.util.Vector;

import sensor.state.State;

/**
 * Interface for sensors.
 * 
 * @author Martin Schneider
 */
public abstract class Sensor extends Observable {

    public abstract void setPosition(Point position);

    public abstract Point getPosition();

    public abstract void setAngle(double angle);

    public abstract double getAngle();

    public abstract int getX();

    public abstract int getY();

    public abstract void setRange(int range);

    public abstract int getRange();

    public abstract void setDirection(double direction);

    public abstract double getDirection();

    public abstract void addObject(DetectedObject object);

    public abstract void removeObjects();

    public abstract void addObject(double distance);

    public abstract Vector<DetectedObject> getObjects();

    public abstract double getClosestObject();

    /**
     * Sets the sensor's data source to a file (on the classpath).
     * 
     * @param filename
     *            The filename of the source file. It shall contain a number
     *            giving the distance to a detected object on each line.
     * @param step
     *            How many lines should be used. E.g. step=3 means, that only
     *            every third line is sent to the Sensor.
     * @param rate
     *            The sampling rate.
     * @param factor
     *            The factor to multiply the distance.
     */
    public abstract void setState(State state);

    public abstract State getState();

    public abstract double getMinDistance();

    public abstract void setMinDistance(double minDistance);
}
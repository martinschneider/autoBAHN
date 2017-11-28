package sensor;

import java.awt.Point;
import java.util.Vector;

import sensor.state.RedState;
import sensor.state.State;

/**
 * Implementation of an ultrasonic sensor.
 * 
 * <p>
 * This class represents an ultrasonic sensor, which can detect a single object
 * and give back its distance from the sensor.
 * </p>
 * 
 * <p>
 * It uses the observer pattern to keep listeners (like e.g. GUIs) informed.
 * </p>
 * 
 * @author Martin Schneider
 */
public class UltraSonicSensor extends Sensor {
    private Point position;
    private double angle;
    private int range;
    private double direction;
    private Vector<DetectedObject> objects = new Vector<DetectedObject>();
    protected double minDistance = 0;
    protected State state = new RedState();

    public UltraSonicSensor(Point position, double angle, double direction,
            int range) {
        this.position = position;
        this.angle = angle;
        this.range = range;
        this.direction = direction;
    }

    public UltraSonicSensor(int x, int y, double angle, double direction,
            int range) {
        position = new Point(x, y);
        this.angle = angle;
        this.range = range;
        this.direction = direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#setPosition(java.awt.Point)
     */
    public void setPosition(Point position) {
        this.position = position;
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getPosition()
     */
    public Point getPosition() {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#setAngle(double)
     */
    public void setAngle(double angle) {
        this.angle = angle;
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getAngle()
     */
    public double getAngle() {
        return angle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getX()
     */
    public int getX() {
        return (int) position.getX();
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getY()
     */
    public int getY() {
        return (int) position.getY();
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#setRange(int)
     */
    public void setRange(int range) {
        this.range = range;
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getRange()
     */
    public int getRange() {
        return range;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#setDirection(double)
     */
    public void setDirection(double direction) {
        this.direction = direction;
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getDirection()
     */
    public double getDirection() {
        return direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#addObject(sensor.DetectedObject)
     */
    public void addObject(DetectedObject object) {
     // the ultrasonic-sensor only detects one object at a time, so we remove
        // all objects from the list (without notifying the observers) before
        // adding the new one
        objects.clear();

        objects.add(object);
        if (getClosestObject()*100 < minDistance)
            state = state.objectInDangerZone();
        else
            state = state.noObjectInDangerZone();
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#removeObjects()
     */
    public void removeObjects() {
        objects.clear();
        setChanged();
        notifyObservers(new Double(getClosestObject()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#addObject(int)
     */
    public void addObject(double distance) {
        addObject(new DetectedObject(distance));
    }

    /*
     * (non-Javadoc)
     * 
     * @see sensor.Sensor#getObjects()
     */
    public Vector<DetectedObject> getObjects() {
        return objects;
    }

    @Override
    public double getClosestObject() {
        double min = -1;
        for (DetectedObject object : objects) {
            double distance = object.getDistanceToSensor();
            if (min == -1) {
                min = distance;
            } else {
                if (distance < min) {
                    min = distance;
                }
            }
        }
        return min;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMinDistance() {
        return minDistance;
    }
}

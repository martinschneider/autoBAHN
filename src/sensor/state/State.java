package sensor.state;

/**
 * Interface for states
 * 
 * @author Martin Schneider
 */
public interface State {

    public abstract State objectInDangerZone();

    public abstract State noObjectInDangerZone();
}
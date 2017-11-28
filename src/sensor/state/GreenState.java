package sensor.state;

/**
 * The "green" state (i.e. no object in danger zone).
 * 
 * @author Martin Schneider
 */
public class GreenState implements State {

    public GreenState() {
    }

    @Override
    public State objectInDangerZone() {
        return new OrangeState();
    }

    @Override
    public State noObjectInDangerZone() {
        return this;
    }
}

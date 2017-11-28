package sensor.state;

/**
 * The "orange" state (i.e. one measure of at least one object in the danger zone).
 * 
 * @author Martin Schneider
 */
public class OrangeState implements State
{

    public OrangeState()
    {
    }

    @Override
    public State objectInDangerZone()
    {
        return new RedState();
    }

    @Override
    public State noObjectInDangerZone()
    {
        return new GreenState();
    }
}

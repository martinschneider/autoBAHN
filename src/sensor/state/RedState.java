package sensor.state;

/**
 * The "red" state (i.e. two or more consecutive measurements showing at least one object
 * in the danger zone).
 * 
 * @author Martin Schneider
 */
public class RedState implements State
{

    public RedState()
    {
    }

    @Override
    public State objectInDangerZone()
    {
        return this;
    }

    @Override
    public State noObjectInDangerZone()
    {
        return new OrangeState();
    }
}

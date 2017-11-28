package sensor.state;

import java.awt.Color;

/**
 * A utility class to map states to colors.
 * 
 * @author Martin Schneider
 */
public class StateColorMapper {
    public static Color mapStateToColor(State state) throws Exception {
        if (state instanceof RedState)
            return Color.red;
        else if (state instanceof OrangeState)
            return Color.orange;
        else if (state instanceof GreenState)
            return Color.green;
        else
            throw new Exception("There is no color mapping for state " + state
                    + ".");
    }
}

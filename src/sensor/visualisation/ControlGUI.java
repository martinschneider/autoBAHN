package sensor.visualisation;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextField;

import sensor.Sensor;

public class ControlGUI extends JFrame
{
    private Vector<Sensor> sensors;
    
    public ControlGUI(Vector<Sensor> sensors){
        super();
        this.sensors=sensors;
        add(new JTextField("Position: "));
        add(new JTextField("Winkel: "));
        add(new JTextField("Reichweite: "));
        setSize(200,200);
        setVisible(true);
    }
}

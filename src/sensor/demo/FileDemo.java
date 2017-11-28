package sensor.demo;

import java.util.Vector;

import sensor.Sensor;
import sensor.UltraSonicSensor;
import sensor.acquisation.FileInterface;
import sensor.acquisation.SensorInterface;
import sensor.visualisation.SensorVisualisation;

/**
 * Demonstration for the sensor GUI. Uses data from text file.
 * 
 * @author Martin Schneider
 */
public class FileDemo
{

    /**
     * @param args
     *            first argument is used as full path to nidaq.dll
     */
    public static void main(String[] args)
    {

        Sensor sensor1 = new UltraSonicSensor(30, 60, Math.PI / 8, 0,
                600);
        sensor1.setMinDistance(150);

        /*
         * read data from sensor via NI USB 6008 on Dev1/ai0, two samples per
         * second
         */
        SensorInterface interface1 = new FileInterface(sensor1, "sampledata_001.txt");
        
        Vector<Sensor> sensors = new Vector<Sensor>();
        sensors.add(sensor1);

        new SensorVisualisation(sensors, 660, 150);
        interface1.start();
    }

}

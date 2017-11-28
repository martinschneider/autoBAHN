package sensor.demo;

import java.util.Vector;

import sensor.Sensor;
import sensor.UltraSonicSensorWithBuffer;
import sensor.acquisation.NationalInstrumentsInterface;
import sensor.visualisation.SensorVisualisation;

/**
 * Demonstration for the sensor GUI. usage: java Demo path where path is the
 * fully qualified path to the nidaq.dll, e.g.: c://nidaq//sensor.dll
 * 
 * @author Martin Schneider
 */
public class NIDemo
{

    /**
     * @param args
     *            first argument is used as full path to nidaq.dll
     */
    public static void main(String[] args)
    {

        UltraSonicSensorWithBuffer sensor1 = new UltraSonicSensorWithBuffer(30, 60, Math.PI / 8, 0, 600);
        sensor1.setBufferSize(5);
        sensor1.setMinDistance(150);

        /*
         * read data from sensor via NI USB 6008 on Dev1/ai0, two samples per
         * second
         */
        NationalInstrumentsInterface interface1 = new NationalInstrumentsInterface(
                sensor1, "Dev1/ai0", 30);
        if (args.length > 0)
        {
            interface1.setPathToDLL(args[0]);
        }

        Vector<Sensor> sensors = new Vector<Sensor>();
        sensors.add(sensor1);

        new SensorVisualisation(sensors, 660, 150);
        interface1.start();
    }

}

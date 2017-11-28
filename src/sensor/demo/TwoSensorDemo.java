package sensor.demo;

import java.util.Vector;

import sensor.Sensor;
import sensor.UltraSonicSensor;
import sensor.UltraSonicSensorWithBuffer;
import sensor.acquisation.FileInterface;
import sensor.acquisation.NationalInstrumentsInterface;
import sensor.acquisation.SensorInterface;
import sensor.visualisation.SensorVisualisation;

public class TwoSensorDemo
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
        
        
        UltraSonicSensorWithBuffer sensor2 = new UltraSonicSensorWithBuffer(30, 150, Math.PI / 8, 0, 600);
        sensor2.setBufferSize(5);
        sensor2.setMinDistance(150);

        /*
         * read data from sensor via NI USB 6008 on Dev1/ai0, two samples per
         * second
         */
        NationalInstrumentsInterface interface2 = new NationalInstrumentsInterface(
                sensor2, "Dev1/ai0", 30);
        Vector<Sensor> sensors = new Vector<Sensor>();
        sensors.add(sensor1);
        sensors.add(sensor2);

        new SensorVisualisation(sensors, 660, 250);
        interface1.start();
        interface2.start();
    }

}

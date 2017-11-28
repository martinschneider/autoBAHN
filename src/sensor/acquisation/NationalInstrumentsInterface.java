package sensor.acquisation;

import java.text.DecimalFormat;
import java.text.Format;

import sensor.Sensor;
import sensor.util.VoltDistanceMapper;
import ch.aplu.jaw.NativeHandler;

/**
 * Interface between "real" sensor and sensor objects. It uses native sensor.dll
 * to communicate with National Instruments device.
 * 
 * @author Martin Schneider
 */
public class NationalInstrumentsInterface extends Thread implements
        SensorInterface {

    /* Native variables */
    private String channel;
    @SuppressWarnings("unused")
    private double min = -10;
    @SuppressWarnings("unused")
    private double max = 10;
    private double value;
    @SuppressWarnings("unused")
    private String errorDescription = "";
    @SuppressWarnings("unused")
    private int period;

    private final VoltDistanceMapper mapper = new VoltDistanceMapper();
    private NativeHandler handler;
    private Sensor sensor;
    private boolean running = true;
    private Format formatter = new DecimalFormat("0.000");
    private String pathToDLL = "c://nidaq//sensor.dll";

    public NationalInstrumentsInterface(Sensor sensor, String channel,
            int sampleRate) {
        this.sensor = sensor;
        period = 1000 / sampleRate;
        this.channel = channel;
        handler = new NativeHandler(pathToDLL, this);
    }

    public void run() {
        System.out.print("Creating task...");
        if (handler.invoke(0) == -1) {
            return;
        }
        System.out.println("OK");

        System.out.print("Open channel...");
        if (handler.invoke(1) == -1) {
            return;
        }
        System.out.println("OK");

        System.out.print("Start task...");
        if (handler.invoke(2) == -1) {
            return;
        }
        System.out.println("OK");

        System.out.println("\nGetting data...");
        int nb = 1;
        while (isRunning()) {
            if (handler.invoke(3) == -1)
                break;
            else {
                double distance = mapper.mapVoltToDistance(value);
                System.out.println("Data #" + nb + ": " + formatter.format(distance) + " m");
                sensor.addObject(distance);
                handler.invoke(4);
                nb++;
            }
        }
    }

    public void kill() {
        running = false;
        System.out.println("Data acquisation from " + channel + " stopped.");
        handler.destroy();
    }

    public String getChannel() {
        return channel;
    }

    public boolean isRunning() {
        return running;
    }
    
    public void setPathToDLL(String pathToDLL){
        this.pathToDLL=pathToDLL;
    }
}

package sensor.acquisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sensor.Sensor;
import sensor.util.VoltDistanceMapper;

/**
 * Interface between saved sensor data from text file and sensor objects.
 * 
 * @author Martin Schneider
 */
public class FileInterface extends Thread implements SensorInterface {
    private Sensor sensor;
    private String file;

    /* time to wait between two samples */
    private final int period = 250;

    /*
     * number of samples to skip (can be used for files recorded with a higher
     * sampling rate than wished for playback)
     */
    private final int step = 250;
    private final VoltDistanceMapper mapper = new VoltDistanceMapper();

    public FileInterface(Sensor sensor, String file) {
        this.sensor = sensor;
        this.file = file;
    }

    public void run() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                file);
        BufferedReader data = new BufferedReader(new InputStreamReader(in));

        try {
            String line;
            while ((line = data.readLine()) != null) {
                sensor.addObject(mapper.mapVoltToDistance(new Double(line)));
                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 1; i < step; i++) {
                    // read a line and dismiss its value
                    data.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package sensor.visualisation;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import sensor.DetectedObject;
import sensor.Sensor;
import sensor.state.StateColorMapper;

/**
 * <p>
 * GUI to visualize one or multiple sensors.
 * </p>
 * 
 * @author Martin Schneider
 */
public class SensorVisualisation extends Frame
{

    private static final long serialVersionUID = 1L;
    private SensorCanvas canvas;

    public SensorVisualisation(final Vector<Sensor> sensors, int width, int height)
    {
        setTitle("autoBAHN - Ultraschallsensoren");
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
            }

            public void keyReleased(KeyEvent e)
            {
                // on pressing of space key, pause the playback
                if (e.getKeyCode() == 32){
                    canvas.setPause(!canvas.getPause());
                }
            }

            public void keyTyped(KeyEvent e)
            {
            }
        });        canvas = new SensorCanvas(sensors);
        add(canvas, BorderLayout.CENTER);
        new ControlGUI(sensors);
        setSize(width, height);
        setVisible(true);
    }

}

class SensorCanvas extends Canvas implements Observer
{

    private static final long serialVersionUID = 1L;
    private boolean pause = false;
    private final int sensorWidth = 20;
    private final int sensorHeight = 20;
    private final int verticalSensorRange = 30;
    private final Color COLOR_LINES = Color.BLACK;

    private Vector<Sensor> sensors;

    public SensorCanvas(final Vector<Sensor> sensors)
    {
        for (Sensor sensor : sensors)
        {
            sensor.addObserver(this);
        }
        this.sensors = sensors;
    }

    public boolean getPause()
    {
        return pause;
    }

    public void setPause(boolean pause)
    {
        this.pause = pause;
    }

    @Override
    public void update(Observable arg0, Object arg1)
    {
        repaint();
    }

    public void paint(Graphics g)
    {
        update(g);
    }

    public void update(Graphics g)
    {
        if (!pause)
        {
            Graphics2D g2 = (Graphics2D) g;

            BufferedImage bi = (BufferedImage) createImage(this.getWidth(),
                    this.getHeight());
            Graphics2D big = bi.createGraphics();

            // draw sensor & objects
            for (Sensor sensor : sensors)
            {
                double x = sensor.getX();
                double y = sensor.getY();
                double theta = sensor.getDirection();

                // the sensor
                Rectangle2D rect = new Rectangle2D.Double(x - sensorWidth / 2,
                        y - sensorHeight / 2, sensorWidth, sensorHeight);

                // a line representing the direction the sensor looks to
                Line2D sensorLine = new Line2D.Double(x + rect.getWidth() / 2,
                        y, x + rect.getWidth() / 2 + sensor.getRange(), y);

                // an AffineTransform to rotate the sensor and associated
                // objects
                AffineTransform at = AffineTransform.getRotateInstance(theta,
                        rect.getX() + rect.getWidth(), rect.getY()
                                + rect.getHeight());

                big.setColor(COLOR_LINES);
                big.fill(at.createTransformedShape(rect));
                big.draw(at.createTransformedShape(sensorLine));

                // draw the grid of the sensor (a line each 50 cm and a thicker
                // line
                // each 1 m)
                Line2D gridLine = new Line2D.Double(sensor.getX() + sensorWidth
                        / 2, sensor.getY() - verticalSensorRange, sensor.getX()
                        + sensorWidth / 2, sensor.getY() + verticalSensorRange);
                AffineTransform atGrid = AffineTransform.getTranslateInstance(
                        0, 0);
                for (int i = 0; i <= sensor.getRange(); i += 50)
                {
                    atGrid = AffineTransform.getTranslateInstance(
                            new Double(i), 0);
                    Stroke wideStroke = new BasicStroke(4.0f);
                    if (i % 100 == 0 && i != 0)
                        big.setStroke(wideStroke);
                    big.draw(at.createTransformedShape(atGrid
                            .createTransformedShape(gridLine)));
                    big.setStroke(new BasicStroke());
                }

                // lines to border the area the sensor "overlooks"
                AffineTransform atTop = AffineTransform.getTranslateInstance(0,
                        verticalSensorRange);
                AffineTransform atBottom = AffineTransform
                        .getTranslateInstance(0, -verticalSensorRange);

                big.draw(at.createTransformedShape(atTop
                        .createTransformedShape(sensorLine)));
                big.draw(at.createTransformedShape(atBottom
                        .createTransformedShape(sensorLine)));

                // mark each detected object with a line
                for (DetectedObject obj : sensor.getObjects())
                {
                    double dist = obj.getDistanceToSensor() * 100;
                    if (dist > 0)
                    {
                        Line2D objectLine = new Line2D.Double(sensor.getX()
                                + sensorWidth / 2, sensor.getY()
                                - verticalSensorRange, sensor.getX()
                                + sensorWidth / 2, sensor.getY()
                                + verticalSensorRange);
                        AffineTransform atDist = AffineTransform
                                .getTranslateInstance(dist, 0);
                        try
                        {
                            big.setColor(StateColorMapper
                                    .mapStateToColor(sensor.getState()));
                        }
                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                        Stroke wideStroke = new BasicStroke(8.0f);
                        big.setStroke(wideStroke);
                        big.draw(at.createTransformedShape(atDist
                                .createTransformedShape(objectLine)));
                    }
                }
                big.setColor(COLOR_LINES);
                big.setStroke(new BasicStroke());
            }
            g2.drawImage(bi, 0, 0, this);
        }
    }
}
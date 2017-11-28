package sensor.util;

/**
 * Implements a simple buffer. It keeps a given number of objects in an array.
 * When the capacity is full, the oldest entries are overwritten.
 * 
 * @author Martin Schneider
 */
public class BufferArray
{
    private Object[] data;
    private int position = 0;
    public int length;

    public BufferArray(int length)
    {
        this.length = length;
        data = new Object[length];
    }

    public void add(Object o)
    {
        if (position < length)
        {
            data[position] = o;
            position++;
        }
        else
        {
            position = 0;
            add(o);
        }
    }

    public Object[] getData()
    {
        return data;
    }

    public Object get(int position)
    {
        return data[position];
    }

    public String toString()
    {
        String str = "";
        for (int i = 0; i < length; i++)
        {
            str += i + ": " + data[i] + "\n";
        }
        return str;
    }

    public void clear()
    {
        data = new Object[length];
    }
}
/*
 Title:        modifiable_float
 Description:  A modifiable float wrapper.
 */

package script;

public class modifiable_float extends Number implements Comparable
{
	private float m_data;


	/**
	 * Class default constructor.
	 */
	public modifiable_float()
	{
		m_data = 0.0f;
	}	// modifiable_float()

	/**
	 * Class constructor.
	 *
	 * @param data		the value of the class
	 */
	public modifiable_float(float data)
	{
		m_data = data;
	}	// modifiable_float(float)

	/**
	 * Clone function.
	 *
	 * @return a copy of this object
	 */
	public Object clone()
	{
		return new modifiable_float(m_data);
	}	// clone

	/**
	 * Changes the value of the class.
	 *
	 * @param data		the new value of the class
	 */
	public void set(float data)
	{
		m_data = data;
	}	// set(float)

	/**
	 * Changes the value of the class.
	 *
	 * @param data		the new value of the class
	 */
	public void set(modifiable_float data)
	{
		m_data = data.m_data;
	}	// set(modifiable_float)

	/**
	 * Returns the class value as it's default type.
	 *
	 * @return the class value.
	 */
	public float value()
	{
		return m_data;
	}	// value

	/**
	 * Returns the class value as a double.
	 *
	 * @return the class value.
	 */
	public double doubleValue()
	{
		return m_data;
	}	// doubleValue

	/**
	 * Returns the class value as a float.
	 *
	 * @return the class value.
	 */
    public float floatValue()
	{
		return m_data;
	}	// floatValue

	/**
	 * Returns the class value as an int.
	 *
	 * @return the class value.
	 */
    public int intValue()
	{
		return (int)m_data;
	}	// intValue

	/**
	 * Returns the class value as a long.
	 *
	 * @return the class value.
	 */
    public long longValue()
	{
		return (long)m_data;
	}	// longValue

	/**
	 * Conversion function.
	 *
	 * @return the id as a string.
	 */
	public String toString()
	{
		return Float.valueOf(m_data).toString();
	}	// toString

	/**
	 * Compares this to a generic object.
	 *
	 * @returns <, =, or > 0 if the object is a modifiable_float, else throws
	 *		ClassCastException
	 */
	public int compareTo(Object o) throws ClassCastException
	{
		return compareTo((modifiable_float)o);
	}	// compareTo(Object)

	/**
	 * Compares this to another string_id.
	 *
	 * @returns <, =, or > 0
	 */
	public int compareTo(modifiable_float src)
	{
        return Float.compare(m_data, src.m_data);
	}	// compareTo(modifiable_float)

	/**
	 * Compares this to a generic object.
	 *
	 * @returns true if the objects have the same data, false if not
	 */
	public boolean equals(Object o)
	{
		return (compareTo(o) == 0);
	}	// equals

}  // modifiable_float

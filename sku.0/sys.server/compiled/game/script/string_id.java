/*
 Title:        string_id
 Description:  Wrapper for a string table id.
 */

package script;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class string_id implements Comparable, Serializable
{
	private final static long serialVersionUID = -1331982663286942264L;

	// A string_id is represented by a table name, and either an ascii text id
	// or an integer index id. The ascii text id is the default id to use; the
	// index id will only be used if the ascii id is 0 length or null
	//
	protected           String m_table   = "";		// table/file of string id
	protected transient String m_asciiId = "";	// english ascii text for string id
	protected transient int    m_indexId = -1;		// index for string id

	public string_id ()
	{
	}

	/**
	 * Class constructor.
	 *
	 * @param table		the string table
	 * @param id		the string table id
	 */
	public string_id(String table, String id)
	{
		if (table != null)
			m_table = table.toLowerCase();
		else
			m_table = "";
		if (id != null)
			m_asciiId = id.toLowerCase();
		else
			m_asciiId = "";
	}	// string_id

	/**
	 * Class constructor.
	 *
	 * @param table		the string table
	 * @param id		the string table id
	 */
	public string_id(String table, int id)
	{
		if (table != null)
			m_table = table.toLowerCase();
		else
			m_table = "";
		m_indexId = id;
	}	// string_id

	/**
	 * Copy constructor.
	 *
	 * @param src		class instance to copy
	 */
	public string_id(string_id src)
	{
		m_table = src.m_table;
		m_indexId = src.m_indexId;
		m_asciiId = src.m_asciiId;
	}	// string_id(string_id)
	/**
	 * Overloaded version of string_id constructor that uses a dummy string not a localized one
	 * Allows forcing of inline-defined Strings in lieu of creating strings in .STF where a string_id is expected by the code
	 * Can be used directly as a string_id or pushed into a prose_package
	 * This is predominately intended for use in admin/debug scenarios where adding a real string does not make sense
	 * However, this seemingly works perfectly fine for production use provided that you never intend to localize your client in another language
	 * Good for radial menu items, comm messages, and all those other annoying functions that want a string_id
	 * You should NOT use this for conversations, though, as conversation logic relies on STF indexing
	 *
	 * Example Usage: string_id message = new string_id("a message you don't want to put into a .STF here");
	 *
	 * @param dummyText String text to display in the client
	 */
	public string_id(String dummyText)
	{
		m_table = "dummy_string_table"; // client relies on this name to not parse as a string_id
		m_asciiId = dummyText;
	} // string_id(dummyText)
	/**
	 * Accessor function.
	 *
	 * @return the string table
	 */
	public String getTable()
	{
		return m_table;
	}	// getTable

	/**
	 * Accessor function.
	 *
	 * @return the ascii id
	 */
	public String getAsciiId()
	{
		return m_asciiId;
	}	// getStringId

	/**
	 * Accessor function.
	 *
	 * @return the index id
	 */
	public int getIndexId()
	{
		return m_indexId;
	}	// getIndexId

	/**
	*
	*/
	public boolean isValid ()
	{
		return m_table != null && m_table.length () > 0 && m_asciiId != null && m_asciiId.length () > 0;
	}

	/**
	*
	*/
	public boolean isEmpty ()
	{
		return (m_table == null || m_table.length () == 0) && (m_asciiId == null || m_asciiId.length () == 0);
	}

	/**
	 * Conversion function.
	 *
	 * @return the id as a string.
	 */
	public String toString()
	{
		StringBuilder sbuf = new StringBuilder(m_table);
		sbuf.append (':');

		if (m_asciiId != null)
			sbuf.append (m_asciiId);
		else
			sbuf.append (m_indexId);

		return sbuf.toString ();
	}

	/**
	 * Compares this to a generic object.
	 *
	 * @returns <, =, or > 0 if the object is a string_id, else throws
	 *		ClassCastException
	 */
	public int compareTo(Object o) throws ClassCastException
	{
		return compareTo((string_id)o);
	}	// compareTo(Object)

	/**
	 * Compares this to another string_id.
	 *
	 * @returns <, =, or > 0
	 */
	public int compareTo(string_id id)
	{
		int result = m_table.compareTo(id.m_table);
		if ( result == 0 )
		{
			if ( m_asciiId != null && m_asciiId.length() != 0 )
				result = m_asciiId.compareTo(id.m_asciiId);
			else
				result = m_indexId - id.m_indexId;
		}
		return result;
	}	// compareTo(string_id)

	/**
	 * Compares this to a generic object.
	 *
	 * @returns true if the objects have the same data, false if not
	 */
	public boolean equals(String o){
		return m_asciiId.equals(o);
	}
	public boolean equals(string_id o){
		if ( m_table.equals(o.m_table))
		{
			if (m_asciiId != null && m_asciiId.length() != 0)
			{
				if (m_asciiId.equals(o.m_asciiId)) return true;
			}
			else if (m_indexId == o.m_indexId )
				return true;
		}
		return false;
	}
	// equals

	/**
	 * \defgroup serialize Serialize support functions
	 * @{ */

	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
		if (m_asciiId.length() > 0)
		{
			out.writeBoolean(true);
			out.writeObject(m_asciiId);
		}
		else
		{
			out.writeBoolean(false);
			out.writeInt(m_indexId);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		boolean isTextId = in.readBoolean();
		if (isTextId)
		{
			m_asciiId = (String)in.readObject();
		}
		else
		{
			m_indexId = in.readInt();
		}
	}

	/*@}*/

}	// class string_id

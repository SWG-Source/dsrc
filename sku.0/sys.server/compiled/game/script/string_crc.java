// string_crc.java

package script;

// ======================================================================

public final class string_crc implements crc
{
	public static int getStringCrc(String s)
	{
		int crc = CRC_INIT;
		if (s != null)
		{
			char[] c = s.toCharArray();
            for (char c1 : c) crc = crctable[((crc >> 24) ^ (c1 & 0xff)) & 0xff] ^ (crc << 8);
		}
		return crc ^ CRC_INIT;
	}
}

// ======================================================================


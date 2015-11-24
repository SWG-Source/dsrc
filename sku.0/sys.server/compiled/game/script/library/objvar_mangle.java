package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class objvar_mangle extends script.base_script
{
    public objvar_mangle()
    {
    }
    public static final int INTS_PER_SEGMENT = 80;
    public static final int FLOATS_PER_SEGMENT = 40;
    public static final int STRINGS_PER_SEGMENT = 10;
    public static final int OBJIDS_PER_SEGMENT = 40;
    public static int[] getMangledIntArrayObjVar(obj_id self, String varName) throws InterruptedException
    {
        if (hasObjVar(self, varName))
        {
            return getIntArrayObjVar(self, varName);
        }
        int count = getIntObjVar(self, varName + "_mangled.count");
        if (count <= 0)
        {
            return null;
        }
        int[] values = new int[count];
        int segment = 0;
        int valuePos = 0;
        while (valuePos < count)
        {
            int[] segmentValues = getIntArrayObjVar(self, varName + "_mangled.segment." + segment);
            if (segmentValues == null)
            {
                break;
            }
            ++segment;
            for (int i = 0; i < segmentValues.length && valuePos < count; ++i)
            {
                values[valuePos++] = segmentValues[i];
            }
        }
        if (valuePos != count)
        {
            return null;
        }
        return values;
    }
    public static void setMangledIntArrayObjVar(obj_id self, String varName, int[] values) throws InterruptedException
    {
        removeObjVar(self, varName);
        removeObjVar(self, varName + "_mangled.segment");
        setObjVar(self, varName + "_mangled.count", values.length);
        int pos, segment;
        for (pos = 0, segment = 0; pos < values.length; pos += INTS_PER_SEGMENT, ++segment)
        {
            int segmentLength = values.length - pos;
            if (segmentLength > INTS_PER_SEGMENT)
            {
                segmentLength = INTS_PER_SEGMENT;
            }
            int[] segmentValues = new int[segmentLength];
            for (int i = 0; i < segmentLength; ++i)
            {
                segmentValues[i] = values[pos + i];
            }
            if (segmentValues.length > 0)
            {
                setObjVar(self, varName + "_mangled.segment." + segment, segmentValues);
            }
        }
    }
    public static float[] getMangledFloatArrayObjVar(obj_id self, String varName) throws InterruptedException
    {
        if (hasObjVar(self, varName))
        {
            return getFloatArrayObjVar(self, varName);
        }
        int count = getIntObjVar(self, varName + "_mangled.count");
        if (count <= 0)
        {
            return null;
        }
        float[] values = new float[count];
        int segment = 0;
        int valuePos = 0;
        while (valuePos < count)
        {
            float[] segmentValues = getFloatArrayObjVar(self, varName + "_mangled.segment." + segment);
            if (segmentValues == null)
            {
                break;
            }
            ++segment;
            for (int i = 0; i < segmentValues.length && valuePos < count; ++i)
            {
                values[valuePos++] = segmentValues[i];
            }
        }
        if (valuePos != count)
        {
            return null;
        }
        return values;
    }
    public static void setMangledFloatArrayObjVar(obj_id self, String varName, float[] values) throws InterruptedException
    {
        removeObjVar(self, varName);
        removeObjVar(self, varName + "_mangled.segment");
        setObjVar(self, varName + "_mangled.count", values.length);
        int pos, segment;
        for (pos = 0, segment = 0; pos < values.length; pos += FLOATS_PER_SEGMENT, ++segment)
        {
            int segmentLength = values.length - pos;
            if (segmentLength > FLOATS_PER_SEGMENT)
            {
                segmentLength = FLOATS_PER_SEGMENT;
            }
            float[] segmentValues = new float[segmentLength];
            for (int i = 0; i < segmentLength; ++i)
            {
                segmentValues[i] = values[pos + i];
            }
            if (segmentValues.length > 0)
            {
                setObjVar(self, varName + "_mangled.segment." + segment, segmentValues);
            }
        }
    }
    public static String[] getMangledStringArrayObjVar(obj_id self, String varName) throws InterruptedException
    {
        if (hasObjVar(self, varName))
        {
            return getStringArrayObjVar(self, varName);
        }
        int count = getIntObjVar(self, varName + "_mangled.count");
        if (count <= 0)
        {
            return null;
        }
        String[] values = new String[count];
        int segment = 0;
        int valuePos = 0;
        while (valuePos < count)
        {
            String[] segmentValues = getStringArrayObjVar(self, varName + "_mangled.segment." + segment);
            if (segmentValues == null)
            {
                break;
            }
            ++segment;
            for (int i = 0; i < segmentValues.length && valuePos < count; ++i)
            {
                values[valuePos++] = segmentValues[i];
            }
        }
        if (valuePos != count)
        {
            return null;
        }
        return values;
    }
    public static String[] getBrokenMangledStringArrayObjVar(obj_id self, String varName) throws InterruptedException
    {
        if (hasObjVar(self, varName))
        {
            return getStringArrayObjVar(self, varName);
        }
        int count = getIntObjVar(self, varName + "_mangled.count");
        if (count <= 0)
        {
            return null;
        }
        String[] values = new String[count];
        int segment = 0;
        int valuePos = 0;
        while (valuePos < count)
        {
            String[] segmentValues = getStringArrayObjVar(self, varName + "_mangled.segment." + segment);
            if (segmentValues == null)
            {
                break;
            }
            ++segment;
            for (int i = 0; i < segmentValues.length && valuePos < count; ++i)
            {
                values[valuePos++] = segmentValues[i];
            }
        }
        if (valuePos != count)
        {
            String[] strNewStrings = new String[valuePos];
            for (int intI = 0; intI < valuePos; intI++)
            {
                strNewStrings[intI] = values[intI];
            }
            return strNewStrings;
        }
        return values;
    }
    public static void setMangledStringArrayObjVar(obj_id self, String varName, String[] values) throws InterruptedException
    {
        removeObjVar(self, varName);
        removeObjVar(self, varName + "_mangled.segment");
        setObjVar(self, varName + "_mangled.count", values.length);
        int pos, segment;
        for (pos = 0, segment = 0; pos < values.length; pos += STRINGS_PER_SEGMENT, ++segment)
        {
            int segmentLength = values.length - pos;
            if (segmentLength > STRINGS_PER_SEGMENT)
            {
                segmentLength = STRINGS_PER_SEGMENT;
            }
            String[] segmentValues = new String[segmentLength];
            for (int i = 0; i < segmentLength; ++i)
            {
                segmentValues[i] = values[pos + i];
            }
            if (segmentValues.length > 0)
            {
                setObjVar(self, varName + "_mangled.segment." + segment, segmentValues);
            }
        }
    }
    public static obj_id[] getMangledObjIdArrayObjVar(obj_id self, String varName) throws InterruptedException
    {
        if (hasObjVar(self, varName))
        {
            return getObjIdArrayObjVar(self, varName);
        }
        int count = getIntObjVar(self, varName + "_mangled.count");
        if (count <= 0)
        {
            return null;
        }
        obj_id[] values = new obj_id[count];
        int segment = 0;
        int valuePos = 0;
        while (valuePos < count)
        {
            obj_id[] segmentValues = getObjIdArrayObjVar(self, varName + "_mangled.segment." + segment);
            if (segmentValues == null)
            {
                break;
            }
            ++segment;
            for (int i = 0; i < segmentValues.length && valuePos < count; ++i)
            {
                values[valuePos++] = segmentValues[i];
            }
        }
        if (valuePos != count)
        {
            return null;
        }
        return values;
    }
    public static void setMangledObjIdArrayObjVar(obj_id self, String varName, obj_id[] values) throws InterruptedException
    {
        removeObjVar(self, varName);
        removeObjVar(self, varName + "_mangled.segment");
        setObjVar(self, varName + "_mangled.count", values.length);
        int pos, segment;
        for (pos = 0, segment = 0; pos < values.length; pos += OBJIDS_PER_SEGMENT, ++segment)
        {
            int segmentLength = values.length - pos;
            if (segmentLength > OBJIDS_PER_SEGMENT)
            {
                segmentLength = OBJIDS_PER_SEGMENT;
            }
            obj_id[] segmentValues = new obj_id[segmentLength];
            for (int i = 0; i < segmentLength; ++i)
            {
                segmentValues[i] = values[pos + i];
            }
            if (segmentValues.length > 0)
            {
                setObjVar(self, varName + "_mangled.segment." + segment, segmentValues);
            }
        }
    }
}

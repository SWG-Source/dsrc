package script.developer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileWriter;

public class file_access extends script.base_script
{
    public file_access()
    {
    }
    public static String readTextFile(String fileName) throws InterruptedException
    {
        String result = null;
        File f = new File(fileName);
        if (f.exists())
        {
            long len = f.length();
            if (len > 0)
            {
                try
                {
                    FileInputStream inputStream = new FileInputStream(f);
                    byte[] buf = new byte[(int)len];
                    inputStream.read(buf);
                    result = new String(buf);
                }
                catch(Exception e)
                {
                    obj_id self = getSelf();
                    sendSystemMessageTestingOnly(self, "An exception occurred while trying to read " + fileName);
                }
            }
        }
        return result;
    }
    public static boolean isWritable(String fileName) throws InterruptedException
    {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists())
        {
            if (f.canWrite())
            {
                result = true;
            }
        }
        return result;
    }
    public static boolean writeTextFile(String fileName, String fileContents) throws InterruptedException
    {
        boolean result = false;
        File f = new File(fileName);
        if (!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch(Exception e)
            {
                return false;
            }
        }
        if (f.canWrite())
        {
            f.delete();
            try
            {
                if (f.createNewFile())
                {
                    FileWriter writer = new FileWriter(f);
                    writer.write(fileContents);
                    writer.close();
                    result = true;
                }
            }
            catch(Exception e)
            {
                obj_id self = getSelf();
                sendSystemMessageTestingOnly(self, "failed to write " + fileName + " : " + e);
            }
        }
        return result;
    }
}

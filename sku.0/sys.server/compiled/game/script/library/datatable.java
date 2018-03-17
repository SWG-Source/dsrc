package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.datatable_writer;

public class datatable extends script.base_script
{
    public datatable()
    {
    }
    public static String getRandomTemplate(String datatable) throws InterruptedException
    {
        String[] templateFilenames = dataTableGetStringColumn(datatable, "templateFilename");
        if (templateFilenames == null)
        {
            return null;
        }
        else 
        {
            int numItems = templateFilenames.length;
            int row = rand(0, numItems - 1);
            return templateFilenames[row];
        }
    }
    public static obj_id createRandomTemplateInWorld(String datatable) throws InterruptedException
    {
        String templateFilename = getRandomTemplate(datatable);
        obj_id myObjects = createObject(templateFilename, getLocation(getSelf()));
        if (myObjects == null)
        {
            return null;
        }
        else 
        {
            return myObjects;
        }
    }
    public static obj_id createRandomTemplateAtTarget(String datatable, obj_id target) throws InterruptedException
    {
        String templateFilename = getRandomTemplate(datatable);
        if ((target == null) || (templateFilename == null))
        {
            return null;
        }
        else 
        {
            obj_id myObject = createObjectAt(templateFilename, target);
            if (myObject == null)
            {
                return null;
            }
            else 
            {
                return myObject;
            }
        }
    }
    public static obj_id createRandomTemplateAtTarget(String datatable, location loc) throws InterruptedException
    {
        String templateFilename = getRandomTemplate(datatable);
        if ((loc == null) || (templateFilename == null))
        {
            return null;
        }
        else 
        {
            obj_id myObject = createObject(templateFilename, loc);
            if (myObject == null)
            {
                return null;
            }
            else 
            {
                return myObject;
            }
        }
    }
    public static boolean createDataTable(String strFileName, String[] strHeaders, String[] strHeaderTypes) throws InterruptedException
    {
        if (strFileName == null)
        {
            LOG("ERROR", "Null string passed into createDataTable)");
            return false;
        }
        int intIndex = strFileName.indexOf(".tab");
        if (intIndex < 0)
        {
            LOG("ERROR", "Datatbles need a .tab extension");
            return false;
        }
        if (strHeaders.length != strHeaderTypes.length)
        {
            LOG("ERROR", "You need to use the same length header/headertypes arrays");
            return false;
        }
        String strHeaderString = "";
        String strHeaderTypeString = "";
        strFileName = "../../dsrc/sku.0/sys.server/compiled/game/" + strFileName;
        for (int intI = 0; intI < strHeaders.length; intI++)
        {
            String strTest = strHeaderTypes[intI];
            if ((!strTest.equals("i")) && (!strTest.equals("s")) && (!strTest.equals("f")) && (!strTest.equals("h")) && (!strTest.equals("c")) && (!strTest.equals("e")) && (!strTest.equals("b")) && (!strTest.equals("p")))
            {
                LOG("ERROR", "You passed in a bad type for your header types at entry " + intI + " value was " + strTest);
                return false;
            }
            if (intI < strHeaders.length - 1)
            {
                strHeaderString = strHeaderString + strHeaders[intI] + "\t";
                strHeaderTypeString = strHeaderTypeString + strHeaderTypes[intI] + "\t";
            }
            else 
            {
                strHeaderString = strHeaderString + strHeaders[intI];
                strHeaderTypeString = strHeaderTypeString + strHeaderTypes[intI];
            }
        }
        strHeaderString = strHeaderString + "\n";
        strHeaderTypeString = strHeaderTypeString + "\n";
        String strTest = datatable_writer.makeDataTable(strFileName, strHeaderString, strHeaderTypeString);
        LOG("NOT_ERROR", "Datatable filename is " + strFileName);
        if (strTest != null)
        {
            return true;
        }
        return false;
    }
    public static void serverDataTableAddRow(String strFileName, dictionary dctParams) throws InterruptedException
    {
        strFileName = "../../dsrc/sku.0/sys.server/compiled/game/" + strFileName;
        dataTableAddRow(strFileName, dctParams);
    }
}

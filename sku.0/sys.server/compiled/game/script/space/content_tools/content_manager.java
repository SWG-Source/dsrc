package script.space.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.ship_ai;
import script.library.utils;

public class content_manager extends script.base.remote_object_creator
{
    public content_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "intNoDump", 1);
        location locTest = new location();
        obj_id objQuestObject = createObject("object/tangible/space/content_infrastructure/quest_manager.iff", locTest);
        if (!isIdValid(objQuestObject))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(objQuestObject, "intNoDump", 1);
        String strArea = locTest.area;
        String strDataTable = "";
        if (utils.checkConfigFlag("ScriptFlags", "e3Demo"))
        {
            strDataTable = "datatables/space_zones/buildout/e3_" + strArea + ".iff";
        }
        else 
        {
            strArea = getRootZoneName(strArea);
            strDataTable = "datatables/space_zones/buildout/" + strArea + ".iff";
        }
        registerNamedObject("content_manager", self);
        if (utils.checkConfigFlag("ScriptFlags", "noWorld"))
        {
            return SCRIPT_CONTINUE;
        }
        String[] strObject = dataTableGetStringColumn(strDataTable, "strObject");
        float[] fltJX = dataTableGetFloatColumn(strDataTable, "fltJX");
        float[] fltJY = dataTableGetFloatColumn(strDataTable, "fltJY");
        float[] fltJZ = dataTableGetFloatColumn(strDataTable, "fltJZ");
        float[] fltKX = dataTableGetFloatColumn(strDataTable, "fltKX");
        float[] fltKY = dataTableGetFloatColumn(strDataTable, "fltKY");
        float[] fltKZ = dataTableGetFloatColumn(strDataTable, "fltKZ");
        float[] fltPX = dataTableGetFloatColumn(strDataTable, "fltPX");
        float[] fltPY = dataTableGetFloatColumn(strDataTable, "fltPY");
        float[] fltPZ = dataTableGetFloatColumn(strDataTable, "fltPZ");
        String[] strObjVars = dataTableGetStringColumn(strDataTable, "strObjVars");
        String[] strScripts = dataTableGetStringColumn(strDataTable, "strScripts");
        for (int intI = 0; intI < strObject.length; intI++)
        {
            vector vctK = new vector(fltKX[intI], fltKY[intI], fltKZ[intI]);
            vctK = vctK.normalize();
            vector vctJ = new vector(fltJX[intI], fltJY[intI], fltJZ[intI]);
            vector vctI = vctJ.cross(vctK);
            vctJ = vctK.cross(vctI);
            vector vctP = new vector(fltPX[intI], fltPY[intI], fltPZ[intI]);
            transform tranCreation = new transform();
            try
            {
                tranCreation = new transform(vctI, vctJ, vctK, vctP);
            }
            catch(Throwable err)
            {
                vctJ = vector.unitY;
                vctK = vector.unitZ;
                vctI = vector.unitX;
                tranCreation = new transform(vctI, vctJ, vctK, vctP);
            }
            tranCreation = tranCreation.reorthonormalize();
            if (!strObject[intI].equals(""))
            {
                obj_id objTest = createObject(strObject[intI], tranCreation, null);
                if ((!isIdValid(objTest)) || (!objTest.isLoaded()))
                {
                }
                else 
                {
                    if (!strObjVars[intI].equals(""))
                    {
                        if ((isIdValid(objTest)) && (objTest.isLoaded()))
                        {
                            setPackedObjvars(objTest, strObjVars[intI]);
                        }
                    }
                    if (!strScripts[intI].equals(""))
                    {
                        String[] strScriptArray = split(strScripts[intI], ',');
                        for (int intJ = 0; intJ < strScriptArray.length; intJ++)
                        {
                            if ((isIdValid(objTest)) && (objTest.isLoaded()))
                            {
                                String script = strScriptArray[intJ];
                                if (script.indexOf("script.") > -1)
                                {
                                    script = script.substring(7);
                                }
                                if (!script.equals(""))
                                {
                                    if (!hasScript(objTest, script))
                                    {
                                        attachScript(objTest, script);
                                    }
                                }
                            }
                        }
                    }
                    if (hasObjVar(objTest, "ship.shipName"))
                    {
                        space_create.setupShipFromObjVars(objTest);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        registerNamedObject("content_manager", null);
        return SCRIPT_CONTINUE;
    }
    public String getRootZoneName(String strName) throws InterruptedException
    {
        int intIndex = strName.indexOf("_2");
        if (intIndex > -1)
        {
            String strTest = strName.substring(0, intIndex);
            LOG("space", "strTest is " + strTest);
            return strTest;
        }
        else 
        {
            return strName;
        }
    }
}

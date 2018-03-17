package script.systems.spawning;

import script.library.locations;
import script.*;

public class spawn_egg_manager extends script.base_script
{
    public spawn_egg_manager()
    {
    }
    public static final string_id SID_MUST_BE_IN_CITY = new string_id("city/city", "must_be_in_city");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        LOG("spawn", "Spawning stuff");
        setObjVar(self, "intNoDump", 1);
        location locTest = getLocation(self);
        String strDataTable = "";
        String strName = locations.getCityName(locTest);
        if (strName != null)
        {
            strDataTable = "datatables/spawning/city_layout/" + locTest.area + "_" + strName + ".iff";
        }
        if (strDataTable.equals(""))
        {
            LOG("spawn", "Did not open datatable 2 " + strDataTable);
            sendSystemMessage(self, SID_MUST_BE_IN_CITY);
            return SCRIPT_CONTINUE;
        }
        if (!dataTableOpen(strDataTable))
        {
            LOG("spawn", "Did not open datatable " + strDataTable);
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
            transform tranCreation;
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
                if ((isIdValid(objTest)) && (objTest.isLoaded())) {
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
                        String script;
                        for (String aStrScriptArray : strScriptArray) {
                            if ((isIdValid(objTest)) && (objTest.isLoaded())) {
                                script = aStrScriptArray;
                                if (script.contains("script.")) {
                                    script = script.substring(7);
                                }
                                if (!script.equals("")) {
                                    if (!hasScript(objTest, script)) {
                                        attachScript(objTest, script);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        LOG("spawn", "DONE");
        return SCRIPT_CONTINUE;
    }
}

package script.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_transition;
import script.library.prose;
import script.library.space_crafting;
import script.library.space_utils;
import script.library.space_combat;

public class interior_buildout extends script.base_script
{
    public interior_buildout()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        String strDataTable = "";
        if (hasObjVar(self, "strBuildout"))
        {
            strDataTable = "datatables/interior_buildouts/" + getStringObjVar(self, "strBuildout") + ".iff";
        }
        else 
        {
            setName(self, "FAILED TO BUILDOUT");
            return SCRIPT_CONTINUE;
        }
        if (dataTableOpen(strDataTable))
        {
            String[] strTemplates = dataTableGetStringColumn(strDataTable, "strTemplate");
            float[] fltJX = dataTableGetFloatColumn(strDataTable, "fltJX");
            float[] fltJY = dataTableGetFloatColumn(strDataTable, "fltJY");
            float[] fltJZ = dataTableGetFloatColumn(strDataTable, "fltJZ");
            float[] fltKX = dataTableGetFloatColumn(strDataTable, "fltKX");
            float[] fltKY = dataTableGetFloatColumn(strDataTable, "fltKY");
            float[] fltKZ = dataTableGetFloatColumn(strDataTable, "fltKZ");
            float[] fltPX = dataTableGetFloatColumn(strDataTable, "fltPX");
            float[] fltPY = dataTableGetFloatColumn(strDataTable, "fltPY");
            float[] fltPZ = dataTableGetFloatColumn(strDataTable, "fltPZ");
            String[] strCellName = dataTableGetStringColumn(strDataTable, "strCellName");
            String[] strObjVars = dataTableGetStringColumn(strDataTable, "strObjVars");
            String[] strScripts = dataTableGetStringColumn(strDataTable, "strScripts");
            String[] strLocationList = dataTableGetStringColumn(strDataTable, "strLocationList");
            int[] intNoCreate = dataTableGetIntColumn(strDataTable, "intNoCreate");
            for (int intI = 0; intI < strTemplates.length; intI++)
            {
                vector vctJ = new vector(fltJX[intI], fltJY[intI], fltJZ[intI]);
                vector vctK = new vector(fltKX[intI], fltKY[intI], fltKZ[intI]);
                vector vctP = new vector(fltPX[intI], fltPY[intI], fltPZ[intI]);
                transform tr = new transform(vctJ.cross(vctK), vctJ, vctK, vctP);
                obj_id objCell = getCellId(self, strCellName[intI]);
                if (intNoCreate[intI] > 0)
                {
                    if (!strLocationList[intI].equals(""))
                    {
                        Vector tranList = utils.getResizeableTransformArrayScriptVar(self, strLocationList[intI]);
                        Vector objCells = utils.getResizeableObjIdArrayScriptVar(self, strLocationList[intI] + "Cells");
                        if ((tranList == null) || (tranList.size() == 0))
                        {
                            Vector tranTestList = new Vector();
                            tranTestList.setSize(0);
                            Vector objTestCells = new Vector();
                            objTestCells.setSize(0);
                            tranTestList = utils.addElement(tranTestList, tr);
                            objTestCells = utils.addElement(objTestCells, objCell);
                            utils.setScriptVar(self, strLocationList[intI], tranTestList);
                            utils.setScriptVar(self, strLocationList[intI] + "Cells", objTestCells);
                        }
                        else 
                        {
                            objCells = utils.addElement(objCells, objCell);
                            tranList = utils.addElement(tranList, tr);
                            utils.setScriptVar(self, strLocationList[intI], tranList);
                            utils.setScriptVar(self, strLocationList[intI] + "Cells", objCells);
                        }
                    }
                    if (!utils.checkConfigFlag("ScriptFlags", "liveSpaceServer"))
                    {
                        obj_id objTest = createObject(strTemplates[intI], tr, objCell);
                        if (!strObjVars[intI].equals(""))
                        {
                            setPackedObjvars(objTest, strObjVars[intI]);
                        }
                        if (!strScripts[intI].equals(""))
                        {
                            String[] strScriptArray = split(strScripts[intI], ',');
                            for (int intJ = 0; intJ < strScriptArray.length; intJ++)
                            {
                                String script = strScriptArray[intJ];
                                if (script.indexOf("script.") > -1)
                                {
                                    script = script.substring(7);
                                }
                                if (!hasScript(objTest, script))
                                {
                                    attachScript(objTest, script);
                                }
                            }
                        }
                        attachScript(objTest, "space.ship.ship_interior_autocleanup");
                    }
                }
                else 
                {
                    obj_id objTest = createObject(strTemplates[intI], tr, objCell);
                    if (!strObjVars[intI].equals(""))
                    {
                        setPackedObjvars(objTest, strObjVars[intI]);
                    }
                    if (!strScripts[intI].equals(""))
                    {
                        String[] strScriptArray = split(strScripts[intI], ',');
                        for (int intJ = 0; intJ < strScriptArray.length; intJ++)
                        {
                            String script = strScriptArray[intJ];
                            if (script.indexOf("script.") > -1)
                            {
                                script = script.substring(7);
                            }
                            if (!hasScript(objTest, script))
                            {
                                attachScript(objTest, script);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

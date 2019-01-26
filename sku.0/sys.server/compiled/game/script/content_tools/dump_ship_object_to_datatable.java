package script.content_tools;

import script.*;
import script.library.datatable;
import script.library.space_utils;
import script.library.utils;

import java.util.Vector;

public class dump_ship_object_to_datatable extends script.base_script
{
    public dump_ship_object_to_datatable()
    {
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equals("dumpShipContents"))
        {
            location locTest = getLocation(self);
            obj_id objCell = locTest.cell;
            if (!isIdValid(objCell))
            {
                sendSystemMessageTestingOnly(self, "DONT USE THIS IF YOU'RE NOT IN A BUILDING!");
                return SCRIPT_CONTINUE;
            }
            obj_id objBuilding = getContainedBy(objCell);
            if (!isIdValid(objBuilding))
            {
                sendSystemMessageTestingOnly(self, "You are not in a building. Don't use this when not in a building");
                return SCRIPT_CONTINUE;
            }
            String strArea = locTest.area;
            String strDataTable = "";
            strDataTable = "datatables/ship_interiors/shipObjectsTemp" + getGameTime() + ".tab";
            LOG("space", "table name is " + strDataTable);
            String[] strHeaderTypes = 
            {
                "s",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "f",
                "p",
                "s",
                "s",
                "i",
                "s"
            };
            String[] strHeaders = 
            {
                "strTemplate",
                "fltJX",
                "fltJY",
                "fltJZ",
                "fltKX",
                "fltKY",
                "fltKZ",
                "fltPX",
                "fltPY",
                "fltPZ",
                "strObjVars",
                "strScripts",
                "strCellName",
                "intNoCreate",
                "strLocationList"
            };
            boolean boolTest = datatable.createDataTable(strDataTable, strHeaders, strHeaderTypes);
            if (!boolTest)
            {
                sendSystemMessageTestingOnly(self, "No datatable made");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objObjects = getBuildingContents(objBuilding);
            sendSystemMessageTestingOnly(self, "dumping contents of " + objBuilding);
            for (obj_id objObject : objObjects) {
                if (isDumpable(objObject)) {
                    dictionary dctRow = new dictionary();
                    int intNoCreate = 0;
                    locTest = getLocation(objObject);
                    String strTemplate = getTemplateName(objObject);
                    float fltX = locTest.x;
                    float fltY = locTest.y;
                    float fltZ = locTest.z;
                    String strCellName = space_utils.getCellName(objBuilding, locTest.cell);
                    if (hasObjVar(objObject, "intNoCreate")) {
                        intNoCreate = 1;
                    }
                    String strLocationList = "";
                    if (hasObjVar(objObject, "strLocationList")) {
                        strLocationList = getStringObjVar(objObject, "strLocationList");
                    }
                    dctRow.put("strLocationList", strLocationList);
                    dctRow.put("intNoCreate", intNoCreate);
                    transform vctTest = getTransform_o2p(objObject);
                    vector vctJ = vctTest.getLocalFrameJ_p();
                    vector vctK = vctTest.getLocalFrameK_p();
                    vector vctP = vctTest.getPosition_p();
                    dctRow.put("fltJX", vctJ.x);
                    dctRow.put("fltJY", vctJ.y);
                    dctRow.put("fltJZ", vctJ.z);
                    dctRow.put("fltKX", vctK.x);
                    dctRow.put("fltKY", vctK.y);
                    dctRow.put("fltKZ", vctK.z);
                    dctRow.put("fltPX", vctP.x);
                    dctRow.put("fltPY", vctP.y);
                    dctRow.put("fltPZ", vctP.z);
                    dctRow.put("strCellName", strCellName);
                    dctRow.put("strTemplate", strTemplate);
                    String strObjVars = getPackedObjvars(objObject);
                    dctRow.put("strObjVars", strObjVars);
                    String strScripts = utils.getPackedScripts(objObject);
                    dctRow.put("strScripts", strScripts);
                    datatable.serverDataTableAddRow(strDataTable, dctRow);
                }
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isDumpable(obj_id objObject) throws InterruptedException
    {
        if (isPlayer(objObject))
        {
            return false;
        }
        String strName = getTemplateName(objObject);
        int intIndex = strName.indexOf("object/cell");
        if (intIndex > -1)
        {
            return false;
        }
        if (hasObjVar(objObject, "intNoDump"))
        {
            return false;
        }
        if (hasScript(objObject, "systems.spawning.spawned_tracker"))
        {
            return false;
        }
        return true;
    }
    public obj_id[] getBuildingContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        for (obj_id objCell : objCells) {
            obj_id[] objTestContents = getContents(objCell);
            if ((objTestContents != null) && (objTestContents.length > 0)) {
                for (obj_id objTestContent : objTestContents) {
                    objContents = utils.addElement(objContents, objTestContent);
                }
            }
        }
        obj_id[] _objContents = new obj_id[0];
        if (objContents != null)
        {
            _objContents = new obj_id[objContents.size()];
            objContents.toArray(_objContents);
        }
        return _objContents;
    }
}

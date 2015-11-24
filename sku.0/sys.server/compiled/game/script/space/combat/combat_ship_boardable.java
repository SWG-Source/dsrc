package script.space.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.space_combat;

public class combat_ship_boardable extends script.base_script
{
    public combat_ship_boardable()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String strBoardingType = getStringObjVar(self, "strBoardingType");
        if (strBoardingType == null)
        {
            LOG("space", "BAD BOARDING TYPE ON " + self);
            return SCRIPT_CONTINUE;
        }
        String strFileName = "datatables/space_content/capital_ship_interiors/" + strBoardingType + ".iff";
        LOG("space", "Type is " + strFileName);
        String[] strMobs = dataTableGetStringColumn(strFileName, "strMobsToSpawn");
        String[] strBossesToSpawn = dataTableGetStringColumn(strFileName, "strBossesToSpawn");
        for (int intI = 0; intI < strBossesToSpawn.length; intI++)
        {
            location locSpawnLocation = getLocationFromLocationListRandom(self, "locBossSpawners", 1.5f);
            obj_id objMob = create.object(strBossesToSpawn[intI], locSpawnLocation);
        }
        utils.setLocalVar(self, "strMobs", strMobs);
        messageTo(self, "delayedSpawn", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public location getLocationFromLocationList(obj_id objObject, String strLocationList) throws InterruptedException
    {
        transform[] trTransforms = utils.getTransformArrayScriptVar(objObject, strLocationList);
        obj_id[] objCells = utils.getObjIdArrayScriptVar(objObject, strLocationList + "Cells");
        if ((trTransforms == null) || (trTransforms.length == 0))
        {
            LOG("space", "No location list for object " + objObject + " and List " + strLocationList);
            return null;
        }
        int intRoll = rand(0, trTransforms.length - 1);
        transform tr = trTransforms[intRoll];
        location locTest = space_utils.getLocationFromTransform(tr);
        locTest.cell = objCells[intRoll];
        return locTest;
    }
    public location getLocationFromLocationListRandom(obj_id objObject, String strLocationList, float fltRandomValue) throws InterruptedException
    {
        location locTest = getLocationFromLocationList(objObject, strLocationList);
        locTest.z = locTest.z + rand(-1 * fltRandomValue, fltRandomValue);
        locTest.y = locTest.y + rand(-1 * fltRandomValue, fltRandomValue);
        return locTest;
    }
    public int delayedSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        int intIndex = utils.getIntLocalVar(self, "intIndex");
        String[] strMobs = utils.getStringArrayLocalVar(self, "strMobs");
        location locSpawnLocation = getLocationFromLocationListRandom(self, "locSpawners", 1.5f);
        obj_id objMob = create.object(strMobs[intIndex], locSpawnLocation);
        intIndex = intIndex + 1;
        if (intIndex < strMobs.length)
        {
            utils.setLocalVar(self, "intIndex", intIndex);
            messageTo(self, "delayedSpawn", null, 1, false);
        }
        else 
        {
            utils.removeLocalVar(self, "intIndex");
            utils.removeLocalVar(self, "strMobs");
        }
        return SCRIPT_CONTINUE;
    }
}

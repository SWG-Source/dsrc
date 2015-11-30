package script.systems.spawning;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_utils;
import script.library.planetary_map;
import script.library.create;
import script.library.objvar_mangle;
import script.library.utils;
import java.lang.Math;
import script.library.hue;

public class mob_spawner extends script.base_script
{
    public mob_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        String strTest = "" + self;
        location locTest = getLocation(self);
        String strPlanet = locTest.area;
        dictionary dctSpawnInfo = dataTableGetRow("datatables/spawning/ground_spawning/static/" + strPlanet + ".iff", strTest);
        if (dctSpawnInfo == null)
        {
            obj_id objTest = createObject("object/tangible/gravestone/gravestone01.iff", getLocation(self));
            setName(objTest, "BAD NPC SPAWNER IS HERE!, NO ENTRY IN Ground SPawning " + strPlanet + " NPC SPAWNERS DATATABLE");
            return SCRIPT_CONTINUE;
        }
        String strType = dctSpawnInfo.getString("strType");
        String strMob = dctSpawnInfo.getString("strMob");
        String strName = dctSpawnInfo.getString("strName");
        float fltMinSpawnTime = dctSpawnInfo.getFloat("fltMinSpawnTime");
        float fltMaxSpawnTime = dctSpawnInfo.getFloat("fltMaxSpawnTime");
        Vector strScripts = new Vector();
        strScripts.setSize(0);
        String strMobToSpawn = "";
        if (!strType.equals(""))
        {
            String strFileName = "datatables/spawning/ground_spawning/types/" + strType + ".iff";
            String[] strSpawnList = dataTableGetStringColumnNoDefaults(strFileName, "strItem");
            if ((strSpawnList == null) || (strSpawnList.length == 0))
            {
                obj_id objTest = createObject("object/tangible/gravestone/gravestone01.iff", getLocation(self));
                setName(objTest, "for object " + self + " bad entry of " + strType + " and planet " + strPlanet);
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "strSpawnList", strSpawnList);
        }
        else 
        {
            String[] strSpawnList = new String[1];
            strSpawnList[0] = strMob;
            utils.setScriptVar(self, "strSpawnList", strSpawnList);
        }
        for (int intI = 1; intI < 5; intI++)
        {
            String strScript = dctSpawnInfo.getString("strScript" + intI);
            if (!strScript.equals(""))
            {
                strScripts = utils.addElement(strScripts, strScript);
            }
        }
        if (strScripts.size() > 0)
        {
            utils.setScriptVar(self, "strScripts", strScripts);
        }
        if (!strType.equals(""))
        {
            utils.setScriptVar(self, "strName", "");
        }
        else 
        {
            utils.setScriptVar(self, "strName", strName);
        }
        utils.setScriptVar(self, "fltMinSpawnTime", fltMinSpawnTime);
        utils.setScriptVar(self, "fltMaxSpawnTime", fltMaxSpawnTime);
        makeMob(self);
        return SCRIPT_CONTINUE;
    }
    public void makeMob(obj_id self) throws InterruptedException
    {
        location locTest = getLocation(self);
        String strPlanet = locTest.area;
        String[] strSpawnList = utils.getStringArrayScriptVar(self, "strSpawnList");
        String[] strScripts = utils.getStringArrayScriptVar(self, "strScripts");
        String strName = utils.getStringScriptVar(self, "strName");
        float fltMinSpawnTime = utils.getFloatScriptVar(self, "fltMinSpawnTime");
        float fltMaxSpawnTime = utils.getFloatScriptVar(self, "fltMaxSpawnTime");
        String strMob = strSpawnList[rand(0, strSpawnList.length - 1)];
        obj_id objNPC = null;
        boolean spawnFailed = false;
        try
        {
            objNPC = create.object(strMob, getLocation(self));
        }
        catch(Throwable err)
        {
            spawnFailed = true;
        }
        if (!isIdValid(objNPC) || spawnFailed)
        {
            locTest.x = locTest.x + 1;
            obj_id objTest = createObject("object/tangible/gravestone/gravestone01.iff", locTest);
            setName(objTest, "For " + self.toString() + " Bad NPC Type of type " + strMob + " on planet " + strPlanet);
            return;
        }
        setYaw(objNPC, getYaw(self));
        if (!strName.equals(""))
        {
            setName(objNPC, "");
            setName(objNPC, strName);
        }
        if ((strScripts != null) && (strScripts.length > 0))
        {
            for (int intI = 0; intI < strScripts.length; intI++)
            {
                if (!strScripts[intI].equals(""))
                {
                    if (!hasScript(objNPC, strScripts[intI]))
                    {
                        attachScript(objNPC, strScripts[intI]);
                    }
                }
            }
        }
        attachScript(objNPC, "systems.spawning.mob_spawn_tracker");
        utils.setScriptVar(objNPC, "objParent", self);
        utils.setScriptVar(objNPC, "fltRespawnTime", rand(fltMinSpawnTime, fltMaxSpawnTime));
        return;
    }
    public int respawnMob(obj_id self, dictionary params) throws InterruptedException
    {
        makeMob(self);
        return SCRIPT_CONTINUE;
    }
}

package script.systems.spawning;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;

public class theater_spawnegg extends script.base_script
{
    public theater_spawnegg()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginSpawning", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id[] spawnList = getObjIdArrayObjVar(self, "spawnList");
        if (spawnList == null || spawnList.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id aSpawnList : spawnList) {
            if (isIdValid(aSpawnList)) {
                destroyObject(aSpawnList);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int beginSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        String strNumToSpawn = getStringObjVar(self, "pop");
        int numToSpawn = utils.stringToInt(strNumToSpawn);
        if (numToSpawn < 1)
        {
            setObjVar(self, "pop", 1);
            numToSpawn = 1;
        }
        if (numToSpawn > 5)
        {
            setObjVar(self, "pop", 5);
            numToSpawn = 5;
        }
        String npcType = getStringObjVar(self, "type");
        if (npcType == null)
        {
            npcType = "none";
        }
        if (npcType.equals("none"))
        {
            setObjVar(self, "type", "thug");
            npcType = "thug";
        }
        String name = getStringObjVar(self, "name");
        if (name == null)
        {
            name = "default";
        }
        String behavior = getStringObjVar(self, "behavior");
        if (behavior == null)
        {
            behavior = "default";
        }
        String objVarName = getStringObjVar(self, "objvarname");
        String objVarType = getStringObjVar(self, "objvartype");
        String objVarValue = getStringObjVar(self, "objvarvalue");
        if (objVarName == null)
        {
            objVarName = "none";
        }
        else 
        {
            if (objVarValue == null)
            {
                objVarName = "none";
            }
        }
        int x = 0;
        obj_id[] spawnList = new obj_id[numToSpawn];
        while (x < numToSpawn)
        {
            obj_id npc = create.object(npcType, pickLoc(self, x));
            if (!name.equals("default"))
            {
                setName(npc, name);
            }
            String script = getStringObjVar(self, "script");
            if (script == null || script.equals(""))
            {
                script = "none";
            }
            if (!script.equals("none"))
            {
                attachScript(npc, script);
            }
            if (!objVarName.equals("none"))
            {
                if (objVarType.equalsIgnoreCase("int"))
                {
                    int intObjVarValue = utils.stringToInt(objVarValue);
                    setObjVar(npc, objVarName, intObjVarValue);
                }
                else if (objVarType.equalsIgnoreCase("float"))
                {
                    float floatObjVarValue = utils.stringToFloat(objVarValue);
                    setObjVar(npc, objVarName, floatObjVarValue);
                }
                else 
                {
                    setObjVar(npc, objVarName, objVarValue);
                }
            }
            if (behavior.equalsIgnoreCase("sentinel"))
            {
                setObjVar(npc, "SENTINEL", 1);
                ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
            }
            else if (behavior.equalsIgnoreCase("loiter"))
            {
                setObjVar(npc, "LOITER", 1);
                ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_LOITER);
            }
            else if (behavior.equalsIgnoreCase("wander"))
            {
                setObjVar(npc, "WANDER", 1);
                ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_WANDER);
            }
            spawnList[x] = npc;
            x = x + 1;
        }
        setObjVar(self, "spawnList", spawnList);
        return SCRIPT_CONTINUE;
    }
    public location pickLoc(obj_id self, int x) throws InterruptedException
    {
        location here = getLocation(self);
        setObjVar(self, "location", here);
        location zero = here;
        here.x = here.x - 1.5f;
        here.z = here.z - 1.5f;
        location one = here;
        here.z = here.z + 3;
        location two = here;
        here.x = here.x + 3;
        location three = here;
        here.z = here.z - 3;
        location four = here;
        here = getLocation(self);
        here.x = here.x + rand(-3, 3);
        here.z = here.z + rand(-3, 3);
        location other = here;
        if (x == 0)
        {
            return zero;
        }
        else if (x == 1)
        {
            return one;
        }
        else if (x == 2)
        {
            return two;
        }
        else if (x == 3)
        {
            return three;
        }
        else if (x == 4)
        {
            return four;
        }
        else 
        {
            return other;
        }
    }
}

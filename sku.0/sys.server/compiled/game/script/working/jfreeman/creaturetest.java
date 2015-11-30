package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class creaturetest extends script.base_script
{
    public creaturetest()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("creaturetest"))
        {
            if (utils.hasScriptVar(self, "creatureTest"))
            {
                debugSpeakMsg(self, "creaturetest is already running");
                return SCRIPT_CONTINUE;
            }
            int startTime = getGameTime();
            debugSpeakMsg(self, "beginning creatureTest " + startTime);
            utils.setScriptVar(self, "startTime", startTime);
            utils.setScriptVar(self, "creatureTest", 0);
            LOG("creaturetest", "BEGIN " + startTime);
            messageTo(self, "spawnNextCreature", null, 1, false);
        }
        else if (text.equals("creaturetest stop"))
        {
            utils.removeScriptVar(self, "creatureTest");
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnNextCreature(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "creatureTest"))
        {
            return SCRIPT_CONTINUE;
        }
        int creatureNum = utils.getIntScriptVar(self, "creatureTest");
        int numRows = dataTableGetNumRows(CREATURE_TABLE);
        if (creatureNum >= numRows)
        {
            int startTime = utils.getIntScriptVar(self, "startTime");
            debugSpeakMsg(self, "creaturetest " + startTime + " is DONE");
            utils.removeScriptVar(self, "creatureTest");
            utils.removeScriptVar(self, "startTime");
            LOG("creaturetest", "END " + startTime);
            return SCRIPT_CONTINUE;
        }
        String creatureName = dataTableGetString(CREATURE_TABLE, creatureNum, "creatureName");
        if (creatureName.equals("") || creatureName == null)
        {
            return SCRIPT_CONTINUE;
        }
        debugServerConsoleMsg(self, "CREATURE NAME IS " + creatureName + " - NUMBER: " + creatureNum);
        utils.setScriptVar(self, "lastCreature", creatureName);
        location spawnLoc = getLocation(self);
        spawnLoc.x += 2;
        spawnLoc.z += 2;
        obj_id creature = create.object(creatureName, spawnLoc, false);
        if (!isIdValid(creature))
        {
            String templateName = dataTableGetString(CREATURE_TABLE, creatureName, "template");
            LOG("creaturetest", "UNABLE TO CREATE " + creatureName + " WITH TEMPLATE " + templateName + "DOES THIS TEMPLATE EXIST?");
            LOG("creaturetest", creatureName + ":" + templateName);
        }
        else 
        {
            destroyObject(creature);
        }
        creatureNum++;
        utils.setScriptVar(self, "creatureTest", creatureNum);
        messageTo(self, "spawnNextCreature", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        String creatureName = utils.getStringScriptVar(self, "lastCreature");
        if (creatureName != null)
        {
            LOG("creaturetest", "CLIENT FATAL " + creatureName);
            debugServerConsoleMsg(self, "CLIENT FATAL " + creatureName);
        }
        return SCRIPT_CONTINUE;
    }
}

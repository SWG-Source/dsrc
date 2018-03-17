package script.city.bestine;

import script.*;
import script.library.ai_lib;
import script.library.create;
import script.library.utils;

public class politician_event_spawner extends script.base_script
{
    public politician_event_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        dctScriptVars.put("numCapitolChecks", 1);
        messageTo(self, "checkForCapitolBuilding", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForCapitolBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        deltadictionary dctScriptVars = self.getScriptVars();
        int numCapitolChecks = dctScriptVars.getInt("numCapitolChecks");
        map_location[] allRegisteredCapitols = getPlanetaryMapLocations("capitol", "");
        if (allRegisteredCapitols != null && allRegisteredCapitols.length != 0)
        {
            obj_id tatooineCapitolObjId = null;
            String capitolName;
            for (map_location tatooineCapitol : allRegisteredCapitols) {
                capitolName = getString(utils.unpackString(tatooineCapitol.getLocationName()));
                if (capitolName != null && capitolName.equals("Bestine")) {
                    tatooineCapitolObjId = tatooineCapitol.getLocationId();
                }
            }
            if (!isIdValid(tatooineCapitolObjId))
            {
                if (numCapitolChecks <= 30)
                {
                    numCapitolChecks = numCapitolChecks + 1;
                    dctScriptVars.put("numCapitolChecks", numCapitolChecks);
                    messageTo(self, "checkForCapitolBuilding", null, 60, false);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    LOG("DESIGNER_FATAL", "BESTINE POLITICIAN EVENT: Could not find registered capitol building from here: " + here);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                dctScriptVars.put("tatooineCapitolObjId", tatooineCapitolObjId);
                spawnNpc(self);
            }
        }
        else 
        {
            if (numCapitolChecks <= 30)
            {
                numCapitolChecks = numCapitolChecks + 1;
                dctScriptVars.put("numCapitolChecks", numCapitolChecks);
                messageTo(self, "checkForCapitolBuilding", null, 60, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("DESIGNER_FATAL", "BESTINE POLITICIAN EVENT: Couldn't find a valid map_location for a registered capitol after " + numCapitolChecks + " capitolChecks from here: " + here);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnNpc(obj_id self) throws InterruptedException
    {
        String spawn = getStringObjVar(self, "spawns");
        location here = getLocation(self);
        if (spawn.equals("hutt_informant_quest"))
        {
            here = new location(-1120.21f, 12.0f, -3639.71f, "tatooine", null);
        }
        obj_id npc = create.staticObject(spawn, here);
        setInvulnerable(npc, true);
        setCreatureStatic(npc, true);
        ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id tatooineCapitolObjId = dctScriptVars.getObjId("tatooineCapitolObjId");
        setObjVar(npc, "bestine.tatooineCapitolObjId", tatooineCapitolObjId);
        attachScript(npc, "city.bestine.politician_event_npc");
        if (hasObjVar(self, "quest_script"))
        {
            String script = getStringObjVar(self, "quest_script");
            attachScript(npc, script);
        }
        if (hasObjVar(self, "quest_table"))
        {
            String table = getStringObjVar(self, "quest_table");
            setObjVar(npc, "quest_table", table);
        }
        if (hasObjVar(self, "npc_name"))
        {
            String name = getStringObjVar(self, "npc_name");
            if (name != null)
            {
                if (!name.equals(""))
                {
                    setName(npc, name);
                }
            }
        }
    }
}

package script.city.bestine;

import script.*;
import script.library.ai_lib;
import script.library.create;
import script.library.utils;

public class museum_event_spawner extends script.base_script
{
    public museum_event_spawner()
    {
    }
    public static final String SCRIPT_NAME = "city.bestine.museum_event_spawner";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("museum","Museum event NPC spawner initializing with objid of " + self + ".");
        deltadictionary dctScriptVars = self.getScriptVars();
        dctScriptVars.put("numMuseumChecks", 1);
        messageTo(self, "checkForMuseumBuilding", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForMuseumBuilding(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("museum","Starting to look for Bestine's museum.");
        deltadictionary dctScriptVars = self.getScriptVars();
        int numMuseumChecks = dctScriptVars.getInt("numMuseumChecks");

        map_location[] allRegisteredMuseums = getPlanetaryMapLocations("museum", "");
        location here = getLocation(self);
        if (allRegisteredMuseums != null && allRegisteredMuseums.length != 0)
        {
            obj_id objMuseumBuilding = null;
            map_location registeredMuseum;
            String museumName;
            for (map_location allRegisteredMuseum : allRegisteredMuseums) {
                LOG("museum", "Found " + allRegisteredMuseums.length + " registered museums.");
                registeredMuseum = allRegisteredMuseum;
                museumName = getString(utils.unpackString(registeredMuseum.getLocationName()));
                if (museumName != null && museumName.equals("Bestine")) {
                    objMuseumBuilding = registeredMuseum.getLocationId();
                    LOG("museum", "Found Bestine's museum - has objid of " + objMuseumBuilding);
                }
            }
            if (!isIdValid(objMuseumBuilding))
            {
                if (numMuseumChecks <= 90)
                {
                    numMuseumChecks = numMuseumChecks + 1;
                    dctScriptVars.put("numMuseumChecks", numMuseumChecks);
                    messageTo(self, "checkForMuseumBuilding", null, 60, false);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    LOG("DESIGNER_FATAL", "** " + SCRIPT_NAME + ": Couldn't find obj_id for a registered museum after " + numMuseumChecks + " museumChecks from here:" + here);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                LOG("museum","Adding Bestine's museum obj id to the script vars and getting ready to spawn npc's.");
                dctScriptVars.put("objMuseumBuilding", objMuseumBuilding);
                spawnNpc(self);
            }
        }
        else 
        {
            if (numMuseumChecks <= 90)
            {
                numMuseumChecks = numMuseumChecks + 1;
                dctScriptVars.put("numMuseumChecks", numMuseumChecks);
                messageTo(self, "checkForMuseumBuilding", null, 60, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("DESIGNER_FATAL", "** " + SCRIPT_NAME + ": Couldn't find a valid map_location for a registered museum after " + numMuseumChecks + " museumChecks from here: " + here);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnNpc(obj_id self) throws InterruptedException
    {
        LOG("museum","Starting to spawn NPC.");
        String spawn = getStringObjVar(self, "spawns");
        obj_id npc = create.staticObject(spawn, getLocation(self));
        setInvulnerable(npc, true);
        setCreatureStatic(npc, true);
        ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);

        deltadictionary dctScriptVars = self.getScriptVars();
        obj_id objMuseumBuilding = dctScriptVars.getObjId("objMuseumBuilding");
        setObjVar(npc, "bestine.objMuseumBuilding", objMuseumBuilding);
        attachScript(npc, "city.bestine.museum_event_npc");

        if (spawn.equals("lilas_dinhint"))
        {
            attachScript(npc, "city.bestine.politician_event_npc");
            attachScript(npc, "conversation.lilas_dinhint");
        }
        if (spawn.equals("bestine_artist01"))
        {
            attachScript(npc, "conversation.bestine_artist01");
        }
        if (spawn.equals("bestine_artist02"))
        {
            attachScript(npc, "conversation.bestine_artist02");
        }
        if (spawn.equals("bestine_artist03"))
        {
            attachScript(npc, "conversation.bestine_artist03");
        }
        if (spawn.equals("bestine_artist04"))
        {
            attachScript(npc, "conversation.bestine_artist04");
        }
        if (spawn.equals("bestine_artist05"))
        {
            attachScript(npc, "conversation.bestine_artist05");
        }
        if (spawn.equals("bestine_artist06"))
        {
            attachScript(npc, "conversation.bestine_artist06");
        }
    }
}

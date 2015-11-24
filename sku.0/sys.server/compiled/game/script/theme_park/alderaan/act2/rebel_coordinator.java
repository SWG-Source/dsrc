package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.locations;

public class rebel_coordinator extends script.base_script
{
    public rebel_coordinator()
    {
    }
    public static final String COMMANDER_TABLE = "datatables/convo/alderaan/act2/commander_locs.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int messageStartMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        int missionNum = params.getInt("value");
        location deliveryLoc;
        String npcName;
        switch (missionNum)
        {
            case 1:
            deliveryLoc = getDeliveryLoc(player, 1000, 1500);
            npcName = "coa2_rebel_sympathizer";
            break;
            case 3:
            deliveryLoc = getDeliveryLoc(player, 1250, 2000);
            npcName = "coa2_rebel_lyda";
            break;
            case 4:
            if (!hasObjVar(player, "coa2.rebel.missionPlanet"))
            {
                deliveryLoc = getCommanderLoc(player);
            }
            else 
            {
                deliveryLoc = getLocationObjVar(player, "coa2.rebel.missionLoc");
            }
            npcName = "";
            break;
            default:
            LOG("CoA2_Rebel", "WARNING!!!  Rebel_Coordinator - Recieved messageStartMission for unknown mission number. (" + missionNum + ")");
            return SCRIPT_OVERRIDE;
        }
        location returnLoc = new location();
        obj_id building = getTopMostContainer(npc);
        if (building == npc)
        {
            returnLoc = getLocation(npc);
        }
        else 
        {
            returnLoc = getLocation(building);
        }
        setObjVar(player, "coa2.rebel.missionNum", missionNum);
        setObjVar(player, "coa2.rebel.missionNpc", npcName);
        setObjVar(player, "coa2.rebel.missionLoc", deliveryLoc);
        setObjVar(player, "coa2.rebel.returnLoc", returnLoc);
        if (hasScript(player, "theme_park.alderaan.act2.rebel_mission"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.alderaan.act2.rebel_mission");
        }
        return SCRIPT_CONTINUE;
    }
    public int messageAbortMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        int missionNum = params.getInt("value");
        switch (missionNum)
        {
            case 1:
            case 3:
            case 4:
            messageTo(player, "handleAbortMission", params, 0, false);
            break;
            default:
            LOG("CoA2_Rebel", "WARNING!!!  Rebel_Coordinator - Recieved messageAbortMission for unknown mission number. (" + missionNum + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public location getDeliveryLoc(obj_id player, int minDistance, int maxDistance) throws InterruptedException
    {
        region city = locations.getCityRegion(getLocation(player));
        location cityCenter = locations.getRegionCenter(city);
        location deliveryLoc = null;
        int x = 0;
        while (x < 10)
        {
            LOG("CoA2_Rebel", "Rebel Delivery Location:  Attepmt #" + (x + 1));
            location loc = utils.getRandomLocationInRing(cityCenter, minDistance, maxDistance);
            deliveryLoc = locations.getGoodLocationAroundLocation(loc, 30f, 30f, 200f, 200f);
            if (deliveryLoc != null)
            {
                break;
            }
            x += 1;
        }
        return deliveryLoc;
    }
    public location getCommanderLoc(obj_id player) throws InterruptedException
    {
        String[] commanderFaction = dataTableGetStringColumn(COMMANDER_TABLE, 0);
        int rndCommander = rand(0, commanderFaction.length - 1);
        while (!commanderFaction[rndCommander].equals("rebel"))
        {
            rndCommander = rand(0, commanderFaction.length - 1);
        }
        int x = dataTableGetInt(COMMANDER_TABLE, rndCommander, "x");
        int y = dataTableGetInt(COMMANDER_TABLE, rndCommander, "y");
        int z = dataTableGetInt(COMMANDER_TABLE, rndCommander, "z");
        String planet = dataTableGetString(COMMANDER_TABLE, rndCommander, "planet");
        location commanderLoc = new location(x, y, z, planet);
        setObjVar(player, "coa2.rebel.missionPlanet", planet);
        return commanderLoc;
    }
}

package script.theme_park.alderaan.act3;

import script.dictionary;
import script.library.ai_lib;
import script.library.locations;
import script.library.utils;
import script.location;
import script.obj_id;
import script.region;

public class rebel_commander extends script.base_script
{
    public rebel_commander()
    {
    }
    public static final String COMMANDER_TABLE = "datatables/convo/alderaan/act2/commander_locs.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Princess Leia");
        messageTo(self, "handleLeiaCleanup", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleLeiaCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasScript(self, "npc.celebrity.leia"))
        {
            detachScript(self, "npc.celebrity.leia");
        }
        if (hasScript(self, "theme_park.rebel.quest_convo"))
        {
            detachScript(self, "theme_park.rebel.quest_convo");
        }
        removeObjVar(self, "quest_table");
        removeObjVar(self, "minGating");
        removeObjVar(self, "maxGating");
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
            case 5:
            deliveryLoc = getDeliveryLoc(player, 1500, 2500);
            npcName = "object/building/theme_park/alderaan/act3/rebel_research_defend.iff";
            break;
            default:
            LOG("CoA3_Rebel", "WARNING!!!  Rebel_Commander - Recieved messageStartMission for unknown mission number. (" + missionNum + ")");
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
        setObjVar(player, "coa3.rebel.missionNum", missionNum);
        setObjVar(player, "coa3.rebel.missionNpc", npcName);
        setObjVar(player, "coa3.rebel.missionLoc", deliveryLoc);
        setObjVar(player, "coa3.rebel.returnLoc", returnLoc);
        if (hasScript(player, "theme_park.alderaan.act3.rebel_mission"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.alderaan.act3.rebel_mission");
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
            case 5:
            messageTo(player, "handleAbortMission", params, 0, false);
            break;
            default:
            LOG("CoA3_Rebel", "WARNING!!!  Rebel_Commander - Recieved messageAbortMission for unknown mission number. (" + missionNum + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public location getDeliveryLoc(obj_id player, int minDistance, int maxDistance) throws InterruptedException
    {
        region city = null;
        location cityCenter = null;
        location deliveryLoc = null;
        city = locations.getCityRegion(getLocation(player));
        if (city != null)
        {
            cityCenter = locations.getRegionCenter(city);
        }
        if (cityCenter == null)
        {
            cityCenter = getLocation(player);
        }
        int x = 0;
        while (x < 10)
        {
            LOG("CoA3_Rebel", "Rebel Delivery Location:  Attepmt #" + (x + 1));
            location loc = utils.getRandomLocationInRing(cityCenter, minDistance, maxDistance);
            deliveryLoc = locations.getGoodLocationAroundLocation(loc, 30.0f, 30.0f, 200.0f, 200.0f);
            if (deliveryLoc != null)
            {
                deliveryLoc.y = getElevation(deliveryLoc);
                break;
            }
            x += 1;
        }
        deliveryLoc.y = getHeightAtLocation(deliveryLoc.x, deliveryLoc.z);
        return deliveryLoc;
    }
    public location getVaccaLoc(obj_id self) throws InterruptedException
    {
        location vaccaLoc = getLocation(self);
        int length = dataTableGetNumRows(COMMANDER_TABLE);
        int idx = 5;
        while (idx < length)
        {
            String planet = dataTableGetString(COMMANDER_TABLE, idx, "planet");
            if (planet.equals(vaccaLoc.area))
            {
                vaccaLoc.x = dataTableGetInt(COMMANDER_TABLE, idx, "x2");
                vaccaLoc.y = dataTableGetInt(COMMANDER_TABLE, idx, "y2");
                vaccaLoc.z = dataTableGetInt(COMMANDER_TABLE, idx, "z2");
            }
            idx++;
        }
        return vaccaLoc;
    }
}

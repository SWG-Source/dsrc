package script.theme_park.alderaan.act3;

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
import script.library.chat;

public class imperial_commander extends script.base_script
{
    public imperial_commander()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Colonel Veers");
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
            npcName = "object/building/theme_park/alderaan/act3/imperial_research_destroy.iff";
            break;
            default:
            LOG("CoA3_Imperial", "WARNING!!!  Imperial_Commander - Recieved messageStartMission for unknown mission number. (" + missionNum + ")");
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
        setObjVar(player, "coa3.imperial.missionNum", missionNum);
        setObjVar(player, "coa3.imperial.missionNpc", npcName);
        setObjVar(player, "coa3.imperial.missionLoc", deliveryLoc);
        setObjVar(player, "coa3.imperial.returnLoc", returnLoc);
        if (hasScript(player, "theme_park.alderaan.act3.imperial_mission"))
        {
            messageTo(player, "handleRestartMission", params, 0, false);
        }
        else 
        {
            attachScript(player, "theme_park.alderaan.act3.imperial_mission");
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
            LOG("CoA3_Imperial", "WARNING!!!  Imperial_Commander - Recieved messageAbortMission for unknown mission number. (" + missionNum + ")");
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
            LOG("CoA3_Imperial", "Imperial Delivery Location:  Attepmt #" + (x + 1));
            location loc = utils.getRandomLocationInRing(cityCenter, minDistance, maxDistance);
            deliveryLoc = locations.getGoodLocationAroundLocation(loc, 30f, 30f, 200f, 200f);
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
}

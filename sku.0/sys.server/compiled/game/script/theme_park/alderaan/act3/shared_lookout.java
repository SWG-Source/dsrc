package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.utils;
import script.library.locations;

public class shared_lookout extends script.base_script
{
    public shared_lookout()
    {
    }
    public static final String GU_REPAIR_KIT = "object/tangible/theme_park/alderaan/act3/grav_unit_repair_kit.iff";
    public static final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act3/shared_imperial_missions";
    public static final string_id REPAIR_KIT_RECEIVED = new string_id(IMPERIAL_SHARED_STF, "m2_repair_kit_received");
    public static final string_id REPAIR_KIT_DENIED = new string_id(IMPERIAL_SHARED_STF, "m2_repair_kit_denied");
    public int handleStartCaravanMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id npc = params.getObjId("npc");
        int missionNum = params.getInt("value");
        if (!utils.playerHasItemByTemplate(player, GU_REPAIR_KIT))
        {
            giveRepairKit(player);
        }
        location deliveryLoc = getDeliveryLoc(player, 1000, 1500);
        String npcName = "coa3_caravan_leader";
        if (hasObjVar(player, "coa3.imperial"))
        {
            setObjVar(player, "coa3.imperial.missionNum", missionNum);
            setObjVar(player, "coa3.imperial.missionNpc", npcName);
            setObjVar(player, "coa3.imperial.missionLoc", deliveryLoc);
        }
        else 
        {
            setObjVar(player, "coa3.rebel.missionNum", missionNum);
            setObjVar(player, "coa3.rebel.missionNpc", npcName);
            setObjVar(player, "coa3.rebel.missionLoc", deliveryLoc);
        }
        messageTo(player, "handleRestartMission", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAbortCaravanMission(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        messageTo(player, "handleAbortMission", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAttackPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        startCombat(self, player);
        return SCRIPT_CONTINUE;
    }
    public location getDeliveryLoc(obj_id player, int minDistance, int maxDistance) throws InterruptedException
    {
        location center = getLocation(player);
        location deliveryLoc = null;
        int x = 0;
        while (x < 10)
        {
            LOG("CoA3_Imperial", "Imperial Delivery Location:  Attepmt #" + (x + 1));
            location loc = utils.getRandomLocationInRing(center, minDistance, maxDistance);
            deliveryLoc = locations.getGoodLocationAroundLocation(loc, 30f, 30f, 200f, 200f);
            if (deliveryLoc != null)
            {
                break;
            }
            x += 1;
        }
        deliveryLoc.y = getHeightAtLocation(deliveryLoc.x, deliveryLoc.z);
        return deliveryLoc;
    }
    public void giveRepairKit(obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        obj_id kit = createObject(GU_REPAIR_KIT, inventory, "");
        if (!isIdValid(kit))
        {
            sendSystemMessage(player, REPAIR_KIT_DENIED);
        }
        else 
        {
            sendSystemMessage(player, REPAIR_KIT_RECEIVED);
        }
    }
}

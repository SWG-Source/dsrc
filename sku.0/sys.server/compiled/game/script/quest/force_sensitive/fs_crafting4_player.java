package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.jedi_trials;
import script.library.locations;
import script.library.money;
import script.library.quests;
import script.library.sui;
import script.library.utils;

public class fs_crafting4_player extends script.base_script
{
    public fs_crafting4_player()
    {
    }
    public static final string_id SID_NO_MONEY_MSG = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_no_money");
    public static final string_id SID_PURCHASE_MSG_01 = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_msg_01");
    public static final string_id SID_PURCHASE_MSG_02 = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_msg_02");
    public static final string_id SID_PURCHASE_MSG_03 = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_msg_03");
    public static final string_id SID_CRAFTING4_PHASE_OVER = new string_id("quest/force_sensitive/fs_crafting", "crafting4_phase_over");
    public static final string_id SID_CRAFTING4_SUI_TITLE = new string_id("quest/force_sensitive/fs_crafting", "crafting4_phase_change_title");
    public static final string_id SID_TRACKING_TARGET_UPDATED = new string_id("quest/force_sensitive/fs_crafting", "tracking_device_target_updated");
    public static final String TRACKING_DEVICE_TEMPLATE = "object/tangible/loot/collectible/collectible_rewards/fs_tracking_device.iff";
    public static final String DOWNED_SATELLITE_TEMPLATE = "object/tangible/item/quest/force_sensitive/fs_crafting4_downed_satellite.iff";
    public static final String COMPUTER_CORE_TEMPLATE = "object/tangible/item/quest/force_sensitive/fs_crafting4_computer_core.iff";
    public static final String LOCATION_TARGET_NAME = "crafting4_target_downed_satellite";
    public static final String NEEDS_TRACKING_DATA_OBJVAR = "fs_crafting4.tracking.needsTrackingData";
    public static final String CRAFTING4_BASE_OBJVAR = "fs_crafting4";
    public static final String TRACKING_BASE_OBJVAR = "fs_crafting4.tracking";
    public static final String TARGET_LOC_OBJVAR = "fs_crafting4.tracking.targetLoc";
    public static final String TARGET_WAYPOINT_OBJVAR = "fs_crafting4.tracking.targetWaypoint";
    public static final String MASTER_OBJVAR = "fs_crafting4.master";
    public static final String SATELLITE_OBJVAR = "fs_crafting4.satellite";
    public static final String PHASE4_ENDED_OBJVAR = "fs_crafting4.myPhase4Ended";
    public static final int TRACKING_DATA_COST = 1100;
    public int msgQuestAbortPhaseChange(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, PHASE4_ENDED_OBJVAR, true);
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName != null && questName.equals("fs_crafting4_quest_06"))
        {
            if (hasObjVar(self, PHASE4_ENDED_OBJVAR))
            {
                deactivateCrafting4Quest(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (locationName.equals(LOCATION_TARGET_NAME))
        {
            location satelliteLoc = getLocationObjVar(self, TARGET_LOC_OBJVAR);
            obj_id satellite = createObject(DOWNED_SATELLITE_TEMPLATE, satelliteLoc);
            setObjVar(satellite, "owner", self);
            setObjVar(self, SATELLITE_OBJVAR, satellite);
            if (utils.hasScriptVar(self, "trackingDevice.locationObject"))
            {
                obj_id locationObject = utils.getObjIdScriptVar(self, "trackingDevice.locationObject");
                if (isIdValid(locationObject))
                {
                    locations.destroyLocationObject(locationObject);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetLocationTarget(obj_id self, dictionary params) throws InterruptedException
    {
        location targetLoc = getLocationObjVar(self, TARGET_LOC_OBJVAR);
        addLocationTarget(LOCATION_TARGET_NAME, targetLoc, 64.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnLocationReceived(obj_id self, String locationId, obj_id locationObject, location locationLocation, float locationRadius) throws InterruptedException
    {
        if (isIdValid(locationObject) && locationLocation != null)
        {
            if (locationId.equals("crafting4_target_downed_satellite"))
            {
                setObjVar(self, TARGET_LOC_OBJVAR, locationLocation);
                obj_id oldWaypoint = getObjIdObjVar(self, TARGET_WAYPOINT_OBJVAR);
                removeObjVar(self, TARGET_WAYPOINT_OBJVAR);
                if (isIdValid(oldWaypoint))
                {
                    destroyWaypointInDatapad(oldWaypoint, self);
                }
                obj_id newWaypoint = createWaypointInDatapad(self, locationLocation);
                setWaypointName(newWaypoint, "Downed Satellite");
                setWaypointActive(newWaypoint, true);
                setObjVar(self, TARGET_WAYPOINT_OBJVAR, newWaypoint);
                sendSystemMessage(self, SID_TRACKING_TARGET_UPDATED);
                utils.setScriptVar(self, "trackingDevice.locationObject", locationObject);
                messageTo(self, "handleSetLocationTarget", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveTrackingObjVars(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, TRACKING_BASE_OBJVAR);
        obj_id trackingDevice = utils.getItemPlayerHasByTemplateInBankOrInventory(self, TRACKING_DEVICE_TEMPLATE);
        setObjVar(trackingDevice, NEEDS_TRACKING_DATA_OBJVAR, true);
        return SCRIPT_CONTINUE;
    }
    public int handlePurchaseTrackingData(obj_id self, dictionary params) throws InterruptedException
    {
        if (quests.isActive("fs_crafting4_quest_03", self))
        {
            if (!money.hasFunds(self, money.MT_TOTAL, TRACKING_DATA_COST))
            {
                String str_no_money = utils.packStringId(SID_NO_MONEY_MSG);
                sui.msgbox(self, str_no_money);
            }
            else 
            {
                obj_id starportDroid = utils.getObjIdScriptVar(self, "crafting4.starportDroid");
                utils.moneyOutMetric(self, "FS_QUESTS", TRACKING_DATA_COST);
                money.requestPayment(self, starportDroid, TRACKING_DATA_COST, "pass_fail", null, true);
                sendSystemMessage(self, SID_PURCHASE_MSG_01);
                messageTo(self, "handleTrackingDataPurchaseMsg02", null, rand(1, 4), false);
            }
            utils.removeScriptVar(self, "crafting4.starportDroid");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTrackingDataPurchaseMsg02(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, SID_PURCHASE_MSG_02);
        messageTo(self, "handleTrackingDataPurchaseMsg03", null, rand(1, 2), false);
        return SCRIPT_CONTINUE;
    }
    public int handleTrackingDataPurchaseMsg03(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, SID_PURCHASE_MSG_03);
        obj_id trackingDevice = utils.getItemPlayerHasByTemplateInBankOrInventory(self, TRACKING_DEVICE_TEMPLATE);
        removeObjVar(trackingDevice, NEEDS_TRACKING_DATA_OBJVAR);
        quests.complete("fs_crafting4_quest_03", self, true);
        String custLogMsg = "FS Phase 4 Crafting Quest: Player %TU has purchased the rights to a downed satellite.";
        CustomerServiceLog("FS_Phase4_Crafting", custLogMsg, self);
        return SCRIPT_CONTINUE;
    }
    public void deactivateCrafting4Quest(obj_id self) throws InterruptedException
    {
        String str_message = utils.packStringId(SID_CRAFTING4_PHASE_OVER);
        jedi_trials.oneButtonMsgBox(self, self, "noHandler", SID_CRAFTING4_SUI_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
        quests.deactivate("fs_crafting4_quest_06", self);
        removeObjVar(self, CRAFTING4_BASE_OBJVAR);
        detachScript(self, "quest.force_sensitive.fs_crafting4_player");
        return;
    }
}

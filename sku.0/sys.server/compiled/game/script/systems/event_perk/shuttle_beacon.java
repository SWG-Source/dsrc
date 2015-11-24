package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.create;
import script.library.event_perk;
import script.library.ai_lib;

public class shuttle_beacon extends script.base_script
{
    public shuttle_beacon()
    {
    }
    public static final String SHUTTLE_TABLE = "datatables/event_perk/shuttle_options.iff";
    public static final String DATATABLE = "datatables/event_perk/perk_data.iff";
    public static final String STF_FILE = "event_perk";
    public static final String[] SHUTTLE_TEMPLATE = 
    {
        "object/creature/npc/theme_park/lambda_shuttle.iff",
        "object/creature/npc/theme_park/player_shuttle.iff",
        "object/creature/npc/theme_park/event_transport.iff",
        "object/creature/npc/theme_park/event_transport_theed_hangar.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String name = getStringObjVar(self, "event_perk.deedName");
        int numEntries = dataTableGetNumRows(DATATABLE);
        float terminalRegistration = 999999;
        setObjVar(self, "event_perk.terminal_registration", terminalRegistration);
        if (!hasObjVar(self, "event_perk.timeStamp"))
        {
            
        }
        
        {
            float timeStamp = getGameTime();
            setObjVar(self, "event_perk.timeStamp", timeStamp);
        }
        if (!hasObjVar(self, "event_perk.deedNumber"))
        {
            for (int i = 0; i < numEntries; i++)
            {
                String currentName = dataTableGetString(DATATABLE, i, "NAME");
                if (currentName.equals(name))
                {
                    setObjVar(self, "event_perk.deedNumber", i);
                }
            }
        }
        int deedNumber = getIntObjVar(self, "event_perk.deedNumber");
        float lifeSpan = dataTableGetFloat(DATATABLE, deedNumber, "LIFESPAN");
        setObjVar(self, "event_perk.lifeSpan", lifeSpan);
        setObjVar(self, "event_perk.shuttleStatus", 0);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        String shuttleBeacon = getTemplateName(self);
        obj_id objInventory = utils.getInventoryContainer(transferer);
        int max = 0;
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory, true);
            if (objContents != null)
            {
                for (int i = 0; i < objContents.length; i++)
                {
                    String strItemTemplate = getTemplateName(objContents[i]);
                    if (strItemTemplate.equals(shuttleBeacon))
                    {
                        max++;
                        if (max > 1)
                        {
                            sendSystemMessage(transferer, new string_id(STF_FILE, "only_one_shuttle_beacon"));
                            destroyObject(self);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id shuttle = getObjIdObjVar(self, "event_perk.shuttle.shuttle");
        destroyObject(shuttle);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        checkTimeLimit(self, player);
        int shuttleStatus = getIntObjVar(self, "event_perk.shuttleStatus");
        if (!hasObjVar(self, "event_perk.owner"))
        {
            setObjVar(self, "event_perk.owner", player);
        }
        string_id callShuttle = new string_id(STF_FILE, "call_shuttle");
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, callShuttle);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int shuttleStatus = getIntObjVar(self, "event_perk.shuttleStatus");
        location here = getLocation(self);
        obj_id shuttle = getObjIdObjVar(self, "event_perk.shuttle.shuttle");
        int readyTakeOff = getIntObjVar(shuttle, "event_perk.shuttle.readyTakeOff");
        if (item == menu_info_types.ITEM_USE)
        {
            if (shuttleStatus == 0 && event_perk.canCallShuttle(self, player))
            {
                callShuttle(self, player);
            }
            if (shuttleStatus == 1 && readyTakeOff != 1)
            {
                sendSystemMessage(player, new string_id(STF_FILE, "shuttle_not_take_off"));
                return SCRIPT_CONTINUE;
            }
            if (shuttleStatus == 1 && isIdValid(shuttle) && readyTakeOff == 1)
            {
                dismissShuttle(self, player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void callShuttle(obj_id self, obj_id player) throws InterruptedException
    {
        int deedNumber = getIntObjVar(self, "event_perk.deedNumber");
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float heading = getYaw(player);
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float terminalRegistration = getFloatObjVar(self, "event_perk.terminal_registration");
        String[] rawShuttleOptions = dataTableGetStringColumn(SHUTTLE_TABLE, 0);
        String[] shuttleOptions = new String[rawShuttleOptions.length];
        for (int i = 0; i < rawShuttleOptions.length; i++)
        {
            shuttleOptions[i] = "@event_perk:shuttle_" + rawShuttleOptions[i];
        }
        utils.setScriptVar(self, "event_perk.shuttleOption", rawShuttleOptions);
        utils.setScriptVar(self, "event_perk.deedId", self);
        utils.setScriptVar(self, "event_perk.lifeSpan", lifeSpan);
        utils.setScriptVar(self, "event_perk.timeStamp", timeStamp);
        utils.setScriptVar(self, "event_perk.heading", heading);
        utils.setScriptVar(self, "event_perk.terminalRegistration", terminalRegistration);
        sui.listbox(self, player, "@event_perk:shuttle_beacon_d", sui.OK_CANCEL, "@event_perk:shuttle_beacon_t", shuttleOptions, "handleCallShuttle", true);
        return;
    }
    public int handleCallShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        location here = getLocation(player);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "event_perk.shuttleIndex", idx);
        if (event_perk.canPlaceEventPerkHere(self, player, here) && !event_perk.tooCloseToSomething(here, player, self) && !event_perk.tooManyPerks(here, player))
        {
            float lifeSpan = utils.getFloatScriptVar(self, "event_perk.lifeSpan");
            float timeStamp = utils.getFloatScriptVar(self, "event_perk.timeStamp");
            String ownerName = getFirstName(player);
            obj_id deedId = utils.getObjIdScriptVar(self, "event_perk.deedId");
            location spawnPoint = getLocation(player);
            float heading = getYaw(player);
            int shuttleType = idx;
            obj_id shuttle = create.object(SHUTTLE_TEMPLATE[idx], spawnPoint);
            setObjVar(shuttle, "event_perk.lifeSpan", lifeSpan);
            setObjVar(shuttle, "event_perk.timeStamp", timeStamp);
            setObjVar(shuttle, "event_perk.owner", player);
            setObjVar(shuttle, "event_perk.ownerName", ownerName);
            setObjVar(self, "event_perk.shuttle.shuttle", shuttle);
            setObjVar(shuttle, "event_perk.shuttle.owner", self);
            setObjVar(shuttle, "event_perk.shuttle.shuttleType", shuttleType);
            if (shuttleType < 3)
            {
                setYaw(shuttle, heading + 180);
            }
            else 
            {
                setYaw(shuttle, heading);
            }
            detachScript(shuttle, "ai.ai");
            detachScript(shuttle, "ai.creature_combat");
            detachScript(shuttle, "skeleton.humanoid");
            detachScript(shuttle, "systems.combat.combat_actions");
            detachScript(shuttle, "systems.combat.credit_for_kills");
            stop(shuttle);
            if (shuttleType == 1)
            {
                setPosture(shuttle, POSTURE_PRONE);
            }
            attachScript(shuttle, "systems.event_perk.shuttle");
            messageTo(self, "toggleShuttleStatus", null, 0, false);
            messageTo(shuttle, "landShuttle", null, .25f, false);
        }
        else 
        {
            sendSystemMessage(player, new string_id(STF_FILE, "shuttle_not_called"));
        }
        return SCRIPT_CONTINUE;
    }
    public void dismissShuttle(obj_id self, obj_id player) throws InterruptedException
    {
        messageTo(self, "handleDismissShuttle", null, 0, false);
        return;
    }
    public int handleDismissShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id shuttle = getObjIdObjVar(self, "event_perk.shuttle.shuttle");
        messageTo(shuttle, "takeOff", null, 0, false);
        messageTo(self, "toggleShuttleStatus", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        checkTimeLimit(self, player);
        return SCRIPT_CONTINUE;
    }
    public void checkTimeLimit(obj_id self, obj_id player) throws InterruptedException
    {
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - timeStamp;
        if (delta > lifeSpan)
        {
            dismissShuttle(self, player);
            destroyObject(self);
            sendSystemMessage(player, new string_id(STF_FILE, "deed_expired"));
        }
        return;
    }
    public int toggleShuttleStatus(obj_id self, dictionary params) throws InterruptedException
    {
        int shuttleStatus = getIntObjVar(self, "event_perk.shuttleStatus");
        if (shuttleStatus == 0)
        {
            setObjVar(self, "event_perk.shuttleStatus", 1);
        }
        if (shuttleStatus == 1)
        {
            setObjVar(self, "event_perk.shuttleStatus", 0);
        }
        return SCRIPT_CONTINUE;
    }
    public int getRidOfShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "event_perk.shuttle");
        return SCRIPT_CONTINUE;
    }
}

package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.money;
import script.library.factions;
import script.library.player_structure;

public class terminal extends script.base_script
{
    public terminal()
    {
    }
    public static final string_id MNU_OVERLOAD = new string_id("hq", "mnu_overload");
    public static final string_id MNU_SHUTDOWN = new string_id("hq", "mnu_shutdown");
    public static final string_id MNU_DONATE = new string_id("hq", "mnu_donate");
    public static final String SCRIPTVAR_COUNTDOWN = "countdownInProgress";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "type", "terminal");
        setObjVar(self, "intTerminal", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (pvpAreFactionsOpposed(sFac, pFac))
        {
            if (pvpGetType(player) != PVPTYPE_DECLARED)
            {
                sendSystemMessageTestingOnly(player, "Only declared factional personnel may access this terminal!");
                return SCRIPT_CONTINUE;
            }
            if (utils.hasObjVar(structure, hq.VAR_OBJECTIVE_TRACKING))
            {
                LOG("gcw", "HASOBJVAR");
                obj_id[] objectives = getObjIdArrayObjVar(structure, hq.VAR_OBJECTIVE_ID);
                if (objectives == null || objectives.length == 0)
                {
                    LOG("gcw", "returning continue");
                    return SCRIPT_CONTINUE;
                }
                obj_id[] disabled = getObjIdArrayObjVar(structure, hq.VAR_OBJECTIVE_DISABLED);
                if (disabled == null || disabled.length != objectives.length)
                {
                    LOG("gcw", "Disabled thing");
                    prose_package ppDisableOther = prose.getPackage(hq.PROSE_DISABLE_OTHER, objectives[objectives.length - 1], self);
                    sendSystemMessageProse(player, ppDisableOther);
                    return SCRIPT_CONTINUE;
                }
                int mnuCountdown = mi.addRootMenu(menu_info_types.SERVER_MENU10, MNU_OVERLOAD);
                LOG("gcw", "Added overload");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("gcw", "NO OBJVAR");
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "You are not an enemy of this structure. Why would you want to tamper?");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        LOG("gcw", "OnObject menu selected");
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (pvpAreFactionsOpposed(sFac, pFac))
        {
            if (pvpGetType(player) != PVPTYPE_DECLARED)
            {
                sendSystemMessageTestingOnly(player, "Only declared factional personnel may access this terminal!");
                return SCRIPT_CONTINUE;
            }
            if (item == menu_info_types.SERVER_MENU10)
            {
                LOG("gcw", "10");
                if (!hasObjVar(self, "intCompleted"))
                {
                    setObjVar(self, "intCompleted", 1);
                    String strText = "Facility has succesfully been taken over.";
                    sendSystemMessageTestingOnly(player, strText);
                    messageTo(structure, "objectiveCompleted", null, 0, true);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            LOG("gcw", "1");
            sendSystemMessageTestingOnly(player, "You must first select a sub-menu of 'Donate' to proceed.");
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_MENU9)
        {
            LOG("gcw", "SHUTDOWN");
        }
        return SCRIPT_CONTINUE;
    }
    public void shutdownFacility(obj_id self) throws InterruptedException
    {
        attachScript(self, hq.SCRIPT_TERMINAL_DISABLE);
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (isIdValid(structure))
        {
            hq.disableHqTerminals(structure);
        }
        abortCountdown(self);
        messageTo(self, "handleFacilityReboot", null, 300f, false);
    }
    public void startCountdown(obj_id self, obj_id player) throws InterruptedException
    {
        int meleemod = getSkillStatMod(player, "group_melee_defense");
        int rangemod = getSkillStatMod(player, "group_range_defense");
        float delay = 300f + 300f * (100f - (meleemod + rangemod)) / 100f;
        int minutes = Math.round(delay / 60f);
        obj_id[] players = player_structure.getPlayersInBuilding(getTopMostContainer(self));
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessageTestingOnly(players[i], "COUNTDOWN INITIATED: estimated time to detonation: " + minutes + " minutes.");
            }
        }
        utils.setScriptVar(self, SCRIPTVAR_COUNTDOWN, getGameTime() + delay);
        dictionary d = new dictionary();
        d.put("player", player);
        d.put("cnt", minutes);
        messageTo(self, "handleCountdown", d, 10f, false);
    }
    public void abortCountdown(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, SCRIPTVAR_COUNTDOWN);
        obj_id[] players = player_structure.getPlayersInBuilding(getTopMostContainer(self));
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessageTestingOnly(players[i], "COUNTDOWN ABORTED: FACILITY SHUTTING DOWN!!");
            }
        }
    }
    public int handleFacilityReboot(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, hq.SCRIPT_TERMINAL_DISABLE);
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (isIdValid(structure))
        {
            hq.enableHqTerminals(structure);
        }
        obj_id[] players = player_structure.getPlayersInBuilding(getTopMostContainer(self));
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessageTestingOnly(players[i], "FACILITY RESTART: RELOADING PRIMARY FACILITY SYSTEMS");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCountdown(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_COUNTDOWN))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            abortCountdown(self);
            return SCRIPT_CONTINUE;
        }
        int cnt = params.getInt("cnt");
        int stamp = utils.getIntScriptVar(self, SCRIPTVAR_COUNTDOWN);
        int now = getGameTime();
        int timeLeft = stamp - now;
        int minutes = timeLeft / 60;
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (!isIdValid(structure))
        {
            abortCountdown(self);
            return SCRIPT_CONTINUE;
        }
        if (timeLeft < 1)
        {
            utils.setScriptVar(structure, "faction_hq.detonator", player);
            hq.detonateHq(structure);
            String hqName = getName(structure);
            CustomerServiceLog("faction_hq", "Initiating destroy for Faction HQ " + hqName + " (" + structure + "), by normal terminal overload.");
            return SCRIPT_CONTINUE;
        }
        String msg = "COUNTDOWN: estimated time to detonation: " + minutes + " minutes.";
        if (minutes < 1)
        {
            msg = "COUNTDOWN: estimated time to detonation: " + timeLeft + " seconds.";
        }
        else if (minutes < cnt)
        {
            obj_id[] players = player_structure.getPlayersInBuilding(structure);
            if (players != null && players.length > 0)
            {
                for (int i = 0; i < players.length; i++)
                {
                    sendSystemMessageTestingOnly(players[i], msg);
                }
            }
            params.put("cnt", minutes);
        }
        messageTo(self, "handleCountdown", params, 10f, false);
        return SCRIPT_CONTINUE;
    }
}

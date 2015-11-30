package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.colors_hex;
import script.library.combat;
import script.library.create;
import script.library.factions;
import script.library.gcw;
import script.library.instance;
import script.library.planetary_map;
import script.library.prose;
import script.library.pvp;
import script.library.restuss_event;
import script.library.stealth;
import script.library.sui;
import script.library.utils;

public class battlefield_terminal extends script.base_script
{
    public battlefield_terminal()
    {
    }
    public static final int NO_CONTROL = factions.FACTION_FLAG_UNKNOWN;
    public static final int IMPERIAL_CONTROL = factions.FACTION_FLAG_IMPERIAL;
    public static final int REBEL_CONTROL = factions.FACTION_FLAG_REBEL;
    public static final int TERMINAL_CAPTURE_TIME = 30;
    public static final string_id SID_CONTROL_TERMINAL_MENU_USE = new string_id("gcw", "static_base_control_terminal_menu_use");
    public static final string_id SID_CONTROL_TERMINAL_ACCESS_DELAY = new string_id("gcw", "static_base_control_terminal_access_delay");
    public static final string_id SID_CONTROL_TERMINAL_CANNOT_USE = new string_id("gcw", "static_base_control_terminal_cannot_use");
    public static final string_id SID_CONTROL_TERMINAL_TOO_FAR = new string_id("gcw", "static_base_control_terminal_too_far");
    public static final string_id SID_CONTROL_TERMINAL_ALREADY_CONTROL = new string_id("gcw", "static_base_control_terminal_already_control");
    public static final string_id SID_CONTROL_TERMINAL_IN_COMBAT = new string_id("gcw", "static_base_control_terminal_in_combat");
    public static final string_id SID_CONTROL_TERMINAL_ALREADY_CAPTURING = new string_id("gcw", "static_base_control_terminal_already_capturing");
    public static final string_id SID_CONTROL_TERMINAL_ALREADY_CAPTURED = new string_id("gcw", "static_base_control_terminal_already_captured");
    public static final string_id SID_CONTROL_TERMINAL_MOVED = new string_id("gcw", "static_base_control_terminal_moved");
    public static final string_id SID_CONTROL_TERMINAL_ENTERED_COMBAT = new string_id("gcw", "static_base_control_terminal_entered_combat");
    public static final string_id SID_CONTROL_TERMINAL_INCAPACITATED = new string_id("gcw", "static_base_control_terminal_incapacitated");
    public static final string_id SID_CONTROL_TERMINAL_CAPTURE_CANCELLED = new string_id("gcw", "static_base_control_terminal_capture_cancelled");
    public static final string_id SID_CONTROL_TERMINAL_CAPTURE_SUCCESS = new string_id("gcw", "static_base_control_terminal_capture_success");
    public static final string_id SID_CONTROL_TERMINAL_SUI_PROMPT = new string_id("gcw", "static_base_control_terminal_sui_prompt");
    public static final string_id SID_CONTROL_TERMINAL_NO_STASIS = new string_id("gcw", "static_base_control_terminal_no_stasis");
    public static final string_id SID_BATTLEFIELD_TERMINAL_BATTLE_NOT_STARTED = new string_id("gcw", "battlefield_terminal_battle_not_started");
    public static final string_id SID_BATTLEFIELD_TERMINAL_DESTROYED = new string_id("gcw", "battlefield_terminal_destroyed");
    public static final String BATTLEFIELD_BUILDING_TABLE = "datatables/pvp/pvp_battlefield_building_effects.iff";
    public static final String VAR_MASTER = "gcw.static_base.master";
    public static final String VAR_TERMINAL_ID = "gcw.static_base.terminal_id";
    public static final String VAR_LAST_MESSAGE = "gcw.static_base.last_message";
    public static final String VAR_ACCESS_DELAY = "gcw.static_base.access_delay";
    public static final String VAR_ICON_OBJECT = "gcw.static_base.icon_object";
    public static final String SCRIPT_VAR_CAPTURING = "gcw.static_base.control_terminal.capturing";
    public static final String COLOR_REBELS = "\\" + colors_hex.COLOR_REBELS;
    public static final String COLOR_IMPERIALS = "\\" + colors_hex.COLOR_IMPERIALS;
    public void blog(obj_id controller, String text) throws InterruptedException
    {
        pvp.bfLog(controller, text);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerTerminal", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "registerTerminal", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isIdValid(speaker) || !isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        String[] words = split(text, ' ');
        if (words[0].equals("treset"))
        {
            terminalReset(self);
            sendSystemMessageTestingOnly(speaker, "Terminal Reset.");
        }
        if (words[0].equals("blow"))
        {
            utils.setScriptVar(self, "battlefield.terminal_mitigation", 0);
            blowUpBuilding(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int registerTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        registerTerminalWithController(self);
        return SCRIPT_CONTINUE;
    }
    public obj_id getLocalBattlefieldController(obj_id self) throws InterruptedException
    {
        obj_id controller = null;
        if (utils.hasScriptVar(self, "battlefield.controller"))
        {
            controller = utils.getObjIdScriptVar(self, "battlefield.controller");
        }
        if (!isIdValid(controller) || !exists(controller))
        {
            controller = gcw.getPvpRegionControllerIdByPlayer(self);
        }
        if (!isIdValid(controller) || !exists(controller))
        {
            return null;
        }
        utils.setScriptVar(self, "battlefield.controller", controller);
        return controller;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        mid.setLabel(SID_CONTROL_TERMINAL_MENU_USE);
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (!pvp.bfTerminalIsRegistered(controller, self))
        {
            pvp.bfTerminalRegister(controller, self);
            terminalReset(self);
            blog(controller, "Battlefield terminal was not registered.");
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!utils.hasScriptVar(player, "battlefield.active"))
            {
                return SCRIPT_CONTINUE;
            }
            int capturesLeft = utils.getIntScriptVar(self, "battlefield.terminal_captures_left");
            if (capturesLeft < 1)
            {
                sendSystemMessage(player, SID_BATTLEFIELD_TERMINAL_DESTROYED);
                return SCRIPT_CONTINUE;
            }
            int bfState = utils.getIntScriptVar(controller, "battlefield.state");
            if (bfState != pvp.PVP_STATE_BATTLE_ENGAGED)
            {
                sendSystemMessage(player, SID_BATTLEFIELD_TERMINAL_BATTLE_NOT_STARTED);
                return SCRIPT_CONTINUE;
            }
            if (getDistance(self, player) > 5.0f)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_TOO_FAR);
                return SCRIPT_CONTINUE;
            }
            int playerFaction = factions.getFactionFlag(player);
            if (isDead(player) || isIncapacitated(player) || playerFaction == factions.FACTION_FLAG_NEUTRAL || factions.isOnLeave(player))
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_CANNOT_USE);
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, VAR_ACCESS_DELAY))
            {
                int accessTime = getIntObjVar(self, VAR_ACCESS_DELAY);
                int gameTime = getGameTime();
                if (accessTime > gameTime)
                {
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, SID_CONTROL_TERMINAL_ACCESS_DELAY);
                    pp = prose.setDI(pp, (accessTime - gameTime));
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    removeObjVar(self, VAR_ACCESS_DELAY);
                }
            }
            int terminalFaction = utils.getIntScriptVar(self, "battlefield.terminal_faction");
            if (playerFaction == terminalFaction)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_ALREADY_CONTROL);
                return SCRIPT_CONTINUE;
            }
            if (buff.hasBuff(player, "me_stasis_1") || buff.hasBuff(player, "me_stasis_self_1"))
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_NO_STASIS);
                return SCRIPT_CONTINUE;
            }
            if (pvp.bfGetBattlefieldType(controller) == pvp.BATTLEFIELD_TYPE_CAPTURE_THE_FLAG)
            {
                if (utils.hasScriptVar(self, "battlefield.terminalRunner"))
                {
                    obj_id runner = utils.getObjIdScriptVar(self, "battlefield.terminalRunner");
                    if (isIdValid(runner) && runner != player)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (utils.hasScriptVar(player, SCRIPT_VAR_CAPTURING))
            {
                int captureTime = utils.getIntScriptVar(player, SCRIPT_VAR_CAPTURING);
                int gameTime = getGameTime();
                if ((gameTime - captureTime) > (TERMINAL_CAPTURE_TIME + 10))
                {
                    utils.removeScriptVar(player, SCRIPT_VAR_CAPTURING);
                }
                else 
                {
                    sendSystemMessage(player, SID_CONTROL_TERMINAL_ALREADY_CAPTURING);
                    return SCRIPT_CONTINUE;
                }
            }
            if (utils.hasScriptVar(self, SCRIPT_VAR_CAPTURING))
            {
                int captureTime = utils.getIntScriptVar(self, SCRIPT_VAR_CAPTURING);
                int gameTime = getGameTime();
                if ((gameTime - captureTime) > (TERMINAL_CAPTURE_TIME + 10))
                {
                    utils.removeScriptVar(self, SCRIPT_VAR_CAPTURING);
                }
                else 
                {
                    sendSystemMessage(player, SID_CONTROL_TERMINAL_ALREADY_CAPTURED);
                    return SCRIPT_CONTINUE;
                }
            }
            String terminalStringFaction = convertIntFactionToString(terminalFaction);
            startControlAttempt(self, player, terminalStringFaction);
        }
        return SCRIPT_CONTINUE;
    }
    public void startControlAttempt(obj_id self, obj_id player, String terminalFaction) throws InterruptedException
    {
        int gameTime = getGameTime();
        utils.setScriptVar(self, SCRIPT_VAR_CAPTURING, gameTime);
        utils.setScriptVar(player, SCRIPT_VAR_CAPTURING, gameTime);
        stealth.testInvisCombatAction(player, self);
        int flags = 0;
        flags |= sui.CD_EVENT_LOCOMOTION;
        flags |= sui.CD_EVENT_INCAPACITATE;
        flags |= sui.CD_EVENT_DAMAGED;
        flags |= sui.CD_EVENT_STEALTHED;
        flags |= sui.CD_EVENT_DAMAGE_IMMUNE;
        int captureTime = TERMINAL_CAPTURE_TIME;
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        if (isGod(player) || pvp.bfGetBattlefieldType(controller) == pvp.BATTLEFIELD_TYPE_CAPTURE_THE_FLAG)
        {
            captureTime = 3;
        }
        String terminalName = getStringObjVar(self, "battlefield.terminalName");
        if (terminalName != null && terminalName.length() > 0)
        {
            if (pvp.bfGetBattlefieldType(controller) != pvp.BATTLEFIELD_TYPE_CAPTURE_THE_FLAG)
            {
                pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_terminal_capture_attempt_" + terminalName));
            }
            dictionary dict = new dictionary();
            dict.put("terminal_location", getWorldLocation(self));
            dict.put("terminal", self);
            pvp.bfMessagePlayersOnBattlefield(controller, terminalFaction, "createBattlefieldWaypoint", dict);
        }
        int pid = sui.smartCountdownTimerSUI(self, player, "gcwStaticBaseControlTerminal", SID_CONTROL_TERMINAL_SUI_PROMPT, 0, captureTime, "handleControlAttemptResults", 4.0f, flags);
    }
    public String convertIntFactionToString(int faction) throws InterruptedException
    {
        if (faction == 1)
        {
            return pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS;
        }
        else if (faction == 2)
        {
            return pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS;
        }
        else 
        {
            return null;
        }
    }
    public void registerTerminalWithController(obj_id self) throws InterruptedException
    {
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            messageTo(self, "registerTerminal", null, 1.0f, false);
            return;
        }
        pvp.bfTerminalRegister(controller, self);
    }
    public void cleanupWaypoint(obj_id controller, obj_id terminal) throws InterruptedException
    {
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        dictionary dict = new dictionary();
        dict.put("terminal", terminal);
        pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "destroyBattlefieldWaypoint", dict);
        pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "destroyBattlefieldWaypoint", dict);
    }
    public int handleControlAttemptResults(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        obj_id controller = getLocalBattlefieldController(self);
        String terminalFaction = convertIntFactionToString(utils.getIntScriptVar(self, "battlefield.terminal_faction"));
        if (!isIdValid(player) || !exists(player))
        {
            cleanupWaypoint(controller, self);
            return SCRIPT_CONTINUE;
        }
        int faction = factions.getFactionFlag(player);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, SCRIPT_VAR_CAPTURING);
            utils.removeScriptVar(self, SCRIPT_VAR_CAPTURING);
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            sendSystemMessage(player, SID_CONTROL_TERMINAL_CAPTURE_CANCELLED);
            cleanupWaypoint(controller, self);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            utils.removeScriptVar(player, SCRIPT_VAR_CAPTURING);
            utils.removeScriptVar(self, SCRIPT_VAR_CAPTURING);
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_COMBAT)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_ENTERED_COMBAT);
            }
            else if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_MOVED);
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_INCAPACITATED);
            }
            cleanupWaypoint(controller, self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            cleanupWaypoint(controller, self);
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            cleanupWaypoint(controller, self);
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        utils.removeScriptVar(player, SCRIPT_VAR_CAPTURING);
        utils.removeScriptVar(self, SCRIPT_VAR_CAPTURING);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        String iconTemplate = "";
        int mitigation = getIntObjVar(self, "battlefield.terminal_mitigation");
        if (mitigation < 1)
        {
            mitigation = 1;
        }
        if (isIdValid(controller) && exists(controller))
        {
            int capturesLeft = utils.getIntScriptVar(self, "battlefield.terminal_captures_left");
            boolean captured = false;
            int bfState = utils.getIntScriptVar(controller, "battlefield.state");
            if (bfState != pvp.PVP_STATE_BATTLE_ENGAGED)
            {
                cleanupWaypoint(controller, self);
                return SCRIPT_CONTINUE;
            }
            switch (pvp.bfGetBattlefieldType(controller))
            {
                case pvp.BATTLEFIELD_TYPE_REINFORCEMENTS:
                
                {
                    if (capturesLeft > 1)
                    {
                        if (faction == factions.FACTION_FLAG_REBEL)
                        {
                            setName(self, new string_id("gcw", "battlefield_terminal_rebel"));
                            iconTemplate = "object/tangible/gcw/static_base/rebel_icon.iff";
                        }
                        else if (faction == factions.FACTION_FLAG_IMPERIAL)
                        {
                            setName(self, new string_id("gcw", "battlefield_terminal_imperial"));
                            iconTemplate = "object/tangible/gcw/static_base/imperial_icon.iff";
                        }
                        capturesLeft--;
                        utils.setScriptVar(self, "battlefield.terminal_captures_left", capturesLeft);
                        captured = true;
                        utils.setScriptVar(self, "battlefield.terminal_mitigation", mitigation);
                    }
                    else 
                    {
                        if (capturesLeft == 1)
                        {
                            captured = true;
                        }
                        faction = 0;
                        utils.setScriptVar(self, "battlefield.terminal_captures_left", 0);
                        utils.setScriptVar(self, "battlefield.terminal_mitigation", 0);
                        blowUpBuilding(self);
                    }
                    String terminalName = getStringObjVar(self, "battlefield.terminalName");
                    blog(controller, "terminalName: " + terminalName + " captured: " + captured + " by: " + getName(player));
                    if (terminalName != null && terminalName.length() > 0 && captured)
                    {
                        pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_terminal_capture_completed_" + terminalName));
                    }
                    if (captured)
                    {
                        pvp.bfCreditForCapture(player, 1);
                        int gcwCredits = gcw.BATTLEFIELD_TERMINAL_CAPTURE_MULTIPLIER * mitigation;
                        params = new dictionary();
                        params.put("gcwCredits", gcwCredits);
                        if (gcwCredits > 0)
                        {
                            int playerFaction = factions.getFactionFlag(player);
                            if (playerFaction == factions.FACTION_FLAG_REBEL)
                            {
                                pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldTerminalCapture", params);
                            }
                            else if (playerFaction == factions.FACTION_FLAG_IMPERIAL)
                            {
                                pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldTerminalCapture", params);
                            }
                        }
                    }
                    break;
                }
                case pvp.BATTLEFIELD_TYPE_CAPTURE_THE_FLAG:
                
                {
                    if (!buff.hasBuff(player, "battlefield_communication_run"))
                    {
                        cleanupWaypoint(controller, self);
                        return SCRIPT_CONTINUE;
                    }
                    buff.removeBuff(player, "battlefield_communication_run");
                    pvp.bfClearRunner(controller);
                    utils.removeScriptVar(self, "battlefield.terminalRunner");
                    pvp.bfCreditForCapture(player, 1);
                    String terminalName = getStringObjVar(self, "battlefield.terminalName");
                    blog(controller, "terminalName: " + terminalName + " flag captured by: " + getName(player));
                    int gcwCredits = gcw.BATTLEFIELD_TERMINAL_CAPTURE_MULTIPLIER * mitigation;
                    params = new dictionary();
                    params.put("gcwCredits", gcwCredits);
                    if (gcwCredits > 0)
                    {
                        int playerFaction = factions.getFactionFlag(player);
                        if (playerFaction == factions.FACTION_FLAG_REBEL)
                        {
                            pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_rebel_flag_capture"));
                            pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_REBEL_PLAYERS, "receiveBattlefieldTerminalCapture", params);
                        }
                        else if (playerFaction == factions.FACTION_FLAG_IMPERIAL)
                        {
                            pvp.bfActivePlayersAnnounce(controller, new string_id("spam", "battlefield_imperial_flag_capture"));
                            pvp.bfMessagePlayersOnBattlefield(controller, pvp.BATTLEFIELD_ACTIVE_IMPERIAL_PLAYERS, "receiveBattlefieldTerminalCapture", params);
                        }
                    }
                    faction = 0;
                    break;
                }
            }
        }
        else 
        {
            cleanupWaypoint(controller, self);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, VAR_ICON_OBJECT))
        {
            obj_id oldIcon = getObjIdObjVar(self, VAR_ICON_OBJECT);
            destroyObject(oldIcon);
        }
        if (faction != 0)
        {
            utils.setScriptVar(self, "battlefield.terminal_faction", faction);
            location iconLoc = getLocation(self);
            iconLoc.y += 2.0f;
            obj_id newIcon = create.object(iconTemplate, iconLoc);
            if (isIdValid(newIcon))
            {
                setObjVar(self, VAR_ICON_OBJECT, newIcon);
            }
            updateMapLabel(self);
        }
        cleanupWaypoint(controller, self);
        return SCRIPT_CONTINUE;
    }
    public void updateMapLabel(obj_id self) throws InterruptedException
    {
        obj_id topMost = getTopMostContainer(self);
        if (!isIdValid(topMost) || !exists(topMost))
        {
            return;
        }
        location here = getWorldLocation(topMost);
        int faction = utils.getIntScriptVar(self, "battlefield.terminal_faction");
        String terminalName = "unknown";
        String color = "";
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            color = COLOR_REBELS;
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            color = COLOR_IMPERIALS;
        }
        if (hasObjVar(self, "battlefield.terminalName"))
        {
            terminalName = getStringObjVar(self, "battlefield.terminalName");
        }
        String name = color + localize(new string_id("spam", terminalName));
        String sub_cat = "terminal_gcw_static_base";
        addPlanetaryMapLocationIgnoreLocationCountLimits(self, name, (int)here.x, (int)here.z - 10, planetary_map.CAT_TERMINAL, sub_cat, MLT_STATIC, planetary_map.NO_FLAG);
    }
    public void terminalReset(obj_id self) throws InterruptedException
    {
        String iconTemplate = "";
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            location loc = getLocation(self);
            blog(self, "Battlefield Terminal is invalid at " + loc);
            return;
        }
        int faction = getIntObjVar(self, "battlefield.terminal_faction");
        if (faction == factions.FACTION_FLAG_REBEL)
        {
            utils.setScriptVar(self, "battlefield.terminal_faction", factions.FACTION_FLAG_REBEL);
            iconTemplate = "object/tangible/gcw/static_base/rebel_icon.iff";
            setName(self, new string_id("gcw", "battlefield_terminal_rebel"));
        }
        else if (faction == factions.FACTION_FLAG_IMPERIAL)
        {
            utils.setScriptVar(self, "battlefield.terminal_faction", factions.FACTION_FLAG_IMPERIAL);
            iconTemplate = "object/tangible/gcw/static_base/imperial_icon.iff";
            setName(self, new string_id("gcw", "battlefield_terminal_imperial"));
        }
        else 
        {
            if (pvp.bfGetBattlefieldType(controller) != pvp.BATTLEFIELD_TYPE_CAPTURE_THE_FLAG)
            {
                utils.setScriptVar(self, "battlefield.terminal_faction", factions.FACTION_FLAG_NEUTRAL);
                setName(self, new string_id("gcw", "battlefield_terminal_n"));
                iconTemplate = "";
            }
        }
        int capturesLeft = getIntObjVar(self, "battlefield.terminal_maximum_captures");
        utils.setScriptVar(self, "battlefield.terminal_captures_left", capturesLeft);
        int mitigation = getIntObjVar(self, "battlefield.terminal_mitigation");
        utils.setScriptVar(self, "battlefield.terminal_mitigation", mitigation);
        if (hasObjVar(self, VAR_ICON_OBJECT))
        {
            obj_id oldIcon = getObjIdObjVar(self, VAR_ICON_OBJECT);
            destroyObject(oldIcon);
        }
        if (!iconTemplate.equals(""))
        {
            location iconLoc = getLocation(self);
            iconLoc.y += 2.0f;
            obj_id newIcon = create.object(iconTemplate, iconLoc);
            if (isIdValid(newIcon))
            {
                setObjVar(self, VAR_ICON_OBJECT, newIcon);
            }
        }
        String terminalName = "unknown";
        if (hasObjVar(self, "battlefield.terminalName"))
        {
            terminalName = getStringObjVar(self, "battlefield.terminalName");
        }
        updateMapLabel(self);
        blog(controller, "Battlefield terminal reset for faction: " + faction + " terminalName: " + terminalName);
    }
    public int receiveBattlefieldReset(obj_id self, dictionary params) throws InterruptedException
    {
        terminalReset(self);
        return SCRIPT_CONTINUE;
    }
    public void playEffectAtLoc(String effect, location loc, obj_id[] playerList) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id newObject = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        setObjVar(newObject, restuss_event.EFFECT_NAME, effect);
        setObjVar(newObject, restuss_event.EFFECT_VISABILITY, "visibility-200");
        setObjVar(newObject, restuss_event.EFFECT_DELTA, "0");
        if (playerList != null)
        {
            utils.setScriptVar(newObject, instance.PLAYER_ID_LIST, playerList);
        }
        attachScript(newObject, "theme_park.restuss_event.restuss_clientfx_controller");
    }
    public void playEffectAtLoc(String effect, location loc, Vector playerList) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id newObject = createObject("object/tangible/theme_park/invisible_object.iff", loc);
        setObjVar(newObject, restuss_event.EFFECT_NAME, effect);
        setObjVar(newObject, restuss_event.EFFECT_VISABILITY, "visibility-200");
        setObjVar(newObject, restuss_event.EFFECT_DELTA, "0");
        if (playerList != null)
        {
            utils.setScriptVar(newObject, instance.PLAYER_ID_LIST, playerList);
        }
        attachScript(newObject, "theme_park.restuss_event.restuss_clientfx_controller");
    }
    public void blowUpBuilding(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return;
        }
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            return;
        }
        obj_id topMost = getTopMostContainer(self);
        if (!isIdValid(topMost) || !exists(topMost))
        {
            return;
        }
        String template = getTemplateName(topMost);
        dictionary dict = utils.dataTableGetRow(BATTLEFIELD_BUILDING_TABLE, template);
        if (dict == null)
        {
            return;
        }
        String buildingTable = dict.getString("building_table");
        if (buildingTable == null || buildingTable.length() < 1)
        {
            return;
        }
        int numRows = dataTableGetNumRows(buildingTable);
        if (numRows <= 0)
        {
            return;
        }
        Vector playerList = utils.getResizeableObjIdBatchScriptVar(controller, pvp.BATTLEFIELD_ACTIVE_PLAYERS);
        for (int i = 0; i < numRows; i++)
        {
            dictionary row = dataTableGetRow(buildingTable, i);
            if (row == null)
            {
                continue;
            }
            String effect = row.getString("name");
            String room = row.getString("room");
            location loc = getLocation(self);
            if (room != null && room.length() > 0)
            {
                loc.x = row.getFloat("loc_x");
                loc.y = row.getFloat("loc_y");
                loc.z = row.getFloat("loc_z");
                loc.cell = getCellId(topMost, room);
            }
            else 
            {
                loc = getLocation(topMost);
                loc.x += row.getFloat("loc_x");
                loc.y += row.getFloat("loc_y");
                loc.z += row.getFloat("loc_z");
            }
            dictionary params = new dictionary();
            params.put("loc", loc);
            params.put("effect", effect);
            if (playerList != null)
            {
                params.put("playerList", playerList);
            }
            float delay = row.getFloat("delay");
            messageTo(self, "effectDelay", params, delay, false);
        }
        messageTo(self, "sparkTerminal", null, 2.0f, false);
        messageTo(self, "buildingSmoke", null, 2.0f, false);
    }
    public int effectDelay(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        location loc = params.getLocation("loc");
        String effect = params.getString("effect");
        obj_id[] playerList = params.getObjIdArray("playerList");
        playEffectAtLoc(effect, loc, playerList);
        return SCRIPT_CONTINUE;
    }
    public int sparkTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            blog(self, "Battlefield Terminal is invalid at " + loc);
            return SCRIPT_CONTINUE;
        }
        Vector playerList = utils.getResizeableObjIdBatchScriptVar(controller, pvp.BATTLEFIELD_ACTIVE_PLAYERS);
        playEffectAtLoc("pt_electricity_battlefield_terminal.prt", loc, playerList);
        int mitigation = utils.getIntScriptVar(self, "battlefield.terminal_mitigation");
        if (mitigation <= 0)
        {
            messageTo(self, "sparkTerminal", null, 2.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int buildingSmoke(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id topMost = getTopMostContainer(self);
        if (!isIdValid(topMost) || !exists(topMost))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(topMost);
        obj_id controller = getLocalBattlefieldController(self);
        if (!isIdValid(controller) || !exists(controller))
        {
            blog(self, "Battlefield Terminal is invalid at " + loc);
            return SCRIPT_CONTINUE;
        }
        Vector playerList = utils.getResizeableObjIdBatchScriptVar(controller, pvp.BATTLEFIELD_ACTIVE_PLAYERS);
        playEffectAtLoc("pt_smoke_large_battlefield.prt", loc, playerList);
        int mitigation = utils.getIntScriptVar(self, "battlefield.terminal_mitigation");
        if (mitigation <= 0)
        {
            messageTo(self, "buildingSmoke", null, 6.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}

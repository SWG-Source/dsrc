package script.systems.gcw.static_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.colors_hex;
import script.library.combat;
import script.library.create;
import script.library.planetary_map;
import script.library.prose;
import script.library.stealth;
import script.library.sui;
import script.library.utils;

public class control_terminal extends script.base_script
{
    public control_terminal()
    {
    }
    public static final int NO_CONTROL = 0;
    public static final int IMPERIAL_CONTROL = 1;
    public static final int REBEL_CONTROL = 2;
    public static final int TERMINAL_CAPTURE_TIME = 15;
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
    public static final String VAR_MASTER = "gcw.static_base.master";
    public static final String VAR_TERMINAL_ID = "gcw.static_base.terminal_id";
    public static final String VAR_LAST_MESSAGE = "gcw.static_base.last_message";
    public static final String VAR_ACCESS_DELAY = "gcw.static_base.access_delay";
    public static final String VAR_ICON_OBJECT = "gcw.static_base.icon_object";
    public static final String SCRIPT_VAR_CAPTURING = "gcw.static_base.control_terminal.capturing";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        registerTerminalWithMap(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        registerTerminalWithMap(self);
        return SCRIPT_CONTINUE;
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
        if (item == menu_info_types.ITEM_USE)
        {
            if (getDistance(self, player) > 3.0f)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_TOO_FAR);
                return SCRIPT_CONTINUE;
            }
            int playerFaction = pvpGetAlignedFaction(player);
            if (isDead(player) || isIncapacitated(player) || playerFaction == 0)
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
            int terminalFaction = pvpGetAlignedFaction(self);
            if (playerFaction == terminalFaction)
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_ALREADY_CONTROL);
                return SCRIPT_CONTINUE;
            }
            if (combat.isInCombat(player))
            {
                sendSystemMessage(player, SID_CONTROL_TERMINAL_IN_COMBAT);
                return SCRIPT_CONTINUE;
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
            startControlAttempt(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void startControlAttempt(obj_id self, obj_id player) throws InterruptedException
    {
        int gameTime = getGameTime();
        utils.setScriptVar(self, SCRIPT_VAR_CAPTURING, gameTime);
        utils.setScriptVar(player, SCRIPT_VAR_CAPTURING, gameTime);
        stealth.testInvisCombatAction(player, self);
        int flags = 0;
        flags |= sui.CD_EVENT_COMBAT;
        flags |= sui.CD_EVENT_LOCOMOTION;
        flags |= sui.CD_EVENT_INCAPACITATE;
        int captureTime = TERMINAL_CAPTURE_TIME;
        if (isGod(player))
        {
            captureTime = 3;
        }
        int pid = sui.smartCountdownTimerSUI(self, player, "gcwStaticBaseControlTerminal", SID_CONTROL_TERMINAL_SUI_PROMPT, 0, captureTime, "handleControlAttemptResults", 4.0f, flags);
    }
    public void registerTerminalWithMap(obj_id self) throws InterruptedException
    {
        int terminalFaction = pvpGetAlignedFaction(self);
        String color = "";
        if (terminalFaction == (-615855020))
        {
            color = "\\" + colors_hex.COLOR_IMPERIALS + " ";
        }
        else if (terminalFaction == (370444368))
        {
            color = "\\" + colors_hex.COLOR_REBELS + " ";
        }
        String name = color + getString(utils.unpackString(getEncodedName(self)));
        location here = getWorldLocation(self);
        String sub_cat = "terminal_gcw_static_base";
        addPlanetaryMapLocationIgnoreLocationCountLimits(self, name, (int)here.x, (int)here.z, planetary_map.CAT_TERMINAL, sub_cat, MLT_STATIC, planetary_map.NO_FLAG);
    }
    public int handleControlAttemptResults(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int faction = pvpGetAlignedFaction(player);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, SCRIPT_VAR_CAPTURING);
            utils.removeScriptVar(self, SCRIPT_VAR_CAPTURING);
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            sendSystemMessage(player, SID_CONTROL_TERMINAL_CAPTURE_CANCELLED);
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
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        utils.removeScriptVar(player, SCRIPT_VAR_CAPTURING);
        utils.removeScriptVar(self, SCRIPT_VAR_CAPTURING);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        obj_id master = getObjIdObjVar(self, VAR_MASTER);
        String iconTemplate = "";
        if (isIdValid(master))
        {
            location masterLoc = getLocation(master);
            string_id message = new string_id();
            prose_package pp = new prose_package();
            int terminalId = -1;
            if (hasObjVar(self, VAR_TERMINAL_ID))
            {
                terminalId = getIntObjVar(self, VAR_TERMINAL_ID);
            }
            int control = -1;
            if (faction == (370444368))
            {
                control = REBEL_CONTROL;
                message = new string_id("gcw", "static_base_attack_rebel_" + masterLoc.area);
                pp = prose.setStringId(pp, new string_id("gcw", "static_base_control_terminal_capture_rebel"));
                iconTemplate = "object/tangible/gcw/static_base/rebel_icon.iff";
            }
            else if (faction == (-615855020))
            {
                control = IMPERIAL_CONTROL;
                message = new string_id("gcw", "static_base_attack_imperial_" + masterLoc.area);
                pp = prose.setStringId(pp, new string_id("gcw", "static_base_control_terminal_capture_imperial"));
                iconTemplate = "object/tangible/gcw/static_base/imperial_icon.iff";
            }
            dictionary d = new dictionary();
            d.put("id", terminalId);
            d.put("control", control);
            messageTo(master, "handleControlUpdate", d, 0.0f, false);
            int lastMessageTime = 0;
            if (hasObjVar(master, VAR_LAST_MESSAGE))
            {
                lastMessageTime = getIntObjVar(master, VAR_LAST_MESSAGE);
            }
            if ((getGameTime() - lastMessageTime) > 1800)
            {
                sendFactionalSystemMessagePlanet(message, null, -1f, true, true);
                setObjVar(master, VAR_LAST_MESSAGE, getGameTime());
            }
            pp = prose.setTT(pp, player);
            pp = prose.setTU(pp, self);
            sendFactionalSystemMessagePlanet(pp, masterLoc, 200f, true, true);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, VAR_ICON_OBJECT))
        {
            obj_id oldIcon = getObjIdObjVar(self, VAR_ICON_OBJECT);
            destroyObject(oldIcon);
        }
        if (faction != 0)
        {
            pvpSetAlignedFaction(self, faction);
            location iconLoc = getLocation(self);
            iconLoc.y += 2.0f;
            obj_id newIcon = create.object(iconTemplate, iconLoc);
            if (isIdValid(newIcon))
            {
                setObjVar(self, VAR_ICON_OBJECT, newIcon);
            }
        }
        registerTerminalWithMap(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTerminalInitialization(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId("master");
        int id = params.getInt("id");
        int control = params.getInt("control");
        setObjVar(self, VAR_MASTER, master);
        setObjVar(self, VAR_TERMINAL_ID, id);
        String iconTemplate = "";
        if (control == REBEL_CONTROL)
        {
            pvpSetAlignedFaction(self, (370444368));
            iconTemplate = "object/tangible/gcw/static_base/rebel_icon.iff";
        }
        else if (control == IMPERIAL_CONTROL)
        {
            pvpSetAlignedFaction(self, (-615855020));
            iconTemplate = "object/tangible/gcw/static_base/imperial_icon.iff";
        }
        else 
        {
            pvpSetAlignedFaction(self, 0);
        }
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
        setName(self, new string_id("gcw", "static_base_control_terminal_name_" + id));
        dictionary d = new dictionary();
        d.put("id", id);
        d.put("control", control);
        messageTo(master, "handleControlUpdate", d, 5.0f, false);
        registerTerminalWithMap(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTerminalValidationRequest(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId("master");
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        int response = 1;
        setObjVar(self, VAR_MASTER, master);
        int id = -1;
        if (hasObjVar(self, VAR_TERMINAL_ID))
        {
            id = getIntObjVar(self, VAR_TERMINAL_ID);
        }
        int faction = pvpGetAlignedFaction(self);
        int control = NO_CONTROL;
        if (faction == (370444368))
        {
            control = REBEL_CONTROL;
        }
        else if (faction == (-615855020))
        {
            control = IMPERIAL_CONTROL;
        }
        dictionary d = new dictionary();
        d.put("response", response);
        d.put("id", id);
        d.put("control", control);
        messageTo(master, "handleTerminalValidationResponse", d, 0.0f, false);
        messageTo(master, "handleControlUpdate", d, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTerminalDestructionRequest(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}

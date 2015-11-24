package script.systems.crafting.droid.modules;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.chat;
import script.library.sui;
import script.library.pet_lib;
import script.library.utils;
import script.ai.pet;

public class merchant_barker extends script.base_script
{
    public merchant_barker()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final String SCRIPT_VAR_BARKING_ON = "module.barking_on";
    public static final String SCRIPT_VAR_RECORDING_ON = "module.recording_on";
    public static final String SCRIPT_VAR_WAYPOINT_SUI = "module.waypoint_sui";
    public static final String SCRIPT_VAR_WAYPOINT_LIST = "module.waypoint_list";
    public static final String VAR_MERCHANT_BARKER = "module_data.merchant_barker";
    public static final String VAR_BARK_MESSAGE = "module_data.bark_message";
    public static final String VAR_BARK_WAYPOINT_LOC = "module_data.bark_waypoint_loc";
    public static final String VAR_BARK_WAYPOINT_NAME = "module_data.bark_waypoint_name";
    public static final string_id SID_NO_FREE_TRIAL = new string_id("base_player", "no_free_trial_barker");
    
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(pet.BARK_TRIGGER_VOLUME, pet.BARK_RANGE, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(STF_FILE, "merchant_barker"));
        if (player == getMaster(self))
        {
            if (hasCommand(player, "ad_fees_1"))
            {
                mi.addSubMenu(mnu, menu_info_types.SERVER_MENU2, new string_id(STF_FILE, "record_message"));
                mi.addSubMenu(mnu, menu_info_types.SERVER_MENU3, new string_id(STF_FILE, "store_waypoint"));
                mi.addSubMenu(mnu, menu_info_types.SERVER_MENU4, new string_id(STF_FILE, "barker_on_off"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.SERVER_MENU1 && item != menu_info_types.SERVER_MENU2 && item != menu_info_types.SERVER_MENU3 && item != menu_info_types.SERVER_MENU4)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, new string_id(STF_FILE, "not_enough_power"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (hasObjVar(self, VAR_BARK_WAYPOINT_NAME))
            {
                String name = getStringObjVar(self, VAR_BARK_WAYPOINT_NAME);
                location loc = getLocationObjVar(self, VAR_BARK_WAYPOINT_LOC);
                if (name == null)
                {
                    LOG("droid_module", "merchant_barker.OnObjectMenuSelect -- waypoint name is null for " + self);
                    return SCRIPT_CONTINUE;
                }
                if (loc == null)
                {
                    LOG("droid_module", "merchant_barker.OnObjectMenuSelect -- location is null for " + self);
                    return SCRIPT_CONTINUE;
                }
                obj_id datapad = utils.getPlayerDatapad(player);
                if (!isIdValid(datapad))
                {
                    LOG("droid_module", "merchant_barker.OnObjectMenuSelect -- the datapad for " + self + " is null.");
                    return SCRIPT_CONTINUE;
                }
                if (getVolumeFree(datapad) < 1)
                {
                    sendSystemMessage(player, new string_id(STF_FILE, "not_space_in_datapad"));
                    return SCRIPT_CONTINUE;
                }
                obj_id waypoint = createWaypointInDatapad(player, loc);
                setWaypointName(waypoint, name);
                sendSystemMessage(player, new string_id(STF_FILE, "waypoint_sent"));
            }
            else 
            {
                sendSystemMessage(player, new string_id(STF_FILE, "no_waypoint"));
            }
            return SCRIPT_CONTINUE;
        }
        if (player == getMaster(self))
        {
            if (hasCommand(player, "ad_fees_1"))
            {
                if (item == menu_info_types.SERVER_MENU2)
                {
                    if (isFreeTrialAccount(player))
                    {
                        sendSystemMessage(player, SID_NO_FREE_TRIAL);
                        return SCRIPT_CONTINUE;
                    }
                    if (utils.hasScriptVar(self, SCRIPT_VAR_RECORDING_ON))
                    {
                        sendSystemMessage(player, new string_id(STF_FILE, "recording_message_off"));
                        utils.removeScriptVar(self, SCRIPT_VAR_RECORDING_ON);
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id(STF_FILE, "recording_message_on"));
                        utils.setScriptVar(self, SCRIPT_VAR_RECORDING_ON, 1);
                    }
                }
                if (item == menu_info_types.SERVER_MENU3)
                {
                    if (isFreeTrialAccount(player))
                    {
                        sendSystemMessage(player, SID_NO_FREE_TRIAL);
                        return SCRIPT_CONTINUE;
                    }
                    obj_id waypoints[] = getWaypointsInDatapad(player);
                    if (waypoints != null && waypoints.length > 0)
                    {
                        if (utils.hasScriptVar(player, SCRIPT_VAR_WAYPOINT_SUI))
                        {
                            forceCloseSUIPage(utils.getIntScriptVar(player, SCRIPT_VAR_WAYPOINT_SUI));
                        }
                        String[] waypoint_names = new String[waypoints.length];
                        for (int i = 0; i < waypoint_names.length; i++)
                        {
                            waypoint_names[i] = getWaypointName(waypoints[i]);
                        }
                        String prompt = "@" + STF_FILE + ":select_waypoint";
                        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, prompt, waypoint_names, "msgDroidWaypointSelected");
                        utils.setScriptVar(player, SCRIPT_VAR_WAYPOINT_SUI, pid);
                        utils.setScriptVar(self, SCRIPT_VAR_WAYPOINT_LIST, waypoints);
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id(STF_FILE, "no_waypoints_in_datapad"));
                    }
                }
                if (item == menu_info_types.SERVER_MENU4)
                {
                    if (isFreeTrialAccount(player))
                    {
                        sendSystemMessage(player, SID_NO_FREE_TRIAL);
                        return SCRIPT_CONTINUE;
                    }
                    if (!utils.hasScriptVar(self, SCRIPT_VAR_BARKING_ON))
                    {
                        if (!hasObjVar(self, VAR_BARK_MESSAGE))
                        {
                            sendSystemMessage(player, new string_id(STF_FILE, "no_message_to_bark"));
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id(STF_FILE, "barking_on"));
                            utils.setScriptVar(self, SCRIPT_VAR_BARKING_ON, getGameTime());
                            messageTo(self, "msgMerchantBark", null, 10.0f, false);
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id(STF_FILE, "barking_off"));
                        utils.removeScriptVar(self, SCRIPT_VAR_BARKING_ON);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id player, String message) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, SCRIPT_VAR_RECORDING_ON))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == getMaster(self))
        {
            if (message.length() > 128)
            {
                sendSystemMessage(player, new string_id(STF_FILE, "message_too_long"));
                return SCRIPT_CONTINUE;
            }
            obj_id pet_control = callable.getCallableCD(self);
            if (!isIdValid(pet_control))
            {
                LOG("droid_module", "merchant_barker.OnHearSpeech -- pet control device is invalid for droid " + self);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, VAR_BARK_MESSAGE, message);
            setObjVar(pet_control, VAR_BARK_MESSAGE, message);
            utils.removeScriptVar(self, SCRIPT_VAR_RECORDING_ON);
            sendSystemMessage(player, new string_id(STF_FILE, "message_saved"));
        }
        return SCRIPT_CONTINUE;
    }
    public int msgMerchantBark(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_BARKING_ON);
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            utils.removeScriptVar(self, SCRIPT_VAR_BARKING_ON);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPT_VAR_BARKING_ON))
        {
            if (hasObjVar(self, VAR_BARK_MESSAGE))
            {
                String message = getStringObjVar(self, VAR_BARK_MESSAGE);
                if (message != null)
                {
                    chat.chat(self, message);
                    utils.setScriptVar(self, pet.CAN_BARK, pet.FALSE);
                    messageTo(self, "msgResetCanBark", null, pet.BARK_DELAY, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgResetCanBark(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, pet.CAN_BARK, pet.TRUE);
        return SCRIPT_CONTINUE;
    }
    public int msgDroidWaypointSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getMaster(self);
        if (!isIdValid(player))
        {
            LOG("droid_module", "merchant_barker.msgDroidWaypointSelected -- player is invalid");
            return SCRIPT_CONTINUE;
        }
        if (!player.isAuthoritative())
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(player, SCRIPT_VAR_WAYPOINT_SUI);
        obj_id[] waypoints;
        if (utils.hasScriptVar(self, SCRIPT_VAR_WAYPOINT_LIST))
        {
            waypoints = utils.getObjIdArrayScriptVar(self, SCRIPT_VAR_WAYPOINT_LIST);
            utils.removeScriptVar(self, SCRIPT_VAR_WAYPOINT_LIST);
        }
        else 
        {
            LOG("droid_module", "merchant_barker.msgDroidWaypointSelected -- can't find waypoint list.");
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= waypoints.length)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id selected_waypoint = waypoints[row_selected];
        LOG("droid_module", "merchant_barker.msgDroidWaypointSelected -- selected_waypoint:" + selected_waypoint + " row ->" + row_selected);
        if (!isIdValid(selected_waypoint))
        {
            LOG("droid_module", "merchant_barker.msgDroidWaypointSelected -- selected_waypoint is not valid for " + player + ".");
            return SCRIPT_CONTINUE;
        }
        location waypoint_loc = getWaypointLocation(selected_waypoint);
        if (waypoint_loc == null)
        {
            LOG("droid_module", "merchant_barker.msgDroidWaypointSelected -- location is null for waypoint " + selected_waypoint);
            return SCRIPT_CONTINUE;
        }
        String waypoint_name = getWaypointName(selected_waypoint);
        obj_id pet_control = callable.getCallableCD(self);
        if (!isIdValid(pet_control))
        {
            LOG("droid_module", "merchant_barker.msgDroidWaypointSelected -- pet control device is invalid for droid " + self);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, VAR_BARK_WAYPOINT_LOC, waypoint_loc);
        setObjVar(pet_control, VAR_BARK_WAYPOINT_LOC, waypoint_loc);
        setObjVar(self, VAR_BARK_WAYPOINT_NAME, waypoint_name);
        setObjVar(pet_control, VAR_BARK_WAYPOINT_NAME, waypoint_name);
        sendSystemMessage(player, new string_id(STF_FILE, "waypoint_set"));
        return SCRIPT_CONTINUE;
    }
}

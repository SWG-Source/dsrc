package script.item.travel_ticket;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.travel;
import script.library.player_structure;
import script.library.city;
import script.library.groundquests;
import script.library.utils;
import script.library.sui;
import script.library.prose;

public class travel_shuttle_pilot extends script.base_script
{
    public travel_shuttle_pilot()
    {
    }
    public static final string_id SID_BOARD_SHUTTLE = new string_id("travel", "board_shuttle");
    public static final string_id SID_NEXT_SHUTTLE = new string_id("travel", "next_shuttle");
    public static final string_id SID_ON_PET_OR_VEHICLE = new string_id("travel/travel", "on_pet_or_vehicle");
    public static final string_id SID_BANNED_TICKET = new string_id("city/city", "banned_services");
    public static final string_id SID_WRONG_STATE = new string_id("error_message", "wrong_state");
    public static final string_id SID_OBTAIN_TRACKING_DATA = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_menu_obtain_data");
    public static final string_id SID_OBTAIN_DATA_TITLE = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_title");
    public static final string_id SID_OBTAIN_DATA_MSG = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_msg");
    public static final string_id SID_OKAY_BUTTOM = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_purchase_data");
    public static final string_id SID_CANCEL_BUTTON = new string_id("quest/force_sensitive/fs_crafting", "tracking_data_cancel_button");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        detachScript(self, "skeleton.humanoid");
        detachScript(self, "systems.combat.combat_actions");
        detachScript(self, "systems.combat.credit_for_kills");
        stop(self);
        return SCRIPT_CONTINUE;
    }
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        obj_id starport = travel.getStarportFromTerminal(self);
        LOG("LOG_CHANNEL", "travel_shuttle_pilot::OnUnloadedFromMemory -- " + self + " from starport " + starport);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        obj_id starport = travel.getStarportFromTerminal(self);
        if (data != null)
        {
            data.setServerNotify(true);
        }
        else 
        {
            if (travel.isShuttleAvailable(starport))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_BOARD_SHUTTLE);
            }
            else if (travel.qualifiesForGcwTravelPerks(player))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_BOARD_SHUTTLE);
                sendSystemMessage(player, "Bypassing the shuttle timer because you are a GCW officer and your faction currently controls the planet.", "");
            }
            else 
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_NEXT_SHUTTLE);
            }
        }
        if (!travel.isShuttlePort(starport))
        {
            if (groundquests.isTaskActive(player, "star_destroyer_intro_rebel", "star_destroyer_intro_01") || groundquests.isTaskActive(player, "star_destroyer_intro_neutral", "star_destroyer_intro_01"))
            {
                mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SID_OBTAIN_TRACKING_DATA);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIdValid(getMountId(player)))
            {
                LOG("LOG_CHANNEL", player + " -> TRAVEL_SHUTTLE_PILOT - OnObjectMenuSelect - USE - You cannot board the shuttle when mounted on a pet/vehicle?");
                sendSystemMessage(player, SID_ON_PET_OR_VEHICLE);
                return SCRIPT_CONTINUE;
            }
            obj_id starport = travel.getStarportFromTerminal(self);
            if (player_structure.isCivic(starport))
            {
                int city_id = getCityAtLocation(getLocation(starport), 0);
                if (city.isCityBanned(player, city_id))
                {
                    sendSystemMessage(player, SID_BANNED_TICKET);
                    return SCRIPT_CONTINUE;
                }
            }
            if (travel.isTravelBlocked(player, false))
            {
                return SCRIPT_CONTINUE;
            }
            if (travel.isShuttleAvailable(starport))
            {
                obj_id shuttle = travel.getShuttleFromStarport(starport);
                queueCommand(player, (1573732770), shuttle, "", COMMAND_PRIORITY_DEFAULT);
            }
            else if (travel.qualifiesForGcwTravelPerks(player))
            {
                obj_id shuttle = travel.getShuttleFromStarport(starport);
                queueCommand(player, (1573732770), shuttle, "", COMMAND_PRIORITY_DEFAULT);
                sendSystemMessage(player, "Bypassing the shuttle timer because you are a GCW officer and your faction currently controls the planet.", "");
            }
            else 
            {
                int time_stamp = travel.getShuttleTimestamp(starport);
                int air_time = travel.getAirTime(starport);
                int arrival_time = (air_time - (getGameTime() - time_stamp));
                if (arrival_time > 0)
                {
                    string_id strSpam = new string_id("travel/travel", "shuttle_board_delay");
                    prose_package pp = prose.getPackage(strSpam, arrival_time);
                    sendSystemMessageProse(player, pp);
                }
                else 
                {
                    LOG("LOG_CHANNEL", player + " ->The next shuttle is about to begin boarding.");
                    string_id strSpam = new string_id("travel/travel", "shuttle_begin_boarding");
                    sendSystemMessage(player, strSpam);
                }
            }
        }
        else if (item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            if (groundquests.isTaskActive(player, "star_destroyer_intro_rebel", "star_destroyer_intro_01") || groundquests.isTaskActive(player, "star_destroyer_intro_neutral", "star_destroyer_intro_01"))
            {
                utils.setScriptVar(player, "crafting4.starportDroid", self);
                String TITLE_MSG = utils.packStringId(SID_OBTAIN_DATA_TITLE);
                String TEXT_MSG = utils.packStringId(SID_OBTAIN_DATA_MSG);
                String OK_BUTTON = utils.packStringId(SID_OKAY_BUTTOM);
                String CANCEL_BUTTON = utils.packStringId(SID_CANCEL_BUTTON);
                int pid = sui.createSUIPage(sui.SUI_MSGBOX, player, player, "handlePurchaseTrackingData");
                setSUIProperty(pid, "", "Size", "500,250");
                setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE_MSG);
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT_MSG);
                sui.msgboxButtonSetup(pid, sui.YES_NO);
                setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
                setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
                utils.setScriptVar(player, "travel_voucher.travelVoucherSui", pid);
                sui.showSUIPage(pid);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

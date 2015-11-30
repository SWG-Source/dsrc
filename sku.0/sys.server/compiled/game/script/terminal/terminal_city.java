package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.library.money;
import script.library.player_structure;
import java.util.Vector;

public class terminal_city extends script.base_script
{
    public terminal_city()
    {
    }
    public static final String STF = "city/city";
    public static final string_id SID_CITY_HACKS = new string_id(STF, "city_hacks");
    public static final string_id SID_CITY_FORCE_UPDATE = new string_id(STF, "force_update");
    public static final string_id SID_CITY_RANK_UP = new string_id(STF, "rank_up");
    public static final string_id SID_CITY_RANK_DOWN = new string_id(STF, "rank_down");
    public static final string_id SID_CITY_FIX_MAYOR = new string_id(STF, "fix_mayor");
    public static final string_id SID_CITY_MAKE_MAYOR = new string_id(STF, "make_mayor");
    public static final string_id SID_CITY_REVOKE_CITIZENSHIP = new string_id(STF, "revoke_citizenship");
    public static final string_id SID_CITY_REMOVE_OLD_TRAINERS = new string_id(STF, "remove_trainers");
    public static final string_id SID_CLONE_DATA_WIPED = new string_id(STF, "clone_data_wiped");
    public static final string_id SID_SHUTTLE_DATA_WIPED = new string_id(STF, "clone_data_wiped");
    public static final string_id SID_CITY_RESET_CLONE_DATA = new string_id(STF, "reset_clone_data");
    public static final string_id SID_CITY_RESET_SHUTTLE_DATA = new string_id(STF, "reset_shuttle_data");
    public static final string_id SID_CITY_MODIFY_STRUCTURE_LIST = new string_id(STF, "modify_structure_list");
    public static final string_id SID_CLEAR_STRUCTURE_VARS = new string_id(STF, "clear_structure_vars");
    public static final string_id SID_CITY_INFO = new string_id(STF, "city_info");
    public static final string_id SID_CITY_STATUS = new string_id(STF, "city_status");
    public static final string_id SID_NON_CITIZEN_CITY_STATUS = new string_id(STF, "non_citizen_city_status");
    public static final string_id SID_CITY_CITIZENS = new string_id(STF, "city_citizens");
    public static final string_id SID_CITY_STRUCTURES = new string_id(STF, "city_structures");
    public static final string_id SID_CITY_RANK = new string_id(STF, "city_rank");
    public static final string_id SID_CITY_MAINT_REPORT = new string_id(STF, "city_maint");
    public static final string_id SID_CITY_MANAGEMENT = new string_id(STF, "city_management");
    public static final string_id SID_CITY_NAME = new string_id(STF, "city_name");
    public static final string_id SID_CITY_MILITIA = new string_id(STF, "city_militia");
    public static final string_id SID_CITY_REGISTER = new string_id(STF, "city_register");
    public static final string_id SID_CITY_UNREGISTER = new string_id(STF, "city_unregister");
    public static final string_id SID_CITY_SPECIALIZATIONS = new string_id(STF, "city_specializations");
    public static final string_id SID_CITY_VISITOR_MOTD = new string_id(STF, "city_visitor_message");
    public static final string_id SID_CITY_CITIZEN_MOTD = new string_id(STF, "city_citizen_message");
    public static final string_id SID_TREASURY_MANAGEMENT = new string_id(STF, "treasury_management");
    public static final string_id SID_TREASURY_STATUS = new string_id(STF, "treasury_status");
    public static final string_id SID_TREASURY_DEPOSIT = new string_id(STF, "treasury_deposit");
    public static final string_id SID_TREASURY_WITHDRAW = new string_id(STF, "treasury_withdraw");
    public static final string_id SID_TREASURY_TAXES = new string_id(STF, "treasury_taxes");
    public static final string_id SID_NAME_CHANGED = new string_id(STF, "name_changed");
    public static final string_id SID_OBSCENE = new string_id("player_structure", "obscene");
    public static final string_id SID_NOT_UNIQUE = new string_id("player_structure", "cityname_not_unique");
    public static final string_id SID_CANT_FIND_PLAYER = new string_id(STF, "cant_find_player");
    public static final string_id SID_NOT_CITIZEN = new string_id(STF, "not_citizen");
    public static final string_id SID_ADDED_MILITIA = new string_id(STF, "added_militia");
    public static final string_id SID_REMOVED_MILITIA = new string_id(STF, "removed_militia");
    public static final string_id SID_ADDED_MILITIA_TARGET = new string_id(STF, "added_militia_target");
    public static final string_id SID_REMOVED_MILITIA_TARGET = new string_id(STF, "removed_militia_target");
    public static final string_id SID_NO_MONEY = new string_id(STF, "no_money");
    public static final string_id SID_TAX_OUT_OF_RANGE = new string_id(STF, "tax_out_of_range");
    public static final string_id SID_INCOME_TAX = new string_id(STF, "set_income_tax");
    public static final string_id SID_PROPERTY_TAX = new string_id(STF, "set_property_tax");
    public static final string_id SID_SALES_TAX = new string_id(STF, "set_sales_tax");
    public static final string_id SID_TRAVEL_FEE = new string_id(STF, "set_travel_fee");
    public static final string_id SID_GARAGE_TAX = new string_id(STF, "set_garage_tax");
    public static final string_id SID_CANT_TAX = new string_id(STF, "cant_tax");
    public static final string_id SID_CANT_REGISTER = new string_id(STF, "cant_register");
    public static final string_id SID_CANT_REGISTER_RANK = new string_id(STF, "cant_register_rank");
    public static final string_id SID_REGISTERED = new string_id(STF, "registered");
    public static final string_id SID_UNREGISTERED = new string_id(STF, "unregistered");
    public static final string_id SID_CANT_MILITIA = new string_id(STF, "cant_militia");
    public static final string_id SID_NO_RANK_TAXES = new string_id(STF, "no_rank_taxes");
    public static final string_id SID_NO_RANK_SPEC = new string_id(STF, "no_rank_spec");
    public static final string_id SID_NO_SPECS = new string_id(STF, "no_specs");
    public static final string_id SID_SPEC_SET = new string_id(STF, "spec_set");
    public static final string_id SID_SPEC_TIME = new string_id(STF, "spec_time");
    public static final string_id SID_WITHDRAW_LIMITS = new string_id(STF, "withdraw_limits");
    public static final string_id SID_WITHDRAW_DAILY = new string_id(STF, "withdraw_daily");
    public static final string_id SID_NO_SHUTTLEPORT = new string_id(STF, "no_shuttleport");
    public static final string_id PUBLIC_ELECTION_SUBJECT = new string_id(STF, "public_election_subject");
    public static final string_id PUBLIC_ELECTION_BODY = new string_id(STF, "public_election_body");
    public static final String CITY_SPECS = "datatables/city/specializations.iff";
    public static final String[] TAX_STRING = 
    {
        "income",
        "property",
        "sales",
        "travel",
        "garage"
    };
    public static final int[] TAX_MIN = 
    {
        0,
        0,
        0,
        1,
        0
    };
    public static final int[] TAX_MAX = 
    {
        2000,
        50,
        20,
        500,
        30
    };
    public static final string_id SID_CITY_ZONE = new string_id(STF, "zone");
    public static final string_id SID_CITY_UNZONE = new string_id(STF, "unzone");
    public static final string_id SID_ZONING_ENABLED = new string_id(STF, "zoning_enabled");
    public static final string_id SID_ZONING_DISABLED = new string_id(STF, "zoning_disabled");
    public static final string_id SID_ZONING_SKILL = new string_id(STF, "zoning_skill");
    public static final string_id TREASURY_WITHDRAW_SUBJECT = new string_id(STF, "treasury_withdraw_subject");
    public static final string_id TREASURY_WITHDRAW_BODY = new string_id(STF, "treasury_withdraw_body");
    public static final string_id TREASURY_DEPOSIT_SUBJECT = new string_id(STF, "treasury_deposit_subject");
    public static final string_id TREASURY_DEPOSIT_BODY = new string_id(STF, "treasury_deposit_body");
    public static final string_id TAX_INCOME_SUBJECT = new string_id(STF, "tax_income_subject");
    public static final string_id TAX_INCOME_BODY = new string_id(STF, "tax_income_body");
    public static final string_id TAX_PROPERTY_SUBJECT = new string_id(STF, "tax_property_subject");
    public static final string_id TAX_PROPERTY_BODY = new string_id(STF, "tax_property_body");
    public static final string_id TAX_SALES_SUBJECT = new string_id(STF, "tax_sales_subject");
    public static final string_id TAX_SALES_BODY = new string_id(STF, "tax_sales_body");
    public static final string_id TAX_TRAVEL_SUBJECT = new string_id(STF, "tax_travel_subject");
    public static final string_id TAX_TRAVEL_BODY = new string_id(STF, "tax_travel_body");
    public static final string_id GARAGE_FEE_SUBJECT = new string_id(STF, "garage_fee_subject");
    public static final string_id GARAGE_FEE_BODY = new string_id(STF, "garage_fee_body");
    public static final string_id SID_YOU_WITHDRAW_FROM_TREASURY = new string_id(STF, "you_withdraw_from_treasury");
    public static final string_id SID_SUI_CONFIRM_REMOVE_TITLE = new string_id(STF, "sui_confirm_remove_title");
    public static final string_id SID_REVOKE_CITIZEN_WARNING = new string_id(STF, "revoke_citizenship_warning");
    public static final String PROFESSION_TRAINER_SCRIPT = "npc.skillteacher.trainer_spawner";
    public static final string_id SID_SAFE_HOUSE_PROMPT = new string_id(STF, "safe_house_citizen_prompt");
    public static final string_id SID_REMOVE_SAFE_HOUSE_CITIZEN_BODY = new string_id(STF, "remove_safe_citizen_body");
    public static final string_id SID_ADD_SAFE_HOUSE_CITIZEN_BODY = new string_id(STF, "add_safe_citizen_body");
    public static final string_id SID_REMOVE_SAFE_HOUSE_CITIZEN_SUBJECT = new string_id(STF, "remove_safe_citizen_subject");
    public static final string_id SID_ADD_SAFE_HOUSE_CITIZEN_SUBJECT = new string_id(STF, "add_safe_citizen_subject");
    public static final string_id SID_CITY_CITIZEN_PROTECTION = new string_id(STF, "city_citizen_protection");
    public static final string_id SID_ALIGN_IMPERIAL = new string_id(STF, "align_imperial");
    public static final string_id SID_ALIGN_REBEL = new string_id(STF, "align_rebel");
    public static final string_id SID_ALIGN_NEUTRAL = new string_id(STF, "align_neutral");
    public static final string_id SID_BEGIN_GCW_REGION_DEFENDER = new string_id(STF, "begin_gcw_region_defender");
    public static final string_id SID_END_GCW_REGION_DEFENDER = new string_id(STF, "end_gcw_region_defender");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id mayor = cityGetLeader(city_id);
        if (!city.isCitizenOfCity(player, city_id) && (player != mayor) && !isGod(player))
        {
            return SCRIPT_CONTINUE;
        }
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, SID_CITY_INFO);
        mi.addSubMenu(menu, menu_info_types.CITY_STATUS, SID_CITY_STATUS);
        mi.addSubMenu(menu, menu_info_types.CITY_CITIZENS, SID_CITY_CITIZENS);
        mi.addSubMenu(menu, menu_info_types.CITY_STRUCTURES, SID_CITY_STRUCTURES);
        mi.addSubMenu(menu, menu_info_types.CITY_RANK, SID_CITY_RANK);
        mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, SID_CITY_MAINT_REPORT);
        mi.addSubMenu(menu, menu_info_types.CITY_TREASURY, SID_TREASURY_STATUS);
        mi.addSubMenu(menu, menu_info_types.CITY_TREASURY_DEPOSIT, SID_TREASURY_DEPOSIT);
        if (city.isCitizenOfCity(player, city_id) && (player != mayor))
        {
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU5, SID_CITY_REVOKE_CITIZENSHIP);
        }
        if (player == mayor || isGod(player))
        {
            menu = mi.addRootMenu(menu_info_types.CITY_MANAGEMENT, SID_CITY_MANAGEMENT);
            mi.addSubMenu(menu, menu_info_types.CITY_NAME, SID_CITY_NAME);
            if (!city.isCityRegistered(city_id))
            {
                mi.addSubMenu(menu, menu_info_types.CITY_REGISTER, SID_CITY_REGISTER);
            }
            else 
            {
                mi.addSubMenu(menu, menu_info_types.CITY_REGISTER, SID_CITY_UNREGISTER);
            }
            if (city.isCityZoned(city_id))
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, SID_CITY_UNZONE);
            }
            else 
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, SID_CITY_ZONE);
            }
            mi.addSubMenu(menu, menu_info_types.CITY_MILITIA, SID_CITY_MILITIA);
            mi.addSubMenu(menu, menu_info_types.CITY_TAXES, SID_TREASURY_TAXES);
            mi.addSubMenu(menu, menu_info_types.CITY_TREASURY_WITHDRAW, SID_TREASURY_WITHDRAW);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU1, SID_CITY_SPECIALIZATIONS);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU11, SID_CITY_VISITOR_MOTD);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU12, SID_CITY_CITIZEN_MOTD);
            if (!city.isCitizenOfCity(player, city_id))
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU8, SID_CITY_FIX_MAYOR);
            }
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU13, SID_CITY_CITIZEN_PROTECTION);
            int factionId = cityGetFaction(city_id);
            if (((-615855020) == factionId) || ((370444368) == factionId))
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU16, SID_ALIGN_NEUTRAL);
            }
            else 
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU14, SID_ALIGN_IMPERIAL);
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU15, SID_ALIGN_REBEL);
            }
            final String gcwDefenderRegion = cityGetGcwDefenderRegion(city_id);
            if ((gcwDefenderRegion == null) || (gcwDefenderRegion.length() <= 0))
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU17, SID_BEGIN_GCW_REGION_DEFENDER);
            }
            else 
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU18, SID_END_GCW_REGION_DEFENDER);
            }
        }
        if (isGod(player))
        {
            int godMenu = mi.addRootMenu(menu_info_types.ITEM_USE_SELF, SID_CITY_HACKS);
            mi.addSubMenu(godMenu, menu_info_types.SERVER_MENU4, SID_CITY_FORCE_UPDATE);
            mi.addSubMenu(godMenu, menu_info_types.SERVER_MENU6, SID_CITY_RANK_UP);
            mi.addSubMenu(godMenu, menu_info_types.SERVER_MENU7, SID_CITY_RANK_DOWN);
            mi.addSubMenu(godMenu, menu_info_types.CITY_NAME, SID_CITY_NAME);
            mi.addSubMenu(godMenu, menu_info_types.SERVER_MENU9, SID_CITY_MAKE_MAYOR);
            mi.addSubMenu(godMenu, menu_info_types.CITY_ADMIN_1, SID_CITY_RESET_CLONE_DATA);
            mi.addSubMenu(godMenu, menu_info_types.CITY_ADMIN_2, SID_CITY_RESET_SHUTTLE_DATA);
            mi.addSubMenu(godMenu, menu_info_types.CITY_ADMIN_3, SID_CITY_MODIFY_STRUCTURE_LIST);
            mi.addSubMenu(godMenu, menu_info_types.CITY_ADMIN_4, SID_CLEAR_STRUCTURE_VARS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(structure);
        if (template.indexOf("cityhall_") < 0)
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return SCRIPT_CONTINUE;
        }
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id mayor = cityGetLeader(city_id);
        if (item == menu_info_types.CITY_STATUS)
        {
            showCityInfo(player, self, city_id);
        }
        else if (item == menu_info_types.CITY_CITIZENS)
        {
            showCitizensList(player, self, city_id);
        }
        else if (item == menu_info_types.CITY_STRUCTURES)
        {
            showStructuresList(player, self, city_id);
        }
        else if (item == menu_info_types.CITY_TREASURY)
        {
            showTreasuryInfo(player, self, city_id);
        }
        else if (item == menu_info_types.CITY_RANK)
        {
            showAdvancementInfo(player, self, city_id);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            showMaintInfo(player, self, city_id);
        }
        if (item == menu_info_types.CITY_TREASURY_DEPOSIT)
        {
            makeTreasuryDeposit(player, self, city_id);
        }
        if (player == mayor || isGod(player))
        {
            if (item == menu_info_types.CITY_NAME)
            {
                changeCityName(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_TAXES)
            {
                adjustTaxes(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_MILITIA)
            {
                modifyMilitia(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_TREASURY_WITHDRAW)
            {
                makeTreasuryWithdraw(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_REGISTER)
            {
                if (!city.isCityRegistered(city_id))
                {
                    sui.msgbox(self, player, "@city/city:register_d", sui.YES_NO, "@city/city:register_t", sui.MSG_QUESTION, "handleRegisterCity");
                }
                else 
                {
                    sui.msgbox(self, player, "@city/city:unregister_d", sui.YES_NO, "@city/city:unregister_t", sui.MSG_QUESTION, "handleUnregisterCity");
                }
            }
            else if (item == menu_info_types.SERVER_MENU1)
            {
                changeSpecialization(player, self, city_id);
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                changeZoning(player, self, city_id);
            }
            else if (item == menu_info_types.SERVER_MENU8)
            {
                if (!city.isCitizenOfCity(mayor, city_id))
                {
                    citySetCitizenInfo(city_id, mayor, getName(mayor), mayor, city.CP_CITIZEN);
                    sendSystemMessage(self, new string_id(STF, "mayor_citizen_restored"));
                }
            }
            else if (item == menu_info_types.SERVER_MENU10)
            {
                boolean trainersDestroyed = destroyCitySkillTrainers(mayor, city_id);
                if (trainersDestroyed)
                {
                    sendSystemMessage(mayor, new string_id(STF, "old_trainers_removed"));
                }
            }
            else if (item == menu_info_types.SERVER_MENU11)
            {
                String message = "";
                if (hasObjVar(structure, "city_visitor_message"))
                {
                    message = getStringObjVar(structure, "city_visitor_message");
                }
                else 
                {
                    message = "Enter a Message for Visitors";
                }
                sui.filteredInputbox(self, player, "@city/city:prompt_city_visitor_message", "@city/city:title_city_visitor_message", "handleCityVisitorMessage", message);
            }
            else if (item == menu_info_types.SERVER_MENU12)
            {
                String message = "";
                if (hasObjVar(structure, "city_citizen_message"))
                {
                    message = getStringObjVar(structure, "city_citizen_message");
                }
                else 
                {
                    message = "Enter a Message for Citizens";
                }
                sui.filteredInputbox(self, player, "@city/city:prompt_city_citizen_message", "@city/city:title_city_citizen_message", "handleCityCitizenMessage", message);
            }
            else if (item == menu_info_types.SERVER_MENU13)
            {
                showSafeHouseCitizenList(player, self, city_id);
            }
            else if (item == menu_info_types.SERVER_MENU14)
            {
                boolean allow = true;
                if (hasObjVar(structure, "cityTimeEndRebelAlign"))
                {
                    int cooldown = (getIntObjVar(structure, "cityTimeEndRebelAlign") + (isGod(player) ? 10 : 86400)) - getCalendarTime();
                    if (cooldown > 0)
                    {
                        String cooldownStr = "" + cooldown + "s";
                        int[] convertedTime = player_structure.convertSecondsTime(cooldown);
                        if ((convertedTime != null) && (convertedTime.length == 4))
                        {
                            if (convertedTime[0] > 0)
                            {
                                cooldownStr = "" + convertedTime[0] + "d:" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                            }
                            else if (convertedTime[1] > 0)
                            {
                                cooldownStr = "" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                            }
                            else if (convertedTime[2] > 0)
                            {
                                cooldownStr = "" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                            }
                            else if (convertedTime[3] > 0)
                            {
                                cooldownStr = "" + convertedTime[3] + "s";
                            }
                            else 
                            {
                                cooldownStr = "" + cooldown + "s";
                            }
                        }
                        sendSystemMessage(player, "You must wait " + cooldownStr + " before you can change the city's factional alignment to Imperial.", "");
                        allow = false;
                    }
                }
                if (allow)
                {
                    sendSystemMessage(player, "Setting the city's factional alignment to Imperial. This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                    setObjVar(structure, "cityFactionAlign", (-615855020));
                    citySetFaction(city_id, (-615855020), true);
                }
            }
            else if (item == menu_info_types.SERVER_MENU15)
            {
                boolean allow = true;
                if (hasObjVar(structure, "cityTimeEndImperialAlign"))
                {
                    int cooldown = (getIntObjVar(structure, "cityTimeEndImperialAlign") + (isGod(player) ? 10 : 86400)) - getCalendarTime();
                    if (cooldown > 0)
                    {
                        String cooldownStr = "" + cooldown + "s";
                        int[] convertedTime = player_structure.convertSecondsTime(cooldown);
                        if ((convertedTime != null) && (convertedTime.length == 4))
                        {
                            if (convertedTime[0] > 0)
                            {
                                cooldownStr = "" + convertedTime[0] + "d:" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                            }
                            else if (convertedTime[1] > 0)
                            {
                                cooldownStr = "" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                            }
                            else if (convertedTime[2] > 0)
                            {
                                cooldownStr = "" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                            }
                            else if (convertedTime[3] > 0)
                            {
                                cooldownStr = "" + convertedTime[3] + "s";
                            }
                            else 
                            {
                                cooldownStr = "" + cooldown + "s";
                            }
                        }
                        sendSystemMessage(player, "You must wait " + cooldownStr + " before you can change the city's factional alignment to Rebel.", "");
                        allow = false;
                    }
                }
                if (allow)
                {
                    sendSystemMessage(player, "Setting the city's factional alignment to Rebel. This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                    setObjVar(structure, "cityFactionAlign", (370444368));
                    citySetFaction(city_id, (370444368), true);
                }
            }
            else if (item == menu_info_types.SERVER_MENU16)
            {
                final String gcwDefenderRegion = cityGetGcwDefenderRegion(city_id);
                if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0))
                {
                    sendSystemMessage(player, "You cannot change the city's factional alignment to Neutral while it is a GCW region defender.", "");
                }
                else 
                {
                    int factionId = cityGetFaction(city_id);
                    if ((-615855020) == factionId)
                    {
                        setObjVar(structure, "cityTimeEndImperialAlign", getCalendarTime());
                        removeObjVar(structure, "cityTimeEndRebelAlign");
                    }
                    else if ((370444368) == factionId)
                    {
                        setObjVar(structure, "cityTimeEndRebelAlign", getCalendarTime());
                        removeObjVar(structure, "cityTimeEndImperialAlign");
                    }
                    else if (factionId != 0)
                    {
                        removeObjVar(structure, "cityTimeEndImperialAlign");
                        removeObjVar(structure, "cityTimeEndRebelAlign");
                    }
                    sendSystemMessage(player, "Setting the city's factional alignment to Neutral. This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                    removeObjVar(structure, "cityFactionAlign");
                    citySetFaction(city_id, 0, true);
                }
            }
            else if (item == menu_info_types.SERVER_MENU17)
            {
                final int factionId = cityGetFaction(city_id);
                if (factionId == 0)
                {
                    sendSystemMessage(player, "The city cannot become a GCW region defender until it is aligned with a faction.", "");
                }
                else 
                {
                    final String gcwDefenderRegion = cityGetGcwDefenderRegion(city_id);
                    if ((gcwDefenderRegion == null) || (gcwDefenderRegion.length() <= 0))
                    {
                        String[] gcwDefenderRegions = getGcwDefenderRegions();
                        if ((gcwDefenderRegions != null) && (gcwDefenderRegions.length > 0))
                        {
                            String previousRegion = null;
                            int previousRegionTimeStartDefend = 0;
                            int previousRegionTimeEndDefend = 0;
                            if (hasObjVar(structure, "cityGcwRegionDefender.region"))
                            {
                                previousRegion = getStringObjVar(structure, "cityGcwRegionDefender.region");
                            }
                            if (hasObjVar(structure, "cityGcwRegionDefender.timeBegin"))
                            {
                                previousRegionTimeStartDefend = getIntObjVar(structure, "cityGcwRegionDefender.timeBegin");
                            }
                            if (hasObjVar(structure, "cityGcwRegionDefender.timeEnd"))
                            {
                                previousRegionTimeEndDefend = getIntObjVar(structure, "cityGcwRegionDefender.timeEnd");
                            }
                            String announcement = "Select a GCW region for your city to defend.";
                            if ((previousRegion != null) && (previousRegion.length() > 0) && (previousRegionTimeStartDefend > 0) && (previousRegionTimeEndDefend > 0))
                            {
                                final int cooldown = previousRegionTimeEndDefend + (isGod(player) ? 10 : 86400) - getCalendarTime();
                                if (cooldown > 0)
                                {
                                    String cooldownStr = "" + cooldown + "s";
                                    int[] convertedTime = player_structure.convertSecondsTime(cooldown);
                                    if ((convertedTime != null) && (convertedTime.length == 4))
                                    {
                                        if (convertedTime[0] > 0)
                                        {
                                            cooldownStr = "" + convertedTime[0] + "d:" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                                        }
                                        else if (convertedTime[1] > 0)
                                        {
                                            cooldownStr = "" + convertedTime[1] + "h:" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                                        }
                                        else if (convertedTime[2] > 0)
                                        {
                                            cooldownStr = "" + convertedTime[2] + "m:" + convertedTime[3] + "s";
                                        }
                                        else if (convertedTime[3] > 0)
                                        {
                                            cooldownStr = "" + convertedTime[3] + "s";
                                        }
                                        else 
                                        {
                                            cooldownStr = "" + cooldown + "s";
                                        }
                                    }
                                    announcement += "\n";
                                    announcement += "You can immediately defend the GCW region you most recently defended (" + localize(new string_id("gcw_regions", previousRegion)) + ").\n";
                                    announcement += "You must wait " + cooldownStr + " before you can defend a different GCW region.";
                                }
                            }
                            final String[] columnHeader = 
                            {
                                "GCW Region"
                            };
                            final String[] columnHeaderType = 
                            {
                                "text"
                            };
                            final String[][] columnData = new String[1][0];
                            columnData[0] = gcwDefenderRegions;
                            sui.tableColumnMajor(player, player, sui.OK_CANCEL, "@gcw:gcw_region_defender_war_terminal_menu", "handleCityGcwRegionDefenderChoice", announcement, columnHeader, columnHeaderType, columnData, false);
                        }
                    }
                }
            }
            else if (item == menu_info_types.SERVER_MENU18)
            {
                final String gcwDefenderRegion = cityGetGcwDefenderRegion(city_id);
                if ((gcwDefenderRegion != null) && (gcwDefenderRegion.length() > 0))
                {
                    setObjVar(structure, "cityGcwRegionDefender.timeEnd", getCalendarTime());
                    citySetGcwDefenderRegion(city_id, "", 0, true);
                    sendSystemMessage(player, "Setting the city's GCW defender region to (None). This may take a few seconds. You will receive mail confirmation once the change has been completed.", "");
                }
            }
        }
        else 
        {
            if (item == menu_info_types.SERVER_MENU5)
            {
                if (city.isCitizenOfCity(player, city_id))
                {
                    sui.msgbox(self, player, "@city/city:revoke_cit_d", sui.YES_NO, "@city/city:revoke_cit_t", sui.MSG_QUESTION, "handleRevokeCitizenship");
                }
            }
        }
        if (isGod(player))
        {
            if (item == menu_info_types.SERVER_MENU4)
            {
                forceUpdate(player, self, city_id);
            }
            else if (item == menu_info_types.SERVER_MENU6)
            {
                forceRank(player, self, city_id, 1);
            }
            else if (item == menu_info_types.SERVER_MENU7)
            {
                forceRank(player, self, city_id, -1);
            }
            else if (item == menu_info_types.SERVER_MENU9)
            {
                makeMayor(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_ADMIN_1)
            {
                resetCloneData(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_ADMIN_2)
            {
                resetShuttleData(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_ADMIN_3)
            {
                modifyStructureList(player, self, city_id);
            }
            else if (item == menu_info_types.CITY_ADMIN_4)
            {
                clearStructureScriptVars(player, self, city_id);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void forceUpdate(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        sui.msgbox(self, player, "@city/city:force_election_only", sui.YES_NO, "@city/city:force_city_update_t", sui.MSG_QUESTION, "handleForceUpdateElection");
    }
    public int handleForceUpdateElection(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        obj_id structure = getTopMostContainer(self);
        int btn = sui.getIntButtonPressed(params);
        if (!isIdValid(player) || !isIdValid(structure))
        {
            sendSystemMessageTestingOnly(player, "Invalid Object Id - Bailing Out - Please Try Again.");
            return SCRIPT_CONTINUE;
        }
        int city_id = findCityByCityHall(structure);
        if (city_id <= 0)
        {
            sendSystemMessageTestingOnly(player, "Invalid City Id - Bailing Out - Please Try Again.");
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_CANCEL)
        {
            sui.msgbox(self, player, "@city/city:confirm_city_update", sui.YES_NO, "@city/city:force_city_update_t", sui.MSG_QUESTION, "handleForceUpdateConfirmed");
        }
        else 
        {
            CustomerServiceLog("player_city", "!!!!! A CITY ELECTION WAS FORCED !!!!! Player: " + player + " City: " + city_id);
            sendSystemMessageProse(self, prose.getPackage(new string_id(STF, "force_city_election"), city_id));
            obj_id cityMaster = getMasterCityObject();
            dictionary outparams = new dictionary();
            outparams.put("city_id", city_id);
            messageTo(cityMaster, "forceElection", outparams, 0.f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleForceUpdateConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        sui.msgbox(self, player, "@city/city:confirm_disable_cleanup", sui.YES_NO, "@city/city:disable_cleanup_t", sui.MSG_QUESTION, "handleForceUpdateNow");
        return SCRIPT_CONTINUE;
    }
    public int handleForceUpdateNow(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        dictionary outparams = new dictionary();
        outparams.put("city_id", city_id);
        int btn = sui.getIntButtonPressed(params);
        if (btn != sui.BP_CANCEL)
        {
            outparams.put("no_citizen_cleanup", 1);
        }
        CustomerServiceLog("player_city", "!!!!! A CITY UPDATE WAS FORCED !!!!! Player: " + player + " City: " + city_id);
        sendSystemMessageProse(self, prose.getPackage(new string_id(STF, "force_city_update"), city_id));
        obj_id cityMaster = getMasterCityObject();
        messageTo(cityMaster, "forceUpdate", outparams, 0.f, false);
        return SCRIPT_CONTINUE;
    }
    public void forceRank(obj_id player, obj_id self, int city_id, int rank) throws InterruptedException
    {
        utils.setScriptVar(self, "rank_change", rank);
        sui.msgbox(self, player, "@city/city:force_city_rank_d", sui.YES_NO, "@city/city:force_city_rank_t", sui.MSG_QUESTION, "handleForceRankConfirmed");
    }
    public int handleForceRankConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        CustomerServiceLog("player_city", "!!!!! A CITY RANK CHANGE WAS FORCED !!!!! Player: " + player + " City: " + city_id);
        sendSystemMessageProse(self, prose.getPackage(new string_id(STF, "force_city_rank"), city_id));
        obj_id cityMaster = getMasterCityObject();
        dictionary outparams = new dictionary();
        outparams.put("city_id", city_id);
        outparams.put("rank_change", utils.getIntScriptVar(self, "rank_change"));
        messageTo(cityMaster, "forceRank", outparams, 0.f, false);
        return SCRIPT_CONTINUE;
    }
    public void makeMayor(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return;
        }
        if (!cityExists(city_id))
        {
            sendSystemMessageTestingOnly(player, "Player City not found at this location...please try again.");
            return;
        }
        obj_id[] citizen_list = cityGetCitizenIds(city_id);
        String[] citizen_names = new String[citizen_list.length];
        if (citizen_list == null || citizen_list.length <= 0)
        {
            sendSystemMessageTestingOnly(player, "Player City not found at this location...please try again.");
            return;
        }
        for (int i = 0; i < citizen_list.length; i++)
        {
            String citizen = cityGetCitizenName(city_id, citizen_list[i]);
            if (citizen == null || citizen.equals(""))
            {
                citizen_names[i] = "Null Citizen";
            }
            else 
            {
                citizen_names[i] = citizen;
            }
        }
        utils.setScriptVar(player, "god_command_make_mayor_citizen_ids", citizen_list);
        utils.setScriptVar(player, "god_command_make_mayor_citizen_names", citizen_names);
        sui.listbox(self, player, "Select the Citizen to Make Mayor. You can only select Online Citizens to become the New Mayor", sui.OK_CANCEL, "God Command: Make Mayor", citizen_names, "handleMakeMayor", true);
    }
    public int handleMakeMayor(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "god_command_make_mayor_citizen_ids") && !utils.hasScriptVar(player, "god_command_make_mayor_citizen_names"))
        {
            sendSystemMessageTestingOnly(player, "Invalid Data - Please attempt the City Transfer Again.");
            return SCRIPT_CONTINUE;
        }
        String[] names = utils.getStringArrayScriptVar(player, "god_command_make_mayor_citizen_names");
        obj_id[] ids = utils.getObjIdArrayScriptVar(player, "god_command_make_mayor_citizen_ids");
        utils.removeScriptVar(player, "god_command_make_mayor_citizen_names");
        utils.removeScriptVar(player, "god_command_make_mayor_citizen_ids");
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        String newMayorName = names[idx];
        obj_id newMayorId = ids[idx];
        if (!hasSkill(newMayorId, "social_politician_novice"))
        {
            sendSystemMessage(player, new string_id(STF, "not_politician"));
            return SCRIPT_CONTINUE;
        }
        if (city.isAMayor(newMayorId))
        {
            sendSystemMessage(player, new string_id(STF, "already_other_mayor"));
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        location structureLoc = getLocation(structure);
        int city_id = getCityAtLocation(structureLoc, 0);
        obj_id incumbent_mayor = cityGetLeader(city_id);
        String template = getTemplateName(structure);
        if (template.indexOf("cityhall_") < 0)
        {
            sendSystemMessageTestingOnly(player, "City Hall not recognized - Please make sure you are in a City Hall and attempt the Mayor Transfer again.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("player_city", "CSR changed mayor. CSR(" + player + ") Changed City(" + city_id + ")" + cityGetName(city_id) + " to New Mayor(" + newMayorId + ")" + newMayorName + ".");
        sendSystemMessage(player, new string_id(STF, "mayor_confirm"));
        city.setMayor(city_id, newMayorId);
        city.doMayoralStructureTransfer(newMayorId, incumbent_mayor, city_id);
        sendNewMayorMail(city_id, newMayorId);
        return SCRIPT_CONTINUE;
    }
    public void resetCloneData(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        sui.msgbox(self, player, "@city/city:reset_clone_data_d", sui.YES_NO, "@city/city:reset_clone_data_t", sui.MSG_QUESTION, "handleResetCloneData");
    }
    public int handleResetCloneData(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        city.setCloneInfo(city_id, null, null, null);
        sendSystemMessage(player, SID_CLONE_DATA_WIPED);
        CustomerServiceLog("player_city", "CSR wiped clone data.  Player: " + player + " City: " + city_id);
        return SCRIPT_CONTINUE;
    }
    public void resetShuttleData(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        sui.msgbox(self, player, "@city/city:reset_shuttle_data_d", sui.YES_NO, "@city/city:reset_shuttle_data_t", sui.MSG_QUESTION, "handleResetShuttleData");
    }
    public int handleResetShuttleData(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        city.removeStarport(self, city_id);
        sendSystemMessage(player, SID_SHUTTLE_DATA_WIPED);
        CustomerServiceLog("player_city", "CSR wiped shuttle data.  Player: " + player + " City: " + city_id);
        return SCRIPT_CONTINUE;
    }
    public void clearStructureScriptVars(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "loadedStructuresIds") && utils.hasScriptVar(self, "loadedStructuresNames") && utils.hasScriptVar(self, "loadedStructuresLocations"))
        {
            utils.removeScriptVar(self, "loadedStructuresIds");
            utils.removeScriptVar(self, "loadedStructuresNames");
            utils.removeScriptVar(self, "loadedStructuresLocations");
        }
        if (utils.hasScriptVarTree(self, city.CITIZEN_LIST_QUERIED))
        {
            utils.removeScriptVarTree(self, city.CITIZEN_LIST_QUERIED);
        }
        if (utils.hasScriptVarTree(self, city.CITIZEN_LIST_DATA))
        {
            utils.removeScriptVarTree(self, city.CITIZEN_LIST_DATA);
        }
    }
    public void modifyStructureList(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        Vector list_structures = new Vector();
        list_structures.setSize(0);
        Vector list_strings = new Vector();
        list_strings.setSize(0);
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (city.isMissionTerminal(city_id, structures[i]))
            {
                String name = localize(getNameStringId(structures[i]));
                String str = "Mission Terminal: ";
                if (name != null)
                {
                    str += name;
                }
                else 
                {
                    str += "\\#FF0000Unloaded or Bad Entry\\";
                }
                str += " (" + structures[i] + ")";
                list_structures = utils.addElement(list_structures, structures[i]);
                list_strings = utils.addElement(list_strings, str);
            }
            else if (city.isSkillTrainer(city_id, structures[i]))
            {
                String name = localize(getNameStringId(structures[i]));
                String str = "Skill Trainer: ";
                if (name != null)
                {
                    str += name;
                }
                else 
                {
                    str += "\\#FF0000Unloaded or Bad Entry\\";
                }
                str += " (" + structures[i] + ")";
                list_structures = utils.addElement(list_structures, structures[i]);
                list_strings = utils.addElement(list_strings, str);
            }
            else if (city.isDecoration(city_id, structures[i]))
            {
                String name = localize(getNameStringId(structures[i]));
                String str = "Decoration: ";
                if (name != null)
                {
                    str += name;
                }
                else 
                {
                    str += "\\#FF0000Unloaded or Bad Entry\\";
                }
                str += " (" + structures[i] + ")";
                list_structures = utils.addElement(list_structures, structures[i]);
                list_strings = utils.addElement(list_strings, str);
            }
        }
        if (list_strings.size() == 0)
        {
            sendSystemMessage(player, new string_id(STF, "no_special_objects"));
            return;
        }
        utils.setScriptVar(self, "list_structures", list_structures);
        utils.setScriptVar(self, "list_strings", list_strings);
        sui.listbox(self, player, "@city/city:city_object_list_d", sui.OK_CANCEL, "@city/city:city_object_list_t", list_strings, "handleCityObjectRemove", true);
    }
    public int handleCityObjectRemove(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        Vector list_strings = utils.getResizeableStringArrayScriptVar(self, "list_strings");
        utils.setScriptVar(self, "list_idx", idx);
        string_id destroy_prefix = new string_id(STF, "destroy_prefix");
        string_id destroy_suffix = new string_id(STF, "destroy_suffix");
        String prompt = getString(destroy_prefix) + ((String)list_strings.get(idx)) + getString(destroy_suffix);
        sui.msgbox(self, player, prompt, sui.YES_NO, utils.packStringId(SID_SUI_CONFIRM_REMOVE_TITLE), sui.MSG_QUESTION, "handleConfirmObjectRemove");
        return SCRIPT_CONTINUE;
    }
    public int handleConfirmObjectRemove(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        int idx = utils.getIntScriptVar(self, "list_idx");
        Vector list_strings = utils.getResizeableStringArrayScriptVar(self, "list_strings");
        Vector list_structures = utils.getResizeableObjIdArrayScriptVar(self, "list_structures");
        String name = ((String)list_strings.get(idx));
        messageTo(((obj_id)list_structures.get(idx)), "requestDestroy", null, 0.f, true);
        cityRemoveStructure(city_id, ((obj_id)list_structures.get(idx)));
        sendSystemMessageProse(self, prose.getPackage(new string_id(STF, "destroy_object"), city_id));
        CustomerServiceLog("player_city", "CSR remove city object via menu.  Player: " + player + " City: " + city_id + " Object: " + ((obj_id)list_structures.get(idx)));
        return SCRIPT_CONTINUE;
    }
    public int handleRevokeCitizenship(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        prose_package bodypp = prose.getPackage(city.LOST_CITIZEN_BODY, cityGetCitizenName(city_id, player));
        utils.sendMail(city.LOST_CITIZEN_SUBJECT, bodypp, mayor_name, "City Hall");
        String city_name = cityGetName(city_id);
        obj_id city_hall = cityGetCityHall(city_id);
        cityRemoveCitizen(city_id, player);
        sendSystemMessage(player, SID_REVOKE_CITIZEN_WARNING);
        CustomerServiceLog("player_city", "Removed citizen by their request.  City: " + city_name + " (" + city_id + "/" + city_hall + ")" + " Citizen: " + player);
        return SCRIPT_CONTINUE;
    }
    public void showCityInfo(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id cityHall = cityGetCityHall(city_id);
        String[] city_info = new String[12];
        string_id city_name_text = new string_id(STF, "name_prompt");
        final String city_name = cityGetName(city_id);
        String city_data = city_name;
        final int factionId = cityGetFaction(city_id);
        if ((-615855020) == factionId)
        {
            city_data += " (Imperial aligned)";
        }
        else if ((370444368) == factionId)
        {
            city_data += " (Rebel aligned)";
        }
        String founder = getStringObjVar(cityHall, "founder.id");
        int creationTime = cityGetCreationTime(city_id);
        if (((founder != null) && (founder.length() > 0)) || (creationTime > 0))
        {
            city_data += " (founded";
            if ((founder != null) && (founder.length() > 0))
            {
                city_data += " by ";
                city_data += founder;
            }
            if (creationTime > 0)
            {
                city_data += " on ";
                city_data += getCalendarTimeStringLocal(creationTime);
            }
            city_data += ")";
        }
        city_info[0] = getString(city_name_text) + city_data;
        obj_id mayor = cityGetLeader(city_id);
        string_id city_mayor_text = new string_id(STF, "mayor_prompt");
        city_info[1] = getString(city_mayor_text) + cityGetCitizenName(city_id, mayor);
        final String gcwDefenderRegion = cityGetGcwDefenderRegion(city_id);
        if ((gcwDefenderRegion == null) || (gcwDefenderRegion.length() <= 0))
        {
            city_info[2] = "GCW Region Defender: (None)";
            city_info[3] = "GCW Region Defender Bonus: (None)";
        }
        else 
        {
            final int timeJoinedGcwDefenderRegion = cityGetTimeJoinedGcwDefenderRegion(city_id);
            city_info[2] = "GCW Region Defender: " + localize(new string_id("gcw_regions", gcwDefenderRegion));
            if (timeJoinedGcwDefenderRegion > 0)
            {
                city_info[2] += (" (started defending on " + getCalendarTimeStringLocal(timeJoinedGcwDefenderRegion) + ")");
                final int gcwDaysRequiredForGcwRegionDefenderBonus = utils.stringToInt(getConfigSetting("GameServer", "gcwDaysRequiredForGcwRegionDefenderBonus"));
                final int age = getCalendarTime() - timeJoinedGcwDefenderRegion;
                if (age > (gcwDaysRequiredForGcwRegionDefenderBonus * 86400))
                {
                    if ((-615855020) == factionId)
                    {
                        city_info[3] = "GCW Region Defender Bonus: " + getGcwDefenderRegionImperialBonus(gcwDefenderRegion) + "%";
                    }
                    else if ((370444368) == factionId)
                    {
                        city_info[3] = "GCW Region Defender Bonus: " + getGcwDefenderRegionRebelBonus(gcwDefenderRegion) + "%";
                    }
                    else 
                    {
                        city_info[3] = "GCW Region Defender Bonus: (None - cannot determine factional alignment)";
                    }
                }
                else 
                {
                    city_info[3] = ("GCW Region Defender Bonus: (None - hasn't defended for " + gcwDaysRequiredForGcwRegionDefenderBonus + " days)\n");
                }
            }
            else 
            {
                city_info[3] = "GCW Region Defender Bonus: (None - cannot determine time started defending)";
            }
        }
        location city_loc = cityGetLocation(city_id);
        string_id city_location_text = new string_id(STF, "location_prompt");
        string_id city_radius_text = new string_id(STF, "radius_prompt");
        city_info[4] = getString(city_location_text) + (int)city_loc.x + ", " + (int)city_loc.z + getString(city_radius_text) + cityGetRadius(city_id) + "m";
        string_id citizens_text = new string_id(STF, "citizen_prompt");
        string_id structures_text = new string_id(STF, "structures_prompt");
        city_info[5] = getString(citizens_text) + cityGetCitizenIds(city_id).length + getString(structures_text) + cityGetStructureIds(city_id).length;
        string_id city_specialization_text = new string_id(STF, "specialization_prompt");
        city_info[6] = getString(city_specialization_text) + localize(new string_id(STF, city.cityGetSpecString(city_id)));
        city_info[7] = "@city/city:income_tax" + " -- " + cityGetIncomeTax(city_id) + " cr";
        city_info[8] = "@city/city:property_tax" + " -- " + cityGetPropertyTax(city_id) + "%";
        city_info[9] = "@city/city:sales_tax" + " -- " + cityGetSalesTax(city_id) + "%";
        city_info[10] = "@city/city:travel_tax" + " -- " + cityGetTravelCost(city_id) + " cr";
        String garage = city_name + ".garageFee";
        if (!hasObjVar(cityHall, garage))
        {
            setObjVar(cityHall, garage, 0);
        }
        city_info[11] = "@city/city:garage_tax" + " -- " + getIntObjVar(cityHall, garage) + "%";
        sui.listbox(self, player, "@city/city:city_info_d", sui.OK_CANCEL, "@city/city:city_info_t", city_info, "handleCityInfoSelect", true);
    }
    public void showCitizensList(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id[] structures = cityGetStructureIds(city_id);
        if (structures == null || structures.length <= 0)
        {
            sendSystemMessage(player, new string_id(STF, "no_city_citizen_data"));
            return;
        }
        int now = getGameTime();
        if (utils.hasScriptVar(self, "citizen_list_refresh_time"))
        {
            int time = utils.getIntScriptVar(self, "citizen_list_refresh_time");
            if ((time + 3600) < now)
            {
                utils.removeScriptVar(self, city.CITIZEN_LIST_QUERIED);
            }
        }
        boolean hasAsked = utils.hasScriptVar(self, city.CITIZEN_LIST_QUERIED);
        if (!hasAsked)
        {
            utils.setScriptVar(self, "citizen_list_refresh_time", now);
            utils.setScriptVar(self, city.CITIZEN_LIST_QUERIED, true);
        }
        dictionary residentInfo = new dictionary();
        for (int j = 0; j < structures.length; ++j)
        {
            if (!exists(structures[j]) && !hasAsked)
            {
                dictionary dict = new dictionary();
                dict.put("terminal_id", self);
                dict.put("city_id", city_id);
                if (!messageTo(structures[j], "getStructureCitizenInformation", dict, 0.0f, false))
                {
                    CustomerServiceLog("player_city", "Citizen List: City(" + city_id + ")" + cityGetName(city_id) + " MessageTo failed!  Attempted to message Object(" + structures[j] + ").");
                }
                CustomerServiceLog("player_city", "Citizen List: City(" + city_id + ")" + cityGetName(city_id) + " Structure(" + structures[j] + ") does not exist on this Server - Messaging to object to obtain Data.");
            }
            if (city.isNormalStructure(city_id, structures[j]) && !player_structure.isCivic(structures[j]))
            {
                if (exists(structures[j]) && hasObjVar(structures[j], player_structure.VAR_RESIDENCE_BUILDING))
                {
                    obj_id citizenId = getObjIdObjVar(structures[j], player_structure.VAR_RESIDENCE_BUILDING);
                    dictionary residentDict = new dictionary();
                    residentDict.put("house_id", structures[j]);
                    residentDict.put("house_loc", getLocation(structures[j]));
                    residentInfo.put(citizenId, residentDict);
                }
            }
        }
        dictionary newDict = new dictionary();
        newDict.put("city_id", city_id);
        newDict.put("player", player);
        residentInfo.put("city_dict", newDict);
        sendSystemMessage(player, new string_id(STF, "accessing_citizen_city_network"));
        messageTo(self, "DisplayCitizenList", residentInfo, 3.0f, false);
    }
    public int DisplayCitizenList(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        dictionary city_info = params.getDictionary("city_dict");
        int city_id = city_info.getInt("city_id");
        obj_id player = city_info.getObjId("player");
        obj_id[] listOfResidents = cityGetCitizenIds(city_id);
        if (listOfResidents == null || listOfResidents.length <= 0)
        {
            sendSystemMessage(player, new string_id(STF, "no_city_citizen_data"));
            return SCRIPT_CONTINUE;
        }
        int columnCount = 6;
        String[][] displayData = new String[listOfResidents.length][columnCount];
        location[] locationData = new location[listOfResidents.length];
        for (int k = 0; k < listOfResidents.length; k++)
        {
            for (int l = 0; l < columnCount; l++)
            {
                String ownerName = cityGetCitizenName(city_id, listOfResidents[k]);
                switch (l)
                {
                    case 0:
                    if (ownerName == null || ownerName.length() <= 0)
                    {
                        ownerName = "@city/city:unknown_owner_data";
                    }
                    if (!isGod(player))
                    {
                        if (city.isMilitiaOfCity(listOfResidents[k], city_id))
                        {
                            displayData[k][l] = ownerName + " (Militia)";
                        }
                        else 
                        {
                            displayData[k][l] = ownerName;
                        }
                    }
                    else 
                    {
                        if (city.isMilitiaOfCity(listOfResidents[k], city_id))
                        {
                            displayData[k][l] = ownerName + " (Militia) : " + listOfResidents[k];
                        }
                        else 
                        {
                            displayData[k][l] = ownerName + " : " + listOfResidents[k];
                        }
                    }
                    break;
                    case 1:
                    if (params.containsKey(listOfResidents[k]))
                    {
                        dictionary residentDict = params.getDictionary(listOfResidents[k]);
                        obj_id house = residentDict.getObjId("house_id");
                        String houseName = getEncodedName(house);
                        if (houseName == null || houseName.length() <= 0)
                        {
                            houseName = "@city/city:unknown_structure_data";
                        }
                        if (!isGod(player))
                        {
                            displayData[k][l] = houseName;
                        }
                        else 
                        {
                            displayData[k][l] = houseName + " : " + house;
                        }
                    }
                    else if (utils.hasScriptVarTree(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k]))
                    {
                        obj_id house = utils.getObjIdScriptVar(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k] + ".house_id");
                        String houseName = utils.getStringScriptVar(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k] + ".house_name");
                        if (houseName == null || houseName.length() <= 0)
                        {
                            houseName = "@city/city:unknown_structure_data";
                        }
                        if (!isGod(player))
                        {
                            displayData[k][l] = houseName;
                        }
                        else 
                        {
                            displayData[k][l] = houseName + " : " + house;
                        }
                    }
                    else if (cityGetLeader(city_id) == listOfResidents[k])
                    {
                        obj_id house = getTopMostContainer(self);
                        String houseName = getEncodedName(house);
                        if (houseName == null || houseName.length() <= 0)
                        {
                            houseName = "@city/city:unknown_structure_data";
                        }
                        if (!isGod(player))
                        {
                            displayData[k][l] = houseName;
                        }
                        else 
                        {
                            displayData[k][l] = houseName + " : " + house;
                        }
                    }
                    else 
                    {
                        String houseName = "unkown";
                        if (!isGod(player))
                        {
                            displayData[k][l] = houseName;
                        }
                        else 
                        {
                            displayData[k][l] = houseName + " : No Id found";
                        }
                    }
                    break;
                    case 2:
                    if (params.containsKey(listOfResidents[k]))
                    {
                        dictionary residentDict = params.getDictionary(listOfResidents[k]);
                        obj_id house = residentDict.getObjId("house_id");
                        String houseLoc = getStructureDisplayLocation(residentDict.getLocation("house_loc"));
                        if (houseLoc == null || houseLoc.length() <= 0)
                        {
                            houseLoc = "@city/city:unknown_location_data";
                        }
                        displayData[k][l] = houseLoc;
                        locationData[k] = residentDict.getLocation("house_loc");
                    }
                    else if (utils.hasScriptVarTree(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k]))
                    {
                        obj_id house = utils.getObjIdScriptVar(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k] + ".house_id");
                        String houseLoc = getStructureDisplayLocation(utils.getLocationScriptVar(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k] + ".house_loc"));
                        if (houseLoc == null || houseLoc.length() <= 0)
                        {
                            houseLoc = "@city/city:unknown_location_data";
                        }
                        displayData[k][l] = houseLoc;
                        locationData[k] = utils.getLocationScriptVar(self, city.CITIZEN_LIST_DATA + "." + listOfResidents[k] + ".house_loc");
                    }
                    else if (cityGetLeader(city_id) == listOfResidents[k])
                    {
                        obj_id house = getTopMostContainer(self);
                        String houseLoc = getStructureDisplayLocation(getLocation(house));
                        if (houseLoc == null || houseLoc.length() <= 0)
                        {
                            houseLoc = "@city/city:unknown_location_data";
                        }
                        displayData[k][l] = houseLoc;
                        locationData[k] = getLocation(house);
                    }
                    else 
                    {
                        String houseLoc = "@city/city:unknown_location_data";
                        displayData[k][l] = houseLoc;
                        locationData[k] = null;
                    }
                    break;
                    case 3:
                    int ownerLevel = cityGetCitizenLevel(city_id, listOfResidents[k]);
                    if (ownerLevel != 0)
                    {
                        displayData[k][l] = "" + ownerLevel;
                    }
                    else 
                    {
                        displayData[k][l] = "@city/city:unknown_level";
                    }
                    break;
                    case 4:
                    String ownerProfession = cityGetCitizenProfession(city_id, listOfResidents[k]);
                    if (ownerProfession != null && ownerProfession.length() > 0)
                    {
                        displayData[k][l] = "@ui_roadmap:" + ownerProfession;
                    }
                    else 
                    {
                        displayData[k][l] = "@city/city:unknown_profession";
                    }
                    break;
                    case 5:
                    String onlineOfflineStatus = utils.getOnlineOfflineStatus(listOfResidents[k]);
                    displayData[k][l] = onlineOfflineStatus;
                    break;
                }
            }
        }
        utils.setScriptVar(player, "citizenLocationData", locationData);
        utils.setScriptVar(player, "citizenDisplayData", displayData);
        showCitizenshipListSui(self, player, displayData);
        return SCRIPT_CONTINUE;
    }
    public int handleCitizenSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        String eventType = sui.getEventType(params);
        if (btn == sui.BP_CANCEL)
        {
            if (utils.hasScriptVar(player, "citizenDisplayData"))
            {
                utils.removeScriptVar(player, "citizenDisplayData");
            }
            if (utils.hasScriptVar(player, "citizenLocationData"))
            {
                utils.removeScriptVar(player, "citizenLocationData");
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "citizenDisplayData") && !utils.hasScriptVar(player, "citizenLocationData"))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        location[] locationData = utils.getLocationArrayScriptVar(player, "citizenLocationData");
        String[][] displayData = utils.getStringArrayArrayScriptVar(player, "citizenDisplayData");
        if (locationData == null || locationData.length <= 0)
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        if (displayData == null || displayData.length <= 0)
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getTableLogicalIndex(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            location chosenLoc = locationData[idx];
            obj_id structureWaypoint = createWaypointInDatapad(player, chosenLoc);
            setWaypointName(structureWaypoint, displayData[idx][0]);
            setWaypointActive(structureWaypoint, true);
            sendSystemMessage(player, new string_id(STF, "creating_waypoint"));
        }
        showCitizenshipListSui(self, player, displayData);
        return SCRIPT_CONTINUE;
    }
    public void showCitizenshipListSui(obj_id self, obj_id player, String[][] displayData) throws InterruptedException
    {
        String[] colTitles = 
        {
            "@city/city:column_citizen_name",
            "@city/city:column_structure_name",
            "@city/city:column_structure_location",
            "@city/city:column_citizen_level",
            "@city/city:column_citizen_profession",
            "@city/city:column_location_or_last_login"
        };
        String[] colTypes = 
        {
            "text",
            "text",
            "text",
            "integer",
            "text",
            "text"
        };
        string_id prompt = new string_id(STF, "citizen_list_prompt");
        String stringPrompt = getString(prompt);
        sui.tableRowMajor(self, player, sui.OK_CANCEL, "@city/city:citizen_list_t", "handleCitizenSelect", stringPrompt, colTitles, colTypes, displayData, true);
    }
    public void showStructuresList(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        if (utils.hasScriptVar(self, "lastUpdateTime") && utils.hasScriptVar(self, "loadedStructuresIds") && utils.hasScriptVar(self, "loadedStructuresNames") && utils.hasScriptVar(self, "loadedStructuresLocations"))
        {
            int currentGameTime = getGameTime();
            int refreshTime = utils.getIntScriptVar(self, "lastUpdateTime");
            if (currentGameTime > refreshTime)
            {
                sendSystemMessage(player, new string_id(STF, "accessing_city_network"));
                getCityStructuresInfo(player, self, city_id);
            }
            else 
            {
                showCityStructuresSuiFromScriptVars(self, player);
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id(STF, "accessing_city_network"));
            getCityStructuresInfo(player, self, city_id);
        }
    }
    public void getCityStructuresInfo(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id[] structures = cityGetStructureIds(city_id);
        Vector loadedStructures = new Vector();
        loadedStructures.setSize(0);
        Vector unloadedStructures = new Vector();
        unloadedStructures.setSize(0);
        for (int i = 0; i < structures.length; i++)
        {
            if (structures[i].isLoaded())
            {
                utils.addElement(loadedStructures, structures[i]);
            }
            else 
            {
                utils.addElement(unloadedStructures, structures[i]);
            }
        }
        obj_id[] loadedStructuresStaticArray = utils.toStaticObjIdArray(loadedStructures);
        obj_id[] unloadedStructuresStaticArray = utils.toStaticObjIdArray(unloadedStructures);
        if (loadedStructuresStaticArray != null)
        {
            LoadedCityStructuresInfo(self, player, city_id, loadedStructuresStaticArray);
        }
        if (unloadedStructuresStaticArray != null)
        {
            messageUnloadedCityStructures(self, player, city_id, unloadedStructuresStaticArray);
        }
    }
    public void LoadedCityStructuresInfo(obj_id self, obj_id player, int city_id, obj_id[] loadedStructures) throws InterruptedException
    {
        location[] structuresLocation = new location[loadedStructures.length];
        String[] structuresName = new String[loadedStructures.length];
        for (int i = 0; i < loadedStructures.length; i++)
        {
            if (!city.isNormalStructure(city_id, loadedStructures[i]))
            {
                structuresLocation[i] = getLocation(loadedStructures[i]);
                structuresName[i] = localize(getNameStringId(loadedStructures[i]));
            }
            else if (player_structure.isCivic(loadedStructures[i]))
            {
                structuresLocation[i] = getLocation(loadedStructures[i]);
                float cond = ((float)player_structure.getStructureCondition(loadedStructures[i])) / ((float)player_structure.getMaxCondition(loadedStructures[i]));
                int outcond = (int)(cond * 100);
                structuresName[i] = getEncodedName(loadedStructures[i]) + " (Condition : " + outcond + "%)";
            }
            else 
            {
                loadedStructures[i] = null;
            }
        }
        Vector loadedStructuresFinal = new Vector();
        loadedStructuresFinal.setSize(0);
        Vector structuresLocationFinal = new Vector();
        structuresLocationFinal.setSize(0);
        Vector structuresNameFinal = new Vector();
        structuresNameFinal.setSize(0);
        for (int j = 0; j < loadedStructures.length; j++)
        {
            if (loadedStructures[j] != null)
            {
                utils.addElement(loadedStructuresFinal, loadedStructures[j]);
                utils.addElement(structuresLocationFinal, structuresLocation[j]);
                utils.addElement(structuresNameFinal, structuresName[j]);
            }
        }
        obj_id[] structuresIdStaticArray = utils.toStaticObjIdArray(loadedStructuresFinal);
        String[] structuresNameStaticArray = utils.toStaticStringArray(structuresNameFinal);
        location[] structuresLocationStaticArray = utils.toStaticLocationArray(structuresLocationFinal);
        utils.setScriptVar(self, "loadedStructuresIds", structuresIdStaticArray);
        utils.setScriptVar(self, "loadedStructuresNames", structuresNameStaticArray);
        utils.setScriptVar(self, "loadedStructuresLocations", structuresLocationStaticArray);
        int currentGameTime = getGameTime();
        utils.setScriptVar(self, "lastUpdateTime", currentGameTime + 3600);
        dictionary dict = new dictionary();
        dict.put("player", player);
        dict.put("city_id", city_id);
        dict.put("city_terminal", self);
        messageTo(self, "DisplayCityStructuresList", dict, 3.0f, false);
    }
    public void messageUnloadedCityStructures(obj_id self, obj_id player, int city_id, obj_id[] unloadedStructures) throws InterruptedException
    {
        for (int k = 0; k < unloadedStructures.length; k++)
        {
            dictionary d = new dictionary();
            d.put("player", player);
            d.put("city_id", city_id);
            d.put("city_terminal", self);
            if (!messageTo(unloadedStructures[k], "handleUnloadedCityStructureReportData", d, 0.0f, false))
            {
                CustomerServiceLog("player_city", "Structure List: City(" + city_id + ")" + cityGetName(city_id) + " MessageTo Structure(" + unloadedStructures[k] + ") Failed!");
            }
            CustomerServiceLog("player_city", "Structure List: City(" + city_id + ")" + cityGetName(city_id) + " Requesting Structure Info from Object(" + unloadedStructures[k] + ") - Sent Data: (requesting)Player(" + player + "), CityID(" + city_id + "), City Terminal(" + self + ").");
        }
    }
    public int DisplayCityStructuresList(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id city_terminal = params.getObjId("city_terminal");
        int city_id = params.getInt("city_id");
        if (!isIdValid(player) || !isIdValid(city_terminal))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(city_terminal, "loadedStructuresIds") && !utils.hasScriptVar(city_terminal, "loadedStructuresNames") && !utils.hasScriptVar(city_terminal, "loadedStructuresLocations"))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        obj_id[] structureIds = utils.getObjIdArrayScriptVar(city_terminal, "loadedStructuresIds");
        String[] structureNames = utils.getStringArrayScriptVar(city_terminal, "loadedStructuresNames");
        location[] structureLocs = utils.getLocationArrayScriptVar(city_terminal, "loadedStructuresLocations");
        final int columnCount = 3;
        String[][] displayData = new String[structureIds.length][columnCount];
        for (int i = 0; i < structureIds.length; i++)
        {
            for (int k = 0; k < columnCount; k++)
            {
                switch (k)
                {
                    case 0:
                    if (structureNames[i] == null || structureNames[i].equals(""))
                    {
                        structureNames[i] = "Unknown Structure";
                    }
                    displayData[i][k] = structureNames[i];
                    break;
                    case 1:
                    displayData[i][k] = getStructureDisplayLocation(structureLocs[i]);
                    break;
                    case 2:
                    String template = getTemplateName(structureIds[i]);
                    if (template.indexOf("garden_") > -1)
                    {
                        displayData[i][k] = "Garden";
                        break;
                    }
                    if (city.isMissionTerminal(city_id, structureIds[i]))
                    {
                        displayData[i][k] = "Terminal";
                        break;
                    }
                    if (city.isDecoration(city_id, structureIds[i]))
                    {
                        displayData[i][k] = "Decoration";
                        break;
                    }
                    displayData[i][k] = "Structure";
                    break;
                    default:
                    LOG("sissynoid", "Horrible Failure");
                    break;
                }
            }
        }
        showCityStructuresSui(self, player, displayData);
        return SCRIPT_CONTINUE;
    }
    public void showCityStructuresSuiFromScriptVars(obj_id self, obj_id player) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "loadedStructuresIds") && !utils.hasScriptVar(self, "loadedStructuresNames") && !utils.hasScriptVar(self, "loadedStructuresLocations"))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
        }
        int city_id = city.checkCity(self, false);
        obj_id[] structureIds = utils.getObjIdArrayScriptVar(self, "loadedStructuresIds");
        String[] structureNames = utils.getStringArrayScriptVar(self, "loadedStructuresNames");
        location[] structureLocs = utils.getLocationArrayScriptVar(self, "loadedStructuresLocations");
        final int columnCount = 3;
        String[][] displayData = new String[structureIds.length][columnCount];
        for (int i = 0; i < structureIds.length; i++)
        {
            for (int k = 0; k < columnCount; k++)
            {
                switch (k)
                {
                    case 0:
                    if (structureNames[i] == null || structureNames[i].equals(""))
                    {
                        structureNames[i] = "Unknown Structure";
                    }
                    displayData[i][k] = structureNames[i];
                    break;
                    case 1:
                    displayData[i][k] = getStructureDisplayLocation(structureLocs[i]);
                    break;
                    case 2:
                    String template = getTemplateName(structureIds[i]);
                    if (template.indexOf("garden_") > -1)
                    {
                        displayData[i][k] = "Garden";
                        break;
                    }
                    if (city.isMissionTerminal(city_id, structureIds[i]))
                    {
                        displayData[i][k] = "Terminal";
                        break;
                    }
                    if (city.isDecoration(city_id, structureIds[i]))
                    {
                        displayData[i][k] = "Decoration";
                        break;
                    }
                    displayData[i][k] = "Structure";
                    break;
                    default:
                    LOG("sissynoid", "Horrible Failure");
                    break;
                }
            }
        }
        showCityStructuresSui(self, player, displayData);
    }
    public void showCityStructuresSui(obj_id self, obj_id player, String[][] displayData) throws InterruptedException
    {
        String[] colTitles = 
        {
            "Structure Name",
            "Structure Location",
            "Structure Type"
        };
        String[] colTypes = 
        {
            "text",
            "text",
            "text"
        };
        string_id prompt = new string_id(STF, "structure_list_prompt");
        String stringPrompt = getString(prompt);
        sui.tableRowMajor(self, player, sui.OK_CANCEL, "@city/city:structure_list_t", "handleStructureWaypointSelect", stringPrompt, colTitles, colTypes, displayData, true);
    }
    public String getStructureDisplayLocation(location loc) throws InterruptedException
    {
        String returnString = "" + Math.round(loc.x) + ", " + Math.round(loc.y) + ", " + Math.round(loc.z);
        return returnString;
    }
    public int RecievedUnloadedStructureResponse(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id_for_log = getCityAtLocation(getLocation(self), 0);
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog("player_city", "Structure List: City(" + city_id_for_log + ")" + cityGetName(city_id_for_log) + " Data was returned, but Null or Empty");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id city_terminal = params.getObjId("city_terminal");
        int city_id = params.getInt("city_id");
        obj_id newObjectId = params.getObjId("unloadedStructureId");
        String newObjectName = params.getString("unloadedStructureName");
        location newObjectLocation = params.getLocation("unloadedStructureLocation");
        if (!isIdValid(player) || !isIdValid(city_terminal) || !isIdValid(newObjectId))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(city_terminal, "loadedStructuresIds") && !utils.hasScriptVar(city_terminal, "loadedStructuresNames") && !utils.hasScriptVar(city_terminal, "loadedStructuresLocations"))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        obj_id[] structureIds = utils.getObjIdArrayScriptVar(city_terminal, "loadedStructuresIds");
        String[] structureNames = utils.getStringArrayScriptVar(city_terminal, "loadedStructuresNames");
        location[] structureLocs = utils.getLocationArrayScriptVar(city_terminal, "loadedStructuresLocations");
        Vector resizeableloadedStructuresIds = new Vector(Arrays.asList(structureIds));
        Vector resizeableloadedStructuresNames = new Vector(Arrays.asList(structureNames));
        Vector resizeableloadedStructuresLocations = new Vector(Arrays.asList(structureLocs));
        utils.addElement(resizeableloadedStructuresIds, newObjectId);
        utils.addElement(resizeableloadedStructuresNames, newObjectName);
        utils.addElement(resizeableloadedStructuresLocations, newObjectLocation);
        obj_id[] loadedStructuresIds = utils.toStaticObjIdArray(resizeableloadedStructuresIds);
        String[] loadedStructuresNames = utils.toStaticStringArray(resizeableloadedStructuresNames);
        location[] loadedStructuresLocations = utils.toStaticLocationArray(resizeableloadedStructuresLocations);
        utils.setScriptVar(self, "loadedStructuresIds", loadedStructuresIds);
        utils.setScriptVar(self, "loadedStructuresNames", loadedStructuresNames);
        utils.setScriptVar(self, "loadedStructuresLocations", loadedStructuresLocations);
        return SCRIPT_CONTINUE;
    }
    public int handleStructureWaypointSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        String eventType = sui.getEventType(params);
        if (btn == sui.BP_CANCEL)
        {
            if (utils.hasScriptVar(player, "city_structure_report_logical"))
            {
                utils.removeScriptVar(player, "city_structure_report_logical");
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "loadedStructuresIds") && !utils.hasScriptVar(self, "loadedStructuresNames") && !utils.hasScriptVar(self, "loadedStructuresLocations"))
        {
            sendSystemMessage(player, new string_id(STF, "no_available_waypoint"));
            return SCRIPT_CONTINUE;
        }
        obj_id[] structureIds = utils.getObjIdArrayScriptVar(self, "loadedStructuresIds");
        String[] structureNames = utils.getStringArrayScriptVar(self, "loadedStructuresNames");
        location[] structureLocs = utils.getLocationArrayScriptVar(self, "loadedStructuresLocations");
        int idx = sui.getTableLogicalIndex(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id structureWaypoint = createWaypointInDatapad(player, structureLocs[idx]);
            setWaypointName(structureWaypoint, structureNames[idx]);
            setWaypointActive(structureWaypoint, true);
            sendSystemMessage(player, new string_id(STF, "creating_waypoint"));
        }
        showCityStructuresSuiFromScriptVars(self, player);
        return SCRIPT_CONTINUE;
    }
    public void showAdvancementInfo(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id city_hall = getTopMostContainer(self);
        String template = getTemplateName(city_hall);
        if (template.indexOf("cityhall_") < 0)
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        int city_rank = city.getCityRank(city_id);
        string_id rank_name = new string_id(STF, "rank" + city_rank);
        String rank_string = localize(rank_name);
        obj_id[] citizens = cityGetCitizenIds(city_id);
        int next_pop_req = -1;
        if (city_rank < 5)
        {
            next_pop_req = dataTableGetInt(city.RANK_TABLE, city_rank, city.RANK_POPULATION);
        }
        int cur_pop_req = dataTableGetInt(city.RANK_TABLE, city_rank - 1, city.RANK_POPULATION);
        Vector rankInfo = new Vector();
        rankInfo.setSize(0);
        string_id city_rank_prompt = new string_id(STF, "city_rank_prompt");
        string_id city_pop_prompt = new string_id(STF, "city_pop_prompt");
        string_id total_citizens = new string_id(STF, "citizens");
        rankInfo = utils.addElement(rankInfo, getString(city_rank_prompt) + rank_string + " (" + city_rank + ")");
        rankInfo = utils.addElement(rankInfo, getString(city_pop_prompt) + citizens.length + getString(total_citizens));
        if (citizens.length < cur_pop_req)
        {
            string_id pop_req_rank_current_green = new string_id(STF, "pop_req_current_rank");
            string_id prev_rank_citizens = new string_id(STF, "citizens");
            rankInfo = utils.addElement(rankInfo, "\\#FF0000" + getString(pop_req_rank_current_green) + cur_pop_req + getString(prev_rank_citizens) + "\\");
        }
        else 
        {
            string_id pop_req_rank_current = new string_id(STF, "pop_req_current_rank");
            string_id prev_rank_citizens = new string_id(STF, "citizens");
            rankInfo = utils.addElement(rankInfo, getString(pop_req_rank_current) + cur_pop_req + getString(prev_rank_citizens));
        }
        if (next_pop_req == -1)
        {
            string_id max_rank_achieved = new string_id(STF, "max_rank_achieved");
            rankInfo = utils.addElement(rankInfo, getString(max_rank_achieved));
        }
        else 
        {
            string_id pop_req_rank_next = new string_id(STF, "pop_req_next_rank");
            string_id next_rank_citizens = new string_id(STF, "citizens");
            rankInfo = utils.addElement(rankInfo, getString(pop_req_rank_next) + next_pop_req + getString(next_rank_citizens));
        }
        string_id max_structures = new string_id(STF, "max_structures");
        rankInfo = utils.addElement(rankInfo, getString(max_structures) + city.getMaxCivicCount(city_id));
        string_id max_decorations = new string_id(STF, "max_decorations");
        rankInfo = utils.addElement(rankInfo, getString(max_decorations) + city.getMaxDecorationCount(city_id));
        string_id max_terminals = new string_id(STF, "max_terminals");
        rankInfo = utils.addElement(rankInfo, getString(max_terminals) + city.getMaxMTCount(city_id));
        string_id max_trainers = new string_id(STF, "max_trainers");
        rankInfo = utils.addElement(rankInfo, getString(max_trainers) + city.getMaxTrainerCount(city_id));
        string_id enabled_structures = new string_id(STF, "rank_enabled_structures");
        rankInfo = utils.addElement(rankInfo, "\\#00FF00" + getString(enabled_structures) + "\\");
        int i = 7;
        if (city_rank >= 1)
        {
            string_id sm_garden = new string_id(STF, "small_garden");
            rankInfo = utils.addElement(rankInfo, getString(sm_garden));
        }
        if (city_rank >= 2)
        {
            string_id bank = new string_id(STF, "bank");
            rankInfo = utils.addElement(rankInfo, getString(bank));
            string_id cantina = new string_id(STF, "cantina");
            rankInfo = utils.addElement(rankInfo, getString(cantina));
            string_id med_garden = new string_id(STF, "medium_garden");
            rankInfo = utils.addElement(rankInfo, getString(med_garden));
            string_id garage = new string_id(STF, "garage");
            rankInfo = utils.addElement(rankInfo, getString(garage));
        }
        if (city_rank >= 3)
        {
            string_id cloning = new string_id(STF, "cloning_facility");
            rankInfo = utils.addElement(rankInfo, getString(cloning));
            string_id hospital = new string_id(STF, "hospital");
            rankInfo = utils.addElement(rankInfo, getString(hospital));
            string_id lg_garden = new string_id(STF, "large_garden");
            rankInfo = utils.addElement(rankInfo, getString(lg_garden));
        }
        if (city_rank >= 4)
        {
            string_id theater = new string_id(STF, "theater");
            rankInfo = utils.addElement(rankInfo, getString(theater));
            string_id shuttleport = new string_id(STF, "shuttleport");
            rankInfo = utils.addElement(rankInfo, getString(shuttleport));
        }
        sui.listbox(self, player, "@city/city:rank_info_d", sui.OK_CANCEL, "@city/city:rank_info_t", rankInfo, "handleRankInfo", true);
        obj_id city_master = getMasterCityObject();
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        outparams.put("city_id", city_id);
        messageTo(city_master, "reportUpdateEstimate", outparams, 0.f, false);
    }
    public void showMaintInfo(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id city_hall = getTopMostContainer(self);
        String template = getTemplateName(city_hall);
        if (template.indexOf("cityhall_") < 0)
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        int city_hall_cost = city.getStructureCost(city_id, city_hall);
        int spec_cost = city.cityGetSpecCost(city_id);
        int over_cap = 0;
        int total_cost = 0;
        String[] maintInfo;
        int civic_count = city.getCivicCount(city_id);
        int max_civic = city.getMaxCivicCount(city_id);
        if (civic_count > max_civic)
        {
            over_cap = civic_count - max_civic;
            total_cost += 75000 * over_cap;
            maintInfo = new String[9];
        }
        else 
        {
            maintInfo = new String[7];
        }
        int structures_cost = 0;
        int deco_cost = 0;
        int other_cost = 0;
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (structures[i] == city_hall)
            {
                continue;
            }
            if (city.isNormalStructure(city_id, structures[i]))
            {
                structures_cost += city.getStructureCost(city_id, structures[i]);
            }
            else if (city.isDecoration(city_id, structures[i]))
            {
                deco_cost += city.getStructureCost(city_id, structures[i]);
            }
            else 
            {
                other_cost += city.getStructureCost(city_id, structures[i]);
            }
        }
        total_cost = city_hall_cost + spec_cost + structures_cost + deco_cost + other_cost;
        if (city.isCityRegistered(city_id))
        {
            city_hall_cost -= 5000;
        }
        int i = 0;
        string_id total_maint = new string_id(STF, "tot_maint");
        string_id credits_suffix = new string_id(STF, "credits");
        string_id city_hall_prompt = new string_id(STF, "city_hall");
        maintInfo[i++] = "\\#FF0000" + getString(total_maint) + "\\" + total_cost + getString(credits_suffix);
        maintInfo[i++] = getString(city_hall_prompt) + city_hall_cost + getString(credits_suffix);
        if (civic_count > max_civic)
        {
            string_id civ_struc_pen = new string_id(STF, "civic_structure_panalty");
            maintInfo[i++] = "\\#FF0000" + getString(civ_struc_pen) + "\\ " + (75000 * over_cap);
            string_id you_have = new string_id(STF, "you_have");
            string_id more_than_cap = new string_id(STF, "more_than_cap");
            maintInfo[i++] = "  (" + getString(you_have) + over_cap + getString(more_than_cap) + max_civic + ".)";
        }
        if (city.isCityRegistered(city_id))
        {
            string_id map_reg_cost = new string_id(STF, "map_reg_cost");
            maintInfo[i++] = getString(map_reg_cost);
        }
        else 
        {
            string_id map_unreg = new string_id(STF, "map_unreg");
            maintInfo[i++] = getString(map_unreg);
        }
        string_id spec_text = new string_id(STF, "specialization");
        maintInfo[i++] = getString(spec_text) + spec_cost + getString(credits_suffix);
        string_id struc_text = new string_id(STF, "structures");
        maintInfo[i++] = getString(struc_text) + structures_cost + getString(credits_suffix);
        string_id deco_text = new string_id(STF, "decorations");
        maintInfo[i++] = getString(deco_text) + deco_cost + getString(credits_suffix);
        string_id tnt_text = new string_id(STF, "train_and_term");
        maintInfo[i++] = getString(tnt_text) + other_cost + getString(credits_suffix);
        sui.listbox(self, player, "@city/city:maint_info_d", sui.OK_CANCEL, "@city/city:maint_info_t", maintInfo, "handleMaintInfo", true);
        obj_id city_master = getMasterCityObject();
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        outparams.put("city_id", city_id);
        messageTo(city_master, "reportUpdateEstimate", outparams, 0.f, false);
    }
    public void changeCityName(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        sui.inputbox(self, player, "@city/city:city_name_new_d", sui.OK_CANCEL, "@city/city:city_name_new_t", sui.INPUT_NORMAL, null, "handleSetCityName", null);
    }
    public int handleSetCityName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String cityName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if ((cityName.equals("")) || isNameReserved(cityName))
        {
            sendSystemMessage(player, SID_OBSCENE);
            sui.inputbox(self, player, "@city/city:city_name_new_d", sui.OK_CANCEL, "@city/city:city_name_new_t", sui.INPUT_NORMAL, null, "handleSetCityName", null);
            return SCRIPT_CONTINUE;
        }
        if (cityName.length() > 24)
        {
            cityName = cityName.substring(0, 23);
        }
        if (!city.isUniqueCityName(cityName))
        {
            sendSystemMessage(player, SID_NOT_UNIQUE);
            sui.inputbox(self, player, "@city/city:city_name_new_d", sui.OK_CANCEL, "@city/city:city_name_new_t", sui.INPUT_NORMAL, null, "handleSetCityName", null);
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        city.setName(city_id, cityName);
        obj_id mayor = cityGetLeader(city_id);
        sendSystemMessage(mayor, SID_NAME_CHANGED);
        return SCRIPT_CONTINUE;
    }
    public void modifyMilitia(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        if (!hasCommand(player, "manage_militia"))
        {
            sendSystemMessage(player, SID_CANT_MILITIA);
            return;
        }
        obj_id[] citizens = cityGetCitizenIds(city_id);
        Vector militiaMembers = new Vector();
        militiaMembers.setSize(0);
        militiaMembers = utils.addElement(militiaMembers, "Add Militia Member");
        for (int i = 0; i < citizens.length; i++)
        {
            if (city.hasMilitiaFlag(citizens[i], city_id))
            {
                String name = cityGetCitizenName(city_id, citizens[i]);
                militiaMembers = utils.addElement(militiaMembers, name);
            }
        }
        sui.listbox(self, player, "@city/city:militia_d", sui.OK_CANCEL, "@city/city:militia_t", militiaMembers, "handleRemoveMilitia", true);
    }
    public int handleRemoveMilitia(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == 0)
        {
            sui.inputbox(self, player, "@city/city:militia_new_d", sui.OK_CANCEL, "@city/city:militia_new_t", sui.INPUT_NORMAL, null, "handleAddMilitia", null);
        }
        else 
        {
            int j = 0;
            String removeName = null;
            obj_id[] citizens = cityGetCitizenIds(city_id);
            Vector militiaMembers = new Vector();
            militiaMembers.setSize(0);
            militiaMembers = utils.addElement(militiaMembers, "Add Militia Member");
            for (int i = 0; i < citizens.length; i++)
            {
                if (city.hasMilitiaFlag(citizens[i], city_id))
                {
                    j++;
                    String name = cityGetCitizenName(city_id, citizens[i]);
                    militiaMembers = utils.addElement(militiaMembers, name);
                    if (j == idx)
                    {
                        utils.setScriptVar(self, "removal.temp", citizens[i]);
                        removeName = name;
                        break;
                    }
                }
            }
            if (removeName == null)
            {
                return SCRIPT_CONTINUE;
            }
            string_id rem_militia_prefix = new string_id(STF, "remove_militia_prefix");
            string_id rem_militia_suffix = new string_id(STF, "remove_militia_suffix");
            String prompt = getString(rem_militia_prefix) + removeName + getString(rem_militia_suffix);
            sui.msgbox(self, player, prompt, sui.YES_NO, "@city/city:remove_militia_confirm", sui.MSG_QUESTION, "handleRemoveMilitiaConfirmed");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAddMilitia(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String playerName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        String s2 = playerName.toLowerCase();
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 10);
        for (int i = 0; i < players.length; i++)
        {
            String s1 = (getName(players[i])).toLowerCase();
            int indexOf = s1.indexOf(' ');
            if (indexOf > -1)
            {
                s1 = s1.substring(0, indexOf);
            }
            if (s1.compareTo(s2) == 0)
            {
                if (city.isCitizenOfCity(players[i], city_id))
                {
                    city.addMilitia(city_id, players[i]);
                    sendSystemMessage(player, SID_ADDED_MILITIA);
                    sendSystemMessage(players[i], SID_ADDED_MILITIA_TARGET);
                }
                else 
                {
                    sendSystemMessage(player, SID_NOT_CITIZEN);
                }
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, SID_CANT_FIND_PLAYER);
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveMilitiaConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id removeTarget = utils.getObjIdScriptVar(self, "removal.temp");
        if (city.isCitizenOfCity(removeTarget, city_id))
        {
            city.removeMilitia(city_id, removeTarget);
            sendSystemMessage(player, SID_REMOVED_MILITIA);
            sendSystemMessage(removeTarget, SID_REMOVED_MILITIA_TARGET);
        }
        else 
        {
            sendSystemMessage(player, SID_NOT_CITIZEN);
        }
        return SCRIPT_CONTINUE;
    }
    public void showTreasuryInfo(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id structure = getTopMostContainer(self);
        String template = getTemplateName(structure);
        if (template.indexOf("cityhall_") < 0)
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        String[] treasury_info = new String[6];
        treasury_info[0] = "Balance: " + getBankBalance(structure);
        obj_id city = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        String garage = city_name + ".garageFee";
        if (!hasObjVar(city, garage))
        {
            setObjVar(city, garage, 0);
        }
        int income_tax = cityGetIncomeTax(city_id);
        int property_tax = cityGetPropertyTax(city_id);
        int sales_tax = cityGetSalesTax(city_id);
        int travel_fee = cityGetTravelCost(city_id);
        int garage_fee = getIntObjVar(city, garage);
        treasury_info[1] = "@city/city:income_tax" + " -- " + income_tax + " cr";
        treasury_info[2] = "@city/city:property_tax" + " -- " + property_tax + "%";
        treasury_info[3] = "@city/city:sales_tax" + " -- " + sales_tax + "%";
        treasury_info[4] = "@city/city:travel_tax" + " -- " + travel_fee + " cr";
        treasury_info[5] = "@city/city:garage_tax" + " -- " + garage_fee + "%";
        sui.listbox(self, player, "@city/city:treasury_balance_d", sui.OK_CANCEL, "@city/city:treasury_balance_t", treasury_info, "handleTreasuryInfoSelect", true);
    }
    public void makeTreasuryDeposit(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        obj_id structure = getTopMostContainer(self);
        String template = getTemplateName(structure);
        if (template.indexOf("cityhall_") < 0)
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        int total = getTotalMoney(player);
        if (total > 0)
        {
            int maint_pool = getBankBalance(structure);
            int pid = sui.transfer(self, player, "@city/city:treasury_deposit_d", "@city/city:treasury_deposit", "@city/city:total_funds", total, "@city/city:treasury", 0, "depositTreasury");
        }
        else 
        {
            sendSystemMessage(player, SID_NO_MONEY);
        }
    }
    public int depositTreasury(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int total = getTotalMoney(player);
        int end_bal = sui.getTransferInputFrom(params);
        int amt = total - end_bal;
        if (amt < 0)
        {
            sendSystemMessage(player, new string_id(STF, "positive_deposit"));
        }
        else if (amt == 0)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            money.requestPayment(player, structure, amt, "handlePayment", null);
            int city_id = findCityByCityHall(structure);
            obj_id mayor = cityGetLeader(city_id);
            String citizen_name = cityGetCitizenName(city_id, player);
            prose_package bodypp = prose.getPackage(TREASURY_DEPOSIT_BODY, citizen_name, amt);
            String strName = getPlayerName(mayor);
            utils.sendMail(TREASURY_DEPOSIT_SUBJECT, bodypp, strName, "City Hall");
        }
        return SCRIPT_CONTINUE;
    }
    public void makeTreasuryWithdraw(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        sui.inputbox(self, player, "@city/city:withdraw_reason_d", sui.OK_CANCEL, "@city/city:withdraw_reason_t", sui.INPUT_NORMAL, null, "handleWithdrawReason", null);
    }
    public int handleWithdrawReason(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String withdraw_reason = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "withdrawReason", withdraw_reason);
        obj_id structure = getTopMostContainer(self);
        int total = getBankBalance(structure);
        if (total > 0)
        {
            int player_cash = getCashBalance(player);
            int pid = sui.transfer(self, player, "@city/city:treasury_withdraw_prompt", "@city/city:treasury_withdraw", "@city/city:treasury", total, "@city/city:funds", 0, "withdrawTreasury");
        }
        else 
        {
            sendSystemMessage(player, SID_NO_MONEY);
        }
        return SCRIPT_CONTINUE;
    }
    public int withdrawTreasury(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int total = getBankBalance(structure);
        int end_bal = sui.getTransferInputFrom(params);
        if (hasObjVar(self, "lastWithdrawTime"))
        {
            int lastWithdrawTime = getIntObjVar(self, "lastWithdrawTime");
            if (getGameTime() - lastWithdrawTime < 86400)
            {
                sendSystemMessage(player, SID_WITHDRAW_DAILY);
                return SCRIPT_CONTINUE;
            }
        }
        int amt = total - end_bal;
        if (amt < 0)
        {
            sendSystemMessage(player, new string_id(STF, "withdraw_treasury"));
        }
        else if (amt == 0)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if ((amt < 5000) || (amt > 150000))
            {
                sendSystemMessage(player, SID_WITHDRAW_LIMITS);
                return SCRIPT_CONTINUE;
            }
            dictionary payparams = new dictionary();
            payparams.put("city_id", city_id);
            payparams.put("amt", amt);
            payparams.put("player", player);
            transferBankCreditsTo(structure, player, amt, "handleWithdrawSuccess", "handleWithdrawFail", payparams);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWithdrawSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = params.getInt("city_id");
        int amt = params.getInt("amt");
        obj_id player = params.getObjId("player");
        setObjVar(self, "lastWithdrawTime", getGameTime());
        prose_package ppWithdraw = prose.getPackage(SID_YOU_WITHDRAW_FROM_TREASURY);
        prose.setDI(ppWithdraw, amt);
        sendSystemMessageProse(player, ppWithdraw);
        CustomerServiceLog("player_city", "Mayor withdrew from treasury. City: " + city_id + " Amt: " + amt + " Player: " + player);
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String citizen_name = "";
        String withdraw_reason = utils.getStringScriptVar(self, "withdrawReason");
        obj_id[] citizens = cityGetCitizenIds(city_id);
        for (int i = 0; i < citizens.length; i++)
        {
            citizen_name = cityGetCitizenName(city_id, citizens[i]);
            prose_package bodypp = prose.getPackage(TREASURY_WITHDRAW_BODY, withdraw_reason, mayor_name, amt);
            utils.sendMail(TREASURY_WITHDRAW_SUBJECT, bodypp, citizen_name, "City Hall");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWithdrawFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        sendSystemMessage(player, new string_id(STF, "withdraw_treasury_error"));
        return SCRIPT_CONTINUE;
    }
    public void adjustTaxes(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        if (city.getCityRank(city_id) < 2)
        {
            sendSystemMessage(player, SID_NO_RANK_TAXES);
            return;
        }
        obj_id city = cityGetCityHall(city_id);
        String city_name = cityGetName(city_id);
        String garage = city_name + ".garageFee";
        if (!hasObjVar(city, garage))
        {
            setObjVar(city, garage, 0);
        }
        int income_tax = cityGetIncomeTax(city_id);
        int property_tax = cityGetPropertyTax(city_id);
        int sales_tax = cityGetSalesTax(city_id);
        int travel_fee = cityGetTravelCost(city_id);
        int garage_fee = getIntObjVar(city, garage);
        String[] tax_info = new String[5];
        tax_info[0] = "@city/city:income_tax" + " -- " + income_tax + " cr";
        tax_info[1] = "@city/city:property_tax" + " -- " + property_tax + "%";
        tax_info[2] = "@city/city:sales_tax" + " -- " + sales_tax + "%";
        tax_info[3] = "@city/city:travel_tax" + " -- " + travel_fee + " cr";
        tax_info[4] = "@city/city:garage_tax" + " -- " + garage_fee + " %";
        sui.listbox(self, player, "@city/city:adjust_taxes_d", sui.OK_CANCEL, "@city/city:adjust_taxes_t", tax_info, "handleTaxInfoSelect", true);
    }
    public int handleTaxInfoSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasCommand(player, "manage_taxes"))
        {
            sendSystemMessage(player, SID_CANT_TAX);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "tax.set", idx);
        sui.inputbox(self, player, "@city/city:set_tax_d_" + TAX_STRING[idx], sui.OK_CANCEL, "@city/city:set_tax_t_" + TAX_STRING[idx], sui.INPUT_NORMAL, null, "handleSetTax", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetTax(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String tax_string = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        int set_tax = utils.getIntScriptVar(self, "tax.set");
        int desired_tax = 0;
        try
        {
            desired_tax = (Integer.valueOf(tax_string)).intValue();
        }
        catch(NumberFormatException err)
        {
            sendSystemMessage(player, SID_TAX_OUT_OF_RANGE);
            return SCRIPT_CONTINUE;
        }
        if (desired_tax < TAX_MIN[set_tax] || desired_tax > TAX_MAX[set_tax])
        {
            sendSystemMessage(player, SID_TAX_OUT_OF_RANGE);
            return SCRIPT_CONTINUE;
        }
        obj_id mayor = cityGetLeader(city_id);
        String mayor_name = cityGetCitizenName(city_id, mayor);
        String city_name = cityGetName(city_id);
        obj_id[] citizens = cityGetCitizenIds(city_id);
        prose_package pp = null;
        switch (set_tax)
        {
            case 0:
            city.setIncomeTax(city_id, desired_tax);
            pp = prose.getPackage(SID_INCOME_TAX, desired_tax);
            for (int i = 0; i < citizens.length; i++)
            {
                String citizen_name = cityGetCitizenName(city_id, citizens[i]);
                prose_package bodypp = prose.getPackage(TAX_INCOME_BODY, city_name, desired_tax);
                utils.sendMail(TAX_INCOME_SUBJECT, bodypp, citizen_name, "City Hall");
            }
            break;
            case 1:
            city.setPropertyTax(city_id, desired_tax);
            pp = prose.getPackage(SID_PROPERTY_TAX, desired_tax);
            for (int i = 0; i < citizens.length; i++)
            {
                String citizen_name = cityGetCitizenName(city_id, citizens[i]);
                prose_package bodypp = prose.getPackage(TAX_PROPERTY_BODY, city_name, desired_tax);
                utils.sendMail(TAX_PROPERTY_SUBJECT, bodypp, citizen_name, "City Hall");
            }
            break;
            case 2:
            city.setSalesTax(city_id, desired_tax);
            pp = prose.getPackage(SID_SALES_TAX, desired_tax);
            for (int i = 0; i < citizens.length; i++)
            {
                String citizen_name = cityGetCitizenName(city_id, citizens[i]);
                prose_package bodypp = prose.getPackage(TAX_SALES_BODY, city_name, desired_tax);
                utils.sendMail(TAX_SALES_SUBJECT, bodypp, citizen_name, "City Hall");
            }
            break;
            case 3:
            location cityTravelLoc = cityGetTravelLocation(city_id);
            if ((cityTravelLoc == null) || ((cityTravelLoc.x == 0) && (cityTravelLoc.y == 0) && (cityTravelLoc.z == 0)))
            {
                sendSystemMessage(player, SID_NO_SHUTTLEPORT);
                return SCRIPT_CONTINUE;
            }
            city.setTravelFee(city_id, desired_tax);
            pp = prose.getPackage(SID_TRAVEL_FEE, desired_tax);
            for (int i = 0; i < citizens.length; i++)
            {
                String citizen_name = cityGetCitizenName(city_id, citizens[i]);
                prose_package bodypp = prose.getPackage(TAX_TRAVEL_BODY, city_name, desired_tax);
                utils.sendMail(TAX_TRAVEL_SUBJECT, bodypp, citizen_name, "City Hall");
            }
            break;
            case 4:
            String garageFee = city_name + ".garageFee";
            setObjVar(structure, garageFee, desired_tax);
            pp = prose.getPackage(SID_GARAGE_TAX, desired_tax);
            for (int i = 0; i < citizens.length; i++)
            {
                String citizen_name = cityGetCitizenName(city_id, citizens[i]);
                prose_package bodypp = prose.getPackage(GARAGE_FEE_BODY, city_name, desired_tax);
                utils.sendMail(GARAGE_FEE_SUBJECT, bodypp, citizen_name, "City Hall");
            }
            break;
        }
        sendSystemMessageProse(player, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleRegisterCity(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasCommand(player, "city_map"))
        {
            sendSystemMessage(player, SID_CANT_REGISTER);
            return SCRIPT_CONTINUE;
        }
        if (city.getCityRank(city_id) < 3)
        {
            sendSystemMessage(player, SID_CANT_REGISTER_RANK);
            return SCRIPT_CONTINUE;
        }
        city.registerCity(city_id);
        sendSystemMessage(player, SID_REGISTERED);
        return SCRIPT_CONTINUE;
    }
    public int handleUnregisterCity(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        city.unregisterCity(city_id);
        sendSystemMessage(player, SID_UNREGISTERED);
        return SCRIPT_CONTINUE;
    }
    public void changeSpecialization(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        int city_rank = city.getCityRank(city_id);
        if (city_rank < 3)
        {
            sendSystemMessage(player, SID_NO_RANK_SPEC);
            return;
        }
        String[] listSpecs = dataTableGetStringColumn(CITY_SPECS, "SPECIALIZATION");
        int[] specCosts = dataTableGetIntColumn(CITY_SPECS, "COST");
        Vector knownSpecs = new Vector();
        knownSpecs.setSize(0);
        for (int i = 0; i < listSpecs.length; i++)
        {
            if ((i == 0) || hasCommand(player, listSpecs[i]))
            {
                utils.addElement(knownSpecs, localize(new string_id(STF, listSpecs[i])) + " (Cost: " + specCosts[i] + ")");
            }
        }
        if (knownSpecs.size() == 0)
        {
            sendSystemMessage(player, SID_NO_SPECS);
            return;
        }
        sui.listbox(self, player, "@city/city:city_specs_d", sui.OK_CANCEL, "@city/city:city_specs_t", knownSpecs, "handleCitySpecInfo", true);
    }
    public int handleCitySpecInfo(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] listSpecs = dataTableGetStringColumn(CITY_SPECS, "SPECIALIZATION");
        Vector knownSpecs = new Vector();
        knownSpecs.setSize(0);
        for (int i = 0; i < listSpecs.length; i++)
        {
            if ((i == 0) || hasCommand(player, listSpecs[i]))
            {
                utils.addElement(knownSpecs, listSpecs[i]);
            }
        }
        utils.setScriptVar(self, "spec", idx);
        sui.msgbox(self, player, "@city/city:" + ((String)knownSpecs.get(idx)) + "_d", sui.YES_NO, "@city/city:" + ((String)knownSpecs.get(idx)), sui.MSG_QUESTION, "handleSetCitySpec");
        return SCRIPT_CONTINUE;
    }
    public int handleSetCitySpec(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int spec = utils.getIntScriptVar(self, "spec");
        String[] listSpecs = dataTableGetStringColumn(CITY_SPECS, "SPECIALIZATION");
        Vector knownSpecs = new Vector();
        knownSpecs.setSize(0);
        for (int i = 0; i < listSpecs.length; i++)
        {
            if ((i == 0) || hasCommand(player, listSpecs[i]))
            {
                utils.addElement(knownSpecs, listSpecs[i]);
            }
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        if (spec != 0)
        {
            if (hasObjVar(structure, "spec_stamp"))
            {
                int curt = getGameTime();
                int stmp = getIntObjVar(structure, "spec_stamp");
                if (curt - stmp < (7 * 24 * 60 * 60))
                {
                    prose_package pp = prose.getPackage(SID_SPEC_TIME, player_structure.assembleTimeRemaining(player_structure.convertSecondsTime((7 * 24 * 60 * 60) - (curt - stmp))));
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        setObjVar(structure, "spec_stamp", getGameTime());
        city.setSpecialization(city_id, ((String)knownSpecs.get(spec)));
        prose_package pp = prose.getPackage(SID_SPEC_SET, "@city/city:" + ((String)knownSpecs.get(spec)));
        sendSystemMessageProse(player, pp);
        return SCRIPT_CONTINUE;
    }
    public void changeZoning(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        if (city.isCityZoned(city_id))
        {
            city.setCityZoned(city_id, false);
            sendSystemMessage(player, SID_ZONING_DISABLED);
        }
        else 
        {
            if (!hasSkill(player, "social_politician_novice"))
            {
                sendSystemMessage(player, SID_ZONING_SKILL);
            }
            else 
            {
                sui.msgbox(self, player, "@city/city:zoning_d", sui.YES_NO, "@city/city:zoning_t", sui.MSG_QUESTION, "handleSetCityZoning");
            }
        }
    }
    public int handleSetCityZoning(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        int city_id = findCityByCityHall(structure);
        if (!cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        city.setCityZoned(city_id, true);
        sendSystemMessage(player, SID_ZONING_ENABLED);
        return SCRIPT_CONTINUE;
    }
    public void sendNewMayorMail(int city_id, obj_id mayor) throws InterruptedException
    {
        String nmayor_name = cityGetCitizenName(city_id, mayor);
        obj_id[] citizens = cityGetCitizenIds(city_id);
        String city_name = cityGetName(city_id);
        for (int i = 0; i < citizens.length; i++)
        {
            city.setCitizenAllegiance(city_id, citizens[i], mayor);
            String citizen_name = cityGetCitizenName(city_id, citizens[i]);
            prose_package bodypp = prose.getPackage(PUBLIC_ELECTION_BODY, city_name, nmayor_name);
            utils.sendMail(PUBLIC_ELECTION_SUBJECT, bodypp, citizen_name, "City Hall");
        }
        return;
    }
    public boolean destroyCitySkillTrainers(obj_id mayor, int city_id) throws InterruptedException
    {
        if (!isValidId(mayor) || !exists(mayor))
        {
            return false;
        }
        if (!cityExists(city_id))
        {
            return false;
        }
        if (!citySkillTrainersExist(city_id))
        {
            return false;
        }
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (structures[i].isLoaded())
            {
                if (!city.isNormalStructure(city_id, structures[i]))
                {
                    if (hasScript(structures[i], PROFESSION_TRAINER_SCRIPT))
                    {
                        CustomerServiceLog("player_city", "Player: " + mayor + " - " + getName(mayor) + " deleted " + structures[i] + " - " + getName(structures[i]) + " at City: " + city_id + " - " + cityGetName(city_id));
                        destroyObject(structures[i]);
                    }
                }
            }
        }
        return true;
    }
    public boolean citySkillTrainersExist(int city_id) throws InterruptedException
    {
        if (!cityExists(city_id))
        {
            return false;
        }
        obj_id[] structures = cityGetStructureIds(city_id);
        for (int i = 0; i < structures.length; i++)
        {
            if (structures[i].isLoaded())
            {
                if (!city.isNormalStructure(city_id, structures[i]))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public int handleCityVisitorMessage(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message = sui.getInputBoxText(params);
        if (message == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        obj_id city_hall = getTopMostContainer(self);
        if (message.length() == 0)
        {
            removeObjVar(city_hall, "city_visitor_message");
            return SCRIPT_CONTINUE;
        }
        if (utils.outOfRange(self, player, 5.0f, true))
        {
            sendSystemMessage(self, new string_id(STF, "out_of_message_range"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(city_hall, "city_visitor_message", message);
        return SCRIPT_CONTINUE;
    }
    public int handleCityCitizenMessage(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String message = sui.getInputBoxText(params);
        if (message == null)
        {
            sendSystemMessage(self, new string_id(STF, "not_valid_message"));
            return SCRIPT_CONTINUE;
        }
        obj_id city_hall = getTopMostContainer(self);
        if (message.length() == 0)
        {
            removeObjVar(city_hall, "city_visitor_message");
            return SCRIPT_CONTINUE;
        }
        if (utils.outOfRange(self, player, 5.0f, true))
        {
            sendSystemMessage(self, new string_id(STF, "out_of_message_range"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(city_hall, "city_citizen_message", message);
        return SCRIPT_CONTINUE;
    }
    public int getStructureCitizenInformationReturn(obj_id self, dictionary params) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog("player_city", "Citizen List: City(" + city_id + ")" + cityGetName(city_id) + "Return Data for Citizenship List was 'null' or 'empty'.");
            return SCRIPT_CONTINUE;
        }
        obj_id citizen = params.getObjId("citizen_id");
        obj_id house = params.getObjId("house_id");
        String houseName = params.getString("house_name");
        location houseLoc = params.getLocation("house_loc");
        CustomerServiceLog("player_city", "Citizen List: City(" + city_id + ")" + cityGetName(city_id) + " Saving Return Data on City Terminal: Citizen(" + citizen + "), House(" + house + ") House Name(" + houseName + ") House Loc(" + houseLoc + ")");
        utils.setScriptVar(self, city.CITIZEN_LIST_DATA + "." + citizen + ".house_id", house);
        utils.setScriptVar(self, city.CITIZEN_LIST_DATA + "." + citizen + ".house_name", houseName);
        utils.setScriptVar(self, city.CITIZEN_LIST_DATA + "." + citizen + ".house_loc", houseLoc);
        return SCRIPT_CONTINUE;
    }
    public void showSafeHouseCitizenList(obj_id player, obj_id self, int city_id) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            sendSystemMessage(player, new string_id(STF, "city_network_error"));
            return;
        }
        if (!cityExists(city_id))
        {
            return;
        }
        obj_id[] citizens = cityGetCitizenIds(city_id);
        if (citizens == null || citizens.length <= 0)
        {
            sendSystemMessage(player, new string_id(STF, "no_city_citizen_data"));
            return;
        }
        int columnCount = 3;
        String[][] displayData = new String[citizens.length][columnCount];
        for (int k = 0; k < citizens.length; k++)
        {
            for (int l = 0; l < columnCount; l++)
            {
                String ownerName = cityGetCitizenName(city_id, citizens[k]);
                switch (l)
                {
                    case 0:
                    if (ownerName == null || ownerName.length() <= 0)
                    {
                        ownerName = "@city/city:unknown_owner_data";
                    }
                    if (!isGod(player))
                    {
                        displayData[k][l] = ownerName;
                    }
                    else 
                    {
                        displayData[k][l] = ownerName + " : " + citizens[k];
                    }
                    break;
                    case 1:
                    String onlineOfflineStatus = utils.getOnlineOfflineStatus(citizens[k]);
                    displayData[k][l] = onlineOfflineStatus;
                    break;
                    case 2:
                    if (city.hasMayorProtectionFlag(citizens[k], city_id))
                    {
                        displayData[k][l] = "Protected Citizen";
                    }
                    else 
                    {
                        displayData[k][l] = "";
                    }
                    break;
                    default:
                    break;
                }
            }
        }
        utils.setScriptVar(player, "safeHouseCitizenList", citizens);
        utils.setScriptVar(player, "city_id", city_id);
        String[] colTitles = 
        {
            "@city/city:column_citizen_name",
            "@city/city:column_location_or_last_login",
            "@city/city:column_safe_house_status"
        };
        String[] colTypes = 
        {
            "text",
            "text",
            "text"
        };
        int currentSafeCount = city.getCurrentSafeHouseCount(city_id);
        int maxSafeCount = city.getMaxSafeHouseCount(city_id);
        String prompt = "Mayor Safe House System:  \nThis system allows for the Mayor of a city to protect a Citizen's Structures by flagging them as a 'Safe House'.  This should only be used in extreme circumstances where a Citizen can not log in during a 90 day time period.\n\n" + "Maximum Number of Safe Houses available: " + maxSafeCount + "\nCurrent Number of Safe Houses Used: " + currentSafeCount;
        sui.tableRowMajor(self, player, sui.OK_CANCEL, "@city/city:safe_house_list_t", "handleSafeHouseCitizenSelect", prompt, colTitles, colTypes, displayData, false);
    }
    public int handleSafeHouseCitizenSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getTableLogicalIndex(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "safeHouseCitizenList");
            utils.removeScriptVar(player, "city_id");
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(player, "safeHouseCitizenList") && !utils.hasScriptVar(player, "city_id"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] citizenList = utils.getObjIdArrayScriptVar(player, "safeHouseCitizenList");
        int city_id = utils.getIntScriptVar(player, "city_id");
        dictionary dict = new dictionary();
        dict.put("player", player);
        dict.put("city_id", city_id);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id mayor = cityGetLeader(city_id);
            if (citizenList[idx] == mayor)
            {
                if (city.hasMayorProtectionFlag(mayor, city_id))
                {
                    city.removeSafeHouseCitizen(city_id, mayor);
                }
                sendSystemMessage(player, new string_id(STF, "can_not_protect_self"));
                messageTo(self, "updateSafeHouseCitizenList", dict, 1.0f, false);
                return SCRIPT_CONTINUE;
            }
            if (city.hasMayorProtectionFlag(citizenList[idx], city_id))
            {
                city.removeSafeHouseCitizen(city_id, citizenList[idx]);
                prose_package bodypp = prose.getPackage(SID_REMOVE_SAFE_HOUSE_CITIZEN_BODY);
                prose.setTT(bodypp, cityGetCitizenName(city_id, citizenList[idx]));
                utils.sendMail(SID_REMOVE_SAFE_HOUSE_CITIZEN_SUBJECT, bodypp, player, "City Hall");
            }
            else 
            {
                int currentSafeCount = city.getCurrentSafeHouseCount(city_id);
                int maxSafeCount = city.getMaxSafeHouseCount(city_id);
                if (currentSafeCount > maxSafeCount)
                {
                    sendSystemMessage(player, new string_id(STF, "safe_house_count_overload"));
                    messageTo(self, "updateSafeHouseCitizenList", dict, 1.0f, false);
                    return SCRIPT_CONTINUE;
                }
                if (currentSafeCount == maxSafeCount)
                {
                    sendSystemMessage(player, new string_id(STF, "safe_house_count_full"));
                    messageTo(self, "updateSafeHouseCitizenList", dict, 1.0f, false);
                    return SCRIPT_CONTINUE;
                }
                city.addSafeHouseCitizen(city_id, citizenList[idx]);
                prose_package bodypp = prose.getPackage(SID_ADD_SAFE_HOUSE_CITIZEN_BODY);
                prose.setTT(bodypp, cityGetCitizenName(city_id, citizenList[idx]));
                utils.sendMail(SID_ADD_SAFE_HOUSE_CITIZEN_SUBJECT, bodypp, player, "City Hall");
            }
        }
        messageTo(self, "updateSafeHouseCitizenList", dict, 1.0f, false);
        sendSystemMessage(player, new string_id(STF, "updating_citizen_protection"));
        return SCRIPT_CONTINUE;
    }
    public int updateSafeHouseCitizenList(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int city_id = params.getInt("city_id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player) || !isIdValid(self) || !cityExists(city_id))
        {
            return SCRIPT_CONTINUE;
        }
        showSafeHouseCitizenList(player, self, city_id);
        return SCRIPT_CONTINUE;
    }
}

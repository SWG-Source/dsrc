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
import script.library.pet_lib;
import script.library.player_structure;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.library.money;

public class structure_maintenance extends script.base_script
{
    public structure_maintenance()
    {
    }
    public static final String STF_FILE = "pet/droid_modules";
    public static final string_id SID_ROOT_MAINT = new string_id(STF_FILE, "struct_maint_root");
    public static final string_id SID_STRUCT_MAINT = new string_id(STF_FILE, "struct_maint_perform");
    public static final string_id SID_EDIT_MAINT = new string_id(STF_FILE, "struct_maint_edit");
    public static final string_id SID_NO_POWER = new string_id(STF_FILE, "not_enough_power");
    public static final string_id SID_DROID_MAINT_LIST_TITLE = new string_id(STF_FILE, "droid_maint_list_title");
    public static final string_id SID_DROID_MAINT_LIST_PROMPT = new string_id(STF_FILE, "droid_maint_list_prompt");
    public static final string_id SID_DROID_MAINT_LIST_HEADER = new string_id(STF_FILE, "droid_maint_list_header");
    public static final string_id SID_MAINT_AMOUNT_TITLE = new string_id(STF_FILE, "droid_maint_amount_title");
    public static final string_id SID_MAINT_AMOUNT_PROMPT = new string_id(STF_FILE, "droid_maint_amount_prompt");
    public static final string_id SID_DROID_EDIT_MAINT_LIST_TITLE = new string_id(STF_FILE, "droid_maint_edit_title");
    public static final string_id SID_DROID_EDIT_MAINT_LIST_PROMPT = new string_id(STF_FILE, "droid_maint_edit_prompt");
    public static final string_id SID_EMPTY_LIST = new string_id(STF_FILE, "droid_maint_empty_list");
    public static final string_id SID_NO_STRUCT = new string_id(STF_FILE, "droid_maint_no_valid_struct");
    public static final string_id SID_EMPTY_MAINT_RUN = new string_id(STF_FILE, "droid_maint_empty_maint_run");
    public static final string_id SID_SELECT_STRUCT = new string_id(STF_FILE, "droid_maint_select_struct");
    public static final string_id SID_DATA_ERROR = new string_id(STF_FILE, "droid_maint_data_error");
    public static final string_id SID_INVALID_AMOUNT = new string_id(STF_FILE, "droid_maint_invalid_amount");
    public static final string_id SID_NOT_ENOUGH_MONEY = new string_id(STF_FILE, "droid_maint_not_enough_cash");
    public static final string_id SID_CANT_TRAVEL = new string_id(STF_FILE, "droid_maint_cant_travel");
    public static final string_id SID_DIFF_PLANET_PREFIX = new string_id(STF_FILE, "droid_maint_diff_planet_prefix");
    public static final string_id SID_DIFF_PLANET_SUFFIX = new string_id(STF_FILE, "droid_maint_diff_planet_suffix");
    public static final string_id SID_SUI_MAINT_AMOUNT_POOL = new string_id(STF_FILE, "sui_maint_amount_pool");
    public static final string_id SID_SUI_MAINT_POOL_UNKNOWN = new string_id(STF_FILE, "sui_maint_pool_unknown");
    public static final string_id SID_SUI_TOTAL_FUNDS_TITLE = new string_id(STF_FILE, "sui_total_funds_title");
    public static final string_id SID_SUI_TOTAL_FUNDS_HEADER = new string_id(STF_FILE, "sui_total_funds_header");
    public static final string_id SID_WILL_COMPLETE_MAINT_RUN = new string_id(STF_FILE, "will_complete_maint_run");
    public static final int droidTravelCost = 300;
    public static final String[] planetList = 
    {
        "corellia",
        "dantooine",
        "dathomir",
        "endor",
        "lok",
        "naboo",
        "rori",
        "talus",
        "tatooine",
        "yavin4"
    };
    public static final String[][] travelMap = 
    {
        
        {
            "corellia",
            "dantooine",
            "dathomir",
            "endor",
            "naboo",
            "naboo",
            "naboo",
            "talus",
            "tatooine",
            "yavin4"
        },
        
        {
            "corellia",
            "dantooine",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia"
        },
        
        {
            "corellia",
            "corellia",
            "dathomir",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia"
        },
        
        {
            "corellia",
            "corellia",
            "corellia",
            "endor",
            "naboo",
            "naboo",
            "naboo",
            "corellia",
            "naboo",
            "corellia"
        },
        
        {
            "naboo",
            "naboo",
            "naboo",
            "naboo",
            "lok",
            "naboo",
            "naboo",
            "naboo",
            "tatooine",
            "naboo"
        },
        
        {
            "corellia",
            "corellia",
            "corellia",
            "endor",
            "lok",
            "naboo",
            "rori",
            "corellia",
            "tatooine",
            "corellia"
        },
        
        {
            "naboo",
            "naboo",
            "naboo",
            "naboo",
            "naboo",
            "naboo",
            "rori",
            "naboo",
            "naboo",
            "naboo"
        },
        
        {
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "talus",
            "corellia",
            "corellia"
        },
        
        {
            "corellia",
            "corellia",
            "corellia",
            "naboo",
            "lok",
            "naboo",
            "naboo",
            "corellia",
            "tatooine",
            "corellia"
        },
        
        {
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corellia",
            "corella",
            "yavin4"
        }
    };
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
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == master)
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_HARVESTER_MANAGE, SID_ROOT_MAINT);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU8, SID_STRUCT_MAINT);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU9, SID_EDIT_MAINT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, SID_NO_POWER);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            clearMaintenanceList();
            requestStructData();
            utils.setScriptVar(self, "player", player);
            utils.setScriptVar(self, "command", "display");
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            requestStructData();
            utils.setScriptVar(self, "player", player);
            utils.setScriptVar(self, "command", "edit");
        }
        return SCRIPT_CONTINUE;
    }
    public void clearMaintenanceList() throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVarTree(self, "maintList");
        utils.removeScriptVar(self, "totalMaintCost");
        utils.removeScriptVar(self, "totalTravelCost");
        utils.removeScriptVar(self, "fullNameList");
        utils.removeScriptVar(self, "idList");
        utils.removeScriptVar(self, "locList");
        utils.removeScriptVar(self, "nameList");
        utils.removeScriptVar(self, "bankList");
        utils.removeScriptVar(self, "msgCount");
    }
    public void requestStructData() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        if (struct_list == null || struct_list.length == 0)
        {
            sendSystemMessage(player, SID_EMPTY_LIST);
            return;
        }
        String[] name_list = new String[struct_list.length];
        int[] bank_list = new int[struct_list.length];
        int msgCount = 0;
        dictionary param = new dictionary();
        param.put("requestingObj", self);
        for (int i = 0; i < struct_list.length; i++)
        {
            name_list[i] = "";
            bank_list[i] = -1;
            if (!isIdValid(struct_list[i]))
            {
                continue;
            }
            messageTo(struct_list[i], "requestName", param, 0, false);
            msgCount++;
            messageTo(struct_list[i], "requestBankBalance", param, 0, false);
            msgCount++;
        }
        if (bank_list.length > 0)
        {
            utils.setScriptVar(self, "bankList", bank_list);
        }
        if (name_list.length > 0)
        {
            utils.setScriptVar(self, "fullNameList", name_list);
        }
        if (msgCount > 0)
        {
            utils.setScriptVar(self, "msgCount", msgCount);
        }
        else 
        {
            sendSystemMessage(player, SID_EMPTY_LIST);
            return;
        }
    }
    public void displayMaintenanceList() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id player = utils.getObjIdScriptVar(self, "player");
        int maint_level = getIntObjVar(self, "module_data.struct_maint");
        boolean canChangePlanet = (maint_level > 10);
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        if (struct_list == null || struct_list.length == 0)
        {
            sendSystemMessage(player, SID_EMPTY_LIST);
            return;
        }
        location[] loc_list = getLocationData();
        String[] fullNameList = utils.getStringArrayScriptVar(self, "fullNameList");
        Vector nameList = new Vector();
        nameList.setSize(0);
        Vector idList = new Vector();
        idList.setSize(0);
        Vector locList = new Vector();
        locList.setSize(0);
        for (int i = 0; i < struct_list.length; i++)
        {
            if (!isIdValid(struct_list[i]))
            {
                break;
            }
            if (!canChangePlanet && !isSamePlanet(player, loc_list[i].area))
            {
                continue;
            }
            if (fullNameList[i] != null && !fullNameList[i].equals(""))
            {
                if (!isInMaintList(struct_list[i]))
                {
                    String name = fullNameList[i];
                    name += " \\#.\\>250";
                    location structLoc = loc_list[i];
                    name += structLoc.area;
                    name += " (" + (int)structLoc.x;
                    name += "," + (int)structLoc.z + ")";
                    nameList = utils.addElement(nameList, name);
                    idList = utils.addElement(idList, struct_list[i]);
                    locList = utils.addElement(locList, loc_list[i]);
                }
            }
        }
        if ((nameList == null || nameList.size() == 0) && currentMaintListCount() == 0)
        {
            sendSystemMessage(player, SID_NO_STRUCT);
            return;
        }
        utils.setScriptVar(self, "idList", idList);
        utils.setScriptVar(self, "locList", locList);
        utils.setScriptVar(self, "nameList", nameList);
        String maintList = getCurrentMaintList();
        String title = utils.packStringId(SID_DROID_MAINT_LIST_TITLE);
        String prompt = maintList + utils.packStringId(SID_DROID_MAINT_LIST_PROMPT);
        int pid = createSUIPage(sui.SUI_LISTBOX, self, player, "handleMaintenanceListDialog");
        setSUIProperty(pid, "", "Size", "650,375");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        if (nameList != null && nameList.size() > 0)
        {
            sui.listboxButtonSetup(pid, sui.OK_CANCEL_ALL);
            setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, "@add");
        }
        else 
        {
            sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        }
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@go");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        int ubound = nameList.size();
        for (int i = 0; i < ubound; i++)
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, ((String)nameList.get(i)));
        }
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
    }
    public String getCurrentMaintList() throws InterruptedException
    {
        obj_id self = getSelf();
        Vector maintListNames = utils.getResizeableStringArrayScriptVar(self, "maintList.name");
        Vector maintListIds = utils.getResizeableObjIdArrayScriptVar(self, "maintList.id");
        Vector maintListLocs = utils.getResizeableLocationArrayScriptVar(self, "maintList.loc");
        Vector maintListAmounts = utils.getResizeableIntArrayScriptVar(self, "maintList.amount");
        String maintList = "";
        if (maintListNames != null && maintListNames.size() > 0)
        {
            for (int i = 0; i < maintListNames.size(); i++)
            {
                String listEntry = "";
                listEntry += ((String)maintListNames.get(i));
                listEntry += " \\#.\\>450";
                listEntry += "+" + ((Integer)maintListAmounts.get(i)).intValue();
                listEntry += "\\>000\n";
                maintList += listEntry;
            }
            maintList += "\n";
        }
        if (!maintList.equals(""))
        {
            maintList = utils.packStringId(SID_DROID_MAINT_LIST_HEADER) + "\n" + maintList;
        }
        return maintList;
    }
    public int currentMaintListCount() throws InterruptedException
    {
        obj_id self = getSelf();
        Vector maintListNames = utils.getResizeableStringArrayScriptVar(self, "maintList.name");
        if (maintListNames == null)
        {
            return 0;
        }
        return (maintListNames.size());
    }
    public boolean isInMaintList(obj_id structure) throws InterruptedException
    {
        obj_id self = getSelf();
        Vector maintListIds = utils.getResizeableObjIdArrayScriptVar(self, "maintList.id");
        if (maintListIds == null || maintListIds.size() == 0)
        {
            return false;
        }
        for (int i = 0; i < maintListIds.size(); i++)
        {
            if (((obj_id)maintListIds.get(i)) == structure)
            {
                return true;
            }
        }
        return false;
    }
    public int handleMaintenanceListDialog(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            clearMaintenanceList();
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            startMaintenanceRun(self, player);
            return SCRIPT_CONTINUE;
            case sui.BP_REVERT:
            addStructureForMaintenance(self, player, idx);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void addStructureForMaintenance(obj_id self, obj_id player, int idx) throws InterruptedException
    {
        if (idx < 0)
        {
            sendSystemMessage(player, SID_SELECT_STRUCT);
            displayMaintenanceList();
            return;
        }
        Vector idList = utils.getResizeableObjIdArrayScriptVar(self, "idList");
        Vector nameList = utils.getResizeableStringArrayScriptVar(self, "nameList");
        Vector locList = utils.getResizeableLocationArrayScriptVar(self, "locList");
        if (idx >= idList.size())
        {
            sendSystemMessage(player, SID_DATA_ERROR);
            displayMaintenanceList();
            return;
        }
        Vector maintListNames = utils.getResizeableStringArrayScriptVar(self, "maintList.name");
        Vector maintListIds = utils.getResizeableObjIdArrayScriptVar(self, "maintList.id");
        Vector maintListLocs = utils.getResizeableLocationArrayScriptVar(self, "maintList.loc");
        Vector maintListAmounts = utils.getResizeableIntArrayScriptVar(self, "maintList.amount");
        if (maintListNames == null)
        {
            maintListNames = new Vector();
            maintListIds = new Vector();
            maintListLocs = new Vector();
            maintListAmounts = new Vector();
        }
        maintListNames = utils.addElement(maintListNames, ((String)nameList.get(idx)));
        maintListIds = utils.addElement(maintListIds, ((obj_id)idList.get(idx)));
        maintListLocs = utils.addElement(maintListLocs, ((location)locList.get(idx)));
        maintListAmounts = utils.addElement(maintListAmounts, -1);
        utils.setScriptVar(self, "maintList.name", maintListNames);
        utils.setScriptVar(self, "maintList.id", maintListIds);
        utils.setScriptVar(self, "maintList.loc", maintListLocs);
        utils.setScriptVar(self, "maintList.amount", maintListAmounts);
        int total = getTotalMoney(player);
        int curMaintCost = utils.getIntScriptVar(self, "totalMaintCost");
        total -= curMaintCost;
        if (total > 0)
        {
            String title = utils.packStringId(SID_MAINT_AMOUNT_TITLE);
            String prompt = utils.packStringId(SID_MAINT_AMOUNT_PROMPT);
            int maint_pool = getStoredBankBalance(((obj_id)idList.get(idx)));
            if (maint_pool >= 0)
            {
                prose_package ppMaintPool = prose.getPackage(SID_SUI_MAINT_AMOUNT_POOL);
                prose.setDI(ppMaintPool, maint_pool);
                prompt += "\n\n \0" + packOutOfBandProsePackage(null, ppMaintPool);
            }
            else 
            {
                prompt += "\n\n" + utils.packStringId(SID_SUI_MAINT_POOL_UNKNOWN);
            }
            if (!isSamePlanet(player, ((location)locList.get(idx)).area))
            {
                int travelCost = getTravelCost(player, ((location)locList.get(idx)).area);
                total -= travelCost;
                if (total < 0)
                {
                    sendSystemMessage(player, SID_CANT_TRAVEL);
                    removeLastStruct();
                    displayMaintenanceList();
                    return;
                }
                prompt += "\n\n" + utils.packStringId(SID_DIFF_PLANET_PREFIX) + travelCost + utils.packStringId(SID_DIFF_PLANET_SUFFIX);
                utils.setScriptVar(self, "curTravelCost", travelCost);
            }
            int pid = sui.transfer(self, player, prompt, title, utils.packStringId(SID_SUI_TOTAL_FUNDS_TITLE), total, utils.packStringId(SID_SUI_TOTAL_FUNDS_HEADER), 0, "handleMaintenanceAmountDialog");
            if (pid > -1)
            {
                utils.setScriptVar(self, "payMaintenance.pid", pid);
                utils.setScriptVar(self, "payMaintenance.target", ((obj_id)idList.get(idx)));
            }
        }
        else 
        {
            sendSystemMessage(player, SID_NOT_ENOUGH_MONEY);
            removeLastStruct();
            displayMaintenanceList();
        }
    }
    public int handleMaintenanceAmountDialog(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            removeLastStruct();
            displayMaintenanceList();
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            int amount = sui.getTransferInputTo(params);
            if (amount <= 0)
            {
                sendSystemMessage(player, SID_INVALID_AMOUNT);
                return SCRIPT_CONTINUE;
            }
            Vector maintListAmounts = utils.getResizeableIntArrayScriptVar(self, "maintList.amount");
            maintListAmounts.set(maintListAmounts.size() - 1, new Integer(amount));
            utils.setScriptVar(self, "maintList.amount", maintListAmounts);
            if (!validateMaintenanceCost(player, amount))
            {
                removeLastStruct();
            }
            displayMaintenanceList();
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean validateMaintenanceCost(obj_id player, int amount) throws InterruptedException
    {
        obj_id self = getSelf();
        int travelCost = 0;
        if (utils.hasScriptVar(self, "curTravelCost"))
        {
            travelCost = utils.getIntScriptVar(self, "curTravelCost");
            utils.removeScriptVar(self, "curTravelCost");
        }
        int totalMoney = getTotalMoney(player);
        int totalMaintCost = utils.getIntScriptVar(self, "totalMaintCost");
        totalMaintCost += amount + travelCost;
        if (totalMoney < totalMaintCost)
        {
            sendSystemMessage(player, SID_NOT_ENOUGH_MONEY);
            return false;
        }
        utils.setScriptVar(self, "totalMaintCost", totalMaintCost);
        int totalTravelCost = utils.getIntScriptVar(self, "totalTravelCost");
        totalTravelCost += travelCost;
        utils.setScriptVar(self, "totalTravelCost", totalTravelCost);
        return true;
    }
    public int getStoredBankBalance(obj_id structure) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        int[] bank_list = utils.getIntArrayScriptVar(self, "bankList");
        if (struct_list == null || struct_list.length == 0)
        {
            return -1;
        }
        if (bank_list == null || bank_list.length == 0)
        {
            return -1;
        }
        int balance = -1;
        for (int i = 0; i < struct_list.length; i++)
        {
            if (structure == struct_list[i])
            {
                balance = bank_list[i];
                break;
            }
        }
        return balance;
    }
    public location[] getLocationData() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        location[] loc_list = new location[struct_list.length];
        for (int i = 0; i < struct_list.length; i++)
        {
            if (!isIdValid(struct_list[i]))
            {
                break;
            }
            loc_list[i] = getLocationObjVar(self, "module_data.maint_list.loc_" + i);
        }
        return loc_list;
    }
    public int getTravelCost(obj_id player, String structPlanet) throws InterruptedException
    {
        String startPlanet = getLocation(player).area;
        String destPlanet = structPlanet;
        int travelCost = getPlanetTravelCost(startPlanet, destPlanet);
        if (travelCost == 0)
        {
            String midPlanet1 = startPlanet;
            String midPlanet2 = travelMap[getPlanetIdx(startPlanet)][getPlanetIdx(destPlanet)];
            while (!midPlanet2.equals(destPlanet))
            {
                travelCost += getPlanetTravelCost(midPlanet1, midPlanet2);
                midPlanet1 = midPlanet2;
                midPlanet2 = travelMap[getPlanetIdx(midPlanet1)][getPlanetIdx(destPlanet)];
            }
        }
        int returnCost = getPlanetTravelCost(destPlanet, startPlanet);
        if (returnCost == 0)
        {
            String midPlanet1 = startPlanet;
            String midPlanet2 = travelMap[getPlanetIdx(startPlanet)][getPlanetIdx(destPlanet)];
            while (!midPlanet2.equals(destPlanet))
            {
                returnCost += getPlanetTravelCost(midPlanet1, midPlanet2);
                midPlanet1 = midPlanet2;
                midPlanet2 = travelMap[getPlanetIdx(midPlanet1)][getPlanetIdx(destPlanet)];
            }
        }
        return travelCost + returnCost + droidTravelCost;
    }
    public int getPlanetIdx(String planet) throws InterruptedException
    {
        for (int i = 0; i < planetList.length; i++)
        {
            if (planet.equals(planetList[i]))
            {
                return i;
            }
        }
        return -1;
    }
    public void removeLastStruct() throws InterruptedException
    {
        obj_id self = getSelf();
        Vector maintListNames = utils.getResizeableStringArrayScriptVar(self, "maintList.name");
        Vector maintListIds = utils.getResizeableObjIdArrayScriptVar(self, "maintList.id");
        Vector maintListLocs = utils.getResizeableLocationArrayScriptVar(self, "maintList.loc");
        Vector maintListAmounts = utils.getResizeableIntArrayScriptVar(self, "maintList.amount");
        if (maintListNames == null || maintListNames.size() == 0)
        {
            return;
        }
        maintListNames = utils.removeElementAt(maintListNames, maintListNames.size() - 1);
        maintListIds = utils.removeElementAt(maintListIds, maintListIds.size() - 1);
        maintListLocs = utils.removeElementAt(maintListLocs, maintListLocs.size() - 1);
        maintListAmounts = utils.removeElementAt(maintListAmounts, maintListAmounts.size() - 1);
        if (maintListNames.size() == 0)
        {
            utils.removeScriptVar(self, "maintList.name");
            utils.removeScriptVar(self, "maintList.id");
            utils.removeScriptVar(self, "maintList.loc");
            utils.removeScriptVar(self, "maintList.amount");
        }
        else 
        {
            utils.setScriptVar(self, "maintList.name", maintListNames);
            utils.setScriptVar(self, "maintList.id", maintListIds);
            utils.setScriptVar(self, "maintList.loc", maintListLocs);
            utils.setScriptVar(self, "maintList.amount", maintListAmounts);
        }
    }
    public void startMaintenanceRun(obj_id self, obj_id player) throws InterruptedException
    {
        Vector maintListIds = utils.getResizeableObjIdArrayScriptVar(self, "maintList.id");
        Vector maintListLocs = utils.getResizeableLocationArrayScriptVar(self, "maintList.loc");
        Vector maintListAmounts = utils.getResizeableIntArrayScriptVar(self, "maintList.amount");
        if (maintListIds == null || maintListIds.size() == 0)
        {
            sendSystemMessage(player, SID_EMPTY_MAINT_RUN);
            return;
        }
        int runTime = 0;
        for (int i = 0; i < maintListIds.size(); i++)
        {
            runTime += getMaintRunTime(player, ((obj_id)maintListIds.get(i)), ((location)maintListLocs.get(i)));
            money.pay(player, ((obj_id)maintListIds.get(i)), ((Integer)maintListAmounts.get(i)).intValue(), "handlePayment", null);
        }
        int travelCost = utils.getIntScriptVar(self, "totalTravelCost");
        money.pay(player, money.ACCT_TRAVEL, travelCost, "handlePayment", null, false);
        String strRunTime = player_structure.assembleTimeRemaining(player_structure.convertSecondsTime(runTime));
        prose_package ppMaintRun = prose.getPackage(SID_WILL_COMPLETE_MAINT_RUN);
        prose.setTT(ppMaintRun, strRunTime);
        sendSystemMessageProse(player, ppMaintRun);
        obj_id pcd = callable.getCallableCD(self);
        setObjVar(pcd, "module_data.maint_run_time", getGameTime() + runTime);
        setObjVar(pcd, "ai.pet.powerLevel", (pet_lib.OUT_OF_POWER + 1));
        pet_lib.storePet(self);
    }
    public int getMaintRunTime(obj_id player, obj_id structure, location structLoc) throws InterruptedException
    {
        float distance = 0;
        int planetHops = 0;
        if (!isSamePlanet(player, structLoc.area))
        {
            distance = 10000;
            planetHops = countPlanetHops(player, structLoc.area);
        }
        else 
        {
            distance = utils.getDistance2D(player, structure);
            if (distance <= 0)
            {
                distance = 6000;
            }
        }
        int planetRunTime = planetHops * (60 * 30);
        int distanceRunTime = (int)(distance / 3) + (60 * 15);
        return planetRunTime + distanceRunTime;
    }
    public int countPlanetHops(obj_id player, String structPlanet) throws InterruptedException
    {
        String startPlanet = getLocation(player).area;
        String destPlanet = structPlanet;
        String midPlanet = travelMap[getPlanetIdx(startPlanet)][getPlanetIdx(destPlanet)];
        int planetHops = 1;
        while (!midPlanet.equals(destPlanet))
        {
            planetHops++;
            midPlanet = travelMap[getPlanetIdx(midPlanet)][getPlanetIdx(destPlanet)];
        }
        return planetHops;
    }
    public void editMaintenanceList() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id player = utils.getObjIdScriptVar(self, "player");
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        if (struct_list == null || struct_list.length == 0)
        {
            sendSystemMessage(player, SID_EMPTY_LIST);
            return;
        }
        location[] loc_list = getLocationData();
        String[] fullNameList = utils.getStringArrayScriptVar(self, "fullNameList");
        Vector nameList = new Vector();
        nameList.setSize(0);
        for (int i = 0; i < struct_list.length; i++)
        {
            if (!isIdValid(struct_list[i]))
            {
                break;
            }
            String name = fullNameList[i];
            name += " \\#.\\>250";
            location structLoc = loc_list[i];
            name += structLoc.area;
            name += " (" + (int)structLoc.x;
            name += "," + (int)structLoc.z + ")";
            nameList = utils.addElement(nameList, name);
        }
        String title = utils.packStringId(SID_DROID_EDIT_MAINT_LIST_TITLE);
        String prompt = utils.packStringId(SID_DROID_EDIT_MAINT_LIST_PROMPT);
        int pid = createSUIPage(sui.SUI_LISTBOX, self, player, "handleEditMaintListDialog");
        setSUIProperty(pid, "", "Size", "650,375");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL_ALL);
        setSUIProperty(pid, sui.LISTBOX_BTN_OTHER, sui.PROP_TEXT, "@delete");
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@ok");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        int ubound = nameList.size();
        for (int i = 0; i < ubound; i++)
        {
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, ((String)nameList.get(i)));
        }
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
    }
    public int handleEditMaintListDialog(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id pcd = callable.getCallableCD(self);
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            case sui.BP_OK:
            return SCRIPT_CONTINUE;
            case sui.BP_REVERT:
            if (idx == -1)
            {
                editMaintenanceList();
                return SCRIPT_CONTINUE;
            }
            player_structure.removeStructureFromMaintenance(self, pcd, idx);
            editMaintenanceList();
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isSamePlanet(obj_id player, String structPlanet) throws InterruptedException
    {
        location playerLoc = getLocation(player);
        return (playerLoc.area.equals(structPlanet));
    }
    public int returnRequestedBankBalance(obj_id self, dictionary params) throws InterruptedException
    {
        int balance = params.getInt("balance");
        obj_id structure = params.getObjId("structure");
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        int[] bank_list = utils.getIntArrayScriptVar(self, "bankList");
        if (struct_list == null || struct_list.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (bank_list == null || bank_list.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < struct_list.length; i++)
        {
            if (structure == struct_list[i])
            {
                bank_list[i] = balance;
                break;
            }
        }
        utils.setScriptVar(self, "bankList", bank_list);
        int msgCount = utils.getIntScriptVar(self, "msgCount");
        msgCount--;
        if (msgCount == 0)
        {
            utils.removeScriptVar(self, "msgCount");
            executeMaintenanceCommand();
        }
        else 
        {
            utils.setScriptVar(self, "msgCount", msgCount);
        }
        return SCRIPT_CONTINUE;
    }
    public int returnRequestedName(obj_id self, dictionary params) throws InterruptedException
    {
        String name = params.getString("name");
        obj_id structure = params.getObjId("structure");
        obj_id[] struct_list = getObjIdArrayObjVar(self, "module_data.maint_list.ids");
        String[] name_list = utils.getStringArrayScriptVar(self, "fullNameList");
        if (struct_list == null || struct_list.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (name_list == null || name_list.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < struct_list.length; i++)
        {
            if (structure == struct_list[i])
            {
                name_list[i] = name;
                break;
            }
        }
        utils.setScriptVar(self, "fullNameList", name_list);
        int msgCount = utils.getIntScriptVar(self, "msgCount");
        msgCount--;
        if (msgCount == 0)
        {
            utils.removeScriptVar(self, "msgCount");
            executeMaintenanceCommand();
        }
        else 
        {
            utils.setScriptVar(self, "msgCount", msgCount);
        }
        return SCRIPT_CONTINUE;
    }
    public void executeMaintenanceCommand() throws InterruptedException
    {
        obj_id self = getSelf();
        String command = utils.getStringScriptVar(self, "command");
        if (command.equals("display"))
        {
            displayMaintenanceList();
        }
        else if (command.equals("edit"))
        {
            editMaintenanceList();
        }
    }
}

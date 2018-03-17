package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.money;
import script.library.player_structure;
import script.library.chat;
import script.library.ai_lib;
import script.library.prose;
import script.library.vendor_lib;
import script.library.xp;
import script.library.turnstile;
import script.library.session;

public class vendor extends script.terminal.base.base_terminal
{
    public vendor()
    {
    }
    public static final string_id SID_GIVE_VENDOR_MAINTENANCE = new string_id("player_structure", "give_maintenance");
    public static final string_id SID_TAKE_VENDOR_MAINTENANCE = new string_id("player_structure", "take_maintenance");
    public static final string_id SID_REGISTER_VENDOR = new string_id("player_structure", "register_vendor");
    public static final string_id SID_UNREGISTER_VENDOR = new string_id("player_structure", "unregister_vendor");
    public static final string_id SID_CHANGE_NAME = new string_id("player_structure", "change_name");
    public static final string_id SID_REMOVE_VENDOR = new string_id("player_structure", "remove_vendor");
    public static final string_id SID_ITEM_DESTROY = new string_id("ui_radial", "item_destroy");
    public static final string_id SID_VENDOR_RENAME = new string_id("player_structure", "vendor_rename");
    public static final string_id SID_VENDOR_RENAME_UNREG = new string_id("player_structure", "vendor_rename_unreg");
    public static final string_id SID_REGISTER_VENDOR_NOT = new string_id("player_structure", "register_vendor_not");
    public static final string_id SID_UNREGISTER_VENDOR_NOT = new string_id("player_structure", "unregister_vendor_not");
    public static final string_id SID_VENDOR_CONTROL = new string_id("player_structure", "vendor_control");
    public static final string_id SID_VENDOR_AREABARKS_ON = new string_id("player_structure", "vendor_areabarks_on");
    public static final string_id SID_VENDOR_AREABARKS_OFF = new string_id("player_structure", "vendor_areabarks_off");
    public static final string_id SID_VENDOR_MAINT_INVALID = new string_id("player_structure", "vendor_maint_invalid");
    public static final string_id SID_VENDOR_MAINT_DENIED = new string_id("player_structure", "vendor_maint_denied");
    public static final string_id SID_VENDOR_STATUS = new string_id("player_structure", "vendor_status");
    public static final string_id SID_VENDOR_INIT = new string_id("player_structure", "vendor_init");
    public static final string_id SID_INITIALIZING_VENDOR = new string_id("player_structure", "vendor_initializing");
    public static final string_id SID_INITIALIZED_VENDOR = new string_id("player_structure", "vendor_initialized");
    public static final string_id SID_OBSCENE = new string_id("player_structure", "obscene");
    public static final string_id SID_CANT_MOVE = new string_id("player_structure", "cant_move");
    public static final string_id SID_CUSTOMIZE_VENDOR = new string_id("player_structure", "customize_vendor");
    public static final string_id SID_ONLY_NPC_VENDORS = new string_id("player_structure", "only_npc_vendors");
    public static final string_id SID_INSUFFICIENT_FUNDS = new string_id("player_structure", "insufficient_funds");
    public static final string_id SID_VENDOR_WITHDRAW = new string_id("player_structure", "vendor_withdraw");
    public static final string_id SID_VENDOR_WITHDRAW_FAIL = new string_id("player_structure", "vendor_withdraw_fail");
    public static final string_id SID_VENDOR_NOT_IN_SHIP = new string_id("player_structure", "vendor_not_in_ship");
    public static final string_id SID_VENDOR_PUBLIC_ONLY = new string_id("player_structure", "vendor_public_only");
    public static final string_id SID_VENDOR_ALREADY_INITIALIZED = new string_id("player_structure", "vendor_already_initialized");
    public static final string_id SID_VENDOR_NOT_IN_SAME_BUILDING = new string_id("player_structure", "vendor_not_in_same_building");
    public static final string_id SID_ENABLE_VENDOR_SEARCH = new string_id("player_structure", "enable_vendor_search");
    public static final string_id SID_DISABLE_VENDOR_SEARCH = new string_id("player_structure", "disable_vendor_search");
    public static final string_id SID_PACK_VENDOR = new string_id("player_structure", "vendor_pack");
    public static final string_id SID_TCG_VENDOR_CTS_WARNING = new string_id("player_vendor", "packup_vendor_cts_warning");
    public static final String TBL_VENDOR_SUBCATEGORIES = "datatables/vendor/vendor_map_subcategories.iff";
    public static final String DESTROY_PID = "player_vendor.destroyVendor.pid";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int time_stamp = getIntObjVar(self, vendor_lib.VAR_LAST_MAINTANENCE);
        if (time_stamp < 1)
        {
            time_stamp = getGameTime();
            setObjVar(self, vendor_lib.VAR_LAST_MAINTANENCE, time_stamp);
        }
        dictionary params = new dictionary();
        params.put("timestamp", time_stamp);
        messageTo(self, "OnMaintenanceLoop", params, 30.0f, false);
        setInvulnerable(self, true);
        registerVendorOnMap(self);
        updateAccessFee(self);
        return super.OnInitialize(self);
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "vendor.map_registered"))
        {
            removeObjVar(self, "vendor.map_registered");
            removePlanetaryMapLocation(self);
        }
        obj_id owner = getObjIdObjVar(self, "vendor_owner");
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary playerMessage = new dictionary();
        playerMessage.put("vendor", self);
        if (vendor_lib.isSpecialVendor(self))
        {
            playerMessage.put("isSpecialVendor", true);
            String skillMod = getStringObjVar(self, "vendor.special_vendor_skillmod");
            playerMessage.put("skillMod", skillMod);
            int skillModCurrentAmount = getSkillStatMod(owner, skillMod);
            playerMessage.put("skillModCurrentAmount", skillModCurrentAmount);
        }
        else 
        {
            playerMessage.put("isSpecialVendor", false);
        }
        if (hasObjVar(self, "vendor.special_decrement_skillmod") || hasObjVar(self, "vendor.special_vendor_skillmod"))
        {
            playerMessage.put("canBeIncremented", true);
        }
        else 
        {
            playerMessage.put("canBeIncremented", false);
        }
        playerMessage.put("vendorTemplate", getTemplateName(self));
        playerMessage.put("destroyLocation", getLocation(self));
        obj_id topContainer = getTopMostContainer(self);
        playerMessage.put("topContainer", topContainer);
        playerMessage.put("topContainerLocation", getLocation(topContainer));
        messageTo(owner, "handleVendorDestroy", playerMessage, 0.f, true);
        return SCRIPT_CONTINUE;
    }
    public void registerVendorOnMap(obj_id self) throws InterruptedException
    {
        requestVendorItemCount(self);
    }
    public void destroyVendor(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "vendor_initialized") && !isCommoditiesServerAvailable())
        {
            return;
        }
        CustomerServiceLog("vendor", "Vendor destroyed by owner. Vendor: " + self + " Location: " + getLocation(self));
        destroyObject(self);
    }
    public void doMapRegistration(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "vendor.map_registered") && hasObjVar(self, "vendor.map_subcat"))
        {
            String displayName = getName(self);
            displayName = displayName.substring(8);
            String subcat = getStringObjVar(self, "vendor.map_subcat");
            obj_id structure = player_structure.getStructure(self);
            location myloc = getLocation(structure);
            addPlanetaryMapLocation(self, displayName, (int)myloc.x, (int)myloc.z, "vendor", subcat, MLT_DYNAMIC, 0);
        }
    }
    public int vendorItemRetry(obj_id self, dictionary params) throws InterruptedException
    {
        registerVendorOnMap(self);
        return SCRIPT_CONTINUE;
    }
    public int OnVendorItemCountReply(obj_id self, int count, int search_enabled) throws InterruptedException
    {
        if (hasObjVar(self, "vendor.getItemCountDisplay"))
        {
            removeObjVar(self, "vendor.getItemCountDisplay");
            obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
            displayStatus(self, ownerId, count, search_enabled);
        }
        else if (hasObjVar(self, "vendor.map_registered") && hasObjVar(self, "vendor.map_subcat"))
        {
            if (count > 0)
            {
                doMapRegistration(self);
            }
            else if (count < 0)
            {
                messageTo(self, "vendorItemRetry", null, 51800 + rand(1, 600), false);
            }
            else 
            {
                removePlanetaryMapLocation(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnVendorStatusChange(obj_id self, int newStatus) throws InterruptedException
    {
        final int VENDOR_EMPTY = 20;
        final int VENDOR_NORMAL = 21;
        final int VENDOR_UNACCESSED = 22;
        final int VENDOR_ENDANGERED = 23;
        final int VENDOR_REMOVED = 24;
        string_id sub = new string_id("auction", "vendor_status_subject");
        string_id body = null;
        switch (newStatus)
        {
            case VENDOR_EMPTY:
            removePlanetaryMapLocation(self);
            body = new string_id("auction", "vendor_status_empty");
            break;
            case VENDOR_NORMAL:
            doMapRegistration(self);
            body = new string_id("auction", "vendor_status_normal");
            break;
            case VENDOR_UNACCESSED:
            registerVendorOnMap(self);
            body = new string_id("auction", "vendor_status_unaccessed");
            break;
            case VENDOR_ENDANGERED:
            registerVendorOnMap(self);
            body = new string_id("auction", "vendor_status_endangered");
            break;
            case VENDOR_REMOVED:
            body = new string_id("auction", "vendor_status_deleted");
            default:
            break;
        }
        prose_package pp = new prose_package();
        pp.stringId = body;
        pp.other.set(getName(self));
        obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
        if (isIdValid(ownerId))
        {
            if (getName(ownerId) != null)
            {
                utils.sendMail(sub, pp, ownerId, "System");
            }
        }
        if (newStatus == VENDOR_REMOVED)
        {
            destroyVendor(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
        if ((player == ownerId) || isGod(player))
        {
            obj_id inventory = getObjectInSlot(player, "inventory");
            if (contains(inventory, self))
            {
                return super.OnObjectMenuRequest(self, player, mi);
            }
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_VENDOR_CONTROL);
            if (!hasObjVar(self, "vendor_initialized"))
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU7, SID_VENDOR_INIT);
            }
            else 
            {
                boolean isSpecialVendor = vendor_lib.isSpecialVendor(self);
                boolean speacialVendorCanBeDressed = false;
                int speacialVendorCanSpeakBasic = 0;
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU5, SID_VENDOR_STATUS);
                mi.addSubMenu(menu, menu_info_types.SERVER_GIVE_VENDOR_MAINTENANCE, SID_GIVE_VENDOR_MAINTENANCE);
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, SID_TAKE_VENDOR_MAINTENANCE);
                if (isSpecialVendor)
                {
                    speacialVendorCanSpeakBasic = getIntObjVar(self, "vendor.special_vendor_basic");
                }
                if (!isSpecialVendor || (isSpecialVendor && speacialVendorCanSpeakBasic > 0))
                {
                    if (utils.isProfession(player, utils.TRADER) && hasObjVar(self, "vendor.NPC"))
                    {
                        if (!hasObjVar(self, "vendor.areaBarks"))
                        {
                            mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_VENDOR_AREABARKS_ON);
                        }
                        else 
                        {
                            mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_VENDOR_AREABARKS_OFF);
                        }
                    }
                }
                int hiringSkillMod = getSkillStatisticModifier(player, "hiring");
                int vendorRegSkillMod = getSkillStatisticModifier(player, "private_vendor_register");
                if (vendorRegSkillMod >= 1)
                {
                    if (!hasObjVar(self, "vendor.map_registered"))
                    {
                        mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, SID_REGISTER_VENDOR);
                    }
                    else 
                    {
                        mi.addSubMenu(menu, menu_info_types.SERVER_MENU3, SID_UNREGISTER_VENDOR);
                    }
                }
                mi.addSubMenu(menu, menu_info_types.SERVER_TERMINAL_PERMISSIONS_ENTER, SID_ENABLE_VENDOR_SEARCH);
                mi.addSubMenu(menu, menu_info_types.SERVER_TERMINAL_PERMISSIONS_BANNED, SID_DISABLE_VENDOR_SEARCH);
                if (isSpecialVendor)
                {
                    speacialVendorCanBeDressed = getBooleanObjVar(self, "vendor.special_vendor_clothing");
                }
                if (!isSpecialVendor || (isSpecialVendor && speacialVendorCanBeDressed))
                {
                    if (hiringSkillMod >= 90)
                    {
                        mi.addSubMenu(menu, menu_info_types.SERVER_MENU8, SID_CUSTOMIZE_VENDOR);
                    }
                }
            }
            if (vendor_lib.isVendorPackUpEnabled())
            {
                mi.addSubMenu(menu, menu_info_types.SERVER_MENU10, SID_PACK_VENDOR);
            }
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU6, SID_REMOVE_VENDOR);
        }
        else 
        {
            obj_id inventory = getObjectInSlot(player, "inventory");
            if (contains(inventory, self) && !isGod(player))
            {
                destroyObject(self);
                return super.OnObjectMenuRequest(self, player, mi);
            }
        }
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
        if ((player != ownerId) && !isGod(player))
        {
            session.logActivity(player, session.ACTIVITY_OTHER_ACCESS_VENDOR);
            return SCRIPT_CONTINUE;
        }
        session.logActivity(player, session.ACTIVITY_OWNER_ACCESS_VENDOR);
        int hiringSkillMod = getSkillStatisticModifier(player, "hiring");
        int vendorRegSkillMod = getSkillStatisticModifier(player, "private_vendor_register");
        if (item == menu_info_types.SERVER_MENU3)
        {
            if (!hasObjVar(self, "vendor.map_registered") && vendorRegSkillMod >= 1)
            {
                if (hasObjVar(self, "vendor.map_registered"))
                {
                    return SCRIPT_CONTINUE;
                }
                if (utils.hasScriptVar(self, "vendor.registering"))
                {
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(self, "vendor.registering", 1);
                String[] rawSubcategories = dataTableGetStringColumn(TBL_VENDOR_SUBCATEGORIES, 0);
                String[] subcategories = new String[rawSubcategories.length];
                for (int i = 0; i < rawSubcategories.length; i++)
                {
                    subcategories[i] = "@player_structure:subcat_" + rawSubcategories[i];
                }
                sui.listbox(self, player, "@player_structure:vendor_mapcat_d", sui.OK_CANCEL, "@player_structure:vendor_mapcat_t", subcategories, "handleMapCatSelect", true);
            }
            else 
            {
                removeObjVar(self, "vendor.map_registered");
                removePlanetaryMapLocation(self);
                sendSystemMessage(player, SID_UNREGISTER_VENDOR_NOT);
            }
            sendDirtyObjectMenuNotification(self);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            sui.inputbox(self, player, "@player_structure:withdraw_vendor_d", sui.OK_CANCEL, "@player_structure:withdraw_vendor_t", sui.INPUT_NORMAL, null, "msgTakeMaintenanceFromVendor", null);
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            setObjVar(self, "vendor.getItemCountDisplay", 1);
            requestVendorItemCount(self);
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            if (sui.hasPid(self, DESTROY_PID))
            {
                forceCloseSUIPage(sui.getPid(self, DESTROY_PID));
            }
            if (hasObjVar(self, "vendor_initialized") && !isCommoditiesServerAvailable())
            {
                int destroyPid = sui.msgbox(self, player, "@player_structure:destroy_vendor_cs_unavailable_d", sui.OK_ONLY, "@player_structure:destroy_vendor_cs_unavailable_t", "noHandler");
                if (destroyPid > -1)
                {
                    sui.setPid(self, destroyPid, DESTROY_PID);
                }
            }
            else 
            {
                int destroyPid = sui.msgbox(self, player, "@player_structure:destroy_vendor_d", sui.YES_NO, "@player_structure:destroy_vendor_t", "handleDestroyVendor");
                if (destroyPid > -1)
                {
                    sui.setPid(self, destroyPid, DESTROY_PID);
                }
            }
        }
        else if (item == menu_info_types.SERVER_MENU7)
        {
            if (!hasObjVar(self, "vendor_initialized"))
            {
                if (getTopMostContainer(player) == getTopMostContainer(self))
                {
                    obj_id structure = player_structure.getStructure(self);
                    int fee = 0;
                    if (turnstile.hasTurnstile(structure))
                    {
                        fee = turnstile.getFee(structure);
                    }
                    obj_id owner = getObjIdObjVar(self, "vendor_owner");
                    createVendorMarket(owner, self, fee);
                    updateSalesTax(self);
                    sendDirtyObjectMenuNotification(self);
                    CustomerServiceLog("vendor", "Vendor initialized.  Owner: " + owner + " Vendor: " + self + " Location: " + getLocation(self));
                }
                else 
                {
                    sendSystemMessage(player, SID_VENDOR_NOT_IN_SAME_BUILDING);
                }
            }
            else 
            {
                sendSystemMessage(player, SID_VENDOR_ALREADY_INITIALIZED);
            }
        }
        else if (item == menu_info_types.SERVER_GIVE_VENDOR_MAINTENANCE)
        {
            sui.inputbox(self, player, "@player_structure:pay_vendor_d", sui.OK_CANCEL, "@player_structure:pay_vendor_t", sui.INPUT_NORMAL, null, "msgGiveMaintenanceToVendor", null);
        }
        else if ((item == menu_info_types.SERVER_MENU8) && hiringSkillMod >= 90)
        {
            sendSystemMessage(player, SID_ONLY_NPC_VENDORS);
            return SCRIPT_OVERRIDE;
        }
        else if (item == menu_info_types.SERVER_TERMINAL_PERMISSIONS_ENTER)
        {
            updateVendorSearchOption(self, true);
        }
        else if (item == menu_info_types.SERVER_TERMINAL_PERMISSIONS_BANNED)
        {
            updateVendorSearchOption(self, false);
        }
        else if (item == menu_info_types.SERVER_MENU10)
        {
            if (vendor_lib.isVendorPackUpEnabled())
            {
                sendSystemMessage(player, SID_TCG_VENDOR_CTS_WARNING);
                utils.setScriptVar(player, "packup.vendor", self);
                if (utils.hasScriptVar(player, "packup.vendor.suiconfirm"))
                {
                    sui.closeSUI(player, utils.getIntScriptVar(player, "packup.vendor.suiconfirm"));
                }
                utils.setScriptVar(player, "packup.vendor.suiconfirm", sui.msgbox(player, player, utils.packStringId(new string_id("sui", "confirm_vendor_packup")), sui.YES_NO, "msgConfirmVendorPackup"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDestroyVendor(obj_id self, dictionary params) throws InterruptedException
    {
        if (sui.hasPid(self, DESTROY_PID))
        {
            forceCloseSUIPage(sui.getPid(self, DESTROY_PID));
        }
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (bp == sui.BP_CANCEL)
        {
            clearVendorDestroyData(self);
            return SCRIPT_CONTINUE;
        }
        int key = rand(100000, 999999);
        utils.setScriptVar(self, "player_vendor.destroyVendor.key", key);
        int pid = sui.inputbox(self, player, "@player_structure:delete_vendor_confirm_prompt" + "\n\nCode: " + key, "@player_structure:delete_vendor_confirm_title", "handleDeleteVendorConfirmed", 6, false, "");
        if (pid > -1)
        {
            sui.setPid(self, pid, DESTROY_PID);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeleteVendorConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        if (sui.hasPid(self, DESTROY_PID))
        {
            forceCloseSUIPage(sui.getPid(self, DESTROY_PID));
        }
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            clearVendorDestroyData(self);
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL || text == null || text.equals(""))
        {
            clearVendorDestroyData(self);
            return SCRIPT_CONTINUE;
        }
        int key = utils.getIntScriptVar(self, "player_vendor.destroyVendor.key");
        String skey = Integer.toString(key);
        if (text.equals(skey))
        {
            destroyVendor(self);
        }
        else 
        {
            sui.msgbox(player, "@player_structure:incorrect_destroy_all_items_code");
            clearVendorDestroyData(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void clearVendorDestroyData(obj_id vendor) throws InterruptedException
    {
        if (!isIdValid(vendor))
        {
            return;
        }
        sui.removePid(vendor, DESTROY_PID);
        utils.removeScriptVarTree(vendor, "player_vendor.destroyVendor");
    }
    public int msgVendorWithdrawSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            int amt = params.getInt("amount");
            obj_id player = params.getObjId("player");
            if (amt > 0)
            {
                prose_package pp = prose.getPackage(SID_VENDOR_WITHDRAW, amt);
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgVendorWithdrawFail(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            int amt = params.getInt("amount");
            obj_id player = params.getObjId("player");
            if (amt > 0)
            {
                prose_package pp = prose.getPackage(SID_VENDOR_WITHDRAW_FAIL, amt);
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetVendorName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String vendorName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if ((vendorName.equals("")) || isNameReserved(vendorName))
        {
            sendSystemMessage(player, SID_OBSCENE);
            sui.inputbox(self, player, "@player_structure:name_d", sui.OK_CANCEL, "@player_structure:name_t", sui.INPUT_NORMAL, null, "handleSetVendorName", null);
            return SCRIPT_CONTINUE;
        }
        if (vendorName.length() > 40)
        {
            vendorName = vendorName.substring(0, 39);
        }
        setName(self, "Vendor: " + vendorName);
        if (hasObjVar(self, "vendor.map_registered"))
        {
            removeObjVar(self, "vendor.map_registered");
            removePlanetaryMapLocation(self);
            sendSystemMessage(player, SID_VENDOR_RENAME_UNREG);
        }
        else 
        {
            sendSystemMessage(player, SID_VENDOR_RENAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMapCatSelect(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "vendor.registering");
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String[] rawSubcategories = dataTableGetStringColumn(TBL_VENDOR_SUBCATEGORIES, 0);
        String[] subcategories = new String[rawSubcategories.length];
        for (int i = 0; i < rawSubcategories.length; i++)
        {
            subcategories[i] = "vendor_" + rawSubcategories[i];
        }
        setObjVar(self, "vendor.map_registered", 1);
        setObjVar(self, "vendor.map_subcat", subcategories[idx]);
        registerVendorOnMap(self);
        sendSystemMessage(player, SID_REGISTER_VENDOR_NOT);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        obj_id from = getContainedBy(self);
        if (isGameObjectTypeOf(dest, GOT_data_vendor_control_device) || (!isIdNull(from) && isGameObjectTypeOf(from, GOT_data_vendor_control_device)))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "vendor_initialized"))
        {
            sendSystemMessage(transferer, SID_CANT_MOVE);
            return SCRIPT_OVERRIDE;
        }
        if (isGod(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getObjIdObjVar(self, "vendor_owner");
        if (!isIdValid(owner))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(owner);
        if (dest == owner || dest == inv)
        {
            return SCRIPT_CONTINUE;
        }
        if (getContainedBy(transferer) == dest)
        {
            obj_id structure = getTopMostContainer(dest);
            int got = getGameObjectType(structure);
            if (isGameObjectTypeOf(got, GOT_ship))
            {
                sendSystemMessage(transferer, SID_VENDOR_NOT_IN_SHIP);
                return SCRIPT_OVERRIDE;
            }
            if (isGameObjectTypeOf(got, GOT_building))
            {
                if (!permissionsIsPublic(structure))
                {
                    sendSystemMessage(transferer, SID_VENDOR_PUBLIC_ONLY);
                    return SCRIPT_OVERRIDE;
                }
            }
            return SCRIPT_CONTINUE;
        }
        String msg = " Vendors may only reside in the owner's inventory or in structures the owner has vendor permissions in.";
        String title = "WARNING";
        sui.msgbox(transferer, transferer, msg, sui.OK_ONLY, title, "noHandler");
        return SCRIPT_OVERRIDE;
    }
    public int msgGiveMaintenanceToVendor(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String amount_str = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.setScriptVar(player, "vendor.creatingVendor", 0);
            return SCRIPT_CONTINUE;
        }
        obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
        if (player != ownerId)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = utils.stringToInt(amount_str);
        if (amt > getTotalMoney(player))
        {
            sendSystemMessage(player, SID_INSUFFICIENT_FUNDS);
            return SCRIPT_CONTINUE;
        }
        if (amt < 1 || amt > 100000)
        {
            sendSystemMessage(player, SID_VENDOR_MAINT_INVALID);
            return SCRIPT_CONTINUE;
        }
        vendor_lib.payMaintenance(player, self, amt);
        return SCRIPT_CONTINUE;
    }
    public int msgTakeMaintenanceFromVendor(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String amount_str = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            utils.setScriptVar(player, "vendor.creatingVendor", 0);
            return SCRIPT_CONTINUE;
        }
        obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
        if (player != ownerId)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = utils.stringToInt(amount_str);
        if (amt < 1 || amt > 100000)
        {
            sendSystemMessage(player, SID_VENDOR_MAINT_INVALID);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("vendor", "Vendor maintenance WITHDRAW (attempt).  Player: " + player + " Vendor " + self + " Amount: " + amt);
        vendor_lib.withdrawMaintenance(player, self, amt);
        return SCRIPT_CONTINUE;
    }
    public void updateSalesTax(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        int city_id = getCityAtLocation(getLocation(structure), 0);
        if (cityExists(city_id))
        {
            obj_id inv = utils.getInventoryContainer(self);
            if (inv == null)
            {
                inv = self;
            }
            obj_id city_hall = cityGetCityHall(city_id);
            int sales_tax = cityGetSalesTax(city_id);
            CustomerServiceLog("vendor", "Vendor sales tax update.  Vendor " + self + " Amount: " + sales_tax);
            setSalesTax(sales_tax * 100, city_hall, inv);
        }
        else 
        {
            obj_id inv = utils.getInventoryContainer(self);
            if (inv == null)
            {
                inv = self;
            }
            setSalesTax(0, null, inv);
        }
    }
    public int OnMaintenanceLoop(obj_id self, dictionary params) throws InterruptedException
    {
        updateSalesTax(self);
        int time_stamp = params.getInt("timestamp");
        int current_time = getGameTime();
        obj_id owner = getObjIdObjVar(self, "vendor_owner");
        int loops = (current_time - time_stamp) / vendor_lib.MAINTENANCE_HEARTBEAT;
        CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", loops: " + loops + ", determined by formula (current_time(" + current_time + ") - time_stamp(" + time_stamp + ")) / vendor_lib.MAINTENANCE_HEARTBEAT(" + vendor_lib.MAINTENANCE_HEARTBEAT + ")");
        int cost = 15 * loops;
        CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", cost: " + cost + ". Determined by formula: cost = 15 * loops(" + loops + ")");
        int hiringSkillMod = getSkillStatisticModifier(owner, "hiring");
        CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", hiring skill mod on owner(" + owner + "): " + hiringSkillMod);
        if (hiringSkillMod >= 100)
        {
            cost *= 0.6;
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", hiring skill mod was greater than or equal to 100 so cost is only 60% (cost * .6). cost is now " + cost);
        }
        else if (utils.isProfession(owner, utils.TRADER))
        {
            cost *= 0.8;
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", hiring skill mod was NOT great than or equal to 100, but they are a trader, so cost is only 80% (cost * .8). cost is now " + cost);
        }
        if (hasObjVar(self, "vendor.map_registered"))
        {
            if (utils.isProfession(owner, utils.TRADER))
            {
                cost *= 1.2;
                CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", vendor is registered. Because the owner is a trader this increases the maintenance fees by 20% (cost * 1.2). cost is now " + cost);
            }
            else 
            {
                cost *= 1.4;
                CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", vendor is registered. Because the owner is NOT a trader this increases the maintenance fees by 40% (cost * 1.4). cost is now " + cost);
            }
        }
        int expertiseMaintenanceDecrease = (int)getSkillStatisticModifier(owner, "expertise_vendor_cost_decrease");
        if (expertiseMaintenanceDecrease > 0)
        {
            cost -= expertiseMaintenanceDecrease;
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", expertiseMaintenanceDecrease is " + expertiseMaintenanceDecrease + " so cost is decreased by this amount. cost is now " + cost);
        }
        if (cost < 0)
        {
            cost = 0;
        }
        int pool_remaining;
        if (cost > 0)
        {
            pool_remaining = vendor_lib.decrementMaintenancePool(self, cost);
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", calling vendor_lib.decrementMaintenancePool with a cost of " + cost);
        }
        else 
        {
            dictionary new_params = new dictionary();
            setObjVar(self, vendor_lib.VAR_LAST_MAINTANENCE, current_time);
            new_params.put("timestamp", current_time);
            messageTo(self, "OnMaintenanceLoop", new_params, vendor_lib.MAINTENANCE_HEARTBEAT, false);
            LOG("LOG_CHANNEL", "New Maintenance Loop set. -----" + vendor_lib.MAINTENANCE_HEARTBEAT);
            return SCRIPT_CONTINUE;
        }
        if (pool_remaining == -1)
        {
            LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop -- unable to decrement maintenance pool.");
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", after calling vendor_lib.decrementMaintenancePool. pool_remaining was returned as -1, this means we were unable to decrement pool.");
        }
        else if (pool_remaining == -2)
        {
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", after calling vendor_lib.decrementMaintenancePool. pool_remaining was returned as -2, this means insufficient funds, we are now going to decay the vendor.");
            LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop -- insufficient funds. Applying damage instead.");
            int damage = vendor_lib.getDecayRate(self) * loops;
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", damage was " + damage + ". Calculated by vendor_lib.getDecayRate(self){" + vendor_lib.getDecayRate(self) + "} * loops(" + loops + ")");
            int condition = vendor_lib.damageVendor(self, damage);
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", condition is now " + condition + ".");
            LOG("LOG_CHANNEL", "damage ->" + damage + " condition ->" + condition);
            if (condition == 0)
            {
                dictionary new_params = new dictionary();
                setObjVar(self, vendor_lib.VAR_LAST_MAINTANENCE, current_time);
                new_params.put("timestamp", current_time);
                messageTo(self, "OnMaintenanceLoop", new_params, vendor_lib.MAINTENANCE_HEARTBEAT, false);
                LOG("LOG_CHANNEL", "New Maintenance Loop set. -----" + vendor_lib.MAINTENANCE_HEARTBEAT);
                return SCRIPT_CONTINUE;
            }
        }
        int condition = vendor_lib.getVendorCondition(self);
        int max_condition = vendor_lib.getMaxCondition(self);
        if (condition != max_condition)
        {
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", was damaged. We are now calculating the fees for fixing him.");
            int damage = max_condition - condition;
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", amount damaged: " + damage + ".");
            int maint_pool = vendor_lib.getBankBalance(self);
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", maint_pool: " + maint_pool + ".");
            int per_point_cost = vendor_lib.getMaintenanceRate(self);
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", per_point_cost: " + per_point_cost + ".");
            int repair_cost = damage * per_point_cost;
            CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", repair_cost: " + repair_cost + ". determined by damage(" + damage + ") * per_point_cost(" + per_point_cost + ")");
            int amt_paid = 0;
            int amt_repaired = 0;
            if (maint_pool >= per_point_cost)
            {
                CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", maint_pool(" + maint_pool + ") is greater than or equal to per_point_cost(" + per_point_cost + ")");
                LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop -- initiating repairs.");
                LOG("LOG_CHANNEL", "cond ->" + condition);
                if (maint_pool < repair_cost)
                {
                    CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", maint_pool(" + maint_pool + ") is less than repair_cost(" + repair_cost + "). Using the whole pool method for cost");
                    amt_repaired = maint_pool / per_point_cost;
                    amt_paid = amt_repaired * per_point_cost;
                    CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", amt_paid: " + amt_paid + ". determined by amt_repaired(" + amt_repaired + ") * per_point_cost(" + per_point_cost + ")");
                }
                else 
                {
                    CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", maint_pool(" + maint_pool + ") is greater than repair_cost(" + repair_cost + "). repairing entire amount");
                    amt_paid = damage * per_point_cost;
                    amt_repaired = damage;
                    CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", amt_paid: " + amt_paid + ". determined by damage(" + damage + ") * per_point_cost(" + per_point_cost + ")");
                }
                CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", calling decrementMaintenancePool with amt_paid: " + amt_paid + ".");
                int result = vendor_lib.decrementMaintenancePool(self, amt_paid);
                if (result > -1)
                {
                    LOG("LOG_CHANNEL", "amt_paid ->" + amt_paid + " amt_repaired ->" + amt_repaired);
                    condition = vendor_lib.repairVendor(self, amt_repaired);
                    LOG("LOG_CHANNEL", "2cond ->" + condition);
                    CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", was repaired by amt_repaired(" + amt_repaired + "). this makes the condition " + condition);
                }
                else 
                {
                    CustomerServiceLog("vendor", "Vendor calculating maintenance fees.  Vendor " + self + ", payment failed, unable to repair the costs.");
                    LOG("LOG_CHANNEL", "permanent_structure::OnMaintenanceLoop -- unable to pay repair costs.");
                }
            }
        }
        dictionary new_params = new dictionary();
        setObjVar(self, vendor_lib.VAR_LAST_MAINTANENCE, current_time);
        new_params.put("timestamp", current_time);
        messageTo(self, "OnMaintenanceLoop", new_params, vendor_lib.MAINTENANCE_HEARTBEAT, false);
        LOG("LOG_CHANNEL", "New Maintenance Loop set. -----" + vendor_lib.MAINTENANCE_HEARTBEAT);
        return SCRIPT_CONTINUE;
    }
    public void displayStatus(obj_id self, obj_id player, int itemCount, int search_enabled) throws InterruptedException
    {
        obj_id ownerId = getObjIdObjVar(self, "vendor_owner");
        if ((player != ownerId) && !isGod(player))
        {
            return;
        }
        String dsrc[] = new String[6];
        int condition = vendor_lib.getVendorCondition(self);
        int max_condition = vendor_lib.getMaxCondition(self);
        int perc_condition = condition * 100 / max_condition;
        if (perc_condition == 100)
        {
            dsrc[0] = "Condition: " + perc_condition + "%";
        }
        else 
        {
            int maint_rate = 15;
            int total_cost = (max_condition - condition) * maint_rate;
            dsrc[0] = "Condition: " + perc_condition + "%" + "   (" + total_cost + " credits to repair.)";
        }
        updateVendorValue(self);
        int cost = 15;
        int hiringSkillMod = getSkillStatisticModifier(ownerId, "hiring");
        if (hiringSkillMod >= 100)
        {
            cost *= 0.6;
        }
        else if (utils.isProfession(ownerId, utils.TRADER))
        {
            cost *= 0.8;
        }
        if (hasObjVar(self, "vendor.map_registered"))
        {
            if (utils.isProfession(ownerId, utils.TRADER))
            {
                cost *= 1.2;
            }
            else 
            {
                cost *= 1.4;
            }
        }
        int expertiseMaintenanceDecrease = (int)getSkillStatisticModifier(player, "expertise_vendor_cost_decrease");
        if (expertiseMaintenanceDecrease > 0)
        {
            cost -= expertiseMaintenanceDecrease;
        }
        if (cost < 0)
        {
            cost = 0;
        }
        dsrc[1] = "Maintenance Rate: " + cost;
        int m_pool = vendor_lib.getMaintenancePool(self);
        int time_remaining = -1;
        if (cost > 0)
        {
            time_remaining = m_pool * vendor_lib.MAINTENANCE_HEARTBEAT / cost;
        }
        if (m_pool > 0 && cost > 0)
        {
            int[] convert_time = player_structure.convertSecondsTime(time_remaining);
            String time_str = player_structure.assembleTimeRemaining(convert_time);
            dsrc[2] = "Maintenance Pool: " + m_pool + "   (" + time_str + ")";
        }
        else 
        {
            dsrc[2] = "Maintenance Pool: " + m_pool;
        }
        dsrc[3] = "Number of Items For Sale: " + itemCount;
        if (search_enabled == 1)
        {
            dsrc[4] = "Vendor Search: Enabled";
        }
        else if (search_enabled == 0)
        {
            dsrc[4] = "Vendor Search: Disabled";
        }
        else 
        {
            dsrc[4] = "Vendor Search: Unknown";
        }
        obj_id inv = utils.getInventoryContainer(self);
        if (inv == null)
        {
            inv = self;
        }
        if (hasObjVar(self, "vendor_deactivated"))
        {
            dsrc[5] = "\\#FF0000Vendor Deactivated - Please Pay Maintenance!\\#";
        }
        else 
        {
            dsrc[5] = "\\#00FF00Vendor Operating Normally\\#";
        }
        sui.listbox(player, "Vendor Status", "Vendor Status", sui.OK_CANCEL, dsrc);
        return;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (transferer == null || transferer == obj_id.NULL_ID || isGod(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (transferer == null || transferer == obj_id.NULL_ID || isGod(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id whoIsOpeningMe) throws InterruptedException
    {
        if (isGod(whoIsOpeningMe))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public void updateAccessFee(obj_id self) throws InterruptedException
    {
        obj_id structure = player_structure.getStructure(self);
        if (turnstile.hasTurnstile(structure))
        {
            setEntranceCharge(self, turnstile.getFee(structure));
        }
        else 
        {
            setEntranceCharge(self, 0);
        }
    }
}

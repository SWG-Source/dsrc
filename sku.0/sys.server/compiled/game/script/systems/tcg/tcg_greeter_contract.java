package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.static_item;
import script.library.sui;
import script.library.utils;
import script.library.vendor_lib;

public class tcg_greeter_contract extends script.base_script
{
    public tcg_greeter_contract()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "greeter";
    public static final String GREETER_APPEARANCE_PID = "greeter_ui_pid.appearance_pid";
    public static final String GREETER_NAME_PID = "greeter_ui_pid.name_pid";
    public static final string_id SID_GREETER_PLACEMENT_INSTRUCT = new string_id("player_vendor", "greeter_placement_instructions");
    public static final string_id SID_SYS_GREETER_NOT_INITIALIZED = new string_id("player_structure", "need_to_initialize_greeter");
    public static final string_id SID_SYS_CREATE_FAILED_GREETER = new string_id("player_structure", "create_failed");
    public static final string_id SID_GREETER_VALIDATION_FAILED = new string_id("player_structure", "greeter_validation_failed");
    public static final string_id SID_INVALID_GREETER_DEED = new string_id("player_structure", "invalid_greeter_deed");
    public static final string_id SID_INVALID_GREETER_LOCATION = new string_id("player_structure", "invalid_greeter_location");
    public static final string_id SID_NAME_GREETER_T = new string_id("player_vendor", "name_greeter_title");
    public static final string_id SID_NAME_GREETER_D = new string_id("player_vendor", "name_greeter_description");
    public static final string_id SID_GREETER_APPEARANCE_TYPE_T = new string_id("player_vendor", "greeter_appear_title");
    public static final string_id SID_GREETER_APPEARANCE_TYPE_D = new string_id("player_vendor", "greeter_appear_description");
    public static final string_id SID_INVALID_LOCATION_SAME_CELL = new string_id("player_vendor", "greeter_same_cell_only");
    public static final string_id SID_OUT_OF_RANGE = new string_id("player_vendor", "greeter_terminal_out_of_range");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        vendor_lib.setObjectOwner(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        blog("tcg_greeter_contract.OnInitialize: Init.");
        obj_id owner = vendor_lib.getObjectOwner(self);
        if (!isValidId(owner))
        {
            blog("tcg_greeter_contract.OnInitialize: Init.");
            CustomerServiceLog("greeter", " TCG Greeter Contract: " + self + " doesn't have valid owner.");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id childObject = getObjIdObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR);
        if (isValidId(childObject) && exists(childObject))
        {
            return SCRIPT_CONTINUE;
        }
        if (!vendor_lib.controllerContainmentCheck(self))
        {
            removeObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, vendor_lib.GREETER_LOCATION_OBJVAR))
        {
            if (!vendor_lib.recreateObjectAtLocation(self, owner, getLocationObjVar(self, vendor_lib.GREETER_LOCATION_OBJVAR)))
            {
                CustomerServiceLog("greeter", " TCG Greeter could not be created at old location using controller: " + self + " owned by: " + owner);
            }
            return SCRIPT_CONTINUE;
        }
        if (!vendor_lib.recreateObject(self, owner))
        {
            CustomerServiceLog("greeter", " TCG Greeter could not be created at old location using controller: " + self + " owned by: " + owner);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("tcg_greeter_contract.OnObjectMenuRequest: Init.");
        obj_id owner = vendor_lib.getObjectOwner(self);
        if (!isValidId(owner) && !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (!vendor_lib.controllerContainmentCheck(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR))
        {
            int menuPlacement = mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("player_structure", "menu_cleanup_greeter"));
        }
        else 
        {
            int menuPlacement = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("player_structure", "menu_place_greeter"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("tcg_greeter_contract.OnObjectMenuSelect: Init.");
        obj_id owner = vendor_lib.getObjectOwner(self);
        if (!isValidId(owner) && !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.SERVER_MENU1 && item != menu_info_types.SERVER_MENU2)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (!vendor_lib.controllerContainmentCheck(self))
            {
                return SCRIPT_CONTINUE;
            }
            if (!vendor_lib.isObjectInSameCellAsController(self, player))
            {
                sendSystemMessage(player, SID_INVALID_LOCATION_SAME_CELL);
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(self, vendor_lib.GREETER_DEED_OBJVAR))
            {
                sendSystemMessage(player, SID_INVALID_GREETER_DEED);
                CustomerServiceLog("greeter", " TCG Greeter Terminal: " + self + " doesn't have valid TCG Greeter data for player: " + player + ". The greeter cannot be created until this issue is resolved.");
                return SCRIPT_CONTINUE;
            }
            if (!vendor_lib.validateNpcPlacementInStructure(player))
            {
                sendSystemMessage(player, SID_INVALID_GREETER_LOCATION);
                CustomerServiceLog("greeter", " TCG Greeter Terminal: " + self + " failed to drop the TCG Greeter for player: " + player + ". The player attempted to place the greeter NPC in an invalid locations.");
                return SCRIPT_CONTINUE;
            }
            String greeterType = getStringObjVar(self, vendor_lib.GREETER_DEED_OBJVAR);
            if (greeterType == null || greeterType.equals(""))
            {
                sendSystemMessage(player, SID_INVALID_GREETER_DEED);
                CustomerServiceLog("greeter", " TCG Greeter Terminal: " + self + " doesn't have valid TCG Greeter data for player: " + player + ". The greeter cannot be created until this issue is resolved.");
                return SCRIPT_CONTINUE;
            }
            String creatureType = vendor_lib.getGreeterNonVendorCreatureType(player, greeterType);
            if (creatureType == null || creatureType.equals(""))
            {
                sendSystemMessage(player, SID_GREETER_VALIDATION_FAILED);
                CustomerServiceLog("greeter", " TCG Greeter Terminal: " + self + " doesn't have valid TCG Greeter data for player: " + player + ". The greeter cannot be created until this issue is resolved.");
                return SCRIPT_CONTINUE;
            }
            blog("tcg_greeter_contract.OnObjectMenuSelect: creatureType: " + creatureType);
            setObjVar(self, vendor_lib.GREETER_CREATURE_TYPE_OBJVAR, creatureType);
            if (!getGreeterData(self, player))
            {
                CustomerServiceLog("greeter", " TCG Greeter Terminal: " + self + " doesn't have valid TCG Greeter data for player: " + player + ". The greeter cannot be created until this issue is resolved.");
                blog("tcg_greeter_contract.OnObjectMenuSelect: getGreeterData Failed.");
                return SCRIPT_CONTINUE;
            }
            sendDirtyObjectMenuNotification(self);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            if (!hasObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id greeter = getObjIdObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR);
            vendor_lib.removeObjectFromController(self, greeter);
            sendDirtyObjectMenuNotification(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!isValidId(destContainer) || !exists(destContainer))
        {
            return SCRIPT_CONTINUE;
        }
        if (!vendor_lib.controllerContainmentCheck(self))
        {
            if (!hasObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id greeter = getObjIdObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR);
            vendor_lib.removeObjectFromController(self, greeter);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id greeter = getObjIdObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR);
        vendor_lib.removeObjectFromController(self, greeter);
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id greeter = getObjIdObjVar(self, vendor_lib.CHILD_GREETER_NONVENDOR_ID_OBJVAR);
        vendor_lib.removeObjectFromController(self, greeter);
        return SCRIPT_CONTINUE;
    }
    public int handleGreeterAppearanceSelection(obj_id self, dictionary params) throws InterruptedException
    {
        blog("tcg_greeter_contract.handleGreeterAppearanceSelection: Init.");
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
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (outOfRange(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.hasPid(player, GREETER_APPEARANCE_PID))
        {
            int pid = sui.getPid(player, GREETER_NAME_PID);
            forceCloseSUIPage(pid);
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        blog("tcg_greeter_contract.handleGreeterAppearanceSelection: all verification completed.");
        String[] appearanceChoices = utils.getStringArrayObjVar(self, vendor_lib.GREETER_APPEARANCE_LIST);
        if (appearanceChoices == null || appearanceChoices.length <= 0)
        {
            blog("tcg_greeter_contract.handleGreeterAppearanceSelection: appearanceChoices script var returned null or had wrong length.");
            return SCRIPT_CONTINUE;
        }
        String[] appearanceCreatureNames = utils.getStringArrayObjVar(self, vendor_lib.GREETER_CREATURENAME_LIST_OBJVAR);
        if (appearanceCreatureNames == null || appearanceCreatureNames.length <= 0)
        {
            blog("tcg_greeter_contract.handleGreeterAppearanceSelection: appearanceCreatureNames script var returned null or had wrong length.");
            return SCRIPT_CONTINUE;
        }
        if (appearanceChoices.length != appearanceCreatureNames.length)
        {
            blog("tcg_greeter_contract.handleGreeterAppearanceSelection: appearanceChoices and appearanceCreatureNames are not equal in length.");
            return SCRIPT_CONTINUE;
        }
        blog("tcg_greeter_contract.handleGreeterAppearanceSelection: appearanceChoices[idx]: " + appearanceChoices[idx]);
        blog("tcg_greeter_contract.handleGreeterAppearanceSelection: appearanceCreatureNames[idx]: " + appearanceCreatureNames[idx]);
        utils.setObjVar(self, vendor_lib.GREETER_SELECTION_OBJVAR, appearanceChoices[idx]);
        utils.setObjVar(self, vendor_lib.GREETER_CREATURE_NAME_OBJVAR, appearanceCreatureNames[idx]);
        int pid = sui.inputbox(self, player, utils.packStringId(SID_NAME_GREETER_D), sui.OK_CANCEL, utils.packStringId(SID_NAME_GREETER_T), sui.INPUT_NORMAL, null, "handleSetGreeterName", null);
        sui.setPid(player, pid, GREETER_NAME_PID);
        return SCRIPT_CONTINUE;
    }
    public int handleSetGreeterName(obj_id self, dictionary params) throws InterruptedException
    {
        blog("tcg_greeter_contract.handleSetNonVendorName: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (outOfRange(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String creatureName = sui.getInputBoxText(params);
        if (creatureName == null || creatureName.equals("") || isNameReserved(creatureName))
        {
            sendSystemMessage(self, vendor_lib.SID_OBSCENE);
            sui.inputbox(self, player, utils.packStringId(SID_NAME_GREETER_D), sui.OK_CANCEL, utils.packStringId(SID_NAME_GREETER_T), sui.INPUT_NORMAL, null, "handleSetGreeterName", null);
            return SCRIPT_CONTINUE;
        }
        if (creatureName.length() > 40)
        {
            creatureName = creatureName.substring(0, 39);
        }
        utils.setObjVar(self, vendor_lib.GREETER_CUSTOM_NAME_OBJVAR, creatureName);
        if (!vendor_lib.controllerContainmentCheck(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!vendor_lib.isObjectInSameCellAsController(self, player))
        {
            sendSystemMessage(player, SID_INVALID_LOCATION_SAME_CELL);
            return SCRIPT_CONTINUE;
        }
        if (!vendor_lib.buildNpcInPlayerStructure(self, player, vendor_lib.GREETER_VAR_PREFIX, true))
        {
            blog("tcg_greeter_contract.handleSetNonVendorName: Unable to create a greeter because vendor_lib.buildNpcInPlayerStructure said NO");
            sendSystemMessage(self, SID_SYS_CREATE_FAILED_GREETER);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, vendor_lib.SID_SYS_CREATE_GREETER_SUCCESS);
        blog("tcg_greeter_contract.getGreeterData: Player was able to create a greeter - SUCCESS");
        return SCRIPT_CONTINUE;
    }
    public boolean getGreeterData(obj_id self, obj_id player) throws InterruptedException
    {
        if (sui.hasPid(player, GREETER_APPEARANCE_PID))
        {
            int pid = sui.getPid(player, GREETER_APPEARANCE_PID);
            forceCloseSUIPage(pid);
        }
        blog("tcg_greeter_contract.getGreeterData: init");
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        else if (!hasObjVar(self, vendor_lib.GREETER_DEED_OBJVAR))
        {
            return false;
        }
        blog("tcg_greeter_contract.getGreeterData: validation complete");
        String creatureType = getStringObjVar(self, vendor_lib.GREETER_CREATURE_TYPE_OBJVAR);
        if (creatureType == null || creatureType.equals(""))
        {
            return false;
        }
        blog("tcg_greeter_contract.getGreeterData: Player has creature type: " + creatureType);
        int row = dataTableSearchColumnForString(creatureType, vendor_lib.COL_CREATURE_TYPE, vendor_lib.TBL_GREETER_NONVENDOR_TABLE);
        if (row < 0)
        {
            blog("tcg_greeter_contract.getGreeterData: TABLE ROW FAILED");
            return false;
        }
        blog("tcg_greeter_contract.getGreeterData: row: " + row);
        dictionary greeterDict = dataTableGetRow(vendor_lib.TBL_GREETER_NONVENDOR_TABLE, row);
        if (greeterDict == null && !greeterDict.isEmpty())
        {
            return false;
        }
        blog("tcg_greeter_contract.getGreeterData: Setting individual vars from dictionary.");
        String greeterNames = greeterDict.getString(vendor_lib.COL_GREETER_CREATURE_NAME);
        String greeterStrIds = greeterDict.getString(vendor_lib.COL_GRTR_STRING_ID);
        int greeterNiche = greeterDict.getInt(vendor_lib.COL_NICHE);
        int greeterDressable = greeterDict.getInt(vendor_lib.COL_DRESSED);
        int greeterSpeaksBasic = greeterDict.getInt(vendor_lib.COL_SPEAK_BASIC);
        int greeterHasChat = greeterDict.getInt(vendor_lib.COL_SAY_CHAT);
        int greeterHasAnims = greeterDict.getInt(vendor_lib.COL_ANIMATES);
        int greeterHasVO = greeterDict.getInt(vendor_lib.COL_VO);
        int greeterHasSounds = greeterDict.getInt(vendor_lib.COL_SOUNDS);
        int greeterHasMoods = greeterDict.getInt(vendor_lib.COL_MOODS);
        int greeterCanColor = greeterDict.getInt(vendor_lib.COL_COLOR);
        utils.setObjVar(self, vendor_lib.GREETER_NAMES_OBJVAR, greeterNames);
        utils.setObjVar(self, vendor_lib.GREETER_STRING_ID_OBJVAR, greeterStrIds);
        utils.setObjVar(self, vendor_lib.GREETER_TYPE_NICHE, greeterNiche);
        utils.setObjVar(self, vendor_lib.GREETER_CAN_DRESS_OBJVAR, greeterDressable);
        utils.setObjVar(self, vendor_lib.GREETER_TYPE_SPEAKBASIC_OBJVAR, greeterSpeaksBasic);
        utils.setObjVar(self, vendor_lib.GREETER_HAS_CHAT_OBJVAR, greeterHasChat);
        utils.setObjVar(self, vendor_lib.GREETER_HAS_ANIMS_OBJVAR, greeterHasAnims);
        utils.setObjVar(self, vendor_lib.GREETER_HAS_VO_OBJVAR, greeterHasVO);
        utils.setObjVar(self, vendor_lib.GREETER_HAS_SOUND_OBJVAR, greeterHasSounds);
        utils.setObjVar(self, vendor_lib.GREETER_MOODS_OBJVAR, greeterHasMoods);
        utils.setObjVar(self, vendor_lib.GREETER_COLOR_OBJVAR, greeterCanColor);
        String[] appearanceChoices = split(greeterStrIds, ',');
        if (appearanceChoices == null)
        {
            blog("tcg_greeter_contract.getGreeterData: appearanceChoices == null.");
            return false;
        }
        String[] appearanceCreatureNames = split(greeterNames, ',');
        if (appearanceCreatureNames == null)
        {
            blog("tcg_greeter_contract.getGreeterData: appearanceCreatureNames == null.");
            return false;
        }
        int appearanceChoicesLength = appearanceChoices.length;
        int appearanceCreatureNamesLength = appearanceCreatureNames.length;
        blog("tcg_greeter_contract.getGreeterData: appearanceChoicesLength: " + appearanceChoicesLength);
        blog("tcg_greeter_contract.getGreeterData: appearanceCreatureNamesLength: " + appearanceCreatureNamesLength);
        if (appearanceChoicesLength != appearanceCreatureNamesLength)
        {
            blog("tcg_greeter_contract.getGreeterData: appearanceCreatureNames & appearanceCreatureNamesLength are not the same size.");
            return false;
        }
        if (appearanceChoicesLength == 1 && appearanceCreatureNamesLength == 1)
        {
            blog("tcg_greeter_contract.getGreeterData: Going to name nonvendor handler.");
            utils.setObjVar(self, vendor_lib.GREETER_SELECTION_OBJVAR, appearanceChoices[0]);
            utils.setObjVar(self, vendor_lib.GREETER_CREATURE_NAME_OBJVAR, appearanceCreatureNames[0]);
            int pid = sui.inputbox(self, player, utils.packStringId(SID_NAME_GREETER_D), sui.OK_CANCEL, utils.packStringId(SID_NAME_GREETER_T), sui.INPUT_NORMAL, null, "handleSetGreeterName", null);
            sui.setPid(player, pid, GREETER_NAME_PID);
            return true;
        }
        int appearanceLength = appearanceChoices.length;
        if (appearanceLength != appearanceChoicesLength)
        {
            blog("tcg_greeter_contract.getGreeterData: appearanceChoiceStrings.length != appearanceChoicesLength. appearanceChoiceStrings.length: " + appearanceLength + "listLength: " + appearanceChoicesLength);
            return false;
        }
        String[] appearanceStrings = new String[appearanceLength];
        for (int i = 0; i < appearanceLength; i++)
        {
            appearanceStrings[i] = "@player_vendor:" + appearanceChoices[i];
        }
        if (appearanceStrings == null || appearanceStrings.length <= 0)
        {
            blog("tcg_greeter_contract.getGreeterData: appearanceStrings was null or had wrong length.");
            return false;
        }
        utils.setObjVar(self, vendor_lib.GREETER_APPEARANCE_LIST, appearanceChoices);
        utils.setObjVar(self, vendor_lib.GREETER_CREATURENAME_LIST_OBJVAR, appearanceCreatureNames);
        int pid = sui.listbox(self, player, utils.packStringId(SID_GREETER_APPEARANCE_TYPE_D), sui.OK_CANCEL, utils.packStringId(SID_GREETER_APPEARANCE_TYPE_T), appearanceStrings, "handleGreeterAppearanceSelection", true);
        sui.setPid(player, pid, GREETER_APPEARANCE_PID);
        return true;
    }
    public boolean outOfRange(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return true;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return true;
        }
        location a = getLocation(self);
        location b = getLocation(player);
        if (a.cell != b.cell)
        {
            sendSystemMessage(player, SID_OUT_OF_RANGE);
            return true;
        }
        return false;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}

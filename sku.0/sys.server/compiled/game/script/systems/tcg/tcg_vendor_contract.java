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

public class tcg_vendor_contract extends script.base_script
{
    public tcg_vendor_contract()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "vendor";
    public static final string_id SID_NO_USE_WHILE_DEAD = new string_id("player_structure", "while_dead");
    public static final string_id SKILLMOD_APPLIED = new string_id("base_player", "skillmod_applied");
    public static final string_id CANT_APPLY_SKILLMOD = new string_id("base_player", "cant_use_item");
    public static final string_id SID_VENDOR_INVOCATION_CANCELED = new string_id("player_vendor", "vendor_invocation_canceled");
    public static final string_id SID_NON_TRADER_CONFIRMATION_MSG = new string_id("player_vendor", "non_trader_confirmation_msg");
    public static final string_id SID_NONVENDOR_PLACEMENT_INSTRUCT = new string_id("player_vendor", "non_trader_placement_instructions");
    public static final string_id SID_INVALID_VENDOR_LOCATION = new string_id("player_vendor", "non_trader_invalid_location");
    public static final string_id SID_NAME_NON_VENDOR_T = new string_id("player_vendor", "name_nonevendor_title");
    public static final string_id SID_NAME_NON_VENDOR_D = new string_id("player_vendor", "name_nonevendor_description");
    public static final string_id SID_NONVENDOR_APPEARANCE_TYPE_T = new string_id("player_vendor", "nonevendor_appear_title");
    public static final string_id SID_NONVENDOR_APPEARANCE_TYPE_D = new string_id("player_vendor", "nonevendor_appear_description");
    public static final string_id SID_VENDOR_CONSUMED = new string_id("player_vendor", "vendor_deed_applied");
    public static final String OWNER_OID = "owner";
    public static final String CONTRACT_PREFIX = "vendor_contract";
    public static final String NON_TRADER_AGREEMENT = CONTRACT_PREFIX + ".non_trader_agreement";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("tcg_vendor_contract.OnObjectMenuRequest: Init.");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self) || utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("tcg_vendor_contract.OnObjectMenuSelect: Init.");
        if (!utils.isNestedWithinAPlayer(self) || utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        String classTemplate = getSkillTemplate(player);
        if (classTemplate == null || classTemplate.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (classTemplate.startsWith("trader"))
        {
            blog("tcg_vendor_contract.OnObjectMenuSelect: Found player to be a trader.");
            if (!giveTcgTraderSkillMod(self, player))
            {
                blog("tcg_vendor_contract.OnObjectMenuSelect: Vendor Skill Mod Application Failed.");
                sendSystemMessage(player, SID_VENDOR_INVOCATION_CANCELED);
                CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " failed to apply a TCG vendor skill mod to player: " + player + ". The contract was not deleted.");
                return SCRIPT_CONTINUE;
            }
            blog("tcg_vendor_contract.OnObjectMenuSelect: Vendor Skill Mod Application Succeeded. Deleting Contract");
            CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " successfully applied a TCG vendor skill mod to player: " + player + ". The contract will now be deleted.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, NON_TRADER_AGREEMENT))
        {
            blog("tcg_vendor_contract.OnObjectMenuSelect: NON-TRADE USING A VENDOR CONTRACT (no confirmation)");
            CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " is being used by a NON-TRADER player: " + player + ". The contract is asking for confirmation before usage.");
            sui.msgbox(self, player, utils.packStringId(SID_NON_TRADER_CONFIRMATION_MSG), sui.YES_NO, "msgConfirmNonTraderVendorUse");
        }
        else if (hasObjVar(self, NON_TRADER_AGREEMENT) && getBooleanObjVar(self, NON_TRADER_AGREEMENT) == true)
        {
            blog("tcg_vendor_contract.OnObjectMenuSelect: NON-TRADE USING A VENDOR CONTRACT (confirmation objvar found)");
            CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " is being used by a NON-TRADER player: " + player + " who has converted the contract to create a limited functionality vendor.");
            if (!vendor_lib.validateNpcPlacementInStructure(player))
            {
                sendSystemMessage(player, SID_INVALID_VENDOR_LOCATION);
                CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " failed to drop the apply a TCG vendor skill mod to player: " + player + ". The contract was not deleted.");
                return SCRIPT_CONTINUE;
            }
            blog("tcg_vendor_contract.OnObjectMenuSelect: Player in valid structure location");
            CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " has found a valid player structure location and will commence with offering the player: " + player + " menu options to create a NON-VENDOR VENDOR.");
            if (!getNonVendorData(self, player))
            {
                blog("tcg_vendor_contract.OnObjectMenuSelect: Player could not get valid non-vendor data");
                CustomerServiceLog("vendor", " TCG Vendor Contract: " + self + " did not give player necessary data to create a NON-VENDOR VENDOR for player: " + player + ".");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgConfirmNonTraderVendorUse(obj_id self, dictionary params) throws InterruptedException
    {
        blog("tcg_vendor_contract.msgConfirmNonTraderVendorUse: Init.");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            blog("tcg_vendor_contract.msgConfirmNonTraderVendorUse: Selected Cancel or No.");
            return SCRIPT_CONTINUE;
        }
        blog("tcg_vendor_contract.msgConfirmNonTraderVendorUse: Confirmed, setting vars.");
        setObjVar(self, NON_TRADER_AGREEMENT, true);
        sendSystemMessage(player, SID_NONVENDOR_PLACEMENT_INSTRUCT);
        return SCRIPT_CONTINUE;
    }
    public int handleNonVendorAppearanceSelection(obj_id self, dictionary params) throws InterruptedException
    {
        blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: Init.");
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
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: all verification completed.");
        String[] templateChoices = utils.getStringArrayScriptVar(player, vendor_lib.NONVENDOR_CREATURE_TEMPLATE_LIST);
        if (templateChoices == null || templateChoices.length <= 0)
        {
            blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: templateeChoices script var returned null or had wrong length.");
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        String[] appearanceChoices = utils.getStringArrayScriptVar(player, vendor_lib.NONVENDOR_APPEARANCE_LIST);
        if (appearanceChoices == null || appearanceChoices.length <= 0)
        {
            blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: appearanceChoices script var returned null or had wrong length.");
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        String[] appearanceCreatureNames = utils.getStringArrayScriptVar(player, vendor_lib.NONVENDOR_CREATURENAME_LIST_SCRVAR);
        if (appearanceCreatureNames == null || appearanceCreatureNames.length <= 0)
        {
            blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: appearanceCreatureNames script var returned null or had wrong length.");
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        if (templateChoices.length != appearanceChoices.length || templateChoices.length != appearanceCreatureNames.length)
        {
            blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: appearanceChoices and appearanceCreatureNames are not equal in length.");
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: appearanceChoices[idx]: " + appearanceChoices[idx]);
        blog("tcg_vendor_contract.handleNonVendorAppearanceSelection: appearanceCreatureNames[idx]: " + appearanceCreatureNames[idx]);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_SELECTION_SCRVAR, appearanceChoices[idx]);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURE_NAME_SCRVAR, appearanceCreatureNames[idx]);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURE_TEMPLATE_SCRVAR, templateChoices[idx]);
        sui.inputbox(self, player, utils.packStringId(SID_NAME_NON_VENDOR_D), sui.OK_CANCEL, utils.packStringId(SID_NAME_NON_VENDOR_T), sui.INPUT_NORMAL, null, "handleSetNonVendorName", null);
        return SCRIPT_CONTINUE;
    }
    public int handleSetNonVendorName(obj_id self, dictionary params) throws InterruptedException
    {
        blog("tcg_vendor_contract.handleSetNonVendorName: init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        String creatureName = sui.getInputBoxText(params);
        if (creatureName == null || creatureName.equals("") || isNameReserved(creatureName))
        {
            sendSystemMessage(self, vendor_lib.SID_OBSCENE);
            sui.inputbox(self, player, utils.packStringId(SID_NAME_NON_VENDOR_D), sui.OK_CANCEL, utils.packStringId(SID_NAME_NON_VENDOR_T), sui.INPUT_NORMAL, null, "handleSetNonVendorName", null);
            return SCRIPT_CONTINUE;
        }
        if (creatureName.length() > 40)
        {
            creatureName = creatureName.substring(0, 39);
        }
        utils.setScriptVar(player, vendor_lib.NONVENDOR_CUSTOM_NAME_SCRVAR, creatureName);
        if (!vendor_lib.buildNpcInPlayerStructure(self, player, vendor_lib.NONVENDOR_VAR_PREFIX, true))
        {
            blog("tcg_vendor_contract.handleSetNonVendorName: Unable to create a nonvendor vendor because vendor_lib.buildNpcInPlayerStructure said NO");
            removeVars(self, player);
            return SCRIPT_CONTINUE;
        }
        removeVars(self, player);
        blog("tcg_vendor_contract.getNonVendorData: Player was able to create a nonvendor vendor - SUCCESS");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean getNonVendorData(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        else if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        else if (!hasObjVar(self, vendor_lib.NONTRADER_NONVENDOR_TYPE))
        {
            return false;
        }
        String nonVendorType = getStringObjVar(self, vendor_lib.NONTRADER_NONVENDOR_TYPE);
        if (nonVendorType == null || nonVendorType.equals("") || !nonVendorType.startsWith(vendor_lib.NONVENDOR_TYPE_PREFIX))
        {
            return false;
        }
        blog("tcg_vendor_contract.getNonVendorData: Player has contract type: " + nonVendorType);
        String creatureType = vendor_lib.getGreeterNonVendorCreatureType(player, nonVendorType);
        if (creatureType == null || creatureType.equals(""))
        {
            return false;
        }
        blog("tcg_vendor_contract.getNonVendorData: creatureType: " + creatureType);
        int row = dataTableSearchColumnForString(creatureType, vendor_lib.COL_CREATURE_TYPE, vendor_lib.TBL_GREETER_NONVENDOR_TABLE);
        if (row < 0)
        {
            blog("tcg_vendor_contract.getNonVendorData: TABLE ROW FAILED");
            removeVars(self, player);
            return false;
        }
        blog("tcg_vendor_contract.getNonVendorData: row: " + row);
        dictionary nonVendorDict = dataTableGetRow(vendor_lib.TBL_GREETER_NONVENDOR_TABLE, row);
        if (nonVendorDict == null && !nonVendorDict.isEmpty())
        {
            removeVars(self, player);
            return false;
        }
        blog("tcg_vendor_contract.getNonVendorData: Setting individual vars from dictionary.");
        String nonVendorTemplate = nonVendorDict.getString(vendor_lib.COL_NONVENDOR_CREATURE_TEMPLATE);
        String nonVendorNames = nonVendorDict.getString(vendor_lib.COL_NONVENDOR_CREATURE_NAME);
        String nonVendorStrIds = nonVendorDict.getString(vendor_lib.COL_NONVNDR_STRING_ID);
        blog("tcg_vendor_contract.getNonVendorData: nonVendorTemplate: " + nonVendorTemplate);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURE_TYPE_SCRVAR, creatureType);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_NAMES_SCRVAR, nonVendorNames);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_STRING_ID_SCRVAR, nonVendorStrIds);
        String[] templateChoices = split(nonVendorTemplate, ',');
        if (templateChoices == null)
        {
            blog("tcg_vendor_contract.getNonVendorData: templateChoices == null.");
            removeVars(self, player);
            return false;
        }
        String[] appearanceChoices = split(nonVendorStrIds, ',');
        if (appearanceChoices == null)
        {
            blog("tcg_vendor_contract.getNonVendorData: appearanceChoices == null.");
            removeVars(self, player);
            return false;
        }
        String[] appearanceCreatureNames = split(nonVendorNames, ',');
        if (appearanceCreatureNames == null)
        {
            blog("tcg_vendor_contract.getNonVendorData: appearanceCreatureNames == null.");
            removeVars(self, player);
            return false;
        }
        int templateChoicesLength = templateChoices.length;
        int appearanceChoicesLength = appearanceChoices.length;
        int appearanceCreatureNamesLength = appearanceCreatureNames.length;
        blog("tcg_vendor_contract.getNonVendorData: appearanceChoicesLength: " + appearanceChoicesLength);
        blog("tcg_vendor_contract.getNonVendorData: appearanceCreatureNamesLength: " + appearanceCreatureNamesLength);
        if (appearanceChoicesLength != appearanceCreatureNamesLength)
        {
            blog("tcg_vendor_contract.getNonVendorData: appearanceCreatureNames & appearanceCreatureNamesLength are not the same size.");
            removeVars(self, player);
            return false;
        }
        if (templateChoicesLength == 1 && appearanceChoicesLength == 1 && appearanceCreatureNamesLength == 1)
        {
            blog("tcg_vendor_contract.getNonVendorData: Going to name nonvendor handler.");
            utils.setScriptVar(player, vendor_lib.NONVENDOR_SELECTION_SCRVAR, appearanceChoices[0]);
            utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURE_NAME_SCRVAR, appearanceCreatureNames[0]);
            utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURE_TEMPLATE_SCRVAR, templateChoices[0]);
            sui.inputbox(self, player, utils.packStringId(SID_NAME_NON_VENDOR_D), sui.OK_CANCEL, utils.packStringId(SID_NAME_NON_VENDOR_T), sui.INPUT_NORMAL, null, "handleSetNonVendorName", null);
            return true;
        }
        int appearanceLength = appearanceChoices.length;
        if (appearanceLength != appearanceChoicesLength)
        {
            blog("tcg_vendor_contract.getNonVendorData: appearanceChoiceStrings.length != appearanceChoicesLength. appearanceChoiceStrings.length: " + appearanceLength + "listLength: " + appearanceChoicesLength);
            removeVars(self, player);
            return false;
        }
        String[] appearanceStrings = new String[appearanceLength];
        for (int i = 0; i < appearanceLength; i++)
        {
            appearanceStrings[i] = "@player_vendor:" + appearanceChoices[i];
        }
        if (appearanceStrings == null || appearanceStrings.length <= 0)
        {
            blog("tcg_vendor_contract.getNonVendorData: appearanceStrings was null or had wrong length.");
            removeVars(self, player);
            return false;
        }
        utils.setScriptVar(player, vendor_lib.NONVENDOR_APPEARANCE_LIST, appearanceChoices);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURENAME_LIST_SCRVAR, appearanceCreatureNames);
        utils.setScriptVar(player, vendor_lib.NONVENDOR_CREATURE_TEMPLATE_LIST, templateChoices);
        sui.listbox(self, player, utils.packStringId(SID_NONVENDOR_APPEARANCE_TYPE_D), sui.OK_CANCEL, utils.packStringId(SID_NONVENDOR_APPEARANCE_TYPE_T), appearanceStrings, "handleNonVendorAppearanceSelection", true);
        return true;
    }
    public boolean giveTcgTraderSkillMod(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (isIncapacitated(player) || isDead(player))
        {
            sendSystemMessage(player, SID_NO_USE_WHILE_DEAD);
            return false;
        }
        String itemName = getStaticItemName(self);
        dictionary itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, itemName);
        if (itemData == null)
        {
            LOG("create", itemName + "TCG Vendor Initalize could not happen because row in datatable is bad");
            return false;
        }
        String skillMod = itemData.getString("skill_mods");
        String clientEffect = itemData.getString("client_effect");
        String clientAnimation = itemData.getString("client_animation");
        String skillModName = "";
        int skillModValue = 0;
        if (skillMod == null || skillMod.equals(""))
        {
            LOG("create", "This object needs a skill mod in the itemstats table to be used " + itemName);
            return false;
        }
        dictionary dict = static_item.parseSkillModifiers(player, skillMod);
        if (dict != null)
        {
            java.util.Enumeration keys = dict.keys();
            while (keys.hasMoreElements())
            {
                skillModName = (String)keys.nextElement();
                skillModValue = dict.getInt(skillModName);
            }
        }
        if (skillModName == null || skillModName.equals(""))
        {
            LOG("create", "This object skill mod was incorrectly named in itemstats table " + itemName);
            return false;
        }
        else if (skillModValue <= 0)
        {
            LOG("create", "This object skill mod had an invalid value " + itemName);
            return false;
        }
        blog("tcg_vendor_contract.OnObjectMenuSelect: Applying skillmod: " + skillModName + " with a value of: " + skillModValue);
        blog("tcg_vendor_contract.OnObjectMenuSelect: Entire skillmod string: " + skillMod);
        if (!applySkillStatisticModifier(player, skillModName, skillModValue))
        {
            blog("tcg_vendor_contract.OnObjectMenuSelect: Applying skillmod failed");
            CustomerServiceLog("create", " Item: " + self + " had skill mod: " + skillMod + " that was added/given to the Owner: " + player + ". Skill mod incremented by 1.");
            sendSystemMessage(player, CANT_APPLY_SKILLMOD);
            return false;
        }
        blog("tcg_vendor_contract.OnObjectMenuSelect: skillmod succeessfully applied.");
        CustomerServiceLog("create", " Item: " + self + " had skill mod: " + skillMod + " that was added/given to the Owner: " + player + ". Skill mod incremented by 1.");
        sendSystemMessage(player, SID_VENDOR_CONSUMED);
        if (clientAnimation != null && !clientAnimation.equals(""))
        {
            doAnimationAction(player, clientAnimation);
        }
        if (clientEffect != null && !clientEffect.equals(""))
        {
            playClientEffectObj(player, clientEffect, player, "");
        }
        return true;
    }
    public boolean removeVars(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        utils.removeScriptVarTree(self, vendor_lib.NONVENDOR_VAR_PREFIX);
        blog("tcg_vendor_contract.removeVars: removed NONVENDOR_VAR_PREFIX Vars ");
        return true;
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

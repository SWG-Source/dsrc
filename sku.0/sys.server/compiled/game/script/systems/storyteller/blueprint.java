package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.colors_hex;
import script.library.player_structure;
import script.library.prose;
import script.library.static_item;
import script.library.storyteller;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class blueprint extends script.base_script
{
    public blueprint()
    {
    }
    public static final int MENU_BLUEPRINT_DEPLOY = menu_info_types.ITEM_USE;
    public static final int MENU_BLUEPRINT_OPTIONS = menu_info_types.SERVER_MENU5;
    public static final int MENU_BLUEPRINT_LOAD = menu_info_types.ITEM_OPEN;
    public static final int MENU_BLUEPRINT_COPY = menu_info_types.SERVER_MENU6;
    public static final int MENU_BLUEPRINT_NEW_DATA = menu_info_types.SERVER_MENU7;
    public static final int MENU_BLUEPRINT_SET_DESCRIPTION = menu_info_types.SERVER_MENU8;
    public static final String[] RESERVED_RULES_TO_IGNORE = new String[]
    {
        "name_declined_number",
        "name_declined_syntax",
        "name_declined_fictionally_reserved",
        "name_declined_reserved"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleStorytellerBlueprintInitialize", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleStorytellerBlueprintInitialize", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerBlueprintInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        initializeBlueprint(self);
        return SCRIPT_CONTINUE;
    }
    public void initializeBlueprint(obj_id self) throws InterruptedException
    {
        storyteller.blueprintParseConversion(self);
        setOwner(self, obj_id.NULL_ID);
        setObjVar(self, "players_can_access_container", true);
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menuDeployBlueprint = mi.addRootMenu(MENU_BLUEPRINT_DEPLOY, new string_id("storyteller", "blueprint_deploy_data"));
        int menuBlueprintOptions = mi.addRootMenu(MENU_BLUEPRINT_OPTIONS, new string_id("storyteller", "blueprint_options"));
        mi.addSubMenu(menuBlueprintOptions, MENU_BLUEPRINT_NEW_DATA, new string_id("storyteller", "blueprint_record_new_data"));
        mi.addSubMenu(menuBlueprintOptions, MENU_BLUEPRINT_LOAD, new string_id("storyteller", "blueprint_load_tokens"));
        if (hasObjVar(self, storyteller.BLUEPRINT_AUTHOR_OBJVAR))
        {
            obj_id blueprintDesigner = getObjIdObjVar(self, storyteller.BLUEPRINT_AUTHOR_OBJVAR);
            if (isIdValid(blueprintDesigner) && blueprintDesigner == player)
            {
                mi.addSubMenu(menuBlueprintOptions, MENU_BLUEPRINT_SET_DESCRIPTION, new string_id("storyteller", "blueprint_description_set"));
                mi.addSubMenu(menuBlueprintOptions, MENU_BLUEPRINT_COPY, new string_id("storyteller", "blueprint_copy_blueprint"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_SWIMMING) == 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_while_swimming"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        location yourLoc = getLocation(player);
        if (isIdValid(yourLoc.cell))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_inside"));
            return SCRIPT_CONTINUE;
        }
        obj_id whatAmIStandingOn = getStandingOn(player);
        if (isIdValid(whatAmIStandingOn) && player_structure.isBuilding(whatAmIStandingOn))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_on_buildings"));
            return SCRIPT_CONTINUE;
        }
        if (item == MENU_BLUEPRINT_OPTIONS)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_options_prompt"));
        }
        if (item == MENU_BLUEPRINT_NEW_DATA)
        {
            String title = utils.packStringId(new string_id("storyteller", "blueprint_confirm_new_data_title"));
            String testMsg = utils.packStringId(new string_id("storyteller", "blueprint_confirm_new_data_msg"));
            String ok_button = utils.packStringId(new string_id("quest/ground/util/quest_giver_object", "button_accept"));
            String cancel_button = utils.packStringId(new string_id("quest/ground/util/quest_giver_object", "button_decline"));
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "confirmSaveNewBlueprintData");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, testMsg);
            sui.msgboxButtonSetup(pid, sui.YES_NO);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, ok_button);
            setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, cancel_button);
            utils.setScriptVar(player, "questGiver.openSui", pid);
            sui.showSUIPage(pid);
        }
        if (item == MENU_BLUEPRINT_LOAD)
        {
            queueCommand(player, (1880585606), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        if (item == MENU_BLUEPRINT_SET_DESCRIPTION)
        {
            showBlueprintDescriptionSetSui(self, player);
            return SCRIPT_CONTINUE;
        }
        if (item == MENU_BLUEPRINT_COPY)
        {
            obj_id blueprintAuthor = getObjIdObjVar(self, storyteller.BLUEPRINT_AUTHOR_OBJVAR);
            if (!isIdValid(blueprintAuthor) || blueprintAuthor != player)
            {
                sendSystemMessage(player, new string_id("storyteller", "blueprint_only_author_may_copy"));
                return SCRIPT_CONTINUE;
            }
            obj_id blankBlueprint = utils.getStaticItemInInventory(player, "st_o_buildout_blueprint_blank");
            if (!isIdValid(blankBlueprint))
            {
                sendSystemMessage(player, new string_id("storyteller", "blueprint_needs_blank_blueprint"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String[] blueprintObjects = utils.getStringBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
                if (blueprintObjects == null || blueprintObjects.length == 0)
                {
                    sendSystemMessage(player, new string_id("storyteller", "blueprint_invalid_data"));
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    obj_id blueprintCopy = static_item.createNewItemFunction("st_o_buildout_blueprint", player);
                    for (int i = 0; i < blueprintObjects.length; i++)
                    {
                        String objectData = blueprintObjects[i];
                        blueprintObjects[i] = storyteller.changeBlueprintItemTokenStatus(storyteller.BLUEPRINT_TOKEN_NEEDED, objectData);
                    }
                    utils.setBatchObjVar(blueprintCopy, storyteller.BLUEPRINT_OBJECTS_OBJVAR, blueprintObjects);
                    setObjVar(blueprintCopy, storyteller.BLUEPRINT_AUTHOR_OBJVAR, player);
                    setObjVar(blueprintCopy, storyteller.BLUEPRINT_AUTHOR_NAME_OBJVAR, getName(player));
                    setObjVar(blueprintCopy, storyteller.BLUEPRINT_VERSION_OBJVAR, storyteller.BLUEPRINT_VERSION_NUM);
                    if (hasObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR))
                    {
                        String blueprintDescription = getStringObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR);
                        if (blueprintDescription != null && blueprintDescription.length() > 0)
                        {
                            setObjVar(blueprintCopy, storyteller.BLUEPRINT_DECRIPTION_OBJVAR, blueprintDescription);
                        }
                    }
                    sendSystemMessage(player, new string_id("storyteller", "blueprint_copy_created"));
                    destroyObject(blankBlueprint);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int confirmSaveNewBlueprintData(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player) && bp == sui.BP_OK)
        {
            if (!createBlueprint(self, player))
            {
                sendSystemMessage(player, new string_id("storyteller", "blueprint_failed_to_create"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showBlueprintDescriptionSetSui(obj_id self, obj_id player) throws InterruptedException
    {
        String prompt = getString(new string_id("storyteller", "blueprint_description_prompt"));
        String title = getString(new string_id("storyteller", "blueprint_description_set"));
        int pid = sui.inputbox(self, player, prompt, sui.OK_CANCEL, title, sui.INPUT_NORMAL, null, "handleBlueprintDescriptionSet");
        if (pid > -1)
        {
            if (hasObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR))
            {
                String defaultText = getStringObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR);
                if (defaultText != null && defaultText.length() > 0)
                {
                    setSUIProperty(pid, sui.INPUTBOX_INPUT, sui.PROP_TEXT, defaultText);
                }
            }
            sui.showSUIPage(pid);
        }
        return;
    }
    public int handleBlueprintDescriptionSet(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        if (isIdValid(player) && bp == sui.BP_OK)
        {
            if (text == null)
            {
                sendSystemMessage(player, new string_id("storyteller", "blueprint_description_invalid"));
            }
            else if (text.length() > sui.MAX_INPUT_LENGTH)
            {
                sendSystemMessage(player, new string_id("storyteller", "blueprint_description_bad_length"));
            }
            else if (text.length() < 1)
            {
                removeObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR);
            }
            else 
            {
                if (isNameReserved(text, RESERVED_RULES_TO_IGNORE))
                {
                    showBlueprintDescriptionSetSui(self, player);
                    sendSystemMessage(player, new string_id("storyteller", "blueprint_description_reserved_fail"));
                }
                else 
                {
                    setObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR, text);
                    sendSystemMessage(player, new string_id("storyteller", "blueprint_description_added"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGroundTargetLoc(obj_id self, obj_id player, int menuItem, float x, float y, float z) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_SWIMMING) == 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_while_swimming"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        location yourLoc = getLocation(player);
        if (isIdValid(yourLoc.cell))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_inside"));
            return SCRIPT_CONTINUE;
        }
        obj_id whatAmIStandingOn = getStandingOn(player);
        if (isIdValid(whatAmIStandingOn) && player_structure.isBuilding(whatAmIStandingOn))
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_not_on_buildings"));
            return SCRIPT_CONTINUE;
        }
        float yaw = getYaw(player);
        location newLoc = new location(x, y, z);
        if (menuItem == MENU_BLUEPRINT_DEPLOY)
        {
            reconstructBlueprintScene(self, player, newLoc, yaw);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasBlueprintData(obj_id blueprint) throws InterruptedException
    {
        return utils.hasStringBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
    }
    public boolean createBlueprint(obj_id blueprint, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id[] storytellerObjects = getAllObjectsWithObjVar(getLocation(player), storyteller.BLUEPRINT_DEFAULT_RADIUS, "storytellerid");
        if (storytellerObjects == null || storytellerObjects.length < 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_objects_found"));
            return false;
        }
        String[] blueprintObjects = storyteller.recordBlueprintData(blueprint, storytellerObjects, player);
        if (blueprintObjects == null || blueprintObjects.length < 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_no_objects_found_of_yours"));
            return false;
        }
        utils.setBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR, blueprintObjects);
        setObjVar(blueprint, storyteller.BLUEPRINT_AUTHOR_OBJVAR, player);
        setObjVar(blueprint, storyteller.BLUEPRINT_AUTHOR_NAME_OBJVAR, getName(player));
        setObjVar(blueprint, storyteller.BLUEPRINT_VERSION_OBJVAR, storyteller.BLUEPRINT_VERSION_NUM);
        sendSystemMessage(player, new string_id("storyteller", "blueprint_saved"));
        return true;
    }
    public void reconstructBlueprintScene(obj_id blueprint, obj_id player, location targetLoc, float targetYaw) throws InterruptedException
    {
        String[] blueprintObjects = utils.getStringBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
        if (blueprintObjects == null || blueprintObjects.length == 0)
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_failed_to_deploy"));
            return;
        }
        boolean creationAllowed = true;
        for (int i = 0; i < blueprintObjects.length; i++)
        {
            String objectData = blueprintObjects[i];
            if (storyteller.validateBlueprintData(objectData))
            {
                String tokenName = storyteller.getBlueprintItemName(objectData);
                location atLoc = storyteller.getBlueprintObjectLocation(objectData, targetLoc);
                float yaw = storyteller.getBlueprintItemYaw(objectData);
                if (targetYaw != 0)
                {
                    atLoc = storyteller.rotateLocationXZ(targetLoc, atLoc, targetYaw);
                    yaw -= (0 - targetYaw);
                }
                if (!storyteller.isBlueprintTokenLoaded(objectData))
                {
                    sendSystemMessage(player, new string_id("storyteller", "blueprint_not_fully_loaded"));
                    creationAllowed = false;
                    break;
                }
                if (!storyteller.canDeployStorytellerToken(player, atLoc, tokenName))
                {
                    creationAllowed = false;
                    if (isGod(player))
                    {
                        utils.setScriptVar(player, "storyteller.godModeStopOverrideMessages", true);
                    }
                    break;
                }
            }
            else 
            {
                creationAllowed = false;
                sendSystemMessage(player, new string_id("storyteller", "blueprint_invalid_data"));
                break;
            }
        }
        if (creationAllowed || isGod(player))
        {
            boolean splitItemCreation = false;
            int numObjectsToCreate = blueprintObjects.length;
            if (numObjectsToCreate > storyteller.BLUEPRINT_CREATE_CYCLE_MAX)
            {
                numObjectsToCreate = storyteller.BLUEPRINT_CREATE_CYCLE_LOOP;
                splitItemCreation = true;
            }
            for (int j = 0; j < numObjectsToCreate; j++)
            {
                String objectData = blueprintObjects[j];
                obj_id blueprintObject = storyteller.createBlueprintObject(objectData, blueprint, player, targetLoc, targetYaw);
                if (isGod(player))
                {
                    utils.setScriptVar(player, "storyteller.godModeStopOverrideMessages", true);
                }
                if (isValidId(blueprintObject))
                {
                    if (j == 0 && splitItemCreation)
                    {
                        String[] splitBlueprintObjects = new String[blueprintObjects.length - storyteller.BLUEPRINT_CREATE_CYCLE_LOOP];
                        splitBlueprintObjects = utils.copyArrayOfRange(blueprintObjects, splitBlueprintObjects, storyteller.BLUEPRINT_CREATE_CYCLE_LOOP, blueprintObjects.length - 1);
                        utils.setBatchObjVar(blueprintObject, storyteller.BLUEPRINT_OBJECTS_OBJVAR, splitBlueprintObjects);
                        attachScript(blueprintObject, "systems.storyteller.blueprint_split_controller");
                        dictionary webster = new dictionary();
                        webster.put("player", player);
                        webster.put("targetLoc", targetLoc);
                        webster.put("targetYaw", targetYaw);
                        messageTo(blueprintObject, "handleSplitBlueprintItemCreation", webster, 1, false);
                    }
                }
            }
            for (int p = 0; p < blueprintObjects.length; p++)
            {
                String objectData = blueprintObjects[p];
                blueprintObjects[p] = storyteller.changeBlueprintItemTokenStatus(storyteller.BLUEPRINT_TOKEN_NEEDED, objectData);
            }
            utils.setBatchObjVar(blueprint, storyteller.BLUEPRINT_OBJECTS_OBJVAR, blueprintObjects);
        }
        else 
        {
            sendSystemMessage(player, new string_id("storyteller", "blueprint_failed_to_deploy"));
        }
        if (utils.hasScriptVar(player, "storyteller.godModeStopOverrideMessages"))
        {
            utils.removeScriptVar(player, "storyteller.godModeStopOverrideMessages");
        }
        return;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, transferer))
        {
            return SCRIPT_OVERRIDE;
        }
        String tokenName = getStaticItemName(item);
        string_id message = new string_id("storyteller", "blueprint_token_unneeded");
        String tokenDisplayName = utils.packStringId(new string_id("static_item_n", tokenName));
        prose_package pp = prose.getPackage(message, transferer, transferer);
        prose.setTO(pp, tokenDisplayName);
        if (!storyteller.isCombatNpc(item) && !storyteller.isFlavorNpc(item) && !storyteller.isProp(item) && !storyteller.isStaticEffect(item))
        {
            sendSystemMessageProse(transferer, pp);
            return SCRIPT_OVERRIDE;
        }
        String[] blueprintObjects = utils.getStringBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
        if (blueprintObjects == null || blueprintObjects.length == 0)
        {
            sendSystemMessage(transferer, new string_id("storyteller", "blueprint_invalid_data"));
            return SCRIPT_OVERRIDE;
        }
        int count = 1;
        if (storyteller.isTokenFlaggedWithDailyUsage(item))
        {
            count = storyteller.getTokenDailyUsesAvailable(item);
        }
        else if (getCount(item) > 0)
        {
            count = getCount(item);
        }
        int numUsed = 0;
        for (int j = 1; j <= count; j++)
        {
            for (int i = 0; i < blueprintObjects.length; i++)
            {
                String objectData = blueprintObjects[i];
                if (storyteller.validateBlueprintData(objectData))
                {
                    if (tokenName.equals(storyteller.getBlueprintItemName(objectData)) && !storyteller.isBlueprintTokenLoaded(objectData))
                    {
                        blueprintObjects[i] = storyteller.changeBlueprintItemTokenStatus(storyteller.BLUEPRINT_TOKEN_LOADED, objectData);
                        utils.setBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR, blueprintObjects);
                        storyteller.handleTokenUsage(item);
                        numUsed = numUsed + 1;
                        break;
                    }
                }
            }
            if (!exists(item))
            {
                break;
            }
        }
        if (numUsed > 0)
        {
            if (numUsed == 1)
            {
                message = new string_id("storyteller", "blueprint_token_loaded_one");
            }
            else 
            {
                message = new string_id("storyteller", "blueprint_token_loaded_multiple");
            }
            pp = prose.getPackage(message, transferer, transferer);
            prose.setTO(pp, tokenDisplayName);
            prose.setDI(pp, numUsed);
        }
        sendSystemMessageProse(transferer, pp);
        return SCRIPT_OVERRIDE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, storyteller.BLUEPRINT_AUTHOR_NAME_OBJVAR))
        {
            String blueprintAuthor = getStringObjVar(self, storyteller.BLUEPRINT_AUTHOR_NAME_OBJVAR);
            if (blueprintAuthor != null && blueprintAuthor.length() > 0)
            {
                names[idx] = "storyteller_blueprint_author";
                attribs[idx] = "\n" + blueprintAuthor;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR))
        {
            String blueprintDescription = getStringObjVar(self, storyteller.BLUEPRINT_DECRIPTION_OBJVAR);
            if (blueprintDescription != null && blueprintDescription.length() > 0)
            {
                names[idx] = "storyteller_blueprint_desc";
                attribs[idx] = "\n" + blueprintDescription;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (utils.hasStringBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR))
        {
            String blueprintData = getBlueprintAttributes(self);
            if (blueprintData.length() > 0)
            {
                names[idx] = "storyteller_blueprint_objects";
                attribs[idx] = blueprintData;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String getBlueprintAttributes(obj_id self) throws InterruptedException
    {
        String blueprintData = "";
        String[] storytellerObjects = utils.getStringBatchObjVar(self, storyteller.BLUEPRINT_OBJECTS_OBJVAR);
        if (storytellerObjects == null || storytellerObjects.length == 0)
        {
            return blueprintData;
        }
        Vector tokensNeeded = new Vector();
        tokensNeeded.setSize(0);
        Vector numTokensNeeded = new Vector();
        numTokensNeeded.setSize(0);
        Vector numTokensLoaded = new Vector();
        numTokensLoaded.setSize(0);
        for (int i = 0; i < storytellerObjects.length; i++)
        {
            String objectData = storytellerObjects[i];
            if (storyteller.validateBlueprintData(objectData))
            {
                String tokenStaticName = storyteller.getBlueprintItemName(objectData);
                if (tokensNeeded.contains(tokenStaticName))
                {
                    int position = tokensNeeded.indexOf(tokenStaticName);
                    numTokensNeeded.set(position, new Integer(((Integer)numTokensNeeded.get(position)).intValue() + 1));
                    if (storyteller.isBlueprintTokenLoaded(objectData))
                    {
                        numTokensLoaded.set(position, new Integer(((Integer)numTokensLoaded.get(position)).intValue() + 1));
                    }
                }
                else 
                {
                    utils.addElement(tokensNeeded, tokenStaticName);
                    utils.addElement(numTokensNeeded, 1);
                    int numLoaded = 0;
                    if (storyteller.isBlueprintTokenLoaded(objectData))
                    {
                        numLoaded = 1;
                    }
                    utils.addElement(numTokensLoaded, numLoaded);
                }
            }
        }
        if (tokensNeeded.size() == numTokensNeeded.size() && tokensNeeded.size() == numTokensLoaded.size())
        {
            blueprintData = "\n";
            for (int j = 0; j < tokensNeeded.size(); j++)
            {
                String tokenName = getString(new string_id("static_item_n", ((String)tokensNeeded.get(j))));
                int needed = ((Integer)numTokensNeeded.get(j)).intValue();
                int loaded = ((Integer)numTokensLoaded.get(j)).intValue();
                String tokenNumTxt = "\\" + colors_hex.RED + " " + loaded + "/" + needed + " \\#.";
                if (needed == loaded)
                {
                    tokenNumTxt = "\\" + colors_hex.GREEN + " " + loaded + "/" + needed + " \\#.";
                }
                blueprintData = blueprintData + tokenName + ":" + tokenNumTxt + "\n";
            }
        }
        return blueprintData;
    }
}

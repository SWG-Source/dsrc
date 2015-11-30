package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.qa;
import script.library.sui;
import script.library.static_item;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

public class qange extends script.base_script
{
    public qange()
    {
    }
    public static final String SCRIPTVAR = "qange";
    public static final String NGE_TOOL_TITLE = "QA NGE TOOL";
    public static final String RESPEC_TOOL_PROMPT = "This tool allows the tester to attain a Respec item in their inventory.\n\nPlease select a number (between 1 - 90) that will represent the level that the player character will respec to (Example: 61).";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qange");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qange");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                toolMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGiveRespecItem(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String stringEntry = sui.getInputBoxText(params);
                int entry = utils.stringToInt(stringEntry);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(player, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                if (entry > 0 && entry < 91)
                {
                    spawnRespecItem(self, entry);
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "Invalid entry");
                    qa.createInputBox(player, RESPEC_TOOL_PROMPT, NGE_TOOL_TITLE, "handleGiveRespecItem", SCRIPTVAR + ".pid");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeletionQuestion(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            int bp = sui.getIntButtonPressed(params);
            if (bp == sui.BP_CANCEL)
            {
                removePlayer(self, "");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (!utils.hasScriptVar(self, SCRIPTVAR + ".deleteInventory"))
                {
                    qa.showConfirmationSui(self, "Delete Inventory?", "Do you want to delete everything in your inventory (that isn't equiped) to make room for roadmap items?", "handleDeletionResponse");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeletionResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            int bp = sui.getIntButtonPressed(params);
            if (bp == sui.BP_CANCEL)
            {
                removePlayer(self, "");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id inventory = utils.getInventoryContainer(self);
                obj_id respecDevice = utils.getStaticItemInBankOrInventory(self, "item_respec_token_01_01");
                obj_id[] items = getContents(inventory);
                for (int i = 0; i < items.length; i++)
                {
                    if (items[i] != respecDevice)
                    {
                        destroyObject(items[i]);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id player) throws InterruptedException
    {
        qa.createInputBox(player, RESPEC_TOOL_PROMPT, NGE_TOOL_TITLE, "handleGiveRespecItem", SCRIPTVAR + ".pid");
    }
    public void removePlayer(obj_id player, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(player, err);
        qa.removeScriptVars(player, SCRIPTVAR);
        utils.removeScriptVarTree(player, SCRIPTVAR);
    }
    public void spawnRespecItem(obj_id self, int combatLevel) throws InterruptedException
    {
        obj_id respecDevice = utils.getStaticItemInBankOrInventory(self, "item_respec_token_01_01");
        if (hasScript(self, "systems.respec.click_combat_respec"))
        {
            detachScript(self, "systems.respec.click_combat_respec");
        }
        if (isIdValid(respecDevice))
        {
            destroyObject(respecDevice);
        }
        resetTester(self);
        utils.setScriptVar(self, "clickRespec.combatLevel", combatLevel);
        obj_id newRespecDevice = static_item.createNewItemFunction("item_respec_token_01_01", self);
        CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has spawned a Respec Token with combat level of " + combatLevel + " by using the QA NGE Tool.");
        if (isIdValid(newRespecDevice))
        {
            setObjVar(newRespecDevice, "combatLevel", combatLevel);
            setObjVar(newRespecDevice, "highestLevel", true);
        }
        String popupText = "You must use the Respec Token in your inventory to get a new profession and the level you specified.";
        int pid = sui.msgbox(self, self, popupText, sui.OK_ONLY, "Respec Tool Instructions", "handleDeletionQuestion");
    }
    public void resetTester(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "clickRespec");
        qa.revokeAllSkills(self);
    }
}

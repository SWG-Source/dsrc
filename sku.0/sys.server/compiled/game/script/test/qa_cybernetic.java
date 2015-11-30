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
import script.library.cybernetic;

public class qa_cybernetic extends script.base_script
{
    public qa_cybernetic()
    {
    }
    public static final String SCRIPTVAR = "qacybernetic";
    public static final int INSTALL_OPTION = 0;
    public static final int UNINSTALL_OPTION = 1;
    public static final int REPAIR_OPTION = 2;
    public static final int STRENGTH_ARM = 0;
    public static final int LIGHTNING = 1;
    public static final int BURST_RUN = 2;
    public static final int REVIVE = 3;
    public static final int ARMOR = 4;
    public static final int SURE_SHOT = 5;
    public static final int CRIT_SNIPE = 6;
    public static final int KICK_DOWN = 7;
    public static final String ARM_STRENGTH = "object/tangible/wearables/cybernetic/s02/cybernetic_s02_arm_l.iff";
    public static final String ARM_LIGHTNING = "object/tangible/wearables/cybernetic/s02/cybernetic_s02_arm_r.iff";
    public static final String LEGS_BURST = "object/tangible/wearables/cybernetic/s02/cybernetic_s02_legs.iff";
    public static final String ARM_REVIVE = "object/tangible/wearables/cybernetic/s03/cybernetic_s03_arm_l.iff";
    public static final String ARM_ARMOR = "object/tangible/wearables/cybernetic/s03/cybernetic_s03_arm_r.iff";
    public static final String ARM_SURESHOT = "object/tangible/wearables/cybernetic/s05/cybernetic_s05_arm_l.iff";
    public static final String ARM_SNIPE = "object/tangible/wearables/cybernetic/s05/cybernetic_s05_arm_r.iff";
    public static final String LEGS_KICK = "object/tangible/wearables/cybernetic/s05/cybernetic_s05_legs.iff";
    public static final String[] CYBER_MENU_LIST = 
    {
        "Install",
        "Uninstall",
        "Repair"
    };
    public static final String[] CYBERNETIC_LIST_OPTIONS = 
    {
        "Cyborg Strength Arm",
        "Cyborg Lightning Arm",
        "Cyborg Burst Run Legs",
        "Cyborg Revive Arm",
        "Cyborg Armor Arm",
        "Cyborg Sure Shot Arm",
        "Cyborg Critical Snipe Arm",
        "Cyborg Kick Down Legs"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_cybernetic");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_cybernetic");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                toolCyberMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.qaToolMainMenu(player);
                utils.removeScriptVarTree(player, SCRIPTVAR);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case INSTALL_OPTION:
                boolean boolCheck = cybernetic.hasMaxInstalled(player);
                if (boolCheck == false)
                {
                    installChoiceMenu(player);
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "This character already has the maximum number of Cybernetics installed!");
                }
                break;
                case UNINSTALL_OPTION:
                utils.setScriptVar(player, SCRIPTVAR + ".uninstall", "uninstall");
                getAttachedCybers(player);
                break;
                case REPAIR_OPTION:
                utils.setScriptVar(player, SCRIPTVAR + ".repair", "repair");
                getAttachedCybers(player);
                break;
                default:
                qa.refreshMenu(player, " - Cybernetic Main Menu - \nSelect Install, Uninstall or Repair", "Cybernetic Tool", CYBERNETIC_LIST_OPTIONS, "handleChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".CyberMainMenu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInstallOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.refreshMenu(player, " - Cybernetic Main Menu - \nSelect Install, Uninstall or Repair", "Cybernetic Tool", CYBERNETIC_LIST_OPTIONS, "handleChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".CyberMainMenu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            switch (idx)
            {
                case STRENGTH_ARM:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", ARM_STRENGTH);
                installCyber(player);
                break;
                case LIGHTNING:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", ARM_LIGHTNING);
                installCyber(player);
                break;
                case BURST_RUN:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", LEGS_BURST);
                installCyber(player);
                break;
                case REVIVE:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", ARM_REVIVE);
                installCyber(player);
                break;
                case ARMOR:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", ARM_ARMOR);
                installCyber(player);
                break;
                case SURE_SHOT:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", ARM_SURESHOT);
                installCyber(player);
                break;
                case CRIT_SNIPE:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", ARM_SNIPE);
                installCyber(player);
                break;
                case KICK_DOWN:
                utils.setScriptVar(player, SCRIPTVAR + ".cyberChoice", LEGS_KICK);
                installCyber(player);
                break;
                default:
                qa.refreshMenu(player, " - Cybernetic Main Menu - \nSelect Install, Uninstall or Repair", "Cybernetic Tool", CYBERNETIC_LIST_OPTIONS, "handleChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".CyberMainMenu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUninstallRepairChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (btn == sui.BP_CANCEL)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_REVERT)
            {
                qa.refreshMenu(player, " - Cybernetic Main Menu - \nSelect Install, Uninstall or Repair", "Cybernetic Tool", CYBERNETIC_LIST_OPTIONS, "handleChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".CyberMainMenu", sui.OK_CANCEL_REFRESH);
                return SCRIPT_CONTINUE;
            }
            int idx = sui.getListboxSelectedRow(params);
            if (idx < 0)
            {
                qa.removePlayer(player, SCRIPTVAR, "");
                return SCRIPT_CONTINUE;
            }
            String[] list = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".cyberList");
            obj_id[] idxObj_id_list = utils.getObjIdArrayScriptVar(player, SCRIPTVAR + ".cyberID");
            obj_id idxObj_id = idxObj_id_list[idx];
            if (utils.hasScriptVar(player, SCRIPTVAR + ".uninstall"))
            {
                removeCyber(player, idxObj_id);
            }
            else if (utils.hasScriptVar(player, SCRIPTVAR + ".repair"))
            {
                repairCyber(player, idxObj_id);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "An error has occurred, please try again.");
                toolCyberMainMenu(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolCyberMainMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "- Cybernetic Install Menu -\nSelect Install, Uninstall or Repair.", "Cybernetic Tool", CYBER_MENU_LIST, "handleChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".CyberMainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void installChoiceMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "- Cybernetic List Menu -\nSelect a Cybernetic to install.", "Cybernetic Tool", CYBERNETIC_LIST_OPTIONS, "handleInstallOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".CyberInstallMenu", sui.OK_CANCEL_REFRESH);
    }
    public void uninstallChoices(obj_id player, String[] list) throws InterruptedException
    {
        qa.refreshMenu(player, "- Cybernetic Uninstall Menu -\nChoose a Cybernetic to Uninstall.", "Cybernetic Tool", list, "handleUninstallRepairChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".uninstallMenu", sui.OK_CANCEL_REFRESH);
    }
    public void repairChoices(obj_id player, String[] list) throws InterruptedException
    {
        qa.refreshMenu(player, "- Cybernetic Repair Menu -\nChoose a Cybernetic to Repair.", "Cybernetic Tool", list, "handleUninstallRepairChoice", SCRIPTVAR + ".pid", SCRIPTVAR + ".uninstallMenu", sui.OK_CANCEL_REFRESH);
    }
    public void installCyber(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, SCRIPTVAR + ".cyberChoice"))
        {
            String myLimb = utils.getStringScriptVar(player, SCRIPTVAR + ".cyberChoice");
            obj_id inventory = utils.getInventoryContainer(player);
            obj_id cyberItemID = createObject(myLimb, inventory, "");
            cybernetic.installCyberneticItem(player, player, cyberItemID);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "An error has occurred, please try again.");
            toolCyberMainMenu(player);
        }
        qa.removePlayer(player, SCRIPTVAR, "");
    }
    public void getAttachedCybers(obj_id player) throws InterruptedException
    {
        Vector installList = new Vector();
        obj_id[] installed = cybernetic.getInstalledCybernetics(player);
        if (installed != null)
        {
            for (int i = 0; i < installed.length; i++)
            {
                String itemName = getTemplateName(installed[i]);
                installList.add(itemName);
            }
            String[] list = new String[installList.size()];
            installList.toArray(list);
            utils.setScriptVar(player, SCRIPTVAR + ".cyberID", installed);
            utils.setScriptVar(player, SCRIPTVAR + ".cyberList", list);
            if (utils.hasScriptVar(player, SCRIPTVAR + ".uninstall"))
            {
                uninstallChoices(player, list);
            }
            else if (utils.hasScriptVar(player, SCRIPTVAR + ".repair"))
            {
                repairChoices(player, list);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "An error has occurred, please try again.");
                toolCyberMainMenu(player);
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "There are not cybernetics installed on this character.");
            toolCyberMainMenu(player);
        }
    }
    public void removeCyber(obj_id player, obj_id idxObj_id) throws InterruptedException
    {
        cybernetic.unInstallCyberneticItem(player, player, idxObj_id);
        qa.removePlayer(player, SCRIPTVAR, "");
    }
    public void repairCyber(obj_id player, obj_id idxObj_id) throws InterruptedException
    {
        cybernetic.repairCyberneticItem(player, player, idxObj_id);
        qa.removePlayer(player, SCRIPTVAR, "");
    }
}

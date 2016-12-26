package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.qa;
import script.library.skill_template;
import script.library.space_flags;
import script.library.sui;
import script.library.utils;
import script.library.xp;

public class qaxp extends script.base_script
{
    public qaxp()
    {
    }
    public static final int XP_AMOUNT = 1000000;
    public static final String SCRIPTVAR = "qaxp";
    public static final String PROMPT = "Select the amount of XP you desire in the right box";
    public static final String[] QATOOL_MAIN_MENU = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
    public static final String QATOOL_TITLE = "QA Tools";
    public static final String QATOOL_PROMPT = "Choose the tool you want to use";
    public static final int REVOKE_XP = 0;
    public static final int COMBAT_GENERAL = 1;
    public static final int QUEST_COMBAT = 2;
    public static final int QUEST_CRAFTING = 3;
    public static final int QUEST_SOCIAL = 4;
    public static final int QUEST_GENERAL = 5;
    public static final int PRESTIGE_IMP = 6;
    public static final int PRESTIGE_REB = 7;
    public static final int PRESTIGE_NEUTRAL = 8;
    public static final String[] THIS_TOOL_MENU = 
    {
        "Revoke non-pilot experience",
        "combat_general",
        "quest_combat",
        "quest_crafting",
        "quest_social",
        "quest_general",
        "prestige_imperial",
        "prestige_rebel",
        "prestige_pilot"
    };
    public static final String[] NON_COMBAT_PRFESSIONS = 
    {
        "entertainer",
        "trader"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.qaxp");
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
    public int handleXpOptions(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            removePlayer(player, "");
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_REVERT)
        {
            qa.refreshMenu(self, QATOOL_PROMPT, QATOOL_TITLE, QATOOL_MAIN_MENU, "toolMainMenu", true, "qatool.pid");
            utils.removeScriptVarTree(player, SCRIPTVAR);
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            removePlayer(player, "");
            return SCRIPT_CONTINUE;
        }
        String template = getSkillTemplate(player);
        switch (idx)
        {
            case REVOKE_XP:
            revokeGroungXp(player);
            break;
            case COMBAT_GENERAL:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case QUEST_COMBAT:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case QUEST_CRAFTING:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case QUEST_SOCIAL:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case QUEST_GENERAL:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case PRESTIGE_IMP:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case PRESTIGE_REB:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            case PRESTIGE_NEUTRAL:
            validateTemplateThenConstructTransferUI(player, THIS_TOOL_MENU[idx], template);
            break;
            default:
            removePlayer(player, "");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleXpAmountAdd(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = qa.findTarget(self);
        int btn = sui.getIntButtonPressed(params);
        int amt = sui.getTransferInputTo(params);
        String xpType = utils.getStringScriptVar(player, SCRIPTVAR + ".xpType");
        if (btn == sui.BP_CANCEL)
        {
            removePlayer(player, "");
            toolMainMenu(player);
            return SCRIPT_CONTINUE;
        }
        xp.grantXpByTemplate(player, amt);
        toolMainMenu(player);
        return SCRIPT_CONTINUE;
    }
    public int handleXpAmountRevoke(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = qa.findTarget(self);
        int btn = sui.getIntButtonPressed(params);
        int amt = sui.getTransferInputTo(params);
        String xpType = utils.getStringScriptVar(player, SCRIPTVAR + ".xpType");
        if (btn == sui.BP_CANCEL)
        {
            removePlayer(player, "");
            toolMainMenu(player);
            return SCRIPT_CONTINUE;
        }
        xp.grantXpByTemplate(player, -amt);
        toolMainMenu(player);
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id player) throws InterruptedException
    {
        qa.refreshMenu(player, "Select the xp type...", "NPE XP Tool", THIS_TOOL_MENU, "handleXpOptions", "qaxp.pid", SCRIPTVAR + ".mainMenu", sui.OK_CANCEL_REFRESH);
    }
    public void removePlayer(obj_id player, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(player, err);
        qa.removeScriptVars(player, SCRIPTVAR);
        utils.removeScriptVarTree(player, SCRIPTVAR);
    }
    public void validateTemplateThenConstructTransferUI(obj_id player, String choice, String template) throws InterruptedException
    {
        if (choice.startsWith("prestige"))
        {
            if (space_flags.hasAnyPilotSkill(player) == true)
            {
                if (choice.startsWith("prestige"))
                {
                    if (choice.equals("prestige_imperial") && space_flags.isImperialPilot(player) == true)
                    {
                        utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                        sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                    }
                    else if (choice.equals("prestige_rebel") && space_flags.isRebelPilot(player) == true)
                    {
                        utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                        sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                    }
                    else if (choice.equals("prestige_pilot") && space_flags.isNeutralPilot(player) == true)
                    {
                        utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                        sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "The player has to be the correct Pilot Faction in order to receive this prestige.");
                    }
                }
                else 
                {
                    utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                    sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                }
            }
            else 
            {
                removePlayer(player, "The test character does not have a pilot skill");
                toolMainMenu(player);
            }
        }
        else 
        {
            String templateType = "";
            for (int i = 0; i < NON_COMBAT_PRFESSIONS.length; i++)
            {
                if (template.startsWith(NON_COMBAT_PRFESSIONS[i]))
                {
                    templateType = "noncombat";
                }
            }
            if (templateType.equals(""))
            {
                if (choice.equals("combat_general") || choice.equals("quest_combat") || choice.equals("quest_general"))
                {
                    utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                    sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                }
                else 
                {
                    removePlayer(player, "The player needs to have the correct profession to seek a non-Combat XP type. (You are a Combat Profession) ");
                    toolMainMenu(player);
                }
            }
            else 
            {
                if (choice.equals("quest_social") && template.startsWith("entertainer") || choice.equals("quest_general") && template.startsWith("entertainer"))
                {
                    templateType = "";
                    utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                    sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                }
                else if (choice.equals("quest_crafting") && template.startsWith("trader") || choice.equals("quest_general") && template.startsWith("trader"))
                {
                    templateType = "";
                    utils.setScriptVar(player, SCRIPTVAR + ".xpType", choice);
                    sui.transfer(player, player, PROMPT, "XP Tool", "Available", XP_AMOUNT, "Amount", 0, "handleXpAmountAdd");
                }
                else 
                {
                    removePlayer(player, "The player needs to have the correct profession to seek that XP type.");
                    toolMainMenu(player);
                }
            }
        }
    }
    public void revokeGroungXp(obj_id player) throws InterruptedException
    {
        String skillName = getWorkingSkill(player);
        String xpType = skill_template.getSkillExperienceType(skillName);
        utils.setScriptVar(player, SCRIPTVAR + ".xpType", xpType);
        sui.transfer(player, player, PROMPT, "XP Tool", "Revoke Experience", XP_AMOUNT, "Amount", 0, "handleXpAmountRevoke");
    }
}

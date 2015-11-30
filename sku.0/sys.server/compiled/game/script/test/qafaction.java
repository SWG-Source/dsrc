package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.qa;
import script.library.sui;
import script.library.utils;
import script.library.factions;
import java.util.HashSet;

public class qafaction extends script.base_script
{
    public qafaction()
    {
    }
    public static final String NPC_PROMPT = "Choose a faction option\n" + "NOTE: Some factions are not really available to players, so occasionally if you select one and try to " + "add or remove the faction, it will not work";
    public static final String PROMPT = "Choose a faction option";
    public static final String TITLE = "QA Faction Tool";
    public static final int XP_AMOUNT = 1000000;
    public static final String[] FACTIONS = 
    {
        "Rebel",
        "Imperial"
    };
    public static final String[] MIN_MAX = 
    {
        "Max faction",
        "Min Faction",
        "Zero Faction"
    };
    public static final String[] MAIN_MENU_CONST = 
    {
        "Join a faction",
        "Go Covert (Combatant)",
        "Go Overt (Special Forces)",
        "Go On Leave",
        "Go Neutral",
        "GCW Faction Points",
        "Manipulate NPC Factions"
    };
    public static final String[] GCW_MENU = 
    {
        "Check Lambda Shuttles"
    };
    public static final String SCRIPTVAR = "qafac";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qafaction");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qafaction");
        }
        return SCRIPT_CONTINUE;
    }
    public int mainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR, false);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String faction = factions.getFaction(player);
                if (btn == sui.BP_CANCEL)
                {
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    qa.qaToolMainMenu(self);
                    utils.removeScriptVarTree(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                switch (idx)
                {
                    case 0:
                    qa.refreshMenu(player, PROMPT, TITLE, FACTIONS, "joinFactions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 1:
                    if (!factions.isRebel(player) && !factions.isImperial(player))
                    {
                        sendSystemMessageTestingOnly(player, "You have to be part of a faction to go Covert!!!");
                        qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                        return SCRIPT_CONTINUE;
                    }
                    setObjVar(player, "intChangingFactionStatus", 1);
                    factions.goCovert(player);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character to Imperial or Rebel Covert (Combatant) status using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, qa.populateArrayDoNotSort(self, "faction_tool", "datatables/test/qa_tool_menu.iff"), "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 2:
                    if (!factions.isRebel(player) && !factions.isImperial(player))
                    {
                        sendSystemMessageTestingOnly(player, "You have to be part of a faction to go Overt!!!");
                        qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                        return SCRIPT_CONTINUE;
                    }
                    setObjVar(player, "intChangingFactionStatus", 1);
                    factions.goOvert(player);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character to Imperial or Rebel Overt (Special Forces) status using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 3:
                    sendSystemMessageTestingOnly(player, faction);
                    if (!factions.isRebel(player) && !factions.isImperial(player))
                    {
                        sendSystemMessageTestingOnly(player, "You have to be part of a faction to go on Leave!!!");
                        qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                        return SCRIPT_CONTINUE;
                    }
                    setObjVar(player, "intChangingFactionStatus", 1);
                    factions.goOnLeave(player);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character to Imperial or Rebel Leave status using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 4:
                    pvpMakeNeutral(player);
                    pvpSetAlignedFaction(player, 0);
                    sendSystemMessageTestingOnly(player, "You are now Neutral.");
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character to Neutral non-faction status using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 5:
                    qa.refreshMenu(player, PROMPT, TITLE, FACTIONS, "getFactionPoints", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 6:
                    String[] menuArray = new String[47];
                    String[] factionArray = qa.populateArray(player, "factionName", "datatables/faction/faction.iff", "Neutral");
                    int i = 0;
                    for (i = 0; i <= 45; i++)
                    {
                        menuArray[i] = factionArray[i];
                    }
                    menuArray[i++] = "*MORE*";
                    Arrays.sort(menuArray);
                    utils.setScriptVar(player, SCRIPTVAR + ".menu", menuArray);
                    utils.setScriptVar(player, SCRIPTVAR + ".factionMenu", factionArray);
                    utils.setScriptVar(player, SCRIPTVAR + ".index", i);
                    qa.refreshMenu(player, NPC_PROMPT, TITLE, menuArray, "npcFactions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    default:
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int getFactionPoints(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR, false);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                switch (idx)
                {
                    case 0:
                    sendSystemMessageTestingOnly(player, "Option 1.");
                    utils.setScriptVar(player, SCRIPTVAR + ".factionType", factions.FACTION_REBEL);
                    sui.transfer(player, player, PROMPT, "Rebel Faction", "Available", XP_AMOUNT, "Amount", 0, "handleFactionAdd");
                    break;
                    case 1:
                    sendSystemMessageTestingOnly(player, "Option 2.");
                    utils.setScriptVar(player, SCRIPTVAR + ".factionType", factions.FACTION_IMPERIAL);
                    sui.transfer(player, player, PROMPT, "Imperial Faction", "Available", XP_AMOUNT, "Amount", 0, "handleFactionAdd");
                    break;
                    default:
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFactionAdd(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        float amt = sui.getTransferInputTo(params);
        String factionType = utils.getStringScriptVar(player, SCRIPTVAR + ".factionType");
        if (btn == sui.BP_CANCEL)
        {
            qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
            return SCRIPT_CONTINUE;
        }
        factions.addFactionStanding(player, factionType, amt);
        qa.refreshMenu(player, PROMPT, TITLE, FACTIONS, "getFactionPoints", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
        return SCRIPT_CONTINUE;
    }
    public int joinFactions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR, false);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                int faction = 0;
                if (btn == sui.BP_CANCEL)
                {
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    qa.refreshMenu(player, PROMPT, TITLE, qa.populateArrayDoNotSort(self, "faction_tool", "datatables/test/qa_tool_menu.iff"), "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                faction = factions.getFactionNumber(FACTIONS[idx]);
                int factionHashCode = dataTableGetInt(factions.FACTION_TABLE, faction, "pvpFaction");
                if (factionHashCode != factions.AD_HOC_FACTION && factionHashCode != 0)
                {
                    pvpSetAlignedFaction(player, factionHashCode);
                    sendSystemMessageTestingOnly(player, "Faction Changed.");
                }
                qa.refreshMenu(player, PROMPT, TITLE, FACTIONS, "joinFactions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int npcFactions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR, false);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String prevMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".menu");
                String factionArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".factionMenu");
                int lastIndex = utils.getIntScriptVar(self, SCRIPTVAR + ".index");
                if (btn == sui.BP_CANCEL)
                {
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    qa.refreshMenu(player, PROMPT, TITLE, MAIN_MENU_CONST, "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                String choice = prevMenuArray[idx];
                if (choice.equals("*MORE*"))
                {
                    HashSet theSet = new HashSet();
                    int nextLastIndex = lastIndex + 45;
                    int i = lastIndex;
                    if (i >= factionArray.length)
                    {
                        sendSystemMessageTestingOnly(player, "There are no more");
                        qa.refreshMenu(player, PROMPT, TITLE, prevMenuArray, "npcFactions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                        return SCRIPT_CONTINUE;
                    }
                    for (i = lastIndex; i < nextLastIndex && i < factionArray.length; i++)
                    {
                        theSet.add(factionArray[i]);
                    }
                    theSet.add("*MORE*");
                    String[] menuArray = new String[theSet.size()];
                    theSet.toArray(menuArray);
                    Arrays.sort(menuArray);
                    utils.setScriptVar(player, SCRIPTVAR + ".menu", menuArray);
                    utils.setScriptVar(player, SCRIPTVAR + ".index", nextLastIndex);
                    qa.refreshMenu(player, PROMPT, TITLE, menuArray, "npcFactions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(player, SCRIPTVAR + ".choice", choice);
                qa.refreshMenu(player, PROMPT, TITLE, MIN_MAX, "minMaxFaction", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int minMaxFaction(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR, false);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String lastChoice = utils.getStringScriptVar(player, SCRIPTVAR + ".choice");
                String prevMenuArray[] = utils.getStringArrayScriptVar(player, SCRIPTVAR + ".menu");
                if (btn == sui.BP_CANCEL)
                {
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                if (btn == sui.BP_REVERT)
                {
                    qa.refreshMenu(player, PROMPT, TITLE, qa.populateArrayDoNotSort(self, "faction_tool", "datatables/test/qa_tool_menu.iff"), "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    return SCRIPT_CONTINUE;
                }
                switch (idx)
                {
                    case 0:
                    factions.setFactionStanding(player, lastChoice, factions.FACTION_RATING_MAX);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character faction for " + lastChoice + " to " + factions.FACTION_RATING_MAX + " using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, qa.populateArrayDoNotSort(self, "faction_tool", "datatables/test/qa_tool_menu.iff"), "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 1:
                    factions.setFactionStanding(player, lastChoice, factions.FACTION_RATING_MIN);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character faction for " + lastChoice + " to " + factions.FACTION_RATING_MIN + " using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, qa.populateArrayDoNotSort(self, "faction_tool", "datatables/test/qa_tool_menu.iff"), "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    case 2:
                    factions.setFactionStanding(player, lastChoice, factions.FACTION_RATING_INVALID);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has set their character faction for " + lastChoice + " to " + factions.FACTION_RATING_INVALID + " using the QA Faction Tool.");
                    qa.refreshMenu(player, PROMPT, TITLE, qa.populateArrayDoNotSort(self, "faction_tool", "datatables/test/qa_tool_menu.iff"), "mainMenuOptions", SCRIPTVAR + ".pid", sui.OK_CANCEL_REFRESH);
                    break;
                    default:
                    qa.removeScriptVars(player, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

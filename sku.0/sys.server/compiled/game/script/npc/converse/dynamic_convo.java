package script.npc.converse;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.chat;
import script.library.utils;
import script.library.ai_lib;

public class dynamic_convo extends script.base_script
{
    public dynamic_convo()
    {
    }
    public static final string_id GIVE_ITEM_SUCCESS = new string_id("system_msg", "give_item_success");
    public static final string_id GIVE_ITEM_FAILURE = new string_id("system_msg", "give_item_failure");
    public static final string_id SCHEMATIC_GRANTED = new string_id("system_msg", "schematic_granted");
    public static final int SPEAK = 0;
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int BRANCH = 3;
    public static final int END = 4;
    public static final int SET_OBJVAR = 5;
    public static final int REMOVE_OBJVAR = 6;
    public static final int MESSAGE_PLAYER = 7;
    public static final int MESSAGE_NPC = 8;
    public static final int GIVE_ITEM = 9;
    public static final int GRANT_SCHEMATIC = 10;
    public static final int REWARD_FACTION = 11;
    public static final String[] actionList = 
    {
        "speak",
        "add",
        "remove",
        "branch",
        "end",
        "setObjvar",
        "removeObjvar",
        "messagePlayer",
        "messageNpc",
        "giveItem",
        "grantSchematic",
        "rewardFaction"
    };
    public static final int HAS_OBJVAR = 0;
    public static final int NOT_HAS_OBJVAR = 1;
    public static final int EQUAL = 2;
    public static final int NOT_EQUAL = 3;
    public static final int LESS_THAN = 4;
    public static final int GREATER_THAN = 5;
    public static final int LESS_THAN_OR_EQUAL = 6;
    public static final int GREATER_THAN_OR_EQUAL = 7;
    public static final int BETWEEN = 8;
    public static final String[] conditionList = 
    {
        "**",
        "!*",
        "==",
        "!=",
        "<",
        ">",
        "<=",
        ">=",
        "><"
    };
    public static final int NEUTRAL = 0;
    public static final int REBEL = 1;
    public static final int REBEL_OVERT = 2;
    public static final int REBEL_COVERT = 3;
    public static final int IMPERIAL = 4;
    public static final int IMPERIAL_OVERT = 5;
    public static final int IMPERIAL_COVERT = 6;
    public static final String[] pvpFactionList = 
    {
        "Neutral",
        "Rebel",
        "RebelOvert",
        "RebelCovert",
        "Imperial",
        "ImperialOvert",
        "ImperialCovert"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        String datatable = getStringObjVar(self, "convo_table");
        datatable = "datatables/convo/" + datatable + ".iff";
        String[] convoType = dataTableGetStringColumn(datatable, "convoType");
        dictionary entry = null;
        int dialogRow = 0;
        for (int i = 0; i < convoType.length; i++)
        {
            if (convoType[i].equals("dialogStart"))
            {
                entry = dataTableGetRow(datatable, i);
                dialogRow = i;
                if (isGated(entry, speaker))
                {
                    entry = null;
                    continue;
                }
                else 
                {
                    break;
                }
            }
        }
        if (entry == null)
        {
            LOG("dynamic_convo", "ERROR: No opening dialog entry found in convo datatable!!");
            return SCRIPT_OVERRIDE;
        }
        String dialogEntry = entry.getString("convoEntry");
        LOG("dynamic_convo", "Dialog entry '" + dialogEntry + "' found");
        String[] convoParent = dataTableGetStringColumn(datatable, "convoParent");
        String[] convoEntry = dataTableGetStringColumn(datatable, "convoEntry");
        string_id openDialog = new string_id(entry.getString("convoFile"), entry.getString("convoText"));
        Vector dialogResponse = new Vector();
        dialogResponse.setSize(0);
        for (int i = 0; i < convoParent.length; i++)
        {
            if (convoParent[i] != null && convoParent[i].equals(dialogEntry))
            {
                entry = dataTableGetRow(datatable, i);
                if (!(entry.getString("convoType")).equals("response"))
                {
                    LOG("dynamic_convo", "WARNING: Parent defined for non-response entry!!  Skipping entry.");
                    continue;
                }
                if (isGated(entry, speaker))
                {
                    continue;
                }
                string_id response = new string_id(entry.getString("convoFile"), entry.getString("convoText"));
                dialogResponse = utils.addElement(dialogResponse, response);
                LOG("dynamic_convo", "Added response '" + entry.getString("convoEntry") + "' to dialog.");
            }
        }
        for (int j = dialogRow + 1; j < convoEntry.length; j++)
        {
            if (convoEntry[j].equals(dialogEntry))
            {
                entry = dataTableGetRow(datatable, j);
                if (!(entry.getString("convoType")).equals("dialogAction"))
                {
                    LOG("dynamic_convo", "WARNING: Duplicate dialog entry not defined as dialogAction!!  Skipping entry.");
                    continue;
                }
                if (isGated(entry, speaker))
                {
                    continue;
                }
                executeAction(entry, speaker, self);
            }
            else 
            {
                break;
            }
        }
        if (dialogResponse.size() == 0)
        {
            chat.chat(self, speaker, openDialog);
            return SCRIPT_OVERRIDE;
        }
        npcStartConversation(speaker, self, "dynamicConvo", openDialog, dialogResponse);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals("dynamicConvo"))
        {
            return SCRIPT_CONTINUE;
        }
        String datatable = getStringObjVar(self, "convo_table");
        datatable = "datatables/convo/" + datatable + ".iff";
        String[] convoFile = dataTableGetStringColumn(datatable, "convoFile");
        String[] convoText = dataTableGetStringColumn(datatable, "convoText");
        dictionary entry = null;
        for (int i = 0; i < convoText.length; i++)
        {
            if ((response.getAsciiId()).equals(convoText[i]) && (response.getTable()).equals(convoFile[i]))
            {
                entry = dataTableGetRow(datatable, i);
                String responseEntry = entry.getString("convoEntry");
                String[] convoEntry = dataTableGetStringColumn(datatable, "convoEntry");
                for (int j = i + 1; j < convoEntry.length; j++)
                {
                    if (convoEntry[j].equals(responseEntry))
                    {
                        entry = dataTableGetRow(datatable, j);
                        if (!(entry.getString("convoType")).equals("responseAction"))
                        {
                            LOG("dynamic_convo", "WARNING: Duplicate response entry not defined as responseAction!!  Skipping entry.");
                            continue;
                        }
                        if (isGated(entry, player))
                        {
                            continue;
                        }
                        executeAction(entry, player, self);
                    }
                    else 
                    {
                        break;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void executeAction(dictionary entry, obj_id player, obj_id npc) throws InterruptedException
    {
        String action = entry.getString("action");
        String data1 = entry.getString("data1");
        int data2 = entry.getInt("data2");
        string_id message = null;
        string_id response = null;
        dictionary params = new dictionary();
        switch (getActionId(action))
        {
            case SPEAK:
            message = new string_id(entry.getString("convoFile"), entry.getString("convoText"));
            npcSpeak(player, message);
            break;
            case ADD:
            response = getResponseEntry(npc, data1);
            if (response == null)
            {
                LOG("dynamic_convo", "WARNING: No response entry found to add!!");
                break;
            }
            npcAddConversationResponse(player, response);
            break;
            case REMOVE:
            response = getResponseEntry(npc, data1);
            if (response == null)
            {
                LOG("dynamic_convo", "WARNING: No response entry found to remove!!");
                break;
            }
            npcRemoveConversationResponse(player, response);
            break;
            case BRANCH:
            startNewConvoBranch(npc, player, data1);
            break;
            case END:
            npcEndConversation(player);
            break;
            case SET_OBJVAR:
            setObjVar(player, data1, data2);
            break;
            case REMOVE_OBJVAR:
            removeObjVar(player, data1);
            break;
            case MESSAGE_PLAYER:
            params.put("player", player);
            params.put("npc", npc);
            params.put("value", data2);
            messageTo(player, data1, params, 0, false);
            break;
            case MESSAGE_NPC:
            params.put("player", player);
            params.put("npc", npc);
            params.put("value", data2);
            messageTo(npc, data1, params, 0, false);
            break;
            case GIVE_ITEM:
            obj_id inventory = getObjectInSlot(player, "inventory");
            obj_id object = createObject(data1, inventory, "");
            if (!isIdValid(object))
            {
                sendSystemMessage(player, GIVE_ITEM_FAILURE);
            }
            else 
            {
                sendSystemMessage(player, GIVE_ITEM_SUCCESS);
            }
            break;
            case GRANT_SCHEMATIC:
            grantSchematic(player, data1);
            sendSystemMessage(player, SCHEMATIC_GRANTED);
            break;
            case REWARD_FACTION:
            factions.awardFactionStanding(player, data1, data2);
            break;
            default:
            LOG("dynamic_convo", "WARNING: Unknown response action defined!!");
            break;
        }
    }
    public void startNewConvoBranch(obj_id npc, obj_id player, String dialogEntry) throws InterruptedException
    {
        String datatable = getStringObjVar(npc, "convo_table");
        datatable = "datatables/convo/" + datatable + ".iff";
        String[] convoEntry = dataTableGetStringColumn(datatable, "convoEntry");
        dictionary entry = null;
        for (int i = 0; i < convoEntry.length; i++)
        {
            if (convoEntry[i].equals(dialogEntry))
            {
                entry = dataTableGetRow(datatable, i);
                if (isGated(entry, player))
                {
                    continue;
                }
            }
        }
        if (entry == null)
        {
            LOG("dynamic_convo", "ERROR: No branching dialog entry found in convo datatable!!");
            npcEndConversation(player);
            return;
        }
        string_id branchDialog = new string_id(entry.getString("convoFile"), entry.getString("convoText"));
        String[] convoParent = dataTableGetStringColumn(datatable, "convoParent");
        Vector dialogResponse = new Vector();
        dialogResponse.setSize(0);
        for (int i = 0; i < convoParent.length; i++)
        {
            if (convoParent[i] != null && convoParent[i].equals(dialogEntry))
            {
                entry = dataTableGetRow(datatable, i);
                if (!(entry.getString("convoType")).equals("response"))
                {
                    LOG("dynamic_convo", "WARNING: Parent defined for non-response entry!!  Skipping entry.");
                    continue;
                }
                if (isGated(entry, player))
                {
                    continue;
                }
                string_id response = new string_id(entry.getString("convoFile"), entry.getString("convoText"));
                dialogResponse = utils.addElement(dialogResponse, response);
            }
        }
        npcSpeak(player, branchDialog);
        if (dialogResponse.size() == 0)
        {
            npcEndConversation(player);
        }
        npcSetConversationResponses(player, dialogResponse);
    }
    public string_id getResponseEntry(obj_id npc, String responseEntry) throws InterruptedException
    {
        String datatable = getStringObjVar(npc, "convo_table");
        datatable = "datatables/convo/" + datatable + ".iff";
        String[] convoEntry = dataTableGetStringColumn(datatable, "convoEntry");
        dictionary entry = null;
        for (int i = 0; i < convoEntry.length; i++)
        {
            if (convoEntry[i].equals(responseEntry))
            {
                entry = dataTableGetRow(datatable, i);
                if (!(entry.getString("convoType")).equals("response"))
                {
                    LOG("dynamic_convo", "WARNING: Non-response entry found!!  Skipping entry.");
                    continue;
                }
                string_id response = new string_id(entry.getString("convoFile"), entry.getString("convoText"));
                return response;
            }
        }
        return null;
    }
    public boolean isGated(dictionary entry, obj_id player) throws InterruptedException
    {
        boolean gated = false;
        String gatingObjvar = entry.getString("gatingObjvar");
        if (gatingObjvar != null && !gatingObjvar.equals(""))
        {
            boolean hasObjvar = false;
            int playerObjvar = 0;
            if (hasObjVar(player, gatingObjvar))
            {
                hasObjvar = true;
                playerObjvar = getIntObjVar(player, gatingObjvar);
            }
            String condition = entry.getString("gatingObjvarCondition");
            int value1 = entry.getInt("gatingObjvarValue1");
            int value2 = entry.getInt("gatingObjvarValue2");
            boolean gatedObjvar = true;
            switch (getConditionId(condition))
            {
                case HAS_OBJVAR:
                if (hasObjvar)
                {
                    gatedObjvar = false;
                }
                break;
                case NOT_HAS_OBJVAR:
                if (!hasObjvar)
                {
                    gatedObjvar = false;
                }
                break;
                case EQUAL:
                LOG("dynamic_convo", "Gating on HAS_OBJVAR");
                if (hasObjvar && playerObjvar == value1)
                {
                    gatedObjvar = false;
                }
                break;
                case NOT_EQUAL:
                if (hasObjvar && playerObjvar != value1)
                {
                    gatedObjvar = false;
                }
                break;
                case LESS_THAN:
                if (hasObjvar && playerObjvar < value1)
                {
                    gatedObjvar = false;
                }
                break;
                case GREATER_THAN:
                if (hasObjvar && playerObjvar > value1)
                {
                    gatedObjvar = false;
                }
                break;
                case LESS_THAN_OR_EQUAL:
                if (hasObjvar && playerObjvar <= value1)
                {
                    gatedObjvar = false;
                }
                break;
                case GREATER_THAN_OR_EQUAL:
                if (hasObjvar && playerObjvar >= value1)
                {
                    gatedObjvar = false;
                }
                break;
                case BETWEEN:
                if (hasObjvar && playerObjvar > value1 && playerObjvar < value2)
                {
                    gatedObjvar = false;
                }
                break;
                default:
                LOG("dynamic_convo", "WARNING: Unknown Objvar gating condition!!");
                break;
            }
            gated |= gatedObjvar;
        }
        String gatingFaction = entry.getString("gatingFaction");
        if (gatingFaction != null && !gatingFaction.equals(""))
        {
            float playerFaction = factions.getFactionStanding(player, gatingFaction);
            String condition = entry.getString("gatingFactionCondition");
            float value1 = entry.getFloat("gatingFactionValue1");
            float value2 = entry.getFloat("gatingFactionValue2");
            boolean gatedFaction = true;
            switch (getConditionId(condition))
            {
                case EQUAL:
                if (playerFaction == value1)
                {
                    gatedFaction = false;
                }
                break;
                case NOT_EQUAL:
                if (playerFaction != value1)
                {
                    gatedFaction = false;
                }
                break;
                case LESS_THAN:
                if (playerFaction < value1)
                {
                    gatedFaction = false;
                }
                break;
                case GREATER_THAN:
                if (playerFaction > value1)
                {
                    gatedFaction = false;
                }
                break;
                case LESS_THAN_OR_EQUAL:
                if (playerFaction <= value1)
                {
                    gatedFaction = false;
                }
                break;
                case GREATER_THAN_OR_EQUAL:
                if (playerFaction >= value1)
                {
                    gatedFaction = false;
                }
                break;
                case BETWEEN:
                if (playerFaction > value1 && playerFaction < value2)
                {
                    gatedFaction = false;
                }
                break;
                default:
                LOG("dynamic_convo", "WARNING: Unknown Faction gating condition!!");
                break;
            }
            gated |= gatedFaction;
        }
        String gatingObject = entry.getString("gatingObject");
        if (gatingObject != null && !gatingObject.equals(""))
        {
            boolean destroyItem = (entry.getInt("destroyGatingObject") != 0);
            boolean gatedItem = true;
            if (hasItem(player, gatingObject, destroyItem))
            {
                gatedItem = false;
            }
            gated |= gatedItem;
        }
        String gatingPvpFaction = entry.getString("gatingPvpFaction");
        if (gatingPvpFaction != null && !gatingPvpFaction.equals(""))
        {
            String playerFaction = factions.getFaction(player);
            if (playerFaction == null)
            {
                playerFaction = "none";
            }
            boolean gatedPvpFaction = true;
            switch (getPvpFactionId(gatingPvpFaction))
            {
                case NEUTRAL:
                if (factions.isNeutral(player))
                {
                    gatedPvpFaction = false;
                }
                break;
                case REBEL:
                if (playerFaction.equals("Rebel"))
                {
                    gatedPvpFaction = false;
                }
                break;
                case REBEL_OVERT:
                if (playerFaction.equals("Rebel") && factions.isDeclared(player))
                {
                    gatedPvpFaction = false;
                }
                break;
                case REBEL_COVERT:
                if (playerFaction.equals("Rebel") && factions.isCovert(player) && !factions.isDeclared(player))
                {
                    gatedPvpFaction = false;
                }
                break;
                case IMPERIAL:
                if (playerFaction.equals("Imperial"))
                {
                    gatedPvpFaction = false;
                }
                break;
                case IMPERIAL_OVERT:
                if (playerFaction.equals("Imperial") && factions.isDeclared(player))
                {
                    gatedPvpFaction = false;
                }
                break;
                case IMPERIAL_COVERT:
                if (playerFaction.equals("Imperial") && factions.isCovert(player) && !factions.isDeclared(player))
                {
                    gatedPvpFaction = false;
                }
                break;
                default:
                LOG("dynamic_convo", "WARNING: Unknown gating PVP Faction!!");
                break;
            }
            gated |= gatedPvpFaction;
        }
        return gated;
    }
    public int getConditionId(String condition) throws InterruptedException
    {
        for (int i = 0; i < conditionList.length; i++)
        {
            if (condition.equals(conditionList[i]))
            {
                return i;
            }
        }
        return -1;
    }
    public int getActionId(String action) throws InterruptedException
    {
        for (int i = 0; i < actionList.length; i++)
        {
            if (action.equals(actionList[i]))
            {
                return i;
            }
        }
        return -1;
    }
    public int getPvpFactionId(String pvpFaction) throws InterruptedException
    {
        for (int i = 0; i < pvpFactionList.length; i++)
        {
            if (pvpFaction.equals(pvpFactionList[i]))
            {
                return i;
            }
        }
        return -1;
    }
    public boolean hasItem(obj_id player, String template, boolean destroyItem) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        obj_id[] contents = utils.getContents(playerInv, true);
        for (int i = 0; i < contents.length; i++)
        {
            String itemInInventory = getTemplateName(contents[i]);
            if (itemInInventory.equals(template))
            {
                if (destroyItem)
                {
                    destroyObject(contents[i]);
                }
                return true;
            }
        }
        return false;
    }
}

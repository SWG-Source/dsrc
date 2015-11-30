package script.npc.epic_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.locations;
import script.library.money;
import script.library.quests;

public class quest_convo extends script.base_script
{
    public quest_convo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        messageTo(self, "scriptCheck", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/epic_quest/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 2, 1);
        String startingObjVar = dataTableGetString(datatable, 14, 1);
        int gating = getIntObjVar(speaker, gatingString);
        if (gating == 0)
        {
            setObjVar(speaker, gatingString, 1);
            gating = 1;
            setObjVar(speaker, gatingString, gating);
        }
        int maxGating = dataTableGetNumColumns(datatable);
        maxGating = maxGating - 1;
        int questNum = gating;
        String CONVO = dataTableGetString(datatable, 1, questNum);
        String questID = dataTableGetString(datatable, 3, questNum);
        String gatingObject = dataTableGetString(datatable, 12, questNum);
        if (gatingObject == null)
        {
            gatingObject = "none";
        }
        if ((!gatingObject.equals("none")) && (!gatingObject.equals("")))
        {
            obj_id playerInv = utils.getInventoryContainer(speaker);
            if (checkForGatingItem(playerInv, speaker, questNum, datatable) != true)
            {
                string_id rewardMessage = new string_id(CONVO, "notyet");
                chat.chat(self, speaker, rewardMessage);
                return SCRIPT_OVERRIDE;
            }
        }
        if (startingObjVar == null)
        {
            startingObjVar = "none";
        }
        if (!startingObjVar.equals("none") && !startingObjVar.equals(""))
        {
            if (!hasObjVar(speaker, startingObjVar))
            {
                CONVO = dataTableGetString(datatable, 1, 1);
                string_id notyet = new string_id(CONVO, "notyet");
                chat.chat(self, speaker, notyet);
                return SCRIPT_OVERRIDE;
            }
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        else if (gating > maxGating)
        {
            CONVO = dataTableGetString(datatable, 1, 1);
            string_id next = new string_id(CONVO, "next");
            chat.chat(self, speaker, next);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".done"))
        {
            string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
            chat.chat(self, speaker, reward);
            giveReward(self, speaker, questNum);
            return SCRIPT_CONTINUE;
        }
        else if (hasScript(speaker, "npc.epic_quest.quest_player"))
        {
            String npcGreet = "npc_work_" + questNum;
            String response1 = "player_reset_" + questNum;
            String response2 = "player_sorry_" + questNum;
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[2];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[3];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/epic_quest/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 2, 1);
        int gating = getIntObjVar(player, gatingString);
        int questNum = gating;
        String type = dataTableGetString(datatable, 0, questNum);
        String CONVO = dataTableGetString(datatable, 1, questNum);
        String questID = dataTableGetString(datatable, 3, questNum);
        location home = new location();
        obj_id npcOrBldg = getTopMostContainer(self);
        if (npcOrBldg == self)
        {
            home = getLocation(self);
        }
        else 
        {
            home = getLocation(npcOrBldg);
        }
        String response1 = "player_1_" + questNum;
        String response2 = "player_2_" + questNum;
        String response3 = "player_3_" + questNum;
        String response4 = "player_sorry_" + questNum;
        String response5 = "player_reset_" + questNum;
        String playerScript = dataTableGetString(datatable, 8, questNum);
        if ((response.getAsciiId()).equals(response1))
        {
            location target = quests.getThemeParkLocation(self);
            if (target == null)
            {
                String noLoc = "npc_noloc_" + questNum;
                string_id message = new string_id(CONVO, noLoc);
                chat.chat(self, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String npcAnswer1 = "npc_2_" + questNum;
                string_id message = new string_id(CONVO, npcAnswer1);
                npcSpeak(player, message);
                npcEndConversation(player);
                setObjVar(player, questID + ".questLoc", target);
                setObjVar(player, questID + ".questNum", questNum);
                setObjVar(player, questID + ".home", home);
                setObjVar(player, "quest_table", datatable);
                attachScript(player, playerScript);
                if (type.equals("smuggle") || type.equals("deliver"))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    String cargo = dataTableGetString(datatable, 6, questNum);
                    createObject(cargo, playerInv, "");
                }
            }
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response2))
        {
            String npcAnswer2 = "npc_3_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer2);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response3))
        {
            npcAddConversationResponse(player, new string_id(CONVO, response1));
            npcAddConversationResponse(player, new string_id(CONVO, response2));
            npcRemoveConversationResponse(player, new string_id(CONVO, response3));
            String npcAnswer3 = "npc_4_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer3);
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response4))
        {
            String npcAnswer4 = "npc_backtowork";
            string_id message = new string_id(CONVO, npcAnswer4);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response5))
        {
            String npcAnswer5 = "npc_reset_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);
            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void giveReward(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        if (questNum == 0)
        {
            questNum = 1;
        }
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/epic_quest/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, 3, questNum);
        String gatingString = dataTableGetString(datatable, 2, questNum);
        String playerScript = dataTableGetString(datatable, 8, questNum);
        int gating = getIntObjVar(player, gatingString);
        gating = gating + 1;
        setObjVar(player, gatingString, gating);
        String type = dataTableGetString(datatable, 0, questNum);
        if (type.equals("rescue") || type.equals("escort") || type.equals("arrest"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String reward = dataTableGetString(datatable, 4, questNum);
        if (reward.equals("credits"))
        {
            money.bankTo(money.ACCT_JABBA, player, 25);
            sendSystemMessage(player, "25 credits have been deposited in your bank account.", null);
        }
        else 
        {
            createObject(reward, playerInv, "");
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
        }
        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        if (hasScript(player, playerScript))
        {
            detachScript(player, playerScript);
        }
        return;
    }
    public void resetPlayer(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/epic_quest/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, 3, questNum);
        String gatingString = dataTableGetString(datatable, 2, questNum);
        String playerScript = dataTableGetString(datatable, 8, questNum);
        String type = dataTableGetString(datatable, 0, questNum);
        if (type.equals("rescue"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        if (hasScript(player, playerScript))
        {
            detachScript(player, playerScript);
        }
        return;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        String name = getTemplateName(item);
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/epic_quest/" + datatable + ".iff";
        String CONVO = dataTableGetString(datatable, 1, 1);
        String itemNeeded = dataTableGetString(datatable, 12, 1);
        if (name == null || name.equals(""))
        {
            name = "none";
        }
        if (name.equals(itemNeeded))
        {
            String gatingObjVar = dataTableGetString(datatable, 13, 1);
            if (gatingObjVar != null && !gatingObjVar.equals(""))
            {
                setObjVar(player, gatingObjVar, 1);
                if (isIdValid(item))
                {
                    destroyObject(item);
                }
            }
            string_id notyet = new string_id(CONVO, "good");
            chat.chat(self, player, notyet);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id notyet = new string_id(CONVO, "notit");
            chat.chat(self, player, notyet);
            return SCRIPT_CONTINUE;
        }
    }
    public boolean checkForGatingItem(obj_id inv, obj_id player, int questNum, String datatable) throws InterruptedException
    {
        String giveMe = dataTableGetString(datatable, 12, questNum);
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            String itemInInventory = getTemplateName(contents[i]);
            if (itemInInventory.equals(giveMe))
            {
                hadIt = true;
            }
        }
        return hadIt;
    }
    public int scriptCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasScript(self, "npc.static_quest.quest_convo"))
        {
            detachScript(self, "npc.static_quest.quest_convo");
        }
        return SCRIPT_CONTINUE;
    }
}

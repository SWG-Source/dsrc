package script.theme_park;

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
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/theme_park/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 2, 1);
        int gating = getIntObjVar(speaker, gatingString);
        if (gating == 0)
        {
            setObjVar(speaker, gatingString, 1);
        }
        int minGating = getIntObjVar(self, "minGating");
        int maxGating = getIntObjVar(self, "maxGating");
        int questNum = getQuestNumber(gating, minGating);
        String CONVO = dataTableGetString(datatable, 1, questNum);
        String questID = dataTableGetString(datatable, 3, questNum);
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (gating <= minGating)
        {
            CONVO = dataTableGetString(datatable, 1, 1);
            string_id notyet = new string_id(CONVO, "notyet");
            chat.chat(self, notyet);
            return SCRIPT_OVERRIDE;
        }
        else if (gating > maxGating)
        {
            CONVO = dataTableGetString(datatable, 1, 1);
            string_id next = new string_id(CONVO, "next");
            chat.chat(self, next);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".done"))
        {
            string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
            chat.chat(self, reward);
            giveReward(self, speaker, questNum);
            return SCRIPT_CONTINUE;
        }
        else if (hasScript(speaker, "theme_park.quest_player") || hasScript(speaker, "theme_park.quest_bldg"))
        {
            String npcGreet = "npc_work_" + questNum;
            String response1 = "player_reset";
            String response2 = "player_sorry";
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
        datatable = "datatables/theme_park/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 2, 1);
        int gating = getIntObjVar(player, gatingString);
        int minGating = getIntObjVar(self, "minGating");
        int questNum = getQuestNumber(gating, minGating);
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
        String response4 = "player_sorry";
        String response5 = "player_reset";
        String playerScript = dataTableGetString(datatable, 6, questNum);
        if ((response.getAsciiId()).equals(response1))
        {
            location target = quests.getThemeParkLocation(self);
            if (target == null)
            {
                String noLoc = "npc_noloc_" + questNum;
                string_id message = new string_id(CONVO, noLoc);
                chat.chat(self, message);
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
                setObjVar(player, "questNum", questNum);
                setObjVar(player, questID + ".home", home);
                setObjVar(player, "quest_table", datatable);
                attachScript(player, playerScript);
                if (type.equals("smuggle"))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    String cargo = dataTableGetString(datatable, 5, questNum);
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
            String npcAnswer2 = "npc_backtowork";
            string_id message = new string_id(CONVO, npcAnswer2);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response5))
        {
            String npcAnswer2 = "npc_reset";
            string_id message = new string_id(CONVO, npcAnswer2);
            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int getQuestNumber(int gating, int minGating) throws InterruptedException
    {
        int questNum = gating - minGating;
        if (questNum == 0)
        {
            questNum = 1;
        }
        return questNum;
    }
    public void giveReward(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/theme_park/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, 3, questNum);
        String gatingString = dataTableGetString(datatable, 2, questNum);
        String playerScript = dataTableGetString(datatable, 6, questNum);
        int gating = getIntObjVar(player, gatingString);
        gating = gating + 1;
        setObjVar(player, gatingString, gating);
        String type = dataTableGetString(datatable, 0, questNum);
        if (type.equals("rescue"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String reward = dataTableGetString(datatable, 4, questNum);
        float factionReward = questNum * 10;
        if (reward.equals("credits"))
        {
            sendSystemMessage(player, "100 credits have been deposited in your bank account.", null);
            money.bankTo(money.ACCT_JABBA, player, 100);
            factions.addFactionStanding(player, "Hutt", factionReward);
        }
        else 
        {
            sendSystemMessage(player, "Your reward has been placed in your backpack.", null);
            createObject(reward, playerInv, "");
            factions.addFactionStanding(player, "Hutt", factionReward);
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
        datatable = "datatables/theme_park/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, 3, questNum);
        String gatingString = dataTableGetString(datatable, 2, questNum);
        String playerScript = dataTableGetString(datatable, 6, questNum);
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
}

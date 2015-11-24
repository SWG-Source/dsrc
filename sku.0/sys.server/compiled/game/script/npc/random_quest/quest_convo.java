package script.npc.random_quest;

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
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/random_quest/" + datatable + ".iff";
        int maxGating = dataTableGetNumRows(datatable);
        int questNum = 1;
        if (!hasObjVar(speaker, "questNum"))
        {
            questNum = rand(1, maxGating - 1);
            setObjVar(speaker, "questNum", questNum);
        }
        else 
        {
            questNum = getIntObjVar(speaker, "questNum");
            if (questNum > maxGating)
            {
                questNum = rand(1, maxGating - 1);
                setObjVar(speaker, "questNum", questNum);
            }
        }
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".done"))
        {
            if (type.equals("fetch") || type.equals("retrieve"))
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForFetchItem(playerInv, speaker, questNum) == true)
                {
                    string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
                    chat.chat(self, speaker, reward);
                    giveReward(self, speaker, questNum);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String npcGreet = "gotowork";
                    String response1 = "player_reset";
                    String response2 = "player_sorry";
                    string_id greeting = new string_id(CONVO, npcGreet);
                    string_id response[] = new string_id[2];
                    response[0] = new string_id(CONVO, response1);
                    response[1] = new string_id(CONVO, response2);
                    npcStartConversation(speaker, self, "questConvo", greeting, response);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
                chat.chat(self, speaker, reward);
                giveReward(self, speaker, questNum);
                return SCRIPT_CONTINUE;
            }
        }
        else if (isOnQuest(speaker))
        {
            String player_quest_table = getStringObjVar(speaker, "quest_table");
            if (player_quest_table == null)
            {
                player_quest_table = "empty";
            }
            if (player_quest_table.equals(datatable))
            {
                String npcGreet = "gotowork";
                String response1 = "player_reset";
                String response2 = "player_sorry";
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[2];
                response[0] = new string_id(CONVO, response1);
                response[1] = new string_id(CONVO, response2);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            else 
            {
                String npcGreet = "quit_quest";
                String response1 = "player_quit";
                String response2 = "player_continue";
                string_id greeting = new string_id("static_npc/default_dialog", npcGreet);
                string_id response[] = new string_id[2];
                response[0] = new string_id("static_npc/default_dialog", response1);
                response[1] = new string_id("static_npc/default_dialog", response2);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
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
        datatable = "datatables/npc/random_quest/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(player, "questNum");
        int questNum = gating;
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
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
        String response6 = "player_quit";
        String response7 = "player_continue";
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        if ((response.getAsciiId()).equals(response1))
        {
            location target = quests.getThemeParkLocation(self);
            if (target == null)
            {
                String noLoc = "npc_noloc_" + questNum;
                string_id message = new string_id(CONVO, noLoc);
                chat.chat(self, player, message);
                npcEndConversation(player);
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                String npcAnswer1 = "npc_2_" + questNum;
                string_id message = new string_id(CONVO, npcAnswer1);
                npcSpeak(player, message);
                npcEndConversation(player);
                if (questID != null && !questID.equals(""))
                {
                    setObjVar(player, questID + ".questLoc", target);
                    setObjVar(player, questID + ".questNum", questNum);
                    setObjVar(player, questID + ".home", home);
                    setObjVar(player, "quest_table", datatable);
                    setObjVar(player, "boss", self);
                    attachScript(player, playerScript);
                }
                else 
                {
                    debugSpeakMsg(self, "I'm sorry, I don't really have anything for you right now...");
                    return SCRIPT_OVERRIDE;
                }
                if (type.equals("smuggle") || type.equals("deliver"))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    String cargo = dataTableGetString(datatable, questNum, "deliver_object");
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
            String npcAnswer5 = "npc_reset";
            string_id message = new string_id(CONVO, npcAnswer5);
            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response6))
        {
            String npcAnswer6 = "npc_quit";
            string_id message = new string_id("static_npc/default_dialog", npcAnswer6);
            npcSpeak(player, message);
            resetOtherQuest(self, player);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response7))
        {
            String npcAnswer7 = "npc_continue";
            string_id message = new string_id("static_npc/default_dialog", npcAnswer7);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void giveReward(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/random_quest/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("rescue") || type.equals("escort") || type.equals("arrest"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 2, true);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String reward = dataTableGetString(datatable, questNum, "reward");
        if (reward == null)
        {
            reward = "none";
        }
        int credits = dataTableGetInt(datatable, questNum, "credits");
        if (credits != 0)
        {
            money.bankTo(money.ACCT_JABBA, player, 25);
            sendSystemMessage(player, "25 credits have been deposited in your bank account.", null);
        }
        if (!reward.equals("none"))
        {
            createObject(reward, playerInv, "");
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
        }
        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, self);
        }
        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        removeObjVar(player, "boss");
        if (hasScript(player, playerScript))
        {
            detachScript(player, playerScript);
        }
        return;
    }
    public void resetPlayer(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/npc/random_quest/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type == null)
        {
            return;
        }
        if (type.equals("rescue") || type.equals("escort") || type.equals("arrest"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 2, true);
        }
        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        removeObjVar(player, "boss");
        if (hasScript(player, playerScript))
        {
            detachScript(player, playerScript);
        }
        return;
    }
    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, questNum, "deliver_object");
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            String itemInInventory = getTemplateName(contents[i]);
            if (itemInInventory.equals(giveMe))
            {
                destroyObject(contents[i]);
                hadIt = true;
            }
        }
        return hadIt;
    }
    public boolean checkForFetchItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, questNum, "retrieve_object");
        boolean hadIt = false;
        obj_id[] contents = getContents(inv);
        for (int i = 0; i < contents.length; i++)
        {
            String itemInInventory = getTemplateName(contents[i]);
            if (itemInInventory.equals(giveMe))
            {
                destroyObject(contents[i]);
                hadIt = true;
            }
        }
        return hadIt;
    }
    public boolean isOnQuest(obj_id player) throws InterruptedException
    {
        if (hasScript(player, "npc.static_quest.quest_player"))
        {
            return true;
        }
        if (hasScript(player, "npc.random_quest.quest_player"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.imperial.quest_player"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.rebel.quest_player"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.jabba.quest_player"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.jabba.quest_bldg"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.jabba.quest_rantok"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.rebel.quest_bldg"))
        {
            return true;
        }
        if (hasScript(player, "theme_park.imperial.quest_bldg"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void resetOtherQuest(obj_id self, obj_id player) throws InterruptedException
    {
        String datatable = "datatables/npc/static_quest/all_quest_names.iff";
        String[] questIDs = dataTableGetStringColumnNoDefaults(datatable, "quest_ids");
        if (questIDs == null)
        {
            return;
        }
        for (int i = 0; i < questIDs.length; ++i)
        {
            if (hasObjVar(player, questIDs[i] + ".waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(player, questIDs[i] + ".waypoint");
                if (waypoint != null)
                {
                    destroyWaypointInDatapad(waypoint, player);
                }
            }
            if (hasObjVar(player, questIDs[i] + ".escort"))
            {
                obj_id vip = getObjIdObjVar(player, questIDs[i] + ".escort");
                messageTo(vip, "stopFollowing", null, 2, true);
            }
            if (hasObjVar(player, questIDs[i]))
            {
                removeObjVar(player, questIDs[i]);
            }
        }
        String[] questScripts = dataTableGetStringColumnNoDefaults(datatable, "quest_scripts");
        if (questScripts == null)
        {
            return;
        }
        for (int x = 0; x < questScripts.length; ++x)
        {
            if (hasScript(player, questScripts[x]))
            {
                detachScript(player, questScripts[x]);
            }
        }
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
        return;
    }
}

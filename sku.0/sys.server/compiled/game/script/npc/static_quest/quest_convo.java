package script.npc.static_quest;

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
import script.library.skill;
import script.library.weapons;

public class quest_convo extends script.base_script
{
    public quest_convo()
    {
    }
    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";
    public static final String[] NPC_DEACTIVATE_LIST = 
    {
        "tato_valarian_kavas_urdano",
        "tato_valarian_ind",
        "hedon_istee",
        "igbi_freemo"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String exclusion = getStringObjVar(self, "quest_table");
        if (exclusion == null)
        {
            exclusion = "";
        }
        if (NPC_DEACTIVATE_LIST != null && NPC_DEACTIVATE_LIST.length > 0)
        {
            for (int i = 0; i < NPC_DEACTIVATE_LIST.length; ++i)
            {
                if (exclusion.equals(NPC_DEACTIVATE_LIST[i]))
                {
                    detachScript(self, "npc.static_quest.quest_convo");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        messageTo(self, "handleStaticQuestNpcAttach", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStaticQuestNpcAttach(obj_id self, dictionary params) throws InterruptedException
    {
        String creatureName = getCreatureName(self);
        if (creatureName == null || creatureName.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (creatureName.startsWith("vana_sage") || creatureName.startsWith("tekil_barje") || creatureName.startsWith("ikka_gesul"))
        {
            detachScript(self, "npc.static_quest.quest_convo");
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, "conversation.faction_recruiter_imperial") || hasScript(self, "conversation.faction_recruiter_rebel"))
        {
            detachScript(self, "npc.static_quest.quest_convo");
            return SCRIPT_CONTINUE;
        }
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        String exclusion = datatable;
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(speaker, gatingString);
        if (gating == 0)
        {
            boolean noMatch = true;
            if (NPC_DEACTIVATE_LIST != null && NPC_DEACTIVATE_LIST.length > 0)
            {
                for (int i = 0; i < NPC_DEACTIVATE_LIST.length; i++)
                {
                    if (exclusion.equals(NPC_DEACTIVATE_LIST[i]))
                    {
                        noMatch = false;
                    }
                }
            }
            if (noMatch)
            {
                setObjVar(speaker, gatingString, 1);
                gating = 1;
            }
        }
        int maxGating = dataTableGetNumRows(datatable);
        maxGating = maxGating - 1;
        int questNum = gating;
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String ALT_CONVO = "static_npc/default_dialog";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String convoType = dataTableGetString(datatable, questNum, "quest_giver_convo");
        String questType = dataTableGetString(datatable, questNum, "quest_type");
        if (questNum == 0)
        {
            string_id deactivated = new string_id(ALT_CONVO, "deactivated");
            chat.chat(self, speaker, deactivated);
            return SCRIPT_CONTINUE;
        }
        if (dataTableHasColumn(datatable, "opposing_objvar"))
        {
            String opposing = dataTableGetString(datatable, questNum, "opposing_objvar");
            if (hasObjVar(speaker, opposing))
            {
                int value = getIntObjVar(speaker, opposing);
                if (value > 1)
                {
                    string_id leave = new string_id(CONVO, "go_away");
                    chat.chat(self, speaker, leave);
                    npcEndConversation(speaker);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (gating > maxGating)
        {
            CONVO = dataTableGetString(datatable, 1, "convo");
            string_id next = new string_id(CONVO, "next");
            chat.chat(self, speaker, next);
            return SCRIPT_OVERRIDE;
        }
        if (dataTableHasColumn(datatable, "gating_faction"))
        {
            String gatingFaction = dataTableGetString(datatable, questNum, "gating_faction");
            if (gatingFaction == null)
            {
                gatingFaction = "none";
            }
            String faction = factions.getFaction(speaker);
            if (gatingFaction.equals("Rebel") || gatingFaction.equals("Imperial"))
            {
                if (gatingFaction.equals("Imperial") && !factions.isImperial(speaker))
                {
                    string_id notyet = new string_id(CONVO, "notyet");
                    chat.chat(self, speaker, notyet);
                    return SCRIPT_OVERRIDE;
                }
                else if (gatingFaction.equals("Rebel") && !factions.isRebel(speaker))
                {
                    string_id notyet = new string_id(CONVO, "notyet");
                    chat.chat(self, speaker, notyet);
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    if (factions.isOnLeave(speaker))
                    {
                        string_id notyet = new string_id(CONVO, "notyet");
                        chat.chat(self, speaker, notyet);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
            else 
            {
                if (!gatingFaction.equals("none"))
                {
                    float playerFaction = getFloatObjVar(speaker, "faction." + gatingFaction);
                    int neededFaction = dataTableGetInt(datatable, questNum, "gating_faction_amt");
                    if (playerFaction < neededFaction)
                    {
                        string_id notyet = new string_id(CONVO, "notyet");
                        chat.chat(self, speaker, notyet);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }
        if (dataTableHasColumn(datatable, "gating_jedi"))
        {
            String gatingJedi = dataTableGetString(datatable, questNum, "gating_jedi");
            if (gatingJedi == null)
            {
                gatingJedi = "none";
            }
            if (gatingJedi.equals("yes"))
            {
                if (isJedi(speaker))
                {
                    string_id jedi = new string_id(CONVO, "jedi");
                    chat.chat(self, speaker, jedi);
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        if (dataTableHasColumn(datatable, "gating_object"))
        {
            String gatingObject = dataTableGetString(datatable, questNum, "gating_object");
            if (gatingObject == null)
            {
                gatingObject = "none";
            }
            if (!gatingObject.equals("none"))
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForGatingItem(playerInv, speaker, questNum, datatable) != true)
                {
                    string_id rewardMessage = new string_id(CONVO, "notyet");
                    chat.chat(self, speaker, rewardMessage);
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        String gatingObjVar = dataTableGetString(datatable, 1, "gating_objvar");
        if (gatingObjVar != null)
        {
            if (!gatingObjVar.equals("") && !gatingObjVar.equals("none"))
            {
                if (!hasObjVar(speaker, gatingObjVar))
                {
                    string_id notyet = new string_id(CONVO, "notyet");
                    chat.chat(self, speaker, notyet);
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    int gatingAmt = dataTableGetInt(datatable, questNum, "gating_objvar_amt");
                    int playerGate = getIntObjVar(speaker, gatingObjVar);
                    if (playerGate < gatingAmt)
                    {
                        string_id notyet = new string_id(CONVO, "notyet");
                        chat.chat(self, speaker, notyet);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }
        if (convoType == null)
        {
            debugSpeakMsg(self, "For some reason I'm stuck with nothing to say.");
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        else if (questNum > maxGating)
        {
            string_id next = new string_id(CONVO, "next");
            chat.chat(self, speaker, next);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".failed"))
        {
            string_id failMessage = new string_id(CONVO, "npc_failure_" + questNum);
            String failSafe = getString(failMessage);
            if (failSafe == null || failSafe.equals(""))
            {
                failMessage = new string_id(CONVO, "npc_failure");
            }
            chat.chat(self, speaker, failMessage);
            missionFailure(self, speaker, questNum);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".noloc"))
        {
            string_id noLocMessage = new string_id(CONVO, "npc_noloc_" + questNum);
            chat.chat(self, speaker, noLocMessage);
            resetPlayer(self, speaker, questNum);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".done"))
        {
            if (questType.equals("fetch") || questType.equals("retrieve"))
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForItem(playerInv, speaker, questNum) == true)
                {
                    string_id rewardMessage = new string_id(CONVO, "npc_reward_" + questNum);
                    chat.chat(self, speaker, rewardMessage);
                    obj_id retrieveObject = getObjIdObjVar(speaker, questID + ".deliver");
                    destroyObject(retrieveObject);
                    giveReward(self, speaker, questNum);
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    String notit = "notit_" + questNum;
                    String response1 = "player_reset_" + questNum;
                    String response2 = "player_sorry_" + questNum;
                    string_id greeting = new string_id(CONVO, notit);
                    string_id response[] = new string_id[2];
                    response[0] = new string_id(CONVO, response1);
                    String answer = getString(response[0]);
                    if (answer == null || answer.equals(""))
                    {
                        response1 = "player_reset";
                        response[0] = new string_id(CONVO, response1);
                    }
                    response[1] = new string_id(CONVO, response2);
                    String answer2 = getString(response[0]);
                    if (answer == null || answer.equals(""))
                    {
                        response2 = "player_sorry";
                        response[1] = new string_id(CONVO, response2);
                    }
                    npcStartConversation(speaker, self, "questConvo", greeting, response);
                    return SCRIPT_CONTINUE;
                }
            }
            else if (questType.equals("escort") || questType.equals("arrest") || questType.equals("rescue"))
            {
                location alaska = getLocation(self);
                obj_id[] creatures = getObjectsInRange(alaska, 20);
                obj_id theVip = getObjIdObjVar(speaker, questID + ".vip");
                boolean goodEscort = false;
                int numCreature = creatures.length;
                int x = 0;
                while (x < numCreature)
                {
                    if (creatures[x] != null)
                    {
                        obj_id thisOne = creatures[x];
                        if (theVip == thisOne)
                        {
                            goodEscort = true;
                        }
                        x = x + 1;
                    }
                }
                if (goodEscort = true)
                {
                    string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
                    chat.chat(self, speaker, reward);
                    giveReward(self, speaker, questNum);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    string_id didnt = new string_id(CONVO, "npc_failure_" + questNum);
                    chat.chat(self, speaker, didnt);
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
                String npcGreet = "npc_work_" + questNum;
                String response1 = "player_reset_" + questNum;
                String response2 = "player_sorry_" + questNum;
                string_id greeting = new string_id(CONVO, npcGreet);
                string_id response[] = new string_id[2];
                response[0] = new string_id(CONVO, response1);
                String answer = getString(response[0]);
                if (answer == null || answer.equals(""))
                {
                    response1 = "player_reset";
                    response[0] = new string_id(CONVO, response1);
                }
                response[1] = new string_id(CONVO, response2);
                String answer2 = getString(response[0]);
                if (answer == null || answer.equals(""))
                {
                    response2 = "player_sorry";
                    response[1] = new string_id(CONVO, response2);
                }
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
        else if (dataTableHasColumn(datatable, "gating_level_amt"))
        {
            int playerLevel = getLevel(speaker);
            int npcLevel = dataTableGetInt(datatable, questNum, "gating_level_amt");
            if (playerLevel < (npcLevel))
            {
                String tooWeak = "too_weak";
                string_id greeting = new string_id(ALT_CONVO, tooWeak);
                chat.chat(self, speaker, greeting);
            }
            else if (playerLevel > (npcLevel + 5))
            {
                String tooStrong = "too_strong";
                String response1 = "its_ok";
                string_id greeting = new string_id(ALT_CONVO, tooStrong);
                string_id response[] = new string_id[1];
                response[0] = new string_id(ALT_CONVO, response1);
                npcStartConversation(speaker, self, "questConvo", greeting, response);
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
        }
        else if (convoType.equals("terse"))
        {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[2];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        }
        else if (convoType.equals("long"))
        {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;
            String response4 = "player_4_" + questNum;
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[4];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            response[3] = new string_id(CONVO, response4);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        }
        else if (convoType.equals("verbose"))
        {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;
            String response4 = "player_4_" + questNum;
            String response5 = "player_5_" + questNum;
            string_id greeting = new string_id(CONVO, npcGreet);
            string_id response[] = new string_id[5];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            response[3] = new string_id(CONVO, response4);
            response[4] = new string_id(CONVO, response5);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
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
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(player, gatingString);
        int questNum = gating;
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String ALT_CONVO = "static_npc/default_dialog";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String convoStyle = dataTableGetString(datatable, questNum, "quest_giver_convo");
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
        String response4 = "player_4_" + questNum;
        String response5 = "player_5_" + questNum;
        String response6 = "player_sorry_" + questNum;
        String response7 = "player_reset_" + questNum;
        String response8 = "player_quit";
        String response9 = "player_continue";
        String response10 = "player_sorry";
        String response11 = "player_reset";
        String response12 = "its_ok";
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        if ((response.getAsciiId()).equals(response1))
        {
            int xCoord = dataTableGetInt(datatable, questNum, "quest_x_loc");
            if (xCoord != 0)
            {
                int yCoord = dataTableGetInt(datatable, questNum, "quest_y_loc");
                int zCoord = dataTableGetInt(datatable, questNum, "quest_z_loc");
                String planet = dataTableGetString(datatable, questNum, "quest_planet_loc");
                location questLoc = new location(xCoord, yCoord, zCoord, planet, null);
                String npcAnswer1 = "npc_2_" + questNum;
                string_id message = new string_id(CONVO, npcAnswer1);
                npcSpeak(player, message);
                npcEndConversation(player);
                if (questID != null && !questID.equals(""))
                {
                    setObjVar(player, questID + ".questLoc", questLoc);
                    setObjVar(player, questID + ".questNum", questNum);
                    setObjVar(player, questID + ".home", home);
                    setObjVar(player, "quest_table", datatable);
                    attachScript(player, playerScript);
                }
                else 
                {
                    debugSpeakMsg(self, "Sorry, I can't give you a job right now...");
                    return SCRIPT_OVERRIDE;
                }
                if (type.equals("smuggle") || type.equals("deliver"))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    String itemToDeliver = dataTableGetString(datatable, questNum, "deliver_object");
                    obj_id cargo = createObjectOverloaded(itemToDeliver, playerInv);
                    if (!isIdValid(cargo))
                    {
                        destroyObject(cargo);
                        string_id come_back = new string_id("generic_convo", "inv_full");
                        npcSpeak(player, come_back);
                        npcEndConversation(player);
                        resetPlayer(self, player, questNum);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
            else 
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
                    if (questID != null && !questID.equals(""))
                    {
                        setObjVar(player, questID + ".questLoc", target);
                        setObjVar(player, questID + ".questNum", questNum);
                        setObjVar(player, questID + ".home", home);
                        setObjVar(player, "quest_table", datatable);
                        attachScript(player, playerScript);
                    }
                    else 
                    {
                        debugSpeakMsg(self, "Sorry, I can't give you a job right now...");
                        return SCRIPT_OVERRIDE;
                    }
                    if (type.equals("smuggle") || type.equals("deliver"))
                    {
                        obj_id playerInv = utils.getInventoryContainer(player);
                        String itemToDeliver = dataTableGetString(datatable, questNum, "deliver_object");
                        obj_id cargo = createObjectOverloaded(itemToDeliver, playerInv);
                        if (!isIdValid(cargo))
                        {
                            destroyObject(cargo);
                            string_id come_back = new string_id("generic_convo", "inv_full");
                            npcSpeak(player, come_back);
                            npcEndConversation(player);
                            resetPlayer(self, player, questNum);
                            return SCRIPT_OVERRIDE;
                        }
                    }
                }
                String npcAnswer1 = "npc_2_" + questNum;
                string_id message = new string_id(CONVO, npcAnswer1);
                npcSpeak(player, message);
                npcEndConversation(player);
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
            npcRemoveConversationResponse(player, new string_id(CONVO, response4));
            String npcAnswer4 = "npc_5_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer4);
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response5))
        {
            npcRemoveConversationResponse(player, new string_id(CONVO, response5));
            String npcAnswer5 = "npc_6_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response6))
        {
            String npcAnswer4 = "npc_backtowork_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer4);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response7))
        {
            String npcAnswer5 = "npc_reset_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);
            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response8))
        {
            String npcAnswer6 = "npc_quit";
            string_id message = new string_id("static_npc/default_dialog", npcAnswer6);
            npcSpeak(player, message);
            resetOtherQuest(self, player);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response9))
        {
            String npcAnswer7 = "npc_continue";
            string_id message = new string_id("static_npc/default_dialog", npcAnswer7);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response10))
        {
            String npcAnswer4 = "npc_backtowork_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer4);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response11))
        {
            String npcAnswer5 = "npc_reset_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);
            String answer = getString(message);
            if (answer == null || answer.equals(""))
            {
                npcAnswer5 = "npc_reset";
                message = new string_id(CONVO, npcAnswer5);
            }
            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals(response12))
        {
            String convoType = dataTableGetString(datatable, questNum, "quest_giver_convo");
            if (convoType.equals("terse"))
            {
                string_id message = new string_id(CONVO, "npc_1_" + questNum);
                npcSpeak(player, message);
                npcRemoveConversationResponse(player, new string_id(ALT_CONVO, response12));
                npcAddConversationResponse(player, new string_id(CONVO, "player_1_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_2_" + questNum));
                return SCRIPT_CONTINUE;
            }
            else if (convoType.equals("long"))
            {
                string_id message = new string_id(CONVO, "npc_1_" + questNum);
                npcSpeak(player, message);
                npcRemoveConversationResponse(player, new string_id(ALT_CONVO, response12));
                npcAddConversationResponse(player, new string_id(CONVO, "player_1_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_2_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_3_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_4_" + questNum));
                return SCRIPT_CONTINUE;
            }
            else if (convoType.equals("verbose"))
            {
                string_id message = new string_id(CONVO, "npc_1_" + questNum);
                npcSpeak(player, message);
                npcRemoveConversationResponse(player, new string_id(ALT_CONVO, response12));
                npcAddConversationResponse(player, new string_id(CONVO, "player_1_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_2_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_3_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_4_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_5_" + questNum));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id message = new string_id(CONVO, "npc_1_" + questNum);
                npcSpeak(player, message);
                npcRemoveConversationResponse(player, new string_id(ALT_CONVO, response12));
                npcAddConversationResponse(player, new string_id(CONVO, "player_1_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_2_" + questNum));
                npcAddConversationResponse(player, new string_id(CONVO, "player_3_" + questNum));
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void giveReward(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        int gating = getIntObjVar(player, gatingString);
        gating = gating + 1;
        setObjVar(player, gatingString, gating);
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("arrest") || type.equals("escort") || type.equals("rescue"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String reward = dataTableGetString(datatable, questNum, "reward");
        String reward2 = dataTableGetString(datatable, questNum, "reward2");
        String reward3 = dataTableGetString(datatable, questNum, "reward3");
        String reward4 = dataTableGetString(datatable, questNum, "reward4");
        if (reward != null && !reward.equals("") && !reward.equals("none"))
        {
            obj_id rewardObject = null;
            if (reward.startsWith("object/weapon/"))
            {
                rewardObject = weapons.createWeapon(reward, playerInv, rand(0.5f, 0.85f));
            }
            else 
            {
                rewardObject = createObjectOverloaded(reward, playerInv);
            }
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar = dataTableGetString(datatable, questNum, "reward_objvar");
            int value = dataTableGetInt(datatable, questNum, "reward_objvar_value");
            if (objvar != null)
            {
                setObjVar(rewardObject, objvar, value);
            }
        }
        if (reward2 != null && !reward2.equals("") && !reward2.equals("none"))
        {
            obj_id rewardObject2 = null;
            if (reward.startsWith("object/weapon/"))
            {
                rewardObject2 = weapons.createWeapon(reward2, playerInv, rand(0.5f, 0.85f));
            }
            else 
            {
                rewardObject2 = createObjectOverloaded(reward2, playerInv);
            }
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar2 = dataTableGetString(datatable, questNum, "reward2_objvar");
            int value2 = dataTableGetInt(datatable, questNum, "reward2_objvar_value");
            if (objvar2 != null)
            {
                setObjVar(rewardObject2, objvar2, value2);
            }
        }
        if (reward3 != null && !reward3.equals("") && !reward3.equals("none"))
        {
            obj_id rewardObject3 = null;
            if (reward.startsWith("object/weapon/"))
            {
                rewardObject3 = weapons.createWeapon(reward3, playerInv, rand(0.5f, 0.85f));
            }
            else 
            {
                rewardObject3 = createObjectOverloaded(reward3, playerInv);
            }
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar3 = dataTableGetString(datatable, questNum, "reward3_objvar");
            int value3 = dataTableGetInt(datatable, questNum, "reward3_objvar_value");
            if (objvar3 != null)
            {
                setObjVar(rewardObject3, objvar3, value3);
            }
        }
        if (reward4 != null && !reward4.equals("") && !reward4.equals("none"))
        {
            obj_id rewardObject4 = null;
            if (reward.startsWith("object/weapon/"))
            {
                rewardObject4 = weapons.createWeapon(reward4, playerInv, rand(0.5f, 0.85f));
            }
            else 
            {
                rewardObject4 = createObjectOverloaded(reward4, playerInv);
            }
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
            String objvar4 = dataTableGetString(datatable, questNum, "reward4_objvar");
            int value4 = dataTableGetInt(datatable, questNum, "reward4_objvar_value");
            if (objvar4 != null)
            {
                setObjVar(rewardObject4, objvar4, value4);
            }
        }
        int credits = dataTableGetInt(datatable, questNum, "credits");
        if (credits != 0)
        {
            money.bankTo(money.ACCT_JABBA, player, credits);
            sendSystemMessage(player, credits + " credits have been deposited in your bank account.", null);
        }
        String factionReward = dataTableGetString(datatable, questNum, "faction reward");
        String factionReward2 = dataTableGetString(datatable, questNum, "faction_reward2");
        String factionReward3 = dataTableGetString(datatable, questNum, "faction_reward3");
        String factionReward4 = dataTableGetString(datatable, questNum, "faction_reward4");
        if (!factionReward.equals("none"))
        {
            int factionAmt = dataTableGetInt(datatable, questNum, "faction_reward_amount");
            if (factionAmt != 0)
            {
                factions.addFactionStanding(player, factionReward, factionAmt);
            }
        }
        if (!factionReward2.equals("none"))
        {
            int factionAmt2 = dataTableGetInt(datatable, questNum, "faction_reward2_amount");
            if (factionAmt2 != 0)
            {
                factions.addFactionStanding(player, factionReward2, factionAmt2);
            }
        }
        if (!factionReward3.equals("none"))
        {
            int factionAmt3 = dataTableGetInt(datatable, questNum, "faction_reward3_amount");
            if (factionAmt3 != 0)
            {
                factions.addFactionStanding(player, factionReward3, factionAmt3);
            }
        }
        if (!factionReward4.equals("none"))
        {
            int factionAmt4 = dataTableGetInt(datatable, questNum, "faction_reward4_amount");
            if (factionAmt4 != 0)
            {
                factions.addFactionStanding(player, factionReward4, factionAmt4);
            }
        }
        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
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
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        obj_id waypoint = getObjIdObjVar(player, questID + ".waypoint");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("escort") || type.equals("arrest"))
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
    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, questNum, "retrieve_object");
        boolean hadIt = false;
        hadIt = utils.playerHasItemByTemplate(player, giveMe);
        return hadIt;
    }
    public boolean checkForGatingItem(obj_id inv, obj_id player, int questNum, String datatable) throws InterruptedException
    {
        String giveMe = dataTableGetString(datatable, questNum, "gating_object");
        boolean hadIt = false;
        hadIt = utils.playerHasItemByTemplate(player, giveMe);
        return hadIt;
    }
    public void missionFailure(obj_id self, obj_id player, int questNum) throws InterruptedException
    {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("escort") || type.equals("arrest"))
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
                obj_id waypointhome = getObjIdObjVar(player, questIDs[i] + ".waypointhome");
                if (waypointhome != null)
                {
                    destroyWaypointInDatapad(waypointhome, player);
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
}

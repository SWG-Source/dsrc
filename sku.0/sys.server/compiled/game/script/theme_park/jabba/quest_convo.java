package script.theme_park.jabba;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.group;
import script.library.factions;
import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.locations;
import script.library.money;
import script.library.quests;
import script.library.badge;
import script.library.prose;
import script.library.weapons;

public class quest_convo extends script.base_script
{
    public quest_convo()
    {
    }
    public static final String REWARD_TABLE = "datatables/theme_park/item_reward.iff";
    public static final String MESSAGE_FILE = "theme_park/messages";
    public static final int REWARD_TYPE_NONE = 0;
    public static final int REWARD_TYPE_FOOD = 1;
    public static final int REWARD_TYPE_COMPONENT = 2;
    public static final int REWARD_TYPE_COMPONENT_ARMOR = 3;
    public static final int REWARD_TYPE_FURNITURE = 4;
    public static final int REWARD_TYPE_CLOTHING = 5;
    public static final int REWARD_TYPE_ARMOR = 6;
    public static final int REWARD_TYPE_WEAPON = 7;
    public static final int REWARD_TYPE_SCHEMATIC = 8;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        faceToBehavior(self, speaker);
        setLookAtTarget(self, speaker);
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/theme_park/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(speaker, gatingString);
        if (gating == 0)
        {
            setObjVar(speaker, gatingString, 1);
            gating = 1;
        }
        if (gating <= 1)
        {
            if (!hasObjVar(speaker, "theme_park_reset.jabba"))
            {
                setObjVar(speaker, "theme_park_reset.jabba", true);
            }
        }
        else 
        {
            if (!hasObjVar(speaker, "theme_park_reset.jabba"))
            {
                string_id needReset = new string_id(MESSAGE_FILE, "need_reset_jabba");
                chat.chat(self, needReset);
                return SCRIPT_CONTINUE;
            }
        }
        int minGating = getIntObjVar(self, "minGating");
        int maxGating = getIntObjVar(self, "maxGating");
        int questNum = getQuestNumber(gating, minGating);
        float jabbaFaction = getFloatObjVar(speaker, "faction.jabba");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (gating <= minGating)
        {
            CONVO = dataTableGetString(datatable, 1, "convo");
            string_id notyet = new string_id(CONVO, "notyet");
            chat.chat(self, notyet);
            return SCRIPT_OVERRIDE;
        }
        else if (jabbaFaction < 0)
        {
            String alternateConvo = "theme_park/messages";
            string_id noFaction = new string_id(alternateConvo, "no_faction");
            chat.chat(self, noFaction);
            return SCRIPT_OVERRIDE;
        }
        else if (gating > maxGating)
        {
            CONVO = dataTableGetString(datatable, 1, "convo");
            string_id next = new string_id(CONVO, "next");
            chat.chat(self, next);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".failed"))
        {
            string_id failMessage = new string_id(CONVO, "npc_failure_" + questNum);
            chat.chat(self, failMessage);
            resetPlayer(self, speaker, questNum);
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(speaker, questID + ".done"))
        {
            String type = dataTableGetString(datatable, questNum, "quest_type");
            if (type.equals("fetch") || type.equals("retrieve"))
            {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (!checkForItem(playerInv, speaker, questNum) == true)
                {
                    string_id work = new string_id(CONVO, "notit");
                    String doesntHaveItem = getString(work);
                    if (doesntHaveItem == null || doesntHaveItem.equals(""))
                    {
                        doesntHaveItem = "notit_" + questNum;
                        work = new string_id(CONVO, doesntHaveItem);
                    }
                    string_id response[] = new string_id[2];
                    String response1 = "player_reset";
                    String response2 = "player_sorry";
                    response[0] = new string_id(CONVO, response1);
                    response[1] = new string_id(CONVO, response2);
                    npcStartConversation(speaker, self, "questConvo", work, response);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
                    chat.chat(self, reward);
                    giveReward(self, speaker, questNum);
                    npcEndConversation(speaker);
                }
            }
            else 
            {
                string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
                chat.chat(self, reward);
                giveReward(self, speaker, questNum);
                npcEndConversation(speaker);
            }
            return SCRIPT_CONTINUE;
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
        datatable = "datatables/theme_park/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(player, gatingString);
        int minGating = getIntObjVar(self, "minGating");
        int questNum = getQuestNumber(gating, minGating);
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        location home = new location(-5861, 0, -6196, "tatooine", null);
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
                if (questID != null && !questID.equals(""))
                {
                    setObjVar(player, questID + ".questLoc", target);
                    setObjVar(player, questID + ".questNum", questNum);
                    setObjVar(player, "questNum", questNum);
                    setObjVar(player, questID + ".home", home);
                    setObjVar(player, "quest_table", datatable);
                }
                else 
                {
                    debugSpeakMsg(self, "I can't give you a job right now...");
                    return SCRIPT_OVERRIDE;
                }
                if (hasScript(player, playerScript))
                {
                    detachScript(player, playerScript);
                }
                attachScript(player, playerScript);
                if (type.equals("smuggle") || type.equals("deliver"))
                {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    String cargo = dataTableGetString(datatable, questNum, "deliver_object");
                    if (cargo == null)
                    {
                        cargo = "none";
                    }
                    if (!cargo.equals("none"))
                    {
                        weapons.createPossibleWeapon(cargo, playerInv, rand(0.5f, 0.85f));
                    }
                    else 
                    {
                        debugSpeakMsg(self, "I don't seem to have that item I was going to give you...");
                        return SCRIPT_OVERRIDE;
                    }
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
        String npc_name = getStringObjVar(self, "quest_table");
        String datatable = "datatables/theme_park/" + npc_name + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        int badgeReward = dataTableGetInt(datatable, questNum, "badge_reward");
        String badgeName = getCollectionSlotName(badgeReward);
        if ((badgeReward != 0) && (badgeName != null) && (badgeName.length() > 0))
        {
            if (!badge.hasBadge(player, badgeName))
            {
                badge.grantBadge(player, badgeName);
            }
        }
        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("rescue") || type.equals("arrest") || type.equals("escort"))
        {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        String oldReward = dataTableGetString(datatable, questNum, "reward");
        int rewardRow = -1;
        int numRewards = 0;
        String npc = "";
        if (oldReward != null && !oldReward.equals("") && !oldReward.equals("none"))
        {
            for (int i = 0; i < dataTableGetNumRows(REWARD_TABLE); i++)
            {
                npc = dataTableGetString(REWARD_TABLE, i, "npc");
                if (npc.equals(npc_name))
                {
                    if (rewardRow < 0)
                    {
                        rewardRow = i;
                    }
                    numRewards++;
                }
            }
            if (numRewards <= 0)
            {
                string_id rewardMessage = new string_id(MESSAGE_FILE, "theme_park_reward");
                sendSystemMessage(player, rewardMessage);
                weapons.createPossibleWeapon(oldReward, playerInv, rand(0.5f, 0.85f));
            }
            else 
            {
                int randomRow = 0;
                if (numRewards > 1)
                {
                    randomRow = rand(0, (numRewards - 1));
                }
                rewardRow += randomRow;
                dictionary reward = dataTableGetRow(REWARD_TABLE, rewardRow);
                String itemTemplateStr;
                String itemTemplate;
                String itemTypeStr;
                int itemType;
                String objvar_name;
                String objvar_value;
                obj_id item;
                obj_id copy_item;
                int stack_size;
                int j;
                int k;
                int n;
                for (j = 1; j <= 2; j++)
                {
                    itemTemplate = reward.getString("reward" + j);
                    if (itemTemplate != null && !itemTemplate.equals(""))
                    {
                        item = weapons.createPossibleWeapon(itemTemplate, playerInv, rand(0.5f, 0.85f));
                        if (isIdValid(item))
                        {
                            string_id rewardMessage = new string_id(MESSAGE_FILE, "theme_park_reward");
                            sendSystemMessage(player, rewardMessage);
                            itemType = reward.getInt("r" + j + "_type");
                            if (itemType == REWARD_TYPE_COMPONENT)
                            {
                                for (k = 1; k <= 8; k++)
                                {
                                    objvar_name = reward.getString("r" + j + "_objvar" + k);
                                    objvar_value = reward.getString("r" + j + "_objvar" + k + "_v");
                                    if (objvar_name != null && !objvar_name.equals(""))
                                    {
                                        setObjVar(item, objvar_name, utils.stringToFloat(objvar_value));
                                    }
                                }
                                setCraftedId(item, self);
                                setCrafter(item, self);
                                stack_size = rand(1, 5);
                                for (n = 0; n < stack_size; n++)
                                {
                                    copy_item = createObject(itemTemplate, playerInv, "");
                                    copyObjVar(item, copy_item, "crafting");
                                    copyObjVar(item, copy_item, "crafting_components");
                                    setCraftedId(copy_item, self);
                                    setCrafter(copy_item, self);
                                }
                            }
                            else 
                            {
                                for (k = 1; k <= 8; k++)
                                {
                                    objvar_name = reward.getString("r" + j + "_objvar" + k);
                                    objvar_value = reward.getString("r" + j + "_objvar" + k + "_v");
                                    if (objvar_name != null && !objvar_name.equals(""))
                                    {
                                        if (objvar_name.startsWith("dot") && objvar_name.endsWith("type"))
                                        {
                                            setObjVar(item, objvar_name, objvar_value);
                                        }
                                        else 
                                        {
                                            setObjVar(item, objvar_name, utils.stringToInt(objvar_value));
                                        }
                                    }
                                }
                            }
                        }
                        else 
                        {
                            string_id itemCreateFail = new string_id(MESSAGE_FILE, "item_create_fail");
                            sendSystemMessage(player, itemCreateFail);
                            return;
                        }
                    }
                }
            }
        }
        int factionReward = dataTableGetInt(datatable, questNum, "faction_reward_amount");
        if (factionReward > 0)
        {
            factions.addFactionStanding(player, "jabba", (float)factionReward);
        }
        int credits = dataTableGetInt(datatable, questNum, "credits");
        if (credits != 0)
        {
            money.bankTo(money.ACCT_JABBA, player, credits);
            prose_package pp = prose.getPackage(new string_id(MESSAGE_FILE, "theme_park_credits_pp"), credits);
            sendSystemMessageProse(player, pp);
        }
        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null)
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        int gating = getIntObjVar(player, gatingString);
        gating = gating + 1;
        setObjVar(player, gatingString, gating);
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
    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException
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
}

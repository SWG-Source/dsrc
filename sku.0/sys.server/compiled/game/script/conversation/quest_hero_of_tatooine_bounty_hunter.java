package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.sui;
import script.library.utils;

public class quest_hero_of_tatooine_bounty_hunter extends script.base_script
{
    public quest_hero_of_tatooine_bounty_hunter()
    {
    }
    public static String c_stringFile = "conversation/quest_hero_of_tatooine_bounty_hunter";
    public boolean quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_hero_of_tatooine_bounty_hunter_condition_complete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.intellect.complete"))
        {
            return true;
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.complete"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_bounty_hunter_condition_failed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.intellect.failed"))
        {
            obj_id last_bh = getObjIdObjVar(player, "quest.hero_of_tatooine.intellect.failed");
            if (last_bh == npc)
            {
                return true;
            }
            else 
            {
                removeObjVar(player, "quest.hero_of_tatooine.intellect.failed");
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_bounty_hunter_condition_in_progress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "quest.hero_of_tatooine.intellect.in_progress"))
        {
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_bounty_hunter_condition_quest_done(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "quest.hero_of_tatooine.intellect.spawner.complete"))
        {
            return true;
        }
        return false;
    }
    public void quest_hero_of_tatooine_bounty_hunter_action_set_help(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "quest.hero_of_tatooine.intellect.in_progress", 1);
    }
    public void quest_hero_of_tatooine_bounty_hunter_action_point_him_out(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id control = getObjIdObjVar(npc, "quest.hero_of_tatooine.intellect.spawner.controller");
        if (!isIdValid(control))
        {
            return;
        }
        Vector liars = getResizeableObjIdArrayObjVar(control, "quest.hero_of_tatooine.intellect.spawner.active.liars");
        Vector newLiars = new Vector();
        newLiars.setSize(0);
        int size = liars.size();
        while (size > 0)
        {
            int i = rand(0, (size - 1));
            newLiars = utils.addElement(newLiars, liars.get(i));
            liars = utils.removeElementAt(liars, i);
            size--;
        }
        if (newLiars == null || newLiars.size() == 0)
        {
            return;
        }
        String[] names = new String[newLiars.size()];
        for (int i = 0; i < newLiars.size(); i++)
        {
            int num = getIntObjVar(((obj_id)newLiars.get(i)), "quest.hero_of_tatooine.intellect.spawner.npc");
            if (num == 3)
            {
                utils.setScriptVar(player, "quest.hero_of_tatooine.intellect.liar", i);
            }
            String name = getEncodedName(((obj_id)newLiars.get(i)));
            names[i] = name;
        }
        String prompt = "@quest/hero_of_tatooine/intellect_liar:sui_prompt";
        String title = "@quest/hero_of_tatooine/intellect_liar:sui_title";
        String btn_ok = "@quest/hero_of_tatooine/intellect_liar:sui_btn_ok";
        String btn_cancel = "@quest/hero_of_tatooine/intellect_liar:sui_btn_cancel";
        int pid = sui.listbox(npc, player, prompt, sui.OK_CANCEL, title, names, "handleImplicationSui", false, false);
        if (pid > -1)
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, btn_ok);
            setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, btn_cancel);
            showSUIPage(pid);
        }
    }
    public String quest_hero_of_tatooine_bounty_hunter_tokenTO_smuggler_leader(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id control = getObjIdObjVar(npc, "quest.hero_of_tatooine.intellect.spawner.controller");
        obj_id[] liars = getObjIdArrayObjVar(npc, "quest.hero_of_tatooine.intellect.spawner.active.liars");
        obj_id leader = liars[0];
        String name = getEncodedName(leader);
        if (name == null)
        {
            name = "";
        }
        return name;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1b0651f8"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_bounty_hunter_action_point_him_out(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc5b59e0");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69cdbab1"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cd33dbbc");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_775eed34"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7eb6abbe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b8f4813d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a49ad86c");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_12afde00"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60d63ec8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b8f4813d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a49ad86c");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3769e5f8"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54e633f8");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b8f4813d"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dec94c0f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3412303");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5fdaa4bb");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250df636");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_922dd166");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eaa5c299");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd6f7e");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a49ad86c"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d3412303"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250df636");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_922dd166");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eaa5c299");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd6f7e");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5fdaa4bb"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86c02324");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_922dd166"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d9ec1dc4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_df981c5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ece0ca2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_eaa5c299"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b8bf2aa9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_df981c5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ece0ca2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dd6f7e"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_df981c5e"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffdc10ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fa16fd3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e244342");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ece0ca2"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_659bb141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_239b0909");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1fa16fd3"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bebac1d7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dce437b6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7cf16bce");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5e244342"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dce437b6"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_806d4263");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47482ee2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1892fb83");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7cf16bce"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47482ee2"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254a92c6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369a8bff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402e3e9a");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1892fb83"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_369a8bff"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_bounty_hunter_action_set_help(player, npc);
                string_id message = new string_id(c_stringFile, "s_44f4df");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_402e3e9a"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_624a9b6f");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffdc10ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fa16fd3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e244342");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_239b0909"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_df981c5e"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffdc10ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fa16fd3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e244342");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ece0ca2"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_659bb141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_239b0909");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_bounty_hunter_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b8f4813d"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dec94c0f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3412303");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5fdaa4bb");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_59"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250df636");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_922dd166");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eaa5c299");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd6f7e");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a49ad86c"))
        {
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.quest_hero_of_tatooine_bounty_hunter");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        if (!hasScript(self, "quest.hero_of_tatooine.intellect_bounty_hunter"))
        {
            attachScript(self, "quest.hero_of_tatooine.intellect_bounty_hunter");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        attachScript(self, "quest.hero_of_tatooine.intellect_bounty_hunter");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.quest_hero_of_tatooine_bounty_hunter");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (quest_hero_of_tatooine_bounty_hunter_condition_complete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b932a57a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_bounty_hunter_condition_failed(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29ecd548");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_bounty_hunter_condition_quest_done(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_df879e");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_bounty_hunter_condition_in_progress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_faaf7075");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1b0651f8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69cdbab1");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 4);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_bounty_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_bd2c41cb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (quest_hero_of_tatooine_bounty_hunter_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_775eed34");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12afde00");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3769e5f8");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId", 7);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_bounty_hunter", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_hero_of_tatooine_bounty_hunter"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
        if (branchId == 4 && quest_hero_of_tatooine_bounty_hunter_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && quest_hero_of_tatooine_bounty_hunter_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && quest_hero_of_tatooine_bounty_hunter_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && quest_hero_of_tatooine_bounty_hunter_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && quest_hero_of_tatooine_bounty_hunter_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && quest_hero_of_tatooine_bounty_hunter_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && quest_hero_of_tatooine_bounty_hunter_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && quest_hero_of_tatooine_bounty_hunter_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && quest_hero_of_tatooine_bounty_hunter_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && quest_hero_of_tatooine_bounty_hunter_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && quest_hero_of_tatooine_bounty_hunter_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && quest_hero_of_tatooine_bounty_hunter_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && quest_hero_of_tatooine_bounty_hunter_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_bounty_hunter.branchId");
        return SCRIPT_CONTINUE;
    }
}

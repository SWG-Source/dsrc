package script.conversation;

import script.*;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

public class azure_cabal_hoolar extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.azure_cabal_hoolar";
    public String c_stringFile = "conversation/azure_cabal_hoolar";

    public azure_cabal_hoolar()
    {
        super.scriptName = "azure_cabal_hoolar";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }

    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01", "ac_30_hoolar_01_dm2");
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01", "ac_30_hoolar_01_dml1");
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01", "ac_30_hoolar_01_dml2");
    }
    private boolean azure_cabal_hoolar_condition_onTask_30ENF_Q1_Return(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01", "ac_30_hoolar_01_wfs_quest_complete");
    }
    public boolean azure_cabal_hoolar_condition_onTask_30ENF_Repeater_Q1orQ2(obj_id player) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30", "ac_repeater_loruna_enf_30_WFS_hoolar_01") || groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30", "ac_repeater_loruna_enf_30_WFS_hoolar_02"));
    }
    private boolean azure_cabal_hoolar_condition_onQuest_30ENF_Q1(obj_id player) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01");
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q2A(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_dm1");
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q2B(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_dm2");
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q2C(obj_id player) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_dml1") || groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_dml1_retrieve"));
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q2D(obj_id player) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_dml2_retrieve") || groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_dml2"));
    }
    private boolean azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01", "ac_30_hoolar_01_dm1");
    }
    private boolean azure_cabal_hoolar_condition_onTask_30ENF_Q2_Return(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02", "ac_30_hoolar_02_wfs_quest_complete");
    }
    private boolean azure_cabal_hoolar_condition_onQuest_30ENF_Q2(obj_id player) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02");
    }
    private boolean azure_cabal_hoolar_condition_onTask_30ENF_Repeater_Q1(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30", "ac_repeater_loruna_enf_30_WFS_hoolar_01");
    }
    private boolean azure_cabal_hoolar_condition_onTask_30ENF_Repeater_Q2(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "ac_repeater_loruna_enf_30", "ac_repeater_loruna_enf_30_WFS_hoolar_02");
    }
    public boolean azure_cabal_hoolar_condition_onQuest_30ENF_Q1orQ2(obj_id player) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_01") || groundquests.isQuestActive(player, "ac_repeater_loruna_enf_30_dnpc2_hoolar_02"));
    }
    private void azure_cabal_hoolar_action_grant30Q1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.grantQuest(player, "quest/ac_repeater_loruna_enf_30_dnpc2_hoolar_01");
    }
    private void azure_cabal_hoolar_action_signal_30ENF_ClearRepeaterQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "ac_repeater_loruna_enf_30_signal_clear_quest");
    }
    public void azure_cabal_hoolar_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    private void azure_cabal_hoolar_action_grant30Q2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.grantQuest(player, "quest/ac_repeater_loruna_enf_30_dnpc2_hoolar_02");
    }
    private void azure_cabal_hoolar_action_signal_30ENF_CompleteQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "ac_quest_complete");
    }
    public String azure_cabal_hoolar_tokenTO_playerFirstName(obj_id player) throws InterruptedException
    {
        return getFirstName(player);
    }
    public String azure_cabal_hoolar_tokenTO_playersProfession() throws InterruptedException
    {
        return "";
    }
    private int azure_cabal_hoolar_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170"))
        {
            azure_cabal_hoolar_action_grant30Q1(player, npc);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_172"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_171"))
        {
            return craft_response(new String[] {"s_181", "s_182", "s_183"}, 3, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            azure_cabal_hoolar_action_grant30Q1(player, npc);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_172"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_183"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_185"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_175"))
        {
            azure_cabal_hoolar_action_grant30Q2(player, npc);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_180"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_176"))
        {
            return craft_response(new String[] {"s_177", "s_178", "s_179"}, 7, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            azure_cabal_hoolar_action_grant30Q2(player, npc);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_180"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_179"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_186"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch9(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_196"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_198"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch11(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_200"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_198"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch12(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_193"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_194"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch13(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_216"))
        {
            return craft_response(new String[] {"s_218", "s_220", "s_221"}, 14, player);
        }
        else if (response.equals("s_217"))
        {
            return craft_response(new String[] {"s_219", "s_228", "s_227"}, 17, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch14(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_220"))
        {
            return craft_response(new String[] {"s_222", "s_223", "s_224"}, 15, player);
        }
        else if (response.equals("s_221"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch15(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_223"))
        {
            return craft_response(new String[] {"s_225", "s_237"}, 16, player);
        }
        else if (response.equals("s_224"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch16(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_237"))
        {
            return craft_response(new String[] {"s_219", "s_228", "s_227"}, 17, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch17(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_228"))
        {
            return craft_response(new String[] {"s_229", "s_230", "s_231", "s_233"}, 18, player);
        }
        else if (response.equals("s_227"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch18(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_230"))
        {
            return craft_response(new String[] {"s_232", "s_235", "s_236"}, 19, player);
        }
        else if (response.equals("s_231"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_233"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch19(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_235"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_236"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch20(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_239"))
        {
            return craft_response(new String[] {"s_241", "s_243", "s_252", "s_244"}, 21, player);
        }
        else if (response.equals("s_240"))
        {
            return craft_response(new String[] {"s_242", "s_245", "s_246"}, 23, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch21(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_243"))
        {
            return craft_response(new String[] {"s_253", "s_254", "s_255"}, 22, player);
        }
        else if (response.equals("s_252"))
        {
            return craft_response(new String[] {"s_242", "s_245", "s_246"}, 23, player);
        }
        else if (response.equals("s_244"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch22(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_254"))
        {
            return craft_response(new String[] {"s_242", "s_245", "s_246"}, 23, player);
        }
        else if (response.equals("s_255"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch23(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_245"))
        {
            return craft_response(new String[] {"s_247", "s_248", "s_249"}, 24, player);
        }
        else if (response.equals("s_246"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch24(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_248"))
        {
            return craft_response(new String[] {"s_250", "s_251"}, 25, player);
        }
        else if (response.equals("s_249"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch25(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_251"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch26(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            return craft_response(new String[] {"s_110", "s_111", "s_112"}, 27, player);
        }
        else if (response.equals("s_109"))
        {
            return craft_response(new String[] {"s_120", "s_121", "s_123"}, 30, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch27(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_111"))
        {
            return craft_response(new String[] {"s_113", "s_114", "s_118"}, 28, player);
        }
        else if (response.equals("s_112"))
        {
            return craft_response(new String[] {"s_120", "s_121", "s_123"}, 31, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch28(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_114"))
        {
            return craft_response(new String[] {"s_115", "s_116", "s_117"}, 29, player);
        }
        else if (response.equals("s_118"))
        {
            return craft_response(new String[] {"s_120", "s_121", "s_123"}, 31, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch29(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_119"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_117"))
        {
            return craft_response(new String[] {"s_120", "s_121", "s_123"}, 31, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch31(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_121"))
        {
            return craft_response(new String[] {"s_122", "s_124", "s_125"}, 32, player);
        }
        else if (response.equals("s_123"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch32(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_126"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_125"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch34(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_87"))
        {
            return craft_response(new String[] {"s_89", "s_91", "s_104"}, 35, player);
        }
        else if (response.equals("s_106"))
        {
            return craft_response(new String[] {"s_127", "s_132", "s_164"}, 39, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch35(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_91"))
        {
            return craft_response(new String[] {"s_93", "s_128", "s_102"}, 36, player);
        }
        else if (response.equals("s_104"))
        {
            return craft_response(new String[] {"s_127", "s_132", "s_164"}, 39, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch36(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            return craft_response(new String[] {"s_129", "s_131"}, 37, player);
        }
        else if (response.equals("s_102"))
        {
            return craft_response(new String[] {"s_127", "s_132", "s_164"}, 39, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch37(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_131"))
        {
            return craft_response(new String[] {"s_133", "s_139"}, 38, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch38(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_139"))
        {
            return craft_response(new String[] {"s_127", "s_132", "s_164"}, 39, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch39(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            return craft_response(new String[] {"s_136", "s_140", "s_162"}, 40, player);
        }
        else if (response.equals("s_164"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch40(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_140"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_142"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_162"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1A(player))
            {
                return craft_response(new String[] {"s_197", "s_216", "s_217"}, 13, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1B(player))
            {
                return craft_response(new String[] {"s_199", "s_239", "s_240"}, 20, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1C(player))
            {
                return craft_response(new String[] {"s_201", "s_108", "s_109"}, 26, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q1D(player))
            {
                return craft_response(new String[] {"s_203", "s_87", "s_106"}, 34, player);
            }
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_167"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch44(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_283"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2A(player))
            {
                return craft_response(new String[] {"s_358", "s_384", "s_403"}, 45, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2B(player))
            {
                return craft_response(new String[] {"s_286", "s_288", "s_312", "s_401"}, 51, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2C(player))
            {
                return craft_response(new String[] {"s_396", "s_399", "s_400", "s_402"}, 56, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2D(player))
            {
                return craft_response(new String[] {"s_398", "s_417", "s_418", "s_419"}, 61, player);
            }
        }
        else if (response.equals("s_328"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch45(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_360"))
        {
            return craft_response(new String[] {"s_362", "s_364", "s_366", "s_382"}, 46, player);
        }
        else if (response.equals("s_384"))
        {
            return craft_response(new String[] {"s_386", "s_388", "s_390"}, 49, player);
        }
        else if (response.equals("s_403"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch46(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_364"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_366"))
        {
            return craft_response(new String[] {"s_368", "s_370", "s_372", "s_380"}, 47, player);
        }
        else if (response.equals("s_382"))
        {
            return craft_response(new String[] {"s_386", "s_388", "s_390"}, 49, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch47(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_370"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_372"))
        {
            return craft_response(new String[] {"s_374", "s_376", "s_378"}, 48, player);
        }
        else if (response.equals("s_380"))
        {
            return craft_response(new String[] {"s_386", "s_388", "s_390"}, 49, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch48(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_376"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_378"))
        {
            return craft_response(new String[] {"s_386", "s_388", "s_390"}, 49, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch49(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_388"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_390"))
        {
            return craft_response(new String[] {"s_392", "s_394"}, 50, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch50(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_394"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch51(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_288"))
        {
            return craft_response(new String[] {"s_290", "s_292", "s_294", "s_310"}, 52, player);
        }
        else if (response.equals("s_312"))
        {
            return craft_response(new String[] {"s_314", "s_316", "s_318"}, 55, player);
        }
        else if (response.equals("s_401"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch52(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_292"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_294"))
        {
            return craft_response(new String[] {"s_296", "s_298", "s_300", "s_308"}, 53, player);
        }
        else if (response.equals("s_310"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2A(player))
            {
                return craft_response(new String[] {"s_358", "s_360", "s_384", "s_403"}, 45, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2B(player))
            {
                return craft_response(new String[] {"s_286", "s_288", "s_312", "s_401"}, 51, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2C(player))
            {
                return craft_response(new String[] {"s_396", "s_399", "s_400", "s_402"}, 56, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2D(player))
            {
                return craft_response(new String[] {"s_398", "s_417", "s_418", "s_419"}, 61, player);
            }
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch53(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_298"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_300"))
        {
            return craft_response(new String[] {"s_302", "s_304", "s_306"}, 54, player);
        }
        else if (response.equals("s_308"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2A(player))
            {
                return craft_response(new String[] {"s_358", "s_360", "s_384", "s_403"}, 45, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2B(player))
            {
                return craft_response(new String[] {"s_286", "s_288", "s_312", "s_401"}, 51, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2C(player))
            {
                return craft_response(new String[] {"s_396", "s_399", "s_400", "s_402"}, 56, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2D(player))
            {
                return craft_response(new String[] {"s_398", "s_417", "s_418", "s_419"}, 61, player);
            }
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch54(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_304"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_306"))
        {
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2A(player))
            {
                return craft_response(new String[] {"s_358", "s_360", "s_384", "s_403"}, 45, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2B(player))
            {
                return craft_response(new String[] {"s_286", "s_288", "s_312", "s_401"}, 51, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2C(player))
            {
                return craft_response(new String[] {"s_396", "s_399", "s_400", "s_402"}, 56, player);
            }
            if (azure_cabal_hoolar_condition_onAssignment_30ENF_Q2D(player))
            {
                return craft_response(new String[] {"s_398", "s_417", "s_418", "s_419"}, 61, player);
            }
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch55(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_316"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        else if (response.equals("s_318"))
        {
            return craft_response(new String[] {"s_290", "s_292", "s_294", "s_310"}, 52, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch56(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_399"))
        {
            return craft_response(new String[] {"s_404", "s_406", "s_407"}, 57, player);
        }
        else if (response.equals("s_400"))
        {
            return craft_response(new String[] {"s_411", "s_413", "s_414"}, 59, player);
        }
        else if (response.equals("s_402"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch57(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_406"))
        {
            return craft_response(new String[] {"s_408", "s_410", "s_409"}, 58, player);
        }
        else if (response.equals("s_407"))
        {
            return craft_response(new String[] {"s_411", "s_413", "s_414"}, 59, player);
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch58(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_410"))
        {
            return craft_response(new String[] {"s_411", "s_413", "s_414"}, 59, player);
        }
        else if (response.equals("s_409"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch59(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_413"))
        {
            return craft_response(new String[] {"s_415", "s_416"}, 60, player);
        }
        else if (response.equals("s_414"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch60(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_416"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch61(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_417"))
        {
            return craft_response(new String[] {"s_420", "s_421", "s_422"}, 62, player);
        }
        else if (response.equals("s_418"))
        {
            return craft_response(new String[] {"s_423", "s_424", "s_425"}, 63, player);
        }
        else if (response.equals("s_419"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch62(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_421"))
        {
            return craft_response(new String[] {"s_423", "s_424", "s_425"}, 63, player);
        }
        else if (response.equals("s_422"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch63(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_424"))
        {
            return craft_response(new String[] {"s_426", "s_427", "s_428"}, 64, player);
        }
        else if (response.equals("s_425"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch64(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_427"))
        {
            return craft_response(new String[] {"s_429", "s_430", "s_431"}, 65, player);
        }
        else if (response.equals("s_428"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch65(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_430"))
        {
            return craft_response(new String[] {"s_432", "s_433"}, 66, player);
        }
        else if (response.equals("s_431"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int azure_cabal_hoolar_handleBranch66(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_433"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_195"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        else if (azure_cabal_hoolar_condition_onTask_30ENF_Repeater_Q1(player))
        {
            azure_cabal_hoolar_action_signal_30ENF_ClearRepeaterQuest(player, self);
            return craft_repeater(new String[] {"s_168", "s_170", "s_171"}, 1, player, self);
        }
        else if (azure_cabal_hoolar_condition_onTask_30ENF_Repeater_Q2(player))
        {
            azure_cabal_hoolar_action_signal_30ENF_ClearRepeaterQuest(player, self);
            return craft_repeater(new String[] {"s_174", "s_175", "s_176"}, 5, player, self);
        }
        else if (azure_cabal_hoolar_condition_onTask_30ENF_Q1_Return(player))
        {
            azure_cabal_hoolar_action_signal_30ENF_CompleteQuest(player, self);
            return craft_repeater(new String[] {"s_189", "s_196"}, 9, player, self);
        }
        else if (azure_cabal_hoolar_condition_onTask_30ENF_Q2_Return(player))
        {
            azure_cabal_hoolar_action_signal_30ENF_CompleteQuest(player, self);
            return craft_repeater(new String[] {"s_191", "s_200"}, 11, player, self);
        }
        else if (azure_cabal_hoolar_condition_onQuest_30ENF_Q1(player))
        {
            return craft_repeater(new String[] {"s_192", "s_193", "s_194"}, 12, player, self);
        }
        else if (azure_cabal_hoolar_condition_onQuest_30ENF_Q2(player))
        {
            return craft_repeater(new String[] {"s_282", "s_283", "s_328"}, 44, player, self);
        }
        chat.chat(self, player, new string_id(c_stringFile, "s_330"));
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("azure_cabal_hoolar"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, conversation + ".branchId");
        if (branchId == 1 && azure_cabal_hoolar_handleBranch1(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 3 && azure_cabal_hoolar_handleBranch3(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 5 && azure_cabal_hoolar_handleBranch5(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 7 && azure_cabal_hoolar_handleBranch7(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 9 && azure_cabal_hoolar_handleBranch9(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 11 && azure_cabal_hoolar_handleBranch11(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 12 && azure_cabal_hoolar_handleBranch12(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 13 && azure_cabal_hoolar_handleBranch13(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 14 && azure_cabal_hoolar_handleBranch14(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 15 && azure_cabal_hoolar_handleBranch15(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 16 && azure_cabal_hoolar_handleBranch16(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 17 && azure_cabal_hoolar_handleBranch17(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 18 && azure_cabal_hoolar_handleBranch18(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 19 && azure_cabal_hoolar_handleBranch19(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 20 && azure_cabal_hoolar_handleBranch20(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 21 && azure_cabal_hoolar_handleBranch21(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 22 && azure_cabal_hoolar_handleBranch22(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 23 && azure_cabal_hoolar_handleBranch23(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 24 && azure_cabal_hoolar_handleBranch24(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 25 && azure_cabal_hoolar_handleBranch25(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 26 && azure_cabal_hoolar_handleBranch26(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 27 && azure_cabal_hoolar_handleBranch27(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 28 && azure_cabal_hoolar_handleBranch28(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 29 && azure_cabal_hoolar_handleBranch29(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 31 && azure_cabal_hoolar_handleBranch31(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 32 && azure_cabal_hoolar_handleBranch32(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 34 && azure_cabal_hoolar_handleBranch34(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 35 && azure_cabal_hoolar_handleBranch35(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 36 && azure_cabal_hoolar_handleBranch36(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 37 && azure_cabal_hoolar_handleBranch37(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 38 && azure_cabal_hoolar_handleBranch38(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 39 && azure_cabal_hoolar_handleBranch39(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 40 && azure_cabal_hoolar_handleBranch40(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 44 && azure_cabal_hoolar_handleBranch44(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 45 && azure_cabal_hoolar_handleBranch45(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 46 && azure_cabal_hoolar_handleBranch46(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 47 && azure_cabal_hoolar_handleBranch47(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 48 && azure_cabal_hoolar_handleBranch48(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 49 && azure_cabal_hoolar_handleBranch49(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 50 && azure_cabal_hoolar_handleBranch50(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 51 && azure_cabal_hoolar_handleBranch51(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 52 && azure_cabal_hoolar_handleBranch52(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 53 && azure_cabal_hoolar_handleBranch53(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 54 && azure_cabal_hoolar_handleBranch54(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 55 && azure_cabal_hoolar_handleBranch55(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 56 && azure_cabal_hoolar_handleBranch56(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 57 && azure_cabal_hoolar_handleBranch57(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 58 && azure_cabal_hoolar_handleBranch58(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 59 && azure_cabal_hoolar_handleBranch59(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 60 && azure_cabal_hoolar_handleBranch60(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 61 && azure_cabal_hoolar_handleBranch61(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 62 && azure_cabal_hoolar_handleBranch62(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 63 && azure_cabal_hoolar_handleBranch63(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 64 && azure_cabal_hoolar_handleBranch64(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 65 && azure_cabal_hoolar_handleBranch65(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        else if (branchId == 66 && azure_cabal_hoolar_handleBranch66(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, conversation + ".branchId");
        return SCRIPT_CONTINUE;
    }
}

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
import script.library.conversation;
import script.library.groundquests;
import script.library.npe;
import script.library.space_quest;
import script.library.sui;
import script.library.utils;

public class npe_station_han_solo2 extends script.base_script
{
    public npe_station_han_solo2()
    {
    }
    public static String c_stringFile = "conversation/npe_station_han_solo2";
    public boolean npe_station_han_solo2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_station_han_solo2_condition_firstConversation(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_solo_profession");
    }
    public boolean npe_station_han_solo2_condition_secondConversation(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_solo_profession", "talktosolo2");
    }
    public boolean npe_station_han_solo2_condition_finishedClassQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_smuggler_try");
    }
    public boolean npe_station_han_solo2_condition_ClassQuestActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "npe_smuggler_try", "talktojake");
    }
    public boolean npe_station_han_solo2_condition_isTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_smuggler_try");
    }
    public boolean npe_station_han_solo2_condition_onLastSmugglerStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_smuggler_try_2", "talktohan");
    }
    public boolean npe_station_han_solo2_condition_isBH(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.BOUNTY_HUNTER);
    }
    public boolean npe_station_han_solo2_condition_choseProfessionTrainer(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_solo_profession_2");
    }
    public boolean npe_station_han_solo2_condition_onMainQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_new_main_quest");
    }
    public boolean npe_station_han_solo2_condition_onCantinaStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "npe_new_main_quest", "getparts");
    }
    public boolean npe_station_han_solo2_condition_onCollectStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "npe_new_main_quest", "bounty");
    }
    public boolean npe_station_han_solo2_condition_onlastHanstep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_new_main_quest", "return");
    }
    public boolean npe_station_han_solo2_condition_onInaldraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_inaldra_quest");
    }
    public boolean npe_station_han_solo2_condition_finishedInaldraQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_inaldra_quest");
    }
    public boolean npe_station_han_solo2_condition_completedMainQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_new_main_quest");
    }
    public boolean npe_station_han_solo2_condition_playerisSpy(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.SPY);
    }
    public boolean npe_station_han_solo2_condition_isSmugglerToTrain(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((utils.isProfession(player, utils.SMUGGLER)) && groundquests.isQuestActive(player, "npe_pointer_smuggler_template"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean npe_station_han_solo2_condition_playerisTrader(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.TRADER);
    }
    public boolean npe_station_han_solo2_condition_playerisForceSensitive(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.FORCE_SENSITIVE);
    }
    public boolean npe_station_han_solo2_condition_playerisEntertainer(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.ENTERTAINER);
    }
    public boolean npe_station_han_solo2_condition_playerisMedic(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.MEDIC);
    }
    public boolean npe_station_han_solo2_condition_playerisOfficer(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.OFFICER);
    }
    public boolean npe_station_han_solo2_condition_playerisSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.SMUGGLER);
    }
    public boolean npe_station_han_solo2_condition_playerisCommando(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.COMMANDO);
    }
    public boolean npe_station_han_solo2_condition_playerisBountyHunter(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.isProfession(player, utils.BOUNTY_HUNTER);
    }
    public boolean npe_station_han_solo2_condition_finishedProfessionTrainer(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(player, "npe.finishedTemplate");
    }
    public boolean npe_station_han_solo2_condition_finishedTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_smuggler_comm", "donedone") || groundquests.hasCompletedQuest(player, "npe_smuggler_comm"));
    }
    public boolean npe_station_han_solo2_condition_isAtLeastLevel5(obj_id player, obj_id npc) throws InterruptedException
    {
        int level = getLevel(player);
        if (npe_station_han_solo2_condition_playerisEntertainer(player, npc) || npe_station_han_solo2_condition_playerisTrader(player, npc))
        {
            return true;
        }
        if (level >= 5)
        {
            return true;
        }
        return false;
    }
    public void npe_station_han_solo2_action_giveContactPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "found_smuggler");
        groundquests.grantQuest(player, "npe_smuggler_try");
        if (!hasObjVar(player, npe.QUEST_REWORK_VAR))
        {
            setObjVar(player, npe.QUEST_REWORK_VAR, npe.QUEST_ENUMERATION);
        }
        play2dNonLoopingSound(player, "sound/vo_han_solo_28c.snd");
    }
    public void npe_station_han_solo2_action_signaSmugglerQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "donedone");
        groundquests.sendSignal(player, "found_smuggler");
    }
    public void npe_station_han_solo2_action_giveSecretaryPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "reward");
        groundquests.grantQuest(player, "npe_pointer_secretary");
        setObjVar(player, "npe.finishedTemplate", 1);
    }
    public void npe_station_han_solo2_action_giveSmugglerQuestSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_smuggler_try_2_han");
        setObjVar(player, "npe.finishedTemplate", 1);
        play2dNonLoopingSound(player, "sound/vo_han_solo_33c.snd");
    }
    public void npe_station_han_solo2_action_giveFirstReward(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (!hasObjVar(player, "npe.gotWeapon"))
        {
            npe.giveProfessionWeapon(player);
            setObjVar(player, "npe.gotWeapon", true);
        }
        play2dNonLoopingSound(player, "sound/vo_han_solo_10c.snd");
    }
    public void npe_station_han_solo2_action_giveAnothersignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "found_smuggler");
    }
    public void npe_station_han_solo2_action_sound1(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_11c.snd");
    }
    public void npe_station_han_solo2_action_sound2(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_12c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_signalMainQuestComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_new_main_quest_han");
        groundquests.grantQuest(player, "npe_pointer_inaldra");
        play2dNonLoopingSound(player, "sound/vo_han_solo_52c.snd");
    }
    public void npe_station_han_solo2_action_giveMainQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_new_main_quest");
        npe.giveMapPopUp(player, npc);
        play2dNonLoopingSound(player, "sound/vo_han_solo_39c.snd");
    }
    public void npe_station_han_solo2_action_faceplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound3(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_13c.snd");
    }
    public void npe_station_han_solo2_action_giveProfessionPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        npe.giveTemplatePointer(player);
        play2dNonLoopingSound(player, "sound/vo_han_solo_14c.snd");
    }
    public void npe_station_han_solo2_action_leaveStation(obj_id player, obj_id npc) throws InterruptedException
    {
        string_id stfPrompt = new string_id("npe", "exit_station_prompt");
        string_id stfTitle = new string_id("npe", "exit_station");
        String prompt = utils.packStringId(stfPrompt);
        String title = utils.packStringId(stfTitle);
        int pid = sui.msgbox(player, player, prompt, sui.OK_CANCEL, title, 0, "handTransfer");
        play2dNonLoopingSound(player, "sound/vo_han_solo_64c.snd");
    }
    public void npe_station_han_solo2_action_signalMQCompleteNoInaldra(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_new_main_quest_han");
        groundquests.grantQuest(player, "npe_job_pointer_han");
        play2dNonLoopingSound(player, "sound/vo_han_solo_51c.snd");
    }
    public void npe_station_han_solo2_action_sound4(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_15c.snd");
    }
    public void npe_station_han_solo2_action_sound5(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_16c.snd");
    }
    public void npe_station_han_solo2_action_sound6(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_17c.snd");
    }
    public void npe_station_han_solo2_action_sound7(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_18c.snd");
    }
    public void npe_station_han_solo2_action_sound8(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_19c.snd");
    }
    public void npe_station_han_solo2_action_sound9(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_20c.snd");
    }
    public void npe_station_han_solo2_action_sound10(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_21c.snd");
    }
    public void npe_station_han_solo2_action_sound11(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_22c.snd");
        npe.giveTemplatePointer(player);
    }
    public void npe_station_han_solo2_action_sound12(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_25c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound13(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_26c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound14(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_27c.snd");
    }
    public void npe_station_han_solo2_action_sound15(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_29c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound16(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_30c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound17(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_31c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound18(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_32c.snd");
    }
    public void npe_station_han_solo2_action_sound19(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_35c.snd");
    }
    public void npe_station_han_solo2_action_sound20(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_36c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound21(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_37c.snd");
    }
    public void npe_station_han_solo2_action_sound22(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_38c.snd");
    }
    public void npe_station_han_solo2_action_sound23(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_40c.snd");
    }
    public void npe_station_han_solo2_action_sound24(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_41c.snd");
    }
    public void npe_station_han_solo2_action_sound25(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_34c.snd");
    }
    public void npe_station_han_solo2_action_sound26(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_43c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound27(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_44c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound28(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_45c.snd");
    }
    public void npe_station_han_solo2_action_sound29(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_46c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound30(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_47c.snd");
    }
    public void npe_station_han_solo2_action_sound31(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_48c.snd");
    }
    public void npe_station_han_solo2_action_sound32(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_49c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound33(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_50c.snd");
    }
    public void npe_station_han_solo2_action_sound34(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_53c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound35(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_54c.snd");
    }
    public void npe_station_han_solo2_action_sound36(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_55c.snd");
    }
    public void npe_station_han_solo2_action_sound37(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_56c.snd");
    }
    public void npe_station_han_solo2_action_sound38(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_57c.snd");
    }
    public void npe_station_han_solo2_action_sound39(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_58c.snd");
    }
    public void npe_station_han_solo2_action_sound40(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_59c.snd");
    }
    public void npe_station_han_solo2_action_sound41(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_60c.snd");
    }
    public void npe_station_han_solo2_action_sound42(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_61c.snd");
    }
    public void npe_station_han_solo2_action_sound43(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_62c.snd");
    }
    public void npe_station_han_solo2_action_sound44(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_63c.snd");
    }
    public void npe_station_han_solo2_action_sound45(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_65c.snd");
    }
    public void npe_station_han_solo2_action_sound46(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_66c.snd");
        faceTo(npc, player);
    }
    public void npe_station_han_solo2_action_sound2ca(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_12ca.snd");
    }
    public void npe_station_han_solo2_action_sound47(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_xtra3.snd");
    }
    public void npe_station_han_solo2_action_sound48(obj_id player, obj_id npc) throws InterruptedException
    {
        play2dNonLoopingSound(player, "sound/vo_han_solo_xtra4.snd");
        npe_station_han_solo2_action_giveSecretaryPointer(player, npc);
    }
    public void npe_station_han_solo2_action_sendMainSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_new_main_quest_han");
    }
    public int npe_station_han_solo2_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_149"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                npe_station_han_solo2_action_sound37(player, npc);
                string_id message = new string_id(c_stringFile, "s_152");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_223"))
        {
            if (npe_station_han_solo2_condition_isAtLeastLevel5(player, npc))
            {
                npe_station_han_solo2_action_sound44(player, npc);
                string_id message = new string_id(c_stringFile, "s_218");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound47(player, npc);
                string_id message = new string_id(c_stringFile, "s_117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_127"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound35(player, npc);
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_151"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                npe_station_han_solo2_action_sound37(player, npc);
                string_id message = new string_id(c_stringFile, "s_152");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_217"))
        {
            if (npe_station_han_solo2_condition_isAtLeastLevel5(player, npc))
            {
                npe_station_han_solo2_action_sound44(player, npc);
                string_id message = new string_id(c_stringFile, "s_218");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_219");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound47(player, npc);
                string_id message = new string_id(c_stringFile, "s_117");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_129"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                npe_station_han_solo2_action_sound36(player, npc);
                string_id message = new string_id(c_stringFile, "s_130");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_153"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                npe_station_han_solo2_action_sound38(player, npc);
                string_id message = new string_id(c_stringFile, "s_154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_155");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_157");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_155"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                npe_station_han_solo2_action_sound39(player, npc);
                string_id message = new string_id(c_stringFile, "s_156");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_159");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_157"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                npe_station_han_solo2_action_sound40(player, npc);
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_159"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                npe_station_han_solo2_action_sound40(player, npc);
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                npe_station_han_solo2_action_sound41(player, npc);
                string_id message = new string_id(c_stringFile, "s_161");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_162"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                npe_station_han_solo2_action_sound42(player, npc);
                string_id message = new string_id(c_stringFile, "s_169");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_141");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_168"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                npe_station_han_solo2_action_sound43(player, npc);
                string_id message = new string_id(c_stringFile, "s_170");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_141"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound48(player, npc);
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_143"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound48(player, npc);
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_219"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_leaveStation(player, npc);
                string_id message = new string_id(c_stringFile, "s_222");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_220"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                npe_station_han_solo2_action_sound45(player, npc);
                string_id message = new string_id(c_stringFile, "s_221");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_136"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound48(player, npc);
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_119"))
        {
            npe_station_han_solo2_action_sendMainSignal(player, npc);
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound33(player, npc);
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_121"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                npe_station_han_solo2_action_signalMQCompleteNoInaldra(player, npc);
                string_id message = new string_id(c_stringFile, "s_123");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_signalMainQuestComplete(player, npc);
                string_id message = new string_id(c_stringFile, "s_216");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_240"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                npe_station_han_solo2_action_sound30(player, npc);
                string_id message = new string_id(c_stringFile, "s_244");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_247");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_247"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                npe_station_han_solo2_action_sound31(player, npc);
                string_id message = new string_id(c_stringFile, "s_249");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_109"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound28(player, npc);
                string_id message = new string_id(c_stringFile, "s_110");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                npe_station_han_solo2_action_sound21(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_163"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound23(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_191"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound48(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound22(player, npc);
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_102"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                npe_station_han_solo2_action_giveMainQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                npe_station_han_solo2_action_sound24(player, npc);
                string_id message = new string_id(c_stringFile, "s_167");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_215");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_215"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                npe_station_han_solo2_action_sound21(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_193"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                npe_station_han_solo2_action_sound21(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_135"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveSecretaryPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                npe_station_han_solo2_action_sound18(player, npc);
                string_id message = new string_id(c_stringFile, "s_107");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                npe_station_han_solo2_action_giveSmugglerQuestSignal(player, npc);
                string_id message = new string_id(c_stringFile, "s_125");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_132");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_132"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                npe_station_han_solo2_action_sound19(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                npe_station_han_solo2_action_sound21(player, npc);
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_163"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound23(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_165");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_191"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound48(player, npc);
                string_id message = new string_id(c_stringFile, "s_192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_226"))
        {
            npe_station_han_solo2_action_giveAnothersignal(player, npc);
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound14(player, npc);
                string_id message = new string_id(c_stringFile, "s_227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_229"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveContactPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_231");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_197"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                npe_station_han_solo2_action_sound1(player, npc);
                string_id message = new string_id(c_stringFile, "s_200");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                npe_station_han_solo2_action_sound2(player, npc);
                string_id message = new string_id(c_stringFile, "s_166");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_139"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound2ca(player, npc);
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_142");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_142"))
        {
            if (npe_station_han_solo2_condition_playerisSpy(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound3(player, npc);
                string_id message = new string_id(c_stringFile, "s_144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisTrader(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound4(player, npc);
                string_id message = new string_id(c_stringFile, "s_173");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisEntertainer(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound5(player, npc);
                string_id message = new string_id(c_stringFile, "s_177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisCommando(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound6(player, npc);
                string_id message = new string_id(c_stringFile, "s_181");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisBountyHunter(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound7(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisMedic(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound8(player, npc);
                string_id message = new string_id(c_stringFile, "s_189");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisForceSensitive(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound9(player, npc);
                string_id message = new string_id(c_stringFile, "s_201");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisOfficer(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo2_action_sound10(player, npc);
                string_id message = new string_id(c_stringFile, "s_203");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_146");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (npe_station_han_solo2_condition_playerisSmuggler(player, npc))
            {
                doAnimationAction(npc, "thumbs_up");
                npe_station_han_solo2_action_sound11(player, npc);
                string_id message = new string_id(c_stringFile, "s_205");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_146"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_giveProfessionPointer(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo2_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_207"))
        {
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                npe_station_han_solo2_action_sound14(player, npc);
                string_id message = new string_id(c_stringFile, "s_227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.npe_station_han_solo2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Han Solo");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Han Solo");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.npe_station_han_solo2");
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
        if (npe_station_han_solo2_condition_finishedInaldraQuest(player, npc))
        {
            doAnimationAction(npc, "thank");
            npe_station_han_solo2_action_sound46(player, npc);
            string_id message = new string_id(c_stringFile, "s_131");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!npe_station_han_solo2_condition_isBH(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_149");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 1);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_completedMainQuest(player, npc))
        {
            npe_station_han_solo2_action_sound34(player, npc);
            string_id message = new string_id(c_stringFile, "s_126");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition_onInaldraQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!npe_station_han_solo2_condition_isBH(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_217");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 2);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_onlastHanstep(player, npc))
        {
            npe_station_han_solo2_action_sound32(player, npc);
            string_id message = new string_id(c_stringFile, "s_118");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_119");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 17);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_onCollectStep(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            npe_station_han_solo2_action_sound29(player, npc);
            string_id message = new string_id(c_stringFile, "s_111");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 21);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_onCantinaStep(player, npc))
        {
            npe_station_han_solo2_action_sound27(player, npc);
            string_id message = new string_id(c_stringFile, "s_108");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_109");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 24);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_onMainQuest(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            npe_station_han_solo2_action_sound26(player, npc);
            string_id message = new string_id(c_stringFile, "s_106");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_finishedProfessionTrainer(player, npc))
        {
            doAnimationAction(npc, "greet");
            npe_station_han_solo2_action_sound20(player, npc);
            string_id message = new string_id(c_stringFile, "s_94");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_191");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 27);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_onLastSmugglerStep(player, npc))
        {
            doAnimationAction(npc, "thank");
            npe_station_han_solo2_action_sound17(player, npc);
            string_id message = new string_id(c_stringFile, "s_103");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 35);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_ClassQuestActive(player, npc))
        {
            npe_station_han_solo2_action_sound16(player, npc);
            string_id message = new string_id(c_stringFile, "s_245");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_isTraining(player, npc))
        {
            npe_station_han_solo2_action_sound15(player, npc);
            string_id message = new string_id(c_stringFile, "s_243");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_isSmugglerToTrain(player, npc))
        {
            doAnimationAction(npc, "nod");
            npe_station_han_solo2_action_sound13(player, npc);
            string_id message = new string_id(c_stringFile, "s_224");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 41);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition_choseProfessionTrainer(player, npc))
        {
            npe_station_han_solo2_action_sound12(player, npc);
            string_id message = new string_id(c_stringFile, "s_225");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo2_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "thumbs_up");
            npe_station_han_solo2_action_giveFirstReward(player, npc);
            string_id message = new string_id(c_stringFile, "s_196");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_197");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo2.branchId", 45);
                npcStartConversation(player, npc, "npe_station_han_solo2", message, responses);
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
        if (!conversationId.equals("npe_station_han_solo2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_station_han_solo2.branchId");
        if (branchId == 1 && npe_station_han_solo2_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && npe_station_han_solo2_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && npe_station_han_solo2_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && npe_station_han_solo2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && npe_station_han_solo2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && npe_station_han_solo2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && npe_station_han_solo2_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_station_han_solo2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && npe_station_han_solo2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && npe_station_han_solo2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_station_han_solo2_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && npe_station_han_solo2_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && npe_station_han_solo2_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && npe_station_han_solo2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && npe_station_han_solo2_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && npe_station_han_solo2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && npe_station_han_solo2_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && npe_station_han_solo2_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && npe_station_han_solo2_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && npe_station_han_solo2_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && npe_station_han_solo2_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && npe_station_han_solo2_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && npe_station_han_solo2_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && npe_station_han_solo2_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && npe_station_han_solo2_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && npe_station_han_solo2_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && npe_station_han_solo2_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && npe_station_han_solo2_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && npe_station_han_solo2_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && npe_station_han_solo2_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && npe_station_han_solo2_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && npe_station_han_solo2_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && npe_station_han_solo2_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && npe_station_han_solo2_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && npe_station_han_solo2_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && npe_station_han_solo2_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && npe_station_han_solo2_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && npe_station_han_solo2_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && npe_station_han_solo2_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && npe_station_han_solo2_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && npe_station_han_solo2_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && npe_station_han_solo2_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_station_han_solo2.branchId");
        return SCRIPT_CONTINUE;
    }
}

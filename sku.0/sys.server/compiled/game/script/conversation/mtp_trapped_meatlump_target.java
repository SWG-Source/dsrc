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
import script.library.trial;
import script.library.utils;

public class mtp_trapped_meatlump_target extends script.base_script
{
    public mtp_trapped_meatlump_target()
    {
    }
    public static String c_stringFile = "conversation/mtp_trapped_meatlump_target";
    public boolean mtp_trapped_meatlump_target_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_trapped_meatlump_target_condition_isMeatlumpFollowing(obj_id player, obj_id npc) throws InterruptedException
    {
        return ai_lib.isFollowing(npc);
    }
    public boolean mtp_trapped_meatlump_target_condition_isCowering(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(npc, "mtp_isCowering");
    }
    public boolean mtp_trapped_meatlump_target_condition_isCoweringCombat(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(npc, "mtp_isCowering") && ai_lib.isInCombat(player);
    }
    public boolean mtp_trapped_meatlump_target_condition_isSafe(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.hasScriptVar(npc, "mtp_isSafe");
    }
    public void mtp_trapped_meatlump_target_action_followPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(npc, "mtp_isCowering"))
        {
            utils.removeScriptVar(npc, "mtp_isCowering");
        }
        ai_lib.setMood(npc, chat.MOOD_NERVOUS);
        chat.setChatMood(npc, chat.MOOD_NERVOUS);
        chat.setChatType(npc, chat.CHAT_YELP);
        utils.setScriptVar(player, "mtp_rescuedMeatlump", npc);
        utils.setScriptVar(npc, "mtp_rescuingPlayer", player);
        detachScript(npc, "ai.ai");
        detachScript(npc, "ai.creature_combat");
        setMovementRun(npc);
        setMaster(npc, player);
        ai_lib.aiFollow(npc, player, 2.0f, 3.0f);
        if (!utils.hasScriptVar(npc, "mtp_spawnedPhase2"))
        {
            utils.setScriptVar(npc, "mtp_spawnedPhase2", true);
            obj_id building = trial.getTop(npc);
            if (isIdValid(building))
            {
                dictionary dict = trial.getSessionDict(building);
                dict.put("triggerType", "triggerId");
                dict.put("triggerName", "mtp_escort_trapped_meatlump_p2");
                messageTo(building, "triggerFired", dict, 0.0f, false);
                dictionary webster = new dictionary();
                webster.put("player", player);
                webster.put("playerCombatLevel", getLevel(player));
                messageTo(building, "setMtpInstanceCombatLevel", webster, 1, false);
            }
        }
        return;
    }
    public void mtp_trapped_meatlump_target_action_moveToPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(npc, "mtp_cowerLoc"))
        {
            utils.removeScriptVar(npc, "mtp_cowerLoc");
        }
        if (utils.hasScriptVar(npc, "mtp_cowerYaw"))
        {
            utils.removeScriptVar(npc, "mtp_cowerYaw");
        }
        location here = getLocation(player);
        location there = new location(here.x - 1, here.y, here.z - 1, here.area, here.cell);
        setMovementWalk(npc);
        pathTo(npc, there);
    }
    public int mtp_trapped_meatlump_target_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_12"))
        {
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
            {
                mtp_trapped_meatlump_target_action_followPlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_13");
                utils.removeScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_trapped_meatlump_target_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_20");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shiver");
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_trapped_meatlump_target_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
            {
                mtp_trapped_meatlump_target_action_followPlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.mtp_trapped_meatlump_target");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmMood(self, "nervous");
        ai_lib.setMood(self, chat.MOOD_NERVOUS);
        chat.setChatMood(self, chat.MOOD_NERVOUS);
        chat.setChatType(self, chat.CHAT_YELP);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmMood(self, "nervous");
        ai_lib.setMood(self, chat.MOOD_NERVOUS);
        chat.setChatMood(self, chat.MOOD_NERVOUS);
        chat.setChatType(self, chat.CHAT_YELP);
        return SCRIPT_CONTINUE;
    }
    public int mtp_handleRescuerAttacked(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "mtp_cowerLoc"))
        {
            utils.removeScriptVar(self, "mtp_cowerLoc");
        }
        if (utils.hasScriptVar(self, "mtp_cowerYaw"))
        {
            utils.removeScriptVar(self, "mtp_cowerYaw");
        }
        if (ai_lib.isFollowing(self))
        {
            utils.setScriptVar(self, "mtp_isCowering", true);
            obj_id player = utils.getObjIdScriptVar(self, "mtp_rescuingPlayer");
            removeObjVar(self, "ai.persistantFollowing");
            stop(self);
            messageTo(self, "handlePathToFearLoc", null, 0.5f, false);
            doAnimationAction(self, "gesticulate_widly");
            ai_lib.setMood(self, chat.MOOD_SCARED);
            chat.setChatMood(self, chat.MOOD_SCARED);
            chat.setChatType(self, chat.CHAT_YELP);
            chat.chat(self, "Ahh, we're under attack!! Help!");
            sendSystemMessage(player, "Meatlump dunder yelps to you, 'Ahh, we're under attack!! Help!'", "");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePathToFearLoc(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "mtp_rescuingPlayer");
        String datatable = "datatables/theme_park/meatlump/meatlump_instance_cower.iff";
        location here = getLocation(self);
        String cellName = getCellName(here.cell);
        if (cellName == null || cellName.length() < 1)
        {
            cellName = "default";
        }
        int row = dataTableSearchColumnForString(cellName, "currentCellName", datatable);
        String destCellName = "";
        if (row > -1)
        {
            destCellName = dataTableGetString(datatable, row, "destCellName");
        }
        if (destCellName == null || destCellName.length() < 1 || destCellName.equals("none"))
        {
        }
        else 
        {
            float destX = dataTableGetFloat(datatable, row, "cellX");
            float destY = dataTableGetFloat(datatable, row, "cellY");
            float destZ = dataTableGetFloat(datatable, row, "cellZ");
            float destYaw = dataTableGetFloat(datatable, row, "cellYaw");
            obj_id destCellId = getCellId(trial.getTop(self), destCellName);
            location there = new location(destX, destY, destZ, "dungeon1", destCellId);
            utils.setScriptVar(self, "mtp_cowerLoc", there);
            utils.setScriptVar(self, "mtp_cowerYaw", destYaw);
            pathTo(self, there);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "mtp_cowerLoc"))
        {
            location destLoc = utils.getLocationScriptVar(self, "mtp_cowerLoc");
            pathTo(self, destLoc);
            utils.removeScriptVar(self, "mtp_cowerLoc");
            if (utils.hasScriptVar(self, "mtp_cowerYaw"))
            {
                float newYaw = utils.getFloatScriptVar(self, "mtp_cowerYaw");
                setYaw(self, newYaw);
                utils.removeScriptVar(self, "mtp_cowerYaw");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "mtp_cowerLoc"))
        {
            location destLoc = utils.getLocationScriptVar(self, "mtp_cowerLoc");
            pathTo(self, destLoc);
            utils.removeScriptVar(self, "mtp_cowerLoc");
            if (utils.hasScriptVar(self, "mtp_cowerYaw"))
            {
                float newYaw = utils.getFloatScriptVar(self, "mtp_cowerYaw");
                setYaw(self, newYaw);
                utils.removeScriptVar(self, "mtp_cowerYaw");
            }
        }
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
        detachScript(self, "conversation.mtp_trapped_meatlump_target");
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
        if (mtp_trapped_meatlump_target_condition_isSafe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_15");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_trapped_meatlump_target_condition_isMeatlumpFollowing(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_10");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_trapped_meatlump_target_condition_isCoweringCombat(player, npc))
        {
            doAnimationAction(npc, "catchbreath");
            string_id message = new string_id(c_stringFile, "s_14");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (mtp_trapped_meatlump_target_condition_isCowering(player, npc))
        {
            mtp_trapped_meatlump_target_action_moveToPlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_11");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12");
                }
                utils.setScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId", 4);
                npcStartConversation(player, npc, "mtp_trapped_meatlump_target", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_16");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mtp_trapped_meatlump_target_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId", 6);
                npcStartConversation(player, npc, "mtp_trapped_meatlump_target", message, responses);
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
        if (!conversationId.equals("mtp_trapped_meatlump_target"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId");
        if (branchId == 4 && mtp_trapped_meatlump_target_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && mtp_trapped_meatlump_target_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && mtp_trapped_meatlump_target_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_trapped_meatlump_target.branchId");
        return SCRIPT_CONTINUE;
    }
}

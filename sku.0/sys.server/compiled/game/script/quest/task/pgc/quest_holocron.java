package script.quest.task.pgc;

import script.*;
import script.library.*;

public class quest_holocron extends script.base_script
{
    public quest_holocron()
    {
    }
    public static final int MENU_ACTIVATE_QUEST = menu_info_types.SERVER_MENU1;
    public static final int MENU_RATE_QUEST = menu_info_types.SERVER_MENU2;
    public static final int MENU_CLAIM_REWARDS = menu_info_types.SERVER_MENU3;
    public static final int MENU_VIEW_REWARDS = menu_info_types.SERVER_MENU4;
    public static final int MENU_EDIT_RECIPE = menu_info_types.SERVER_MENU5;
    public static final int MENU_SHARE_QUEST = menu_info_types.SERVER_MENU6;
    public static final int MENU_BUILD_HOLOCRON = menu_info_types.SERVER_MENU7;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleCheckCompletedHolocron", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCheckCompletedHolocron(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int rootMenu = 0;
        obj_id playerInventory = utils.getInventoryContainer(player);
        if (utils.isNestedWithin(self, playerInventory))
        {
            if (!hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR))
            {
                obj_id creatorId = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                if (isPlayerQuestRecipe(self))
                {
                    if (isIdValid(creatorId) && player == creatorId)
                    {
                        rootMenu = menuInfo.addRootMenu(MENU_ACTIVATE_QUEST, new string_id("saga_system", "holocron_menu_test_recipe"));
                        if (hasRewardItems(self))
                        {
                            rootMenu = menuInfo.addRootMenu(MENU_VIEW_REWARDS, new string_id("saga_system", "holocron_menu_view_rewards"));
                        }
                        rootMenu = menuInfo.addRootMenu(MENU_EDIT_RECIPE, new string_id("saga_system", "holocron_menu_edit_recipe"));
                        rootMenu = menuInfo.addRootMenu(MENU_BUILD_HOLOCRON, new string_id("saga_system", "holocron_menu_build_holocron"));
                    }
                }
                else 
                {
                    rootMenu = menuInfo.addRootMenu(MENU_ACTIVATE_QUEST, new string_id("saga_system", "holocron_menu_activate"));
                    if (hasRewardItems(self) || (isIdValid(creatorId) && player == creatorId))
                    {
                        rootMenu = menuInfo.addRootMenu(MENU_VIEW_REWARDS, new string_id("saga_system", "holocron_menu_view_rewards"));
                    }
                }
            }
            else 
            {
                obj_id questFinisher = getObjIdObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR);
                if (isIdValid(questFinisher) && questFinisher == player)
                {
                    if (!hasObjVar(self, pgc_quests.PCG_QUEST_WAS_RATED_OBJVAR))
                    {
                        rootMenu = menuInfo.addRootMenu(MENU_RATE_QUEST, new string_id("saga_system", "holocron_menu_rating"));
                    }
                    else if (hasRewardItems(self))
                    {
                        obj_id[] rewardItems = getContents(self);
                        if ((rewardItems != null && rewardItems.length > 0))
                        {
                            rootMenu = menuInfo.addRootMenu(MENU_CLAIM_REWARDS, new string_id("saga_system", "holocron_menu_reward"));
                        }
                    }
                }
            }
            if (pgc_quests.canSharePgcQuest(self))
            {
                rootMenu = menuInfo.addRootMenu(MENU_SHARE_QUEST, new string_id("saga_system", "holocron_menu_share_quest"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int menu_item) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        if (utils.isNestedWithin(self, playerInventory))
        {
            if (menu_item == MENU_ACTIVATE_QUEST)
            {
                if (!hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR))
                {
                    if (pgc_quests.hasMaxActivatedQuests(player))
                    {
                        string_id message = new string_id("saga_system", "holocron_has_max_quests");
                        prose_package pp = prose.getPackage(message, player, player);
                        prose.setDI(pp, pgc_quests.PGC_QUEST_MAX_NUM_QUESTS);
                        sendSystemMessageProse(player, pp);
                    }
                    else 
                    {
                        if (pgc_quests.activateQuestHolocron(self, player))
                        {
                            string_id message = new string_id("saga_system", "holocron_quest_activated");
                            prose_package pp = prose.getPackage(message, player, player);
                            prose.setTO(pp, pgc_quests.getQuestName(self));
                            sendSystemMessageProse(player, pp);
                            int currentTime = getCalendarTime();
                            setObjVar(self, pgc_quests.PCG_QUEST_ACTIVATED_TIME, currentTime);
                        }
                    }
                }
            }
            else if (menu_item == MENU_RATE_QUEST)
            {
                if (hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR))
                {
                    obj_id questFinisher = getObjIdObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR);
                    if (isIdValid(questFinisher) && questFinisher == player)
                    {
                        if (!hasObjVar(self, pgc_quests.PCG_QUEST_WAS_RATED_OBJVAR))
                        {
                            pgc_quests.showQuestRatingUI(self, player);
                        }
                    }
                }
            }
            else if (menu_item == MENU_CLAIM_REWARDS)
            {
                if (hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR) && hasRewardItems(self))
                {
                    obj_id questFinisher = getObjIdObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR);
                    if (isIdValid(questFinisher) && questFinisher == player)
                    {
                        openQuestHolocron(self, player);
                        sendDirtyObjectMenuNotification(self);
                    }
                }
            }
            else if (menu_item == MENU_VIEW_REWARDS)
            {
                obj_id creatorId = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                if (hasRewardItems(self) || (isIdValid(creatorId) && player == creatorId))
                {
                    openQuestHolocron(self, player);
                }
            }
            else if (menu_item == MENU_EDIT_RECIPE)
            {
                obj_id creatorId = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                if (isIdValid(creatorId) && player == creatorId)
                {
                    if (isPlayerQuestRecipe(self))
                    {
                        openPlayerQuestRecipe(player, self);
                    }
                }
            }
            else if (menu_item == MENU_BUILD_HOLOCRON)
            {
                obj_id creatorId = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                if (isIdValid(creatorId) && player == creatorId)
                {
                    if (isPlayerQuestRecipe(self))
                    {
                        dictionary taskDictionary = pgc_quests.recreateQuestTaskDictionary(self);
                        if (taskDictionary != null && !taskDictionary.isEmpty())
                        {
                            messageTo(player, "handlePgcHolocronCreation", taskDictionary, 1, false);
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("saga_system", "holocron_quest_failed"));
                        }
                    }
                }
            }
            else if (menu_item == MENU_SHARE_QUEST)
            {
                if (pgc_quests.canSharePgcQuest(self))
                {
                    obj_id target = getIntendedTarget(player);
                    if (!isIdValid(target))
                    {
                        target = getLookAtTarget(player);
                    }
                    if (!isIdValid(target))
                    {
                        sendSystemMessage(player, new string_id("saga_system", "holocron_share_quest_no_target"));
                        return SCRIPT_CONTINUE;
                    }
                    if (!isPlayer(target))
                    {
                        sendSystemMessage(player, new string_id("saga_system", "holocron_share_quest_not_player"));
                        return SCRIPT_CONTINUE;
                    }
                    if (hasObjVar(self, pgc_quests.PCG_QUEST_HOLOCRON_SHARED_WITH_LIST))
                    {
                        obj_id[] sharedWithList = getObjIdArrayObjVar(self, pgc_quests.PCG_QUEST_HOLOCRON_SHARED_WITH_LIST);
                        if (sharedWithList != null && sharedWithList.length > 0)
                        {
                            for (obj_id alreadySharedWith : sharedWithList) {
                                if (target == alreadySharedWith) {
                                    string_id message = new string_id("saga_system", "holocron_share_quest_already_shared");
                                    prose_package pp = prose.getPackage(message, player, player);
                                    prose.setTO(pp, getName(target));
                                    sendSystemMessageProse(player, pp);
                                    return SCRIPT_CONTINUE;
                                }
                            }
                        }
                    }
                    if (target == player)
                    {
                    }
                    else 
                    {
                        boolean sameGroup = false;
                        boolean sameGuild = false;
                        boolean sameStory = false;
                        if (group.inSameGroup(player, target))
                        {
                            sameGroup = true;
                        }
                        if (guild.inSameGuild(player, target))
                        {
                            sameGuild = true;
                        }
                        if (storyteller.inSameStory(player, target))
                        {
                            sameStory = true;
                        }
                        if (!sameGroup && !sameGuild && !sameStory)
                        {
                            sendSystemMessage(player, new string_id("saga_system", "holocron_share_quest_not_group_guild_story"));
                            return SCRIPT_CONTINUE;
                        }
                    }
                    if (utils.hasScriptVar(target, "chronicles.offeredSharedHolocron"))
                    {
                        int oldPid = utils.getIntScriptVar(target, "chronicles.offeredSharedHolocron");
                        forceCloseSUIPage(oldPid);
                    }
                    utils.setScriptVar(player, target + ".sharedHolocron", self);
                    dictionary webster = new dictionary();
                    webster.put("sharingPlayer", player);
                    webster.put("sharingPlayerName", getName(player));
                    webster.put("sharedQuestName", pgc_quests.getQuestName(self));
                    messageTo(target, "handleSharedPgcHolocronOffer", webster, 1, false);
                    string_id message = new string_id("saga_system", "holocron_share_quest_sharing");
                    prose_package pp = prose.getPackage(message, player, player);
                    prose.setTO(pp, getName(target));
                    sendSystemMessageProse(player, pp);
                    sendDirtyObjectMenuNotification(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasRewardItems(obj_id questHolocron) throws InterruptedException
    {
        obj_id[] rewardItems = getContents(questHolocron);
        if ((rewardItems != null && rewardItems.length > 0))
        {
            return true;
        }
        return false;
    }
    public boolean openQuestHolocron(obj_id questHolocron, obj_id player) throws InterruptedException
    {
        return queueCommand(player, (1880585606), questHolocron, "", COMMAND_PRIORITY_DEFAULT);
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id questCreatorId = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
        String questCreatorName = getStringObjVar(self, pgc_quests.PCG_QUEST_CREATOR_NAME_OBJVAR);
        String questName = pgc_quests.getQuestName(self);
        String questDescription = pgc_quests.getQuestDescription(self);
        int questLevel = getIntObjVar(self, pgc_quests.PCG_QUEST_LEVEL_OBJVAR);
        String questGroupSetting = getStringObjVar(self, pgc_quests.PCG_QUEST_DIFFICULTY_OBJVAR);
        boolean requiresKashyyyk = getBooleanObjVar(self, pgc_quests.PCG_QUEST_NEED_KASHYYYK_OBJVAR);
        boolean requiresMustafar = getBooleanObjVar(self, pgc_quests.PCG_QUEST_NEED_MUSTAFAR_OBJVAR);
        boolean involvesPvp = getBooleanObjVar(self, pgc_quests.PCG_QUEST_INVOLVES_PVP_OBJVAR);
        boolean involvesCrafting = getBooleanObjVar(self, pgc_quests.PCG_QUEST_INVOLVES_CRAFT_OBJVAR);
        boolean involvesEntertaining = getBooleanObjVar(self, pgc_quests.PCG_QUEST_INVOLVES_ENTERTAIN_OBJVAR);
        boolean involvesCombat = getBooleanObjVar(self, pgc_quests.PCG_QUEST_INVOLVES_COMBAT_OBJVAR);
        if (questCreatorName != null && questCreatorName.length() > 0)
        {
            names[idx] = "pgc_quest_creator";
            attribs[idx] = "\\#0099FF" + questCreatorName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (isIdValid(questCreatorId))
        {
            int[] chroniclerRating = pgcGetRating(questCreatorId);
            if (chroniclerRating != null && chroniclerRating.length > 0)
            {
                int ratingTotal = chroniclerRating[PGC_INFO_INDEX_TOTAL_RATING_VALUE];
                int ratingCount = chroniclerRating[PGC_INFO_INDEX_TOTAL_RATING_COUNT];
                int lastRatedTime = chroniclerRating[PGC_INFO_INDEX_MOST_RECENT_RATING_TIME];
                if (ratingTotal > 0)
                {
                    float currentRating = pgc_quests.getCurrentPgcRating(ratingTotal, ratingCount);
                    names[idx] = "pgc_quest_chronicler_rating";
                    attribs[idx] = "" + currentRating;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    names[idx] = "pgc_quest_chronicler_rating_count";
                    attribs[idx] = "" + ratingCount;
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    names[idx] = "pgc_quest_chronicler_last_rated";
                    attribs[idx] = "\n \\#62FF15" + utils.formatTimeVerbose(getCalendarTime() - lastRatedTime);
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    names[idx] = "pgc_quest_chronicler_rating";
                    attribs[idx] = "Not Yet Rated";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            else 
            {
                names[idx] = "pgc_quest_chronicler_rating";
                attribs[idx] = "Not Yet Rated";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        names[idx] = "pgc_quest_name";
        attribs[idx] = questName;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "pgc_quest_desc";
        attribs[idx] = questDescription;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "pgc_quest_level";
        attribs[idx] = "" + questLevel;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "pgc_quest_group";
        attribs[idx] = "\\#FF3300" + questGroupSetting;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, pgc_quests.PCG_QUEST_CAN_SHARE_MAX_OBJVAR))
        {
            int shareLimit = 0;
            if (hasObjVar(self, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR))
            {
                shareLimit = getIntObjVar(self, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR);
            }
            int shareLimitMax = getIntObjVar(self, pgc_quests.PCG_QUEST_CAN_SHARE_MAX_OBJVAR);
            names[idx] = "pgc_quest_can_share";
            attribs[idx] = "\\#0099FF" + shareLimit + " of " + shareLimitMax;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (involvesPvp)
        {
            names[idx] = "pgc_quest_involves_pvp";
            attribs[idx] = "\\#FF3300true";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (involvesCrafting)
        {
            names[idx] = "pgc_quest_involves_crafting";
            attribs[idx] = "\\#FF3300true";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (involvesEntertaining)
        {
            names[idx] = "pgc_quest_involves_entertaining";
            attribs[idx] = "\\#FF3300true";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (involvesCombat)
        {
            names[idx] = "pgc_quest_involves_combat";
            attribs[idx] = "\\#FF3300true";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (requiresKashyyyk)
        {
            names[idx] = "pgc_quest_kashyyyk";
            attribs[idx] = "\\#FF3300true";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (requiresMustafar)
        {
            names[idx] = "pgc_quest_mustafar";
            attribs[idx] = "\\#FF3300true";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void areaDebugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(getTopMostContainer(self)), 35.0f);
        if (players != null && players.length > 0)
        {
            for (obj_id player : players) {
                sendSystemMessage(player, message, "");
            }
        }
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR))
        {
            return SCRIPT_OVERRIDE;
        }
        if (isPlayerQuestRecipe(self))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id datapad = utils.getPlayerDatapad(self);
        if (isIdValid(datapad) && utils.isNestedWithin(self, datapad))
        {
            return SCRIPT_OVERRIDE;
        }
        if (isIdValid(transferer) && isPlayer(transferer))
        {
            obj_id questCreator = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
            if (!isIdValid(questCreator))
            {
                return SCRIPT_OVERRIDE;
            }
            if (questCreator != transferer)
            {
                sendSystemMessage(transferer, new string_id("saga_system", "holocron_rewards_cannot_place_item"));
                return SCRIPT_OVERRIDE;
            }
            else 
            {
                obj_id[] holocronContents = getContents(self);
                if (holocronContents != null)
                {
                    if (holocronContents.length >= 0)
                    {
                        int rewardCount = 0;
                        for (obj_id holocronContent : holocronContents) {
                            String template = getTemplateName(holocronContent);
                            if (!template.equals(pgc_quests.PGC_CASH_ITEM_TEMPLATE)) {
                                ++rewardCount;
                            }
                        }
                        if (rewardCount >= pgc_quests.PGC_MAX_NUM_PLAYER_REWARDS)
                        {
                            sendSystemMessage(transferer, new string_id("saga_system", "holocron_rewards_already_max"));
                            return SCRIPT_OVERRIDE;
                        }
                        if (!pgc_quests.isEligiblePgcReward(item))
                        {
                            prose_package pp = null;
                            string_id name = getNameStringId(item);
                            if (name == null)
                            {
                                name = new string_id("saga_system", "holocron_reward_unknown");
                            }
                            pp = prose.getPackage(new string_id("saga_system", "holocron_reward_ineligible_item"), name);
                            sendSystemMessageProse(transferer, pp);
                            return SCRIPT_OVERRIDE;
                        }
                    }
                }
            }
            pgc_quests.logReward(transferer, self, "Holocron Creator added a reward to this quest holocron: " + getNameStringId(item) + "(" + item + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "chronicles.allowRewardsReclaimed"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(destContainer))
        {
            if (isIdValid(transferer) && isPlayer(transferer))
            {
                sendSystemMessage(transferer, new string_id("saga_system", "holocron_rewards_cannot_take_item"));
            }
            return SCRIPT_OVERRIDE;
        }
        if (isIdValid(transferer) && isPlayer(transferer))
        {
            if (hasObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR))
            {
                obj_id questFinisher = getObjIdObjVar(self, pgc_quests.PCG_QUEST_COMPLETE_OBJVAR);
                if (!isIdValid(questFinisher))
                {
                    return SCRIPT_OVERRIDE;
                }
                if (questFinisher != transferer)
                {
                    sendSystemMessage(transferer, new string_id("saga_system", "holocron_rewards_cannot_take_item"));
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    pgc_quests.logReward(transferer, self, "Holocron was completed and the player who finished it is taking a player given reward: " + getNameStringId(item) + "(" + item + ")");
                }
            }
            else if (hasObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR))
            {
                obj_id questCreator = getObjIdObjVar(self, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                if (!isIdValid(questCreator))
                {
                    return SCRIPT_OVERRIDE;
                }
                if (questCreator != transferer)
                {
                    sendSystemMessage(transferer, new string_id("saga_system", "holocron_rewards_cannot_take_item"));
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    pgc_quests.logReward(transferer, self, "Holocron Creator is removing a reward they had added: " + getNameStringId(item) + "(" + item + ")");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestHolocronActivated(obj_id self, dictionary params) throws InterruptedException
    {
        pgc_quests.handlePhaseActived(self, 0);
        return SCRIPT_CONTINUE;
    }
    public int handleQuestHolocronInitializeTaskStatus(obj_id self, dictionary params) throws InterruptedException
    {
        int numTasks = getIntObjVar(self, pgc_quests.PCG_QUEST_NUM_TASKS_OBJVAR);
        pgc_quests.initializeQuestTasksStatus(self, numTasks);
        return SCRIPT_CONTINUE;
    }
    public int handleQuestHolocronAbandoned(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            string_id message = new string_id("saga_system", "qcd_quest_abandoned");
            prose_package pp = prose.getPackage(message, player, player);
            prose.setTO(pp, pgc_quests.getQuestName(self));
            sendSystemMessageProse(player, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHolocronSharedSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id latestSharedWith = params.getObjId("sharedWith");
            int shareLimitMax = getIntObjVar(self, pgc_quests.PCG_QUEST_CAN_SHARE_MAX_OBJVAR);
            obj_id[] sharedWithList = new obj_id[shareLimitMax];
            if (hasObjVar(self, pgc_quests.PCG_QUEST_HOLOCRON_SHARED_WITH_LIST))
            {
                sharedWithList = getObjIdArrayObjVar(self, pgc_quests.PCG_QUEST_HOLOCRON_SHARED_WITH_LIST);
            }
            else 
            {
                for (int i = 0; i < shareLimitMax; i++)
                {
                    sharedWithList[i] = obj_id.getObjId(1);
                }
            }
            for (int k = 0; k < sharedWithList.length; k++)
            {
                obj_id previousShared = sharedWithList[k];
                if (previousShared == obj_id.getObjId(1))
                {
                    sharedWithList[k] = latestSharedWith;
                    break;
                }
            }
            setObjVar(self, pgc_quests.PCG_QUEST_HOLOCRON_SHARED_WITH_LIST, sharedWithList);
        }
        return SCRIPT_CONTINUE;
    }
    public int receiveCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        String phaseString = pgc_quests.getActivePhaseObjVarString(self);
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = pgc_quests.getTaskObjVarString(i);
            for (int j = 0; j < pgc_quests.CREDIT_FOR_KILL_TASKS.length; j++)
            {
                String taskType = pgc_quests.getActiveTaskTaskType(self, phaseString, taskString);
                if (taskType.length() > 0 && taskType.equals(pgc_quests.CREDIT_FOR_KILL_TASKS[j]))
                {
                    pgc_quests.checkForKillTaskCredit(self, taskType, phaseString, taskString, params);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int recivedGcwCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        String phaseString = pgc_quests.getActivePhaseObjVarString(self);
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = pgc_quests.getTaskObjVarString(i);
            String taskType = pgc_quests.getActiveTaskTaskType(self, phaseString, taskString);
            if (taskType.length() > 0 && taskType.equals(pgc_quests.SAGA_PVP_OBJECTIVE))
            {
                pgc_quests.handlePvpPlayerKillCredit(self, taskType, phaseString, taskString, params);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleCommMessageTaskCompletion(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id questControlDevice = getContainedBy(self);
        if (isIdValid(questControlDevice) && !(getTemplateName(questControlDevice)).equals(pgc_quests.PGC_QUEST_CONTROL_DEVICE_TEMPLATE))
        {
            return SCRIPT_CONTINUE;
        }
        String phaseString = params.getString("phaseString");
        String taskString = params.getString("taskString");
        if (phaseString != null && phaseString.length() > 0 && taskString != null && taskString.length() > 0)
        {
            pgc_quests.setTaskComplete(self, phaseString, taskString);
        }
        return SCRIPT_CONTINUE;
    }
    public int ChroniclesMessageBoxCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        int pageId = params.getInt("pageId");
        obj_id questControlDevice = getContainedBy(self);
        if (isIdValid(questControlDevice) && !(getTemplateName(questControlDevice)).equals(pgc_quests.PGC_QUEST_CONTROL_DEVICE_TEMPLATE))
        {
            utils.removeScriptVar(self, pgc_quests.getPhaseStringVarName(pageId, "message_box"));
            utils.removeScriptVar(self, pgc_quests.getTaskStringVarName(pageId, "message_box"));
            return SCRIPT_CONTINUE;
        }
        String phaseString = utils.getStringScriptVar(self, pgc_quests.getPhaseStringVarName(pageId, "message_box"));
        String taskString = utils.getStringScriptVar(self, pgc_quests.getTaskStringVarName(pageId, "message_box"));
        utils.removeScriptVar(self, pgc_quests.getPhaseStringVarName(pageId, "message_box"));
        utils.removeScriptVar(self, pgc_quests.getTaskStringVarName(pageId, "message_box"));
        if (phaseString != null && phaseString.length() > 0 && taskString != null && taskString.length() > 0)
        {
            pgc_quests.setTaskComplete(self, phaseString, taskString);
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = pgc_quests.getQuestPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String phaseString = pgc_quests.getActivePhaseObjVarString(self);
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = pgc_quests.getTaskObjVarString(i);
            String taskType = pgc_quests.getActiveTaskTaskType(self, phaseString, taskString);
            if (taskType.length() > 0 && taskType.equals(pgc_quests.SAGA_PERFORM))
            {
                String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseString, taskString);
                String relicName = pgc_quests.getRelicName(self, phaseString, taskString);
                String[] relicData = pgc_quests.getCollectionSlotCategoryInfo(relicName);
                int desiredPerformType = pgc_quests.getIntPgcTaskData(self, baseObjVar, "what", relicData);
                int currentPerformType = params.getInt(groundquests.PERFORM_TYPE);
                boolean rightType = desiredPerformType == 0 || desiredPerformType == currentPerformType;
                if (!rightType)
                {
                    continue;
                }
                boolean rightLocation = true;
                String cellname = pgc_quests.getStringPgcTaskData(self, baseObjVar, "cell_name", relicData);
                if (cellname != null && cellname.length() != 0)
                {
                    location playerLocation = getLocation(player);
                    rightLocation = (getCellName(playerLocation.cell)).equals(cellname);
                }
                else 
                {
                    obj_id building = pgc_quests.getObjIdRelicData("building_id", relicData);
                    rightLocation = performance.isInRightBuilding(player, building);
                }
                if (!rightLocation)
                {
                    continue;
                }
                int performDuration = pgc_quests.getIntPgcTaskData(self, baseObjVar, "duration", relicData);
                if (performDuration <= 0)
                {
                    performDuration = 10;
                }
                int performEndTime = performDuration + performGetTime(player);
                utils.setScriptVar(self, baseObjVar + ".performEndTime", performEndTime);
                utils.setScriptVar(self, baseObjVar + ".performCurrentlyPerforming", true);
                sendCheckPerformanceComplete(self, phaseString, taskString, performDuration);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = pgc_quests.getQuestPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String phaseString = pgc_quests.getActivePhaseObjVarString(self);
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = pgc_quests.getTaskObjVarString(i);
            String taskType = pgc_quests.getActiveTaskTaskType(self, phaseString, taskString);
            if (taskType.length() > 0 && taskType.equals(pgc_quests.SAGA_PERFORM))
            {
                clearPerformScriptVars(self, phaseString, taskString);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int CheckPerformanceComplete(obj_id self, dictionary params) throws InterruptedException
    {
        String phaseString = params.getString("phaseString");
        String taskString = params.getString("taskString");
        if (!pgc_quests.isTaskComplete(self, phaseString, taskString))
        {
            String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseString, taskString);
            boolean isCurrentlyPerforming = false;
            if (utils.hasScriptVar(self, baseObjVar + ".performCurrentlyPerforming"))
            {
                isCurrentlyPerforming = utils.getBooleanScriptVar(self, baseObjVar + ".performCurrentlyPerforming");
            }
            if (isCurrentlyPerforming)
            {
                if (utils.hasScriptVar(self, baseObjVar + ".performEndTime"))
                {
                    int endTime = utils.getIntScriptVar(self, baseObjVar + ".performEndTime");
                    int remainingTime = endTime - performGetTime(self);
                    if (endTime > 0 && remainingTime <= 1)
                    {
                        pgc_quests.setTaskComplete(self, phaseString, taskString);
                    }
                    else 
                    {
                        sendCheckPerformanceComplete(self, phaseString, taskString, remainingTime);
                    }
                }
            }
            else 
            {
                clearPerformScriptVars(self, phaseString, taskString);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int performGetTime(obj_id player) throws InterruptedException
    {
        return getPlayerPlayedTime(player);
    }
    public void sendCheckPerformanceComplete(obj_id questHolocron, String phaseString, String taskString, int delay) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("phaseString", phaseString);
        params.put("taskString", taskString);
        messageTo(questHolocron, "CheckPerformanceComplete", params, delay, false);
        return;
    }
    public void clearPerformScriptVars(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseString, taskString);
        utils.removeScriptVar(questHolocron, baseObjVar + ".performEndTime");
        utils.removeScriptVar(questHolocron, baseObjVar + ".performCurrentlyPerforming");
        return;
    }
    public int OnCraftedPrototype(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = pgc_quests.getQuestPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String phaseString = pgc_quests.getActivePhaseObjVarString(self);
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = pgc_quests.getTaskObjVarString(i);
            String taskType = pgc_quests.getActiveTaskTaskType(self, phaseString, taskString);
            if (taskType.length() > 0 && taskType.equals(pgc_quests.SAGA_CRAFT_ITEM))
            {
                obj_id prototypeObject = params.getObjId("prototypeObject");
                if (isIdValid(prototypeObject) && exists(prototypeObject))
                {
                    String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseString, taskString);
                    String relicName = pgc_quests.getRelicName(self, phaseString, taskString);
                    String[] relicData = pgc_quests.getCollectionSlotCategoryInfo(relicName);
                    String prototypeObjectTemplate = getTemplateName(prototypeObject);
                    int prototypeObjectTemplateCrc = getObjectTemplateCrc(prototypeObjectTemplate);
                    String targetCraftedObjectTemplateName = pgc_quests.getStringPgcTaskData(self, baseObjVar, "template", relicData);
                    int targetCraftedObjectTemplateCrc = getObjectTemplateCrc(targetCraftedObjectTemplateName);
                    if (targetCraftedObjectTemplateCrc != 0)
                    {
                        if (targetCraftedObjectTemplateCrc == prototypeObjectTemplateCrc)
                        {
                            int targetCount = pgc_quests.getIntPgcTaskData(self, baseObjVar, "count", relicData);
                            int currentCount = pgc_quests.incrementTaskCounter(self, baseObjVar);
                            sendSystemMessage(player, pgc_quests.getQuestName(self) + ": Object Crafted! " + currentCount + "/" + targetCount, "");
                            play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
                            if (currentCount >= targetCount)
                            {
                                pgc_quests.setTaskComplete(self, phaseString, taskString);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int pqOnArrivedAtLocation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = pgc_quests.getQuestPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String phaseString = pgc_quests.getActivePhaseObjVarString(self);
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = pgc_quests.getTaskObjVarString(i);
            String taskType = pgc_quests.getActiveTaskTaskType(self, phaseString, taskString);
            if (taskType.length() > 0 && taskType.equals(pgc_quests.SAGA_GOTO_LOCATION))
            {
                String locationName = params.getString("locationName");
                if (locationName != null && locationName.length() > 0)
                {
                    String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseString, taskString);
                    String relicName = pgc_quests.getRelicName(self, phaseString, taskString);
                    if (locationName.equals(relicName))
                    {
                        pgc_quests.setTaskComplete(self, phaseString, taskString);
                        dictionary webster = new dictionary();
                        webster.put("locationName", locationName);
                        messageTo(player, "playerQuestRemoveLocationTarget", webster, 0, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

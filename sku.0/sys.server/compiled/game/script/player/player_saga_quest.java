package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.groundquests;
import script.library.loot;
import script.library.money;
import script.library.pgc_quests;
import script.library.prose;
import script.library.skill_template;
import script.library.static_item;
import script.library.stealth;
import script.library.sui;
import script.library.utils;
import script.library.xp;

public class player_saga_quest extends script.base_script
{
    public player_saga_quest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleChronicleProfessionGranted", null, 14, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleChronicleProfessionGranted", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            if (!hasObjVar(self, "chroniclesTermsOfServiceShown"))
            {
                messageTo(self, "handleChroniclesTermsOfService", null, 2, false);
            }
            messageTo(self, "handleChroniclesReserveReminder", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChroniclesTermsOfService(obj_id self, dictionary params) throws InterruptedException
    {
        string_id prompt_sid = new string_id("saga_system", "pgc_terms_of_service_text");
        string_id title_sid = new string_id("saga_system", "pgc_terms_of_service_title");
        int messageBoxSizeWidth = 484;
        int messageBoxSizeHeight = 312;
        int messageBoxLocationX = 320;
        int messageBoxLocationY = 256;
        int pageId = sui.createSUIPage(sui.SUI_MSGBOX, self, self, "ChroniclesTermsOfServiceCompleted");
        sui.setAutosaveProperty(pageId, false);
        sui.setSizeProperty(pageId, messageBoxSizeWidth, messageBoxSizeHeight);
        sui.setLocationProperty(pageId, messageBoxLocationX, messageBoxLocationY);
        setSUIProperty(pageId, sui.MSGBOX_TITLE, sui.PROP_TEXT, "@" + title_sid);
        setSUIProperty(pageId, sui.MSGBOX_PROMPT, sui.PROP_TEXT, "@" + prompt_sid);
        sui.msgboxButtonSetup(pageId, sui.OK_ONLY);
        sui.showSUIPage(pageId);
        setObjVar(self, "chroniclesTermsOfServiceShown", getCalendarTime());
        pgc_quests.logProgression(self, obj_id.NULL_ID, "Player shown Chronicles Terms of Service window: " + utils.formatTimeVerbose(getCalendarTime()));
        return SCRIPT_CONTINUE;
    }
    public int handleChroniclesReserveReminder(obj_id self, dictionary params) throws InterruptedException
    {
        int[] pgcRatingData = pgcGetRatingData(self);
        if (pgcRatingData != null && pgcRatingData.length > 0)
        {
            int storedXp = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_XP_INDEX];
            storedXp = pgc_quests.getConfigModifiedChroniclesXPAmount(storedXp);
            boolean completedChroniclesProgression = false;
            String[] chronicleSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
            if (chronicleSkills != null && chronicleSkills.length > 0)
            {
                String finalChronicleSkill = chronicleSkills[chronicleSkills.length - 1];
                if (hasSkill(self, finalChronicleSkill))
                {
                    completedChroniclesProgression = true;
                }
            }
            int storedSilverTokens = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_SILVER_TOKENS_INDEX];
            int storedGoldTokens = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_GOLD_TOKENS_INDEX];
            if ((storedXp > 0 && !completedChroniclesProgression) || storedSilverTokens > 0 || storedGoldTokens > 0)
            {
                sendSystemMessage(self, new string_id("saga_system", "chronicles_reserve_client_ready_reminder"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChronicleProfessionGranted(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasSkill(self, pgc_quests.PGC_CHRONICLES_STARTING_SKILL))
        {
            grantSkill(self, pgc_quests.PGC_CHRONICLES_STARTING_SKILL);
            pgc_quests.logProgression(self, obj_id.NULL_ID, "Granting Starting Chronicles Profession skill: " + pgc_quests.PGC_CHRONICLES_STARTING_SKILL);
        }
        if (!utils.hasScriptVar(self, "chroniclesRewards.alreadyChecking"))
        {
            utils.setScriptVar(self, "chroniclesRewards.alreadyChecking", true);
            messageTo(self, "checkForMissedRoadmapRewards", null, 4, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForMissedRoadmapRewards(obj_id self, dictionary params) throws InterruptedException
    {
        String[] chronicleSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
        if (chronicleSkills != null && chronicleSkills.length > 0)
        {
            for (int i = 0; i < chronicleSkills.length; i++)
            {
                String skillName = chronicleSkills[i];
                if (hasSkill(self, skillName))
                {
                    if (hasObjVar(self, pgc_quests.PGC_GRANTED_ROADMAP_REWARDS_OBJVAR))
                    {
                        int[] getGrantedRewardsArray = getIntArrayObjVar(self, pgc_quests.PGC_GRANTED_ROADMAP_REWARDS_OBJVAR);
                        int alreadyHasReward = getGrantedRewardsArray[i];
                        if (alreadyHasReward <= -1)
                        {
                            pgc_quests.grantChroniclesRoadmapItem(self, skillName, i);
                        }
                    }
                    else 
                    {
                        pgc_quests.grantChroniclesRoadmapItem(self, skillName, i);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreateSaga(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            dictionary taskDictionary = params.getDictionary("taskDictionary");
            if (taskDictionary != null && !taskDictionary.isEmpty())
            {
                boolean isRecipe = false;
                if (taskDictionary.containsKey("recipe"))
                {
                    isRecipe = taskDictionary.getBoolean("recipe");
                }
                if (isRecipe)
                {
                    showHolocronCreationCountdownUi(self, taskDictionary, "holocron_creation_recipe_countdown");
                }
                else 
                {
                    showHolocronCreationCountdownUi(self, taskDictionary, "holocron_creation_countdown");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSharedPgcHolocronOffer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id sharingPlayer = params.getObjId("sharingPlayer");
            String sharingPlayerName = params.getString("sharingPlayerName");
            String questName = params.getString("sharedQuestName");
            String title = utils.packStringId(new string_id("saga_system", "holocron_share_offer_title"));
            prose_package pp = prose.getPackage(new string_id("saga_system", "holocron_share_offer_prompt"));
            prose.setTO(pp, sharingPlayerName);
            prose.setTT(pp, questName);
            String msg = "\0" + packOutOfBandProsePackage(null, pp);
            int pid = sui.msgbox(sharingPlayer, self, msg, 2, title, sui.YES_NO, "handleSharedChroniclesQuestResponse");
            utils.setScriptVar(self, "chronicles.offeredSharedHolocron", pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSharedChroniclesQuestResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id player = sui.getPlayerId(params);
            int btn = sui.getIntButtonPressed(params);
            if (!isIdValid(player))
            {
                return SCRIPT_CONTINUE;
            }
            if (btn == sui.BP_CANCEL)
            {
                prose_package pp = prose.getPackage(new string_id("saga_system", "holocron_share_quest_declined"), self, self);
                prose.setTO(pp, getName(player));
                sendSystemMessageProse(self, pp);
                if (utils.hasScriptVar(player, "chronicles.offeredSharedHolocron"))
                {
                    utils.removeScriptVar(player, "chronicles.offeredSharedHolocron");
                }
                return SCRIPT_CONTINUE;
            }
            obj_id questHolocron = utils.getObjIdScriptVar(self, player + ".sharedHolocron");
            utils.removeScriptVar(self, player + ".sharedHolocron");
            if (isIdValid(questHolocron) && exists(questHolocron))
            {
                if (pgc_quests.canSharePgcQuest(questHolocron))
                {
                    dictionary taskDictionary = pgc_quests.recreateSharedTaskDictionary(questHolocron);
                    if (taskDictionary != null && !taskDictionary.isEmpty())
                    {
                        int newShareLimit = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR) - 1;
                        if (newShareLimit <= 0)
                        {
                            removeObjVar(questHolocron, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR);
                        }
                        else 
                        {
                            setObjVar(questHolocron, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR, newShareLimit);
                        }
                        obj_id questCreatorId = getObjIdObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                        String questCreatorName = getStringObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_NAME_OBJVAR);
                        int questCreatorStationId = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_STATIONID_OBJVAR);
                        taskDictionary.put("quest_creator_id", questCreatorId);
                        taskDictionary.put("quest_creator_name", questCreatorName);
                        taskDictionary.put("quest_creator_station_id", questCreatorStationId);
                        taskDictionary.put("quest_sharer_id", self);
                        taskDictionary.put("quest_sharer_name", getName(self));
                        taskDictionary.put("quest_shared_holocron", questHolocron);
                        showSharedHolocronCreationCountdownUi(self, player, taskDictionary, "holocron_creation_shared_countdown");
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("saga_system", "holocron_share_quest_failed"));
                    }
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("saga_system", "holocron_share_quest_failed"));
            }
            if (utils.hasScriptVar(player, "chronicles.offeredSharedHolocron"))
            {
                utils.removeScriptVar(player, "chronicles.offeredSharedHolocron");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void showSharedHolocronCreationCountdownUi(obj_id creator, obj_id sharedTo, dictionary taskDictionary, String menuString) throws InterruptedException
    {
        string_id menuStringId = new string_id("saga_system", menuString);
        String handler = "handleFakePgcHolocronCreationCountdownTimer";
        int countdownTimer = 3;
        int startTime = 0;
        int range = 3;
        int flags = 0;
        stealth.testInvisNonCombatAction(creator, creator);
        stealth.testInvisNonCombatAction(sharedTo, sharedTo);
        int countdownSui = sui.smartCountdownTimerSUI(creator, creator, "pgc_holocron_creation", menuStringId, startTime, countdownTimer, handler, range, flags);
        transform offset = transform.identity.setPosition_p(0.0f, -0.2f, -0.6f);
        playClientEffectObj(creator, "appearance/pt_pgc_holocron_shared.prt", creator, "", offset);
        doAnimationAction(creator, "medium");
        messageTo(sharedTo, "handleSharedPgcHolocronCreation", taskDictionary, 3, false);
        return;
    }
    public int handleSharedPgcHolocronCreation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            createChronicleQuestObject(self, params);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePgcHolocronCreation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            showHolocronCreationCountdownUi(self, params, "holocron_creation_countdown");
        }
        return SCRIPT_CONTINUE;
    }
    public void showHolocronCreationCountdownUi(obj_id player, dictionary taskDictionary, String menuString) throws InterruptedException
    {
        boolean isRecipe = false;
        if (taskDictionary.containsKey("recipe"))
        {
            isRecipe = taskDictionary.getBoolean("recipe");
        }
        boolean isShared = false;
        if (taskDictionary.containsKey("isShared"))
        {
            isShared = taskDictionary.getBoolean("isShared");
        }
        utils.setScriptVar(player, "temp_pgcTaskDictionary", taskDictionary);
        string_id menuStringId = new string_id("saga_system", menuString);
        String handler = "handlePgcHolocronCreationCountdownTimer";
        int countdownTimer = 3;
        int startTime = 0;
        int range = 3;
        int flags = 0;
        stealth.testInvisNonCombatAction(player, player);
        int countdownSui = sui.smartCountdownTimerSUI(player, player, "pgc_holocron_creation", menuStringId, startTime, countdownTimer, handler, range, flags);
        if (isRecipe)
        {
            transform offset = transform.identity.setPosition_p(0.0f, -0.2f, -0.6f);
            playClientEffectObj(player, "appearance/pt_pgc_recipe.prt", player, "", offset);
            doAnimationAction(player, "medium");
        }
        else if (isShared)
        {
            transform offset = transform.identity.setPosition_p(0.0f, -0.2f, -0.6f);
            playClientEffectObj(player, "appearance/pt_pgc_holocron_shared.prt", player, "", offset);
            doAnimationAction(player, "medium");
        }
        else 
        {
            transform offset = transform.identity.setPosition_p(0.0f, -0.2f, -0.6f);
            playClientEffectObj(player, "appearance/pt_pgc_holocron.prt", player, "", offset);
            doAnimationAction(player, "medium");
        }
        return;
    }
    public int handlePgcHolocronCreationCountdownTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(self, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            detachScript(self, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            detachScript(self, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        int pid = params.getInt("id");
        int test_pid = getIntObjVar(self, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            detachScript(self, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(self, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        dictionary taskDictionary = utils.getDictionaryScriptVar(self, "temp_pgcTaskDictionary");
        createChronicleQuestObject(self, taskDictionary);
        utils.removeScriptVar(self, "temp_pgcTaskDictionary");
        return SCRIPT_CONTINUE;
    }
    public boolean createChronicleQuestObject(obj_id self, dictionary taskDictionary) throws InterruptedException
    {
        if (taskDictionary == null || taskDictionary.isEmpty())
        {
            return false;
        }
        boolean isRecipe = false;
        boolean isNewRecipe = false;
        if (taskDictionary.containsKey("recipe"))
        {
            isRecipe = taskDictionary.getBoolean("recipe");
        }
        boolean isShared = false;
        if (taskDictionary.containsKey("isShared"))
        {
            isShared = taskDictionary.getBoolean("isShared");
        }
        if (!isRecipe && !isShared)
        {
            if (!isGod(self))
            {
                if (!pgc_quests.handleChroniclesRelicCosts(self, taskDictionary))
                {
                    sendSystemMessage(self, new string_id("saga_system", "holocron_does_not_have_relics"));
                    return false;
                }
            }
            else 
            {
                sendSystemMessage(self, "[GOD_MODE] Bypassing relic consumption for chronicle holocron creation.", "");
            }
        }
        obj_id playerInventory = utils.getInventoryContainer(self);
        obj_id questHolocron = obj_id.NULL_ID;
        if (isRecipe)
        {
            if (taskDictionary.containsKey("oldRecipeObject"))
            {
                obj_id oldRecipe = taskDictionary.getObjId("oldRecipeObject");
                if (isIdValid(oldRecipe) && exists(oldRecipe))
                {
                    questHolocron = oldRecipe;
                    resetAllPlayerQuestData(questHolocron);
                    if (hasObjVar(questHolocron, "chronicles"))
                    {
                        removeObjVar(questHolocron, "chronicles");
                    }
                    for (int p = 0; p < pgc_quests.PGC_QUEST_MAX_NUM_TASKS; p++)
                    {
                        String oldPhaseString = pgc_quests.getPhaseObjVarString(p);
                        if (hasObjVar(questHolocron, oldPhaseString))
                        {
                            removeObjVar(questHolocron, oldPhaseString);
                        }
                    }
                    obj_id[] rewardItems = getContents(oldRecipe);
                    if ((rewardItems != null && rewardItems.length > 0))
                    {
                        utils.setScriptVar(oldRecipe, "chronicles.allowRewardsReclaimed", true);
                        for (int i = 0; i < rewardItems.length; i++)
                        {
                            obj_id rewardItem = rewardItems[i];
                            putInOverloaded(rewardItem, playerInventory);
                            String template = getTemplateName(rewardItem);
                            if (template.equals(pgc_quests.PGC_CASH_ITEM_TEMPLATE))
                            {
                                int cash = getIntObjVar(rewardItem, "loot.cashAmount");
                                money.bankTo("pgc_player_donated_credits", self, cash);
                                destroyObject(rewardItem);
                            }
                        }
                    }
                }
                else 
                {
                    questHolocron = createObjectInInventoryAllowOverload(pgc_quests.PGC_QUEST_RECIPE_TEMPLATE, self);
                    isNewRecipe = true;
                }
            }
            else 
            {
                questHolocron = createObjectInInventoryAllowOverload(pgc_quests.PGC_QUEST_RECIPE_TEMPLATE, self);
                isNewRecipe = true;
            }
        }
        else if (isShared)
        {
            questHolocron = createObjectInInventoryAllowOverload(pgc_quests.PGC_QUEST_SHARED_TEMPLATE, self);
        }
        else 
        {
            questHolocron = createObjectInInventoryAllowOverload(pgc_quests.PGC_QUEST_HOLOCRON_TEMPLATE, self);
        }
        if (isIdValid(questHolocron))
        {
            obj_id creatorId = self;
            String creatorName = getName(self);
            int creatorStationId = getPlayerStationId(self);
            if (taskDictionary.containsKey("quest_creator_id"))
            {
                creatorId = taskDictionary.getObjId("quest_creator_id");
            }
            if (taskDictionary.containsKey("quest_creator_name"))
            {
                creatorName = taskDictionary.getString("quest_creator_name");
            }
            if (taskDictionary.containsKey("quest_creator_station_id"))
            {
                creatorStationId = taskDictionary.getInt("quest_creator_station_id");
            }
            setPlayerQuestCreator(questHolocron, creatorId);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR, creatorId);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_STATIONID_OBJVAR, creatorStationId);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_NAME_OBJVAR, creatorName);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_VERSION_OBJVAR, pgc_quests.CHRONICLE_HOLOCRON_VERSION);
            if (!hasScript(questHolocron, pgc_quests.PGC_QUEST_HOLOCRON_SCRIPT))
            {
                attachScript(questHolocron, pgc_quests.PGC_QUEST_HOLOCRON_SCRIPT);
            }
            if (isShared)
            {
                if (!hasScript(questHolocron, utils.NO_TRADE_SCRIPT))
                {
                    attachScript(questHolocron, utils.NO_TRADE_SCRIPT);
                }
                setObjVar(questHolocron, pgc_quests.PCG_QUEST_SHARED_HOLOCRON_OBJVAR, true);
            }
            if (isRecipe)
            {
                if (!hasScript(questHolocron, utils.NO_TRADE_SCRIPT))
                {
                    attachScript(questHolocron, utils.NO_TRADE_SCRIPT);
                }
            }
            String rewardsLog = "";
            if (!isRecipe && !isShared)
            {
                if (taskDictionary.containsKey("rewards"))
                {
                    String rewardsString = taskDictionary.getString("rewards");
                    setPlayerQuestRewardData(questHolocron, rewardsString);
                    String[] rewardParse = split(rewardsString, '~');
                    for (int k = 0; k < pgc_quests.PGC_NUM_REWARD_PARSE_SLOTS; k++)
                    {
                        if (k == 4)
                        {
                            int rewardCredits = utils.stringToInt(rewardParse[k]);
                            if (rewardCredits > 0)
                            {
                                if (money.hasFunds(self, money.MT_TOTAL, rewardCredits))
                                {
                                    money.requestPayment(self, "pgc_player_donated_credits", rewardCredits, "pass_fail", null, false);
                                    rewardsLog += " Credits reward added: " + rewardCredits + " ";
                                    obj_id cashItem = createObject(pgc_quests.PGC_CASH_ITEM_TEMPLATE, questHolocron, "");
                                    if (isIdValid(cashItem))
                                    {
                                        setObjVar(cashItem, "loot.cashAmount", rewardCredits);
                                        setName(cashItem, loot.formatCashAmount(rewardCredits));
                                        attachScript(cashItem, "quest.task.pgc.credit_item");
                                    }
                                }
                                else 
                                {
                                    sendSystemMessage(self, new string_id("saga_system", "holocron_reward_not_enough_credits"));
                                }
                            }
                        }
                        else 
                        {
                            obj_id rewardItem = utils.stringToObjId(rewardParse[k]);
                            if (isIdValid(rewardItem))
                            {
                                prose_package pp = null;
                                string_id name = getNameStringId(rewardItem);
                                if (name == null)
                                {
                                    name = new string_id("saga_system", "holocron_reward_unknown");
                                }
                                if (utils.isNestedWithin(rewardItem, self))
                                {
                                    if (pgc_quests.isEligiblePgcReward(rewardItem))
                                    {
                                        putIn(rewardItem, questHolocron, self);
                                        rewardsLog += "; Reward Item = " + name + "(" + rewardItem + ")";
                                    }
                                    else 
                                    {
                                        pp = prose.getPackage(new string_id("saga_system", "holocron_reward_ineligible_item"), name);
                                        sendSystemMessageProse(self, pp);
                                        pgc_quests.logReward(self, questHolocron, "Player attempted to donate a reward item but it was ineligible: " + name + "(" + rewardItem + ")");
                                    }
                                }
                                else 
                                {
                                    pp = prose.getPackage(new string_id("saga_system", "holocron_reward_not_in_inventory"), name);
                                    sendSystemMessageProse(self, pp);
                                    pgc_quests.logReward(self, questHolocron, "Player attempted to donate a reward item but it was not in their inventory: " + name + "(" + rewardItem + ")");
                                }
                            }
                        }
                    }
                }
            }
            int shareLimit = -1;
            if (!isRecipe && !isShared)
            {
                if (taskDictionary.containsKey("share"))
                {
                    shareLimit = taskDictionary.getInt("share");
                    if (shareLimit > 0)
                    {
                        int maxNumTimesShared = getEnhancedSkillStatisticModifierUncapped(self, pgc_quests.PGC_SKILLMOD_MAX_SHARED_QUESTS);
                        if (shareLimit > maxNumTimesShared)
                        {
                            shareLimit = maxNumTimesShared;
                        }
                        setObjVar(questHolocron, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR, shareLimit);
                        setObjVar(questHolocron, pgc_quests.PCG_QUEST_CAN_SHARE_MAX_OBJVAR, shareLimit);
                    }
                }
            }
            String questTitle = pgc_quests.useFilteredQuestText(taskDictionary.getString("questName"));
            String questDescription = pgc_quests.useFilteredQuestText(taskDictionary.getString("questDescription"));
            setPlayerQuestTitle(questHolocron, questTitle);
            setPlayerQuestDescription(questHolocron, questDescription);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_CUR_QUEST_PHASE_OBJVAR, 0);
            if (isRecipe)
            {
                setName(questHolocron, "[Draft] " + questTitle);
            }
            else 
            {
                setName(questHolocron, questTitle);
            }
            int numTasks = taskDictionary.getInt("totalTasks");
            if (!isShared)
            {
                int maxTasksSkillMod = pgc_quests.getMaxChronicleQuestTasks(self);
                if (numTasks > maxTasksSkillMod)
                {
                    numTasks = maxTasksSkillMod;
                }
            }
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_NUM_TASKS_OBJVAR, numTasks);
            int questLevel = 1;
            String questGroupSetting = "solo";
            boolean questRequiresKashyyyk = false;
            boolean questRequiresMustafar = false;
            boolean involvesPvp = false;
            boolean involvesCrafting = false;
            boolean involvesEntertaining = false;
            boolean involvesCombat = false;
            float[] taskWeightArray = new float[numTasks];
            dictionary taskTypes = new dictionary();
            dictionary relicNames = new dictionary();
            for (int i = 0; i < numTasks; i++)
            {
                String recipeTaskData = "";
                String taskName = "" + i;
                String taskData = taskDictionary.getString(taskName);
                String[] parse = split(taskData, '~');
                String phase = pgc_quests.getPhaseObjVarString(i);
                String task = "task_00";
                String baseObjVar = pgc_quests.getPgcBaseObjVar(phase, task) + ".";
                setObjVar(questHolocron, baseObjVar + "task_index", i);
                String relicName = parse[1];
                int relicNameCount = 0;
                if (relicNames.containsKey(relicName))
                {
                    relicNameCount = relicNames.getInt(relicName);
                }
                relicNames.put(relicName, relicNameCount + 1);
                String[] relicData = getCollectionSlotInfo(relicName);
                String taskType = relicData[COLLECTION_INFO_INDEX_COLLECTION];
                int taskTypeCount = 0;
                if (taskTypes.containsKey(taskType))
                {
                    taskTypeCount = taskTypes.getInt(taskType);
                }
                taskTypes.put(taskType, taskTypeCount + 1);
                recipeTaskData += relicName;
                setObjVar(questHolocron, baseObjVar + "task_type", taskType);
                setObjVar(questHolocron, baseObjVar + "task_relic", relicName);
                String taskTitle = pgc_quests.useFilteredQuestText(parse[2]);
                String taskDescription = pgc_quests.useFilteredQuestText(parse[3]);
                int taskCounterMax = -1;
                int dropRate = -1;
                String[] relicCategoryData = getCollectionSlotCategoryInfo(relicName);
                int taskLevel = pgc_quests.getTaskLevel(relicCategoryData);
                if (taskLevel > questLevel)
                {
                    questLevel = taskLevel;
                }
                if (!questGroupSetting.equals("group"))
                {
                    questGroupSetting = pgc_quests.getTaskGroupSetting(relicCategoryData);
                }
                if (!questRequiresKashyyyk)
                {
                    questRequiresKashyyyk = pgc_quests.getTaskKashyyykSetting(relicCategoryData);
                }
                if (!questRequiresMustafar)
                {
                    questRequiresMustafar = pgc_quests.getTaskMustafarSetting(relicCategoryData);
                }
                dictionary waypointData = pgc_quests.getWaypointRelicData("waypoint", relicCategoryData);
                location waypointLoc = null;
                if (waypointData != null)
                {
                    waypointLoc = waypointData.getLocation("waypointLoc");
                }
                for (int j = 4; j < parse.length; j++)
                {
                    String data = parse[j];
                    if (taskType.equals(pgc_quests.SAGA_DESTROY_MULTIPLE))
                    {
                        involvesCombat = true;
                        switch (j)
                        {
                            case 4:
                            break;
                            case 5:
                            taskCounterMax = utils.stringToInt(data);
                            setObjVar(questHolocron, baseObjVar + "count", taskCounterMax);
                            recipeTaskData += "~" + taskCounterMax;
                            int taskDifficulty = pgc_quests.getTaskDifficultySetting(relicCategoryData);
                            taskWeightArray[i] = pgc_quests.getDestroyTaskWeight(taskLevel, taskDifficulty, taskCounterMax);
                            break;
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_DESTROY_MULTIPLE_LOOT))
                    {
                        involvesCombat = true;
                        switch (j)
                        {
                            case 4:
                            break;
                            case 5:
                            taskCounterMax = utils.stringToInt(data);
                            setObjVar(questHolocron, baseObjVar + "count", taskCounterMax);
                            recipeTaskData += "~" + taskCounterMax;
                            break;
                            case 6:
                            setObjVar(questHolocron, baseObjVar + "message", pgc_quests.useFilteredQuestText(data));
                            recipeTaskData += "~" + data;
                            break;
                            case 7:
                            dropRate = utils.stringToInt(data);
                            setObjVar(questHolocron, baseObjVar + "drop_rate", dropRate);
                            recipeTaskData += "~" + dropRate;
                            break;
                        }
                        if (taskCounterMax > -1 && dropRate > -1)
                        {
                            int taskDifficulty = pgc_quests.getTaskDifficultySetting(relicCategoryData);
                            taskWeightArray[i] = pgc_quests.getDestroyLootTaskWeight(taskLevel, taskDifficulty, taskCounterMax, dropRate);
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_PERFORM))
                    {
                        involvesEntertaining = true;
                        taskWeightArray[i] = pgc_quests.getPerformTaskWeight(taskLevel);
                        switch (j)
                        {
                            case 4:
                            break;
                            case 5:
                            break;
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_CRAFT_ITEM))
                    {
                        involvesCrafting = true;
                        switch (j)
                        {
                            case 4:
                            break;
                            case 5:
                            taskCounterMax = utils.stringToInt(data);
                            setObjVar(questHolocron, baseObjVar + "count", taskCounterMax);
                            recipeTaskData += "~" + taskCounterMax;
                            taskWeightArray[i] = pgc_quests.getCraftingTaskWeight(taskLevel, taskCounterMax);
                            break;
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_COMM_MESSAGE))
                    {
                        taskWeightArray[i] = pgc_quests.getCommTaskWeight(taskLevel);
                        switch (j)
                        {
                            case 4:
                            break;
                            case 5:
                            String voiceOver = pgc_quests.getStringRelicData("voice_over", relicCategoryData);
                            if (voiceOver == null || voiceOver.length() <= 0 || voiceOver.equals("none"))
                            {
                                setObjVar(questHolocron, baseObjVar + "message", pgc_quests.useFilteredQuestText(data));
                                recipeTaskData += "~" + data;
                            }
                            break;
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_GOTO_LOCATION))
                    {
                        taskWeightArray[i] = pgc_quests.getGoToTaskWeight(taskLevel);
                        switch (j)
                        {
                            case 4:
                            if (relicName.equals(pgc_quests.PGC_RELIC_SLOT_GOTO_GENERIC_LOC))
                            {
                                String[] gotoParse = split(data, ':');
                                if (gotoParse.length == 5)
                                {
                                    float x = utils.stringToFloat(gotoParse[0]);
                                    float y = utils.stringToFloat(gotoParse[1]);
                                    float z = utils.stringToFloat(gotoParse[2]);
                                    String planet = gotoParse[3];
                                    String waypointName = gotoParse[4];
                                    waypointLoc = new location(x, y, z, planet);
                                    setObjVar(questHolocron, baseObjVar + "waypoint", waypointLoc);
                                    setObjVar(questHolocron, baseObjVar + "waypointName", waypointName);
                                    recipeTaskData += "~waypoint:" + planet + "," + x + "," + y + "," + z + ",none," + waypointName;
                                }
                            }
                            break;
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_RETRIEVE_ITEM))
                    {
                        switch (j)
                        {
                            case 4:
                            break;
                            case 5:
                            taskCounterMax = utils.stringToInt(data);
                            setObjVar(questHolocron, baseObjVar + "count", taskCounterMax);
                            recipeTaskData += "~" + taskCounterMax;
                            taskWeightArray[i] = pgc_quests.getRetrieveTaskWeight(taskLevel, taskCounterMax);
                            break;
                        }
                    }
                    else if (taskType.equals(pgc_quests.SAGA_PVP_OBJECTIVE))
                    {
                        involvesPvp = true;
                        switch (j)
                        {
                            case 4:
                            taskCounterMax = utils.stringToInt(data);
                            setObjVar(questHolocron, baseObjVar + "count", taskCounterMax);
                            recipeTaskData += "~" + taskCounterMax;
                            taskWeightArray[i] = pgc_quests.getPvpTaskWeight(taskLevel, taskCounterMax);
                            break;
                            case 5:
                            break;
                        }
                    }
                }
                addPlayerQuestTask(questHolocron, taskTitle, taskDescription, taskCounterMax, waypointLoc);
                if (isRecipe)
                {
                    setPlayerQuestRecipe(questHolocron, true);
                    addPlayerQuestTaskRecipeData(questHolocron, recipeTaskData);
                }
                else 
                {
                    addPlayerQuestTaskRecipeData(questHolocron, relicName);
                }
            }
            pgc_quests.initializeQuestTasksStatus(questHolocron, numTasks);
            setPlayerQuestDifficulty(questHolocron, questLevel);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_LEVEL_OBJVAR, questLevel);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_DIFFICULTY_OBJVAR, questGroupSetting);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_NEED_KASHYYYK_OBJVAR, questRequiresKashyyyk);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_NEED_MUSTAFAR_OBJVAR, questRequiresMustafar);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_INVOLVES_PVP_OBJVAR, involvesPvp);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_INVOLVES_CRAFT_OBJVAR, involvesCrafting);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_INVOLVES_ENTERTAIN_OBJVAR, involvesEntertaining);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_INVOLVES_COMBAT_OBJVAR, involvesCombat);
            float questTaskTypeDiversity = pgc_quests.calculateTaskTypeDiversity(taskTypes, numTasks);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_TASK_TYPE_DIVERSITY, questTaskTypeDiversity);
            float questRelicDiversity = pgc_quests.calculateRelicDiversity(relicNames, numTasks);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_RELIC_DIVERSITY, questRelicDiversity);
            float questWeight = pgc_quests.calculateQuestWeight(taskWeightArray);
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_WEIGHT_OBJVAR, questWeight);
            string_id createdMsg = new string_id("saga_system", "holocron_created");
            obj_id sharerId = obj_id.NULL_ID;
            String sharerName = "";
            if (isRecipe)
            {
                if (isNewRecipe)
                {
                    createdMsg = new string_id("saga_system", "holocron_created_draft");
                }
                else 
                {
                    createdMsg = new string_id("saga_system", "holocron_created_draft_edit");
                }
            }
            else if (isShared)
            {
                createdMsg = new string_id("saga_system", "holocron_created_shared");
                if (taskDictionary.containsKey("quest_sharer_id"))
                {
                    sharerId = taskDictionary.getObjId("quest_sharer_id");
                    if (isIdValid(sharerId) && exists(sharerId))
                    {
                        prose_package pp_holocronShared = prose.getPackage(new string_id("saga_system", "holocron_created_shared_confirm"), sharerId, sharerId);
                        prose.setTO(pp_holocronShared, questTitle);
                        prose.setTT(pp_holocronShared, getName(self));
                        sendSystemMessageProse(sharerId, pp_holocronShared);
                        pgc_quests.logQuest(self, questHolocron, "Was successfully shared a quest from " + sharerId + ".");
                    }
                }
                if (taskDictionary.containsKey("quest_sharer_name"))
                {
                    sharerName = taskDictionary.getString("quest_sharer_name");
                }
                if (taskDictionary.containsKey("quest_shared_holocron"))
                {
                    obj_id sharedHolocron = taskDictionary.getObjId("quest_shared_holocron");
                    if (isIdValid(sharedHolocron))
                    {
                        dictionary webster = new dictionary();
                        webster.put("sharedWith", self);
                        messageTo(sharedHolocron, "handleHolocronSharedSuccess", webster, 0.1f, false);
                        setObjVar(questHolocron, "chroniclesShared.sourceHolocron", sharedHolocron);
                    }
                }
            }
            prose_package pp_holocronCreated = prose.getPackage(createdMsg, self, self);
            prose.setTO(pp_holocronCreated, questTitle);
            if (sharerName != null && sharerName.length() > 0)
            {
                prose.setTT(pp_holocronCreated, sharerName);
            }
            sendSystemMessageProse(self, pp_holocronCreated);
            if (!isRecipe && !isShared)
            {
                int xp = pgc_quests.calculateHolocronCreationChroniclesXp(questWeight, questRelicDiversity, questTaskTypeDiversity);
                xp = pgc_quests.grantChronicleXp(self, xp);
                int fragmentCount = rand(0, 1 + questLevel / 20);
                obj_id fragment = static_item.createNewItemFunction(pgc_quests.PGC_CHRONICLES_RELIC_FRAGMENT, playerInventory, fragmentCount);
                pgc_quests.sendPlacedInInventorySystemMessage(self, fragment);
                pgcAdjustRatingData(creatorId, creatorName, pgc_quests.PGC_NUM_QUESTS_CREATED_ALL_INDEX, 1);
                if (questWeight >= pgc_quests.PGC_MIN_MID_QUALITY_QUEST_WEIGHT && questWeight < pgc_quests.PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT)
                {
                    pgcAdjustRatingData(creatorId, creatorName, pgc_quests.PGC_NUM_QUESTS_CREATED_MID_QUALITY_INDEX, 1);
                }
                else if (questWeight >= pgc_quests.PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT)
                {
                    pgcAdjustRatingData(creatorId, creatorName, pgc_quests.PGC_NUM_QUESTS_CREATED_HIGH_QUALITY_INDEX, 1);
                }
                pgc_quests.logProgression(self, questHolocron, "xp=" + xp + ",questWeight=" + questWeight + ",numTasks=" + numTasks + ",questRelicDiversity=" + questRelicDiversity + ",questTaskTypeDiversity=" + questTaskTypeDiversity);
                if (questGetDebugging())
                {
                    sendSystemMessage(self, "CHRONICLES_DEBUG: ______________numTasks = " + numTasks, "");
                    sendSystemMessage(self, "CHRONICLES_DEBUG: _numDifferentTaskTypes = " + taskTypes.size(), "");
                    sendSystemMessage(self, "CHRONICLES_DEBUG: ____numDifferentRelics = " + relicNames.size(), "");
                    sendSystemMessage(self, "CHRONICLES_DEBUG: questTaskTypeDiversity = " + questTaskTypeDiversity, "");
                    sendSystemMessage(self, "CHRONICLES_DEBUG: ___questRelicDiversity = " + questRelicDiversity, "");
                    sendSystemMessage(self, "CHRONICLES_DEBUG: ___________questWeight = " + questWeight, "");
                    sendSystemMessage(self, "CHRONICLES_DEBUG: ____________________xp = " + xp, "");
                }
            }
            else 
            {
                pgc_quests.logProgression(self, questHolocron, "questWeight=" + questWeight + ",questRelicDiversity=" + questRelicDiversity + ",questTaskTypeDiversity=" + questTaskTypeDiversity);
            }
            if (rewardsLog != null && rewardsLog.length() > 0)
            {
                pgc_quests.logReward(self, questHolocron, "Player donated rewards added to Holocron: " + rewardsLog);
            }
            if (shareLimit > 0)
            {
                pgc_quests.logQuest(self, questHolocron, "Sharing enabled on Chronicles Holocron with shareLimit = " + shareLimit + ".");
            }
        }
        else 
        {
            return false;
        }
        return true;
    }
    public int handleCheckForGainedChroniclesLevelDelay(obj_id self, dictionary params) throws InterruptedException
    {
        pgc_quests.checkForGainedChroniclesLevel(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAbandonPlayerQuest(obj_id self, obj_id questHolocron) throws InterruptedException
    {
        if (isIdValid(questHolocron) && exists(questHolocron))
        {
            obj_id datapad = utils.getPlayerDatapad(self);
            if (isIdValid(datapad))
            {
                if (utils.isNestedWithin(questHolocron, datapad))
                {
                    obj_id questControlDevice = getContainedBy(questHolocron);
                    if (isIdValid(questControlDevice) && (getTemplateName(questControlDevice)).equals(pgc_quests.PGC_QUEST_CONTROL_DEVICE_TEMPLATE))
                    {
                        pgc_quests.setQuestAbandoned(questControlDevice, self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRatingFinished(obj_id self, int rating) throws InterruptedException
    {
        if (rating >= 0)
        {
            obj_id questHolocron = utils.getObjIdScriptVar(self, "chronicles.rating_a_holocron");
            if (isIdValid(questHolocron) && exists(questHolocron))
            {
                if (hasObjVar(questHolocron, pgc_quests.PCG_QUEST_WAS_RATED_OBJVAR))
                {
                    sendSystemMessage(self, new string_id("saga_system", "pgc_quest_already_rated"));
                }
                else 
                {
                    obj_id chroniclerId = getObjIdObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_ID_OBJVAR);
                    String chroniclerName = getStringObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_NAME_OBJVAR);
                    int chroniclerStationId = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_STATIONID_OBJVAR);
                    if (chroniclerStationId == getPlayerStationId(self))
                    {
                        sendSystemMessage(self, "You may not rate a quest that you created. Rating ignored.", "");
                        pgc_quests.logRating(self, questHolocron, "Player attempted to rate their own quest (a rating of " + rating + ") but the rating was ignored.");
                        rating = -1;
                    }
                    else 
                    {
                        pgcAdjustRating(chroniclerId, chroniclerName, rating);
                        sendSystemMessage(self, "You gave this Chonicler a rating of " + rating + " which has been added to their galaxy-wide rating.", "");
                        pgc_quests.logRating(self, questHolocron, "Gave " + chroniclerName + " (" + chroniclerId + ") a rating of " + rating + ".");
                        int storedXp = pgc_quests.calculateHolocronQuestCompletedChroniclesXp(questHolocron, rating);
                        int storedTokens = pgc_quests.getNumQuestCompleteChroniclerRewardTokens(questHolocron);
                        pgcAdjustRatingData(chroniclerId, chroniclerName, pgc_quests.PGC_STORED_CHRONICLE_XP_INDEX, storedXp);
                        pgcAdjustRatingData(chroniclerId, chroniclerName, pgc_quests.PGC_STORED_CHRONICLE_SILVER_TOKENS_INDEX, storedTokens);
                        float questWeight = getFloatObjVar(questHolocron, pgc_quests.PCG_QUEST_WEIGHT_OBJVAR);
                        pgcAdjustRatingData(chroniclerId, chroniclerName, pgc_quests.PGC_NUM_YOUR_QUESTS_OTHERS_COMPLETED_ALL_INDEX, 1);
                        if (questWeight >= pgc_quests.PGC_MIN_MID_QUALITY_QUEST_WEIGHT && questWeight < pgc_quests.PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT)
                        {
                            pgcAdjustRatingData(chroniclerId, chroniclerName, pgc_quests.PGC_NUM_YOUR_QUESTS_OTHERS_COMPLETED_MID_QUALITY_INDEX, 1);
                        }
                        else if (questWeight >= pgc_quests.PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT)
                        {
                            pgcAdjustRatingData(chroniclerId, chroniclerName, pgc_quests.PGC_NUM_YOUR_QUESTS_OTHERS_COMPLETED_HIGH_QUALITY_INDEX, 1);
                        }
                        if (questWeight >= pgc_quests.PGC_MIN_MID_QUALITY_QUEST_WEIGHT)
                        {
                            int goldTokenChance = pgc_quests.CHRONICLES_CHRONICLER_GOLD_TOKEN_CHANCE;
                            String configChance_string = getConfigSetting("GameServer", "chroniclesChroniclerGoldTokenChanceOverride");
                            if (configChance_string != null && configChance_string.length() > 0)
                            {
                                int configChance = utils.stringToInt(configChance_string);
                                if (configChance > 0)
                                {
                                    goldTokenChance = configChance;
                                }
                            }
                            int goldTokenRoll = rand(1, 100);
                            if (goldTokenRoll <= goldTokenChance)
                            {
                                pgcAdjustRatingData(chroniclerId, chroniclerName, pgc_quests.PGC_STORED_CHRONICLE_GOLD_TOKENS_INDEX, 1);
                                pgc_quests.logRating(self, questHolocron, "Chronicler " + chroniclerName + " (" + chroniclerId + ") won a gold token in his reserve when his quest (of weight=" + questWeight + ") was completed by " + self + ".");
                            }
                        }
                        pgc_quests.logProgression(chroniclerId, questHolocron, "Quest completed by " + self + " and rated...Chronicle XP and tokens reserved: reserved XP = " + storedXp + " and reserved Tokens = " + storedTokens);
                    }
                    sendDirtyObjectMenuNotification(questHolocron);
                    setObjVar(questHolocron, pgc_quests.PCG_QUEST_WAS_RATED_OBJVAR, rating);
                }
            }
        }
        utils.removeScriptVar(self, "chronicles.rating_a_holocron");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phaseObjVarString = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    for (int j = 0; j < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; j++)
                    {
                        String taskObjVarString = pgc_quests.getTaskObjVarString(j);
                        String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseObjVarString, taskObjVarString);
                        if (hasObjVar(questHolocron, baseObjVar))
                        {
                            String waypointActiveObjVar = baseObjVar + ".waypointActive";
                            if (hasObjVar(questHolocron, waypointActiveObjVar))
                            {
                                pgc_quests.activatePlayerQuestWaypointFromHolocron(questHolocron, baseObjVar);
                            }
                        }
                        else 
                        {
                            break;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int receiveCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phase = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    for (int j = 0; j < pgc_quests.CREDIT_FOR_KILL_TASKS.length; j++)
                    {
                        if (pgc_quests.phaseHasActiveTaskOfType(questHolocron, pgc_quests.CREDIT_FOR_KILL_TASKS[j], phase))
                        {
                            messageTo(questHolocron, "receiveCreditForKill", params, 0.0f, false);
                            break;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int recivedGcwCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phase = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    if (pgc_quests.phaseHasActiveTaskOfType(questHolocron, pgc_quests.SAGA_PVP_OBJECTIVE, phase))
                    {
                        messageTo(questHolocron, "recivedGcwCreditForKill", params, 0.0f, false);
                        break;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phase = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    if (pgc_quests.phaseHasActiveTaskOfType(questHolocron, pgc_quests.SAGA_PERFORM, phase))
                    {
                        messageTo(questHolocron, "startPerform", params, 0.0f, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phase = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    if (pgc_quests.phaseHasActiveTaskOfType(questHolocron, pgc_quests.SAGA_PERFORM, phase))
                    {
                        messageTo(questHolocron, "stopPerform", params, 0.0f, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCraftedPrototype(obj_id self, obj_id prototypeObject, draft_schematic manufacturingSchematic) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phase = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    if (pgc_quests.phaseHasActiveTaskOfType(questHolocron, pgc_quests.SAGA_CRAFT_ITEM, phase))
                    {
                        dictionary webster = new dictionary();
                        webster.put("prototypeObject", prototypeObject);
                        messageTo(questHolocron, "OnCraftedPrototype", webster, 0.0f, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int playerQuestSetLocationTarget(obj_id self, dictionary params) throws InterruptedException
    {
        location targetLoc = params.getLocation("targetLoc");
        int radius = params.getInt("radius");
        String locationTargetName = params.getString("locationTargetName");
        addLocationTarget(locationTargetName, targetLoc, radius);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        obj_id[] activeHolocrons = pgc_quests.getActivateQuestHolocrons(self);
        if (activeHolocrons != null && activeHolocrons.length > 0)
        {
            for (int i = 0; i < activeHolocrons.length; i++)
            {
                obj_id questHolocron = activeHolocrons[i];
                if (isIdValid(questHolocron))
                {
                    String phase = pgc_quests.getActivePhaseObjVarString(questHolocron);
                    if (pgc_quests.phaseHasActiveTaskOfType(questHolocron, pgc_quests.SAGA_GOTO_LOCATION, phase))
                    {
                        dictionary webster = new dictionary();
                        webster.put("locationName", locationName);
                        messageTo(questHolocron, "pqOnArrivedAtLocation", webster, 0.0f, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int playerQuestRemoveLocationTarget(obj_id self, dictionary params) throws InterruptedException
    {
        String locationName = params.getString("locationName");
        removeLocationTarget(locationName);
        return SCRIPT_CONTINUE;
    }
}

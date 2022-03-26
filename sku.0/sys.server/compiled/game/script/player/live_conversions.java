package script.player;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class live_conversions extends script.base_script
{
    public live_conversions()
    {
    }
    public static final int FLAG_ARRAY_SIZE = 2;
    public static final int FLAG_MAX_BITS = FLAG_ARRAY_SIZE * 32;
    public static final int FEMALE_PILOT_MEDAL_FLAG = 0;
    public static final int NOT_USED = 1;
    public static final int UNARMED_DAMAGE_SET = 2;
    public static final int POLITICIAN_CONVERSION = 3;
    public static final int ELDER_BUFF_GRANT = 4;
    public static final int SKILL_VALIDATION = 5;
    public static final int MASTER_BADGE_CORRECTIONS = 6;
    public static final int ENT_TRADER_FREE_SKILL_FLAG = 7;
    public static final int SKILL_VALIDATION_2 = 8;
    public static final int FORCED_RESPEC_VERSION = 2;
    public static final String VAR_FLAGS = "live_conversions";
    public static final string_id EXPERTISE_RESET_FROM_CHANGES = new string_id("spam", "expertise_reset_from_changes");
    public static final string_id JABBAS_COMLINK = new string_id("spam", "jabbas_comlink_system_message");
    public static final String UPDATE_COLLECTION_DATATABLE = "datatables/collection/collection_live_conversion.iff";
    public static final String UPDATE_COLLECTION_NAME_COLUMN = "completed_collections";
    public static final String UPDATE_COLLECTION_SLOT_TO_GRANT = "slot_to_grant";
    public static final String UPDATE_COLLECTION_INCREMENT_AMOUNT = "increment_amount";
    public static final String FORCE_RESPEC_OBJVAR = "configEnforcedRespecVersion";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        runOncePerSessionConversions(self);
        runOncePerTravelConversions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        runOncePerSessionConversions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        runOncePerTravelConversions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            updateBountyHunterMissions(self);
            updateChangedQuests(self);
            updateCollectionSlots(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void runOncePerSessionConversions(obj_id player) throws InterruptedException
    {
        handleStoreUsername(player);
        grantFemaleMasterPilotMedals(player);
        clearDotEffectObjvars(player);
        fixConsentList(player);
        setUnarmedDamageRanges(player);
        doPoliticianSkillConversion(player);
        grantElderBuff(player);
        validateSkills(player);
        correctMasterBadges(player);
        correctEv9D9Quest(player);
        correctNabooKickQuests(player);
        correctSyrenQuests(player);
        checkForContentPathContinuation(player);
        updateResidencyLinks(player);
        updateLotOverLimitStructureLocation(player);
        updateGuildTerminals(player);
        validateExpertises(player);
        expertiseRespecCheck(player);
        updatePermanentSchematics(player);
        updateHothFlawless(player);
        utils.updateCTSObjVars(player);
        respec.checkRespecDecay(player);
        updateRenameCharacterComplete(player);
        forceRespecBasedOnConfig(player);
        givePlayersDeathTroopersOutbreakQuest(player);
        givePlayersDeathTroopersPrologQuestComlink(player);
        removeDeprecatedQuests(player);
        addPlayerScripts(player);
        handleEsbAnniversaryGifts(player);
        handleMailOptInRewards(player);
    }
    public void runOncePerTravelConversions(obj_id player) throws InterruptedException
    {
        if (buff.hasBuff(player, "crystal_buff"))
        {
            buff.removeBuff(player, "crystal_buff");
        }
    }
    public void setConversionFlag(obj_id player, int flag) throws InterruptedException
    {
        if ((!isIdValid(player)) || (flag < 0) || (flag > FLAG_MAX_BITS))
        {
            return;
        }
        if (hasConversionFlag(player, flag))
        {
            return;
        }
        int arrayIdx = flag / 32;
        int bitIdx = flag % 32;
        int[] flags = getFlags(player);
        if (flags == null)
        {
            flags = new int[FLAG_ARRAY_SIZE];
        }
        if (!utils.checkBit(flags[arrayIdx], bitIdx))
        {
            flags[arrayIdx] = utils.setBit(flags[arrayIdx], bitIdx);
            setFlags(player, flags);
        }
    }
    public boolean hasConversionFlag(obj_id player, int flag) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int[] flags = getFlags(player);
        if (flags == null)
        {
            return false;
        }
        int arrayIdx = flag / 32;
        int bitIdx = flag % 32;
        return utils.checkBit(flags[arrayIdx], bitIdx);
    }
    public void clearConversionFlag(obj_id player, int flag) throws InterruptedException
    {
        if ((!isIdValid(player)) || (flag < 0) || (flag > FLAG_MAX_BITS))
        {
            return;
        }
        if (!hasObjVar(player, VAR_FLAGS))
        {
            return;
        }
        int arrayIdx = flag / 32;
        int bitIdx = flag % 32;
        int[] flags = getFlags(player);
        if (flags == null)
        {
            return;
        }
        if (utils.checkBit(flags[arrayIdx], bitIdx))
        {
            flags[arrayIdx] = utils.clearBit(flags[arrayIdx], bitIdx);
            setFlags(player, flags);
        }
    }
    public int[] getFlags(obj_id player) throws InterruptedException
    {
        int[] flags = getIntArrayObjVar(player, VAR_FLAGS);
        if (flags == null || flags.length == 0)
        {
            return null;
        }
        if (flags.length < FLAG_ARRAY_SIZE)
        {
            int[] updatedFlagArray = new int[FLAG_ARRAY_SIZE];
            flags = utils.copyArray(flags, updatedFlagArray);
            setFlags(player, flags);
        }
        return flags;
    }
    public void setFlags(obj_id player, int[] flags) throws InterruptedException
    {
        setObjVar(player, VAR_FLAGS, flags);
    }
    public static final int NEUTRAL = 0;
    public static final int REBEL = 1;
    public static final int IMPERIAL = 2;
    public void grantFemaleMasterPilotMedals(obj_id player) throws InterruptedException
    {
        if (hasConversionFlag(player, FEMALE_PILOT_MEDAL_FLAG))
        {
            return;
        }
        if (getGender(player) != GENDER_MALE && getSpecies(player) != SPECIES_ITHORIAN)
        {
            if (badge.hasBadge(player, "pilot_rebel_navy_naboo"))
            {
                createFemaleMasterMedal(player, REBEL);
            }
            if (badge.hasBadge(player, "pilot_rebel_navy_corellia"))
            {
                createFemaleMasterMedal(player, REBEL);
            }
            if (badge.hasBadge(player, "pilot_rebel_navy_tatooine"))
            {
                createFemaleMasterMedal(player, REBEL);
            }
            if (badge.hasBadge(player, "pilot_imperial_navy_naboo"))
            {
                createFemaleMasterMedal(player, IMPERIAL);
            }
            if (badge.hasBadge(player, "pilot_imperial_navy_corellia"))
            {
                createFemaleMasterMedal(player, IMPERIAL);
            }
            if (badge.hasBadge(player, "pilot_imperial_navy_tatooine"))
            {
                createFemaleMasterMedal(player, IMPERIAL);
            }
            if (badge.hasBadge(player, "pilot_neutral_naboo"))
            {
                createFemaleMasterMedal(player, NEUTRAL);
            }
            if (badge.hasBadge(player, "pilot_neutral_corellia"))
            {
                createFemaleMasterMedal(player, NEUTRAL);
            }
            if (badge.hasBadge(player, "pilot_neutral_tatooine"))
            {
                createFemaleMasterMedal(player, NEUTRAL);
            }
            if (hasSkill(player, "pilot_imperial_navy_master") || hasSkill(player, "pilot_neutral_master") || hasSkill(player, "pilot_rebel_navy_master"))
            {
                space_flags.setSpaceFlag(player, "master_pilot_medal_recieved", true);
            }
        }
        setConversionFlag(player, FEMALE_PILOT_MEDAL_FLAG);
    }
    public void createFemaleMasterMedal(obj_id player, int type) throws InterruptedException
    {
        switch (type)
        {
            case NEUTRAL:
            createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_neutral.iff", player);
            break;
            case IMPERIAL:
            createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_empire.iff", player);
            break;
            case REBEL:
            createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_rebel.iff", player);
            break;
        }
    }
    public void clearDotEffectObjvars(obj_id player) throws InterruptedException
    {
        removeObjVar(player, dot.VAR_DOT_ROOT);
        removeObjVar(player, "slowDotTime");
    }
    public void fixConsentList(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, pclib.VAR_CONSENT_TO_NAME))
        {
            obj_id[] consentTo = getObjIdArrayObjVar(player, pclib.VAR_CONSENT_TO_ID);
            if (consentTo != null)
            {
                Vector newConsentTo = new Vector();
                newConsentTo.setSize(0);
                for (obj_id consentTo1 : consentTo) {
                    if (isPlayer(consentTo1)) {
                        newConsentTo = utils.addElement(newConsentTo, consentTo1);
                    }
                }
                if (newConsentTo.size() > 0)
                {
                    setObjVar(player, pclib.VAR_CONSENT_TO_ID, newConsentTo);
                }
                else 
                {
                    removeObjVar(player, pclib.VAR_CONSENT_TO_ID);
                }
            }
            removeObjVar(player, pclib.VAR_CONSENT_TO_NAME);
        }
        if (hasObjVar(player, pclib.VAR_CONSENT_FROM_NAME))
        {
            obj_id[] consentFrom = getObjIdArrayObjVar(player, pclib.VAR_CONSENT_FROM_ID);
            if (consentFrom != null)
            {
                Vector newConsentFrom = new Vector();
                newConsentFrom.setSize(0);
                for (obj_id obj_id : consentFrom) {
                    if (isPlayer(obj_id)) {
                        newConsentFrom = utils.addElement(newConsentFrom, obj_id);
                    }
                }
                if (newConsentFrom.size() > 0)
                {
                    setObjVar(player, pclib.VAR_CONSENT_FROM_ID, newConsentFrom);
                }
                else 
                {
                    removeObjVar(player, pclib.VAR_CONSENT_FROM_ID);
                }
            }
            removeObjVar(player, pclib.VAR_CONSENT_FROM_NAME);
        }
    }
    public void handleStoreUsername(obj_id player) throws InterruptedException {
        setObjVar(player, "system.accountUsername", _getPlayerUsernameDoNotUse(getLongWithNull(player)));
    }
    public void setUnarmedDamageRanges(obj_id player) throws InterruptedException
    {
        if (hasConversionFlag(player, UNARMED_DAMAGE_SET))
        {
            return;
        }
        obj_id weapon = getDefaultWeapon(player);
        if (!isIdValid(weapon))
        {
            return;
        }
        int weaponType = getWeaponType(weapon);
        if (weaponType != WEAPON_TYPE_UNARMED)
        {
            return;
        }
        int minDmg = getWeaponMinDamage(weapon);
        int maxDmg = getWeaponMaxDamage(weapon);
        setWeaponMinDamage(weapon, 5);
        setWeaponMaxDamage(weapon, 10);
        weapons.setWeaponData(weapon);
        setConversionFlag(player, UNARMED_DAMAGE_SET);
    }
    public void doPoliticianSkillConversion(obj_id player) throws InterruptedException
    {
        if (hasConversionFlag(player, POLITICIAN_CONVERSION))
        {
            return;
        }
        obj_id residence = player_structure.getResidence(player);
        if (isIdValid(residence))
        {
            skill.grantAllPoliticianSkills(player);
        }
        setConversionFlag(player, POLITICIAN_CONVERSION);
    }
    public static final int NPE_BIRTH_DATE = 1777;
    public void grantElderBuff(obj_id player) throws InterruptedException
    {
        String config = getConfigSetting("Custom", "grantElderBuff");
        if(config != null && utils.stringToInt(config) == 1) {
            if (!hasCommand(player, "veteranPlayerBuff")) {
                grantCommand(player, "veteranPlayerBuff");
            }
        }
    }
    public int handleBirthDateCallBack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = self;
        int myBirthDate = getPlayerBirthDate(player);
        if (myBirthDate == -1)
        {
            messageTo(self, "handleBirthDateCallBack", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        else if (myBirthDate <= NPE_BIRTH_DATE)
        {
            if (!hasCommand(player, "veteranPlayerBuff"))
            {
                grantCommand(player, "veteranPlayerBuff");
            }
        }
        setConversionFlag(player, ELDER_BUFF_GRANT);
        return SCRIPT_CONTINUE;
    }
    public void validateSkills(obj_id player) throws InterruptedException
    {
        if (hasConversionFlag(player, SKILL_VALIDATION) && hasConversionFlag(player, SKILL_VALIDATION_2))
        {
            return;
        }
        String skillTemplate = getSkillTemplate(player);
        String workingSkill = getWorkingSkill(player);
        if (skillTemplate == null || skillTemplate.equals("") || skillTemplate.equals("a"))
        {
            CustomerServiceLog("validateSkillCheck:", " NullTemplate: player " + getFirstName(player) + "(" + player + ")'s skill template is either null or 'a'");
            CustomerServiceLog("validateSkillCheck:", " NullTemplate: player " + getFirstName(player) + "(" + player + ") skillTemplate is " + skillTemplate);
            CustomerServiceLog("validateSkillCheck:", " NullTemplate: player " + getFirstName(player) + "(" + player + ") workingSkill is " + workingSkill);
            return;
        }
        if (workingSkill == null || workingSkill.equals(""))
        {
            CustomerServiceLog("validateSkillCheck:", " NullWorkingSkill: player " + getFirstName(player) + "(" + player + ")'s skill workingSkill that is null");
            return;
        }
        int skillCount = 0;
        if (hasSkill(player, "class_forcesensitive_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_bountyhunter_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_smuggler_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_officer_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_spy_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_medic_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_entertainer_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_domestics_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_structures_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_munitions_phase1_novice"))
        {
            ++skillCount;
        }
        if (hasSkill(player, "class_engineering_phase1_novice"))
        {
            ++skillCount;
        }
        if (skillCount > 1)
        {
            CustomerServiceLog("validateSkillCheck:", " player " + getFirstName(player) + "(" + player + ") has a skillTemplate of " + skillTemplate);
            CustomerServiceLog("validateSkillCheck:", " player " + getFirstName(player) + "(" + player + ") has a workingSkill of " + workingSkill);
            CustomerServiceLog("validateSkillCheck:", " player " + getFirstName(player) + "(" + player + ") has a skillCount of " + skillCount);
            int combatLevel = getLevel(player);
            setObjVar(player, "clickRespec.combatLevel", combatLevel);
        }
        setConversionFlag(player, SKILL_VALIDATION);
        setConversionFlag(player, SKILL_VALIDATION_2);
        return;
    }
    public void correctMasterBadges(obj_id player) throws InterruptedException
    {
        if (hasConversionFlag(player, MASTER_BADGE_CORRECTIONS))
        {
            return;
        }
        String[] allSkills = getSkillListingForPlayer(player);
        if (allSkills != null && allSkills.length > 0)
        {
            for (String allSkill : allSkills) {
                if (allSkill.endsWith("_master")) {
                    badge.grantMasterSkillBadge(player, allSkill);
                }
            }
        }
        badge.checkBadgeCount(player);
        setConversionFlag(player, MASTER_BADGE_CORRECTIONS);
    }
    public void correctEv9D9Quest(obj_id player) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "legacy_head_runaway", "locationOfCache"))
        {
            if (groundquests.hasCompletedTask(player, "legacy_head_runaway", "legacy_head_runaway_e91"))
            {
                groundquests.sendSignal(player, "droidsAllDead");
            }
        }
    }
    public void correctSyrenQuests(obj_id player) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "c_story1_2_imp") && groundquests.isQuestActiveOrComplete(player, "c_story1_3_imp"))
        {
            groundquests.completeQuest(player, "c_story1_2_imp");
        }
        if (groundquests.isQuestActive(player, "c_story1_2_reb") && groundquests.isQuestActiveOrComplete(player, "c_story1_3_reb"))
        {
            groundquests.completeQuest(player, "c_story1_2_reb");
        }
        if (groundquests.isQuestActive(player, "c_story1_2_neu") && groundquests.isQuestActiveOrComplete(player, "c_story1_3_neu"))
        {
            groundquests.completeQuest(player, "c_story1_2_neu");
        }
    }
    public void checkForContentPathContinuation(obj_id player) throws InterruptedException
    {
        String prerequisiteQuest = "talus_nashal_goto_mother";
        String pointerQuest01 = "talus_dearic_goto_dathnaeya";
        String pointerQuest02 = "";
        String nextContentPathQuest = "talus_dearic_crisis_of_allegiance";
        if (groundquests.hasCompletedQuest(player, prerequisiteQuest) && !groundquests.isQuestActive(player, pointerQuest01) && !groundquests.isQuestActiveOrComplete(player, nextContentPathQuest))
        {
            dictionary webster = new dictionary();
            webster.put("player", player);
            webster.put("questToGrant", pointerQuest01);
            messageTo(player, "handleContentPathContinuation", webster, 5, false);
        }
        return;
    }
    public int handleContentPathContinuation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null && !params.isEmpty())
        {
            obj_id player = params.getObjId("player");
            if (isIdValid(player))
            {
                String newQuestPointer = params.getString("questToGrant");
                groundquests.grantQuest(player, newQuestPointer);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void updateBountyHunterMissions(obj_id player) throws InterruptedException
    {
        messageTo(player, "informantComm", null, 10.0f, false);
        return;
    }
    public void updateChangedQuests(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "alreadyCheckedUpdatedQuests"))
        {
            return;
        }
        utils.setScriptVar(player, "alreadyCheckedUpdatedQuests", true);
        String datatable = "datatables/quest/ground/updated_quests.iff";
        String oldQuestColumn = "old_version_quest_name";
        String newQuestColumn = "new_version_quest_name";
        String[] oldQuests = dataTableGetStringColumn(datatable, oldQuestColumn);
        String updatedQuestsMessage = utils.packStringId(new string_id("quest/groundquests", "quest_updated_msg")) + " \n";
        boolean updatedQuestsFound = false;
        if (oldQuests != null && oldQuests.length > 0)
        {
            for (String oldQuestName : oldQuests) {
                if (groundquests.isQuestActive(player, oldQuestName)) {
                    String newQuestName = dataTableGetString(datatable, oldQuestName, newQuestColumn);
                    groundquests.clearQuest(player, oldQuestName);
                    if (!newQuestName.equals("delete")) {
                        updatedQuestsFound = true;
                        groundquests.grantQuestNoAcceptUI(player, newQuestName, false);
                        String questlistDatatable = "questlist";
                        int newQuestId = groundquests.getQuestIdFromString(newQuestName);
                        String column = groundquests.datatableColumnjournalEntryTitle;
                        String taskTitle = groundquests.getStringDataEntry(questlistDatatable, newQuestId, 0, column);
                        updatedQuestsMessage = updatedQuestsMessage + "\n \\#pcontrast1 " + taskTitle + " \\#. ";
                    }
                }
            }
        }
        if (updatedQuestsFound)
        {
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, player, player, "noHandlerNeeded");
            String title = utils.packStringId(new string_id("quest/groundquests", "quest_updated_title"));
            String OK_BUTTON = utils.packStringId(new string_id("new_player", "okay_button"));
            sui.setSizeProperty(pid, 400, 200);
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, updatedQuestsMessage);
            sui.msgboxButtonSetup(pid, sui.OK_ONLY);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
            sui.showSUIPage(pid);
        }
        return;
    }
    public void correctNabooKickQuests(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            if (isPlayer(player))
            {
                if (groundquests.isQuestActiveOrComplete(player, "naboo_kadaraa_tipping_the_balance_1"))
                {
                    if (groundquests.isQuestActive(player, "legacy_naboo_kick_imp"))
                    {
                        groundquests.completeQuest(player, "legacy_naboo_kick_imp");
                    }
                    if (groundquests.isQuestActive(player, "legacy_naboo_kick_reb"))
                    {
                        groundquests.completeQuest(player, "legacy_naboo_kick_reb");
                    }
                }
            }
        }
    }
    public void grantNextTraderAndEntertainerSkill(obj_id player) throws InterruptedException
    {
        if (hasConversionFlag(player, ENT_TRADER_FREE_SKILL_FLAG))
        {
            return;
        }
        String template = getSkillTemplate(player);
        if (template.equals("") || template == null)
        {
            setConversionFlag(player, ENT_TRADER_FREE_SKILL_FLAG);
            return;
        }
        if (!template.startsWith("trader") && !template.startsWith("entertainer"))
        {
            setConversionFlag(player, ENT_TRADER_FREE_SKILL_FLAG);
            return;
        }
        if (getLevel(player) < 6)
        {
            setConversionFlag(player, ENT_TRADER_FREE_SKILL_FLAG);
            return;
        }
        String templateSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, template, "template");
        String[] skillList = split(templateSkills, ',');
        String workingSkill = getWorkingSkill(player);
        skill_template.grantRoadmapItem(player);
        grantSkill(player, workingSkill);
        setWorkingSkill(player, skill_template.getNextWorkingSkill(player));
        int xpTotal = respec.getCurrentXpTotal(player);
        int currentLevel = getLevel(player);
        int xpForLevel = dataTableGetInt("datatables/player/player_level.iff", currentLevel - 1, "xp_required");
        int xpDifference = xpTotal - xpForLevel;
        xpDifference *= -1;
        String xpType = "";
        if (template.startsWith("trader"))
        {
            xpType = "crafting";
        }
        if (template.startsWith("entertainer"))
        {
            xpType = "entertainer";
        }
        grantExperiencePoints(player, xpType, xpDifference);
        setConversionFlag(player, ENT_TRADER_FREE_SKILL_FLAG);
    }
    public void addPlayerScripts(obj_id player) throws InterruptedException
    {
        if (!hasScript(player, "systems.storyteller.storyteller_commands"))
        {
            attachScript(player, "systems.storyteller.storyteller_commands");
        }
        if (!hasScript(player, "systems.gcw.player_pvp"))
        {
            attachScript(player, "systems.gcw.player_pvp");
        }
        if (!hasScript(player, "space.combat.combat_ship_player"))
        {
            attachScript(player, "space.combat.combat_ship_player");
        }
        if (!hasScript(player, "player.player_chat"))
        {
            attachScript(player, "player.player_chat");
        }
        if (!hasScript(player, "player.veteran_rewards"))
        {
            attachScript(player, "player.veteran_rewards");
        }
        if (!hasScript(player, "player.player_beastmaster"))
        {
            attachScript(player, "player.player_beastmaster");
        }
        if (!hasScript(player, "player.player_guild"))
        {
            attachScript(player, "player.player_guild");
        }
        if (!hasScript(player, "player.player_collection"))
        {
            attachScript(player, "player.player_collection");
        }
        if (!hasScript(player, "player.player_instance"))
        {
            attachScript(player, "player.player_instance");
        }
        if (!hasScript(player, "player.player_saga_quest"))
        {
            attachScript(player, "player.player_saga_quest");
        }
    }
    public void updateResidencyLinks(obj_id player) throws InterruptedException
    {
        obj_id structure = getHouseId(player);
        if (!isIdValid(structure))
        {
            return;
        }
        int currentCity = getCitizenOfCityId(player);
        obj_id currentCityMayor = cityGetLeader(currentCity);
        if (player == currentCityMayor)
        {
            setHouseId(player, obj_id.NULL_ID);
            messageTo(structure, "removeResidentVar", null, 0.0f, true);
        }
        else 
        {
            dictionary dict = new dictionary();
            dict.put("player", player);
            String playerName = getName(player);
            if ((playerName != null) && (playerName.length() > 0))
            {
                dict.put("playerName", playerName);
            }
            messageTo(structure, "checkResidenceLinks", dict, 1, false, player, "residentLinkFalse");
        }
    }
    public void updateLotOverLimitStructureLocation(obj_id player) throws InterruptedException
    {
        if (getAccountNumLots(getPlayerObject(player)) <= getMaxHousingLots())
        {
            removeObjVar(player, "lotOverlimit");
        }
        else 
        {
            obj_id lotOverlimitStructure = getObjIdObjVar(player, "lotOverlimit.structure_id");
            if (isIdValid(lotOverlimitStructure))
            {
                if (exists(lotOverlimitStructure) && !isInWorldCell(lotOverlimitStructure))
                {
                    setObjVar(player, "lotOverlimit.structure_location", "Datapad");
                }
                if (!hasObjVar(player, "lotOverlimit.violation_time"))
                {
                    setObjVar(player, "lotOverlimit.violation_time", getCalendarTime());
                }
            }
        }
    }
    public void updateGuildTerminals(obj_id player) throws InterruptedException
    {
        int guildId = getGuildId(player);
        obj_id pDataPad = utils.getPlayerDatapad(player);
        boolean hasTerminal = utils.playerHasItemByTemplateInDataPad(player, guild.STR_GUILD_REMOTE_DEVICE);
        if (hasTerminal)
        {
            return;
        }
        messageTo(player, "onGuildCreateTerminalDataObject", null, 0, false);
    }
    public void validateExpertises(obj_id player) throws InterruptedException
    {
        skill.validateExpertise(player);
        return;
    }
    public boolean expertiseRespecCheck(obj_id player) throws InterruptedException
    {
        int version = respec.getRespecVersion(player);
        if (version == -1)
        {
            messageTo(player, "setRespecVersion", null, 2, false);
            return false;
        }
        int playerVersion = 0;
        if (hasObjVar(player, respec.EXPERTISE_VERSION_OBJVAR))
        {
            playerVersion = getIntObjVar(player, respec.EXPERTISE_VERSION_OBJVAR);
        }
        if (version != playerVersion)
        {
            messageTo(player, "fullExpertiseReset", null, 2, false);
            sui.msgbox(player, EXPERTISE_RESET_FROM_CHANGES);
            CustomerServiceLog("professionExpertiseReset:", " player " + getFirstName(player) + "(" + player + ") has had their expertise reset due to profession updates.");
            return true;
        }
        // handle beast master expertise reset as it doesn't fall under the profession checks
        if(hasSkill(player, "expertise_bm_incubation_base_1")) {
            int row = dataTableSearchColumnForString("beast_master", "profession", respec.EXPERTISE_VERSION_TABLE);
            int bmExpertiseVersion = dataTableGetInt(respec.EXPERTISE_VERSION_TABLE, row, "version");
            int playerBmVersion = 0;
            if(hasObjVar(player, respec.BEAST_MASTER_EXPERTISE_VERSION_OBJVAR)) {
                playerBmVersion = getIntObjVar(player, respec.BEAST_MASTER_EXPERTISE_VERSION_OBJVAR);
            }
            if(playerBmVersion != bmExpertiseVersion) {
                messageTo(player, "fullExpertiseReset", null, 2, false);
                sui.msgbox(player, EXPERTISE_RESET_FROM_CHANGES);
                CustomerServiceLog("professionExpertiseReset:", " player " + getFirstName(player) + "(" + player + ") has had their expertise reset due to profession updates.");
                return true;
            }
        }
        return false;
    }
    public boolean updatePermanentSchematics(obj_id player) throws InterruptedException
    {
        return craftinglib.updatePermanentSchematics(player);
    }
    public int fullExpertiseReset(obj_id self, dictionary params) throws InterruptedException
    {
        utils.fullExpertiseReset(self, false);
        return SCRIPT_CONTINUE;
    }
    public void updateCollectionSlots(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) && !exists(player))
        {
            return;
        }
        String[] collectionList = dataTableGetStringColumn(UPDATE_COLLECTION_DATATABLE, UPDATE_COLLECTION_NAME_COLUMN);
        if (collectionList != null && collectionList.length > 0)
        {
            for (String s1 : collectionList) {
                if (hasCompletedCollection(player, s1)) {
                    String slotToGrant = dataTableGetString(UPDATE_COLLECTION_DATATABLE, s1, UPDATE_COLLECTION_SLOT_TO_GRANT);
                    String[] slotList = split(slotToGrant, ',');
                    if (slotList != null & slotList.length > 0) {
                        for (String s : slotList) {
                            if (!hasCompletedCollectionSlot(player, s)) {
                                int incrementAmount = dataTableGetInt(UPDATE_COLLECTION_DATATABLE, s1, UPDATE_COLLECTION_INCREMENT_AMOUNT);
                                modifyCollectionSlotValue(player, s, incrementAmount);
                                CustomerServiceLog("Collection:", " onInitialize Collection Update: player " + getFirstName(player) + "(" + player + ") received a Collection Slot Update on Login for Collection" + s1 + " : Slot(s) Name: " + slotToGrant + ".");
                            }
                        }
                    }
                }
            }
        }
    }
    public void updateHothFlawless(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) && !exists(player))
        {
            return;
        }
        if (hasObjVar(player, "hoth.flawless_reward"))
        {
            return;
        }
        if (badge.hasBadge(player, "champion_of_hoth"))
        {
            obj_id[] rewards = new obj_id[1];
            obj_id inventory = utils.getInventoryContainer(player);
            rewards[0] = static_item.createNewItemFunction("item_hoth_flawless_painting", inventory);
            showLootBox(player, rewards);
            setObjVar(player, "hoth.flawless_reward", 1);
        }
    }
    public void updateRenameCharacterComplete(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (!hasObjVar(player, "renameCharacterRequest.requestNewName"))
        {
            return;
        }
        final String previousRenameName = getStringObjVar(player, "renameCharacterRequest.requestNewName");
        final String currentName = getName(player);
        if ((currentName != null) && (previousRenameName != null) && previousRenameName.equals(currentName))
        {
            removeObjVar(player, "renameCharacterRequest.requestNewName");
        }
    }
    public void forceRespecBasedOnConfig(obj_id player) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "forceProfessionRespec") == null)
        {
            return;
        }
        if (!hasObjVar(player, "character_builder"))
        {
            return;
        }
        if (hasObjVar(player, FORCE_RESPEC_OBJVAR))
        {
            int version = getIntObjVar(player, FORCE_RESPEC_OBJVAR);
            if (version != FORCED_RESPEC_VERSION)
            {
                utils.fullExpertiseReset(player, false);
                respec.revokeAllSkillsAndExperience(player);
                respec.autoLevelPlayer(player, 90, false);
                setObjVar(player, FORCE_RESPEC_OBJVAR, FORCED_RESPEC_VERSION);
            }
        }
        else 
        {
            utils.fullExpertiseReset(player, false);
            respec.revokeAllSkillsAndExperience(player);
            respec.autoLevelPlayer(player, 90, false);
            setObjVar(player, FORCE_RESPEC_OBJVAR, FORCED_RESPEC_VERSION);
        }
    }
    public boolean givePlayersDeathTroopersOutbreakQuest(obj_id player) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: Player: " + player + " is being checked for old prolog quest line.");
        if (!groundquests.isQuestActiveOrComplete(player, "quest_08_dathomir_outpost"))
        {
            return false;
        }
        CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " has quest quest_08_dathomir_outpost active or completed.");
        if (groundquests.isQuestActiveOrComplete(player, "outbreak_live_conversion_rebel") || groundquests.isQuestActiveOrComplete(player, "outbreak_live_conversion_imperial") || groundquests.isQuestActiveOrComplete(player, "outbreak_live_conversion_neutral") || groundquests.isQuestActiveOrComplete(player, "outbreak_switch_to_neutral") || groundquests.isQuestActiveOrComplete(player, "outbreak_switch_to_rebel") || groundquests.isQuestActiveOrComplete(player, "outbreak_switch_to_imperial"))
        {
            return false;
        }
        CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " does not have quest outbreak_live_conversion_rebel, imperial or neutral active or completed.");
        if (groundquests.isQuestActive(player, "quest_08_dathomir_outpost"))
        {
            CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " has quest quest_08_dathomir_outpost currently active!");
            if (groundquests.isTaskActive(player, "quest_08_dathomir_outpost", "travelDathomir"))
            {
                CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " has quest quest_08_dathomir_outpost with task travelDathomir currently active! We need to complete this quest immediatly so this player is not gated!");
                int questid = questGetQuestId("quest/quest_08_dathomir_outpost");
                CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " has quest quest_08_dathomir_outpost and that quest ID is: " + questid);
                if (questid != 0)
                {
                    CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " is having quest quest_08_dathomir_outpost completed to avoid a gate!");
                    questCompleteQuest(questid, player);
                }
            }
            else 
            {
                CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: The Player: " + player + " does not have the quest quest_08_dathomir_outpost task travelDathomir currently so we are not completing the quest prematurely.");
                return false;
            }
        }
        if (factions.isRebel(player))
        {
            CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: Granting Player: " + player + " the outbreak_live_conversion_rebel quest because they are currently of rebel faction.");
            groundquests.grantQuest(player, "outbreak_live_conversion_rebel");
            return true;
        }
        else if (factions.isImperial(player))
        {
            CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: Granting Player: " + player + " the outbreak_live_conversion_rebel quest because they are currently of imp faction.");
            groundquests.grantQuest(player, "outbreak_live_conversion_imperial");
            return true;
        }
        CustomerServiceLog("outbreak_themepark", "givePlayersDeathTroopersOutbreakQuest: Granting Player: " + player + " the outbreak_live_conversion_rebel quest because they are currently of neutral faction.");
        groundquests.grantQuest(player, "outbreak_live_conversion_neutral");
        return true;
    }
    public boolean givePlayersDeathTroopersPrologQuestComlink(obj_id player) throws InterruptedException
    {
        if (isFreeTrialAccount(player))
        {
            return false;
        }
        if (isInTutorialArea(player))
        {
            return false;
        }
        if (hasObjVar(player, "prologComlink"))
        {
            return false;
        }
        if (!groundquests.isQuestActiveOrComplete(player, "quest_01_comlink_call_to_adventure_neg_faction") && !groundquests.isQuestActiveOrComplete(player, "quest_01_comlink_call_to_adventure_neut_faction") && !groundquests.isQuestActiveOrComplete(player, "quest_01_comlink_call_to_adventure_pos_faction"))
        {
            obj_id[] rewards = new obj_id[1];
            obj_id inventory = utils.getInventoryContainer(player);
            if (!isValidId(inventory))
            {
                return false;
            }
            rewards[0] = static_item.createNewItemFunction("item_publish_gift_update_14_comlink", inventory);
            if (!isValidId(rewards[0]))
            {
                return false;
            }
            showLootBox(player, rewards);
        }
        sendSystemMessage(player, JABBAS_COMLINK);
        setObjVar(player, "prologComlink", true);
        return true;
    }
    public boolean removeDeprecatedQuests(obj_id player) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "u16_nym_themepark_weed_pulling"))
        {
            groundquests.clearQuest(player, "u16_nym_themepark_weed_pulling");
        }
        return true;
    }
    public void handleEsbAnniversaryGifts(obj_id player) throws InterruptedException {
        if(!utils.checkConfigFlag("Custom", "grantEsbAnniversaryRewards")) {
            return;
        }
        if(hasObjVar(player, "publish_gifts.has_esb_rewards")) {
            return;
        }
        obj_id inv = utils.getInventoryContainer(player);
        obj_id col1 = createObjectInInventoryAllowOverload("object/tangible/collection/esb_week_1.iff", player);
        obj_id col2 = createObjectInInventoryAllowOverload("object/tangible/collection/esb_week_2.iff", player);
        obj_id col3 = createObjectInInventoryAllowOverload("object/tangible/collection/esb_week_3.iff", player);
        obj_id col4 = createObjectInInventoryAllowOverload("object/tangible/collection/esb_week_4.iff", player);
        obj_id cos1 = static_item.createNewItemFunction("item_esb_luke_costume", inv);
        obj_id cos2 = static_item.createNewItemFunction("item_esb_leia_costume", inv);
        obj_id cos3 = static_item.createNewItemFunction("item_esb_han_solo_costume", inv);
        obj_id cos4 = static_item.createNewItemFunction("item_esb_lando_costume", inv);
        showLootBox(player, new obj_id[] {col1, col2, col3, col4, cos1, cos2, cos3, cos4});
        setObjVar(player, "publish_gifts.has_esb_rewards", 1);
    }

    public void handleMailOptInRewards(obj_id player) throws InterruptedException {
        if(!utils.checkConfigFlag("Custom", "grantMailOptInRewards")) {
            return;
        }
        if(hasObjVar(player, "publish_gifts.has_mail_opt_in_rewards")) {
            return;
        }
        obj_id inv = utils.getInventoryContainer(player);
        obj_id r1 = static_item.createNewItemFunction("col_mail_opt_in_1_01", inv);
        obj_id r2 = static_item.createNewItemFunction("col_mail_opt_in_1_02", inv);
        obj_id r3 = static_item.createNewItemFunction("col_mail_opt_in_1_03", inv);
        obj_id r4 = static_item.createNewItemFunction("col_mail_opt_in_2_01", inv);
        obj_id r5 = static_item.createNewItemFunction("col_mail_opt_in_2_02", inv);
        obj_id r6 = static_item.createNewItemFunction("col_mail_opt_in_2_03", inv);
        obj_id r7 = static_item.createNewItemFunction("col_mail_opt_in_3_01", inv);
        obj_id r8 = static_item.createNewItemFunction("col_mail_opt_in_3_02", inv);
        obj_id r9 = static_item.createNewItemFunction("col_mail_opt_in_3_03", inv);
        obj_id r10 = static_item.createNewItemFunction("col_mail_opt_in_4_01", inv);
        obj_id r11 = static_item.createNewItemFunction("col_mail_opt_in_4_02", inv);
        showLootBox(player, new obj_id[] {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11});
        setObjVar(player, "publish_gifts.has_mail_opt_in_rewards", 1);
    }
}

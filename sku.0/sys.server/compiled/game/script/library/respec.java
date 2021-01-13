package script.library;

import script.*;

import java.util.Enumeration;

public class respec extends script.base_script
{
    public respec()
    {
    }
    public static final String EXPERTISE_VERSION_TABLE = "datatables/expertise/respec_version.iff";
    public static final String EXPERTISE_VERSION_OBJVAR = "professionExpertiseVersion";
    public static final String BEAST_MASTER_EXPERTISE_VERSION_OBJVAR = "beastMasterExpertiseVersion";
    public static final int RESPEC_PRICE_MULTIPLIER = 10000;
    public static final int[] RESPEC_COST = new int[]
    {
        0,
        100000,
        150000,
        250000,
        500000,
        1000000,
        6000000,
        7000000,
        8000000,
        9000000,
        10000000,
        25000000
    };
    public static final String OBJVAR_RESPEC_TOKEN = "respecToken.token";
    public static final String OBJVAR_RESPEC_TOKEN_USED = "respecToken.tokenUsed";
    public static final String OBJVAR_RESPEC_ROOT = "respec";
    public static final String OBJVAR_RESPEC = "respec.authorized";
    public static final String OBJVAR_REMOVE_ON_LOGOUT = "respec.removeonlogout";
    public static final String OBJVAR_RESPEC_COUNT = "respec.count";
    public static final String OBJVAR_TIMESTAMP = "respec.timestamp";
    public static final String OBJVAR_LAST_TIMESTAMP = "respec.lastTimestamp";
    public static final String SCRIPT_GRANT_ON_LOGIN = "systems.respec.grant_respec_on_login";
    public static final String SCRIPT_GRANT_SINGLE_ON_LOGIN = "systems.respec.grant_single_respec_on_login";
    public static final String SCRIPT_CHECK_INFORM = "systems.respec.check_inform_respec";
    public static final String MESSAGE_ON_GRANT = "onRespecGrant";
    public static final String MESSAGE_ON_EXPIRE = "onRespecExpire";
    public static final String MESSAGE_HAS_TOKEN = "onRespecHasToken";
    public static final String PROF_LEVEL_ARRAY = "playerRespec.levelArrayComEntCra";
    public static final String OBJVAR_RESPEC_DECAY_TIME = "playerRespec.decayTime";
    public static final string_id RESPEC_DECAY_MESSAGE = new string_id("spam", "respec_decay_message");
    public static final int PROF_LEVEL_COMBAT = 0;
    public static final int PROF_LEVEL_ENT = 1;
    public static final int PROF_LEVEL_TRADER = 2;
    public static void startClickRespec(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "respecToken"))
        {
            removeObjVar(player, "respecToken");
        }
        if (hasObjVar(player, "respec"))
        {
            removeObjVar(player, "respec");
        }
        int level = getIntObjVar(player, "clickRespec.combatLevel");
        if (level > 0 && !hasObjVar(player, "combatLevel"))
        {
            setObjVar(player, "combatLevel", level);
        }
        if (level > 0 && !hasCommand(player, "veteranPlayerBuff"))
        {
            grantCommand(player, "veteranPlayerBuff");
        }
        if (hasScript(player, "systems.respec.click_combat_respec"))
        {
            playUiEffect(player, "showMediator=ws_professiontemplateselect");
        }
        else 
        {
            attachScript(player, "systems.respec.click_combat_respec");
        }
    }
    public static void startNpcRespec(obj_id player, obj_id npcGrantingRespec) throws InterruptedException
    {
        startNpcRespec(player, npcGrantingRespec, false);
    }
    public static void startNpcRespec(obj_id player, obj_id npcGrantingRespec, boolean isFree) throws InterruptedException
    {
        if (hasObjVar(player, "clickRespec.tokenId"))
        {
            sendSystemMessage(player, new string_id("spam", "cant_use_respec_npc"));
            return;
        }
        setObjVar(player, "npcRespec.inProgress", 1);
        setObjVar(player, "npcRespec.tokenId", npcGrantingRespec);
        setObjVar(player, "npcRespec.oldTemplate", getSkillTemplate(player));
        setObjVar(player, "npcRespec.isFree", isFree);
        if (utils.hasScriptVar(player, "combat.weaponCertified"))
        {
            utils.removeScriptVar(player, "combat.weaponCertified");
        }
        if (hasScript(player, "systems.respec.click_combat_respec"))
        {
            playUiEffect(player, "showMediator=ws_professiontemplateselect");
        }
        else 
        {
            attachScript(player, "systems.respec.click_combat_respec");
        }
    }
    public static void handleNpcRespec(obj_id player, String skillTemplateName) throws InterruptedException
    {
        String oldTemplate = getStringObjVar(player, "npcRespec.oldTemplate");
        if (oldTemplate == null || oldTemplate.length() < 1 || oldTemplate.equals(getSkillTemplate(player)))
        {
            removeObjVar(player, "npcRespec");
            detachScript(player, "systems.respec.click_combat_respec");
            return;
        }
        boolean isFree = getBooleanObjVar(player, "npcRespec.isFree");
        int cost = isFree ? 0 : getRespecCost(player);
        int balance = getTotalMoney(player);
        if (cost > balance)
        {
            setSkillTemplate(player, oldTemplate);
            prose_package pp = new prose_package();
            pp.stringId = new string_id("spam", "not_enough_cash_4_respec");
            pp.digitInteger = cost;
            sendSystemMessageProse(player, pp);
            return;
        }
        String msg = "NPC_RESPEC: %TU has initiated a respec via NPC for " + cost + " credits.  Current combat level is " + getLevel(player) + "; current skill template is " + oldTemplate;
        trace.log("respec", msg, player, trace.TL_CS_LOG);
        pet_lib.destroyOfficerPets(player);
        obj_id weapon = getCurrentWeapon(player);
        // make sure our weapon is valid and isn't the base default weapon
        if (isIdValid(weapon) && !getTemplateName(weapon).equals("object/weapon/melee/unarmed/unarmed_default_player.iff"))
        {
            putInOverloaded(weapon, utils.getInventoryContainer(player));
        }

        if (cost > 0)
        {
            money.requestPayment(player, money.ACCT_SKILL_TRAINING, cost, "none", null, false);
        }
        if (!oldTemplate.startsWith("entertainer") && !oldTemplate.startsWith("trader"))
        {
            setProfessionLevel(player, PROF_LEVEL_COMBAT, getLevel(player));
        }
        if (oldTemplate.startsWith("entertainer"))
        {
            setProfessionLevel(player, PROF_LEVEL_ENT, getLevel(player));
        }
        if (oldTemplate.startsWith("trader"))
        {
            setProfessionLevel(player, PROF_LEVEL_TRADER, getLevel(player));
        }
        int targetLevel = -1;
        if (!skillTemplateName.startsWith("entertainer") && !skillTemplateName.startsWith("trader"))
        {
            targetLevel = getCombatLevel(player);
            if (targetLevel == -1)
            {
                targetLevel = hasObjVar(player, "combatLevel") ? getIntObjVar(player, "combatLevel") : 5;
                setProfessionLevel(player, PROF_LEVEL_COMBAT, targetLevel);
            }
        }
        if (skillTemplateName.startsWith("entertainer"))
        {
            targetLevel = getEntertainerLevel(player);
        }
        if (skillTemplateName.startsWith("trader"))
        {
            targetLevel = getTraderLevel(player);
        }
        earnProfessionSkillsViaNpc(player, skillTemplateName, true, targetLevel);
        int numBought = 0;
        if (hasObjVar(player, "respecsBought"))
        {
            numBought = getIntObjVar(player, "respecsBought");
        }
        if (!isFree)
        {
            setObjVar(player, "respecsBought", ++numBought);
        }
        if (utils.hasScriptVar(player, "holoId1"))
        {
            performance.holographicCleanup(player);
        }
        playUiEffect(player, "showMediator=ws_roadmap");
        msg = "NPC_RESPEC: %TU has completed a respec via NPC for " + cost + " credits.  New combat level is " + getLevel(player) + "; New skill template is " + getSkillTemplate(player);
        trace.log("respec", msg, player, trace.TL_CS_LOG);
        if (utils.isProfession(player, utils.SMUGGLER))
        {
            messageTo(player, "applySmugglingBonuses", null, 1.0f, false);
        }
        else 
        {
            messageTo(player, "removeSmugglingBonuses", null, 1.0f, false);
        }
    }
    public static void earnProfessionSkillsViaNpc(obj_id player, String skillTemplateName, boolean withItems, int level) throws InterruptedException
    {
        setObjVar(player, "npcRespec.granting", 1);
        callable.storeCallables(player);
        revokeAllSkillsAndExperience(player);
        buff.removeAllBuffs(player, true, true);
        obj_id weapon = getCurrentWeapon(player);
        if (isIdValid(weapon))
        {
            putInOverloaded(weapon, utils.getInventoryContainer(player));
        }
        if (level > 0)
        {
            autoLevelPlayer(player, level, true);
        }
        else 
        {
            if (skillTemplateName.startsWith("trader") && hasObjVar(player, "traderPct"))
            {
                float oldPct = 0.0f;
                if (hasObjVar(player, "traderPct"))
                {
                    oldPct = getFloatObjVar(player, "traderPct");
                }
                if (hasObjVar(player, "clickRespec.npeRespec"))
                {
                    oldPct = (level / 80.0f);
                    if (oldPct < 0.1f)
                    {
                        oldPct = 0.10f;
                    }
                    if (oldPct > 1.0f)
                    {
                        oldPct = 1.0f;
                    }
                }
                setPercentageCompletion(player, skillTemplateName, oldPct, withItems);
            }
            else if (skillTemplateName.startsWith("entertainer") && hasObjVar(player, "entWorkingSkill"))
            {
                String workingSkill = "class_entertainer_phase1_02";
                if (hasObjVar(player, "entWorkingSkill"))
                {
                    workingSkill = getStringObjVar(player, "entWorkingSkill");
                }
                if (hasObjVar(player, "clickRespec.npeRespec"))
                {
                    float pct = (level / 80.0f);
                    if (pct < 0.1f)
                    {
                        pct = 0.1f;
                    }
                    if (pct > 1.0f)
                    {
                        pct = 1.0f;
                    }
                    setPercentageCompletion(player, skillTemplateName, pct, withItems);
                }
                else 
                {
                    grantProfessionSkills(player, skillTemplateName, workingSkill, withItems);
                }
            }
        }
        if (utils.isProfession(player, utils.TRADER))
        {
            grantSkill(player, "class_trader");
        }
        if (getLevel(player) < 5)
        {
            autoLevelPlayer(player, 5, true);
        }
        skill.recalcPlayerPools(player, true);
        skill.setPlayerStatsForLevel(player, getLevel(player));
        removeObjVar(player, "npcRespec");
        if (hasObjVar(player, "clickRespec.npeRespec"))
        {
            removeObjVar(player, "clickRespec.npeRespec");
        }
        if ((skillTemplateName.startsWith("force_sensitive")) || (skillTemplateName.startsWith("bounty_hunter")))
        {
            grantSkill(player, "expertise");
        }
        if (hasScript(player, "systems.respec.click_combat_respec"))
        {
            detachScript(player, "systems.respec.click_combat_respec");
        }
        utils.setScriptVar(player, "respec.conceal", 1);
    }
    public static int getRespecCost(obj_id player) throws InterruptedException
    {
        int numBought = 0;
        if (hasObjVar(player, "respecsBought"))
        {
            numBought = getIntObjVar(player, "respecsBought");
        }
        int respecMod = 1;
        if (hasObjVar(player, "respec_voucher"))
        {
            respecMod = 2;
        }
        if (numBought < RESPEC_COST.length)
        {
            return (RESPEC_COST[numBought] / respecMod);
        }
        return ((RESPEC_COST[RESPEC_COST.length - 1]) / respecMod);
    }
    public static int[] getProfessionLevelArray(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, PROF_LEVEL_ARRAY))
        {
            return new int[]{
                -1,
                -1,
                -1
            };
        }
        return getIntArrayObjVar(player, PROF_LEVEL_ARRAY);
    }
    public static int getCombatLevel(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, PROF_LEVEL_ARRAY))
        {
            return -1;
        }
        int[] profLevels = getProfessionLevelArray(player);
        return profLevels[PROF_LEVEL_COMBAT];
    }
    public static int getEntertainerLevel(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, PROF_LEVEL_ARRAY))
        {
            return -1;
        }
        int[] profLevels = getProfessionLevelArray(player);
        return profLevels[PROF_LEVEL_ENT];
    }
    public static int getTraderLevel(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, PROF_LEVEL_ARRAY))
        {
            return -1;
        }
        int[] profLevels = getProfessionLevelArray(player);
        return profLevels[PROF_LEVEL_TRADER];
    }
    public static void setProfessionLevel(obj_id player, int profType, int level) throws InterruptedException
    {
        int[] profLevelArray = getProfessionLevelArray(player);
        int storedLevel = profLevelArray[profType];
        if (level >= storedLevel)
        {
            profLevelArray[profType] = level;
        }
        setObjVar(player, PROF_LEVEL_ARRAY, profLevelArray);
    }
    public static void setProfessionLevelArray(obj_id player, int[] profLevelArray) throws InterruptedException
    {
        setObjVar(player, PROF_LEVEL_ARRAY, profLevelArray);
    }
    public static void handleNpcRealloc(obj_id player) throws InterruptedException
    {
        int cost = getReallocationCost(player);
        int balance = getTotalMoney(player);
        if (cost > balance)
        {
            prose_package pp = new prose_package();
            pp.stringId = new string_id("spam", "not_enough_cash_4_respec");
            pp.digitInteger = cost;
            sendSystemMessageProse(player, pp);
            return;
        }
        String msg = "NPC_REALLOC: %TU has initiated a realloc via NPC for " + cost + " credits.";
        trace.log("respec", msg, player, trace.TL_CS_LOG);
        if (cost > 0)
        {
            money.requestPayment(player, money.ACCT_SKILL_TRAINING, cost, "none", null, false);
        }
        utils.fullExpertiseReset(player, true);
        pet_lib.destroyOfficerPets(player);
        int numBought = 0;
        if (hasObjVar(player, "expertise_reset"))
        {
            numBought = getIntObjVar(player, "expertise_reset");
        }
        setObjVar(player, "expertise_reset", ++numBought);
        if (!hasSkill(player, "expertise"))
        {
            grantSkill(player, "expertise");
        }
        msg = "NPC_REALLOC: %TU has completed a realloc via NPC for " + cost + " credits.";
        trace.log("respec", msg, player, trace.TL_CS_LOG);
        if (utils.isProfession(player, utils.SMUGGLER))
        {
            messageTo(player, "applySmugglingBonuses", null, 1.0f, false);
        }
        else 
        {
            messageTo(player, "removeSmugglingBonuses", null, 1.0f, false);
        }
    }
    public static int getReallocationCost(obj_id player) throws InterruptedException
    {
        if (!hasFreeReallocation(player))
        {
            int numBought = (numFreeAllocations(player) * -1);
            return (numBought * 500);
        }
        return 0;
    }
    public static boolean hasFreeReallocation(obj_id player) throws InterruptedException
    {
        return 0 <= numFreeAllocations(player);
    }
    public static int numFreeAllocations(obj_id player) throws InterruptedException
    {
        int numBought = 0;
        if (hasObjVar(player, "expertise_reset"))
        {
            numBought = getIntObjVar(player, "expertise_reset");
        }
        return (5 - numBought);
    }
    public static int getOldCombatLevel(obj_id player) throws InterruptedException
    {
        logCharacterSkills(player);
        String[] xpTypes = dataTableGetStringColumn("datatables/player/old_player_level.iff", "xp_type");
        int[] xpMults = dataTableGetIntColumn("datatables/player/old_player_level.iff", "xp_multiplier");
        String[] skillList = getSkillListingForPlayer(player);
        int totalXp = 0;
        int level = 0;
        if ((skillList != null) && (skillList.length != 0))
        {
            dictionary xpReqs;
            Enumeration e;
            String xpType;

            for (String aSkillList : skillList) {
                xpReqs = getSkillPrerequisiteExperience(aSkillList);
                if ((xpReqs == null) || (xpReqs.isEmpty())) {
                    continue;
                }
                e = xpReqs.keys();
                xpType = (String) (e.nextElement());
                int xpCost = xpReqs.getInt(xpType);
                int idx = utils.getElementPositionInArray(xpTypes, xpType);
                if (idx > -1) {
                    totalXp += (xpCost * xpMults[idx]);
                }
            }
        }
        int[] levelList = dataTableGetIntColumn("datatables/player/old_player_level.iff", "xp_required");
        if ((levelList != null) && (levelList.length != 0))
        {
            for (int xpAmountForLevel : levelList) {
                if (xpAmountForLevel <= totalXp) {
                    level++;
                } else {
                    break;
                }
            }
        }
        if (level < 1)
        {
            level = 1;
        }
        CustomerServiceLog("click_respec", "Player %TU :: *** Combat Level = " + level + " ***", player);
        return level;
    }
    public static void logCharacterSkills(obj_id player) throws InterruptedException
    {
        CustomerServiceLog("click_respec", "Player %TU :: *** Skill List ***", player);
        String[] skillList = getSkillListingForPlayer(player);
        for (String aSkillList : skillList) {
            CustomerServiceLog("click_respec", "Player %TU :: " + aSkillList, player);
        }
        CustomerServiceLog("click_respec", "Player %TU :: *** Experience List ***", player);
        dictionary xpList = getExperiencePoints(player);
        java.util.Enumeration e = xpList.keys();
        String xpType;
        while (e.hasMoreElements())
        {
            xpType = (String) (e.nextElement());
            int xpCost = xpList.getInt(xpType);
            CustomerServiceLog("click_respec", "Player %TU :: " + xpType + " = " + xpCost, player);
        }
    }
    public static boolean autoLevelPlayer(obj_id player, int level, boolean withItems) throws InterruptedException
    {
        int oldXpAmount = getCurrentXpTotal(player);
        int newXpAmount = dataTableGetInt("datatables/player/player_level.iff", level - 1, "xp_required");
        String template = getSkillTemplate(player);
        if (template == null || template.equals(""))
        {
            return false;
        }
        String templateSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, template, "template");
        String[] skillList = split(templateSkills, ',');
        String xp_type = "combat_general";
        if (template.startsWith("entertainer"))
        {
            xp_type = "entertainer";
        }
        else if (template.startsWith("trader"))
        {
            xp_type = "crafting";
        }
        if (newXpAmount > oldXpAmount)
        {
            setWorkingSkill(player, skillList[0]);
            for (String skill : skillList) {
                int skillCost = getSkillXpCost(player, skill);
                if (skillCost <= newXpAmount) {
                    newXpAmount -= skillCost;
                    if (!hasSkill(player, skill)) {
                        grantSkill(player, skill);
                        if (withItems) {
                            skill_template.grantRoadmapItem(player);
                        }
                    }
                    setWorkingSkill(player, skill_template.getNextWorkingSkill(player));
                } else {
                    break;
                }
            }
            int curXp = getExperiencePoints(player, xp_type);
            curXp = newXpAmount - curXp;
            grantExperiencePoints(player, xp_type, curXp);
        }
        else if (newXpAmount < oldXpAmount)
        {
            int curXp = getExperiencePoints(player, xp_type);
            grantExperiencePoints(player, xp_type, -curXp);
            int xpDiff = oldXpAmount - newXpAmount - curXp;
            for (int i = 0; i < (skillList.length - 1) + 1; i++)
            {
                if (!hasSkill(player, skillList[i]))
                {
                    continue;
                }
                if (xpDiff > 0)
                {
                    int skillCost = getSkillXpCost(player, skillList[i]);
                    xpDiff -= skillCost;
                    revokeSkillSilent(player, skillList[i]);
                }
                else 
                {
                    grantExperiencePoints(player, xp_type, -xpDiff);
                    setWorkingSkill(player, skillList[i]);
                    break;
                }
            }
        }
        return true;
    }
    public static int getSkillXpCost(obj_id player, String skillName) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player) || skillName == null || skillName.equals(""))
        {
            return 0;
        }
        dictionary xpReqs = getSkillPrerequisiteExperience(skillName);
        if ((xpReqs == null) || (xpReqs.isEmpty()))
        {
            return 0;
        }
        java.util.Enumeration e = xpReqs.keys();
        String xpType = (String)(e.nextElement());
        return xpReqs.getInt(xpType);
    }
    public static int getCurrentXpTotal(obj_id player) throws InterruptedException
    {
        int totalXp = 0;
        String template = getSkillTemplate(player);
        if (template == null || template.equals(""))
        {
            return 0;
        }
        String templateSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, template, "template");
        String[] skillList = split(templateSkills, ',');
        String xp_type = "combat_general";
        if (template.startsWith("entertainer"))
        {
            xp_type = "entertainer";
        }
        else if (template.startsWith("trader"))
        {
            xp_type = "crafting";
        }
        for (String aSkillList : skillList) {
            if (hasSkill(player, aSkillList)) {
                totalXp += getSkillXpCost(player, aSkillList);
            }
        }
        totalXp += getExperiencePoints(player, xp_type);
        return totalXp;
    }
    public static void earnProfessionSkills(obj_id player, String skillTemplateName, boolean withItems) throws InterruptedException
    {
        setObjVar(player, "clickRespec.granting", 1);
        pet_lib.destroyOfficerPets(player);
        revokeAllSkillsAndExperience(player);
        buff.removeAllBuffs(player, true, true);
        int combatLevel = 0;
        if (hasObjVar(player, "clickRespec.combatLevel"))
        {
            combatLevel = getIntObjVar(player, "clickRespec.combatLevel");
        }
        if (combatLevel > 0)
        {
            if (skillTemplateName.startsWith("trader"))
            {
                float oldPct = 0.0f;
                if (hasObjVar(player, "traderPct"))
                {
                    oldPct = getFloatObjVar(player, "traderPct");
                }
                if (hasObjVar(player, "clickRespec.npeRespec"))
                {
                    oldPct = (combatLevel / 80.0f);
                    if (oldPct < 0.0f)
                    {
                        oldPct = 0.0f;
                    }
                    if (oldPct > 1.0f)
                    {
                        oldPct = 1.0f;
                    }
                }
                setPercentageCompletion(player, skillTemplateName, oldPct, withItems);
            }
            else if (skillTemplateName.startsWith("entertainer"))
            {
                String workingSkill = "class_entertainer_phase1_02";
                if (hasObjVar(player, "entWorkingSkill"))
                {
                    workingSkill = getStringObjVar(player, "entWorkingSkill");
                }
                if (hasObjVar(player, "clickRespec.npeRespec"))
                {
                    float pct = (combatLevel / 80.0f);
                    if (pct < 0.0f)
                    {
                        pct = 0.0f;
                    }
                    if (pct > 1.0f)
                    {
                        pct = 1.0f;
                    }
                    setPercentageCompletion(player, skillTemplateName, pct, withItems);
                }
                else 
                {
                    grantProfessionSkills(player, skillTemplateName, workingSkill, withItems);
                }
            }
            else 
            {
                respec.autoLevelPlayer(player, combatLevel, withItems);
            }
            if (combatLevel < 11)
            {
                contentPathHandoff(player);
            }
        }
        skill.recalcPlayerPools(player, true);
        removeObjVar(player, "clickRespec");
        if (hasScript(player, "systems.respec.click_combat_respec"))
        {
            detachScript(player, "systems.respec.click_combat_respec");
        }
        utils.setScriptVar(player, "respec.conceal", 1);
        if ((skillTemplateName.startsWith("force_sensitive")) || (skillTemplateName.startsWith("bounty_hunter")))
        {
            grantSkill(player, "expertise");
        }
        if (utils.isProfession(player, utils.SMUGGLER))
        {
            messageTo(player, "applySmugglingBonuses", null, 1.0f, false);
        }
        else 
        {
            messageTo(player, "removeSmugglingBonuses", null, 1.0f, false);
        }
        String msg = "OBJECT_RESPEC: %TU has completed a respec via respec object. New combat level is " + getLevel(player) + "; new skill template is " + getSkillTemplate(player);
        trace.log("respec", msg, player, trace.TL_CS_LOG);
    }
    public static void grantProfessionSkills(obj_id player, String skillTemplateName, String workingSkill, boolean withItems) throws InterruptedException
    {
        if (workingSkill != null && workingSkill.equals("master"))
        {
            workingSkill = null;
        }
        String templateSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, skillTemplateName, "template");
        String[] skillList = split(templateSkills, ',');
        setWorkingSkill(player, skillList[0]);
        for (String aSkillList : skillList) {
            if (workingSkill != null && aSkillList.equals(workingSkill)) {
                break;
            }
            grantSkill(player, aSkillList);
            if (withItems) {
                skill_template.grantRoadmapItem(player);
            }
            setWorkingSkill(player, skill_template.getNextWorkingSkill(player));
        }
    }
    public static void revokeAllSkillsAndExperience(obj_id player) throws InterruptedException
    {
        int curXp = 0;
        String[] xpTypes = 
        {
            xp.COMBAT_GENERAL,
            xp.COMBAT_MELEESPECIALIZE_UNARMED,
            xp.COMBAT_MELEESPECIALIZE_ONEHAND,
            xp.COMBAT_MELEESPECIALIZE_TWOHAND,
            xp.COMBAT_MELEESPECIALIZE_POLEARM,
            xp.COMBAT_RANGEDSPECIALIZE_RIFLE,
            xp.COMBAT_RANGEDSPECIALIZE_PISTOL,
            xp.COMBAT_RANGEDSPECIALIZE_CARBINE,
            xp.COMBAT_RANGEDSPECIALIZE_HEAVY,
            xp.COMBAT_THROWN,
            xp.COMBAT_GRENADE,
            xp.JEDI_GENERAL,
            xp.COMBAT_JEDI_ONEHANDLIGHTSABER,
            xp.COMBAT_JEDI_TWOHANDLIGHTSABER,
            xp.COMBAT_JEDI_POLEARMLIGHTSABER,
            xp.COMBAT_JEDI_FORCE_POWER,
            xp.SCOUT,
            xp.TRAPPING,
            xp.BOUNTYHUNTER,
            xp.SMUGGLER,
            xp.SQUADLEADER,
            xp.CREATUREHANDLER,
            xp.SLICING,
            xp.MEDICAL,
            xp.COMBATMEDIC,
            xp.BIO_ENGINEER,
            xp.BIO_ENGINEER_DNA_HARVESTING,
            xp.MERCHANT,
            xp.CRAFTING_GENERAL,
            xp.CRAFTING_FOOD_GENERAL,
            xp.CRAFTING_MEDICINE_GENERAL,
            xp.CRAFTING_CLOTHING_ARMOR,
            xp.CRAFTING_CLOTHING_GENERAL,
            xp.CRAFTING_WEAPONS_GENERAL,
            xp.CRAFTING_WEAPONS_MELEE,
            xp.CRAFTING_WEAPONS_RANGED,
            xp.CRAFTING_WEAPONS_MUNITION,
            xp.CRAFTING_STRUCTURE_GENERAL,
            xp.CRAFTING_DROID_GENERAL,
            xp.ENTERTAINER,
            xp.MUSIC,
            xp.DANCE,
            xp.IMAGEDESIGNER,
            force_rank.FRS_XP,
            "fs_combat",
            "fs_reflex",
            "fs_crafting",
            "fs_senses",
            "entertainer",
            "crafting"
        };
        for (String xpType : xpTypes) {
            curXp = getExperiencePoints(player, xpType);
            grantExperiencePoints(player, xpType, -curXp);
        }
        String[] skillList = getSkillListingForPlayer(player);
        int attempts = skillList.length;
        if ((skillList.length != 0))
        {
            while (skillList.length > 0 && attempts > 0)
            {
                for (String skillName : skillList) {
                    if (!skillName.startsWith("costume") && !skillName.startsWith("class_chronicles") && !skillName.startsWith("species_") && !skillName.startsWith("social_language_") && !skillName.startsWith("social_politician_") && !skillName.startsWith("pilot_") && !skillName.startsWith("swg_") && !skillName.startsWith("utility_") && !skillName.startsWith("common_") && !skillName.startsWith("pvp_") && !skillName.startsWith("internal_expertise_") && !skillName.equals("expertise")) {
                        skill.revokeSkillSilent(player, skillName);
                    }
                }
                skillList = getSkillListingForPlayer(player);
                --attempts;
            }
        }
        obj_id mission = bounty_hunter.getBountyMission(player);
        if (isIdValid(mission))
        {
            messageTo(mission, "abortMission", null, 0, false);
        }
    }
    public static void contentPathHandoff(obj_id self) throws InterruptedException
    {
        location origin = getLocation(self);
        location crafty = new location(3309.0f, 6.0f, -4785.0f, origin.area);
        String profession = getSkillTemplate(self);
        obj_id objInv = utils.getInventoryContainer(self);

        String questNewbieStart = "quest/speeder_quest";
        String questNewbieStartBH = "quest/speeder_quest";
        String questCrafterEntertainer = "quest/tatooine_eisley_noncombat";

        int crafter = profession.indexOf("trader");
        int entertainer = profession.indexOf("entertainer");
        int bountyhunter = profession.indexOf("bounty_hunter");

        if (crafter > -1 || entertainer > -1)
        {
            if (!groundquests.isQuestActiveOrComplete(self, questCrafterEntertainer))
            {
                createClientPath(self, origin, crafty);
                groundquests.grantQuest(self, questCrafterEntertainer);
            }
        }
        else if (bountyhunter > -1)
        {
            if (groundquests.hasCompletedQuest(self, questNewbieStartBH) || groundquests.isQuestActive(self, questNewbieStartBH))
            {
                detachScript(self, "npe.handoff_to_tatooine");
            }
            else 
            {
                groundquests.requestGrantQuest(self, questNewbieStartBH);
            }
        }
        else 
        {
            if (groundquests.hasCompletedQuest(self, questNewbieStart) || groundquests.isQuestActive(self, questNewbieStart))
            {
                detachScript(self, "npe.handoff_to_tatooine");
            }
            else 
            {
                groundquests.requestGrantQuest(self, questNewbieStart);
            }
        }
        if (crafter > -1)
        {
            messageTo(self, "handleSurveyToolbarSetup", null, 5, false);
        }
        static_item.createNewItemFunction("item_npe_uniform_crate_01_01", objInv);
        npe.giveProfessionWeapon(self);
    }
    public static float getPercentageCompletion(obj_id self, String template) throws InterruptedException
    {
        if (template == null || template.equals(""))
        {
            return 0.0f;
        }
        float oldSkillPct;
        if (template.startsWith("trader") || template.startsWith("entertainer"))
        {
            String oldSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, template, "template");
            String[] oldSkillList = split(oldSkills, ',');
            if (oldSkillList == null || oldSkillList.length == 0)
            {
                return 0.0f;
            }
            int oldSkillCount = 0;
            for (String anOldSkillList : oldSkillList) {
                if (hasSkill(self, anOldSkillList)) {
                    oldSkillCount++;
                }
            }
            oldSkillPct = (float)oldSkillCount / oldSkillList.length;
        }
        else 
        {
            float level = getLevel(self);
            oldSkillPct = level / 90.0f;
        }
        if (oldSkillPct > 1.0f)
        {
            oldSkillPct = 1.0f;
        }
        return oldSkillPct;
    }
    public static boolean setPercentageCompletion(obj_id self, String skillTemplateName, float pct, boolean withItems) throws InterruptedException
    {
        String newSkills = dataTableGetString(skill_template.TEMPLATE_TABLE, skillTemplateName, "template");
        String[] newSkillList = split(newSkills, ',');
        int newSkillCount = (int)Math.floor(pct * newSkillList.length);
        if (newSkillCount < 1)
        {
            newSkillCount = 1;
        }
        for (int i = 0; i < newSkillCount; i++)
        {
            grantSkill(self, newSkillList[i]);
            setWorkingSkill(self, newSkillList[i]);
            if (withItems)
            {
                skill_template.grantRoadmapItem(self);
            }
        }
        if (newSkillCount == newSkillList.length)
        {
            setWorkingSkill(self, null);
        }
        return true;
    }
    public static boolean isHigherWorkingSkill(String newSkill, String oldSkill, String skillTemplate) throws InterruptedException
    {
        if (newSkill == null || newSkill.equals(""))
        {
            return false;
        }
        if (oldSkill == null || oldSkill.equals(""))
        {
            return true;
        }
        if (skillTemplate == null || skillTemplate.equals(""))
        {
            return false;
        }
        String skills = dataTableGetString(skill_template.TEMPLATE_TABLE, skillTemplate, "template");
        String[] skillList = split(skills, ',');
        int newIdx = -1;
        int oldIdx = -1;
        for (int i = 0; i < skillList.length; i++)
        {
            if (newSkill.equals(skillList[i]))
            {
                newIdx = i;
            }
            if (oldSkill.equals(skillList[i]))
            {
                oldIdx = i;
            }
        }
        return (newIdx > oldIdx);
    }
    public static String getHigherWorkingSkill(String newSkill, String oldSkill, String skillTemplate) throws InterruptedException
    {
        if (newSkill == null || newSkill.equals(""))
        {
            return oldSkill;
        }
        if (oldSkill == null || oldSkill.equals(""))
        {
            return newSkill;
        }
        if (oldSkill.equals("master"))
        {
            return oldSkill;
        }
        if (skillTemplate == null || skillTemplate.equals(""))
        {
            return oldSkill;
        }
        String skills = dataTableGetString(skill_template.TEMPLATE_TABLE, skillTemplate, "template");
        String[] skillList = split(skills, ',');
        int newIdx = -1;
        int oldIdx = -1;
        for (int i = 0; i < skillList.length; i++)
        {
            if (newSkill.equals(skillList[i]))
            {
                newIdx = i;
            }
            if (oldSkill.equals(skillList[i]))
            {
                oldIdx = i;
            }
        }
        if (newIdx >= oldIdx)
        {
            return newSkill;
        }
        return oldSkill;
    }
    public static boolean setRespecVersion(obj_id player) throws InterruptedException
    {
        String skillTemplate = getSkillTemplate(player);
        String profession = skill.getProfessionName(skillTemplate);
        if (profession == null || skillTemplate == null)
        {
            return false;
        }
        if (profession.equals("") || skillTemplate.equals(""))
        {
            return false;
        }
        if (profession.equals("trader"))
        {
            if (skillTemplate.equals("trader_0a"))
            {
                profession = "trader_dom";
            }
            if (skillTemplate.equals("trader_0b"))
            {
                profession = "trader_struct";
            }
            if (skillTemplate.equals("trader_0c"))
            {
                profession = "trader_mun";
            }
            if (skillTemplate.equals("trader_0d"))
            {
                profession = "trader_eng";
            }
        }
        int row = dataTableSearchColumnForString(profession, "profession", EXPERTISE_VERSION_TABLE);
        int version = 0;
        if (row >= 0)
        {
            version = dataTableGetInt(EXPERTISE_VERSION_TABLE, row, "version");
        }
        setObjVar(player, EXPERTISE_VERSION_OBJVAR, version);
        return true;
    }
    public static int getRespecVersion(obj_id player) throws InterruptedException
    {
        String skillTemplate = getSkillTemplate(player);
        String profession = skill.getProfessionName(skillTemplate);
        if (profession == null || skillTemplate == null)
        {
            return -1;
        }
        if (profession.equals("") || skillTemplate.equals(""))
        {
            return -1;
        }
        if (profession.equals("trader"))
        {
            if (skillTemplate.equals("trader_0a"))
            {
                profession = "trader_dom";
            }
            if (skillTemplate.equals("trader_0b"))
            {
                profession = "trader_struct";
            }
            if (skillTemplate.equals("trader_0c"))
            {
                profession = "trader_mun";
            }
            if (skillTemplate.equals("trader_0d"))
            {
                profession = "trader_eng";
            }
        }
        int row = dataTableSearchColumnForString(profession, "profession", EXPERTISE_VERSION_TABLE);
        if (row < 0)
        {
            return 0;
        }
        return dataTableGetInt(EXPERTISE_VERSION_TABLE, row, "version");
    }
    public static boolean checkRespecDecay(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!hasObjVar(player, "respecsBought"))
        {
            CustomerServiceLog("respec_decay", "Player %TU :: had no respec count objvar. We set the decay timer on them to expire on the first of the month.", player);
            setRespecDecayObjVar(player);
            return true;
        }
        prose_package pp = new prose_package();
        prose.setStringId(pp, RESPEC_DECAY_MESSAGE);
        if (!hasObjVar(player, OBJVAR_RESPEC_DECAY_TIME))
        {
            int currentRespecsBought = getIntObjVar(player, "respecsBought");
            currentRespecsBought -= 1;
            if (currentRespecsBought <= 0)
            {
                prose.setDI(pp, 0);
                sui.msgbox(player, pp);
                removeObjVar(player, "respecsBought");
                CustomerServiceLog("respec_decay", "Player %TU :: had their respec counter decay to 0. This means the objvar was removed. They had no reset timer, so we set the decay timer on them to expire on the first next month.", player);
            }
            else 
            {
                prose.setDI(pp, currentRespecsBought);
                sui.msgbox(player, pp);
                setObjVar(player, "respecsBought", currentRespecsBought);
                CustomerServiceLog("respec_decay", "Player %TU :: had their respec counter decay to " + currentRespecsBought + ". They had no reset timer, so we set the decay timer on them to expire on the first next month.", player);
            }
            setRespecDecayObjVar(player);
            return true;
        }
        else 
        {
            int timeTillNextDecayOnPlayer = getIntObjVar(player, OBJVAR_RESPEC_DECAY_TIME);
            int now = getCalendarTime();
            if (timeTillNextDecayOnPlayer <= now)
            {
                int currentRespecsBought = getIntObjVar(player, "respecsBought");
                currentRespecsBought -= 1;
                if (currentRespecsBought <= 0)
                {
                    prose.setDI(pp, 0);
                    sui.msgbox(player, pp);
                    removeObjVar(player, "respecsBought");
                    CustomerServiceLog("respec_decay", "Player %TU :: had their respec counter decay to 0. This means the objvar was removed. We reset the decay timer on them to expire on the first next month.", player);
                }
                else 
                {
                    prose.setDI(pp, currentRespecsBought);
                    sui.msgbox(player, pp);
                    setObjVar(player, "respecsBought", currentRespecsBought);
                    CustomerServiceLog("respec_decay", "Player %TU :: had their respec counter decay to " + currentRespecsBought + ". We reset the decay timer on them to expire on the first next month.", player);
                }
                setRespecDecayObjVar(player);
                return true;
            }
            return false;
        }
    }
    public static boolean setRespecDecayObjVar(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }

        int timeTillNextDecay = getCalendarTime() + secondsUntilNextMonthlyTime(1, 10, 0, 0);
        setObjVar(player, OBJVAR_RESPEC_DECAY_TIME, timeTillNextDecay);
        return true;
    }
}

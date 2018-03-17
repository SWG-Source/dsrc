package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.chat;
import script.ai.ai_combat;
import script.library.pet_lib;
import script.library.beast_lib;
import script.library.colors;
import script.library.combat_consts;
import script.library.healing;
import script.library.utils;
import script.library.poi;
import script.library.xp;
import script.library.hue;
import script.library.player_stomach;
import script.library.consumable;
import script.library.food;
import script.library.space_transition;
import script.library.sui;
import script.library.stealth;
import script.library.prose;
import script.library.buff;

public class beast extends script.base_script
{
    public beast()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public static final int MAX_UNACKNOWLEDGED_MESSAGE_COUNT = 3;
    public static final int PING_INTERVAL = 120;
    public static final int PING_INTERVAL_RETRY = 30;
    public static final float MAX_DISTANCE_FROM_MASTER = 128.0f;
    public static final boolean BEAST_DEBUG = false;
    public static final string_id PCOLOR = new string_id("sui", "set_primary_color");
    public void blog(String text) throws InterruptedException
    {
        if (BEAST_DEBUG)
        {
            LOG("beast.script", text);
        }
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "beast.pingMessageNumber", 0);
        setObjVar(self, "beast.lastAcknowledgeNumber", 0);
        messageTo(self, "handleSetupBeast", null, 1, false);
        messageTo(self, "beastPing", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSetupBeast", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        obj_id master = getMaster(self);
        obj_id beastBCD = beast_lib.getBeastBCD(self);
        if (!beast_lib.isValidPlayer(master))
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "owner";
        attribs[idx] = getName(master);
        idx++;
        if (exists(beastBCD) && hasObjVar(beastBCD, beast_lib.OBJVAR_BEAST_ENGINEER))
        {
            String creatorName = getStringObjVar(beastBCD, beast_lib.OBJVAR_BEAST_ENGINEER);
            names[idx] = "bm_creator";
            attribs[idx] = creatorName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = "health";
        attribs[idx] = " " + (int)getMaxAttrib(self, HEALTH);
        idx++;
        int level = getLevel(self);
        if (level < 90)
        {
            dictionary beastStatsNextLevel = utils.dataTableGetRow(beast_lib.BEASTS_STATS, level - 1);
            int experienceLastLevel = 0;
            int experienceNextLevel = 0;
            if (level > 1)
            {
                dictionary beastStatsLastLevel = utils.dataTableGetRow(beast_lib.BEASTS_STATS, level - 2);
                if (beastStatsLastLevel == null || beastStatsLastLevel.size() <= 0)
                {
                    return SCRIPT_CONTINUE;
                }
                experienceLastLevel = beastStatsLastLevel.getInt("XP");
            }
            if (beastStatsNextLevel == null || beastStatsNextLevel.size() <= 0)
            {
                blog("initializeBeastStats() missing entry in the " + beast_lib.BEASTS_STATS + " table for level: " + level + ".");
                return SCRIPT_CONTINUE;
            }
            experienceNextLevel = beastStatsNextLevel.getInt("XP");
            int beastExperience = beast_lib.getBeastExperience(self);
            int experienceProgress = beastExperience - experienceLastLevel;
            int experienceNeeded = experienceNextLevel - experienceLastLevel;
            int experiencePercentage = 0;
            if (experienceProgress > 0)
            {
                experiencePercentage = (int)(((float)experienceProgress / (float)experienceNeeded) * 100);
            }
            names[idx] = "level_progress";
            attribs[idx] = "" + experiencePercentage + "%";
            idx++;
        }
        if (beast_lib.getBeastCanLevel(self))
        {
            names[idx] = "exp_on";
            attribs[idx] = "true";
            idx++;
        }
        else 
        {
            names[idx] = "exp_off";
            attribs[idx] = "true";
            idx++;
        }
        names[idx] = beast_lib.BEAST_MOOD_TITLE;
        String currentHappiness = utils.packStringId(beast_lib.convertHappinessString(beastBCD));
        if (currentHappiness != null)
        {
            attribs[idx] = "" + currentHappiness;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = beast_lib.BEAST_LOYALTY_TITLE;
        String currentLoyalty = utils.packStringId(beast_lib.convertLoyaltyString(beastBCD));
        if (currentLoyalty != null)
        {
            attribs[idx] = "" + currentLoyalty;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int loyaltyPercentage = beast_lib.getBeastLoyaltyToNextLevelPercentage(self);
        if (loyaltyPercentage >= 0)
        {
            names[idx] = beast_lib.BEAST_LOYALTY_PERCENTAGE_TITLE;
            attribs[idx] = "" + loyaltyPercentage + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        String[] abilityList = beast_lib.getBeastmasterPetBarData(player, self);
        int slotsAvalable = getSkillStatisticModifier(player, "expertise_bm_add_pet_bar");
        if (!abilityList[3].equals("") && slotsAvalable >= 0 && !abilityList[3].equals("empty") && !abilityList[3].equals("disabled"))
        {
            names[idx] = "bm_ability_1";
            attribs[idx] = "" + utils.packStringId(new string_id("cmd_n", abilityList[3]));
            idx++;
        }
        if (!abilityList[4].equals("") && slotsAvalable >= 1 && !abilityList[4].equals("empty") && !abilityList[4].equals("disabled"))
        {
            names[idx] = "bm_ability_2";
            attribs[idx] = "" + utils.packStringId(new string_id("cmd_n", abilityList[4]));
            idx++;
        }
        if (!abilityList[5].equals("") && slotsAvalable >= 2 && !abilityList[5].equals("empty") && !abilityList[5].equals("disabled"))
        {
            names[idx] = "bm_ability_3";
            attribs[idx] = "" + utils.packStringId(new string_id("cmd_n", abilityList[5]));
            idx++;
        }
        if (!abilityList[6].equals("") && slotsAvalable >= 3 && !abilityList[6].equals("empty") && !abilityList[6].equals("disabled"))
        {
            names[idx] = "bm_ability_4";
            attribs[idx] = "" + utils.packStringId(new string_id("cmd_n", abilityList[6]));
            idx++;
        }
        names[idx] = "armorhpmax";
        attribs[idx] = "" + (utils.getIntScriptVar(self, "beast.display.armor") + getEnhancedSkillStatisticModifier(self, "private_armor_bonus"));
        idx++;
        obj_id beastWeapon = getCurrentWeapon(self);
        if (isIdValid(beastWeapon))
        {
            int minDamage = getWeaponMinDamage(beastWeapon);
            int maxDamage = getWeaponMaxDamage(beastWeapon);
            int expertiseDamageBonus = getEnhancedSkillStatisticModifierUncapped(self, "expertise_damage_all");
            minDamage = (int)(minDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
            maxDamage = (int)(maxDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
            float weaponSpeed = getWeaponAttackSpeed(beastWeapon);
            names[idx] = "damage";
            attribs[idx] = "" + minDamage + " - " + maxDamage;
            idx++;
            names[idx] = "attackspeed";
            attribs[idx] = "" + weaponSpeed;
            idx++;
            float beastDPS = utils.roundFloatByDecimal((minDamage + maxDamage) / weaponSpeed / 2);
            names[idx] = "basedps";
            attribs[idx] = "" + beastDPS;
            idx++;
        }
        else 
        {
            obj_id defaultWeapon = getDefaultWeapon(self);
            if (isIdValid(defaultWeapon))
            {
                int minDamage = getWeaponMinDamage(defaultWeapon);
                int maxDamage = getWeaponMaxDamage(defaultWeapon);
                float weaponSpeed = getWeaponAttackSpeed(defaultWeapon);
                int expertiseDamageBonus = getEnhancedSkillStatisticModifierUncapped(self, "expertise_damage_all");
                minDamage = (int)(minDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
                maxDamage = (int)(maxDamage * (1.0f + ((float)expertiseDamageBonus / 100.0f)));
                names[idx] = "damage";
                attribs[idx] = "" + minDamage + " - " + maxDamage;
                idx++;
                names[idx] = "attackspeed";
                attribs[idx] = "" + weaponSpeed;
                idx++;
                float beastDPS = utils.roundFloatByDecimal((minDamage + maxDamage) / weaponSpeed / 2);
                names[idx] = "basedps";
                attribs[idx] = "" + beastDPS;
                idx++;
            }
        }
        if (beast_lib.DISPLAY_NAMES.length == beast_lib.ARRAY_BEAST_INCUBATION_STATS.length && beast_lib.DISPLAY_NAMES.length == beast_lib.DISPLAY_CONVERSION_RATES.length)
        {
            for (int i = 0; i < beast_lib.DISPLAY_NAMES.length; ++i)
            {
                String name = beast_lib.DISPLAY_NAMES[i];
                if (name.indexOf("_skill") < 0)
                {
                    if (!name.equals("block_value_bonus"))
                    {
                        names[idx] = beast_lib.DISPLAY_NAMES[i];
                        attribs[idx] = "" + utils.roundFloatByDecimal((utils.getFloatScriptVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]) * beast_lib.DISPLAY_CONVERSION_RATES[i])) + "%";
                        idx++;
                    }
                    else 
                    {
                        names[idx] = beast_lib.DISPLAY_NAMES[i];
                        attribs[idx] = "" + utils.roundFloatByDecimal(utils.getFloatScriptVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]));
                        idx++;
                    }
                    continue;
                }
                else 
                {
                    names[idx] = beast_lib.DISPLAY_NAMES[i];
                    attribs[idx] = "" + utils.roundFloatByDecimal(utils.getFloatScriptVar(self, beast_lib.ARRAY_BEAST_INCUBATION_STATS[i]));
                    idx++;
                    continue;
                }
            }
        }
        float glanceReduct = beast_lib.getBeastModGlancingReduction(self);
        if (glanceReduct > 0)
        {
            names[idx] = "bm_glance_reduct";
            attribs[idx] = "" + glanceReduct + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        float damageReduct = beast_lib.getBeastModDamageReduction(self);
        if (damageReduct > 0)
        {
            names[idx] = "bm_damage_reduct";
            attribs[idx] = "" + damageReduct + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        float punishReduct = beast_lib.getBeastModPunishingReduction(self);
        if (punishReduct > 0)
        {
            names[idx] = "bm_punish_reduct";
            attribs[idx] = "" + punishReduct + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            if (isIdValid(beast_lib.getBeastOnPlayer(master)))
            {
                blog("Beast not cleaned up on " + master + " and something attempted to detach its beast script.");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            if (isIdValid(beast_lib.getBeastOnPlayer(master)))
            {
                blog("Beast not cleaned up on " + master + " and something attempted to destroy it.");
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetupBeast(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master) || !exists(master))
        {
            blog("handleSetupBeast() master is not valid.  Destroying beast.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(master))
        {
            setObjVar(self, "ai.pet", true);
        }
        beast_lib.beastFollowTarget(self, master);
        if (!ai_lib.isInCombat(self) && stealth.canBeastStealth(self))
        {
            stealth.makeBeastInvisible(master, "appearance/pt_smoke_puff.prt");
        }
        return SCRIPT_CONTINUE;
    }
    public int beastPing(obj_id self, dictionary params) throws InterruptedException
    {
        int lastMessage = getIntObjVar(self, "beast.pingMessageNumber");
        int nextMessage = lastMessage + 1;
        setObjVar(self, "beast.pingMessageNumber", nextMessage);
        int acknowledgedMessage = getIntObjVar(self, "beast.lastAcknowledgeNumber");
        int unacknowledgedGap = lastMessage - acknowledgedMessage;
        obj_id master = getMaster(self);
        if (!isIdValid(master) || !exists(master))
        {
            setMaster(self, null);
            blog("beastPing() master is not valid.  Destroying beast.");
            if (destroyObject(self))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                blog("beastPing() failed to destroy the beast with no master online");
            }
        }
        obj_id bcd = beast_lib.getBeastBCD(self);
        if (!beast_lib.isValidBCD(bcd))
        {
            setMaster(self, null);
            blog("beastPing() bcd is not valid.  Destroying beast.");
            if (destroyObject(self))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                blog("beastPing() failed to destroy the beast with no BCD online");
            }
        }
        if (unacknowledgedGap > MAX_UNACKNOWLEDGED_MESSAGE_COUNT)
        {
            beast_lib.storeBeast(bcd);
            return SCRIPT_CONTINUE;
        }
        if (getDistance(self, master) > MAX_DISTANCE_FROM_MASTER)
        {
            blog("self: " + self + " too far from master: " + master + "(" + getDistance(self, master) + " and is being stored.");
            beast_lib.storeBeast(bcd);
            return SCRIPT_CONTINUE;
        }
        int nextCallbackInterval = (unacknowledgedGap <= 0) ? PING_INTERVAL : PING_INTERVAL_RETRY;
        dictionary messageData = new dictionary();
        messageData.put("beastId", self);
        messageData.put("messageId", nextMessage);
        messageTo(bcd, "beastPing", messageData, 1, false);
        messageTo(self, "beastPing", null, nextCallbackInterval, false);
        return SCRIPT_CONTINUE;
    }
    public int bcdPingResponse(obj_id self, dictionary params) throws InterruptedException
    {
        int ack = params.getInt("messageId");
        int previousAck = getIntObjVar(self, "beast.pingMessageNumber");
        if (ack > previousAck)
        {
            setObjVar(self, "beast.lastAcknowledgeNumber", ack);
        }
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            blog("resumeDefaultCalmBehavior() master is invalid and is being destroyed.");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(master))
        {
            location masterLoc = getLocation(master);
            if(isValidLocation(masterLoc))
                setHomeLocation(self, masterLoc);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkMovementSpeed(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        beast_lib.validateFollowTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowWaiting(obj_id self, obj_id target) throws InterruptedException
    {
        beast_lib.validateFollowTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowMoving(obj_id self, obj_id target) throws InterruptedException
    {
        beast_lib.validateFollowTarget(self, target);
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("beast", "OnBehaviorChange newBehavior: " + newBehavior + " oldBehavior: " + oldBehavior);
        if (newBehavior <= oldBehavior)
        {
            if (doCalmerBehavior(self, newBehavior, oldBehavior) == SCRIPT_CONTINUE)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
        else 
        {
            if (doAgitatedBehavior(self, newBehavior, oldBehavior) == SCRIPT_CONTINUE)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
    }
    public int doCalmerBehavior(obj_id npc, int newBehavior, int oldBehavior) throws InterruptedException
    {
        switch (newBehavior)
        {
            case BEHAVIOR_CALM:
            break;
            case BEHAVIOR_ALERT:
            break;
            case BEHAVIOR_THREATEN:
            break;
            case BEHAVIOR_FLEE:
            break;
            case BEHAVIOR_PANIC:
            break;
            case BEHAVIOR_ATTACK:
            break;
            case BEHAVIOR_FRENZY:
            break;
            default:
            break;
        }
        return SCRIPT_OVERRIDE;
    }
    public int doAgitatedBehavior(obj_id npc, int newBehavior, int oldBehavior) throws InterruptedException
    {
        switch (newBehavior)
        {
            case BEHAVIOR_CALM:
            break;
            case BEHAVIOR_ALERT:
            break;
            case BEHAVIOR_THREATEN:
            break;
            case BEHAVIOR_FLEE:
            break;
            case BEHAVIOR_PANIC:
            break;
            case BEHAVIOR_ATTACK:
            break;
            case BEHAVIOR_FRENZY:
            break;
            default:
            break;
        }
        return SCRIPT_OVERRIDE;
    }
    public int lairThreatened(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        beast_lib.killBeast(self, attacker);
        return SCRIPT_CONTINUE;
    }
    public int handleBeastIncappedDecay(obj_id self, dictionary params) throws InterruptedException
    {
        int incapTimer = utils.getIntScriptVar(self, "incapTimer");
        int messageTimer = params.getInt("incapTimer");
        if (incapTimer != messageTimer)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            blog("handleBeastIncappedDecay() destroying self from incapacitation.");
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRecapacitated(obj_id self) throws InterruptedException
    {
        beast_lib.beastFollowTarget(self, getMaster(self));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        int opt_menu = 0;
        if (beast_lib.isBeastMaster(player))
        {
            if (!beast_lib.getBeastCanLevel(self))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(MENU_FILE, "exp_on"));
            }
            else 
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id(MENU_FILE, "exp_off"));
            }
        }
        obj_id bcd = beast_lib.getBeastBCD(self);
        if (!ai_lib.isInCombat(player) && !ai_lib.isAiDead(self) && !ai_lib.aiIsDead(player))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("beast", "name_beast"));
        }
        if (!ai_lib.isInCombat(player))
        {
            mi.addRootMenu(menu_info_types.PET_STORE, new string_id(MENU_FILE, "menu_store"));
        }
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(bcd, beast_lib.OBJVAR_OLD_PET_IDENTIFIER) && !hasObjVar(bcd, beast_lib.OBJVAR_OLD_PET_REHUED))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU3, PCOLOR);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(self);
        if (!isIdValid(bcd) || !exists(bcd))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(player) || ai_lib.isAiDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            boolean canLevel = !beast_lib.getBeastCanLevel(self);
            beast_lib.setBeastCanLevel(self, canLevel);
            beast_lib.setBCDBeastCanLevel(bcd, canLevel);
            sendDirtyObjectMenuNotification(self);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            if (!ai_lib.isInCombat(player) && !ai_lib.isAiDead(self) && !ai_lib.aiIsDead(player))
            {
                sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.PET_STORE)
        {
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_COMBAT_PET))
            {
                obj_id currentBeast = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
                if (ai_lib.isInCombat(currentBeast))
                {
                    sendSystemMessage(player, new string_id("beast", "beast_cant_store"));
                    return SCRIPT_CONTINUE;
                }
            }
            beast_lib.storeBeast(bcd);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.PET_COMMAND)
        {
            pet_lib.openLearnCommandSui(self, player);
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            if (hasObjVar(bcd, beast_lib.OBJVAR_OLD_PET_IDENTIFIER) && !hasObjVar(bcd, beast_lib.OBJVAR_OLD_PET_REHUED))
            {
                sui.colorize(self, player, self, hue.INDEX_1, "handlePrimaryColorize");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePrimaryColorize(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beast = self;
        if (!isValidId(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        if (!isValidId(bcd))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getColorPickerIndex(params);
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx > -1)
        {
            setObjVar(bcd, beast_lib.OBJVAR_BEAST_HUE, idx);
            hue.setColor(beast, "/private/index_color_1", idx);
            setObjVar(bcd, beast_lib.OBJVAR_OLD_PET_REHUED, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetBeastName(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String beastName = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (beastName.equals("") || isNameReserved(beastName))
        {
            sendSystemMessage(player, new string_id("player_structure", "obscene"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        beastName = beastName.trim();
        if (beastName.length() < 3)
        {
            sendSystemMessage(player, new string_id("beast", "name_too_short"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        if (beastName.length() > 40)
        {
            sendSystemMessage(player, new string_id("beast", "name_too_long"));
            sui.inputbox(self, player, "@beast:name_d", sui.OK_CANCEL, "@beast:name_t", sui.INPUT_NORMAL, null, "handleSetBeastName", null);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        beast_lib.setBCDBeastName(beast_lib.getBeastBCD(self), beastName);
        beast_lib.setBeastName(self, beastName);
        if (hasObjVar(beast_lib.getBeastBCD(self), beast_lib.OBJVAR_OLD_PET_IDENTIFIER))
        {
            setObjVar(beast_lib.getBeastBCD(self), beast_lib.OBJVAR_OLD_PET_RENAMED, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleGroupInvite(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int postCombatPathHome(obj_id self, dictionary params) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "pet::postCombatPathHome() self(" + self + ":" + getName(self) + ") master(" + getMaster(self) + ":" + getName(getMaster(self)) + ")");
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "ai.pet.staying"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id guardTarget = utils.getObjIdScriptVar(self, "ai.pet.guarding");
        if (isIdValid(guardTarget))
        {
            pet_lib.petFollow(self, guardTarget);
        }
        else if (hasObjVar(self, "ai.inFormation"))
        {
            ai_lib.resumeFormationFollowing(self);
        }
        else 
        {
            obj_id master = getMaster(self);
            if (isIdValid(master) && master.isLoaded())
            {
                pet_lib.petFollow(self, master);
            }
            else 
            {
                stop(self);
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public int handleAbandonment(obj_id self, dictionary params) throws InterruptedException
    {
        int ignoredForDays = getIntObjVar(self, "ai.pet.ignoredForDays");
        ignoredForDays++;
        if (ignoredForDays > 3)
        {
            pet_lib.releasePet(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            setObjVar(self, "ai.pet.ignoredForDays", ignoredForDays);
        }
        messageTo(self, "handleAbandonment", null, 86400, false);
        return SCRIPT_CONTINUE;
    }
    public int resumePetTrick(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = params.getObjId("master");
        int trickNum = params.getInt("trickNum");
        pet_lib.doStupidPetTrick(self, master, trickNum);
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (!isIdValid(master))
        {
            return SCRIPT_CONTINUE;
        }
        int pet_food = getIntObjVar(item, "race_restriction");
        if (pet_lib.isCreaturePet(self) && (pet_food == -2) && (player == master))
        {
            food.petEatFood(self, master, item, player);
            return SCRIPT_CONTINUE;
        }
        obj_id petControlDevice = callable.getCallableCD(self);
        obj_id petInv = utils.getInventoryContainer(petControlDevice);
        if (!isIdValid(petInv))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != master)
        {
            return SCRIPT_CONTINUE;
        }
        if (isMob(item))
        {
            return SCRIPT_CONTINUE;
        }
        else if (!ai_lib.isNpc(self) || !ai_lib.isAndroid(self))
        {
            return SCRIPT_CONTINUE;
        }
        putIn(item, petInv);
        return SCRIPT_CONTINUE;
    }
    public int handleMoveToMaster(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            location targetLocation = new location(getLocation(master));
            targetLocation.x += rand(-4.0f, +4.0f);
            targetLocation.z += rand(-4.0f, +4.0f);
            targetLocation.y = getHeightAtLocation(targetLocation.x, targetLocation.z);
            setLocation(self, getLocation(master));
            pathTo(self, targetLocation);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePackRequest(obj_id self, dictionary params) throws InterruptedException
    {
        blog("handlePackRequest() destroying beast.");
        if (!destroyObject(self))
        {
            debugServerConsoleMsg(null, "+++ beast.messageHandler handlePackRequest +++ WARNINGWARNING - FAILED TO DESTROY SELF");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAddToStomach(obj_id self, dictionary params) throws InterruptedException
    {
        int stomach = params.getInt("stomach");
        int vol = params.getInt("vol");
        player_stomach.addToStomach(self, stomach, vol);
        return SCRIPT_CONTINUE;
    }
    public int handleSetColors(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        setColors(self, params);
        return SCRIPT_CONTINUE;
    }
    public boolean setColors(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return false;
        }
        boolean litmus = true;
        java.util.Enumeration keys = params.keys();
        while (keys.hasMoreElements())
        {
            String var = (String)keys.nextElement();
            int idx = params.getInt(var);
            litmus &= hue.setColor(self, var, idx);
        }
        return litmus;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        if (isIdValid(master) && master.isLoaded())
        {
            aiSetHomeLocation(self, getLocation(master));
        }
        else 
        {
            aiSetHomeLocation(self, getLocation(self));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        if (!beast_lib.getBeastDefensive(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (utils.hasScriptVar(self, "petIgnoreAttacks"))
        {
            int timeStarted = utils.getIntScriptVar(self, "petIgnoreAttacks");
            blog("OnDefenderCombatAction() beast hasScriptVar petIgnoreAttacks timeStarted: " + timeStarted + " getGameTime: " + getGameTime());
            if (getGameTime() < (timeStarted + 20))
            {
                return SCRIPT_OVERRIDE;
            }
            utils.removeScriptVar(self, "petIgnoreAttacks");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        if (beast_lib.isBeast(self) && beast_lib.getBeastDefensive(self))
        {
            for (int i = 0; i < attackers.length; ++i)
            {
                final obj_id attacker = attackers[i];
                if (isIdValid(attacker) && !ai_lib.isInCombat(self) && defender == getMaster(self))
                {
                    addHate(self, attacker, 0.0f);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "beast.combatEnded", getGameTime());
        if (!utils.hasScriptVar(self, "ai.pet.staying"))
        {
            obj_id master = getMaster(self);
            if (isIdValid(master) && master.isLoaded())
            {
                beast_lib.beastFollowTarget(self, master);
                if (beast_lib.getBeastDefensive(self))
                {
                    messageTo(self, "defensiveModePing", null, 1.0f, false);
                }
                if (!beast_lib.getBeastDefensive(self) && !utils.hasScriptVar(self, "petIgnoreAttacks"))
                {
                    utils.setScriptVar(self, "petIgnoreAttacks", getGameTime());
                }
            }
            else 
            {
                aiSetHomeLocation(self, getLocation(self));
            }
        }
        else 
        {
            obj_id master = getMaster(self);
            if (isIdValid(master) && master.isLoaded())
            {
                beast_lib.doStayCommand(self, master);
            }
            else 
            {
                ai_lib.aiStopFollowing(self);
                location myLocation = getLocation(self);
                setHomeLocation(self, myLocation);
                setMovementWalk(self);
                ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_STOP);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id performer, String emote) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (performer != master)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(master))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(master) || ai_lib.aiIsDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (getLookAtTarget(master) != self)
        {
            if (getIntendedTarget(master) != self)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (emote.startsWith("pet") || emote.equals("reassure") || emote.equals("nuzzle") || emote.equals("hug"))
        {
            if (rand(1, 2) == 1)
            {
                stop(self);
                if (ai_lib.canSit(self))
                {
                    if (rand(1, 2) == 1)
                    {
                        ai_lib.aiSetPosture(self, POSTURE_SITTING);
                    }
                    else 
                    {
                        ai_lib.aiSetPosture(self, POSTURE_LYING_DOWN);
                    }
                }
                else if (ai_lib.canLieDown(self))
                {
                    ai_lib.aiSetPosture(self, POSTURE_LYING_DOWN);
                }
            }
            else 
            {
                ai_lib.doAction(self, "happy");
            }
        }
        else if (emote.equals("bonk") || emote.equals("whap") || emote.equals("scold") || emote.equals("bad") || emote.equals("slap"))
        {
            ai_lib.doAction(self, "ashamed");
        }
        else if (emote.equals("pointat") || emote.equals("tap"))
        {
            ai_lib.doAction(self, ai_lib.ACTION_ALERT);
        }
        else if (emote.equals("beckon") || emote.equals("summon"))
        {
            if (rand(1, 2) == 1)
            {
                ai_lib.doAction(self, "confused");
            }
            else 
            {
                beast_lib.doFollowCommand(self, master);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id master = getMaster(self);
        CustomerServiceLog("Beasts", "*Beast DEATH: (" + self + ": " + getName(self) + ") has died. Their master was, (" + master + " sid:" + getPlayerObject(master));
        return SCRIPT_CONTINUE;
    }
    public int receiveCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (isIdValid(master))
        {
            String creatureName = params.getString("creatureName");
            params.put("col_faction", dataTableGetString("datatables/mob/creatures.iff", creatureName, "col_faction"));
            params.put("col_faction", dataTableGetInt("datatables/mob/creatures.iff", creatureName, "difficultyClass"));
            messageTo(master, "receiveCreditForKill", params, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHealOverTimeTick(obj_id self, dictionary params) throws InterruptedException
    {
        float duration = params.getFloat("duration");
        float tick = params.getFloat("tick");
        int heal = params.getInt("heal");
        int hot_id = params.getInt("id");
        int isCombat = params.getInt("combat");
        obj_id medic = params.getObjId("medic");
        if (isDead(self))
        {
            if (buff.hasBuff(self, "healOverTime"))
            {
                buff.removeBuff(self, "healOverTime");
            }
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, healing.VAR_PLAYER_HOT_ID))
        {
            if (buff.hasBuff(self, "healOverTime"))
            {
                buff.removeBuff(self, "healOverTime");
            }
            return SCRIPT_CONTINUE;
        }
        int player_id = utils.getIntScriptVar(self, healing.VAR_PLAYER_HOT_ID);
        if (hot_id != player_id)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIncapacitated(self))
        {
            int delta = healing.healDamage(medic, self, HEALTH, heal);
            if (isCombat == 1 && isIdValid(medic))
            {
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, new string_id("healing", "heal_fly"));
                pp = prose.setDI(pp, delta);
                pp = prose.setTO(pp, "HEALTH");
                showFlyTextPrivateProseWithFlags(self, self, pp, 2.0f, colors.SEAGREEN, FLY_TEXT_FLAG_IS_HEAL);
                if (medic != self)
                {
                    showFlyTextPrivateProseWithFlags(self, medic, pp, 2.0f, colors.SEAGREEN, FLY_TEXT_FLAG_IS_HEAL);
                }
                pp = prose.setStringId(pp, healing.SID_PERFORM_HEAL_DAMAGE_SUCCESS);
                pp = prose.setTT(pp, medic);
                pp = prose.setTO(pp, self);
                pp = prose.setDI(pp, delta);
                healing.sendMedicalSpam(medic, self, pp, true, true, true, COMBAT_RESULT_MEDICAL);
                delta = (int)(delta * healing.HEAL_OVER_TIME_AGGRO_REDUCER);
                healing._addMedicalHate(medic, self, delta);
            }
        }
        duration -= tick;
        if (duration <= 0.0f)
        {
            if (buff.hasBuff(self, "healOverTime"))
            {
                buff.removeBuff(self, "healOverTime");
            }
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("duration", duration);
        d.put("tick", tick);
        d.put("heal", heal);
        d.put("id", hot_id);
        d.put("combat", isCombat);
        d.put("medic", medic);
        if (tick >= 0)
        {
            messageTo(self, healing.MSG_HEAL_OVER_TIME, d, tick, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int defensiveModePing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        if (isIdValid(master) && ai_lib.isInCombat(master))
        {
            obj_id[] masterAttackers = getHateList(master);
            for (int i = 0; i < masterAttackers.length; i++)
            {
                if (getHateTarget(masterAttackers[i]) == master)
                {
                    utils.removeScriptVar(self, "petIgnoreAttacks");
                    startCombat(self, masterAttackers[i]);
                    addHate(self, masterAttackers[i], 0.0f);
                    utils.setScriptVar(self, "ai.combat.target", masterAttackers[i]);
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doBeastFlourish(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beast = (self);
        int beastTrickNum = rand(1, 2);
        doAnimationAction(beast, "trick_" + beastTrickNum);
        return SCRIPT_CONTINUE;
    }
}

package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.buff;
import script.library.chat;
import script.library.colors;
import script.library.combat;
import script.library.create;
import script.library.incubator;
import script.library.pet_lib;
import script.library.qa;
import script.library.sui;
import script.library.utils;

public class player_beastmaster extends script.base_script
{
    public player_beastmaster()
    {
    }
    public static final String ABILITY_TO_EXECUTE = "abilityToExecute";
    public static final String TRAIN_DIALOGUE_PID = "petTraining.sui_pid";
    public static final boolean BEAST_DEBUG = false;
    public void blog(String text) throws InterruptedException
    {
        if (BEAST_DEBUG)
        {
            LOG("player_beastmaster.script", text);
        }
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (!beast_lib.isValidBeast(beast_lib.getBeastOnPlayer(self)))
        {
            return SCRIPT_CONTINUE;
        }
        beast_lib.storeBeasts(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        if (!beast_lib.isValidBeast(beast_lib.getBeastOnPlayer(self)))
        {
            return SCRIPT_CONTINUE;
        }
        beast_lib.storeBeasts(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        if (!beast_lib.isValidBeast(beast_lib.getBeastOnPlayer(self)))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        obj_id bcd = beast_lib.getBeastBCD(beast);
        blog("Player killed.  Packing beast: " + beast + " killer: " + killer);
        beast_lib.playerDeathStoreBeast(bcd);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (beast_lib.isValidBeast(beast))
        {
            setBeastmasterPet(self, beast);
        }
        else 
        {
            setBeastmasterPet(self, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillGranted(obj_id self, String skill) throws InterruptedException
    {
        if (skill.equals("expertise_bm_attack_1"))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(self);
            if (!isIdValid(beast))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id bcd = beast_lib.getBeastBCD(beast);
            beast_lib.updatePetAbilityList(bcd, beast);
        }
        if (skill.endsWith("pet_bar_1"))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(self);
            if (!isIdValid(beast))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id bcd = beast_lib.getBeastBCD(beast);
            beast_lib.updatePetAbilityList(bcd, beast);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSkillRevoked(obj_id self, String skill) throws InterruptedException
    {
        if (skill.equals("expertise_bm_attack_1"))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(self);
            if (!isIdValid(beast))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id bcd = beast_lib.getBeastBCD(beast);
            beast_lib.updatePetAbilityList(bcd, beast);
        }
        if (skill.endsWith("pet_bar_1"))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(self);
            if (!isIdValid(beast))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id bcd = beast_lib.getBeastBCD(beast);
            beast_lib.updatePetAbilityList(bcd, beast);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (!beast_lib.isBeastMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast) || ai_lib.isInCombat(beast) || !beast_lib.getBeastDefensive(beast) || utils.hasScriptVar(beast, "petIgnoreAttacks"))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self) && beast_lib.getBeastDefensive(beast))
        {
            obj_id target = getIntendedTarget(self);
            if (isIdValid(target) && pvpCanAttack(beast, target))
            {
                startCombat(beast, target);
                addHate(beast, target, 0.0f);
                utils.setScriptVar(beast, "ai.combat.target", target);
                return SCRIPT_CONTINUE;
            }
            obj_id[] masterAttackers = getWhoIsTargetingMe(self);
            if (masterAttackers != null)
            {
                for (int i = 0; i < masterAttackers.length; i++)
                {
                    if (pvpCanAttack(beast, target))
                    {
                        startCombat(beast, masterAttackers[i]);
                        addHate(beast, masterAttackers[i], 0.0f);
                        utils.setScriptVar(beast, "ai.combat.target", masterAttackers[i]);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        if (!beast_lib.isBeastMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (beast_lib.isValidBeast(beast) && !ai_lib.isInCombat(beast) && beast_lib.getBeastDefensive(beast) && pvpCanAttack(beast, attacker) && !utils.hasScriptVar(beast, "petIgnoreAttacks"))
        {
            startCombat(beast, attacker);
            addHate(beast, attacker, 0.0f);
            utils.setScriptVar(beast, "ai.combat.target", attacker);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        if (!beast_lib.isValidBCD(bcd))
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("setPet"))
        {
            setBeastmasterPet(self, getTarget(self));
        }
        if (strText.equals("clearTrainedSkills"))
        {
            beast_lib.clearTrainedSkills(beast);
        }
        else if (strCommands[0].equals("doability"))
        {
            dictionary dict = new dictionary();
            dict.put(ABILITY_TO_EXECUTE, strCommands[1]);
            messageTo(self, "performCreatureAbility", dict, 0, false);
        }
        else if (strCommands[0].equalsIgnoreCase("next"))
        {
            int lastBeast = getIntObjVar(self, "lastBeast");
            if (lastBeast >= 132)
            {
                return SCRIPT_CONTINUE;
            }
            lastBeast++;
            dictionary beastDict = utils.dataTableGetRow(beast_lib.BEASTS_TABLE, lastBeast - 1);
            String template = beastDict.getString("template");
            setObjVar(self, "lastBeast", lastBeast);
            beast_lib.setBeastType(beast, template);
        }
        else if (strCommands[0].equalsIgnoreCase("last"))
        {
            int lastBeast = getIntObjVar(self, "lastBeast");
            if (lastBeast <= 1)
            {
                return SCRIPT_CONTINUE;
            }
            lastBeast--;
            dictionary beastDict = utils.dataTableGetRow(beast_lib.BEASTS_TABLE, lastBeast - 1);
            String template = beastDict.getString("template");
            setObjVar(self, "lastBeast", lastBeast);
            beast_lib.setBeastType(beast, template);
        }
        else if (strCommands[0].equalsIgnoreCase("beastLevel") && strCommands.length > 1)
        {
            int level = Integer.parseInt(strCommands[1]);
            beast_lib.setBCDBeastLevel(bcd, level);
            beast_lib.setBeastLevel(beast, level);
            beast_lib.initializeBeastStats(bcd, beast);
            blog("Beast: " + beast + " Level set: " + level);
        }
        else if (strCommands[0].equalsIgnoreCase("beastScale") && strCommands.length > 1)
        {
            float scale = Float.parseFloat(strCommands[1]);
            setScale(beast, scale);
            blog("Beast: " + beast + " scale: " + scale);
        }
        else if (strCommands[0].equalsIgnoreCase("attack") && getMaster(beast) == self)
        {
            beast_lib.doAttackCommand(beast, self);
            blog("doAttackCommand() beast: " + beast + " self: " + self);
        }
        else if (strCommands[0].equalsIgnoreCase("follow") && getMaster(beast) == self)
        {
            beast_lib.doFollowCommand(beast, self);
            blog("doFollowCommand() beast: " + beast + " self: " + self);
        }
        else if (strCommands[0].equalsIgnoreCase("revive") && getMaster(beast) == self)
        {
            int beastHealth = getMaxAttrib(beast, HEALTH);
            setAttrib(beast, HEALTH, beastHealth);
            if (ai_lib.isInCombat(beast))
            {
                obj_id target = getCombatTarget(beast);
                if (isIdValid(target) && exists(target))
                {
                    int beastTargetHealth = getMaxAttrib(target, HEALTH);
                    setAttrib(target, HEALTH, beastTargetHealth);
                }
            }
            blog("Beast revived.");
        }
        else if (strCommands[0].equalsIgnoreCase("rez") && getMaster(beast) == self)
        {
            if (!isDead(beast))
            {
                return SCRIPT_CONTINUE;
            }
            int maxHealth = getMaxAttrib(beast, HEALTH);
            beast_lib.storeBeast(bcd);
            beast_lib.setBCDBeastIsDead(bcd, false);
            beast_lib.setBCDBeastHealth(bcd, maxHealth / 10);
            beast_lib.createBeastFromBCD(self, bcd);
        }
        else if (strCommands[0].equalsIgnoreCase("beastType") && strCommands.length > 1)
        {
            beast_lib.setBeastType(beast, strCommands[1]);
            blog("Beast: " + beast + " Type set: " + strCommands[1]);
        }
        else if (strCommands[0].equalsIgnoreCase("learnAbility"))
        {
            beast_lib.playerLearnBeastMasterSkill(self, strCommands[1], true);
        }
        else if (strCommands[0].equalsIgnoreCase("makeTypeThreeEnzyme"))
        {
            if (strCommands.length == 2)
            {
                float value = utils.stringToFloat(strCommands[1]);
                beast_lib.generateTypeThreeEnzyme(self, value);
            }
            else if (strCommands.length == 3)
            {
                float purity = utils.stringToFloat(strCommands[1]);
                float mutagen = utils.stringToFloat(strCommands[2]);
                beast_lib.generateTypeThreeEnzyme(self, purity, mutagen);
            }
        }
        else if (strCommands[0].equalsIgnoreCase("toggleDefend"))
        {
            beast_lib.setBeastDefensive(beast, self, !beast_lib.getBeastDefensive(beast));
        }
        else if (strCommands[0].equalsIgnoreCase("moveInfo"))
        {
            debugSpeakMsg(beast, "Run: " + (getLocomotion(beast) == LOCOMOTION_RUNNING));
        }
        else if (strCommands[0].equalsIgnoreCase("setupHappiness"))
        {
            setupHappinessMenu(self);
        }
        else if (strCommands[0].equalsIgnoreCase("showHappiness"))
        {
            showHappiness(self);
        }
        else if (strCommands[0].equalsIgnoreCase("incrementbeastexperience"))
        {
            showBeastExperienceGain(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void showBeastExperienceGain(obj_id self) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return;
        }
        debugSpeakMsg(beast, "Current experience: " + beast_lib.getBeastExperience(beast));
        beast_lib.incrementBeastExperience(beast);
        debugSpeakMsg(beast, "Incremented experience: " + beast_lib.getBeastExperience(beast));
    }
    public void showHappiness(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self) || !isGod(self))
        {
            return;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteFood = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_FOOD_COL);
        String[] favoriteActivity = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_ACTIVITY_COL);
        String[] favoriteLocation = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_LOCATION_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        debugSpeakMsg(beast, "Favorite Food: " + favoriteFood[favorites[0] - 1] + " Hated Food: " + favoriteFood[favorites[1] - 1]);
        debugSpeakMsg(beast, "Favorite Activity: " + favoriteActivity[favorites[2] - 1] + " Hated Activity: " + favoriteActivity[favorites[3] - 1]);
        debugSpeakMsg(beast, "Favorite Location: " + favoriteLocation[favorites[4] - 1] + " Hated Location: " + favoriteLocation[favorites[5] - 1]);
        debugSpeakMsg(beast, "Current Happiness: " + beast_lib.getBCDBeastHappiness(bcd));
    }
    public void setupHappinessMenu(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self) || !isGod(self))
        {
            return;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteFood = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_FOOD_COL);
        beast_lib.setupHappinessLoyalty(bcd);
        qa.refreshMenu(self, "Select Food...", "-Favorite Food-", favoriteFood, "handleFavoriteFoodMenu", "happiness_menu", "happiness_menu", sui.OK_CANCEL_REFRESH);
    }
    public int handleFavoriteFoodMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteFood = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_FOOD_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        if (idx >= 0 && idx < favoriteFood.length)
        {
            String food = "";
            if (favorites == null || favorites.length <= 0)
            {
                debugSpeakMsg(self, "No favorites defined.");
            }
            else 
            {
                debugSpeakMsg(self, "Favorite food: " + favoriteFood[favorites[0] - 1] + " Changed to: " + favoriteFood[idx]);
            }
            favorites[0] = idx + 1;
            setObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR, favorites);
            qa.refreshMenu(self, "Select Food...", "-Hated Food-", favoriteFood, "handleHatedFoodMenu", "happiness_menu", "happiness_menu", sui.OK_CANCEL_REFRESH);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHatedFoodMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteFood = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_FOOD_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        if (idx >= 0 && idx < favoriteFood.length)
        {
            String food = "";
            if (favorites == null || favorites.length <= 0)
            {
                debugSpeakMsg(self, "No favorites defined.");
            }
            else 
            {
                debugSpeakMsg(self, "Hated food: " + favoriteFood[favorites[1] - 1] + " Changed to: " + favoriteFood[idx]);
            }
            favorites[1] = idx + 1;
            setObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR, favorites);
            String[] favoriteActivity = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_ACTIVITY_COL);
            qa.refreshMenu(self, "Select Activity...", "-Favorite Activity-", favoriteActivity, "handleFavoriteActivityMenu", "happiness_menu", "happiness_menu", sui.OK_CANCEL_REFRESH);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFavoriteActivityMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteActivity = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_ACTIVITY_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        if (idx >= 0 && idx < favoriteActivity.length)
        {
            String food = "";
            if (favorites == null || favorites.length <= 0)
            {
                debugSpeakMsg(self, "No favorites defined.");
            }
            else 
            {
                debugSpeakMsg(self, "Favorite activity: " + favoriteActivity[favorites[2] - 1] + " Changed to: " + favoriteActivity[idx]);
            }
            favorites[2] = idx + 1;
            setObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR, favorites);
            qa.refreshMenu(self, "Select Activity...", "-Hated Activity-", favoriteActivity, "handleHatedActivityMenu", "happiness_menu", "happiness_menu", sui.OK_CANCEL_REFRESH);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHatedActivityMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteActivity = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_ACTIVITY_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        if (idx >= 0 && idx < favoriteActivity.length)
        {
            String food = "";
            if (favorites == null || favorites.length <= 0)
            {
                debugSpeakMsg(self, "No favorites defined.");
            }
            else 
            {
                debugSpeakMsg(self, "Hated activity: " + favoriteActivity[favorites[3] - 1] + " Changed to: " + favoriteActivity[idx]);
            }
            favorites[3] = idx + 1;
            setObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR, favorites);
            String[] favoriteLocation = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_LOCATION_COL);
            qa.refreshMenu(self, "Select Location...", "-Favorite Location-", favoriteLocation, "handleFavoriteLocationMenu", "happiness_menu", "happiness_menu", sui.OK_CANCEL_REFRESH);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFavoriteLocationMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteLocation = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_LOCATION_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        if (idx >= 0 && idx < favoriteLocation.length)
        {
            String food = "";
            if (favorites == null || favorites.length <= 0)
            {
                debugSpeakMsg(self, "No favorites defined.");
            }
            else 
            {
                debugSpeakMsg(self, "Hated activity: " + favoriteLocation[favorites[4] - 1] + " Changed to: " + favoriteLocation[idx]);
            }
            favorites[4] = idx + 1;
            setObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR, favorites);
            qa.refreshMenu(self, "Select Location...", "-Hated Location-", favoriteLocation, "handleHatedLocationMenu", "happiness_menu", "happiness_menu", sui.OK_CANCEL_REFRESH);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHatedLocationMenu(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int idx = sui.getListboxSelectedRow(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String[] favoriteLocation = dataTableGetStringColumnNoDefaults(beast_lib.DATATABLE_BEAST_FAVORITES, beast_lib.DATATABLE_LOCATION_COL);
        int[] favorites = getIntArrayObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR);
        if (idx >= 0 && idx < favoriteLocation.length)
        {
            String food = "";
            if (favorites == null || favorites.length <= 0)
            {
                debugSpeakMsg(self, "No favorites defined.");
            }
            else 
            {
                debugSpeakMsg(self, "Hated activity: " + favoriteLocation[favorites[5] - 1] + " Changed to: " + favoriteLocation[idx]);
            }
            favorites[5] = idx + 1;
            setObjVar(bcd, beast_lib.PET_FAVORITES_OBJVAR, favorites);
            debugSpeakMsg(self, "Happiness setup completed!");
        }
        return SCRIPT_CONTINUE;
    }
    public int channelRevivePet(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "bm_revive.suiPid"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(beast_lib.getBeastOnPlayer(self)) || !exists(beast_lib.getBeastOnPlayer(self)))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isDead(beast))
        {
            return SCRIPT_CONTINUE;
        }
        location myStartLocation = getLocation(self);
        int duration = 20;
        int reviveTimeMod = getEnhancedSkillStatisticModifierUncapped(self, "expertise_bm_pet_revive_time");
        duration -= reviveTimeMod;
        int flags = sui.CD_EVENT_INCAPACITATE;
        int pid = sui.smartCountdownTimerSUI(self, self, "bm_revive_pet", null, 0, duration, "", 0, flags);
        duration += getGameTime();
        utils.setScriptVar(self, "bm_revive.suiPid", pid);
        utils.setScriptVar(self, "bm_revive.duration", duration);
        utils.setScriptVar(self, "bm_revive.location", myStartLocation);
        messageTo(self, "checkChannelRevivePet", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int checkChannelRevivePet(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "bm_revive.suiPid"))
        {
            utils.removeScriptVarTree(self, "bm_revive");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(beast_lib.getBeastOnPlayer(self)) || !exists(beast_lib.getBeastOnPlayer(self)))
        {
            int pid = utils.getIntScriptVar(self, "bm_revive.suiPid");
            forceCloseSUIPage(pid);
            utils.removeScriptVarTree(self, "bm_revive");
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            return SCRIPT_CONTINUE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        location myStartLocation = utils.getLocationScriptVar(self, "bm_revive.location");
        int duration = utils.getIntScriptVar(self, "bm_revive.duration");
        if (duration - getGameTime() < 1)
        {
            int pid = utils.getIntScriptVar(self, "bm_revive.suiPid");
            forceCloseSUIPage(pid);
            utils.removeScriptVarTree(self, "bm_revive");
            reviveBeast(self, beast);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            utils.setScriptVar(self, "bm_revive.duration", duration);
            messageTo(self, "checkChannelRevivePet", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void reviveBeast(obj_id self, obj_id beast) throws InterruptedException
    {
        obj_id bcd = beast_lib.getBeastBCD(beast);
        if (!exists(beast) || isIdNull(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            return;
        }
        if (!isDead(beast))
        {
            return;
        }
        float revivePercent = 10;
        float maxHealthFloat = getMaxAttrib(beast, HEALTH);
        revivePercent += getEnhancedSkillStatisticModifierUncapped(self, "expertise_bm_pet_recovery");
        maxHealthFloat = maxHealthFloat * (revivePercent / 100);
        int maxHealth = (int)maxHealthFloat;
        beast_lib.checkForFavoriteLocation(bcd);
        utils.setScriptVar(self, "beast.no_store_message", 1);
        beast_lib.storeBeast(bcd);
        beast_lib.setBCDBeastIsDead(bcd, false);
        beast_lib.setBCDBeastHealth(bcd, (int)(maxHealth * (float)(revivePercent / 100)));
        beast_lib.createBeastFromBCD(self, bcd);
        return;
    }
    public int bm_follow_1(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            return SCRIPT_OVERRIDE;
        }
        if (isDead(beast))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            sendSystemMessage(self, new string_id("spam", "pet_beyond_healing"));
            return SCRIPT_OVERRIDE;
        }
        beast_lib.doFollowCommand(beast, self);
        return SCRIPT_CONTINUE;
    }
    public int bm_stay_1(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            return SCRIPT_OVERRIDE;
        }
        if (isDead(beast))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            sendSystemMessage(self, new string_id("spam", "pet_beyond_healing"));
            return SCRIPT_OVERRIDE;
        }
        beast_lib.doStayCommand(beast, self);
        return SCRIPT_CONTINUE;
    }
    public int bm_pet_attack_1(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            return SCRIPT_OVERRIDE;
        }
        if (isDead(beast))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            sendSystemMessage(self, new string_id("spam", "pet_beyond_healing"));
            return SCRIPT_OVERRIDE;
        }
        beast_lib.doAttackCommand(beast, self);
        return SCRIPT_CONTINUE;
    }
    public int bm_pet_trick_1(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            return SCRIPT_OVERRIDE;
        }
        if (isDead(beast))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            sendSystemMessage(self, new string_id("spam", "pet_beyond_healing"));
            return SCRIPT_OVERRIDE;
        }
        beast_lib.doTrickCommand(beast, 1);
        return SCRIPT_CONTINUE;
    }
    public int bm_pet_trick_2(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            return SCRIPT_OVERRIDE;
        }
        if (isDead(beast))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            sendSystemMessage(self, new string_id("spam", "pet_beyond_healing"));
            return SCRIPT_OVERRIDE;
        }
        beast_lib.doTrickCommand(beast, 2);
        return SCRIPT_CONTINUE;
    }
    public int bm_pet_trick_3(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id beast = beast_lib.getBeastOnPlayer(self);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(self, new string_id("spam", "no_beast_out"));
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            return SCRIPT_OVERRIDE;
        }
        if (isDead(beast))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
            sendSystemMessage(self, new string_id("spam", "pet_beyond_healing"));
            return SCRIPT_OVERRIDE;
        }
        sendSystemMessageTestingOnly(self, "Beast Command: Trick 3 (not hooked up yet)");
        return SCRIPT_CONTINUE;
    }
    public int performCreatureAbility(obj_id self, dictionary params) throws InterruptedException
    {
        String ability = params.getString(ABILITY_TO_EXECUTE);
        obj_id pet = beast_lib.getBeastOnPlayer(self);
        int toExecute = utils.stringToInt(ability);
        if (toExecute < 1 || toExecute > 4)
        {
            sendSystemMessageTestingOnly(self, "SYNTAX: doability 1-4");
            return SCRIPT_CONTINUE;
        }
        toExecute = toExecute - 1;
        if (!beast_lib.isBeastMaster(self))
        {
            sendSystemMessageTestingOnly(self, "Only beastmasters may use this command");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(pet) || !exists(pet))
        {
            sendSystemMessageTestingOnly(self, "You do not have a valid pet");
            return SCRIPT_CONTINUE;
        }
        String[] skillList = beast_lib.getTrainedSkills(pet);
        if (skillList[toExecute].equals(""))
        {
            sendSystemMessageTestingOnly(self, "No ability for slot " + toExecute);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "Queue'd pet command: " + skillList[toExecute]);
        obj_id target = isIdValid(getTarget(pet)) ? getTarget(pet) : getTarget(self);
        queueCommand(pet, getStringCrc(skillList[toExecute].toLowerCase()), target, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int getPetTrainPid(obj_id player) throws InterruptedException
    {
        return utils.hasScriptVar(player, TRAIN_DIALOGUE_PID) ? utils.getIntScriptVar(player, TRAIN_DIALOGUE_PID) : 0;
    }
    public void setPetTrainPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid == -1)
        {
            utils.removeScriptVar(player, TRAIN_DIALOGUE_PID);
        }
        else 
        {
            utils.setScriptVar(player, TRAIN_DIALOGUE_PID, pid);
        }
    }
    public int cmdTrainPet(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id pet = beast_lib.getBeastOnPlayer(self);
        if (!beast_lib.canTrainPet(self, pet))
        {
            return SCRIPT_OVERRIDE;
        }
        int pid = getPetTrainPid(self);
        if (pid > 0)
        {
            sui.closeSUI(self, pid);
        }
        createTrainingSui(self, pet);
        return SCRIPT_CONTINUE;
    }
    public void createTrainingSui(obj_id player, obj_id pet) throws InterruptedException
    {
        if (!beast_lib.isValidPlayer(player) || !beast_lib.isValidBeast(pet))
        {
            return;
        }
        String[] playerKnownSkills = beast_lib.getKnownSkills(player);
        if (playerKnownSkills == null || playerKnownSkills.length == 0)
        {
            sendSystemMessage(player, new string_id("beast", "player_no_known_skills"));
            return;
        }
        String title = utils.packStringId(new string_id("beast", "train_sui_title"));
        String prompt = utils.packStringId(new string_id("beast", "train_sui_heading"));
        int pid = createSUIPage(sui.SUI_LISTBOX, player, player, "handlePetTrainingDialog");
        setSUIProperty(pid, "", "Size", "650,375");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.listboxButtonSetup(pid, sui.OK_CANCEL);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@train");
        setSUIProperty(pid, sui.LISTBOX_BTN_CANCEL, sui.PROP_TEXT, "@cancel");
        clearSUIDataSource(pid, sui.LISTBOX_DATASOURCE);
        obj_id bcd = beast_lib.getBeastBCD(pet);
        if (!beast_lib.isValidBCD(bcd))
        {
            return;
        }
        for (int i = 0; i < playerKnownSkills.length; i++)
        {
            String abilityName = playerKnownSkills[i];
            int returnCode = beast_lib.getTrainingSuccessCode(pet, abilityName);
            prose_package pp = new prose_package();
            switch (returnCode)
            {
                case beast_lib.TRAINING_INVALID_PETTYPE:
                pp.stringId = new string_id("beast", "train_fail_bad_pet_type_for_ability");
                pp.actor.set(new string_id("cmd_n", abilityName));
                abilityName = " \0" + packOutOfBandProsePackage(null, pp);
                break;
                case beast_lib.TRAINING_SKILL_TOO_HIGH:
                pp.stringId = new string_id("beast", "train_fail_single_skill_max");
                pp.actor.set(new string_id("cmd_n", abilityName));
                abilityName = " \0" + packOutOfBandProsePackage(null, pp);
                break;
                case beast_lib.TRAINING_INSUFFICIENT_POINTS:
                pp.stringId = new string_id("beast", "train_fail_insufficient_points");
                pp.actor.set(new string_id("cmd_n", abilityName));
                abilityName = " \0" + packOutOfBandProsePackage(null, pp);
                break;
                case beast_lib.TRAINING_NON_IMPROVED_SKILL:
                pp.stringId = new string_id("beast", "train_fail_already_known");
                pp.actor.set(new string_id("cmd_n", abilityName));
                abilityName = " \0" + packOutOfBandProsePackage(null, pp);
                break;
                case beast_lib.TRAINING_NO_AVAILABLE_SLOTS:
                pp.stringId = new string_id("beast", "train_fail_no_slots");
                pp.actor.set(new string_id("cmd_n", abilityName));
                abilityName = " \0" + packOutOfBandProsePackage(null, pp);
                break;
                case beast_lib.TRAINING_VALID:
                pp.stringId = new string_id("cmd_n", abilityName);
                abilityName = " \0" + packOutOfBandProsePackage(null, pp);
                break;
            }
            addSUIDataItem(pid, sui.LISTBOX_DATASOURCE, "" + i);
            setSUIProperty(pid, sui.LISTBOX_DATASOURCE + "." + i, sui.PROP_TEXT, abilityName);
        }
        subscribeToSUIEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, "handlePetTrainingUpdate");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onGenericSelection, sui.LISTBOX_LIST, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_LIST, sui.PROP_SELECTEDROW);
        subscribeToSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT);
        showSUIPage(pid);
        flushSUIPage(pid);
        setPetTrainPid(player, pid);
    }
    public int handlePetTrainingDialog(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if ((params == null) || (params.isEmpty()))
        {
            setPetTrainPid(self, -1);
            return SCRIPT_CONTINUE;
        }
        obj_id pet = beast_lib.getBeastOnPlayer(player);
        String[] abilityList = beast_lib.getKnownSkills(player);
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (sui.getListboxOtherButtonPressed(params))
        {
            return SCRIPT_CONTINUE;
        }
        switch (bp)
        {
            case sui.BP_OK:
            if (idx > -1)
            {
                beast_lib.trainPetAbility(pet, abilityList[idx]);
                queueCommand(player, (1828856022), player, "", COMMAND_PRIORITY_DEFAULT);
            }
            break;
        }
        setPetTrainPid(player, -1);
        return SCRIPT_CONTINUE;
    }
    public int handlePetTrainingUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            setPetTrainPid(self, -1);
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String[] abilityList = beast_lib.getKnownSkills(player);
        int pid = getPetTrainPid(player);
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String ability = abilityList[idx];
        String prompt = utils.packStringId(new string_id("beast", "train_sui_heading"));
        prompt += getPetAbilityDescription(ability);
        setSUIProperty(pid, sui.LISTBOX_PROMPT, sui.PROP_TEXT, prompt);
        flushSUIPage(pid);
        return SCRIPT_CONTINUE;
    }
    public String getPetAbilityDescription(String ability) throws InterruptedException
    {
        String desc = "";
        return desc;
    }
    public int bm_creature_knowledge(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id objTarget = getIntendedTarget(self);
        if (!isIdValid(objTarget) || !ai_lib.isMonster(objTarget) || beast_lib.isBeast(objTarget) || pet_lib.isPet(objTarget))
        {
            sendSystemMessage(getSelf(), new string_id("beast", "invalid_target_for_creature_knowledge"));
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(objTarget, "bm_doNotShowExamineInfo"))
        {
            sendSystemMessage(getSelf(), new string_id("beast", "invalid_target_for_creature_knowledge"));
            return SCRIPT_OVERRIDE;
        }
        int flags = 0;
        flags |= sui.CD_EVENT_INCAPACITATE;
        flags |= sui.CD_EVENT_DAMAGED;
        int startTime = 0;
        int endTime = 6;
        String displayGroup = "bm_creature_knowledge";
        string_id prompt = new string_id("beast", "ability_activate_creature_knowledge");
        String handler = "completeTheZipTimer";
        float maxRange = 32;
        int pid = sui.smartCountdownTimerSUI(self, self, displayGroup, prompt, startTime, endTime, handler, maxRange, flags);
        utils.setScriptVar(self, sui.COUNTDOWNTIMER_SUI_VAR, pid);
        utils.setScriptVar(self, "target", objTarget);
        return SCRIPT_CONTINUE;
    }
    public int completeTheZipTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_DAMAGED)
            {
                return SCRIPT_CONTINUE;
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                return SCRIPT_CONTINUE;
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            return SCRIPT_CONTINUE;
        }
        int test_pid = getIntObjVar(player, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            return SCRIPT_CONTINUE;
        }
        forceCloseSUIPage(pid);
        detachScript(player, sui.COUNTDOWNTIMER_PLAYER_SCRIPT);
        obj_id target = utils.getObjIdScriptVar(self, "target");
        utils.removeScriptVar(self, "target");
        buff.applyBuff(self, "bm_creature_knowledge");
        int[] buffs = buff.getAllBuffs(target);
        if (buffs != null && buffs.length > 0)
        {
            for (int i = 0; i < buffs.length; i++)
            {
                String name = buff.getBuffNameFromCrc(buffs[i]);
                if (beast_lib.makeAbilityLearnSkillCheck(self, name))
                {
                    beast_lib.playerLearnBeastMasterSkill(self, name);
                }
            }
        }
        int species = getSpecies(target);
        utils.setScriptVar(self, "creature_knowledge.species", species);
        return SCRIPT_CONTINUE;
    }
    public int bm_pig_forage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_truffle_pig"))
        {
            buff.applyBuff(player, "bm_truffle_pig");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_helper_monkey_domestic(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_helper_monkey_domestic"))
        {
            buff.applyBuff(player, "bm_helper_monkey_domestic");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_helper_monkey_engineering(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_helper_monkey_engineering"))
        {
            buff.applyBuff(player, "bm_helper_monkey_engineering");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_helper_monkey_structure(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_helper_monkey_structure"))
        {
            buff.applyBuff(player, "bm_helper_monkey_structure");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_helper_monkey_munitions(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_helper_monkey_munitions"))
        {
            buff.applyBuff(player, "bm_helper_monkey_munitions");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_helper_monkey_jedi(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_helper_monkey_jedi"))
        {
            buff.applyBuff(player, "bm_helper_monkey_jedi");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_helper_monkey_shipwright(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!buff.hasBuff(player, "bm_helper_monkey_shipwright"))
        {
            buff.applyBuff(player, "bm_helper_monkey_shipwright");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int bm_dancing_cat(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id player = self;
        if (!beast_lib.isBeastMaster(player))
        {
            sendSystemMessage(player, beast_lib.SID_NOT_BEAST_MASTER);
            return SCRIPT_OVERRIDE;
        }
        obj_id beast = beast_lib.getBeastOnPlayer(player);
        if (!isIdValid(beast) || !exists(beast))
        {
            sendSystemMessage(player, beast_lib.SID_NO_BEAST_OUT);
            return SCRIPT_OVERRIDE;
        }
        obj_id bcd = beast_lib.getBeastBCD(beast);
        String beastName = beast_lib.getBeastType(bcd);
        dictionary beastDict = utils.dataTableGetRow(beast_lib.BEASTS_TABLE, beastName);
        String specialAttackFamily = beastDict.getString("special_attack_family");
        if (!specialAttackFamily.equals("predatory_cat"))
        {
            sendSystemMessage(player, new string_id("beast", "beast_wrong_type"));
            return SCRIPT_OVERRIDE;
        }
        location startLocation = getLocation(beast);
        float beastYaw = getYaw(beast);
        float beastScale = getScale(beast);
        String template = getTemplateName(beast);
        beastName = getName(beast);
        String[] splitName = split(beastName, '/');
        if (splitName.length > 1)
        {
            beastName = "@" + beastName;
        }
        location holoOneLocation = startLocation;
        holoOneLocation.z = holoOneLocation.z + 2;
        holoOneLocation = utils.rotatePointXZ(startLocation, holoOneLocation, beastYaw);
        obj_id holo_01 = create.object(template, holoOneLocation, false);
        setInvulnerable(holo_01, true);
        setHologramType(holo_01, HOLOGRAM_TYPE1_QUALITY3);
        setName(holo_01, beastName);
        setYaw(holo_01, beastYaw);
        setScale(holo_01, beastScale);
        location holoTwoLocation = startLocation;
        holoTwoLocation.z = holoTwoLocation.z - 2;
        holoTwoLocation = utils.rotatePointXZ(startLocation, holoTwoLocation, beastYaw);
        obj_id holo_02 = create.object(template, holoTwoLocation, false);
        setInvulnerable(holo_02, true);
        setHologramType(holo_02, HOLOGRAM_TYPE1_QUALITY3);
        setName(holo_02, beastName);
        setYaw(holo_02, beastYaw);
        setScale(holo_02, beastScale);
        int beastTrickNum = rand(1, 2);
        int holoTrickNum = 1;
        if (beastTrickNum == 1)
        {
            holoTrickNum = 2;
        }
        doAnimationAction(beast, "trick_" + beastTrickNum);
        doAnimationAction(holo_01, "trick_" + holoTrickNum);
        doAnimationAction(holo_02, "trick_" + holoTrickNum);
        dictionary dict = new dictionary();
        dict.put("holo_01", holo_01);
        dict.put("holo_02", holo_02);
        messageTo(self, "destroyHoloPets", dict, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyHoloPets(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id holo_01 = params.getObjId("holo_01");
        obj_id holo_02 = params.getObjId("holo_02");
        destroyObject(holo_01);
        destroyObject(holo_02);
        return SCRIPT_CONTINUE;
    }
    public int bm_dancing_pet_entertainer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (utils.isProfession(self, 9))
        {
            if (!buff.hasBuff(self, "bm_dancing_pet_entertainer"))
            {
                buff.applyBuff(self, "bm_dancing_pet_entertainer");
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
    }
    public int bm_survey_bonus(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!buff.hasBuff(self, "bm_survey_bonus"))
        {
            buff.applyBuff(self, "bm_survey_bonus");
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCustomizeFinished(obj_id self, obj_id object, String params) throws InterruptedException
    {
        if (!isIdValid(object) || !exists(object))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(object, "beast.tool_oid"))
        {
            beast_lib.initializeBeastColor(object);
            return SCRIPT_CONTINUE;
        }
        obj_id beastDye = utils.getObjIdScriptVar(object, "beast.tool_oid");
        if (!isValidId(beastDye) || !exists(beastDye) || !utils.isNestedWithin(beastDye, self) || params == null || params.equals(""))
        {
            debugSpeakMsg(self, "cancelled hue.  reinitializing color object: " + object);
            beast_lib.initializeBeastColor(object);
            return SCRIPT_CONTINUE;
        }
        String[] colorArray = split(params, ' ');
        if (colorArray.length < 2)
        {
            beast_lib.initializeBeastColor(object);
            return SCRIPT_CONTINUE;
        }
        if (colorArray.length >= 2)
        {
            beast_lib.setBeastHuePrimary(object, colorArray[0], utils.stringToInt(colorArray[1]));
        }
        if (colorArray.length >= 4)
        {
            beast_lib.setBeastHueSecondary(object, colorArray[2], utils.stringToInt(colorArray[3]));
        }
        if (colorArray.length >= 6)
        {
            beast_lib.setBeastHueThird(object, colorArray[4], utils.stringToInt(colorArray[5]));
        }
        utils.removeScriptVar(object, "beast.tool_oid");
        destroyObject(beastDye);
        return SCRIPT_CONTINUE;
    }
}

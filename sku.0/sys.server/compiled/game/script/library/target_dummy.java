package script.library;

import script.combat_engine.combat_data;
import script.combat_engine.hit_result;
import script.*;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

public class target_dummy extends script.base_script
{
    public target_dummy()
    {
    }
    public static final String TERMINAL_LOGGING = "target_creature";
    public static final boolean LOGGING_ON = true;
    public static final String OWNER_OBJVAR = "targetDummyOwner";
    public static final String CREATURE_NAME_OBJVAR = "targetDummyCreatureName";
    public static final String TARGET_DUMMY_CONTROLLER_OBJVAR = "targetDummyController";
    public static final String TARGET_DUMMY_ID_OBJVAR = "targetDummyId";
    public static final String TARGET_DUMMY_SOUND_DELAY = "targetDummySoundDelay";
    public static final String TARGET_DUMMY_NO_SOUND = "targetDummyNoSound";
    public static final String[] TARGET_DUMMY_DEFENSES = 
    {
        "expertise_innate_protection_all",
        "expertise_dodge",
        "expertise_parry",
        "expertise_block_chance",
        "expertise_glancing_blow_all",
        "expertise_evasion_chance"
    };
    public static final String BASE_TARGET_DUMMY_VAR = "target_dummy";
    public static final String ALL_ATTACKS_SUB_VAR = "all_attacks";
    public static final String LAST_ATTACK_TIME_VAR = "lastAttackTime";
    public static final int PURGE_DATA_INTERVAL = 6 * 60 * 60;
    public static final int INDEX_NUM_OF_ATTACKS = 0;
    public static final int INDEX_TOTAL_DAMAGE_DONE = 1;
    public static final int INDEX_LARGEST_ATTACK = 2;
    public static final int INDEX_SMALLEST_ATTACK = 3;
    public static final int INDEX_NUM_CRITAL_HITS = 4;
    public static final int INDEX_NUM_STRIKETHROUGH = 5;
    public static final int INDEX_NUM_MISSED = 6;
    public static final int INDEX_NUM_DODGED = 7;
    public static final int INDEX_NUM_PARRIED = 8;
    public static final int INDEX_NUM_GLANCING_HITS = 9;
    public static final int INDEX_NUM_EVADED_HITS = 10;
    public static final int INDEX_NUM_BLOCKED_HITS = 11;
    public static final int INDEX_NUM_SUCCESSFUL_HITS = 12;
    public static final int INDEX_NUM_NORMAL_HITS = 13;
    public static final String[] ATTACK_DATA_ARRAY = 
    {
        "numAttacks",
        "totalDamageDone",
        "largestAttack",
        "smallestAttack",
        "numCriticalHits",
        "numStrikethroughHit",
        "numMissed",
        "numDodged",
        "numParried",
        "numGlancingHit",
        "numEvadedHit",
        "numBlockedHit",
        "numSuccessfulAttacks",
        "numNormalHits"
    };
    public static final string_id REPORT_TITLE_SID = new string_id("target_dummy", "report_title");
    public static final string_id REPORT_ATTACK_TYPE_SID = new string_id("target_dummy", "report_attack_type");
    public static final string_id REPORT_NUM_ATTACKS_SID = new string_id("target_dummy", "report_num_attacks");
    public static final string_id REPORT_TOTAL_DAMAGE_SID = new string_id("target_dummy", "report_total_damage");
    public static final string_id REPORT_AVERAGE_DAMAGE_SID = new string_id("target_dummy", "report_average_damage");
    public static final string_id REPORT_SMALLEST_ATTACK_SID = new string_id("target_dummy", "report_smallest_attack");
    public static final string_id REPORT_LARGEST_ATTACK_SID = new string_id("target_dummy", "report_largest_attack");
    public static final string_id REPORT_NUM_CRITS_SID = new string_id("target_dummy", "report_num_crits");
    public static final string_id REPORT_NUM_STRIKETHROUGH_SID = new string_id("target_dummy", "report_num_strikethrough");
    public static final string_id REPORT_NUM_MISSES_SID = new string_id("target_dummy", "report_num_misses");
    public static final string_id REPORT_NUM_DODGES_SID = new string_id("target_dummy", "report_num_dodges");
    public static final string_id REPORT_NUM_PARRIES_SID = new string_id("target_dummy", "report_num_parries");
    public static final string_id REPORT_NUM_GLANCING_SID = new string_id("target_dummy", "report_num_glancing");
    public static final string_id REPORT_NUM_EVADES_SID = new string_id("target_dummy", "report_num_evades");
    public static final string_id REPORT_NUM_BLOCKED_SID = new string_id("target_dummy", "report_num_blocked");
    public static final string_id REPORT_NUM_SUCCESSFUL_SID = new string_id("target_dummy", "report_num_successful_attacks");
    public static final string_id REPORT_NUM_NORMAL_SID = new string_id("target_dummy", "report_num_normal_hits");
    public static final string_id SID_TARGET_DUMMY_DIED_RESSURECTION = new string_id("target_dummy", "target_died_ressurected");
    public static final string_id REPORT_LINE_BREAK_SID = new string_id("target_dummy", "report_line_break");
    public static final string_id SID_SOUND_ANIMS_ON = new string_id("target_dummy", "sounds_anims_on");
    public static final string_id SID_SOUND_ANIMS_OFF = new string_id("target_dummy", "sounds_anims_off");
    public static final string_id REPORT_PERCENT_SIGN = new string_id("target_dummy", "report_percent");
    public static final String GREEN = "  \\#99FF33 ";
    public static final String RED = "  \\#FF3300 ";
    public static final String ORANGE = "  \\#FF8C00 ";
    public static final String WHITE = "  \\#FFFFFF ";
    public static final String BLUE = "  \\#0099FF ";
    public static final String YELLOW = "  \\#FFFF00 ";
    public static final String LINE = "  \n ";
    public static final String[] TARGET_DUMMY_ANIMS = 
    {
        "shiver",
        "shake_head_no",
        "slump_head",
        "beg",
        "apologize",
        "cover_eyes",
        "cover_mouth",
        "hands_above_head",
        "hug_self",
        "implore"
    };
    public static String addLineBreaks(int num) throws InterruptedException
    {
        String lineBreaks = "";
        for (int i = 1; i <= num; i++)
        {
            lineBreaks += LINE;
        }
        return lineBreaks;
    }
    public static String percentSign() throws InterruptedException
    {
        return " " + utils.packStringId(REPORT_PERCENT_SIGN);
    }
    public static boolean isTargetDummy(obj_id target) throws InterruptedException
    {
        return hasObjVar(target, "isTargetDummy");
    }
    public static obj_id createTargetDummy(obj_id controller, obj_id player) throws InterruptedException
    {
        if (!isValidId(controller) || !isValidId(player))
        {
            return obj_id.NULL_ID;
        }
        String creature = getTargetDummyCreatureType(controller);
        if (creature == null || creature.equals(""))
        {
            return obj_id.NULL_ID;
        }
        if (creature.equals("tcg_target_dummy"))
        {
            if (isInWorldCell(player) || isInWorldCell(controller))
            {
                blog("Both are in world cell, abort");
                return obj_id.NULL_ID;
            }
            location controllerLocation = getLocation(controller);
            location awayLoc = utils.getRandomAwayLocation(controllerLocation, 1.f, 2.f);
            if ((awayLoc.cell != null) && isIdValid(awayLoc.cell))
            {
                awayLoc = (location)controllerLocation.clone();
            }
            if (!permittedToCallTargetDummy(player, awayLoc))
            {
                return obj_id.NULL_ID;
            }
            obj_id creatureObject = createTargetDummyAtLocation(controller, player, awayLoc);
            messageTo(creatureObject, "faceThePlayer", null, 2, false);
            messageTo(creatureObject, "checkCurrentLocation", null, 1, false);
            return creatureObject;
        }
        else 
        {
            location where = getLocation(controller);
            if (!permittedToCallTargetDummy(player, where))
            {
                return obj_id.NULL_ID;
            }
            return createTargetDummyAtLocation(controller, player, where);
        }
    }
    public static obj_id recreateTargetDummy(obj_id controller, obj_id player) throws InterruptedException
    {
        if (isInWorldCell(controller))
        {
            return obj_id.NULL_ID;
        }
        location where = getLocation(controller);
        obj_id targetDummyObj = createTargetDummyAtLocation(controller, player, where);
        if ((getTargetDummyCreatureType(controller)).equals("tcg_target_dummy"))
        {
            float controllerYaw = getYaw(controller);
            location offsetLoc = (location)where.clone();
            offsetLoc.x += 2f;
            location pathToLoc = utils.rotatePointXZ(where, offsetLoc, controllerYaw);
            pathTo(targetDummyObj, pathToLoc);
            setYaw(targetDummyObj, controllerYaw);
            messageTo(targetDummyObj, "checkCurrentLocation", null, 1, false);
        }
        return targetDummyObj;
    }
    public static obj_id createTargetDummyAtLocation(obj_id controller, obj_id player, location createLoc) throws InterruptedException
    {
        obj_id npc = obj_id.NULL_ID;
        obj_id owner = getTargetDummyOwnerFromController(controller);
        if (isIdValid(owner))
        {
            String toCreate = getTargetDummyCreatureType(controller);
            npc = create.object(toCreate, createLoc);
            if (isIdValid(npc))
            {
                float controllerYaw = getYaw(controller);
                if (exists(player))
                {
                    location playerLoc = getLocation(player);
                    if (playerLoc.cell == createLoc.cell)
                    {
                        faceTo(npc, player);
                    }
                    else 
                    {
                        setYaw(npc, controllerYaw);
                    }
                }
                else 
                {
                    setYaw(npc, controllerYaw);
                }
                obj_id structure = getTopMostContainer(controller);
                if (isIdValid(structure) && isGameObjectTypeOf(structure, GOT_building_player))
                {
                    permissionsAddAllowed(structure, npc.toString());
                }
                setObjVar(npc, OWNER_OBJVAR, owner);
                setObjVar(npc, TARGET_DUMMY_CONTROLLER_OBJVAR, controller);
                setObjVar(controller, TARGET_DUMMY_ID_OBJVAR, npc);
                ai_lib.setDefaultCalmBehavior(npc, ai_lib.BEHAVIOR_SENTINEL);
                String planet = createLoc.area;
                if (planet.startsWith("space_"))
                {
                    setInvulnerable(npc, true);
                }
            }
        }
        return npc;
    }
    public static void cleanupTargetDummy(obj_id controller, obj_id targetDummy) throws InterruptedException
    {
        removeObjVar(controller, target_dummy.TARGET_DUMMY_ID_OBJVAR);
        removeTargetDummyFromPermissions(targetDummy);
        destroyObject(targetDummy);
    }
    public static void removeTargetDummyFromPermissions(obj_id targetDummy) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(targetDummy);
        if (isIdValid(structure))
        {
            if (player_structure.isNameOnEntryList(structure, targetDummy.toString()))
            {
                permissionsRemoveAllowed(structure, targetDummy.toString());
            }
        }
    }
    public static void removeTargetDummyFromPermissionsViaController(obj_id controller, obj_id targetDummy) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(controller);
        if (isIdValid(structure))
        {
            if (player_structure.isNameOnEntryList(structure, targetDummy.toString()))
            {
                permissionsRemoveAllowed(structure, targetDummy.toString());
            }
        }
    }
    public static obj_id setTargetDummyOwner(obj_id controller) throws InterruptedException
    {
        obj_id owner = getOwner(controller);
        if (isIdValid(owner))
        {
            return owner;
        }
        else 
        {
            CustomerServiceLog("target_dummy", "target_dummy.scriptlib:setTargetDummyOwner - The Target Dummy System is attempting to assign an owner for controller: " + controller);
            owner = utils.getContainingPlayer(controller);
            if (isIdValid(owner))
            {
                setOwner(controller, owner);
                CustomerServiceLog("target_dummy", "target_dummy.scriptlib:setTargetDummyOwner - Target Dummy Controller: " + controller + " Ownership given to Owner OID: " + owner);
            }
        }
        return owner;
    }
    public static obj_id getTargetDummyOwnerFromController(obj_id controller) throws InterruptedException
    {
        return getOwner(controller);
    }
    public static obj_id getTargetDummyController(obj_id targetDummy) throws InterruptedException
    {
        obj_id controller = obj_id.NULL_ID;
        if (hasObjVar(targetDummy, TARGET_DUMMY_CONTROLLER_OBJVAR))
        {
            controller = getObjIdObjVar(targetDummy, TARGET_DUMMY_CONTROLLER_OBJVAR);
        }
        return controller;
    }
    public static obj_id getTargetDummyId(obj_id controller) throws InterruptedException
    {
        obj_id targetDummy = obj_id.NULL_ID;
        if (hasObjVar(controller, TARGET_DUMMY_ID_OBJVAR))
        {
            targetDummy = getObjIdObjVar(controller, TARGET_DUMMY_ID_OBJVAR);
        }
        return targetDummy;
    }
    public static String getTargetDummyCreatureType(obj_id controller) throws InterruptedException
    {
        String targetDummyCreatureType = "tcg_target_creature_acklay";
        if (hasObjVar(controller, target_dummy.CREATURE_NAME_OBJVAR))
        {
            targetDummyCreatureType = getStringObjVar(controller, target_dummy.CREATURE_NAME_OBJVAR);
        }
        return targetDummyCreatureType;
    }
    public static boolean permittedToCallTargetDummy(obj_id player, location here) throws InterruptedException
    {
        if (!isIdValid(player) || here == null)
        {
            return false;
        }
        if (isGod(player))
        {
            String logMsg = "(" + player + ")" + getName(player) + " is using GodMode override to deploy a target dummy at " + here;
            CustomerServiceLog("target_dummy", logMsg);
            sendSystemMessage(player, new string_id("target_dummy", "placement_god_mode"));
            return true;
        }
        obj_id myCell = here.cell;
        obj_id myContainer = getTopMostContainer(myCell);
        String planet = here.area;
        if (planet.startsWith("space_"))
        {
            obj_id owner = getOwner(myContainer);
            if (!isIdValid(owner) || owner != player)
            {
                sendSystemMessage(player, new string_id("target_dummy", "placement_not_ship_owner"));
                return false;
            }
            if (getState(player, STATE_SHIP_INTERIOR) != 1 || space_utils.isInStation(player))
            {
                sendSystemMessage(player, new string_id("target_dummy", "placement_not_in_station"));
                return false;
            }
        }
        else 
        {
            if (!isIdValid(myCell))
            {
                sendSystemMessage(player, new string_id("target_dummy", "placement_not_outside"));
                return false;
            }
            else if (!player_structure.isAdmin(myContainer, player))
            {
                sendSystemMessage(player, new string_id("target_dummy", "placement_not_building_admin"));
                return false;
            }
        }
        return true;
    }
    public static boolean controllerContainmentCheck(obj_id controller) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(controller))
        {
            obj_id building = getTopMostContainer(controller);
            if (isGameObjectTypeOf(building, GOT_building_player) || (isGameObjectTypeOf(building, GOT_ship_fighter) && space_utils.isShipWithInterior(building)))
            {
                location here = getLocation(controller);
                if (getContainedBy(controller) == here.cell)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean initializeTargetDummy(obj_id targetDummy, int combatLevel, int difficulty) throws InterruptedException
    {
        String creatureName = getCreatureName(targetDummy);
        dictionary creatureDict = utils.dataTableGetRow(create.CREATURE_TABLE, creatureName);
        if (creatureDict != null)
        {
            creatureDict.put("difficultyClass", difficulty);
            create.initializeCreature(targetDummy, creatureName, creatureDict, combatLevel);
            setNpcDifficulty(targetDummy, difficulty);
            obj_id controller = target_dummy.getTargetDummyController(targetDummy);
            if (isIdValid(controller))
            {
                setObjVar(controller, "intCombatDifficulty", combatLevel);
                setObjVar(controller, "difficultyClass", difficulty);
                return true;
            }
        }
        return false;
    }
    public static int getTargetDummyDifficulty(obj_id targetDummy) throws InterruptedException
    {
        int difficulty = 0;
        if (hasObjVar(targetDummy, "difficultyClass"))
        {
            difficulty = getIntObjVar(targetDummy, "difficultyClass");
        }
        return difficulty;
    }
    public static int getTargetDummyCombatLevel(obj_id targetDummy) throws InterruptedException
    {
        int level = 50;
        if (hasObjVar(targetDummy, "intCombatDifficulty"))
        {
            level = getIntObjVar(targetDummy, "intCombatDifficulty");
        }
        return level;
    }
    public static void promptForCombatLevel(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        String prompt = utils.packStringId(new string_id("target_dummy", "combat_level_prompt"));
        String name = utils.packStringId(new string_id("target_dummy", "combat_level_name"));
        sui.inputbox(targetDummy, player, prompt, sui.OK_CANCEL, name, sui.INPUT_NORMAL, null, "handleTargetDummyLevelSelect", null);
    }
    public static void promptForCombatDifficulty(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        String title = utils.packStringId(new string_id("target_dummy", "combat_difficulty_title"));
        String prompt = utils.packStringId(new string_id("target_dummy", "combat_difficulty_prompt"));
        String normal = utils.packStringId(new string_id("target_dummy", "difficulty_normal"));
        String elite = utils.packStringId(new string_id("target_dummy", "difficulty_elite"));
        String boss = utils.packStringId(new string_id("target_dummy", "difficulty_boss"));
        String[] difficulties = 
        {
            normal,
            elite,
            boss
        };
        sui.listbox(targetDummy, player, prompt, sui.OK_CANCEL, title, difficulties, "handleTargetDummyDifficultySelect");
    }
    public static void showTargetDummyDefenseListSui(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "targetDummy.pidTargetDummyDefenses"))
        {
            int oldPid = utils.getIntScriptVar(player, "targetDummy.pidTargetDummyDefenses");
            forceCloseSUIPage(oldPid);
        }
        String title = utils.packStringId(new string_id("target_dummy", "combat_set_defenses_title"));
        String prompt = utils.packStringId(new string_id("target_dummy", "combat_set_defenses_prompt"));
        int pid = sui.listbox(targetDummy, player, prompt, sui.OK_CANCEL, title, getTargetDummySkillModsOptions(targetDummy), "targetDummyDefenseTypeSelected");
        if (pid > -1)
        {
            utils.setScriptVar(player, "targetDummy.pidTargetDummyDefenses", pid);
        }
    }
    public static String[] getTargetDummySkillModsOptions(obj_id targetDummy) throws InterruptedException
    {
        String[] targetDummySkillMods = new String[TARGET_DUMMY_DEFENSES.length];
        for (int i = 0; i < TARGET_DUMMY_DEFENSES.length; i++)
        {
            String skillModName = TARGET_DUMMY_DEFENSES[i];
            String skillModOption = utils.packStringId(new string_id("target_dummy", TARGET_DUMMY_DEFENSES[i]));
            targetDummySkillMods[i] = skillModOption + " [current value: " + getEnhancedSkillStatisticModifier(targetDummy, skillModName) + "]";
        }
        return targetDummySkillMods;
    }
    public static void promptForDefensiveValue(obj_id targetDummy, obj_id player, int skill_mod_selected) throws InterruptedException
    {
        utils.setScriptVar(player, "targetDummySkillModSelected", skill_mod_selected);
        String prompt = utils.packStringId(new string_id("target_dummy", "combat_defensive_skill_mod_prompt"));
        prompt += "  " + utils.packStringId(new string_id("target_dummy", target_dummy.TARGET_DUMMY_DEFENSES[skill_mod_selected])) + " :";
        sui.inputbox(targetDummy, player, prompt, "handleTargetDummyDefensiveSkillModSet");
    }
    public static void setTargetDummyDefensiveValue(obj_id targetDummy, obj_id player, int value) throws InterruptedException
    {
        int skillModIndex = utils.getIntScriptVar(player, "targetDummySkillModSelected");
        String skillModName = target_dummy.TARGET_DUMMY_DEFENSES[skillModIndex];
        setTargetDummyDefensiveValue(targetDummy, player, value, skillModName);
    }
    public static void setTargetDummyDefensiveValue(obj_id targetDummy, obj_id player, int value, String skillModName) throws InterruptedException
    {
        int previousValue = getEnhancedSkillStatisticModifier(targetDummy, skillModName);
        if (skillModName.equals("expertise_innate_protection_all"))
        {
            if (value < 0 || value > 12000)
            {
                if (isIdValid(player))
                {
                    sendSystemMessage(player, new string_id("target_dummy", "combat_armor_mod_invalid"));
                    return;
                }
            }
            else 
            {
                applySkillStatisticModifier(targetDummy, skillModName, 0 - previousValue);
                applySkillStatisticModifier(targetDummy, skillModName, value);
                armor.recalculateArmorForMob(targetDummy);
            }
        }
        else 
        {
            if (value < 0 || value > 99)
            {
                if (isIdValid(player))
                {
                    sendSystemMessage(player, new string_id("target_dummy", "combat_skill_mod_invalid"));
                    return;
                }
            }
            else 
            {
                applySkillStatisticModifier(targetDummy, skillModName, 0 - previousValue);
                applySkillStatisticModifier(targetDummy, skillModName, value);
            }
        }
        if (isIdValid(player))
        {
            string_id message = new string_id("target_dummy", "combat_skill_mod_set");
            prose_package pp = prose.getPackage(message, player, player);
            prose.setTO(pp, utils.packStringId(new string_id("target_dummy", skillModName)));
            prose.setDI(pp, value);
            sendSystemMessageProse(player, pp);
        }
        obj_id controller = getTargetDummyController(targetDummy);
        if (isIdValid(controller))
        {
            setObjVar(controller, "target_dummy_defense." + skillModName, value);
        }
    }
    public static void confirmClearYourCombatData(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        String title = utils.packStringId(new string_id("target_dummy", "data_cleared_confirm_title"));
        String prompt = utils.packStringId(new string_id("target_dummy", "data_cleared_confirm_prompt"));
        String handler = "confirmClearYourCombatData";
        buildConfirmClearCombatDataSui(targetDummy, player, title, prompt, handler);
    }
    public static void confirmClearAllCombatData(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        String title = utils.packStringId(new string_id("target_dummy", "data_all_cleared_confirm_title"));
        String prompt = utils.packStringId(new string_id("target_dummy", "data_all_cleared_confirm_prompt"));
        String handler = "confirmClearAllCombatData";
        buildConfirmClearCombatDataSui(targetDummy, player, title, prompt, handler);
    }
    public static void buildConfirmClearCombatDataSui(obj_id targetDummy, obj_id player, String title, String prompt, String handler) throws InterruptedException
    {
        String ok_button = utils.packStringId(new string_id("quest/ground/util/quest_giver_object", "button_accept"));
        String cancel_button = utils.packStringId(new string_id("quest/ground/util/quest_giver_object", "button_decline"));
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, targetDummy, player, handler);
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, ok_button);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, cancel_button);
        utils.setScriptVar(player, "clearCombatData.openSui", pid);
        sui.showSUIPage(pid);
    }
    public static void clearYourCombatData(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        obj_id controller = getTargetDummyController(targetDummy);
        if (isIdValid(controller) && utils.hasScriptVarTree(controller, BASE_TARGET_DUMMY_VAR + "." + player))
        {
            utils.removeScriptVarTree(controller, BASE_TARGET_DUMMY_VAR + "." + player);
            sendSystemMessage(player, new string_id("target_dummy", "combat_data_clearing"));
            sendSystemMessage(player, new string_id("target_dummy", "combat_data_cleared"));
            if (hasObjVar(controller, target_dummy.BASE_TARGET_DUMMY_VAR))
            {
                removeObjVar(controller, target_dummy.BASE_TARGET_DUMMY_VAR);
            }
        }
    }
    public static void clearAllCombatData(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        obj_id controller = getTargetDummyController(targetDummy);
        if (isIdValid(controller) && utils.hasScriptVarTree(controller, BASE_TARGET_DUMMY_VAR))
        {
            utils.removeScriptVarTree(controller, BASE_TARGET_DUMMY_VAR);
            sendSystemMessage(player, new string_id("target_dummy", "combat_data_all_clearing"));
            sendSystemMessage(player, new string_id("target_dummy", "combat_data_all_cleared"));
            if (hasObjVar(controller, target_dummy.BASE_TARGET_DUMMY_VAR))
            {
                removeObjVar(controller, target_dummy.BASE_TARGET_DUMMY_VAR);
            }
        }
    }
    public static void sendTargetDummyCombatData(obj_id attacker, obj_id defender, combat_data actionData, hit_result hitData) throws InterruptedException
    {
        if (utils.hasScriptVar(attacker, "testing_recordCombatData"))
        {
            recoredCombatResult(attacker, attacker, actionData, hitData);
        }
        if (isTargetDummy(defender))
        {
            recoredCombatResultViaController(attacker, defender, actionData, hitData);
        }
    }
    public static void recoredCombatResultViaController(obj_id attacker, obj_id targetDummy, combat_data actionData, hit_result hitData) throws InterruptedException
    {
        obj_id controller = getTargetDummyController(targetDummy);
        if (isIdValid(controller))
        {
            recoredCombatResult(attacker, controller, actionData, hitData);
        }
    }
    public static void recoredCombatResult(obj_id attacker, obj_id controller, combat_data actionData, hit_result hitData) throws InterruptedException
    {
        String attackUsed = actionData.actionName;
        boolean attackMissed = hitData.miss;
        boolean attackDodged = hitData.dodge;
        boolean attackParried = hitData.parry;
        int damageDone = hitData.damage;
        boolean criticalHit = hitData.critical;
        boolean strikethroughHit = hitData.strikethrough;
        boolean glancingHit = hitData.glancing;
        boolean evadedDamage = hitData.evadeResult;
        boolean blockedHit = hitData.blockResult;
        obj_id combatDataOwner = attacker;
        if (beast_lib.isBeast(attacker))
        {
            combatDataOwner = getMaster(attacker);
        }
        String baseAttackerVar = BASE_TARGET_DUMMY_VAR + "." + combatDataOwner;
        String baseAllAttacksVar = baseAttackerVar + "." + ALL_ATTACKS_SUB_VAR;
        String baseSpecificAttackVar = baseAttackerVar + "." + attackUsed;
        int[] allAttacksData = new int[ATTACK_DATA_ARRAY.length];
        if (utils.hasScriptVarTree(controller, baseAllAttacksVar))
        {
            allAttacksData = utils.getIntArrayScriptVar(controller, baseAllAttacksVar);
        }
        else 
        {
            for (int i = 0; i < allAttacksData.length; i++)
            {
                allAttacksData[i] = 0;
            }
        }
        int[] specificAttackData = new int[ATTACK_DATA_ARRAY.length];
        if (utils.hasScriptVarTree(controller, baseSpecificAttackVar))
        {
            specificAttackData = utils.getIntArrayScriptVar(controller, baseSpecificAttackVar);
        }
        else 
        {
            for (int i = 0; i < specificAttackData.length; i++)
            {
                specificAttackData[i] = 0;
            }
        }
        storeCumulativeCombatData(allAttacksData, INDEX_NUM_OF_ATTACKS, 1);
        storeCumulativeCombatData(specificAttackData, INDEX_NUM_OF_ATTACKS, 1);
        if (!hitData.success)
        {
            int indexOfAvoidanceType;
            if (attackMissed)
            {
                indexOfAvoidanceType = INDEX_NUM_MISSED;
            }
            else if (attackDodged)
            {
                indexOfAvoidanceType = INDEX_NUM_DODGED;
            }
            else if (attackParried)
            {
                indexOfAvoidanceType = INDEX_NUM_PARRIED;
            }
            else 
            {
                indexOfAvoidanceType = INDEX_NUM_MISSED;
            }
            storeCumulativeCombatData(allAttacksData, indexOfAvoidanceType, 1);
            storeCumulativeCombatData(specificAttackData, indexOfAvoidanceType, 1);
        }
        else 
        {
            storeCumulativeCombatData(allAttacksData, INDEX_TOTAL_DAMAGE_DONE, damageDone);
            storeComparisonCombatData(allAttacksData, damageDone);
            storeCumulativeCombatData(specificAttackData, INDEX_TOTAL_DAMAGE_DONE, damageDone);
            storeComparisonCombatData(specificAttackData, damageDone);
            if (criticalHit)
            {
                storeCumulativeCombatData(allAttacksData, INDEX_NUM_CRITAL_HITS, 1);
                storeCumulativeCombatData(specificAttackData, INDEX_NUM_CRITAL_HITS, 1);
            }
            if (strikethroughHit)
            {
                storeCumulativeCombatData(allAttacksData, INDEX_NUM_STRIKETHROUGH, 1);
                storeCumulativeCombatData(specificAttackData, INDEX_NUM_STRIKETHROUGH, 1);
            }
            if (glancingHit)
            {
                storeCumulativeCombatData(allAttacksData, INDEX_NUM_GLANCING_HITS, 1);
                storeCumulativeCombatData(specificAttackData, INDEX_NUM_GLANCING_HITS, 1);
            }
            if (evadedDamage)
            {
                storeCumulativeCombatData(allAttacksData, INDEX_NUM_EVADED_HITS, 1);
                storeCumulativeCombatData(specificAttackData, INDEX_NUM_EVADED_HITS, 1);
            }
            if (blockedHit)
            {
                storeCumulativeCombatData(allAttacksData, INDEX_NUM_BLOCKED_HITS, 1);
                storeCumulativeCombatData(specificAttackData, INDEX_NUM_BLOCKED_HITS, 1);
            }
            storeCumulativeCombatData(allAttacksData, INDEX_NUM_SUCCESSFUL_HITS, 1);
            storeCumulativeCombatData(specificAttackData, INDEX_NUM_SUCCESSFUL_HITS, 1);
            if (recordAsNormalHit(hitData))
            {
                storeCumulativeCombatData(allAttacksData, INDEX_NUM_NORMAL_HITS, 1);
                storeCumulativeCombatData(specificAttackData, INDEX_NUM_NORMAL_HITS, 1);
            }
        }
        utils.setScriptVar(controller, baseAllAttacksVar, allAttacksData);
        utils.setScriptVar(controller, baseSpecificAttackVar, specificAttackData);
        utils.setScriptVar(controller, baseAttackerVar + "." + LAST_ATTACK_TIME_VAR, getGameTime());
    }
    public static void storeCumulativeCombatData(int[] dataArray, int index, int newData) throws InterruptedException
    {
        dataArray[index] = dataArray[index] + newData;
    }
    public static void storeComparisonCombatData(int[] dataArray, int newData) throws InterruptedException
    {
        int previousLargest = dataArray[INDEX_LARGEST_ATTACK];
        if (newData >= previousLargest)
        {
            dataArray[INDEX_LARGEST_ATTACK] = newData;
        }
        int previousSmallest = newData;
        if (dataArray[INDEX_SMALLEST_ATTACK] > 0)
        {
            previousSmallest = dataArray[INDEX_SMALLEST_ATTACK];
        }
        if (newData <= previousSmallest)
        {
            dataArray[INDEX_SMALLEST_ATTACK] = newData;
        }
    }
    public static boolean recordAsNormalHit(hit_result hitData) throws InterruptedException
    {
        return !hitData.critical && !hitData.strikethrough;
    }
    public static void reportCombatData(obj_id player, obj_id targetDummy) throws InterruptedException
    {
        reportCombatDataViaController(player, targetDummy, obj_id.NULL_ID);
    }
    public static void reportCombatDataViaController(obj_id player, obj_id targetDummy, obj_id controller) throws InterruptedException
    {
        obj_id reportDataObject = targetDummy;
        if (isIdValid(controller))
        {
            reportDataObject = controller;
        }
        if (hasObjVar(reportDataObject, target_dummy.BASE_TARGET_DUMMY_VAR))
        {
            removeObjVar(reportDataObject, target_dummy.BASE_TARGET_DUMMY_VAR);
        }
        String baseAttackerVar = BASE_TARGET_DUMMY_VAR + "." + player;
        if (utils.hasScriptVarTree(reportDataObject, baseAttackerVar))
        {
            String report = getCombatDataReportString(reportDataObject, player);
            if (utils.hasScriptVar(player, "target_dummy.openCombatReportSui"))
            {
                int oldPid = utils.getIntScriptVar(player, "target_dummy.openCombatReportSui");
                forceCloseSUIPage(oldPid);
            }
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, targetDummy, player, "handleTargetDummyCombatReport");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_title")));
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, report);
            sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_refreshbutton")));
            setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_clear_button")));
            setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_closebutton")));
            setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
            subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
            utils.setScriptVar(player, "target_dummy.openCombatReportSui", pid);
            sui.showSUIPage(pid);
        }
        else 
        {
            sendSystemMessage(player, new string_id("target_dummy", "placement_no_combat_data"));
        }
    }
    public static String getCombatDataReportString(obj_id reportDataObject, obj_id player) throws InterruptedException
    {
        String report;
        String baseAttackerVar = BASE_TARGET_DUMMY_VAR + "." + player;
        if (utils.hasScriptVarTree(reportDataObject, baseAttackerVar))
        {
            deltadictionary vars = reportDataObject.getScriptVars();
            Vector attackTypes = new Vector();
            attackTypes.setSize(0);
            Enumeration keys = vars.keys();
            String key;
            String[] data;
            String attackType;
            while (keys.hasMoreElements())
            {
                key = (String) (keys.nextElement());
                if (key.startsWith(baseAttackerVar + "."))
                {
                    data = split(key, '.');
                    attackType = data[2];
                    if (!attackType.equals(LAST_ATTACK_TIME_VAR))
                    {
                        utils.addElement(attackTypes, attackType);
                    }
                }
            }
            int numItems = attackTypes.size();
            String[] initialAttackTypes = new String[numItems - 1];
            int[] initialDamageDoneArray = new int[numItems - 1];
            String[] sortedAttackTypes = new String[numItems];
            int initialIdx = 0;
            String attackName;
            String baseSpecificAttackVar;
            for (Object attackType1 : attackTypes) {
                attackName = ((String) attackType1);
                if (attackName.equals(ALL_ATTACKS_SUB_VAR)) {
                    sortedAttackTypes[0] = attackName;
                } else {
                    initialAttackTypes[initialIdx] = attackName;
                    baseSpecificAttackVar = baseAttackerVar + "." + attackName;
                    int[] tempAttackData = utils.getIntArrayScriptVar(reportDataObject, baseSpecificAttackVar);
                    initialDamageDoneArray[initialIdx] = tempAttackData[INDEX_TOTAL_DAMAGE_DONE];
                    initialIdx = initialIdx + 1;
                }
            }
            int[] ascendingDataArray = new int[initialDamageDoneArray.length];
            utils.copyArray(initialDamageDoneArray, ascendingDataArray);
            Arrays.sort(ascendingDataArray);
            String name;
            for (int j = 0; j < ascendingDataArray.length; j++)
            {
                int index = utils.getElementPositionInArray(initialDamageDoneArray, ascendingDataArray[j]);
                name = initialAttackTypes[index];
                sortedAttackTypes[(ascendingDataArray.length) - j] = name;
                initialDamageDoneArray[index] = -1;
            }
            report = utils.packStringId(REPORT_TITLE_SID) + addLineBreaks(1);
            String attackTypeVarName;
            for (String attackTypeName : sortedAttackTypes) {
                attackTypeVarName = baseAttackerVar + "." + attackTypeName;
                int[] attackData = utils.getIntArrayScriptVar(reportDataObject, attackTypeVarName);
                int numAttacks = attackData[INDEX_NUM_OF_ATTACKS];
                int totalDamageDone = attackData[INDEX_TOTAL_DAMAGE_DONE];
                int largestAttack = attackData[INDEX_LARGEST_ATTACK];
                int smallestAttack = attackData[INDEX_SMALLEST_ATTACK];
                int numCriticalHits = attackData[INDEX_NUM_CRITAL_HITS];
                int numStrikethroughHit = attackData[INDEX_NUM_STRIKETHROUGH];
                int numMissed = attackData[INDEX_NUM_MISSED];
                int numDodged = attackData[INDEX_NUM_DODGED];
                int numParried = attackData[INDEX_NUM_PARRIED];
                int numGlancingHit = attackData[INDEX_NUM_GLANCING_HITS];
                int numEvadedDamage = attackData[INDEX_NUM_EVADED_HITS];
                int numBlockedHit = attackData[INDEX_NUM_BLOCKED_HITS];
                int numSuccessfulAttacks = attackData[INDEX_NUM_SUCCESSFUL_HITS];
                int averageDamage = totalDamageDone / numAttacks;
                float percentageMissed = getPercentage(numMissed, numAttacks);
                float percentageDodged = getPercentage(numDodged, numAttacks);
                float percentageParried = getPercentage(numParried, numAttacks);
                float percentageCrit = getPercentage(numCriticalHits, numAttacks);
                float percentageStrikethrough = getPercentage(numStrikethroughHit, numAttacks);
                float percentageGlancing = getPercentage(numGlancingHit, numAttacks);
                float percentageEvaded = getPercentage(numEvadedDamage, numAttacks);
                float percentageBlocked = getPercentage(numBlockedHit, numAttacks);
                report += YELLOW + utils.packStringId(REPORT_ATTACK_TYPE_SID);
                report += WHITE + utils.packStringId(new string_id("cmd_n", attackTypeName)) + addLineBreaks(1);
                report += GREEN + utils.packStringId(REPORT_NUM_ATTACKS_SID) + WHITE + numAttacks + addLineBreaks(1);
                report += GREEN + utils.packStringId(REPORT_TOTAL_DAMAGE_SID) + WHITE + totalDamageDone + addLineBreaks(1);
                report += GREEN + utils.packStringId(REPORT_AVERAGE_DAMAGE_SID) + WHITE + averageDamage + addLineBreaks(1);
                report += GREEN + utils.packStringId(REPORT_SMALLEST_ATTACK_SID) + WHITE + smallestAttack + addLineBreaks(1);
                report += GREEN + utils.packStringId(REPORT_LARGEST_ATTACK_SID) + WHITE + largestAttack + addLineBreaks(2);
                report += BLUE + utils.packStringId(REPORT_NUM_SUCCESSFUL_SID) + WHITE + numSuccessfulAttacks + addLineBreaks(1);
                report += BLUE + utils.packStringId(REPORT_NUM_CRITS_SID) + WHITE + numCriticalHits + "   ( " + percentageCrit + percentSign() + addLineBreaks(1);
                report += BLUE + utils.packStringId(REPORT_NUM_STRIKETHROUGH_SID) + WHITE + numStrikethroughHit + "   ( " + percentageStrikethrough + percentSign() + addLineBreaks(2);
                report += ORANGE + utils.packStringId(REPORT_NUM_GLANCING_SID) + WHITE + numGlancingHit + "   ( " + percentageGlancing + percentSign() + addLineBreaks(1);
                report += ORANGE + utils.packStringId(REPORT_NUM_EVADES_SID) + WHITE + numEvadedDamage + "   ( " + percentageEvaded + percentSign() + addLineBreaks(1);
                report += ORANGE + utils.packStringId(REPORT_NUM_BLOCKED_SID) + WHITE + numBlockedHit + "   ( " + percentageBlocked + percentSign() + addLineBreaks(2);
                report += RED + utils.packStringId(REPORT_NUM_MISSES_SID) + WHITE + numMissed + "   ( " + percentageMissed + percentSign() + addLineBreaks(1);
                report += RED + utils.packStringId(REPORT_NUM_DODGES_SID) + WHITE + numDodged + "   ( " + percentageDodged + percentSign() + addLineBreaks(1);
                report += RED + utils.packStringId(REPORT_NUM_PARRIES_SID) + WHITE + numParried + "   ( " + percentageParried + percentSign() + addLineBreaks(1);
                report += WHITE + utils.packStringId(REPORT_LINE_BREAK_SID) + addLineBreaks(2);
            }
        }
        else 
        {
            report = utils.packStringId(new string_id("target_dummy", "placement_no_combat_data"));
        }
        return report;
    }
    public static void showRawCombatDataViaController(obj_id player, obj_id targetDummy) throws InterruptedException
    {
        if (isGod(player))
        {
            obj_id controller = target_dummy.getTargetDummyController(targetDummy);
            if (isIdValid(controller))
            {
                showRawCombatData(player, controller);
            }
        }
    }
    public static void showRawCombatData(obj_id player, obj_id controller) throws InterruptedException
    {
        if (isGod(player))
        {
            String report;
            String baseAttackerVar = BASE_TARGET_DUMMY_VAR + "." + player;
            if (utils.hasScriptVarTree(controller, baseAttackerVar))
            {
                deltadictionary vars = controller.getScriptVars();
                Vector attackTypes = new Vector();
                attackTypes.setSize(0);
                Enumeration keys = vars.keys();
                String key;
                String[] data;
                String attackType;
                while (keys.hasMoreElements())
                {
                    key = (String) (keys.nextElement());
                    if (key.startsWith(baseAttackerVar + "."))
                    {
                        data = split(key, '.');
                        attackType = data[2];
                        if (!attackType.equals(LAST_ATTACK_TIME_VAR))
                        {
                            utils.addElement(attackTypes, attackType);
                        }
                    }
                }
                report = utils.packStringId(REPORT_TITLE_SID) + addLineBreaks(1);
                String attackTypeName;
                String attackTypeVarName;

                for (Object attackType1 : attackTypes) {
                    attackTypeName = ((String) attackType1);
                    attackTypeVarName = baseAttackerVar + "." + attackTypeName;
                    int[] attackData = utils.getIntArrayScriptVar(controller, attackTypeVarName);
                    report += attackTypeName + ": { ";
                    for (int j = 0; j < attackData.length; j++) {
                        report += attackData[j];
                        if (j == attackData.length - 1) {
                            report += " }" + sui.newLine();
                        } else {
                            report += ", ";
                        }
                    }
                }
            }
            else 
            {
                report = utils.packStringId(new string_id("target_dummy", "placement_no_combat_data"));
            }
            if (utils.hasScriptVar(player, "target_dummy.rawCombatReportSui"))
            {
                int oldPid = utils.getIntScriptVar(player, "target_dummy.rawCombatReportSui");
                forceCloseSUIPage(oldPid);
            }
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, controller, player, "noRawCombatDataTargetDummyHandler");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_title")));
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, report);
            sui.msgboxButtonSetup(pid, sui.YES_NO);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_refreshbutton")));
            setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, utils.packStringId(new string_id("target_dummy", "report_closebutton")));
            utils.setScriptVar(player, "target_dummy.rawCombatReportSui", pid);
            sui.showSUIPage(pid);
        }
    }
    public static float getPercentage(float num, float total) throws InterruptedException
    {
        float answer = num * 10000 / total;
        int truncated = (int)answer;
        return (float)truncated / 100;
    }
    public static void removeCombatAi(obj_id targetDummy) throws InterruptedException
    {
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
        if (hasScript(targetDummy, "ai.creature_combat"))
        {
            detachScript(targetDummy, "ai.creature_combat");
        }
    }
    public static void freezeMob(obj_id targetDummy) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(targetDummy, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.aiStopFollowing(targetDummy);
    }
    public static void setFullHealth(obj_id targetDummy) throws InterruptedException
    {
        int maxHealth = getUnmodifiedMaxAttrib(targetDummy, HEALTH);
        setAttrib(targetDummy, HEALTH, maxHealth);
    }
    public static void endTargetDummyCombat(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(targetDummy) || ai_lib.isInCombat(player))
        {
            obj_id[] enemies = getHateList(targetDummy);
            setCombatTarget(targetDummy, obj_id.NULL_ID);
            stopCombat(targetDummy);
            ai_lib.clearCombatData();
            utils.removeScriptVarTree(targetDummy, "ai.combat");
            setFullHealth(targetDummy);
            removeAttackerFromCombat(targetDummy, player);
            if (enemies != null && enemies.length > 0)
            {
                for (obj_id enemy : enemies) {
                    if (isIdValid(enemy) && enemy != player) {
                        removeAttackerFromCombat(targetDummy, enemy);
                    }
                }
            }
            location where = getLocation(targetDummy);
            if (where == null)
            {
                return;
            }
            obj_id controller = getObjIdObjVar(targetDummy, TARGET_DUMMY_CONTROLLER_OBJVAR);
            if (!isValidId(controller))
            {
                return;
            }
            if (isValidId(createTargetDummyAtLocation(controller, player, where)))
            {
                destroyObject(targetDummy);
            }
        }
    }
    public static void removeAttackerFromCombat(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        removeHateTarget(targetDummy, player);
        removeHateTarget(player, targetDummy);
        stopCombat(player);
    }
    public static void enterIntoCombat(obj_id targetDummy, obj_id player) throws InterruptedException
    {
        startCombat(targetDummy, player);
        addHate(targetDummy, player, 1000.0f);
    }
    public static final String DATA = "datatables/tcg/target_dummy_extras.iff";
    public static final String TYPE_COLUMN = "target_dummy_type";
    public static final String RANDON_ANIM_COLUMN = "random_anims";
    public static final String TRICK_ANIM_COLUMN = "trick_anims";
    public static final String TARGET_DUMMY_LAST_EXTRAS_ANIM = "targetDummyLastAnim";
    public static final String[] EXTRA_TYPES = 
    {
        RANDON_ANIM_COLUMN,
        TRICK_ANIM_COLUMN
    };
    public static boolean hasTargetDummyExtras(obj_id targetDummy) throws InterruptedException
    {
        String testExtra;
        for (String EXTRA_TYPE : EXTRA_TYPES) {
            testExtra = getTargetDummyAnim(targetDummy, EXTRA_TYPE);
            if (testExtra != null && testExtra.length() > 0) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasTargetDummyRandomAnim(obj_id targetDummy) throws InterruptedException
    {
        String testAnim = getTargetDummyAnim(targetDummy, RANDON_ANIM_COLUMN);
        return testAnim != null && testAnim.length() > 0;
    }
    public static boolean hasTargetDummyTrickAnim(obj_id targetDummy) throws InterruptedException
    {
        String testTrick = getTargetDummyAnim(targetDummy, TRICK_ANIM_COLUMN);
        return testTrick != null && testTrick.length() > 0;
    }
    public static void doTargetDummyRandomAnim(obj_id targetDummy) throws InterruptedException
    {
        String animName = getTargetDummyAnim(targetDummy, RANDON_ANIM_COLUMN);
        doTargetDummyAnimation(targetDummy, animName);
    }
    public static void doTargetDummyTrick(obj_id targetDummy) throws InterruptedException
    {
        String trickName = getTargetDummyAnim(targetDummy, TRICK_ANIM_COLUMN);
        doTargetDummyAnimation(targetDummy, trickName);
    }
    public static String getTargetDummyAnim(obj_id targetDummy, String column) throws InterruptedException
    {
        String anims = getTargetDummyExtrasData(targetDummy, column);
        if (anims != null && anims.length() > 0)
        {
            String[] animsList = split(anims, ',');
            if (animsList != null && animsList.length > 0)
            {
                int randAnim = rand(0, animsList.length - 1);
                return animsList[randAnim];
            }
        }
        return "";
    }
    public static void doTargetDummyAnimation(obj_id targetDummy, String animName) throws InterruptedException
    {
        if (animName != null && animName.length() > 0)
        {
            doAnimationAction(targetDummy, animName);
            setObjVar(targetDummy, TARGET_DUMMY_LAST_EXTRAS_ANIM, animName);
        }
    }
    public static String getTargetDummyExtrasData(obj_id targetDummy, String column) throws InterruptedException
    {
        String result = "";
        String type = getCreatureName(targetDummy);
        if (type != null && type.length() > 0)
        {
            dictionary targetDummyExtras = dataTableGetRow(DATA, type);
            if (targetDummyExtras != null && !targetDummyExtras.isEmpty())
            {
                result = targetDummyExtras.getString(column);
            }
        }
        return result;
    }
    public static boolean blog(String msg) throws InterruptedException
    {
        if (msg == null || msg.equals(""))
        {
            return false;
        }
        if (LOGGING_ON)
        {
            LOG(TERMINAL_LOGGING, msg);
        }
        return true;
    }
}

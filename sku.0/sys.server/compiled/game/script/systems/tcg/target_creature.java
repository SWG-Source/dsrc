package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.anims;
import script.library.ai_lib;
import script.library.armor;
import script.library.buff;
import script.library.create;
import script.library.combat;
import script.library.prose;
import script.library.static_item;
import script.library.sui;
import script.library.target_dummy;
import script.library.utils;

public class target_creature extends script.base_script
{
    public target_creature()
    {
    }
    public static final String TERMINAL_LOGGING = "target_creature";
    public static final boolean LOGGING_ON = false;
    public static final int OPTIONS_REPORT_COMBAT_DATA = menu_info_types.SERVER_MENU1;
    public static final int OPTIONS_ROOT_MENU = menu_info_types.SERVER_MENU2;
    public static final int OPTIONS_SET_LEVEL_MENU = menu_info_types.SERVER_MENU3;
    public static final int OPTIONS_START_STOP_COMBAT = menu_info_types.SERVER_MENU4;
    public static final int OPTIONS_SET_DIFFICULTY = menu_info_types.SERVER_MENU5;
    public static final int OPTIONS_SET_DEFENSE = menu_info_types.SERVER_MENU6;
    public static final int OPTIONS_CLEANUP = menu_info_types.SERVER_MENU7;
    public static final int OPTIONS_CLEAR_YOUR_COMBAT_DATA = menu_info_types.SERVER_MENU8;
    public static final int OPTIONS_CLEAR_ALL_COMBAT_DATA = menu_info_types.SERVER_MENU9;
    public static final int OPTIONS_SHOW_RAW_COMBAT_DATA = menu_info_types.SERVER_MENU10;
    public static final int OPTIONS_EXTRAS = menu_info_types.SERVER_MENU11;
    public static final int OPTIONS_EXTRAS_RANDOM = menu_info_types.SERVER_MENU12;
    public static final int OPTIONS_EXTRAS_TRICK = menu_info_types.SERVER_MENU13;
    public static final int OPTIONS_ANIMATIONS = menu_info_types.SERVER_MENU14;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleTargetCreatureSetUp", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleTargetCreatureSetUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = target_dummy.getTargetDummyController(self);
        if (isIdValid(controller))
        {
            int combatLevel = 50;
            if (hasObjVar(self, target_dummy.OWNER_OBJVAR))
            {
                obj_id owner = getObjIdObjVar(self, target_dummy.OWNER_OBJVAR);
                if (isIdValid(owner) && exists(owner))
                {
                    combatLevel = getLevel(owner);
                }
            }
            if (hasObjVar(controller, "intCombatDifficulty"))
            {
                combatLevel = target_dummy.getTargetDummyCombatLevel(controller);
            }
            target_dummy.freezeMob(self);
            target_dummy.removeCombatAi(self);
            int difficulty = target_dummy.getTargetDummyDifficulty(controller);
            target_dummy.initializeTargetDummy(self, combatLevel, difficulty);
            for (int i = 0; i < target_dummy.TARGET_DUMMY_DEFENSES.length; i++)
            {
                String skillModName = target_dummy.TARGET_DUMMY_DEFENSES[i];
                if (hasObjVar(controller, "target_dummy_defense." + skillModName))
                {
                    int value = getIntObjVar(controller, "target_dummy_defense." + skillModName);
                    target_dummy.setTargetDummyDefensiveValue(self, obj_id.NULL_ID, value, skillModName);
                }
            }
        }
        else 
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (!isInvulnerable(self))
        {
            int menuStartStopCombat = mi.addRootMenu(OPTIONS_START_STOP_COMBAT, new string_id("target_dummy", "menu_stop_combat"));
        }
        if (hasObjVar(self, target_dummy.OWNER_OBJVAR))
        {
            obj_id owner = getObjIdObjVar(self, target_dummy.OWNER_OBJVAR);
            if (isIdValid(owner))
            {
                if (player == owner && !isInvulnerable(self))
                {
                    int menuNpcOptions = mi.addRootMenu(OPTIONS_ROOT_MENU, new string_id("target_dummy", "menu_options_root"));
                    mi.addSubMenu(menuNpcOptions, OPTIONS_SET_LEVEL_MENU, new string_id("target_dummy", "menu_set_combat_level"));
                    mi.addSubMenu(menuNpcOptions, OPTIONS_SET_DIFFICULTY, new string_id("target_dummy", "menu_set_combat_difficulty"));
                    mi.addSubMenu(menuNpcOptions, OPTIONS_SET_DEFENSE, new string_id("target_dummy", "menu_set_defense"));
                    if ((getStringObjVar(self, "targetDummyCreatureName")).equals("tcg_target_dummy"))
                    {
                        if (hasObjVar(self, target_dummy.TARGET_DUMMY_NO_SOUND))
                        {
                            mi.addSubMenu(menuNpcOptions, OPTIONS_ANIMATIONS, new string_id("target_dummy", "menu_turnon_target_dummy_anims"));
                        }
                        else 
                        {
                            mi.addSubMenu(menuNpcOptions, OPTIONS_ANIMATIONS, new string_id("target_dummy", "menu_turnoff_target_dummy_anims"));
                        }
                        mi.addSubMenu(menuNpcOptions, OPTIONS_CLEANUP, new string_id("target_dummy", "menu_cleanup_target_dummy"));
                    }
                    else if ((getStringObjVar(self, "targetDummyCreatureName")).equals("tcg_target_creature_acklay"))
                    {
                        mi.addSubMenu(menuNpcOptions, OPTIONS_CLEANUP, new string_id("target_dummy", "menu_cleanup_target_creature"));
                    }
                    else 
                    {
                        mi.addSubMenu(menuNpcOptions, OPTIONS_CLEANUP, new string_id("target_dummy", "menu_cleanup_target_object"));
                    }
                }
                if (player == owner)
                {
                    if (target_dummy.hasTargetDummyExtras(self))
                    {
                        int menuExtras = mi.addRootMenu(OPTIONS_EXTRAS, new string_id("target_dummy", "menu_extras"));
                        if (target_dummy.hasTargetDummyTrickAnim(self))
                        {
                            mi.addSubMenu(menuExtras, OPTIONS_EXTRAS_TRICK, new string_id("target_dummy", "menu_extras_trick"));
                        }
                        if (target_dummy.hasTargetDummyRandomAnim(self))
                        {
                            mi.addSubMenu(menuExtras, OPTIONS_EXTRAS_RANDOM, new string_id("target_dummy", "menu_extras_random"));
                        }
                    }
                }
                int menuReportCombatData = mi.addRootMenu(OPTIONS_REPORT_COMBAT_DATA, new string_id("target_dummy", "menu_report_combat_data"));
                mi.addSubMenu(menuReportCombatData, OPTIONS_CLEAR_YOUR_COMBAT_DATA, new string_id("target_dummy", "menu_clear_combat_data"));
                if (player == owner)
                {
                    mi.addSubMenu(menuReportCombatData, OPTIONS_CLEAR_ALL_COMBAT_DATA, new string_id("target_dummy", "menu_clear_all_combat_data"));
                }
                if (isGod(player))
                {
                    mi.addSubMenu(menuReportCombatData, OPTIONS_SHOW_RAW_COMBAT_DATA, new string_id("target_dummy", "menu_show_raw_combat_data"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int menu) throws InterruptedException
    {
        if (menu == OPTIONS_REPORT_COMBAT_DATA)
        {
            obj_id controller = target_dummy.getTargetDummyController(self);
            if (isIdValid(controller))
            {
                target_dummy.reportCombatDataViaController(player, self, controller);
            }
        }
        else if (menu == OPTIONS_ANIMATIONS)
        {
            if (!hasObjVar(self, target_dummy.TARGET_DUMMY_NO_SOUND))
            {
                setObjVar(self, target_dummy.TARGET_DUMMY_NO_SOUND, true);
                sendSystemMessage(player, target_dummy.SID_SOUND_ANIMS_OFF);
            }
            else 
            {
                removeObjVar(self, target_dummy.TARGET_DUMMY_NO_SOUND);
                sendSystemMessage(player, target_dummy.SID_SOUND_ANIMS_ON);
            }
        }
        else if (menu == OPTIONS_CLEAR_YOUR_COMBAT_DATA)
        {
            target_dummy.confirmClearYourCombatData(self, player);
        }
        else if (menu == OPTIONS_START_STOP_COMBAT)
        {
            target_dummy.endTargetDummyCombat(self, player);
        }
        else if (menu == OPTIONS_SHOW_RAW_COMBAT_DATA)
        {
            if (isGod(player))
            {
                target_dummy.showRawCombatDataViaController(player, self);
            }
        }
        else if (hasObjVar(self, target_dummy.OWNER_OBJVAR))
        {
            obj_id owner = getObjIdObjVar(self, target_dummy.OWNER_OBJVAR);
            if (isIdValid(owner) && player == owner)
            {
                if (menu == OPTIONS_ROOT_MENU)
                {
                    sendSystemMessage(player, new string_id("target_dummy", "menu_options_prompt"));
                }
                else if (menu == OPTIONS_SET_LEVEL_MENU)
                {
                    target_dummy.promptForCombatLevel(self, player);
                }
                else if (menu == OPTIONS_SET_DIFFICULTY)
                {
                    target_dummy.promptForCombatDifficulty(self, player);
                }
                else if (menu == OPTIONS_SET_DEFENSE)
                {
                    target_dummy.showTargetDummyDefenseListSui(self, player);
                }
                else if (menu == OPTIONS_CLEANUP)
                {
                    obj_id controller = target_dummy.getTargetDummyController(self);
                    if (isIdValid(controller))
                    {
                        target_dummy.cleanupTargetDummy(controller, self);
                    }
                }
                else if (menu == OPTIONS_CLEAR_ALL_COMBAT_DATA)
                {
                    target_dummy.confirmClearAllCombatData(self, player);
                }
                else if (menu == OPTIONS_EXTRAS)
                {
                    if (hasObjVar(self, target_dummy.TARGET_DUMMY_LAST_EXTRAS_ANIM))
                    {
                        String animName = getStringObjVar(self, target_dummy.TARGET_DUMMY_LAST_EXTRAS_ANIM);
                        target_dummy.doTargetDummyAnimation(self, animName);
                    }
                }
                else if (menu == OPTIONS_EXTRAS_RANDOM)
                {
                    target_dummy.doTargetDummyRandomAnim(self);
                }
                else if (menu == OPTIONS_EXTRAS_TRICK)
                {
                    target_dummy.doTargetDummyTrick(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        blog("OnDestroy - creature reports being destroyed");
        target_dummy.removeTargetDummyFromPermissions(self);
        return SCRIPT_CONTINUE;
    }
    public int handleTargetDummyCombatReport(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player))
        {
            int button = sui.getIntButtonPressed(params);
            String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
            if (button == sui.BP_CANCEL)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id controller = target_dummy.getTargetDummyController(self);
                if (isIdValid(controller))
                {
                    if (revert != null && !revert.equals(""))
                    {
                        if (utils.hasScriptVarTree(controller, target_dummy.BASE_TARGET_DUMMY_VAR + "." + player))
                        {
                            target_dummy.confirmClearYourCombatData(self, player);
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("target_dummy", "placement_no_combat_data"));
                        }
                    }
                    else 
                    {
                        target_dummy.reportCombatDataViaController(player, self, controller);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int confirmClearYourCombatData(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player) && bp == sui.BP_OK)
        {
            obj_id controller = target_dummy.getTargetDummyController(self);
            if (isIdValid(controller))
            {
                if (utils.hasScriptVarTree(controller, target_dummy.BASE_TARGET_DUMMY_VAR + "." + player))
                {
                    target_dummy.clearYourCombatData(self, player);
                }
                else 
                {
                    sendSystemMessage(player, new string_id("target_dummy", "placement_no_combat_data"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int confirmClearAllCombatData(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player) && bp == sui.BP_OK)
        {
            obj_id controller = target_dummy.getTargetDummyController(self);
            if (isIdValid(controller))
            {
                if (utils.hasScriptVarTree(controller, target_dummy.BASE_TARGET_DUMMY_VAR))
                {
                    target_dummy.clearAllCombatData(self, player);
                }
                else 
                {
                    sendSystemMessage(player, new string_id("target_dummy", "placement_no_combat_data"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTargetDummyLevelSelect(obj_id self, dictionary params) throws InterruptedException
    {
        blog("handleTargetDummyLevelSelect - init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String text = sui.getInputBoxText(params);
        int level = utils.stringToInt(text);
        if (level < 1 || level > 100)
        {
            sendSystemMessage(player, new string_id("target_dummy", "combat_level_invalid"));
        }
        else 
        {
            blog("handleTargetDummyLevelSelect - setting");
            int difficulty = target_dummy.getTargetDummyDifficulty(self);
            if (target_dummy.initializeTargetDummy(self, level, difficulty))
            {
                sendSystemMessage(player, new string_id("target_dummy", "combat_level_set"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTargetDummyDifficultySelect(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int difficulty_selected = sui.getListboxSelectedRow(params);
        int combatLevel = getLevel(self);
        if (target_dummy.initializeTargetDummy(self, combatLevel, difficulty_selected))
        {
            sendSystemMessage(player, new string_id("target_dummy", "combat_difficulty_set"));
        }
        return SCRIPT_CONTINUE;
    }
    public int targetDummyDefenseTypeSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int skill_mod_selected = sui.getListboxSelectedRow(params);
        target_dummy.promptForDefensiveValue(self, player, skill_mod_selected);
        return SCRIPT_CONTINUE;
    }
    public int handleTargetDummyDefensiveSkillModSet(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String text = sui.getInputBoxText(params);
        int value = utils.stringToInt(text);
        target_dummy.setTargetDummyDefensiveValue(self, player, value);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id hateTarget = getHateTarget(self);
        messageTo(self, "handleTargetDummyCombatTick", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTargetDummyCombatTick(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] enemies = getHateList(self);
        if (enemies != null && enemies.length > 0)
        {
            messageTo(self, "handleTargetDummyCombatTick", null, 3, false);
            for (int i = 0; i < enemies.length; i++)
            {
                obj_id enemy = enemies[i];
                if (isIdValid(enemy))
                {
                    location there = getLocation(enemy);
                    location here = getLocation(self);
                    if (!isValidLocation(there) || !isValidLocation(here) || there.cell != here.cell || utils.getDistance2D(here, there) > 60)
                    {
                        target_dummy.removeAttackerFromCombat(self, enemy);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetChanged(obj_id self, obj_id target) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetRemoved(obj_id self, obj_id target) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        blog("OnCreatureDamaged - creature dmg");
        faceTo(self, attacker);
        if ((getStringObjVar(self, "targetDummyCreatureName")).startsWith("tcg_target_dummy") && !hasObjVar(self, target_dummy.TARGET_DUMMY_NO_SOUND) && !utils.hasScriptVar(self, target_dummy.TARGET_DUMMY_SOUND_DELAY))
        {
            playTargetDummySound(self, attacker);
        }
        int currentHealth = getAttrib(self, HEALTH);
        for (int i = 0; i < damage.length; i++)
        {
            currentHealth = currentHealth - damage[i];
        }
        blog("OnCreatureDamaged - currentHealth: " + currentHealth);
        if (currentHealth <= 0)
        {
            combat.sendCombatSpamMessage(attacker, target_dummy.SID_TARGET_DUMMY_DIED_RESSURECTION);
            setHealth(self, getMaxAttrib(self, HEALTH));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int faceThePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, target_dummy.OWNER_OBJVAR);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(self, owner);
        messageTo(self, "waveThePlayer", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int waveThePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, target_dummy.OWNER_OBJVAR);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        doAnimationAction(self, anims.PLAYER_SALUTE1);
        playClientEffectObj(self, "sound/dro_fs_biped.snd", owner, "");
        return SCRIPT_CONTINUE;
    }
    public int removeSoundDelay(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, target_dummy.TARGET_DUMMY_SOUND_DELAY);
        return SCRIPT_CONTINUE;
    }
    public int checkCurrentLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isInWorldCell(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = target_dummy.getTargetDummyController(self);
        if (!isIdValid(controller))
        {
            return SCRIPT_CONTINUE;
        }
        target_dummy.cleanupTargetDummy(controller, self);
        return SCRIPT_CONTINUE;
    }
    public boolean playTargetDummySound(obj_id dummy, obj_id attacker) throws InterruptedException
    {
        if (!isValidId(dummy) || !isValidId(attacker))
        {
            return false;
        }
        if (utils.hasScriptVar(dummy, target_dummy.TARGET_DUMMY_SOUND_DELAY))
        {
            return false;
        }
        if (rand(1, 100) > 50)
        {
            return false;
        }
        int randomAnimation = rand(0, (target_dummy.TARGET_DUMMY_ANIMS.length - 1));
        if (randomAnimation <= 0)
        {
            return false;
        }
        String anim = target_dummy.TARGET_DUMMY_ANIMS[randomAnimation];
        if (anim == null || anim.length() <= 0)
        {
            return false;
        }
        doAnimationAction(dummy, anim);
        utils.setScriptVar(dummy, target_dummy.TARGET_DUMMY_SOUND_DELAY, true);
        playClientEffectObj(dummy, "sound/dro_target_dummy_hit.snd", attacker, "");
        messageTo(dummy, "removeSoundDelay", null, rand(3, 10), false);
        return true;
    }
    public boolean blog(String msg) throws InterruptedException
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

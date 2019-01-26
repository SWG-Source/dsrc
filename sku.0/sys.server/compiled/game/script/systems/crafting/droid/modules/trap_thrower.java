package script.systems.crafting.droid.modules;

import script.*;
import script.library.*;

public class trap_thrower extends script.base_script
{
    public trap_thrower()
    {
    }
    public static final String STF = "pet/droid_modules";
    public static final String MAX_TRAP_LOAD = "max_trap_load";
    public static final String RECENT_TRAP_THROWN = "thrown.recent_trap";
    public static final String THROW_DELAY_END = "thrown.delay_end";
    public static final int TRAP_DELAY = 4;
    public static final float MAX_SHOOTING_DISTANCE = 64.0f;
    public static final String DROID_TRAP = "droid_trap";
    public static final String TRAP_NUM = DROID_TRAP + ".trap_num";
    public static final String TEMPLATE = TRAP_NUM + ".trap_template";
    public static final String NAME = TRAP_NUM + ".trap_name";
    public static final String CHARGES = TRAP_NUM + ".charges";
    public static final String DIFF = TRAP_NUM + ".difficulty";
    public static final String AE = TRAP_NUM + ".area_effect";
    public static final String SCRIPT = TRAP_NUM + ".script";
    public static final string_id SID_THROWTRAP_OPTIONS = new string_id(STF, "throw_trap_options");
    public static final string_id SID_TRAIN_THROW_ONE = new string_id(STF, "train_throw_one");
    public static final string_id SID_TRAIN_THROW_TWO = new string_id(STF, "train_throw_two");
    public static final string_id SID_INITIALIZE_TRAPS = new string_id(STF, "initialize_traps");
    public static final string_id SID_RETRIEVE_TRAP = new string_id(STF, "retrieve_trap");
    public static final string_id SID_CLEAR_TRAP = new string_id(STF, "clear_trap");
    public static final string_id SID_ALREADY_LOADED = new string_id(STF, "already_loaded");
    public static final string_id SID_INSUFFICIENT_SKILL = new string_id(STF, "insufficient_skill");
    public static final string_id SID_NO_TRAP_LOADED = new string_id(STF, "no_trap_loaded");
    public static final string_id SID_TARGET_TOO_FAR = new string_id(STF, "target_too_far");
    public static final string_id SID_TRAP_MATRIX_CLEARED = new string_id(STF, "trap_matrix_cleared");
    public static final string_id SID_TRAP_MODULE_INITIALIZE = new string_id(STF, "trap_module_initialize");
    public static final string_id SID_TRAP_MATRIX_FULL = new string_id(STF, "trap_matrix_full");
    public static final string_id SID_TRAP_MAX_REACHED = new string_id(STF, "trap_max_reached");
    public static final string_id SID_INVALID_TARGET = new string_id(STF, "invalid_trap_target");
    public static final string_id SID_CANT_THROW_YET = new string_id(STF, "cant_throw_yet");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int maxTrapCount = pet_lib.calculateMaxTrapLoad(self);
        names[idx] = "max_trap_load";
        attribs[idx] = Integer.toString(maxTrapCount);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, DROID_TRAP))
        {
            String trapName = localize(new string_id("item_n", "trap_" + getStringObjVar(self, NAME)));
            names[idx] = "droid_trap_type";
            attribs[idx] = trapName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            int numTraps = getIntObjVar(self, CHARGES);
            names[idx] = "droid_trap_number";
            attribs[idx] = Integer.toString(numTraps);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id controlDevice = callable.getCallableCD(self);
        if (!isIdValid(controlDevice))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, MAX_TRAP_LOAD, pet_lib.calculateMaxTrapLoad(self));
        if (hasObjVar(controlDevice, DROID_TRAP))
        {
            copyObjVar(controlDevice, self, DROID_TRAP);
        }
        attachScript(self, getStringObjVar(self, SCRIPT));
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (getMaster(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(item, "trapName"))
        {
            transferDataFromTrapToDroid(player, item);
        }
        return SCRIPT_CONTINUE;
    }
    public int doRadialTrapAdd(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id trap = params.getObjId("trap");
        obj_id player = params.getObjId("player");
        transferDataFromTrapToDroid(player, trap);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == getMaster(self))
        {
            if (hasSkill(player, "outdoors_scout_novice"))
            {
                int menu = mi.addRootMenu(menu_info_types.SERVER_TRAVEL_OPTIONS, SID_THROWTRAP_OPTIONS);
                mi.addSubMenu(menu, menu_info_types.SERVER_BAZAAR_OPTIONS, SID_TRAIN_THROW_ONE);
                if (hasObjVar(self, DROID_TRAP))
                {
                    mi.addSubMenu(menu, menu_info_types.SERVER_HUE, SID_CLEAR_TRAP);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, new string_id(STF, "not_enough_power"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_BAZAAR_OPTIONS)
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_TRAP_THROW_ONE);
        }
        else if (item == menu_info_types.SERVER_HUE)
        {
            obj_id controlDevice = callable.getCallableCD(self);
            sendSystemMessage(player, SID_TRAP_MATRIX_CLEARED);
            detachScript(self, getStringObjVar(self, SCRIPT));
            removeObjVar(self, DROID_TRAP);
            removeObjVar(controlDevice, DROID_TRAP);
        }
        return SCRIPT_CONTINUE;
    }
    public int doPetThrowTrap(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id droid = params.getObjId("droid");
        obj_id player = params.getObjId("player");
        int trapNum = params.getInt("trapNum");
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, DROID_TRAP))
        {
            sendSystemMessage(player, SID_NO_TRAP_LOADED);
            return SCRIPT_CONTINUE;
        }
        int trapBonus = getIntObjVar(self, "trap_bonus");
        int skillMod = getSkillStatMod(player, "trapping");
        if (!hasSkill(player, "outdoors_scout_novice"))
        {
            return SCRIPT_CONTINUE;
        }
        if (skillMod <= 0)
        {
            string_id trapNoSkill = new string_id("trap/trap", "trap_no_skill");
            sendSystemMessage(self, trapNoSkill);
            return SCRIPT_CONTINUE;
        }
        if (skillMod < getIntObjVar(self, DIFF))
        {
            sendSystemMessage(player, SID_INSUFFICIENT_SKILL);
            return SCRIPT_CONTINUE;
        }
        double dblTrapBonus = (skillMod * 0.84) + ((skillMod * 0.35) * (trapBonus / 100));
        int intTrapBonus = (int)dblTrapBonus;
        obj_id target = getLookAtTarget(player);
        if (getDistance(self, target) > MAX_SHOOTING_DISTANCE)
        {
            sendSystemMessage(player, SID_TARGET_TOO_FAR);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isAiDead(target) || !isIdValid(target) || !ai_lib.isMonster(target) || pet_lib.isPet(target))
        {
            sendSystemMessage(player, SID_INVALID_TARGET);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, RECENT_TRAP_THROWN))
        {
            int timeLeft = pet_lib.getTimeLeft(self, RECENT_TRAP_THROWN, THROW_DELAY_END);
            if (timeLeft > 0)
            {
                prose_package pp = prose.getPackage(SID_CANT_THROW_YET, timeLeft);
                sendSystemMessageProse(player, pp);
                return SCRIPT_CONTINUE;
            }
            utils.removeScriptVar(self, RECENT_TRAP_THROWN);
        }
        boolean area = false;
        obj_id[] objDefenders = null;
        if (hasObjVar(self, AE))
        {
            objDefenders = pvpGetTargetsInRange(self, target, 5.0f);
            area = true;
        }
        else 
        {
            objDefenders = new obj_id[1];
            objDefenders[0] = target;
        }
        boolean exp = false;
        for (obj_id tt : objDefenders) {
            if (!isIdValid(tt)) {
                continue;
            }
            int creatureLev = ai_lib.getLevel(tt);
            int reduction = (int) (intTrapBonus * 0.5f);
            int levDiff = creatureLev - ((intTrapBonus - reduction) * 2);
            int chance = 80;
            if (levDiff > 0) {
                chance -= (int) (StrictMath.pow(levDiff, 3.0f / 2.0f) * 2.2f + 5.0f);
            }
            int roll = rand(1, 100);
            if (roll < chance) {
                dictionary resultParams = new dictionary();
                resultParams.put("target", tt);
                resultParams.put("player", player);
                messageTo(self, "trapHit", resultParams, 3, false);
            } else {
                dictionary resultParams = new dictionary();
                resultParams.put("target", tt);
                resultParams.put("player", player);
                messageTo(self, "trapMiss", resultParams, 3, false);
            }
        }
        doTrapCombatResults(self, target, self, player);
        utils.setScriptVar(self, RECENT_TRAP_THROWN, getGameTime());
        utils.setScriptVar(self, THROW_DELAY_END, TRAP_DELAY);
        messageTo(self, "trapDone", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public void doTrapCombatResults(obj_id self, obj_id target, obj_id trap, obj_id player) throws InterruptedException
    {
        String strDefense = "get_hit_grenade_far_light";
        String strAttack = "throw_trap_" + getStringObjVar(trap, NAME);
        dictionary params = new dictionary();
        params.put("player", player);
        params.put("target", target);
        messageTo(self, "doDelayedFire", params, 2, false);
        attacker_results cbtAttackerResult = new attacker_results();
        cbtAttackerResult.id = self;
        cbtAttackerResult.weapon = null;
        cbtAttackerResult.endPosture = getPosture(self);
        defender_results[] cbtDefenderResult = new defender_results[1];
        cbtDefenderResult[0] = new defender_results();
        cbtDefenderResult[0].id = target;
        cbtDefenderResult[0].endPosture = getPosture(target);
        cbtDefenderResult[0].result = COMBAT_RESULT_HIT;
        doCombatResults(strAttack, cbtAttackerResult, cbtDefenderResult);
        return;
    }
    public int doDelayedFire(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id target = params.getObjId("target");
        String clientEffectTrap = getStringObjVar(self, TEMPLATE);
        clientEffectTrap = clientEffectTrap.substring(27, clientEffectTrap.length() - 4);
        playClientEffectLoc(player, "clienteffect/combat_" + clientEffectTrap + ".cef", getLocation(target), 0.8f);
        playClientEffectLoc(player, "clienteffect/combat_special_attacker_aim.cef", getLocation(self), 1.1f);
        return SCRIPT_CONTINUE;
    }
    public void transferDataFromTrapToDroid(obj_id player, obj_id trap) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!isCurrentTrap(self, trap))
        {
            sendSystemMessage(player, SID_ALREADY_LOADED);
            return;
        }
        int skillMod = getSkillStatMod(player, "trapping");
        int trapDiff = getIntObjVar(trap, "trapDiff");
        if (trapDiff > skillMod)
        {
            sendSystemMessage(player, SID_INSUFFICIENT_SKILL);
            return;
        }
        if (isAtTrapLoadMax(self, getIntObjVar(self, CHARGES)))
        {
            sendSystemMessage(player, SID_TRAP_MATRIX_FULL);
            return;
        }
        obj_id controlDevice = callable.getCallableCD(self);
        String[] scriptList = new String[1];
        scriptList = getScriptList(trap);
        String scriptName = scriptList[0].substring(7);
        setObjVar(self, TEMPLATE, getTemplateName(trap));
        setObjVar(self, NAME, getStringObjVar(trap, "trapName"));
        if (hasObjVar(trap, "areaEffect"))
        {
            setObjVar(self, AE, 1);
        }
        setObjVar(self, CHARGES, getAdditiveTrapCount(self, trap, player));
        setObjVar(self, DIFF, trapDiff);
        setObjVar(self, SCRIPT, scriptName);
        attachScript(self, scriptName);
        copyObjVar(self, controlDevice, DROID_TRAP);
        String trapName = localize(new string_id("item_n", "trap_" + getStringObjVar(self, NAME)));
        int numCharges = getIntObjVar(self, CHARGES);
        prose_package pp = prose.getPackage(SID_TRAP_MODULE_INITIALIZE, null, trapName, null, null, null, null, null, null, null, numCharges, 0.0f);
        sendSystemMessageProse(player, pp);
        return;
    }
    public boolean isCurrentTrap(obj_id droid, obj_id item) throws InterruptedException
    {
        if (!hasObjVar(droid, DROID_TRAP))
        {
            return true;
        }
        String[] scriptList = new String[1];
        scriptList = getScriptList(item);
        String scriptName = scriptList[0].substring(7);
        String trapScript = getStringObjVar(droid, SCRIPT);
        if (scriptList[0].equals("") || scriptList[0] == null)
        {
            return false;
        }
        if (scriptName.equals(trapScript))
        {
            return true;
        }
        return false;
    }
    public int getAdditiveTrapCount(obj_id droid, obj_id trap, obj_id player) throws InterruptedException
    {
        if (hasObjVar(droid, DROID_TRAP))
        {
            int itterations = getCount(trap);
            int currentTraps = getIntObjVar(droid, CHARGES);
            int trapDelta = 0;
            for (int i = 0; i < itterations; i++)
            {
                if (!isAtTrapLoadMax(droid, currentTraps + trapDelta))
                {
                    trapDelta++;
                }
            }
            setObjVar(droid, CHARGES, (currentTraps + trapDelta));
            setCount(trap, (itterations - trapDelta));
            if (getCount(trap) > 0)
            {
                sendSystemMessage(player, SID_TRAP_MAX_REACHED);
                return getIntObjVar(droid, CHARGES);
            }
            if (getCount(trap) <= 0)
            {
                destroyObject(trap);
            }
            return getIntObjVar(droid, CHARGES);
        }
        else 
        {
            int count = getCount(trap);
            destroyObject(trap);
            return count;
        }
    }
    public boolean isAtTrapLoadMax(obj_id droid, int checkNum) throws InterruptedException
    {
        int trapMax = getIntObjVar(droid, MAX_TRAP_LOAD);
        if (!hasObjVar(droid, DROID_TRAP))
        {
            return false;
        }
        if (checkNum >= trapMax)
        {
            return true;
        }
        return false;
    }
}

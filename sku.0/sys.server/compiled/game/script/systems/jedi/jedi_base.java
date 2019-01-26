package script.systems.jedi;

import script.combat_engine.weapon_data;
import script.*;
import script.library.*;

import java.util.Vector;

public class jedi_base extends script.base_script
{
    public jedi_base()
    {
    }
    public static final int DAMAGE = 0;
    public static final int WOUNDS = 1;
    public static final int BATTLE_FATIGUE = NUM_ATTRIBUTES;
    public static final int STATES = 10;
    public static final int BLEEDING = 11;
    public static final int DISEASE = 12;
    public static final int POISON = 13;
    public static final String[] STAT_STRINGS = 
    {
        "health",
        "constitution",
        "action",
        "stamina",
        "mind",
        "willpower",
        "battle_fatigue"
    };
    public static final String[] STAT_TYPE = 
    {
        "damage",
        "wounds"
    };
    public void doJediHealingSpam(int intStat, int intDamageType, int intAmount, obj_id objPlayer, obj_id objTarget) throws InterruptedException
    {
        if (intStat < NUM_ATTRIBUTES)
        {
            String strTest = STAT_STRINGS[intStat];
            strTest = strTest + "_" + STAT_TYPE[intDamageType];
            string_id strStat = new string_id("jedi_spam", strTest);
            if (objTarget != objPlayer)
            {
                string_id strSpam = new string_id("jedi_spam", "heal_other_self");
                prose_package proseTest = prose.getPackage(strSpam, null, objTarget, strStat, intAmount);
                sendSystemMessageProse(objPlayer, proseTest);
                if (isPlayer(objTarget))
                {
                    strSpam = new string_id("jedi_spam", "heal_other_other");
                    proseTest = prose.getPackage(strSpam, null, objPlayer, strStat, intAmount);
                    sendSystemMessageProse(objTarget, proseTest);
                }
            }
            else 
            {
                string_id strSpam = new string_id("jedi_spam", "heal_self");
                prose_package proseTest = prose.getPackage(strSpam, objPlayer, null, strStat, intAmount);
                sendSystemMessageProse(objPlayer, proseTest);
            }
        }
        else if (intStat == BATTLE_FATIGUE)
        {
            String strTest = STAT_STRINGS[intStat];
            string_id strStat = new string_id("jedi_spam", strTest);
            if (objTarget != objPlayer)
            {
                string_id strSpam = new string_id("jedi_spam", "heal_other_self");
                prose_package proseTest = prose.getPackage(strSpam, null, objTarget, strStat, intAmount);
                sendSystemMessageProse(objPlayer, proseTest);
                if (isPlayer(objTarget))
                {
                    strSpam = new string_id("jedi_spam", "heal_other_other");
                    proseTest = prose.getPackage(strSpam, null, objPlayer, strStat, intAmount);
                    sendSystemMessageProse(objTarget, proseTest);
                }
            }
            else 
            {
                string_id strSpam = new string_id("jedi_spam", "heal_self");
                prose_package proseTest = prose.getPackage(strSpam, objPlayer, null, strStat, intAmount);
                sendSystemMessageProse(objPlayer, proseTest);
            }
        }
        else if (intStat == BLEEDING)
        {
            if (objPlayer != objTarget)
            {
                if (intAmount < 0)
                {
                    string_id strSpam = new string_id("jedi_spam", "stop_bleeding_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
                else 
                {
                    string_id strSpam = new string_id("jedi_spam", "staunch_bleeding_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
            }
        }
        else if (intStat == STATES)
        {
            if (objPlayer != objTarget)
            {
                if (intAmount < 0)
                {
                    string_id strSpam = new string_id("jedi_spam", "stop_states_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
            }
        }
        else if (intStat == DISEASE)
        {
            if (objPlayer != objTarget)
            {
                if (intAmount < 0)
                {
                    string_id strSpam = new string_id("jedi_spam", "stop_disease_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
                else 
                {
                    string_id strSpam = new string_id("jedi_spam", "staunch_disease_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
            }
        }
        else if (intStat == POISON)
        {
            if (objPlayer != objTarget)
            {
                if (intAmount < 0)
                {
                    string_id strSpam = new string_id("jedi_spam", "stop_poison_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
                else 
                {
                    string_id strSpam = new string_id("jedi_spam", "staunch_poison_other");
                    prose_package proseTest = prose.getPackage(strSpam, objTarget);
                    sendSystemMessageProse(objPlayer, proseTest);
                }
            }
        }
        return;
    }
    public dictionary modifyJediHealDataFromAlignment(obj_id objPlayer, dictionary dctJediInfo) throws InterruptedException
    {
        float fltAlignmentModifier = jedi.getJediAlignmentModifier(objPlayer, dctJediInfo);
        int intMinMindHeal = dctJediInfo.getInt("intMinMindHeal");
        float fltTest = intMinMindHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinMindHeal = (int)fltTest;
        dctJediInfo.put("intMinMindHeal", intMinMindHeal);
        int intMaxMindHeal = dctJediInfo.getInt("intMaxMindHeal");
        fltTest = intMaxMindHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxMindHeal = (int)fltTest;
        dctJediInfo.put("intMaxMindHeal", intMaxMindHeal);
        int intMinMindWoundHeal = dctJediInfo.getInt("intMinMindWoundHeal");
        fltTest = intMinMindWoundHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinMindWoundHeal = (int)fltTest;
        dctJediInfo.put("intMinMindWoundHeal", intMinMindWoundHeal);
        int intMaxMindWoundHeal = dctJediInfo.getInt("intMaxMindWoundHeal");
        fltTest = intMaxMindWoundHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxMindWoundHeal = (int)fltTest;
        dctJediInfo.put("intMaxMindWoundHeal", intMaxMindWoundHeal);
        int intMinHealthHeal = dctJediInfo.getInt("intMinHealthHeal");
        fltTest = intMinHealthHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinHealthHeal = (int)fltTest;
        dctJediInfo.put("intMinHealthHeal", intMinHealthHeal);
        int intMaxHealthHeal = dctJediInfo.getInt("intMaxHealthHeal");
        fltTest = intMaxHealthHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxHealthHeal = (int)fltTest;
        dctJediInfo.put("intMaxHealthHeal", intMaxHealthHeal);
        int intMinHealthWoundHeal = dctJediInfo.getInt("intMinHealthWoundHeal");
        fltTest = intMinHealthWoundHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinHealthWoundHeal = (int)fltTest;
        dctJediInfo.put("intMinHealthWoundHeal", intMinHealthWoundHeal);
        int intMaxHealthWoundHeal = dctJediInfo.getInt("intMaxHealthWoundHeal");
        fltTest = intMaxHealthWoundHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxHealthWoundHeal = (int)fltTest;
        dctJediInfo.put("intMaxHealthWoundHeal", intMaxHealthWoundHeal);
        int intMinActionHeal = dctJediInfo.getInt("intMinActionHeal");
        fltTest = intMinActionHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinActionHeal = (int)fltTest;
        dctJediInfo.put("intMinActionHeal", intMinActionHeal);
        int intMaxActionHeal = dctJediInfo.getInt("intMaxActionHeal");
        fltTest = intMaxActionHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxActionHeal = (int)fltTest;
        dctJediInfo.put("intMaxActionHeal", intMaxActionHeal);
        int intMinActionWoundHeal = dctJediInfo.getInt("intMinActionWoundHeal");
        fltTest = intMinActionWoundHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinActionWoundHeal = (int)fltTest;
        dctJediInfo.put("intMinActionWoundHeal", intMinActionWoundHeal);
        int intMaxActionWoundHeal = dctJediInfo.getInt("intMaxActionWoundHeal");
        fltTest = intMaxActionWoundHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxActionWoundHeal = (int)fltTest;
        dctJediInfo.put("intMaxActionWoundHeal", intMaxActionWoundHeal);
        int intMinBattleFatigueHeal = dctJediInfo.getInt("intMinBattleFatigueHeal");
        fltTest = intMinBattleFatigueHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMinBattleFatigueHeal = (int)fltTest;
        dctJediInfo.put("intMinBattleFatigueHeal", intMinBattleFatigueHeal);
        int intMaxBattleFatigueHeal = dctJediInfo.getInt("intMaxBattleFatigueHeal");
        fltTest = intMaxBattleFatigueHeal;
        fltTest = fltTest * fltAlignmentModifier;
        intMaxBattleFatigueHeal = (int)fltTest;
        dctJediInfo.put("intMaxBattleFatigueHeal", intMaxBattleFatigueHeal);
        return dctJediInfo;
    }
    public int performJediHealCommand(obj_id player, obj_id target, dictionary actionData) throws InterruptedException
    {
        final int BLEED_HEAL_STRENGTH = 250;
        final int POISON_HEAL_STRENGTH = 250;
        final int DISEASE_HEAL_STRENGTH = 100;
        final int FIRE_HEAL_STRENGTH = 500;
        boolean boolHealedStats = false;
        boolean boolHealedWounds = false;
        boolean boolHealedBattleFatigue = false;
        boolean boolHealedState = false;
        boolean boolHealedBleeding = false;
        boolean boolHealedDots = false;
        int maxHealthHeal = actionData.getInt("intMaxHealthHeal");
        int maxHealthWoundHeal = actionData.getInt("intMaxHealthWoundHeal");
        int maxBattleFatigueHeal = actionData.getInt("intMaxBattleFatigueHeal");
        int healStates = actionData.getInt("intHealStates");
        int healBleeding = actionData.getInt("intHealBleeding");
        int healPoison = actionData.getInt("intHealPoison");
        int healDisease = actionData.getInt("intHealDisease");
        int healFire = actionData.getInt("intHealFire");
        float extraForceCost = jedi.getModifiedExtraForceCost(player, actionData.getFloat("extraForceCost"), actionData.getString("intActionId"));
        int totalForceCost = jedi.getModifiedForceCost(player, actionData.getInt("intJediPowerCost"), actionData.getString("intActionId"));
        int jediForcePower = getForcePower(player);
        if (totalForceCost < jediForcePower && maxHealthHeal != 0)
        {
            int curHealth = getHealth(target);
            int maxHealth = getWoundedMaxAttrib(target, HEALTH);
            float healForceCost = 0;
            int healthToHeal = 0;
            if (maxHealthHeal != 0 && curHealth < maxHealth)
            {
                healthToHeal = maxHealth - curHealth;
                if (maxHealthHeal > 0 && healthToHeal > maxHealthHeal)
                {
                    healthToHeal = maxHealthHeal;
                }
                healForceCost += healthToHeal * extraForceCost;
            }
            if ((totalForceCost + (int)healForceCost) > jediForcePower)
            {
                healForceCost = (jediForcePower - totalForceCost);
                while (healthToHeal * extraForceCost > healForceCost)
                {
                    healthToHeal -= 1;
                    if (healthToHeal < 0)
                    {
                        healthToHeal = 0;
                    }
                }
            }
            if (healthToHeal > 0)
            {
                boolHealedStats = true;
                totalForceCost += (int)healForceCost;
                if (healthToHeal > 0)
                {
                    healing.healDamage(player, target, HEALTH, healthToHeal);
                    doJediHealingSpam(HEALTH, DAMAGE, healthToHeal, player, target);
                }
            }
        }
        if (totalForceCost < jediForcePower && maxBattleFatigueHeal != 0)
        {
            int fatigueToHeal = 0;
            float fatigueHealForceCost = 0;
            if (maxBattleFatigueHeal != 0)
            {
                fatigueToHeal = getShockWound(target);
                if (maxBattleFatigueHeal > 0 && fatigueToHeal > maxBattleFatigueHeal)
                {
                    fatigueToHeal = maxBattleFatigueHeal;
                }
                fatigueHealForceCost += fatigueToHeal * extraForceCost;
            }
            if ((totalForceCost + (int)fatigueHealForceCost) > jediForcePower)
            {
                fatigueHealForceCost = jediForcePower - totalForceCost;
                while (fatigueToHeal * extraForceCost > fatigueHealForceCost)
                {
                    fatigueToHeal -= 1;
                    if (fatigueToHeal < 0)
                    {
                        fatigueToHeal = 0;
                    }
                }
            }
            if (fatigueToHeal > 0)
            {
                boolHealedBattleFatigue = true;
                totalForceCost += (int)fatigueHealForceCost;
                healShockWound(target, fatigueToHeal);
                doJediHealingSpam(BATTLE_FATIGUE, WOUNDS, fatigueToHeal, player, target);
            }
        }
        if (totalForceCost < jediForcePower && healStates != 0)
        {
            int statesToHeal = 0;
            float stateHealForceCost = 0;
            for (int i = 0; i < STATE_NUMBER_OF_STATES; i++)
            {
                if (hasObjVar(target, "combat.intEffects." + i))
                {
                    statesToHeal++;
                    stateHealForceCost += healStates;
                }
            }
            if ((totalForceCost + (int)stateHealForceCost) > jediForcePower)
            {
                stateHealForceCost = jediForcePower - totalForceCost;
                while (stateHealForceCost > jediForcePower - totalForceCost)
                {
                    statesToHeal -= 1;
                    if (statesToHeal < 0)
                    {
                        statesToHeal = 0;
                    }
                    stateHealForceCost += healStates;
                    if (stateHealForceCost < 0)
                    {
                        stateHealForceCost = 0;
                    }
                }
            }
            if (statesToHeal > 0)
            {
            }
        }
        if (totalForceCost < jediForcePower && healBleeding != 0)
        {
            if ((totalForceCost + healBleeding) < jediForcePower)
            {
                int healResult = dot.reduceDotTypeStrength(target, dot.DOT_BLEEDING, BLEED_HEAL_STRENGTH);
                if (healResult >= 0)
                {
                    boolHealedBleeding = true;
                    totalForceCost += healBleeding;
                    doJediHealingSpam(BLEEDING, BLEEDING, healResult, player, target);
                }
            }
        }
        if (totalForceCost < jediForcePower && healPoison != 0)
        {
            if ((totalForceCost + healPoison) < jediForcePower)
            {
                int healResult = dot.reduceDotTypeStrength(target, dot.DOT_POISON, POISON_HEAL_STRENGTH);
                if (healResult >= 0)
                {
                    boolHealedDots = true;
                    totalForceCost += healPoison;
                    doJediHealingSpam(POISON, POISON, healResult, player, target);
                }
            }
        }
        if (totalForceCost < jediForcePower && healDisease != 0)
        {
            if ((totalForceCost + healDisease) < jediForcePower)
            {
                int healResult = dot.reduceDotTypeStrength(target, dot.DOT_DISEASE, DISEASE_HEAL_STRENGTH);
                if (healResult >= 0)
                {
                    boolHealedDots = true;
                    totalForceCost += healDisease;
                    doJediHealingSpam(DISEASE, DISEASE, healResult, player, target);
                }
            }
        }
        if (totalForceCost < jediForcePower && healFire != 0)
        {
            if ((totalForceCost + healFire) < jediForcePower)
            {
                int healResult = dot.reduceDotTypeStrength(target, dot.DOT_FIRE, FIRE_HEAL_STRENGTH);
                if (healResult >= 0)
                {
                    boolHealedDots = true;
                    totalForceCost += healFire;
                }
            }
        }
        if (boolHealedStats || boolHealedWounds || boolHealedBattleFatigue || boolHealedState || boolHealedBleeding || boolHealedDots)
        {
            jedi.playJediActionEffect(player, target, actionData);
        }
        else 
        {
            totalForceCost = 0;
            if (target != player)
            {
                string_id strSpam = new string_id("jedi_spam", "no_damage_heal_other");
                sendSystemMessage(player, strSpam);
            }
            else 
            {
                string_id strSpam = new string_id("jedi_spam", "no_damage_heal_self");
                sendSystemMessage(player, strSpam);
            }
        }
        return totalForceCost;
    }
    public boolean doJediHealCommand(obj_id self, obj_id target, String strCommand) throws InterruptedException
    {
        int intCommand = getStringCrc(strCommand);
        strCommand = combat.getBestAction(self, strCommand);
        int armorCount = utils.getIntScriptVar(self, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_with_armor"));
            return false;
        }
        if (buff.isParalyzed(self))
        {
            return false;
        }
        dictionary dctJediInfo = dataTableGetRow(jedi.JEDI_ACTIONS_FILE, strCommand);
        LOG("jedi", "getting row " + strCommand + " from " + jedi.JEDI_ACTIONS_FILE);
        if (dctJediInfo == null)
        {
            LOG("DESIGNER_FATAL", "Action " + strCommand + " has no entry in the jedi actions datatable");
            return false;
        }
        target = jedi.getCorrectFriendlyTarget(self, target, dctJediInfo);
        if (!isIdValid(target))
        {
            LOG("jedi", "No valid target");
            return false;
        }
        if (ai_lib.isDroid(target) || ai_lib.isAndroid(target) || ai_lib.isVehicle(target) || vehicle.isVehicle(target))
        {
            sendSystemMessage(self, new string_id("jedi_spam", "no_help_target"));
            return false;
        }
        if (!jedi.doNonCombatActionPrecheck(self, target, dctJediInfo))
        {
            LOG("jedi", "non combat precheck failed");
            return false;
        }
        LOG("jedi", "self is " + self + " target is " + target);
        weapon_data weaponData = new weapon_data();
        weaponData = getWeaponData(getCurrentWeapon(self));
        int[] actionCost = combat.getActionCost(self, weaponData, dctJediInfo);
        if (!combat.canDrainCombatActionAttributes(self, actionCost))
        {
            return false;
        }
        int health_before = getAttrib(target, HEALTH);
        int intForcePowerCost = performJediHealCommand(self, target, dctJediInfo);
        if (!jedi.drainForcePower(self, intForcePowerCost))
        {
            LOG("jedi", "no force power");
            string_id strSpam = new string_id("jedi_spam", "no_force_power");
            sendSystemMessage(self, strSpam);
            return false;
        }
        if (intForcePowerCost > 0)
        {
            combat.drainCombatActionAttributes(self, actionCost);
            int intVisibilityValue = dctJediInfo.getInt("intVisibilityValue");
            int intVisibilityRange = dctJediInfo.getInt("intVisibilityRange");
            jedi.jediActionPerformed(self, intVisibilityValue, intVisibilityRange);
            LOG("jedi", "Performed heal command");
            pvpHelpPerformed(self, target);
            jedi.doJediNonCombatAnimation(self, target, dctJediInfo);
            int health_after = getAttrib(target, HEALTH);
            int health_delta = (int)((health_after - health_before) * 0.5f);
            healing._addMedicalHate(self, target, health_delta);
            int xpOverride = dctJediInfo.getInt("xpOverride");
            if (xpOverride < 0)
            {
                xpOverride = intForcePowerCost;
            }
            jedi.grantJediXP(self, xpOverride);
            stealth.testInvisNonCombatAction(self, target);
        }
        else 
        {
            return false;
        }
        return true;
    }
    public void findNewSkillTrainer(obj_id self) throws InterruptedException
    {
        final String[] TRAINER_TYPES = 
        {
            "trainer_brawler",
            "trainer_artisan",
            "trainer_scout",
            "trainer_marksman",
            "trainer_entertainer",
            "trainer_medic"
        };
        location locTest = getLocation(self);
        if (locTest.area.equals("tutorial"))
        {
            removeObjVar(self, "jedi.locTrainerLocation");
            return;
        }
        String strPrimaryCategory = "trainer";
        String strSecondaryCategory = TRAINER_TYPES[rand(0, TRAINER_TYPES.length - 1)];
        map_location[] rawMapLocations = getPlanetaryMapLocations(strPrimaryCategory, strSecondaryCategory);
        Vector mapLocations = new Vector();
        mapLocations.setSize(0);
        location testloc = new location();
        for (map_location rawMapLocation : rawMapLocations) {
            testloc.x = rawMapLocation.getX();
            testloc.z = rawMapLocation.getY();
            testloc.area = getLocation(self).area;
            if (getCityAtLocation(testloc, 0) > 0) {
                continue;
            }
            mapLocations = utils.addElement(mapLocations, rawMapLocation);
        }
        if ((mapLocations == null) || (mapLocations.size() == 0))
        {
            removeObjVar(self, "jedi.locTrainerLocation");
            return;
        }
        locTest.y = 0;
        Vector locNewLocations = new Vector();
        locNewLocations.setSize(0);
        for (Object mapLocation : mapLocations) {
            location locNewLocation = new location();
            locNewLocation.area = locTest.area;
            locNewLocation.x = ((map_location) mapLocation).getX();
            locNewLocation.z = ((map_location) mapLocation).getY();
            float fltDistance = getDistance(locTest, locNewLocation);
            if (fltDistance > 128) {
                locNewLocations = utils.addElement(locNewLocations, locNewLocation);
            }
        }
        if (locNewLocations.size() < 1)
        {
            removeObjVar(self, "jedi.locTrainerLocation");
            return;
        }
        location locFinalLocation = ((location)locNewLocations.get(rand(0, locNewLocations.size() - 1)));
        setObjVar(self, "jedi.locTrainerLocation", locFinalLocation);
        string_id strSpam = new string_id("jedi_spam", "trainer_updated");
        sendSystemMessage(self, strSpam);
        return;
    }
}

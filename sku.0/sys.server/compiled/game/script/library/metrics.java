package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.xp;

public class metrics extends script.base_script
{
    public metrics()
    {
    }
    public static final String CONFIG_BUFF_METRICS = "logBuffMetrics";
    public static final String CONFIG_WEAPON_METRICS = "logWeaponMetrics";
    public static final String CONFIG_ARMOR_METRICS = "logArmorMetrics";
    public static boolean checkConfigSetting(String configString) throws InterruptedException
    {
        String enabled = toLower(getConfigSetting("GameServer", configString));
        if (enabled == null)
        {
            return false;
        }
        if (enabled.equals("true") || enabled.equals("1"))
        {
            return true;
        }
        return false;
    }
    public static void logBuffStatus(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            return;
        }
        if (!checkConfigSetting(CONFIG_BUFF_METRICS))
        {
            return;
        }
        String logMsg = "Buff Metric: %TU buff status: ";
        String[] attrib = 
        {
            "H:",
            "A:",
            "M:"
        };
        for (int i = 0; i < 3; i++)
        {
            logMsg += attrib[i];
            for (int j = 0; j < 3; j++)
            {
                logMsg += getUnmodifiedMaxAttrib(target, (i * 3) + j);
                logMsg += "(" + getMaxAttrib(target, (i * 3) + j) + ")";
                if (j < 2)
                {
                    logMsg += ",";
                }
                else 
                {
                    logMsg += " ";
                }
            }
        }
        CustomerServiceLog("metrics", logMsg, target);
    }
    public static void logWeaponStatus(obj_id player, obj_id weapon) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return;
        }
        if (!isIdValid(weapon) || !isWeapon(weapon))
        {
            return;
        }
        if (!checkConfigSetting(CONFIG_WEAPON_METRICS))
        {
            return;
        }
        String logMsg = "Weapon Metric: %TU: ";
        String weaponTemplate = getTemplateName(weapon);
        logMsg += localize(getNameFromTemplate(weaponTemplate)) + "(" + weaponTemplate + ") - ";
        if (weaponTemplate.endsWith("unarmed_default_player.iff"))
        {
            return;
        }
        combat_engine.weapon_data weaponData = getWeaponData(weapon);
        logMsg += "Spd (" + weaponData.attackSpeed + "), ";
        logMsg += "Dmg (" + weaponData.minDamage + "-" + weaponData.maxDamage + "), ";
        logMsg += "DmgType (";
        if ((weaponData.damageType & DAMAGE_KINETIC) != 0)
        {
            logMsg += "KINETIC, ";
        }
        if ((weaponData.damageType & DAMAGE_ENERGY) != 0)
        {
            logMsg += "ENERGY, ";
        }
        if ((weaponData.damageType & DAMAGE_BLAST) != 0)
        {
            logMsg += "BLAST, ";
        }
        if ((weaponData.damageType & DAMAGE_STUN) != 0)
        {
            logMsg += "STUN, ";
        }
        if ((weaponData.damageType & DAMAGE_RESTRAINT) != 0)
        {
            logMsg += "RESTRAINT, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_HEAT) != 0)
        {
            logMsg += "ELEMENTAL_HEAT, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_COLD) != 0)
        {
            logMsg += "ELEMENTAL_COLD, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_ACID) != 0)
        {
            logMsg += "ELEMENTAL_ACID, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_ELECTRICAL) != 0)
        {
            logMsg += "ELEMENTAL_ELECTRICAL, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_HEAT) != 0)
        {
            logMsg += "ENVIRONMENTAL_HEAT, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_COLD) != 0)
        {
            logMsg += "ENVIRONMENTAL_COLD, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_ACID) != 0)
        {
            logMsg += "ENVIRONMENTAL_ACID, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_ELECTRICAL) != 0)
        {
            logMsg += "ENVIRONMENTAL_ELECTRICAL, ";
        }
        logMsg += "), ";
        logMsg += "Range (" + weaponData.minRange + ", " + weaponData.maxRange + "), ";
        logMsg += "Cost (" + weaponData.attackCost + ")";
        logMsg += "Wound (" + weaponData.woundChance + ")";
        logMsg += "Accuracy (" + weaponData.accuracy + ")";
        CustomerServiceLog("metrics", logMsg, player);
    }
    public static void logArmorStatus(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return;
        }
        if (!checkConfigSetting(CONFIG_ARMOR_METRICS))
        {
            return;
        }
        obj_id[] inv = getWornItems(player);
        if (inv == null || inv.length == 0)
        {
            return;
        }
        for (int i = 0; i < inv.length; i++)
        {
            int got = getGameObjectType(inv[i]);
            if (isGameObjectTypeOf(got, GOT_armor))
            {
                int oldHp = utils.getIntScriptVar(inv[i], "metrics.armorHp");
                int curHp = getHitpoints(inv[i]);
                int maxHp = getMaxHitpoints(inv[i]);
                if (oldHp == maxHp && curHp < maxHp)
                {
                    logArmorPiece(player, inv[i]);
                }
                else if (oldHp >= (maxHp / 4) * 3 && curHp < (maxHp / 4) * 3)
                {
                    logArmorPiece(player, inv[i]);
                }
                else if (oldHp >= maxHp / 2 && curHp < maxHp / 2)
                {
                    logArmorPiece(player, inv[i]);
                }
                else if (oldHp >= maxHp / 4 && curHp < maxHp / 4)
                {
                    logArmorPiece(player, inv[i]);
                }
                utils.setScriptVar(inv[i], "metrics.armorHp", curHp);
            }
        }
    }
    public static void logArmorPiece(obj_id player, obj_id object) throws InterruptedException
    {
        if (!isIdValid(object))
        {
            return;
        }
        if (!hasScript(object, "item.armor.new_armor"))
        {
            attachScript(object, "item.armor.new_armor");
        }
        String logMsg = "Armor Metric: %TU: ";
        String armorTemplate = getTemplateName(object);
        logMsg += localize(getNameFromTemplate(armorTemplate)) + "(" + armorTemplate + ") - ";
        int eff = armor.getArmorGeneralProtection(object);
        logMsg += "Eff (" + eff + "), ";
        CustomerServiceLog("metrics", logMsg, player);
    }
    public static obj_id[] getWornItems(obj_id player) throws InterruptedException
    {
        final String[] wornSlots = 
        {
            "shoes",
            "pants1",
            "pants2",
            "utility_belt",
            "chest1",
            "chest2",
            "chest3_l",
            "chest3_r",
            "back",
            "cloak",
            "bicep_l",
            "bicep_r",
            "bracer_upper_l",
            "bracer_upper_r",
            "bracer_lower_l",
            "bracer_lower_r",
            "wrist_l",
            "wrist_r",
            "gloves",
            "ring_l",
            "ring_r",
            "neck",
            "hat",
            "eyes",
            "earring_l",
            "earring_r"
        };
        Vector inv = new Vector();
        for (int i = 0; i < wornSlots.length; i++)
        {
            obj_id item = getObjectInSlot(player, wornSlots[i]);
            if (isIdValid(item) && !utils.isElementInArray(inv, item))
            {
                inv = utils.addElement(inv, item);
            }
        }
        obj_id[] _inv = new obj_id[0];
        if (inv != null)
        {
            _inv = new obj_id[inv.size()];
            inv.toArray(_inv);
        }
        return _inv;
    }
    public static void doKillMetrics(obj_id killCredit, obj_id target) throws InterruptedException
    {
        String killLog = "";
        int curTime = getGameTime();
        String targetName = getCreatureName(target);
        int targetLevel = getLevel(target);
        int combatTime = curTime - utils.getIntScriptVar(target, xp.VAR_COMBAT_TIMESTAMP);
        int numHits = utils.getIntScriptVar(target, xp.VAR_DAMAGE_COUNT);
        int dmgTotal = utils.getIntScriptVar(target, xp.VAR_DAMAGE_TALLY);
        int avgHit = (int)((float)dmgTotal / numHits);
        killLog = "kill;" + curTime + ";" + target + ";" + targetName + ";" + targetLevel + ";" + combatTime + ";" + numHits + ";" + avgHit;
        if (group.isGroupObject(killCredit))
        {
            killLog += ";group;" + skill.getGroupLevel(killCredit);
            obj_id[] members = getGroupMemberIds(killCredit);
            for (int i = 0; i < members.length; i++)
            {
                String playerName = getEncodedName(members[i]);
                int playerLevel = getLevel(members[i]);
                int dmgAmount = utils.getIntScriptVar(target, xp.VAR_ATTACKER_LIST + "." + members[i] + ".damage");
                float dmgPercent = (float)((int)((float)dmgAmount / dmgTotal * 10000) / 100f);
                killLog += ";" + members[i] + ";" + playerName + ";" + playerLevel + ";" + dmgAmount + ";" + dmgPercent;
            }
        }
        else 
        {
            String playerName = getEncodedName(killCredit);
            int playerLevel = getLevel(killCredit);
            int dmgAmount = utils.getIntScriptVar(target, xp.VAR_ATTACKER_LIST + "." + killCredit + ".damage");
            float dmgPercent = (float)((int)((float)dmgAmount / dmgTotal * 10000) / 100f);
            killLog += ";solo;" + killCredit + ";" + playerName + ";" + playerLevel + ";" + dmgAmount + ";" + dmgPercent;
        }
        logBalance(killLog);
        return;
    }
    public static void doXpRateMetrics(obj_id player, String xpType, int xpAmount) throws InterruptedException
    {
        int curTime = getGameTime();
        String playerName = getEncodedName(player);
        int playerLevel = getLevel(player);
        int startTime = utils.getIntScriptVar(player, "xpRateMetrics." + xpType + ".startTime");
        int lastTime = utils.getIntScriptVar(player, "xpRateMetrics." + xpType + ".timeStamp");
        if (startTime == 0)
        {
            startTime = lastTime = curTime;
            utils.setScriptVar(player, "xpRateMetrics." + xpType + ".startTime", curTime);
        }
        int xpTotal = xpAmount + utils.getIntScriptVar(player, "xpRateMetrics." + xpType + ".amount");
        float xpRate = 0;
        if (startTime != curTime)
        {
            xpRate = (float)((int)((float)xpTotal / ((curTime - startTime) / 3600f) * 100f) / 100f);
        }
        String xpRateLog = "xpRate;" + curTime + ";" + player + ";" + playerName + ";" + playerLevel + ";" + lastTime + ";" + xpType + ";" + xpTotal + ";" + xpRate;
        logBalance(xpRateLog);
        utils.setScriptVar(player, "xpRateMetrics." + xpType + ".timestamp", curTime);
        utils.setScriptVar(player, "xpRateMetrics." + xpType + ".amount", xpTotal);
    }
    public static void doQuestMetrics(obj_id player, int questId, int level, int tier, String xpType, int xpAmount) throws InterruptedException
    {
        int curTime = getGameTime();
        String playerName = getEncodedName(player);
        int playerLevel = getLevel(player);
        String questName = questGetQuestName(questId);
        String questLog = "quest;" + curTime + ";" + player + ";" + playerName + ";" + playerLevel + ";" + questName + ";" + level + ";" + tier + ";" + xpType + ";" + xpAmount;
        logBalance(questLog);
    }
}

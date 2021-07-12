package script.theme_park.wod;

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
import script.library.combat;
import script.library.create;
import script.library.group;
import script.library.prose;
import script.library.static_item;
import script.library.trial;
import script.library.utils;

public class wod_mutant_rancor_combat extends script.base_script
{
    public wod_mutant_rancor_combat()
    {
    }
    public static final String NS_BICEP_SCHEMATIC = "item_wod_bossloot_ns_bracer";
    public static final String SMC_BICEP_SCHEMATIC = "item_wod_bossloot_smc_bicep_";
    public static final String SMC_BRACER_SCHEMATIC = "item_wod_bossloot_smc_bracer_";
    public static final int MAX_DISTANCE = 104;
    public static final int REPEAT_TIME = 45;
    public static final int OFFSET_TIME = 15;
    public static final int HP_WOD_RANCOR = 618125;
    public static int buffStartTime = 0;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, HP_WOD_RANCOR);
        applySkillStatisticModifier(self, "expertise_glancing_blow_reduction", 100);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "open_balance_buff", -1.0f);
        obj_id[] players = getPlayerCreaturesInRange(self, 250.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                obj_id player = players[i];
                if (isIdValid(player) && exists(player) && !isIncapacitated(player) && !isDead(player))
                {
                    addHate(self, player, 1000.0f);
                    startCombat(self, player);
                }
            }
        }
        messageTo(self, "checkCombatStatus", null, REPEAT_TIME + ((rand(0, OFFSET_TIME) + OFFSET_TIME) - OFFSET_TIME), false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        if (hasObjVar(self, "buffStartTime"))
        {
            buffStartTime = getIntObjVar(self, "buffStartTime");
        }
        int buffTime = getGameTime() - buffStartTime;
        int randomTime = REPEAT_TIME + ((rand(0, OFFSET_TIME) + OFFSET_TIME) - OFFSET_TIME);
        float healthPercent = (float)getAttrib(self, HEALTH) / (float)getMaxAttrib(self, HEALTH);
        if (healthPercent <= 0.67f && healthPercent > 0.20f)
        {
            int randBuff = rand(1, 4);
            if (!hasObjVar(self, "scaleOne"))
            {
                float mutationScaleOne = (float)(getScale(self) * 1.4);
                setScale(self, mutationScaleOne);
                setYaw(self, rand(0.0f, 360.0f));
                setObjVar(self, "scaleOne", true);
            }
            if (randBuff == 1 && buffTime > randomTime)
            {
                buff.applyBuff(self, "kun_twin_ranged_shield", 45.0f);
                setObjVar(self, "buffStartTime", getGameTime());
                buff.removeBuff(self, "hoth_atat_shield");
                buff.removeBuff(self, "sher_kar_rage");
                buff.removeBuff(self, "kun_twin_bonus");
            }
            if (randBuff == 2 && buffTime > randomTime)
            {
                buff.applyBuff(self, "hoth_atat_shield", 45.0f);
                setObjVar(self, "buffStartTime", getGameTime());
                buff.removeBuff(self, "kun_twin_ranged_shield");
                buff.removeBuff(self, "sher_kar_rage");
                buff.removeBuff(self, "kun_twin_bonus");
            }
            if (randBuff == 3 && buffTime > randomTime)
            {
                buff.applyBuff(self, "sher_kar_rage", 45.0f);
                setObjVar(self, "buffStartTime", getGameTime());
                buff.removeBuff(self, "kun_twin_ranged_shield");
                buff.removeBuff(self, "hoth_atat_shield");
                buff.removeBuff(self, "kun_twin_bonus");
            }
            if (randBuff == 4 && buffTime > randomTime)
            {
                buff.applyBuff(self, "kun_twin_bonus", 45.0f);
                setObjVar(self, "buffStartTime", getGameTime());
                buff.removeBuff(self, "kun_twin_ranged_shield");
                buff.removeBuff(self, "hoth_atat_shield");
                buff.removeBuff(self, "sher_kar_rage");
            }
        }
        if (healthPercent <= 0.20f)
        {
            if (buffTime > randomTime)
            {
                buff.applyBuff(self, "open_balance_buff", 30.0f);
                setObjVar(self, "buffStartTime", getGameTime());
            }
            if (!hasObjVar(self, "scaleTwo"))
            {
                float mutationScaleTwo = (float)(getScale(self) * 1.9);
                setScale(self, mutationScaleTwo);
                setYaw(self, rand(0.0f, 360.0f));
                setObjVar(self, "scaleTwo", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkCombatStatus(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            CustomerServiceLog("wod_themepark", "wod_mutant_rancor_combat.checkCombatStatus(): Boss Mob: " + self + " is already in combat.");
            return SCRIPT_CONTINUE;
        }
        obj_id parent = getObjIdObjVar(self, "parent");
        if (isIdValid(parent))
        {
            if (getRandomCombatTarget(self, parent))
            {
                return SCRIPT_CONTINUE;
            }
            dictionary webster = trial.getSessionDict(parent);
            messageTo(parent, "defaultEventReset", webster, 2, false);
        }
        messageTo(self, "checkCombatStatus", null, REPEAT_TIME + (rand(0, OFFSET_TIME + OFFSET_TIME) - OFFSET_TIME), false);
        return SCRIPT_CONTINUE;
    }
    public boolean getRandomCombatTarget(obj_id self, obj_id parent) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            CustomerServiceLog("wod_themepark", "wod_mutant_rancor_combat.getRandomCombatTarget() FAILED to find self.");
            return false;
        }
        obj_id[] targets = trial.getValidTargetsInRadiusIgnoreLOS(self, MAX_DISTANCE);
        if (targets == null || targets.length == 0)
        {
            CustomerServiceLog("wod_themepark", "wod_mutant_rancor_combat.getRandomCombatTarget() no targets for boss (" + self + ") is: " + MAX_DISTANCE);
            dictionary webster = trial.getSessionDict(parent);
            messageTo(parent, "defaultEventReset", webster, 2, false);
            return false;
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("wod_themepark", "wod_mutant_rancor_combat.getRandomCombatTarget() Mob found player but player was not part of owners group.");
            return false;
        }
        for (int i = 0; i < targets.length; i++)
        {
            if (!isIdValid(targets[i]))
            {
                continue;
            }
            if (!group.inSameGroup(targets[i], playerEnemy))
            {
                CustomerServiceLog("wod_themepark", "boss_fight_functionality.getRandomCombatTarget(): Player " + targets[i] + " was found as an invalid target.");
                continue;
            }
            startCombat(self, targets[i]);
            CustomerServiceLog("wod_themepark", "boss_fight_functionality.getRandomCombatTarget(): Player " + targets[i] + " was found as a valid target. Boss Mob: " + self + " attacking player.");
            return true;
        }
        CustomerServiceLog("wod_themepark", "boss_fight_functionality.OnIncapacitateTarget(): Boss Mob: " + self + " failed to find a player to attack from player: " + playerEnemy + " group.");
        dictionary webster = trial.getSessionDict(parent);
        messageTo(parent, "defaultEventReset", webster, 2, false);
        return false;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(self);
        int lootChance = rand(1, 100);
        if (lootChance <= 5)
        {
            static_item.createNewItemFunction(NS_BICEP_SCHEMATIC, inv);
        }
        if (lootChance > 5 && lootChance <= 20)
        {
            int leftOrRight = rand(0, 1);
            String toAppend = "l";
            if (leftOrRight == 1)
            {
                toAppend = "r";
            }
            static_item.createNewItemFunction(SMC_BICEP_SCHEMATIC + toAppend, inv);
        }
        if (lootChance > 20 && lootChance <= 35)
        {
            int leftOrRight = rand(0, 1);
            String toAppend = "l";
            if (leftOrRight == 1)
            {
                toAppend = "r";
            }
            static_item.createNewItemFunction(SMC_BRACER_SCHEMATIC + toAppend, inv);
        }
        return SCRIPT_CONTINUE;
    }
}

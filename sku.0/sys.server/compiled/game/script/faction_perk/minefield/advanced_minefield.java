package script.faction_perk.minefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.pclib;
import script.library.stealth;
import script.library.utils;
import script.library.vehicle;
import script.library.weapons;

public class advanced_minefield extends script.systems.combat.combat_base
{
    public advanced_minefield()
    {
    }
    public static final float MAX_MINE_CHANCE = 0.8f;
    public static final int MINE_IMMUNITY_TIME = 10;
    public static final String[] MINE_ATTACK_COMMAND = 
    {
        "mine_attack_xg",
        "mine_attack_drx55",
        "mine_attack_sr88"
    };
    public static final String[] MINE_EFFECT = 
    {
        "clienteffect/mine_xg.cef",
        "clienteffect/mine_drx55.cef",
        "clienteffect/mine_sr88.cef"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeMinefield", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeMinefield", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String name, obj_id who) throws InterruptedException
    {
        if (!name.startsWith("hq_minefield"))
        {
            return SCRIPT_CONTINUE;
        }
        checkMineDetonation(self, who);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String name, obj_id who) throws InterruptedException
    {
        if (!name.startsWith("hq_minefield"))
        {
            return SCRIPT_CONTINUE;
        }
        checkMineDetonation(self, who);
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeMinefield(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, hq.VAR_DEFENSE_PARENT))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getObjIdObjVar(self, hq.VAR_DEFENSE_PARENT);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        float radius = hq.getMinefieldRadius(structure);
        float interval = (radius / 5f);
        int count = 1;
        while (radius > 1f && count < 6)
        {
            String volume_name = "hq_minefield_" + count;
            createTriggerVolume(volume_name, radius, true);
            radius -= interval;
            count++;
        }
        weapons.setWeaponData(self, 4000, 6000, 64f, WEAPON_TYPE_HEAVY, DAMAGE_KINETIC, DAMAGE_ELEMENTAL_HEAT, 0, 1f, 0f);
        return SCRIPT_CONTINUE;
    }
    public void checkMineDetonation(obj_id self, obj_id who) throws InterruptedException
    {
        if (!isIdValid(who))
        {
            return;
        }
        if (!pvpCanAttack(self, who))
        {
            return;
        }
        int locomotion = getLocomotion(who);
        if (locomotion == LOCOMOTION_PRONE || locomotion == LOCOMOTION_CRAWLING)
        {
            return;
        }
        if (utils.hasScriptVar(who, "mine_immunity"))
        {
            int immunity_time = utils.getIntScriptVar(who, "mine_immunity");
            if (getGameTime() > immunity_time)
            {
                utils.removeScriptVar(who, "mine_immunity");
            }
            else 
            {
                return;
            }
        }
        if (!hasObjVar(self, hq.VAR_DEFENSE_PARENT))
        {
            return;
        }
        obj_id structure = getObjIdObjVar(self, hq.VAR_DEFENSE_PARENT);
        if (!isIdValid(structure))
        {
            return;
        }
        float max = (float)hq.getMaxMines(structure);
        float current = (float)hq.getTotalMines(structure);
        int chance = (int)(((current / max) * MAX_MINE_CHANCE) * 100f);
        int roll = rand(1, 100);
        if (roll < chance)
        {
            executeMineAttack(self, structure, who);
            utils.setScriptVar(who, "mine_immunity", getGameTime() + (MINE_IMMUNITY_TIME * 2));
        }
        else 
        {
            utils.setScriptVar(who, "mine_immunity", getGameTime() + MINE_IMMUNITY_TIME);
        }
    }
    public void executeMineAttack(obj_id self, obj_id structure, obj_id who) throws InterruptedException
    {
        obj_id mount = getMountId(who);
        if (isIdValid(mount))
        {
            if (vehicle.isJetPackVehicle(mount))
            {
                return;
            }
        }
        int type = hq.getRandomMineType(structure);
        if (type < 0)
        {
            return;
        }
        if (type >= MINE_ATTACK_COMMAND.length || type >= MINE_EFFECT.length)
        {
            return;
        }
        if (combatStandardAction(MINE_ATTACK_COMMAND[type], self, who, self, "", null, true))
        {
            stealth.testInvisCombatAction(who, who);
            playClientEffectLoc(who, MINE_EFFECT[type], getLocation(who), 0f);
            utils.dismountRiderJetpackCheck(who);
            dictionary d = new dictionary();
            d.put("player", who);
            messageTo(self, "handleMineDeathBlow", d, 1f, false);
        }
    }
    public int handleMineDeathBlow(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        pclib.coupDeGrace(player, self, false, false);
        return SCRIPT_CONTINUE;
    }
    public int handleAccidentalMineDetonation(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id structure = params.getObjId("hq");
        executeMineAttack(self, structure, player);
        utils.setScriptVar(player, "mine_immunity", getGameTime() + (MINE_IMMUNITY_TIME * 2));
        return SCRIPT_CONTINUE;
    }
}

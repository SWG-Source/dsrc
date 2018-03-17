package script.item.trap;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.skill;
import script.library.utils;
import script.library.xp;
import script.library.pet_lib;

public class trap_base extends script.base_script
{
    public trap_base()
    {
    }
    public static final string_id SID_DECODE = new string_id("treasure_map/treasure_map", "decode");
    public static final string_id SID_SYS_NOT_READY = new string_id("trap/trap", "sys_not_ready");
    public static final string_id SID_SYS_MISS = new string_id("trap/trap", "sys_miss");
    public static final string_id SID_SYS_CREATURES_ONLY = new string_id("trap/trap", "sys_creatures_only");
    public static final string_id SID_SYS_NO_PETS = new string_id("trap/trap", "sys_no_pets");
    public static final string_id SID_ADD_TRAP_TO_DROID = new string_id("pet/droid_modules", "add_trap_to_droid");
    public static final string_id SID_NO_TRAP_IN_SPACE = new string_id("space/space_interaction", "no_trap_in_space");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            int count = getCount(self);
            names[idx] = "quantity";
            attribs[idx] = Integer.toString(count);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
            int diff = getIntObjVar(self, "trapDiff");
            names[idx] = "complexity";
            attribs[idx] = Integer.toString(diff);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, "droid_trap"))
        {
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
            mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid != null)
            {
                mid.setServerNotify(true);
            }
        }
        if (utils.getTrapDroidId(player) != null && !hasScript(self, "ai.pet"))
        {
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_ADD_TRAP_TO_DROID);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_TRAP_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "droid_trap"))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                int skillMod = getSkillStatMod(player, "trapping");
                if (skillMod <= 0)
                {
                    string_id trapNoSkill = new string_id("trap/trap", "trap_no_skill");
                    sendSystemMessage(player, trapNoSkill);
                    return SCRIPT_OVERRIDE;
                }
                String strParams = self.toString();
                obj_id objTarget = getLookAtTarget(player);
                if (!isIdValid(objTarget))
                {
                    return SCRIPT_CONTINUE;
                }
                if (!canSee(player, objTarget))
                {
                    sendSystemMessage(player, new string_id("combat_effects", "cansee_fail"));
                    return SCRIPT_CONTINUE;
                }
                if (!ai_lib.isMonster(objTarget) || isIncapacitated(objTarget) || isDead(objTarget))
                {
                    sendSystemMessage(player, SID_SYS_CREATURES_ONLY);
                    return SCRIPT_CONTINUE;
                }
                if (pet_lib.isPet(objTarget))
                {
                    sendSystemMessage(player, SID_SYS_NO_PETS);
                    return SCRIPT_CONTINUE;
                }
                queueCommand(player, (88718951), objTarget, strParams, COMMAND_PRIORITY_NORMAL);
            }
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            dictionary params = new dictionary();
            params.put("trap", getSelf());
            params.put("player", player);
            obj_id droid = callable.getCDCallable(utils.getTrapDroidId(player));
            messageTo(droid, "doRadialTrapAdd", params, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void trapUsed(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "droid_trap"))
        {
            droidTrapUsed(self);
            return;
        }
        int intUses = getCount(self) - 1;
        if (intUses <= 0)
        {
            destroyObject(self);
        }
        else 
        {
            setCount(self, intUses);
        }
        return;
    }
    public int trapDone(obj_id self, dictionary params) throws InterruptedException
    {
        trapUsed(self);
        utils.setScriptVar(self, "hits", 0);
        utils.setScriptVar(self, "misses", 0);
        utils.setScriptVar(self, "grantedXP", 0);
        return SCRIPT_CONTINUE;
    }
    public int trapHit(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int trapMiss(obj_id self, dictionary params) throws InterruptedException
    {
        int misses = utils.getIntScriptVar(self, "misses");
        if (misses == 0)
        {
            obj_id player = params.getObjId("player");
            sendSystemMessage(player, SID_SYS_MISS);
        }
        misses++;
        utils.setScriptVar(self, "misses", misses);
        return SCRIPT_CONTINUE;
    }
    public void grantTrapXP(obj_id player, obj_id target, float trapMod) throws InterruptedException
    {
        obj_id self = getSelf();
        if (hasScript(target, "ai.pet_advance"))
        {
            return;
        }
        if (!hasObjVar(self, "droid_trap"))
        {
            float targetLevel = (float)getLevel(target);
            int pseudoDamage = (int)(Math.pow(targetLevel, 1.5f) * 2.2f + 66.f);
            if (pseudoDamage > 2000)
            {
                pseudoDamage = 2000;
            }
            pseudoDamage *= (int)trapMod;
            xp.updateCombatXpList(target, player, xp.SCOUT, pseudoDamage);
            return;
        }
        else 
        {
            float targetLevel = (float)getLevel(target);
            int pseudoDamage = (int)(Math.pow(targetLevel, 1.5f) * 1f + 66.f);
            if (pseudoDamage > 1000)
            {
                pseudoDamage = 1000;
            }
            pseudoDamage *= (int)trapMod;
            xp.updateCombatXpList(target, player, xp.SCOUT, pseudoDamage);
            return;
        }
    }
    public void assignTrapEffect(obj_id player, obj_id target, int drainhealth, int drainact, int drainmind) throws InterruptedException
    {
        drainAttributes(target, drainact, drainmind);
    }
    public void droidTrapUsed(obj_id self) throws InterruptedException
    {
        obj_id controlDevice = callable.getCallableCD(self);
        int intUses = getIntObjVar(self, "droid_trap.trap_num.charges") - 1;
        if (intUses <= 0)
        {
            detachScript(self, getStringObjVar(self, "droid_trap.trap_num.script"));
            removeObjVar(self, "droid_trap");
            removeObjVar(controlDevice, "droid_trap");
        }
        else 
        {
            setObjVar(self, "droid_trap.trap_num.charges", intUses);
            setObjVar(controlDevice, "droid_trap.trap_num.charges", intUses);
        }
        return;
    }
}

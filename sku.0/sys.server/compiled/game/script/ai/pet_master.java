package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.buff;
import script.library.callable;
import script.library.utils;
import script.library.pet_lib;
import script.library.colors;
import script.library.ai_lib;
import script.library.chat;
import script.library.combat;
import script.library.scout;
import script.library.create;
import script.library.vehicle;
import script.library.sui;
import script.library.factions;

public class pet_master extends script.base_script
{
    public pet_master()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public static final String STF_FILE = "pet/droid_modules";
    public static final string_id SID_SYS_EMBOLDEN = new string_id("pet/pet_menu", "sys_embolden");
    public static final string_id SID_SYS_FAIL_EMBOLDEN = new string_id("pet/pet_menu", "sys_fail_embolden");
    public static final string_id SID_SYS_ENRAGE = new string_id("pet/pet_menu", "sys_enrage");
    public static final string_id SID_SYS_FAIL_ENRAGE = new string_id("pet/pet_menu", "sys_fail_enrage");
    public static final string_id SID_SYS_CANT_BUFF = new string_id("pet/pet_menu", "sys_cant_buff");
    public int OnRemovedFromGroup(obj_id self, obj_id group) throws InterruptedException
    {
        if (!callable.hasAnyCallable(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] memberList = getGroupMemberIds(group);
        if (memberList == null || memberList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < memberList.length; i++)
        {
            if (isIdValid(memberList[i]) && pet_lib.isMyPet(memberList[i], self))
            {
                queueCommand(memberList[i], (1348589140), group, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        obj_id pet = callable.getCallable(self, callable.CALLABLE_TYPE_COMBAT_PET);
        if (isIdValid(pet) && exists(pet) && !ai_lib.isInCombat(pet) && !beast_lib.isBeast(pet) && utils.hasScriptVar(pet, "ai.pet.guarding"))
        {
            addHate(pet, attacker, 0.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int failPetBuff(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        sendSystemMessage(self, SID_SYS_CANT_BUFF);
        return SCRIPT_CONTINUE;
    }
    public int OnSkillGranted(obj_id self, String skillName) throws InterruptedException
    {
        if (hasObjVar(self, "familiar"))
        {
            obj_id pet = getObjIdObjVar(self, "familiar");
            if (exists(pet) && isInWorld(pet))
            {
                dictionary trickData = new dictionary();
                trickData.put("pet", pet);
                trickData.put("trickNum", 2);
                trickData.put("heartBeat", false);
                messageTo(pet, "doFamiliarTrick", trickData, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        if (!callable.hasAnyCallable(self))
        {
            return SCRIPT_CONTINUE;
        }
        callable.storeCallables(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        if (!callable.hasAnyCallable(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isPet(killer))
        {
            killer = getMaster(killer);
        }
        callable.killCallables(self, killer);
        return SCRIPT_CONTINUE;
    }
    public int cmdDetonateDroid(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("droid_module", "ai.pet_master.cmdDetonateDroid");
        if (!isIdValid(target))
        {
            target = getLookAtTarget(self);
            if (!isIdValid(target))
            {
                sendSystemMessage(self, new string_id(STF_FILE, "invalid_droid_bomb"));
                return SCRIPT_CONTINUE;
            }
        }
        if (!pet_lib.isDroidPet(target))
        {
            sendSystemMessage(self, new string_id(STF_FILE, "invalid_droid_bomb"));
            return SCRIPT_CONTINUE;
        }
        if (getMaster(target) != self)
        {
            sendSystemMessage(self, new string_id(STF_FILE, "must_be_owner_droid_bomb"));
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isAiDead(target))
        {
            sendSystemMessage(self, new string_id(STF_FILE, "droid_disabled_detonate"));
            return SCRIPT_CONTINUE;
        }
        int bomb_level = getIntObjVar(target, "module_data.bomb_level");
        if (bomb_level < 1)
        {
            sendSystemMessage(self, new string_id(STF_FILE, "no_bomb_module"));
            return SCRIPT_CONTINUE;
        }
        if (!hasSkill(self, "class_smuggler_phase1_novice") && !hasSkill(self, "class_bountyhunter_phase1_novice"))
        {
            sendSystemMessage(self, new string_id(STF_FILE, "insufficient_skill_detonate"));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(target, "droid_module.countdown"))
        {
            sendSystemMessage(self, new string_id(STF_FILE, "countdown_already_started"));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "droid.bomb_droid_active") && utils.getIntScriptVar(self, "droid.bomb_droid_active") > getGameTime())
        {
            sendSystemMessage(self, new string_id(STF_FILE, "countdown_already_started"));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(target, "module_data.detonation_warmup"))
        {
            sendSystemMessage(self, new string_id(STF_FILE, "detonation_warmup"));
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(target, "droid_module.countdown", 1);
        dictionary d = new dictionary();
        d.put("master", self);
        d.put("count", 3);
        messageTo(target, "msgDetonationCountdown", d, 1.0f, false);
        sendSystemMessage(self, new string_id(STF_FILE, "countdown_started"));
        utils.setScriptVar(self, "droid.bomb_droid_active", (getGameTime() + 10));
        return SCRIPT_CONTINUE;
    }
    public int clearBombDroidTimer(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "droid.bomb_droid_active"))
        {
            utils.removeScriptVar(self, "droid.bomb_droid_active");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasPrereq(obj_id pet, int ability) throws InterruptedException
    {
        if (!isIdValid(pet) || !exists(pet))
        {
            return false;
        }
        obj_id pcd = callable.getCallableCD(pet);
        int[] abilityList = getIntArrayObjVar(pcd, "ai.petAbility.abilityList");
        if (abilityList == null || abilityList.length == 0)
        {
            return false;
        }
        return (hasPrereq(abilityList, ability));
    }
    public boolean hasPrereq(int[] abilityList, int ability) throws InterruptedException
    {
        int row = dataTableSearchColumnForInt(ability, "abilityCrc", pet_lib.PET_ABILITY_TABLE);
        if (row == -1)
        {
            return false;
        }
        dictionary abilityData = dataTableGetRow(pet_lib.PET_ABILITY_TABLE, row);
        String prereq = abilityData.getString("prereq");
        if (prereq == null || prereq.equals(""))
        {
            return true;
        }
        int prereqCrc = getStringCrc(prereq.toLowerCase());
        if (utils.getElementPositionInArray(abilityList, prereqCrc) != -1)
        {
            return true;
        }
        return false;
    }
    public int[] buildPrereqList(int ability) throws InterruptedException
    {
        Vector prereqList = new Vector();
        prereqList.setSize(0);
        int row = dataTableSearchColumnForInt(ability, "abilityCrc", pet_lib.PET_ABILITY_TABLE);
        if (row == -1)
        {
            return null;
        }
        dictionary abilityData = dataTableGetRow(pet_lib.PET_ABILITY_TABLE, row);
        String prereq = abilityData.getString("prereq");
        if (prereq == null || prereq.equals(""))
        {
            return null;
        }
        int prereqCrc = getStringCrc(prereq.toLowerCase());
        while (prereqCrc != 0)
        {
            row = dataTableSearchColumnForInt(prereqCrc, "abilityCrc", pet_lib.PET_ABILITY_TABLE);
            if (row == -1)
            {
                break;
            }
            prereqList = utils.addElement(prereqList, prereqCrc);
            abilityData = dataTableGetRow(pet_lib.PET_ABILITY_TABLE, row);
            prereq = abilityData.getString("prereq");
            if (prereq == null || prereq.equals(""))
            {
                break;
            }
            prereqCrc = getStringCrc(prereq.toLowerCase());
        }
        int[] _prereqList = new int[0];
        if (prereqList != null)
        {
            _prereqList = new int[prereqList.size()];
            for (int _i = 0; _i < prereqList.size(); ++_i)
            {
                _prereqList[_i] = ((Integer)prereqList.get(_i)).intValue();
            }
        }
        return _prereqList;
    }
    public int droid_follow(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_FOLLOW))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_follow_other(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_FOLLOW_OTHER))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_stay(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_STAY))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_guard(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_GUARD))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_friend(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_FRIEND))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_attack(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_ATTACK))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_patrol(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_PATROL))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_patrol_point(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_SET_PATROL_POINT))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_patrol_clear(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_CLEAR_PATROL_POINTS))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_store(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_RELEASE))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_transfer(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_TRANSFER))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_group(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_GROUP))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_trick_1(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_TRICK_1))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_trick_2(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_TRICK_2))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_trick_3(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_TRICK_3))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int droid_trick_4(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!pet_lib.doCommandNum(self, pet_lib.COMMAND_TRICK_4))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}

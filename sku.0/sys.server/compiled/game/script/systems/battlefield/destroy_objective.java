package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.factions;
import script.library.utils;

public class destroy_objective extends script.base_script
{
    public destroy_objective()
    {
    }
    public static final String VAR_WARNING_LEVEL = "battlefield.warning_level";
    public static final String VAR_FACTION = "battlefield.faction";
    public static final string_id SID_REPAIR_STRUCTURE = new string_id("battlefield", "repair_structure");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.destroy_objective::OnAttach -- " + self);
        if (hasObjVar(self, battlefield.VAR_VALUE1))
        {
            int hitpoints = (int)getFloatObjVar(self, battlefield.VAR_VALUE1);
            setMaxHitpoints(self, hitpoints);
            setHitpoints(self, hitpoints);
        }
        if (hasObjVar(self, battlefield.VAR_VALUE2))
        {
            int repair_cost = getIntObjVar(self, battlefield.VAR_VALUE2);
            setObjVar(self, battlefield.VAR_REPAIR_COST, repair_cost);
        }
        else 
        {
            setObjVar(self, battlefield.VAR_REPAIR_COST, 10);
        }
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        int faction_id = pvpBattlefieldGetFaction(self, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        setObjVar(self, VAR_FACTION, faction_id);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "destroy_objective::OnCombatDamaged");
        region bf = battlefield.getBattlefield(self);
        String name = getName(self);
        int faction_id = pvpBattlefieldGetFaction(self, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        int warning_level = 0;
        if (hasObjVar(self, VAR_WARNING_LEVEL))
        {
            warning_level = getIntObjVar(self, VAR_WARNING_LEVEL);
        }
        int hitpoints = getHitpoints(self);
        int max_hitpoints = getMaxHitpoints(self);
        float percent_hp = (float)hitpoints / (float)max_hitpoints;
        if (percent_hp < .5)
        {
            if (warning_level == 0)
            {
                battlefield.sendFactionMessage(bf, faction, name + " is heavily damaged!");
                setObjVar(self, VAR_WARNING_LEVEL, 1);
            }
        }
        if (percent_hp < .25)
        {
            if (warning_level == 1)
            {
                battlefield.sendFactionMessage(bf, faction, name + " is severely damaged!");
                setObjVar(self, VAR_WARNING_LEVEL, 2);
            }
        }
        if (percent_hp < .1)
        {
            if (warning_level == 2)
            {
                battlefield.sendFactionMessage(bf, faction, name + " is about to be destroyed!");
                setObjVar(self, VAR_WARNING_LEVEL, 3);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "destroy_objective::OnObjectDisabled");
        if (!hasObjVar(self, battlefield.VAR_COMBAT_DESTROYED))
        {
            return SCRIPT_CONTINUE;
        }
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        String name = getName(self);
        int faction_id = getIntObjVar(self, VAR_FACTION);
        String faction = factions.getFactionNameByHashCode(faction_id);
        String objVar_name = battlefield.VAR_GAME + "." + faction + "_destroy";
        Vector destroy_objs = getResizeableObjIdArrayObjVar(master_object, objVar_name);
        if (destroy_objs == null)
        {
            LOG("LOG_CHANNEL", "destroy_objective::OnObjectDisabled -- destroy_objs is null");
            return SCRIPT_CONTINUE;
        }
        if (destroy_objs.size() == 1)
        {
            dictionary params = new dictionary();
            params.put("faction", faction);
            messageTo(master_object, "msgEliminateFaction", params, 0.0f, true);
        }
        else 
        {
            int idx = utils.getElementPositionInArray(destroy_objs, self);
            if (idx == -1)
            {
                LOG("LOG_CHANNEL", "battlefield.destroy_objective::OnObjectDisabled -- cannot find " + self + " in objective list.");
            }
            else 
            {
                destroy_objs = utils.removeElementAt(destroy_objs, idx);
                setObjVar(master_object, objVar_name, destroy_objs);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

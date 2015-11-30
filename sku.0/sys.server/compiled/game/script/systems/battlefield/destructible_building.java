package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.player_structure;
import script.library.factions;
import script.library.sui;
import script.library.utils;

public class destructible_building extends script.base_script
{
    public destructible_building()
    {
    }
    public static final String VAR_SMOKE_LEVEL = "battlefield.smoke_level";
    public static final string_id SID_CONSTRUCTION_SITE = new string_id("battlefield", "construction_site");
    public static final string_id SID_DESTROY_STRUCTURE = new string_id("battlefield", "destroy_structure");
    public static final string_id SID_REPAIR_STRUCTURE = new string_id("battlefield", "repair_structure");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, battlefield.VAR_BUILDTIME))
        {
            int construction_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT_STATUS, SID_CONSTRUCTION_SITE);
        }
        if (battlefield.isBattlefieldConstructed(self))
        {
            if (battlefield.isSameBattlefieldFaction(self, player))
            {
                int destroy_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT_DESTROY, SID_DESTROY_STRUCTURE);
            }
        }
        if (getHitpoints(self) < getMaxHitpoints(self))
        {
            if (battlefield.isSameBattlefieldFaction(self, player))
            {
                int repair_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT_PAY, SID_REPAIR_STRUCTURE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_TERMINAL_MANAGEMENT_STATUS)
        {
            region bf = battlefield.getBattlefield(self);
            obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
            int faction_id = pvpBattlefieldGetFaction(self, bf);
            String faction = factions.getFactionNameByHashCode(faction_id);
            String name = getStringObjVar(self, battlefield.VAR_NAME);
            int build_time = getIntObjVar(self, battlefield.VAR_BUILDTIME);
            int timestamp = getIntObjVar(self, battlefield.VAR_TIMESTAMP);
            int current_time = getGameTime();
            int time_passed = current_time - timestamp;
            int percent = time_passed * 100 / build_time;
            int time = build_time - time_passed;
            if (time > 0)
            {
                int[] conv_time = player_structure.convertSecondsTime(time);
                String time_str = player_structure.assembleTimeRemaining(conv_time);
                String[] dsrc = new String[5];
                dsrc[0] = "Structure: " + name;
                dsrc[1] = "Faction: " + faction;
                dsrc[2] = "This structure is " + percent + "% complete";
                dsrc[3] = "";
                dsrc[4] = "Time Remaining: " + time_str;
                sui.listbox(player, "Structure Construction Status", "Structure Construction Status", dsrc);
            }
        }
        if (item == menu_info_types.SERVER_TERMINAL_MANAGEMENT_DESTROY)
        {
            if (battlefield.isSameBattlefieldFaction(self, player))
            {
                battlefield.removeBattlefieldObject(self);
            }
        }
        if (item == menu_info_types.SERVER_TERMINAL_MANAGEMENT_PAY)
        {
            if (battlefield.isSameBattlefieldFaction(self, player))
            {
                queueCommand(player, (1476603522), self, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        if (hasObjVar(self, battlefield.VAR_REPAIRING))
        {
            removeObjVar(self, battlefield.VAR_REPAIRING);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.destructible_building::OnObjectDisabled -- " + self);
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        if (!isIdValid(master_object))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, battlefield.VAR_GAME_CRITICAL))
        {
            setObjVar(self, battlefield.VAR_COMBAT_DESTROYED, 1);
            String name = getName(self);
            int faction_id = pvpBattlefieldGetFaction(self, bf);
            String faction = factions.getFactionNameByHashCode(faction_id);
            battlefield.sendBattlefieldMessage(master_object, "The " + faction + "'s " + name + " was destroyed by " + getFirstName(killer));
        }
        obj_id[] players = battlefield.getPlayersOnBattlefield(master_object);
        if (players != null)
        {
            location loc = getLocation(self);
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", loc, 0);
            }
        }
        messageTo(self, "battlefieldObjectDestroyed", null, 2.5f, false);
        return SCRIPT_CONTINUE;
    }
    public int msgRefreshSmoke(obj_id self, dictionary params) throws InterruptedException
    {
        int smoke_level = 0;
        if (hasObjVar(self, VAR_SMOKE_LEVEL))
        {
            smoke_level = getIntObjVar(self, VAR_SMOKE_LEVEL);
        }
        location loc = getLocation(self);
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        if (smoke_level == 1)
        {
            obj_id[] players = battlefield.getPlayersOnBattlefield(master_object);
            if (players != null)
            {
                for (int i = 0; i < players.length; i++)
                {
                    playClientEffectLoc(players[i], "clienteffect/lair_med_damage_smoke.cef", loc, 0);
                }
            }
            messageTo(self, "msgRefreshSmoke", null, 5.0f, true);
        }
        else if (smoke_level == 2)
        {
            obj_id[] players = battlefield.getPlayersOnBattlefield(master_object);
            if (players != null)
            {
                for (int i = 0; i < players.length; i++)
                {
                    playClientEffectLoc(players[i], "clienteffect/lair_hvy_damage_fire.cef", loc, 0);
                }
            }
            messageTo(self, "msgRefreshSmoke", null, 8.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgConstructionComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.destructible_building::msgConstructionComplete");
        location loc = getLocation(self);
        float yaw = getYaw(self);
        String template = params.getString("template");
        float height = params.getFloat("height");
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        int faction_id = pvpBattlefieldGetFaction(self, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        String name = params.getString("name");
        int repair_cost = params.getInt("cost") / 10;
        float hitpoints = (float)getHitpoints(self);
        float maxHitpoints = (float)getMaxHitpoints(self);
        int percent = (int)((hitpoints / maxHitpoints) * 100.0f);
        if (percent < 5)
        {
            percent = 5;
        }
        battlefield.removeBattlefieldObject(self);
        loc.y = height;
        obj_id structure = battlefield.addBattlefieldObject(master_object, template, loc, yaw, faction);
        setObjVar(structure, battlefield.VAR_CONSTRUCTED, 1);
        setObjVar(structure, battlefield.VAR_NAME, name);
        setObjVar(structure, battlefield.VAR_REPAIR_COST, repair_cost);
        int hitpoint_mod = (getMaxHitpoints(structure) * percent) / 100;
        setHitpoints(structure, hitpoint_mod);
        if (structure == null)
        {
            return SCRIPT_CONTINUE;
        }
        battlefield.sendFactionMessage(master_object, faction, "Construction complete on " + name);
        return SCRIPT_CONTINUE;
    }
    public int msgRepairPulse(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.destructible_building::msgRepairPulse -- " + self);
        if (!hasObjVar(self, battlefield.VAR_REPAIRING))
        {
            return SCRIPT_CONTINUE;
        }
        int hitpoints = getHitpoints(self);
        int max_hitpoints = getMaxHitpoints(self);
        int repair_amt = max_hitpoints / 20;
        hitpoints = hitpoints + repair_amt;
        if (hitpoints >= max_hitpoints)
        {
            hitpoints = max_hitpoints;
        }
        else 
        {
            messageTo(self, "msgRepairPulse", null, battlefield.REPAIR_PULSE, true);
        }
        setHitpoints(self, hitpoints);
        if (hasObjVar(self, VAR_SMOKE_LEVEL))
        {
            float percent_hp = (float)hitpoints / (float)max_hitpoints;
            if (percent_hp > .5)
            {
                removeObjVar(self, VAR_SMOKE_LEVEL);
            }
            if ((percent_hp <= .5) && (percent_hp > .25))
            {
                setObjVar(self, VAR_SMOKE_LEVEL, 1);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int battlefieldObjectDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        battlefield.removeBattlefieldObject(self);
        return SCRIPT_CONTINUE;
    }
}

package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;
import script.library.xp;
import script.library.group;

public class boss_loot extends script.base_script
{
    public boss_loot()
    {
    }
    public static final String BLOOD = "object/tangible/loot/dungeon/death_watch_bunker/blood_vial.iff";
    public static final String STABLE = "object/tangible/loot/dungeon/death_watch_bunker/jetpack_stabilizer.iff";
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "spawner");
        obj_id player = getObjIdObjVar(spawner, "death_watch_herald.player01");
        setObjVar(spawner, "death_watch_herald.boss.died", getGameTime());
        removeObjVar(spawner, "death_watch_herald.boss.spawned");
        removeObjVar(player, "death_watch_herald.destroy");
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id winner = getObjIdObjVar(self, xp.VAR_TOP_GROUP);
        if (isIdValid(winner))
        {
            obj_id[] permitted = null;
            if (group.isGroupObject(winner))
            {
                permitted = getGroupMemberIds(winner);
            }
            else 
            {
                permitted = new obj_id[1];
                permitted[0] = winner;
            }
            if ((permitted == null) || (permitted.length == 0))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id looter = null;
                for (int i = 0; i < permitted.length; i++)
                {
                    looter = permitted[i];
                    if (looter != null)
                    {
                        if (hasObjVar(looter, "death_watch_herald.imperialquest"))
                        {
                            createTheirLoot(self);
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            createJetpackLoot(self);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createTheirLoot(obj_id self) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return;
        }
        String creatureName = ai_lib.getCreatureName(self);
        if (creatureName.equals("mand_bunker_dthwatch_gold"))
        {
            obj_id item = createObject(BLOOD, corpseInventory, "");
            obj_id jet = createObject(STABLE, corpseInventory, "");
        }
    }
    public void createJetpackLoot(obj_id self) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return;
        }
        String creatureName = ai_lib.getCreatureName(self);
        if (creatureName.equals("mand_bunker_dthwatch_gold"))
        {
            obj_id jet = createObject(STABLE, corpseInventory, "");
        }
        return;
    }
}

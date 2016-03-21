package script.city.bestine;

import script.dictionary;
import script.library.*;
import script.obj_id;

public class tusken_spawner extends script.base_script
{
    public tusken_spawner()
    {
    }
    public static final String HEAD = "object/tangible/loot/quest/tusken_head.iff";
    public static final String BATON = "object/weapon/melee/baton/victor_baton_gaderiffi.iff";
    public static final String CARVED = "object/tangible/loot/quest/carved_stone.iff";
    public static final String SMOOTH = "object/tangible/loot/quest/smooth_stone.iff";
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "spawner");
        messageTo(spawner, "doDeathRespawn", null, 10, false);
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
                obj_id looter;
                for (obj_id aPermitted : permitted) {
                    looter = aPermitted;
                    if (looter != null) {
                        if (hasObjVar(looter, "bestine.tuskenquest")) {
                            createTheirLoot(self);
                            return SCRIPT_CONTINUE;
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
        if (ai_lib.getCreatureName(self).equals("tusken_executioner"))
        {
            createObject(HEAD, corpseInventory, "");
            checkForLoot(self);
        }
        else 
        {
            checkForLoot(self);
        }
    }
    public void checkForLoot(obj_id self) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return;
        }
        int chance = rand(0, 7000);
        if ((chance >= 0) && (chance <= 70))
        {
            createObject(CARVED, corpseInventory, "");
        }
        else if ((chance >= 71) && (chance <= 300))
        {
            createObject(SMOOTH, corpseInventory, "");
        }
        else if ((chance >= 301) && (chance <= 500))
        {
            weapons.createWeapon(BATON, corpseInventory, rand(0.75f, 1.0f));
        }
    }
}

package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.*;

public class event_three_boss extends script.base_script
{
    public event_three_boss()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_VOLCANO_THREE_BOSS);
        trial.markAsVolcanoCommander(self);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "boss");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (isIdValid(corpseInventory)) {
            int x = rand(1, 100);
            if (x <= 12){  // 12% chance at dropping bonus loot Lava Transport Skiff
                static_item.createNewItemFunction("item_tow_schematic_vehicle_02_02", corpseInventory);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isIncapacitated(self))
        {
            resetEncounter(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int guardReset(obj_id self, dictionary params) throws InterruptedException
    {
        resetEncounter(self);
        return SCRIPT_CONTINUE;
    }
    public void resetEncounter(obj_id self) throws InterruptedException
    {
        clearAllAdds(self);
        respawnAdds(self);
        resetSelf(self);
    }
    public void clearAllAdds(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getCreaturesInRange(getLocation(self), 400);
        if (objects == null || objects.length == 0)
        {
            doLogging("clearAllAdds", "There are no objects in range");
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if (hasObjVar(objects[i], "boss"))
            {
                obj_id parent = getObjIdObjVar(objects[i], "boss");
                if (parent == self)
                {
                    trial.cleanupNpc(objects[i]);
                }
            }
        }
    }
    public void respawnAdds(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        messageTo(parent, "spawnGuards", null, 2, false);
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
    }
    public int beginAttack(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id[] players = trial.getValidTargetsInRadius(self, 100.0f);
        if (players == null || players.length == 0)
        {
            doLogging("beginAttack", "Found no players to attack");
            return SCRIPT_CONTINUE;
        }
        obj_id toAttack = trial.getClosest(self, players);
        if (!isIdValid(toAttack))
        {
            doLogging("beginAttack", "player toAttack, was invalid");
            return SCRIPT_CONTINUE;
        }
        startCombat(self, toAttack);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/event_two_boss/" + section, message);
        }
    }
}

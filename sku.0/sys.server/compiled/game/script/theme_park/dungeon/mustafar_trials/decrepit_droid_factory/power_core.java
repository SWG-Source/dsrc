package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;
import script.library.create;
import script.library.ai_lib;

public class power_core extends script.base_script
{
    public power_core()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_ACTIVATE_CORE = new string_id(DECREPIT_STF, "activate_core");
    public static final string_id SID_CORE_ACTIVE = new string_id(DECREPIT_STF, "core_active");
    public static final string_id SID_GUARDIAN_SPAWNED = new string_id(DECREPIT_STF, "guardian_spawned");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setPowerCoreState(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        boolean isEnabled = trial.isPowerCoreOn(self);
        if (!isEnabled)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_CORE);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_CORE_ACTIVE);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!trial.isPowerCoreOn(self))
            {
                activateCore(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateCore(obj_id core) throws InterruptedException
    {
        trial.setPowerCoreState(core, true);
        obj_id[] players = trial.getPlayersInDungeon(core);
        utils.sendSystemMessage(players, SID_GUARDIAN_SPAWNED);
        spawnGuardian(trial.getTop(core));
    }
    public void spawnGuardian(obj_id dungeon) throws InterruptedException
    {
        obj_id[] spawnPoint = trial.getObjectsInDungeonWithObjVar(dungeon, "patrol_wp");
        if (spawnPoint == null || spawnPoint.length == 0)
        {
            return;
        }
        location spawnLoc = getLocation(getSelf());
        for (int i = 0; i < spawnPoint.length; i++)
        {
            String wp = getStringObjVar(spawnPoint[i], "patrol_wp");
            if (wp.equals("controlFour"))
            {
                spawnLoc = getLocation(spawnPoint[i]);
            }
        }
        spawnLoc.x = (float)(spawnLoc.x - 3.4);
        spawnLoc.z = (float)(spawnLoc.z - 1.4);
        obj_id guardian = create.object("som_decrepit_guardian", spawnLoc);
        if (!isIdValid(guardian) || !exists(guardian))
        {
            return;
        }
        attachScript(guardian, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.decrepit_guardian");
        ai_lib.setDefaultCalmBehavior(guardian, ai_lib.BEHAVIOR_SENTINEL);
        setYaw(guardian, -90);
    }
}

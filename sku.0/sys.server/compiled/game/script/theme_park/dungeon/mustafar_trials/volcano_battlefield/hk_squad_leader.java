package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.trial;

public class hk_squad_leader extends script.base_script
{
    public hk_squad_leader()
    {
    }
    public static final String GUARD = "som_volcano_final_squadmember";
    public static final boolean LOGGING = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        generateSquad(self);
        trial.setHp(self, trial.HP_VOLCANO_HK_SQUAD_LEADER);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "sl_guard");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        if (!hasObjVar(self, "guardList"))
        {
            doLogging("OnDestroy", "Can not find the guardList objvar");
            return SCRIPT_CONTINUE;
        }
        obj_id[] guards = getObjIdArrayObjVar(self, "guardList");
        for (int i = 0; i < guards.length; i++)
        {
            messageTo(guards[i], "leaderDied", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void generateSquad(obj_id self) throws InterruptedException
    {
        obj_id eventController = trial.getParent(self);
        String[] offSet = 
        {
            "-3:0",
            "-5:0",
            "-7:0",
            "-3:3",
            "-5:3",
            "-7:3"
        };
        location here = getLocation(self);
        obj_id[] guardList = new obj_id[6];
        for (int i = 0; i < offSet.length; i++)
        {
            String[] parse = split(offSet[i], ':');
            float locX = here.x + utils.stringToFloat(parse[0]);
            float locZ = here.z + utils.stringToFloat(parse[1]);
            location spawnLoc = new location(locX, here.y, locZ, here.area, here.cell);
            obj_id item = create.object(GUARD, spawnLoc);
            if (!isIdValid(item))
            {
                doLogging("spawnActors", "Tried to create invalid item(" + GUARD + ")");
                return;
            }
            trial.setParent(eventController, item, false);
            attachScript(item, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_squad_member");
            guardList[i] = item;
            setYaw(item, 25);
            ai_lib.setDefaultCalmBehavior(item, ai_lib.BEHAVIOR_SENTINEL);
        }
        if (guardList == null || guardList.length == 0)
        {
            doLogging("generateSquad", "Failed to populate the guardList");
            return;
        }
        setObjVar(self, "guardList", guardList);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/hk_squad_leader/" + section, message);
        }
    }
}

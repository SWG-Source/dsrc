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

public class hk_final extends script.base_script
{
    public hk_final()
    {
    }
    public static final String BOSS = "som_volcano_final_hk47";
    public static final String SQUAD_LEADER = "som_volcano_final_squadleader";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        spawnEvent(self);
        return SCRIPT_CONTINUE;
    }
    public int activateEvent(obj_id self, dictionary params) throws InterruptedException
    {
        setTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public void setTriggerVolume(obj_id self) throws InterruptedException
    {
        if (!hasTriggerVolume(self, "activateVolume"))
        {
            createTriggerVolume("activateVolume", 95, true);
        }
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (isPlayer(breacher) && !isIncapacitated(breacher))
        {
            if (volumeName.equals("activateVolume"))
            {
                activateEncounter(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateEncounter(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnSquadLeaders", null, 0, false);
        messageTo(trial.getParent(self), "landYt", null, 0, false);
        dictionary dict = new dictionary();
        dict.put("hk", utils.getObjIdScriptVar(self, "eventBoss"));
        messageTo(trial.getParent(self), "doHkTaunt", dict, 0, false);
        removeTriggerVolume("activateVolume");
    }
    public void spawnEvent(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id eventBoss = create.object(BOSS, here);
        if (!isIdValid(eventBoss))
        {
            doLogging("spawnGuards", "Failed to generate boss");
            return;
        }
        setYaw(eventBoss, 25);
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_final_boss");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        utils.setScriptVar(self, "eventBoss", eventBoss);
        String[] offSet = 
        {
            "55:34",
            "9:48",
            "-18:18",
            "23:-30",
            "15:-1",
            "-18:3",
            "-2:46",
            "27:33",
            "39:4",
            "-21:28"
        };
        for (int i = 0; i < offSet.length; i++)
        {
            String[] parse = split(offSet[i], ':');
            float locX = here.x + utils.stringToFloat(parse[0]);
            float locZ = here.z + utils.stringToFloat(parse[1]);
            location spawnLoc = new location(locX, here.y, locZ, here.area, here.cell);
            obj_id item = createObject(trial.WP_OBJECT, spawnLoc);
            if (!isIdValid(item))
            {
                doLogging("spawnEvent", "Tried to create invalid item(" + trial.WP_OBJECT + ")");
                return;
            }
            trial.markAsTempObject(item, true);
            trial.setParent(self, item, false);
            if (i < 4)
            {
                setObjVar(item, "hk_beetle", true);
            }
            else if (i < 6)
            {
                setObjVar(item, "hk_walker", true);
            }
            else 
            {
                setObjVar(item, "hk_septipod", true);
            }
        }
    }
    public int spawnSquadLeaders(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "sl_guard", 0);
        utils.setScriptVar(self, "corpseIdx", 0);
        location here = getLocation(self);
        String[] guardOffset = 
        {
            "-1:32",
            "32:25"
        };
        for (int i = 0; i < guardOffset.length; i++)
        {
            String[] parse = split(guardOffset[i], ':');
            float guardX = here.x + utils.stringToFloat(parse[0]);
            float guardZ = here.z + utils.stringToFloat(parse[1]);
            location guardLoc = new location(guardX, here.y, guardZ, here.area, here.cell);
            obj_id eventGuard = create.object(SQUAD_LEADER, guardLoc);
            if (!isIdValid(eventGuard))
            {
                doLogging("spawnEvent", "Tried to create invalid item(" + SQUAD_LEADER + ")");
                return SCRIPT_CONTINUE;
            }
            trial.setParent(self, eventGuard, false);
            attachScript(eventGuard, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_squad_leader");
            ai_lib.setDefaultCalmBehavior(eventGuard, ai_lib.BEHAVIOR_SENTINEL);
            setYaw(eventGuard, 25);
        }
        return SCRIPT_CONTINUE;
    }
    public int eventMobDied(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("boss"))
        {
            winEncounter(self);
        }
        if (type.equals("sl_guard"))
        {
            int guard = 0;
            if (utils.hasScriptVar(self, "sl_guard"))
            {
                guard = utils.getIntScriptVar(self, "sl_guard");
            }
            guard += 1;
            utils.setScriptVar(self, "sl_guard", guard);
            placeGuardCorpse(self);
            if (guard == 14)
            {
                activateHK(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void placeGuardCorpse(obj_id self) throws InterruptedException
    {
        String[] offSet = 
        {
            "7:26",
            "-10:34",
            "0:18",
            "-5:46",
            "10:35",
            "26:32",
            "-18:23",
            "6:39",
            "24:38",
            "18:27",
            "31:19",
            "5:26",
            "11:21",
            "-3:31"
        };
        int idx = 0;
        if (utils.hasScriptVar(self, "corpseIdx"))
        {
            idx = utils.getIntScriptVar(self, "corpseIdx");
        }
        location here = getLocation(self);
        String[] parse = split(offSet[idx], ':');
        float locX = here.x + utils.stringToFloat(parse[0]);
        float locZ = here.z + utils.stringToFloat(parse[1]);
        idx += 1;
        utils.setScriptVar(self, "corpseIdx", idx);
        location spawnLoc = new location(locX, here.y, locZ, here.area, here.cell);
        obj_id corpse = create.object("som_volcano_final_squadmember", spawnLoc);
        trial.prepareCorpse(corpse);
        setPosture(corpse, POSTURE_DEAD);
        trial.markAsHkCorpse(corpse);
        setInvulnerable(corpse, true);
        trial.setParent(utils.getObjIdScriptVar(self, "eventBoss"), corpse, false);
    }
    public void activateHK(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "eventBoss"))
        {
            doLogging("reactivateBoss", "Do not have boss script var");
            return;
        }
        obj_id eventBoss = utils.getObjIdScriptVar(self, "eventBoss");
        if (!isIdValid(eventBoss) || !exists(eventBoss))
        {
            doLogging("reactivateBoss", "Boss was invalid or does not exist");
        }
        messageTo(eventBoss, "activate", null, 3, false);
    }
    public void winEncounter(obj_id self) throws InterruptedException
    {
        obj_id top = trial.getParent(self);
        messageTo(top, "hkDefeated", null, 0, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/hk_final/" + section, message);
        }
    }
}

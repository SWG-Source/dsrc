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

public class event_three extends script.base_script
{
    public event_three()
    {
    }
    public static final String boss = "som_volcano_three_forward_commander";
    public static final String GUARD = "som_volcano_three_hk77";
    public static final String risen = "som_volcano_three_risen_commander";
    public static final String INDEX = "guardIndex";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        spawnBoss(self);
        messageTo(self, "spawnGuards", null, 2, false);
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
            createTriggerVolume("activateVolume", 45, true);
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
        dictionary dict = trial.getSessionDict(self);
        dict.put(INDEX, 0);
        messageTo(self, "doGuardAttackCycle", dict, 1, false);
    }
    public void spawnBoss(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id eventBoss = create.object(boss, here);
        if (!isIdValid(eventBoss))
        {
            doLogging("spawnGuards", "Failed to generate boss");
            return;
        }
        setYaw(eventBoss, 180);
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_three_boss");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        utils.setScriptVar(self, "boss", eventBoss);
    }
    public int spawnGuards(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        String[] offSet = 
        {
            "-6:-10",
            "-3:-10",
            "0:-10",
            "3:-10",
            "6:-10",
            "-6:-8",
            "-3:-8",
            "0:-8",
            "3:-8",
            "6:-8",
            "-6:-6",
            "-3:-6",
            "0:-6",
            "3:-6",
            "6:-6"
        };
        obj_id[] guards = new obj_id[15];
        for (int i = 0; i < offSet.length; i++)
        {
            String[] parse = split(offSet[i], ':');
            float locX = here.x + utils.stringToFloat(parse[0]);
            float locZ = here.z + utils.stringToFloat(parse[1]);
            location spawnLoc = new location(locX, here.y, locZ, here.area, here.cell);
            guards[i] = create.object(GUARD, spawnLoc);
            if (isIdValid(guards[i]))
            {
                ai_lib.setDefaultCalmBehavior(guards[i], ai_lib.BEHAVIOR_SENTINEL);
                attachScript(guards[i], "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_three_guard");
                setYaw(guards[i], 180);
                trial.setParent(self, guards[i], false);
                setObjVar(guards[i], "boss", utils.getObjIdScriptVar(self, "boss"));
            }
        }
        utils.setScriptVar(self, "guards", guards);
        utils.setScriptVar(self, "deadBoss", 0);
        utils.setScriptVar(self, "deadGuards", 0);
        utils.setScriptVar(self, "corpseIdx", 0);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int doGuardAttackCycle(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            doLogging("doGuardAttackCycle", "Passed session did not equal current session");
            return SCRIPT_CONTINUE;
        }
        obj_id[] guards = utils.getObjIdArrayScriptVar(self, "guards");
        if (guards == null || guards.length == 0)
        {
            doLogging("doGuardAttackCycle", "Guard array was empty");
            return SCRIPT_CONTINUE;
        }
        int index = params.getInt(INDEX);
        if (index < guards.length)
        {
            if (exists(guards[index]))
            {
                messageTo(guards[index], "beginAttack", null, 0, false);
                index++;
                dictionary dict = trial.getSessionDict(self);
                dict.put(INDEX, index);
                messageTo(self, "doGuardAttackCycle", dict, 10, false);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                dictionary dict = trial.getSessionDict(self);
                index++;
                dict.put(INDEX, index);
                messageTo(self, "doGuardAttackCycle", dict, 0, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int eventMobDied(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("boss"))
        {
            int deadBoss = 0;
            if (utils.hasScriptVar(self, "deadBoss"))
            {
                deadBoss = utils.getIntScriptVar(self, "deadBoss");
            }
            deadBoss += 1;
            if (deadBoss == 16)
            {
                winEncounter(self);
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "deadBoss", deadBoss);
            checkForLastBoss(self);
        }
        if (type.equals("guard"))
        {
            int deadGuards = 0;
            if (utils.hasScriptVar(self, "deadGuards"))
            {
                deadGuards = utils.getIntScriptVar(self, "deadGuards");
            }
            deadGuards += 1;
            placeGuardCorpse(self);
            if (deadGuards == 15)
            {
                activateBoss(self);
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "deadGuards", deadGuards);
        }
        return SCRIPT_CONTINUE;
    }
    public void winEncounter(obj_id self) throws InterruptedException
    {
        obj_id top = trial.getParent(self);
        if (!isIdValid(top))
        {
            doLogging("winEvent", "Parent ID is not valid");
            return;
        }
        messageTo(top, "eventDefeated", null, 0, false);
    }
    public void placeGuardCorpse(obj_id self) throws InterruptedException
    {
        String[] offSet = 
        {
            "7:4",
            "2:-6",
            "0:-11",
            "-5:1",
            "14:-13",
            "-18:-32",
            "-18:-23",
            "6:-2",
            "16:-8",
            "18:-14",
            "-18:4",
            "5:-4",
            "11:4",
            "-3:-12",
            "7:-29"
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
        obj_id corpse = create.object(GUARD, spawnLoc);
        ai_lib.setDefaultCalmBehavior(corpse, ai_lib.BEHAVIOR_SENTINEL);
        setPosture(corpse, POSTURE_DEAD);
        trial.prepareCorpse(corpse);
        trial.markAsVolcanoCorpse(corpse);
        setInvulnerable(corpse, true);
        setObjVar(corpse, "boss", utils.getObjIdScriptVar(self, "boss"));
    }
    public void activateBoss(obj_id self) throws InterruptedException
    {
        obj_id eventBoss = utils.getObjIdScriptVar(self, "boss");
        if (!isIdValid(eventBoss) || !exists(eventBoss))
        {
            doLogging("activateBoss", "Boss ID is invalid");
            return;
        }
        messageTo(eventBoss, "beginAttack", null, 3, false);
        messageTo(self, "doResEffect", trial.getSessionDict(self), 10, false);
    }
    public int doResEffect(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] boss = trial.getObjectsInRangeWithScriptVar(self, trial.VOLCANO_THREE_IS_BOSS, 400.0f);
        obj_id[] corpseList = trial.getObjectsInRangeWithScriptVar(self, trial.VOLCANO_THREE_IS_CORPSE, 400.0f);
        if (boss == null || boss.length == 0)
        {
            checkForLastBoss(self);
            return SCRIPT_CONTINUE;
        }
        Vector liveBoss = new Vector();
        liveBoss.setSize(0);
        for (int i = 0; i < boss.length; i++)
        {
            if (!isDead(boss[i]))
            {
                utils.addElement(liveBoss, boss[i]);
            }
        }
        if (liveBoss == null || liveBoss.size() == 0)
        {
            checkForLastBoss(self);
            return SCRIPT_CONTINUE;
        }
        if (liveBoss != null)
        {
            boss = new obj_id[liveBoss.size()];
            liveBoss.toArray(boss);

        }
        if (corpseList == null || corpseList.length == 0)
        {
            trial.bumpSession(self);
            return SCRIPT_CONTINUE;
        }
        performRez(self, boss, corpseList);
        return SCRIPT_CONTINUE;
    }
    public void performRez(obj_id self, obj_id[] bossList, obj_id[] corpseList) throws InterruptedException
    {
        for (int i = 0; i < bossList.length && i < corpseList.length; i++)
        {
            if (isIdValid(bossList[i]) && exists(bossList[i]) && !isDead(bossList[i]))
            {
                location playLoc = getLocation(bossList[i]);
                playClientEffectLoc(bossList[i], trial.PRT_DROID_REVIVE, playLoc, 3.0f);
            }
            if (isIdValid(corpseList[i]) && exists(corpseList[i]))
            {
                location corpseLoc = getLocation(corpseList[i]);
                playClientEffectLoc(corpseList[i], trial.PRT_DROID_REVIVE, corpseLoc, 3.0f);
                destroyObject(corpseList[i]);
                obj_id eventBoss = create.object(risen, corpseLoc);
                if (!isIdValid(eventBoss))
                {
                    doLogging("performRez", "Failed to generate a resurrected boss");
                    return;
                }
                setYaw(eventBoss, rand(0, 360));
                attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_three_boss_revived");
                ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
                trial.setParent(self, eventBoss, false);
                setObjVar(eventBoss, "boss", utils.getObjIdScriptVar(self, "boss"));
            }
        }
        messageTo(self, "doResEffect", trial.getSessionDict(self), 35, false);
    }
    public void checkForLastBoss(obj_id self) throws InterruptedException
    {
        obj_id[] boss = trial.getObjectsInRangeWithScriptVar(self, trial.VOLCANO_THREE_IS_BOSS, 400.0f);
        obj_id[] corpseList = trial.getObjectsInRangeWithScriptVar(self, trial.VOLCANO_THREE_IS_CORPSE, 400.0f);
        Vector bossList = new Vector();
        bossList.setSize(0);
        for (int i = 0; i < boss.length; i++)
        {
            if (!isDead(boss[i]))
            {
                utils.addElement(bossList, boss[i]);
            }
        }
        if (bossList != null && bossList.size() > 0 && corpseList != null && corpseList.length > 0)
        {
            if (bossList != null && bossList.size() > 0)
            {
                if (bossList != null)
                {
                    boss = new obj_id[bossList.size()];
                    bossList.toArray(boss);

                }
                performRez(self, boss, corpseList);
                return;
            }
        }
        if (bossList == null || bossList.size() == 0)
        {
            performSoloCorpseRez(self, corpseList[0]);
        }
    }
    public void performSoloCorpseRez(obj_id self, obj_id corpse) throws InterruptedException
    {
        playClientEffectObj(corpse, trial.PRT_DROID_REVIVE, corpse, "");
        location corpseLoc = getLocation(corpse);
        obj_id eventBoss = create.object(risen, corpseLoc);
        if (!isIdValid(eventBoss))
        {
            doLogging("performSoloCorpseRez", "Failed to generate a resurrected boss");
            return;
        }
        setYaw(eventBoss, rand(0, 360));
        attachScript(eventBoss, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_three_boss_revived");
        ai_lib.setDefaultCalmBehavior(eventBoss, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventBoss, false);
        trial.bumpSession(self);
        destroyObject(corpse);
        messageTo(self, "doResEffect", trial.getSessionDict(self), 18, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_three/" + section, message);
        }
    }
}

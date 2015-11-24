package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.combat;
import script.library.movement;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class echo_theater extends script.base_script
{
    public echo_theater()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "posture"))
        {
            obj_id npc = self;
            setNpcPosture(npc);
        }
        if (hasObjVar(self, "void_damage"))
        {
            buff.applyBuff(self, "void_damage");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHateTargetAdded(obj_id self, obj_id target) throws InterruptedException
    {
        if (isPlayer(target))
        {
            if (hasObjVar(self, "void_damage"))
            {
                buff.removeBuff(self, "void_damage");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int r1_st_death(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] cannon = trial.getObjectsInCellWithObjVar(trial.getTop(self), "r1", "cannon_1");
        obj_id[] players = trial.getPlayersInCell(trial.getTop(self), "r1");
        if (players == null || players.length == 0)
        {
        }
        else 
        {
            createClientProjectileObjectToObject(players[0], "object/weapon/ranged/turret/shared_turret_energy.iff", cannon[0], "", self, "", 150.0f, 1.0f, false, 0, 0, 0, 0);
        }
        messageTo(self, "suicide", null, .25f, false);
        return SCRIPT_CONTINUE;
    }
    public int r2_st_death(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] cannon = trial.getObjectsInCellWithObjVar(trial.getTop(self), "r1", "cannon_2");
        obj_id[] players = trial.getPlayersInCell(trial.getTop(self), "r1");
        if (players == null || players.length == 0)
        {
        }
        else 
        {
            createClientProjectileObjectToObject(players[0], "object/weapon/ranged/turret/shared_turret_energy.iff", cannon[0], "", self, "", 150.0f, 1.0f, false, 0, 0, 0, 0);
        }
        messageTo(self, "suicide", null, .25f, false);
        return SCRIPT_CONTINUE;
    }
    public int suicide(obj_id self, dictionary params) throws InterruptedException
    {
        kill(self);
        messageTo(self, "handleDelayedCleanup", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int r1_st_show(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] cannon = trial.getObjectsInCellWithObjVar(trial.getTop(self), "r1", "cannon_1");
        obj_id[] players = trial.getPlayersInCell(trial.getTop(self), "r1");
        if (players == null || players.length == 0)
        {
        }
        else 
        {
            createClientProjectileObjectToObject(players[0], "object/weapon/ranged/turret/shared_turret_energy.iff", cannon[0], "", self, "", 150.0f, 1.0f, false, 0, 0, 0, 0);
        }
        return SCRIPT_CONTINUE;
    }
    public int r2_st_show(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] cannon = trial.getObjectsInCellWithObjVar(trial.getTop(self), "r1", "cannon_2");
        obj_id[] players = trial.getPlayersInCell(trial.getTop(self), "r1");
        if (players == null || players.length == 0)
        {
        }
        else 
        {
            createClientProjectileObjectToObject(players[0], "object/weapon/ranged/turret/shared_turret_energy.iff", cannon[0], "", self, "", 150.0f, 1.0f, false, 0, 0, 0, 0);
        }
        return SCRIPT_CONTINUE;
    }
    public int kill_scott(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] scott = trial.getObjectsInInstanceBySpawnId(trial.getParent(self), "tg_1");
        if (scott == null || scott.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        String[] cells = 
        {
            "r1",
            "r2"
        };
        obj_id[] players = trial.getPlayersInCellList(self, cells);
        messageTo(scott[0], "suicide", null, 0.25f, false);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        createClientProjectileObjectToObject(players[0], "object/weapon/ranged/rifle/shared_quest_rifle_flame_thrower.iff", self, "", scott[0], "", 75.0f, 2.0f, false, 0, 0, 0, 0);
        return SCRIPT_CONTINUE;
    }
    public int kill_downey(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] downey = trial.getObjectsInInstanceBySpawnId(trial.getParent(self), "tg_2");
        if (downey == null || downey.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        String[] cells = 
        {
            "r1",
            "r2"
        };
        obj_id[] players = trial.getPlayersInCellList(self, cells);
        messageTo(downey[0], "suicide", null, 0.25f, false);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        createClientProjectileObjectToObject(players[0], "object/weapon/ranged/rifle/shared_quest_rifle_flame_thrower.iff", self, "", downey[0], "", 75.0f, 2.0f, false, 0, 0, 0, 0);
        return SCRIPT_CONTINUE;
    }
    public void setNpcPosture(obj_id npc) throws InterruptedException
    {
        String posture = "";
        if (!hasObjVar(npc, "posture"))
        {
            return;
        }
        posture = getStringObjVar(npc, "posture");
        if (posture.equals("prone"))
        {
            setPosture(npc, POSTURE_PRONE);
        }
        if (posture.equals("kneel"))
        {
            setPosture(npc, POSTURE_CROUCHED);
        }
        if (posture.equals("sit"))
        {
            setPosture(npc, POSTURE_SITTING);
        }
        if (posture.equals("sneak"))
        {
            setPosture(npc, POSTURE_SNEAKING);
        }
        if (posture.equals("block"))
        {
            setPosture(npc, POSTURE_BLOCKING);
        }
        if (posture.equals("climb"))
        {
            setPosture(npc, POSTURE_CLIMBING);
        }
        if (posture.equals("fly"))
        {
            setPosture(npc, POSTURE_FLYING);
        }
        if (posture.equals("lay_down"))
        {
            setPosture(npc, POSTURE_LYING_DOWN);
        }
        if (posture.equals("sit"))
        {
            setPosture(npc, POSTURE_SITTING);
        }
        if (posture.equals("sit"))
        {
            setPosture(npc, POSTURE_SITTING);
        }
        if (posture.equals("skill_anim"))
        {
            setPosture(npc, POSTURE_SKILL_ANIMATING);
        }
        if (posture.equals("drive"))
        {
            setPosture(npc, POSTURE_DRIVING_VEHICLE);
        }
        if (posture.equals("ride"))
        {
            setPosture(npc, POSTURE_RIDING_CREATURE);
        }
        if (posture.equals("knock_down"))
        {
            setPosture(npc, POSTURE_KNOCKED_DOWN);
        }
        if (posture.equals("incap"))
        {
            setPosture(npc, POSTURE_INCAPACITATED);
        }
        if (posture.equals("dead"))
        {
            setPosture(npc, POSTURE_DEAD);
        }
        if (posture.equals("count"))
        {
            setPosture(npc, POSTURE_COUNT);
        }
    }
    public int startRunning(obj_id self, dictionary params) throws InterruptedException
    {
        float rate = 1.0f;
        removeObjVar(self, "run");
        setObjVar(self, "run", rate);
        setMovementRun(self);
        setMovementPercent(self, rate);
        setBaseRunSpeed(self, 6.0f);
        return SCRIPT_CONTINUE;
    }
    public int startWalking(obj_id self, dictionary params) throws InterruptedException
    {
        movement.performWalk(self);
        return SCRIPT_CONTINUE;
    }
}

package script.poi.tusken_attack;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.scenario;
import script.library.poi;
import script.library.pclib;
import script.ai.ai_combat;

public class beast extends script.poi.base.scenario_actor
{
    public beast()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiTuskenAttack Antagonist";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(poiMaster, scenario.HANDLER_ACTOR_DEATH, null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int vocalizeEndCombat(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isDead(self) || ai_lib.isInCombat(self))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id[] players = getPlayerCreaturesInRange(self, 80f);
        for (int i = 0; i < players.length; i++)
        {
            if (isIncapacitated(players[i]) && getPosture(players[i]) != POSTURE_DEAD)
            {
                pclib.coupDeGrace(players[i], self);
                return SCRIPT_OVERRIDE;
            }
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
        for (int i = 0; i < mediators.length; i++)
        {
            if (isIncapacitated(mediators[i]) || ai_lib.isDead(mediators[i]))
            {
                continue;
            }
            startCombat(self, mediators[i]);
            return SCRIPT_CONTINUE;
        }
        if (getPosture(self) == POSTURE_UPRIGHT)
        {
            String skeleton = dataTableGetString("datatables/ai/species.iff", ai_lib.aiGetSpecies(self), "Skeleton");
            if (skeleton.equals("human"))
            {
                doAnimationAction(self, "celebrate");
            }
            else 
            {
                doAnimationAction(self, "vocalize");
            }
        }
        messageTo(self, "postCombatPathHome", null, rand(10, 20), false);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_OVERRIDE;
    }
    public int OnTargeted(obj_id self, obj_id attacker) throws InterruptedException
    {
        if (hasObjVar(self, "attackedByElse"))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "attackedByElse", 1);
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
        for (int i = 0; i < mediators.length; i++)
        {
            if (mediators[i] == attacker)
            {
                return SCRIPT_CONTINUE;
            }
        }
        poi.quickSay(self, "a_whatthehell");
        messageTo(self, "startAttack", null, 0, false);
        return SCRIPT_CONTINUE;
    }
}

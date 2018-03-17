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
import script.poi.tusken_attack.master;

public class antagonist extends script.poi.base.scenario_actor
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiTuskenAttack Antagonist";
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "armWeapon", null, 2, false);
        detachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int armWeapon(obj_id self, dictionary params) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
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
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (breacher == self)
        {
            return SCRIPT_OVERRIDE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isMob(breacher))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isMonster(breacher))
        {
            return SCRIPT_OVERRIDE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
            if (!poi.isNpcTagged(self, "antagonist_0"))
            {
                return SCRIPT_OVERRIDE;
            }
            if (hasObjVar(self, "warStarted"))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "warStarted", true);
            obj_id m = poi.findObject("mediator_0");
            if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "breacher", breacher);
            messageTo(self, "startAttack", null, 15, false);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id breacher = getObjIdObjVar(self, "breacher");
        float dist = getDistance(self, breacher);
        boolean bclose = false;
        if (dist < 20)
        {
            bclose = true;
        }
        int count = getIntObjVar(poiMaster, master.ANTAGONIST_COUNT);
        int k = 0;
        obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
        obj_id[] antagonists = scenario.getTeamMembers(poiMaster, "antagonist");
        if ((mediators != null) && (antagonists != null))
        {
            for (int i = 0; i < mediators.length; i++)
            {
                if (mediators[i] == null)
                {
                    continue;
                }
                setObjVar(mediators[i], "attackedByElse", 1);
                for (int j = 0; j < antagonists.length; j++)
                {
                    if (antagonists[j] == null)
                    {
                        continue;
                    }
                    if (j < count)
                    {
                        ai_lib.addToMentalStateToward(antagonists[j], mediators[i], FEAR, 50f);
                        ai_lib.addToMentalStateToward(antagonists[j], mediators[i], ANGER, 100f);
                        ai_lib.addToMentalStateToward(mediators[i], antagonists[j], FEAR, 50f);
                        ai_lib.addToMentalStateToward(mediators[i], antagonists[j], ANGER, 100f);
                    }
                    if (i == j)
                    {
                        startCombat(antagonists[j], mediators[i]);
                        startCombat(mediators[i], antagonists[j]);
                    }
                    if (k == 0)
                    {
                        setObjVar(antagonists[i], "attackedByElse", 1);
                    }
                }
                k++;
            }
        }
        if (bclose)
        {
            ai_lib.addToMentalStateToward(self, breacher, FEAR, 50f);
            startCombat(self, breacher);
        }
        return SCRIPT_CONTINUE;
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
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
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
        messageTo(self, "startAttack", null, 0, false);
        return SCRIPT_CONTINUE;
    }
}

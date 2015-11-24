package script.poi.gangwar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.combat;
import script.library.poi;
import script.library.scenario;
import script.library.ai_lib;
import script.library.pclib;
import script.ai.ai_combat;

public class antagonist extends script.poi.base.scenario_actor
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiGangWar Antagonist";
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
        attachScript(self, SCRIPT_CONVERSE);
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
            messageTo(self, "firstInsult", null, 3, false);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int firstInsult(obj_id self, dictionary params) throws InterruptedException
    {
        poi.quickSay(self, "a_insult1");
        messageTo(self, "firstInsultResponse", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int firstInsultResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(m, "m_insult1_response");
        messageTo(self, "secondInsult", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int secondInsult(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id a = poi.findObject("antagonist_0");
        if ((a == null) || (a == obj_id.NULL_ID) || ai_lib.isInCombat(a))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(self, "a_insult2");
        messageTo(self, "secondInsultResponse", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int secondInsultResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(m, "m_insult2_response");
        messageTo(self, "thirdInsult", null, 7, false);
        return SCRIPT_CONTINUE;
    }
    public int thirdInsult(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id a = poi.findObject("antagonist_0");
        if ((a == null) || (a == obj_id.NULL_ID) || ai_lib.isInCombat(a))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(self, "a_insult3");
        messageTo(self, "thirdInsultResponse", null, 7, false);
        String taunts = getStringObjVar(poi.getBaseObject(self), "TAUNTS");
        if (taunts.equals("true"))
        {
            messageTo(self, "thirdInsultSocial", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int thirdInsultSocial(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (1780871594), null, "taunt", COMMAND_PRIORITY_DEFAULT);
        obj_id a = poi.findObject("antagonist_3");
        if ((a == null) || (a == obj_id.NULL_ID) || ai_lib.isInCombat(a))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(a, (1780871594), null, "laugh", COMMAND_PRIORITY_DEFAULT);
        a = poi.findObject("antagonist_1");
        if ((a == null) || (a == obj_id.NULL_ID) || ai_lib.isInCombat(a))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(a, (1780871594), null, "cackle", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int thirdInsultResponse(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(m, "m_insult3_response");
        messageTo(self, "lastInsult", null, 7, false);
        messageTo(self, "thirdInsultResponseSocial", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int thirdInsultResponseSocial(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(m, (1780871594), null, "snarl", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int lastInsult(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id a = poi.findObject("antagonist_0");
        if ((a == null) || (a == obj_id.NULL_ID) || ai_lib.isInCombat(a))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(self, "a_insult4");
        messageTo(self, "startAttack", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id m = poi.findObject("mediator_0");
        if ((m == null) || (m == obj_id.NULL_ID) || ai_lib.isInCombat(m))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id battacker = poi.findObject("antagonist_6");
        obj_id breacher = getObjIdObjVar(self, "breacher");
        float dist = getDistance(self, battacker);
        boolean bclose = false;
        if (dist < 20)
        {
            bclose = true;
        }
        int last = 7;
        if (bclose)
        {
            last = 6;
        }
        for (int i = 0; i < last; i++)
        {
            obj_id enemy = poi.findObject("mediator_" + i);
            if ((enemy == null) || (enemy == obj_id.NULL_ID))
            {
                continue;
            }
            obj_id attacker = poi.findObject("antagonist_" + i);
            if ((attacker == null) || (attacker == obj_id.NULL_ID))
            {
                continue;
            }
            ai_lib.addToMentalStateToward(attacker, enemy, FEAR, 50f);
            startCombat(attacker, enemy);
            ai_lib.addToMentalStateToward(enemy, attacker, FEAR, 50f);
            startCombat(enemy, attacker);
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        int k = 0;
        obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
        obj_id[] antagonists = scenario.getTeamMembers(poiMaster, "antagonists");
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
                    ai_lib.addToMentalStateToward(mediators[i], antagonists[j], FEAR, 50f);
                    ai_lib.addToMentalStateToward(mediators[i], antagonists[j], ANGER, 100f);
                    ai_lib.addToMentalStateToward(antagonists[j], mediators[i], FEAR, 50f);
                    ai_lib.addToMentalStateToward(antagonists[j], mediators[i], ANGER, 100f);
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
            poi.quickSay(battacker, "a_whoareyou");
            ai_lib.addToMentalStateToward(battacker, breacher, FEAR, 50f);
            startCombat(battacker, breacher);
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
        poi.quickSay(self, "a_whatthehell");
        messageTo(self, "startAttack", null, 0, false);
        return SCRIPT_CONTINUE;
    }
}

package script.simulator;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.utils;

public class combat_simulator_actor extends script.base_script
{
    public combat_simulator_actor()
    {
    }
    public static boolean debug = false;
    public int prepareForCombat(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id enemy = getObjIdObjVar(self, "combat_simulator.enemy");
        faceTo(self, enemy);
        setState(self, STATE_COMBAT, true);
        setState(self, STATE_CA_NORMAL, true);
        setTarget(self, enemy);
        return SCRIPT_CONTINUE;
    }
    public int startCombat(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id enemy = getObjIdObjVar(self, "combat_simulator.enemy");
        if (debug)
        {
            debugSpeakMsg(self, "Got message to start combat against " + enemy);
        }
        if (!hasObjVar(self, "combat_simulator.is_creature"))
        {
            messageTo(self, "queueCommandLoop", null, 0, false);
        }
        else 
        {
            addHate(self, enemy, 0.0f);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("actorsDumpInfo"))
        {
            obj_id owner = getObjIdObjVar(self, "combat_simulator.owner");
            obj_id enemy = getObjIdObjVar(self, "combat_simulator.enemy");
            String[] queueCommands = getStringArrayObjVar(self, "combat_simulator.queue_commands");
            if (queueCommands == null)
            {
                queueCommands = new String[0];
            }
            int queueCommandIndex = getIntObjVar(self, "combat_simulator.queue_command_index");
            int numberOfAttacks = getIntObjVar(self, "combat_simulator.number_of_attacks");
            boolean hasCombatActions = checkForCombatActions(self);
            debugSpeakMsg(self, "Self: " + self + ", Enemy: " + enemy + ", Owner: " + owner + ", NumCommands: " + queueCommands.length + ", Index: " + queueCommandIndex + ", numAttacks " + numberOfAttacks + ", hasCombatActions " + hasCombatActions);
        }
        else if (text.equals("debug on"))
        {
            debug = true;
        }
        else if (text.equals("debug off"))
        {
            debug = false;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            debugSpeakMsg(self, "Entered combat");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            debugSpeakMsg(self, "Exited combat");
        }
        return SCRIPT_CONTINUE;
    }
    public int queueCommandLoop(obj_id self, dictionary params) throws InterruptedException
    {
        queueNewCommand(self);
        messageTo(self, "queueCommandLoop", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void queueNewCommand(obj_id self) throws InterruptedException
    {
        if (checkForCombatActions(self))
        {
            return;
        }
        obj_id enemy = getObjIdObjVar(self, "combat_simulator.enemy");
        String[] queueCommands = getStringArrayObjVar(self, "combat_simulator.queue_commands");
        int queueCommandIndex = getIntObjVar(self, "combat_simulator.queue_command_index");
        if (debug)
        {
            debugSpeakMsg(self, "Queuing command " + queueCommands[queueCommandIndex] + " against " + enemy);
        }
        if (queueCommand(self, getStringCrc((toLower(queueCommands[queueCommandIndex])).toString()), enemy, "", COMMAND_PRIORITY_NORMAL))
        {
            ++queueCommandIndex;
            if (queueCommandIndex >= queueCommands.length)
            {
                queueCommandIndex = 0;
            }
        }
        setObjVar(self, "combat_simulator.queue_command_index", queueCommandIndex);
        return;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        int numberOfAttacks = getIntObjVar(attacker, "combat_simulator.number_of_attacks");
        ++numberOfAttacks;
        setObjVar(attacker, "combat_simulator.number_of_attacks", numberOfAttacks);
        if (!hasObjVar(self, "combat_simulator.is_creature"))
        {
            queueNewCommand(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnChangedPosture(obj_id self, int before, int after) throws InterruptedException
    {
        if (hasObjVar(self, "combat_simulator.is_creature"))
        {
            return SCRIPT_CONTINUE;
        }
        if (after == POSTURE_INCAPACITATED || before != POSTURE_KNOCKED_DOWN)
        {
            return SCRIPT_CONTINUE;
        }
        if (after == POSTURE_KNOCKED_DOWN)
        {
            if (debug)
            {
                debugSpeakMsg(self, "I'm hit! Going down");
            }
            if (!(utils.hasScriptVar(self, "standMsgStamp")))
            {
                stop(self);
                dictionary parms = new dictionary();
                int standStamp = getGameTime() + rand(1, 1000);
                parms.put("msgStamp", standStamp);
                utils.setScriptVar(self, "standMsgStamp", standStamp);
                messageTo(self, "handleStandFromKnockedDown", parms, 2, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (debug)
        {
            debugSpeakMsg(self, "I lost - sending notice to owner");
        }
        obj_id owner = getObjIdObjVar(self, "combat_simulator.owner");
        dictionary params = new dictionary();
        params.put("winner", killer);
        messageTo(owner, "endCombat", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public boolean checkForCombatActions(obj_id objPlayer) throws InterruptedException
    {
        if (queueHasCommandFromGroup(objPlayer, (-1170591580)))
        {
            return true;
        }
        if (queueHasCommandFromGroup(objPlayer, (391413347)))
        {
            return true;
        }
        if (queueHasCommandFromGroup(objPlayer, (-506878646)))
        {
            return true;
        }
        return false;
    }
    public int handleStandFromKnockedDown(obj_id self, dictionary params) throws InterruptedException
    {
        if (getPosture(self) == POSTURE_INCAPACITATED)
        {
            return SCRIPT_CONTINUE;
        }
        int standStamp = utils.getIntScriptVar(self, "standMsgStamp");
        if (params.getInt("msgStamp") != standStamp)
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, "standMsgStamp");
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
}

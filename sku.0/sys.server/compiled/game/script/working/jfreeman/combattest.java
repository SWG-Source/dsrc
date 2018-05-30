package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class combattest extends script.systems.combat.combat_base
{
    public combattest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached.  Detaching AI scripts in 5");
        messageTo(self, "detachAI", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int detachAI(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        stop(self);
        debugSpeakMsg(self, "ai detached!");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("setTarget"))
        {
            debugSpeakMsg(self, "target is set to " + objSpeaker);
            setTarget(self, objSpeaker);
        }
        if (strText.equals("getTarget"))
        {
            obj_id objTarget = getTarget(self);
            debugSpeakMsg(self, "target is " + objTarget);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        debugSpeakMsg(self, "defender_action, locomotion is " + getLocomotion(self) + " and posture is " + getPosture(self));
        if (hasCombatAction(self))
        {
            debugSpeakMsg(self, "I've got combat actions already, returning");
            return SCRIPT_CONTINUE;
        }
        obj_id objTarget = getTarget(self);
        if ((objTarget == null) || (objTarget == obj_id.NULL_ID))
        {
            debugSpeakMsg(self, "setting target to " + attacker);
            setTarget(self, attacker);
        }
        debugSpeakMsg(self, "queueing attack against " + attacker);
        queueCommand(self, (966697619), attacker, "Test params", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public boolean hasCombatAction(obj_id npc) throws InterruptedException
    {
        return checkForCombatActions(npc);
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        kill(self);
        debugSpeakMsg(self, "I am dead");
        detachScript(self, "jfreeman.combattest");
        return SCRIPT_CONTINUE;
    }
}

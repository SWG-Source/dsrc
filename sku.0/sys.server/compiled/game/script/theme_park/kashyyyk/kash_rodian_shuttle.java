package script.theme_park.kashyyyk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class kash_rodian_shuttle extends script.base_script
{
    public kash_rodian_shuttle()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        detachScript(self, "skeleton.humanoid");
        detachScript(self, "systems.combat.combat_actions");
        detachScript(self, "systems.combat.credit_for_kills");
        stop(self);
        setPosture(self, POSTURE_UPRIGHT);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "msgShuttleTakeOff", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setPosture(self, POSTURE_UPRIGHT);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "msgShuttleTakeOff", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int msgShuttleTakeOff(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasMessageTo(self, "msgShuttleTakeOff"))
        {
            return SCRIPT_CONTINUE;
        }
        setPosture(self, POSTURE_PRONE);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_PRONE;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "msgShuttleLand", null, rand(40, 60), false);
        return SCRIPT_CONTINUE;
    }
    public int msgShuttleLand(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_UPRIGHT);
        attacker_results cbtAnimationResults = new attacker_results();
        cbtAnimationResults.endPosture = POSTURE_UPRIGHT;
        cbtAnimationResults.id = self;
        doCombatResults("change_posture", cbtAnimationResults, null);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        messageTo(self, "msgShuttleTakeOff", null, rand(60, 300), false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id objParent = getObjIdObjVar(self, "objParent");
        dictionary webster = new dictionary();
        webster.put("destroyedShuttle", self);
        messageTo(objParent, "shuttleDestroyed", webster, 9, false);
        return SCRIPT_CONTINUE;
    }
}

package script.working.wwallace;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class lamdatest extends script.base_script
{
    public lamdatest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached");
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        detachScript(self, "skeleton.humanoid");
        detachScript(self, "systems.combat.combat_actions");
        detachScript(self, "systems.combat.credit_for_kills");
        stop(self);
        setName(self, "Stormtrooper Transport");
        setPosture(self, POSTURE_PRONE);
        messageTo(self, "spawnSts", null, 17, true);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public int changePosture(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "upright");
        setPosture(self, POSTURE_UPRIGHT);
        return SCRIPT_CONTINUE;
    }
    public int spawnSts(obj_id self, dictionary params) throws InterruptedException
    {
        location spawnLoc = getLocation(self);
        create.createCreature("stormtrooper", spawnLoc, true);
        create.createCreature("stormtrooper", spawnLoc, true);
        create.createCreature("stormtrooper", spawnLoc, true);
        create.createCreature("stormtrooper", spawnLoc, true);
        create.createCreature("stormtrooper", spawnLoc, true);
        messageTo(self, "changePosture", null, 5, false);
        return SCRIPT_CONTINUE;
    }
}

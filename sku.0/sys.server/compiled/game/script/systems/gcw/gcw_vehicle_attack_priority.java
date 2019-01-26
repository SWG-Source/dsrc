package script.systems.gcw;

import script.dictionary;
import script.library.trial;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

public class gcw_vehicle_attack_priority extends script.systems.combat.combat_base
{
    public gcw_vehicle_attack_priority()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "findTarget", null, rand(5.0f, 10.0f), false);
        return SCRIPT_CONTINUE;
    }
    public int findTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.getObjIdScriptVar(self, "gcw.towerTarget");
        if (!isIdValid(target) || !exists(target))
        {
            obj_id[] targets = getNonCreaturesInRange(self, 32.0f);
            Vector validTargets = new Vector();
            validTargets.setSize(0);
            if (targets == null || targets.length == 0)
            {
                messageTo(self, "findTarget", null, rand(2.0f, 4.0f), false);
                return SCRIPT_CONTINUE;
            }
            for (obj_id target1 : targets) {
                if (!isIdValid(target1)) {
                    continue;
                }
                if (target1 == self) {
                    continue;
                }
                String templateName = getTemplateName(target1);
                if (templateName.equals("object/tangible/destructible/gcw_imperial_tower.iff") || templateName.equals("object/tangible/destructible/gcw_rebel_tower.iff")) {
                    validTargets.add(target1);
                }
            }
            if (validTargets == null || validTargets.size() <= 0)
            {
                messageTo(self, "findTarget", null, rand(2.0f, 4.0f), false);
                return SCRIPT_CONTINUE;
            }
            target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
            if (!isIdValid(target))
            {
                target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
            }
            if (!isIdValid(target) || !exists(target))
            {
                messageTo(self, "findTarget", null, rand(2.0f, 4.0f), false);
                return SCRIPT_CONTINUE;
            }
            utils.setScriptVar(self, "gcw.towerTarget", target);
        }
        addHate(self, target, 1000.0f);
        queueCommand(self, (-285778303), target, "", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "findTarget", null, rand(4.0f, 6.0f), false);
        return SCRIPT_CONTINUE;
    }
}

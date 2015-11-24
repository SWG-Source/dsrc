package script.theme_park.alderaan.act3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.library.chat;
import script.ai.ai_combat;

public class rebel_research_attacker extends script.base_script
{
    public rebel_research_attacker()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        obj_id facility = getObjIdObjVar(self, "coa3.rebel.facility");
        if (isIdValid(facility))
        {
            dictionary params = new dictionary();
            params.put("attacker", attacker);
            messageTo(facility, "attackerKilled", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startAttack", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id facility = getObjIdObjVar(self, "coa3.rebel.facility");
        location loc = getLocation(facility);
        loc.x += -10.0f;
        ai_lib.aiPathTo(self, loc);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        stop(self);
        attackNewTarget(self);
        return SCRIPT_CONTINUE;
    }
    public int postCombatPathHome(obj_id self, dictionary params) throws InterruptedException
    {
        attackNewTarget(self);
        return SCRIPT_OVERRIDE;
    }
    public void attackNewTarget(obj_id self) throws InterruptedException
    {
        obj_id facility = getObjIdObjVar(self, "coa3.rebel.facility");
        obj_id player = getObjIdObjVar(facility, "coa3.rebel.playerId");
        if (isIdValid(facility) && exists(facility))
        {
            int numGuards = getIntObjVar(facility, "coa3.rebel.numGuards");
            Vector objectList = getResizeableObjIdArrayObjVar(facility, "coa3.rebel.obj_list");
            if (numGuards > 0)
            {
                obj_id target = null;
                int x = 0;
                while (target == null)
                {
                    obj_id newTarget = ((obj_id)objectList.get(rand(0, objectList.size() - 1)));
                    if (isIdValid(newTarget) && exists(newTarget) && ai_lib.isNpc(newTarget))
                    {
                        target = newTarget;
                    }
                    if (x++ >= 50)
                    {
                        break;
                    }
                }
                if (target != null)
                {
                    startCombat(self, target);
                    return;
                }
            }
            if (isIdValid(player) && exists(player))
            {
                startCombat(self, player);
                return;
            }
        }
    }
}

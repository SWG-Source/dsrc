package script.poi.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.xp;
import script.library.group;
import script.library.scenario;

public class scenario_actor extends script.theme_park.poi.base
{
    public scenario_actor()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "npc.converse.npc_convo");
        if (!hasScript(self, SCRIPT_CONVERSE))
        {
            attachScript(self, SCRIPT_CONVERSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int xpDelegated(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] killers = null;
        obj_id winner = getObjIdObjVar(self, xp.VAR_TOP_GROUP);
        if ((winner == null) || (winner == obj_id.NULL_ID))
        {
        }
        else 
        {
            if (group.isGroupObject(winner))
            {
                killers = getGroupMemberIds(winner);
            }
            else 
            {
                killers = new obj_id[1];
                killers[0] = winner;
            }
        }
        scenario.setActorDead(self, killers);
        return SCRIPT_CONTINUE;
    }
}

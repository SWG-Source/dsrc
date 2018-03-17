package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.combat;

public class volleytarget extends script.base_script
{
    public volleytarget()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id[] me = new obj_id[]
        {
            self
        };
        playClientEffectObj(me, "appearance/pt_special_attack_volley_fire.prt", self, "", new transform(), combat.ID_VOLLEY_FIRE_PARTICLE);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.combat.volleytarget");
        return SCRIPT_CONTINUE;
    }
    public void notifyVolleyTargetDone() throws InterruptedException
    {
        obj_id self = getSelf();
        Vector groups = utils.getResizeableObjIdBatchScriptVar(self, combat.VAR_VOLLEY_GROUPS);
        utils.removeScriptVar(self, combat.VAR_VOLLEY_GROUPS);
        for (int i = 0; i < groups.size(); i++)
        {
            notifyVolleyTargetDone((obj_id)groups.get(i));
        }
    }
    public void notifyVolleyTargetDone(obj_id group) throws InterruptedException
    {
        dictionary parms = new dictionary();
        parms.put("objTarget", getSelf());
        messageTo(group, "volleyTargetDone", parms, 0, false);
    }
    public int handlePlayerDeath(obj_id self, dictionary params) throws InterruptedException
    {
        stopClientEffectObjByLabel(new obj_id[]
        {
            self
        }, self, combat.ID_VOLLEY_FIRE_PARTICLE);
        notifyVolleyTargetDone();
        detachScript(self, "systems.combat.volleytarget");
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (isPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        stopClientEffectObjByLabel(new obj_id[]
        {
            self
        }, self, combat.ID_VOLLEY_FIRE_PARTICLE);
        notifyVolleyTargetDone();
        detachScript(self, "systems.combat.volleytarget");
        return SCRIPT_CONTINUE;
    }
    public int msgMarkedByGroup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id group = params.getObjId("objGroup");
        if (!isIdValid(group))
        {
            return SCRIPT_CONTINUE;
        }
        Vector groups = new Vector();
        if (utils.hasScriptVar(self, combat.VAR_VOLLEY_GROUPS))
        {
            groups = utils.getResizeableObjIdBatchScriptVar(self, combat.VAR_VOLLEY_GROUPS);
        }
        if (groups.indexOf(group) > -1)
        {
            return SCRIPT_CONTINUE;
        }
        groups.add(group);
        utils.setBatchScriptVar(self, combat.VAR_VOLLEY_GROUPS, groups);
        return SCRIPT_CONTINUE;
    }
    public int msgUnmarkedByGroup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id group = params.getObjId("objGroup");
        if (!isIdValid(group))
        {
            return SCRIPT_CONTINUE;
        }
        Vector groups = new Vector();
        if (utils.hasScriptVar(self, combat.VAR_VOLLEY_GROUPS))
        {
            groups = utils.getResizeableObjIdBatchScriptVar(self, combat.VAR_VOLLEY_GROUPS);
        }
        if (groups.indexOf(group) < 0)
        {
            return SCRIPT_CONTINUE;
        }
        groups.remove(group);
        if (groups.size() < 1)
        {
            utils.removeScriptVar(self, combat.VAR_VOLLEY_GROUPS);
            stopClientEffectObjByLabel(self, self, combat.ID_VOLLEY_FIRE_PARTICLE);
            detachScript(self, "systems.combat.volleytarget");
        }
        else 
        {
            utils.setBatchScriptVar(self, combat.VAR_VOLLEY_GROUPS, groups);
        }
        notifyVolleyTargetDone(group);
        return SCRIPT_CONTINUE;
    }
}

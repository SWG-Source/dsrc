package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.fs_counterstrike;
import script.library.trace;
import script.library.fs_dyn_village;
import script.library.utils;

public class fs_surveillance_droid extends script.base_script
{
    public fs_surveillance_droid()
    {
    }
    public void squeal() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id master = fs_counterstrike.getMyOutpostId(self);
        if (!isIdValid(master) || !exists(master))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_surveillance_droi::OnDestroy: -> Couldn't create droid defenders. Missing outpost master reference or master object doesnt exist.", self, trace.TL_WARNING);
            return;
        }
        dictionary d = new dictionary();
        d.put("sender", self);
        messageTo(master, "msgSurveillandeDroidDied", d, 0.0f, false);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        playClientEffectLoc(self, "clienteffect/combat_explosion_lair_large.cef", getLocation(self), 10.0f);
        if (utils.hasScriptVar(self, "silentDestroy"))
        {
            return SCRIPT_CONTINUE;
        }
        squeal();
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, "silentDestroy", true);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int msgSilentSelfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "silentDestroy", true);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}

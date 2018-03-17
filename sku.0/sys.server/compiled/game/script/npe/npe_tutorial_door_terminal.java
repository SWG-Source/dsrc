package script.npe;

import script.dictionary;
import script.library.utils;
import script.location;
import script.obj_id;

public class npe_tutorial_door_terminal extends script.base_script
{
    public npe_tutorial_door_terminal()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        // prevent door terminal from being searched for eggs (ya, exploit).
        self.setScriptVar("lair.searched",1);
        obj_id building = getTopMostContainer(self);
        utils.setScriptVar(building, "objTerminal", self);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        // prevent door terminal from being searched for eggs (ya, exploit).
        self.setScriptVar("lair.searched",1);
        obj_id building = getTopMostContainer(self);
        utils.setScriptVar(building, "objTerminal", self);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        messageTo(building, "terminalDestroyed", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "destroyDisabledLair", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyDisabledLair(obj_id self, dictionary params) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(self, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(self, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}

package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class grievous extends script.base_script
{
    public grievous()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setScale(self, 1.5f);
        createTriggerVolume("playerEnter", 28f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id player) throws InterruptedException
    {
        messageTo(self, "destroyCorpse", null, 5.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        int number = getIntObjVar(self, "demoNumber");
        if (number == 1)
        {
            messageTo(player, "resetDemo1", null, 1.0f, true);
        }
        if (number == 2)
        {
            messageTo(player, "resetDemo2", null, 1.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyCorpse(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals("playerEnter"))
        {
            if (!utils.hasScriptVar(self, "intBreached"))
            {
                debugSpeakMsg(self, "Berece");
                if (isPlayer(breacher))
                {
                    startCombat(self, breacher);
                    utils.setScriptVar(self, "intBreached", 1);
                    setState(self, STATE_COMBAT, true);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

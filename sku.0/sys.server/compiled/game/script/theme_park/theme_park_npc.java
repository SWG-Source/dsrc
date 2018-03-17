package script.theme_park;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class theme_park_npc extends script.base_script
{
    public theme_park_npc()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setupNpc(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setupNpc(self);
        return SCRIPT_CONTINUE;
    }
    public void setupNpc(obj_id self) throws InterruptedException
    {
        setWantSawAttackTriggers(self, false);
        if (hasTriggerVolume(self, "alertTriggerVolume"))
        {
            removeTriggerVolume("alertTriggerVolume");
        }
        setInvulnerable(self, true);
        return;
    }
}

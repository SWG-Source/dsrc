package script.space.characters;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class oberhaur extends script.base_script
{
    public oberhaur()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Cdr. Oberhaur");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        setInvulnerable(self, true);
        setName(self, "Cdr. Oberhaur");
        return SCRIPT_CONTINUE;
    }
}

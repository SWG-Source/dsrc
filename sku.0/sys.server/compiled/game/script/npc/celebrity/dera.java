package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.colors;
import script.library.ai_lib;
import script.library.chat;
import script.library.factions;
import script.library.skill;

public class dera extends script.base_script
{
    public dera()
    {
    }
    public static final String CONVO = "celebrity/dera_darklighter";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "Dera Darklighter");
        return SCRIPT_CONTINUE;
    }
}

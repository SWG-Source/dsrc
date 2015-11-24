package script.systems.player_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class player_quest_base extends script.base_script
{
    public player_quest_base()
    {
    }
    public static final String HOLOCRON_NAME = "holocronName";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, HOLOCRON_NAME))
        {
            String nameObjVar = getStringObjVar(self, HOLOCRON_NAME);
            setName(self, nameObjVar);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}

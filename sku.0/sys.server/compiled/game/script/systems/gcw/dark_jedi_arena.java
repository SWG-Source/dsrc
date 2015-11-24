package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.pet_lib;
import script.library.factions;
import script.library.force_rank;
import script.library.arena;

public class dark_jedi_arena extends script.base_script
{
    public dark_jedi_arena()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, arena.VAR_I_AM_ARENA, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!utils.hasScriptVar(item, arena.VAR_I_AM_DUELING))
        {
            return SCRIPT_CONTINUE;
        }
        arena.leftArenaDuringDuel(item);
        return SCRIPT_CONTINUE;
    }
}

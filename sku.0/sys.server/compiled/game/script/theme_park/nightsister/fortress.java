package script.theme_park.nightsister;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai;
import script.library.ai_lib;
import script.library.create;
import script.ai.ai_combat;

public class fortress extends script.base_script
{
    public fortress()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnEveryone(self);
        return SCRIPT_CONTINUE;
    }
    public void spawnEveryone(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Spawning Everyone");
        if (!hasObjVar(self, "spawn_table"))
        {
            setObjVar(self, "spawn_table", "datatables/spawning/dungeon/nightsister_fortress.iff");
        }
        messageTo(self, "scriptFix", null, 2, false);
        return;
    }
    public int scriptFix(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasScript(self, "theme_park.dungeon.generic_spawner"))
        {
            
        }
        
        {
            attachScript(self, "theme_park.dungeon.generic_spawner");
        }
        detachScript(self, "theme_park.singing_mountain.stronghold");
        return SCRIPT_CONTINUE;
    }
}

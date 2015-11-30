package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;

public class fs_reflex2_crate extends script.base_script
{
    public fs_reflex2_crate()
    {
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "player"))
        {
            obj_id player = getObjIdObjVar(self, "player");
            quests.complete("fs_reflex_fetch_quest_03", player, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetupCrate(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("newquests", "REFLEX2: crate script - setting up crate");
        obj_id player = params.getObjId("player");
        setObjVar(self, "player", player);
        setOwner(self, player);
        return SCRIPT_CONTINUE;
    }
}

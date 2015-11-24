package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;

public class dungeon_experience_manager extends script.base_script
{
    public dungeon_experience_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id droidCell = getCellId(self, "cave_entrance");
        location droidLoc = new location(1.5f, 10.2f, 4.3f, "poi_all_bunker_mad_bio_s01", droidCell);
        obj_id droidInvis = create.object("object/tangible/npe/npe_node.iff", droidLoc);
        setName(droidInvis, "C-3P0");
        setObjVar(droidInvis, "convo.appearance", "object/mobile/c_3po.iff");
        utils.setScriptVar(self, "objDroidInvis", droidInvis);
        return SCRIPT_CONTINUE;
    }
}

package script.poi.mystic_tree;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class tree_spawner extends script.base_script
{
    public tree_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnTree", null, 5, true);
        return SCRIPT_CONTINUE;
    }
    public int spawnTree(obj_id self, dictionary params) throws InterruptedException
    {
        int yaw = 140;
        location treeLoc = new location(-42, 39, 134, "kashyyyk_dead_forest");
        obj_id newTree = createObject("object/tangible/item/ep3/poi_kash_mystic_tree_sickly.iff", treeLoc);
        setYaw(newTree, yaw);
        utils.setScriptVar(self, "tree", newTree);
        attachScript(newTree, "poi.mystic_tree.mystic_tree");
        return SCRIPT_CONTINUE;
    }
}

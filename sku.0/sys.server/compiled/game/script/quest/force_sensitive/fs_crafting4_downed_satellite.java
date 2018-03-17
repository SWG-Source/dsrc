package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class fs_crafting4_downed_satellite extends script.base_script
{
    public fs_crafting4_downed_satellite()
    {
    }
    public static final String COMPUTER_CORE_TEMPLATE = "object/tangible/item/quest/force_sensitive/fs_crafting4_computer_core.iff";
    public int cleanUpSelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean canSearch(obj_id self, obj_id player) throws InterruptedException
    {
        return false;
    }
}

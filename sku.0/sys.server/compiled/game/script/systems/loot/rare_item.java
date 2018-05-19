package script.systems.loot;

import script.library.badge;
import script.library.collection;
import script.library.loot;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class rare_item extends script.base_script {
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if ((names == null) || (attribs == null) || (names.length != attribs.length))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != null)
        {
            final int firstFreeIndex = getFirstFreeIndex(names);
            if ((firstFreeIndex >= 0) && (firstFreeIndex < names.length))
            {
                names[firstFreeIndex] = "rare_loot_category";
                attribs[firstFreeIndex] = "\\#ed8d16Rare";
            }
        }
        return SCRIPT_CONTINUE;
    }
}
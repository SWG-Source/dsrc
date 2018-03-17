package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.permissions;
import script.library.features;

public class loot_ep3_clone_relics_jedi_starfighter extends script.base_script
{
    public loot_ep3_clone_relics_jedi_starfighter()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "makeMoreLoot", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (!features.hasEpisode3Expansion(who))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_OPEN)
        {
            utils.requestContainerOpen(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isGod(transferer))
        {
            messageTo(self, "makeMoreLoot", null, 10, false);
        }
        else 
        {
            messageTo(self, "makeMoreLoot", null, 1800, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int makeMoreLoot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] boxContent = getContents(self);
        if (boxContent == null || boxContent.length == 0)
        {
            createObject("object/tangible/quest/quest_start/ep3_clone_relics_jedi_starfighter.iff", self, "");
        }
        return SCRIPT_CONTINUE;
    }
}

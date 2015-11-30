package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.utils;
import script.library.static_item;

public class jedi_conversion_robe extends script.base_script
{
    public jedi_conversion_robe()
    {
    }
    public static final String STF_FILE = "jedi_spam";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "giveRobe", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "giveRobe", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int giveRobe(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id gaveRobe = static_item.createNewItemFunction("item_jedi_robe_padawan_04_01", self);
        if (gaveRobe == null)
        {
            messageTo(self, "giveRobe", null, 60, false);
            sendSystemMessage(self, new string_id(STF_FILE, "inventory_full_jedi_robe"));
        }
        else 
        {
            detachScript(self, "player.jedi_conversion_robe");
        }
        return SCRIPT_CONTINUE;
    }
}

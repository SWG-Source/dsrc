package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.prose;
import script.library.utils;
import script.library.jedi;
import script.library.static_item;

public class jedi_robe extends script.base_script
{
    public jedi_robe()
    {
    }
    public static final String VAR_JEDI_SKILL = "jedi.skill_required";
    public static final String VAR_ROBE_REGEN = "jedi.robe.regen";
    public static final String VAR_ROBE_POWER = "jedi.robe.power";
    public static final string_id SID_MUST_BE_HIGHER_RANK = new string_id("jedi_spam", "must_be_higher_rank");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!static_item.isStaticItem(self))
        {
            String templateName = getTemplateName(self);
            if (templateName.equals("object/tangible/wearables/robe/robe_jedi_padawan.iff"))
            {
                obj_id player = utils.getContainingPlayer(self);
                if (isIdValid(player))
                {
                    obj_id inventory = utils.getInventoryContainer(player);
                    destroyObject(self);
                    static_item.createNewItemFunction("item_jedi_robe_padawan_04_01", player);
                }
            }
            else 
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

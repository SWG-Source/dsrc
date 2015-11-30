package script.item.lore;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class lore extends script.base_script
{
    public lore()
    {
    }
    public static final string_id ITEM_LORE = new string_id("error_message", "item_lore");
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        String template = getTemplateName(self);
        obj_id[] contents = utils.getContents(destContainer, true);
        if (contents == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < contents.length; i++)
        {
            String _template = getTemplateName(contents[i]);
            if ((contents[i] != self) && template.equals(_template))
            {
                if (isPlayer(transferer))
                {
                    sendSystemMessage(transferer, ITEM_LORE);
                }
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "lore";
        attribs[idx] = "@obj_attr_n:lore_d";
        return SCRIPT_CONTINUE;
    }
}

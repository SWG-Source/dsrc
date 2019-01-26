package script.item.lore;

import script.library.utils;
import script.obj_id;
import script.string_id;

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
        for (obj_id content : contents) {
            String _template = getTemplateName(content);
            if ((content != self) && template.equals(_template)) {
                if (isPlayer(transferer)) {
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

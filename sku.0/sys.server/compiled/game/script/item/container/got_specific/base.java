package script.item.container.got_specific;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final String VAR_GOT = "container.got";
    public static final string_id PROSE_WRONG_ITEM_TYPE = new string_id("error_message", "wrong_item_type");
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer))
        {
            if (hasObjVar(self, VAR_GOT))
            {
                int gotType = getIntObjVar(self, VAR_GOT);
                if (getGameObjectType(item) != gotType)
                {
                    string_id got_sid = getGameObjectTypeStringId(gotType);
                    prose_package pp = prose.getPackage(PROSE_WRONG_ITEM_TYPE, self, got_sid);
                    sendSystemMessageProse(transferer, pp);
                    return SCRIPT_OVERRIDE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

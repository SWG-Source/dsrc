package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class block_open extends script.base_script
{
    public block_open()
    {
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id who) throws InterruptedException
    {
        if (isGod(who))
        {
            sendSystemMessageTestingOnly(who, "GOD MODE: You are able to open this container because you are in God Mode!");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
}

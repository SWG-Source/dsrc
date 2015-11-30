package script.item.container;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class remove_only extends script.base_script
{
    public remove_only()
    {
    }
    public static final String SCRIPT_ME = "item.container.remove_only";
    public static final string_id SID_REMOVE_ONLY_CONTAINER = new string_id("error_message", "remove_only");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if ((getContainerType(self) == 0) || (isPlayer(self)))
        {
            detachScript(self, SCRIPT_ME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer))
        {
            sendSystemMessage(transferer, SID_REMOVE_ONLY_CONTAINER);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}

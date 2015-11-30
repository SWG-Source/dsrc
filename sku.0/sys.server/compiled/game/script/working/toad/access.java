package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class access extends script.base_script
{
    public access()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("access list"))
        {
            obj_id room = getContainedBy(self);
            String[] aList = permissionsGetAllowed(room);
            sendSystemMessageTestingOnly(self, "You're looking in Room " + room);
            int number = aList.length;
            sendSystemMessageTestingOnly(self, "There are " + number + " entries.");
            if (number == 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < number; i++)
            {
                String name = aList[i];
                sendSystemMessageTestingOnly(self, i + ". " + name);
            }
        }
        if (text.equals("ban list"))
        {
            obj_id room = getContainedBy(self);
            String[] aList = permissionsGetBanned(room);
            sendSystemMessageTestingOnly(self, "You're looking in Room " + room);
            int number = aList.length;
            sendSystemMessageTestingOnly(self, "There are " + number + " entries.");
            if (number == 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < number; i++)
            {
                String name = aList[i];
                sendSystemMessageTestingOnly(self, i + ". " + name);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

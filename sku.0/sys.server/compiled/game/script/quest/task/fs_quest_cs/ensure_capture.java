package script.quest.task.fs_quest_cs;

import script.library.fs_counterstrike;
import script.obj_id;
import script.string_id;

public class ensure_capture extends script.base_script
{
    public ensure_capture()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessage(self, new string_id("fs_quest_village", "fs_cs_ensure_detail"));
        if (!hasObjVar(self, fs_counterstrike.OBJVAR_MY_CAMP_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id camp = getObjIdObjVar(self, fs_counterstrike.OBJVAR_MY_CAMP_ID);
        fs_counterstrike.createCommander(camp);
        return SCRIPT_CONTINUE;
    }
}

package script.theme_park.corellia.content;

import script.library.groundquests;
import script.obj_id;
import script.string_id;

public class kor_vella_ryll_smuggler_block extends script.base_script
{
    public kor_vella_ryll_smuggler_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item) || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.hasCompletedTask(item, "corellia_38_corsec_files_04", "corsec_files_04_06"))
        {
            string_id warning = new string_id("theme_park/corellia/quest", "kor_vella_ryll_smuggler");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}

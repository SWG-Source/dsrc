package script.item.special;

import script.obj_id;

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

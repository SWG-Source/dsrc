package script.library;

import script.obj_id;
import script.prose_package;
import script.string_id;

public class conversation extends script.base_script
{
    public conversation()
    {
    }
    public static void echoToGroup(obj_id player, obj_id actor, obj_id target, string_id sid) throws InterruptedException
    {
        if (!group.isGrouped(player))
        {
            return;
        }
        chat.chat(actor, target, sid, chat.ChatFlag_targetAndSourceGroup | chat.ChatFlag_skipTarget | chat.ChatFlag_skipSource);
    }
    public static void echoToGroup(obj_id player, obj_id actor, obj_id target, prose_package pp) throws InterruptedException
    {
        if (!group.isGrouped(player))
        {
            return;
        }
        chat.chat(actor, target, pp, chat.ChatFlag_targetAndSourceGroup | chat.ChatFlag_skipTarget | chat.ChatFlag_skipSource);
    }
}

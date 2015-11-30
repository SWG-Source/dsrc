package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.group;

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

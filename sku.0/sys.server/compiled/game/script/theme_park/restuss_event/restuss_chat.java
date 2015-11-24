package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class restuss_chat extends script.base_script
{
    public restuss_chat()
    {
    }
    public static final String STF = "restuss_event/rebel_commando_chat";
    public static final string_id OPENING = new string_id(STF, "opening");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleOpeningChat", null, 3f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleOpeningChat(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, OPENING);
        return SCRIPT_CONTINUE;
    }
}

package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class neina_annoying extends script.base_script
{
    public neina_annoying()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "annoying1", null, 1, false);
        messageTo(self, "annoying2", null, 20, false);
        messageTo(self, "annoying3", null, 40, false);
        messageTo(self, "annoying4", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "annoying1", null, 1, false);
        messageTo(self, "annoying2", null, 20, false);
        messageTo(self, "annoying3", null, 40, false);
        messageTo(self, "annoying4", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int annoying1(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, "It's about time you showed up! What took you so long!");
        return SCRIPT_CONTINUE;
    }
    public int annoying2(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, "When are we getting out of here! You're too slow.");
        return SCRIPT_CONTINUE;
    }
    public int annoying3(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, "Ow! I just stepped on a rock. I need to be carried. Hey! Carry me!");
        return SCRIPT_CONTINUE;
    }
    public int annoying4(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, "Where's my room service? Ugh! I can't wait to get home.");
        return SCRIPT_CONTINUE;
    }
}

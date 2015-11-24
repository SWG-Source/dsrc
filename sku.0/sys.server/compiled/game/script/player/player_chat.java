package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class player_chat extends script.base_script
{
    public player_chat()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        LOG("ChatFilter", "OnChatLogin");
        return SCRIPT_CONTINUE;
    }
}

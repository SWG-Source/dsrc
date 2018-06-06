package script.player;

import script.obj_id;

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

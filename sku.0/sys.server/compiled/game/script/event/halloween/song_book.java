package script.event.halloween;

import script.library.utils;
import script.*;

public class song_book extends script.base_script
{
    public song_book()
    {
    }
    private static final String HALLOWEEN = "event/halloween";
    public static final string_id SID_USE = new string_id(HALLOWEEN, "learn_song");
    private static final string_id CANT_USE = new string_id(HALLOWEEN, "not_entertainer");
    private static final string_id LEARNED_SONG = new string_id(HALLOWEEN, "learned_song");
    private static final string_id CANT_USE_LOW_LEVEL = new string_id(HALLOWEEN, "not_entertainer_enough");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            if (utils.isProfession(player, utils.ENTERTAINER))
            {
                if (!hasCommand(player, "startMusic+dirge"))
                {
                    mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                if (utils.isProfession(player, utils.ENTERTAINER))
                {
                    if (getLevel(player) > 81)
                    {
                        grantCommand(player, "startMusic+dirge");
                        sendSystemMessage(player, LEARNED_SONG);
                        destroyObject(self);
                    }
                    else 
                    {
                        sendSystemMessage(player, CANT_USE_LOW_LEVEL);
                    }
                }
                else 
                {
                    sendSystemMessage(player, CANT_USE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

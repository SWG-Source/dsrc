package script.npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class c_story1_1_corpse extends script.base_script
{
    public c_story1_1_corpse()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.c_story1_1_corpse");
        }
        setInvulnerable(self, true);
        setName(self, "Moxxar Krieg");
        setAnimationMood(self, "npc_dead_03");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setName(self, "Moxxar Krieg");
        setAnimationMood(self, "npc_dead_03");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.EXAMINE, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        detachScript(self, "conversation.c_story1_1_corpse");
        return SCRIPT_CONTINUE;
    }
}

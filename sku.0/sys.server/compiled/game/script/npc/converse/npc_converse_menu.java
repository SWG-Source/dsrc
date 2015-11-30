package script.npc.converse;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class npc_converse_menu extends script.base_script
{
    public npc_converse_menu()
    {
    }
    public static final String SCRIPT_NPC_CONVERSE_MENU = "npc.converse.npc_converse_menu";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, SCRIPT_NPC_CONVERSE_MENU);
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, SCRIPT_NPC_CONVERSE_MENU);
        return SCRIPT_CONTINUE;
    }
}

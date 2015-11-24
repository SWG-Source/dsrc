package script.npc.faction_recruiter;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class recruiter_setup extends script.base_script
{
    public recruiter_setup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setUpRecruiter(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setUpRecruiter(self);
        return SCRIPT_CONTINUE;
    }
    public void setUpRecruiter(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        String faction = getStringObjVar(self, "recruiter");
        setFactionRecruiter(self, faction);
        return;
    }
    public boolean setFactionRecruiter(obj_id npc, String faction) throws InterruptedException
    {
        if (npc == null || npc == obj_id.NULL_ID)
        {
            return false;
        }
        if (faction == null)
        {
            return false;
        }
        setObjVar(npc, "faction_recruiter.faction", faction);
        attachScript(npc, "npc.faction_recruiter.faction_recruiter");
        return true;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!ai_lib.isAiDead(self))
        {
            int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
            menu_info_data mdata = mi.getMenuItemById(mnu);
            mdata.setServerNotify(false);
        }
        return SCRIPT_CONTINUE;
    }
}

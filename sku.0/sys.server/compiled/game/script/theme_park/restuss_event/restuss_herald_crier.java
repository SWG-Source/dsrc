package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;

public class restuss_herald_crier extends script.base_script
{
    public restuss_herald_crier()
    {
    }
    public static final String STF = "restuss_event/stage_one_herald";
    public static final string_id ST_ONE = new string_id(STF, "stormtrooper_01");
    public static final string_id ST_TWO = new string_id(STF, "stormtrooper_02");
    public static final string_id ST_THREE = new string_id(STF, "stormtrooper_03");
    public static final string_id REB_ONE = new string_id(STF, "rebel_01");
    public static final string_id REB_TWO = new string_id(STF, "rebel_02");
    public static final string_id REB_THREE = new string_id(STF, "rebel_03");
    public static final string_id GUY_ONE = new string_id(STF, "guy_01");
    public static final string_id GUY_TWO = new string_id(STF, "guy_02");
    public static final string_id GUY_THREE = new string_id(STF, "guy_03");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startCheck", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "startCheck", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int startCheck(obj_id self, dictionary params) throws InterruptedException
    {
        int npcType = getIntObjVar(self, "restussHerald");
        if (npcType == 1)
        {
            messageTo(self, "handleHeraldST", null, 60, false);
            return SCRIPT_CONTINUE;
        }
        if (npcType == 2)
        {
            messageTo(self, "handleHeraldReb", null, 60, false);
            return SCRIPT_CONTINUE;
        }
        if (npcType == 3)
        {
            messageTo(self, "handleHeraldGuy", null, 60, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHeraldST(obj_id self, dictionary params) throws InterruptedException
    {
        int chatChoice = rand(1, 3);
        if (chatChoice == 1)
        {
            chat.chat(self, ST_ONE);
        }
        if (chatChoice == 2)
        {
            chat.chat(self, ST_TWO);
        }
        if (chatChoice == 3)
        {
            chat.chat(self, ST_THREE);
        }
        messageTo(self, "handleHeraldST", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int handleHeraldReb(obj_id self, dictionary params) throws InterruptedException
    {
        int chatChoice = rand(1, 3);
        if (chatChoice == 1)
        {
            chat.chat(self, REB_ONE);
        }
        if (chatChoice == 2)
        {
            chat.chat(self, REB_TWO);
        }
        if (chatChoice == 3)
        {
            chat.chat(self, REB_THREE);
        }
        messageTo(self, "handleHeraldReb", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int handleHeraldGuy(obj_id self, dictionary params) throws InterruptedException
    {
        int chatChoice = rand(1, 3);
        if (chatChoice == 1)
        {
            chat.chat(self, GUY_ONE);
        }
        if (chatChoice == 2)
        {
            chat.chat(self, GUY_TWO);
        }
        if (chatChoice == 3)
        {
            chat.chat(self, GUY_THREE);
        }
        messageTo(self, "handleHeraldGuy", null, 60, false);
        return SCRIPT_CONTINUE;
    }
}

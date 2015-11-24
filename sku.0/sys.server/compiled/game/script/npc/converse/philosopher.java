package script.npc.converse;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.hue;
import script.library.colors;
import script.library.chat;

public class philosopher extends script.base_script
{
    public philosopher()
    {
    }
    public static final String CONVO = "static_npc/philosopher";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String lineToSay = "philosopher_1";
        String datatable = "datatables/npc/convo/philosopher.iff";
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker) || hasScript(self, "themepark.poi.tatooine.city.npc_mission_convo") || hasScript(self, "themepark.poi.tatooine.city.npc_mission_01_friend_convo"))
        {
            return SCRIPT_OVERRIDE;
        }
        int numLines = dataTableGetNumRows(datatable);
        if (numLines == 0)
        {
            numLines = rand(1, 5);
            lineToSay = "philosopher_" + numLines;
        }
        else 
        {
            int total = numLines - 1;
            int pickLine = rand(0, total);
            lineToSay = dataTableGetString(datatable, pickLine, "philosopher_lines");
        }
        string_id message = new string_id(CONVO, lineToSay);
        chat.chat(self, speaker, message);
        return SCRIPT_CONTINUE;
    }
}

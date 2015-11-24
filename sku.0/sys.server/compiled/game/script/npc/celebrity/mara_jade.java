package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.colors;
import script.library.ai_lib;
import script.library.chat;
import script.library.factions;
import script.library.skill;

public class mara_jade extends script.base_script
{
    public mara_jade()
    {
    }
    public static final String CONVO = "celebrity/mara_jade";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String creatureName = getCreatureName(self);
        if (creatureName != null && creatureName.equals("itp_vader_mara_jade"))
        {
            attachScript(self, "conversation.itp_vader_mara_jade");
        }
        else 
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        obj_id bsuit = createObject("object/tangible/wearables/bodysuit/bodysuit_s15.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/boots_s12.iff", self, "");
        hue.setColor(bsuit, 1, colors.RED);
        hue.setColor(bsuit, 2, 34);
        hue.setColor(boots, 1, colors.BLACK);
        setName(self, "Mara Jade");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}

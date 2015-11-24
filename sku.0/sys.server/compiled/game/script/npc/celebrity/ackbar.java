package script.npc.celebrity;

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

public class ackbar extends script.base_script
{
    public ackbar()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s07.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s12.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/shoes/shoes_s03.iff", self, "");
        obj_id vest = createObject("object/tangible/wearables/vest/vest_s05.iff", self, "");
        obj_id belt = createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_utility_belt.iff", self, "");
        string_id ackbar = new string_id("mob/creature_names", "captain_ackbar");
        setName(self, ackbar);
        hue.setColor(shirt, 1, colors.WHITE);
        hue.setColor(shirt, 1, colors.WHITE);
        hue.setColor(vest, 1, colors.WHITE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "It's a trap!");
        attachScript(self, "conversation.rtp_ackbar_main");
        return SCRIPT_CONTINUE;
    }
}

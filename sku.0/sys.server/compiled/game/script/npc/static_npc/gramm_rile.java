package script.npc.static_npc;

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
import script.library.factions;
import script.library.skill;

public class gramm_rile extends script.base_script
{
    public gramm_rile()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s07.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s05.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/boots_s22.iff", self, "");
        obj_id jacket = createObject("object/tangible/wearables/jacket/jacket_s16.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/human/hair_human_male_s28.iff", self, "");
        setName(self, "Mayor Gramm Rile");
        hue.setColor(shirt, 1, 42);
        hue.setColor(shirt, 2, 0);
        hue.setColor(jacket, 1, 85);
        hue.setColor(jacket, 2, 41);
        hue.setColor(pants, 1, 85);
        hue.setColor(pants, 2, 41);
        hue.setColor(hair, 1, 13);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.static_quest.quest_convo");
        setObjVar(self, "quest_table", "gramm_rile");
        debugSpeakMsg(self, "I'm the MAYOR!");
        return SCRIPT_CONTINUE;
    }
}

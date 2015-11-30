package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.ai_lib;
import script.library.hue;
import script.library.colors;

public class huff_darklighter extends script.base_script
{
    public huff_darklighter()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Huff Darklighter");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s15.iff", self, "");
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s12.iff", self, "");
        obj_id shoes = createObject("object/tangible/wearables/boots/boots_s04.iff", self, "");
        obj_id hair = createObject("object/tangible/hair/human/hair_human_male_s28.iff", self, "");
        hue.setColor(shirt, 1, 174);
        hue.setColor(pants, 1, 85);
        hue.setColor(hair, 1, 12);
        attachScript(self, "npc.static_quest.quest_convo");
        setObjVar(self, "quest_table", "huff_darklighter_krayt");
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        String itemName = getTemplateName(item);
        if (itemName.equals("object/tangible/loot/quest/rifle_quest_tusken.iff"))
        {
            destroyObject(item);
            string_id thatsIt = new string_id("epic_quest/krayt_skull/huff_darklighter", "thats_it");
            chat.chat(self, thatsIt);
            setObjVar(player, "epic.krayt.rifle", 1);
        }
        return SCRIPT_CONTINUE;
    }
}

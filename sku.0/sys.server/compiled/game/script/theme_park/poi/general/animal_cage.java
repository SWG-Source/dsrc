package script.theme_park.poi.general;

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

public class animal_cage extends script.theme_park.poi.base
{
    public animal_cage()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "runAnimalCage", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "runAnimalCage", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int runAnimalCage(obj_id self, dictionary params) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("animal"))
        {
            obj_id crate = poiCreateObject(self, "cage", "object/tangible/container/drum/tatt_drum_1.iff", 0, 1);
            obj_id scientist = poiCreateNpc("scientist", 1, 1);
            attachScript(scientist, "theme_park.poi.general.conversation.animal_cage_convo");
            String sciName = getName(scientist);
            setName(scientist, "Dr. " + sciName);
            setObjVar(scientist, "scientist", 1);
            obj_id jacket = createObject("object/tangible/wearables/jacket/jacket_s21.iff", scientist, "");
            hue.setColor(jacket, 1, colors.WHITE);
            hue.setColor(jacket, 2, colors.WHITE);
            ai_lib.setDefaultCalmBehavior(scientist, ai_lib.BEHAVIOR_SENTINEL);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}

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

public class nightsister extends script.base_script
{
    public nightsister()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int clothesType = rand(1, 2);
        if (clothesType == 1)
        {
            obj_id dress = createObject("object/tangbile/wearables/dress/nightsister_dress.iff", self, "");
            obj_id boots = createObject("object/tangible/wearables/boots/nightsister_boots.iff", self, "");
            obj_id hat = createObject(pickHat(), self, "");
            hue.setColor(boots, 1, rand(1, 9));
        }
        else 
        {
            obj_id hat = createObject(pickHat(), self, "");
            obj_id shirt = createObject(pickShirt(), self, "");
            obj_id pants = createObject(pickPants(), self, "");
            obj_id boots = createObject("object/tangible/wearables/boots/nightsister_boots.iff", self, "");
            hue.setColor(pants, 1, rand(1, 158));
            hue.setColor(pants, 2, rand(1, 90));
            hue.setColor(boots, 1, rand(1, 9));
        }
        hue.randomizeObject(self);
        setName(self, "Nightsister");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public String pickHat() throws InterruptedException
    {
        int hatType = rand(1, 3);
        String hat = "";
        switch (hatType)
        {
            case 1:
            hat = "object/tangible/wearables/hat/nightsister_hat_s01.iff";
            break;
            case 2:
            hat = "object/tangible/wearables/hat/nightsister_hat_s02.iff";
            break;
            case 3:
            hat = "object/tangible/wearables/hat/nightsister_hat_s03.iff";
            break;
        }
        return hat;
    }
    public String pickShirt() throws InterruptedException
    {
        int shirtType = rand(1, 3);
        String shirt = "";
        switch (shirtType)
        {
            case 1:
            shirt = "object/tangible/wearables/shirt/nightsister_shirt_s01.iff";
            break;
            case 2:
            shirt = "object/tangible/wearables/shirt/nightsister_shirt_s02.iff";
            break;
            case 3:
            shirt = "object/tangible/wearables/shirt/nightsister_shirt_s03.iff";
            break;
        }
        return shirt;
    }
    public String pickPants() throws InterruptedException
    {
        int pantsType = rand(1, 2);
        String pants = "";
        switch (pantsType)
        {
            case 1:
            pants = "object/tangible/wearables/pants/nightsister_pants_s01.iff";
            break;
            case 2:
            pants = "object/tangible/wearables/pants/nightsister_pants_s02.iff";
            break;
        }
        return pants;
    }
}

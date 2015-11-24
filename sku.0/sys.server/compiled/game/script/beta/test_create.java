package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;
import script.library.utils;

public class test_create extends script.base_script
{
    public test_create()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        int stringCheck = text.indexOf("create ");
        if (stringCheck > -1)
        {
            String toCreate = text.substring(text.indexOf(" ") + 1);
            String[] createParms = split(toCreate, ' ');
            location here = getLocation(self);
            location heading = getHeading(self);
            int numToMake = 1;
            float distance = 1.25f;
            float spacing = 1.25f;
            boolean spaceUsingCollisionRadius = true;
            if (createParms.length > 1)
            {
                numToMake = utils.stringToInt(createParms[1]);
                if (createParms.length > 2)
                {
                    distance = utils.stringToFloat(createParms[2]);
                    if (createParms.length > 3)
                    {
                        spacing = utils.stringToFloat(createParms[3]);
                        spaceUsingCollisionRadius = false;
                    }
                }
            }
            for (int i = 0; i < numToMake; ++i)
            {
                final float offset = i == 0 ? distance : spacing;
                here.x += heading.x * offset;
                here.z += heading.z * offset;
                final obj_id creature = create.object(createParms[0], here);
                if (spaceUsingCollisionRadius && isValidId(creature))
                {
                    spacing = getObjectCollisionRadius(creature) * 2.5f;
                }
            }
            return SCRIPT_CONTINUE;
        }
        stringCheck = text.indexOf("lookat ");
        if (stringCheck > -1)
        {
            String toCreate = text.substring(text.indexOf(" ") + 1, text.length());
            location here = getLocation(self);
            here.x = here.x + 1.25f;
            obj_id guy = create.object(toCreate, here);
            if (!isIdValid(guy))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasScript(guy, "ai.ai"))
            {
                stop(guy);
                ai_lib.setDefaultCalmBehavior(guy, ai_lib.BEHAVIOR_SENTINEL);
            }
            return SCRIPT_CONTINUE;
        }
        stringCheck = text.indexOf("baby ");
        if (stringCheck > -1)
        {
            String toCreate = text.substring(text.indexOf(" ") + 1, text.length());
            location here = getLocation(self);
            here.x = here.x + 1.25f;
            obj_id guy = create.object(toCreate, here);
            if (isIdValid(guy))
            {
                attachScript(guy, "ai.pet_advance");
            }
            return SCRIPT_CONTINUE;
        }
        stringCheck = text.indexOf("persist ");
        if (stringCheck > -1)
        {
            String toCreate = text.substring(text.indexOf(" ") + 1, text.length());
            location here = getLocation(self);
            here.x = here.x + 2;
            String[] createParms = split(toCreate, ' ');
            if (createParms.length > 1)
            {
                int numToMake = utils.stringToInt(createParms[1]);
                for (int i = 0; i < numToMake; i++)
                {
                    obj_id newNpc = create.object(createParms[0], here);
                    if (isIdValid(newNpc))
                    {
                        persistObject(newNpc);
                    }
                    else 
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            else 
            {
                obj_id newNpc = create.object(toCreate, here);
                if (isIdValid(newNpc))
                {
                    persistObject(newNpc);
                }
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}

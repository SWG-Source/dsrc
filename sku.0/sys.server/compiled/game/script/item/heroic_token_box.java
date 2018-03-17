package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class heroic_token_box extends script.base_script
{
    public heroic_token_box()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "item.set.tokens_held"))
        {
            trial.initializeBox(self);
        }
        else 
        {
            trial.verifyBox(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "item.set.tokens_held"))
        {
            trial.initializeBox(self);
        }
        else 
        {
            trial.verifyBox(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "item.set.tokens_held"))
        {
            return SCRIPT_CONTINUE;
        }
        int[] tokenTypes = getIntArrayObjVar(self, "item.set.tokens_held");
        if (tokenTypes.length == trial.NUM_HEROIC_TOKEN_TYPES)
        {
            for (int i = 0; i < tokenTypes.length; i++)
            {
                names[free] = utils.packStringId(new string_id("static_item_n", trial.HEROIC_TOKENS[i]));
                attribs[free++] = "" + tokenTypes[i];
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id whoDat = getTopMostContainer(self);
        sendSystemMessage(whoDat, new string_id("spam", "can_not_destroy"));
        return SCRIPT_OVERRIDE;
    }
}

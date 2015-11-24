package script.item.dice;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.debug;
import script.library.xp;
import script.library.craftinglib;

public class configurable_group_dice extends script.item.dice.base.base_dice
{
    public configurable_group_dice()
    {
    }
    public static final int DEFAULT_FACE_COUNT = 20;
    public static final int NUMBER_OF_DICE = 1;
    public static final String TYPE_NAME = "configurable_group_dice";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########configurable dice script initialized##############");
        setObjVar(self, VAR_FACE_COUNT, DEFAULT_FACE_COUNT);
        setObjVar(self, VAR_DICE_COUNT, NUMBER_OF_DICE);
        setObjVar(self, DICE_TYPE_NAME, TYPE_NAME);
        return super.OnInitialize(self);
    }
    public int roll(obj_id self, dictionary params) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########configurable dice roll()##############");
        obj_id player = params.getObjId("player");
        xp.grantCraftingXpChance(self, player, 40);
        int faceCount = getIntObjVar(self, VAR_FACE_COUNT);
        int diceCount = getIntObjVar(self, VAR_DICE_COUNT);
        int[] rollValues = new int[diceCount];
        debug.debugAllMsg("DEBUG", self, "##########FACECOUNT: " + faceCount + "##############");
        for (int i = 0; i < diceCount; ++i)
        {
            int roll = rand(1, faceCount, 0);
            rollValues[i] = roll;
        }
        if (rollValues == null)
        {
            debug.debugAllMsg("DEBUG", self, "#############NULL FOUND IN CONFIGURABLE DICE############");
            return SCRIPT_CONTINUE;
        }
        if (rollValues.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, VAR_ROLL_RESULT, rollValues);
        for (int i = 0; i < rollValues.length; ++i)
        {
            debug.debugAllMsg("DEBUG", self, "#############Roll Values: " + rollValues[i] + "############");
        }
        if (getBooleanObjVar(self, VAR_VALUE_INITIALIZED))
        {
            informGroupOfResults(rollValues, player, self, null);
        }
        else 
        {
            sendSystemMessage(player, CONFIGURE_MSG);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########configurable dice script detached##############");
        return super.OnDetach(self);
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".useModifier"))
        {
            names[idx] = "usemodifier";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".useModifier");
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}

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

public class chance_cube extends script.item.dice.base.base_dice
{
    public chance_cube()
    {
    }
    public static final String DICE_FACE_COLOR_RED = "red";
    public static final String DICE_FACE_COLOR_BLUE = "blue";
    public static final int DICE_FACE_COUNT = 2;
    public static final int NUMBER_OF_DICE = 1;
    public static final String TYPE_NAME = "chance_cube";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########chance_cube initialized##############");
        setObjVar(self, DICE_TYPE_NAME, TYPE_NAME);
        return super.OnInitialize(self);
    }
    public int roll(obj_id self, dictionary params) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########chance_cube roll()##############");
        obj_id player = params.getObjId("player");
        xp.grantCraftingXpChance(self, player, 40);
        int rollResult = rand(NUMBER_OF_DICE, DICE_FACE_COUNT, 0);
        if (rollResult == 1)
        {
            setObjVar(self, VAR_ROLL_RESULT, DICE_FACE_COLOR_RED);
        }
        if (rollResult == 2)
        {
            setObjVar(self, VAR_ROLL_RESULT, DICE_FACE_COLOR_BLUE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########chance script detached##############");
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

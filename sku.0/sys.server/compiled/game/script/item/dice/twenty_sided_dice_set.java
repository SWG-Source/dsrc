package script.item.dice;

import script.dictionary;
import script.library.craftinglib;
import script.library.debug;
import script.library.utils;
import script.library.xp;
import script.obj_id;

public class twenty_sided_dice_set extends script.item.dice.base.base_dice
{
    public twenty_sided_dice_set()
    {
    }
    public static final int FACE_COUNT = 20;
    public static final int DEFAULT_DICE_COUNT = 1;
    public static final String TYPE_NAME = "twenty_sided_dice_set";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########twenty sided dice script initialized##############");
        setObjVar(self, VAR_FACE_COUNT, FACE_COUNT);
        setObjVar(self, VAR_DICE_COUNT, DEFAULT_DICE_COUNT);
        setObjVar(self, DICE_TYPE_NAME, TYPE_NAME);
        return super.OnInitialize(self);
    }
    public int roll(obj_id self, dictionary params) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########twenty sided dice roll()##############");
        obj_id player = params.getObjId("player");
        xp.grantCraftingXpChance(self, player, 40);
        int faceCount = getIntObjVar(self, VAR_FACE_COUNT);
        int diceCount = getIntObjVar(self, VAR_DICE_COUNT);
        int[] rollValues = new int[diceCount];
        for (int i = 0; i < diceCount; ++i)
        {
            int roll = rand(1, faceCount, 0);
            rollValues[i] = roll;
        }
        for (int rollValue : rollValues) {
            debug.debugAllMsg("DEBUG", self, "#############Roll Values: " + rollValue + "############");
        }
        setObjVar(self, VAR_ROLL_RESULT, rollValues);
        informGroupOfResults(rollValues, player, self, null);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########twenty sided dice script detached##############");
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

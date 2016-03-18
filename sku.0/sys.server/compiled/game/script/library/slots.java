package script.library;

import script.obj_id;

import java.util.Vector;

public class slots extends script.base_script
{
    public slots()
    {
    }
    public static final String BACK = "back";
    public static final String BICEP_L = "bicep_l";
    public static final String BICEP_R = "bicep_r";
    public static final String BRACER_L = "bracer_l";
    public static final String BRACER_R = "bracer_r";
    public static final String CHEST1 = "chest1";
    public static final String CHEST2 = "chest2";
    public static final String CHEST3 = "chest3";
    public static final String CLOAK = "cloak";
    public static final String EARRING_L = "earring_l";
    public static final String EARRING_R = "earring_r";
    public static final String EYES = "eyes";
    public static final String GLOVES = "gloves";
    public static final String HAIR = "hair";
    public static final String HAT = "hat";
    public static final String HOLD_L = "hold_l";
    public static final String HOLD_R = "hold_r";
    public static final String MOUTH = "mouth";
    public static final String NECK = "neck";
    public static final String PANTS1 = "pants1";
    public static final String PANTS2 = "pants2";
    public static final String RING_L = "ring_l";
    public static final String RING_R = "ring_r";
    public static final String SHOES = "shoes";
    public static final String UTILITY_BELT = "utility_belt";
    public static final String WRIST_L = "wrist_l";
    public static final String WRIST_R = "wrist_r";
    public static final String[] EQ_SLOTS = 
    {
        BACK,
        BICEP_L,
        BICEP_R,
        BRACER_L,
        BRACER_R,
        CHEST1,
        CHEST2,
        CHEST3,
        CLOAK,
        EARRING_L,
        EARRING_R,
        EYES,
        GLOVES,
        HAT,
        HOLD_L,
        HOLD_R,
        MOUTH,
        NECK,
        PANTS1,
        PANTS2,
        RING_L,
        RING_R,
        SHOES,
        UTILITY_BELT,
        WRIST_L,
        WRIST_R
    };
    public static String[] getOccupiedSlots(obj_id target, String[] slots) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((slots == null) || (slots.length == 0))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_id item;
        for (String name : slots) {
            item = getObjectInSlot(target, name);
            if (isIdValid(item)) {
                ret = utils.addElement(ret, name);
            }
        }
        if ((ret == null) || (ret.size() == 0))
        {
            return null;
        }
        String[] _ret = new String[0];
        _ret = new String[ret.size()];
        ret.toArray(_ret);
        return _ret;
    }
    public static String[] getOccupiedEqSlots(obj_id target) throws InterruptedException
    {
        return getOccupiedSlots(target, EQ_SLOTS);
    }
}

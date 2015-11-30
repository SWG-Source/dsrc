package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class heroic_random_stat_item extends script.base_script
{
    public heroic_random_stat_item()
    {
    }
    public static final String[] STAT_ONE = 
    {
        "agility_modified",
        "stamina_modified",
        "constitution_modified"
    };
    public static final String[] STAT_TWO = 
    {
        "precision_modified",
        "strength_modified",
        "luck_modified"
    };
    public static final String[] STAT_THREE = 
    {
        "expertise_action_weapon_0",
        "expertise_action_weapon_1",
        "expertise_action_weapon_10",
        "expertise_action_weapon_11",
        "expertise_action_weapon_2",
        "expertise_action_weapon_3",
        "expertise_action_weapon_4",
        "expertise_action_weapon_5",
        "expertise_action_weapon_6",
        "expertise_action_weapon_7",
        "expertise_action_weapon_9"
    };
    public static final int[] STAT_VALS = 
    {
        25,
        25,
        2
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "skillmod.bonus"))
        {
            messageTo(self, "generateRandomStats", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "skillmod.bonus"))
        {
            messageTo(self, "generateRandomStats", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int generateRandomStats(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "skillmod.bonus"))
        {
            setObjVar(self, "skillmod.bonus." + STAT_ONE[rand(0, STAT_ONE.length - 1)], STAT_VALS[0]);
            setObjVar(self, "skillmod.bonus." + STAT_TWO[rand(0, STAT_TWO.length - 1)], STAT_VALS[1]);
            setObjVar(self, "skillmod.bonus.expertise_action_weapon_" + getWeightedWeaponValue(), STAT_VALS[2]);
        }
        return SCRIPT_CONTINUE;
    }
    public int getWeightedWeaponValue() throws InterruptedException
    {
        int value = 0;
        int weightingRoll = rand(0, 100);
        if (weightingRoll <= 57)
        {
            int[] weaponChoices = 
            {
                0,
                1,
                2
            };
            value = weaponChoices[rand(0, weaponChoices.length - 1)];
        }
        else if (weightingRoll >= 78)
        {
            int[] weaponChoices = 
            {
                9,
                10,
                11
            };
            value = weaponChoices[rand(0, weaponChoices.length - 1)];
        }
        else 
        {
            int[] weaponChoices = 
            {
                4,
                5,
                6,
                7
            };
            value = weaponChoices[rand(0, weaponChoices.length - 1)];
        }
        return value;
    }
}

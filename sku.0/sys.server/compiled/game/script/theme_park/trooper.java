package script.theme_park;

import script.library.utils;
import script.library.weapons;
import script.obj_id;

public class trooper extends script.base_script
{
    public trooper()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_boots_combo.iff", self, "shoes");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_chest_plate_combo.iff", self, "chest2");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_gauntlets_combo.iff", self, "gloves");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_left_bracer_combo.iff", self, "bracer_l");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_right_bracer_combo.iff", self, "bracer_r");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_left_sleeve_combo.iff", self, "bicep_l");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_right_sleeve_combo.iff", self, "bicep_r");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_leggings_combo.iff", self, "pants2");
        createObject("object/tangible/wearables/armor/stormtrooper/armor_stormtrooper_helmet_combo.iff", self, "hat");
        obj_id weapon = weapons.createWeapon("object/weapon/ranged/rifle/rifle_e11.iff", utils.getInventoryContainer(self), rand(0.9f, 1.1f));
        if (isIdValid(weapon))
        {
            equip(weapon, self, "hold_r");
        }
        setName(self, "Stormtrooper");
        return SCRIPT_CONTINUE;
    }
}

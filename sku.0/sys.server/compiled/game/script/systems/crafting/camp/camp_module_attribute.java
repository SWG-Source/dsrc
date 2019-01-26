package script.systems.crafting.camp;

import script.library.craftinglib;
import script.library.pet_lib;
import script.library.utils;
import script.obj_id;

import java.util.Vector;

public class camp_module_attribute extends script.base_script
{
    public camp_module_attribute()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_module"))
        {
            names[idx] = "armor_module";
            float attrib = getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_module");
            if (attrib == 0.0f)
            {
                attrib = getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_module");
                String objvarName = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_module";
                setObjVar(self, objvarName, attrib);
            }
            attribs[idx] = " " + (int)(attrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality"))
        {
            names[idx] = "mechanism_quality";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality");
            attribs[idx] = " " + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".medical_module"))
        {
            names[idx] = "medpower";
            int med = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".medical_module");
            attribs[idx] = " " + med;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".repair_module"))
        {
            names[idx] = "is_repair_droid";
            attribs[idx] = " ";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".storage_module"))
        {
            names[idx] = "storage_module_rating";
            int storage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".storage_module");
            attribs[idx] = " " + storage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".data_module"))
        {
            names[idx] = "data_module_rating";
            int datastorage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".data_module");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cmbt_module"))
        {
            names[idx] = "combat_module_rating";
            int datastorage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cmbt_module");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".crafting_module"))
        {
            int craftingModuleValue = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".crafting_module");
            if ((craftingModuleValue > 0) && (craftingModuleValue < 100000))
            {
                names[idx] = "crafting_station";
                attribs[idx] = " installed";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
                if (craftingModuleValue >= 10000)
                {
                    names[idx] = "crafting_station_space";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 10000) * 10000));
                }
                if (craftingModuleValue >= 1000)
                {
                    names[idx] = "crafting_station_structure";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 1000) * 1000));
                }
                if (craftingModuleValue >= 100)
                {
                    names[idx] = "crafting_station_clothing";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 100) * 100));
                }
                if (craftingModuleValue >= 10)
                {
                    names[idx] = "crafting_station_food";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    craftingModuleValue = (craftingModuleValue - ((craftingModuleValue / 10) * 10));
                }
                if (craftingModuleValue >= 1)
                {
                    names[idx] = "crafting_station_weapon";
                    attribs[idx] = " installed";
                    idx++;
                    if (idx >= names.length)
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".bomb_level"))
        {
            names[idx] = "bomb_level";
            int datastorage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".bomb_level");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".stimpack_capacity"))
        {
            names[idx] = "stimpack_capacity";
            int datastorage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".stimpack_capacity");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".stimpack_speed"))
        {
            names[idx] = "stimpack_speed";
            int datastorage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".stimpack_speed");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".auto_repair_power"))
        {
            names[idx] = "auto_repair_power";
            int datastorage = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".auto_repair_power");
            attribs[idx] = " " + datastorage;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".playback_module"))
        {
            names[idx] = "playback_modules";
            int tracks = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".playback_module");
            attribs[idx] = " " + tracks;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".harvest_power"))
        {
            names[idx] = "harvest_power";
            int bonusHarvest = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".harvest_power");
            attribs[idx] = " " + bonusHarvest;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".struct_module"))
        {
            names[idx] = "struct_module_rating";
            int struct = getIntObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".struct_module");
            attribs[idx] = " " + struct;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".trap_bonus"))
        {
            names[idx] = "trap_bonus";
            int trapBonus = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".trap_bonus");
            attribs[idx] = " " + trapBonus;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".entertainer_effects"))
        {
            int raw_effects = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".entertainer_effects");
            if (raw_effects > 0)
            {
                int i = 1;
                Vector available_effects = new Vector();
                available_effects.setSize(0);
                while (i <= pet_lib.LIGHTING_EFFECTS.length)
                {
                    int result = (int)((raw_effects % StrictMath.pow(10, i)) / StrictMath.pow(10, i - 1));
                    if (result >= 1)
                    {
                        available_effects = utils.addElement(available_effects, pet_lib.LIGHTING_EFFECTS[i - 1]);
                    }
                    i++;
                }
                if (available_effects.size() > 0)
                {
                    for (Object available_effect : available_effects) {
                        names[idx] = (String) available_effect;
                        attribs[idx] = " installed";
                        idx++;
                        if (idx >= names.length) {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".merchant_barker"))
        {
            names[idx] = "merchant_barker";
            attribs[idx] = " installed";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}

package script.item.plant;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.resource;

public class player_plant_grow extends script.base_script
{
    public player_plant_grow()
    {
    }
    public static final String VAR_CRITICAL_ATTRIBS = "plant_grow.critical_attribs";
    public static final String VAR_WATER_LEVEL = "plant_grow.water_level";
    public static final String VAR_IDEAL_WATER_LEVEL = "plant_grow.water_level_ideal";
    public static final String VAR_WATER_QUALITY = "plant_grow.water_quality";
    public static final String VAR_NUTRIENT_LEVEL = "plant_grow.nutrient_level";
    public static final String VAR_IDEAL_NUTRIENT_LEVEL = "plant_grow.nutrient_level_ideal";
    public static final String VAR_NUTRIENT_QUALITY = "plant_grow.nutrient_quality";
    public static final String VAR_HEALTH = "plant_grow.health";
    public static final String VAR_FRUIT = "plant_grow.fruit";
    public static final String VAR_GROWTH = "plant_grow.growth";
    public static final String VAR_SIZE = "plant_grow.size";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.plant.player_plant_grow");
        return SCRIPT_CONTINUE;
    }
    public int msgPlantResourceSelected(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "item.plant.player_plant_grow");
        if (utils.hasScriptVar(self, "plant_grow.sui"))
        {
            utils.removeScriptVar(self, "plant_grow.sui");
        }
        String[] nutrient_list;
        if (utils.hasScriptVar(self, "plant_grow.nutrient_list"))
        {
            nutrient_list = utils.getStringArrayScriptVar(self, "plant_grow.nutrient_list");
            utils.removeScriptVar(self, "plant_grow.nutrient_list");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        obj_id plant;
        if (utils.hasScriptVar(self, "plant_grow.plant"))
        {
            plant = utils.getObjIdScriptVar(self, "plant_grow.plant");
            utils.removeScriptVar(self, "plant_grow.plant");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] container_list;
        if (utils.hasScriptVar(self, "plant_grow.container_list"))
        {
            container_list = utils.getObjIdArrayScriptVar(self, "plant_grow.container_list");
            utils.removeScriptVar(self, "plant_grow.container_list");
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= nutrient_list.length)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id container = container_list[row_selected];
        obj_id resource_type = getResourceContainerResourceType(container);
        if (!isIdValid(resource_type))
        {
            LOG("plant_grow", "player_plant_grow.msgPlantResourceSelected -- resource_type is null.");
            return SCRIPT_CONTINUE;
        }
        String resource_class = getResourceClass(resource_type);
        int qty = getResourceContainerQuantity(container);
        if (qty < 25)
        {
            sendSystemMessage(self, new string_id("plant_grow", "not_enough_resource"));
            return SCRIPT_CONTINUE;
        }
        int water_level = 0;
        if (hasObjVar(plant, VAR_WATER_LEVEL))
        {
            water_level = getIntObjVar(plant, VAR_WATER_LEVEL);
        }
        int water_quality = 0;
        if (hasObjVar(plant, VAR_WATER_QUALITY))
        {
            water_quality = getIntObjVar(plant, VAR_WATER_QUALITY);
        }
        int nutrient_level = 0;
        if (hasObjVar(plant, VAR_NUTRIENT_LEVEL))
        {
            nutrient_level = getIntObjVar(plant, VAR_NUTRIENT_LEVEL);
        }
        int nutrient_quality = 0;
        if (hasObjVar(plant, VAR_NUTRIENT_QUALITY))
        {
            nutrient_quality = getIntObjVar(plant, VAR_NUTRIENT_QUALITY);
        }
        String[] critical_attribs = getStringArrayObjVar(plant, VAR_CRITICAL_ATTRIBS);
        if (critical_attribs == null || critical_attribs.length < 2)
        {
            LOG("plant_grow", "player_plant_grow.msgPlantResourceSelected -- can't find critical resources.");
            return SCRIPT_CONTINUE;
        }
        int critical_attrib_1 = getResourceAttribute(resource_type, critical_attribs[0]);
        int critical_attrib_2 = getResourceAttribute(resource_type, critical_attribs[1]);
        int quality = getResourceAttribute(resource_type, "res_quality");
        if (resource.isOrganic(resource_class))
        {
            if (nutrient_level > 100)
            {
                sendSystemMessage(self, new string_id("plant_grow", "too_much_nutrients"));
                return SCRIPT_CONTINUE;
            }
            if (critical_attrib_1 == 0)
            {
                critical_attrib_1 = critical_attrib_2;
            }
            else if (critical_attrib_2 == 0)
            {
                critical_attrib_2 = critical_attrib_1;
            }
            int nutrient_factor = (critical_attrib_1 + critical_attrib_2) / 2;
            int new_quality = (nutrient_factor * 25 + nutrient_quality * nutrient_level) / (nutrient_level + 25);
            nutrient_level += 5;
            setObjVar(plant, VAR_NUTRIENT_LEVEL, nutrient_level);
            setObjVar(plant, VAR_NUTRIENT_QUALITY, new_quality);
            sendSystemMessage(self, new string_id("plant_grow", "apply_nutrients"));
        }
        else 
        {
            if (water_level > 100)
            {
                sendSystemMessage(self, new string_id("plant_grow", "too_much_water"));
                return SCRIPT_CONTINUE;
            }
            int new_quality = (quality * 25 + nutrient_quality * water_level) / (water_level + 25);
            water_level += 5;
            setObjVar(plant, VAR_WATER_LEVEL, water_level);
            setObjVar(plant, VAR_WATER_QUALITY, new_quality);
            sendSystemMessage(self, new string_id("plant_grow", "apply_water"));
        }
        if (qty == 25)
        {
            destroyObject(container);
        }
        else 
        {
            removeResourceFromContainer(container, resource_type, 25);
        }
        return SCRIPT_CONTINUE;
    }
}

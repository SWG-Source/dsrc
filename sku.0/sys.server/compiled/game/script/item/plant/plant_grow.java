package script.item.plant;

import script.*;
import script.library.player_structure;
import script.library.resource;
import script.library.sui;
import script.library.utils;

import java.util.Vector;

public class plant_grow extends script.base_script
{
    public plant_grow()
    {
    }
    public static final int PULSE_RATE = 21600;
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
    public static final String VAR_GROWTH_RATE = "plant_grow.growth_rate";
    public static final String VAR_SIZE = "plant_grow.size";
    public static final String VAR_LAST_PULSE = "plant_grow.last_pulse";
    public static final String VAR_FAST_PULSE = "plant_grow.fast_pulse";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "plant_grow.initialized"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, VAR_CRITICAL_ATTRIBS))
        {
            setObjVar(self, VAR_HEALTH, 50);
            setObjVar(self, VAR_WATER_LEVEL, 51);
            setObjVar(self, VAR_NUTRIENT_LEVEL, 51);
            setObjVar(self, VAR_WATER_QUALITY, 500);
            setObjVar(self, VAR_NUTRIENT_QUALITY, 500);
            setObjVar(self, VAR_GROWTH, 0);
            setObjVar(self, VAR_FRUIT, 0);
            setObjVar(self, VAR_SIZE, 1);
            intializePlant(self);
        }
        utils.setScriptVar(self, "plant_grow.initialized", 1);
        if (getIntObjVar(self, VAR_SIZE) > 0)
        {
            messageTo(self, "msgPlantGrowPulse", null, 60.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (getIntObjVar(self, VAR_SIZE) == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int nutrient_root = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("plant_grow", "nutrients"));
        mi.addSubMenu(nutrient_root, menu_info_types.SERVER_MENU2, new string_id("plant_grow", "add_nutrients"));
        mi.addSubMenu(nutrient_root, menu_info_types.SERVER_MENU3, new string_id("plant_grow", "remove_nutrients_menu"));
        int water_root = mi.addRootMenu(menu_info_types.SERVER_MENU4, new string_id("plant_grow", "water"));
        mi.addSubMenu(water_root, menu_info_types.SERVER_MENU5, new string_id("plant_grow", "add_water"));
        mi.addSubMenu(water_root, menu_info_types.SERVER_MENU6, new string_id("plant_grow", "remove_water_menu"));
        if (getIntObjVar(self, VAR_FRUIT) > 0)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU7, new string_id("plant_grow", "pick_fruit_menu"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (getIntObjVar(self, VAR_SIZE) == 0)
        {
            return SCRIPT_CONTINUE;
        }
        boolean can_use = false;
        obj_id structure = player_structure.getStructure(player);
        if (isIdValid(structure))
        {
            if (!player_structure.isInstallation(structure))
            {
                if (player_structure.isAdmin(structure, player))
                {
                    can_use = true;
                }
            }
        }
        if (!can_use)
        {
            sendSystemMessage(player, new string_id("plant_grow", "must_be_in_building"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1 || item == menu_info_types.SERVER_MENU2)
        {
            showPlantResourceSUI(player, self, "nutrient");
        }
        if (item == menu_info_types.SERVER_MENU4 || item == menu_info_types.SERVER_MENU5)
        {
            showPlantResourceSUI(player, self, "water");
        }
        if (item == menu_info_types.SERVER_MENU3)
        {
            int nutrient_level = getIntObjVar(self, VAR_NUTRIENT_LEVEL);
            nutrient_level -= 5;
            if (nutrient_level < 0)
            {
                nutrient_level = 0;
            }
            setObjVar(self, VAR_NUTRIENT_LEVEL, nutrient_level);
            sendSystemMessage(player, new string_id("plant_grow", "remove_nutrients"));
        }
        if (item == menu_info_types.SERVER_MENU6)
        {
            int water_level = getIntObjVar(self, VAR_WATER_LEVEL);
            water_level -= 5;
            if (water_level < 0)
            {
                water_level = 0;
            }
            setObjVar(self, VAR_WATER_LEVEL, water_level);
            sendSystemMessage(player, new string_id("plant_grow", "remove_water"));
        }
        if (item == menu_info_types.SERVER_MENU7)
        {
            int fruit = getIntObjVar(self, VAR_FRUIT);
            if (fruit < 1)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id inv = getObjectInSlot(player, "inventory");
            if (isIdValid(inv))
            {
                int free_space = getVolumeFree(inv);
                if (free_space < 1)
                {
                    sendSystemMessage(player, new string_id("plant_grow", "no_inventory"));
                    return SCRIPT_CONTINUE;
                }
                setObjVar(self, VAR_FRUIT, fruit - 1);
                createObject("object/tangible/item/plant/force_melon.iff", inv, "");
                sendSystemMessage(player, new string_id("plant_grow", "pick_fruit"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "plant_health_n";
        int health = getIntObjVar(self, VAR_HEALTH);
        if (health < 10 && health > 0)
        {
            health = 10;
        }
        attribs[idx] = "@plant_grow:health_" + (health / 10);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        int fruit = getIntObjVar(self, VAR_FRUIT);
        if (fruit > 0)
        {
            names[idx] = "plant_fruit";
            attribs[idx] = Integer.toString(fruit);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = "plant_growth_rate_n";
        int growth_rate = getIntObjVar(self, VAR_GROWTH_RATE);
        if (growth_rate > 10)
        {
            growth_rate = 10;
        }
        if (growth_rate < -3)
        {
            growth_rate = -3;
        }
        if (growth_rate < 0)
        {
            int abs_val = Math.abs(growth_rate);
            attribs[idx] = "@plant_grow:growth_loss_" + abs_val;
        }
        else 
        {
            attribs[idx] = "@plant_grow:growth_rate_" + growth_rate;
        }
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "plant_water_level_n";
        int water_level = getIntObjVar(self, VAR_WATER_LEVEL);
        if (water_level > 100)
        {
            water_level = 100;
        }
        attribs[idx] = "@plant_grow:water_level_" + (water_level / 10);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "plant_nutrient_level_n";
        int nutrient_level = getIntObjVar(self, VAR_NUTRIENT_LEVEL);
        if (nutrient_level > 100)
        {
            nutrient_level = 100;
        }
        attribs[idx] = "@plant_grow:nutrient_level_" + (nutrient_level / 10);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int msgPlantGrowPulse(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("plant_grow", "plant_grow.msgPlantGrowPulse");
        obj_id structure = player_structure.getStructure(self);
        if (!isIdValid(structure))
        {
            setObjVar(self, VAR_LAST_PULSE, getGameTime());
            messageTo(self, "msgPlantGrowPulse", null, getPulseRate(self), false);
            return SCRIPT_CONTINUE;
        }
        int last_pulse = getIntObjVar(self, VAR_LAST_PULSE);
        if (last_pulse < 1)
        {
            last_pulse = getGameTime();
        }
        int loops = (getGameTime() - last_pulse) / getPulseRate(self);
        if (loops < 1)
        {
            loops = 1;
        }
        else if (loops > 10)
        {
            loops = 10;
        }
        setObjVar(self, VAR_LAST_PULSE, getGameTime());
        int ideal_water = getIntObjVar(self, VAR_IDEAL_WATER_LEVEL);
        int ideal_nutrient = getIntObjVar(self, VAR_IDEAL_NUTRIENT_LEVEL);
        int water_level = getIntObjVar(self, VAR_WATER_LEVEL);
        int nutrient_level = getIntObjVar(self, VAR_NUTRIENT_LEVEL);
        int water_quality = getIntObjVar(self, VAR_WATER_QUALITY);
        int nutrient_quality = getIntObjVar(self, VAR_NUTRIENT_QUALITY);
        int health = getIntObjVar(self, VAR_HEALTH);
        int growth = getIntObjVar(self, VAR_GROWTH);
        int fruit = getIntObjVar(self, VAR_FRUIT);
        int size = getIntObjVar(self, VAR_SIZE);
        int diff_water = Math.abs(water_level - ideal_water);
        int diff_nutrient = Math.abs(nutrient_level - ideal_nutrient);
        LOG("plant_grow", "diff ->" + diff_water + " / " + diff_nutrient);
        if (diff_water < 20 && diff_nutrient < 20)
        {
            int health_change = (((40 - (diff_water + diff_nutrient)) * (water_quality + nutrient_quality) / 2000) / 5) * loops;
            if (health_change < 1)
            {
                health_change = 1;
            }
            LOG("plant_grow", "health_change ->" + health_change);
            health += health_change;
            if (health > 100)
            {
                health = 100;
            }
            setObjVar(self, VAR_HEALTH, health);
            setObjVar(self, VAR_GROWTH_RATE, health_change / loops);
            if (health >= 70)
            {
                int change_loop = health_change / loops;
                int growth_change = (((health / 20) + change_loop) * (water_quality + nutrient_quality) / 2000) * loops;
                LOG("plant_grow", "growth_change ->" + growth_change);
                growth += growth_change;
                setObjVar(self, VAR_GROWTH, growth);
            }
        }
        else 
        {
            if (diff_water < 20)
            {
                diff_water = 20;
            }
            if (diff_nutrient < 20)
            {
                diff_nutrient = 20;
            }
            int health_change = (((diff_water - 20) + (diff_nutrient - 20)) / 10) * loops;
            if (health_change > 6)
            {
                health_change = 6;
            }
            if (health_change < 1)
            {
                health_change = 1;
            }
            LOG("plant_grow", "health_loss ->" + health_change);
            health -= health_change;
            setObjVar(self, VAR_HEALTH, health);
            int change_loop = health_change / loops;
            int growth_rate = change_loop / 2;
            if (growth_rate < 1)
            {
                growth_rate = 1;
            }
            setObjVar(self, VAR_GROWTH_RATE, growth_rate * -1);
            if (health < 1)
            {
                changeSize(self, 0);
                return SCRIPT_CONTINUE;
            }
        }
        nutrient_level -= loops;
        if (nutrient_level < 0)
        {
            nutrient_level = 0;
        }
        water_level -= loops;
        if (water_level < 0)
        {
            water_level = 0;
        }
        setObjVar(self, VAR_NUTRIENT_LEVEL, nutrient_level);
        setObjVar(self, VAR_WATER_LEVEL, water_level);
        if (growth > 100)
        {
            if (size < 3)
            {
                setObjVar(self, VAR_GROWTH, 0);
                changeSize(self, size + 1);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                fruit++;
                if (fruit > 10)
                {
                    fruit = 10;
                }
                setObjVar(self, VAR_FRUIT, fruit);
                setObjVar(self, VAR_GROWTH, 0);
            }
        }
        messageTo(self, "msgPlantGrowPulse", null, getPulseRate(self), false);
        return SCRIPT_CONTINUE;
    }
    public boolean showPlantResourceSUI(obj_id player, obj_id plant, String type) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (!isIdValid(plant))
        {
            return false;
        }
        if (type == null)
        {
            return false;
        }
        if (utils.hasScriptVar(player, "plant_grow.sui"))
        {
            int pid = utils.getIntScriptVar(player, "plant_grow.sui");
            forceCloseSUIPage(pid);
            utils.removeScriptVar(player, "plant_grow.sui");
        }
        obj_id inv = getObjectInSlot(player, "inventory");
        if (isIdValid(inv))
        {
            Vector dsrc = new Vector();
            dsrc.setSize(0);
            Vector container_list = new Vector();
            container_list.setSize(0);
            obj_id[] objects = utils.getContents(inv, false);
            for (obj_id object : objects) {
                if (isResourceContainer(object)) {
                    obj_id resource_type = getResourceContainerResourceType(object);
                    if (isIdValid(resource_type)) {
                        String resource_class = getResourceClass(resource_type);
                        if (resource_class != null) {
                            if (type.equals("nutrient")) {
                                if (resource.isOrganic(resource_class)) {
                                    dsrc.add(getResourceName(resource_type));
                                    container_list.add(object);
                                }
                            } else {
                                if (isResourceClassDerivedFrom(resource_class, resource.RT_LIQUID_WATER)) {
                                    dsrc.add(getResourceName(resource_type));
                                    container_list.add(object);
                                }
                            }
                        }
                    }
                }
            }
            if (dsrc.size() > 0)
            {
                int pid = sui.listbox(player, player, "@plant_grow:select_resource_body", sui.OK_CANCEL, "@plant_grow:select_resource_sub", dsrc, "msgPlantResourceSelected");
                utils.setScriptVar(player, "plant_grow.nutrient_list", dsrc);
                utils.setScriptVar(player, "plant_grow.container_list", container_list);
                utils.setScriptVar(player, "plant_grow.plant", plant);
                if (!hasScript(player, "item.plant.player_plant_grow"))
                {
                    attachScript(player, "item.plant.player_plant_grow");
                }
            }
            else 
            {
                if (type.equals("nutrient"))
                {
                    sendSystemMessage(player, new string_id("plant_grow", "no_nutrients"));
                }
                else 
                {
                    sendSystemMessage(player, new string_id("plant_grow", "no_water"));
                }
            }
        }
        return true;
    }
    public boolean intializePlant(obj_id plant) throws InterruptedException
    {
        if (!isIdValid(plant))
        {
            return false;
        }
        String[] critical_attribs = 
        {
            "res_decay_resist",
            "res_flavor",
            "res_potential_energy",
            "res_toughness",
            "res_shock_resistance",
            "res_malleability",
            "res_quality"
        };
        String[] plant_attribs = new String[2];
        int plant_attrib_1 = rand(0, critical_attribs.length - 1);
        plant_attribs[0] = critical_attribs[plant_attrib_1];
        int plant_attrib_2 = rand(0, critical_attribs.length - 1);
        if (plant_attrib_1 == plant_attrib_2)
        {
            while (plant_attrib_1 == plant_attrib_2)plant_attrib_2 = rand(0, critical_attribs.length - 1);
        }
        plant_attribs[1] = critical_attribs[plant_attrib_2];
        setObjVar(plant, VAR_CRITICAL_ATTRIBS, plant_attribs);
        int ideal_water_level;
        int ideal_nutrient_level;
        if (getIntObjVar(plant, VAR_SIZE) == 1)
        {
            ideal_water_level = rand(30, 70);
            ideal_nutrient_level = rand(30, 70);
        }
        else 
        {
            ideal_water_level = rand(10, 90);
            ideal_nutrient_level = rand(10, 90);
        }
        setObjVar(plant, VAR_IDEAL_WATER_LEVEL, ideal_water_level);
        setObjVar(plant, VAR_IDEAL_NUTRIENT_LEVEL, ideal_nutrient_level);
        return true;
    }
    public boolean changeSize(obj_id plant, int size) throws InterruptedException
    {
        if (!isIdValid(plant))
        {
            return false;
        }
        if (size < 0 || size > 3)
        {
            return false;
        }
        String[] templates = 
        {
            "object/tangible/loot/plant_grow/plant_stage_dead.iff",
            "object/tangible/loot/plant_grow/plant_stage_1.iff",
            "object/tangible/loot/plant_grow/plant_stage_2.iff",
            "object/tangible/loot/plant_grow/plant_stage_3.iff"
        };
        location loc = getLocation(plant);
        obj_id new_plant = createObject(templates[size], loc);
        if (size > 0)
        {
            copyObjVar(plant, new_plant, "plant_grow");
            intializePlant(new_plant);
            setObjVar(new_plant, VAR_SIZE, size);
            persistObject(new_plant);
        }
        destroyObject(plant);
        return true;
    }
    public int getPulseRate(obj_id plant) throws InterruptedException
    {
        if (hasObjVar(plant, VAR_FAST_PULSE))
        {
            return 30;
        }
        else 
        {
            return PULSE_RATE;
        }
    }
}

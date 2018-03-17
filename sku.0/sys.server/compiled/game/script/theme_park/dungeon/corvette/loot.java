package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.permissions;
import script.library.weapons;

public class loot extends script.base_script
{
    public loot()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.container.loot_crate");
        attachScript(self, "item.container.base.base_container");
        obj_id dungeon = getTopMostContainer(self);
        String newLoot = pickNewLoot(dungeon);
        obj_id cargo = null;
        if (newLoot.startsWith("object/weapon/"))
        {
            cargo = weapons.createWeapon(newLoot, self, rand(0.80f, 0.95f));
        }
        else 
        {
            cargo = createObject(newLoot, self, "");
        }
        String cargoName = getTemplateName(cargo);
        if (cargoName == null || cargoName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if ((cargoName.equals("object/tangible/component/vehicle/veh_power_plant_av21.iff")) || (cargoName.equals("object/tangible/component/weapon/corvette_rifle_barrel.iff")))
        {
            setCraftedId(cargo, cargo);
            setCrafter(cargo, cargo);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        String classTemplate = getSkillTemplate(player);
        if (classTemplate == null || classTemplate.startsWith("entertainer") || classTemplate.startsWith("trader"))
        {
            sendSystemMessage(player, new string_id("dungeon/corvette", "no_trader_farming_allowed"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_OPEN)
        {
            if (!hasObjVar(self, "used"))
            {
                setObjVar(self, "used", 1);
                spawnEnemies(self, player);
            }
            if (!hasObjVar(self, permissions.VAR_PERMISSION_BASE))
            {
                switch (getContainerType(self))
                {
                    case 0:
                    detachScript(self, "item.container.base.base_container");
                    break;
                    case 1:
                    utils.requestContainerOpen(player, self);
                    break;
                    default:
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String pickNewLoot(obj_id dungeon) throws InterruptedException
    {
        String newLoot = "object/tangible/wearables/shirt/shirt_s03_rebel.iff";
        String missionType = getStringObjVar(dungeon, "space_dungeon.quest_type");
        if (missionType == null || missionType.equals(""))
        {
            missionType = "none";
        }
        int pickNewLoot = rand(1, 10);
        if (missionType.equals("rebel_assassin") || missionType.equals("rebel_destroy") || missionType.equals("rebel_rescue"))
        {
            switch (pickNewLoot)
            {
                case 1:
                newLoot = "object/tangible/wearables/shirt/shirt_s03_rebel.iff";
                break;
                case 2:
                newLoot = "object/tangible/wearables/armor/marine/armor_marine_chest_plate_rebel.iff";
                break;
                case 3:
                newLoot = "object/tangible/component/vehicle/veh_power_plant_av21.iff";
                break;
                case 4:
                newLoot = "object/tangible/food/spice/spice_giggledust.iff";
                break;
                case 5:
                newLoot = "object/weapon/melee/sword/sword_blade_ryyk.iff";
                break;
                case 6:
                newLoot = "object/weapon/ranged/rifle/rifle_lightning.iff";
                break;
                case 7:
                newLoot = "object/tangible/wearables/shirt/shirt_s03_rebel.iff";
                break;
                case 8:
                newLoot = "object/tangible/wearables/armor/marine/armor_marine_chest_plate_rebel.iff";
                break;
                case 9:
                newLoot = "object/weapon/ranged/pistol/pistol_fwg5.iff";
                break;
                case 10:
                newLoot = "object/tangible/loot/quest/bantha_doll.iff";
                break;
                default:
                break;
            }
        }
        else if (missionType.equals("imperial_assassin") || missionType.equals("imperial_rescue") || missionType.equals("imperial_destroy"))
        {
            switch (pickNewLoot)
            {
                case 1:
                newLoot = "object/tangible/loot/loot_schematic/corellian_corvette_rifle_berserker_schematic.iff";
                break;
                case 2:
                newLoot = "object/tangible/component/weapon/corvette_rifle_barrel.iff";
                break;
                case 3:
                newLoot = "object/tangible/component/vehicle/veh_power_plant_av21.iff";
                break;
                case 4:
                newLoot = "object/weapon/ranged/rifle/rifle_e11.iff";
                break;
                case 5:
                newLoot = "object/weapon/ranged/rifle/rifle_lightning.iff";
                break;
                case 6:
                newLoot = "object/weapon/ranged/pistol/pistol_fwg5.iff";
                break;
                case 7:
                newLoot = "object/weapon/melee/sword/sword_blade_ryyk.iff";
                break;
                case 8:
                newLoot = "object/tangible/loot/quest/bantha_doll.iff";
                break;
                case 9:
                newLoot = "object/tangible/food/spice/spice_crash_n_burn.iff";
                break;
                case 10:
                newLoot = "object/weapon/melee/baton/baton_stun.iff";
                break;
                default:
                break;
            }
        }
        else 
        {
            switch (pickNewLoot)
            {
                case 1:
                newLoot = "object/tangible/component/vehicle/veh_power_plant_av21.iff";
                break;
                case 2:
                newLoot = "object/tangible/loot/quest/bantha_doll.iff";
                break;
                case 3:
                newLoot = "object/tangible/food/spice/spice_giggledust.iff";
                break;
                case 4:
                newLoot = "object/weapon/melee/baton/baton_stun.iff";
                break;
                case 5:
                newLoot = "object/weapon/ranged/rifle/rifle_lightning.iff";
                break;
                case 6:
                newLoot = "object/weapon/melee/baton/baton_stun.iff";
                break;
                case 7:
                newLoot = "object/weapon/ranged/rifle/rifle_lightning.iff";
                break;
                case 8:
                newLoot = "object/weapon/ranged/rifle/rifle_e11.iff";
                break;
                case 9:
                newLoot = "object/weapon/melee/sword/sword_blade_ryyk.iff";
                break;
                case 10:
                newLoot = "object/tangible/food/spice/spice_crash_n_burn.iff";
                break;
                default:
                break;
            }
        }
        return newLoot;
    }
    public void spawnEnemies(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        location here = getLocation(self);
        obj_id cell = here.cell;
        String room = utils.getCellName(top, cell);
        location loc = getGoodLocation(top, room);
        String type = getTemplateName(top);
        if (type.equals("object/building/general/space_dungeon_corellian_corvette_imperial.iff"))
        {
            obj_id guard = create.object("rebel_super_battle_droid", loc);
            return;
        }
        if (type.equals("object/building/general/space_dungeon_corellian_corvette_rebel.iff"))
        {
            obj_id guard = create.object("imperial_super_battle_droid", loc);
        }
        else 
        {
            obj_id guard = create.object("corsec_super_battle_droid", loc);
        }
        return;
    }
}

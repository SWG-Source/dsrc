package script.item.structure_deed;

import script.*;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class player_structure_deed extends script.base_script
{
    public player_structure_deed()
    {
    }
    public static final string_id SID_NO_PLACING_STRUCTURES_IN_SPACE = new string_id("space/space_interaction", "no_placing_structures_in_space");
    public static final String LIGHT_HARVESTERS[] = 
    {
        "object/installation/mining_gas/mining_gas_harvester_style_1.iff",
        "object/installation/mining_liquid/mining_liquid_harvester_style_1.iff",
        "object/installation/mining_liquid/mining_liquid_moisture_harvester.iff",
        "object/installation/mining_ore/mining_ore_harvester_style_1.iff",
        "object/installation/mining_organic/mining_organic_flora_farm.iff"
    };
    public static final String MEDIUM_HARVESTERS[] = 
    {
        "object/installation/mining_gas/mining_gas_harvester_style_2.iff",
        "object/installation/mining_liquid/mining_liquid_harvester_style_2.iff",
        "object/installation/mining_liquid/mining_liquid_moisture_harvester_medium.iff",
        "object/installation/mining_ore/mining_ore_harvester_style_2.iff",
        "object/installation/mining_organic/mining_organic_flora_farm_medium.iff"
    };
    public static final String HEAVY_HARVESTERS[] = 
    {
        "object/installation/mining_gas/mining_gas_harvester_style_3.iff",
        "object/installation/mining_liquid/mining_liquid_harvester_style_3.iff",
        "object/installation/mining_liquid/mining_liquid_moisture_harvester_heavy.iff",
        "object/installation/mining_ore/mining_ore_harvester_heavy.iff",
        "object/installation/mining_organic/mining_organic_flora_farm_heavy.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String deed_scene = getStringObjVar(self, "player_structure.deed.scene");
        if (deed_scene != null && deed_scene.length() > 0)
        {
            switch (deed_scene) {
                case "tatooine":
                    deed_scene = "tatooine,lok,dantooine";
                    setObjVar(self, "player_structure.deed.scene", deed_scene);
                    break;
                case "naboo":
                    deed_scene = "naboo,rori,dantooine";
                    setObjVar(self, "player_structure.deed.scene", deed_scene);
                    break;
                case "corellia":
                    deed_scene = "corellia,talus";
                    setObjVar(self, "player_structure.deed.scene", deed_scene);
                    break;
                case "tatooine,lok,naboo,rori,dantooine,corellia,talus":
                    CustomerServiceLog("playerStructure", "Deed " + self + " has had all invalid/old objvars removed.");
                    removeObjVar(self, "player_structure.deed.scene");
                    break;
            }
        }
        else 
        {
            setObjVar(self, "player_structure.deed.scene", deed_scene);
        }
        if (hasObjVar(self, player_structure.VAR_DEED_SURPLUS_MAINTENANCE))
        {
            int maintenance_pool = getIntObjVar(self, player_structure.VAR_DEED_SURPLUS_MAINTENANCE);
            if (maintenance_pool > 10000000)
            {
                CustomerServiceLog("playerStructure", "Deed " + self + " has a suspicious amount of credits: " + maintenance_pool + ".");
            }
        }
        if (hasObjVar(self, player_structure.VAR_DEED_MAX_EXTRACTION))
        {
            messageTo(self, "validateHopper", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = getFirstFreeIndex(names);
        if (idx < 0 || idx > names.length)
        {
            return SCRIPT_CONTINUE;
        }
        int table_idx = dataTableSearchColumnForString(player_structure.getDeedTemplate(self), "STRUCTURE", player_structure.PLAYER_STRUCTURE_DATATABLE);
        if (table_idx != -1)
        {
            int maintenance_rate = dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, table_idx, "MAINT_RATE");
            int civic = dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, table_idx, "CIVIC");
            if (maintenance_rate > 0 && civic == 0)
            {
                names[idx] = "examine_maintenance_rate";
                attribs[idx] = Integer.toString(maintenance_rate * 2) + " / hour";
                idx++;
            }
        }
        if (utils.hasObjVar(self, player_structure.VAR_DEED_SURPLUS_MAINTENANCE))
        {
            int maintenance = getIntObjVar(self, player_structure.VAR_DEED_SURPLUS_MAINTENANCE);
            if (maintenance > 0)
            {
                names[idx] = "examine_maintenance";
                attribs[idx] = Integer.toString(maintenance);
                idx++;
            }
        }
        if (hasObjVar(self, player_structure.VAR_DEED_SURPLUS_POWER))
        {
            int power = Math.round(getFloatObjVar(self, player_structure.VAR_DEED_SURPLUS_POWER));
            if (power > 0)
            {
                names[idx] = "examine_power";
                attribs[idx] = Integer.toString(power);
                idx++;
            }
        }
        if (hasObjVar(self, "player_structure.deed.maxHopperSize"))
        {
            int hs = getIntObjVar(self, "player_structure.deed.maxHopperSize");
            if (hs > 0)
            {
                names[idx] = "examine_hoppersize";
                attribs[idx] = Integer.toString(hs);
                idx++;
            }
        }
        if (hasObjVar(self, "player_structure.deed.currentExtractionRate"))
        {
            int hs = getIntObjVar(self, "player_structure.deed.currentExtractionRate");
            if (hs > 0)
            {
                names[idx] = "examine_extractionrate";
                attribs[idx] = Integer.toString(hs);
                idx++;
            }
        }
        if (hasObjVar(self, player_structure.VAR_DEED_SCENE))
        {
            String deed_scene = player_structure.getDeedScene(self);
            java.util.StringTokenizer st = new java.util.StringTokenizer(deed_scene, ",");
            while (st.hasMoreTokens())
            {
                String curScene = st.nextToken();
                names[idx] = "examine_scene";
                attribs[idx] = curScene;
                idx++;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (hasObjVar(self, "player_structure.deed.scene"))
        {
            String deed_scene = getStringObjVar(self, "player_structure.deed.scene");
            if (deed_scene != null && deed_scene.length() > 0)
            {
                if (deed_scene.equals("tatooine,lok,naboo,rori,dantooine,corellia,talus"))
                {
                    CustomerServiceLog("playerStructure", "Deed " + self + " has had all invalid/old objvars removed.");
                    removeObjVar(self, "player_structure.deed.scene");
                }
            }
        }
        if (isSpaceScene())
        {
            sendSystemMessage(player, SID_NO_PLACING_STRUCTURES_IN_SPACE);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            player_structure.tryQueueStructurePlacement(self, player);
        }
        if (item == menu_info_types.EXAMINE)
        {
            LOG("LOG_CHANNEL", "player_structure_deed::OnExamine");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNoReclaimConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVarTree(self, "noreclaim");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(player, (123886506), self, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int validateHopper(obj_id self, dictionary params) throws InterruptedException
    {
        int objvar_hopper = getIntObjVar(self, player_structure.VAR_DEED_MAX_HOPPER);
        int hopperSize = player_structure.validateHopperSize(self);
        if (hopperSize < 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (objvar_hopper != hopperSize)
        {
            setObjVar(self, player_structure.VAR_DEED_MAX_HOPPER, hopperSize);
        }
        return SCRIPT_CONTINUE;
    }
}

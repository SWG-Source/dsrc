package script.structure.manufacture;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.craftinglib;
import script.library.prose;
import script.library.sui;

public class manufacture extends script.base_script
{
    public manufacture()
    {
    }
    public static final String SCRIPT_ME = "structure.manufacture.manufacture";
    public static final string_id SID_FACTORY_OPTIONS = new string_id("manf_station", "options");
    public static final string_id SID_SCHEMATIC = new string_id("manf_station", "schematic");
    public static final string_id SID_FACTORY_EXAMINE_PROMPT = new string_id("manf_station", "examine_prompt");
    public static final string_id SID_ACTIVATE = new string_id("manf_station", "activate");
    public static final string_id SID_DEACTIVATE = new string_id("manf_station", "deactivate");
    public static final string_id SID_INPUT_HOPPER = new string_id("manf_station", "input_hopper");
    public static final string_id SID_OUTPUT_HOPPER = new string_id("manf_station", "output_hopper");
    public static final string_id SID_SCHEMATIC_ADDED = new string_id("manf_station", "schematic_added");
    public static final string_id SID_SCHEMATIC_NOT_ADDED = new string_id("manf_station", "schematic_not_added");
    public static final string_id SID_SCHEMATIC_REMOVED = new string_id("manf_station", "schematic_removed");
    public static final string_id SID_SCHEMATIC_NOT_REMOVED = new string_id("manf_station", "schematic_not_removed");
    public static final string_id SID_INGREDIENTS = new string_id("manf_station", "ingredients");
    public static final string_id SID_STATION_ACTIVATED = new string_id("manf_station", "activated");
    public static final string_id SID_STATION_DEACTIVATED = new string_id("manf_station", "deactivated");
    public static final string_id SID_NO_VALID_SCHEMATIC = new string_id("manf_station", "no_valid_schematic");
    public static final String HANDLER_UPDATE_SCHEMATIC = "handleUpdateSchematic";
    public static final String OBJVAR_SCHEMATICS = "crafting.schematics";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, OBJVAR_SCHEMATICS);
        String template = getTemplateName(self);
        if (template != null && template.indexOf("weapon_factory.iff") >= 0)
        {
            int craftingType = getIntObjVar(self, craftinglib.OBJVAR_CRAFTING_TYPE);
            craftingType &= ~CT_genericItem;
            craftingType |= CT_misc;
            craftingType |= CT_space;
            craftingType |= CT_reverseEngineering;
            setObjVar(self, craftinglib.OBJVAR_CRAFTING_TYPE, craftingType);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isStructureCondemned(self) && player_structure.isOwner(player, self))
        {
            player_structure.doCondemnedSui(self, player);
            return SCRIPT_OVERRIDE;
        }
        boolean canAccess = player_structure.canHopper(self, player) || player_structure.isAdmin(self, player);
        int mnu;
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid == null)
        {
            mnu = mi.addRootMenu(menu_info_types.EXAMINE, new string_id("", ""));
        }
        else 
        {
            mid.setServerNotify(true);
        }
        mnu = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, SID_FACTORY_OPTIONS);
        int id;
        if (isHarvesterActive(self))
        {
            id = mi.addSubMenu(mnu, menu_info_types.ITEM_DEACTIVATE, SID_DEACTIVATE);
            if (id != 0 && !canAccess)
            {
                (mi.getMenuItemById(id)).setEnabled(false);
            }
        }
        else 
        {
            String stationSchematic = getManufactureStationSchematic(self);
            if (stationSchematic != null)
            {
                id = mi.addSubMenu(mnu, menu_info_types.ITEM_ACTIVATE, SID_ACTIVATE);
                if (id != 0 && !canAccess)
                {
                    (mi.getMenuItemById(id)).setEnabled(false);
                }
                id = mi.addSubMenu(mnu, menu_info_types.SERVER_HARVEST_CORPSE, SID_INGREDIENTS);
                if (id != 0 && !canAccess)
                {
                    (mi.getMenuItemById(id)).setEnabled(false);
                }
            }
            if (stationSchematic != null || hasValidManufactureSchematicsForStation(player, self))
            {
                id = mi.addSubMenu(mnu, menu_info_types.SERVER_MANF_STATION_SCHEMATIC, SID_SCHEMATIC);
                if (id != 0 && !canAccess)
                {
                    (mi.getMenuItemById(id)).setEnabled(false);
                }
            }
            else 
            {
                sendSystemMessage(player, SID_NO_VALID_SCHEMATIC);
            }
            id = mi.addSubMenu(mnu, menu_info_types.SERVER_MANF_HOPPER_INPUT, SID_INPUT_HOPPER);
            if (id != 0 && !canAccess)
            {
                (mi.getMenuItemById(id)).setEnabled(false);
            }
            id = mi.addSubMenu(mnu, menu_info_types.SERVER_MANF_HOPPER_OUTPUT, SID_OUTPUT_HOPPER);
            if (id != 0 && !canAccess)
            {
                (mi.getMenuItemById(id)).setEnabled(false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isStructureCondemned(self))
        {
            player_structure.doCondemnedSui(self, player);
            return SCRIPT_CONTINUE;
        }
        boolean canAccess = player_structure.canHopper(self, player) || player_structure.isAdmin(self, player);
        if (!canAccess)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_HARVEST_CORPSE)
        {
            if (getManufactureStationSchematic(self) != null)
            {
                String[][] ingredients = new String[1][];
                ingredients[0] = null;
                getIngredientsForManufactureStation(self, ingredients);
                String prompt = getString(SID_FACTORY_EXAMINE_PROMPT);
                if (ingredients[0] != null)
                {
                    sui.listbox(player, prompt, ingredients[0]);
                }
                else 
                {
                }
            }
        }
        else if (item == menu_info_types.ITEM_ACTIVATE || (item == menu_info_types.ITEM_USE && !isHarvesterActive(self)))
        {
            activate(self);
            if (isHarvesterActive(self))
            {
                prose_package pp = prose.getPackage(SID_STATION_ACTIVATED, self);
                if (pp != null)
                {
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        else if (item == menu_info_types.ITEM_DEACTIVATE || (item == menu_info_types.ITEM_USE && isHarvesterActive(self)))
        {
            deactivate(self);
            if (!isHarvesterActive(self))
            {
                prose_package pp = prose.getPackage(SID_STATION_DEACTIVATED, self);
                if (pp != null)
                {
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        else if (item == menu_info_types.SERVER_MANF_HOPPER_INPUT)
        {
            obj_id inputHopper = getManufactureStationInputHopper(self);
            if (inputHopper != null)
            {
                queueCommand(player, (1880585606), inputHopper, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        else if (item == menu_info_types.SERVER_MANF_HOPPER_OUTPUT || item == menu_info_types.SERVER_ITEM_OPTIONS)
        {
            obj_id outputHopper = getManufactureStationOutputHopper(self);
            if (outputHopper != null)
            {
                queueCommand(player, (1880585606), outputHopper, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        else if (item == menu_info_types.SERVER_MANF_STATION_SCHEMATIC)
        {
            String currentSchematicName = getManufactureStationSchematic(self);
            if (currentSchematicName != null)
            {
                int markerPos = currentSchematicName.indexOf('*');
                if (markerPos >= 0)
                {
                    currentSchematicName = currentSchematicName.substring(markerPos + 1);
                }
            }
            else 
            {
                currentSchematicName = "";
            }
            String prompt = "Current schematic installed: " + currentSchematicName;
            String[][] schematics = new String[1][];
            schematics[0] = null;
            getValidManufactureSchematicsForStation(player, self, schematics);
            if (schematics[0] != null)
            {
                String[] schematicIds = new String[schematics[0].length];
                for (int i = 0; i < schematicIds.length; ++i)
                {
                    int markerPos = schematics[0][i].indexOf('*');
                    if (markerPos >= 0)
                    {
                        schematicIds[i] = schematics[0][i].substring(0, markerPos);
                        schematics[0][i] = schematics[0][i].substring(markerPos + 1);
                    }
                    else 
                    {
                        String err = "ERROR " + (getClass()).getName() + ".OnObjectMenuSelect: handling SERVER_HARVESTER_MANAGE " + "schematics for player " + player + " returned invalid schematic string <" + schematics[0][i] + ">";
                        debugServerConsoleMsg(null, err);
                        LOG("crafting", err);
                        schematicIds[i] = "";
                        schematics[0][i] = "";
                    }
                }
                if (schematicIds.length > 0)
                {
                    setObjVar(self, OBJVAR_SCHEMATICS, schematicIds);
                }
                else 
                {
                    removeObjVar(self, OBJVAR_SCHEMATICS);
                }
                int pid = -1;
                if (schematics[0].length > 0)
                {
                    pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, sui.DEFAULT_TITLE, schematics[0], HANDLER_UPDATE_SCHEMATIC, false);
                }
                else 
                {
                    pid = sui.emptylistbox(self, player, prompt, sui.OK_CANCEL, sui.DEFAULT_TITLE, HANDLER_UPDATE_SCHEMATIC, false);
                }
                if (pid == -1)
                {
                    LOG("crafting", "Error creating schematic listbox");
                }
                else 
                {
                    if (!currentSchematicName.equals(""))
                    {
                        sui.listboxUseOtherButton(pid, "@remove_schematic");
                    }
                    sui.setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@use_schematic");
                    sui.showSUIPage(pid);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleUpdateSchematic(obj_id self, dictionary params) throws InterruptedException
    {
        String[] schematicIds = getStringArrayObjVar(self, OBJVAR_SCHEMATICS);
        removeObjVar(self, OBJVAR_SCHEMATICS);
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
            case sui.BP_REVERT:
            
            {
                if (sui.getListboxOtherButtonPressed(params) == true)
                {
                    removeSchematicFromStation(self, player);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (schematicIds == null)
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        else if (idx >= schematicIds.length)
        {
            String err = "ERROR " + (getClass()).getName() + ".handleUpdateSchematic: bad index " + idx + " chosen for schematic list of length " + schematicIds.length;
            debugServerConsoleMsg(null, err);
            LOG("crafting", err);
        }
        else if (schematicIds[idx] == null || schematicIds[idx].length() == 0)
        {
            String err = "ERROR " + (getClass()).getName() + ".handleUpdateSchematic: null/empty schematic id at index " + idx;
            debugServerConsoleMsg(null, err);
            LOG("crafting", err);
        }
        else 
        {
            removeSchematicFromStation(self, player);
            prose_package pp = null;
            obj_id schematic = obj_id.getObjId(Long.parseLong(schematicIds[idx]));
            if (schematic == null || schematic == obj_id.NULL_ID)
            {
                String err = "ERROR " + (getClass()).getName() + ".handleUpdateSchematic: schematic id " + schematicIds[idx] + "at index " + idx + " gives an invalid obj_id";
                debugServerConsoleMsg(null, err);
                LOG("crafting", err);
            }
            else 
            {
                if (transferManufactureSchematicToStation(schematic, self))
                {
                    pp = prose.getPackage(SID_SCHEMATIC_ADDED, schematic);
                }
                else 
                {
                    pp = prose.getPackage(SID_SCHEMATIC_NOT_ADDED, schematic);
                }
            }
            if (pp != null)
            {
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean removeSchematicFromStation(obj_id station, obj_id player) throws InterruptedException
    {
        prose_package pp = null;
        String schematicName = getManufactureStationSchematic(station);
        boolean transferred = transferManufactureSchematicToPlayer(station, player);
        if (schematicName != null && schematicName.length() > 0)
        {
            obj_id schematicId = obj_id.getObjId(Long.valueOf(schematicName.substring(0, schematicName.indexOf('*'))));
            if (isIdValid(schematicId))
            {
                if (transferred)
                {
                    pp = prose.getPackage(SID_SCHEMATIC_REMOVED, schematicId);
                }
                else 
                {
                    pp = prose.getPackage(SID_SCHEMATIC_NOT_REMOVED, schematicId);
                }
            }
        }
        if (pp != null)
        {
            sendSystemMessageProse(player, pp);
        }
        return transferred;
    }
}

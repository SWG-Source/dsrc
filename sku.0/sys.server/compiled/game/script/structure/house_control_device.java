package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.player_structure;
import script.library.trace;
import script.player.base.base_player;

public class house_control_device extends script.base_script
{
    public house_control_device()
    {
    }
    public static final string_id UNPACK_HOUSE = new string_id("sui", "unpack_house");
    public static final string_id SID_NO_PLACING_STRUCTURES_IN_SPACE = new string_id("space/space_interaction", "no_placing_structures_in_space");
    public static final String IN_USE_OBJVAR = "house_redeed.inUse";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "noTrade", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "noTrade", 1);
        removeObjVar(self, IN_USE_OBJVAR);
        if (!hasObjVar(self, player_structure.VAR_DEED_SCENE))
        {
            obj_id[] contents = getContents(self);
            if (contents != null && contents.length == 1)
            {
                obj_id structure = contents[0];
                if (isIdValid(structure))
                {
                    if (hasObjVar(structure, player_structure.VAR_DEED_SCENE))
                    {
                        setObjVar(self, player_structure.VAR_DEED_SCENE, player_structure.getDeedScene(structure));
                    }
                }
            }
        }
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
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        trace.log("housepackup", "Structure control device " + self + ", contained by %TU has been destroyed.", player, trace.TL_CS_LOG);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id objPlayer, String[] strNames, String[] strAttribs) throws InterruptedException
    {
        int intIndex = utils.getValidAttributeIndex(strNames);
        if (intIndex == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String strName = getAssignedName(self);
        if ((strName != null) && (!strName.equals("")))
        {
            strNames[intIndex] = "ship_name";
            strAttribs[intIndex] = strName;
            intIndex++;
        }
        if (hasObjVar(self, player_structure.VAR_DEED_SCENE))
        {
            String deed_scene = player_structure.getDeedScene(self);
            java.util.StringTokenizer st = new java.util.StringTokenizer(deed_scene, ",");
            while (st.hasMoreTokens())
            {
                String curScene = st.nextToken();
                strNames[intIndex] = "examine_scene";
                strAttribs[intIndex] = curScene;
                intIndex++;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU9, UNPACK_HOUSE);
        mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT_STATUS, new string_id("player_structure", "management_status"));
        mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT_PAY, new string_id("player_structure", "management_pay"));
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
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] contents = getContents(self);
        if (contents == null || contents.length != 1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = contents[0];
        if (item == menu_info_types.SERVER_MENU9)
        {
            if (isSpaceScene())
            {
                sendSystemMessage(player, SID_NO_PLACING_STRUCTURES_IN_SPACE);
                return SCRIPT_CONTINUE;
            }
            queueCommand(player, (123886506), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        else if (item == menu_info_types.SERVER_TERMINAL_MANAGEMENT_STATUS)
        {
            queueCommand(player, (335013253), structure, "", COMMAND_PRIORITY_DEFAULT);
        }
        else if (item == menu_info_types.SERVER_TERMINAL_MANAGEMENT_PAY)
        {
            queueCommand(player, (-404530384), structure, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        if (contents == null || contents.length != 1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = contents[0];
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        String template = getTemplateName(structure);
        if (template == null || template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String footprint_template = player_structure.getFootprintTemplate(template);
        if (footprint_template == null || footprint_template.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = player_structure.getStructureTableIndex(template);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int ignore_lots = dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, idx, player_structure.DATATABLE_COL_NO_LOT_REQ);
        int struct_lots = 0;
        if (ignore_lots != 1)
        {
            struct_lots = (player_structure.getNumberOfLots(footprint_template) / 4) - dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, idx, player_structure.DATATABLE_COL_LOT_REDUCTION);
            if (struct_lots < 1)
            {
                struct_lots = 1;
            }
        }
        obj_id player = utils.getContainingPlayer(self);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(player, base_player.CTS_LOT_COUNT))
        {
            struct_lots = struct_lots + getIntObjVar(player, base_player.CTS_LOT_COUNT);
        }
        CustomerServiceLog("CharacterTransfer", "setting lot count adjustment(" + self + ", " + struct_lots + ")");
        setObjVar(player, base_player.CTS_LOT_COUNT, struct_lots);
        return SCRIPT_CONTINUE;
    }
}

package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;
import script.library.storyteller;

public class st_object_movement extends script.base_script
{
    public st_object_movement()
    {
    }
    public static final String DATATABLE_HEIGHT = "datatables/structure/cell_height.iff";
    public static final String STORYTELLER_DATATABLE = "datatables/item/master_item/storyteller_item.iff";
    public static final String THEATER_MODE = "theater_mode";
    public static final String STF = "player_structure";
    public static final float MAX_HEIGHT = 8.0f;
    public int rotateStorytellerObject(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() == 0)
        {
            LOG("LOG_CHANNEL", self + " ->Format: /storyObjectRotate <LEFT/RIGHT> <degrees>");
            sendSystemMessage(self, new string_id(STF, "format_rotatestorytellerobject_degrees"));
            return SCRIPT_CONTINUE;
        }
        String direction = (st.nextToken()).toUpperCase();
        if (!direction.equals("LEFT") && !direction.equals("RIGHT"))
        {
            LOG("LOG_CHANNEL", self + " ->Format: /storyObjectRotate <LEFT/RIGHT> <degrees>");
            sendSystemMessage(self, new string_id(STF, "format_rotatestorytellerobject_degrees"));
            return SCRIPT_CONTINUE;
        }
        int rotation = 0;
        if (st.hasMoreTokens())
        {
            String rot_str = st.nextToken();
            int rot_int = utils.stringToInt(rot_str);
            if (rot_int != -1)
            {
                rotation = rot_int;
            }
            if (rotation < 1 || rotation > 180)
            {
                LOG("LOG_CHANNEL", self + " ->The amount to rotate must be between 1 and 180.");
                sendSystemMessage(self, new string_id(STF, "rotate_params"));
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            rotation = 90;
        }
        if (st.hasMoreTokens())
        {
            target = obj_id.getObjId(Long.valueOf(st.nextToken()));
        }
        else 
        {
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
        }
        if (!isIdValid(target))
        {
            LOG("LOG_CHANNEL", self + " ->What do you want to rotate?");
            sendSystemMessage(self, new string_id(STF, "rotate_what_storyteller"));
            return SCRIPT_CONTINUE;
        }
        if (!isStorytellerMoveCommandValidation(self, target))
        {
            return SCRIPT_CONTINUE;
        }
        if (direction.equals("LEFT"))
        {
            rotation = rotation * -1;
        }
        float obj_rot = getYaw(target);
        obj_rot = obj_rot + (float)rotation;
        if (obj_rot >= 360)
        {
            obj_rot = obj_rot - 360;
        }
        if (obj_rot <= 0)
        {
            obj_rot = obj_rot + 360;
        }
        setYaw(target, obj_rot);
        return SCRIPT_CONTINUE;
    }
    public int moveStorytellerObject(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() == 0)
        {
            LOG("LOG_CHANNEL", self + " ->Format: /storyObjectMove <FORWARD/BACK/UP/DOWN> <distance>");
            sendSystemMessage(self, new string_id(STF, "format_movestorytellerobject_distance"));
            return SCRIPT_CONTINUE;
        }
        String direction = (st.nextToken()).toUpperCase();
        String dist_str = null;
        if (!direction.equals("FORWARD") && !direction.equals("BACK") && !direction.equals("UP") && !direction.equals("DOWN"))
        {
            LOG("LOG_CHANNEL", self + " ->Format: /storyObjectMove <FORWARD/BACK/UP/DOWN> <distance>");
            sendSystemMessage(self, new string_id(STF, "format_movestorytellerobject_distance"));
            return SCRIPT_CONTINUE;
        }
        int distance = 0;
        if (st.hasMoreTokens() && dist_str == null)
        {
            dist_str = st.nextToken();
            int dist_int = utils.stringToInt(dist_str);
            if (dist_int != -1)
            {
                distance = dist_int;
            }
            if (distance < 1 || distance > 500)
            {
                LOG("LOG_CHANNEL", self + " ->The amount to move must be between 1 and 500.");
                sendSystemMessage(self, new string_id(STF, "moveStorytellerObject_params"));
                return SCRIPT_CONTINUE;
            }
        }
        else if (dist_str != null)
        {
            int dist_int = utils.stringToInt(dist_str);
            if (dist_int != -1)
            {
                distance = dist_int;
            }
            if (distance < 1 || distance > 500)
            {
                LOG("LOG_CHANNEL", self + " ->The amount to move must be between 1 and 500.");
                sendSystemMessage(self, new string_id(STF, "moveStorytellerObject_params"));
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            distance = 100;
        }
        if (st.hasMoreTokens())
        {
            target = obj_id.getObjId(Long.valueOf(st.nextToken()));
        }
        else 
        {
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
        }
        if (!isIdValid(target))
        {
            LOG("LOG_CHANNEL", self + " ->What do you want to move?");
            sendSystemMessage(self, new string_id(STF, "move_what_storyteller"));
            return SCRIPT_CONTINUE;
        }
        if (!isStorytellerMoveCommandValidation(self, target))
        {
            return SCRIPT_CONTINUE;
        }
        location move_loc = null;
        if (direction.equals("FORWARD") || direction.equals("BACK"))
        {
            location loc = getLocation(target);
            float facing = getYaw(self);
            float dist_scaled = (float)distance / 100.0f;
            float facing_rad = (float)Math.toRadians(facing);
            float x = dist_scaled * (float)Math.sin(facing_rad);
            float z = dist_scaled * (float)Math.cos(facing_rad);
            if (direction.equals("BACK"))
            {
                x = x * -1;
                z = z * -1;
            }
            LOG("LOG_CHANNEL", "x ->" + x + " z ->" + z + " dist ->" + dist_scaled);
            move_loc = new location(x + loc.x, loc.y, z + loc.z, loc.area, loc.cell);
            LOG("LOG_CHANNEL", "move_loc ->" + move_loc);
            location creationLocation = getLocationObjVar(target, "storytellerCreationLoc");
            float distanceFromCreation = getDistance(creationLocation, move_loc);
            if (distanceFromCreation > 64.0f)
            {
                LOG("LOG_CHANNEL", self + " ->That is not a valid location.");
                sendSystemMessage(self, new string_id(STF, "too_far_from_creationloc"));
                return SCRIPT_CONTINUE;
            }
            float groundElevation = getHeightAtLocation(move_loc.x, move_loc.z);
            if (move_loc.y > groundElevation + MAX_HEIGHT)
            {
                LOG("LOG_CHANNEL", self + " ->That is not a valid location.");
                sendSystemMessage(self, new string_id(STF, "not_valid_location"));
                return SCRIPT_CONTINUE;
            }
            if (isIdValid(move_loc.cell))
            {
                if (!isValidInteriorLocation(move_loc))
                {
                    LOG("LOG_CHANNEL", self + " ->That is not a valid location.");
                    sendSystemMessage(self, new string_id(STF, "not_valid_location"));
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else if (direction.equals("UP") || direction.equals("DOWN"))
        {
            location loc = getLocation(target);
            float facing = getYaw(self);
            float dist_scaled = (float)distance / 100.0f;
            float y = dist_scaled;
            if (direction.equals("DOWN"))
            {
                y = y * -1;
            }
            float new_y = y + loc.y;
            LOG("LOG_CHANNEL", "y ->" + y + " dist ->" + dist_scaled);
            move_loc = new location(loc.x, new_y, loc.z, loc.area, loc.cell);
            LOG("LOG_CHANNEL", "move_loc ->" + move_loc);
            obj_id building = getTopMostContainer(target);
            String bldgstr = getTemplateName(building);
            String cellname = getCellName(building);
            float curr_y = getHeightAtLocation(loc.x, loc.z);
            float dest_y = (curr_y + MAX_HEIGHT);
            if (!utils.hasScriptVar(target, "vertical.template") || !utils.hasScriptVar(target, "vertical.cell") || !utils.hasScriptVar(target, "vertical.min_height") || !utils.hasScriptVar(target, "vertical.max_height") || !(utils.getStringScriptVar(target, "vertical.template")).equals(bldgstr) || !(utils.getStringScriptVar(target, "vertical.cell")).equals(cellname))
            {
                String[] template = dataTableGetStringColumn(DATATABLE_HEIGHT, "template");
                String[] cell = dataTableGetStringColumn(DATATABLE_HEIGHT, "cell");
                float[] max_height = dataTableGetFloatColumn(DATATABLE_HEIGHT, "max_height");
                float[] min_height = dataTableGetFloatColumn(DATATABLE_HEIGHT, "min_height");
                for (int i = 0; i < template.length; i++)
                {
                    if ((template[i].equals(bldgstr)) && (cell[i].equals(cellname)))
                    {
                        utils.setScriptVar(target, "vertical.template", template[i]);
                        utils.setScriptVar(target, "vertical.cell", cell[i]);
                        utils.setScriptVar(target, "vertical.min_height", min_height[i]);
                        utils.setScriptVar(target, "vertical.max_height", max_height[i]);
                    }
                }
            }
            if (!utils.hasScriptVar(target, "vertical.min_height") || !utils.hasScriptVar(target, "vertical.max_height"))
            {
                if ((new_y < curr_y) || (new_y > dest_y))
                {
                    LOG("LOG_CHANNEL", self + " ->That is not a valid location.");
                    sendSystemMessage(self, new string_id(STF, "not_valid_location"));
                    return SCRIPT_CONTINUE;
                }
            }
            else if (new_y < utils.getFloatScriptVar(target, "vertical.min_height") || new_y > utils.getFloatScriptVar(target, "vertical.max_height"))
            {
                LOG("LOG_CHANNEL", self + " ->That is not a valid location.");
                sendSystemMessage(self, new string_id(STF, "not_valid_location"));
                return SCRIPT_CONTINUE;
            }
        }
        if (move_loc != null)
        {
            if (storyteller.canDeployStorytellerToken(self, move_loc, target))
            {
                setLocation(target, move_loc);
                location new_loc = getLocation(target);
                LOG("LOG_CHANNEL", "new_loc ->" + new_loc);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int storytellerItemRotateRight(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-933756769), target, "left 90 " + target, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int storytellerItemRotateLeft(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-933756769), target, "right 90 " + target, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int storytellerItemMoveForward(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-918547884), target, "forward 10 " + target, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int storytellerItemMoveBack(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-918547884), target, "back 10 " + target, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int storytellerItemMoveUp(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-918547884), target, "up 1 " + target, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public int storytellerItemMoveDown(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        queueCommand(self, (-918547884), target, "down 1 " + target, COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
    public boolean isStorytellerMoveCommandValidation(obj_id player, obj_id target) throws InterruptedException
    {
        if (!hasObjVar(target, "storytellerid"))
        {
            return false;
        }
        obj_id storytellerId = getObjIdObjVar(target, "storytellerid");
        if (storytellerId != player && storytellerId != utils.getObjIdScriptVar(player, "storytellerAssistant") && !isGod(player))
        {
            return false;
        }
        String itemName = getStaticItemName(target);
        dictionary dict = new dictionary();
        int row = dataTableSearchColumnForString(itemName, "name", storyteller.STORYTELLER_DATATABLE);
        if (row == -1)
        {
            sendSystemMessageTestingOnly(player, "This is not a valid item");
            return false;
        }
        dict = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, itemName);
        int typeString = dict.getInt("type");
        if (typeString == storyteller.THEATER)
        {
            sendSystemMessageTestingOnly(player, "This is a theater object");
            return false;
        }
        obj_id structure = getTopMostContainer(player);
        obj_id item_structure = getTopMostContainer(target);
        if (((structure != player) || (item_structure != target)) && (structure != item_structure))
        {
            sendSystemMessageTestingOnly(player, "This is not a valid move location");
            return false;
        }
        if (!isIdValid(structure))
        {
            sendSystemMessageTestingOnly(player, "This is not a valid location");
            return false;
        }
        return true;
    }
}

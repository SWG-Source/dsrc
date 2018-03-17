package script.item.dice.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.debug;
import script.library.prose;

public class base_dice extends script.base_script
{
    public base_dice()
    {
    }
    public static final String VAR_ROLL_RESULT = "roll_result_value";
    public static final String VAR_FACE_COUNT = "dice_face_count";
    public static final String DICE_TYPE_NAME = "dice_type_name";
    public static final String CHANCE_CUBE_DICE = "chance_cube";
    public static final String VAR_DICE_COUNT = "dice_count";
    public static final String CHANCE_CUBE_SCRIPT = "item.dice.chance_cube";
    public static final String CONFIGURABLE_DICE_SCRIPT = "item.dice.configurable_group_dice";
    public static final String SIX_SIDED_SCRIPT = "item.dice.six_sided_dice_set";
    public static final String VAR_VALUE_INITIALIZED = "dice_initialized";
    public static final string_id CONFIGURE_MSG = new string_id("dice/dice", "dice_configure_msg");
    public static final String STF = "dice/dice";
    public static final string_id RED = new string_id(STF, "red");
    public static final string_id BLUE = new string_id(STF, "blue");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########base_dice script attached##############");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########base_dice script initialized##############");
        setObjVar(self, VAR_VALUE_INITIALIZED, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String dice_type_name = getStringObjVar(self, DICE_TYPE_NAME);
        if (dice_type_name == null || dice_type_name.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        debug.debugAllMsg("DEBUG", self, "##########" + dice_type_name + "##############");
        if (dice_type_name.equals("chance_cube"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("dice/dice", "dice_roll_single"));
        }
        else if (dice_type_name.equals("configurable_group_dice"))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("dice/dice", "dice_roll_single"));
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("dice/dice_details", "eqp_dice_configure"));
            mi.addSubMenu(menu, menu_info_types.DICE_EIGHT_FACE, new string_id("dice/dice", "dice_eight_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_SEVEN_FACE, new string_id("dice/dice", "dice_seven_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_SIX_FACE, new string_id("dice/dice", "dice_six_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_FIVE_FACE, new string_id("dice/dice", "dice_five_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_FOUR_FACE, new string_id("dice/dice", "dice_four_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_THREE_FACE, new string_id("dice/dice", "dice_three_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_TWO_FACE, new string_id("dice/dice", "dice_two_single"));
        }
        else 
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("dice/dice", "dice_roll_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_COUNT_FOUR, new string_id("dice/dice", "dice_roll_four_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_COUNT_THREE, new string_id("dice/dice", "dice_roll_three_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_COUNT_TWO, new string_id("dice/dice", "dice_roll_two_single"));
            mi.addSubMenu(menu, menu_info_types.DICE_COUNT_ONE, new string_id("dice/dice", "dice_roll_one_single"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########OnObjectMenuSelect()#########");
        String typeName = getStringObjVar(self, DICE_TYPE_NAME);
        if (typeName == null || typeName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE && typeName.equals("chance_cube"))
        {
            String color = null;
            debug.debugAllMsg("DEBUG", self, "##########found color in base_dice#########");
            messageTo(self, "roll", null, 0, true);
            color = getStringObjVar(self, VAR_ROLL_RESULT);
            if (color == null)
            {
                int chooser = rand(1, 2, 0);
                if (chooser == 1)
                {
                    color = "blue";
                }
                else 
                {
                    color = "red";
                }
            }
            debug.debugAllMsg("DEBUG", self, "##########Color found: + " + color + "#########");
            informGroupOfResults(null, player, self, color);
            return SCRIPT_CONTINUE;
        }
        if (typeName.equals("chance_cube"))
        {
            return SCRIPT_CONTINUE;
        }
        debug.debugAllMsg("DEBUG", self, "##########found value in base_dice#########");
        dictionary params = new dictionary();
        params.put("player", player);
        if (item == menu_info_types.DICE_TWO_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 2);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_THREE_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 3);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_FOUR_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 4);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_FIVE_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 5);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_SIX_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 6);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_SEVEN_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 7);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_EIGHT_FACE)
        {
            setObjVar(self, VAR_FACE_COUNT, 8);
            setObjVar(self, VAR_VALUE_INITIALIZED, true);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.DICE_COUNT_ONE || item == menu_info_types.ITEM_USE)
        {
            setObjVar(self, VAR_DICE_COUNT, 1);
            messageTo(self, "roll", params, 0, true);
        }
        else if (item == menu_info_types.DICE_COUNT_TWO)
        {
            setObjVar(self, VAR_DICE_COUNT, 2);
            messageTo(self, "roll", params, 0, true);
        }
        else if (item == menu_info_types.DICE_COUNT_THREE)
        {
            setObjVar(self, VAR_DICE_COUNT, 3);
            messageTo(self, "roll", params, 0, true);
        }
        else if (item == menu_info_types.DICE_COUNT_FOUR)
        {
            setObjVar(self, VAR_DICE_COUNT, 4);
            messageTo(self, "roll", params, 0, true);
        }
        else if (item == menu_info_types.DICE_ROLL)
        {
            messageTo(self, "roll", params, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public void informGroupOfResults(int[] rollValues, obj_id player, obj_id die, String color) throws InterruptedException
    {
        obj_id groupId = getGroupObject(player);
        obj_id[] groupMembers = getGroupMemberIds(groupId);
        if (groupMembers != null)
        {
            for (int i = 0; i < groupMembers.length; ++i)
            {
                if (groupMembers[i] != player && groupMembers[i].isLoaded())
                {
                    displayResults(player, groupMembers[i], die, rollValues, color);
                }
            }
        }
        displayResults(player, player, die, rollValues, color);
    }
    public void displayResults(obj_id player, obj_id target, obj_id die, int[] rollValues, String color) throws InterruptedException
    {
        if (color != null)
        {
            prose_package pp = new prose_package();
            if (player == target)
            {
                pp = prose.getPackage(new string_id(STF, "chance_cube_result_self"), new string_id(STF, color));
            }
            else 
            {
                pp = prose.getPackage(new string_id(STF, "chance_cube_result_other"), player, new string_id(STF, color));
            }
            sendSystemMessageProse(target, pp);
            String messageStr = getName(player) + "'s chance cube rolled the color " + color + ".";
            debug.debugAllMsg("DEBUG", player, "##########" + messageStr + "###########");
            return;
        }
        String dice_type_name = getStringObjVar(die, DICE_TYPE_NAME);
        if (dice_type_name.equals("configurable_group_dice"))
        {
            int faceCount = getIntObjVar(die, VAR_FACE_COUNT);
            String value = String.valueOf(rollValues[0]);
            prose_package pp = new prose_package();
            if (player == target)
            {
                pp = prose.getPackage(new string_id(STF, "configurable_dice_result_self"), value, faceCount);
            }
            else 
            {
                pp = prose.getPackage(new string_id(STF, "configurable_dice_result_other"), null, null, null, player, null, null, null, value, null, faceCount, 0f);
            }
            sendSystemMessageProse(target, pp);
            String messageStr = "Your " + faceCount + " sided die rolled a value of " + rollValues[0] + ".";
            debug.debugAllMsg("DEBUG", player, "#############" + messageStr + "############");
            return;
        }
        int dieCount = getIntObjVar(die, VAR_DICE_COUNT);
        String rollStr = new String();
        for (int i = 0; i < rollValues.length && rollValues != null; ++i)
        {
            rollStr += " " + rollValues[i];
        }
        prose_package pp = new prose_package();
        string_id sidNumSides = new string_id(STF, dice_type_name.substring(0, dice_type_name.length() - 15));
        if (dieCount == 1)
        {
            if (target == player)
            {
                pp = prose.getPackage(new string_id(STF, "roll_one_self"), null, null, null, null, null, sidNumSides, null, rollStr, null, dieCount, 0f);
            }
            else 
            {
                pp = prose.getPackage(new string_id(STF, "roll_one_other"), player, null, null, null, null, sidNumSides, null, rollStr, null, dieCount, 0f);
            }
        }
        else 
        {
            if (target == player)
            {
                pp = prose.getPackage(new string_id(STF, "roll_many_self"), null, null, null, null, null, sidNumSides, null, rollStr, null, dieCount, 0f);
            }
            else 
            {
                pp = prose.getPackage(new string_id(STF, "roll_many_other"), player, null, null, null, null, sidNumSides, null, rollStr, null, dieCount, 0f);
            }
        }
        sendSystemMessageProse(target, pp);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "##########base_dice script detached##############");
        return SCRIPT_CONTINUE;
    }
}

package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.player_structure;
import script.library.space_utils;
import script.library.space_transition;

public class custom_sign extends script.base_script
{
    public custom_sign()
    {
    }
    public static final String SIGN_TYPE = new String("hangingStanding");
    public static final String MODIFIED_SIGN = new String("modifiedSign");
    public static final String SPAM = new String("spam");
    public static final string_id SUI_TITLE = new string_id(SPAM, "replace_sign_sui");
    public static final string_id SUI_QUESTION = new string_id(SPAM, "replace_sign_sui_question");
    public static final string_id SID_USE = new string_id(SPAM, "change_sign");
    public static final string_id CANT_USE = new string_id(SPAM, "not_sign");
    public static final string_id CANT_DROP = new string_id(SPAM, "cant_drop_sign");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.SERVER_MENU1);
        obj_id target = getIntendedTarget(player);
        obj_id house = utils.getObjIdScriptVar(target, "player_structure.parent");
        String templateName = getTemplateName(target);
        if (player_structure.isOwner(house, player))
        {
            if (exists(house) && isIdValid(house) && validSign(target, self, house) == true)
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id target = getIntendedTarget(player);
        obj_id house = utils.getObjIdScriptVar(target, "player_structure.parent");
        String templateName = getTemplateName(target);
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (exists(house) && isIdValid(house) && validSign(target, self, house) == true)
            {
                if (player_structure.isOwner(house, player))
                {
                    String title = utils.packStringId(SUI_TITLE);
                    String prompt = getString(SUI_QUESTION);
                    int pid = sui.msgbox(self, player, prompt, sui.YES_NO, title, "signReplacementSui");
                }
            }
            else 
            {
                sendSystemMessage(player, CANT_USE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id newLocationNonPlayer = getTopMostContainer(destContainer);
        obj_id newLocationPlayer = getContainedBy(destContainer);
        obj_id ship = space_transition.getContainingShip(destContainer);
        String templateName = "";
        if (isIdValid(ship))
        {
            templateName = getTemplateName(ship);
        }
        if (isPlayer(newLocationPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        else if (player_structure.isPlayerStructure(newLocationNonPlayer) || space_utils.isPobType(templateName))
        {
            sendSystemMessage(transferer, CANT_DROP);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int signReplacementSui(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        replaceSign(player, self);
        return SCRIPT_CONTINUE;
    }
    public void replaceSign(obj_id player, obj_id sign) throws InterruptedException
    {
        obj_id target = getIntendedTarget(player);
        obj_id house = utils.getObjIdScriptVar(target, "player_structure.parent");
        String templateName = getTemplateName(target);
        if (exists(house) && isIdValid(house) && validSign(target, sign, house) == true)
        {
            if (player_structure.isOwner(house, player))
            {
                String signType = getStringObjVar(sign, MODIFIED_SIGN);
                setObjVar(house, player_structure.MODIFIED_HOUSE_SIGN, signType);
                player_structure.destroyStructureSign(house);
                player_structure.createStructureSign(house);
                String customSign = getStringObjVar(sign, player_structure.MODIFIED_HOUSE_SIGN_MODEL);
                setObjVar(house, player_structure.MODIFIED_HOUSE_SIGN_MODEL, customSign);
                CustomerServiceLog("playerStructure", "Player " + player + "replaced his house sign for building " + house + "with a custom sign. Static item name for custome sign was " + customSign);
                destroyObject(sign);
            }
        }
        else 
        {
            sendSystemMessage(player, CANT_USE);
        }
    }
    public boolean validSign(obj_id target, obj_id self, obj_id structure) throws InterruptedException
    {
        String templateName = getTemplateName(target);
        String structureName = getTemplateName(structure);
        if (hasObjVar(self, SIGN_TYPE))
        {
            int signType = getIntObjVar(self, SIGN_TYPE);
            if (signType == 1)
            {
                if (structureName.indexOf("merchant_tent") > -1)
                {
                    return false;
                }
                if (templateName.startsWith("object/tangible/sign/player/house_address"))
                {
                    return true;
                }
                if (templateName.startsWith("object/tangible/sign/municipal/municipal_sign_hanging_cantina"))
                {
                    return true;
                }
            }
            if (signType == 2)
            {
                if (templateName.startsWith("object/tangible/sign/player/shop_sign"))
                {
                    return true;
                }
            }
        }
        return false;
    }
}

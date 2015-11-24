package script.systems.sign;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.sui;
import script.library.utils;
import script.library.player_structure;
import script.library.prose;
import script.library.utils;

public class sign extends script.base_script
{
    public sign()
    {
    }
    public static final boolean LOGGING_ON = false;
    public static final String LOGGING_CATEGORY = "packup";
    public static final string_id SID_TERMINAL_MANAGEMENT = new string_id("player_structure", "management");
    public static final string_id SID_TERMINAL_PACK_HOUSE = new string_id("sui", "packup_house");
    public static final string_id EMAIL_TITLE = new string_id("spam", "email_title");
    public static final string_id EMAIL_BODY = new string_id("spam", "email_body");
    public static final string_id SID_OWNER_PACKUP_AT_TERMINAL = new string_id("player_structure", "onwer_packup_at_terminal");
    public static final int minTimeDelayBetweenSameServerRequests = 300;
    public static final String timeOfLastSameServerRequest = "timeOfLastSameServerRequest";
    public static final string_id SID_MAYOR_HOUSE_SIGN_DISPLAY = new string_id("city/city", "house_owner");
    public static final string_id SID_TERMINAL_CITY_PACK_HOUSE = new string_id("city/city", "city_packup_house");
    public static final string_id SID_NOT_CITY_ABANDONED = new string_id("city/city", "not_city_abandoned");
    public static final int cityMinTimeDelayBetweenSameServerRequests = 300;
    public static final String cityTimeOfLastSameServerRequest = "timeOfLastSameServerRequest";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data menuData = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (menuData != null)
        {
            menuData.setServerNotify(true);
        }
        deltadictionary scriptvars = self.getScriptVars();
        if (!utils.hasScriptVar(self, "player_structure.parent"))
        {
            LOG("sissynoid", "Player (" + player + ")" + getPlayerFullName(player) + " attempted to access a house sign(" + self + ") - and the sign has no parent (parent is the House ObjId).");
            CustomerServiceLog("playerStructure", "Player (" + player + ")" + getPlayerFullName(player) + " attempted to access a house sign(" + self + ") - and the sign has no parent (parent is the House ObjId).");
            return SCRIPT_CONTINUE;
        }
        obj_id house = scriptvars.getObjId("player_structure.parent");
        if (player_structure.canShowPackOption(player, house))
        {
            int management_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT, SID_TERMINAL_MANAGEMENT);
            mi.addSubMenu(management_root, menu_info_types.SERVER_MENU10, SID_TERMINAL_PACK_HOUSE);
        }
        if (player_structure.doesUnmarkedStructureQualifyForHousePackup(house) && !player_structure.isAbandoned(house) && player_structure.isCityAbandoned(house) && cityIsInactivePackupActive())
        {
            int management_root = mi.addRootMenu(menu_info_types.SERVER_TERMINAL_MANAGEMENT, SID_TERMINAL_MANAGEMENT);
            mi.addSubMenu(management_root, menu_info_types.SERVER_MENU11, SID_TERMINAL_CITY_PACK_HOUSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id parent = obj_id.NULL_ID;
            String text = "";
            if (utils.hasScriptVar(self, "player_structure.parent"))
            {
                parent = utils.getObjIdScriptVar(self, "player_structure.parent");
                if (player_structure.isPlayerStructure(parent))
                {
                    text = getName(self);
                    int cityId = getCityAtLocation(getLocation(parent), 0);
                    if (cityId > 0 && city.isTheCityMayor(player, cityId))
                    {
                        obj_id houseOwnerId = player_structure.getStructureOwnerObjId(parent);
                        String houseOwnerName = getPlayerFullName(houseOwnerId);
                        prose_package pp = new prose_package();
                        prose.setStringId(pp, SID_MAYOR_HOUSE_SIGN_DISPLAY);
                        prose.setTT(pp, text);
                        prose.setTU(pp, houseOwnerName);
                        sui.msgbox(self, player, pp, "noHandlerNeeded");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        sui.msgbox(self, player, text, "noHandlerNeeded");
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    string_id desc = getDescriptionStringId(self);
                    text = utils.packStringId(desc);
                    return SCRIPT_CONTINUE;
                }
            }
            string_id desc = getDescriptionStringId(self);
            text = utils.packStringId(desc);
            sui.msgbox(self, player, text, "noHandlerNeeded");
        }
        if (item == menu_info_types.SERVER_MENU10)
        {
            deltadictionary scriptvars = self.getScriptVars();
            obj_id house = scriptvars.getObjId("player_structure.parent");
            if (player_structure.isOwner(house, player))
            {
                sendSystemMessage(player, SID_OWNER_PACKUP_AT_TERMINAL);
                return SCRIPT_CONTINUE;
            }
            if (player_structure.isPlayerGatedFromHousePackUp(player))
            {
                return SCRIPT_CONTINUE;
            }
            if (!player_structure.canPlayerPackAbandonedStructure(player, house))
            {
                if (isAtPendingLoadRequestLimit())
                {
                    sendSystemMessage(player, new string_id("player_structure", "abandoned_structure_pack_up_try_again_later"));
                }
                else if (player_structure.isAbandoned(house) && (!house.isAuthoritative() || !player.isAuthoritative()))
                {
                    if (!utils.hasScriptVar(player, timeOfLastSameServerRequest) || utils.getIntScriptVar(player, timeOfLastSameServerRequest) < getGameTime())
                    {
                        requestSameServer(player, house);
                        utils.setScriptVar(player, timeOfLastSameServerRequest, getGameTime() + minTimeDelayBetweenSameServerRequests);
                        utils.setScriptVar(player, "requestedSameServerToAbandonHouse", house);
                    }
                    sendSystemMessage(player, new string_id("player_structure", "abandoned_structure_pack_up_please_wait_processing"));
                }
                return SCRIPT_CONTINUE;
            }
            if (isIdValid(house) && !utils.hasScriptVar(house, player_structure.SCRIPTVAR_HOUSE_PACKUP_LOCKOUT_TIME))
            {
                dictionary params = new dictionary();
                params.put("house", house);
                params.put("player", player);
                messageTo(house, "packAbandonedBuilding", params, 4, false);
                messageTo(player, "handlePlayerStructurePackupLockout", params, 0, false);
                if (!hasObjVar(player, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR))
                {
                    String recipient = getPlayerName(player);
                    utils.sendMail(EMAIL_TITLE, EMAIL_BODY, recipient, "Galactic Vacant Building Demolishing Movement");
                }
            }
        }
        if (item == menu_info_types.SERVER_MENU11 && cityIsInactivePackupActive())
        {
            obj_id sign = getSelf();
            AttemptPackCityAbandonedStructure(player, sign);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRemoteCommandCityHousePackup(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog("playerStructure", "Remote City Abandoned Packup - Params were NULL when messageTo was sent.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id paramsSign = params.getObjId("sign");
        if (!isIdValid(player) || !isIdValid(paramsSign))
        {
            CustomerServiceLog("playerStructure", "Remote City Abandoned Packup - Params were bad: Player(" + player + "), paramsSign(" + paramsSign + ")");
            return SCRIPT_CONTINUE;
        }
        if (paramsSign != self)
        {
            CustomerServiceLog("playerStructure", "Remote City Abandoned Packup - Player(" + player + ") was attempting to pack a house - but the signs don't match: paramsSign(" + paramsSign + "), self(" + self + ")");
            return SCRIPT_CONTINUE;
        }
        AttemptPackCityAbandonedStructure(player, paramsSign);
        return SCRIPT_CONTINUE;
    }
    public void AttemptPackCityAbandonedStructure(obj_id player, obj_id sign) throws InterruptedException
    {
        if (!utils.hasScriptVar(sign, "player_structure.parent"))
        {
            CustomerServiceLog("playerStructure", "Player(" + player + ") attempted city pack a structure - but the House Sign(" + sign + ") has invalid data.");
            return;
        }
        obj_id house = utils.getObjIdScriptVar(sign, "player_structure.parent");
        if (!house.isAuthoritative() || !player.isAuthoritative())
        {
            LOG("sissynoid", "Player (" + player + ")" + getPlayerFullName(player) + " attempted to access a house sign(" + sign + ") - Structure and Sign were on different server processes - requesting Same Server.");
            CustomerServiceLog("playerStructure", "Player (" + player + ")" + getPlayerFullName(player) + " attempted to access a house sign(" + sign + ") - Structure and Sign were on different server processes - requesting Same Server.");
            if (!utils.hasScriptVar(player, cityTimeOfLastSameServerRequest) || utils.getIntScriptVar(player, cityTimeOfLastSameServerRequest) < getGameTime())
            {
                requestSameServer(player, house);
                utils.setScriptVar(player, cityTimeOfLastSameServerRequest, getGameTime() + cityMinTimeDelayBetweenSameServerRequests);
                utils.setScriptVar(player, "cityRequestedSameServerToAbandonHouse", house);
            }
            sendSystemMessage(player, new string_id("player_structure", "abandoned_structure_pack_up_please_wait_processing"));
        }
        else 
        {
            player_structure.confirmCityAbandonedAndPack(house, player);
        }
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}

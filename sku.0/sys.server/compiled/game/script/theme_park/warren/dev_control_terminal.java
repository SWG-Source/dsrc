package script.theme_park.warren;

import script.library.sui;
import script.library.utils;
import script.*;

public class dev_control_terminal extends script.base_script
{
    public dev_control_terminal()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String PASSKEYCODE = "object/intangible/data_item/warren_encryption_key.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "dev_control_name"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasEncryptionKey(player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(SYSTEM_MESSAGES, "mnu_download"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasEncryptionKey(player))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 10.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        sui.msgbox(player, new string_id(SYSTEM_MESSAGES, "download_complete"));
        obj_id playerInv = utils.getPlayerDatapad(player);
        if (isIdValid(playerInv))
        {
            obj_id passKey = createObject(PASSKEYCODE, playerInv, "");
            if (isIdValid(passKey))
            {
                setObjVar(passKey, "warren.airlockkey", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasEncryptionKey(obj_id player) throws InterruptedException
    {
        obj_id playerInv = utils.getPlayerDatapad(player);
        if (playerInv == null)
        {
            return false;
        }
        obj_id[] contents = getContents(playerInv);
        if (contents == null)
        {
            return false;
        }
        for (obj_id content : contents) {
            if (hasObjVar(content, "warren.airlockkey")) {
                return true;
            }
        }
        return false;
    }
}

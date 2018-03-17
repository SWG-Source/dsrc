package script.event.housepackup;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.cts;
import script.library.utils;

public class cts_unrestricted_gift extends script.base_script
{
    public cts_unrestricted_gift()
    {
    }
    public static final boolean BLOGGING_ON = true;
    public static final String BLOG_CATEGORY = "CharacterTransfer";
    public static final String STF_FILE = "veteran_new";
    public static final string_id SID_OPEN_GIFT = new string_id(STF_FILE, "open_gift");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("OnObjectMenuRequest init");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnObjectMenuRequest player valid");
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnObjectMenuRequest is nested");
        if (!hasObjVar(self, cts.CURRENT_PLAYER_OID) || !hasObjVar(self, cts.SPONSOR_PLAYER_OID))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnObjectMenuRequest has original owner and sponser");
        if (!hasObjVar(self, cts.CTS_PAINTING_OBJVAR))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnObjectMenuRequest has cts painting");
        int mnu = mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN_GIFT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (!cts.grantPackagedPainting(self, player))
        {
            CustomerServiceLog("CharacterTransfer", "CTS Sponsor Gift unpacked failed for player: " + player + " " + getName(player) + ". Offending object: " + self);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("CharacterTransfer", "CTS Sponsor Gift unpacked and stamped. Destroying original sponsor gift object: " + self);
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        String inscriptionString = cts.getCtsInscription(self);
        if (inscriptionString != null && !inscriptionString.equals(""))
        {
            names[idx] = "inscription";
            attribs[idx] = inscriptionString;
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (BLOGGING_ON)
        {
            LOG(BLOG_CATEGORY, msg);
        }
        return true;
    }
}

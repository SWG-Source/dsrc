package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.gcw;

public class recruitment_letter extends script.base_script
{
    public recruitment_letter()
    {
    }
    public static final string_id USE_LETTER = new string_id("gcw", "use_pub_gift_recruitment_letter");
    public static final string_id SID_NOT_ALLIGNED = new string_id("gcw", "must_be_factionally_alligned");
    public static final int LETTER_POINT_VALUE = 10000;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isAlligned(player))
        {
            sendSystemMessage(player, SID_NOT_ALLIGNED);
            return SCRIPT_CONTINUE;
        }
        if (isOwner(self, player))
        {
            int menuOption = mi.addRootMenu(menu_info_types.SERVER_ITEM_OPTIONS, USE_LETTER);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_ITEM_OPTIONS && isOwner(self, player) && isAlligned(player))
        {
            gcw.grantUnmodifiedGcwPoints(player, LETTER_POINT_VALUE);
            destroyObject(self);
            CustomerServiceLog("GCW_points_publish_gift", "%TU has acquired " + LETTER_POINT_VALUE + " points.", player);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isOwner(obj_id self, obj_id player) throws InterruptedException
    {
        return getOwner(self) == player;
    }
    public boolean isAlligned(obj_id player) throws InterruptedException
    {
        String faction = factions.getFaction(player);
        if (faction.equals("Rebel") || faction.equals("Imperial"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
}

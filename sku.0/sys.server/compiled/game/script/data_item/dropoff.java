package script.data_item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.loot;

public class dropoff extends script.item.container.add_only
{
    public dropoff()
    {
    }
    public static final string_id SID_DATA_ONLY = new string_id("error_message", "data_only");
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer))
        {
            if (!loot.isFactionalDataItem(item))
            {
                sendSystemMessage(transferer, SID_DATA_ONLY);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (isPlayer(transferer))
        {
            if (loot.isFactionalDataItem(item))
            {
                if (!loot.redeemFactionCoupon(getContainedBy(self), transferer, item))
                {
                    debugSpeakMsg(transferer, "redeem failed: returning coupon to source container!");
                    putIn(item, srcContainer);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClosedContainer(obj_id self, obj_id who) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        if ((contents == null) || (contents.length == 0))
        {
        }
        else 
        {
            for (int i = 0; i < contents.length; i++)
            {
                destroyObject(contents[i]);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

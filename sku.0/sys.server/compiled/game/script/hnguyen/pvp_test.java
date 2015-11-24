package script.hnguyen;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.util.StringTokenizer;

public class pvp_test extends script.base_script
{
    public pvp_test()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("setpe"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id dest = utils.stringToObjId(st.nextToken());
                obj_id enemy = utils.stringToObjId(st.nextToken());
                pvpSetPersonalEnemyFlag(dest, enemy);
                sendSystemMessageTestingOnly(self, dest + " now has " + enemy + " as a personal enemy");
            }
        }
        else if (strText.startsWith("haspe"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id actor = utils.stringToObjId(st.nextToken());
                obj_id target = utils.stringToObjId(st.nextToken());
                if (pvpHasPersonalEnemyFlag(actor, target))
                {
                    sendSystemMessageTestingOnly(self, actor + " has " + target + " as a personal enemy");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, actor + " DOESN'T have " + target + " as a personal enemy");
                }
            }
        }
        else if (strText.startsWith("removepe"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 3)
            {
                String command = st.nextToken();
                obj_id target = utils.stringToObjId(st.nextToken());
                obj_id enemyId = utils.stringToObjId(st.nextToken());
                pvpRemovePersonalEnemyFlags(target, enemyId);
                sendSystemMessageTestingOnly(self, target + " no longer has " + enemyId + " as a personal enemy");
            }
        }
        return SCRIPT_CONTINUE;
    }
}

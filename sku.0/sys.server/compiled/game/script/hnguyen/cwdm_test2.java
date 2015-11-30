package script.hnguyen;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_dungeon;

public class cwdm_test2 extends script.base_script
{
    public cwdm_test2()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("cwdm_doit"))
        {
            LOG("***HUY***", "OnHearSpeech() initiating cluster wide data test");
            int requestId = getClusterWideData("dungeon", "Corellian Corvette - Instance 1", true, self);
            LOG("***HUY***", "getClusterWideData() to lock data for initial registration returned request Id (" + requestId + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String strManagerName, String strElementNameRegex, int requestID, String[] elementNameList, dictionary[] dictionaryList, int lockKey) throws InterruptedException
    {
        LOG("***HUY***", "OnClusterWideDataResponse() manager name (" + strManagerName + ") element name regex (" + strElementNameRegex + ") request id (" + requestID + ") match count (" + elementNameList.length + ") lock key (" + lockKey + ")");
        for (int i = 0; i < elementNameList.length; ++i)
        {
            LOG("***HUY***", "element " + (i + 1) + " is (" + elementNameList[i] + ")");
            LOG("***HUY***", "dictionary " + dictionaryList[i].toString());
        }
        return SCRIPT_CONTINUE;
    }
}

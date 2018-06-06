package script.hnguyen;

import script.library.cts;
import script.library.utils;
import script.obj_id;

import java.util.StringTokenizer;

public class sc_cts_test extends script.base_script
{
    public sc_cts_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("initiateCtsFromItem "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id item = utils.stringToObjId(st.nextToken());
                sendSystemMessageTestingOnly(self, "calling cts.initiateCtsFromItem(player=" + self + ", item=" + item + ")");
                cts.initiateCtsFromItem(self, item);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

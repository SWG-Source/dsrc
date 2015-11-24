package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.features;

public class featuretest extends script.base_script
{
    public featuretest()
    {
    }
    public static final int SWG_BASE_GAME = 0x00000001;
    public static final int SWG_COLLECTORS_GAME = 0x00000002;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "feature test script attached");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("featuretest"))
        {
            sendSystemMessageTestingOnly(self, "getGameFeatures(): " + getGameFeatureBits(self));
            sendSystemMessageTestingOnly(self, "getSubFeatures(): " + getSubscriptionFeatureBits(self));
            sendSystemMessageTestingOnly(self, "utils.checkBit( SWG_BASE_GAME ): " + utils.checkBit(getGameFeatureBits(self), SWG_COLLECTORS_GAME));
            sendSystemMessageTestingOnly(self, "utils.checkBit( SWG_COLLECTORS_GAME ): " + utils.checkBit(getGameFeatureBits(self), SWG_COLLECTORS_GAME));
            sendSystemMessageTestingOnly(self, "isCollectorEdition" + features.isCollectorEdition(self));
        }
        return SCRIPT_CONTINUE;
    }
}

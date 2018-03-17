package script.event.ewok_festival;

import script.dictionary;
import script.library.groundquests;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class chief extends script.base_script
{
    public chief()
    {
    }
    public static final string_id GIVE_GIFT = new string_id("spam", "ewok_chief_give_gift");
    public static final string_id HAVE_GIFT = new string_id("spam", "ewok_chief_have_gift");
    public static final String OBJ_BOUQUET_QUEST = "loveday_flowers_2010";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleChiefInitialize", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int handleChiefInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        if (!getLocation(self).area.equals("naboo"))
        {
            setName(self, "an Ewok chieftain");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (utils.getDistance2D(self, speaker) > 10f)
        {
            return SCRIPT_CONTINUE;
        }
        if ((toLower(text)).equals("allayloo ta nuv"))
        {
            if (getIntendedTarget(speaker) == self)
            {
                if (groundquests.hasCompletedQuest(speaker, OBJ_BOUQUET_QUEST))
                {
                    sendSystemMessage(speaker, HAVE_GIFT);
                    doAnimationAction(self, "explain");
                    return SCRIPT_CONTINUE;
                }
                doAnimationAction(self, "bow");
                groundquests.requestGrantQuest(speaker, OBJ_BOUQUET_QUEST);
                sendSystemMessage(speaker, GIVE_GIFT);
                return SCRIPT_CONTINUE;
            }
            doAnimationAction(self, "celebrate");
        }
        return SCRIPT_CONTINUE;
    }
}

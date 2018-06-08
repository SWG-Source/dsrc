package script.working.tfiala;

import script.obj_id;

public class scale_test extends script.base_script
{
    public scale_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("scaletest"))
        {
            float bothanDefaultScale = getDefaultScaleFromObjectTemplate("object/creature/player/bothan_male.iff");
            LOG("scaletest", "bothan default scale: " + Float.toString(bothanDefaultScale));
            float humanDefaultScale = getDefaultScaleFromObjectTemplate("object/creature/player/human_male.iff");
            LOG("scaletest", "human default scale: " + Float.toString(humanDefaultScale));
            float wookieeDefaultScale = getDefaultScaleFromObjectTemplate("object/creature/player/wookiee_male.iff");
            LOG("scaletest", "wookiee default scale: " + Float.toString(wookieeDefaultScale));
        }
        return SCRIPT_CONTINUE;
    }
}

package script.event;

import script.library.utils;
import script.obj_id;

public class boss_buff extends script.base_script
{
    public boss_buff()
    {
    }
    private static final String[] HELP_TEXT =
    {
        "=========================================",
        "USAGE:",
        "\"Buff <VALUE>\": Sets your primary stats to <VALUE> and secondary stats to 1/2 of <VALUE>",
        "or \"Buff <VALUE> <SECONDARY STAT OPTIONS>\": See Secondary stats options below for details.",
        "or \"Buff <VALUE> <SECONDARY STAT OPTIONS> <DURATION IN SECONDS>: See Duration in Seconds below for details",
        "Secondary stats options: These can either be a specific number or the words \"default\" or \"low\".",
        "Secondary stats options: If you specify a number your secondary stats will be buffed to that value.",
        "Secondary stats options: Specify \"low\" to make your secondary stats automatically lower. This is useful when buffing to very high HAM to prevent crazy regen.",
        "Secondary stats options: Specify \"default\" to use the standard derived secondary stats.",
        "Duration in Seconds: Specify a number in seconds if you do not want the default 10000 seconds, which is roughly 2 hours and 45 minutes.",
        "Saying \"Detach\" will automatically remove this script.",
        "========================================="
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isPlayer(self))
        {
            detachScript(self, "event.boss_buff");
        }
        if (!isGod(self))
        {
            detachScript(self, "event.boss_buff");
            sendSystemMessage(self, "You must be in God Mode for this script to take hold.", null);
            return SCRIPT_CONTINUE;
        }
        else
        {
            if (getGodLevel(self) < 15)
            {
                detachScript(self, "event.boss_buff");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(self, "Say \"Help\" for usage and options.", null);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "event.boss_buff");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
        }
        else
        {
            if (getGodLevel(self) < 15)
            {
                detachScript(self, "event.boss_buff");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            if (getGodLevel(self) < 15)
            {
                detachScript(self, "event.boss_buff");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        else
        {
            detachScript(self, "event.boss_buff");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).startsWith("buff") || (toLower(strText)).startsWith("target"))
        {
            obj_id target = self;
            if ((toLower(strText)).startsWith("buff"))
            {
                target = self;
            }
            else if ((toLower(strText)).startsWith("target"))
            {
                target = getLookAtTarget(self);
                if (!isIdValid(target) || target == null)
                {
                    target = self;
                }
            }
            int buffTime = 10000;
            String secondBuffLevelStr;
            String args[] = strText.split(" ");
            int buffLevel = utils.stringToInt(args[1]);
            int secondBuffLevel = buffLevel / 2;
            secondBuffLevelStr = args[2];
            switch(args.length){
                case 4:
                    buffTime = utils.stringToInt(args[3]);
                    // don't break.
                case 3:
                    if ((toLower(secondBuffLevelStr)).equals("low"))
                    {
                        secondBuffLevel = buffLevel / 10;
                        if (secondBuffLevel > 10000)
                        {
                            secondBuffLevel = 10000;
                        }
                    }
                    else if (!(toLower(secondBuffLevelStr)).equals("default"))
                    {
                        secondBuffLevel = utils.stringToInt(secondBuffLevelStr);
                    }
                    // don't break.
                case 2:
                    addAttribModifier(target, "medical_enhance_health", HEALTH, buffLevel, buffTime, 0.0f, 10.0f, true, false, true);
                    addAttribModifier(target, "medical_enhance_action", ACTION, buffLevel, buffTime, 0.0f, 10.0f, true, false, true);
                    addAttribModifier(target, "medical_enhance_constitution", CONSTITUTION, secondBuffLevel, buffTime, 0.0f, 10.0f, true, false, true);
                    addAttribModifier(target, "medical_enhance_stamina", STAMINA, secondBuffLevel, buffTime, 0.0f, 10.0f, true, false, true);
                    sendSystemMessage(self, "Primary stats: " + buffLevel + " -- Secondary stats: " + secondBuffLevel + " -- Duration: " + buffTime, null);
                    // don't break.
                default:
                    return SCRIPT_CONTINUE;
            }
        }
        else if ((toLower(strText)).equals("detach"))
        {
            detachScript(self, "event.boss_buff");
        }
        else if ((toLower(strText)).equals("help"))
        {
            for (String helpText : HELP_TEXT) {
                sendSystemMessage(self, helpText, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
}

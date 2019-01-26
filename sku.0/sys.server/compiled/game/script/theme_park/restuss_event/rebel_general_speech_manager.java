package script.theme_park.restuss_event;

import script.dictionary;
import script.library.buff;
import script.library.chat;
import script.library.groundquests;
import script.obj_id;
import script.string_id;

public class rebel_general_speech_manager extends script.base_script
{
    public rebel_general_speech_manager()
    {
    }
    public static final String STF_GENERAL_CONVO = "restuss_event/rebel_general_speech";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                messageTo(self, "speechOne", null, 20, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                messageTo(self, "speechOne", null, 20, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int speechOne(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpeech1 = new string_id(STF_GENERAL_CONVO, "speech_1");
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                chat.chat(self, strSpeech1);
                doAnimationAction(self, "explain");
                messageTo(self, "speechTwo", null, 10, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int speechTwo(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpeech2 = new string_id(STF_GENERAL_CONVO, "speech_2");
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                chat.chat(self, strSpeech2);
                messageTo(self, "speechThree", null, 10, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int speechThree(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpeech3 = new string_id(STF_GENERAL_CONVO, "speech_3");
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                chat.chat(self, strSpeech3);
                messageTo(self, "speechFour", null, 10, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int speechFour(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpeech4 = new string_id(STF_GENERAL_CONVO, "speech_4");
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                chat.chat(self, strSpeech4);
                messageTo(self, "speechFive", null, 10, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int speechFive(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpeech5 = new string_id(STF_GENERAL_CONVO, "speech_5");
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                chat.chat(self, strSpeech5);
                doAnimationAction(self, "pound_fist_palm");
                messageTo(self, "speechSix", null, 7, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int speechSix(obj_id self, dictionary params) throws InterruptedException
    {
        string_id strSpeech6 = new string_id(STF_GENERAL_CONVO, "speech_6");
        obj_id cell = getContainedBy(self);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id content : contents) {
            if (isPlayer(content)) {
                chat.chat(self, strSpeech6);
                doAnimationAction(self, "salute1");
                buff.applyBuff(content, "restuss_rebel_general_speech");
                messageTo(self, "speechOne", null, 240, false);
                if (!groundquests.hasCompletedQuest(content, "restuss_rebel_atst")) {
                    if (!groundquests.isQuestActive(content, "restuss_rebel_atst")) {
                        groundquests.grantQuest(content, "restuss_rebel_atst");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

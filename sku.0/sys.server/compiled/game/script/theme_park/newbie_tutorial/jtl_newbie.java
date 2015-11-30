package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.features;

public class jtl_newbie extends script.theme_park.newbie_tutorial.tutorial_base
{
    public jtl_newbie()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "jtlNewbie", 1);
        newbieTutorialRequest(self, "clientReady");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (getIntObjVar(self, "jtlNewbie") != 4)
        {
            newbieTutorialRequest(self, "clientReady");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (!action.equals("clientReady"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isInTutorial(self))
        {
            setObjVar(self, "jtlNewbie", 4);
            detachScript(self, "theme_park.newbie_tutorial.jtl_newbie");
            return SCRIPT_CONTINUE;
        }
        int jtlNewbiePhase = getIntObjVar(self, "jtlNewbie");
        if (jtlNewbiePhase == 4)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendJtLemails(self);
            setObjVar(self, "jtlNewbie", 4);
        }
        return SCRIPT_CONTINUE;
    }
    public void sendJtLemails(obj_id self) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("intMail", 1);
        messageTo(self, "sendNewbieMails", dctParams, rand(30, 45), true);
        dctParams.put("intMail", 2);
        messageTo(self, "sendNewbieMails", dctParams, rand(90, 120), true);
        dctParams.put("intMail", 3);
        messageTo(self, "sendNewbieMails", dctParams, rand(180, 220), true);
        messageTo(self, "handleDetachJtlNewbie", null, 225, false);
    }
    public int sendNewbieMails(obj_id self, dictionary params) throws InterruptedException
    {
        int intMail = params.getInt("intMail");
        LOG("space", "received mail " + intMail + " for " + self);
        string_id strSubject = new string_id("space/space_interaction", "email_subject_" + intMail);
        string_id strBody = new string_id("space/space_interaction", "email_body_" + intMail);
        string_id strSender = new string_id("space/space_interaction", "email_sender_" + intMail);
        String strSenderName = utils.packStringId(strSender);
        LOG("space", "Received sendNewbieMail for " + self + " sending email " + intMail + " name is " + strSenderName);
        String body_oob = chatMakePersistentMessageOutOfBandBody(null, strBody);
        String subject_str = "@" + strSubject.toString();
        String waypName = "none";
        switch (intMail)
        {
            case 1:
            waypName = utils.packStringId(new string_id("npc_spawner_n", "cmdr_landau"));
            body_oob = chatAppendPersistentMessageWaypointData(body_oob, "naboo", -5516.0f, 4403.0f, null, waypName);
            break;
            case 2:
            waypName = utils.packStringId(new string_id("npc_spawner_n", "gil_burtin"));
            body_oob = chatAppendPersistentMessageWaypointData(body_oob, "tatooine", -1174.0f, -3647.0f, null, waypName);
            break;
            case 3:
            waypName = utils.packStringId(new string_id("npc_spawner_n", "j_pai_brek"));
            body_oob = chatAppendPersistentMessageWaypointData(body_oob, "corellia", -5072.0f, -2343.0f, null, waypName);
            break;
        }
        chatSendPersistentMessage(strSenderName, getChatName(self), subject_str, null, body_oob);
        return SCRIPT_CONTINUE;
    }
    public boolean isInTutorial(obj_id self) throws InterruptedException
    {
        boolean tutorialTest = false;
        location loc = getLocation(self);
        String area = loc.area;
        if (area.equals("tutorial") || area.startsWith("space_") || area.startsWith("space_ord_mantell") || hasObjVar(self, "npe.station_instance_id"))
        {
            tutorialTest = true;
        }
        return (tutorialTest);
    }
    public int handleDetachJtlNewbie(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "jtlNewbie", 4);
        detachScript(self, "theme_park.newbie_tutorial.jtl_newbie");
        return SCRIPT_CONTINUE;
    }
}

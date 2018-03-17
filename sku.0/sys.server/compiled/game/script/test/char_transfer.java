package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.create;
import script.library.pclib;
import script.library.skill;
import script.library.sui;
import script.library.utils;
import script.library.weapons;

public class char_transfer extends script.base.remote_object_requester
{
    public char_transfer()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                debugConsoleMsg(self, "command is: " + command);
                if (command.equalsIgnoreCase("ct_totarget"))
                {
                    debugConsoleMsg(self, "hit ct_totarget");
                    obj_id target = getLookAtTarget(self);
                    debugSpeakMsg(self, command + ": target=" + target);
                    dictionary params = new dictionary();
                    params.put("withItems", true);
                    params.put("allowOverride", true);
                    Object[] triggerParams = new Object[2];
                    triggerParams[0] = self;
                    triggerParams[1] = params;
                    int err = script_entry.runScripts("OnUploadCharacter", triggerParams);
                    if (err == SCRIPT_CONTINUE)
                    {
                        byte[] packedData = params.pack();
                        triggerParams = new Object[2];
                        triggerParams[0] = target;
                        triggerParams[1] = packedData;
                        err = script_entry.runScripts("OnDownloadCharacter", triggerParams);
                        removeObjVar(target, "hasTransferred");
                        debugSpeakMsg(self, command + ": err=" + err);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 10 || !isPlayer(self)) {
            detachScript(self, "test.char_transfer");
        }
        debugConsoleMsg(self, "char_transfer attached!");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "char_transfer detached!");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debugServerConsoleMsg(self, "char_transfer initialized!");
        return SCRIPT_CONTINUE;
    }
}

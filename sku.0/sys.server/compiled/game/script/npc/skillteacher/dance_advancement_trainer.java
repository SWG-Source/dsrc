package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class dance_advancement_trainer extends script.base_script
{
    public dance_advancement_trainer()
    {
    }
    public static final String CONVO = "dance_advancement";
    public static final String ADVANCEMENT_OBJVAR = "dance_advancement";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.converse.npc_converse_menu");
        fixName(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!hasScript(self, "npc.converse.npc_converse_menu"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        fixName(self);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (!hasObjVar(speaker, ADVANCEMENT_OBJVAR))
        {
            string_id msg = new string_id(CONVO, "greeting_nodance");
            chat.chat(self, speaker, msg, chat.ChatFlag_targetOnly);
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(CONVO, "greeting");
        string_id[] responses = new string_id[2];
        responses[0] = new string_id(CONVO, "greeting_r0");
        responses[1] = new string_id(CONVO, "greeting_r1");
        npcStartConversation(speaker, self, CONVO, msg, responses);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversation, obj_id speaker, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("greeting_r0"))
        {
            string_id msg = new string_id(CONVO, "offer");
            obj_var_list ovl = getObjVarList(speaker, ADVANCEMENT_OBJVAR);
            if (ovl != null)
            {
                int numItem = ovl.getNumItems();
                if (numItem > 10)
                {
                    numItem = 10;
                }
                prose_package[] responses = new prose_package[numItem];
                for (int i = 0; i < numItem; i++)
                {
                    obj_var ov = ovl.getObjVar(i);
                    String var = ov.getName();
                    responses[i] = prose.getPackage(new string_id(CONVO, "offer_r" + i), var);
                }
                npcSpeak(speaker, msg);
                npcSetConversationResponses(speaker, responses);
                npcAddConversationResponse(speaker, new string_id(CONVO, "offer_r10"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                msg = new string_id(CONVO, "cant_teach");
                npcSpeak(speaker, msg);
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
            }
        }
        else if ((response.getAsciiId()).equals("greeting_r1"))
        {
            string_id msg = new string_id(CONVO, "goodbye");
            npcSpeak(speaker, msg);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("offer_r10"))
        {
            string_id msg = new string_id(CONVO, "goodbye");
            npcSpeak(speaker, msg);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).startsWith("offer_r"))
        {
            int index = -1;
            obj_var_list ovl = getObjVarList(speaker, ADVANCEMENT_OBJVAR);
            if (ovl != null)
            {
                int numItem = ovl.getNumItems();
                if (numItem > 10)
                {
                    numItem = 10;
                }
                for (int i = 0; i < numItem; i++)
                {
                    if ((response.getAsciiId()).equals("offer_r" + i))
                    {
                        index = i;
                    }
                }
            }
            if (index > -1)
            {
                utils.setScriptVar(speaker, ADVANCEMENT_OBJVAR + ".idx", index);
                obj_var ov = ovl.getObjVar(index);
                String var = ov.getName();
                if (hasObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var + ".remaining"))
                {
                    int remaining = getIntObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var + ".remaining");
                    if (remaining <= 0)
                    {
                        if (hasObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var + ".cost"))
                        {
                            int cost = getIntObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var + ".cost");
                            int exp = getExperiencePoints(speaker, xp.DANCE);
                            if (exp < cost)
                            {
                                prose_package msg = prose.getPackage(new string_id(CONVO, "not_enough_xp"), localize(new string_id("exp_n", xp.DANCE)), cost);
                                npcSpeak(speaker, msg);
                                npcEndConversation(speaker);
                                return SCRIPT_CONTINUE;
                            }
                            prose_package msg = prose.getPackage(new string_id(CONVO, "confirm"), localize(new string_id("exp_n", xp.DANCE)), cost);
                            string_id[] responses = new string_id[2];
                            responses[0] = new string_id(CONVO, "confirm_r0");
                            responses[1] = new string_id(CONVO, "confirm_r1");
                            npcSpeak(speaker, msg);
                            npcSetConversationResponses(speaker, responses);
                            return SCRIPT_CONTINUE;
                        }
                    }
                    else 
                    {
                        string_id msg = new string_id(CONVO, "cant_teach_yet");
                        npcSpeak(speaker, msg);
                        npcEndConversation(speaker);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            string_id msg = new string_id(CONVO, "cant_teach");
            npcSpeak(speaker, msg);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("confirm_r0"))
        {
            if (utils.hasScriptVar(speaker, ADVANCEMENT_OBJVAR + ".idx"))
            {
                int index = utils.getIntScriptVar(speaker, ADVANCEMENT_OBJVAR + ".idx");
                obj_var_list ovl = getObjVarList(speaker, ADVANCEMENT_OBJVAR);
                obj_var ov = ovl.getObjVar(index);
                String var = ov.getName();
                int cost = getIntObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var + ".cost");
                int exp = getExperiencePoints(speaker, xp.DANCE);
                if (exp < cost)
                {
                    prose_package msg = prose.getPackage(new string_id(CONVO, "not_enough_xp"), localize(new string_id("exp_n", xp.DANCE)), cost);
                    npcSpeak(speaker, msg);
                    npcEndConversation(speaker);
                    return SCRIPT_CONTINUE;
                }
                cost *= -1;
                if (xp.grantUnmodifiedExperience(speaker, xp.DANCE, cost))
                {
                    String danceName = getStringObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var + ".grants");
                    String cmdName = "startDance+" + danceName;
                    grantCommand(speaker, cmdName);
                    removeObjVar(speaker, ADVANCEMENT_OBJVAR + "." + var);
                    utils.removeScriptVar(speaker, ADVANCEMENT_OBJVAR + ".idx");
                    if (!hasObjVar(speaker, ADVANCEMENT_OBJVAR))
                    {
                        detachScript(speaker, "systems.skills.performance.dance_advancement");
                    }
                    prose_package pp = prose.getPackage(new string_id(CONVO, "sysmsg_success"), danceName);
                    sendSystemMessageProse(speaker, pp);
                    string_id msg = new string_id(CONVO, "success");
                    npcSpeak(speaker, msg);
                    npcEndConversation(speaker);
                    return SCRIPT_CONTINUE;
                }
            }
            string_id msg = new string_id(CONVO, "cant_teach");
            npcSpeak(speaker, msg);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("confirm_r1"))
        {
            string_id msg = new string_id(CONVO, "goodbye");
            npcSpeak(speaker, msg);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void fixName(obj_id self) throws InterruptedException
    {
        String name_title = getName(self);
        String title = localize(new string_id(CONVO, "title"));
        String name = "";
        int idx = name_title.indexOf("(");
        if (idx == -1)
        {
            name = name_title + " ";
        }
        else 
        {
            name = name_title.substring(0, idx);
        }
        name_title = name + "(" + title + ")";
        setName(self, name_title);
    }
}

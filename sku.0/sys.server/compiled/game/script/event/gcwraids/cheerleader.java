package script.event.gcwraids;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

public class cheerleader extends script.base_script
{
    public cheerleader()
    {
    }
    private static final int NUMSPEECHES = 1;
    public static final String STF_FILE = "event/gcw_raids";
    private static final float MY_VISIT_TIME = 60 * 15;
    public static final String DATATABLE = "datatables/event/gcwraid/city_data.iff";
    private static final String[] SHUTTLETYPE =
    {
        "object/creature/npc/theme_park/lambda_shuttle.iff",
        "object/creature/npc/theme_park/player_shuttle.iff"
    };
    private static final String[] CELEB =
    {
        "darth_vader",
        "luke_skywalker"
    };
    private static final String[] ESCORT =
    {
        "fbase_dark_trooper_extreme",
        "rebel_commando"
    };
    public int startCheerleaderEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (gcw.getRebelRatio(self) > gcw.getImperialRatio(self))
        {
            setObjVar(self, "event.gcwraids.cheerleader_type", 1);
        }
        else 
        {
            setObjVar(self, "event.gcwraids.cheerleader_type", 0);
        }
        String myCity = locations.getGuardSpawnerRegionName(getLocation(self));
        if (myCity.equals("@tatooine_region_names:bestine"))
        {
            setObjVar(self, "event.gcwraids.cheerleader_type", 0);
        }
        if (myCity.equals("@tatooine_region_names:anchorhead"))
        {
            setObjVar(self, "event.gcwraids.cheerleader_type", 1);
        }
        messageTo(self, "createShuttle", null, 1, false);
        removeObjVar(self, "auto_invasion.next_invasion_time");
        return SCRIPT_CONTINUE;
    }
    public int createShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        int type = getIntObjVar(self, "event.gcwraids.cheerleader_type");
        obj_id shuttle = create.object(SHUTTLETYPE[type], getLocation(self));
        setObjVar(self, "event.gcwraids.shuttle", shuttle);
        setYaw(shuttle, 178);
        detachScript(shuttle, "ai.ai");
        detachScript(shuttle, "ai.creature_combat");
        detachScript(shuttle, "skeleton.humanoid");
        detachScript(shuttle, "systems.combat.combat_actions");
        detachScript(shuttle, "systems.combat.credit_for_kills");
        stop(shuttle);
        if (type == 1)
        {
            setPosture(shuttle, POSTURE_PRONE);
        }
        messageTo(self, "landShuttle", null, 6, false);
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, new string_id(STF_FILE, "areabroadcast_" + type));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int landShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id shuttle = getObjIdObjVar(self, "event.gcwraids.shuttle");
        int type = getIntObjVar(self, "event.gcwraids.cheerleader_type");
        if (type == 1)
        {
            queueCommand(shuttle, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(shuttle, POSTURE_UPRIGHT);
        }
        else if (type == 0)
        {
            queueCommand(shuttle, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(shuttle, POSTURE_PRONE);
        }
        messageTo(self, "spawnEscortsAndCeleb", null, 30, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnEscortsAndCeleb(obj_id self, dictionary params) throws InterruptedException
    {
        int type = getIntObjVar(self, "event.gcwraids.cheerleader_type");
        location here = getLocation(self);
        location celebSpot = (location)here.clone();
        celebSpot.z -= 12;
        obj_id celeb = create.object(CELEB[type], celebSpot);
        setObjVar(celeb, "event.gcwraids.type", type);
        ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
        stop(celeb);
        setInvulnerable(celeb, true);
        attachScript(celeb, "event.gcwraids.celeb_respect");
        setObjVar(self, "event.gcwraids.celeb", celeb);
        if (type == 0)
        {
            detachScript(celeb, "npc.celebrity.darth_vader");
            detachScript(celeb, "theme_park.imperial.quest_convo");
            detachScript(celeb, "npc.converse.npc_converse_menu");
        }
        else if (type == 1)
        {
            detachScript(celeb, "npc.celebrity.luke");
            detachScript(celeb, "theme_park.rebel.quest_convo");
            detachScript(celeb, "npc.converse.npc_converse_menu");
        }
        clearCondition(celeb, CONDITION_CONVERSABLE);
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 100.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                if (type == 0) {
                    playMusic(objPlayer, "sound/music_emperor_theme_stereo.snd");
                }
                else if (type == 1) {
                    playMusic(objPlayer, "sound/music_ambience_desert_stereo.snd");
                }
            }
        }
        obj_id escort;
        for (int i = 0; i < 12; i++)
        {
            escort = create.object(ESCORT[type], here);
            setObjVar(self, "event.gcwraids.escort" + i, escort);
            setObjVar(escort, "event.gcwraids.myId", i);
            setObjVar(escort, "event.gcwraids.mom", self);
            attachScript(escort, "event.gcwraids.cheerleader_escort");
            ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_WANDER);
        }
        messageTo(self, "walkForwardCeleb", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int walkForwardCeleb(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id celeb = getObjIdObjVar(self, "event.gcwraids.celeb");
        location there = getLocation(self);
        there.z += -25;
        String celebType = getCreatureName(celeb);
        ai_lib.aiPathTo(celeb, there);
        if (celebType.equals("darth_vader"))
        {
            setMovementWalk(celeb);
        }
        else 
        {
            setMovementRun(celeb);
        }
        utils.setScriptVar(self, "dialogue_set", rand(1, NUMSPEECHES));
        utils.setScriptVar(self, "dialogue_step", 0);
        messageTo(self, "engageInJingoisticRhetoric", null, 8, false);
        return SCRIPT_CONTINUE;
    }
    public int engageInJingoisticRhetoric(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id celeb = getObjIdObjVar(self, "event.gcwraids.celeb");
        int dialogueStep = utils.getIntScriptVar(self, "dialogue_step");
        int type = getIntObjVar(self, "event.gcwraids.cheerleader_type");
        if (dialogueStep > 10)
        {
            messageTo(self, "celebStartGivingQuests", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        string_id myLine = new string_id(STF_FILE, "cheerleader_" + type + "_" + utils.getIntScriptVar(self, "dialogue_set") + "_" + dialogueStep);
        chat.chat(celeb, chat.CHAT_SAY, chat.MOOD_NONE, myLine);
        if(type == 0){
            switch(dialogueStep){
                case 3:
                    doAnimationAction(celeb, "pound_fist_palm");
                    break;
                case 5:
                    doAnimationAction(celeb, "force_push");
                    break;
                case 7:
                    doAnimationAction(celeb, "force_choke");
                    break;
                case 10:
                    doAnimationAction(celeb, "point_forward");
                    break;
            }
        }
        else{
            switch(dialogueStep){
                case 3:
                    doAnimationAction(celeb, "point_right");
                    break;
                case 5:
                    doAnimationAction(celeb, "pound_fist_palm");
                    break;
                case 7:
                    doAnimationAction(celeb, "nod_head_once");
                    break;
                case 10:
                    doAnimationAction(celeb, "point_forward");
                    break;
            }
        }
        utils.setScriptVar(self, "dialogue_step", dialogueStep + 1);
        messageTo(self, "engageInJingoisticRhetoric", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int celebStartGivingQuests(obj_id self, dictionary params) throws InterruptedException
    {
        int type = getIntObjVar(self, "event.gcwraids.cheerleader_type");
        obj_id celeb = getObjIdObjVar(self, "event.gcwraids.celeb");
        setCondition(celeb, CONDITION_CONVERSABLE);
        if (type == 0)
        {
            attachScript(celeb, "conversation.event_cheerleader_vader");
        }
        else if (type == 1)
        {
            attachScript(celeb, "conversation.event_cheerleader_luke");
        }
        messageTo(self, "everyoneWalkBack", null, MY_VISIT_TIME, false);
        return SCRIPT_CONTINUE;
    }
    public int everyoneWalkBack(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id celeb = getObjIdObjVar(self, "event.gcwraids.celeb");
        setObjVar(self, "auto_invasion.invasion_active", 0);
        if (isIdValid(celeb))
        {
            String celebType = getCreatureName(celeb);
            ai_lib.aiPathTo(celeb, here);
            if (celebType.equals("darth_vader"))
            {
                setMovementWalk(celeb);
            }
            else 
            {
                setMovementRun(celeb);
            }
        }
        obj_id escort;
        for (int i = 0; i < 12; i++)
        {
            escort = getObjIdObjVar(self, "event.gcwraids.escort" + i);
            if (isIdValid(escort))
            {
                ai_lib.aiPathTo(escort, here);
            }
        }
        messageTo(self, "endEvent", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int endEvent(obj_id self, dictionary params) throws InterruptedException
    {
        int type = getIntObjVar(self, "event.gcwraids.cheerleader_type");
        obj_id shuttle = getObjIdObjVar(self, "event.gcwraids.shuttle");
        obj_id celeb = getObjIdObjVar(self, "event.gcwraids.celeb");
        destroyObject(celeb);
        obj_id escort;
        for (int i = 0; i < 12; i++)
        {
            escort = getObjIdObjVar(self, "event.gcwraids.escort" + i);
            if (isIdValid(escort))
            {
                destroyObject(escort);
            }
        }
        if (type > 0)
        {
            queueCommand(shuttle, (-1114832209), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(shuttle, POSTURE_PRONE);
        }
        else if (type == 0)
        {
            queueCommand(shuttle, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
            setPosture(shuttle, POSTURE_UPRIGHT);
        }
        messageTo(self, "cleanupShuttleAndStuff", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupShuttleAndStuff(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(getObjIdObjVar(self, "event.gcwraids.shuttle"));
        removeObjVar(self, "event.gcwraids.celeb");
        removeObjVar(self, "event.gcwraids.shuttle");
        for (int i = 0; i < 12; i++)
        {
            removeObjVar(self, "event.gcwraids.escort" + i);
        }
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, new string_id(STF_FILE, "closingbroadcast_" + getIntObjVar(self, "event.gcwraids.cheerleader_type")));
            }
        }
        int referenceNumber = getIntObjVar(self, "auto_invasion.reference_number");
        float minInvasionTime = dataTableGetFloat(DATATABLE, referenceNumber, "MINTIME");
        float timeChunkSize = dataTableGetFloat(DATATABLE, referenceNumber, "TIMECHUNKSIZE");
        int numTimeChunk = dataTableGetInt(DATATABLE, referenceNumber, "NUMTIMECHUNK");
        float rightNow = getGameTime();
        if (!hasObjVar(self, "auto_invasion.next_invasion_time"))
        {
            float nextInvasionTime = (rand(1, numTimeChunk) * timeChunkSize) + minInvasionTime + rightNow;
            setObjVar(self, "auto_invasion.next_invasion_time", nextInvasionTime);
        }
        setObjVar(self, "auto_invasion.testing_multiplier", 1);
        messageTo(self, "invasionTimerPing", null, 2700, false);
        return SCRIPT_CONTINUE;
    }
    public int escortDied(obj_id self, dictionary params) throws InterruptedException
    {
        if (getIntObjVar(self, "auto_invasion.invasion_active") != 1)
        {
            return SCRIPT_CONTINUE;
        }
        int myId = params.getInt("myId");
        if (params.getObjId("escort") == getObjIdObjVar(self, "event.gcwraids.escort" + myId))
        {
            removeObjVar(self, "event.gcwraids.escort" + myId);
            obj_id escort = create.object(ESCORT[getIntObjVar(self, "event.gcwraids.cheerleader_type")], getLocation(self));
            setObjVar(self, "event.gcwraids.escort" + myId, escort);
            setObjVar(escort, "event.gcwraids.myId", myId);
            setObjVar(escort, "event.gcwraids.mom", self);
            attachScript(escort, "event.gcwraids.cheerleader_escort");
        }
        return SCRIPT_CONTINUE;
    }
}

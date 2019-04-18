package script.test;

import script.library.create;
import script.library.healing;
import script.library.utils;
import script.location;
import script.obj_id;

public class ai_test extends script.base_script
{
    public ai_test()
    {
    }
    public void end(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "testPet"))
        {
            obj_id mob = getObjIdObjVar(self, "testPet");
            destroyObject(mob);
            removeObjVar(self, "testPet");
        }
    }
    public void start(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "testPet"))
        {
            end(self);
        }
        debugSpeakMsg(self, "--Noodle has begun");
        createTriggerVolume("blah", 3, true);
        String creatureType = getStringObjVar(self, "testPetType");
        location blah = getLocation(self);
        blah.x = blah.x + 1.25f;
        obj_id newmob = create.object(creatureType, blah);
        if (newmob == null)
        {
            debugSpeakMsg(self, "--Could not spawn pet");
            return;
        }
        detachAllScripts(newmob);
        attachScript(newmob, "test.report_behavior");
        attachScript(newmob, "test.ai_test2");
        setObjVar(self, "testPet", newmob);
        location loc = getLocation(self);
        setHomeLocation(newmob, loc);
        stop(newmob);
        stopFloating(newmob);
    }
    public void gimme_skills(obj_id speaker) throws InterruptedException
    {
        grantSkill(speaker, "outdoors_creaturehandler_novice");
        grantSkill(speaker, "outdoors_creaturehandler_taming_01");
        grantSkill(speaker, "outdoors_creaturehandler_taming_02");
        grantSkill(speaker, "outdoors_creaturehandler_taming_03");
        grantSkill(speaker, "outdoors_creaturehandler_taming_04");
        grantSkill(speaker, "outdoors_creaturehandler_training_01");
        grantSkill(speaker, "outdoors_creaturehandler_training_02");
        grantSkill(speaker, "outdoors_creaturehandler_training_03");
        grantSkill(speaker, "outdoors_creaturehandler_training_04");
        grantSkill(speaker, "outdoors_creaturehandler_healing_01");
        grantSkill(speaker, "outdoors_creaturehandler_healing_02");
        grantSkill(speaker, "outdoors_creaturehandler_healing_03");
        grantSkill(speaker, "outdoors_creaturehandler_healing_04");
        grantSkill(speaker, "outdoors_creaturehandler_support_01");
        grantSkill(speaker, "outdoors_creaturehandler_support_02");
        grantSkill(speaker, "outdoors_creaturehandler_support_03");
        grantSkill(speaker, "outdoors_creaturehandler_support_04");
        grantSkill(speaker, "outdoors_creaturehandler_master");
        setObjVar(speaker, "fasttame", 1);
    }
    public void start2(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "testPet"))
        {
            end(self);
        }
        debugSpeakMsg(self, "--Testing has begun");
        String creatureType = getStringObjVar(self, "testPetType");
        location blah = getLocation(self);
        obj_id newmob = create.object(creatureType, blah);
        if (newmob == null)
        {
            debugSpeakMsg(self, "--Could not spawn pet");
            return;
        }
        location loc = getLocation(self);
        setHomeLocation(newmob, loc);
    }
    public void startMounts(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(self, "testPet"))
        {
            end(self);
        }
        debugSpeakMsg(self, "--Testing has begun");
        String creatureType = getStringObjVar(self, "testPetType");
        location blah = getLocation(self);
        obj_id newmob = create.object(creatureType, blah);
        if (newmob == null)
        {
            debugSpeakMsg(self, "--Could not spawn pet");
            return;
        }
        location loc = getLocation(self);
        setHomeLocation(newmob, loc);
        attachScript(newmob, "ai.pet_advance");
        gimme_skills(speaker);
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        debugSpeakMsg(self, "Trigger Volume Enter");
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        debugSpeakMsg(self, "Trigger Volume Exit");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        healing.fullHeal(self);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        healing.fullHeal(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if(!isGod(self) || getGodLevel(self) < 10 || !isPlayer(self)){
            detachScript(self, "test.ai_test");
        }
        setObjVar(self, "blahblah", 1);
        start(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        end(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        String[] words = split(text, ' ');
        if (words[0].equals("cone"))
        {
            debugSpeakMsg(self, "happy cone cone");
            float fltConeLength = 64.0f;
            float fltConeWidth = 15.0f;
            obj_id objTarget = getLookAtTarget(self);
            obj_id objPlayer = self;
            obj_id[] objDefenders = getCreaturesInCone(objPlayer, objTarget, fltConeLength, fltConeWidth);
            for (obj_id objDefender1 : objDefenders) {
                debugSpeakMsg(objDefender1, "I am targetsd");
            }
            objDefenders = pvpGetTargetsInCone(objPlayer, objPlayer, objTarget, fltConeLength, fltConeWidth);
            for (obj_id objDefender : objDefenders) {
                debugSpeakMsg(objDefender, "I am PVP targetsd");
            }
        }
        if (speaker == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (words[0].equals("spawn"))
        {
            String toCreate = words[1];
            location here = getLocation(self);
            obj_id newMob = create.object(toCreate, here);
            stopFloating(newMob);
            debugSpeakMsg(newMob, "i am a spatula");
            return SCRIPT_CONTINUE;
        }
        switch (words[0]) {
            case "start":
                start(self);
                return SCRIPT_CONTINUE;
            case "start2":
                start2(self);
                return SCRIPT_CONTINUE;
            case "startMounts":
                startMounts(self, speaker);
                return SCRIPT_CONTINUE;
            case "setPetType":
                setObjVar(self, "testPetType", words[1]);
                return SCRIPT_CONTINUE;
            case "spawnCreatures": {
                String creatureType = getStringObjVar(self, "testPetType");
                for (int i = 0; i < 10; i++) {
                    location loc = getLocation(self);
                    create.object(creatureType, loc);
                }
                break;
            }
            case "spawnRandom300": {
                String creatureType = getStringObjVar(self, "testPetType");
                for (int i = 0; i < 300; i++) {
                    location loc = getLocation(self);
                    obj_id mob = create.object(creatureType, loc);
                    loiterTarget(mob, self, 10, 30, 1, 2);
                }
                break;
            }
            case "reload":
                debugSpeakMsg(self, "Reloading node " + words[1]);
                obj_id idToReload = utils.stringToObjId(words[1]);
                obj_id[] ids = new obj_id[1];
                ids[0] = idToReload;
                reloadPathNodes(ids);
                break;
            case "testSwarm": {
                String creatureType = getStringObjVar(self, "testPetType");
                for (int i = 0; i < 1; i++) {
                    location loc = getLocation(self);
                    obj_id mob = create.object(creatureType, loc);
                    swarm(mob, speaker);
                    setMovementRun(mob);
                }
                break;
            }
            case "testSwarm2": {
                String creatureType = getStringObjVar(self, "testPetType");
                for (int i = 0; i < 1; i++) {
                    location loc = getLocation(self);
                    obj_id mob = create.object(creatureType, loc);
                    swarm(mob, speaker, 8.0f);
                    setMovementRun(mob);
                }
                break;
            }
            case "testSwarm3": {
                String creatureType = getStringObjVar(self, "testPetType");
                for (int i = 0; i < 1; i++) {
                    location loc = getLocation(self);
                    obj_id mob = create.object(creatureType, loc);
                    swarm(mob, speaker, 16.0f);
                    setMovementRun(mob);
                }
                break;
            }
        }
        if (!hasObjVar(self, "testPet"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id mob = getObjIdObjVar(self, "testPet");
        switch (words[0]) {
            case "follow":
                debugSpeakMsg(self, "--Now following");
                follow(mob, speaker, 2.0f, 5.0f);
                break;
            case "followOffset":
                debugSpeakMsg(self, "--Now following");
                follow(mob, speaker, new location(2, 0, 0, ""));
                break;
            case "swarm":
                debugSpeakMsg(self, "--Swarm");
                swarm(mob, speaker);
                break;
            case "swarmit":
                obj_id target = utils.stringToObjId(words[1]);
                debugSpeakMsg(self, "--Swarming target");
                swarm(mob, target);
                break;
            case "wander":
                debugSpeakMsg(self, "--Wandering");
                wander(mob);
                break;
            case "loiter":
                debugSpeakMsg(self, "--Loitering");
                final location home = getLocation(mob);
                loiterLocation(mob, home, 15.0f, 20.0f, 1.0f, 2.0f);
                break;
            case "loiterNear":
                debugSpeakMsg(self, "--Loitering");
                loiterTarget(mob, self, 0, 5, 0, 0);
                break;
            case "stop":
                debugSpeakMsg(self, "--Stop");
                stop(mob);
                break;
            case "pathHome": {
                debugSpeakMsg(self, "--Going home");
                location loc = getLocation(self);
                pathTo(mob, loc);
                break;
            }
            case "pathAway": {
                debugSpeakMsg(self, "--Going to speaker");
                location loc = getLocation(speaker);
                pathTo(mob, loc);
                break;
            }
            case "moveX": {
                debugSpeakMsg(self, "--Going to speaker");
                location loc = getLocation(mob);
                loc.x += 1;
                pathTo(mob, loc);
                break;
            }
            case "flee":
                debugSpeakMsg(self, "--Fleeing");
                flee(mob, speaker, 5, 10);
                break;
            case "face":
                debugSpeakMsg(self, "--Facing target");
                faceToBehavior(mob, speaker);
                break;
            case "faceBehavior":
                debugSpeakMsg(self, "--Facing behavior");
                faceToBehavior(mob, speaker);
                break;
            case "end":
                debugSpeakMsg(self, "--Done with testing");
                end(self);
                break;
            case "anger":
                debugSpeakMsg(self, "--Angry at speaker");
                addToMentalStateToward(mob, speaker, ANGER, 40);
                break;
            case "frenzy":
                debugSpeakMsg(self, "--Frenzying toward speaker");
                addToMentalStateToward(mob, speaker, ANGER, 100);
                break;
            case "attack":
                debugSpeakMsg(self, "--Attacking speaker");
                addToMentalStateToward(mob, speaker, ANGER, 100, BEHAVIOR_ATTACK);
                break;
            case "upright":
                debugSpeakMsg(self, "--setPosture(self, POSTURE_UPRIGHT)");
                setPosture(mob, POSTURE_UPRIGHT);
                break;
            case "crouch":
                debugSpeakMsg(self, "--setPosture(self, POSTURE_CROUCHED)");
                setPosture(mob, POSTURE_CROUCHED);
                break;
            case "prone":
                debugSpeakMsg(self, "--setPosture(self, POSTURE_PRONE)");
                setPosture(mob, POSTURE_PRONE);
                break;
            case "lie":
                debugSpeakMsg(self, "--setPosture(self, POSTURE_LYING_DOWN)");
                setPosture(mob, POSTURE_LYING_DOWN);
                break;
            case "drive":
                debugSpeakMsg(self, "--setPosture(self, POSTURE_DRIVING_VEHICLE)");
                setPosture(mob, POSTURE_DRIVING_VEHICLE);
                break;
            case "stand":
                debugSpeakMsg(self, "--setLocomotion(self, LOCOMOTION_STANDING");
                setLocomotion(mob, LOCOMOTION_STANDING);
                break;
            case "walk":
                debugSpeakMsg(self, "--setLocomotion(self, LOCOMOTION_WALKING");
                setLocomotion(mob, LOCOMOTION_WALKING);
                break;
            case "run":
                debugSpeakMsg(self, "--setLocomotion(self, LOCOMOTION_RUNNING");
                setLocomotion(mob, LOCOMOTION_RUNNING);
                break;
            case "fast":
                debugSpeakMsg(self, "--setMovementRun(self)");
                setMovementRun(mob);
                break;
            case "slow":
                debugSpeakMsg(self, "--setMovementWalk(self)");
                setMovementWalk(mob);
                break;
            case "pathToName":
                debugSpeakMsg(self, "--Going to waypoint " + words[1]);
                pathTo(mob, words[1]);
                break;
            case "canSee":
                if (canSee(mob, speaker)) {
                    debugSpeakMsg(mob, "Can see you");
                } else {
                    debugSpeakMsg(mob, "Can NOT see you");
                }
                break;
        }
        return SCRIPT_CONTINUE;
    }
}

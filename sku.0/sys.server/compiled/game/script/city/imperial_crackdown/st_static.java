package script.city.imperial_crackdown;

import script.*;
import script.library.*;

public class st_static extends script.base_script
{
    public st_static()
    {
    }
    public static final String VOLUME_NAME = "perception";
    public static final String STRING_FILE = "conversation/crackdown_st_static";
    public static final string_id REBEL_BREACH = new string_id(STRING_FILE, "rebel_breach");
    public static final string_id FOLLOW_START = new string_id(STRING_FILE, "follow_start");
    public static final string_id FOLLOW_LOST = new string_id(STRING_FILE, "follow_lost");
    public static final string_id FOLLOW_GIVEUP = new string_id(STRING_FILE, "follow_giveup");
    public static final string_id SALUTE = new string_id(STRING_FILE, "salute");
    public static final string_id SALUTE_NAME = new string_id(STRING_FILE, "salute_name");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        createTriggerVolume(VOLUME_NAME, 10.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id player) throws InterruptedException
    {
        if (volumeName.equals(VOLUME_NAME))
        {
            if (!utils.hasScriptVar(self, "event"))
            {
                if (isPlayer(player) && !ai_lib.isInCombat(self))
                {
                    dictionary params = new dictionary();
                    int lotto;
                    String playerFaction = factions.getFaction(player);
                    if (playerFaction == null || playerFaction.equals(""))
                    {
                        playerFaction = "neutral";
                    }
                    if (playerFaction.equals(factions.FACTION_IMPERIAL))
                    {
                        if (factions.pvpGetType(player) == factions.PVPTYPE_DECLARED)
                        {
                            int rank = pvpGetCurrentGcwRank(player);
                            if (rank > 6)
                            {
                                utils.setScriptVar(self, "event", true);
                                params.put("player", player);
                                params.put("rank", rank);
                                messageTo(self, "officerBreach", params, 0, false);
                                return SCRIPT_CONTINUE;
                            }
                        }
                        return SCRIPT_CONTINUE;
                    }
                    if (playerFaction.equals(factions.FACTION_REBEL))
                    {
                        lotto = rand(1, 100);
                        if (lotto <= 5)
                        {
                            utils.setScriptVar(self, "event", true);
                            params.put("playerName", getName(player));
                            messageTo(self, "rebelBreach", params, 10, false);
                            return SCRIPT_CONTINUE;
                        }
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        lotto = rand(1, 1000);
                        if (lotto <= 5)
                        {
                            utils.setScriptVar(self, "event", true);
                            params.put("player", player);
                            messageTo(self, "playerFollow", params, 2, false);
                            return SCRIPT_CONTINUE;
                        }
                        else if (lotto <= 20)
                        {
                            params.put("player", player);
                            messageTo(self, "playerBark", params, 1, false);
                            return SCRIPT_CONTINUE;
                        }
                        else if (lotto <= 100)
                        {
                            utils.setScriptVar(self, "event", true);
                            params.put("player", player);
                            messageTo(self, "playerLoiter", params, 10, false);
                            return SCRIPT_CONTINUE;
                        }
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        location homeLoc = getHomeLocation(self);
        removeObjVar(self, "ai.persistantFollowing");
        if (!ai_lib.isInCombat(self))
        {
            chat.publicChat(self, null, null, null, prose.getPackage(FOLLOW_LOST, getName(self)));
            addLocationTarget("home", homeLoc, 1.5f);
            setMovementWalk(self);
            pathTo(self, homeLoc);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowPathNotFound(obj_id self, obj_id target) throws InterruptedException
    {
        location homeLoc = getHomeLocation(self);
        removeObjVar(self, "ai.persistantFollowing");
        if (!ai_lib.isInCombat(self))
        {
            chat.publicChat(self, null, null, null, prose.getPackage(FOLLOW_LOST, getName(self)));
            addLocationTarget("home", homeLoc, 1.5f);
            setMovementWalk(self);
            pathTo(self, homeLoc);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        pathTo(self, getHomeLocation(self));
        dictionary params = new dictionary();
        params.put("oldLocation", getLocation(self));
        messageTo(self, "amIStuck", params, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String loc) throws InterruptedException
    {
        utils.removeScriptVar(self, "event");
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "mom");
        if (!isIdValid(mom) || mom == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int spawnNum = getIntObjVar(self, "spawn_number");
        if (spawnNum == 0)
        {
            return SCRIPT_OVERRIDE;
        }
        int respawnTime = getIntObjVar(self, "respawn_time");
        if (respawnTime == 0)
        {
            respawnTime = 300;
        }
        dictionary info = new dictionary();
        info.put("spawnNumber", spawnNum);
        info.put("spawnMob", self);
        messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        return SCRIPT_CONTINUE;
    }
    public int rebelBreach(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            chat.publicChat(self, null, chat.CHAT_COMMAND, null, prose.getPackage(REBEL_BREACH, params.getString("playerName")));
        }
        utils.removeScriptVar(self, "event");
        return SCRIPT_CONTINUE;
    }
    public int playerFollow(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            chat.publicChat(self, null, null, null, prose.getPackage(FOLLOW_START, getName(self)));
            obj_id player = params.getObjId("player");
            if (isIdValid(player))
            {
                setObjVar(self, "ai.persistantFollowing", player);
                setMovementRun(self);
                follow(self, player, 8f, 10f);
                messageTo(self, "stopPersuit", null, 30, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int stopPersuit(obj_id self, dictionary params) throws InterruptedException
    {
        location homeLoc = getHomeLocation(self);
        removeObjVar(self, "ai.persistantFollowing");
        if (!ai_lib.isInCombat(self))
        {
            chat.publicChat(self, null, null, null, prose.getPackage(FOLLOW_GIVEUP, getName(self)));
            setMovementWalk(self);
            addLocationTarget("home", homeLoc, 1.5f);
            pathTo(self, homeLoc);
        }
        return SCRIPT_CONTINUE;
    }
    public int amIStuck(obj_id self, dictionary params) throws InterruptedException
    {
        if (params.getLocation("oldLocation").equals(getLocation(self)))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int playerBark(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            faceTo(self, params.getObjId("player"));
            chat.chat(self, new string_id(STRING_FILE, "bark_s" + rand(1, 3)));
        }
        return SCRIPT_CONTINUE;
    }
    public int playerLoiter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id[] nearby = getTriggerVolumeContents(self, VOLUME_NAME);
        utils.removeScriptVar(self, "event");
        if (isIdValid(player))
        {
            for (obj_id aNearby : nearby) {
                if (aNearby == player) {
                    if (!ai_lib.isInCombat(self)) {
                        faceTo(self, player);
                        chat.chat(self, new string_id(STRING_FILE, "loiter_s" + rand(1, 2)));
                        int randAnim = rand(0, 2);
                        if (randAnim == 0) {
                            doAnimationAction(self, "point_left");
                        } else if (randAnim == 1) {
                            doAnimationAction(self, "wave_on_directing");
                        }
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int officerBreach(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int rank = params.getInt("rank");
        String faction = factions.getFaction(player);
        if ((getGameTime() - utils.getIntScriptVar(player, "saluted")) < 3600)
        {
            utils.removeScriptVar(self, "event");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(player, "saluted", getGameTime());
        faceTo(self, player);
        doAnimationAction(self, "salute2");
        String playerName = getName(player);
        prose_package pp;
        java.util.StringTokenizer st = new java.util.StringTokenizer(playerName, " ");
        if (st.countTokens() == 2)
        {
            st.nextToken();
            String lastName = st.nextToken();
            pp = prose.getPackage(SALUTE_NAME, factions.getRankNameStringId(rank, faction), lastName);
        }
        else 
        {
            pp = prose.getPackage(SALUTE, factions.getRankNameStringId(rank, faction));
        }
        chat.publicChat(self, null, null, null, pp);
        messageTo(self, "removeEvent", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int removeEvent(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "event");
        return SCRIPT_CONTINUE;
    }
}

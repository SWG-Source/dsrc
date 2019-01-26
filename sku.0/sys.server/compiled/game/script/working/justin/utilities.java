package script.working.justin;

import script.library.*;
import script.location;
import script.obj_id;
import script.script_entry;

public class utilities extends script.base_script
{
    public utilities()
    {
    }
    public void usage(obj_id self) throws InterruptedException
    {
        chat.chat(self, "Usagement!: utility <cmd> [<args ...>]");
        chat.chat(self, "commands: ");
        chat.chat(self, "grantSkills - grant all skills to yourself");
        chat.chat(self, "quest - execute a quest command dammit!!?");
        chat.chat(self, "  activate <name> - activate a quest named <name> from player.quests datatable");
        chat.chat(self, "  clear           - clear all quest bits");
        chat.chat(self, "spawn <name> - spawn a creature from creatures.tab");
        chat.chat(self, "faction <faction name> <pounts - set your faction standing");
        chat.chat(self, "makeFsVillageEligible");
        chat.chat(self, "makeFsVillageIneligible");
        chat.chat(self, "unlock <branch> - unlock fs branch using the fs_quests library. E.g. force_sensitive_combat_prowess_ranged_speed");
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.startsWith("utility"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            st.nextToken();
            String cmd = st.nextToken();
            switch (cmd) {
                case "grantSkills":
                    grantSkill(self, "combat_unarmed_master");
                    obj_id pInv = utils.getInventoryContainer(self);
                    if (isIdValid(pInv)) {
                        createObject("object/tangible/terminal/terminal_character_builder.iff", pInv, "");
                    }
                    break;
                case "completed": {
                    String arg1 = st.nextToken();
                    if (arg1 != null) {
                        Object[] params = new Object[3];
                        params[0] = self;
                        params[1] = arg1;
                        params[2] = Boolean.TRUE;
                        script_entry.runScripts("OnForceSensitiveQuestCompleted", params);
                    }
                    break;
                }
                case "showOff":
                    chat.chat(self, "Hi there BANDIT!!!");
                    break;
                case "quest": {
                    String arg1 = st.nextToken();
                    if (arg1 != null) {
                        if (arg1.equals("activate")) {
                            String arg2 = st.nextToken();
                            if (arg2 != null) {
                                int questId = questGetQuestId(arg2);
                                questActivateQuest(questId, self, null);
                            }
                        } else if (arg1.equals("clear")) {
                            String arg2 = st.nextToken();
                            if (arg2 != null) {
                                int questId = questGetQuestId(arg2);
                                questClearQuest(questId, self);
                            }
                        }
                    }
                    break;
                }
                case "faction": {
                    String arg1 = st.nextToken();
                    if (arg1 != null) {
                        String arg2 = st.nextToken();
                        if (arg2 != null) {
                            int points = utils.stringToInt(arg2);
                            factions.setFactionStanding(self, arg1, points);
                            chat.chat(self, "set faction standing for " + arg1 + " to " + points);
                        } else {
                            usage(self);
                        }
                    } else {
                        usage(self);
                    }
                    break;
                }
                case "requestLocation":
                    requestLocation(self, "testrequest", getLocation(self), 512.0f, 128.0f, true, true);
                    break;
                case "spawn": {
                    String arg1 = st.nextToken();
                    if (arg1 != null) {
                        location l = getLocation(self);
                        l.x += 2;
                        obj_id creature = create.createCreature(arg1, l, true);
                    }
                    break;
                }
                case "makeFsVillageEligible":
                    fs_quests.makeVillageEligible(self);
                    break;
                case "makeFsVillageIneligible":
                    removeObjVar(self, fs_quests.VAR_VILLAGE_ELIGIBLE);
                    break;
                case "unlock": {
                    String arg1 = st.nextToken();
                    if (arg1 != null) {
                        boolean success = fs_quests.unlockBranch(self, arg1);
                        if (!success) {
                            chat.chat(self, "could not unlock " + arg1);
                        } else {
                            chat.chat(self, arg1 + " has been unlocked");
                        }
                    }
                    break;
                }
                case "rad2deg": {
                    String arg1 = st.nextToken();
                    if (arg1 != null) {
                        float rads = utils.stringToFloat(arg1);
                        float degs = (float) Math.toDegrees(rads);
                        chat.chat(self, "" + degs);
                    }
                    break;
                }
                case "cover":
                    chat.chat(self, "calling setCreatureCoverVisibility(" + self + ", true)");
                    setCreatureCoverVisibility(self, false);
                    break;
                case "uncover":
                    setCreatureCoverVisibility(self, true);
                    break;
                case "getcover":
                    if (getCreatureCoverVisibility(self)) {
                        chat.chat(self, "I am visible");
                    } else {
                        chat.chat(self, "I am not visible");
                    }
                    break;
                default:
                    usage(self);
                    break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLocationReceived(obj_id self, String locationId, obj_id locationObject, location locationLocation, float locationRadius) throws InterruptedException
    {
        LOG("testlocation", "locationId=" + locationId);
        chat.chat(self, "locationId=" + locationId);
        chat.chat(self, "locationObject = " + locationObject);
        destroyObject(locationObject);
        return SCRIPT_CONTINUE;
    }
}

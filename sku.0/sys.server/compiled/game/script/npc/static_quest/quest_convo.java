package script.npc.static_quest;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

public class quest_convo extends script.base_script {
    public quest_convo() {
    }

    public static final String FACETO_VOLUME_NAME = "faceToTriggerVolume";

    public static final String[] NPC_DEACTIVATE_LIST =
            {
                    "tato_valarian_kavas_urdano",
                    "tato_valarian_ind",
                    "hedon_istee",
                    "igbi_freemo"
            };

    public int OnAttach(obj_id self) throws InterruptedException {
        String exclusion = getStringObjVar(self, "quest_table");

        // IF THIS IS AN NPC WHO'S BEING UPDATED, WE'RE REMOVING THE QUEST CONVO
        // SCRIPT AND NOT MAKING HIM INTERESTING.
        if (exclusion == null) {
            exclusion = "";
        }
        for (String s : NPC_DEACTIVATE_LIST) {
            if (exclusion.equals(s)) {
                detachScript(self, "npc.static_quest.quest_convo");
                return SCRIPT_CONTINUE;
            }
        }

        messageTo(self, "handleStaticQuestNpcAttach", null, 2, false);
        return SCRIPT_CONTINUE;
    }

    public int handleStaticQuestNpcAttach(obj_id self, dictionary params) throws InterruptedException {
        String creatureName = getCreatureName(self);
        if (creatureName == null || creatureName.length() <= 0) {
            return SCRIPT_CONTINUE;
        }
        if (creatureName.startsWith("vana_sage") || creatureName.startsWith("ikka_gesul"))
        {
            detachScript(self, "npc.static_quest.quest_convo");
            return SCRIPT_CONTINUE;
        }

        if (hasScript(self, "conversation.faction_recruiter_imperial") || hasScript(self, "conversation.faction_recruiter_rebel")) {
            detachScript(self, "npc.static_quest.quest_convo");
            return SCRIPT_CONTINUE;
        }

        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        createTriggerVolume(FACETO_VOLUME_NAME, 8.0f, true);
        return SCRIPT_CONTINUE;
    }

    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException {
        //Gets the datatable from his objvars.
        String datatable = getStringObjVar(self, "quest_table");
        String exclusion = datatable;
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";

        //The gating string tells us if he's ever done work for this NPC before, and if so, what mission he's on.
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");
        int gating = getIntObjVar(speaker, gatingString);
        if (gating == 0) {
            boolean noMatch = true;
            for (String s : NPC_DEACTIVATE_LIST) {
                if (exclusion.equals(s)) {
                    noMatch = false;
                    break;
                }
            }

            if (noMatch) {
                //He's never worked for this NPC so he's on mission 1
                setObjVar(speaker, gatingString, 1);
                gating = 1;
            }
        }

        //the number of rows in the datatable is the total # of missions this NPC can give out.
        int maxGating = dataTableGetNumRows(datatable) - 1;
        int questNum = gating;

        //All from the datatable
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String ALT_CONVO = "static_npc/default_dialog";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String convoType = dataTableGetString(datatable, questNum, "quest_giver_convo");
        String questType = dataTableGetString(datatable, questNum, "quest_type");

        // This means that the starting of this quest has been deactivated
        if (questNum == 0) {
            string_id deactivated = new string_id(ALT_CONVO, "deactivated");
            chat.chat(self, deactivated);
            return SCRIPT_CONTINUE;
        }

        //This is to prevent a player from doing both a "light" and a "dark" side quest.
        if (dataTableHasColumn(datatable, "opposing_objvar")) {
            String opposing = dataTableGetString(datatable, questNum, "opposing_objvar");
            if (hasObjVar(speaker, opposing)) {
                if (getIntObjVar(speaker, opposing) > 1) {
                    chat.chat(self, new string_id(CONVO, "go_away"));
                    npcEndConversation(speaker);
                    return SCRIPT_CONTINUE;
                }
            }
        }

        // If you've done all this guys missions he uses the "next" line to tell you.
        if (gating > maxGating) {
            CONVO = dataTableGetString(datatable, 1, "convo");
            chat.chat(self, new string_id(CONVO, "next"));
            return SCRIPT_OVERRIDE;
        }

        // Is this NPC Rebel Only, or Imperial Only?  We'll find out below.
        if (dataTableHasColumn(datatable, "gating_faction")) {
            String gatingFaction = dataTableGetString(datatable, questNum, "gating_faction");
            if (gatingFaction == null) {
                gatingFaction = "none";
            }

            if (gatingFaction.equals("Rebel") || gatingFaction.equals("Imperial")) {
                if (gatingFaction.equals("Imperial") && !factions.isImperial(speaker)) {
                    chat.chat(self, new string_id(CONVO, "notyet"));
                    return SCRIPT_OVERRIDE;
                } else if (gatingFaction.equals("Rebel") && !factions.isRebel(speaker)) {
                    chat.chat(self, new string_id(CONVO, "notyet"));
                    return SCRIPT_OVERRIDE;
                } else if (factions.isOnLeave(speaker)) {
                    chat.chat(self, new string_id(CONVO, "notyet"));
                    return SCRIPT_OVERRIDE;
                }
            } else {
                if (!gatingFaction.equals("none")) {
                    float playerFaction = getFloatObjVar(speaker, "faction." + gatingFaction);
                    int neededFaction = dataTableGetInt(datatable, questNum, "gating_faction_amt");
                    if (playerFaction < neededFaction) {
                        chat.chat(self, new string_id(CONVO, "notyet"));
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }

        // Is this Quest not for Jedi's?
        if (dataTableHasColumn(datatable, "gating_jedi")) {
            String gatingJedi = dataTableGetString(datatable, questNum, "gating_jedi");
            if (gatingJedi == null) {
                gatingJedi = "none";
            }
            if (gatingJedi.equals("yes")) {
                if (isJedi(speaker)) {
                    chat.chat(self, new string_id(CONVO, "jedi"));
                    return SCRIPT_OVERRIDE;
                }
            }
        }

        if (dataTableHasColumn(datatable, "gating_object")) {
            String gatingObject = dataTableGetString(datatable, questNum, "gating_object");
            if (gatingObject == null) {
                gatingObject = "none";
            }
            if (!gatingObject.equals("none")) {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (!checkForGatingItem(playerInv, speaker, questNum, datatable)) {
                    chat.chat(self, new string_id(CONVO, "notyet"));
                    return SCRIPT_OVERRIDE;
                }
            }
        }

        String gatingObjVar = dataTableGetString(datatable, 1, "gating_objvar");

        if (gatingObjVar != null) {
            if (!gatingObjVar.equals("") && !gatingObjVar.equals("none")) {
                if (!hasObjVar(speaker, gatingObjVar)) {
                    chat.chat(self, new string_id(CONVO, "notyet"));
                    return SCRIPT_OVERRIDE;
                } else {
                    int gatingAmt = dataTableGetInt(datatable, questNum, "gating_objvar_amt");
                    int playerGate = getIntObjVar(speaker, gatingObjVar);
                    if (playerGate < gatingAmt) {
                        chat.chat(self, new string_id(CONVO, "notyet"));
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }

        //If the convotype is null then something is wrong with the datatable and we'll exit gracefully from this script.
        if (convoType == null) {
            debugSpeakMsg(self, "For some reason I'm stuck with nothing to say.");
            return SCRIPT_OVERRIDE;
        }

        // If the NPC is busy fighting, or the player for that matter, well then they shouldn't be talking about quests
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
            return SCRIPT_OVERRIDE;
        // Now we're getting into it.  This means you've failed his mission and you've come back for punishment.
        else if (hasObjVar(speaker, questID + ".failed")) {
            string_id failMessage = new string_id(CONVO, "npc_failure_" + questNum);

            String failSafe = getString(failMessage);

            if (failSafe == null || failSafe.equals("")) {
                failMessage = new string_id(CONVO, "npc_failure");
            }

            chat.chat(self, failMessage);
            missionFailure(self, speaker, questNum);
            return SCRIPT_OVERRIDE;
        }
        // This is where the NPC can't find a good location to spawn an NPC or an NPC wasn't able to spawn because
        // of a typo or some other problem.  This is the graceful conclusion of such a problem.
        else if (hasObjVar(speaker, questID + ".noloc")) {
            chat.chat(self, new string_id(CONVO, "npc_noloc_" + questNum));
            resetPlayer(self, speaker, questNum);
            return SCRIPT_OVERRIDE;
        }
        // You've finished this guys mission and you're back for your reward(s).
        else if (hasObjVar(speaker, questID + ".done")) {
            if (questType.equals("fetch") || questType.equals("retrieve")) {
                obj_id playerInv = utils.getInventoryContainer(speaker);
                if (checkForItem(playerInv, speaker, questNum)) {
                    chat.chat(self, new string_id(CONVO, "npc_reward_" + questNum));
                    destroyObject(getObjIdObjVar(speaker, questID + ".deliver"));
                    giveReward(self, speaker, questNum);
                    return SCRIPT_OVERRIDE;
                } else {
                    String notit = "notit_" + questNum;
                    String response1 = "player_reset_" + questNum;
                    String response2 = "player_sorry_" + questNum;

                    string_id greeting = new string_id(CONVO, notit);

                    string_id[] response = new string_id[2];
                    response[0] = new string_id(CONVO, response1);

                    String answer = getString(response[0]);

                    if (answer == null || answer.equals("")) {
                        response1 = "player_reset";
                        response[0] = new string_id(CONVO, response1);
                    }

                    response[1] = new string_id(CONVO, response2);

                    if (answer == null || answer.equals("")) {
                        response2 = "player_sorry";
                        response[1] = new string_id(CONVO, response2);
                    }

                    npcStartConversation(speaker, self, "questConvo", greeting, response);

                    return SCRIPT_CONTINUE;
                }
            } else if (questType.equals("escort") || questType.equals("arrest") || questType.equals("rescue")) {
                obj_id[] creatures = getObjectsInRange(getLocation(self), 20);
                obj_id theVip = getObjIdObjVar(speaker, questID + ".vip");

                for (obj_id creature : creatures) {
                    if (creature != null && theVip == creature) {
                        string_id reward = new string_id(CONVO, "npc_reward_" + questNum);
                        chat.chat(self, reward);
                        giveReward(self, speaker, questNum);
                        return SCRIPT_CONTINUE;
                    }
                }

                chat.chat(self, new string_id(CONVO, "npc_failure_" + questNum));
                missionFailure(self, speaker, questNum);
                return SCRIPT_CONTINUE;
            } else {
                chat.chat(self, new string_id(CONVO, "npc_reward_" + questNum));
                giveReward(self, speaker, questNum);
                return SCRIPT_CONTINUE;
            }
        }
        //Assuming you had none of the above objvars you may be on a quest for someone else.  This is where
        //we get slick and let you quit out other quests from any quest NPC (just in case).
        else if (isOnQuest(speaker)) {
            // Whenever an NPC gives a player a quest he puts his datatable on the PC until it's done
            // so that other NPC's can see it (like a brand) and say, "Hey you're busy."
            String player_quest_table = getStringObjVar(speaker, "quest_table");

            // This indicates an issue, but is here for safety valve purposes
            if (player_quest_table == null) {
                player_quest_table = "empty";
            }

            // If the players table matches the NPC's table, then he's on a mission for this NPC, but doesn't
            // have any objvars.  This indicates that he's in the middle of a quest and the NPC gives him the
            // option to quit out of the quest or continue on.
            if (player_quest_table.equals(datatable)) {
                String npcGreet = "npc_work_" + questNum;
                String response1 = "player_reset_" + questNum;
                String response2 = "player_sorry_" + questNum;

                string_id greeting = new string_id(CONVO, npcGreet);

                string_id[] response = new string_id[2];
                response[0] = new string_id(CONVO, response1);

                String answer = getString(response[0]);

                if (answer == null || answer.equals("")) {
                    response1 = "player_reset";
                    response[0] = new string_id(CONVO, response1);
                }
                response[1] = new string_id(CONVO, response2);

                if (answer == null || answer.equals("")) {
                    response2 = "player_sorry";
                    response[1] = new string_id(CONVO, response2);
                }
                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            // Otherwise the player may want to quit out of somebody else's quests to take this guy's quest
            else {
                String npcGreet = "quit_quest";
                String response1 = "player_quit";
                String response2 = "player_continue";

                string_id greeting = new string_id("static_npc/default_dialog", npcGreet);

                string_id[] response = new string_id[2];
                response[0] = new string_id("static_npc/default_dialog", response1);
                response[1] = new string_id("static_npc/default_dialog", response2);

                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }
            return SCRIPT_CONTINUE;
        } else if (dataTableHasColumn(datatable, "gating_level_amt")) {
            int playerLevel = getLevel(speaker);
            int npcLevel = dataTableGetInt(datatable, questNum, "gating_level_amt");

            if (playerLevel < npcLevel) {
                String tooWeak = "too_weak";
                string_id greeting = new string_id(ALT_CONVO, tooWeak);
                chat.chat(self, greeting);
            } else if (playerLevel > npcLevel + 5) {
                String tooStrong = "too_strong";
                String response1 = "its_ok";

                string_id greeting = new string_id(ALT_CONVO, tooStrong);

                string_id[] response = new string_id[1];
                response[0] = new string_id(ALT_CONVO, response1);

                npcStartConversation(speaker, self, "questConvo", greeting, response);
            } else {
                String npcGreet = "npc_1_" + questNum;
                String response1 = "player_1_" + questNum;
                String response2 = "player_2_" + questNum;
                String response3 = "player_3_" + questNum;

                string_id greeting = new string_id(CONVO, npcGreet);

                string_id[] response = new string_id[3];
                response[0] = new string_id(CONVO, response1);
                response[1] = new string_id(CONVO, response2);
                response[2] = new string_id(CONVO, response3);

                npcStartConversation(speaker, self, "questConvo", greeting, response);
            }

        } else if (convoType.equals("terse")) {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;

            string_id greeting = new string_id(CONVO, npcGreet);

            string_id[] response = new string_id[2];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);

            npcStartConversation(speaker, self, "questConvo", greeting, response);
        } else if (convoType.equals("long")) {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;
            String response4 = "player_4_" + questNum;

            string_id greeting = new string_id(CONVO, npcGreet);

            string_id[] response = new string_id[4];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            response[3] = new string_id(CONVO, response4);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        } else if (convoType.equals("verbose")) {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;
            String response4 = "player_4_" + questNum;
            String response5 = "player_5_" + questNum;

            string_id greeting = new string_id(CONVO, npcGreet);

            string_id[] response = new string_id[5];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);
            response[3] = new string_id(CONVO, response4);
            response[4] = new string_id(CONVO, response5);
            npcStartConversation(speaker, self, "questConvo", greeting, response);
        } else {
            String npcGreet = "npc_1_" + questNum;
            String response1 = "player_1_" + questNum;
            String response2 = "player_2_" + questNum;
            String response3 = "player_3_" + questNum;

            string_id greeting = new string_id(CONVO, npcGreet);

            string_id[] response = new string_id[3];
            response[0] = new string_id(CONVO, response1);
            response[1] = new string_id(CONVO, response2);
            response[2] = new string_id(CONVO, response3);

            npcStartConversation(speaker, self, "questConvo", greeting, response);
        }
        return SCRIPT_CONTINUE;
    }

    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException {
        String datatable = getStringObjVar(self, "quest_table");
        datatable = "datatables/spawning/static_npc/" + datatable + ".iff";
        String gatingString = dataTableGetString(datatable, 1, "overall_objvar");

        int gating = getIntObjVar(player, gatingString);
        int questNum = gating;
        String type = dataTableGetString(datatable, questNum, "quest_type");
        String CONVO = dataTableGetString(datatable, questNum, "convo");
        String ALT_CONVO = "static_npc/default_dialog";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");

        location home = getLocation(getTopMostContainer(self));

        String response1 = "player_1_" + questNum;
        String response2 = "player_2_" + questNum;
        String response3 = "player_3_" + questNum;
        String response4 = "player_4_" + questNum;
        String response5 = "player_5_" + questNum;
        String response6 = "player_sorry_" + questNum;
        String response7 = "player_reset_" + questNum;
        String response8 = "player_quit";
        String response9 = "player_continue";
        String response10 = "player_sorry";
        String response11 = "player_reset";
        String response12 = "its_ok";

        String playerScript = dataTableGetString(datatable, questNum, "player_script");

        if (response.getAsciiId().equals(response1)) //
        {
            int xCoord = dataTableGetInt(datatable, questNum, "quest_x_loc");
            if (xCoord != 0) {
                int yCoord = dataTableGetInt(datatable, questNum, "quest_y_loc");
                int zCoord = dataTableGetInt(datatable, questNum, "quest_z_loc");
                String planet = dataTableGetString(datatable, questNum, "quest_planet_loc");

                location questLoc = new location(xCoord, yCoord, zCoord, planet, null);

                string_id message = new string_id(CONVO, "npc_2_" + questNum);
                npcSpeak(player, message);
                npcEndConversation(player);

                if (questID != null && !questID.equals("")) {
                    setObjVar(player, questID + ".questLoc", questLoc);
                    setObjVar(player, questID + ".questNum", questNum);
                    setObjVar(player, questID + ".home", home);
                    setObjVar(player, "quest_table", datatable);
                    attachScript(player, playerScript);
                } else {
                    debugSpeakMsg(self, "Sorry, I can't give you a job right now...");
                    return SCRIPT_OVERRIDE;
                }

                if (type.equals("smuggle") || type.equals("deliver")) {
                    obj_id playerInv = utils.getInventoryContainer(player);
                    String itemToDeliver = dataTableGetString(datatable, questNum, "deliver_object");
                    obj_id cargo = createObjectOverloaded(itemToDeliver, playerInv);
                    if (!isIdValid(cargo)) {
                        destroyObject(cargo);
                        npcSpeak(player, new string_id("generic_convo", "inv_full"));
                        npcEndConversation(player);
                        resetPlayer(self, player, questNum);
                        return SCRIPT_OVERRIDE;
                    }
                }
            } else {
                location target = quests.getThemeParkLocation(self);
                if (target == null) {
                    String noLoc = "npc_noloc_" + questNum;
                    chat.chat(self, new string_id(CONVO, noLoc));
                    npcEndConversation(player);
                    return SCRIPT_CONTINUE;
                } else {
                    if (questID != null && !questID.equals("")) {
                        setObjVar(player, questID + ".questLoc", target);
                        setObjVar(player, questID + ".questNum", questNum);
                        setObjVar(player, questID + ".home", home);
                        setObjVar(player, "quest_table", datatable);
                        attachScript(player, playerScript);
                    } else {
                        debugSpeakMsg(self, "Sorry, I can't give you a job right now...");
                        return SCRIPT_OVERRIDE;
                    }

                    if (type.equals("smuggle") || type.equals("deliver")) {
                        obj_id playerInv = utils.getInventoryContainer(player);
                        String itemToDeliver = dataTableGetString(datatable, questNum, "deliver_object");
                        obj_id cargo = createObjectOverloaded(itemToDeliver, playerInv);
                        if (!isIdValid(cargo)) {
                            destroyObject(cargo);
                            npcSpeak(player, new string_id("generic_convo", "inv_full"));
                            npcEndConversation(player);
                            resetPlayer(self, player, questNum);
                            return SCRIPT_OVERRIDE;
                        }
                    }
                }
                String npcAnswer1 = "npc_2_" + questNum;
                string_id message = new string_id(CONVO, npcAnswer1);
                npcSpeak(player, message);
                npcEndConversation(player);
            }
            return SCRIPT_CONTINUE;
        }

        if (response.getAsciiId().equals(response2)) //
        {
            String npcAnswer2 = "npc_3_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer2);//
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response3)) {
            npcRemoveConversationResponse(player, new string_id(CONVO, response3)); //
            String npcAnswer3 = "npc_4_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer3);//
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response4)) {
            npcRemoveConversationResponse(player, new string_id(CONVO, response4)); //
            String npcAnswer4 = "npc_5_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer4);//
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response5)) {
            npcRemoveConversationResponse(player, new string_id(CONVO, response5)); //
            String npcAnswer5 = "npc_6_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);//
            npcSpeak(player, message);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response6)) //
        {
            String npcAnswer4 = "npc_backtowork_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer4);//
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response7)) //
        {
            String npcAnswer5 = "npc_reset_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);//
            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);

            // take back virus if you're talking to Tekil Barje
            if(getCreatureName(self).startsWith("tekil_barje")) {
                if(utils.playerHasItemByTemplate(player, "object/tangible/mission/quest_item/tekil_barje_q1_needed.iff")) {
                    destroyObject(utils.getItemPlayerHasByTemplate(player, "object/tangible/mission/quest_item/tekil_barje_q1_needed.iff"));
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response8)) //
        {
            String npcAnswer6 = "npc_quit";
            string_id message = new string_id("static_npc/default_dialog", npcAnswer6);//
            npcSpeak(player, message);
            resetOtherQuest(self, player);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response9)) //
        {
            String npcAnswer7 = "npc_continue";
            string_id message = new string_id("static_npc/default_dialog", npcAnswer7);//
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response10)) //
        {
            String npcAnswer4 = "npc_backtowork_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer4);//
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response11)) //
        {
            String npcAnswer5 = "npc_reset_" + questNum;
            string_id message = new string_id(CONVO, npcAnswer5);//

            String answer = getString(message);

            if (answer == null || answer.equals("")) {
                npcAnswer5 = "npc_reset";
                message = new string_id(CONVO, npcAnswer5);
            }

            npcSpeak(player, message);
            resetPlayer(self, player, questNum);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if (response.getAsciiId().equals(response12)) //
        {
            String convoType = dataTableGetString(datatable, questNum, "quest_giver_convo");
            string_id message = new string_id(CONVO, "npc_1_" + questNum);
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(ALT_CONVO, response12));
            npcAddConversationResponse(player, new string_id(CONVO, "player_1_" + questNum));
            npcAddConversationResponse(player, new string_id(CONVO, "player_2_" + questNum));

            switch (convoType) {
                case "terse":
                    return SCRIPT_CONTINUE;
                case "long":
                    npcAddConversationResponse(player, new string_id(CONVO, "player_3_" + questNum));
                    npcAddConversationResponse(player, new string_id(CONVO, "player_4_" + questNum));
                    return SCRIPT_CONTINUE;
                case "verbose":
                    npcAddConversationResponse(player, new string_id(CONVO, "player_3_" + questNum));
                    npcAddConversationResponse(player, new string_id(CONVO, "player_4_" + questNum));
                    npcAddConversationResponse(player, new string_id(CONVO, "player_5_" + questNum));
                    return SCRIPT_CONTINUE;
                case "normal":
                    npcAddConversationResponse(player, new string_id(CONVO, "player_3_" + questNum));
                    return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }

    public void giveReward(obj_id self, obj_id player, int questNum) throws InterruptedException {
        String datatable = "datatables/spawning/static_npc/" + getStringObjVar(self, "quest_table") + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String gatingString = dataTableGetString(datatable, questNum, "overall_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");

        setObjVar(player, gatingString, getIntObjVar(player, gatingString) + 1);

        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("arrest") || type.equals("escort") || type.equals("rescue")) {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }

        obj_id playerInv = utils.getInventoryContainer(player);

        String reward;

        obj_id rewardObject = null;
        String objvar;
        int value;

        for(int x = 1; x <= 4; x++) {
            reward = dataTableGetString(datatable, questNum, "reward" + (x > 1 ? x : ""));
            if(reward != null && !reward.equals("") && !reward.equals("none")) {
                if (reward.startsWith("object/weapon/")) {
                    rewardObject = weapons.createWeapon(reward, playerInv, rand(0.5f, 0.85f));
                } else {
                    rewardObject = createObjectOverloaded(reward, playerInv);
                }
            }

            objvar = dataTableGetString(datatable, questNum, "reward_objvar");
            value = dataTableGetInt(datatable, questNum, "reward_objvar_value");

            if (objvar != null) {
                setObjVar(rewardObject, objvar, value);
            }
        }
        if(rewardObject != null) {
            sendSystemMessage(player, "A gift has been placed in your inventory for completing this task.", null);
        }

        int credits = dataTableGetInt(datatable, questNum, "credits");
        if (credits != 0) {
            money.bankTo(money.ACCT_JABBA, player, credits);
            sendSystemMessage(player, credits + " credits have been deposited in your bank account.", null);
        }

        String factionReward = dataTableGetString(datatable, questNum, "faction reward");
        int factionAmt;
        int maxFactionReward = 4;

        for(int x = 1; x <= maxFactionReward; x++) {
            if(!factionReward.equals("none")) {
                factionAmt = dataTableGetInt(datatable, questNum, "faction_reward" + (x > 1 ? x : "") + "_amount");
                if (factionAmt != 0) {
                    factions.addFactionStanding(player, factionReward, factionAmt);
                }
            }
            if(x < maxFactionReward)
                factionReward = dataTableGetString(datatable, questNum, "faction_reward" + (x + 1));
        }

        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null) {
            destroyWaypointInDatapad(waypoint, player);
        }

        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");

        if (hasScript(player, playerScript)) {
            detachScript(player, playerScript);
        }
    }

    public void resetPlayer(obj_id self, obj_id player, int questNum) {
        String datatable = "datatables/spawning/static_npc/" + getStringObjVar(self, "quest_table") + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");

        //DO SOMETHING WITH GATING HERE, IF IT'S 1 REMOVE IT, OTHER WISE MAKE SURE IT'S RIGHT

        obj_id waypoint = getObjIdObjVar(player, questID + ".waypoint");
        if (waypoint != null) {
            destroyWaypointInDatapad(waypoint, player);
        }

        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("escort") || type.equals("arrest")) {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }

        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");

        if (hasScript(player, playerScript)) {
            detachScript(player, playerScript);
        }
    }

    public boolean checkForItem(obj_id inv, obj_id player, int questNum) throws InterruptedException {
        String datatable = getStringObjVar(player, "quest_table");
        String giveMe = dataTableGetString(datatable, questNum, "retrieve_object");

        return utils.playerHasItemByTemplate(player, giveMe);
    }

    public boolean checkForGatingItem(obj_id inv, obj_id player, int questNum, String datatable) throws InterruptedException {
        return utils.playerHasItemByTemplate(player, dataTableGetString(datatable, questNum, "gating_object"));
    }

    public void missionFailure(obj_id self, obj_id player, int questNum) {
        String datatable = "datatables/spawning/static_npc/" + getStringObjVar(self, "quest_table") + ".iff";
        String questID = dataTableGetString(datatable, questNum, "temp_objvar");
        String playerScript = dataTableGetString(datatable, questNum, "player_script");

        obj_id waypoint = getObjIdObjVar(player, questID + ".waypointhome");
        if (waypoint != null) {
            destroyWaypointInDatapad(waypoint, player);
        }

        String type = dataTableGetString(datatable, questNum, "quest_type");
        if (type.equals("escort") || type.equals("arrest")) {
            obj_id vip = getObjIdObjVar(player, questID + ".vip");
            messageTo(vip, "stopFollowing", null, 0, true);
        }

        removeObjVar(player, questID);
        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");

        if (hasScript(player, playerScript)) {
            detachScript(player, playerScript);
        }
    }

    //const string FACETO_VOLUME_NAME = "faceToTriggerVolume";
    //createTriggerVolume( FACETO_VOLUME_NAME, 8.0f, true );
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) {
        if (!isPlayer(breacher) || !volumeName.equals(FACETO_VOLUME_NAME) || isInNpcConversation(self))
            return SCRIPT_CONTINUE;

        if (canSee(self, breacher))
            faceTo(self, breacher);

        return SCRIPT_CONTINUE;
    }

    public void resetOtherQuest(obj_id self, obj_id player) {
        String datatable = "datatables/npc/static_quest/all_quest_names.iff";
        String[] questIDs = dataTableGetStringColumnNoDefaults(datatable, "quest_ids");

        if (questIDs == null) {
            return;
        }

        for (String questID : questIDs) {
            if (hasObjVar(player, questID + ".waypoint")) {
                obj_id waypoint = getObjIdObjVar(player, questID + ".waypoint");

                if (waypoint != null) {
                    destroyWaypointInDatapad(waypoint, player);
                }

                obj_id waypointhome = getObjIdObjVar(player, questID + ".waypointhome");

                if (waypointhome != null) {
                    destroyWaypointInDatapad(waypointhome, player);
                }

            }

            if (hasObjVar(player, questID + ".escort")) {
                obj_id vip = getObjIdObjVar(player, questID + ".escort");
                messageTo(vip, "stopFollowing", null, 2, true);
            }

            if (hasObjVar(player, questID)) {
                removeObjVar(player, questID);
            }
        }

        String[] questScripts = dataTableGetStringColumnNoDefaults(datatable, "quest_scripts");

        if (questScripts == null) {
            return;
        }

        for (String questScript : questScripts) {
            if (hasScript(player, questScript)) {
                detachScript(player, questScript);
            }
        }

        removeObjVar(player, "quest_table");
        removeObjVar(player, "questNum");
    }

    public boolean isOnQuest(obj_id player) {
        return hasScript(player, "npc.static_quest.quest_player") ||
                hasScript(player, "npc.random_quest.quest_player") ||
                hasScript(player, "theme_park.imperial.quest_player") ||
                hasScript(player, "theme_park.rebel.quest_player") ||
                hasScript(player, "theme_park.jabba.quest_player") ||
                hasScript(player, "theme_park.jabba.quest_bldg") ||
                hasScript(player, "theme_park.jabba.quest_rantok") ||
                hasScript(player, "theme_park.rebel.quest_bldg") ||
                hasScript(player, "theme_park.imperial.quest_bldg");
    }
}

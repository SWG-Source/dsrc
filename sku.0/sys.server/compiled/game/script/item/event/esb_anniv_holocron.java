package script.item.event;

import script.*;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

import java.util.Arrays;

public class esb_anniv_holocron extends script.base_script {

    public esb_anniv_holocron()
    {
    }

    public static final String prompt_corellia = "While relaxing and enjoying the snow of Doaba Guerfel, a drunken fool insults you in a cantina. How do you react?";
    public static final String[] options_corellia = {
            "Strike down the fool. No insult or challenge will go unanswered.",
            "I push him out of the way and ignore him. There is no profit in killing drunken fools.",
            "I will walk away rather than letting the man's foolishness lead to unecessary violence."
    };
    public static final String prompt_dantooine = "While exploring an ancient Jedi temple, you discover a Sith Holocron. Do you embrace the Dark Side of the Force?";
    public static final String[] options_dantooine = {
            "I will resist the temptation of the dark side.",
            "I answer to neither the light nor the dark side. I make my own way in the galaxy.",
            "Yes, the dark side will make me stronger than before."
    };
    public static final String prompt_dathomir = "While waiting for a delayed shuttle flight, you discover that you and a group of other adventurers share the goal of taking down a rancor. What role do you seek in the group?";
    public static final String[] options_dathomir = {
            "The role of adviser and guide. Let others claim the greater glory as long as we all succeed.",
            "I prefer to work alone. The group will have to manage without me.",
            "The role of group leader. No-one will challenge my authority."
    };
    public static final String prompt_endor = "You find a rare Tessellated Arboreal Binjinphant while adventuring on Endor. In fear, the small creature strikes out at you, drawing blood. What do you do?";
    public static final String[] options_endor = {
            "Back away and leave it in peace.",
            "Angrily strike back and kill it.",
            "Capture it and sell it to the highest bidder."
    };
    public static final String prompt_lok = "Upon arriving at Nym's Stronghold, a guard gives you a copy of the city's rules. What do you do?";
    public static final String[] options_lok = {
            "Strike it aside. I dictate the rules and others should follow them without question.",
            "Ignore it, the only rules I recognize are the rules I set for myself.",
            "Humbly accept it. Rules are to be respected, though they should sometimes be broken for the greater good."
    };
    public static final String prompt_naboo = "Abandoned on a stool in a cantina you discover a datapad containing the location of the Emperor's Retreat. What do you do?";
    public static final String[] options_naboo = {
            "Track down and interrogate the owner of the datapad. Such carelessness is inexcusable.",
            "Turn it over to the Empire. Though it could fetch a high price, I am no fool.",
            "Get it into the hands of the Rebel Alliance. Used properly, this information could end the war."
    };
    public static final String prompt_rori = "During a brief stay in a simple village on Rori, you and the villagers come under attack by Borgle. What do you do?";
    public static final String[] options_rori = {
            "Destroy the creatures.",
            "Look to my own safety, unless the villagers are willing to pay me to defend them.",
            "Defend the villagers but look for a way to solve the issue without resorting to violence."
    };
    public static final String prompt_tatooine = "While travelling across the Dune Sea, you come across a group of bandits robbing an unarmed moisture farmer. What do you do?";
    public static final String[] options_tatooine = {
            "Assist whoever will pay the most.",
            "Confront the thieves, but give them a chance to surrender.",
            "Strike down all of the thieves, showing them less mercy than they showed the farmer."
    };
    public static final String prompt_talus = "Near the city of Dearic, you see a group of Rebels being placed under arrest by Imperial forces. What do you do?";
    public static final String[] options_talus = {
            "Nothing, they were arrested on my orders.",
            "Find a way to help the Rebels.",
            "Walk away. This is none of my concern."
    };
    public static final String prompt_yavin4 = "While exploring a partially demolished bio-lab, you rescue a wealthy trader from a rampaging mutation. In gratitude she offers you a tailor-made starship. Which do you choose.";
    public static final String[] options_yavin4 = {
            "I want something versitle. A ship that will allow me to operate effectively by my self.",
            "I am grateful for any reward offered, though none are needed.",
            "I would take the strongest and most powerful ship available."
    };
    public static final String[] allowed_planets = {
            "corellia",
            "dantooine",
            "dathomir",
            "endor",
            "lok",
            "naboo",
            "rori",
            "tatooine",
            "talus",
            "yavin4"
    };

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {
        if (!isIdValid(self) || !isIdValid(player)) {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self)) {
            return SCRIPT_CONTINUE;
        }
        if (!hasCompletedCollectionPage(player, "esb_anniversary_collection")) {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(self) || !isIdValid(player)) {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self)) {
            return SCRIPT_CONTINUE;
        }
        String planet = getCurrentSceneName();
        if (hasCompletedThisPlanet(self, planet)) {
            sendSystemMessageTestingOnly(player, "The Holocron of Destiny has nothing more for you while you are on this planet.");
            return SCRIPT_CONTINUE;
        }
        if(!Arrays.asList(allowed_planets).contains(planet)) {
            sendSystemMessageTestingOnly(player, "The Holocron of Destiny has no knowledge of this planet. You must continue your journey.");
            return SCRIPT_CONTINUE;
        }
        if (!hasCompletedCollectionPage(player, "esb_anniversary_collection")) {
            return SCRIPT_CONTINUE;
        }
        if(item == menu_info_types.ITEM_USE) {
            if (!hasObjVar(self, "esb_holocron_pc")) {
                setObjVar(self, "esb_holocron_pc", 10);
            }
            showHolocronPrompt(self, player, planet);
        }
        return SCRIPT_CONTINUE;
    }

    public boolean hasCompletedThisPlanet(obj_id self, String planet) throws InterruptedException {
        return hasObjVar(self, "esb_holocron_"+planet);
    }

    public void showHolocronPrompt(obj_id self, obj_id player, String planet) throws InterruptedException {
        int pid = sui.listbox(self, player, getPlanetPrompts().getString(planet), sui.OK_CANCEL, "Holocron of Destiny", getPlanetOptions().getStringArray(planet), "handleHolocronPrompt", true, false);
        sui.setPid(player, pid, "holocronOfDestiny");
    }

    public int handleHolocronPrompt(obj_id self, dictionary params) throws InterruptedException {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_OK) {
            int tct = getIntObjVar(self, "esb_holocron_pc");
            switch (idx) {
                case 0:
                    if(!hasObjVar(self, "esb_holocron_darkside_ct")) {
                        setObjVar(self, "esb_holocron_darkside_ct", 1);
                    } else {
                        setObjVar(self, "esb_holocron_darkside_ct", getIntObjVar(self, "esb_holocron_darkside_ct")+1);
                    }
                    setObjVar(self, "esb_holocron_pc", tct-1);
                    break;
                case 1:
                    if(!hasObjVar(self, "esb_holocron_neutral_ct")) {
                        setObjVar(self, "esb_holocron_neutral_ct", 1);
                    } else {
                        setObjVar(self, "esb_holocron_neutral_ct", getIntObjVar(self, "esb_holocron_neutral_ct")+1);
                    }
                    setObjVar(self, "esb_holocron_pc", tct-1);
                    break;
                case 2:
                    if(!hasObjVar(self, "esb_holocron_lightside_ct")) {
                        setObjVar(self, "esb_holocron_lightside_ct", 1);
                    } else {
                        setObjVar(self, "esb_holocron_lightside_ct", getIntObjVar(self, "esb_holocron_lightside_ct")+1);
                    }
                    setObjVar(self, "esb_holocron_pc", tct-1);
                    break;
            }
            setObjVar(self, "esb_holocron_"+getCurrentSceneName(), true);
            if(getIntObjVar(self, "esb_holocron_pc") < 1) {
                handleCompletedHolocron(self, player);
            } else {
                sendSystemMessageTestingOnly(player, "Your response to the Holocron of Destiny has been recorded.");
            }
        }
        return SCRIPT_CONTINUE;
    }

    public static void handleCompletedHolocron(obj_id self, obj_id player) throws InterruptedException {
        sendSystemMessageTestingOnly(player, "You have completed the Holocron of Destiny!");
        int[] scores = {
                getIntObjVar(self, "esb_holocron_neutral_ct"),
                getIntObjVar(self, "esb_holocron_darkside_ct"),
                getIntObjVar(self, "esb_holocron_lightside_ct")
        };
        int largest = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[largest]) largest = i;
        }
        obj_id item = obj_id.NULL_ID;
        switch (largest) {
            case 0:
                item = static_item.createNewItemFunction("item_esb_boba_fett_costume", utils.getInventoryContainer(player));
                break;
            case 1:
                item = static_item.createNewItemFunction("item_esb_darth_vader_costume", utils.getInventoryContainer(player));
                break;
            case 2:
                item = static_item.createNewItemFunction("item_esb_yoda_costume", utils.getInventoryContainer(player));
                break;
        }
        showLootBox(player, new obj_id[]{item});
        destroyObject(self);
    }

    public static dictionary getPlanetPrompts() throws InterruptedException {
        dictionary d = new dictionary();
        d.put("corellia", prompt_corellia);
        d.put("dantooine", prompt_dantooine);
        d.put("dathomir", prompt_dathomir);
        d.put("endor", prompt_endor);
        d.put("lok", prompt_lok);
        d.put("naboo", prompt_naboo);
        d.put("rori", prompt_rori);
        d.put("talus", prompt_talus);
        d.put("tatooine", prompt_tatooine);
        d.put("yavin4", prompt_yavin4);
        return d;
    }

    public static dictionary getPlanetOptions() throws InterruptedException {
        dictionary d = new dictionary();
        d.put("corellia", options_corellia);
        d.put("dantooine", options_dantooine);
        d.put("dathomir", options_dathomir);
        d.put("endor", options_endor);
        d.put("lok", options_lok);
        d.put("naboo", options_naboo);
        d.put("rori", options_rori);
        d.put("talus", options_talus);
        d.put("tatooine", options_tatooine);
        d.put("yavin4", options_yavin4);
        return d;
    }

}

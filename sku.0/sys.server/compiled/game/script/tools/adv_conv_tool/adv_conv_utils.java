package script.tools.adv_conv_tool;

import script.obj_id;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static script.base_class.doAnimationAction;

/**
 * This utils class will be referenced statically throughout the codebase and should not perform direct actions on any
 * of the actual objects within the game.
 */
public class adv_conv_utils {

    /**
     * This function will return a list of animation string that can be used or passed into the
     * following function "doAnimationAction(npc, "snap_finger1");", i.e., the "snap_finger1".
     */
    public static List<String> getAvailableAnimations() {
        String[] animations = {
                "2hot4u", "accept_affection", "action", "adjust",
                "airguitar", "alert", "angry", "apologize",
                "applause_excited", "applause_polite", "ashamed", "backhand",
                "backhand_threaten", "bang", "beckon", "belly_laugh",
                "blame", "bounce", "bow", "bow2",
                "bow3", "bow4", "bow5", "catchbreath",
                "celebrate", "celebrate1", "check_wrist_device", "cheer",
                "chicken", "chopped_liver", "clap_rousing", "claw",
                "clientAnimation", "conversation_1", "cough_heavy", "cough_polite",
                "cover_ears_mocking", "cover_eyes", "cover_mouth", "cuckoo",
                "curtsey", "curtsey1", "dismiss", "dream", "eat", "elbow", "embarrassed", "emt_nod_head_once",
                "emt_stand_confused", "expect_tip", "explain", "face_eye_roll", "face_innocent", "face_wink",
                "fakepunch", "flex_biceps", "flex3", "flipcoin", "forage", "force_choke",
                "gesticulate_widly", "gesticulate_wildly", "giveup", "goodbye", "greet", "hair_flip",
                "hands_above_head", "hands_behind_head", "handshake_tandem", "he_dies", "heavy_cough_vomit", "helpme",
                "hi5_tandem", "hold_nose", "hug_self", "hug_tandem", "huge", "huh", "implore", "kiss",
                "kiss_blow_kiss", "kisscheek", "laugh", "laugh_cackle", "laugh_pointing", "laugh_titter", "listen",
                "look_casual", "look_left", "loser", "manipulate_high", "manipulate_low", "manipulate_medium",
                "medium", "mistake", "mock", "nervous", "nod", "nod_head_multiple", "nod_head_once",
                "offer_affection", "paper", "pat", "pet_creature_medium", "pet_high", "petAnim", "point_accusingly",
                "point_away", "point_down", "point_forward", "point_left", "point_right", "point_to_self",
                "point_up", "poke", "pose_proudly", "pound_fist_chest", "pound_fist_palm", "refuse_offer_affection",
                "reload", "rofl", "rose", "rub_belly", "rub_chin_thoughtful", "rude", "salute", "salute1", "salute2",
                "scare", "scared", "scratch_head", "scream", "search", "shake_head_disgust", "shake_head_no",
                "shakefist", "shiver", "shoo", "shrug_hands", "shrug_shoulders", "shush", "sigh_deeply",
                "sit_trick_1", "sit_trick_2", "slit_throat", "slow_down", "slump_head", "smack_self", "small",
                "smell_air", "smell_armpit", "snap_finger1", "snap_finger2", "sneeze", "spit_hands", "squirm",
                "stamp_feet", "standing_placate", "standing_raise_fist", "std_manipulate_medium", "stop",
                "stretch", "strut", "survey", "sweat", "taken_aback", "tap_foot", "tap_head", "taunt2", "thank",
                "threaten", "threaten_combat", "throwat", "thumb_down", "thumb_up", "thumbs_down", "thumbs_up",
                "tiny", "tiphat", "trick_1", "trick_2", "trickName", "twitch", "udaman", "vocalize", "waft",
                "wave_finger_warning", "wave_hail", "wave_on_directing", "wave_on_dismissing", "wave1", "wave2",
                "weeping", "whisper", "worship", "wtf", "yawn", "yes"
        };

        return List.of(animations);
    }

    /**
     * Follow on function that will randomize the list of animations and return a random animation
     */
    public static String getRandomAnimationString() {
        List<String> list = getAvailableAnimations();
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        // Use ThreadLocalRandom for better performance and thread safety
        int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(randomIndex);
    }

    /**
     * Another utility function that call the doAnimation function for the object passed
     * and perform the random animation on that object.
     */
    public static void performRandomAnimation(obj_id npcOrPlayer) {
        doAnimationAction(npcOrPlayer, getRandomAnimationString());
    }
}

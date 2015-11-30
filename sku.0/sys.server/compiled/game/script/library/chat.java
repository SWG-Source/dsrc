package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_utils;

public class chat extends script.base_script
{
    public chat()
    {
    }
    public static final String MOOD_NONE = "none";
    public static final String MOOD_ABSENTMINDED = "absentminded";
    public static final String MOOD_AMAZED = "amazed";
    public static final String MOOD_AMUSED = "amused";
    public static final String MOOD_ANGRY = "angry";
    public static final String MOOD_APPROVING = "approving";
    public static final String MOOD_BITTER = "bitter";
    public static final String MOOD_BLOODTHIRSTY = "bloodthirsty";
    public static final String MOOD_BORED = "bored";
    public static final String MOOD_BRAVE = "brave";
    public static final String MOOD_CALLOUS = "callous";
    public static final String MOOD_CAREFUL = "careful";
    public static final String MOOD_CARELESS = "careless";
    public static final String MOOD_CASUAL = "casual";
    public static final String MOOD_CLINICAL = "clinical";
    public static final String MOOD_COCKY = "cocky";
    public static final String MOOD_COLD = "cold";
    public static final String MOOD_COMPASSIONATE = "compassionate";
    public static final String MOOD_CONDESCENDING = "condescending";
    public static final String MOOD_CONFIDENT = "confident";
    public static final String MOOD_CONFUSED = "confused";
    public static final String MOOD_CONTENT = "content";
    public static final String MOOD_COURTLY = "courtly";
    public static final String MOOD_COY = "coy";
    public static final String MOOD_CRUDE = "crude";
    public static final String MOOD_CRUEL = "cruel";
    public static final String MOOD_CURIOUS = "curious";
    public static final String MOOD_CYNICAL = "cynical";
    public static final String MOOD_DEFENSIVE = "defensive";
    public static final String MOOD_DEPRESSED = "depressed";
    public static final String MOOD_DEVIOUS = "devious";
    public static final String MOOD_DIMWITTED = "dimwitted";
    public static final String MOOD_DISAPPOINTED = "disappointed";
    public static final String MOOD_DISCREET = "discreet";
    public static final String MOOD_DISGRUNTLED = "disgruntled";
    public static final String MOOD_DISGUSTED = "disgusted";
    public static final String MOOD_DISMAYED = "dismayed";
    public static final String MOOD_DISORIENTED = "disoriented";
    public static final String MOOD_DISTRACTED = "distracted";
    public static final String MOOD_DOUBTFUL = "doubtful";
    public static final String MOOD_DRAMATIC = "dramatic";
    public static final String MOOD_DREAMY = "dreamy";
    public static final String MOOD_DRUNK = "drunk";
    public static final String MOOD_EARNEST = "earnest";
    public static final String MOOD_ECSTATIC = "ecstatic";
    public static final String MOOD_EMBARRASSED = "embarrassed";
    public static final String MOOD_EMPHATIC = "emphatic";
    public static final String MOOD_ENCOURAGING = "encouraging";
    public static final String MOOD_ENTHUSIASTIC = "enthusiastic";
    public static final String MOOD_EVIL = "evil";
    public static final String MOOD_EXASPERATED = "exasperated";
    public static final String MOOD_EXUBERANT = "exuberant";
    public static final String MOOD_FANATICAL = "fanatical";
    public static final String MOOD_FORGIVE = "forgive";
    public static final String MOOD_FRUSTRATED = "frustrated";
    public static final String MOOD_GUILTY = "guilty";
    public static final String MOOD_HAPPY = "happy";
    public static final String MOOD_HONEST = "honest";
    public static final String MOOD_HOPEFUL = "hopeful";
    public static final String MOOD_HOPELESS = "hopeless";
    public static final String MOOD_HUMBLE = "humble";
    public static final String MOOD_HYSTERICAL = "hysterical";
    public static final String MOOD_IMPLORING = "imploring";
    public static final String MOOD_INDIFFERENT = "indifferent";
    public static final String MOOD_INDIGNANT = "indignant";
    public static final String MOOD_INTERESTED = "interested";
    public static final String MOOD_JEALOUS = "jealous";
    public static final String MOOD_JOYFUL = "joyful";
    public static final String MOOD_LOFTY = "lofty";
    public static final String MOOD_LOUD = "loud";
    public static final String MOOD_LOVING = "loving";
    public static final String MOOD_LUSTFUL = "lustful";
    public static final String MOOD_MEAN = "mean";
    public static final String MOOD_MISCHIEVOUS = "mischievous";
    public static final String MOOD_NERVOUS = "nervous";
    public static final String MOOD_NEUTRAL = "neutral";
    public static final String MOOD_OFFENDED = "offended";
    public static final String MOOD_OPTIMISTIC = "optimistic";
    public static final String MOOD_PEDANTIC = "pedantic";
    public static final String MOOD_PESSIMISTIC = "pessimistic";
    public static final String MOOD_PETULANT = "petulant";
    public static final String MOOD_PHILOSOPHICAL = "philosophical";
    public static final String MOOD_PITYING = "pitying";
    public static final String MOOD_PLAYFUL = "playful";
    public static final String MOOD_POLITE = "polite";
    public static final String MOOD_POMPOUS = "pompous";
    public static final String MOOD_PROUD = "proud";
    public static final String MOOD_PROVOCATIVE = "provocative";
    public static final String MOOD_PUZZLED = "puzzled";
    public static final String MOOD_REGRETFUL = "regretful";
    public static final String MOOD_RELIEVED = "relieved";
    public static final String MOOD_RELUCTANT = "reluctant";
    public static final String MOOD_RESIGNED = "resigned";
    public static final String MOOD_RESPECTFUL = "respectful";
    public static final String MOOD_ROMANTIC = "romantic";
    public static final String MOOD_RUDE = "rude";
    public static final String MOOD_SAD = "sad";
    public static final String MOOD_SARCASTIC = "sarcastic";
    public static final String MOOD_SCARED = "scared";
    public static final String MOOD_SCOLDING = "scolding";
    public static final String MOOD_SCORNFUL = "scornful";
    public static final String MOOD_SERIOUS = "serious";
    public static final String MOOD_SHAMELESS = "shameless";
    public static final String MOOD_SHOCKED = "shocked";
    public static final String MOOD_SHY = "shy";
    public static final String MOOD_SINCERE = "sincere";
    public static final String MOOD_SLEEPY = "sleepy";
    public static final String MOOD_SLY = "sly";
    public static final String MOOD_SMUG = "smug";
    public static final String MOOD_SNOBBY = "snobby";
    public static final String MOOD_SORRY = "sorry";
    public static final String MOOD_SPITEFUL = "spiteful";
    public static final String MOOD_STUBBORN = "stubborn";
    public static final String MOOD_SULLEN = "sullen";
    public static final String MOOD_SUSPICIOUS = "suspicious";
    public static final String MOOD_TAUNTING = "taunting";
    public static final String MOOD_TERRIFIED = "terrified";
    public static final String MOOD_THANKFUL = "thankful";
    public static final String MOOD_THOUGHTFUL = "thoughtful";
    public static final String MOOD_TOLERANT = "tolerant";
    public static final String MOOD_UNCERTAIN = "uncertain";
    public static final String MOOD_UNHAPPY = "unhappy";
    public static final String MOOD_UNWILLING = "unwilling";
    public static final String MOOD_WARM = "warm";
    public static final String MOOD_WHINY = "whiny";
    public static final String MOOD_WICKED = "wicked";
    public static final String MOOD_WISTFUL = "wistful";
    public static final String MOOD_WORRIED = "worried";
    public static final String MOOD_TIRED = "tired";
    public static final String MOOD_EXHAUSTED = "exhausted";
    public static final String MOOD_FRIENDLY = "friendly";
    public static final String MOOD_TIMID = "timid";
    public static final String MOOD_LAZY = "lazy";
    public static final String MOOD_SURPRISED = "surprised";
    public static final String MOOD_INNOCENT = "innocent";
    public static final String MOOD_WISE = "wise";
    public static final String MOOD_YOUTHFUL = "youthful";
    public static final String MOOD_ADVENTUROUS = "adventurous";
    public static final String MOOD_ANNOYED = "annoyed";
    public static final String MOOD_PERTURBED = "perturbed";
    public static final String MOOD_SEDATE = "sedate";
    public static final String MOOD_CALM = "calm";
    public static final String MOOD_SUFFERING = "suffering";
    public static final String MOOD_HUNGRY = "hungry";
    public static final String MOOD_THIRSTY = "thirsty";
    public static final String MOOD_ALERT = "alert";
    public static final String MOOD_SHIFTY = "shifty";
    public static final String MOOD_RELAXED = "relaxed";
    public static final String MOOD_CROTCHETY = "crotchety";
    public static final String MOOD_SURLY = "surly";
    public static final String MOOD_PAINFUL = "painful";
    public static final String MOOD_WOUNDED = "wounded";
    public static final String MOOD_BUBBLY = "bubbly";
    public static final String MOOD_HEROIC = "heroic";
    public static final String MOOD_QUIET = "quiet";
    public static final String MOOD_REMORSEFUL = "remorseful";
    public static final String MOOD_GRUMPY = "grumpy";
    public static final String MOOD_LOGICAL = "logical";
    public static final String MOOD_EMOTIONAL = "emotional";
    public static final String MOOD_TROUBLED = "troubled";
    public static final String MOOD_PANICKED = "panicked";
    public static final String MOOD_NICE = "nice";
    public static final String MOOD_CHEERFUL = "cheerful";
    public static final String MOOD_EMOTIONLESS = "emotionless";
    public static final String MOOD_GLOOMY = "gloomy";
    public static final String MOOD_AMBIVALENT = "ambivalent";
    public static final String MOOD_ENVIOUS = "envious";
    public static final String MOOD_VENGEFUL = "vengeful";
    public static final String MOOD_FEARFUL = "fearful";
    public static final String MOOD_ENRAGED = "enraged";
    public static final String MOOD_SHEEPISH = "sheepish";
    public static final String MOOD_OBNOXIOUS = "obnoxious";
    public static final String MOOD_FASTIDIOUS = "fastidious";
    public static final String MOOD_SQUEAMISH = "squeamish";
    public static final String MOOD_DAINTY = "dainty";
    public static final String MOOD_DIGNIFIED = "dignified";
    public static final String MOOD_HAUGHTY = "haughty";
    public static final String MOOD_OBSCURE = "obscure";
    public static final String MOOD_GOOFY = "goofy";
    public static final String MOOD_SILLY = "silly";
    public static final String MOOD_DISDAINFUL = "disdainful";
    public static final String MOOD_CONTEMPTUOUS = "contemptuous";
    public static final String MOOD_DIPLOMATIC = "diplomatic";
    public static final String MOOD_WARY = "wary";
    public static final String MOOD_MALEVOLENT = "malevolent";
    public static final String MOOD_HURRIED = "hurried";
    public static final String MOOD_PATIENT = "patient";
    public static final String MOOD_FIRM = "firm";
    public static final String[] BADMOODS = 
    {
        MOOD_ANGRY,
        MOOD_BITTER,
        MOOD_BLOODTHIRSTY,
        MOOD_CALLOUS,
        MOOD_COLD,
        MOOD_CONDESCENDING,
        MOOD_CRUDE,
        MOOD_CRUEL,
        MOOD_CYNICAL,
        MOOD_DEFENSIVE,
        MOOD_DEPRESSED,
        MOOD_DEVIOUS,
        MOOD_DISAPPOINTED,
        MOOD_DISGRUNTLED,
        MOOD_DISGUSTED,
        MOOD_DISMAYED,
        MOOD_DOUBTFUL,
        MOOD_EVIL,
        MOOD_EXASPERATED,
        MOOD_FRUSTRATED,
        MOOD_GUILTY,
        MOOD_HOPELESS,
        MOOD_INDIFFERENT,
        MOOD_INDIGNANT,
        MOOD_MEAN,
        MOOD_MISCHIEVOUS,
        MOOD_PESSIMISTIC,
        MOOD_PETULANT,
        MOOD_POMPOUS,
        MOOD_RESIGNED,
        MOOD_RUDE,
        MOOD_SAD,
        MOOD_SCOLDING,
        MOOD_SCORNFUL,
        MOOD_SMUG,
        MOOD_SNOBBY,
        MOOD_SPITEFUL,
        MOOD_SULLEN,
        MOOD_UNHAPPY,
        MOOD_UNWILLING,
        MOOD_WICKED,
        MOOD_WORRIED,
        MOOD_TIRED,
        MOOD_EXHAUSTED,
        MOOD_ANNOYED,
        MOOD_PERTURBED,
        MOOD_SUFFERING,
        MOOD_CROTCHETY,
        MOOD_SURLY,
        MOOD_GRUMPY,
        MOOD_EMOTIONLESS,
        MOOD_GLOOMY,
        MOOD_AMBIVALENT,
        MOOD_OBNOXIOUS,
        MOOD_DISDAINFUL,
        MOOD_CONTEMPTUOUS,
        MOOD_WARY,
        MOOD_MALEVOLENT
    };
    public static final String[] GOODMOODS = 
    {
        MOOD_AMUSED,
        MOOD_APPROVING,
        MOOD_BRAVE,
        MOOD_CASUAL,
        MOOD_COMPASSIONATE,
        MOOD_CONTENT,
        MOOD_DISCREET,
        MOOD_DREAMY,
        MOOD_EARNEST,
        MOOD_ECSTATIC,
        MOOD_ENCOURAGING,
        MOOD_ENTHUSIASTIC,
        MOOD_EXUBERANT,
        MOOD_HAPPY,
        MOOD_INTERESTED,
        MOOD_JOYFUL,
        MOOD_LOFTY,
        MOOD_LOVING,
        MOOD_OPTIMISTIC,
        MOOD_PLAYFUL,
        MOOD_POLITE,
        MOOD_RELIEVED,
        MOOD_RESPECTFUL,
        MOOD_SINCERE,
        MOOD_THOUGHTFUL,
        MOOD_TOLERANT,
        MOOD_WARM,
        MOOD_FRIENDLY,
        MOOD_YOUTHFUL,
        MOOD_ADVENTUROUS,
        MOOD_CALM,
        MOOD_ALERT,
        MOOD_BUBBLY,
        MOOD_HEROIC,
        MOOD_NICE,
        MOOD_CHEERFUL,
        MOOD_DIGNIFIED,
        MOOD_PATIENT
    };
    public static final String[] ANGRYMOODS = 
    {
        MOOD_ANGRY,
        MOOD_BITTER,
        MOOD_BLOODTHIRSTY,
        MOOD_CALLOUS,
        MOOD_COCKY,
        MOOD_COLD,
        MOOD_CRUEL,
        MOOD_EVIL,
        MOOD_FANATICAL,
        MOOD_MEAN,
        MOOD_PETULANT,
        MOOD_RUDE,
        MOOD_SCORNFUL,
        MOOD_TAUNTING,
        MOOD_WICKED,
        MOOD_PERTURBED,
        MOOD_VENGEFUL,
        MOOD_ENRAGED,
        MOOD_OBNOXIOUS,
        MOOD_CONTEMPTUOUS,
        MOOD_MALEVOLENT
    };
    public static final String[] NEUTRALMOODS = 
    {
        MOOD_NONE,
        MOOD_BORED,
        MOOD_CASUAL,
        MOOD_CONTENT,
        MOOD_INDIFFERENT,
        MOOD_NEUTRAL,
        MOOD_SERIOUS,
        MOOD_THOUGHTFUL,
        MOOD_INNOCENT,
        MOOD_SEDATE,
        MOOD_CALM,
        MOOD_RELAXED,
        MOOD_QUIET,
        MOOD_EMOTIONLESS,
        MOOD_FIRM
    };
    public static final String[] ALLMOODS = 
    {
        MOOD_NONE,
        MOOD_ABSENTMINDED,
        MOOD_AMAZED,
        MOOD_AMUSED,
        MOOD_ANGRY,
        MOOD_APPROVING,
        MOOD_BITTER,
        MOOD_BLOODTHIRSTY,
        MOOD_BORED,
        MOOD_BRAVE,
        MOOD_CALLOUS,
        MOOD_CAREFUL,
        MOOD_CARELESS,
        MOOD_CASUAL,
        MOOD_CLINICAL,
        MOOD_COCKY,
        MOOD_COLD,
        MOOD_COMPASSIONATE,
        MOOD_CONDESCENDING,
        MOOD_CONFIDENT,
        MOOD_CONFUSED,
        MOOD_CONTENT,
        MOOD_COURTLY,
        MOOD_COY,
        MOOD_CRUDE,
        MOOD_CRUEL,
        MOOD_CURIOUS,
        MOOD_CYNICAL,
        MOOD_DEFENSIVE,
        MOOD_DEPRESSED,
        MOOD_DEVIOUS,
        MOOD_DIMWITTED,
        MOOD_DISAPPOINTED,
        MOOD_DISCREET,
        MOOD_DISGRUNTLED,
        MOOD_DISGUSTED,
        MOOD_DISMAYED,
        MOOD_DISORIENTED,
        MOOD_DISTRACTED,
        MOOD_DOUBTFUL,
        MOOD_DRAMATIC,
        MOOD_DREAMY,
        MOOD_DRUNK,
        MOOD_EARNEST,
        MOOD_ECSTATIC,
        MOOD_EMBARRASSED,
        MOOD_EMPHATIC,
        MOOD_ENCOURAGING,
        MOOD_ENTHUSIASTIC,
        MOOD_EVIL,
        MOOD_EXASPERATED,
        MOOD_EXUBERANT,
        MOOD_FANATICAL,
        MOOD_FORGIVE,
        MOOD_FRUSTRATED,
        MOOD_GUILTY,
        MOOD_HAPPY,
        MOOD_HONEST,
        MOOD_HOPEFUL,
        MOOD_HOPELESS,
        MOOD_HUMBLE,
        MOOD_HYSTERICAL,
        MOOD_IMPLORING,
        MOOD_INDIFFERENT,
        MOOD_INDIGNANT,
        MOOD_INTERESTED,
        MOOD_JEALOUS,
        MOOD_JOYFUL,
        MOOD_LOFTY,
        MOOD_LOUD,
        MOOD_LOVING,
        MOOD_LUSTFUL,
        MOOD_MEAN,
        MOOD_MISCHIEVOUS,
        MOOD_NERVOUS,
        MOOD_NEUTRAL,
        MOOD_OFFENDED,
        MOOD_OPTIMISTIC,
        MOOD_PEDANTIC,
        MOOD_PESSIMISTIC,
        MOOD_PETULANT,
        MOOD_PHILOSOPHICAL,
        MOOD_PITYING,
        MOOD_PLAYFUL,
        MOOD_POLITE,
        MOOD_POMPOUS,
        MOOD_PROUD,
        MOOD_PROVOCATIVE,
        MOOD_PUZZLED,
        MOOD_REGRETFUL,
        MOOD_RELIEVED,
        MOOD_RELUCTANT,
        MOOD_RESIGNED,
        MOOD_RESPECTFUL,
        MOOD_ROMANTIC,
        MOOD_RUDE,
        MOOD_SAD,
        MOOD_SARCASTIC,
        MOOD_SCARED,
        MOOD_SCOLDING,
        MOOD_SCORNFUL,
        MOOD_SERIOUS,
        MOOD_SHAMELESS,
        MOOD_SHOCKED,
        MOOD_SHY,
        MOOD_SINCERE,
        MOOD_SLEEPY,
        MOOD_SLY,
        MOOD_SMUG,
        MOOD_SNOBBY,
        MOOD_SORRY,
        MOOD_SPITEFUL,
        MOOD_STUBBORN,
        MOOD_SULLEN,
        MOOD_SUSPICIOUS,
        MOOD_TAUNTING,
        MOOD_TERRIFIED,
        MOOD_THANKFUL,
        MOOD_THOUGHTFUL,
        MOOD_TOLERANT,
        MOOD_UNCERTAIN,
        MOOD_UNHAPPY,
        MOOD_UNWILLING,
        MOOD_WARM,
        MOOD_WHINY,
        MOOD_WICKED,
        MOOD_WISTFUL,
        MOOD_WORRIED,
        MOOD_TIRED,
        MOOD_EXHAUSTED,
        MOOD_FRIENDLY,
        MOOD_TIMID,
        MOOD_LAZY,
        MOOD_SURPRISED,
        MOOD_INNOCENT,
        MOOD_WISE,
        MOOD_YOUTHFUL,
        MOOD_ADVENTUROUS,
        MOOD_ANNOYED,
        MOOD_PERTURBED,
        MOOD_SEDATE,
        MOOD_CALM,
        MOOD_SUFFERING,
        MOOD_HUNGRY,
        MOOD_THIRSTY,
        MOOD_ALERT,
        MOOD_SHIFTY,
        MOOD_RELAXED,
        MOOD_CROTCHETY,
        MOOD_SURLY,
        MOOD_PAINFUL,
        MOOD_WOUNDED,
        MOOD_BUBBLY,
        MOOD_HEROIC,
        MOOD_QUIET,
        MOOD_REMORSEFUL,
        MOOD_GRUMPY,
        MOOD_LOGICAL,
        MOOD_EMOTIONAL,
        MOOD_TROUBLED,
        MOOD_PANICKED,
        MOOD_NICE,
        MOOD_CHEERFUL,
        MOOD_EMOTIONLESS,
        MOOD_GLOOMY,
        MOOD_AMBIVALENT,
        MOOD_ENVIOUS,
        MOOD_VENGEFUL,
        MOOD_FEARFUL,
        MOOD_ENRAGED,
        MOOD_SHEEPISH,
        MOOD_OBNOXIOUS,
        MOOD_FASTIDIOUS,
        MOOD_SQUEAMISH,
        MOOD_DAINTY,
        MOOD_DIGNIFIED,
        MOOD_HAUGHTY,
        MOOD_OBSCURE,
        MOOD_GOOFY,
        MOOD_SILLY,
        MOOD_DISDAINFUL,
        MOOD_CONTEMPTUOUS,
        MOOD_DIPLOMATIC,
        MOOD_WARY,
        MOOD_MALEVOLENT,
        MOOD_HURRIED,
        MOOD_PATIENT,
        MOOD_FIRM
    };
    public static final String CHAT_SAY = "say";
    public static final String CHAT_INTONE = "intone";
    public static final String CHAT_MUMBLE = "mumble";
    public static final String CHAT_BABBLE = "babble";
    public static final String CHAT_ASSERT = "assert";
    public static final String CHAT_ADD = "add";
    public static final String CHAT_ADMIT = "admit";
    public static final String CHAT_ANSWER = "answer";
    public static final String CHAT_CAROL = "carol";
    public static final String CHAT_CHATTER = "chatter";
    public static final String CHAT_COMPLAIN = "complain";
    public static final String CHAT_COO = "coo";
    public static final String CHAT_DECLARE = "declare";
    public static final String CHAT_DEMAND = "demand";
    public static final String CHAT_DESCRIBE = "describe";
    public static final String CHAT_DRAWL = "drawl";
    public static final String CHAT_DRONE = "drone";
    public static final String CHAT_EXCLAIM = "exclaim";
    public static final String CHAT_DECREE = "decree";
    public static final String CHAT_EXPLAIN = "explain";
    public static final String CHAT_HOWL = "howl";
    public static final String CHAT_INDICATE = "indicate";
    public static final String CHAT_MOAN = "moan";
    public static final String CHAT_HUFF = "huff";
    public static final String CHAT_PROCLAIM = "proclaim";
    public static final String CHAT_NOTE = "note";
    public static final String CHAT_PLEAD = "plead";
    public static final String CHAT_BEG = "beg";
    public static final String CHAT_PRATTLE = "prattle";
    public static final String CHAT_TATTLE = "tattle";
    public static final String CHAT_QUOTE = "quote";
    public static final String CHAT_RAMBLE = "ramble";
    public static final String CHAT_DRIVEL = "drivel";
    public static final String CHAT_SING = "sing";
    public static final String CHAT_REQUEST = "request";
    public static final String CHAT_RETORT = "retort";
    public static final String CHAT_SCREAM = "scream";
    public static final String CHAT_STATE = "state";
    public static final String CHAT_STUTTER = "stutter";
    public static final String CHAT_THREATEN = "threaten";
    public static final String CHAT_UTTER = "utter";
    public static final String CHAT_WAIL = "wail";
    public static final String CHAT_WHINE = "whine";
    public static final String CHAT_PREACH = "preach";
    public static final String CHAT_ARGUE = "argue";
    public static final String CHAT_LISP = "lisp";
    public static final String CHAT_RECITE = "recite";
    public static final String CHAT_MOUTH = "mouth";
    public static final String CHAT_COMMAND = "command";
    public static final String CHAT_RAP = "rap";
    public static final String CHAT_PROPHESIZE = "prophesize";
    public static final String CHAT_GAB = "gab";
    public static final String CHAT_GOSSIP = "gossip";
    public static final String CHAT_PRONOUNCE = "pronounce";
    public static final String CHAT_LECTURE = "lecture";
    public static final String CHAT_BELLOW = "bellow";
    public static final String CHAT_EULOGIZE = "eulogize";
    public static final String CHAT_BLAB = "blab";
    public static final String CHAT_VENT = "vent";
    public static final String CHAT_DIVULGE = "divulge";
    public static final String CHAT_CONFIDE = "confide";
    public static final String CHAT_AVOW = "avow";
    public static final String CHAT_VOW = "vow";
    public static final String CHAT_CONFESS = "confess";
    public static final String CHAT_BLURT = "blurt";
    public static final String CHAT_INTERJECT = "interject";
    public static final String CHAT_RIDDLE = "riddle";
    public static final String CHAT_ANNOUNCE = "announce";
    public static final String CHAT_FORETELL = "foretell";
    public static final String CHAT_SLUR = "slur";
    public static final String CHAT_BLEAT = "bleat";
    public static final String CHAT_DISCLOSE = "disclose";
    public static final String CHAT_HYPOTHESIZE = "hypothesize";
    public static final String CHAT_SWEAR = "swear";
    public static final String CHAT_PROMISE = "promise";
    public static final String CHAT_PARROT = "parrot";
    public static final String CHAT_BRAG = "brag";
    public static final String CHAT_JAW = "jaw";
    public static final String CHAT_JABBER = "jabber";
    public static final String CHAT_CHAT = "chat";
    public static final String CHAT_YACK = "yack";
    public static final String CHAT_INQUIRE = "inquire";
    public static final String CHAT_INTERRUPT = "interrupt";
    public static final String CHAT_SUPPOSE = "suppose";
    public static final String CHAT_JEST = "jest";
    public static final String CHAT_YELP = "yelp";
    public static final String CHAT_SHRILL = "shrill";
    public static final String CHAT_BOAST = "boast";
    public static final String CHAT_CONCEDE = "concede";
    public static final String CHAT_CONCLUDE = "conclude";
    public static final String CHAT_SURMISE = "surmise";
    public static final String CHAT_THEORIZE = "theorize";
    public static final String CHAT_CHANT = "chant";
    public static final String CHAT_PROPOSE = "propose";
    public static final String CHAT_RESPOND = "respond";
    public static final String CHAT_DEBATE = "debate";
    public static final String CHAT_MUSE = "muse";
    public static final String CHAT_EMOTE = "emote";
    public static final String CHAT_WHISPER = "whisper";
    public static final String CHAT_YELL = "yell";
    public static final String CHAT_SHOUT = "shout";
    public static final String[] BADTYPES = 
    {
        CHAT_SAY,
        CHAT_INTONE,
        CHAT_COMPLAIN,
        CHAT_HUFF,
        CHAT_VENT,
        CHAT_BLURT,
        CHAT_SWEAR
    };
    public static final String[] GOODTYPES = 
    {
        CHAT_SAY,
        CHAT_ASSERT,
        CHAT_DECLARE,
        CHAT_EXCLAIM,
        CHAT_PRATTLE,
        CHAT_STATE,
        CHAT_UTTER,
        CHAT_MOUTH,
        CHAT_GAB,
        CHAT_GOSSIP,
        CHAT_BLAB,
        CHAT_ANNOUNCE,
        CHAT_JAW,
        CHAT_JABBER,
        CHAT_CHAT,
        CHAT_YACK
    };
    public static final String[] ANGRYTYPES = 
    {
        CHAT_SAY,
        CHAT_ASSERT,
        CHAT_DECLARE,
        CHAT_DEMAND,
        CHAT_EXCLAIM,
        CHAT_HOWL,
        CHAT_PROCLAIM,
        CHAT_SCREAM,
        CHAT_THREATEN,
        CHAT_COMMAND,
        CHAT_PRONOUNCE,
        CHAT_BELLOW,
        CHAT_VENT,
        CHAT_BLURT,
        CHAT_SWEAR,
        CHAT_YELP,
        CHAT_YELL,
        CHAT_SHOUT
    };
    public static final String[] NEUTRALTYPES = 
    {
        CHAT_SAY,
        CHAT_INTONE,
        CHAT_MUMBLE,
        CHAT_CHATTER,
        CHAT_DRONE,
        CHAT_INDICATE,
        CHAT_NOTE,
        CHAT_PRATTLE,
        CHAT_RAMBLE,
        CHAT_STATE,
        CHAT_UTTER,
        CHAT_GAB,
        CHAT_GOSSIP,
        CHAT_BLAB,
        CHAT_CHAT,
        CHAT_YACK
    };
    public static final String[] ALL_CHAT_TYPES = 
    {
        CHAT_SAY,
        CHAT_INTONE,
        CHAT_MUMBLE,
        CHAT_BABBLE,
        CHAT_ASSERT,
        CHAT_ADD,
        CHAT_ADMIT,
        CHAT_ANSWER,
        CHAT_CAROL,
        CHAT_CHATTER,
        CHAT_COMPLAIN,
        CHAT_COO,
        CHAT_DECLARE,
        CHAT_DEMAND,
        CHAT_DESCRIBE,
        CHAT_DRAWL,
        CHAT_DRONE,
        CHAT_EXCLAIM,
        CHAT_DECREE,
        CHAT_EXPLAIN,
        CHAT_HOWL,
        CHAT_INDICATE,
        CHAT_MOAN,
        CHAT_HUFF,
        CHAT_PROCLAIM,
        CHAT_NOTE,
        CHAT_PLEAD,
        CHAT_BEG,
        CHAT_PRATTLE,
        CHAT_TATTLE,
        CHAT_QUOTE,
        CHAT_RAMBLE,
        CHAT_DRIVEL,
        CHAT_SING,
        CHAT_REQUEST,
        CHAT_RETORT,
        CHAT_SCREAM,
        CHAT_STATE,
        CHAT_STUTTER,
        CHAT_THREATEN,
        CHAT_UTTER,
        CHAT_WAIL,
        CHAT_WHINE,
        CHAT_PREACH,
        CHAT_ARGUE,
        CHAT_LISP,
        CHAT_RECITE,
        CHAT_MOUTH,
        CHAT_COMMAND,
        CHAT_PROPHESIZE,
        CHAT_GAB,
        CHAT_GOSSIP,
        CHAT_PRONOUNCE,
        CHAT_LECTURE,
        CHAT_BELLOW,
        CHAT_EULOGIZE,
        CHAT_BLAB,
        CHAT_VENT,
        CHAT_DIVULGE,
        CHAT_CONFIDE,
        CHAT_AVOW,
        CHAT_VOW,
        CHAT_CONFESS,
        CHAT_BLURT,
        CHAT_INTERJECT,
        CHAT_RIDDLE,
        CHAT_ANNOUNCE,
        CHAT_FORETELL,
        CHAT_SLUR,
        CHAT_BLEAT,
        CHAT_DISCLOSE,
        CHAT_HYPOTHESIZE,
        CHAT_SWEAR,
        CHAT_PROMISE,
        CHAT_PARROT,
        CHAT_BRAG,
        CHAT_JAW,
        CHAT_JABBER,
        CHAT_CHAT,
        CHAT_YACK,
        CHAT_INQUIRE,
        CHAT_INTERRUPT,
        CHAT_SUPPOSE,
        CHAT_JEST,
        CHAT_YELP,
        CHAT_SHRILL,
        CHAT_BOAST,
        CHAT_CONCEDE,
        CHAT_CONCLUDE,
        CHAT_SURMISE,
        CHAT_THEORIZE,
        CHAT_CHANT,
        CHAT_PROPOSE,
        CHAT_RESPOND,
        CHAT_DEBATE,
        CHAT_MUSE,
        CHAT_EMOTE,
        CHAT_WHISPER,
        CHAT_YELL,
        CHAT_SHOUT
    };
    public static void chat(obj_id npc, String chatType, String moodType, string_id messageId) throws InterruptedException
    {
        _chat(npc, null, chatType, moodType, null, messageId, null);
    }
    public static void chat(obj_id npc, String chatType, string_id messageId) throws InterruptedException
    {
        String moodType = getChatMood(npc);
        if (moodType.equals("none"))
        {
            moodType = null;
        }
        _chat(npc, null, chatType, moodType, null, messageId, null);
    }
    public static void think(obj_id npc, String text) throws InterruptedException
    {
        _chat(npc, null, CHAT_EMOTE, null, text, null, null);
    }
    public static void thinkTo(obj_id npc, obj_id target, String text) throws InterruptedException
    {
        _chat(npc, target, CHAT_EMOTE, null, chat.ChatFlag_targetOnly, text, null, null);
    }
    public static void think(obj_id npc, string_id textId) throws InterruptedException
    {
        _chat(npc, null, CHAT_EMOTE, null, null, textId, null);
    }
    public static void thinkTo(obj_id npc, obj_id target, string_id messageId) throws InterruptedException
    {
        _chat(npc, target, CHAT_EMOTE, null, chat.ChatFlag_targetOnly, null, messageId, null);
    }
    public static void chat(obj_id npc, string_id messageId) throws InterruptedException
    {
        String moodType = getChatMood(npc);
        if (moodType.equals("none"))
        {
            moodType = null;
        }
        String chatType = getChatType(npc);
        _chat(npc, null, chatType, moodType, null, messageId, null);
    }
    public static void chat(obj_id npc, obj_id target, string_id messageId, int flags) throws InterruptedException
    {
        String moodType = getChatMood(npc);
        if (moodType.equals("none"))
        {
            moodType = null;
        }
        String chatType = getChatType(npc);
        _chat(npc, target, chatType, moodType, flags, null, messageId, null);
    }
    public static void chat(obj_id npc, obj_id target, prose_package pp, int flags) throws InterruptedException
    {
        String moodType = getChatMood(npc);
        if (moodType.equals("none"))
        {
            moodType = null;
        }
        String chatType = getChatType(npc);
        String oob = packOutOfBandProsePackage(null, -1, pp);
        _chat(npc, target, chatType, moodType, flags, null, null, oob);
    }
    public static void chat(obj_id npc, obj_id target, string_id messageId) throws InterruptedException
    {
        if (hasObjVar(npc, "convo.appearance"))
        {
            space_utils.tauntPlayer(target, npc, messageId);
        }
        else 
        {
            String moodType = getChatMood(npc);
            if (moodType.equals("none"))
            {
                moodType = null;
            }
            String chatType = getChatType(npc);
            _chat(npc, target, chatType, moodType, ChatFlag_targetOnly, null, messageId, null);
        }
    }
    public static void publicChat(obj_id npc, obj_id target, string_id messageId) throws InterruptedException
    {
        if (hasObjVar(npc, "convo.appearance"))
        {
            space_utils.tauntPlayer(target, npc, messageId);
        }
        else 
        {
            String moodType = getChatMood(npc);
            if (moodType.equals("none"))
            {
                moodType = null;
            }
            String chatType = getChatType(npc);
            _chat(npc, target, chatType, moodType, 0, null, messageId, null);
        }
    }
    public static void chat(obj_id npc, String message) throws InterruptedException
    {
        String moodType = getChatMood(npc);
        if (moodType.equals("none"))
        {
            moodType = null;
        }
        String chatType = getChatType(npc);
        _chat(npc, null, chatType, moodType, message, null, null);
    }
    public static void chat(obj_id npc, obj_id target, String chatType, String moodType, prose_package pp) throws InterruptedException
    {
        if (hasObjVar(npc, "convo.appearance"))
        {
            space_utils.tauntPlayer(target, npc, pp);
        }
        else 
        {
            String oob = packOutOfBandProsePackage(null, -1, pp);
            _chat(npc, target, chatType, moodType, ChatFlag_targetOnly, null, null, oob);
        }
    }
    public static void publicChat(obj_id npc, obj_id target, String chatType, String moodType, prose_package pp) throws InterruptedException
    {
        if (hasObjVar(npc, "convo.appearance"))
        {
            space_utils.tauntPlayer(target, npc, pp);
        }
        else 
        {
            String oob = packOutOfBandProsePackage(null, -1, pp);
            _chat(npc, target, chatType, moodType, null, null, oob);
        }
    }
    public static void chat(obj_id npc, obj_id target, prose_package pp) throws InterruptedException
    {
        if (hasObjVar(npc, "convo.appearance"))
        {
            space_utils.tauntPlayer(target, npc, pp);
        }
        else 
        {
            String moodType = getChatMood(npc);
            if (moodType.equals("none"))
            {
                moodType = null;
            }
            String chatType = getChatType(npc);
            String oob = packOutOfBandProsePackage(null, -1, pp);
            _chat(npc, target, chatType, moodType, null, null, oob);
        }
    }
    public static void chat(obj_id npc, obj_id target, String chatType, string_id blah, int chatFlag) throws InterruptedException
    {
        _chat(npc, target, chatType, null, chatFlag, null, blah, null);
    }
    public static void chat(obj_id npc, obj_id target, String text, int flags) throws InterruptedException
    {
        String moodType = getChatMood(npc);
        if (moodType.equals("none"))
        {
            moodType = null;
        }
        String chatType = getChatType(npc);
        _chat(npc, target, chatType, moodType, flags, text, null, null);
    }
    public static void chat(obj_id npc, obj_id target, String chatType, String moodType, int flags, prose_package pp) throws InterruptedException
    {
        String oob = packOutOfBandProsePackage(null, -1, pp);
        _chat(npc, target, chatType, moodType, flags, null, null, oob);
    }
    public static final int ChatFlag_isPrivate = 0x0001;
    public static final int ChatFlag_skipTarget = 0x0002;
    public static final int ChatFlag_skipSource = 0x0004;
    public static final int ChatFlag_targetOnly = 0x0008;
    public static final int ChatFlag_targetGroupOnly = 0x0010;
    public static final int ChatFlag_targetAndSourceGroup = 0x0100;
    public static void _chat(obj_id npc, obj_id target, String chatType, String moodType, String text, string_id textId, String oob) throws InterruptedException
    {
        _chat(npc, target, chatType, moodType, 0, text, textId, oob);
    }
    public static void _chat(obj_id npc, obj_id target, String chatType, String moodType, int flags, String text, string_id textId, String oob) throws InterruptedException
    {
        StringBuffer sbuf = new StringBuffer();
        if (chatType != null)
        {
            sbuf.append(chatType);
        }
        else 
        {
            sbuf.append('.');
        }
        sbuf.append(' ');
        if (moodType != null)
        {
            sbuf.append(moodType);
        }
        else 
        {
            sbuf.append('.');
        }
        sbuf.append(' ');
        if (textId != null)
        {
            prose_package pp = new prose_package();
            pp.stringId = textId;
            pp.actor.id = npc;
            pp.target.id = target;
            String local_oob = packOutOfBandProsePackage(oob, -1, pp);
            if (oob != null)
            {
                oob += local_oob;
            }
            else 
            {
                oob = local_oob;
            }
        }
        sbuf.append(flags);
        sbuf.append(' ');
        if (text != null)
        {
            sbuf.append(text);
        }
        sbuf.append(' ');
        if (oob != null)
        {
            sbuf.append('\0');
            sbuf.append(oob);
        }
        queueCommand(npc, (-296481545), target, sbuf.toString(), COMMAND_PRIORITY_DEFAULT);
    }
    public static void setChatMood(obj_id npc, String chatMood) throws InterruptedException
    {
        if (canHaveChatMood(npc))
        {
            setObjVar(npc, "ai.chatMood", chatMood);
        }
    }
    public static String getChatMood(obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(npc, "ai.chatMood"))
        {
            return "none";
        }
        else 
        {
            return getStringObjVar(npc, "ai.chatMood");
        }
    }
    public static void setChatType(obj_id npc, String chatType) throws InterruptedException
    {
        if (canHaveChatMood(npc))
        {
            setObjVar(npc, "ai.chatType", chatType);
        }
    }
    public static String getChatType(obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(npc, "ai.chatType"))
        {
            return CHAT_SAY;
        }
        else 
        {
            return getStringObjVar(npc, "ai.chatType");
        }
    }
    public static void setBadMood(obj_id npc) throws InterruptedException
    {
        if (!canHaveChatMood(npc))
        {
            return;
        }
        setObjVar(npc, "ai.chatMood", BADMOODS[rand(0, BADMOODS.length - 1)]);
        setObjVar(npc, "ai.chatType", BADTYPES[rand(0, BADTYPES.length - 1)]);
    }
    public static void setGoodMood(obj_id npc) throws InterruptedException
    {
        if (!canHaveChatMood(npc))
        {
            return;
        }
        setObjVar(npc, "ai.chatMood", GOODMOODS[rand(0, GOODMOODS.length - 1)]);
        setObjVar(npc, "ai.chatType", GOODTYPES[rand(0, GOODTYPES.length - 1)]);
    }
    public static void setAngryMood(obj_id npc) throws InterruptedException
    {
        if (!canHaveChatMood(npc))
        {
            return;
        }
        setObjVar(npc, "ai.chatMood", ANGRYMOODS[rand(0, ANGRYMOODS.length - 1)]);
        setObjVar(npc, "ai.chatType", ANGRYTYPES[rand(0, ANGRYTYPES.length - 1)]);
    }
    public static void setNeutralMood(obj_id npc) throws InterruptedException
    {
        if (!canHaveChatMood(npc))
        {
            return;
        }
        setObjVar(npc, "ai.chatMood", NEUTRALMOODS[rand(0, NEUTRALMOODS.length - 1)]);
        setObjVar(npc, "ai.chatType", NEUTRALTYPES[rand(0, NEUTRALTYPES.length - 1)]);
    }
    public static boolean canHaveChatMood(obj_id npc) throws InterruptedException
    {
        if (ai_lib.aiGetNiche(npc) != NICHE_NPC)
        {
            return false;
        }
        if (hasObjVar(npc, "ai.defaultCalmMood"))
        {
            return false;
        }
        if (hasObjVar(npc, "ai.noChatMood"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public static void setTempAnimationMood(obj_id target, String mood) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        String animMood = getAnimationMood(target);
        if (animMood == null || animMood.equals(""))
        {
            animMood = "default";
        }
        setObjVar(target, "temp.animMood", animMood);
        setAnimationMood(target, mood);
    }
    public static void resetTempAnimationMood(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (!hasObjVar(target, "temp.animMood"))
        {
            return;
        }
        String animMood = getStringObjVar(target, "temp.animMood");
        if (animMood == null || animMood.equals("") || animMood.equals("none"))
        {
            animMood = "default";
        }
        setAnimationMood(target, animMood);
    }
}

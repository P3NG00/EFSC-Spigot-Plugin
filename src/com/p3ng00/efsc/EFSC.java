package com.p3ng00.efsc;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.abbacaving.ABBACaving;
import com.p3ng00.efsc.module.armorstandarms.ArmorStandArms;
import com.p3ng00.efsc.module.boop.Boop;
import com.p3ng00.efsc.module.chairs.Chairs;
import com.p3ng00.efsc.module.fortunesilverfish.FortuneSilverfish;
import com.p3ng00.efsc.module.hat.Hats;
import com.p3ng00.efsc.module.mineablespawners.MineableSpawners;
import com.p3ng00.efsc.module.nametether.NameTether;
import com.p3ng00.efsc.module.place.Place;
import com.p3ng00.efsc.module.playerheads.PlayerHeads;
import com.p3ng00.efsc.module.playerstacking.PlayerStacking;
import com.p3ng00.efsc.module.snowballfight.SnowballFight;
import com.p3ng00.efsc.module.tag.Tag;
import com.p3ng00.efsc.module.textcolors.TextColors;
import com.p3ng00.efsc.module.tridentretrieval.TridentRetrieval;
import com.p3ng00.p3plugin.P3Plugin;
import org.bukkit.ChatColor;

import java.util.Arrays;

public final class EFSC extends P3Plugin {

    /*
    TODO
     - Boop
     - Snowball Fight

     - name tether change
       - Make name tags actually name flying arrow. when it hits entity, check if arrow is named and apply to entity.

     - blessings and curses
     - ultimate tag

     - EFSC Listener
       - Crit Mod
     */

    public static final String ERROR_SENDER = ChatColor.RED + "Command must be executed by a player.";
    public static final String ERROR_NOT_OP = ChatColor.RED + "Must be an Admin to use this command.";
    public static final String ERROR_OFFLINE = ChatColor.RED + "Player is not online.";

    // DONT INITIALIZE THESE HERE. DO THAT IN #onEnable() (i need this here because I've tried to simplify this code a hundred times and i keep doing this and it keeps giving me the same error and i keep wondering why i'm having an error and i keep spending an hour fixing something that wasn't broken in the first place)
    public static ABBACaving ABBA_CAVING;
    public static ArmorStandArms ARMOR_STAND_ARMS;
    public static Boop BOOP;
    public static Chairs CHAIRS;
    public static FortuneSilverfish FORTUNE_SILVERFISH;
    public static Hats HATS;
    public static MineableSpawners MINEABLE_SPAWNERS;
    public static NameTether NAME_TETHER;
    public static Place PLACE;
    public static PlayerHeads PLAYER_HEADS;
    public static PlayerStacking PLAYER_STACKING;
    public static SnowballFight SNOWBALL_FIGHT;
    public static Tag TAG;
    public static TextColors TEXT_COLORS;
    public static TridentRetrieval TRIDENT_RETRIEVAL;

    public static Module[] MODULES;

    public EFSC() {
        super(true);
    }

    @Override
    public void onEnable() {

        // THESE HAVE TO STAY HERE. DONT TRY TO INITIALIZE THESE STATICALLY. OTHERWISE, ILLEGALSTATEEXCEPTION, P3PLUGIN NOT FINISHED INITIALIZING BEFORE BEING CALLED TO REGISTER A LISTENER
        ABBA_CAVING = new ABBACaving();
        ARMOR_STAND_ARMS = new ArmorStandArms();
        BOOP = new Boop();
        CHAIRS = new Chairs();
        FORTUNE_SILVERFISH = new FortuneSilverfish();
        HATS = new Hats();
        MINEABLE_SPAWNERS = new MineableSpawners();
        NAME_TETHER = new NameTether();
        PLACE = new Place();
        PLAYER_HEADS = new PlayerHeads();
        PLAYER_STACKING = new PlayerStacking();
        SNOWBALL_FIGHT = new SnowballFight();
        TAG = new Tag();
        TEXT_COLORS = new TextColors();
        TRIDENT_RETRIEVAL = new TridentRetrieval();

        MODULES = new Module[] {
                ABBA_CAVING,
                ARMOR_STAND_ARMS,
                BOOP,
                CHAIRS,
                FORTUNE_SILVERFISH,
                HATS,
                MINEABLE_SPAWNERS,
                NAME_TETHER,
                PLACE,
                PLAYER_HEADS,
                PLAYER_STACKING,
                SNOWBALL_FIGHT,
                TAG,
                TEXT_COLORS,
                TRIDENT_RETRIEVAL
        };

        registerCommands(new EFSCCommand());

    }

    @Override
    public void onDisable() {

        getConfig().options().copyDefaults(true);
        Arrays.stream(MODULES).forEach(Module::disable);
        saveConfig();

    }

    public static void printToConsole(Object msg) {
        P3Plugin.printToConsole(String.format("[EFSC] %s", msg));
    }
}

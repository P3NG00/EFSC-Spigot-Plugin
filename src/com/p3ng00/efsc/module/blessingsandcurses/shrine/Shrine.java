package com.p3ng00.efsc.module.blessingsandcurses.shrine;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;

import static com.p3ng00.efsc.module.blessingsandcurses.BlessingsAndCurses.*;
import static org.bukkit.Material.*;
import static org.bukkit.potion.PotionEffectType.*;

public class Shrine {

    public final Type type;
    public final boolean tier2;

    public Shrine(Type type, boolean tier2) {
        this.type = type;
        this.tier2 = tier2;
    }

    public static Shrine getShrine(Location l) {
        Checker c = new Checker(l.clone());
        Type type = null;
        boolean tier2 = false;
        // Written Facing NORTH and DOWN (+-180, 90)
        if (!c.updateMaterial(SEA_LANTERN).check(0, 0, 1)) {
            return null;
        }
        if (c.check(EMERALD_ORE, COAL_ORE, DIAMOND_ORE, EMERALD_BLOCK, DIAMOND_BLOCK)) {
            type = Type.FORTUNE;
        } else if (c.check(GOLD_BLOCK, BONE_BLOCK, REDSTONE_BLOCK, REDSTONE_LAMP, GOLD_BLOCK)) {
            type = Type.HEALTH;
        } else if (c.updateMaterial(BLUE_ICE).check(-2, 0) &&
                   c.check(-1, 1) &&
                   c.check(-1, 0) &&
                   c.check(0, 2) &&
                   c.check(0, 1) &&
                   c.check(1, 1) &&
                   c.updateMaterial(MAGMA_BLOCK).check(-1, -1) &&
                   c.check(0, -1) &&
                   c.check(0, -2) &&
                   c.check(1, 0) &&
                   c.check(1, -1) &&
                   c.check(2, 0) &&
                   c.updateMaterial(PRISMARINE).check(-2, 1) &&
                   c.check(-1, 2) &&
                   c.check(1, 2) &&
                   c.check(2, 1) &&
                   c.updateMaterial(RED_NETHER_BRICKS).check(-2, -1) &&
                   c.check(-1, -2) &&
                   c.check(1, -2) &&
                   c.check(2, -1) &&
                   c.checkPlace(Place.OUTSIDE_CORNER, CRACKED_STONE_BRICKS)) {
            type = Type.EXPLORE;
        }
        switch (type) {
            case FORTUNE:
                tier2 = c.checkTier2(DIAMOND_BLOCK, LAPIS_BLOCK, SEA_LANTERN);
                break;
            case HEALTH:
                tier2 = c.checkTier2(GOLD_BLOCK, BONE_BLOCK, REDSTONE_BLOCK);
                break;
            case EXPLORE:
                tier2 = c.updateMaterial(BLUE_ICE).check(-3, 0, -1) &&
                        c.check(-3, 0, -2) &&
                        c.check(-2, 2, -1) &&
                        c.check(0, 3, -1) &&
                        c.check(0, 3, -2) &&
                        c.check(2, 2, -1) &&
                        c.updateMaterial(MAGMA_BLOCK).check(-2, -2, -1) &&
                        c.check(0, -3, -1) &&
                        c.check(0, -3, -2) &&
                        c.check(2, -2, -1) &&
                        c.check(3, 0, -1) &&
                        c.check(3, 0, -2) &&
                        c.updateMaterial(END_ROD).check(-3, 0, -3) &&
                        c.check(0, 3, -3) &&
                        c.check(0, -3, -3) &&
                        c.check(3, 0, -3);
                break;
        }
        return new Shrine(type, tier2);
    }

    public Collection<PotionEffect> getPotionEffects() {
        Collection<PotionEffect> effects = new ArrayList<>();
        int duration = tier2 ? 3000 : 1200;
        int amplifier = tier2 ? 2 : 0;
        for (PotionEffectType pet : type.potionEffects) {
            effects.add(new PotionEffect(pet, duration, amplifier));
        }
        return effects;
    }

    public static class Checker {

        private final Location center;
        private Material current;

        public Checker(Location l) {
            center = l.clone();
            current = WATER;
        }

        public Checker updateMaterial(Material m) {
            current = m;
            return this;
        }

        public boolean check(int x, int y, int z) {
            return center.clone().add(x, -z, -y).getBlock().getType() == current;
        }

        public boolean check(int x, int y) {
            return check(x, y, 0);
        }

        public boolean check(Material... materials) {
            if (materials.length != 5) {
                throw new IllegalArgumentException("Must have 5 materials!");
            }
            for (int i = 0; i < 5; i++) {
                if (!checkPlace(Place.values()[i], materials[i])) {
                    return false;
                }
            }
            return true;
        }

        public boolean checkCoords(int x, int y, int z) {
            return check(x, y, z) &&
                   check(x, -y, z) &&
                   check(-x, y, z) &&
                   check(-x, -y, z);
        }

        public boolean checkCoords(int x, int y) {
            return checkCoords(x, y, 0);
        }

        public boolean checkFlip(int x, int y, int z) {
            return checkCoords(x, y, z) && checkCoords(y, x, z);
        }

        public boolean checkPlace(Place place, Material material) {
            updateMaterial(material);
            return checkCoords(place.x, place.y) && checkCoords(place.y, place.x);
        }

        public boolean checkTier2(Material... materials) {
            if (materials.length != 3) {
                throw new IllegalArgumentException("Must have 3 materials!");
            } else {
                return updateMaterial(materials[0]).checkFlip(2, 2, -1) &&
                       updateMaterial(materials[1]).checkFlip(3, 0, -1) &&
                       updateMaterial(materials[2]).checkFlip(3, 0, -2);
            }
        }
    }

    public enum Place {
        INSIDE(1, 0),
        INSIDE_EDGE(1, 1),
        INSIDE_CORNER(2, 0),
        OUTSIDE_EDGE(2, 1),
        OUTSIDE_CORNER(3, 0);

        public final int x, y;

        Place(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public enum Type {

        EXPLORE(SHRINE_EXPLORE,
                new Material[]{}, // todo fill and implement

                new PotionEffectType[]{
                        FIRE_RESISTANCE,
                        NIGHT_VISION,
                        WATER_BREATHING,
                        DOLPHINS_GRACE
                }),

        HEALTH(SHRINE_HEALTH,
                new Material[]{},

                new PotionEffectType[]{
                        REGENERATION,
                        ABSORPTION,
                        INCREASE_DAMAGE,
                        SPEED,
                        JUMP,
                        SATURATION,
                }),

        FORTUNE(SHRINE_FORTUNE,
                new Material[]{},

                new PotionEffectType[]{
                        LUCK,
                        FAST_DIGGING,
                        DAMAGE_RESISTANCE,
                        HERO_OF_THE_VILLAGE,
                        GLOWING
                });

        private final String title;
        private final Material[] validOfferings;
        private final PotionEffectType[] potionEffects; // todo change

        Type(String title, Material[] validOfferings, PotionEffectType[] potionEffects) {
            this.title = title;
            this.validOfferings = validOfferings;
            this.potionEffects = potionEffects;
            /*
            for (PotionEffectType pe : potionEffects) {
                this.potionEffects.add(new PotionEffect(pe, 1200, 0));
            }
             */
        }

        public String getTitle() {
            return title;
        }
    }
}

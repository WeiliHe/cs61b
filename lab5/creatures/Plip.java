package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.lang.Math;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    private double reEnergyNeeded = 1;

    // return the name plip
    public String name() {
        return name.toLowerCase();
    }

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = (int) (96 * e + 63);
        b = 76;
        energy = Math.min(e, 2.0);
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        g = (int) (96 * energy + 63);
        return color(r, g, b);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.15;
        energy = Math.max(energy, 0);
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy += 0.2;
        energy = Math.min(energy, 2);
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        double babyEnergy = energy * 0.5;
        energy = energy * 0.5;
        return new Plip(babyEnergy);
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */

    // another method is to use .getClass to check the empty class, which extends the 
    // occupant, then have a deque<Occupant> = new ArrayDeque<Occupant>(); add the empty
    // class into the deque
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<Direction>();
        boolean anyClorus = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        emptyNeighbors = getNeighborsOfType(neighbors, "empty");
        if (emptyNeighbors.size() == 0) { // FIXME
            return new Action(Action.ActionType.STAY);
        }
        Direction dir = HugLifeUtils.randomEntry(emptyNeighbors);
        if (energy >= reEnergyNeeded) {
            return new Action(Action.ActionType.REPLICATE, dir);
        }
        Deque<Direction> clorusNeighbors = new ArrayDeque<>();
        clorusNeighbors = getNeighborsOfType(neighbors, "clorus");
        if (clorusNeighbors.size() > 0) {
            anyClorus = false;
        }
        if (anyClorus && Math.random() < 0.5) {
            dir = HugLifeUtils.randomEntry(emptyNeighbors);
            return new Action(Action.ActionType.MOVE, dir);
        }
        return new Action(Action.ActionType.STAY);
    }
}

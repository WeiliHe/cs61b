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
* @author Felix
*/

public class Clorus extends Creature {
	private int r;
	private int g;
	private int b;
	private double repEnergyRetained = 0.5;
	private double repEnergyGiven = 0.5;
	private double stayEnergyLoss = 0.01;
	private double moveEnergyLoss = 0.03;

	public String name() {
		return name.toLowerCase();
	}

	public Clorus(double e) {
		super("clorus");
		r = 34;
		g = 0;
		b = 231;
		energy = e;
	}

	public Color color() {
        return color(r, g, b);
    }

	public void move() {
		energy -= moveEnergyLoss;
		energy = Math.max(energy, 0);
	}

	public void stay() {
		energy -= stayEnergyLoss;
		energy = Math.max(energy, 0);
	}


	public void attack(Creature c) {
		energy += c.energy();
	}

    public Clorus replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }

	public Action chooseAction(Map<Direction, Occupant> neighbors) {
		Deque<Direction> emptyNeighbors = new ArrayDeque<Direction>();
		emptyNeighbors = getNeighborsOfType(neighbors, "empty");
		if (emptyNeighbors.size() == 0) {
			return new Action(Action.ActionType.STAY);
		}

		Deque<Direction> plipNeighbors = getNeighborsOfType(neighbors, "plip");
		if (plipNeighbors.size() > 0) {
			Direction plipD = HugLifeUtils.randomEntry(plipNeighbors);
			return new Action(Action.ActionType.ATTACK, plipD);
		}

		Direction emptyD = HugLifeUtils.randomEntry(emptyNeighbors);
		if (energy >= 1) {
			return new Action(Action.ActionType.REPLICATE, emptyD);
		} else {
			return new Action(Action.ActionType.MOVE, emptyD);
		}
	}
}
package model.abilities;
import java.util.*;
public class Ability {
	private String name;
	private int manaCost;
	private int baseCooldown;
	private int currentCooldown;
	private int castRange;
	private int requiredActionPoints;
	private AreaOfEffect castArea;

	public Ability(String name, int cost, int baseCoolDown, int castRange, AreaOfEffect area, int required) {
		this.manaCost = cost;
		this.name = name;
		this.baseCooldown = baseCoolDown;
		this.castRange = castRange;
		this.castArea = area;
		this.requiredActionPoints = required;

	}

	public Ability() {

	}

	public void setCastArea(AreaOfEffect castArea) {
		this.castArea = castArea;
	}

	public int getCurrentCooldown() {
		return currentCooldown;
	}

	public void setCurrentCooldown(int currentCooldown) {
		this.currentCooldown = currentCooldown;
	}

	public String getName() {
		return name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getBaseCooldown() {
		return baseCooldown;
	}

	public int getCastRange() {
		return castRange;
	}

	public int getRequiredActionPoints() {
		return requiredActionPoints;
	}

	public AreaOfEffect getCastArea() {
		return castArea;
	}

}

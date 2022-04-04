package engine;

import java.awt.Point;
import java.io.*;
import java.util.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;

public class Game {
	private Player firstPlayer, secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private static final int BOARDHEIGHT = 5;
	private static final int BOARDWIDTH = 5;

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public Object[][] getBoard() {
		return board;
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public Game(Player first, Player second) throws IOException {
		this.firstPlayer = first;
		this.secondPlayer = second;
		board = new Object[BOARDHEIGHT][BOARDWIDTH];
//		loadAbilities("Abilities.csv");
//		loadChampions("Champions.csv");
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
	}

	private void placeChampions() throws IOException {
		if (this.firstPlayer.getTeam().size() == 3) {
			board[0][1] = firstPlayer.getTeam().get(0);
			firstPlayer.getTeam().get(0).setLocation(new Point(0, 1));
			board[0][2] = firstPlayer.getTeam().get(1);
			firstPlayer.getTeam().get(1).setLocation(new Point(0, 2));
			board[0][3] = firstPlayer.getTeam().get(2);
			firstPlayer.getTeam().get(2).setLocation(new Point(0, 3));

		
		if (this.secondPlayer.getTeam().size() == 3) {
			board[4][1] = secondPlayer.getTeam().get(0);
			secondPlayer.getTeam().get(0).setLocation(new Point(4, 1));
			board[4][2] = secondPlayer.getTeam().get(1);
			secondPlayer.getTeam().get(1).setLocation(new Point(4, 2));
			board[4][3] = secondPlayer.getTeam().get(2);
			secondPlayer.getTeam().get(2).setLocation(new Point(4, 3));
		}

		availableChampions = new ArrayList<>();
		availableAbilities = new ArrayList<>();
	}
	}

	private void placeCovers() {
		boolean[][] vis = new boolean[5][5];

		for (int i = 0; i < 5; i++) {
			int x = (int) (Math.random() * 5), y = (int) (Math.random() * 5);
			while (vis[x][y] || x == 0 || x == 4) {
				x = (int) (Math.random() * 3) + 1;
				y = (int) (Math.random() * 5);
			}
			board[x][y] = new Cover(x, y);
			vis[x][y] = true;
		}
	}

	public static void loadAbilities(String filePath) throws IOException {
		availableAbilities = new ArrayList<Ability>();
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String r;
		while (br.ready()) {
			r = br.readLine();
			String[] arr = r.split(",");
			if (arr[0].equals("CC")) {

				Effect effect;
				int duration = Integer.parseInt(arr[8]);
				switch (arr[7]) {
				case "Disarm":
					effect = new Disarm(duration);
					break;
				case "Dodge":
					effect = new Dodge(duration);

					break;
				case "Embrace":
					effect = new Embrace(duration);

					break;
				case "PowerUp":
					effect = new PowerUp(duration);

					break;
				case "Root":
					effect = new Root(duration);

					break;
				case "Shield":
					effect = new Shield(duration);

					break;
				case "Shock":
					effect = new Shock(duration);

					break;
				case "SpeedUp":
					effect = new SpeedUp(duration);

					break;
				case "Silence":
					effect = new Silence(duration);

					break;
				case "Stun":
					effect = new Stun(duration);

					break;
				default:
					effect = null;
				}
				CrowdControlAbility ab = new CrowdControlAbility(arr[1], Integer.parseInt(arr[2]),
						Integer.parseInt(arr[4]), Integer.parseInt(arr[3]), AreaOfEffect.valueOf(arr[5]),
						Integer.parseInt(arr[6]), effect);

				availableAbilities.add(ab);
			} else if (arr[0].equals("DMG")) {
				DamagingAbility db = new DamagingAbility(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[4]),
						Integer.parseInt(arr[3]), AreaOfEffect.valueOf(arr[5]), Integer.parseInt(arr[6]),
						Integer.parseInt(arr[7]));
				availableAbilities.add(db);
			} else {
				HealingAbility ha = new HealingAbility(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[4]),
						Integer.parseInt(arr[3]), AreaOfEffect.valueOf(arr[5]), Integer.parseInt(arr[6]),
						Integer.parseInt(arr[7]));
				availableAbilities.add(ha);
			}
//			r = br.readLine();
		}
	}

	public static void loadChampions(String filePath) throws IOException {
		availableChampions = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String r;
		while ((r = br.readLine()) != null) {
			String[] arr = r.split(",");
			String a1 = arr[8], a2 = arr[9], a3 = arr[10];

			if (arr[0].equals("H")) {
				Hero h = new Hero(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), Integer.parseInt(arr[4]),
						Integer.parseInt(arr[5]), Integer.parseInt(arr[6]), Integer.parseInt(arr[7]));
				for (int i = 0; i < availableAbilities.size(); i++) {
					if (availableAbilities.get(i).getName().equals(a1) || availableAbilities.get(i).getName().equals(a2)
							|| availableAbilities.get(i).getName().equals(a3)) {
						h.getAbilities().add(availableAbilities.get(i));
					}
				}
				availableChampions.add(h);

			} else if (arr[0].equals("A")) {
				AntiHero ah = new AntiHero(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]),
						Integer.parseInt(arr[4]), Integer.parseInt(arr[5]), Integer.parseInt(arr[6]),
						Integer.parseInt(arr[7]));
				for (int i = 0; i < availableAbilities.size(); i++) {
					if (availableAbilities.get(i).getName().equals(a1) || availableAbilities.get(i).getName().equals(a2)
							|| availableAbilities.get(i).getName().equals(a3)) {
						ah.getAbilities().add(availableAbilities.get(i));
					}
				}
				availableChampions.add(ah);

			} else {
				Villain v = new Villain(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]),
						Integer.parseInt(arr[4]), Integer.parseInt(arr[5]), Integer.parseInt(arr[6]),
						Integer.parseInt(arr[7]));
				for (int i = 0; i < availableAbilities.size(); i++) {
					if (availableAbilities.get(i).getName().equals(a1) || availableAbilities.get(i).getName().equals(a2)
							|| availableAbilities.get(i).getName().equals(a3)) {
						v.getAbilities().add(availableAbilities.get(i));
					}
				}
				availableChampions.add(v);

			}
		}

	}

}

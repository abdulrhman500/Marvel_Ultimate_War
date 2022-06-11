package Graphics;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.abilities.*;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;

public class board {

	private static final double b_width = 2;
	private static String Blank_Image_Path = "blank.jpg";
	private static String Cover_Image_Path = "cover2.jpg";
	private static String Board_Background = "background2.jpg";
	private static String Source_Path = null;
	Label player1;
	Label player2;
	Label p1, p2;
	Ability curr_abi = null;
	Label info[];
	MenuButton p_info[] = new MenuButton[6];
	Game game;
	Stage window;
	Object board[][];
	Button btn[][] = new Button[5][5];
	Button players[] = new Button[6];
	Button player_turn_order[] = new Button[6];
	Button abilities[] = new Button[3];
	boolean move = false;
	boolean attack = false;
	boolean ability = false;
	boolean end = false;
	Label used_leader_ability1;
	Label used_leader_ability2;
	Label Vused_leader_ability1;
	Label Vused_leader_ability2;

	board(Stage win, Game game) throws IOException {
		Player p1 = new Player("Ahmed");
		Player p2 = new Player("Abdo");
		window = win;

		this.game = game;
		p_info[0] = new MenuButton();
		p_info[0].getItems().setAll(new MenuItem("fvvmnkfdlknd"));
		System.out.println(p_info[0].getItems());
		board = game.getBoard();
//		ArrayList<Champion> a = new ArrayList<>();
//		ArrayList<Champion> b = new ArrayList<>();
//
//		game = new Game(p1, p2);
//		game.loadChampions("Champions.csv");
//		game.loadAbilities("Abilities.csv");
//		 game.getAvailableChampions().size());
//
//		for (int i = 0; i < 6; i++) {
//			if (i < 3)
//				a.add(game.getAvailableChampions().get(i));
//			else
//				b.add(game.getAvailableChampions().get(i));
//		}
//		p1.setTeam(a);
//		p2.setTeam(b);
		// game.excute();

	}

	Label curr_ch_name = new Label();
	Label curr_ch_info = new Label();

	static int x0 = -1;
	static int y0 = -1;

	void display() {
		StackPane root = new StackPane();
		GridPane bor = new GridPane();
		GridPane player = new GridPane();
		GridPane turnorder = new GridPane();
		HBox txtl = new HBox();
		HBox txtr = new HBox();

		VBox cur_ch_infop = new VBox();
		root.getChildren().add(cur_ch_infop);
		cur_ch_infop.getChildren().addAll(curr_ch_name, curr_ch_info);
		cur_ch_infop.setAlignment(Pos.CENTER_RIGHT);
		txtl.setAlignment(Pos.TOP_LEFT);
		txtr.setAlignment(Pos.TOP_RIGHT);

		creat_Image(Board_Background, root);
		double BUTTON_PADDING = 1.5;

		bor.minWidth(5);
		bor.minHeight(5);
		bor.setAlignment(Pos.CENTER);
		bor.setPadding(new Insets(BUTTON_PADDING));
		bor.setHgap(BUTTON_PADDING);
		bor.setVgap(BUTTON_PADDING);
		bor.setGridLinesVisible(true);

		player.minWidth(6);
		player.minHeight(6);
		player.setAlignment(Pos.TOP_CENTER);
		player.setPadding(new Insets(BUTTON_PADDING));
		// player.setStyle("-fx-border-color: blue");
		player.setHgap(BUTTON_PADDING);
		player.setVgap(BUTTON_PADDING);
		player.setGridLinesVisible(false);

		turnorder.minWidth(6);
		turnorder.minHeight(6);
		turnorder.setAlignment(Pos.BOTTOM_CENTER);
		turnorder.setPadding(new Insets(BUTTON_PADDING));
		// turnorder.setStyle("-fx-border-color: blue");
		turnorder.setHgap(BUTTON_PADDING);
		turnorder.setVgap(BUTTON_PADDING);
		turnorder.setGridLinesVisible(false);

		prep_grid(bor, turnorder, player, txtl, txtr);

		root.getChildren().add(bor);
		root.getChildren().add(turnorder);
		root.getChildren().add(txtl);
		root.getChildren().add(txtr);
		VBox abilit = new VBox();
		abilit.setAlignment(Pos.CENTER_LEFT);
		root.getChildren().add(abilit);

		HBox h11 = new HBox();
		HBox h22 = new HBox();
		h11.getChildren().add(used_leader_ability1);
		h11.getChildren().add(Vused_leader_ability1);
		h22.getChildren().add(used_leader_ability2);
		h22.getChildren().add(Vused_leader_ability2);
		abilit.getChildren().add(h11);
		abilit.getChildren().add(h22);

		for (int i = 0; i < abilities.length; i++) {
			abilit.getChildren().add(abilities[i]);
		}
		abilities[0].setOnAction(e -> {
			if (ability_work0(0)) {
				update_grid();
				update_turn_order(turnorder);
				update_players(player);
				update_txt(txtl, txtr);
				update_ability();
				end_scene(check_if_dead_and_return_winner());

			}

		});
		abilities[1].setOnAction(e -> {
			if (ability_work0(1)) {
				update_grid();
				update_turn_order(turnorder);
				update_players(player);
				update_txt(txtl, txtr);
				update_ability();
				end_scene(check_if_dead_and_return_winner());

			}

		});
		abilities[2].setOnAction(e -> {
			if (ability_work0(2)) {
				update_grid();
				update_turn_order(turnorder);
				update_players(player);
				update_txt(txtl, txtr);
				update_ability();
				end_scene(check_if_dead_and_return_winner());

			}

		});

		Scene sc = new Scene(root, 1300, 750);
		sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				Champion cur = game.getCurrentChampion();
				switch (event.getCode()) {
				case M:
					move = true;
					attack = false;
					break;
				case K:
					move = false;
					attack = true;
					break;
				case S:
					if (ability) {
						try {
							game.castAbility(curr_abi, Direction.DOWN);
							done = true;
						} catch (NotEnoughResourcesException e1) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion does not have enough resources ");
							e1.printStackTrace();
						} catch (AbilityUseException e2) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion can not use this ability ");
						} catch (CloneNotSupportedException e4) {

						}
						ability = false;
					}
					if (board_action(cur, move, attack, Direction.UP)) {
						update_grid();
						update_turn_order(turnorder);
						update_players(player);
						update_txt(txtl, txtr);
						update_ability();
						end_scene(check_if_dead_and_return_winner());

					}
					break;
				case W:
					if (ability) {
						try {
							game.castAbility(curr_abi, Direction.UP);
							done = true;

						} catch (NotEnoughResourcesException e1) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion does not have enough resources ");
							e1.printStackTrace();
						} catch (AbilityUseException e2) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion can not use this ability ");
						} catch (CloneNotSupportedException e4) {

						}
						ability = false;
					}
					if (board_action(cur, move, attack, Direction.DOWN)) {
						update_grid();
						update_turn_order(turnorder);
						update_players(player);
						update_txt(txtl, txtr);
						update_ability();
						end_scene(check_if_dead_and_return_winner());

					}
					break;
				case D:
					if (ability) {
						try {
							game.castAbility(curr_abi, Direction.RIGHT);
							done = true;

						} catch (NotEnoughResourcesException e1) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion does not have enough resources ");

						} catch (AbilityUseException e2) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion can not use this ability ");
						} catch (CloneNotSupportedException e4) {

						}
						ability = false;
					}

					if (board_action(cur, move, attack, Direction.RIGHT)) {
						update_grid();
						update_turn_order(turnorder);
						update_players(player);
						update_txt(txtl, txtr);
						update_ability();
						end_scene(check_if_dead_and_return_winner());

					}
					break;
				case A:
					if (ability) {
						try {
							game.castAbility(curr_abi, Direction.LEFT);
							done = true;

						} catch (NotEnoughResourcesException e1) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion does not have enough resources ");
							e1.printStackTrace();
						} catch (AbilityUseException e2) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion can not use this ability ");
						} catch (CloneNotSupportedException e4) {

						}
						ability = false;
						// update_ability();
					}
					if (board_action(cur, move, attack, Direction.LEFT)) {
						update_grid();
						update_turn_order(turnorder);
						update_players(player);
						update_txt(txtl, txtr);
						update_ability();
						end_scene(check_if_dead_and_return_winner());

					}
					break;
				case T:
					game.endTurn();
					update_grid();
					update_turn_order(turnorder);
					update_players(player);
					update_txt(txtl, txtr);
					update_ability();
					end_scene(check_if_dead_and_return_winner());
					break;
				default:

				}
			}

		});

		window.setScene(sc);
		window.show();

	}

	private void prep_grid(GridPane bor, GridPane turnorder, GridPane player, HBox txtl, HBox txtr) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				btn[i][j] = new Button();
				btn[i][j].setMinSize(80, 80);
				bor.add(btn[i][j], j, i);

			}
		}

		Border bb = new Border(
				new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8)));

		Border bbb = new Border(
				new BorderStroke(Color.VIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8)));

		DropShadow borderGlow = new DropShadow();
		borderGlow.setColor(Color.BLUEVIOLET);
		curr_ch_name.setBorder(bb);
		curr_ch_info.setBorder(bbb);
		curr_ch_name.setTextAlignment(TextAlignment.LEFT);
		curr_ch_name.setEffect(borderGlow);
		curr_ch_name.setTextFill(Color.GOLD);
		curr_ch_info.setTextFill(Color.AQUAMARINE);
		curr_ch_name.setAlignment(Pos.TOP_CENTER);
		curr_ch_info.setAlignment(Pos.CENTER);

		curr_ch_info.setEffect(borderGlow);
		curr_ch_name.setStyle("-fx-border-radius: 50px;-fx-font-size: 9pt");
		curr_ch_info.setStyle("-fx-border-radius: 50px;-fx-font-size: 8pt");

		used_leader_ability1 = new Label(game.getFirstPlayer().getName() + " Leader Ability Useed ?");
		used_leader_ability1.setTextFill(Color.WHITE);

		used_leader_ability2 = new Label(game.getSecondPlayer().getName() + " Leader Ability Useed ?");
		Vused_leader_ability1 = new Label("NOT YET!");
		Vused_leader_ability2 = new Label("NOT YET!");
		used_leader_ability1.setStyle("-fx-font-size:12px;-fx-font-weight: bold;fx-text-fill: white;");
		used_leader_ability2.setStyle("-fx-font-size: 12px;-fx-font-weight: bold;fx-text-fill: white;");
		Vused_leader_ability1.setStyle("-fx-font-size: 12px;-fx-font-weight: bold;fx-text-fill: red;");
		Vused_leader_ability2.setStyle("-fx-font-size:12px;-fx-font-weight: bold;fx-text-fill: red;");
		used_leader_ability2.setTextFill(Color.WHITE);

		for (int i = 0; i < 3; i++) {
			abilities[i] = new Button();
			abilities[i].setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
					+ "    -fx-background-radius: 2;\r\n" + "    -fx-background-insets: 2;\r\n"
					+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: black;");

		}

		p1 = new Label(game.getFirstPlayer().getName());
		p2 = new Label(game.getSecondPlayer().getName());
		Label p3 = new Label("Turn Order");
		p1.setTextFill(Color.GOLD);
		p1.setStyle("-fx-background-color: \r\n" + "        #FF5349,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		p2.setTextFill(Color.GOLD);
		p2.setStyle("-fx-background-color: \r\n" + "       #00BFFF,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);

		p3.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 5;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");

		// player.add(p1, 0, 0);
		// player.add(p2, 0, 1);
		turnorder.add(p3, 0, 0);
		for (int i = 0; i < 6; i++) {
			players[i] = new Button();
			players[i].setMinSize(80, 80);
			player_turn_order[i] = new Button();
			player_turn_order[i].setMinSize(80, 80);
			turnorder.add(player_turn_order[i], i + 1, 0);

			if (i < 3)
				player.add(players[i], i + 1, 0);
			else
				player.add(players[i], i + 1 - 3, 1);
			p_info[i] = new MenuButton();
			p_info[i].setPrefSize(100, 80);

		}
//		player1 = new Label(game.getFirstPlayer().getName());
//		player2 = new Label(game.getSecondPlayer().getName());
//		player1.setTextFill(Color.GOLD);
//		player1.setStyle("-fx-background-color: \r\n" + "        #FF5349,\r\n" + "        rgba(0,0,0,0.05),\r\n"
//				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
//				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
//				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
//				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
//				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
//				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
//
//		);
//		player2.setTextFill(Color.GOLD);
//		player2.setStyle("-fx-background-color: \r\n" + "        #00BFFF,\r\n" + "        rgba(0,0,0,0.05),\r\n"
//				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
//				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
//				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
//				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
//				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
//				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
//
//		);

//		txtl.getChildren().add(p1);
//		txtr.getChildren().add(p2);
		info = new Label[6];
		for (int i = 0; i < 6; i++) {
			info[i] = new Label();
		}
//		p1.setStyle(Blank_Image_Path);
		update_grid();
		update_players(player);
		update_turn_order(turnorder);
		update_txt(txtl, txtr);
		update_ability();

		AlertBox.display("Instructions",
				"To Move use M then select Direction . \nTo ATTACK use K THEN select a Directions.\nTo ENDTURN use T .\nDirections are : \nW for UP\nS for Down\nA for LEFT\nD for RIGHT ");

	}

	private void update_txt(HBox txtl, HBox txtr) {
		curr_ch_name.setText(game.getCurrentChampion().getName() + " ");
		curr_ch_info.setText(game.getCurrentChampion().toString() + " ");
		txtl.getChildren().clear();
		txtr.getChildren().clear();
		txtl.getChildren().add(p1);
		if (game.isFirstLeaderAbilityUsed()) {
			Vused_leader_ability1.setText("USED");
			Vused_leader_ability1.setTextFill(Color.RED);

		} else {
			Vused_leader_ability1.setText("NOT YET");
			Vused_leader_ability1.setTextFill(Color.GREEN);

		}
		int j = 0;

		for (int i = 0; i < game.getFirstPlayer().getTeam().size(); i++) {
			// ">>>>>>>>>>>>WE ARE HEATR");
			setInfo(p_info[j++], game.getFirstPlayer().getTeam().get(i));
			creat_Image(get_path_of(game.getFirstPlayer().getTeam().get(i).getName()), p_info[j - 1]);
			p_info[j - 1].setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID,
					CornerRadii.EMPTY, new BorderWidths(b_width))));
			if (game.getCurrentChampion().equals(game.getFirstPlayer().getTeam().get(i))) {
				p_info[j - 1].setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(b_width))));
			}
			txtl.getChildren().add(p_info[j - 1]);

		}

		for (int i = 0; i < game.getSecondPlayer().getTeam().size(); i++) {

			setInfo(p_info[j++], game.getSecondPlayer().getTeam().get(i));

			creat_Image(get_path_of(game.getSecondPlayer().getTeam().get(i).getName()), p_info[j - 1]);
			p_info[j - 1].setBorder(new Border(new BorderStroke(Color.DEEPSKYBLUE, BorderStrokeStyle.SOLID,
					CornerRadii.EMPTY, new BorderWidths(b_width))));
			if (game.getCurrentChampion().equals(game.getSecondPlayer().getTeam().get(i))) {
				p_info[j - 1].setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(b_width))));
			}
			txtr.getChildren().add(p_info[j - 1]);
			p_info[j - 1].setVisible(true);

		}

		if (j < p_info.length) {
			p_info[j].setVisible(false);
		}

		if (game.isSecondLeaderAbilityUsed()) {
			Vused_leader_ability2.setText("USED");
			Vused_leader_ability2.setTextFill(Color.RED);

		} else {
			Vused_leader_ability2.setText("NOT YET");
			Vused_leader_ability2.setTextFill(Color.GREEN);

		}
		txtr.getChildren().add(p2);

	}

	private void setInfo(MenuButton menuButton, Champion champion) {
		MenuItem x = new MenuItem();
		menuButton.getItems().clear();
		menuButton.getItems().setAll(x);
		x.setVisible(true);
		x.setStyle("-fx-background-color: black ;-fx-text-fill: white");
		x.setText(champion.toString());

	}

	private void update_turn_order(GridPane player) {

		PriorityQueue tmp1 = game.getTurnOrder();
		PriorityQueue tmp = new PriorityQueue(6);
		int len = tmp1.size();
		int i = 0;
		Champion ch_curr = game.getCurrentChampion();
		while (!tmp1.isEmpty()) {
			Champion curr = (Champion) tmp1.remove();
			tmp.insert(curr);
			if (i < len) {
				creat_Image(get_path_of(curr.getName()), player_turn_order[i++]);
				player_turn_order[i - 1].setVisible(true);

				if (curr.equals(ch_curr)) {

					player_turn_order[i - 1].setBorder(new Border(

							new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
									new BorderWidths(b_width))));
				} else {

					if (game.getFirstPlayer().getTeam().contains(curr))
						player_turn_order[i - 1].setBorder(new Border(new BorderStroke(Color.ORANGERED,
								BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(b_width))));

					else
						player_turn_order[i - 1].setBorder(new Border(new BorderStroke(Color.LIGHTSKYBLUE,
								BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(b_width))));

				}
			} else {
				creat_Image(get_path_of(Blank_Image_Path), player_turn_order[i++]);
			}
		}

		if (i < 6) {
			player_turn_order[i].setVisible(false);
		}
		game.setTurnOrder(tmp);
	}

	private void update_players(GridPane player) {

		for (int i = 0; i < game.getFirstPlayer().getTeam().size(); i++) {
			creat_Image(get_path_of(game.getFirstPlayer().getTeam().get(i).getName()), players[i]);
			if (!game.getFirstPlayer().getTeam().get(i).equals(game.getCurrentChampion()))
				players[i].setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(2))));
			else
				players[i].setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(b_width))));

		}

		for (int i = 0; i < game.getSecondPlayer().getTeam().size(); i++) {
			creat_Image(get_path_of(game.getSecondPlayer().getTeam().get(i).getName()), players[i + 3]);

			if (!game.getSecondPlayer().getTeam().get(i).equals(game.getCurrentChampion()))
				players[i + 3].setBorder(new Border(new BorderStroke(Color.LIGHTSKYBLUE, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(b_width))));
			else
				players[i + 3].setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(b_width))));

		}

	}

	void update_grid() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (board[i][j] instanceof Champion) {
					creat_Image(get_path_of(((Champion) board[i][j]).getName()), btn[i][j]);
					double hel = (double) (((Champion) board[i][j]).getCurrentHP())
							/ (((Champion) board[i][j]).getMaxHP()) * 100;
					DecimalFormat de = new DecimalFormat("#.##");
					btn[i][j].setText("HP:" + de.format(hel) + "%");
					btn[i][j].setTextFill(Color.YELLOW);

				} else if (board[i][j] instanceof Cover) {
					creat_Image(Cover_Image_Path, btn[i][j]);
					btn[i][j].setText(((Cover) board[i][j]).getCurrentHP() + "");
					btn[i][j].setTextFill(Color.RED);
					btn[i][j].setAlignment(Pos.CENTER);

				} else {
					creat_Image((Blank_Image_Path), btn[i][j]);
					btn[i][j].setText("");
				}
				if (board[i][j] instanceof Champion) {

					if (((Champion) board[i][j]).equals(game.getCurrentChampion())) {

						btn[i][j].setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,
								CornerRadii.EMPTY, new BorderWidths(b_width))));
					} else {

						if (game.getFirstPlayer().getTeam().contains((Champion) board[i][j]))
							btn[i][j].setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID,
									CornerRadii.EMPTY, new BorderWidths(b_width))));

						else
							btn[i][j].setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID,
									CornerRadii.EMPTY, new BorderWidths(b_width))));

					}

				} else
					btn[i][j].setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
							CornerRadii.EMPTY, new BorderWidths(b_width))));

//				if (board[i][j] instanceof Champion
//						&& ((Champion) board[i][j]).getName().equals(game.getCurrentChampion().getName())) {
//
//// btn[i][j].setStyle("-fx-border-color: blue;");
//				}

			}
		}
	}

	public static void main(String[] args) {

	}

	boolean board_action(Champion cur, boolean move, boolean attack, Direction d) {

		boolean ret = true;
		try {
			if (move)
				game.move(d);
			else if (attack)
				game.attack(d);
		} catch (NotEnoughResourcesException e1) {
			AlertBox.display("Not Enough Resource Error", "The current champion does not have enough resources ");
			ret = false;
		} catch (UnallowedMovementException e1) {
			AlertBox.display("UnAllowed Movement Error",
					game.hasEffectHelper(cur, "Root") ? "You can not move while being rooted"
							: "The Current Champion cannot move in this Direction , Try another one");
			ret = false;

		} catch (ChampionDisarmedException e2) {
			AlertBox.display("Champion Disarmed ERROR", "The Current Champion Can not attack while being disarmed");
			ret = false;

		}

		return ret;
	}

	Image creat_Image(String url, Button pane) {

		Image image = null;

		image = new Image(url);

		BackgroundSize size = new BackgroundSize(window.getMaxWidth(), window.getMaxHeight(), false, false, false,
				true);
		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);

		Background background = new Background(backgroundimage);

		pane.setBackground(background);

		return image;
	}

	ImageView Image(String url, Button btn) {

		Image image = new Image(url);
		ImageView iv = new ImageView(image);
		btn.setGraphic(iv);
		return iv;
	}

	Image creat_Image(String url, Pane pane) {
		Image image = new Image(url);
		BackgroundSize size = new BackgroundSize(window.getMaxWidth(), window.getMaxHeight(), false, false, false,
				true);
		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
		Background background = new Background(backgroundimage);
		pane.setBackground(background);

		return image;
	}

	String get_path_of(String name) {
		name = name.trim();
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == ' ')
				continue;
			tmp.append(name.charAt(i));
		}
		name = tmp.toString();
		name = name.toLowerCase();
		String ret = "hulk.jpg";
		switch (name) {
		case "captainamerica":
			ret = "captainAmerica.jpg";
			break;
		case "deadpool":
			ret = "deadPool.jpg";
			break;
		case "drstrange":
			ret = "doctorStrange.jpg";
			break;
		case "electro":
			ret = "electro.jpg";
			break;
		case "ghostrider":
			ret = "ghostRider.jpg";
			break;
		case "hela":
			ret = "hela.jpg";
			break;
		case "hulk":
			ret = "hulk.jpg";
			break;
		case "iceman":
			ret = "iceMan.png";
			break;
		case "ironman":
			ret = "ironMan.jpg";
			break;
		case "loki":
			ret = "loki2.jpg";
			break;
		case "quicksilver":
			ret = "quicksilver.jpg";
			break;
		case "spiderman":
			ret = "spiderMan.jpg";
			break;
		case "thor":
			ret = "thor2.jpeg";
			break;
		case "venom":
			ret = "venom.jpg";
			break;
		case "yellowjacket":
			ret = "yellowJacket2.jpg";
			break;
		}

		return ret;
	}

	/*
	 * String get_path_of_Icon(String name) { name = name.trim(); StringBuilder tmp
	 * = new StringBuilder(); for (int i = 0; i < name.length(); i++) { if
	 * (name.charAt(i) == ' ') continue; tmp.append(name.charAt(i)); } name =
	 * tmp.toString(); name = name.toLowerCase(); String ret =
	 * "CaptainAmericaIcon.jpg"; switch (name) { case "captainamerica": ret =
	 * "CaptainAmericaIcon.jpg"; break; case "deadpool": ret = "DeadpoolIcon.jpg";
	 * break; case "drstrange": ret = "doctorStrangeIcon.jpg"; break; case
	 * "electro": ret = "ElectroIcon.jpg"; break; case "ghostrider": ret =
	 * "GhostRiderIcon.jpg"; break; case "hela": ret = "HelaIcon.jpg"; break; case
	 * "hulk": ret = "HulkIcon.jpg"; break; case "iceman": ret = "IcemanIcon.jpg";
	 * break; case "ironman": ret = "IronManIcon.jpg"; break; case "loki": ret =
	 * "LokiIcon.jpg"; break; case "quicksilver": ret = "QuickSilverIcon.jpg";
	 * break; case "spiderman": ret = "SpiderManIcon.jpg"; break; case "thor": ret =
	 * "ThorIcon.jpg"; break; case "venom": ret = "VenomIcon.jpg"; break; case
	 * "yellowjacket": ret = "Yllowjacket.jpg"; break; }
	 * 
	 * return ret; }
	 */
	Image creat_Image(String url, MenuButton pane) {

		Image image = null;

		image = new Image(url);

		BackgroundSize size = new BackgroundSize(window.getMaxWidth(), window.getMaxHeight(), false, false, false,
				true);
		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);

		Background background = new Background(backgroundimage);

		pane.setBackground(background);

		return image;
	}

	void end_scene(Player x) {
		if (x == null)
			return;
		///
		StackPane root = new StackPane();
		creat_Image("background0.jpg", root);
		Label win = new Label(x.getName() + " IS THE WINNER!! ");
		win.setStyle("-fx-font-size: 30px");
		win.setTextFill(Color.AZURE);
		Button playagain = new Button();
		playagain.setPrefSize(100, 50);
		playagain.setTextFill(Color.FUCHSIA);

		playagain.setText("Hit  Me to \nPlay Again!");
		playagain.setStyle("-fx-background-color: black;-fx-text-fill: FUCHSIA");
		VBox vv = new VBox();
		playagain.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				View x = new View();
				try {
					x.start(window);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		vv.getChildren().addAll(win, playagain);
		root.getChildren().add(vv);
		vv.setAlignment(Pos.CENTER);
		Scene sc = new Scene(root);
		window.setHeight(750);
		window.setWidth(1450);
		window.setResizable(false);
		window.setScene(sc);
		window.setTitle("WINNER!!!");
		window.getIcons().add(new Image("logo.jpg"));
		window.show();
	}

	void update_ability() {
		int i = 0;
		for (int a = 0; a < game.getCurrentChampion().getAbilities().size(); a++) {
			abilities[a].setVisible(true);
			abilities[a].setText(game.getCurrentChampion().getAbilities().get(a).getName());
			i++;
		}

		if (i < game.getCurrentChampion().getAbilities().size()) {
			abilities[i++].setVisible(false);
		}
	}

	boolean done;

	boolean ability_work0(int lm) {
		done = false;
		for (Ability x : game.getCurrentChampion().getAbilities()) {

			if (abilities[lm].getText().equals(x.getName())) {
				curr_abi = x;
				switch (x.getCastArea()) {
				case DIRECTIONAL:
					ability = true;
					AlertBox.display("Choose direction", "Choose the direction you want to cast the ability ");
					break;
				case SINGLETARGET:
					Stage tmp = new Stage();
					x0 = 0;
					y0 = 0;
					tmp.getIcons().add(new Image("logo.jpg"));
					VBox vb = new VBox();
					Label info = new Label("Choose Location to Cast the ability");
					vb.setAlignment(Pos.CENTER);
					vb.getChildren().add(info);
					tmp.setResizable(false);
					tmp.setMinHeight(100);
					tmp.setMinWidth(350);

					HBox h1 = new HBox();
					HBox h2 = new HBox();
					vb.getChildren().addAll(h1, h2);
					Label lx = new Label("X: ");
					Label ly = new Label("Y: ");

					ChoiceBox<Integer> yy = new ChoiceBox<>();
					ChoiceBox<Integer> xx = new ChoiceBox<>();

					for (int i = 0; i < 5; i++) {
						xx.getItems().add(i + 1);
						yy.getItems().add(i + 1);
					}
					xx.setValue(1);
					yy.setValue(1);
					h1.getChildren().add(lx);
					h1.getChildren().add(xx);
					h2.getChildren().add(ly);
					h2.getChildren().add(yy);

					Button apply = new Button("Apply");
					vb.getChildren().add(apply);
					Scene sc = new Scene(vb);

					tmp.setScene(sc);

					lx.setStyle("-fx-background-color: \r\n" + "        #090a0c,\r\n"
							+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
							+ "        linear-gradient(#20262b, #191d22),\r\n"
							+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\r\n"
							+ "    -fx-background-radius: 5,4,3,5;\r\n" + "    -fx-background-insets: 0,1,2,0;\r\n"
							+ "    -fx-text-fill: white;\r\n"
							+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
							+ "    -fx-font-family: \"Arial\";\r\n"
							+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);\r\n" + "    -fx-font-size: 12px;\r\n"
							+ "    -fx-padding: 10 20 10 20;");
					ly.setStyle("-fx-background-color: \r\n" + "        #090a0c,\r\n"
							+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
							+ "        linear-gradient(#20262b, #191d22),\r\n"
							+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\r\n"
							+ "    -fx-background-radius: 5,4,3,5;\r\n" + "    -fx-background-insets: 0,1,2,0;\r\n"
							+ "    -fx-text-fill: white;\r\n"
							+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
							+ "    -fx-font-family: \"Arial\";\r\n"
							+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);\r\n" + "    -fx-font-size: 12px;\r\n"
							+ "    -fx-padding: 10 20 10 20;");

					apply.setOnAction(eve -> {
						try {
							game.castAbility(x, x0, y0);
							done = true;

						} catch (NotEnoughResourcesException e1) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion does not have enough resources ");
							e1.printStackTrace();
						} catch (AbilityUseException e2) {
							AlertBox.display("NotEnoughResources Error",
									"The current champion can not use this ability ");
						} catch (InvalidTargetException e3) {
							AlertBox.display("Invalid Target", "Can not apply the ability on this target. ");

						} catch (CloneNotSupportedException e4) {

						}

						tmp.close();

					});
					xx.getSelectionModel().selectedIndexProperty()
							.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
								x0 = ((int) new_val);
							});
					yy.getSelectionModel().selectedIndexProperty()
							.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
								y0 = ((int) new_val);
							});

					tmp.show();
					break;

				default:
					try {
						game.castAbility(curr_abi);
						done = true;

					} catch (NotEnoughResourcesException e1) {
						AlertBox.display("NotEnoughResources Error",
								"The current champion does not have enough resources ");
						// e1.printStackTrace();
					} catch (AbilityUseException e2) {
						AlertBox.display("NotEnoughResources Error", "The current champion can not use this ability ");
					} catch (CloneNotSupportedException e4) {

					}
				}

				break;
			}
		}

		return done;
	}

	Player check_if_dead_and_return_winner() {
		if (game.getFirstPlayer().getTeam().size() == 0)
			return game.getSecondPlayer();
		if (game.getSecondPlayer().getTeam().size() == 0)
			return game.getFirstPlayer();
		return null;
	}

}

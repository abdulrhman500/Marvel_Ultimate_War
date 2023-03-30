package Graphics;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
//
//import org.junit.validator.PublicClassValidator;

import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import model.world.Hero;

public class View extends Application implements EventHandler<ActionEvent> {
	static int ctr2 = 0;
	static boolean clicked = false, clicked2 = false, clicked3 = false, ability = false, f, fff = true;
	static boolean[] picked = new boolean[15];
	static Label label = new Label();
	static HBox g;
	Player p1;
	Player p2;
	static Game game;
	static Object[][] board;
	Stage stage;
	Scene scene1, scene2, scene3, scene4, scene5, scene6, scene7,tmp,endScene;
	Direction[] dir = new Direction[] { Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT };
	static VBox abilities;
	static MediaPlayer mediaPlayer;
	static boolean ff;

	public static void main(String[] args) {
		launch(args);
	}

	public void scene1() throws MalformedURLException {
		File file = new File("intro.mp4");
		Media media = new Media(file.toURL().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitHeight(750);
		mediaView.setFitWidth(1400);
		mediaPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				scene2();

			}
		});
		Button skipButton = new Button("Skip");
		skipButton.setTranslateX(1200);
		skipButton.setTranslateY(650);
		skipButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		skipButton.setOnAction(e -> {
			mediaPlayer.stop();
			// stage.setScene(scene2);
			scene2();
		});

		Group root = new Group();
		root.getChildren().add(mediaView);
		root.getChildren().add(skipButton);
		scene1 = new Scene(root, 1300, 750);
	}

	public void scene2() {
		Label label1 = new Label("First Player :");
		label1.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		label1.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #a6b5c9,\r\n"
				+ "        linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\r\n"
				+ "        linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 7 30 7 30;\r\n" + "    -fx-text-fill: #242d35;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 12px;\r\n"
				+ "    -fx-text-fill: white;");
		label1.setTextFill(Color.LIGHTCYAN);
		TextField input = new TextField();
		input.setPromptText("Player 1");
		HBox hBox = new HBox();
		Button startButton = new Button("Start");
		hBox.getChildren().addAll(label1, input);
		hBox.setSpacing(10);
		hBox.setTranslateX(100);
		hBox.setTranslateY(200);
		Label label2 = new Label("Second Player :");
		label2.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #a6b5c9,\r\n"
				+ "        linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\r\n"
				+ "        linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 7 30 7 30;\r\n" + "    -fx-text-fill: #242d35;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 12px;\r\n"
				+ "    -fx-text-fill: white;");
		label2.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		label2.setTextFill(Color.LIGHTCYAN);
		TextField input2 = new TextField();
		input2.setPromptText("Player 2");
		HBox hBox2 = new HBox();
		hBox2.getChildren().addAll(label2, input2);
		hBox2.setSpacing(10);
		hBox2.setTranslateX(70);
		hBox2.setTranslateY(250);
		startButton.setOnAction(e -> {
			p1 = new Player(input.getText());
			p2 = new Player(input2.getText());
			if (p1.getName().length() == 0) {
				AlertBox.display("Player 1 has no name ", "Please enter the name of the first player !");
			} else if (p2.getName().length() == 0) {
				AlertBox.display("Player 2 has no name ", "Please enter the name of the second player !");
			} else if (p2.getName().equals(p1.getName())) {
				AlertBox.display("Name already taken", "Same name of Player 1 , Please change it !");
			} else {

				game = new Game(p1, p2);
				board = game.getBoard();
				try {
					game.loadAbilities("Abilities.csv");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					game.loadChampions("Champions.csv");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// stage.setScene(scene3);
				scene3();
			}

		});

		startButton.setTranslateX(250);
		startButton.setTranslateY(300);
		startButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		startButton.setTextFill(Color.RED);
		Group layout = new Group();
		layout.getChildren().add(hBox);
		layout.getChildren().add(hBox2);
		layout.getChildren().add(startButton);
		StackPane stackPane = new StackPane();
		Image image = new Image("54702.jpg");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(1300);
		imageView.setFitHeight(750);
		stackPane.getChildren().add(imageView);
		stackPane.getChildren().add(layout);
		scene2 = new Scene(stackPane, 1300, 750);
		stage.setScene(scene2);
	}

	public void scene3() {
		ImageView[] arr = new ImageView[15];
		ImageView[] arr2 = new ImageView[15];
		Group gp = new Group();
		Image capAmerica = new Image("CaptainAmericaIcon.jpg");
		ImageView capAmericaView = new ImageView(capAmerica);
		arr[0] = capAmericaView;
		Image dp = new Image("DeadpoolIcon.jpg");
		ImageView dpView = new ImageView(dp);
		arr[1] = dpView;
		Image ds = new Image("doctorStrangeIcon.jpg");
		ImageView dsView = new ImageView(ds);
		arr[2] = dsView;
		Image elctro = new Image("ElectroIcon.jpg");
		ImageView electroView = new ImageView(elctro);
		arr[3] = electroView;
		Image gr = new Image("GhostRiderIcon.jpg");
		ImageView grView = new ImageView(gr);
		arr[4] = grView;
		Image hela = new Image("HelaIcon.jpg");
		ImageView helaView = new ImageView(hela);
		arr[5] = helaView;
		Image hulk = new Image("HulkIcon.jpg");
		ImageView hulkView = new ImageView(hulk);
		arr[6] = hulkView;
		Image im = new Image("IcemanIcon.jpg");
		ImageView imView = new ImageView(im);
		arr[7] = imView;
		Image ironm = new Image("IronManIcon.jpg");
		ImageView ironmView = new ImageView(ironm);
		arr[8] = ironmView;
		Image loki = new Image("LokiIcon.jpg");
		ImageView lokiView = new ImageView(loki);
		arr[9] = lokiView;
		Image qs = new Image("QuickSilverIcon.jpg");
		ImageView qsView = new ImageView(qs);
		arr[10] = qsView;
		Image sm = new Image("SpiderManIcon.jpg");
		ImageView smView = new ImageView(sm);
		arr[11] = smView;
		Image thor = new Image("ThorIcon.jpg");
		ImageView thorView = new ImageView(thor);
		arr[12] = thorView;
		Image venom = new Image("VenomIcon.jpg");
		ImageView venomView = new ImageView(venom);
		arr[13] = venomView;
		Image yj = new Image("Yllowjacket.jpg");
		ImageView yjView = new ImageView(yj);
		arr[14] = yjView;
		Image capAmerica2 = new Image("captainAmerica.gif");
		ImageView capAmericaView2 = new ImageView(capAmerica2);
		arr2[0] = capAmericaView2;
		Image dp2 = new Image("deadPool.gif");
		ImageView dpView2 = new ImageView(dp2);
		arr2[1] = dpView2;
		Image ds2 = new Image("doctorStrange.jpg");
		ImageView dsView2 = new ImageView(ds2);
		arr2[2] = dsView2;
		Image elctro2 = new Image("electro.jpg");
		ImageView electroView2 = new ImageView(elctro2);
		arr2[3] = electroView2;
		Image gr2 = new Image("ghostRider.jpg");
		ImageView grView2 = new ImageView(gr2);
		arr2[4] = grView2;
		Image hela2 = new Image("hela.jpg");
		ImageView helaView2 = new ImageView(hela2);
		arr2[5] = helaView2;
		Image hulk2 = new Image("hulk.jpg");
		ImageView hulkView2 = new ImageView(hulk2);
		arr2[6] = hulkView2;
		Image im2 = new Image("iceMan.png");
		ImageView imView2 = new ImageView(im2);
		arr2[7] = imView2;
		Image ironm2 = new Image("ironMan.gif");
		ImageView ironmView2 = new ImageView(ironm2);
		arr2[8] = ironmView2;
		Image loki2 = new Image("loki2.jpg");
		ImageView lokiView2 = new ImageView(loki2);
		arr2[9] = lokiView2;
		Image qs2 = new Image("quicksilver.jpg");
		ImageView qsView2 = new ImageView(qs2);
		arr2[10] = qsView2;
		Image sm2 = new Image("spidermanGif.gif");
		ImageView smView2 = new ImageView(sm2);
		arr2[11] = smView2;
		Image thor2 = new Image("thor2.jpeg");
		ImageView thorView2 = new ImageView(thor2);
		arr2[12] = thorView2;
		Image venom2 = new Image("venom.jpg");
		ImageView venomView2 = new ImageView(venom2);
		arr2[13] = venomView2;
		Image yj2 = new Image("yellowJacket.jpg");
		ImageView yjView2 = new ImageView(yj2);
		arr2[14] = yjView2;
		int ctr = 0;
		Button[] arr3 = new Button[15];
		for (int i = 0; i < 15; i++) {
			arr3[i] = new Button();
			arr3[i].setMinHeight(100);
			arr3[i].setMinWidth(80);
			arr[i].setFitHeight(100);
			arr[i].setFitWidth(80);
			arr3[i].setGraphic(arr[i]);
			if (i == 5 || i == 10)
				ctr = 0;
			arr3[i].setTranslateX(ctr * 100);
			ctr++;
			if (i > 4 && i < 10) {
				arr3[i].setTranslateY(150);
			} else if (i > 9) {
				arr3[i].setTranslateY(300);
			}
			gp.getChildren().add(arr3[i]);
		}
		gp.setTranslateX(-350);
		gp.setTranslateY(100);
		StackPane st = new StackPane();
		Image image = new Image("background.jpg");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(750);
		imageView.setFitWidth(1300);
		st.getChildren().add(imageView);
		st.getChildren().add(gp);
		for (int i = 0; i < 15; i++) {
			arr2[i].setTranslateX(400);
			arr2[i].setTranslateY(-50);
			arr2[i].setFitHeight(500);
			if (i == 1)
				arr2[i].setFitWidth(400);
			else
				arr2[i].setFitWidth(400);
		}
		arr3[0].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("capAmerica intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[0]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(0));
							picked[0] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[0]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(0));
							picked[0] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Captain America \n" + "Type : Hero \n" + "maxHp 1500 \n" + "mana 1000 \n"
						+ "Actions 6 \n" + "Speed 80 \n" + "Attack Range 1 \n" + "Attack Damage 100 \n"
						+ "Abilities : 1) " + "Shield throw \n" + "2) I can do this all day \n" + "3) Shield up \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[0]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[0]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Captain America")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Captain America")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[0] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[1].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("deadPool intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[1]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(1));
							picked[1] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[1]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(1));
							picked[1] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Deadpool \n" + "Type : AntiHero \n" + "maxHp 1350 \n" + "mana 700 \n"
						+ "Actions 6 \n" + "Speed 80 \n" + "Attack Range 3 \n" + "Attack Damage 90 \n"
						+ "Abilities : 1) " + "Try Harder \n" + "2) 8 bullets left \n" + "3) Can't Catch Me \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[1]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[1]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Deadpool")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Deadpool")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[1] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[2].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("drStrange ability 1.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[2]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(2));
							picked[2] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[2]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(2));
							picked[2] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText(
						"Name : Dr.Strange \n" + "Type : Hero \n" + "maxHp 1100 \n" + "mana 1500 \n" + "Actions 6 \n"
								+ "Speed 60 \n" + "Attack Range 2 \n" + "Attack Damage 60 \n" + "Abilities : 1) "
								+ "The eye of agamotto \n" + "2) Thousand Hand \n" + "3) Mirror Dimension \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[2]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[2]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Dr Strange")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Dr Strange")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[2] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[3].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("Electro intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[3]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(3));
							picked[3] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[3]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(3));
							picked[3] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Electro \n" + "Type : Villain \n" + "maxHp 1200 \n" + "mana 1200 \n"
						+ "Actions 5 \n" + "Speed 75 \n" + "Attack Range 3 \n" + "Attack Damage 110 \n"
						+ "Abilities : 1) " + "Fully Charged \n" + "2) EMP \n" + "3) Lightning Strike \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[3]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[3]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Electro")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Electro")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[3] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[4].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("ghostRider intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[4]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(4));
							picked[4] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[4]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(4));
							picked[4] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Ghost Rider \n" + "Type : AntiHero \n" + "maxHp 1800 \n" + "mana 600 \n"
						+ "Actions 6 \n" + "Speed 85 \n" + "Attack Range 1 \n" + "Attack Damage 140 \n"
						+ "Abilities : 1) " + "Death stare \n" + "2) Fire Breath \n" + "3) Chain Whip \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[4]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[4]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Ghost Rider")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Ghost Rider")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[4] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[5].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("hela intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[5]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(5));
							picked[5] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[5]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(5));
							picked[5] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Hela \n" + "Type : Villain \n" + "maxHp 1500 \n" + "mana 750 \n" + "Actions 5 \n"
						+ "Speed 75 \n" + "Attack Range 1 \n" + "Attack Damage 150 \n" + "Abilities : 1) "
						+ "Godess of Death \n" + "2) Thord Shield \n" + "3) Thorn Shower \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[5]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[5]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Hela")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Hela")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[5] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[6].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("hulk intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[6]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(6));
							picked[6] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[6]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(6));
							picked[6] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Hulk \n" + "Type : Hero \n" + "maxHp 2250 \n" + "mana 550 \n" + "Actions 5 \n"
						+ "Speed 55 \n" + "Attack Range 1 \n" + "Attack Damage 200 \n" + "Abilities : 1) " + "Rage \n"
						+ "2) Hulk Smash \n" + "3) Sun is getting real low \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[6]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[6]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Hulk")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Hulk")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[6] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[7].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("iceMan intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[7]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(7));
							picked[7] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[7]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(7));
							picked[7] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Iceman \n" + "Type : Hero \n" + "maxHp 1000 \n" + "mana 900 \n" + "Actions 5 \n"
						+ "Speed 65 \n" + "Attack Range 2 \n" + "Attack Damage 120 \n" + "Abilities : 1) "
						+ "Frost bite \n" + "2) SubZero \n" + "3) Hail Storm \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[7]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[7]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Iceman")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Iceman")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[7] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[8].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("ironMan intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[8]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(8));
							picked[8] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[8]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(8));
							picked[8] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Ironman \n" + "Type : Hero \n" + "maxHp 1200 \n" + "mana 800 \n" + "Actions 7 \n"
						+ "Speed 85 \n" + "Attack Range 3 \n" + "Attack Damage 90 \n" + "Abilities : 1) "
						+ "I am Ironman \n" + "2) Unibeam \n" + "3) 3000 \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[8]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[8]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Ironman")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Ironman")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[8] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[9].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("loki intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[9]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(9));
							picked[9] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[9]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(9));
							picked[9] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Loki \n" + "Type : Villain \n" + "maxHp 1150 \n" + "mana 900 \n" + "Actions 5 \n"
						+ "Speed 70 \n" + "Attack Range 1 \n" + "Attack Damage 150 \n" + "Abilities : 1) "
						+ "God of Mischief \n" + "2) The Hidden Dagger \n" + "3) Fake Death \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[9]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[9]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Loki")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Loki")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[9] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[10].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("capAmerica move.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[10]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(10));
							picked[10] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[10]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(10));
							picked[10] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Quicksilver \n" + "Type : Villain \n" + "maxHp 1200 \n" + "mana 650 \n"
						+ "Actions 8 \n" + "Speed 99 \n" + "Attack Range 1 \n" + "Attack Damage 70 \n"
						+ "Abilities : 1) " + "Time in a bottle \n" + "2) Good as new \n" + "3) 1 sec 100 punch \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[10]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[10]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Quicksilver")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Quicksilver")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[10] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[11].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("spiderMan intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[11]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(11));
							picked[11] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[11]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(11));
							picked[11] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Spiderman \n" + "Type : Hero \n" + "maxHp 1400 \n" + "mana 750 \n"
						+ "Actions 7 \n" + "Speed 85 \n" + "Attack Range 1 \n" + "Attack Damage 120 \n"
						+ "Abilities : 1) " + "give me that \n" + "2) web trap \n" + "3) Spiderverse \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[11]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[11]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Spiderman")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Spiderman")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[11] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[12].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("Thor intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[12]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(12));
							picked[12] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[12]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(12));
							picked[12] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Thor \n" + "Type : Hero \n" + "maxHp 1800 \n" + "mana 800 \n" + "Actions 7 \n"
						+ "Speed 90 \n" + "Attack Range 1 \n" + "Attack Damage 130 \n" + "Abilities : 1) "
						+ "God of Thunder \n" + "2) Mjollnir Throw \n" + "3) Bring Me Thanos \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[12]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[12]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Thor")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Thor")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[12] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[13].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("venom intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[13]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(13));
							picked[13] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[13]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(13));
							picked[13] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Venom \n" + "Type : AntiHero \n" + "maxHp 1650 \n" + "mana 700 \n"
						+ "Actions 5 \n" + "Speed 70 \n" + "Attack Range 1 \n" + "Attack Damage 140 \n"
						+ "Abilities : 1) " + "Head Bite \n" + "2) We are venom \n" + "3) Symbiosis \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[13]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[13]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Venom")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Venom")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[13] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});
			}
		});
		arr3[14].setOnAction(e -> {
			if (!clicked) {
				if (mediaPlayer != null)
					mediaPlayer.setMute(true);
				Media sound = new Media(new File("yellowJacket intro.mp3").toURI().toString());
				mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.play();
				clicked = true;
				Label label = new Label();
				Group gp2 = new Group();
				Button pickButton = new Button("Pick");
				pickButton.setOnAction(e2 -> {
					if (p1.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[14]) {
							label.setText("Picked in " + p1.getName() + "'s team ");
							p1.getTeam().add(getChamp(14));
							picked[14] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else if (p2.getTeam().size() < 3) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						if (!picked[14]) {
							label.setText("Picked in " + p2.getName() + "'s team ");
							p2.getTeam().add(getChamp(14));
							picked[14] = true;
						} else {
							label.setText("Champion is already picked !");
						}
					} else {
						new AlertBox().display("Team is full", "The Player's Team Already Contains three Champions");
					}
				});
				MenuItem popUp = new MenuItem();
				popUp.setText("Name : Yellow Jacket \n" + "Type : Villain \n" + "maxHp 1050 \n" + "mana 800 \n"
						+ "Actions 6 \n" + "Speed 60 \n" + "Attack Range 2 \n" + "Attack Damage 80 \n"
						+ "Abilities : 1) " + "Laser Sting \n" + "2) QuANTaMANia \n" + "3) Pym Particle Upsize \n");
				MenuButton showButton = new MenuButton("Show information");
				popUp.setStyle(" -fx-background-color: \r\n"
						+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
						+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
						+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
						+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
						+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
						+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
						+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
						+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
				showButton.getItems().setAll(popUp);
				gp2.getChildren().add(arr2[14]);
				gp2.getChildren().add(label);
				label.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
				label.setTextFill(Color.RED);
				gp2.getChildren().add(pickButton);
				gp2.setTranslateX(100);
				gp2.setTranslateY(50);
				label.setTranslateX(-100);
				label.setTranslateY(-50);
				Button removeButton = new Button("Remove");
				removeButton.setTranslateX(650);
				removeButton.setTranslateY(450);
				removeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
				removeButton.setOnAction(e2 -> {
					if (picked[14]) {
						label.setStyle(" -fx-background-color: \r\n"
								+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
								+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
								+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
								+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
								+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
								+ "    -fx-background-insets: 0,1,4,5,6;\r\n"
								+ "    -fx-background-radius: 9,8,5,4,3;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
								+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
								+ "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
								+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
						label.setText("Removed");
						for (int i = 0; i < p1.getTeam().size(); i++) {
							if (p1.getTeam().get(i).getName().equals("Yellow Jacket")) {
								p1.getTeam().remove(i);
								break;
							}
						}
						for (int i = 0; i < p2.getTeam().size(); i++) {
							if (p2.getTeam().get(i).getName().equals("Yellow Jacket")) {
								p2.getTeam().remove(i);
								break;
							}
						}
						picked[14] = false;
					} else {
						new AlertBox().display("Champion is not picked", "You can't remove a non-picked champion !");
					}
				});
				gp2.getChildren().add(removeButton);
				pickButton.setTranslateX(400);
				pickButton.setTranslateY(450);
				showButton.setTranslateX(400);
				showButton.setTranslateY(550);
				showButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"
						+ "-fx-font-size: 10px;" + "-fx-font-family: \"Arial Black\";" + "-fx-fill: #818181;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 )"

				);
				showButton.setTextFill(Color.RED);
				gp2.getChildren().add(showButton);
				pickButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				st.getChildren().add(gp2);
				Button closeButton = new Button("Close");
				closeButton.setTranslateX(500);
				closeButton.setTranslateY(450);
				closeButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n"
						+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

				);
				gp2.getChildren().add(closeButton);
				closeButton.setOnAction(e3 -> {
					mediaPlayer.stop();
					clicked = false;
					st.getChildren().remove(gp2);
				});

			}
		});

		Button endButton = new Button("Continue");
		endButton.setTranslateX(600);
		endButton.setTranslateY(500);

		endButton.setOnAction(e -> {
			mediaPlayer.stop();
			if (p1.getTeam().size() < 3 || p2.getTeam().size() < 3) {
				new AlertBox().display("Team is not completed ", "Please select 3 Champions for each team");
			} else {
				clicked = false;
				scene4();
			}
		});
		Group gp3 = new Group();
		endButton.setStyle(

				"-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
						+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
						+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
						+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
						+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
						+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
						+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		gp3.getChildren().add(endButton);
		gp3.setTranslateX(450);
		gp3.setTranslateY(340);
		st.getChildren().add(gp3);
		scene3 = new Scene(st, 1300, 750);
		stage.setScene(scene3);
	}

	public void scene4() {
		clicked2 = false;
		clicked3 = false;
		StackPane st = new StackPane();
		Image image = new Image("background.jpg");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(750);
		imageView.setFitWidth(1300);
		st.getChildren().add(imageView);
		Group gp1 = new Group();
		Label label1 = new Label();
		label1.setText("Choose leader for " + p1.getName() + "'s team");
		Group gp2 = new Group();
		Label label2 = new Label();
		label2.setText("Choose leader for " + p2.getName() + "'s team");
		RadioButton rb1 = new RadioButton(p1.getTeam().get(0).getName());
		RadioButton rb2 = new RadioButton(p1.getTeam().get(1).getName());
		RadioButton rb3 = new RadioButton(p1.getTeam().get(2).getName());
		rb1.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		rb2.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		rb3.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		rb1.setOnAction(e -> {
			if (!clicked2) {
				p1.setLeader(p1.getTeam().get(0));
				rb2.setDisable(true);
				rb3.setDisable(true);
				clicked2 = true;
			} else {
				rb2.setDisable(false);
				rb3.setDisable(false);
				p1.setLeader(null);
				clicked2 = false;
			}
		});
		rb2.setOnAction(e -> {
			if (!clicked2) {
				p1.setLeader(p1.getTeam().get(1));
				rb1.setDisable(true);
				rb3.setDisable(true);
				clicked2 = true;
			} else {
				rb1.setDisable(false);
				rb3.setDisable(false);
				p1.setLeader(null);
				clicked2 = false;
			}
		});
		rb3.setOnAction(e -> {
			if (!clicked2) {
				p1.setLeader(p1.getTeam().get(2));
				rb2.setDisable(true);
				rb1.setDisable(true);
				clicked2 = true;
			} else {
				rb2.setDisable(false);
				rb1.setDisable(false);
				p1.setLeader(null);
				clicked2 = false;
			}
		});
		RadioButton rb4 = new RadioButton(p2.getTeam().get(0).getName());
		RadioButton rb5 = new RadioButton(p2.getTeam().get(1).getName());
		RadioButton rb6 = new RadioButton(p2.getTeam().get(2).getName());
		rb4.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		rb5.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		rb6.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		rb4.setOnAction(e -> {
			if (!clicked3) {
				p2.setLeader(p2.getTeam().get(0));
				rb5.setDisable(true);
				rb6.setDisable(true);
				clicked3 = true;
			} else {
				rb5.setDisable(false);
				rb6.setDisable(false);
				p2.setLeader(null);
				clicked3 = false;
			}
		});

		rb5.setOnAction(e -> {
			if (!clicked3) {
				p2.setLeader(p2.getTeam().get(1));
				rb4.setDisable(true);
				rb6.setDisable(true);
				clicked3 = true;
			} else {
				rb4.setDisable(false);
				rb6.setDisable(false);
				p2.setLeader(null);
				clicked3 = false;
			}
		});

		rb6.setOnAction(e -> {
			if (!clicked3) {
				p2.setLeader(p2.getTeam().get(2));
				rb5.setDisable(true);
				rb4.setDisable(true);
				clicked3 = true;
			} else {
				rb5.setDisable(false);
				rb4.setDisable(false);
				p2.setLeader(null);
				clicked3 = false;
			}
		});
		gp1.getChildren().addAll(label1, rb1, rb2, rb3);
		label1.setTranslateY(-100);
		label1.setStyle(" -fx-background-color: \r\n"
				+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
				+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
				+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
				+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
				+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
				+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
				+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
				+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);;");
		label2.setStyle(" -fx-background-color: \r\n"
				+ "        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n"
				+ "        linear-gradient(#020b02, #3a3a3a),\r\n"
				+ "        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n"
				+ "        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n"
				+ "        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n"
				+ "    -fx-background-insets: 0,1,4,5,6;\r\n" + "    -fx-background-radius: 9,8,5,4,3;\r\n"
				+ "    -fx-padding: 15 30 15 30;\r\n" + "    -fx-font-family: \"Helvetica\";\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;\r\n"
				+ "    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);"

		);
		rb1.setTranslateY(0);
		rb2.setTranslateY(100);
		rb3.setTranslateY(200);
		gp2.getChildren().addAll(label2, rb4, rb5, rb6);
		label2.setTranslateY(-100);
		rb4.setTranslateY(0);
		rb5.setTranslateY(100);
		rb6.setTranslateY(200);
		gp1.setTranslateX(-200);
		gp2.setTranslateX(200);
		Group gp3 = new Group();
		Button fightButton = new Button("NEXT");
		fightButton.setOnAction(e -> {
			if (p1.getLeader() == null || p2.getLeader() == null) {
				new AlertBox().display("Leaders are not declared", "Please choose the leader of each player !");
			} else {
				tmpScene();
			}
		});
		
		fightButton.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		fightButton.setMinWidth(150);
		fightButton.setMinHeight(80);
		Button backButton = new Button("BACK");
		backButton.setMinHeight(80);
		backButton.setMinWidth(150);
		backButton.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		backButton.setTranslateX(-300);
		backButton.setOnAction(e -> {
			scene3();
		});
		gp3.setTranslateX(350);
		gp3.setTranslateY(300);
		gp3.getChildren().add(fightButton);
		gp3.getChildren().add(backButton);
		st.getChildren().add(gp1);
		st.getChildren().add(gp2);
		st.getChildren().add(gp3);
		scene4 = new Scene(st, 1300, 750);
		stage.setScene(scene4);
	}
	static boolean mode = false;
	static int mode2;
	public void tmpScene() {
		StackPane st = new StackPane();
		Image im = new Image("54669.jpg");
		ImageView imageView = new ImageView(im);
		imageView.setFitHeight(750);
		imageView.setFitWidth(1300);
		st.getChildren().add(imageView);
		Image im2 = new Image("mode1.png");
		ImageView imageView2 = new ImageView(im2);
		Image im3 = new Image("mode2.png");
		ImageView imageView3 = new ImageView(im3);
		imageView2.setFitWidth(500);
		imageView2.setFitHeight(400);
		imageView3.setFitWidth(500);
		imageView3.setFitHeight(400);
		imageView2.setTranslateX(-400);
		imageView3.setTranslateX(400);
		RadioButton rb1 = new RadioButton("Mouse Mode");
		RadioButton rb2 = new RadioButton("KeyBoard Mode");
		mode=false;
		rb1.setOnAction(e -> {
			if (!mode) {
				mode2=1;
				rb2.setDisable(true);
				mode = true;
			} else {
				rb2.setDisable(false);
				mode = false;
			}
		});
		rb2.setOnAction(e -> {
			if (!mode) {
				mode2=2;
				rb1.setDisable(true);
				mode = true;
			} else {
				rb1.setDisable(false);
				mode = false;
			}
		});
		Button fightButton = new Button("INTO THE BATTLE");
		fightButton.setOnAction(e -> {
			if (!mode) {
				new AlertBox().display("No Mode is selected", "Please select one of the two modes");
			} else {
				try {
					scene5();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		fightButton.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		fightButton.setMinWidth(150);
		fightButton.setMinHeight(80);
		Group gp3 = new Group();
		gp3.setTranslateX(380);
		gp3.setTranslateY(300);
		gp3.getChildren().add(fightButton);
		st.getChildren().add(imageView3);
		st.getChildren().add(imageView2);
		rb1.setTranslateX(-400);
		rb1.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		rb2.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		rb2.setTranslateX(400);
		rb1.setTranslateY(-300);
		rb2.setTranslateY(-300);
		st.getChildren().add(rb1);
		st.getChildren().add(rb2);
		st.getChildren().add(gp3);
		tmp = new Scene(st,1300,750);
		stage.setScene(tmp);
	}

	public void scene5() throws MalformedURLException {
		File file = new File("loading.mp4");
		Media media = new Media(file.toURL().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitHeight(750);
		mediaView.setFitWidth(1400);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				if(mode2==1) {
				try {
					scene6();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					new board(stage, game).display();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		});
		Button skipButton = new Button("Skip");
		skipButton.setTranslateX(1200);
		skipButton.setTranslateY(650);
		skipButton.setStyle("-fx-background-color: \r\n" + "        #ecebe9,\r\n" + "        rgba(0,0,0,0.05),\r\n"
				+ "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);" + "-fx-background-insets: 0,9 9 8 9,9,10,11;"
				+ "-fx-background-radius: 50;" + "-fx-padding: 15 30 15 30;" + "-fx-font-family: \"Helvetica\";"
				+ "-fx-font-size: 18px;" + "-fx-text-fill: #311c09;"
				+ "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"

		);
		skipButton.setOnAction(e -> {
			mediaPlayer.stop();
			if(mode2==1) {
				try {
					scene6();
				} catch (MalformedURLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			} else {
				try {
					new board(stage, game).display();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
		});
		Group root = new Group();
		root.getChildren().add(mediaView);
		root.getChildren().add(skipButton);
		scene5 = new Scene(root, 1300, 750);
		stage.setScene(scene5);
		game.excute();
	}

static Label leader1,leader2;
	public void scene6() throws MalformedURLException {
		Button[] playBoard = new Button[25];
		Group gp = new Group();
		updateGrid(playBoard, 0, gp);
		playBoard[0].setOnAction(e -> {
			if (board[0][0] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[0][0])).getCurrentHP());
			} else if (board[0][0] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[0][0])).getName(),
						((Champion) (board[0][0])).toString());
			}
		});
		playBoard[1].setOnAction(e -> {
			if (board[0][1] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[0][1])).getCurrentHP());
			} else if (board[0][1] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[0][1])).getName(),
						((Champion) (board[0][1])).toString());
			}
		});
		playBoard[2].setOnAction(e -> {
			if (board[0][2] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[0][2])).getCurrentHP());
			} else if (board[0][2] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[0][2])).getName(),
						((Champion) (board[0][2])).toString());
			}
		});
		playBoard[3].setOnAction(e -> {
			if (board[0][3] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[0][3])).getCurrentHP());
			} else if (board[0][3] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[0][3])).getName(),
						((Champion) (board[0][3])).toString());
			}
		});
		playBoard[4].setOnAction(e -> {
			if (board[0][4] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[0][4])).getCurrentHP());
			} else if (board[0][4] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[0][4])).getName(),
						((Champion) (board[0][4])).toString());
			}
		});
		playBoard[5].setOnAction(e -> {
			if (board[1][0] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[1][0])).getCurrentHP());
			} else if (board[1][0] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[1][0])).getName(),
						((Champion) (board[1][0])).toString());
			}
		});
		playBoard[6].setOnAction(e -> {
			if (board[1][1] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[1][1])).getCurrentHP());
			} else if (board[1][1] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[1][1])).getName(),
						((Champion) (board[1][1])).toString());
			}
		});
		playBoard[7].setOnAction(e -> {
			if (board[1][2] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[1][2])).getCurrentHP());
			} else if (board[1][2] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[1][2])).getName(),
						((Champion) (board[1][2])).toString());
			}
		});
		playBoard[8].setOnAction(e -> {
			if (board[1][3] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[1][3])).getCurrentHP());
			} else if (board[1][3] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[1][3])).getName(),
						((Champion) (board[1][3])).toString());
			}
		});
		playBoard[9].setOnAction(e -> {
			if (board[1][4] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[1][4])).getCurrentHP());
			} else if (board[1][4] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[1][4])).getName(),
						((Champion) (board[1][4])).toString());
			}
		});
		playBoard[10].setOnAction(e -> {
			if (board[2][0] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[2][0])).getCurrentHP());
			} else if (board[2][0] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[2][0])).getName(),
						((Champion) (board[2][0])).toString());
			}
		});
		playBoard[11].setOnAction(e -> {
			if (board[2][1] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[2][1])).getCurrentHP());
			} else if (board[2][1] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[2][1])).getName(),
						((Champion) (board[2][1])).toString());
			}
		});
		playBoard[12].setOnAction(e -> {
			if (board[2][2] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[2][2])).getCurrentHP());
			} else if (board[2][2] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[2][2])).getName(),
						((Champion) (board[2][2])).toString());
			}
		});
		playBoard[13].setOnAction(e -> {
			if (board[2][3] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[2][3])).getCurrentHP());
			} else if (board[2][3] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[2][3])).getName(),
						((Champion) (board[2][3])).toString());
			}
		});
		playBoard[14].setOnAction(e -> {
			if (board[2][4] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[2][4])).getCurrentHP());
			} else if (board[2][4] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[2][4])).getName(),
						((Champion) (board[2][4])).toString());
			}
		});
		playBoard[15].setOnAction(e -> {
			if (board[3][0] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[3][0])).getCurrentHP());
			} else if (board[3][0] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[3][0])).getName(),
						((Champion) (board[3][0])).toString());
			}
		});
		playBoard[16].setOnAction(e -> {
			if (board[3][1] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[3][1])).getCurrentHP());
			} else if (board[3][1] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[3][1])).getName(),
						((Champion) (board[3][1])).toString());
			}
		});
		playBoard[17].setOnAction(e -> {
			if (board[3][2] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[3][2])).getCurrentHP());
			} else if (board[3][2] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[3][2])).getName(),
						((Champion) (board[3][2])).toString());
			}
		});
		playBoard[18].setOnAction(e -> {
			if (board[3][3] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[3][3])).getCurrentHP());
			} else if (board[3][3] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[3][3])).getName(),
						((Champion) (board[3][3])).toString());
			}
		});
		playBoard[19].setOnAction(e -> {
			if (board[3][4] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[3][4])).getCurrentHP());
			} else if (board[3][4] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[3][4])).getName(),
						((Champion) (board[3][4])).toString());
			}
		});
		playBoard[20].setOnAction(e -> {
			if (board[4][0] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[4][0])).getCurrentHP());
			} else if (board[4][0] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[4][0])).getName(),
						((Champion) (board[4][0])).toString());
			}
		});
		playBoard[21].setOnAction(e -> {
			if (board[4][1] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[4][1])).getCurrentHP());
			} else if (board[4][1] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[4][1])).getName(),
						((Champion) (board[4][1])).toString());
			}
		});
		playBoard[22].setOnAction(e -> {
			if (board[4][2] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[4][2])).getCurrentHP());
			} else if (board[4][2] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[4][2])).getName(),
						((Champion) (board[4][2])).toString());
			}
		});
		playBoard[23].setOnAction(e -> {
			if (board[4][3] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[4][3])).getCurrentHP());
			} else if (board[4][3] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[4][3])).getName(),
						((Champion) (board[4][3])).toString());
			}
		});
		playBoard[24].setOnAction(e -> {
			if (board[4][4] instanceof Cover) {
				new InformationBox().display("Cover", "Health = " + ((Cover) (board[4][4])).getCurrentHP());
			} else if (board[4][4] == null) {
				new InformationBox().display("Empty Cell", "You can move here");
			} else {
				new InformationBox().display(((Champion) (board[4][4])).getName(),
						((Champion) (board[4][4])).toString());
			}
		});
		File file = new File("fireRectangle.mp4");
		Media media = new Media(file.toURL().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitHeight(1000);
		mediaView.setFitWidth(750);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		Group gp2 = new Group();
		gp2.getChildren().add(mediaView);
		StackPane st = new StackPane();
		Image image = new Image("void.jpg");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(750);
		imageView.setFitWidth(1300);
		st.getChildren().add(imageView);
		Image player = new Image("1.jpg");
		ImageView playerView = new ImageView(player);
		Image player2 = new Image("1.jpg");
		ImageView playerView2 = new ImageView(player2);
		playerView2.setFitWidth(200);
		playerView2.setFitHeight(100);
		playerView.setFitWidth(200);
		playerView.setFitHeight(100);
		Label l1 = new Label();
		leader1 = new Label("Leader ability not used");
		leader2 = new Label("Leader ability not used");
		l1.setTranslateY(25);
		leader1.setTranslateY(70);
		l1.setTranslateX(45);
		leader1.setTranslateX(45);
		l1.setAlignment(Pos.CENTER);
		leader1.setTextFill(Color.WHITE);
	//	leader1.setAlignment(Pos.CENTER);
		l1.setText(p1.getName());
		l1.setStyle("-fx-font-size: 22px;\r\n" + "-fx-font-weight: bold;\r\n" + "-fx-text-fill: white;");
		Label l2 = new Label();
		l2.setTranslateY(25);
		leader2.setTranslateY(70);
		l2.setTranslateX(45);
		leader2.setTranslateX(45);
		l2.setAlignment(Pos.CENTER);
		//leader2.setAlignment(Pos.CENTER);
		l2.setStyle("-fx-font-size: 22px;\r\n" + "-fx-font-weight: bold;\r\n" + "-fx-text-fill: white;");
		l2.setAlignment(Pos.CENTER);
		l2.setText(p2.getName());
		leader2.setTextFill(Color.WHITE);
		Group g3 = new Group();
		Group g4 = new Group();
		g3.getChildren().add(playerView);
		g3.getChildren().add(l1);
		g3.getChildren().add(leader1);
		g4.getChildren().add(playerView2);
		g4.getChildren().add(l2);
		g4.getChildren().add(leader2);
		Image im = new Image("2.jpg");
		ImageView imView = new ImageView(im);
		imView.setFitHeight(400);
		imView.setFitWidth(250);
		Group g5 = new Group();
		g5.setTranslateX(-520);
		g5.setTranslateY(-50);
		g5.getChildren().add(imView);
		Image im2 = new Image("2.jpg");
		ImageView imView2 = new ImageView(im2);
		imView2.setFitHeight(400);
		imView2.setFitWidth(250);
		Group g6 = new Group();
		g6.setTranslateX(520);
		g6.setTranslateY(-50);
		g6.getChildren().add(imView2);
		g3.setTranslateX(-550);
		g3.setTranslateY(-300);
		g4.setTranslateX(550);
		g4.setTranslateY(-300);
		st.getChildren().add(g5);
		st.getChildren().add(g6);
		Button moveButton = new Button("Move");
		moveButton.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #ecebe9,\r\n"
				+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);\r\n" + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\r\n"
				+ "    -fx-background-radius: 50;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
				+ "    -fx-text-fill: #311c09;\r\n"
				+ "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
		f = false;
		if (p1.getTeam().contains(game.getCurrentChampion()))
			f = true;
		Group gp5 = new Group();
		gp5.getChildren().add(label);
		ff = true;
		Group tmp = new Group();
		Group tmp2 = new Group();
		Group tmp3 = new Group();
		Group tmp4 = new Group();
		moveButton.setOnAction(e -> {
			if (ff) {
				Button up = new Button();
				Image upImage = new Image("up.png");
				ImageView upView = new ImageView(upImage);
				upView.setFitHeight(30);
				upView.setFitWidth(30);
				up.setGraphic(upView);
				up.setOnAction(e2 -> {
					try {
						int x = game.getCurrentChampion().getLocation().x;
						int y = game.getCurrentChampion().getLocation().y;
						game.move(Direction.DOWN);
						Media sound2 = new Media(new File("move.mp3").toURI().toString());
						mediaPlayer2 = new MediaPlayer(sound2);
						mediaPlayer2.setAutoPlay(true);
						mediaPlayer2.play();

						handleCurrent(gp5, f,playBoard);

						int total = y + 5 * x;
						Image nullImage = new Image("empty.jpg");
						ImageView nullView = new ImageView(nullImage);
						nullView.setFitHeight(70);
						nullView.setFitWidth(105);
						playBoard[total].setGraphic(nullView);
						Image curImage = new Image(findImageByName(game.getCurrentChampion().getName()));
						ImageView curView = new ImageView(curImage);
						curView.setFitHeight(70);
						curView.setFitWidth(105);
						playBoard[total - 5].setGraphic(curView);
					} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
						new AlertBox().display("Can't move", "This movement is  Unallowed");

					}
				});
				Button down = new Button();
				Image downImage = new Image("down.png");
				ImageView downView = new ImageView(downImage);
				down.setGraphic(downView);
				downView.setFitHeight(30);
				downView.setFitWidth(30);
				down.setOnAction(e2 -> {
					try {
						int x = game.getCurrentChampion().getLocation().x;
						int y = game.getCurrentChampion().getLocation().y;
						game.move(Direction.UP);
						Media sound2 = new Media(new File("move.mp3").toURI().toString());
						mediaPlayer2 = new MediaPlayer(sound2);
						mediaPlayer2.setAutoPlay(true);
						mediaPlayer2.play();

						handleCurrent(gp5, f,playBoard);

						int total = y + 5 * x;
						Image nullImage = new Image("empty.jpg");
						ImageView nullView = new ImageView(nullImage);
						nullView.setFitHeight(70);
						nullView.setFitWidth(105);
						playBoard[total].setGraphic(nullView);
						Image curImage = new Image(findImageByName(game.getCurrentChampion().getName()));
						ImageView curView = new ImageView(curImage);
						curView.setFitHeight(70);
						curView.setFitWidth(105);
						playBoard[total + 5].setGraphic(curView);
					} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
						new AlertBox().display("Can't move", "This movement is  Unallowed");

					}
				});

				Button left = new Button();
				Image leftImage = new Image("left.png");
				ImageView leftView = new ImageView(leftImage);
				leftView.setFitHeight(30);
				leftView.setFitWidth(30);
				left.setGraphic(leftView);
				left.setOnAction(e2 -> {
					try {
						int x = game.getCurrentChampion().getLocation().x;
						int y = game.getCurrentChampion().getLocation().y;
						game.move(Direction.LEFT);
						Media sound2 = new Media(new File("move.mp3").toURI().toString());
						mediaPlayer2 = new MediaPlayer(sound2);
						mediaPlayer2.setAutoPlay(true);
						mediaPlayer2.play();

						handleCurrent(gp5, f,playBoard);

						int total = y + 5 * x;
						Image nullImage = new Image("empty.jpg");
						ImageView nullView = new ImageView(nullImage);
						nullView.setFitHeight(70);
						nullView.setFitWidth(105);
						playBoard[total].setGraphic(nullView);
						Image curImage = new Image(findImageByName(game.getCurrentChampion().getName()));
						ImageView curView = new ImageView(curImage);
						curView.setFitHeight(70);
						curView.setFitWidth(105);
						playBoard[total + -1].setGraphic(curView);
					} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
						new AlertBox().display("Can't move", "This movement is  Unallowed");

					}
				});
				Button right = new Button();
				Image rightImage = new Image("right.png");
				ImageView rightView = new ImageView(rightImage);
				rightView.setFitHeight(30);
				rightView.setFitWidth(30);
				right.setGraphic(rightView);
				right.setOnAction(e2 -> {
					try {
						int x = game.getCurrentChampion().getLocation().x;
						int y = game.getCurrentChampion().getLocation().y;
						game.move(Direction.RIGHT);
						Media sound2 = new Media(new File("move.mp3").toURI().toString());
						mediaPlayer2 = new MediaPlayer(sound2);
						mediaPlayer2.setAutoPlay(true);
						mediaPlayer2.play();

						handleCurrent(gp5, f,playBoard);

						int total = y + 5 * x;
						Image nullImage = new Image("empty.jpg");
						ImageView nullView = new ImageView(nullImage);
						nullView.setFitHeight(70);
						nullView.setFitWidth(105);
						playBoard[total].setGraphic(nullView);
						Image curImage = new Image(findImageByName(game.getCurrentChampion().getName()));
						ImageView curView = new ImageView(curImage);
						curView.setFitHeight(70);
						curView.setFitWidth(105);
						playBoard[total + 1].setGraphic(curView);
					} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
						new AlertBox().display("Can't move", "This movement is  Unallowed");

					}
				});
				tmp4.getChildren().add(right);
				tmp4.setTranslateX(-440);
				tmp4.setTranslateY(300);
				tmp3.getChildren().add(left);
				tmp.getChildren().add(up);
				tmp2.getChildren().add(down);
				tmp2.setTranslateX(-520);
				tmp3.setTranslateX(-600);
				tmp3.setTranslateY(300);
				tmp2.setTranslateY(350);
				tmp.setTranslateY(250);
				tmp.setTranslateX(-520);
				st.getChildren().add(tmp4);
				st.getChildren().add(tmp3);
				st.getChildren().add(tmp);
				st.getChildren().add(tmp2);

				ff = false;
			} else {
				ff = true;
				st.getChildren().remove(tmp);
				st.getChildren().remove(tmp2);
				st.getChildren().remove(tmp3);
				st.getChildren().remove(tmp4);
				if (mediaPlayer2 != null)
					mediaPlayer2.stop();
			}
		});
		Button attackButton = new Button("Attack");
		attackButton.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #ecebe9,\r\n"
				+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);\r\n" + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\r\n"
				+ "    -fx-background-radius: 50;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
				+ "    -fx-text-fill: #311c09;\r\n"
				+ "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
		Group tmp5 = new Group(), tmp7 = new Group(), tmp6 = new Group(), tmp8 = new Group();
		attackButton.setOnAction(e -> {
			if (fff) {

				Button up2 = new Button();
				Image upImage2 = new Image("up.png");
				ImageView upView2 = new ImageView(upImage2);
				upView2.setFitHeight(30);
				upView2.setFitWidth(30);
				up2.setGraphic(upView2);
				up2.setOnAction(e2 -> {

					int x = game.getCurrentChampion().getLocation().x;
					int y = game.getCurrentChampion().getLocation().y;
					try {
						game.attack(Direction.DOWN);
						updateGrid2(playBoard);
						Media attack = new Media(new File("attack.mp3").toURI().toString());
						mediaPlayer3 = new MediaPlayer(attack);
						mediaPlayer3.setAutoPlay(true);
						mediaPlayer3.play();
						handleCurrent(gp5, f,playBoard);
						int total = y + 5 * x;
						File attackFile = new File("ability1.mp4");
						Media attackMedia;
						try {
							attackMedia = new Media(attackFile.toURL().toString());
							MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
							attackMediaPlayer.setAutoPlay(true);
							MediaView attackMediaView = new MediaView(attackMediaPlayer);
							attackMediaView.setFitHeight(80);
							attackMediaView.setFitWidth(110);
							// HERE
							for (int i = game.getCurrentChampion().getLocation().x - 1; i >= Math.max(0,
									game.getCurrentChampion().getLocation().x
											- game.getCurrentChampion().getAttackRange()); i--) {
								if (board[i][game.getCurrentChampion().getLocation().y] instanceof Cover
										|| (board[i][game.getCurrentChampion().getLocation().y] instanceof Champion
												&& !sameTeam(game.getCurrentChampion(), ((Champion) (board[i][game
														.getCurrentChampion().getLocation().y]))))) {
									if (!(playBoard[(i) * 5 + y].getGraphic() instanceof MediaView)) {
										// tmpView1 = (ImageView) (playBoard[(i) * 5 + y].getGraphic());
										playBoard[(i) * 5 + y].setGraphic(attackMediaView);
										// cnt1 = i;
										attackMediaPlayer.setOnEndOfMedia(new Runnable() {

											@Override
											public void run() {
												updateGrid2(playBoard);

											}
										});
									}
									break;
								}
							}
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NotEnoughResourcesException | ChampionDisarmedException e1) {

						new AlertBox().display("Can't Attack", "You can't do this attack");
					}
				});
				Button down2 = new Button();
				Image downImage2 = new Image("down.png");
				ImageView downView2 = new ImageView(downImage2);
				downView2.setFitHeight(30);
				downView2.setFitWidth(30);
				down2.setGraphic(downView2);
				down2.setOnAction(e2 -> {
					int x = game.getCurrentChampion().getLocation().x;
					int y = game.getCurrentChampion().getLocation().y;
					try {
						game.attack(Direction.UP);
						updateGrid2(playBoard);
						Media attack = new Media(new File("attack.mp3").toURL().toString());
						mediaPlayer3 = new MediaPlayer(attack);
						mediaPlayer3.setAutoPlay(true);
						mediaPlayer3.play();
						handleCurrent(gp5, f,playBoard);
						int total = y + 5 * x;
						File attackFile = new File("ability1.mp4");
						Media attackMedia;
						try {
							attackMedia = new Media(attackFile.toURL().toString());
							MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
							attackMediaPlayer.setAutoPlay(true);
							MediaView attackMediaView = new MediaView(attackMediaPlayer);
							attackMediaView.setFitHeight(80);
							attackMediaView.setFitWidth(110);
							// HERE
							for (int i = game.getCurrentChampion().getLocation().x + 1; i < Math.min(5,
									game.getCurrentChampion().getLocation().x + 1
											+ game.getCurrentChampion().getAttackRange()); i++) {
								if (board[i][game.getCurrentChampion().getLocation().y] instanceof Cover
										|| (board[i][game.getCurrentChampion().getLocation().y] instanceof Champion
												&& !sameTeam(game.getCurrentChampion(), ((Champion) (board[i][game
														.getCurrentChampion().getLocation().y]))))) {
									if (!(playBoard[(i) * 5 + y].getGraphic() instanceof MediaView)) {
//										tmpView2 = (ImageView) (playBoard[(i) * 5 + y].getGraphic());
										playBoard[(i) * 5 + y].setGraphic(attackMediaView);
										cnt2 = i;
										attackMediaPlayer.setOnEndOfMedia(new Runnable() {

											@Override
											public void run() {
												updateGrid2(playBoard);

											}
										});
									}
									break;
								}
							}
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NotEnoughResourcesException | ChampionDisarmedException e1) {

						new AlertBox().display("Can't Attack", "You can't do this attack");
					} catch (MalformedURLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				});
				Button right2 = new Button();
				Image rightImage2 = new Image("right.png");
				ImageView rightView2 = new ImageView(rightImage2);
				rightView2.setFitHeight(30);
				rightView2.setFitWidth(30);
				right2.setGraphic(rightView2);
				right2.setOnAction(e2 -> {
					int x = game.getCurrentChampion().getLocation().x;
					int y = game.getCurrentChampion().getLocation().y;
					try {
						game.attack(Direction.RIGHT);
						updateGrid2(playBoard);
						Media attack = new Media(new File("attack.mp3").toURL().toString());
						mediaPlayer3 = new MediaPlayer(attack);
						mediaPlayer3.setAutoPlay(true);
						mediaPlayer3.play();
						handleCurrent(gp5, f,playBoard);
						int total = y + 5 * x;
						File attackFile = new File("ability1.mp4");
						Media attackMedia;
						try {
							attackMedia = new Media(attackFile.toURL().toString());
							MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
							attackMediaPlayer.setAutoPlay(true);
							MediaView attackMediaView = new MediaView(attackMediaPlayer);
							attackMediaView.setFitHeight(80);
							attackMediaView.setFitWidth(110);
							// HERE
							for (int i = game.getCurrentChampion().getLocation().y + 1; i < Math.min(5,
									game.getCurrentChampion().getLocation().y + 1
											+ game.getCurrentChampion().getAttackRange()); i++) {
								if (board[game.getCurrentChampion().getLocation().x][i] instanceof Cover || (board[game
										.getCurrentChampion().getLocation().x][i] instanceof Champion
										&& !sameTeam(game.getCurrentChampion(),
												((Champion) (board[game.getCurrentChampion().getLocation().x][i]))))) {
									if (!(playBoard[(game.getCurrentChampion().getLocation().x) * 5 + i]
											.getGraphic() instanceof MediaView)) {
//										tmpView3 = (ImageView) (playBoard[(game.getCurrentChampion().getLocation().x)
//												* 5 + i].getGraphic());
										playBoard[(game.getCurrentChampion().getLocation().x) * 5 + i]
												.setGraphic(attackMediaView);
										// cnt3 = i;
										attackMediaPlayer.setOnEndOfMedia(new Runnable() {

											@Override
											public void run() {
												updateGrid2(playBoard);
											}
										});
									}
									break;
								}
							}
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NotEnoughResourcesException | ChampionDisarmedException e1) {

						new AlertBox().display("Can't Attack", "You can't do this attack");
					} catch (MalformedURLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				});
				Button left2 = new Button();
				Image leftImage2 = new Image("left.png");
				ImageView leftView2 = new ImageView(leftImage2);
				leftView2.setFitHeight(30);
				leftView2.setFitWidth(30);
				left2.setGraphic(leftView2);
				left2.setOnAction(e2 -> {
					int x = game.getCurrentChampion().getLocation().x;
					int y = game.getCurrentChampion().getLocation().y;
					try {
						game.attack(Direction.LEFT);
						updateGrid2(playBoard);
						Media attack = new Media(new File("attack.mp3").toURI().toString());
						mediaPlayer3 = new MediaPlayer(attack);
						mediaPlayer3.setAutoPlay(true);
						mediaPlayer3.play();
						handleCurrent(gp5, f,playBoard);
						int total = y + 5 * x;
						File attackFile = new File("ability1.mp4");
						Media attackMedia;
						try {
							attackMedia = new Media(attackFile.toURL().toString());
							MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
							attackMediaPlayer.setAutoPlay(true);
							MediaView attackMediaView = new MediaView(attackMediaPlayer);
							attackMediaView.setFitHeight(80);
							attackMediaView.setFitWidth(110);
							// HERE
							for (int i = game.getCurrentChampion().getLocation().y - 1; i >= Math.max(0,
									game.getCurrentChampion().getLocation().y
											- game.getCurrentChampion().getAttackRange()); i--) {
								if (board[game.getCurrentChampion().getLocation().x][i] instanceof Cover || (board[game
										.getCurrentChampion().getLocation().x][i] instanceof Champion
										&& !sameTeam(game.getCurrentChampion(),
												((Champion) (board[game.getCurrentChampion().getLocation().x][i]))))) {
									if (!(playBoard[(game.getCurrentChampion().getLocation().x) * 5 + i]
											.getGraphic() instanceof MediaView)) {
//										tmpView4 = (ImageView) (playBoard[(game.getCurrentChampion().getLocation().x)
//												* 5 + i].getGraphic());
										playBoard[(game.getCurrentChampion().getLocation().x) * 5 + i]
												.setGraphic(attackMediaView);
										// cnt4 = i;
										attackMediaPlayer.setOnEndOfMedia(new Runnable() {

											@Override
											public void run() {
												updateGrid2(playBoard);

											}
										});
									}
									break;
								}
							}
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NotEnoughResourcesException | ChampionDisarmedException e1) {

						new AlertBox().display("Can't Attack", "You can't do this attack");
					}
				});

				tmp8.getChildren().add(left2);
				tmp8.setTranslateX(440);
				tmp8.setTranslateY(300);
				st.getChildren().add(tmp8);

				tmp7.getChildren().add(right2);
				tmp7.setTranslateX(600);
				tmp7.setTranslateY(300);
				st.getChildren().add(tmp7);
				tmp6.getChildren().add(down2);
				tmp6.setTranslateX(520);
				tmp6.setTranslateY(350);
				st.getChildren().add(tmp6);

				tmp5.getChildren().add(up2);
				tmp5.setTranslateX(520);
				tmp5.setTranslateY(250);
				st.getChildren().add(tmp5);

				fff = false;
			} else {
				fff = true;
				st.getChildren().remove(tmp5);
				st.getChildren().remove(tmp6);
				st.getChildren().remove(tmp7);
				st.getChildren().remove(tmp8);
			}

		});
		Button endTurnButton = new Button("End Turn");
		endTurnButton.setOnAction(e -> {
			game.endTurn();
			box.getChildren().clear();
			st.getChildren().remove(box);
			if (hBox.getChildren().size() == 1) {
				hBox.getChildren().remove(0);
				updateTurns();
			} else {
				while((!hBox.getChildren().isEmpty())||((hBox.getChildren().size()>0)&&(!game.getCurrentChampion().getName().equals((findChampbyImage(((ImageView)hBox.getChildren().get(0)).getImage().getUrl().toString())))))) {
					hBox.getChildren().remove(0);
				}
				if(hBox.getChildren().isEmpty()) updateTurns();
			}
			f = false;
			if (p1.getTeam().contains(game.getCurrentChampion()))
				f = true;
			handleCurrent(gp5, f,playBoard);
		});
		endTurnButton.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #ecebe9,\r\n"
				+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);\r\n" + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\r\n"
				+ "    -fx-background-radius: 50;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
				+ "    -fx-text-fill: #311c09;\r\n"
				+ "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
		Group gp8 = new Group();
		updateTurns();
		File file33 = new File("turnOrder.mp4");
		Media media33 = new Media(file33.toURL().toString());
		MediaPlayer mediaPlayer33 = new MediaPlayer(media33);
		mediaPlayer33.setAutoPlay(true);
		MediaView mediaView33 = new MediaView(mediaPlayer33);
		mediaPlayer33.setCycleCount(MediaPlayer.INDEFINITE);
		mediaView33.setTranslateY(-200);
		mediaView33.setFitHeight(350);
		mediaView33.setFitWidth(850);
		hBox.setTranslateX(380);
		hBox.setTranslateY(50);
		Button abilityButton = new Button("Cast Ability");
		abilityButton.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #ecebe9,\r\n"
				+ "        rgba(0,0,0,0.05),\r\n" + "        linear-gradient(#dcca8a, #c7a740),\r\n"
				+ "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\r\n"
				+ "        linear-gradient(#f6ebbe, #e6c34d);\r\n" + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\r\n"
				+ "    -fx-background-radius: 50;\r\n" + "    -fx-padding: 15 30 15 30;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 18px;\r\n"
				+ "    -fx-text-fill: #311c09;\r\n"
				+ "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
		// NOW HERE  fix sounds, gameover, , punch !
		 box = new Group();
		f5 = true;
		abilityButton.setOnAction(e -> {
			if (f5) {
				Button ab1Button = new Button(game.getCurrentChampion().getAbilities().get(0).getName());
				ab1Button.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
						+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
						+ "    -fx-text-fill: white;");
				ab1Button.setOnAction(e2 -> {
					if (game.getCurrentChampion().getAbilities().get(0).getCastArea() == AreaOfEffect.TEAMTARGET
							|| game.getCurrentChampion().getAbilities().get(0).getCastArea() == AreaOfEffect.SELFTARGET
							|| game.getCurrentChampion().getAbilities().get(0).getCastArea() == AreaOfEffect.SURROUND) {
						try {
							game.castAbility(game.getCurrentChampion().getAbilities().get(0));
							Media ability1 = new Media(new File(getSoundOfAbility1()).toURI().toString());
							MediaPlayer ability1Player = new MediaPlayer(ability1);
							ability1Player.setAutoPlay(true);
							ability1Player.play();
							
						} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
							new AlertBox().display("Can't use this ability", "You are unallowed to do this ability");
						}
					}else if((game.getCurrentChampion().getAbilities().get(0).getCastArea() ==AreaOfEffect.DIRECTIONAL)) {
						new DirectionBox().display("Choose direction ",game,game.getCurrentChampion().getAbilities().get(0),getSoundOfAbility1());
					}
					else {
						new singleTargetBox().display("Choose a cell", game, game.getCurrentChampion().getAbilities().get(0), getSoundOfAbility1());
					}
					updateGrid2(playBoard);
					f = false;
					if (p1.getTeam().contains(game.getCurrentChampion()))
						f = true;
					handleCurrent(gp5, f,playBoard);
				});
				Button ab2Button = new Button(game.getCurrentChampion().getAbilities().get(1).getName());
				ab2Button.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
						+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
						+ "    -fx-text-fill: white;");
				ab2Button.setOnAction(e2 -> {
					if (game.getCurrentChampion().getAbilities().get(1).getCastArea() == AreaOfEffect.TEAMTARGET
							|| game.getCurrentChampion().getAbilities().get(1).getCastArea() == AreaOfEffect.SELFTARGET
							|| game.getCurrentChampion().getAbilities().get(1).getCastArea() == AreaOfEffect.SURROUND) {
						try {
							game.castAbility(game.getCurrentChampion().getAbilities().get(1));
							Media ability2 = new Media(new File(getSoundOfAbility2()).toURI().toString());
							MediaPlayer ability2Player = new MediaPlayer(ability2);
							ability2Player.setAutoPlay(true);
							ability2Player.play();
							
						} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
							new AlertBox().display("Can't use this ability", "You are unallowed to do this ability");
						}
					}
					else if((game.getCurrentChampion().getAbilities().get(1).getCastArea() ==AreaOfEffect.DIRECTIONAL)) {
						new DirectionBox().display("Choose direction ",game,game.getCurrentChampion().getAbilities().get(1),getSoundOfAbility2());
					}
					else {
						new singleTargetBox().display("Choose a cell", game, game.getCurrentChampion().getAbilities().get(1), getSoundOfAbility2());
					}
					updateGrid2(playBoard);
					f = false;
					if (p1.getTeam().contains(game.getCurrentChampion()))
						f = true;
					handleCurrent(gp5, f,playBoard);
				});
				// TODO 1) Sound not working well     3)handle turn order
				Button ab3Button = new Button(game.getCurrentChampion().getAbilities().get(2).getName());
				ab3Button.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
						+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
						+ "    -fx-text-fill: white;");
				ab3Button.setOnAction(e2 -> {
					if (game.getCurrentChampion().getAbilities().get(2).getCastArea() == AreaOfEffect.TEAMTARGET
							|| game.getCurrentChampion().getAbilities().get(2).getCastArea() == AreaOfEffect.SELFTARGET
							|| game.getCurrentChampion().getAbilities().get(2).getCastArea() == AreaOfEffect.SURROUND) {
						try {
							game.castAbility(game.getCurrentChampion().getAbilities().get(2));
							Media ability3 = new Media(new File(getSoundOfAbility3()).toURI().toString());
							MediaPlayer ability3Player = new MediaPlayer(ability3);
							ability3Player.setAutoPlay(true);
							ability3Player.play();
							
						} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
							new AlertBox().display("Can't use this ability", "You are unallowed to do this ability");
						}
					}
					else if((game.getCurrentChampion().getAbilities().get(2).getCastArea() ==AreaOfEffect.DIRECTIONAL)) {
						new DirectionBox().display("Choose direction ",game,game.getCurrentChampion().getAbilities().get(2),getSoundOfAbility3());
					}
					else {
						new singleTargetBox().display("Choose a cell", game, game.getCurrentChampion().getAbilities().get(2), getSoundOfAbility3());
					}
					updateGrid2(playBoard);
					f = false;
					if (p1.getTeam().contains(game.getCurrentChampion()))
						f = true;
					handleCurrent(gp5, f,playBoard);
				});
				if(game.getCurrentChampion().getAbilities().size()>3) {
					Button ab4Button = new Button(game.getCurrentChampion().getAbilities().get(3).getName());
					ab4Button.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
							+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
							+ "    -fx-text-fill: white;");
					ab4Button.setOnAction(e2->
					new singleTargetBox().display("Choose a cell", game, game.getCurrentChampion().getAbilities().get(3), getSoundOfAbility3()));
					box.getChildren().add(ab4Button);
					ab4Button.setTranslateX(300);
				}
				box.getChildren().addAll(ab1Button, ab2Button, ab3Button);
				ab1Button.setTranslateX(-150);
				ab3Button.setTranslateX(150);
				box.setTranslateY(250);
				box.setTranslateX(-150);
				st.getChildren().add(box);
				f5 = false;
			} else {
				box.getChildren().clear();
				st.getChildren().remove(box);
				f5 = true;
			}
		});
		Group gp9 = new Group();
		gp9.getChildren().add(abilityButton);
		gp8.getChildren().add(endTurnButton);
		gp9.setTranslateX(-150);
		gp9.setTranslateY(300);
		gp8.setTranslateX(150);
		gp8.setTranslateY(300);
		Group gp7 = new Group();
		gp7.setTranslateX(520);
		gp7.setTranslateY(300);
		Group gp6 = new Group();
		gp6.getChildren().add(moveButton);
		gp6.setTranslateY(300);
		gp6.setTranslateX(-520);
		gp7.getChildren().add(attackButton);
		handleCurrent(gp5, f,playBoard);

		st.getChildren().add(mediaView33);
		st.getChildren().add(hBox);
		st.getChildren().add(gp8);
		st.getChildren().add(gp7);
		st.getChildren().add(gp5);
		st.getChildren().add(g4);
		st.getChildren().add(g3);
		st.getChildren().add(gp2);
		st.getChildren().add(gp6);
		st.getChildren().add(gp9);
		st.getChildren().add(gp);
		scene6 = new Scene(st, 1300, 750);
		stage.setScene(scene6);
	}
	
	public void endScene(Player p) {
		StackPane st= new StackPane();
		Image im = new Image("end.jpg");
		ImageView imageView = new ImageView(im);
		imageView.setFitHeight(750);
		imageView.setFitWidth(1300);
		st.getChildren().add(imageView);
		Label label = new Label("Congratulations "+p.getName()+", You Won !");
		Button b1 = new Button("Play Again");
		Button b2 = new Button("Exit");
		label.setTranslateY(-250);
		label.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		b1.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		b2.setStyle("\r\n" + "    -fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n"
				+ "    -fx-background-radius: 30;\r\n" + "    -fx-background-insets: 0;\r\n"
				+ "    -fx-font-size: 18px;\r\n" + "    -fx-font-weight: bold;\r\n" + "    -fx-text-fill: white;");
		b1.setOnAction(e-> scene2());
		b2.setOnAction(e->stage.close());
		b1.setTranslateY(300);
		b2.setTranslateY(300);
		b2.setTranslateX(-300);
		b1.setTranslateX(300);
		st.getChildren().add(label);
		st.getChildren().add(b2);
		st.getChildren().add(b1);
		endScene = new Scene(st,1300,750);
		stage.setScene(endScene);
	}
	static Group box;
	static HBox hBox = new HBox();
	static MediaPlayer mediaPlayer2, mediaPlayer3;
	static ImageView tmpView1, tmpView2, tmpView3, tmpView4;
	static int cnt1, cnt2, cnt3, cnt4;

	public void updateGrid(Button[] playBoard, int ctr, Group gp) {
		for (int i = 0; i < 25; i++) {
			playBoard[i] = new Button();
			playBoard[i].setMinHeight(80);
			playBoard[i].setMinWidth(120);
			BorderStroke bs = new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					BorderWidths.DEFAULT);
			playBoard[i].setBorder(new Border(bs));
			if (i == 5 || i == 10 || i == 15 || i == 20)
				ctr = 0;
			playBoard[i].setTranslateX(ctr * 120);
			ctr++;
			if (i <= 4)
				playBoard[i].setTranslateY(0);
			else if (i > 4 && i < 10) {
				playBoard[i].setTranslateY(80);
			} else if (i > 9 && i < 15) {
				playBoard[i].setTranslateY(160);
			} else if (i > 14 && i < 20) {
				playBoard[i].setTranslateY(240);
			} else
				playBoard[i].setTranslateY(320);
			gp.getChildren().add(playBoard[i]);
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof Cover) {
					Image coverImage = new Image("cover.jpg");
					ImageView coverView = new ImageView(coverImage);
					coverView.setFitHeight(70);
					coverView.setFitWidth(105);
					playBoard[j + i * 5].setGraphic(coverView);
				} else if (board[i][j] == null) {
					Image nullImage = new Image("empty.jpg");
					ImageView nullView = new ImageView(nullImage);
					nullView.setFitHeight(70);
					nullView.setFitWidth(105);
					playBoard[j + i * 5].setGraphic(nullView);
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			Image image1 = new Image(findImageByName(p1.getTeam().get(i).getName()));
			ImageView champView = new ImageView(image1);
			champView.setFitHeight(70);
			champView.setFitWidth(105);
			playBoard[i + 1].setGraphic(champView);
			Image image2 = new Image(findImageByName(p2.getTeam().get(i).getName()));
			ImageView champView2 = new ImageView(image2);
			champView2.setFitHeight(70);
			champView2.setFitWidth(105);
			playBoard[i + 21].setGraphic(champView2);
		}
	}

	public void updateGrid2(Button[] playBoard) {
		if(p1.getTeam().size()==0) endScene(p2);
		else if(p2.getTeam().size()==0) endScene(p1);
		else {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] instanceof Cover) {
					Image coverImage = new Image("cover.jpg");
					ImageView coverView = new ImageView(coverImage);
					coverView.setFitHeight(70);
					coverView.setFitWidth(105);
					playBoard[j + i * 5].setGraphic(coverView);
				} else if (board[i][j] == null) {
					Image nullImage = new Image("empty.jpg");
					ImageView nullView = new ImageView(nullImage);
					nullView.setFitHeight(70);
					nullView.setFitWidth(105);
					playBoard[j + i * 5].setGraphic(nullView);
				} else {
					Image champImage = new Image(findImageByName(((Champion) (board[i][j])).getName()));
					ImageView imageView = new ImageView(champImage);
					imageView.setFitHeight(70);
					imageView.setFitWidth(105);
					playBoard[j + i * 5].setGraphic(imageView);
				}
			}
		}
		}
	}
	public void handleCurrent(Group gp5, boolean ff,Button[] playBoard) {
		// gp5 = new Group();
		Image currentImage = new Image(findImageByName(game.getCurrentChampion().getName()));
		ImageView currView = new ImageView(currentImage);
		currView.setTranslateY(-50);
		currView.setFitHeight(50);
		currView.setFitWidth(50);
		// label= new Label();
		if(game.getCurrentChampion().equals(game.getFirstPlayer().getLeader()) ||game.getCurrentChampion().equals(game.getSecondPlayer().getLeader()) )
		{		
				label.setText("Current Attacking Champion : \n " + game.getCurrentChampion().getName() + "\n "
						+ "Health : " +game.getCurrentChampion().getCurrentHP() + "\n" +"Mana : " + game.getCurrentChampion().getMana() + "\n"+
						"Action Points : " + game.getCurrentChampion().getCurrentActionPoints() + "\n"+
						"Attack Damage : " + game.getCurrentChampion().getAttackDamage() + "\n"+
						"Attack Range : " + game.getCurrentChampion().getAttackRange() + "\n"+
						"This Champion is a Leader\n"
						);
			
		}	else {
		label.setText("Current Attacking Champion : \n " + game.getCurrentChampion().getName() + "\n "
				+ "Health : " +game.getCurrentChampion().getCurrentHP() + "\n" +"Mana : " + game.getCurrentChampion().getMana() + "\n"+
				"Action Points : " + game.getCurrentChampion().getCurrentActionPoints() + "\n"+
				"Attack Damage : " + game.getCurrentChampion().getAttackDamage() + "\n"+
				"Attack Range : " + game.getCurrentChampion().getAttackRange() + "\n"); }
		label.setStyle("-fx-font-size: 14px;\r\n" + "-fx-font-weight: bold;\r\n" + "-fx-text-fill: white;");
		// gp5.getChildren().add(label);
		gp5.getChildren().add(currView);
		if (ff)
			gp5.setTranslateX(-520);
		else
			gp5.setTranslateX(520);
		gp5.setTranslateY(-65);
		Button abilityButton = new Button("Abilities");
		abilityButton.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #a6b5c9,\r\n"
				+ "        linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\r\n"
				+ "        linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 7 30 7 30;\r\n" + "    -fx-text-fill: #242d35;\r\n"
				+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 12px;\r\n"
				+ "    -fx-text-fill: white;");
		abilityButton.setOnAction(e -> {
			new AbilityShowBox().display("Abilities", game.getCurrentChampion().getAbilities().get(0).getName(),
					game.getCurrentChampion().getAbilities().get(1).getName(),
					game.getCurrentChampion().getAbilities().get(2).getName(), game.getCurrentChampion());
		});
		gp5.getChildren().add(abilityButton);
		abilityButton.setTranslateY(200);
			Button leaderAbilityButton = new Button("Use Leader Ability");
			leaderAbilityButton.setOnAction(e->{
				
					try {
						game.useLeaderAbility();
						if(p1.getTeam().contains(game.getCurrentChampion()))
						leader1.setText("Leader ability is used");
						else
						leader2.setText("Leader ability is used");
					} catch (LeaderNotCurrentException e1) {
						new AlertBox().display("Wrong cast","You can't cast leader ability on a champion who is not a leader" );
						
					} catch (LeaderAbilityAlreadyUsedException e1) {
						
						new AlertBox().display("Leader Ability already used","You can't use leader ability twice");
					}
					updateGrid2(playBoard);
					f = false;
					if (p1.getTeam().contains(game.getCurrentChampion()))
						f = true;
					handleCurrent(gp5, f,playBoard);
				
			});
			gp5.getChildren().add(leaderAbilityButton);
			leaderAbilityButton.setTranslateY(250);
			leaderAbilityButton.setStyle("\r\n" + "    -fx-background-color: \r\n" + "        #a6b5c9,\r\n"
					+ "        linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\r\n"
					+ "        linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\r\n"
					+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
					+ "    -fx-padding: 7 30 7 30;\r\n" + "    -fx-text-fill: #242d35;\r\n"
					+ "    -fx-font-family: \"Helvetica\";\r\n" + "    -fx-font-size: 12px;\r\n"
					+ "    -fx-text-fill: white;");
		
	}
	public String findImageByName(String champ) {
		if (champ.equals("Hulk"))
			return "HulkIcon.jpg";
		if (champ.equals("Hela"))
			return "HelaIcon.jpg";
		if (champ.equals("Captain America"))
			return "CaptainAmericaIcon.jpg";
		if (champ.equals("Deadpool"))
			return "DeadpoolIcon.jpg";
		if (champ.equals("Dr Strange"))
			return "doctorStrangeIcon.jpg";
		if (champ.equals("Electro"))
			return "ElectroIcon.jpg";
		if (champ.equals("Ghost Rider"))
			return "GhostRiderIcon.jpg";
		if (champ.equals("Iceman"))
			return "IcemanIcon.jpg";
		if (champ.equals("Ironman"))
			return "IronManIcon.jpg";
		if (champ.equals("Loki"))
			return "LokiIcon.jpg";
		if (champ.equals("Quicksilver"))
			return "QuickSilverIcon.jpg";
		if (champ.equals("Spiderman"))
			return "SpiderManIcon.jpg";
		if (champ.equals("Thor"))
			return "ThorIcon.jpg";
		if (champ.equals("Venom"))
			return "VenomIcon.jpg";
		else
			return "Yllowjacket.jpg";
	}

	public String findChampbyImage(String champ) {
		if (champ.equals("HulkIcon.jpg"))
			return "Hulk";
		if (champ.equals("HelaIcon.jpg"))
			return "Hela";
		if (champ.equals("CaptainAmericaIcon.jpg"))
			return "Captain America";
		if (champ.equals("DeadpoolIcon.jpg"))
			return "Deadpool";
		if (champ.equals("doctorStrangeIcon.jpg"))
			return "Dr Strange";
		if (champ.equals("ElectroIcon.jpg"))
			return "Electro";
		if (champ.equals("GhostRiderIcon.jpg"))
			return "Ghost Rider";
		if (champ.equals("IcemanIcon.jpg"))
			return "Iceman";
		if (champ.equals("IronManIcon.jpg"))
			return "Ironman";
		if (champ.equals("LokiIcon.jpg"))
			return "Loki";
		if (champ.equals("QuickSilverIcon.jpg"))
			return "Quicksilver";
		if (champ.equals("SpiderManIcon.jpg"))
			return "Spiderman";
		if (champ.equals("ThorIcon.jpg"))
			return "Thor";
		if (champ.equals("VenomIcon.jpg"))
			return "Venom";
		else
			return "Yellow Jacket";
	}

	public boolean sameTeam(Champion champ1, Champion champ2) {
		if (p1.getTeam().contains(champ2) && p1.getTeam().contains(champ1))
			return true;
		if (p2.getTeam().contains(champ2) && p2.getTeam().contains(champ1))
			return true;
		return false;
	}

	static boolean f5;

	public void start(Stage window) throws Exception {
		stage = window;
		scene1();
		stage.getIcons().add(new Image("logo.jpg"));
		stage.setTitle("Marvel Ultimate War");
		stage.setScene(scene1);
		stage.setResizable(false);
		stage.show();
	}

	public void updateTurns() {
		PriorityQueue tmp = new PriorityQueue(game.getTurnOrder().size());
		PriorityQueue turn = new PriorityQueue(game.getTurnOrder().size());
		while (!game.getTurnOrder().isEmpty()) {
			tmp.insert(game.getTurnOrder().peekMin());
			turn.insert(game.getTurnOrder().remove());
		}
		while (!tmp.isEmpty()) {
			game.getTurnOrder().insert(tmp.remove());
		}
		while (!turn.isEmpty()) {
			Champion champ = (Champion) (turn.remove());
			Image im = new Image(findImageByName(champ.getName()));
			ImageView imView = new ImageView(im);
			imView.setFitHeight(70);
			imView.setFitWidth(90);
			hBox.getChildren().add(imView);
		}
	}

	public String getSoundOfAbility1() {
		if (game.getCurrentChampion().getName().equals("Captain America"))
			return "capAmerica ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Deadpool"))
			return "deadPool ability1.mp3";
		if (game.getCurrentChampion().getName().equals("Dr Strange"))
			return "drStrange ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Electro"))
			return "Electro Ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Ghost Rider"))
			return "ghostRider ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Hela"))
			return "hela ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Hulk"))
			return "hulk ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Iceman"))
			return "iceMan ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Ironman"))
			return "ironMan ability1.mp3";
		if (game.getCurrentChampion().getName().equals("Loki"))
			return "loki ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Quicksilver"))
			return "quickSilver ability1.mp3";
		if (game.getCurrentChampion().getName().equals("Spiderman"))
			return "spiderMan ability1.mp3";
		if (game.getCurrentChampion().getName().equals("Thor"))
			return "thor ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Venom"))
			return "venom ability 1.mp3";
		else
			return "yellowJacket ability 1.mp3";
	}

	public String getSoundOfAbility2() {
		if (game.getCurrentChampion().getName().equals("Captain America"))
			return "capAmerica ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Deadpool"))
			return "deadPool ability2.mp3";
		if (game.getCurrentChampion().getName().equals("Dr Strange"))
			return "drStrange ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Electro"))
			return "Electro Ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Ghost Rider"))
			return "ghostRider ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Hela"))
			return "hela ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Hulk"))
			return "hulk ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Iceman"))
			return "iceMan ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Ironman"))
			return "unibeam.mp3";
		if (game.getCurrentChampion().getName().equals("Loki"))
			return "loki ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Quicksilver"))
			return "quickSilver ability2.mp3";
		if (game.getCurrentChampion().getName().equals("Spiderman"))
			return "spiderMan ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Thor"))
			return "thor ability 2.mp3";
		if (game.getCurrentChampion().getName().equals("Venom"))
			return "we are venom ability.mp3";
		else
			return "yellowJacket ability 2.mp3";
	}

	public String getSoundOfAbility3() {
		if (game.getCurrentChampion().getName().equals("Captain America"))
			return "capAmerica ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Deadpool"))
			return "deadPool ability3.mp3";
		if (game.getCurrentChampion().getName().equals("Dr Strange"))
			return "drStrange ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Electro"))
			return "Electro Ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Ghost Rider"))
			return "ghostRider ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Hela"))
			return "hela ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Hulk"))
			return "hulk ability 1.mp3";
		if (game.getCurrentChampion().getName().equals("Iceman"))
			return "iceMan ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Ironman"))
			return "ironMan ability2.mp3";
		if (game.getCurrentChampion().getName().equals("Loki"))
			return "loki ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Quicksilver"))
			return "quickSilver ability1.mp3";
		if (game.getCurrentChampion().getName().equals("Spiderman"))
			return "spiderMan ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Thor"))
			return "thor ability 3.mp3";
		if (game.getCurrentChampion().getName().equals("Venom"))
			return "venom ability 3.mp3";
		else
			return "yellowJacket ability 3.mp3";
	}

	@Override
	public void handle(ActionEvent arg0) {

	}

	public static Champion getChamp(int i) {
		return game.getAvailableChampions().get(i);
	}
}
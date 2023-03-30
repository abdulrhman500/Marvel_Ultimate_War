package Graphics;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

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
import javafx.scene.Node;
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

public class Quiz extends Application implements EventHandler<ActionEvent> {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage = new Stage();
		Game game = new Game(new Player("Yehia"), new Player("Yahia"));
		game.loadAbilities("Abilities.csv");
		game.loadChampions("Champions.csv");
		Label label1 = new Label();
		label1.setTranslateY(-200);
		Champion champ1 = game.getAvailableChampions().get((int)(Math.random()*game.getAvailableChampions().size()));
		Champion champ2 = game.getAvailableChampions().get((int)(Math.random()*game.getAvailableChampions().size()));
		Champion champ3 = game.getAvailableChampions().get((int)(Math.random()*game.getAvailableChampions().size()));
		prepareChampions(label1, champ1);
		Label label2 = new Label();
		prepareChampions(label2, champ2);
		Label label3=new Label();
		label3.setTranslateY(200);
		label3.setText(champ3.getName()+" "+champ3.getCurrentHP());
		label3.setOnMouseClicked(e->{
			champ3.setCurrentHP(champ3.getCurrentHP()-500);
			if(champ3.getCurrentHP()>=0)
			label3.setText(champ3.getName()+" "+champ3.getCurrentHP());
			
		});
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(label1,label2,label3);
		Scene scene = new Scene(vBox);
		stage.setScene(scene);
		stage.setWidth(1400);
		stage.setHeight(750);
		stage.setResizable(false);
		stage.show();
		
	}

	private void prepareChampions(Label label1, Champion champ1) {
		label1.setText(champ1.getName()+" "+champ1.getCurrentHP());
		label1.setOnMouseClicked(e->{
			champ1.setCurrentHP(champ1.getCurrentHP()-500);
			if(champ1.getCurrentHP()>=0)
			label1.setText(champ1.getName()+" "+champ1.getCurrentHP());

		});

	}

}

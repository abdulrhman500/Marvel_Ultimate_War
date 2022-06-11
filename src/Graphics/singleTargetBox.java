package Graphics;

import java.io.File;

import engine.Game;
import exceptions.AbilityUseException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughResourcesException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.abilities.Ability;
import model.world.Direction;

public class singleTargetBox {
	public static void display(String title,Game game,Ability ability,String sound) {
	      Stage window = new Stage();
	      window.initModality(Modality.APPLICATION_MODAL);
	      window.setTitle(title);
	      window.setMinWidth(300);
	      window.setMinHeight(200);
	      window.setResizable(false);
	      Label label= new Label("Enter a cell between 1 and 25 :");
	     TextField input = new TextField();
	      HBox layout = new HBox(5);
	      Button attackButton = new Button("Choose cell");
	      attackButton.setOnAction(e2->{
	      for(int i=0;i<input.getText().length();i++) {
	    	  if(!(input.getText().charAt(i)>='0' && input.getText().charAt(i)<='9')) { new AlertBox().display("Not a number", "Please enter a correct number");
	    	  window.close();}
	      }
	      int x = Integer.parseInt(input.getText());
	      if(x<1 || x>25) {
	    	  new AlertBox().display("Not a correct cell", "Please enter a cell between 1 and 25");
	    	  window.close();
	      }
	      else {
			x-=1;
			int i = x/5;
			int j = x%5;
			try {
				game.castAbility(ability, i, j);
				Media ability1 = new Media(new File(sound).toURI().toString());
				MediaPlayer ability1Player = new MediaPlayer(ability1);
				ability1Player.setAutoPlay(true);
				ability1Player.play();
			} catch (NotEnoughResourcesException | AbilityUseException | InvalidTargetException
					| CloneNotSupportedException e) {
				new AlertBox().display("Can't use this ability", "You are unallowed to do this ability");
			}
			window.close();
		}
	      });
	      layout.getChildren().addAll(label,input,attackButton);
	      layout.setAlignment(Pos.CENTER);
	      layout.setStyle("\r\n"
	      		+ "    -fx-padding: 8 15 15 15;\r\n"
	      		+ "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\r\n"
	      		+ "    -fx-background-radius: 8;\r\n"
	      		+ "    -fx-background-color: \r\n"
	      		+ "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\r\n"
	      		+ "        #9d4024,\r\n"
	      		+ "        #d86e3a,\r\n"
	      		+ "        radial-gradient(center 50 "
	      		+ "50%, radius 100%, #d86e3a, #c54e2c);\r\n"
	      		+ "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\r\n"
	      		+ "    -fx-font-weight: bold;\r\n"
	      		+ "    -fx-font-size: 1.1em;");
	      Scene scene = new Scene(layout);
	      window.getIcons().add(new Image("info.png"));
	      window.setScene(scene);
	      window.showAndWait();
	;	}
}

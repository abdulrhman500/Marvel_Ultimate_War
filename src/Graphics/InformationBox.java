package Graphics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InformationBox {
	public static void display(String title, String message) {
	      Stage window = new Stage();
	      window.initModality(Modality.APPLICATION_MODAL);
	      window.setTitle(title);
	      window.setMinWidth(300);
	      window.setMinHeight(200);
	      window.setResizable(false);
	      Label label = new Label();
	      label.setText(message);
	      Button button = new Button("Close");
	      button.setStyle("\r\n"
	      		+ "    -fx-background-color: \r\n"
	      		+ "        linear-gradient(#ffd65b, #e68400),\r\n"
	      		+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
	      		+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
	      		+ "        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\r\n"
	      		+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\r\n"
	      		+ "    -fx-background-radius: 30;\r\n"
	      		+ "    -fx-background-insets: 0,1,2,3,0;\r\n"
	      		+ "    -fx-text-fill: #654b00;\r\n"
	      		+ "    -fx-font-weight: bold;\r\n"
	      		+ "    -fx-font-size: 14px;\r\n"	
	      		+ "    -fx-padding: 10 20 10 20;");
	      label.setStyle("-fx-text-fill: black;\r\n"
	      		+ "    -fx-font-weight: bold;\r\n"
	      		+ "    -fx-font-size: 18px;\r\n"	);
	      button.setOnAction(EventHandler -> window.close());   
	      VBox layout = new VBox(10);
	      layout.getChildren().add(label);
	      layout.getChildren().add(button);
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

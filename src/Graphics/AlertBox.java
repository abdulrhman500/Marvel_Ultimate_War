package Graphics;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class AlertBox {
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
      button.setOnAction(EventHandler -> window.close());
      label.setStyle("\r\n"
      		+ "    -fx-background-color: \r\n"
      		+ "        #090a0c,\r\n"
      		+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
      		+ "        linear-gradient(#20262b, #191d22),\r\n"
      		+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\r\n"
      		+ "    -fx-background-radius: 5,4,3,5;\r\n"
      		+ "    -fx-background-insets: 0,1,2,0;\r\n"
      		+ "    -fx-text-fill: white;\r\n"
      		+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
      		+ "    -fx-font-family: \"Arial\";\r\n"
      		+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);\r\n"
      		+ "    -fx-font-size: 12px;\r\n"
      		+ "    -fx-padding: 10 20 10 20;");
      
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
      window.getIcons().add(new Image("icon.jpg"));
      window.setScene(scene);
      window.showAndWait();
;	}
}

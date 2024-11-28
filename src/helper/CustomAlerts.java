package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class CustomAlerts {
    
    public static void showErrorAlert(String title, String subtitle) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(subtitle);
    
        alert.showAndWait();
    }

    public static void showInformationAlert(String title, String subtitle) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(subtitle);
        
        alert.showAndWait();
    }

}

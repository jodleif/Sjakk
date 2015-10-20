package GUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class SjakkApp extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Sjakk - Jo Øivind Gjernes");

		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
		System.exit(0);
	}
}

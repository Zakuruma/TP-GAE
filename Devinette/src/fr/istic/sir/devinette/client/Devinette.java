package fr.istic.sir.devinette.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("unused")
public class Devinette implements EntryPoint {
	
	private int nbCoups = 0;
	private int randomNumber;
	private int min = 1;
	private int max = 100;

	private boolean isFinished = false;

	private VerticalPanel mainPanel = new VerticalPanel();
	
	private VerticalPanel gamePanel = new VerticalPanel();
	private HorizontalPanel startPanel = new HorizontalPanel();

	private TextBox numberTextBox = new TextBox();

	private Button startButton = new Button("Démarrer");
	private Button replayButton = new Button("Rejouer ?");

	private Label label = new Label("Choisissez un nombre entre " + min + " et " + max);
	private Label resultLabel = new Label("");


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
		resultLabel.setVisible(false);

		gamePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		gamePanel.setSpacing(10);
		gamePanel.add(label);
		gamePanel.add(resultLabel);
		gamePanel.add(numberTextBox);
		gamePanel.setVisible(false);

		startPanel.add(startButton);

		mainPanel.add(gamePanel);
		mainPanel.add(startPanel);
		RootPanel.get("mainDevinette").add(mainPanel);

		// Listen for keyboard events and click events
		startButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				isFinished = false;
				randomNumber = generateRandomNumber();
				nbCoups = 0;
				startButton.setEnabled(false);
				gamePanel.setVisible(true);
				numberTextBox.setText("");
				numberTextBox.setEnabled(true);
				resultLabel.setVisible(false);
				label.setVisible(true);
			}
		});
		
		numberTextBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent e) {
				if(e.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					resultLabel.setText(verifNumber());
					resultLabel.setVisible(true);
					if(isFinished) {
						numberTextBox.setEnabled(false);
						label.setVisible(false);
						startButton.setEnabled(true);
					}
				}
			}
			
		});
	}
	
	public int generateRandomNumber() {
		return (int) (Math.random() * (max - min) + min);
	}
	
	public String verifNumber() {
		String txt = numberTextBox.getText();
		int number;
		try {
			number = Integer.parseInt(txt);
		} catch(Exception e) {
			return "Veuillez choisir une valeur numérique";
		}
		if(number < min || number > max) {
			return "Vous devez choisir un nombre entre " + min + " et " + max;
		}
		nbCoups++;
		if(number == randomNumber) {
			isFinished = true;
			return "Vous avez gagné en " + nbCoups + " coups";
		} else if(number < randomNumber) {
			return "Trop petit";
		}
		return "Trop grand";
	}

}

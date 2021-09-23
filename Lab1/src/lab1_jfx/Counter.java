package lab1_jfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * Assessment: Lab1
 * @author JieKe
 * Date:Sep 23, 2021
 * Description: create a stage let user input text then 
 * display the number of line, chars, words on the screen.
 */

/*
 * This program is simply mimics word counter in MicrosoftWord , the user types some text in a TextArea and presses
 * a button.  The program computes and displays the number of lines, words,characters and digits
 * in the text */


//TODO Inherit from Application class. The main JavaFX class must inherit from Application.
public class Counter extends Application{
    public static void main(String[] args) {
    	//TODO to start a JavaFX application we must call the static method
    	launch( args); 
    	//in the main method. This method is from the Application Class.
    }

    //---------------------------------------------------------------------

    private TextArea textInput;     // For the user's input text.

    private Label lineCountLabel;   // For displaying the number of lines.
    private Label wordCountLabel;   // For displaying the number of words.
    private Label charCountLabel;   // For displaying the number of chars.
    private Label digitCountLabel;
    private ToolBar statusBar;

	private Stage stage;  //This program's window

    /**
     * The constructor creates components and lays out the window.
     */ 
    public void start(Stage stage) {

        textInput = new TextArea();
        textInput.setPrefRowCount(20);
        textInput.setPrefColumnCount(50);
        statusBar = new ToolBar();
        this.stage = stage;
        //TODO call the method createStatusBar
        createStatusBar();
        //TODO Create a new button  "process the text" .
        Button countButton= new Button("Process the text");
        //TODO call setOnKeyPressed on this button and pass a lambda to it. In the lambda if the key pressed, invoke processInput method 

        countButton.setOnAction((ActionEvent e) -> { processInput();		
		});
        //TODO set  button style   as "-fx-background-color: darkslateblue; -fx-text-fill: white;"
        
        countButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white");
       // statusBar.setMaxWidth(1000);
        
        
     /*when escape key is pressed close the application*/
        
        stage.addEventHandler( KeyEvent.KEY_RELEASED, ( KeyEvent event) -> {

			if ( !event.isConsumed() && KeyCode.ESCAPE == event.getCode()) {
				stage.hide();
			}
		});

        String style = "-fx-padding: 4px; -fx-font: bold 14pt serif; -fx-background-color: white";
        
        /* Create each of the labels, and set their properties. */
        
        lineCountLabel = new Label("  Number of lines:");
        lineCountLabel.setStyle("-fx-text-fill: green; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
        lineCountLabel.setMaxWidth(1000);
        
        //TODO Create wordCountLabel, and set their properties. 
        wordCountLabel = new Label("  Number of words:");
        wordCountLabel.setStyle("-fx-text-fill: red; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
        wordCountLabel.setMaxWidth(1000);      
        //TODO Create charCountLabel, and set their properties. 
        charCountLabel = new Label("  Number of chars:");
        charCountLabel.setStyle("-fx-text-fill: blue; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
        charCountLabel.setMaxWidth(1000);   
        //TODO Create digitCountLabel, and set their properties. 
        digitCountLabel = new Label("  Number of digits:");
        digitCountLabel.setStyle("-fx-text-fill: black; -fx-font: italic bold 16pt serif;-fx-padding: 4px;-fx-background-color: white");
        digitCountLabel.setMaxWidth(1000);        
        

        /* Use a VBox as the root component */

      
		VBox root = new VBox( 6,new BorderPane(statusBar),textInput, new BorderPane(countButton),
                                  lineCountLabel, wordCountLabel, charCountLabel,digitCountLabel);
       
        root.setStyle("-fx-background-color: lightgrey; -fx-border-color: grey; -fx-border-width:5px");
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MicrosoftWord/Word/Char/digit Counter");
        stage.setResizable(false);
        stage.show();

    } 

    private void createStatusBar() {
    	
    	//TODO create a new label to show "Type your text here "
    	Label typeLabel	= new Label("Type your text here");
    	//TODO set the style as "-fx-text-fill: black; -fx-font: bold  12pt serif;-fx-padding: 4px;"
    	typeLabel.setStyle("-fx-text-fill: black; -fx-font: bold  12pt serif;-fx-padding: 4px");
    	//TODO create a new Pane object
    	Pane pane1 = new Pane();
    	//TODO call the static method setHgrow from HBox and pass to it the pane object and Priority.ALWAYS.
    	//pane1.getChildren().addAll(pane1,escLabel);
    	HBox.setHgrow(pane1,Priority.ALWAYS);
    	//TODO create a new label and set the text as "Press Esc to Exit".
    	Label escLabel = new Label("Press Esc to Exit");
    	//TODO use the statusBar object to store the 3 object we created here.
    			//to access the list of children in a ToolBar use the method getItems.
    			//getItems method will return a list. use addAll on it to add all the Nodes.
    	statusBar.getItems().addAll(typeLabel,pane1,escLabel);

	}
    
    
    /**
     * This method will be called when the user clicks the "Process the Text" button.
     * It gets the text from the text area, counts the number of chars, words, digits
     * and lines that it contains, and sets the labels to display the results.
     */
    public void processInput() {

        String text;  // The user's input from the text area.

        int charCt, wordCt, lineCt, digitCt;  // Char, word, line and digits  counts.

        text = textInput.getText();

        charCt = text.length();  // The number of characters in the
                                 //    text is just its length.

        /* Compute the wordCt by counting the number of characters
              in the text that lie at the beginning of a word.  The
              beginning of a word is a letter such that the preceding
              character is not a letter. If the letter is the first character in the
              text, then it is the beginning of a word.  If the letter
              is preceded by an apostrophe, and the apostrophe is
              preceded by a letter, than its not the first character
              in a word.
         */

        wordCt = 0;
        for (int i = 0; i < charCt; i++) {
            boolean startOfWord;  // Is character i the start of a word?
            if ( Character.isLetter(text.charAt(i)) == false )
                startOfWord = false;  // No.  It's not a letter.
            else if (i == 0)
                startOfWord = true;   // Yes.  It's a letter at start of text.
            else if ( Character.isLetter(text.charAt(i-1)) )
                startOfWord = false;  // No.  It's a letter preceded by a letter.
            else if ( text.charAt(i-1) == '\'' && i > 1 
                    && Character.isLetter(text.charAt(i-2)) )
                startOfWord = false;  // No.  It's a continuation of a word
                                      //      after an apostrophe.
            else
                startOfWord = true;   // Yes.  It's a letter preceded by
                                      //       a non-letter.
            if (startOfWord)
                wordCt++;
        }

        /* The number of lines is just  the number of times the
              end of line character, '\n', occurs in the text. */

        lineCt = 0;
        for (int i = 0; i < charCt; i++) {
            if (text.charAt(i) == '\n')
                lineCt++;
        }
        
        //TODO count the number of digits occurs in the text.
        digitCt = 0;
        for (int i = 0; i < charCt; i++) {
        	if (Character.isDigit(text.charAt(i)))
        	{
                digitCt++;
        	}
        }
        
        /* Set the labels to display the data. */

        lineCountLabel.setText("  Number of lines:  " + lineCt);
        wordCountLabel.setText("  Number of words:  " + wordCt);
        charCountLabel.setText("  Number of chars:  " + charCt);
        digitCountLabel.setText("  Number of digits:  " + digitCt);

    }  // end processInput()



}




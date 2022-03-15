/** This class represents a Rock, Paper, Scissors game.
 *  The user enters in their choice and the application will select its own.
 *  The winner is determined by the number of wins and the user will be able
 *  to dictate how many rounds to play.
 *  An added feature include a popup-window that acts as a warning to users
 *  when they enter a number less than 1 for the amount of rounds. Another
 *  added feature are themes. The rock, paper and scissors icon, text and
 *  background colors change alongside the theme.
 *  @author Thea Arias
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    //OG theme
    static Image rock1 = createImage("file:Images/rock.png");
    static Image rock2 = createImage("file:Images/rock_black.png");
    static Image paper1 = createImage("file:Images/paper.png");
    static Image paper2 = createImage("file:Images/paper_black.png");
    static Image scissors1 = createImage("file:Images/scissors.png");
    static Image scissors2 = createImage("file:Images/scissors_black.png");

    //emoji theme
    static Image emojiRock1 = createImage("file:Images/emojirock1.png");
    static Image emojiRock2 = createImage("file:Images/emojirock2.png");
    static Image emojiPaper1 = createImage("file:Images/emojipaper1.png");
    static Image emojiPaper2 = createImage("file:Images/emojipaper2.png");
    static Image emojiScissors1 = createImage("file:Images/emojiscissors1.png");
    static Image emojiScissors2 = createImage("file:Images/emojiscissors2.png");

    //color theme
    static Image colorRock1 = createImage("file:Images/colorrock1.png");
    static Image colorRock2 = createImage("file:Images/colorrock2.png");
    static Image colorPaper1 = createImage("file:Images/colorpaper1.png");
    static Image colorPaper2 = createImage("file:Images/colorpaper2.png");
    static Image colorScissors1 = createImage("file:Images/colorscissors1.png");
    static Image colorScissors2 = createImage("file:Images/colorscissors2.png");

    static ToggleGroup difficultySelection = new ToggleGroup();
    static ToggleGroup themeSelection = new ToggleGroup();
    static int gamesNum = 0,difficulty=0;
    static boolean gameOver=false,ogBool=true,emojiBool=false,colorBool=false;


    @Override
    public void start(Stage primaryStage) throws Exception{
        // // // // first window // // // //
        primaryStage.setTitle("Rock, Paper, Scissors");
        Label title = createTitleLabel("Rock, Paper, Scissors");


        //theme RB
        Label subtitle2 = createSubtitleLabel("Theme:");
        RadioButton themeOG = createThemeRB("Original");
        themeOG.setSelected(true);
        RadioButton themeEmoji = createThemeRB("Emoji");
        RadioButton themeColor = createThemeRB("Color");
        HBox themeRadioHB = new HBox(subtitle2, themeOG, themeEmoji, themeColor);
        styleThemeHB(themeRadioHB);


        //difficulty RB
        Label subtitle = createSubtitleLabel("Difficulty:");
        RadioButton easyRB = createRB("Easy");
        easyRB.setSelected(true);
        RadioButton mediumRB = createRB("Medium");
        RadioButton hardRB = createRB("Hard");
        RadioButton expertRB = createRB("Expert");
        HBox radioHB = new HBox(subtitle,easyRB,mediumRB,hardRB,expertRB);
        styleRBHB(radioHB);

        TextField roundsTF = createTextField("Enter number of rounds here");
        Button beginButton = createButtons("Begin");
        styleBeginButton(beginButton);
        VBox mainMenuHB = new VBox(title,themeRadioHB,radioHB,roundsTF,beginButton);
        mainMenuHB.setSpacing(20);


        //  //  //  //  second window // // //  //
        ImageView viewRock = new ImageView(rock1);
        ImageView viewPaper = new ImageView(paper1);
        ImageView viewScissors = new ImageView(scissors1);
        HBox rpsHB = new HBox(viewRock,viewPaper,viewScissors);
        styleRpsHB(rpsHB);

        Label screen = new Label("Hi, let's play! Select an image to begin.");
        styleScreenLabel(screen);

        Label roundLabel = createLabel("Round:");
        Label round = createLabel("0",1);
        Label winsLabel = createLabel("Wins:");
        Label wins = createLabel("0",1);
        Label lossesLabel = createLabel("Losses:");
        Label losses = createLabel("0",1);
        Label tiesLabel = createLabel("Ties:");
        Label ties = createLabel("0",1);
        HBox scoreHB = new HBox(roundLabel,round,winsLabel,wins,lossesLabel,losses,tiesLabel,ties);
        styleHB(scoreHB);

        Button resetButton = createButtons("Create Game");
        Button mainMenuButton = createButtons("Main Menu");
        Button seeChartButton = createButtons("See Chart");
        HBox menuHB = new HBox(resetButton,mainMenuButton,seeChartButton);
        styleHB(menuHB);
        menuHB.setSpacing(30);

        //scenes and stages
        VBox mainRoot = new VBox(mainMenuHB);
        VBox gameRoot = new VBox(rpsHB,screen,scoreHB,menuHB);
        Scene mainScene = new Scene(mainRoot,600,450);
        Scene gameScene = new Scene(gameRoot,600,450);
        styleRoot(mainRoot);
        styleRoot(gameRoot);


        // // // // main menu GUI // // // //
        beginButton.setOnMouseClicked(mouseEvent -> {
            String user = roundsTF.getText();
            gamesNum = Integer.parseInt(user);  //string to int
            if (gamesNum<1) {
                //warning popup
                Label warningLabel = new Label("Number of games cannot be less than 1!");
                styleWarningLabel(warningLabel);
                VBox warningRoot = new VBox(warningLabel);
                styleWarningRoot(warningRoot);
                Scene warningScene = new Scene(warningRoot,400,100);
                Stage warningStage = new Stage();
                warningStage.setScene(warningScene);
                warningStage.setTitle("Warning");
                warningStage.show();
            } else {
                //find out difficulty
                difficulty = getDifficulty(easyRB,mediumRB,hardRB,expertRB);

                primaryStage.setScene(gameScene);
                primaryStage.show();
            }
        });     //begin game button event
        themeOG.setOnMouseClicked(mouseEvent -> {
            ogBool=true;
            emojiBool=false;
            colorBool=false;
            styleRoot(mainRoot);
            styleRoot(gameRoot);
            styleGameImage(viewRock,viewPaper,viewScissors);
            styleMainText(title,subtitle2,themeOG,themeEmoji,themeColor,subtitle,easyRB,mediumRB,hardRB,expertRB,roundsTF);
            styleGameText(screen,roundLabel,round,winsLabel,wins,lossesLabel,losses,tiesLabel,ties);
        });         //OG theme rb
        themeEmoji.setOnMouseClicked(mouseEvent -> {
            ogBool=false;
            emojiBool=true;
            colorBool=false;
            styleRoot(mainRoot);
            styleRoot(gameRoot);
            styleGameImage(viewRock,viewPaper,viewScissors);
            styleMainText(title,subtitle2,themeOG,themeEmoji,themeColor,subtitle,easyRB,mediumRB,hardRB,expertRB,roundsTF);
            styleGameText(screen,roundLabel,round,winsLabel,wins,lossesLabel,losses,tiesLabel,ties);
        });      //emoji theme rb
        themeColor.setOnMouseClicked(mouseEvent -> {
            ogBool=false;
            emojiBool=false;
            colorBool=true;
            styleRoot(mainRoot);
            styleRoot(gameRoot);
            styleGameImage(viewRock,viewPaper,viewScissors);
            styleMainText(title,subtitle2,themeOG,themeEmoji,themeColor,subtitle,easyRB,mediumRB,hardRB,expertRB,roundsTF);
            styleGameText(screen,roundLabel,round,winsLabel,wins,lossesLabel,losses,tiesLabel,ties);
        });      //color theme rb


        // // // // rps game GUI // // // //
        viewRock.setOnMouseClicked(mouseEvent -> {
            if(!gameOver) {
                if(ogBool) {
                    viewRock.setImage(rock2);
                    viewPaper.setImage(paper1);
                    viewScissors.setImage(scissors1);
                }if(emojiBool){
                    viewRock.setImage(emojiRock2);
                    viewPaper.setImage(emojiPaper1);
                    viewScissors.setImage(emojiScissors1);
                }if(colorBool){
                    viewRock.setImage(colorRock2);
                    viewPaper.setImage(colorPaper1);
                    viewScissors.setImage(colorScissors1);
                }

                calculate(0, difficulty, screen, round, wins, losses, ties);
            }
        });        //rock
        viewPaper.setOnMouseClicked(mouseEvent -> {
            if(!gameOver) {
                if(ogBool) {
                    viewRock.setImage(rock1);
                    viewPaper.setImage(paper2);
                    viewScissors.setImage(scissors1);
                }if(emojiBool){
                    viewRock.setImage(emojiRock1);
                    viewPaper.setImage(emojiPaper2);
                    viewScissors.setImage(emojiScissors1);
                }if(colorBool){
                    viewRock.setImage(colorRock1);
                    viewPaper.setImage(colorPaper2);
                    viewScissors.setImage(colorScissors1);
                }

                calculate(1, difficulty, screen,round,wins,losses,ties);
            }
        });       //paper
        viewScissors.setOnMouseClicked(mouseEvent -> {
            if(!gameOver) {
                if(ogBool) {
                    viewRock.setImage(rock1);
                    viewPaper.setImage(paper1);
                    viewScissors.setImage(scissors2);
                }if(emojiBool){
                    viewRock.setImage(emojiRock1);
                    viewPaper.setImage(emojiPaper1);
                    viewScissors.setImage(emojiScissors2);
                }if(colorBool){
                    viewRock.setImage(colorRock1);
                    viewPaper.setImage(colorPaper1);
                    viewScissors.setImage(colorScissors2);
                }

                calculate(2,difficulty,screen,round,wins,losses,ties);
            }
        });    //scissors

        resetButton.setOnMouseClicked(mouseEvent -> {
            reset(viewRock,viewPaper,viewScissors,screen,round,wins,losses,ties);
            styleRoot(mainRoot);
            styleRoot(gameRoot);
            styleGameImage(viewRock,viewPaper,viewScissors);
            gameOver=false;
        });     //create game button event
        mainMenuButton.setOnMouseClicked(mouseEvent -> {
            reset(viewRock,viewPaper,viewScissors,screen,round,wins,losses,ties);
            roundsTF.setText("Enter number of rounds here");
            easyRB.setSelected(true);
            difficulty=0;
            gamesNum=0;
            primaryStage.setScene(mainScene);
            primaryStage.show();
            gameOver=false;
            styleGameImage(viewRock,viewPaper,viewScissors);
            styleGameText(screen,roundLabel,round,winsLabel,wins,lossesLabel,losses,tiesLabel,ties);
        });  //main menu button event
        seeChartButton.setOnMouseClicked(mouseEvent -> {
            //pie chart scene
            int percentWin = Integer.parseInt(wins.getText()) * 100;
            int percentLoss = Integer.parseInt(losses.getText()) * 100;
            int percentTie = Integer.parseInt(ties.getText()) * 100;

            Label chartTitle = new Label("Game Statistics");
            PieChart chart = new PieChart();
            styleChartTitle(chartTitle);
            VBox pieRoot = new VBox(chartTitle,chart);
            styleChartVB(pieRoot);
            StackPane pieStack = new StackPane(pieRoot);
            Scene chartScene = new Scene(pieStack, 500,500);
            Stage chartStage = new Stage();

            PieChart.Data slice1 = new PieChart.Data("Wins", percentWin);
            PieChart.Data slice2 = new PieChart.Data("Losses", percentLoss);
            PieChart.Data slice3 = new PieChart.Data("Ties", percentTie);
            chart.getData().add(slice1);
            chart.getData().add(slice2);
            chart.getData().add(slice3);

            chartStage.setScene(chartScene); //stage created but not shown
            chartStage.setTitle("Score tracker");
            chartStage.show();
        });  //see chart button event

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    // // // // gui style methods & themes /// /// // //
    public static void styleRoot(VBox root) {
        root.setPadding(new Insets(70,50,20,50));
        root.setSpacing(20);

        if(ogBool) {
            root.setBackground(new Background(new BackgroundFill(Color.web("81B29A"), CornerRadii.EMPTY, Insets.EMPTY)));
        }if (emojiBool){
            root.setBackground(new Background(new BackgroundFill(Color.web("3D405B"), CornerRadii.EMPTY, Insets.EMPTY)));
        }if (colorBool) {
            root.setBackground(new Background(new BackgroundFill(Color.web("F4F1DE"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    public static void styleMainText(Label title, Label subtitle2, RadioButton themeOG, RadioButton themeEmoji, RadioButton themeColor,
                                     Label subtitle, RadioButton easy, RadioButton medium, RadioButton hard, RadioButton expert, TextField tf) {
        if(ogBool){
            title.setFont(Font.font("Lucida Sans Typewriter", FontWeight.EXTRA_BOLD, 34));
            title.setTextFill(Color.web("3D405B"));
            subtitle2.setFont(Font.font("Arial Rounded", FontWeight.BOLD, 15));
            subtitle2.setTextFill(Color.BLACK);
            themeOG.setFont(Font.font("Arial Rounded", FontWeight.LIGHT,12));
            themeOG.setTextFill(Color.web("3D405B"));
            themeEmoji.setFont(Font.font("Arial Rounded", FontWeight.LIGHT,12));
            themeEmoji.setTextFill(Color.web("3D405B"));
            themeColor.setFont(Font.font("Arial Rounded", FontWeight.LIGHT,12));
            themeColor.setTextFill(Color.web("3D405B"));
            subtitle.setFont(Font.font("Arial Rounded", FontWeight.BOLD, 15));
            subtitle.setTextFill(Color.BLACK);
            easy.setFont(Font.font("Arial Rounded", FontWeight.MEDIUM,12));
            easy.setTextFill(Color.web("3D405B"));
            medium.setFont(Font.font("Arial Rounded", FontWeight.MEDIUM,12));
            medium.setTextFill(Color.web("3D405B"));
            hard.setFont(Font.font("Arial Rounded", FontWeight.MEDIUM,12));
            hard.setTextFill(Color.web("3D405B"));
            expert.setFont(Font.font("Arial Rounded", FontWeight.MEDIUM,12));
            expert.setTextFill(Color.web("3D405B"));
            tf.setFont(Font.font("Arial Rounded", FontWeight.NORMAL,14));
        }if(emojiBool){
            title.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 34));
            title.setTextFill(Color.web("F4F1DE"));
            subtitle2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
            subtitle2.setTextFill(Color.BLACK);
            themeOG.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT,12));
            themeOG.setTextFill(Color.web("F4F1DE"));
            themeEmoji.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT,12));
            themeEmoji.setTextFill(Color.web("F4F1DE"));
            themeColor.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT,12));
            themeColor.setTextFill(Color.web("F4F1DE"));
            subtitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
            subtitle.setTextFill(Color.BLACK);
            easy.setFont(Font.font("Comic Sans MS", FontWeight.MEDIUM,12));
            easy.setTextFill(Color.web("F4F1DE"));
            medium.setFont(Font.font("Comic Sans MS", FontWeight.MEDIUM,12));
            medium.setTextFill(Color.web("F4F1DE"));
            hard.setFont(Font.font("Comic Sans MS", FontWeight.MEDIUM,12));
            hard.setTextFill(Color.web("F4F1DE"));
            expert.setFont(Font.font("Comic Sans MS", FontWeight.MEDIUM,12));
            expert.setTextFill(Color.web("F4F1DE"));
            tf.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL,14));
        }if(colorBool){
            title.setFont(Font.font("Calisto MT", FontWeight.EXTRA_BOLD, 34));
            title.setTextFill(Color.web("3D405B"));
            subtitle2.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
            subtitle2.setTextFill(Color.BLACK);
            themeOG.setFont(Font.font("Cambria", FontWeight.LIGHT,12));
            themeOG.setTextFill(Color.web("3D405B"));
            themeEmoji.setFont(Font.font("Cambria", FontWeight.LIGHT,12));
            themeEmoji.setTextFill(Color.web("3D405B"));
            themeColor.setFont(Font.font("Cambria", FontWeight.LIGHT,12));
            themeColor.setTextFill(Color.web("3D405B"));
            subtitle.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
            subtitle.setTextFill(Color.BLACK);
            easy.setFont(Font.font("Cambria", FontWeight.MEDIUM,12));
            easy.setTextFill(Color.web("3D405B"));
            medium.setFont(Font.font("Cambria", FontWeight.MEDIUM,12));
            medium.setTextFill(Color.web("3D405B"));
            hard.setFont(Font.font("Cambria", FontWeight.MEDIUM,12));
            hard.setTextFill(Color.web("3D405B"));
            expert.setFont(Font.font("Cambria", FontWeight.MEDIUM,12));
            expert.setTextFill(Color.web("3D405B"));
            tf.setFont(Font.font("Cambria", FontWeight.NORMAL,14));
        }
    }
    public static void styleGameImage(ImageView viewRock, ImageView viewPaper, ImageView viewScissors){
        if(ogBool){
            viewRock.setImage(rock1);
            viewPaper.setImage(paper1);
            viewScissors.setImage(scissors1);
        }if(emojiBool){
            viewRock.setImage(emojiRock1);
            viewPaper.setImage(emojiPaper1);
            viewScissors.setImage(emojiScissors1);
        }if(colorBool){
            viewRock.setImage(colorRock1);
            viewPaper.setImage(colorPaper1);
            viewScissors.setImage(colorScissors1);
        }
    }
    public static void styleGameText(Label screen, Label roundLabel, Label round, Label winsLabel, Label wins, Label lossesLabel, Label losses, Label tiesLabel, Label ties){
        if(ogBool){
            screen.setFont(Font.font("Arial Rounded",12));
            screen.setTextFill(Color.web("3D405B"));
            roundLabel.setTextFill(Color.web("3D405B"));
            round.setTextFill(Color.web("3D405B"));
            winsLabel.setTextFill(Color.web("3D405B"));
            wins.setTextFill(Color.web("3D405B"));
            lossesLabel.setTextFill(Color.web("3D405B"));
            losses.setTextFill(Color.web("3D405B"));
            tiesLabel.setTextFill(Color.web("3D405B"));
            ties.setTextFill(Color.web("3D405B"));
        }if(emojiBool){
            screen.setFont(Font.font("Courier New",12));
            screen.setTextFill(Color.web("F4F1DE"));
            roundLabel.setTextFill(Color.web("F4F1DE"));
            round.setTextFill(Color.web("F4F1DE"));
            winsLabel.setTextFill(Color.web("F4F1DE"));
            wins.setTextFill(Color.web("F4F1DE"));
            lossesLabel.setTextFill(Color.web("F4F1DE"));
            losses.setTextFill(Color.web("F4F1DE"));
            tiesLabel.setTextFill(Color.web("F4F1DE"));
            ties.setTextFill(Color.web("F4F1DE"));
        }if(colorBool){
            screen.setFont(Font.font("Calisto MT",12));
            screen.setTextFill(Color.web("3D405B"));
            roundLabel.setTextFill(Color.web("3D405B"));
            round.setTextFill(Color.web("3D405B"));
            winsLabel.setTextFill(Color.web("3D405B"));
            wins.setTextFill(Color.web("3D405B"));
            lossesLabel.setTextFill(Color.web("3D405B"));
            losses.setTextFill(Color.web("3D405B"));
            tiesLabel.setTextFill(Color.web("3D405B"));
            ties.setTextFill(Color.web("3D405B"));
        }
    }

    // // // // first window methods // // // //
    public static Label createTitleLabel(String str) {
        Label temp = new Label(str);
        temp.setPrefWidth(Double.MAX_VALUE);
        temp.setAlignment(Pos.CENTER);
        temp.setPadding(new Insets(10));
        temp.setFont(Font.font("Lucida Sans Typewriter", FontWeight.EXTRA_BOLD, 34));
        temp.setTextFill(Color.web("3D405B"));
        return temp;
    }
    public static Label createSubtitleLabel(String str) {
        Label temp = new Label(str);
        temp.setFont(Font.font("Arial Rounded", FontWeight.BOLD, 15));
        temp.setTextFill(Color.BLACK);
        return temp;
    }
    public static RadioButton createThemeRB(String str) {
        RadioButton temp = new RadioButton(str);
        temp.setFont(Font.font("Arial Rounded", FontWeight.LIGHT,12));
        temp.setTextFill(Color.web("3D405B"));
        temp.setToggleGroup(themeSelection);
        return temp;
    }
    public static void styleThemeHB(HBox hb){
        hb.setPrefWidth(Double.MAX_VALUE);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(30);
    }
    public static RadioButton createRB(String str) {
        RadioButton temp = new RadioButton(str);
        temp.setFont(Font.font("Arial Rounded", FontWeight.MEDIUM,12));
        temp.setTextFill(Color.web("3D405B"));
        temp.setToggleGroup(difficultySelection);
        return temp;
    }
    public static void styleRBHB(HBox hb) {
        hb.setPrefWidth(Double.MAX_VALUE);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(15);
    }
    public static TextField createTextField(String str){
        TextField temp = new TextField(str);
        temp.setAlignment(Pos.CENTER);
        temp.setFont(Font.font("Arial Rounded", FontWeight.NORMAL,14));
        return temp;
    }
    public static void styleBeginButton(Button begin) {
        begin.setPrefWidth(Double.MAX_VALUE);
        begin.setAlignment(Pos.CENTER);
    }
    public static void styleWarningLabel(Label label) {
        label.setPrefWidth(Double.MAX_VALUE);
        label.setTextFill(Color.web("F4F1DE"));
        label.setFont(Font.font("Arial Rounded", FontWeight.BOLD, 14));
        label.setAlignment(Pos.CENTER);
    }
    public static void styleWarningRoot(VBox root) {
        root.setBackground(new Background(new BackgroundFill(Color.web("E07A5F"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setPadding(new Insets(20));
    }

    // // // // second window methods // // // //
    public static Image createImage(String path) {
        return new Image(path,100,0,true,true);
    }
    public static void styleRpsHB(HBox hb) {
        hb.setPadding(new Insets(20));
        hb.setSpacing(50);
        hb.setPrefWidth(Double.MAX_VALUE);
        hb.setAlignment(Pos.CENTER);
    }
    public static void styleScreenLabel(Label label) {
        label.setPrefWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial Rounded",12));
        label.setTextFill(Color.web("3D405B"));
    }
    public static Label createLabel(String str){
        Label temp = new Label(str);
        temp.setFont(Font.font("Lucida Console", FontWeight.BOLD, 13));
        temp.setTextFill(Color.web("3D405B"));
        return temp;
    }
    public static Label createLabel(String str, int i){
        Label temp = new Label(str);
        temp.setFont(Font.font("Lucida Console", FontWeight.BOLD, 13));
        temp.setTextFill(Color.web("3D405B"));
        temp.setPadding(new Insets(0,15,0,10));
        return temp;
    }
    public static void styleHB(HBox hb) {
        hb.setPadding(new Insets(20));
        hb.setPrefWidth(Double.MAX_VALUE);
        hb.setAlignment(Pos.CENTER);
    }
    public static Button createButtons(String str) {
        Button temp = new Button(str);
        temp.setPrefSize(120,50);
        temp.setFont(Font.font("Arial Rounded",13));
        temp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(5), BorderWidths.DEFAULT)));
        return temp;
    }

    //pie chart methods // // // //
    public static void styleChartVB (VBox vb) {
        vb.setPadding(new Insets(20));
        vb.setSpacing(20);

        if(ogBool) {
            vb.setBackground(new Background(new BackgroundFill(Color.web("81B29A"), CornerRadii.EMPTY, Insets.EMPTY)));
        }if(emojiBool){
            vb.setBackground(new Background(new BackgroundFill(Color.web("3D405B"), CornerRadii.EMPTY, Insets.EMPTY)));
        }if(colorBool){
            vb.setBackground(new Background(new BackgroundFill(Color.web("F4F1DE"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    public static void styleChartTitle (Label label) {
        label.setPrefWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);

        if(ogBool) {
            label.setFont(Font.font("Lucida Sans Typewriter", FontWeight.EXTRA_BOLD, 30));
            label.setTextFill(Color.web("3D405B"));
        }if(emojiBool){
            label.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 30));
            label.setTextFill(Color.web("F4F1DE"));
        }if(colorBool){
            label.setFont(Font.font("Calisto MT", FontWeight.EXTRA_BOLD, 30));
            label.setTextFill(Color.web("3D405B"));
        }
    }

    //rps game methods // // // //
    public static int getDifficulty(RadioButton easy, RadioButton medium, RadioButton hard, RadioButton expert) {
        int num=-1;

        if (easy.isSelected()) {
            num= 0;
        } if (medium.isSelected()) {
            num= 1;
        } if (hard.isSelected()) {
            num= 2;
        } if (expert.isSelected()) {
            num= 3;
        }
        return num;
    }
    public static int getComChoice (int user, int lvl) {
        //user 0rock 1paper 2scissors
        //lvl 0easy 1medium 2hard 3expert
        Random random = new Random();
        int num=-1,com=0;

        switch (lvl) {
            case 0: //easy lvl
                com= random.nextInt(2); //0,1,2
            case 1: //med lvl
                num=random.nextInt(11);
                switch(user) {
                    case 0: //user:rock, com:paper
                        if (num==0 || num==1 || num==2) { //0,1,2 com:rock
                            com= 0;
                        } if (num>=3 && num<=8) { //3,4,5,6,7,8 com:paper
                            com= 1;
                        } else { //9,10,11, com:scissors
                            com= 2;
                        }
                        break;
                    case 1: //user:paper, com:scissors
                        if (num==0 || num==1 || num==2) { //0,1,2 com:rock
                            com= 0;
                        } if (num==3 || num==4 || num==5) { //3,4,5 com:paper
                            com= 1; //com:paper
                        } else { //6,7,8,9,10,11 com:scissors
                            com= 2;
                        }
                        break;
                    case 2: //user:scissors, com:rock
                        if (num>=0 && num<=5) { //0,1,2,3,4,5 com:rock
                            com= 0;
                        } if (num==6 || num==7 || num==8) { //6,7,8 com:paper
                            com= 1; //com:paper
                        } else { //9,10,11 com:scissors
                            com= 2;
                        }
                        break;
                }
                break;
            case 2: //hard lvl
                num=random.nextInt(11);
                switch(user) {
                    case 0: //user:rock, com:paper
                        if (num==0 || num==1) { //0,1 com:rock
                            com= 0;
                        } if (num>=2 && num<=9) { //2,3,4,5,6,7,8,9 com:paper
                            com= 1;
                        } else { //10,11, com:scissors
                            com= 2;
                        }
                        break;
                    case 1: //user:paper, com:scissors
                        if (num==0 || num==1) { //0,1 com:rock
                            com= 0;
                        } if (num==2 || num==3) { //2,3 com:paper
                            com= 1; //com:paper
                        } else{ //4,5,6,7,8,9,10,11 com:scissors
                            com= 2;
                        }
                        break;
                    case 2: //user:scissors, com:rock
                        if (num<=7) { //0,1,2,3,4,5,6,7 com:rock
                            com= 0;
                        } if (num==8 || num==9) { //8,9 com:paper
                            com= 1; //com:paper
                        } else { //10,11 com:scissors
                            com= 2;
                        }
                        break;
                }
                break;
            case 3: //expert lvl
                num=random.nextInt(11);
                switch(user) {
                    case 0: //user:rock, com:paper
                        if (num==0) { //0 com:rock
                            com= 0;
                        } if (num==1) { //11 com:scissors
                            com=2;
                        } else { //1,2,3,4,5,6,7,8,9,10 com:paper
                            com= 1;
                        }
                        break;
                    case 1: //user:paper, com:scissors
                        if (num==0) { //0 com:rock
                            com= 0;
                        } if (num==1) { //1 com:paper
                            com= 1; //com:paper
                        } else { //2,3,4,5,6,7,8,9,10,11 com:scissors
                            com= 2;
                        }
                        break;
                    case 2: //user:scissors, com:rock
                        if (num<=9) { //0,1,2,3,4,5,6,7,8,9 com:rock
                            com= 0;
                        } if (num==10) { //10 com:paper
                            com= 1; //com:paper
                        } else { //11 com:scissors
                            com= 2;
                        }
                        break;
                }
                break;
        }
        return com;
    }
    public static void calculate(int user, int difficulty, Label label, Label rounds, Label win, Label lose, Label tie) {
        String userStr,comStr,status;
        int com = getComChoice(user,difficulty); //(user choice, difficulty) 0easy 1med 2hard 3expert

        if (user==0) {            //user rock
            userStr="rock";
            if (com==0) {         //user rock, com rock
                comStr="rock";
                status="tie";
                printStats(userStr,comStr,status,label,tie);
            } else if (com==1) {  //user rock, com paper
                comStr="paper";
                status="lose";
                printStats(userStr,comStr,status,label,lose);
            } else {              //user rock, com scissors
                comStr="scissors";
                status="win";
                printStats(userStr,comStr,status,label,win);
            }
        } else if (user==1) {    //user paper
            userStr="paper";
            if (com==0) {        //user paper, com rock
                comStr="rock";
                status="win";
                printStats(userStr,comStr,status,label,win);
            } else if (com==1) { //user paper, com paper
                comStr="paper";
                status="tie";
                printStats(userStr,comStr,status,label,tie);
            } else {             //user paper, com scissors
                comStr="scissors";
                status="lose";
                printStats(userStr,comStr,status,label,lose);
            }
        } else {                 //user scissors
            userStr="scissors";
            if(com==0){          //user scissors, com rock
                comStr="rock";
                status="lose";
                printStats(userStr,comStr,status,label,lose);
            } else if (com==1){  //user scissors, com paper
                comStr="paper";
                status="win";
                printStats(userStr,comStr,status,label,win);
            } else {             //user scissors, com scissors
                comStr="scissors";
                status="tie";
                printStats(userStr,comStr,status,label,tie);
            }
        }

        rounds.setText(increment(rounds));

        if (isDone(rounds.getText())) {
            label.setText("Game Over! Click Create Game to start again.");
            gameOver=true;
        }
    }
    public static void printStats(String user, String com, String status, Label label, Label labelToIncrement) {
        label.setText("You chose " + user + ", Computer chose " + com + ", you " + status + "!");
        String str = increment(labelToIncrement);
        labelToIncrement.setText(str);
    }
    public static String increment(Label label) {
        String str = label.getText();
        int num = Integer.parseInt(str);
        num++;
        return Integer.toString(num);
    }
    public static boolean isDone(String str){
        int roundNum = Integer.parseInt(str);

        if(roundNum==gamesNum) {
            return true;
        } else {
            return false;
        }
    }
    public static void reset(ImageView rock, ImageView paper, ImageView scissors, Label screen, Label round, Label wins, Label losses, Label ties) {
        rock.setImage(rock1);
        paper.setImage(paper1);
        scissors.setImage(scissors1);
        screen.setText("Hi, let's play! Select an image to begin.");
        round.setText("0");
        wins.setText("0");
        losses.setText("0");
        ties.setText("0");
    }
}

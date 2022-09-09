import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import svu.csc213.Dialog;

public class Blackjack extends GraphicsProgram {

    // data about the game
    private int wager;
    private int balance = 10000;
    private int bank = 10000;

    // labels to display info to the player
    private GLabel bankLabel;
    private GLabel wagerLabel;
    private GLabel balanceLabel;
    private GLabel blackjack;

    // buttons for controls
    private JButton wagerButton;
    private JButton playButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton quitButton;

    // objects we are playing with
    private Deck deck; // duh
    private GHand player;
    private GHand dealer;

    private int firsthand;
    private int dealercount;
    private int onecard;



    @Override
    public void init() {
        Color background = new Color(90, 100, 200);
        this.setBackground(Color.LIGHT_GRAY);

        deck = new Deck();

        // set up our buttons
        wagerButton = new JButton("Wager");
        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");


        // add buttons to the screen
        add(wagerButton, SOUTH);
        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        // TODO: handle initial button visibility
        wagerButton.setVisible(true);
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        playButton.setVisible(false);


        addActionListeners();

        // TODO: set up your GLabels
        updateLabels();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Wager":
                wager();
                break;

            case "Play":
                play();
                break;

            case "Hit":
                hit();
                break;

            case "Stay":
                stay();
                break;

            case "Quit":
                System.exit(1);
                break;

            default:
                System.out.println("I do not recognize that action command. Check your button text.");
        }
    }


    @Override
    public void run() {



    }



    public void play() {
        player = new GHand(new Hand(deck,false));
        dealer = new GHand(new Hand(deck,true));
        add(dealer,300,100);
        add(player,300,300);
        dealercount = dealer.getTotal();
        firsthand = player.getTotal();
        System.out.println("total of the first hand we get " + firsthand);
        System.out.println("dealers hand is " + dealercount);
        hitButton.setVisible(true);
        stayButton.setVisible(true);
        playButton.setVisible(false);

        if(player.getTotal() == 21) {
            Dialog.showMessage("You win, Your total was equal to 21");
            wager += balance;
            wager += balance;
            bank -= wager;
            updateLabels();

        }




    }
    public void wager() {


        wager = Dialog.getInteger("Enter a wager here: ");
        int check = wager;
        balance -= wager;
        if(check > 0) {
            wagerButton.setVisible(false);
            playButton.setVisible(true);
            balanceLabel.setLabel("Your balance: " + balance);
            wagerLabel.setLabel("Your wager: " + wager);
            bankLabel.setLabel("Banks money: " + bank);
        }
        else{Dialog.showMessage("Enter a valid wager");}
    }

    public void hit() {
        player.hit();
        onecard = player.getTotal();
        System.out.print("This is the total with one card addd " + onecard);
        if(onecard > 21) {
            Dialog.showMessage("Your over 21, you lose");
            bank += wager;
            wager -= wager;
            balanceLabel.setLabel("Your balance: " + balance);
            wagerLabel.setLabel("Your wager: " + wager);
            bankLabel.setLabel("Banks money: " + bank);


        }
        if(onecard == 21) {
            Dialog.showMessage("You got 21 you win");
        }

    }
    public void stay() {
        if(player.getTotal() > dealer.getTotal()) {
            Dialog.showMessage("You win");

            wager+= balance;
            wager += balance;
            bank -= wager;
            balanceLabel.setLabel("Your balance: " + balance);
            wagerLabel.setLabel("Your wager: " + wager);
            bankLabel.setLabel("Banks money: " + bank);


        }
        else if (player.getTotal() < dealer.getTotal()) {
            Dialog.showMessage("You lose sorry");
            bank += wager;
            wager -= wager;
            balanceLabel.setLabel("Your balance: " + balance);
            wagerLabel.setLabel("Your wager: " + wager);
            bankLabel.setLabel("Banks money: " + bank);


        }
        else if(player.getTotal() == dealer.getTotal()) {
            Dialog.showMessage("No one wins its a tie");
            Dialog.showMessage("Your total was equal to: " + player.getTotal());
            Dialog.showMessage("Dealers total was equal to: " + dealer.getTotal());


        }
        // show our dealers card

        dealer.flipCard(0);


    }

    public void updateLabels() {
        wagerLabel = new GLabel("");
        bankLabel = new GLabel("");
        balanceLabel = new GLabel("");
        blackjack = new GLabel("BlackJack");

        ;

        add(wagerLabel,10,10);
        add(bankLabel,10,30);
        add(balanceLabel,10,50);
        add(blackjack,350,20);




    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}
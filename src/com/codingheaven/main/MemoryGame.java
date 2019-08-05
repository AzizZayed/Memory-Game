package com.codingheaven.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class MemoryGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtInput;
	private JLabel lblScore;
	private JLabel lblNum;
	private JLabel lblHighScore;
	private JButton btnStart;
	private JButton btnSubmit;
	private JLabel lblLose;
	private JLabel lblLoseInfo;

	private final int INITIAL_LEVEL = 3;
	private final int INITIAL_SPEED = 5;

	private int level;
	private int index;
	private int speed;
	private int score;
	private int record;
	private String numbers;
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemoryGame frame = new MemoryGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public MemoryGame() {
		setTitle("Memory Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 500, 184);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		txtInput = new JTextField();
		txtInput.setForeground(Color.BLACK);
		txtInput.setBounds(99, 114, 286, 20);
		contentPane.add(txtInput);
		txtInput.setColumns(10);
		txtInput.setEnabled(false);

		lblScore = new JLabel("Score: ");
		lblScore.setBounds(10, 11, 224, 20);
		contentPane.add(lblScore);

		lblHighScore = new JLabel("High score: ");
		lblHighScore.setBounds(244, 11, 230, 20);
		contentPane.add(lblHighScore);

		lblNum = new JLabel("");
		lblNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum.setBounds(-60, 42, 51, 61);
		lblNum.setFont(new Font("Times New Roman", Font.BOLD, 50));
		contentPane.add(lblNum);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		btnStart.setBounds(10, 114, 79, 20);
		contentPane.add(btnStart);

		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSubmit.setEnabled(false);
				txtInput.setEnabled(false);

				if (txtInput.getText().trim().equals(numbers))
					win();
				else
					lose();
			}
		});
		btnSubmit.setBounds(395, 113, 79, 20);
		contentPane.add(btnSubmit);
		btnSubmit.setEnabled(false);

		lblLose = new JLabel("YOU LOST!");
		lblLose.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblLose.setHorizontalAlignment(SwingConstants.CENTER);
		lblLose.setBounds(99, 42, 286, 52);
		lblLose.setVisible(false);
		contentPane.add(lblLose);

		lblLoseInfo = new JLabel("");
		lblLoseInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoseInfo.setBounds(99, 89, 286, 14);
		lblLoseInfo.setVisible(false);
		contentPane.add(lblLoseInfo);

		timer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}

		});	
		
		setupNumbers();
		
	}

	private void resetNumberLabel() {
		lblNum.setText(numbers.substring(index, index + 1)); // first number
		lblNum.setLocation(-lblNum.getWidth(), lblNum.getY());
	}

	private void startGame() {
		level = INITIAL_LEVEL;
		speed = INITIAL_LEVEL * INITIAL_SPEED;
		index = 0;
		score = 0;

		txtInput.setText("");
		lblLose.setVisible(false);
		lblLoseInfo.setVisible(false);
		btnStart.setEnabled(false);
		txtInput.setEnabled(false);
		btnSubmit.setEnabled(false);

		setupNumbers();

		resetNumberLabel();
		lblScore.setText("Score: " + score);
		lblHighScore.setText("High score: " + record);

		timer.start();
	}

	private void win() {
		score += (level * speed);
		level++;
		speed += 5;
		index = 0;

		if (score > record)
			record = score;

		setupNumbers();

		resetNumberLabel();
		lblScore.setText("Score: " + score);
		lblHighScore.setText("High score: " + record);
		txtInput.setText("");

		timer.start();
	}

	private void lose() {
		lblLoseInfo.setText("It was " + numbers);
		lblLose.setVisible(true);
		lblLoseInfo.setVisible(true);

		// restart
		btnStart.setText("Restart");
		btnStart.setEnabled(true);
		txtInput.setEnabled(false);
		btnSubmit.setEnabled(false);
	}

	private void update() {
		lblNum.setLocation(lblNum.getX() + speed, lblNum.getY());

		if (lblNum.getX() >= getWidth()) {
			index++;

			if (index == level) {
				// stop and input
				txtInput.setEnabled(true);
				btnSubmit.setEnabled(true);
				timer.stop();
			} else {
				resetNumberLabel();
			}
		}
	}

	private void setupNumbers() {
		numbers = "";
		final int MAX_NUM = 9;

		// random numbers
		for (int i = 0; i < level; i++)
			numbers += (int) (Math.random() * (MAX_NUM + 1));
	}
}

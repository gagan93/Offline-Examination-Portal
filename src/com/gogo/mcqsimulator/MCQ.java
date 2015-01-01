package com.gogo.mcqsimulator;

import javax.swing.*;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MCQ implements ActionListener
{
	private ButtonGroup bg;
	private JButton start, submit, startSubmit, ruleStart;
	private JFrame frame;
	private JLabel main_bg, name, pass, question, nameStamp, startName,
			startEno, startBranch, startChoice, startBg, ruleHeading, ruleBg,
			timeLabel, noqa, noqc, noql, noqs;
	private JPanel mainPanel;
	private JPasswordField pass_f;
	private JRadioButton a, b, c, d, startC, startCpp, startJava;
	private JTextField name_f, startName_f, startEno_f, startBranch_f,
			questionPatht, answerPatht, scorePatht, resourcePatht, tnqt, nqdt,
			nqrt, changeUserPasst, changeAdminPasst;
	private String user, eno, branch, choice, answer[], ques[][],
			responseToQuestion = "", tempPercentage, pathq, patha, paths,
			userPass, adminPass, imgFolder;
	private Timer timer;
	private int paperChoice = 0, totalMarks, cqno = 1, questionNumber = 0,
			skipQuestion = 0, noOfCorrectQuestions = 0, questionsAttempted = 0,
			tempTime = 0, hours, minutes, seconds, NO_OF_QUESTIONS_TO_DISPLAY,
			NO_OF_QUESTIONS_TO_READ, TIME;;
	private double percentage;
	private long startTime, endTime;
	private boolean questionFlag[], radioButtonFlag = false,
			questionAttempted = false, startTest = false;

	private void setColors(Color c, JComponent... C)
	{
		for (JComponent com : C)
			com.setForeground(c);
	}

	private void setFonts(Font f, JComponent... C)
	{
		for (JComponent com : C)
			com.setFont(f);
	}

	private void addComponentsToPanel(JPanel p, JComponent... C)
	{
		for (JComponent com : C)
			p.add(com);
	}

	private void setBounds(int arr[], JComponent... C)
	{
		int tempI = 0;
		for (JComponent com : C)
			com.setBounds(arr[tempI++], arr[tempI++], arr[tempI++],
					arr[tempI++]);
	}

	private void computeTime()
	{
		int t1, t2;
		t1 = TIME;
		t2 = tempTime;
		hours = (t1 - t2) / 3600;
		if (hours != 0)
			t1 -= (hours * 3600);
		minutes = (t1 - t2) / 60;
		seconds = (TIME - tempTime) % 60;
	}
	MCQ(String arg[])
	{
		NO_OF_QUESTIONS_TO_READ = (new Integer(arg[0]));
		NO_OF_QUESTIONS_TO_DISPLAY = (new Integer(arg[1]));
		if (NO_OF_QUESTIONS_TO_DISPLAY > NO_OF_QUESTIONS_TO_READ)
			NO_OF_QUESTIONS_TO_DISPLAY = NO_OF_QUESTIONS_TO_READ;
		TIME = (new Integer(arg[2]));
		imgFolder = arg[3];
		pathq = arg[4];
		patha = arg[5];
		paths = arg[6];
		userPass = arg[7];
		adminPass = arg[8];
		questionFlag = new boolean[NO_OF_QUESTIONS_TO_READ + 5];
		ques = new String[NO_OF_QUESTIONS_TO_READ + 2][5];
		answer = new String[NO_OF_QUESTIONS_TO_READ];
		frame = new JFrame("MCQ SIMULATOR");
		mainPanel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(950, 650);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		mainPanel.setSize(979, 649);
		mainPanel.setVisible(true);
		mainPanel.setLayout(null);
		init();
		mainPage();
	}

	private void init()
	{
		totalMarks = questionNumber = questionsAttempted = noOfCorrectQuestions = skipQuestion = tempTime = 0;
		cqno = 1;
		radioButtonFlag = questionAttempted = startTest = false;
		mainPanel.removeAll();
		for (int i = 0; i < questionFlag.length; i++)
			questionFlag[i] = false;
		questionFlag[NO_OF_QUESTIONS_TO_READ + 1] = true; // just for random no.
															// while loop
		timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				endTime = System.currentTimeMillis();
				tempTime = (int) ((endTime - startTime) / 1000);
				if (tempTime > TIME - 1)
				{
					timer.stop();
					displayResult();
				}
				computeTime();
				timeLabel.setText("Time Left : " + hours + " : " + minutes
						+ " : " + seconds);
				noqa.setText("Questions Attempted : " + questionsAttempted);
				noqc.setText("Questions Correct   : " + noOfCorrectQuestions);
				noqs.setText("Questions Skipped   : " + (skipQuestion));
				noql.setText("Questions Left      : "
						+ (NO_OF_QUESTIONS_TO_DISPLAY - questionsAttempted - skipQuestion));
			}
		});
		frame.setContentPane(mainPanel);
		startTime = System.currentTimeMillis();
	}

	private void mainPage()
	{
		int mainArr[] = { 125, 300, 250, 50, 125, 375, 250, 50, 375, 300, 250,
				45, 375, 375, 250, 45, 0, 0, 950, 650, 330, 475, 125, 40, 170,
				535, 525, 50 };
		main_bg = new JLabel(new ImageIcon(imgFolder + "frontpage.jpg"));
		nameStamp = new JLabel("Credits : Gagandeep Singh");
		name = new JLabel("Username : ");
		pass = new JLabel("Password : ");
		name_f = new JTextField(user);
		pass_f = new JPasswordField(30);
		start = new JButton("-LOGIN-");
		setBounds(mainArr, name, pass, name_f, pass_f, main_bg, start,
				nameStamp);
		pass_f.setEchoChar('*');
		name.setFont(new Font("GreyscaleBasic", Font.PLAIN, 42));
		nameStamp.setFont(new Font("Garamond", Font.PLAIN, 26));
		pass.setFont(new Font("GreyscaleBasic", Font.PLAIN, 42));
		name_f.setFont(new Font("Quicksand", Font.PLAIN, 26));
		pass_f.setFont(new Font("Existence Light", Font.PLAIN, 30));
		start.setFont(new Font("Nasalization Rg", Font.PLAIN, 20));
		setColors(Color.white, name, pass, nameStamp);
		addComponentsToPanel(mainPanel, name, nameStamp, pass, pass_f, name_f,
				start, main_bg);
		start.addActionListener(this);
	}

	private void adminPanel()
	{
		mainPanel.removeAll();
		int adminArr[] = { 50, 100, 300, 30, 50, 150, 300, 30, 50, 200, 300,
				30, 50, 250, 350, 30, 50, 300, 350, 30, 50, 350, 300, 30, 50,
				400, 300, 30, 50, 450, 300, 30, 50, 500, 300, 30, 375, 100, 80,
				30, 375, 150, 80, 30, 375, 200, 80, 30, 375, 250, 250, 30, 375,
				300, 250, 30, 375, 350, 250, 30, 375, 400, 250, 30, 375, 450,
				100, 30, 375, 500, 100, 30, 375, 550, 200, 30, 0, 0, 950, 650 };
		JLabel nqr = new JLabel("No. of questions to read");
		JLabel nqd = new JLabel("No. of questions to display");
		JLabel tnq = new JLabel("Time to be given (in sec)");
		JLabel resourcePath = new JLabel("Path for resources");
		JLabel questionPath = new JLabel("Path of Question files(C/C++/Java)");
		JLabel answerPath = new JLabel("Path of Answer files(C/C++/Java)");
		JLabel scorePath = new JLabel("Path of file to store score of student");
		JLabel changeUserPass = new JLabel("Change User  Password");
		JLabel changeAdminPass = new JLabel("Change Admin Password");
		JLabel adminBg = new JLabel(new ImageIcon(imgFolder + "adminPanel.jpg"));

		nqrt = new JTextField((new Integer(NO_OF_QUESTIONS_TO_READ)).toString());
		nqdt = new JTextField(
				(new Integer(NO_OF_QUESTIONS_TO_DISPLAY)).toString());
		tnqt = new JTextField((new Integer(TIME)).toString());
		resourcePatht = new JTextField(imgFolder);
		questionPatht = new JTextField(pathq);
		answerPatht = new JTextField(patha);
		scorePatht = new JTextField(paths);
		changeUserPasst = new JTextField(userPass);
		changeAdminPasst = new JTextField(adminPass);

		JButton submitButton = new JButton("Save and Continue");

		setFonts(new Font("times new roman", Font.PLAIN, 18), nqr, nqd, tnq,
				resourcePath, resourcePatht, changeAdminPasst, questionPath,
				answerPath, scorePath, changeUserPass, changeAdminPass, nqrt,
				nqdt, tnqt, questionPatht, answerPatht, scorePatht,
				changeUserPasst, submitButton);
		setBounds(adminArr, nqr, nqd, tnq, resourcePath, questionPath,
				answerPath, scorePath, changeUserPass, changeAdminPass, nqrt,
				nqdt, tnqt, resourcePatht, questionPatht, answerPatht,
				scorePatht, changeUserPasst, changeAdminPasst, submitButton,
				adminBg);
		setColors(Color.WHITE, nqr, nqd, tnq, resourcePath, questionPath,
				answerPath, scorePath, changeUserPass, changeAdminPass);
		addComponentsToPanel(mainPanel, nqr, nqd, tnq, questionPath,
				answerPath, scorePath, changeUserPass, changeAdminPass, nqrt,
				nqdt, tnqt, questionPatht, answerPatht, scorePatht,
				changeUserPasst, changeAdminPasst, submitButton, resourcePath,
				resourcePatht, adminBg);
		submitButton.addActionListener(this);
		frame.setContentPane(mainPanel);
	}

	private void startPage()
	{
		mainPanel.removeAll();
		int startArr[] = { 160, 40, 450, 30, 160, 90, 250, 50, 160, 190, 250,
				50, 160, 290, 250, 50, 160, 390, 250, 50, 400, 90, 300, 40,
				400, 190, 250, 40, 400, 290, 150, 40, 400, 390, 100, 40, 500,
				390, 100, 50, 600, 390, 100, 50, 0, 0, 950, 650, 380, 475, 140,
				35 };
		ButtonGroup bg = new ButtonGroup();
		startName = new JLabel("Name");
		startEno = new JLabel("Enr. No.");
		startBranch = new JLabel("Branch");
		startChoice = new JLabel("Choose from :");
		startBg = new JLabel(new ImageIcon(imgFolder + "choice.jpg"));
		JLabel allFieldsAreCompulsory = new JLabel("All fields are compulsory");
		startSubmit = new JButton("PROCEED ->");
		startName_f = new JTextField(user);
		startEno_f = new JTextField(30);
		startBranch_f = new JTextField(30);
		startEno_f.setText("");
		startBranch_f.setText("");
		startC = new JRadioButton("C");
		startCpp = new JRadioButton("C++");
		startJava = new JRadioButton("JAVA");
		bg.add(startC);
		bg.add(startCpp);
		bg.add(startJava);
		allFieldsAreCompulsory.setFont(new Font("GreyscaleBasic", Font.PLAIN,
				20));
		setFonts(new Font("GreyscaleBasic", Font.PLAIN, 35), startName,
				startEno, startBranch, startChoice);
		setFonts(new Font("Quicksand", Font.PLAIN, 26), startName_f,
				startEno_f, startBranch_f);
		setFonts(new Font("Garamond", Font.PLAIN, 30), startC, startCpp,
				startJava);
		startSubmit.setFont(new Font("Nasalization Rg", Font.PLAIN, 16));
		setColors(Color.black, startName, startEno, startBranch, startChoice,
				startC, startCpp, startJava);
		setBounds(startArr, allFieldsAreCompulsory, startName, startEno,
				startBranch, startChoice, startName_f, startEno_f,
				startBranch_f, startC, startCpp, startJava, startBg,
				startSubmit);
		startC.setOpaque(false);
		startCpp.setOpaque(false);
		startJava.setOpaque(false);
		addComponentsToPanel(mainPanel, startC, startCpp, startJava,
				startSubmit, allFieldsAreCompulsory, startName, startName_f,
				startEno, startEno_f, startBranch, startBranch_f, startChoice,
				startBg);
		startSubmit.addActionListener(this);
		startC.addActionListener(this);
		startCpp.addActionListener(this);
		startJava.addActionListener(this);
		frame.setContentPane(mainPanel);
	}

	private void rules()
	{
		mainPanel.removeAll();
		int ruleArr[] = { 120, 95, 750, 100, 100, 10, 750, 600, 0, 0, 950, 650,
				370, 500, 125, 35 };
		ruleHeading = new JLabel("Please Read the Rules Carefully");
		JLabel ruleBody = new JLabel(
				"<html><br>1. There are 20 Questions, with four options for each question.<br><br>2. Only one option is MOST APPRORIATE for each question.<br><br>3. Marking  : +2 for each correct answer and -1 for each incorrect answer.<br><br>4. Marks are not deducted if you skip a question.<br><br>5. Answer is not submitted unless \"Submit\" button is pressed.</html>");
		ruleBg = new JLabel(new ImageIcon(imgFolder + "rules_bg.jpg"));
		ruleStart = new JButton("- BEGIN -");
		setBounds(ruleArr, ruleHeading, ruleBody, ruleBg, ruleStart);
		ruleHeading.setFont(new Font("Renfrew", Font.PLAIN, 35));
		ruleBody.setFont(new Font("times new roman", Font.PLAIN, 25));
		ruleStart.setFont(new Font("Georgia", Font.PLAIN, 20));
		addComponentsToPanel(mainPanel, ruleHeading, ruleBody, ruleStart,
				ruleBg);// ,ruleL1,ruleL2,ruleL3,ruleL4,ruleStart,ruleBg);
		ruleStart.addActionListener(this);
		frame.setContentPane(mainPanel);
	}

	private void displayResult()
	{
		mainPanel.removeAll();
		timer.stop();
		percentage = (((double) noOfCorrectQuestions / NO_OF_QUESTIONS_TO_DISPLAY) * 100);
		if (skipQuestion <= NO_OF_QUESTIONS_TO_DISPLAY - 1)
		{
			if (percentage >= 50)
			{
				certify();
				storeScore();
				return;
			}
			else
				JOptionPane.showMessageDialog(frame, user
						+ ", You have not cleared this exam ",
						"Percentage less than 50 %", 1);
		}
		else if (skipQuestion > NO_OF_QUESTIONS_TO_DISPLAY - 1)
		{
			JOptionPane
					.showMessageDialog(
							frame,
							"You have not attempted even a single question\n\nPress any key to exit ",
							"All questions were skipped", 1);
		}
		System.exit(0);
	}

	private void generateQuestion()
	{
		Random rand = new Random();
		int qno = NO_OF_QUESTIONS_TO_READ + 1;
		while (questionFlag[qno])
		{
			qno = 1 + (rand.nextInt(NO_OF_QUESTIONS_TO_READ));
			if (cqno > NO_OF_QUESTIONS_TO_DISPLAY)
			{
				displayResult();
				return;
			}
		}
		questionFlag[qno] = true;
		questions(ques[qno]);
		questionNumber = qno;
	}

	private void questions(String... print)
	{
		mainPanel.removeAll();
		int questionArr[] = { 730, 285, 200, 20, 730, 315, 200, 20, 730, 345,
				200, 20, 730, 375, 200, 20, 730, 405, 200, 20, 10, 110, 900,
				40, 50, 260, 600, 50, 50, 300, 600, 50, 50, 340, 600, 50, 50,
				380, 600, 50, 250, 500, 120, 40, 500, 500, 80, 40, 0, 0, 950,
				650 };
		String line1 = "", line2 = "", line3 = "", temp = "";
		int prevCount = 0, count = 0;
		boolean line2Flag = false, line3Flag = false;
		line1 = print[0];
		if (line1.length() > 50)
		{
			line2Flag = true;
			StringTokenizer stLine1 = new StringTokenizer(line1, " ", true);
			while (stLine1.hasMoreTokens())
			{
				count = temp.length();
				if (count < 55)
				{
					temp += stLine1.nextToken();
					prevCount = count;
				}
				else
					break;
			}
			line2 = line1.substring(prevCount);
			line1 = line1.substring(0, prevCount);
		}
		temp = "";
		if (line2.length() > 50)
		{
			line3Flag = true;
			StringTokenizer stLine2 = new StringTokenizer(line2, " ", true);
			while (stLine2.hasMoreTokens())
			{
				count = temp.length();
				if (count < 50)
				{
					temp += stLine2.nextToken();
					prevCount = count;
				}
				else
					break;
			}
			line3 = line2.substring(prevCount);
			line2 = line2.substring(0, prevCount);
		}
		noqa = new JLabel(" ");
		noqc = new JLabel(" ");
		noqs = new JLabel(" ");
		noql = new JLabel(" ");
		timeLabel = new JLabel(" ");
		JLabel l1 = new JLabel(new ImageIcon(imgFolder + "paper_bg.jpg"));
		question = new JLabel("Q" + cqno + ") " + line1);
		a = new JRadioButton(print[1]);
		b = new JRadioButton(print[2]);
		c = new JRadioButton(print[3]);
		d = new JRadioButton(print[4]);
		submit = new JButton("Submit");
		JButton skip = new JButton("Skip");
		bg = new ButtonGroup();
		question.setFont(new Font("Garamond", Font.PLAIN, 35));
		setFonts(new Font("Garamond", Font.PLAIN, 24), a, b, c, d, submit, skip);
		setFonts(new Font("Palatino Linotype", Font.BOLD, 16), timeLabel, noqa,
				noqc, noqs, noql);
		setColors(Color.WHITE, timeLabel, noqa, noqc, noqs, noql);
		setBounds(questionArr, timeLabel, noqa, noqc, noqs, noql, question, a,
				b, c, d, submit, skip, l1);
		a.setOpaque(false);
		b.setOpaque(false);
		c.setOpaque(false);
		d.setOpaque(false);
		a.setActionCommand("A");
		b.setActionCommand("B");
		c.setActionCommand("C");
		d.setActionCommand("D");
		bg.add(a);
		bg.add(b);
		bg.add(c);
		bg.add(d);
		if (line2Flag)
		{
			JLabel question2 = new JLabel(line2);
			question2.setFont(new Font("Garamond", Font.PLAIN, 35));
			question2.setBounds(85, 150, 900, 40);
			mainPanel.add(question2);
		}
		if (line3Flag)
		{
			JLabel question3 = new JLabel(line3);
			question3.setFont(new Font("Garamond", Font.PLAIN, 35));
			question3.setBounds(85, 190, 900, 40);
			mainPanel.add(question3);
		}
		cqno++;
		addComponentsToPanel(mainPanel, timeLabel, noqa, noqc, noqs, noql,
				question, a, b, c, d, submit, skip, l1);
		submit.addActionListener(this);
		a.addActionListener(this);
		b.addActionListener(this);
		c.addActionListener(this);
		d.addActionListener(this);
		skip.addActionListener(this);
		submit.setActionCommand("next");
		skip.setActionCommand("skip");
		if (!startTest)
		{
			startTest = true;
			computeTime();
		}
		timeLabel.setText("Time Left : " + hours + " : " + minutes + " : "
				+ seconds);
		noqa.setText("Questions Attempted : " + questionsAttempted);
		noqc.setText("Questions Correct   : " + noOfCorrectQuestions);
		noqs.setText("Questions Skipped   : " + skipQuestion);
		noql.setText("Questions Left      : "
				+ (NO_OF_QUESTIONS_TO_DISPLAY - questionsAttempted - skipQuestion));
		frame.setContentPane(mainPanel);
	}

	private String adjustString(String s, int len)
	{
		int ll = s.length();
		for (int i = 0; i < len - ll; i++)
			s += " ";
		return s;
	}

	private void certify()
	{
		int certifyArr[] = { 80, 100, 400, 100, 80, 170, 400, 100, 80, 240,
				400, 100, 80, 310, 500, 100, 80, 380, 500, 100, 80, 450, 400,
				100, 500, 100, 420, 100, 500, 170, 420, 100, 500, 240, 420,
				100, 500, 310, 420, 100, 500, 380, 420, 100, 500, 450, 420,
				100, 0, 0, 950, 650, 250, 570, 100, 35, 405, 570, 110, 35, 565,
				570, 160, 35 };
		JLabel l, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
		JButton exit = new JButton("Exit");
		JButton restart = new JButton("Start Again");
		JButton newuser = new JButton("Login as new user");
		user = (user.length() > 30) ? user.substring(0, 29) : user;
		eno = (eno.length() > 20) ? eno.substring(0, 19) : eno;
		tempPercentage = new Double(percentage).toString();
		user = adjustString(user, 30);
		eno = adjustString(eno, 20);
		tempPercentage = (tempPercentage.length()) > 5 ? tempPercentage
				.substring(0, 5) : tempPercentage;
		l1 = new JLabel("Name of student");
		l2 = new JLabel("Enrollment Number");
		l3 = new JLabel("Total No. of questions ");
		l4 = new JLabel("No. of questions attempted");
		l5 = new JLabel("No. of correct questions ");
		l6 = new JLabel("Percentage / Score");
		l7 = new JLabel(": " + user);
		l8 = new JLabel(": " + eno);
		l9 = new JLabel(": " + NO_OF_QUESTIONS_TO_DISPLAY);
		l10 = new JLabel(": " + (new Integer(questionsAttempted).toString()));
		l11 = new JLabel(": " + new Integer(noOfCorrectQuestions).toString());
		l12 = new JLabel(": " + tempPercentage);
		l = new JLabel(new ImageIcon(imgFolder + "result_bg.jpg"));
		setFonts(new Font("GreyscaleBasic", Font.PLAIN, 34), l1, l2, l3, l4,
				l5, l6, l7, l8, l9, l10, l11, l12);
		setBounds(certifyArr, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11,
				l12, l, exit, restart, newuser);
		setColors(Color.white, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11,
				l12);
		addComponentsToPanel(mainPanel, l1, l2, l3, l4, l5, l6, l7, l8, l9,
				l10, l11, l12, restart, newuser, exit, l);
		exit.addActionListener(this);
		restart.addActionListener(this);
		newuser.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				frame.dispose();
				UseMCQ.main("a");
			}
		});
		frame.setContentPane(mainPanel);
	}

	private void storeScore()
	{
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter(paths + ".txt", true));
		}
		catch (Exception e)
		{
		}
		try
		{
			bw.write("Name : " + user + "\tEnr no. : " + eno
					+ "\tPercentage : " + tempPercentage + "\n\n");
		}
		catch (Exception e)
		{
		}
		try
		{
			bw.close();
		}
		catch (Exception e)
		{
		}
	}

	private void readFile()
	{
		BufferedReader br = null, br2 = null;
		try
		{
			br = new BufferedReader(
					new FileReader(pathq + paperChoice + ".txt"));
		}
		catch (FileNotFoundException fnfe)
		{
		}
		String temp = "";
		int i = 1, j = 0;
		while (i < NO_OF_QUESTIONS_TO_READ + 1)
		{
			if (j == 5)
			{
				j = 0;
				i++;
			}
			try
			{
				temp = br.readLine();
			}
			catch (IOException ioe)
			{
			}
			ques[i][j++] = temp;
		}
		i = 0;
		try
		{
			br2 = new BufferedReader(new FileReader(patha + paperChoice
					+ ".txt"));
		}
		catch (FileNotFoundException fnfe)
		{
		}
		while (i < NO_OF_QUESTIONS_TO_READ)
		{
			try
			{
				temp = br2.readLine();
			}
			catch (IOException ioe)
			{
			}
			answer[i++] = temp;
		}
		timer.start();
		startTime = System.currentTimeMillis();
	}

	public void actionPerformed(ActionEvent ae)
	{
		String s, pass;
		s = ae.getActionCommand();
		boolean nfeFlag = false;
		if (s.equals("-LOGIN-"))
		{
			user = name_f.getText();
			pass = new String(pass_f.getPassword());
			if (user.equals("ADMIN") && pass.equals(adminPass))
			{
				JOptionPane.showMessageDialog(frame, "Opening Admin Panel",
						"Welcome Admin", 1);
				adminPanel();
			}
			else if (user.equals(""))
				if (pass.equals(""))
					JOptionPane.showMessageDialog(frame,
							"Please Enter username and password",
							"Login Error", 2);
				else
					JOptionPane.showMessageDialog(frame,
							"Please Enter a username", "Login Error", 1);
			else if (pass.equals(userPass))
			{
				JOptionPane.showMessageDialog(frame, name_f.getText()
						+ ", You have Successfully logged in",
						"Login Successful", 1);
				startPage();
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Invalid Password ",
						"Login Faliure", 0);
				pass_f.setText("");
			}
		}
		else if (s.equals("Save and Continue"))
		{
			String t1, t2, t3;
			t1 = nqrt.getText();
			t2 = nqdt.getText();
			t3 = tnqt.getText();
			imgFolder = resourcePatht.getText();
			pathq = questionPatht.getText();
			patha = answerPatht.getText();
			paths = scorePatht.getText();
			userPass = changeUserPasst.getText();
			adminPass = changeAdminPasst.getText();
			if (t1.equals("") || t2.equals("") || t3.equals("")
					|| imgFolder.equals("") || pathq.equals("")
					|| patha.equals("") || paths.equals("")
					|| userPass.equals("") || adminPass.equals(""))
			{
				JOptionPane
						.showMessageDialog(frame, "All fields are compulsory",
								"Required Data Missing", 0);
				return;
			}
			try
			{
				NO_OF_QUESTIONS_TO_READ = new Integer(t1);
				NO_OF_QUESTIONS_TO_DISPLAY = new Integer(t2);
				TIME = new Integer(t3);
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(frame, "Integer Data Expected",
						"Invalid Data input", 0);
				return;
			}
			BufferedWriter bww = null;
			try
			{
				bww = new BufferedWriter(new FileWriter("config.txt"));
			}
			catch (IOException e)
			{
				System.out.println("Exception while creating the file");
			}
			try
			{
				bww.write(t1 + "\n" + t2 + "\n" + t3 + "\n" + imgFolder + "\n"
						+ pathq + "\n" + patha + "\n" + paths + "\n" + userPass
						+ "\n" + adminPass);
			}
			catch (IOException e)
			{
				System.out.println("Exception while reading the file");
			}
			try
			{
				bww.close();
				JOptionPane.showMessageDialog(frame, "Changes Saved", "Done !",
						1);
				frame.dispose();
				UseMCQ.main("a");
			}
			catch (IOException e)
			{

				System.out.println("Exception while reading the file");
			}
		}
		else if (s.equals("C") || s.equals("C++") || s.equals("JAVA"))
		{
			choice = s;
			radioButtonFlag = true;
		}
		else if (s.equals("PROCEED ->"))
		{
			user = startName_f.getText();
			eno = startEno_f.getText();
			branch = startBranch_f.getText();
			try
			{
				long x = Long.parseLong(eno);
			}
			catch (NumberFormatException e)
			{
				nfeFlag = true;
			}
			if (user.equals("") || eno.equals("") || branch.equals(""))
			{
				JOptionPane.showMessageDialog(frame,
						"All fields are compulsory",
						"Fill in complete information", 0);
				return;
			}
			if (nfeFlag && !eno.equals(""))
			{
				JOptionPane.showMessageDialog(frame,
						"Enrollment No. must be \"numeric\"",
						"Invalid Enr. no.", 0);
				return;
			}
			if (radioButtonFlag)
				rules();
			else
				JOptionPane.showMessageDialog(frame,
						"Must choose one of the three subjects(C,C++,JAVA)",
						"Choose Exam", 0);
		}
		else if (s.equals("- BEGIN -"))
		{
			JOptionPane.showMessageDialog(frame, "ALL THE BEST FOR YOUR EXAM",
					"STARTING EXAM", 1);
			if (choice.equals("C"))
				paperChoice = 1;
			else if (choice.equals("C++"))
				paperChoice = 2;
			else if (choice.equals("JAVA"))
				paperChoice = 3;
			readFile();
			generateQuestion();
		}
		if (s.equals("next") && questionAttempted)
		{
			questionsAttempted++;
			if (responseToQuestion.equals(answer[questionNumber - 1]))
			{
				totalMarks += 2;
				noOfCorrectQuestions++;
			}
			else
				totalMarks -= 2;
			questionAttempted = false;
			generateQuestion();
		}
		else if (s.equals("skip"))
		{
			skipQuestion++;
			generateQuestion();
		}
		else if (s.equals("A") || s.equals("B") || s.equals("C")
				|| s.equals("D"))
		{
			responseToQuestion = s;
			questionAttempted = true;
		}
		else if (s.equals("Exit"))
			System.exit(0);
		else if (s.equals("Start Again"))
		{
			timer.start();
			computeTime();
			init();
			generateQuestion();
		}
	}
}
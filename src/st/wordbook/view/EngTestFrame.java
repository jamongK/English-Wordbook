package st.wordbook.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import st.wordbook.controller.WordbookController;
import st.wordbook.vo.WordsVO;

/**
 * 영한테스트 창
 */
public class EngTestFrame extends JFrame {
	private WordbookController co = new WordbookController();
	private List<WordsVO> words = WordbookController.list;
	private JTextField qstField;
	private JTextField ansField;
	private JLabel correctNumLbl;
	private JLabel wrongNumLbl;
	private JLabel solveNumLbl;
	/*
	 * Line 41~43을 ActionListener의 actionPerformed메소드 안에서 초기화,
	 * 또는, 값으로 0을 지정(저장)할 경우,
	 * action이 발생할때마다 correctNum등이 카운트 되지 않는다.
	 */
	private int correctNum = 0;
	private int wrongNum = 0;
	private int solveNum = 0;

	public EngTestFrame() {
		super("영어 테스트");
		setSize(450, 550);
		setLocationRelativeTo(null);
		setLayout(null);

		JPanel pnTop = new JPanel();
		pnTop.setBounds(31, 35, 365, 245);
		pnTop.setBorder(new LineBorder(Color.BLACK));
		pnTop.setLayout(null);
		

		JLabel testLbl1 = new JLabel("문제 : ");
		testLbl1.setBounds(49, 60, 54, 22);
		testLbl1.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnTop.add(testLbl1);

		qstField = new JTextField();
		qstField.setBounds(120, 60, 193, 28);
		qstField.setFont(new Font("Gulim", Font.PLAIN, 18));
		qstField.setEditable(false);
		qstField.setBackground(Color.WHITE);
		pnTop.add(qstField);

		JLabel testLbl2 = new JLabel("답안 : ");
		testLbl2.setBounds(49, 120, 54, 22);
		testLbl2.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnTop.add(testLbl2);

		ansField = new JTextField();
		ansField.setBounds(120, 117, 193, 28);
		ansField.setFont(new Font("Gulim", Font.PLAIN, 18));
		ansField.setColumns(20);
		pnTop.add(ansField);
		
		
		// 맞은수~틀린수 출력 panel
		JPanel pnBottom = new JPanel();
		pnBottom.setBounds(31, 299, 365, 158);
		pnBottom.setLayout(null);
		pnBottom.setBorder(new LineBorder(Color.BLACK));
		
		
		JLabel correctLbl = new JLabel("맞은 수 : ");
		correctLbl.setBounds(123, 31, 78, 22);
		correctLbl.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnBottom.add(correctLbl);

		JLabel wrongLbl = new JLabel("틀린 수 : ");
		wrongLbl.setBounds(123, 68, 78, 22);
		wrongLbl.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnBottom.add(wrongLbl);

		JLabel solveLbl = new JLabel("푼 문제 수 : ");
		solveLbl.setBounds(99, 105, 102, 22);
		solveLbl.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnBottom.add(solveLbl);

		correctNumLbl = new JLabel("0");
		correctNumLbl.setBounds(212, 31, 94, 22);
		correctNumLbl.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnBottom.add(correctNumLbl);

		wrongNumLbl = new JLabel("0");
		wrongNumLbl.setBounds(212, 68, 94, 22);
		wrongNumLbl.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnBottom.add(wrongNumLbl);

		solveNumLbl = new JLabel("0");
		solveNumLbl.setBounds(212, 105, 94, 22);
		solveNumLbl.setFont(new Font("Gulim", Font.PLAIN, 18));
		pnBottom.add(solveNumLbl);

		
		// 테스트 시작, 정답 제출 버튼
		JButton startBtn = new JButton("다음 문제");
		startBtn.setBounds(32, 197, 131, 31);
		startBtn.setFont(new Font("Gulim", Font.PLAIN, 18));
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = (int)(Math.random() * words.size());
				qstField.setText(words.get(i).getWord());
			}
		});
		pnTop.add(startBtn);

		JButton submitBtn = new JButton("정답 제출");
		submitBtn.setBounds(200, 197, 131, 31);
		submitBtn.setFont(new Font("Gulim", Font.PLAIN, 18));
		submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// trim() : 문자열 맨 왼쪽 끝, 맨 오른쪽 끝 공백제거
				if (ansField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "정답을 입력하지 않으셨습니다.", "정답 미입력",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (co.isCorrect(qstField.getText(), ansField.getText(), words)) {
					// 맞았을때
					solveNum++;
					correctNum++;
				} else if (!co.isCorrect(qstField.getText(), ansField.getText(), words)) {
					// 틀렸을때
					solveNum++;
					wrongNum++;
				}
				
				solveNumLbl.setText(String.valueOf(solveNum));
				correctNumLbl.setText(String.valueOf(correctNum));
				wrongNumLbl.setText(String.valueOf(wrongNum));
				ansField.setText(null);
			}
		});
		pnTop.add(submitBtn);
		
		
		add(pnTop);
		add(pnBottom);
	}
}

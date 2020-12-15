package st.wordbook.view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import st.wordbook.controller.WordbookController;
import st.wordbook.vo.WordsVO;

/**
 * 메인 프로그램 창
 */
public class WordbookFrame extends JFrame {
	private WordbookController co = new WordbookController();
	private List<WordsVO> words = WordbookController.list;
	private JTextField searchField;
	private JButton search;
	private JButton btnAdd;
	private JButton btnDelete;
	private JTable wordTable;
	private String columns[] = { "영어단어", "뜻" };
	private String data[][];

	public WordbookFrame() {
		// 프레임 기본 설정
		super("영어 단어장");
		setSize(850, 583);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 메뉴바
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu optMenu = new JMenu("옵션");
		optMenu.setFont(new Font("Gulim", Font.PLAIN, 18));
		menuBar.add(optMenu);

		JMenuItem txtSaveMtm = new JMenuItem("txt 파일 추출");
		txtSaveMtm.setFont(new Font("Gulim", Font.PLAIN, 18));
		txtSaveMtm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog("저장할 파일명을 입력해주세요.") + ".txt";
				String fileURL = "SavedTXTFiles/" + fileName;
				
				co.txtSave(fileURL, words);
			}
		});
		optMenu.add(txtSaveMtm);

		JMenu testMenu = new JMenu("테스트");
		testMenu.setFont(new Font("Gulim", Font.PLAIN, 18));
		menuBar.add(testMenu);

		JMenuItem enKoMtm = new JMenuItem("영한 테스트");
		enKoMtm.setFont(new Font("Gulim", Font.PLAIN, 18));
		enKoMtm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new EngTestFrame().setVisible(true);
			}

		});
		testMenu.add(enKoMtm);

		
		// Panels
		JPanel jpanel1 = new JPanel();
		jpanel1.setBounds(90, 37, 703, 42);
		jpanel1.setLayout(null);

		JPanel jpanel2 = new JPanel();
		jpanel2.setBounds(47, 398, 735, 66);
		jpanel2.setLayout(null);

		
		// jpanel1 Components
		// 네이버 사전 버튼
		JButton btnNaver = new JButton("네이버 사전");
		btnNaver.setFont(new Font("Gulim", NORMAL, 13));
		btnNaver.setBounds(400, 5, 105, 36);
		btnNaver.setForeground(Color.black);
		jpanel1.add(btnNaver);

		btnNaver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					Desktop.getDesktop().browse(new URI("https://en.dict.naver.com/#/main"));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}

		});

		// 구글 번역기 버튼
		JButton btnGoogle = new JButton("구글 번역기");
		btnGoogle.setBounds(520, 5, 105, 36);
		btnGoogle.setFont(new Font("Gulim", NORMAL, 13));
		jpanel1.add(btnGoogle);

		btnGoogle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					Desktop.getDesktop().browse(new URI("https://translate.google.co.kr/?hl=ko"));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}

		});

		
		reSettingWord();

		// 테이블 생성
		wordTable = new JTable(data, columns);

		JTableHeader header = wordTable.getTableHeader();
		header.setBackground(Color.white);
		header.setForeground(Color.black);

		JScrollPane scrollPane = new JScrollPane(wordTable);
		scrollPane.setBounds(130, 94, 567, 280);
		wordTable.setFillsViewportHeight(true);
		
		
		// 추가할 단어 입력할 JTextFields
		JLabel addWordLbl = new JLabel("영어단어:");
		addWordLbl.setBounds(33, 25, 80, 22);
		addWordLbl.setFont(new Font("Gulim", NORMAL, 18));
		jpanel2.add(addWordLbl);

		JTextField wordTxtField = new JTextField();
		wordTxtField.setBounds(115, 21, 145, 28);
		jpanel2.add(wordTxtField);

		JLabel addMeanLbl = new JLabel("뜻:");
		addMeanLbl.setBounds(271, 25, 27, 22);
		addMeanLbl.setFont(new Font("Gulim", NORMAL, 18));
		jpanel2.add(addMeanLbl);

		JTextField meanTxtField = new JTextField();
		meanTxtField.setColumns(10);
		meanTxtField.setBounds(300, 21, 145, 28);
		jpanel2.add(meanTxtField);

		
		// 추가버튼
		btnAdd = new JButton("입력추가");
		btnAdd.setFont(new Font("Gulim", NORMAL, 12));
		btnAdd.setBounds(485, 17, 100, 35);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = new DefaultTableModel(data, columns);
				
				// 빈칸(공백) 여부 판별 --> 빈칸일 경우 미입력 WARNING_MESSAGE
				if (wordTxtField.getText().trim().equals("") || meanTxtField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "단어 또는 뜻 입력이 되지 않았습니다.", "추가 내용 미입력",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				model.addRow(new Object[] { wordTxtField.getText(), meanTxtField.getText() });
				words.add(new WordsVO(wordTxtField.getText(), meanTxtField.getText()));
				wordTable.setModel(model);
				reSettingWord();
				
				// 추가버튼 클릭 --> 테이블 및 words(ArrayList)에 추가 --> 입력 필드 null로 설정해 빈칸으로 만들기
				wordTxtField.setText(null);
				meanTxtField.setText(null);
			}
		});
		jpanel2.add(btnAdd);

		
		// 선택한 단어 삭제 버튼
		btnDelete = new JButton("선택삭제");
		btnDelete.setFont(new Font("Gulim", NORMAL, 12));
		btnDelete.setBounds(595, 17, 100, 35);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("삭제 버튼 클릭");
				// 테이블에서 선택(클릭)한 줄의 위치(번지 수, 인덱스) --> 삭제하고자하는 위치 번지 수로 지정(저장)
				int deleteIndex = wordTable.getSelectedRow();

				if (deleteIndex < 0) {
					System.out.println("선택되지 않음");
					return;
				}

				DefaultTableModel model = new DefaultTableModel(data, columns);

				model.removeRow(deleteIndex);
				wordTable.setModel(model);
				words.remove(deleteIndex);

				// data배열이랑 column배열 다시 크기 지정
				reSettingWord();

			}
		});
		jpanel2.add(btnDelete);

		
		// 검색 창 및 버튼
		searchField = new JTextField();
		searchField.setBounds(106, 4, 255, 37);
		jpanel1.add(searchField);

		search = new JButton("검색");
		search.setFont(new Font("Gulim", Font.BOLD, 18));
		search.setBounds(20, 4, 85, 36);
		jpanel1.add(search);

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 김자경 - WordSearchFrame클래스 --> 파라미터생성자 이용해 검색결과 창 띄우기
				new WordSearchFrame(searchField.getText()).setVisible(true);
			}

		});

		
		add(jpanel1);
		add(jpanel2);
		add(scrollPane);

	}

	public void reSettingWord() {
		data = new String[words.size()][2]; // 가변배열X --> 단어, 뜻 중 하나라도 null로 이뤄지지 않도록
		for (int i = 0; i < words.size(); i++) {
			WordsVO w = words.get(i);
			String[] s = { w.getWord(), w.getMeaning() };
			data[i] = s;
		}
	}

}
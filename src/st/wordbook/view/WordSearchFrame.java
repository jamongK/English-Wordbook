package st.wordbook.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import st.wordbook.controller.WordbookController;
import st.wordbook.vo.WordsVO;

public class WordSearchFrame extends JFrame {
	private WordbookController co = new WordbookController();
	private List<WordsVO> resultList = new ArrayList<WordsVO>();
	
	private JTable resultTable;
	private String columns[] = new String[] { "영어단어", "뜻" };
	private String data[][];

	public WordSearchFrame(String s) {
		super("단어 검색 결과");
		setSize(550, 500);
		setLocationRelativeTo(null);
		
		resultList = co.searchWords(s);

		data = new String[resultList.size()][2];
		for (int i = 0; i < resultList.size(); i++) {
			WordsVO wv = resultList.get(i);
			String[] sArr = {wv.getWord(), wv.getMeaning()};
			data[i] = sArr;
		}
		
		JTable resultTable = new JTable(data, columns);
		
		JScrollPane sp = new JScrollPane(resultTable);
		add(sp);
	}

}

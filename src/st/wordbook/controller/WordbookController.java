package st.wordbook.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import st.wordbook.vo.WordsVO;

public class WordbookController {
	public static List<WordsVO> list = new ArrayList<>();
	
	/**
	 * JTable에 들어있는 값(List)들 추출해 txt 파일로 저장
	 * @param fileURL 저장할 파일경로
	 * @param words JTable에 들어있는 WordsVO들에 대한 ArrayList
	 */
	public void txtSave(String fileURL, List<WordsVO> words) {
		File f = new File(fileURL);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			StringBuilder sb = new StringBuilder();
			
			for (WordsVO wordsVO : words) {
				sb.append(wordsVO.toString());
				sb.append("\n");
			}
			
			bw.write(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 영한테스트 - 정답여부 메소드
	 * @param qst 문제로 출제된 단어(String)
	 * @param ans 사용자가 제출한 답안(단어 뜻)
	 * @param words JTable에 들어있는 WordsVO들에 대한 ArrayList
	 * @return 출제된 문제(단어)에 대한 뜻(단어장에 입력된 Meaning 값)이 일치하면 true, 그 외엔 false
	 */
	public boolean isCorrect(String qst, String ans, List<WordsVO> words) {
		int idx = 0;
		for (WordsVO wordsVO : words) {
			if (wordsVO.getWord().equals(qst)) {
				idx = words.indexOf(wordsVO);
			}
		}
		
		if (words.get(idx).getMeaning().equals(ans)) {
			return true;
		}
		
		return false;
	}

	
	/**
	 * 단어 검색 메소드
	 * @param input 검색값
	 * @return input값을 포함하는 WordsVO들에 대한 List
	 */
	public List<WordsVO> searchWords(String input) {
		List<WordsVO> result = new ArrayList<WordsVO>();
		
		for (WordsVO wordsVO : list) {
			if (wordsVO.getWord().contains(input)) {
				result.add(wordsVO);
			} else if (wordsVO.getMeaning().contains(input)) {
				result.add(wordsVO);
			}
		}
		
		return result;
	}
	
}

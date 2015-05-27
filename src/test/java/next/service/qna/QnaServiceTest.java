package next.service.qna;

import next.dao.qna.QuestionDao;
import next.model.qna.Answer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class QnaServiceTest {
	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	QuestionDao questionDao;

	@Test
	public void testAddAnswer() throws Exception {
		Answer answer = new Answer("asdf", "asdf", 9999);
		qnaService.addAnswer(9999, answer);
	}
}

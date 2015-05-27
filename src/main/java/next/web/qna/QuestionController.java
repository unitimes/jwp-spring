package next.web.qna;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import next.model.qna.Question;
import next.service.qna.ExistedAnotherUserException;
import next.service.qna.QnaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import core.web.Result;

@Controller
@RequestMapping(value = { "", "/questions" })
public class QuestionController {
	private static final Logger logger = LoggerFactory
			.getLogger(QuestionController.class);

	@Resource(name = "qnaService")
	private QnaService qnaService;

	@RequestMapping("")
	public String list(Model model) {
		model.addAttribute("questions", qnaService.findAll());
		return "qna/list";
	}

	@RequestMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		model.addAttribute("question", qnaService.findById(id));
		return "qna/show";
	}

	@RequestMapping("/{id}/form")
	public String modify(@PathVariable long id, Model model) {
		logger.info("mod entered");
		model.addAttribute("question", qnaService.findById(id));
		return "qna/form";
	}
	
	@RequestMapping("/{id}/delete")
	public @ResponseBody Result delete(@PathVariable long id) {
		try {
			qnaService.delete(id);
		} catch(ExistedAnotherUserException e) {
			return Result.fail(e.getMessage());
		}
		return Result.ok();
	}

	@RequestMapping(value = "/{id}/mod", method = RequestMethod.POST)
	public String mod(@PathVariable long id, @Valid Question question,
			BindingResult bindingResult) {
		logger.debug("Question : {}", question);
		if (bindingResult.hasFieldErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				logger.debug("field : {}, error code : {}", error.getField(),
						error.getCode());
			}
			return "qna/form";
		}
		question.setQuestionId(id);
		qnaService.mod(question);
		return "qna/show";
	}

	@RequestMapping("/form")
	public String form(Model model) {
		model.addAttribute("question", new Question());
		return "qna/form";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String save(@Valid Question question, BindingResult bindingResult) {
		logger.debug("Question : {}", question);
		if (bindingResult.hasFieldErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				logger.debug("field : {}, error code : {}", error.getField(),
						error.getCode());
			}
			return "qna/form";
		}
		qnaService.save(question);
		return "redirect:/";
	}
}

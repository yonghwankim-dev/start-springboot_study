package org.zerock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.WebBoard;
import org.zerock.service.WebBoardService;
import org.zerock.vo.PageMarker;
import org.zerock.vo.PageVO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/")
@Log
public class WebBoardController {
    private final WebBoardService webBoardService;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageVO") PageVO pageVO, Model model){
        // bno 기준 내림차순, 1페이지, 10개
        Pageable page = pageVO.makePageable(0, "bno");
        Page<WebBoard> result = webBoardService.findAll(webBoardService.makePredicates(pageVO.getType(), pageVO.getKeyword()),
                                                        page);

        log.info("" + page);
        log.info("" + result);
        log.info("TOTAL PAGE NUMBER: " + result.getTotalPages());

        model.addAttribute("result", new PageMarker<>(result));
    }

    @GetMapping("/register")
    public void registerGET(@ModelAttribute("webBoard") WebBoard webBoard){
        log.info("register get");
    }

    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("webBoard") WebBoard webBoard, RedirectAttributes rttr){
        log.info("register post");
        log.info(""+webBoard);

        webBoardService.save(webBoard);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/boards/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("BNO : " + bno);

        webBoardService.findById(bno).ifPresent(board->model.addAttribute("board",board));
    }

    @GetMapping("/modify")
    public void modify(Long bno, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("MODIFY BNO: " + bno);

        webBoardService.findById(bno).ifPresent(board->model.addAttribute("board", board));
    }
}

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
import org.zerock.persistence.CustomCrudRepository;
import org.zerock.service.WebBoardService;
import org.zerock.vo.PageMarker;
import org.zerock.vo.PageVO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/")
@Log
public class WebBoardController {
//    private final WebBoardService repo;
    private final CustomCrudRepository repo;
    @GetMapping("/list")
    public void list(@ModelAttribute("pageVO") PageVO pageVO, Model model){
        // bno 기준 내림차순, 1페이지, 10개
        Pageable page = pageVO.makePageable(0, "bno");
        Page<Object[]> result = repo.getCustomPage(pageVO.getType(), pageVO.getKeyword(), page);
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

        repo.save(webBoard);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/boards/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("BNO : " + bno);

        repo.findById(bno).ifPresent(board->model.addAttribute("board",board));
    }

    @GetMapping("/modify")
    public void modifyGET(Long bno, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("MODIFY BNO: " + bno);

        repo.findById(bno).ifPresent(board->model.addAttribute("board", board));
    }

    @PostMapping("/modify")
    public String modifyPOST(WebBoard webBoard, PageVO pageVO, RedirectAttributes rttr){
        log.info("Modify webBoard : " + webBoard);

        repo.findById(webBoard.getBno()).ifPresent(origin->{
            origin.setTitle(webBoard.getTitle());
            origin.setContent(webBoard.getContent());
            
            repo.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("bno", origin.getBno());
        });

        // 페이징과 검색했던 결과로 이동하는 경우
        rttr.addAttribute("page", pageVO.getPage());
        rttr.addAttribute("size", pageVO.getSize());
        rttr.addAttribute("type", pageVO.getType());
        rttr.addAttribute("keyword", pageVO.getKeyword());

        return "redirect:/boards/view";
    }

    @PostMapping("/delete")
    public String delete(Long bno, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE BNO : " + bno);

        repo.deleteById(bno);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        rttr.addAttribute("page", pageVO.getPage());
        rttr.addAttribute("size", pageVO.getSize());
        rttr.addAttribute("type", pageVO.getType());
        rttr.addAttribute("keyword", pageVO.getKeyword());

        return "redirect:/boards/list";
    }
}

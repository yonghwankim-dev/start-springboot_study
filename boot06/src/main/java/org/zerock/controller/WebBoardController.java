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
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.WebBoard;
import org.zerock.service.WebBoardService;
import org.zerock.vo.PageVO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/")
@Log
public class WebBoardController {
    private final WebBoardService webBoardService;

    @GetMapping("/list")
    public void list(PageVO pageVO, Model model){
        // bno 기준 내림차순, 1페이지, 10개
        Pageable pageable = pageVO.makePageable(0, "bno");
        Page<WebBoard> webBoardPage = webBoardService.findAll(webBoardService.makePredicates(null, null), pageable);

        log.info("/boards/list : " + pageable);
        log.info("/boards/list : " + webBoardPage);

        model.addAttribute("webBoardPage", webBoardPage);
    }
}

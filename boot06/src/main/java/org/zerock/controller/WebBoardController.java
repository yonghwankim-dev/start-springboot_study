package org.zerock.controller;

import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.vo.PageVO;

@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {
    @GetMapping("/list")
    public void list(PageVO pageVO){
        // bno 기준 내림차순, 1페이지, 10개
        Pageable pageable = pageVO.makePageable(0, "bno");
        log.info("/boards/list : " + pageable);
    }
}

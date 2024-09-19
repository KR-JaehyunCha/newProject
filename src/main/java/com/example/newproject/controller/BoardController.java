package com.example.newproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import com.example.newproject.dto.BoardDTO;
import com.example.newproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boardSave")
    public String boardSave() {
        return "/board/boardSavePage";
    }

    @PostMapping("/boardSave")
    public String boardSaving(BoardDTO boardDTO, Model model) {
    //    System.out.println("제목: " + boardTitle + " 내용: " + boardContent);
        BoardDTO board = boardService.boardSave(boardDTO);
        model.addAttribute("board", board);
        String response = "";

        if(board == null){
            response = "Board Saving Failed";
        }else{
            response = "Board Saving Success: " + board.getId();
        }

        System.out.println(response);

        return "/board/boardDetailPage";
    }

    @GetMapping("/boardList")
    public String boardList(Model model){
        List<BoardDTO> boardDTOList = boardService.boardList();
        model.addAttribute("boardList",boardDTOList);
        return "/board/boardListPage";
    }

    @GetMapping("/boardPagingList")
    public String boardPagingList(@PageableDefault(page = 1) Pageable pageable, Model model){
        // How many Data are you going to Display?
        final int pageLimit = 20;
        // Receiving Data from the Service
        Page<BoardDTO> boardDTOList = boardService.boardPagingList(pageable,pageLimit);
        // How many Page Buttons?
        int blockLimit = 10;
        // Calculating (Starting Page)
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        // Calculating (Ending Page)
        int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();

        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("nowPage",pageable.getPageNumber());
        model.addAttribute("total",boardDTOList.getTotalElements());

        return "/board/boardPagingList";
    }

    @GetMapping("boardDetailPage")
    public String boardDetailPage(@RequestParam("id") Long id, Model model){
        BoardDTO board = boardService.findById(id);
        model.addAttribute("board", board);
        return "/board/boardDetailPage";
    }
}

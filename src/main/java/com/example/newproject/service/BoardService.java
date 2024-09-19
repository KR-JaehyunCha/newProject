package com.example.newproject.service;

import com.example.newproject.BoardRepository;
import com.example.newproject.dto.BoardDTO;
import com.example.newproject.entity.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardDTO boardSave(BoardDTO boardDTO) {

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContent(boardDTO.getBoardContent());

        Long saved_id = boardRepository.save(boardEntity).getId();

        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(saved_id);

        if(optionalBoardEntity.isEmpty()) {
            return null;
        }else{
            BoardDTO board = new BoardDTO();
            board.setId(optionalBoardEntity.get().getId());
            board.setBoardTitle(optionalBoardEntity.get().getBoardTitle());
            board.setBoardContent(optionalBoardEntity.get().getBoardContent());
            return board;
        }
    }

    public List<BoardDTO> boardList() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(boardEntity.getId());
            boardDTO.setBoardTitle(boardEntity.getBoardTitle());
            boardDTO.setBoardContent(boardEntity.getBoardContent());
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    public Page<BoardDTO> boardPagingList(Pageable pageable, int pageLimit) {
        int page = pageable.getPageNumber() - 1;
        Page<BoardEntity> boardEntityList = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        Page<BoardDTO> boardDTOPage = boardEntityList.map(boardEntity -> {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(boardEntity.getId());
            boardDTO.setBoardTitle(boardEntity.getBoardTitle());
            boardDTO.setBoardContent(boardEntity.getBoardContent());
            return boardDTO;
        });
        return boardDTOPage;
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        if(optionalBoardEntity.isEmpty()) {
            return null;
        }else{
            BoardDTO board = new BoardDTO();
            board.setId(optionalBoardEntity.get().getId());
            board.setBoardTitle(optionalBoardEntity.get().getBoardTitle());
            board.setBoardContent(optionalBoardEntity.get().getBoardContent());
            return board;
        }
    }
}

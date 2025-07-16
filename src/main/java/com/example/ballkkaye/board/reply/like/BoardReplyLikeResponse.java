package com.example.ballkkaye.board.reply.like;

import lombok.Data;

public class BoardReplyLikeResponse {
    @Data
    public static class SaveDTO {
        private Integer id;
        private Integer userId;
        private Integer boardId;
        private Integer count;

        public SaveDTO(BoardReplyLike boardReplyLike, Integer count) {
            this.id = boardReplyLike.getId();
            this.userId = boardReplyLike.getUser().getId();
            this.boardId = boardReplyLike.getBoardReply().getId();
            this.count = count;
        }
    }

    @Data
    public static class DeleteDTO {
        private Integer count;

        public DeleteDTO(Integer count) {
            this.count = count;
        }
    }
}

package com.example.ballkkaye.board.like;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardLikeRepository {
    private final EntityManager em;

    public Optional<BoardLike> findByBoardIdAndUserId(Integer boardId, Integer userId) {
        try {
            BoardLike replyLike = em.createQuery(
                            "SELECT brl FROM BoardLike brl WHERE brl.board.id = :boardId AND brl.user.id = :userId",
                            BoardLike.class)
                    .setParameter("boardId", boardId)
                    .setParameter("userId", userId)
                    .getSingleResult();

            return Optional.of(replyLike);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Integer totalCount(Integer boardId) {
        return em.createQuery(
                        "SELECT COUNT(brl) FROM BoardLike brl WHERE brl.board.id = :boardId", Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult().intValue();
    }

    public void save(BoardLike boardLike) {
        em.persist(boardLike);
    }

    public Optional<BoardLike> findById(Integer likeId) {
        BoardLike boardLike = em.find(BoardLike.class, likeId);
        return Optional.ofNullable(boardLike);
    }

    public void deleteById(Integer id) {
        Query query = em.createQuery("delete from BoardLike lo where lo.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}

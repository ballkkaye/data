package com.example.ballkkaye.match;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchRepository matchRepository;
}

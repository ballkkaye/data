package com.example.ballkkaye.stadium;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StadiumService {
    private final StadiumRepository stadiumRepository;
}

package com.example.ballkkaye.user.userMatch;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserMatchController {
    private final UserMatchService userMatchService;
}
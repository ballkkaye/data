package com.example.ballkkaye.team;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamController {
    private final TeamService teamService;
}
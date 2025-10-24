package com.resolve.Re_Solve.platform.entity;

import lombok.Getter;

@Getter
public enum PlatformType {
    BEAKJOON("https://www.acmicpc.net/problem/"),
    PROGRAMMERS("https://school.programmers.co.kr/learn/courses/30/lessons/");
    private final String url;
    PlatformType(String url) {
        this.url = url;
    }
}

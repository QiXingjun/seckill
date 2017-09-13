package com.seckill.entity;

import org.junit.Test;

public class GitTest {

    @Test
    public void testGitLombok() {
        Git git = new Git();
        git.setId("123456789");
        git.setName("gitTest");
        git.setDesc("This is a test pojo,its name is git.");
        System.out.println(git.toString());
    }
}
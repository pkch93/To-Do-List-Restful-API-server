package com.pkch.todolist.domain;

import com.pkch.todolist.api.review.Review;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewTest {

    @Test
    public void createReview(){
        Review review = Review.builder()
                    .title("좀 더 열심히!")
                    .content("하루에 운동 1시간 / 블로그 포스팅 철저히!")
                    .build();
        assertThat(review).isNotNull();
    }
}
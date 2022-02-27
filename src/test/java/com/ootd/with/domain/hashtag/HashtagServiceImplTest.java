package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostHashtag;
import com.ootd.with.domain.post.PostHashtagRepository;
import com.ootd.with.domain.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HashtagServiceImplTest {

    @Autowired EntityManager em;
    @Autowired HashtagService hashtagService;
    @Autowired PostRepository postRepository;
    @Autowired PostHashtagRepository postHashtagRepository;

    @Test
    public void findAllByPostIdTest() {
        // given
        Hashtag hashtag = new Hashtag("hashtag");
        hashtagService.save(hashtag);
        Post post = new Post();
        postRepository.save(post);
        PostHashtag ph = new PostHashtag(post, hashtag);
        postHashtagRepository.save(ph);

        em.flush();
        em.clear();

        // when
        List<Hashtag> hashtagList = hashtagService.findAllByPostId(post.getId());

        // then
        assertThat(hashtagList.size()).isEqualTo(1);
    }

//    @Test
//    public void findPostsByHashtagIdTest() {
//        // given
//        Hashtag hashtag = new Hashtag("hashtag");
//        hashtagService.save(hashtag);
//        Post post = new Post();
//        postRepository.save(post);
//        PostHashtag ph = new PostHashtag(post, hashtag);
//        postHashtagRepository.save(ph);
//
//        em.flush();
//        em.clear();
//
//        // when
//        Page<Post> page = hashtagService.findPostsByHashtagId(hashtag.getId(), 0, 5);
//        List<Post> content = page.getContent();
//
//        // then
//        assertThat(content.size()).isEqualTo(1);
//        assertThat(page.getTotalPages()).isEqualTo(1);
//        assertThat(page.getTotalElements()).isEqualTo(1);
//        assertThat(page.getNumber()).isEqualTo(0);
//    }
}